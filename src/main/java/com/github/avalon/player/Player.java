package com.github.avalon.player;

import com.flowpowered.network.ConnectionManager;
import com.github.avalon.account.PlayerProfile;
import com.github.avalon.character.character.CharacterLiving;
import com.github.avalon.chat.message.ChatColor;
import com.github.avalon.common.math.Vector2;
import com.github.avalon.data.Transform;
import com.github.avalon.dimension.chunk.IChunk;
import com.github.avalon.dimension.dimension.Dimension;
import com.github.avalon.inventory.Inventory;
import com.github.avalon.inventory.inventory.PlayerInventory;
import com.github.avalon.network.ProtocolType;
import com.github.avalon.network.protocol.ProtocolRegistry;
import com.github.avalon.packet.PacketBatch;
import com.github.avalon.packet.packet.Packet;
import com.github.avalon.packet.packet.play.PacketChatMessage;
import com.github.avalon.packet.packet.play.PacketKeepAliveClient;
import com.github.avalon.packet.packet.play.PacketPositionAndLook;
import com.github.avalon.player.attributes.MessageType;
import com.github.avalon.player.attributes.PlayerAttributes;
import com.github.avalon.player.attributes.PlayerSettings;
import com.github.avalon.player.attributes.Status;
import com.github.avalon.player.handler.PlayerChunkProvider;
import com.github.avalon.server.Server;
import io.netty.channel.Channel;

import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.LinkedTransferQueue;

public class Player implements IPlayer {

  public static final int INVALID_MOVES = 10; // This is something like basic antiteleport ac.

  private final Server server;
  private final ConnectionManager connectionManager;
  private final PlayerConnection playerConnection;

  private volatile Status playerStatus = Status.DISCONNECTED;

  private volatile byte[] verifyToken;

  private volatile String verifyUsername;
  private volatile InetSocketAddress virtualHost;

  private volatile int version = -1;

  private volatile long pingMessageId;

  private volatile ActionHandler actionHandler;
  private CharacterLiving controllingCharacter;

  private int currentSlot;
  private Map<Integer, Inventory> inventories;

  private Dimension dimension;
  private PlayerChunkProvider chunkProvider;
  private final List<Vector2> chunkView;

  private PlayerProfile profile;
  private PlayerSettings settings;
  private PlayerAttributes attributes;

  private volatile Queue<Transform> incomingMovements;
  private volatile Transform currentLocation;
  private volatile int locationVerifier;
  private volatile boolean sneaking;

  private int currentInvalidMoves;

  private long lifetime;

  public Player(Server server, Channel channel, ConnectionManager connectionManager) {
    this.server = server;
    playerConnection = new PlayerConnection(this, channel);
    this.connectionManager = connectionManager;

    dimension = getServer().getDimensionModule().getMainDimension();
    currentLocation = new Transform(getDimension(), 0, 100, 0);
    chunkView = new ArrayList<>();

    if (dimension == null) {
      disconnect("Something went wrong with your destination dimension.");
      return;
    }

    attributes = new PlayerAttributes();
    settings = new PlayerSettings();
    inventories = new LinkedHashMap<>();
    incomingMovements = new LinkedTransferQueue<>();
    actionHandler = new ActionHandler(this);
    chunkProvider = new PlayerChunkProvider(this);

    inventories.put(0, new PlayerInventory(this));
  }

  @Override
  public void tick() {
    playerConnection.handleMessages();

    if (playerStatus == Status.DISCONNECTED) {
      getServer().getPlayerSessionRegistry().removePlayer(this);
    } else if (playerStatus == Status.ONLINE) {
      lifetime++;
      actionHandler.handleMovementOut();
      chunkProvider.handleChunk();
    }
  }

  @Override
  public void idle() {
    if (playerConnection.getProtocolType().equals(ProtocolType.PLAY)) {
      pingMessageId = System.currentTimeMillis();
      sendPacket(new PacketKeepAliveClient(pingMessageId));
    } else {
      disconnect("You connection timed out.");
    }
  }

  @Override
  public void sendPacket(Packet<?> packet) {
    playerConnection.send(packet);
  }

  @Override
  public void sendPacket(PacketBatch batch) {
    batch.forEach(this::sendPacket);
  }

  @Override
  public void setProtocol(ProtocolType type) {
    ProtocolRegistry protocol = server.getProtocolContainer().getProtocol(type);
    playerConnection.setCurrentProtocol(type);
    playerConnection.setProtocol(protocol);
  }

  @Override
  public void setVersion(int version) {
    this.version = version;
  }

  @Override
  public void setVerifyToken(byte[] token) {
    verifyToken = token;
  }

  @Override
  public void setVerifyUsername(String username) {
    verifyUsername = username;
  }

  @Override
  public void setVirtualHost(InetSocketAddress address) {
    virtualHost = address;
  }

  @Override
  public int getVersion() {
    return version;
  }

  @Override
  public byte[] getVerifyToken() {
    return verifyToken;
  }

  @Override
  public String getVerifyUsername() {
    return verifyUsername;
  }

  @Override
  public InetSocketAddress getVirtualHost() {
    return virtualHost;
  }

  @Override
  public ConnectionManager getConnectionManager() {
    return connectionManager;
  }

  @Override
  public PlayerConnection getConnection() {
    return playerConnection;
  }

  @Override
  public void teleport(Transform destination) {
    PacketPositionAndLook packet = new PacketPositionAndLook(destination);
    sendPacket(packet);

    currentLocation = destination;
    locationVerifier = packet.getTeleportId();
  }

  @Override
  public void setLocation(Transform transform) {
    currentLocation = transform;
  }

  @Override
  public void disconnect(String reason) {
    actionHandler.handleDisconnect(reason);
  }

  @Override
  public void setDimension(Dimension dimension) {
    this.dimension = dimension;
  }

  @Override
  public Map<Integer, Inventory> getInventories() {
    return inventories;
  }

  @Override
  public void openInventory(Inventory inventory) {}

  @Override
  public void closeInventory(int identifier) {}

  @Override
  public PlayerInventory getInventory() {
    return (PlayerInventory) inventories.get(0);
  }

  @Override
  public void sendMessage(String message) {
    sendMessage(MessageType.CHAT, message);
  }

  @Override
  public void sendSystemMessage(String message) {
    sendMessage(MessageType.SYSTEM_INFO, message);
  }

  @Override
  public void sendGameInfo(String message) {
    sendMessage(MessageType.GAME_INFO, message);
  }

  @Override
  public void sendMessage(MessageType type, String message) {
    PacketChatMessage packet =
        new PacketChatMessage(
            ChatColor.toChat(message),
            type.getType(),
            UUID.randomUUID()); // TODO: Maybe implement players uuids.

    sendPacket(packet);
  }

  @Override
  public void setPlayerProfile(PlayerProfile profile) {
    this.profile = profile;
  }

  @Override
  public IChunk getCurrentChunk() {
    return currentLocation.getChunk();
  }

  @Override
  public CharacterLiving getControllingCharacter() {
    return controllingCharacter;
  }

  @Override
  public Queue<Transform> getIncomingMovements() {
    return incomingMovements;
  }

  @Override
  public void setControllingCharacter(CharacterLiving character) {
    controllingCharacter = character;
  }

  @Override
  public void setSneaking(boolean sneaking) {
    this.sneaking = sneaking;
  }

  @Override
  public long getLifeTime() {
    return lifetime;
  }

  @Override
  public boolean isSneaking() {
    return sneaking;
  }

  @Override
  public IActionHandler getActionHandler() {
    return actionHandler;
  }

  @Override
  public Transform getLocation() {
    return currentLocation;
  }

  @Override
  public Dimension getDimension() {
    return dimension;
  }

  @Override
  public Server getServer() {
    return server;
  }

  @Override
  public PlayerProfile getPlayerProfile() {
    return profile;
  }

  @Override
  public PlayerSettings getSettings() {
    return settings;
  }

  @Override
  public PlayerAttributes getPlayerAttributes() {
    return attributes;
  }

  @Override
  public Status getPlayerStatus() {
    return playerStatus;
  }

  @Override
  public List<Vector2> getChunkView() {
    return chunkView;
  }

  @Override
  public void setPlayerStatus(Status status) {
    playerStatus = status;
  }
}
