package com.github.avalon.dimension.chunk;

import com.github.avalon.data.Material;

import java.util.Map;

/**
 * IChunk section is used in chunk it's created by 16*16*16 cube of blocks. {@link
 * com.github.avalon.packet.packet.play.PacketChunkData} sends chunk column that is created by these
 * sections. In versions below 1.17 usually IChunk column was created by 16 sections. Also we should
 * determine whether is chunk section empty or not for writing of these information's to the {@link
 * com.github.avalon.network.PacketBuffer}
 *
 * @author Horizon
 * @version 1.1
 */
public interface IChunkSection {

  IChunk getChunk();

  int getX();

  int getY();

  int getZ();

  boolean isEmpty();

  void setEmpty(boolean empty);

  Map<Integer, Integer> getBlocks();

  default Material getMaterialAt(int x, int y, int z) {
    int location = x | (y << 0x08) | (z << 0x10);

    int material = getBlocks().getOrDefault(location, 0);
    return Material.getByIdentifier(material != 0 ? (material >> 0x08) & 0xFF : 0);
  }

  default int getStateAt(int x, int y, int z) {
    int location = x | (y << 0x08) | (z << 0x10);

    int state = getBlocks().getOrDefault(location, 0);
    return state != 0 ? state & 0xFF : 0;
  }

  /**
   * Sets the new material for block and reset it's state.
   *
   * @since 1.0
   * @param x,y,z Location of block.
   * @param material Material that will be set.
   */
  default void setMaterialAt(int x, int y, int z, Material material) {
    int location = x | (y << 0x08) | (z << 0x10);
    if (getBlocks().containsKey(location)) {
      if (material.equals(Material.AIR)) {
        getBlocks().remove(location);
        return;
      }

      int block = material.getIdentifier() << 0x08;
      getBlocks().replace(location, block);
    } else if (!material.equals(Material.AIR)) {
      int block = material.getIdentifier() << 0x08;
      getBlocks().put(location, block);
    }
  }

  /**
   * Sets the state of block if block at specified location exists.
   *
   * @since 1.1
   * @param x,y,z Location of block.
   * @param state State of block.
   */
  default void setStateAt(int x, int y, int z, int state) {
    int location = x | (y << 0x08) | (z << 0x10);
    if (getBlocks().containsKey(location)) {
      int currentMaterial = getMaterialAt(x, y, z).getIdentifier();

      state = state | (currentMaterial << 0x08);
      getBlocks().replace(location, state);
    }
  }
}
