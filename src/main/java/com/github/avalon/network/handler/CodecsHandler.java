package com.github.avalon.network.handler;

import com.flowpowered.network.Codec;
import com.flowpowered.network.Message;
import com.flowpowered.network.util.ByteBufUtils;
import com.github.avalon.network.PacketBuffer;
import com.github.avalon.network.protocol.ProtocolRegistry;
import com.github.avalon.server.Server;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.EncoderException;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

public final class CodecsHandler extends MessageToMessageCodec<ByteBuf, Message> {

  private final Server server;
  private final ProtocolRegistry protocol;

  public CodecsHandler(
          Server server, ProtocolRegistry protocol) {
    this.server = server;
    this.protocol = protocol;
  }

  @Override
  protected void encode(ChannelHandlerContext context, Message message, List<Object> out)
      throws EncoderException, java.io.IOException {
    Class<? extends Message> clazz = message.getClass();
    Codec.CodecRegistration registration = protocol.getCodecRegistration(clazz);
    if (registration == null) {
      throw new EncoderException("Unknown message type: " + clazz + '.');
    }

    ByteBuf headerBuf = context.alloc().buffer(8);
    ByteBufUtils.writeVarInt(headerBuf, registration.getOpcode());

    PacketBuffer packetBuffer =
        new PacketBuffer(server.getDescriptorModule(), context.alloc().buffer());
    packetBuffer = (PacketBuffer) registration.getCodec().encode(packetBuffer, message);

    out.add(Unpooled.wrappedBuffer(headerBuf, packetBuffer));
  }

  @Override
  protected void decode(ChannelHandlerContext context, ByteBuf byteBuf, List<Object> out)
      throws java.io.IOException, com.flowpowered.network.exception.IllegalOpcodeException {
    Codec<?> codecs = protocol.newReadHeader(byteBuf);

    Message decoded = codecs.decode(new PacketBuffer(server.getDescriptorModule(), byteBuf));
    if (byteBuf.readableBytes() > 0) {}

    out.add(decoded);
  }

  public ProtocolRegistry getProtocol() {
    return protocol;
  }
}
