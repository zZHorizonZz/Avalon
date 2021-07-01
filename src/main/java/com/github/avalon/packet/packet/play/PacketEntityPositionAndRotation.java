package com.github.avalon.packet.packet.play;

import com.github.avalon.network.PacketBuffer;
import com.github.avalon.packet.packet.LegacyPacket;
import com.github.avalon.player.PlayerConnection;

import java.io.IOException;

public class PacketEntityPositionAndRotation extends LegacyPacket<PacketEntityPositionAndRotation> {

  private int id;
  private short x;
  private short y;
  private short z;
  private float yaw;
  private float pitch;
  private boolean onGround;

  public PacketEntityPositionAndRotation(
          int id, short x, short y, short z, float yaw, float pitch, boolean onGround) {
    super(0x27);
    this.id = id;
    this.x = x;
    this.y = y;
    this.z = z;
    this.yaw = yaw;
    this.pitch = pitch;
    this.onGround = onGround;
  }

  public PacketEntityPositionAndRotation() {
    super(0x27);
  }

  @Override
  public boolean isAsync() {
    return false;
  }

  @Override
  public PacketEntityPositionAndRotation decode(PacketBuffer buffer) throws IOException {
    int id = buffer.readVarInt();
    short x = buffer.readShort();
    short y = buffer.readShort();
    short z = buffer.readShort();
    float yaw = buffer.readAngle();
    float pitch = buffer.readAngle();
    boolean ground = buffer.readBoolean();
    return new PacketEntityPositionAndRotation(id, x, y, z, yaw, pitch, ground);
  }

  @Override
  public PacketBuffer encode(PacketBuffer buffer, PacketEntityPositionAndRotation packet)
      throws IOException {
    buffer.writeVarInt(packet.getId());
    buffer.writeShort(packet.getX());
    buffer.writeShort(packet.getY());
    buffer.writeShort(packet.getZ());
    buffer.writeAngle(packet.getYaw());
    buffer.writeAngle(packet.getPitch());
    buffer.writeBoolean(packet.isOnGround());
    return buffer;
  }

  public short getX() {
    return x;
  }

  public short getY() {
    return y;
  }

  public short getZ() {
    return z;
  }

  public float getYaw() {
    return yaw;
  }

  public float getPitch() {
    return pitch;
  }

  public boolean isOnGround() {
    return onGround;
  }

  public int getId() {
    return id;
  }

  @Override
  public void handle(
          PlayerConnection connection,
          PacketEntityPositionAndRotation packetEntityPositionAndRotation) {}
}
