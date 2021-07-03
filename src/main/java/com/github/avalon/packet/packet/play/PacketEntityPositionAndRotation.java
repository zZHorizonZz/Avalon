package com.github.avalon.packet.packet.play;

import com.github.avalon.common.data.DataType;
import com.github.avalon.network.ProtocolType;
import com.github.avalon.packet.annotation.PacketRegister;
import com.github.avalon.packet.packet.Packet;
import com.github.avalon.packet.schema.FunctionScheme;
import com.github.avalon.packet.schema.PacketStrategy;
import com.github.avalon.player.PlayerConnection;

/**
 * This packet is sent by server if connection if inactive for specific period of time. Client
 * should respond with same identifier.
 *
 * <h3>Packet Strategy</h3>
 *
 * <ul>
 *   <li>1. Random generated identifier.
 * </ul>
 *
 * @version 1.1
 */
@PacketRegister(
    operationCode = 0x27,
    protocolType = ProtocolType.PLAY,
    direction = PacketRegister.Direction.CLIENT)
public class PacketEntityPositionAndRotation extends Packet<PacketEntityPositionAndRotation> {

  public PacketStrategy strategy =
      new PacketStrategy(
          new FunctionScheme<>(DataType.VARINT, this::getId, this::setId),
          new FunctionScheme<>(DataType.SHORT, this::getX, this::setX),
          new FunctionScheme<>(DataType.SHORT, this::getY, this::setY),
          new FunctionScheme<>(DataType.SHORT, this::getZ, this::setZ),
          new FunctionScheme<>(DataType.ANGLE, this::getYaw, this::setYaw),
          new FunctionScheme<>(DataType.ANGLE, this::getPitch, this::setPitch),
          new FunctionScheme<>(DataType.BOOLEAN, this::isOnGround, this::setOnGround));

  private int id;
  private short x;
  private short y;
  private short z;
  private float yaw;
  private float pitch;
  private boolean onGround;

  public PacketEntityPositionAndRotation(
      int id, short x, short y, short z, float yaw, float pitch, boolean onGround) {
    this.id = id;
    this.x = x;
    this.y = y;
    this.z = z;
    this.yaw = yaw;
    this.pitch = pitch;
    this.onGround = onGround;
  }

  public PacketEntityPositionAndRotation() {}

  @Override
  public boolean isAsync() {
    return false;
  }

  @Override
  public void handle(
      PlayerConnection connection,
      PacketEntityPositionAndRotation packetEntityPositionAndRotation) {}

  @Override
  public PacketStrategy getStrategy() {
    return strategy;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public short getX() {
    return x;
  }

  public void setX(short x) {
    this.x = x;
  }

  public short getY() {
    return y;
  }

  public void setY(short y) {
    this.y = y;
  }

  public short getZ() {
    return z;
  }

  public void setZ(short z) {
    this.z = z;
  }

  public float getYaw() {
    return yaw;
  }

  public void setYaw(float yaw) {
    this.yaw = yaw;
  }

  public float getPitch() {
    return pitch;
  }

  public void setPitch(float pitch) {
    this.pitch = pitch;
  }

  public boolean isOnGround() {
    return onGround;
  }

  public void setOnGround(boolean onGround) {
    this.onGround = onGround;
  }
}
