package com.github.avalon.dimension.chunk;

import com.github.avalon.data.Material;
import com.github.avalon.data.Transform;
import com.github.avalon.dimension.DimensionModule;
import com.github.avalon.packet.packet.play.PacketBlockChange;
import com.github.avalon.player.IPlayer;

import java.util.Collection;

public class ChunkSectionProvider {

  private final IChunk chunk;
  private final IChunkSection[] sections;

  public ChunkSectionProvider(IChunk chunk) {
    this.chunk = chunk;

    sections = new IChunkSection[16];
    for (int y = 0; y < IChunk.CHUNK_SECTIONS; y++) {
      sections[y] = new ChunkSection(chunk, chunk.getX(), y, chunk.getZ());
    }
  }

  /**
   * Return the material at specified location. Because material call can/can not be called we
   * retrieving material directly from {@link IChunkSection}.
   *
   * @param x,y,z Location of material.
   * @return Material at specified location.
   */
  private Material getMaterialAt(int x, int y, int z) {
    return sections[y / IChunk.CHUNK_SECTION_SIZE].getMaterialAt(
        x,
        y % IChunk.CHUNK_SECTION_SIZE == 0
            ? IChunk.CHUNK_SECTION_SIZE
            : y % IChunk.CHUNK_SECTION_SIZE,
        z);
  }

  /**
   * Return the state at specified location. Because material call can/can not be called we
   * retrieving material directly from {@link IChunkSection}.
   *
   * @param x,y,z Location of material.
   * @return Material at specified location.
   */
  private int getStateAt(int x, int y, int z) {
    return sections[y / IChunk.CHUNK_SECTION_SIZE].getStateAt(
        x,
        y % IChunk.CHUNK_SECTION_SIZE == 0
            ? IChunk.CHUNK_SECTION_SIZE
            : y % IChunk.CHUNK_SECTION_SIZE,
        z);
  }

  /**
   * Sets the material of block at specified location.
   *
   * @param x,y,z Location of block/material.
   * @param material Material that will be set.
   */
  private void setMaterialAt(int x, int y, int z, Material material) {
    sections[Math.floorDiv(y, IChunk.CHUNK_SECTION_SIZE)].setMaterialAt(
        x,
        y % IChunk.CHUNK_SECTION_SIZE == 0
            ? IChunk.CHUNK_SECTION_SIZE
            : y % IChunk.CHUNK_SECTION_SIZE,
        z,
        material);
  }

  /**
   * Sets the state of block at specified location.
   *
   * @param x,y,z Location of block/material.
   * @param state State that will be set.
   */
  private void setStateAt(int x, int y, int z, int state) {
    sections[Math.floorDiv(y, IChunk.CHUNK_SECTION_SIZE)].setStateAt(
        x,
        y % IChunk.CHUNK_SECTION_SIZE == 0
            ? IChunk.CHUNK_SECTION_SIZE
            : y % IChunk.CHUNK_SECTION_SIZE,
        z,
        state);
  }

  /**
   * @param x,y,z Location of block.
   * @param players
   */
  private void updateBlockAt(int x, int y, int z, Collection<IPlayer> players) {
    for (IPlayer player : players) {
      updateBlockAt(x, y, z, player);
    }
  }

  /**
   * @param x,y,z Location of block.
   * @param players
   */
  private void updateBlockAt(int x, int y, int z, IPlayer... players) {
    for (IPlayer player : players) {
      updateBlockAt(x, y, z, player);
    }
  }

  /**
   * Sends the placket about block change to all clients currently.
   *
   * <p>//TODO: This should be send only to players that are currently have loaded this chunk.
   *
   * @param x,y,z Location of block.
   * @param receiver Client that will receive packet about block change.
   */
  private void updateBlockAt(int x, int y, int z, IPlayer receiver) {
    int material = getMaterialAt(x, y, z).getIdentifier();
    int state = getStateAt(x, y, z);

    PacketBlockChange packet = new PacketBlockChange(x, y, z, material + state);
    receiver.sendPacket(packet);
  }

  /**
   * Sets the block at specified location to material and sends the update packet to all players
   * except the caller.
   *
   * @param caller Player that should place the block.
   * @param location Location of block.
   * @param material Material that will be placed.
   */
  public void placeBlockAsPlayer(IPlayer caller, Transform location, Material material) {
    Collection<IPlayer> receivers = chunk.getChunkBatch().getDimension().getPlayers();
    if (receivers.remove(caller)) {
      DimensionModule.LOGGER.info(
          "Registering weird behavior with block placement called by player (%s) at location %s",
          caller.getPlayerProfile().getName(), location.toString());
    }

    setMaterialAt(location.getBlockX(), location.getBlockY(), location.getBlockZ(), material);
    updateBlockAt(location.getBlockX(), location.getBlockY(), location.getBlockZ(), receivers);
  }

  /**
   * Sets the block at specified location to material and sends the update packet to all players.
   *
   * @param location Location of block.
   * @param material Material that will be placed.
   */
  public void placeBlockAsSystem(Transform location, Material material) {
    Collection<IPlayer> receivers = chunk.getChunkBatch().getDimension().getPlayers();
    setMaterialAt(location.getBlockX(), location.getBlockY(), location.getBlockZ(), material);
    updateBlockAt(location.getBlockX(), location.getBlockY(), location.getBlockZ(), receivers);
  }

  /**
   * Sets the state at specified location to state and sends the update packet to all players.
   *
   * @param location Location of block.
   * @param state State that will be changed.
   */
  public void setStateAsSystem(Transform location, int state) {
    Collection<IPlayer> receivers = chunk.getChunkBatch().getDimension().getPlayers();
    setStateAt(location.getBlockX(), location.getBlockY(), location.getBlockZ(), state);
    updateBlockAt(location.getBlockX(), location.getBlockY(), location.getBlockZ(), receivers);
  }

  /**
   * Returns the material of block at specified location.
   *
   * @param location Location of block.
   * @return Material of block. If nothing is found then Material.AIR is returned.
   */
  public Material getMaterial(Transform location) {
    return getMaterialAt(location.getBlockX(), location.getBlockY(), location.getBlockZ());
  }

  /**
   * Returns the state of block at specified location.
   *
   * @param location Location of block.
   * @return State of block. If nothing is found then 0 is returned.
   */
  public int getState(Transform location) {
    return getStateAt(location.getBlockX(), location.getBlockY(), location.getBlockZ());
  }

  public IChunk getChunk() {
    return chunk;
  }
}
