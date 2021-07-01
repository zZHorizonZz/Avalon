package com.github.avalon.packet.packet.login;

import com.github.avalon.common.data.DataType;
import com.github.avalon.network.ProtocolType;
import com.github.avalon.packet.annotation.PacketRegister;
import com.github.avalon.packet.packet.Packet;
import com.github.avalon.packet.schema.FunctionScheme;
import com.github.avalon.packet.schema.PacketStrategy;
import com.github.avalon.player.PlayerConnection;

import java.util.UUID;

/**
 * Packet is sent by server to client if login sequence is valid and successful.
 *
 * <h3>Packet Strategy</h3>
 *
 * <ul>
 *   <li>1. Unique identifier depends on if player is connection with original mc or with cracked version.
 *   <li>2. Username of player.
 * </ul>
 *
 * @version 1.1
 */
@PacketRegister(
    operationCode = 0x02,
    protocolType = ProtocolType.LOGIN,
    direction = PacketRegister.Direction.CLIENT)
public class PacketLoginSuccess extends Packet<PacketLoginSuccess> {

  public PacketStrategy strategy =
      new PacketStrategy(
          new FunctionScheme<>(DataType.UUID, this::getUuid, this::setUuid),
          new FunctionScheme<>(DataType.STRING, this::getUsername, this::setUsername));

  private UUID uuid;
  private String username;

  public PacketLoginSuccess(UUID uuid, String username) {
    this.uuid = uuid;
    this.username = username;
  }

  public PacketLoginSuccess() {}

  @Override
  public boolean isAsync() {
    return false;
  }

  @Override
  public void handle(PlayerConnection connection, PacketLoginSuccess packetLoginSuccess) {}

  @Override
  public PacketStrategy getStrategy() {
    return strategy;
  }

  public UUID getUuid() {
    return uuid;
  }

  public void setUuid(UUID uuid) {
    this.uuid = uuid;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
