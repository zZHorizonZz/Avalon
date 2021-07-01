package com.github.avalon.dimension.dimension;

public enum DimensionType {
  NORMAL(0, "minecraft:infiniburn_overworld"),
  NETHER(-1, "minecraft:infiniburn_nether"),
  THE_END(1, "minecraft:infiniburn_end");

  private final int id;
  private final String infiniburn;

  DimensionType(int id, String infiniburn) {
    this.id = id;
    this.infiniburn = infiniburn;
  }

  public int getId() {
    return id;
  }

  public String getInfiniburn() {
    return infiniburn;
  }
}
