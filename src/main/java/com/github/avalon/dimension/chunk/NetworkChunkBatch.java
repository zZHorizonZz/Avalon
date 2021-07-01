package com.github.avalon.dimension.chunk;

import com.github.avalon.common.system.TripleMap;
import com.github.avalon.dimension.dimension.Dimension;

public class NetworkChunkBatch implements ChunkBatch {

  public static int DEFAULT_CHUNK_BATCH_SIZE = 25;

  private final Dimension dimension;
  private final int chunk_batch_size;
  private final int x;
  private final int z;

  private final TripleMap<Integer, Integer, Chunk> chunks;

  public NetworkChunkBatch(Dimension dimension, int x, int z, int chunk_batch_size) {
    this.dimension = dimension;
    this.chunk_batch_size = chunk_batch_size;
    this.x = x;
    this.z = z;

      chunks = new TripleMap<>();
  }

  public NetworkChunkBatch(Dimension dimension, int x, int z) {
    this(dimension, x, z, NetworkChunkBatch.DEFAULT_CHUNK_BATCH_SIZE);
  }

  @Override
  public Dimension getDimension() {
    return dimension;
  }

  @Override
  public int getSizeOfBatch() {
    return chunk_batch_size;
  }

  @Override
  public int getX() {
    return x;
  }

  @Override
  public int getZ() {
    return z;
  }

  @Override
  public TripleMap<Integer, Integer, Chunk> getChunks() {
    return chunks;
  }

  @Override
  public Chunk createChunk(int x, int z) {
    Chunk chunk = new NetworkChunk(this, x, z);
    chunks.put(x, z, chunk);
    return chunk;
  }

  @Override
  public Chunk getChunk(int x, int z) {
    return chunks.get(x, z);
  }

  @Override
  public void tick() {}
}
