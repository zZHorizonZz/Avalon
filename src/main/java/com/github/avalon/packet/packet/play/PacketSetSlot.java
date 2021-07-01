package com.github.avalon.packet.packet.play;

import com.github.avalon.common.data.DataType;
import com.github.avalon.item.Item;
import com.github.avalon.network.ProtocolType;
import com.github.avalon.packet.annotation.PacketRegister;
import com.github.avalon.packet.packet.Packet;
import com.github.avalon.packet.schema.FunctionScheme;
import com.github.avalon.packet.schema.PacketStrategy;
import com.github.avalon.player.PlayerConnection;

@PacketRegister(
    operationCode = 0x15,
    protocolType = ProtocolType.PLAY,
    direction = PacketRegister.Direction.CLIENT)
public class PacketSetSlot extends Packet<PacketSetSlot> {

  public PacketStrategy strategy =
      new PacketStrategy(
          new FunctionScheme<>(DataType.BYTE, this::getWindowIdentifier, this::setWindowIdentifier),
          new FunctionScheme<>(DataType.SHORT, this::getSlot, this::setSlot),
          new FunctionScheme<>(DataType.ITEM, this::getItem, this::setItem));

  private byte windowIdentifier;
  private short slot;
  private Item item;

  public PacketSetSlot(byte windowIdentifier, short slot, Item item) {
    this.windowIdentifier = windowIdentifier;
    this.slot = slot;
    this.item = item;
  }

  public PacketSetSlot() {}

  @Override
  public PacketStrategy getStrategy() {
    return strategy;
  }

  @Override
  public boolean isAsync() {
    return false;
  }

  @Override
  public void handle(PlayerConnection connection, PacketSetSlot packetSetSlot) {}

  public byte getWindowIdentifier() {
    return windowIdentifier;
  }

  public void setWindowIdentifier(byte windowIdentifier) {
    this.windowIdentifier = windowIdentifier;
  }

  public short getSlot() {
    return slot;
  }

  public void setSlot(short slot) {
    this.slot = slot;
  }

  public Item getItem() {
    return item;
  }

  public void setItem(Item item) {
    this.item = item;
  }
}
