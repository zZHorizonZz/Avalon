package com.github.avalon.dimension.chunk;

import java.util.HashMap;
import java.util.Map;

public class ChunkSection implements IChunkSection {

  private final IChunk chunk;

  private final int x;
  private final int y;
  private final int z;

  private final Map<Integer, Integer> blocks;

  public ChunkSection(IChunk chunk, int x, int y, int z) {
    this.chunk = chunk;

    this.x = x;
    this.y = y;
    this.z = z;

    blocks = new HashMap<>();
  }

  @Override
  public IChunk getChunk() {
    return chunk;
  }

  @Override
  public int getX() {
    return x;
  }

  @Override
  public int getY() {
    return y;
  }

  @Override
  public int getZ() {
    return z;
  }

  @Override
  public boolean isEmpty() {
    return blocks.isEmpty();
  }

  @Override
  public Map<Integer, Integer> getBlocks() {
    return blocks;
  }
}
