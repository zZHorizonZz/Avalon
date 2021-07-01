package com.github.avalon.packet.packet;

import com.flowpowered.network.AsyncableMessage;
import com.flowpowered.network.Codec;
import com.flowpowered.network.Message;
import com.flowpowered.network.MessageHandler;
import com.github.avalon.network.PacketBuffer;
import com.github.avalon.player.PlayerConnection;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

public abstract class LegacyPacket<P extends Message>
    implements Codec<P>, MessageHandler<PlayerConnection, P>, AsyncableMessage {

  public int opcode;

  public LegacyPacket(int opcode) {
    this.opcode = opcode;
  }

  public abstract P decode(PacketBuffer buffer) throws IOException;

  public abstract PacketBuffer encode(PacketBuffer buffer, P packet) throws IOException;

  @Override
  public P decode(ByteBuf buffer) throws IOException {
    return decode((PacketBuffer) buffer);
  }

  @Override
  public ByteBuf encode(ByteBuf buffer, P packet) throws IOException {
    return encode((PacketBuffer) buffer, packet);
  }

  public int getOpcode() {
    return opcode;
  }
}
