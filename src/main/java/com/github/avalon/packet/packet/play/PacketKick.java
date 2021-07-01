package com.github.avalon.packet.packet.play;

import com.github.avalon.chat.message.Message;
import com.github.avalon.common.data.DataType;
import com.github.avalon.network.ProtocolType;
import com.github.avalon.packet.annotation.PacketRegister;
import com.github.avalon.packet.packet.Packet;
import com.github.avalon.packet.schema.FunctionScheme;
import com.github.avalon.packet.schema.PacketStrategy;
import com.github.avalon.player.PlayerConnection;

/**
 * Packet is sent as informative packet to the client containing reason of kick. Can be also
 * formatted.
 *
 * <h3>Packet Strategy</h3>
 *
 * <ul>
 *   <li>1. Reason of kick.
 * </ul>
 *
 * @version 1.1
 */
@PacketRegister(
    operationCode = 0x19,
    protocolType = ProtocolType.PLAY,
    direction = PacketRegister.Direction.CLIENT)
public class PacketKick extends Packet<PacketKick> {

  public PacketStrategy strategy =
      new PacketStrategy(new FunctionScheme<>(DataType.CHAT, this::getMessage, this::setMessage));

  private Message message;

  public PacketKick(Message message) {
    this.message = message;
  }

  public PacketKick() {}

  @Override
  public boolean isAsync() {
    return false;
  }

  @Override
  public void handle(PlayerConnection connection, PacketKick packetKick) {}

  @Override
  public PacketStrategy getStrategy() {
    return strategy;
  }

  public void setMessage(Message message) {
    this.message = message;
  }

  public Message getMessage() {
    return message;
  }
}
