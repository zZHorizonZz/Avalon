package com.github.avalon.inventory;

import com.github.avalon.inventory.inventory.player.ArmorInventoryContent;
import com.github.avalon.item.Item;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * InventoryContent represents a piece of inventory within set range. For example {@link
 * ArmorInventoryContent} starts at index 5 and ends at index 8. More about inventories we can find
 * at <url>www.wiki.vg/Inventory#Windows</url>
 *
 * @version 1.0
 */
public abstract class InventoryContent {

  private final Inventory inventory;

  private final int startIndex;
  private final int endIndex;

  protected InventoryContent(Inventory inventory, int startIndex, int endIndex) {
    this.inventory = inventory;
    this.startIndex = startIndex;
    this.endIndex = endIndex;
  }

  public List<Item> getContents() {
    final List<Item> collect =
        inventory.getInventory().entrySet().stream()
            .filter(slot -> slot.getKey() >= startIndex && slot.getKey() <= endIndex)
            .map(Map.Entry::getValue)
            .collect(Collectors.toUnmodifiableList());
    return collect;
  }

  public Item getItem(int slot) {
    slot = startIndex + slot;
    assert slot <= endIndex
        : "Slot must be between 0 and " + (endIndex - startIndex) + ". Input was " + slot + '.';
    return inventory.getSlot(slot);
  }

  public void setItem(int slot, Item item) {
    slot = startIndex + slot;
    assert slot <= endIndex
        : "Slot must be between 0 and " + (endIndex - startIndex) + ". Input was " + slot + '.';
    inventory.setItem(slot, item);
  }

  public Inventory getInventory() {
    return inventory;
  }

  public int getStartIndex() {
    return startIndex;
  }

  public int getEndIndex() {
    return endIndex;
  }
}
