package com.github.avalon.packet.packet.play;

import com.github.avalon.common.data.DataType;
import com.github.avalon.data.Transform;
import com.github.avalon.network.ProtocolType;
import com.github.avalon.packet.annotation.PacketRegister;
import com.github.avalon.packet.packet.Packet;
import com.github.avalon.packet.schema.FunctionScheme;
import com.github.avalon.packet.schema.PacketStrategy;
import com.github.avalon.player.PlayerConnection;

import java.util.UUID;

/**
 * This player is sent to the client when some other player comes into his visibility range and
 * shows the entity of player to client.
 *
 * <h3>Packet Strategy</h3>
 *
 * <ul>
 *   <li>1. Entity identifier. If this entity is spawned as controllable it should have same id.
 *   <li>2. UUID of entity (Player).
 *   <li>3. X position of entity.
 *   <li>4. Y position of entity.
 *   <li>5. Z position of entity.
 *   <li>6. Yaw angle of entity.
 *   <li>7. Pitch angle of entity.
 * </ul>
 *
 * @version 1.1
 */
@PacketRegister(
    operationCode = 0x04,
    protocolType = ProtocolType.PLAY,
    direction = PacketRegister.Direction.CLIENT)
public class PacketSpawnPlayer extends Packet<PacketSpawnPlayer> {

  public PacketStrategy strategy =
      new PacketStrategy(
          new FunctionScheme<>(DataType.VARINT, this::getIdentifier, this::setIdentifier),
          new FunctionScheme<>(DataType.UUID, this::getUuid, this::setUuid),
          new FunctionScheme<>(DataType.DOUBLE, this::getX, this::setX),
          new FunctionScheme<>(DataType.DOUBLE, this::getY, this::setY),
          new FunctionScheme<>(DataType.DOUBLE, this::getZ, this::setZ),
          new FunctionScheme<>(DataType.BYTE, this::getYaw, this::setYaw),
          new FunctionScheme<>(DataType.BYTE, this::getPitch, this::setPitch));

  private int identifier;
  private UUID uuid;

  private double x;
  private double y;
  private double z;

  private byte yaw;
  private byte pitch;

  public PacketSpawnPlayer(int identifier, UUID uuid, Transform transform) {
    this.identifier = identifier;
    this.uuid = uuid;
    x = transform.getX();
    y = transform.getY();
    z = transform.getZ();
    yaw = (byte) (transform.getYaw() * 256.0F / 360.0F);
    pitch = (byte) (transform.getPitch() * 256.0F / 360.0F);
  }

  public PacketSpawnPlayer() {}

  @Override
  public boolean isAsync() {
    return false;
  }

  @Override
  public void handle(PlayerConnection connection, PacketSpawnPlayer packetSpawnPlayer) {}

  @Override
  public PacketStrategy getStrategy() {
    return strategy;
  }

  public int getIdentifier() {
    return identifier;
  }

  public void setIdentifier(int identifier) {
    this.identifier = identifier;
  }

  public UUID getUuid() {
    return uuid;
  }

  public void setUuid(UUID uuid) {
    this.uuid = uuid;
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

  public byte getYaw() {
    return yaw;
  }

  public void setYaw(byte yaw) {
    this.yaw = yaw;
  }

  public byte getPitch() {
    return pitch;
  }

  public void setPitch(byte pitch) {
    this.pitch = pitch;
  }
}
