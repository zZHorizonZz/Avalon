package com.github.avalon.inventory;

import com.github.avalon.item.Item;

import java.util.Map;

public interface Inventory {

  int getInventoryIdentifier();

  Map<Integer, Item> getInventory();

  Item getSlot(int slot);

  int getMaxSize();

  void setItem(int slot, Item item);

  void updateInventory();

  void clear();
}
