package com.github.avalon.dimension.chunk;

import com.github.avalon.data.Material;

import java.util.Map;

/**
 * IChunk section is used in chunk it's created by 16*16*16 cube of blocks. {@link
 * com.github.avalon.packet.packet.play.PacketChunkData} sends chunk column that is created by
 * these sections. In versions below 1.17 usually IChunk column was created by 16 sections. Also we
 * should determine whether is chunk section empty or not for writing of these information's to the
 * {@link com.github.avalon.network.PacketBuffer}
 *
 * @author Horizon
 * @version 1.0
 */
public interface IChunkSection {

  IChunk getChunk();

  int getX();

  int getY();

  int getZ();

  boolean isEmpty();

  void setEmpty(boolean empty);

  Map<Integer, Material> getMaterials();

  default Material getMaterialAt(int x, int y, int z) {
    int location = x | (y << 0x08) | (z << 0x10);
    return getMaterials().getOrDefault(location, Material.AIR);
  }

  default void setMaterialAt(int x, int y, int z, Material material) {
    int location = x | (y << 0x08) | (z << 0x10);
    if (getMaterials().containsKey(location)) {
      if (material.equals(Material.AIR)) {
        getMaterials().remove(location);
        return;
      }
      getMaterials().replace(location, material);
    } else if (material != Material.AIR) {
      getMaterials().put(location, material);
    }
  }
}
