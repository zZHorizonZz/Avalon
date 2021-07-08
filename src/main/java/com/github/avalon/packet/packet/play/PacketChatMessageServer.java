package com.github.avalon.packet.packet.play;

import com.github.avalon.common.data.DataType;
import com.github.avalon.network.ProtocolType;
import com.github.avalon.packet.annotation.PacketRegister;
import com.github.avalon.packet.packet.Packet;
import com.github.avalon.packet.schema.FunctionScheme;
import com.github.avalon.packet.schema.PacketStrategy;
import com.github.avalon.player.PlayerConnection;

/**
 * This packet is sent by client to server and contains message that client sends to chat
 * (Usually...).
 *
 * <h3>Packet Strategy</h3>
 *
 * <ul>
 *   <li>1. Text of message.
 * </ul>
 *
 * @version 1.0
 */
@PacketRegister(
    operationCode = 0x03,
    protocolType = ProtocolType.PLAY,
    direction = PacketRegister.Direction.SERVER)
public class PacketChatMessageServer extends Packet<PacketChatMessageServer> {

  public PacketStrategy strategy =
      new PacketStrategy(new FunctionScheme<>(DataType.STRING, this::getMessage, this::setMessage));

  private String message;

  public PacketChatMessageServer() {}

  @Override
  public boolean isAsync() {
    return false;
  }

  @Override
  public void handle(PlayerConnection connection, PacketChatMessageServer packet) {
    connection.getPlayer().getServer().getPacketModule().handle(packet, connection);
  }

  @Override
  public PacketStrategy getStrategy() {
    return strategy;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
