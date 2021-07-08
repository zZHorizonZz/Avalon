package com.github.avalon.packet.packet.play;

import com.github.avalon.common.data.DataType;
import com.github.avalon.common.exception.NotSupportedException;
import com.github.avalon.dimension.dimension.Difficulty;
import com.github.avalon.dimension.dimension.Dimension;
import com.github.avalon.dimension.dimension.TerrainType;
import com.github.avalon.nbt.tag.TagCompound;
import com.github.avalon.network.ProtocolType;
import com.github.avalon.packet.annotation.PacketRegister;
import com.github.avalon.packet.packet.Packet;
import com.github.avalon.packet.schema.ArrayScheme;
import com.github.avalon.packet.schema.FunctionScheme;
import com.github.avalon.packet.schema.PacketStrategy;
import com.github.avalon.player.IPlayer;
import com.github.avalon.player.PlayerConnection;
import com.github.avalon.player.attributes.PlayerAttributes;
import com.github.avalon.resource.data.ResourceIdentifier;
import com.github.avalon.server.Server;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Join game packet is packet that is sending all necessary information to the client. This packet
 * contains entity id, hardcore mode, gamemode, previous gamemode, array of world identifiers,
 * dimension codecs, and dimension nbts, name of current player world, hashed seed, maximum players,
 * view distance, reduced debug info, enabled respawn screen, is debug world, is flat world.
 *
 * <h3>Packet Strategy</h3>
 *
 * <ul>
 *   <li>1. ID of entity that will player control.
 *   <li>2. If player should be in hardcore mode.
 *   <li>3. Player's gamemode.
 *   <li>4. Previous player's gamemode. (If previous gamemode is unknown should be -1)
 *   <li>5. Array of world identifiers. (This is created from 2 values a) length of array b) array
 *       list itself.
 *   <li>6. Dimension codecs is registry of dimension that contains all information that dimension
 *       has.
 *   <li>7. Information about current dimension is same as information in list before.
 *   <li>8. Identifier of world. Needs to be specified in array before.
 *   <li>9. First 8 bytes of the SHA-256 hash of the world's seed. Used client side for biome noise.
 *       According to <url>www.wiki.vg</url>
 *   <li>10. Maximum players. This value is not used, it was used in older versions of minecraft to
 *       create tab table.
 *   <li>11. Render distance that player should use.
 *   <li>12. Reduced debug information. This is used to disable extended debug information on client
 *       side (Notchian client in other clients this could be bypassed).
 *   <li>13. If client should show death screen after death.
 *   <li>14. Debug mode. This is used probably by mojang developers it generates debug world with
 *       all world and can not be modified via packets.
 *   <li>15. If world is flat. This will move fog (dark side of skybox) to 0 instead of 63.
 * </ul>
 *
 * @version 1.1
 */
@PacketRegister(
    operationCode = 0x24,
    protocolType = ProtocolType.PLAY,
    direction = PacketRegister.Direction.CLIENT)
public class PacketJoinGame extends Packet<PacketJoinGame> {

  public PacketStrategy strategy =
      new PacketStrategy(
          new FunctionScheme<>(DataType.INTEGER, this::getIdentifier, this::setIdentifier),
          new FunctionScheme<>(DataType.BOOLEAN, this::isHardcore, this::setHardcore),
          new FunctionScheme<>(DataType.UNSIGNED_BYTE, this::getGamemode, this::setGamemode),
          new FunctionScheme<>(DataType.BYTE, this::getPreviousGameMode, this::setPreviousGameMode),
          new ArrayScheme<>(DataType.IDENTIFIER, this::getDimensions, this::setDimensions),
          new FunctionScheme<>(DataType.NBT_TAG, this::getDimensionCodec, this::setDimensionCodec),
          new FunctionScheme<>(DataType.NBT_TAG, this::getDimension, this::setDimension),
          new FunctionScheme<>(
              DataType.IDENTIFIER, this::getDimensionIdentifier, this::setDimensionIdentifier),
          new FunctionScheme<>(DataType.LONG, this::getHashedSeed, this::setHashedSeed),
          new FunctionScheme<>(DataType.VARINT, this::getMaxPlayers, this::setMaxPlayers),
          new FunctionScheme<>(DataType.VARINT, this::getViewDistance, this::setViewDistance),
          new FunctionScheme<>(DataType.BOOLEAN, this::isReduceDebug, this::setReduceDebug),
          new FunctionScheme<>(DataType.BOOLEAN, this::isRespawnScreen, this::setRespawnScreen),
          new FunctionScheme<>(DataType.BOOLEAN, this::isDebugMode, this::setDebugMode),
          new FunctionScheme<>(DataType.BOOLEAN, this::isFlatWorld, this::setFlatWorld));

  private int identifier;

  private boolean hardcore;
  private byte gamemode;
  private byte previousGameMode;
  private List<ResourceIdentifier> dimensions;
  private TagCompound dimensionCodec;
  private TagCompound dimension;
  private ResourceIdentifier dimensionIdentifier;
  private long hashedSeed;
  private int maxPlayers;
  private int viewDistance;
  private boolean reduceDebug;
  private boolean respawnScreen;
  private boolean debugMode;
  private boolean flatWorld;

  public PacketJoinGame(IPlayer player) {
    Server server = player.getServer();

    PlayerAttributes attributes = player.getPlayerAttributes();
    Dimension playerDimension = player.getDimension();

    identifier = 1;
    hardcore = playerDimension.getDifficulty().equals(Difficulty.HARD);
    gamemode = (byte) attributes.getGameMode().getIndex();
    previousGameMode = (byte) attributes.getPreviousGameMode().getIndex();
    dimensionIdentifier = playerDimension.getResourceIdentifier();
    dimensions =
        server.getDimensionModule().getDimensions().stream()
            .map(Dimension::getResourceIdentifier)
            .collect(Collectors.toList());
    dimensionCodec =
        (TagCompound) server.getDimensionModule().getDimensionRegistry().serialize(this);
    dimension =
        (TagCompound)
            playerDimension.getDimensionData().serialize(playerDimension.getDimensionData());
    hashedSeed = playerDimension.getHashedSeed();
    maxPlayers = server.getServerData().getMaxPlayers();
    viewDistance = attributes.getViewDistance();
    reduceDebug = false;
    respawnScreen = false;
    debugMode = server.getServerData().isDebugWorld();
    flatWorld = playerDimension.getTerrainType().equals(TerrainType.FLAT);
  }

  public PacketJoinGame() {}

  @Override
  public boolean isAsync() {
    return false;
  }

  @Override
  public void handle(PlayerConnection connection, PacketJoinGame packetJoinGame) {
    throw new NotSupportedException("This type of operation is not valid for this packet.");
  }

  @Override
  public PacketStrategy getStrategy() {
    return strategy;
  }

  public int getIdentifier() {
    return identifier;
  }

  public void setIdentifier(int identifier) {
    this.identifier = identifier;
  }

  public boolean isHardcore() {
    return hardcore;
  }

  public void setHardcore(boolean hardcore) {
    this.hardcore = hardcore;
  }

  public byte getGamemode() {
    return gamemode;
  }

  public void setGamemode(byte gamemode) {
    this.gamemode = gamemode;
  }

  public byte getPreviousGameMode() {
    return previousGameMode;
  }

  public void setPreviousGameMode(byte previousGameMode) {
    this.previousGameMode = previousGameMode;
  }

  public List<ResourceIdentifier> getDimensions() {
    return dimensions;
  }

  public void setDimensions(List<ResourceIdentifier> dimensions) {
    this.dimensions = dimensions;
  }

  public TagCompound getDimensionCodec() {
    return dimensionCodec;
  }

  public void setDimensionCodec(TagCompound dimensionCodec) {
    this.dimensionCodec = dimensionCodec;
  }

  public TagCompound getDimension() {
    return dimension;
  }

  public void setDimension(TagCompound dimension) {
    this.dimension = dimension;
  }

  public ResourceIdentifier getDimensionIdentifier() {
    return dimensionIdentifier;
  }

  public void setDimensionIdentifier(ResourceIdentifier dimensionIdentifier) {
    this.dimensionIdentifier = dimensionIdentifier;
  }

  public long getHashedSeed() {
    return hashedSeed;
  }

  public void setHashedSeed(long hashedSeed) {
    this.hashedSeed = hashedSeed;
  }

  public int getMaxPlayers() {
    return maxPlayers;
  }

  public void setMaxPlayers(int maxPlayers) {
    this.maxPlayers = maxPlayers;
  }

  public int getViewDistance() {
    return viewDistance;
  }

  public void setViewDistance(int viewDistance) {
    this.viewDistance = viewDistance;
  }

  public boolean isReduceDebug() {
    return reduceDebug;
  }

  public void setReduceDebug(boolean reduceDebug) {
    this.reduceDebug = reduceDebug;
  }

  public boolean isRespawnScreen() {
    return respawnScreen;
  }

  public void setRespawnScreen(boolean respawnScreen) {
    this.respawnScreen = respawnScreen;
  }

  public boolean isDebugMode() {
    return debugMode;
  }

  public void setDebugMode(boolean debugMode) {
    this.debugMode = debugMode;
  }

  public boolean isFlatWorld() {
    return flatWorld;
  }

  public void setFlatWorld(boolean flatWorld) {
    this.flatWorld = flatWorld;
  }
}
