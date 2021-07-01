package com.github.avalon.dimension.biome;

import java.awt.*;

/**
 * Biome effect defines the colors of the {@link Biome}. Biome colors are optional so you can just
 * set them to null or let the as they are now.
 *
 * <p>Default values are from:
 * <url>https://gist.githubusercontent.com/aramperes/44e2beefac9fe966177f2f28dd0136ab/raw/fedb31c32e27265fb916a68ad476470fc65631da/1-dimension_codec.snbt</url>
 *
 * @author Horizon
 * @version 1.0
 */
public class BiomeEffect {

  private Color skyColor = new Color(7907327);
  private Color waterFogColor = new Color(329011);
  private Color fogColor = new Color(12638463);
  private Color waterColor = new Color(4159204);
  private Color foliageColor = new Color(6975545);
  private Color grassColor = new Color(9470285);
  private String grassColorModifier;

  // TODO create biome music.

  public Color getSkyColor() {
    return skyColor;
  }

  public void setSkyColor(Color skyColor) {
    this.skyColor = skyColor;
  }

  public Color getWaterFogColor() {
    return waterFogColor;
  }

  public void setWaterFogColor(Color waterFogColor) {
    this.waterFogColor = waterFogColor;
  }

  public Color getFogColor() {
    return fogColor;
  }

  public void setFogColor(Color fogColor) {
    this.fogColor = fogColor;
  }

  public Color getWaterColor() {
    return waterColor;
  }

  public void setWaterColor(Color waterColor) {
    this.waterColor = waterColor;
  }

  public Color getFoliageColor() {
    return foliageColor;
  }

  public void setFoliageColor(Color foliageColor) {
    this.foliageColor = foliageColor;
  }

  public Color getGrassColor() {
    return grassColor;
  }

  public void setGrassColor(Color grassColor) {
    this.grassColor = grassColor;
  }

  public String getGrassColorModifier() {
    return grassColorModifier;
  }

  public void setGrassColorModifier(String grassColorModifier) {
    this.grassColorModifier = grassColorModifier;
  }
}
