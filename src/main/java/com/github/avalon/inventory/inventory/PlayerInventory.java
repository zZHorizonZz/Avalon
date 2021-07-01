package com.github.avalon.inventory.inventory;

import com.github.avalon.inventory.Inventory;
import com.github.avalon.inventory.inventory.player.ArmorInventoryContent;
import com.github.avalon.inventory.inventory.player.HotbarInventoryContent;
import com.github.avalon.inventory.inventory.player.MainInventoryContent;
import com.github.avalon.item.Item;
import com.github.avalon.packet.packet.play.PacketHeldSlotChangeClient;
import com.github.avalon.packet.packet.play.PacketSetSlot;
import com.github.avalon.player.IPlayer;

import java.util.HashMap;
import java.util.Map;

public class PlayerInventory implements Inventory {

  public static final int PLAYER_DEFAULT_WINDOW_ID = 0;
  public static final int PLAYER_INVENTORY_SIZE = 44;

  private final IPlayer player;

  private final Map<Integer, Item> inventory;

  private final MainInventoryContent inventoryContest;
  private final HotbarInventoryContent hotbarContest;
  private final ArmorInventoryContent armorContest;

  private Item offHand;

  private int currentSlot;

  public PlayerInventory(IPlayer player) {
    this.player = player;

    inventory = new HashMap<>();

    inventoryContest = new MainInventoryContent(this);
    hotbarContest = new HotbarInventoryContent(this);
    armorContest = new ArmorInventoryContent(this);
  }

  @Override
  public int getInventoryIdentifier() {
    return PLAYER_DEFAULT_WINDOW_ID;
  }

  @Override
  public Map<Integer, Item> getInventory() {
    return inventory;
  }

  @Override
  public Item getSlot(int slot) {
    return inventory.get(slot);
  }

  @Override
  public int getMaxSize() {
    return PLAYER_INVENTORY_SIZE;
  }

  @Override
  public void setItem(int slot, Item item) {
    if (inventory.containsKey(slot)) {
      inventory.replace(slot, item);
    } else {
      inventory.put(slot, item);
    }

    PacketSetSlot packet = new PacketSetSlot((byte) 0, (short) slot, item);
    player.sendPacket(packet);
  }

  @Override
  public void updateInventory() {}

  @Override
  public void clear() {}

  public IPlayer getPlayer() {
    return player;
  }

  public ArmorInventoryContent getArmorContest() {
    return armorContest;
  }

  public MainInventoryContent getInventoryContest() {
    return inventoryContest;
  }

  public HotbarInventoryContent getHotbarContest() {
    return hotbarContest;
  }

  public Item getOffHand() {
    return offHand;
  }

  public void setCurrentSlot(int slot) {
    setCurrentSlot(slot, true);
  }

  public void setCurrentSlot(int slot, boolean update) {
    currentSlot = slot;

    if (update) {
      updateCurrentSlot();
    }
  }

  private void updateCurrentSlot() {
    PacketHeldSlotChangeClient packet = new PacketHeldSlotChangeClient((byte) currentSlot);
    player.sendPacket(packet);
  }

  public int getCurrentSlot() {
    return currentSlot;
  }

  /**
   * Returns the current item that is in player's hand. If slot is empty then null is returned.
   *
   * @since 1.0
   * @return Current held item or air.
   */
  public Item getCurrentHeldItem() {
    return hotbarContest.getItem(currentSlot);
  }

  public void setCurrentHeldItem(Item item) {
    hotbarContest.setItem(currentSlot, item);
  }
}
