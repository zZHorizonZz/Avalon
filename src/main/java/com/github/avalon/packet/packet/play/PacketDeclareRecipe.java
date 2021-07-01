package com.github.avalon.packet.packet.play;

import com.github.avalon.common.data.DataType;
import com.github.avalon.network.ProtocolType;
import com.github.avalon.packet.annotation.PacketRegister;
import com.github.avalon.packet.packet.Packet;
import com.github.avalon.packet.schema.FunctionScheme;
import com.github.avalon.packet.schema.PacketStrategy;
import com.github.avalon.player.PlayerConnection;

@PacketRegister(
    operationCode = 0x5A,
    protocolType = ProtocolType.PLAY,
    direction = PacketRegister.Direction.CLIENT)
public class PacketDeclareRecipe extends Packet<PacketDeclareRecipe> {

  public PacketStrategy strategy =
      new PacketStrategy(new FunctionScheme<>(DataType.VARINT, this::getLength, this::setLength));

  private int length;

  public PacketDeclareRecipe() {
    length = 0;
  }

  @Override
  public boolean isAsync() {
    return false;
  }

  @Override
  public void handle(PlayerConnection connection, PacketDeclareRecipe packetDeclareRecipe) {}

  @Override
  public PacketStrategy getStrategy() {
    return strategy;
  }

  public int getLength() {
    return length;
  }

  public void setLength(int length) {
    this.length = length;
  }
}
