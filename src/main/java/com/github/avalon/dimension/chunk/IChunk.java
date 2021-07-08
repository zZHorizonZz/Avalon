package com.github.avalon.dimension.chunk;

import com.github.avalon.common.math.Vector2;
import com.github.avalon.dimension.DimensionModule;
import com.github.avalon.dimension.dimension.Dimension;
import com.github.avalon.nbt.serialization.NamedBinarySerializer;
import com.github.avalon.nbt.tag.Tag;
import com.github.avalon.nbt.tag.TagCompound;
import com.github.avalon.nbt.tag.array.TagLongArray;

import java.util.Arrays;
import java.util.Collection;

/**
 * This class provides methods that are used by {@link
 * DimensionModule} to manage {@link Dimension} data. IChunk is
 * 16x16 segment of blocks. From which is created whole world.
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

  // TODO: Add block data that will be stored propably in byte array.

  @Override
  default Tag serialize(Object object) {
    TagCompound parent = new TagCompound("");

    Long[] array = new Long[37];
    Arrays.fill(array, 0x0100804020100804L);
    TagLongArray motionBlocking = new TagLongArray("MOTION_BLOCKING", array);
    TagLongArray worldSurface = new TagLongArray("WORLD_SURFACE", array);
    parent.add(motionBlocking);
    parent.add(worldSurface);
    return parent;
  }
}
