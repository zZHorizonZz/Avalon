package com.github.avalon.dimension.biome;

public enum BiomeCategory {
  OCEAN("ocean"),
  PLAINS("plains", new NetworkBiome("plains", 0)),
  DESERT("desert"),
  FOREST("forest"),
  EXTREME_HILLS("extreme_hills"),
  TAIGA("taiga"),
  SWAMP("swamp"),
  RIVER("river"),
  NETHER("nether"),
  THE_END("the_end"),
  ICY("icy"),
  MUSHROOM("mushroom"),
  BEACH("beach"),
  JUNGLE("jungle"),
  MESA("mesa"),
  SAVANNA("savanna"),
  NONE("none");

  private final String name;
  private Biome[] biomes;

  BiomeCategory(String name) {
    this.name = name;
  }

  BiomeCategory(String name, Biome... biomes) {
    this.name = name;
    this.biomes = biomes;
  }

  public Biome[] getBiomes() {
    return biomes;
  }

  public String getName() {
    return name;
  }
}
