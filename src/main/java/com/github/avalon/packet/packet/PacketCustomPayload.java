package com.github.avalon.packet.packet;

import com.flowpowered.network.util.ByteBufUtils;
import com.github.avalon.network.PacketBuffer;
import com.github.avalon.player.PlayerConnection;

import java.io.IOException;

public class PacketCustomPayload extends LegacyPacket<PacketCustomPayload> {

  private String channelIdentifier;
  private PacketBuffer data;

  public PacketCustomPayload(String channelIdentifier, PacketBuffer data) {
    super(0x17);
    this.channelIdentifier = channelIdentifier;
    this.data = data;
  }

  public PacketCustomPayload() {
    super(0x17);
  }

  @Override
  public boolean isAsync() {
    return false;
  }

  @Override
  public PacketCustomPayload decode(PacketBuffer buffer) throws IOException {
    /*String identifier = buffer.readUTF8();
    PacketBuffer data = new PacketBuffer(buffer.getDescriptorManager(), buffer.getBytes(buffer.readableBytes(), buffer));*/
    PacketCustomPayload packet = new PacketCustomPayload();
    return packet;
  }

  @Override
  public PacketBuffer encode(PacketBuffer byteBuf, PacketCustomPayload packetCustomPayload)
      throws IOException {
    ByteBufUtils.writeUTF8(byteBuf, packetCustomPayload.getChannelIdentifier());
    byteBuf.writeBytes(packetCustomPayload.getData());
    return byteBuf;
  }

  public String getChannelIdentifier() {
    return channelIdentifier;
  }

  public PacketBuffer getData() {
    return data;
  }

  @Override
  public void handle(PlayerConnection connection, PacketCustomPayload packetCustomPayload) {

  }
}
