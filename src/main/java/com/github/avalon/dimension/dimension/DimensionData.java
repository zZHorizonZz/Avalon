package com.github.avalon.dimension.dimension;

import com.github.avalon.nbt.serialization.NamedBinarySerializer;
import com.github.avalon.nbt.tag.*;

public class DimensionData implements NamedBinarySerializer {

  private final Dimension dimension;

  private boolean piglinSafe;
  private boolean natural = true;
  private float ambientLight;
  private boolean respawnAnchorWorks;
  private boolean skylight = true;
  private boolean bed = true;
  private String effects = "minecraft:overworld";
  private boolean raids = true;
  private int logicalHeight = 256;
  private float coordinateScale = 1.0f;
  private boolean ultrawarm;
  private boolean ceiling;

  public DimensionData(Dimension dimension) {
    this.dimension = dimension;
  }

  public boolean isPiglinSafe() {
    return piglinSafe;
  }

  public void setPiglinSafe(boolean piglinSafe) {
    this.piglinSafe = piglinSafe;
  }

  public boolean isNatural() {
    return natural;
  }

  public void setNatural(boolean natural) {
    this.natural = natural;
  }

  public float getAmbientLight() {
    return ambientLight;
  }

  public void setAmbientLight(float ambientLight) {
    this.ambientLight = ambientLight;
  }

  public boolean isRespawnAnchorWorks() {
    return respawnAnchorWorks;
  }

  public void setRespawnAnchorWorks(boolean respawnAnchorWorks) {
    this.respawnAnchorWorks = respawnAnchorWorks;
  }

  public boolean isSkylight() {
    return skylight;
  }

  public void setSkylight(boolean skylight) {
    this.skylight = skylight;
  }

  public boolean isBed() {
    return bed;
  }

  public void setBed(boolean bed) {
    this.bed = bed;
  }

  public String getEffects() {
    return effects;
  }

  public void setEffects(String effects) {
    this.effects = effects;
  }

  public boolean isRaids() {
    return raids;
  }

  public void setRaids(boolean raids) {
    this.raids = raids;
  }

  public int getLogicalHeight() {
    return logicalHeight;
  }

  public void setLogicalHeight(int logicalHeight) {
    this.logicalHeight = logicalHeight;
  }

  public float getCoordinateScale() {
    return coordinateScale;
  }

  public void setCoordinateScale(float coordinateScale) {
    this.coordinateScale = coordinateScale;
  }

  public boolean isUltrawarm() {
    return ultrawarm;
  }

  public void setUltrawarm(boolean ultrawarm) {
    this.ultrawarm = ultrawarm;
  }

  public boolean isCeiling() {
    return ceiling;
  }

  public void setCeiling(boolean ceiling) {
    this.ceiling = ceiling;
  }

  @Override
  public Tag serialize(Object object) {
    DimensionData dimensionData = (DimensionData) object;

    TagCompound mainParent = new TagCompound("");

    // Todo create serialize for  data
    mainParent.add(new TagByte("piglin_safe", dimensionData.isPiglinSafe()));
    mainParent.add(new TagByte("natural", dimensionData.isNatural()));
    mainParent.add(new TagFloat("ambient_light", dimensionData.getAmbientLight()));
    mainParent.add(new TagString("infiniburn", dimensionData.getDimension().getType().getInfiniburn()));
    mainParent.add(
            new TagByte("respawn_anchor_works", dimensionData.isRespawnAnchorWorks()));
    mainParent.add(new TagByte("has_skylight", dimensionData.isSkylight()));
    mainParent.add(new TagByte("bed_works", dimensionData.isBed()));
    mainParent.add(new TagString("effects", dimensionData.getEffects()));
    mainParent.add(new TagByte("has_raids", dimensionData.isRaids()));
    mainParent.add(new TagInteger("logical_height", dimensionData.getLogicalHeight()));
    mainParent.add(
            new TagFloat("coordinate_scale", dimensionData.getCoordinateScale()));
    mainParent.add(new TagByte("ultrawarm", dimensionData.isUltrawarm()));
    mainParent.add(new TagByte("has_ceiling", dimensionData.isCeiling()));

    return mainParent;
  }

  public Dimension getDimension() {
    return dimension;
  }
}
