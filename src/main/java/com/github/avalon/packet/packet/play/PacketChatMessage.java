package com.github.avalon.packet.packet.play;

import com.github.avalon.chat.message.Message;
import com.github.avalon.common.data.DataType;
import com.github.avalon.network.ProtocolType;
import com.github.avalon.packet.annotation.PacketRegister;
import com.github.avalon.packet.packet.Packet;
import com.github.avalon.packet.schema.FunctionScheme;
import com.github.avalon.packet.schema.PacketStrategy;
import com.github.avalon.player.PlayerConnection;

import java.util.UUID;

/**
 * This packet send message to client. We should also consider fact that client can have disabled
 * chat, command only and else. So when sending message we should check these attributes as well.
 *
 * <h3>Packet Strategy</h3>
 *
 * <ul>
 *   <li>1. Message converted to json. More info about json format <url>wiki.vg/ChatOperator</url>
 *   <li>2. Position on screen respectively for us type of message. (0: chat (chat box), 1: system
 *       message (chat box), 2: game info (above hotbar)) according to documentation.
 *   <li>3. UUID of sender can be blank, random or something else.
 * </ul>
 *
 * @version 1.0
 */
@PacketRegister(
    operationCode = 0x0E,
    protocolType = ProtocolType.PLAY,
    direction = PacketRegister.Direction.CLIENT)
public class PacketChatMessage extends Packet<PacketChatMessage> {

  public PacketStrategy strategy =
      new PacketStrategy(
          new FunctionScheme<>(DataType.CHAT, this::getMessage, this::setMessage),
          new FunctionScheme<>(DataType.BYTE, this::getPosition, this::setPosition),
          new FunctionScheme<>(DataType.UUID, this::getSender, this::setSender));

  private Message message;
  private byte position;
  private UUID sender;

  public PacketChatMessage(Message message, byte position, UUID sender) {
    this.message = message;
    this.position = position;
    this.sender = sender;
  }

  public PacketChatMessage() {}

  @Override
  public PacketStrategy getStrategy() {
    return strategy;
  }

  @Override
  public boolean isAsync() {
    return true;
  }

  @Override
  public void handle(PlayerConnection connection, PacketChatMessage packetChatMessage) {}

  public Message getMessage() {
    return message;
  }

  public void setMessage(Message message) {
    this.message = message;
  }

  public byte getPosition() {
    return position;
  }

  public void setPosition(byte position) {
    this.position = position;
  }

  public UUID getSender() {
    return sender;
  }

  public void setSender(UUID sender) {
    this.sender = sender;
  }
}
