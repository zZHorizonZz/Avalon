package com.github.avalon.packet.packet.play;

import com.github.avalon.common.bytes.BitField;
import com.github.avalon.common.data.DataType;
import com.github.avalon.common.system.UtilSecurity;
import com.github.avalon.data.Transform;
import com.github.avalon.network.ProtocolType;
import com.github.avalon.packet.annotation.PacketRegister;
import com.github.avalon.packet.packet.Packet;
import com.github.avalon.packet.schema.FunctionScheme;
import com.github.avalon.packet.schema.PacketStrategy;
import com.github.avalon.player.PlayerConnection;

/**
 * This packets sent data about player's teleport this not includes move of entity this only move's
 * client itself. Also this packet is sent in login sequence as to remove loading screen.
 *
 * <h3>Packet Strategy</h3>
 *
 * <ul>
 *   <li>1. X position that will be player teleported.
 *   <li>2. Y position that will be player teleported.
 *   <li>3. Z position that will be player teleported.
 *   <li>4. New yaw angle of player.
 *   <li>5. New pitch angle of player.
 *   <li>6. Teleport flags.
 *   <li>7. Teleport identifier. Client should respond with {@link PacketTeleportConfirm}
 * </ul>
 *
 * @version 1.1
 */
@PacketRegister(
    operationCode = 0x34,
    protocolType = ProtocolType.PLAY,
    direction = PacketRegister.Direction.CLIENT)
public class PacketPositionAndLook extends Packet<PacketPositionAndLook> {

  public PacketStrategy strategy =
      new PacketStrategy(
          new FunctionScheme<>(DataType.DOUBLE, this::getX, this::setX),
          new FunctionScheme<>(DataType.DOUBLE, this::getY, this::setY),
          new FunctionScheme<>(DataType.DOUBLE, this::getZ, this::setZ),
          new FunctionScheme<>(DataType.FLOAT, this::getYaw, this::setYaw),
          new FunctionScheme<>(DataType.FLOAT, this::getPitch, this::setPitch),
          new FunctionScheme<>(DataType.BIT_FIELD, this::getFlags, this::setFlags),
          new FunctionScheme<>(DataType.VARINT, this::getTeleportId, this::setTeleportId));

  private double x;
  private double y;
  private double z;
  private float yaw;
  private float pitch;
  private BitField flags;
  private int teleportId;

  public PacketPositionAndLook(Transform transform) {
    x = transform.getX();
    y = transform.getY();
    z = transform.getZ();
    yaw = transform.getYaw();
    pitch = transform.getPitch();
    flags = new BitField();
    teleportId = UtilSecurity.generateVerifyIdentifier();
  }

  public PacketPositionAndLook() {}

  @Override
  public boolean isAsync() {
    return false;
  }

  @Override
  public void handle(PlayerConnection connection, PacketPositionAndLook packetPositionAndLook) {}

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

  public BitField getFlags() {
    return flags;
  }

  public void setFlags(BitField flags) {
    this.flags = flags;
  }

  public int getTeleportId() {
    return teleportId;
  }

  public void setTeleportId(int teleportId) {
    this.teleportId = teleportId;
  }
}
