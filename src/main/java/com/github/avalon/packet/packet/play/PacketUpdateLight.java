package com.github.avalon.packet.packet.play;

import com.github.avalon.network.PacketBuffer;
import com.github.avalon.packet.packet.LegacyPacket;
import com.github.avalon.player.PlayerConnection;

import java.io.IOException;

public class PacketUpdateLight extends LegacyPacket<PacketUpdateLight> {

  private int x;
  private int z;

  private boolean trustEdges;

  public PacketUpdateLight(int x, int z, boolean trustEdges) {
    super(0x25);
    this.x = x;
    this.z = z;
    this.trustEdges = trustEdges;
  }

  public PacketUpdateLight() {
    super(0x25);
  }

  @Override
  public PacketUpdateLight decode(PacketBuffer buffer) throws IOException {
    return null;
  }

  @Override
  public PacketBuffer encode(PacketBuffer buffer, PacketUpdateLight packet) throws IOException {
    buffer.writeVarInt(packet.getX());
    buffer.writeVarInt(packet.getZ());
    buffer.writeBoolean(packet.isTrustEdges());
    buffer.writeVarInt(0);
    buffer.writeVarInt(0);
    buffer.writeVarInt(0);
    buffer.writeVarInt(0);
    buffer.writeVarInt(0);
    buffer.writeVarInt(0);
    return buffer;
  }

  @Override
  public boolean isAsync() {
    return false;
  }

  @Override
  public void handle(PlayerConnection connection, PacketUpdateLight packetUpdateLight) {}

  public int getX() {
    return x;
  }

  public int getZ() {
    return z;
  }

  public boolean isTrustEdges() {
    return trustEdges;
  }
}
