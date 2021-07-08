package com.github.avalon.packet.packet.login;

import com.github.avalon.common.data.DataType;
import com.github.avalon.network.ProtocolType;
import com.github.avalon.packet.annotation.PacketRegister;
import com.github.avalon.packet.packet.Packet;
import com.github.avalon.packet.schema.FunctionScheme;
import com.github.avalon.packet.schema.PacketStrategy;
import com.github.avalon.player.PlayerConnection;

/**
 * Packet is sent by client as start of verification process in {@code LOGIN} type protocol.
 * Contains player's username.
 *
 * <h3>Packet Strategy</h3>
 *
 * <ul>
 *   <li>1. Username of the client.
 * </ul>
 *
 * @version 1.1
 */
@PacketRegister(
    operationCode = 0x00,
    protocolType = ProtocolType.LOGIN,
    direction = PacketRegister.Direction.SERVER)
public class PacketLoginIn extends Packet<PacketLoginIn> {

  public PacketStrategy strategy =
      new PacketStrategy(
          new FunctionScheme<>(DataType.STRING, this::getUsername, this::setUsername));

  private String username;

  public PacketLoginIn(String username) {
    this.username = username;
  }

  public PacketLoginIn() {}

  @Override
  public boolean isAsync() {
    return true;
  }

  @Override
  public void handle(PlayerConnection connection, PacketLoginIn packet) {
    connection.getPlayer().getServer().getPacketModule().handle(packet, connection);
  }

  @Override
  public PacketStrategy getStrategy() {
    return strategy;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
