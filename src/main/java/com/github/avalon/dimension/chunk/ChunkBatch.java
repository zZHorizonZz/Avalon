package com.github.avalon.dimension.chunk;

import com.github.avalon.common.system.TripleMap;
import com.github.avalon.dimension.dimension.Dimension;

/**
 * Chunk batch is something like {@link Chunk} but instead of blocks it contains the chunks.
 *
 * @author Horizon
 * @version 1.0
 */
public interface ChunkBatch {

  Dimension getDimension();

  /** @return Returns the width of the batch in chunks. */
  int getSizeOfBatch();

  /** @return X coordinate of chunk batch in the world. */
  int getX();

  /** @return Z coordinate of chunk batch in the world. */
  int getZ();

  /**
   * Returns the list of chunks in current batch.
   *
   * @return List of chunks.
   */
  TripleMap<Integer, Integer, Chunk> getChunks();

  /**
   * Create chunk at specified coordinates.
   *
   * @param x X position
   * @param z Z position
   */
  Chunk createChunk(int x, int z);

  /**
   * Tries to find the chunk in this current batch.
   *
   * @param x X coordinates.
   * @param z Z coordinates.
   * @return Chunk if exists.
   */
  Chunk getChunk(int x, int z);

  /** Performs the update of all chunks in this batch. */
  void tick();
}
