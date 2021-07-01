package com.github.avalon.packet.packet.play;

import com.github.avalon.common.data.DataType;
import com.github.avalon.network.ProtocolType;
import com.github.avalon.packet.annotation.PacketRegister;
import com.github.avalon.packet.packet.Packet;
import com.github.avalon.packet.schema.FunctionScheme;
import com.github.avalon.packet.schema.PacketStrategy;
import com.github.avalon.player.PlayerConnection;

/**
 * This packet is sent by server if connection if inactive for specific period of time. Client
 * should respond with same identifier.
 *
 * <h3>Packet Strategy</h3>
 *
 * <ul>
 *   <li>1. Random generated identifier.
 * </ul>
 *
 * @version 1.1
 */
@PacketRegister(
    operationCode = 0x00,
    protocolType = ProtocolType.PLAY,
    direction = PacketRegister.Direction.SERVER)
public class PacketTeleportConfirm extends Packet<PacketTeleportConfirm> {

  public PacketStrategy strategy =
      new PacketStrategy(
          new FunctionScheme<>(DataType.VARINT, this::getIdentifier, this::setIdentifier));

  private int identifier;

  public PacketTeleportConfirm(int identifier) {
    this.identifier = identifier;
  }

  public PacketTeleportConfirm() {}

  @Override
  public boolean isAsync() {
    return false;
  }

  @Override
  public void handle(PlayerConnection connection, PacketTeleportConfirm packetTeleportConfirm) {}

  @Override
  public PacketStrategy getStrategy() {
    return strategy;
  }

  public int getIdentifier() {
    return identifier;
  }

  public void setIdentifier(int identifier) {
    this.identifier = identifier;
  }
}
