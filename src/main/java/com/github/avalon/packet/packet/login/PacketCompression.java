package com.github.avalon.packet.packet.login;

import com.github.avalon.common.data.DataType;
import com.github.avalon.network.ProtocolType;
import com.github.avalon.packet.annotation.PacketRegister;
import com.github.avalon.packet.packet.Packet;
import com.github.avalon.packet.schema.FunctionScheme;
import com.github.avalon.packet.schema.PacketStrategy;
import com.github.avalon.player.PlayerConnection;

/**
 * Packet compression sent maximum size of packet before it's compression. And also enables
 * compression itself.
 *
 * <h3>Packet Strategy</h3>
 *
 * <ul>
 *   <li>1. Maximum size of packet before it's compression.
 * </ul>
 *
 * @version 1.1
 */
@PacketRegister(
    operationCode = 0x03,
    protocolType = ProtocolType.LOGIN,
    direction = PacketRegister.Direction.CLIENT)
public class PacketCompression extends Packet<PacketCompression> {

  public PacketStrategy strategy =
      new PacketStrategy(
          new FunctionScheme<>(DataType.VARINT, this::getThreshold, this::setThreshold));

  private int threshold;

  public PacketCompression(int threshold) {
    this.threshold = threshold;
  }

  public PacketCompression() {}

  @Override
  public boolean isAsync() {
    return false;
  }

  @Override
  public void handle(PlayerConnection connection, PacketCompression packetCompression) {}

  @Override
  public PacketStrategy getStrategy() {
    return strategy;
  }

  public int getThreshold() {
    return threshold;
  }

  public void setThreshold(int threshold) {
    this.threshold = threshold;
  }
}
