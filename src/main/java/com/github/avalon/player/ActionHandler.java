package com.github.avalon.player;

import com.github.avalon.account.PlayerProfile;
import com.github.avalon.data.Transform;
import com.github.avalon.dimension.chunk.Chunk;
import com.github.avalon.dimension.dimension.Dimension;
import com.github.avalon.network.ProtocolType;
import com.github.avalon.packet.packet.login.PacketLoginSuccess;
import com.github.avalon.packet.packet.play.PacketEntityAction;
import com.github.avalon.packet.packet.play.PacketPlayerPositionAndRotation;
import com.github.avalon.server.NetworkServer;

import java.util.Queue;

public class ActionHandler implements IActionHandler {

  private final Player player;
  private final PlayerConnection connection;
  private final NetworkServer server;

  private boolean blockPlaced;

  public ActionHandler(Player player) {
    this.player = player;
    connection = player.getConnection();
    server = player.getServer();
  }

  @Override
  public void handleMovementIn(PacketPlayerPositionAndRotation packet) {
    double x = packet.getX();
    double y = packet.getY();
    double z = packet.getZ();
    float yaw = packet.getYaw();
    float pitch = packet.getPitch();

    player.setLocation(player.getLocation().setTransform(x, y, z, yaw, pitch));
    Transform moveTransform = new Transform(player.getDimension(), x, y, z, yaw, pitch);
    player.getIncomingMovements().offer(moveTransform);
  }

  // TODO Create packet service that will manage all packets. And createte PacketHandler annotation
  // similar to bukkits eventhandler.

  @Override
  public void handleMovementOut() {
    Queue<Transform> movements = player.getIncomingMovements();
    if (!movements.isEmpty()) {

      for (Transform lastMove : movements) {
        // player.getControllingCharacter().move(lastMove);
        // player.teleport(lastMove);
      }

      movements.clear();
    }
  }

  @Override
  public void handleAction(PacketEntityAction packet) {
    switch (packet.getAction()) {
      case START_SNEAKING:
        {
          player.setSneaking(true);
          break;
        }

      case STOP_SNEAKING:
        {
          player.setSneaking(false);
          break;
        }
      case LEAVE_BED:
      case START_SPRINTING:
      case START_JUMP_HORSE:
      case STOP_SPRINTING:
      case STOP_JUMP_HORSE:
      case OPEN_HORSE_INVENTORY:
      case UNKNOWN:
      case START_ELYTRA:
        break;
    }
  }

  @Override
  public void handleLogin(PlayerProfile profile) {
    if (!connection.isActive()) {
      return;
    }

    player.setPlayerProfile(profile);
    player.sendPacket(new PacketLoginSuccess(profile.getUUID(), profile.getName()));
    player.setProtocol(ProtocolType.PLAY);
    server.getPlayerSessionRegistry().addPlayer(player);
  }

  @Override
  public void handlePong(long pingId) {}

  @Override
  public void handleDisconnect(String reason) {}

  // TODO Rework chunk chandler and playerhandler (actionhandler) to work as one class with inputs
  // by players for better handling.
  // Should be 5
  @Override
  public void handleChunk() {
    Chunk currentChunk = player.getLocation().getChunk();
    Dimension dimension = player.getDimension();
    for (int x = -1; x <= 1; x++) {
      for (int z = -1; z <= 1; z++) {
        int xPosition = currentChunk.getX() + x;
        int zPosition = currentChunk.getZ() + z;

        Chunk chunk = dimension.getChunkAt(xPosition, zPosition);

        if (!player.getChunkView().contains(chunk.getPosition())) {
          player.getDimension().getDimensionManager().getChunkHandler().loadToClient(player, chunk);
          player.getChunkView().add(chunk.getPosition());
        }
      }
    }
  }

  @Override
  public void handleChat() {}
}
