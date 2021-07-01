package com.github.avalon.dimension.biome;

/**
 * Data about the biome are send to the client. Currently there is not that much usage for this
 * because there is no way to generate the world but still it has use in default world because world
 * should have at least one biome.
 *
 * @author Horizon
 * @version 1.0
 */
public class BiomeData {

  private PrecipitationType precipitationType = PrecipitationType.NONE;
  private float depth = 1.5f;
  private float temperature = 1.0f;
  private float scale;
  private float downfall;
  private BiomeCategory biomeCategory = BiomeCategory.NONE;
  private BiomeEffect biomeEffect = new BiomeEffect();

  public PrecipitationType getPrecipitationType() {
    return precipitationType;
  }

  public void setPrecipitationType(PrecipitationType precipitationType) {
    this.precipitationType = precipitationType;
  }

  public float getDepth() {
    return depth;
  }

  public void setDepth(float depth) {
    this.depth = depth;
  }

  public float getTemperature() {
    return temperature;
  }

  public void setTemperature(float temperature) {
    this.temperature = temperature;
  }

  public float getScale() {
    return scale;
  }

  public void setScale(float scale) {
    this.scale = scale;
  }

  public float getDownfall() {
    return downfall;
  }

  public void setDownfall(float downfall) {
    this.downfall = downfall;
  }

  public BiomeCategory getBiomeCategory() {
    return biomeCategory;
  }

  public void setBiomeCategory(BiomeCategory biomeCategory) {
    this.biomeCategory = biomeCategory;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;

    BiomeData biomeData = (BiomeData) object;

    if (Float.compare(biomeData.getDepth(), getDepth()) != 0) return false;
    if (Float.compare(biomeData.getTemperature(), getTemperature()) != 0) return false;
    if (Float.compare(biomeData.getScale(), getScale()) != 0) return false;
    if (Float.compare(biomeData.getDownfall(), getDownfall()) != 0) return false;
    if (getPrecipitationType() != biomeData.getPrecipitationType()) return false;
    return getBiomeCategory() == biomeData.getBiomeCategory();
  }

  @Override
  public int hashCode() {
    int result = getPrecipitationType() != null ? getPrecipitationType().hashCode() : 0;
    result = 31 * result + (getDepth() != +0.0f ? Float.floatToIntBits(getDepth()) : 0);
    result = 31 * result + (getTemperature() != +0.0f ? Float.floatToIntBits(getTemperature()) : 0);
    result = 31 * result + (getScale() != +0.0f ? Float.floatToIntBits(getScale()) : 0);
    result = 31 * result + (getDownfall() != +0.0f ? Float.floatToIntBits(getDownfall()) : 0);
    result = 31 * result + (getBiomeCategory() != null ? getBiomeCategory().hashCode() : 0);
    return result;
  }

  public BiomeEffect getBiomeEffect() {
    return biomeEffect;
  }

  public void setBiomeEffect(BiomeEffect biomeEffect) {
    this.biomeEffect = biomeEffect;
  }
}
