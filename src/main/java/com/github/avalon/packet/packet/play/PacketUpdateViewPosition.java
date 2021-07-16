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
    operationCode = 0x40,
    protocolType = ProtocolType.PLAY,
    direction = PacketRegister.Direction.CLIENT)
public class PacketUpdateViewPosition extends Packet<PacketUpdateViewPosition> {

  public PacketStrategy strategy =
      new PacketStrategy(
          new FunctionScheme<>(DataType.VARINT, this::getX, this::setX),
          new FunctionScheme<>(DataType.VARINT, this::getZ, this::setZ));

  private int x;
  private int z;

  public PacketUpdateViewPosition(int x, int z) {
    this.x = x;
    this.z = z;
  }

  public PacketUpdateViewPosition() {}

  @Override
  public PacketStrategy getStrategy() {
    return strategy;
  }

  @Override
  public boolean isAsync() {
    return false;
  }

  @Override
  public void handle(PlayerConnection connection, PacketUpdateViewPosition packetSetSlot) {}

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getZ() {
    return z;
  }

  public void setZ(int z) {
    this.z = z;
  }
}
