package com.github.avalon.dimension.biome;

import com.github.avalon.nbt.tag.*;

/**
 * An implementation of the {@link Biome}.
 *
 * @author Horizon
 * @version 1.0
 */
public class NetworkBiome implements Biome, Cloneable {

  private final String name;
  private final int identifier;

  private BiomeData biomeData;

  public NetworkBiome(String name, int identifier) {
    this.name = name;
    this.identifier = identifier;

    biomeData = new BiomeData();
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public int getIdentifier() {
    return identifier;
  }

  @Override
  public BiomeData getBiomeData() {
    return biomeData;
  }

  @Override
  public void setBiomeData(BiomeData biomeData) {
    this.biomeData = biomeData;
  }

  @Override
  protected NetworkBiome clone() {
    NetworkBiome biome = new NetworkBiome(name, identifier);
    biome.setBiomeData(biomeData);
    return biome;
  }

  @Override
  public Tag serialize(Object object) {
    Biome biome = (Biome) object;
    BiomeData biomeData = biome.getBiomeData();
    BiomeEffect biomeEffect = biomeData.getBiomeEffect();

    TagCompound mainParent = new TagCompound("");

    mainParent.add(new TagString("name", "minecraft:" + biome.getName()));
    mainParent.add(new TagInteger("id", biome.getIdentifier()));

    TagCompound elementParent = new TagCompound("element");
    mainParent.add(elementParent);

    elementParent.add(new TagString("precipitation", biomeData.getPrecipitationType().getName()));
    elementParent.add(new TagFloat("depth", biomeData.getDepth()));
    elementParent.add(new TagFloat("temperature", biomeData.getTemperature()));
    elementParent.add(new TagFloat("scale", biomeData.getScale()));
    elementParent.add(new TagFloat("downfall", biomeData.getDownfall()));
    elementParent.add(new TagString("category", biomeData.getBiomeCategory().getName()));
    // TODO: Temperature modifier is optinal

    TagCompound effectParent = new TagCompound("effects");
    elementParent.add(effectParent);

    effectParent.add(new TagInteger("sky_color", biomeEffect.getSkyColor().getRGB()));
    effectParent.add(new TagInteger("water_fog_color", biomeEffect.getWaterColor().getRGB()));
    effectParent.add(new TagInteger("fog_color", biomeEffect.getFogColor().getRGB()));
    effectParent.add(new TagInteger("water_color", biomeEffect.getWaterColor().getRGB()));
    // TODO: Foliage color, grass color, grass color modifier, music, ambient sound, additions
    // sound, mood sound are optinal so this will be implemented in later versions when will be
    // implemented codec management.

    return mainParent;
  }
}
