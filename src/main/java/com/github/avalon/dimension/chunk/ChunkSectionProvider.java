package com.github.avalon.dimension.chunk;

import com.github.avalon.data.Material;
import com.github.avalon.data.Transform;
import com.github.avalon.dimension.DimensionManager;
import com.github.avalon.packet.packet.play.PacketBlockChange;
import com.github.avalon.player.IPlayer;

import java.util.Collection;

public class ChunkSectionProvider {

  private final Chunk chunk;
  private final ChunkSection[] sections;

  public ChunkSectionProvider(Chunk chunk) {
    this.chunk = chunk;

    sections = new ChunkSection[16];
    for (int y = 0; y < Chunk.CHUNK_SECTIONS; y++) {
      sections[y] = new NetworkChunkSection(chunk, chunk.getX(), y, chunk.getZ());
    }
  }

  /**
   * Return the material at selected location. Because material call can/can not be called we
   * retrieving material directly from {@link ChunkSection}.
   *
   * @param x,y,z Location of material.
   * @return Material at specified location.
   */
  private Material getMaterialAt(int x, int y, int z) {
    return sections[y / Chunk.CHUNK_SECTION_SIZE].getMaterialAt(
        x,
        y % Chunk.CHUNK_SECTION_SIZE == 0 ? Chunk.CHUNK_SECTION_SIZE : y % Chunk.CHUNK_SECTION_SIZE,
        z);
  }

  /**
   * Sets the material of block at specified location.
   *
   * @param x,y,z Location of block/material.
   * @param material Material that will be set.
   */
  private void setMaterialAt(int x, int y, int z, Material material) {
    sections[Math.floorDiv(y, Chunk.CHUNK_SECTION_SIZE)].setMaterialAt(
        x,
        y % Chunk.CHUNK_SECTION_SIZE == 0 ? Chunk.CHUNK_SECTION_SIZE : y % Chunk.CHUNK_SECTION_SIZE,
        z,
        material);
  }

  /**
   * @param x,y,z Location of block.
   * @param players
   */
  private void updateMaterialAt(int x, int y, int z, Collection<IPlayer> players) {
    for (IPlayer player : players) {
      updateMaterialAt(x, y, z, player);
    }
  }

  /**
   * @param x,y,z Location of block.
   * @param players
   */
  private void updateMaterialAt(int x, int y, int z, IPlayer... players) {
    for (IPlayer player : players) {
      updateMaterialAt(x, y, z, player);
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
  private void updateMaterialAt(int x, int y, int z, IPlayer receiver) {
    PacketBlockChange packet =
        new PacketBlockChange(x, y, z, getMaterialAt(x, y, z).getIdentifier());
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
    Collection<IPlayer> receivers = chunk.getChunkBatch().getDimension().getPlayers().values();
    if (receivers.remove(caller)) {
      DimensionManager.LOGGER.info(
          "Registering weird behavior with block placement called by player (%s) at location %s",
          caller.getPlayerProfile().getName(), location.toString());
    }

    setMaterialAt(location.getBlockX(), location.getBlockY(), location.getBlockZ(), material);
    updateMaterialAt(location.getBlockX(), location.getBlockY(), location.getBlockZ(), receivers);
  }

  /**
   * Sets the block at specified location to material and sends the update packet to all players.
   *
   * @param location Location of block.
   * @param material Material that will be placed.
   */
  public void placeBlockAsSystem(Transform location, Material material) {
    Collection<IPlayer> receivers = chunk.getChunkBatch().getDimension().getPlayers().values();
    setMaterialAt(location.getBlockX(), location.getBlockY(), location.getBlockZ(), material);
    updateMaterialAt(location.getBlockX(), location.getBlockY(), location.getBlockZ(), receivers);
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

  public Chunk getChunk() {
    return chunk;
  }
}
