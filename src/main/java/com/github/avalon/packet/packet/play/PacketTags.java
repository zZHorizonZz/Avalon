package com.github.avalon.packet.packet.play;

import com.github.avalon.network.PacketBuffer;
import com.github.avalon.network.ProtocolType;
import com.github.avalon.packet.annotation.PacketRegister;
import com.github.avalon.packet.packet.Packet;
import com.github.avalon.packet.schema.PacketStrategy;
import com.github.avalon.player.PlayerConnection;
import com.google.common.annotations.Beta;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

/** This packet is only temporary and in the future should be properly implemented. */
@Beta
@PacketRegister(
    operationCode = 0x5B,
    protocolType = ProtocolType.PLAY,
    direction = PacketRegister.Direction.CLIENT)
public class PacketTags extends Packet<PacketTags> {

  public PacketTags() {}

  @Override
  public boolean isAsync() {
    return false;
  }

  @Override
  public ByteBuf encode(ByteBuf buffer, Packet<?> packet) throws IOException {
    ((PacketBuffer) buffer).writeTags();
    return buffer;
  }

  @Override
  public void handle(PlayerConnection connection, PacketTags packetDeclareRecipe) {}

  @Override
  public PacketStrategy getStrategy() {
    return null;
  }
}
