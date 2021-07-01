package com.github.avalon.data;

import com.github.avalon.resource.data.ResourceIdentifier;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public enum Material {
  AIR(0),
  STONE(1),
  GRANITE(2),
  POLISHED_GRANITE(3),
  DIORITE(4),
  POLISHED_DIORITE(5),
  ANDESITE(6),
  POLISHED_ANDESITE(7),
  GRASS_BLOCK(8),
  DIRT(9),
  COARSE_DIRT(10);

  private final int identifier;
  private final String defaultName;

  Material(int identifier) {
    this.identifier = identifier;
    defaultName = null;
  }

  Material(int identifier, String defaultName) {
    this.identifier = identifier;
    this.defaultName = defaultName;
  }

  /**
   * Tries to find material with specified name, if material is not found then Material.AIR is
   * returned.
   *
   * @param name Name of material.
   * @return Material if is found.
   */
  public static Material getByName(String name) {
    try {
      return valueOf(name.toUpperCase());
    } catch (IllegalArgumentException e) {
      return Material.AIR;
    }
  }

  /**
   * Tries to find material with specified identifier, if material is not found then Material.AIR is
   * returned.
   *
   * @param identifier Identifier of material.
   * @return Material if is found.
   */
  public static Material getByIdentifier(int identifier) {
    return Arrays.stream(values())
        .filter(material -> material.getIdentifier() == identifier)
        .findFirst()
        .orElse(Material.AIR);
  }

  public ResourceIdentifier getResourceIdentifier() {
    return new ResourceIdentifier(getName());
  }

  public int getIdentifier() {
    return identifier;
  }

  public String getName() {
    if (defaultName == null) {
      return StringUtils.capitalize(name().toLowerCase());
    }
    return defaultName;
  }
}
