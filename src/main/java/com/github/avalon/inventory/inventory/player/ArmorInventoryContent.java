package com.github.avalon.inventory.inventory.player;

import com.github.avalon.inventory.InventoryContent;
import com.github.avalon.inventory.Inventory;
import com.github.avalon.item.Item;

public class ArmorInventoryContent extends InventoryContent {

  public static final int HELMET_SLOT = 5;
  public static final int CHESTPLATE_SLOT = 6;
  public static final int LEGGINGS_SLOT = 7;
  public static final int BOOTS_SLOT = 8;

  private Item helmet;
  private Item chestplate;
  private Item leggings;
  private Item boots;

  public ArmorInventoryContent(Inventory inventory) {
    super(inventory, 5, 8);
  }

  public Item getHelmet() {
    return helmet;
  }

  public void setHelmet(Item helmet) {
    getInventory().setItem(HELMET_SLOT, helmet);
    this.helmet = helmet;
  }

  public Item getChestplate() {
    return chestplate;
  }

  public void setChestplate(Item chestplate) {
    getInventory().setItem(CHESTPLATE_SLOT, chestplate);
    this.chestplate = chestplate;
  }

  public Item getLeggings() {
    return leggings;
  }

  public void setLeggings(Item leggings) {
    getInventory().setItem(LEGGINGS_SLOT, leggings);
    this.leggings = leggings;
  }

  public Item getBoots() {
    return boots;
  }

  public void setBoots(Item boots) {
    getInventory().setItem(BOOTS_SLOT, boots);
    this.boots = boots;
  }
}
