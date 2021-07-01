package com.github.avalon.packet.packet.play;

import com.github.avalon.common.data.DataType;
import com.github.avalon.network.ProtocolType;
import com.github.avalon.packet.annotation.PacketRegister;
import com.github.avalon.packet.packet.Packet;
import com.github.avalon.packet.schema.FunctionScheme;
import com.github.avalon.packet.schema.PacketStrategy;
import com.github.avalon.player.PlayerConnection;

@PacketRegister(
    operationCode = 0x3F,
    protocolType = ProtocolType.PLAY,
    direction = PacketRegister.Direction.CLIENT)
public class PacketHeldSlotChangeClient extends Packet<PacketHeldSlotChangeClient> {

  public PacketStrategy strategy =
      new PacketStrategy(new FunctionScheme<>(DataType.BYTE, this::getSlot, this::setSlot));

  private byte slot;

  public PacketHeldSlotChangeClient(byte slot) {
    assert slot >= 0 && slot <= 8 : "Slot must be between 0 - 8 current slot is " + slot;
    this.slot = slot;
  }

  public PacketHeldSlotChangeClient() {}

  @Override
  public boolean isAsync() {
    return false;
  }

  @Override
  public void handle(PlayerConnection connection, PacketHeldSlotChangeClient packetHeldSlotChangeClient) {}

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
