package com.github.avalon.dimension.chunk;

import com.github.avalon.block.block.Block;
import com.github.avalon.common.math.Vector2;
import com.github.avalon.data.Material;
import com.github.avalon.data.Transform;
import com.github.avalon.dimension.DimensionModule;
import com.github.avalon.dimension.dimension.Dimension;
import com.github.avalon.nbt.serialization.NamedBinarySerializer;
import com.github.avalon.nbt.tag.Tag;
import com.github.avalon.nbt.tag.TagCompound;
import com.github.avalon.nbt.tag.array.TagLongArray;

import java.util.Arrays;
import java.util.Collection;

/**
 * This class provides methods that are used by {@link DimensionModule} to manage {@link Dimension}
 * data. IChunk is 16x16 segment of blocks. From which is created whole world.
 *
 * @author Horizon
 * @version 1.0
 */
public interface IChunk extends NamedBinarySerializer {

  int CHUNK_SECTIONS = 16;
  int CHUNK_SECTION_SIZE = 16;

  IChunkBatch getChunkBatch();

  /** @return Returns the position of chunk written in {@link Vector2} */
  Vector2 getPosition();

  /** @return X coordinate of chunk in the world. */
  int getX();

  /** @return Z coordinate of chunk in the world. */
  int getZ();

  /** @return Returns the current status of the chunk. */
  ChunkStatus getStatus();

  /**
   * Set the status of the chunk.
   *
   * @param chunkStatus Status of the chunk.
   */
  void setStatus(ChunkStatus chunkStatus);

  /**
   * Returns the collection of the characters that are currently contained in this chunk.
   *
   * @return {@link Collection} of characters.
   */
  Collection<com.github.avalon.character.character.Character> getCharacters();

  /**
   * Will addPlayer the {@link Character} to this current chunk.
   *
   * @param character Character to addPlayer.
   */
  void characterEnterChunk(com.github.avalon.character.character.Character character);

  /**
   * Remove character from this chunk by identifier..
   *
   * @param characterIdentifier Id of the character.
   */
  void characterLeaveChunk(int characterIdentifier);

  /**
   * Get the character from chunk by its identifier.
   *
   * @param characterIdentifier Character identifier.
   * @return Character that will be returned.
   */
  com.github.avalon.character.character.Character getCharacter(int characterIdentifier);

  /**
   * Tries to load the chunk and if load is successful, returns {@code ChunkStatus.LOADED}.
   *
   * @return Status of the chunk.
   */
  ChunkStatus loadChunk();

  /**
   * Tries to unload the chunk and if unload is successful, returns {@code ChunkStatus.UNLOADED}.
   *
   * @param force If chunk should be unloaded by force in exchange of instability or something like
   *     that.
   * @return Status of the chunk.
   */
  ChunkStatus unloadChunk(boolean force);

  /**
   * {@link IChunkSection} should not be edit otherwise client can crash or something else can
   * happen. Everytime we want to edit these sections we should unload this chunk and then load it
   * again.
   *
   * @return IChunk sections of the current chunk.
   */
  ChunkSectionProvider getProvider();

  /**
   * Tries to find highest block at specified location. If nothing is found then null is returned.
   *
   * @param x Location x
   * @param z Location z
   * @return Returns block if block is valid otherwise null is returned.
   */
  default Block getHighestBlockAt(int x, int z) {
    Transform transform = new Transform(getChunkBatch().getDimension(), x, 256, z);
    Block block = null;

    for (int y = 255; y >= 0; y--) {
      transform = transform.setY(y);
      Material material = getProvider().getMaterial(transform);
      if (material != Material.AIR) {
        block = material.createBlock(transform);
      }
    }

    return block;
  }

  // TODO: Add block data that will be stored propably in byte array.

  @Override
  default Tag serialize(Object object) {
    TagCompound parent = new TagCompound("");

    int[] heightmap = new int[256];
    int index = 0;

    for (int x = 1; x <= 16; x++) {
      for (int z = 1; z <= 16; z++) {
        Block block =
            getHighestBlockAt(
                (getX() == 0 ? 0 : getX() * 16 + x), (getZ() == 0 ? 0 : getZ() * 16) + z);
        if (block != null) {
          heightmap[index++] = block.getTransform().getBlockY();
        }
      }
    }

    // TODO Rework this to some class.
    Long[] array = new Long[37];
    Arrays.fill(array, 0L);

    long entry = 0;
    index = 0;
    int arrayIndex = 0;

    for (int datum : heightmap) {
      if (index == 0) {
        entry |= (long) datum << 0x01;
      } else if (index == 7) {
        entry |= (long) datum << (0x05 * index);
        array[arrayIndex++] = entry;
        entry = 0;
        index = 0;
      } else {
        entry |= (long) datum << (0x05 * index);
      }

      index++;
    }

    TagLongArray motionBlocking = new TagLongArray("MOTION_BLOCKING", array);
    TagLongArray worldSurface = new TagLongArray("WORLD_SURFACE", new Long[] {});
    parent.add(motionBlocking);
    parent.add(worldSurface);
    return parent;
  }
}
