package com.github.avalon.dimension.biome;

import com.github.avalon.data.Container;

import java.util.Collection;

public class BiomeContainer extends Container<Integer, Biome> {

  public void registerBiome(int identifier, Biome biome) {
    add(identifier, biome);
  }

  public void unregisterBiome(int identifier) {
    remove(identifier);
  }

  public Biome getBiome(int identifier) {
    return get(identifier);
  }

  public boolean isBiome(int identifier) {
    return containsKey(identifier);
  }

  public boolean isBiome(Biome biome) {
    return containsValue(biome);
  }

  public Collection<Biome> getBiomes() {
    return getRegistry().values();
  }
}
