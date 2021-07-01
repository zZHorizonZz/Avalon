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
    operationCode = 0x10,
    protocolType = ProtocolType.PLAY,
    direction = PacketRegister.Direction.SERVER)
public class PacketKeepAliveServer extends Packet<PacketKeepAliveServer> {

  public PacketStrategy strategy =
      new PacketStrategy(
          new FunctionScheme<>(DataType.LONG, this::getIdentifier, this::setIdentifier));

  private long identifier;

  public PacketKeepAliveServer(long identifier) {
    this.identifier = identifier;
  }

  public PacketKeepAliveServer() {}

  @Override
  public boolean isAsync() {
    return false;
  }

  @Override
  public void handle(PlayerConnection connection, PacketKeepAliveServer packetKeepAlive) {}

  @Override
  public PacketStrategy getStrategy() {
    return strategy;
  }

  public long getIdentifier() {
    return identifier;
  }

  public void setIdentifier(long identifier) {
    this.identifier = identifier;
  }
}
