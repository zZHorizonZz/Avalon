package com.github.avalon.packet.packet.status;

import com.github.avalon.common.data.DataType;
import com.github.avalon.network.ProtocolType;
import com.github.avalon.packet.annotation.PacketRegister;
import com.github.avalon.packet.packet.Packet;
import com.github.avalon.packet.schema.FunctionScheme;
import com.github.avalon.packet.schema.PacketStrategy;
import com.github.avalon.player.PlayerConnection;

/**
 * Packet client ping is used for reading packet that is sent by client to verify ping. There is
 * also packet for checking ping of player {@link PacketPong}
 *
 * <h3>Packet Strategy</h3>
 *
 * <ul>
 *   <li>1. Time that is this packet being sent.
 * </ul>
 *
 * @version 1.1
 */
@PacketRegister(
    operationCode = 0x01,
    protocolType = ProtocolType.STATUS,
    direction = PacketRegister.Direction.SERVER)
public class PacketPing extends Packet<PacketPing> {

  public PacketStrategy strategy =
      new PacketStrategy(new FunctionScheme<>(DataType.LONG, this::getTime, this::setTime));

  private long time;

  public PacketPing(long time) {
    this.time = time;
  }

  public PacketPing() {}

  @Override
  public boolean isAsync() {
    return true;
  }

  @Override
  public void handle(PlayerConnection session, PacketPing packet) {
    session.getPlayer().sendPacket(new PacketPong(System.currentTimeMillis()));
  }

  @Override
  public PacketStrategy getStrategy() {
    return strategy;
  }

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }
}
