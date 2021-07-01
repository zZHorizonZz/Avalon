package com.github.avalon.packet.packet.play;

import com.github.avalon.common.data.DataType;
import com.github.avalon.network.ProtocolType;
import com.github.avalon.packet.annotation.PacketRegister;
import com.github.avalon.packet.packet.Packet;
import com.github.avalon.packet.schema.FunctionScheme;
import com.github.avalon.packet.schema.PacketStrategy;
import com.github.avalon.player.PlayerConnection;

@PacketRegister(
    operationCode = 0x25,
    protocolType = ProtocolType.PLAY,
    direction = PacketRegister.Direction.SERVER)
public class PacketHeldSlotChangeServer extends Packet<PacketHeldSlotChangeServer> {

  public PacketStrategy strategy =
      new PacketStrategy(new FunctionScheme<>(DataType.BYTE, this::getSlot, this::setSlot));

  private byte slot;

  public PacketHeldSlotChangeServer(byte slot) {
    assert slot >= 0 && slot <= 8 : "Slot must be between 0 - 8 current slot is " + slot;
    this.slot = slot;
  }

  public PacketHeldSlotChangeServer() {}

  @Override
  public boolean isAsync() {
    return false;
  }

  @Override
  public void handle(
      PlayerConnection connection, PacketHeldSlotChangeServer packetHeldSlotChangeClient) {
    connection
        .getPlayer()
        .getInventory()
        .setCurrentSlot(packetHeldSlotChangeClient.getSlot(), false);
  }

  @Override
  public PacketStrategy getStrategy() {
    return strategy;
  }

  public byte getSlot() {
    return slot;
  }

  public void setSlot(byte slot) {
    assert slot >= 0 && slot <= 8 : "Slot must be between 0 - 8 current slot is " + slot;
    this.slot = slot;
  }
}
