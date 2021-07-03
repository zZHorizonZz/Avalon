package com.github.avalon.data;

import com.github.avalon.block.block.Block;
import com.github.avalon.block.block.GrassBlock;
import com.github.avalon.block.block.SimpleBlock;
import com.github.avalon.resource.data.ResourceIdentifier;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.function.Function;

public enum Material {
  AIR(0, SimpleBlock::new),
  STONE(0, SimpleBlock::new),
  GRANITE(0, SimpleBlock::new),
  POLISHED_GRANITE(0, SimpleBlock::new),
  DIORITE(0, SimpleBlock::new),
  POLISHED_DIORITE(0, SimpleBlock::new),
  ANDESITE(0, SimpleBlock::new),
  POLISHED_ANDESITE(0, SimpleBlock::new),
  GRASS_BLOCK(1, GrassBlock::new),
  DIRT(0, SimpleBlock::new),
  COARSE_DIRT(0, SimpleBlock::new);

  public static final EnumMap<Material, Integer> BLOCK_BY_IDENTIFIER =
      new EnumMap<>(Material.class);

  private final int validStates;
  private final String defaultName;
  private final Function<Transform, Block> create;

  Material(int validStates, Function<Transform, Block> create) {
    this.validStates = validStates;
    this.create = create;

    defaultName = null;
  }

  Material(int validStates, String defaultName, Function<Transform, Block> create) {
    this.validStates = validStates;
    this.defaultName = defaultName;
    this.create = create;
  }

  static {
    for (Material material : Material.values()) {
      BLOCK_BY_IDENTIFIER.put(
          material,
          material.ordinal() > 0
              ? values()[material.ordinal() - 1].getIdentifier()
                  + values()[material.ordinal() - 1].getValidStates()
                  + 1
              : 0);
    }
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

  public String getName() {
    if (defaultName == null) {
      return StringUtils.capitalize(name().toLowerCase());
    }
    return defaultName;
  }

  public Block createBlock(Transform transform) {
    return create.apply(transform);
  }

  public int getIdentifier() {
    return BLOCK_BY_IDENTIFIER.get(this);
  }

  public int getValidStates() {
    return validStates;
  }
}
