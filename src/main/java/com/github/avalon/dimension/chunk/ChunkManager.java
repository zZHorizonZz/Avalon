package com.github.avalon.dimension.chunk;

import com.github.avalon.common.system.TripleMap;
import com.github.avalon.dimension.dimension.Dimension;
import com.github.avalon.manager.DefaultManager;

import javax.annotation.Nullable;

/**
 * This chunk manager manages, updates and deletes all chunks that are in parent {@link Dimension}.
 *
 * @author Horizon
 * @version 1.0
 */
public class ChunkManager extends DefaultManager<Dimension> {

  private final TripleMap<Integer, Integer, IChunkBatch> batches;

  public ChunkManager(Dimension host) {
    super("IChunk manager", host);

    batches = new TripleMap<>();
  }

  @Nullable
  public IChunk getChunk(int x, int z) {
    // TODO check for maximum size of the world.
    return batches
        .get(
            (int) Math.round((double) (x / ChunkBatch.DEFAULT_CHUNK_BATCH_SIZE)),
            (int) Math.round((double) (z / ChunkBatch.DEFAULT_CHUNK_BATCH_SIZE)))
        .getChunk(x, z);
  }

  public TripleMap<Integer, Integer, IChunkBatch> getBatches() {
    return batches;
  }
}
