package com.github.avalon.dimension.biome;

import com.github.avalon.nbt.serialization.NamedBinarySerializer;

public interface Biome extends NamedBinarySerializer {

  String getName();

  int getIdentifier();

  BiomeData getBiomeData();

  void setBiomeData(BiomeData biomeData);
}
