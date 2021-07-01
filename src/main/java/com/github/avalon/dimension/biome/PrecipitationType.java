package com.github.avalon.dimension.biome;

public enum PrecipitationType {
  NONE("none"),
  RAIN("rain"),
  SNOW("snow");

  private final String name;

  PrecipitationType(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
