package com.github.avalon.dimension.chunk;

import com.github.avalon.data.Material;

import java.util.HashMap;
import java.util.Map;

public class NetworkChunkSection implements ChunkSection {

  private final Chunk chunk;

  private final int x;
  private final int y;
  private final int z;

  private final Map<Integer, Material> blocks;

  public NetworkChunkSection(Chunk chunk, int x, int y, int z) {
    this.chunk = chunk;

    this.x = x;
    this.y = y;
    this.z = z;

    blocks = new HashMap<>();
  }

  @Override
  public Chunk getChunk() {
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
    return false;
  }

  @Override
  public void setEmpty(boolean empty) {}

  @Override
  public Map<Integer, Material> getMaterials() {
    return blocks;
  }
}
