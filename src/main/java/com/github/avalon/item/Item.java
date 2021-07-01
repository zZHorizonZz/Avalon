package com.github.avalon.item;

import com.github.avalon.data.Material;
import com.github.avalon.nbt.serialization.NamedBinarySerializer;
import com.github.avalon.nbt.tag.Tag;
import com.github.avalon.nbt.tag.TagCompound;
import com.github.avalon.nbt.tag.TagList;
import com.github.avalon.nbt.tag.TagString;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class Item implements NamedBinarySerializer {

  public static final int DEFAULT_MAX_STACK_SIZE = 64;

  private Material material = Material.AIR;

  private String name;
  private List<String> lore = new ArrayList<>();

  private int amount = 1;
  private int maxAmount = DEFAULT_MAX_STACK_SIZE;

  public Item() {}

  public Item(Material material) {
    this.material = material;
    name = material.getName();
  }

  public Item(Material material, int amount) {
    this.material = material;
    this.amount = amount;
    name = material.getName();
  }

  public Item(Material material, int amount, String name) {
    this.material = material;
    this.amount = amount;
    this.name = name;
  }

  public Material getMaterial() {
    return material;
  }

  public void setMaterial(Material material) {
    this.material = material;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<String> getLore() {
    return lore;
  }

  public void setLore(List<String> lore) {
    this.lore = lore;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public int getMaxAmount() {
    return maxAmount;
  }

  public void setMaxAmount(int maxAmount) {
    this.maxAmount = maxAmount;
  }

  @Nullable
  public TagCompound toNamedBinaryTag() {
    return (TagCompound) serialize(this);
  }

  @Nullable
  @Override
  public Tag serialize(Object object) {
    if (!(object instanceof Item)) {
      return null;
    }

    Item item = (Item) object;
    TagCompound parent = new TagCompound("");
    TagCompound display = new TagCompound("display");

    if (!item.getName().equals(item.getMaterial().getName())) {
      display.add(
          new TagString(
              "TEST")); // TODO: Rework system of chat and chat components to new and stable system.
    }

    if (!item.getLore().isEmpty()) {
      TagList<TagString> list = new TagList<>("Lore", TagString.class);
      item.getLore().forEach(line -> list.add(new TagString(line)));
      display.add(list);
    }

    parent.add(display);
    return parent;
  }
}
