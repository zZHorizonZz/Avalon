package com.github.avalon.packet.packet.play;

import com.github.avalon.common.data.DataType;
import com.github.avalon.network.ProtocolType;
import com.github.avalon.packet.annotation.PacketRegister;
import com.github.avalon.packet.packet.Packet;
import com.github.avalon.packet.schema.FunctionScheme;
import com.github.avalon.packet.schema.PacketStrategy;
import com.github.avalon.player.PlayerConnection;

/**
 * Sent by client to server every tick on client side (In modified clients this can be different).
 * Contains information about player's movement.
 *
 * <h3>Packet Strategy</h3>
 *
 * <ul>
 *   <li>1. New x player's position.
 *   <li>2. New y player's position.
 *   <li>3. New z player's position.
 *   <li>4. New yaw angle of player.
 *   <li>5. New pitch angle of player.
 *   <li>6. If client things he is on ground.
 * </ul>
 *
 * @version 1.1
 */
@PacketRegister(
    operationCode = 0x13,
    protocolType = ProtocolType.PLAY,
    direction = PacketRegister.Direction.SERVER)
public class PacketPlayerPositionAndRotation extends Packet<PacketPlayerPositionAndRotation> {

  public PacketStrategy strategy =
      new PacketStrategy(
          new FunctionScheme<>(DataType.DOUBLE, this::getX, this::setX),
          new FunctionScheme<>(DataType.DOUBLE, this::getY, this::setY),
          new FunctionScheme<>(DataType.DOUBLE, this::getZ, this::setZ),
          new FunctionScheme<>(DataType.ANGLE, this::getYaw, this::setYaw),
          new FunctionScheme<>(DataType.ANGLE, this::getPitch, this::setPitch),
          new FunctionScheme<>(DataType.BOOLEAN, this::isOnGround, this::setOnGround));

  private double x;
  private double y;
  private double z;
  private float yaw;
  private float pitch;
  private boolean onGround;

  public PacketPlayerPositionAndRotation(
      double x, double y, double z, float yaw, float pitch, boolean onGround) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.yaw = yaw;
    this.pitch = pitch;
    this.onGround = onGround;
  }

  public PacketPlayerPositionAndRotation() {}

  @Override
  public boolean isAsync() {
    return false;
  }

  @Override
  public void handle(PlayerConnection connection, PacketPlayerPositionAndRotation packet) {
    connection.getPlayer().getServer().getPacketModule().handle(packet, connection);
  }

  @Override
  public PacketStrategy getStrategy() {
    return strategy;
  }

  public double getX() {
    return x;
  }

  public void setX(double x) {
    this.x = x;
  }

  public double getY() {
    return y;
  }

  public void setY(double y) {
    this.y = y;
  }

  public double getZ() {
    return z;
  }

  public void setZ(double z) {
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
