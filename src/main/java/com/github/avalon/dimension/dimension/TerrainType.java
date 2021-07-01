package com.github.avalon.dimension.dimension;

import javax.annotation.Nullable;

/**
 * Type of terrain. With description from
 * <url>https://minecraft.fandom.com/wiki/Server.properties</url>.
 *
 * @author Horizon
 * @version 1.0
 */
public enum TerrainType {
  DEFAULT("Standard world with hills, valleys, water, etc. (Currently not working)"),
  FLAT("A flat world with no features."),
  LARGE_BIOMES("Same as default but all biomes are larger."),
  AMPLIFIED("Same as default but world-generation height limit is increased."),
  BUFFET("Only for 1.15 or before."),
  CUSTOMIZED(
      "Only for 1.15 or before. After 1.13, this value is no different than default, but in 1.12 and before, it could be used to create a completely custom world.");

  private String generatorDescription;

  TerrainType() {}

  TerrainType(String generatorDescription) {
    this.generatorDescription = generatorDescription;
  }

  @Nullable
  public String getGeneratorDescription() {
    return generatorDescription;
  }
}
