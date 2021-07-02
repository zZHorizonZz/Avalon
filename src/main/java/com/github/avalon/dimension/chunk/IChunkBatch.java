package com.github.avalon.dimension.chunk;

import com.github.avalon.common.system.TripleMap;
import com.github.avalon.dimension.dimension.Dimension;

/**
 * IChunk batch is something like {@link IChunk} but instead of blocks it contains the chunks.
 *
 * @author Horizon
 * @version 1.0
 */
public interface IChunkBatch {

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
  TripleMap<Integer, Integer, IChunk> getChunks();

  /**
   * Create chunk at specified coordinates.
   *
   * @param x X position
   * @param z Z position
   */
  IChunk createChunk(int x, int z);

  /**
   * Tries to find the chunk in this current batch.
   *
   * @param x X coordinates.
   * @param z Z coordinates.
   * @return IChunk if exists.
   */
  IChunk getChunk(int x, int z);

  /** Performs the update of all chunks in this batch. */
  void tick();
}
