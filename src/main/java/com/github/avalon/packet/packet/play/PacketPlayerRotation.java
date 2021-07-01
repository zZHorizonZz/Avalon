package com.github.avalon.packet.packet.play;

import com.github.avalon.common.data.DataType;
import com.github.avalon.network.ProtocolType;
import com.github.avalon.packet.annotation.PacketRegister;
import com.github.avalon.packet.packet.Packet;
import com.github.avalon.packet.schema.FunctionScheme;
import com.github.avalon.packet.schema.PacketStrategy;
import com.github.avalon.player.PlayerConnection;

@PacketRegister(
    operationCode = 0x14,
    protocolType = ProtocolType.PLAY,
    direction = PacketRegister.Direction.SERVER)
public class PacketPlayerRotation extends Packet<PacketPlayerRotation> {

  public PacketStrategy strategy =
      new PacketStrategy(
          new FunctionScheme<>(DataType.FLOAT, this::getYaw, this::setYaw),
          new FunctionScheme<>(DataType.FLOAT, this::getPitch, this::setYaw),
          new FunctionScheme<>(DataType.BOOLEAN, this::isOnGround, this::setOnGround));

  private float yaw;
  private float pitch;
  private boolean onGround;

  public PacketPlayerRotation(float yaw, float pitch, boolean onGround) {
    this.yaw = yaw;
    this.pitch = pitch;
    this.onGround = onGround;
  }

  public PacketPlayerRotation() {}

  @Override
  public boolean isAsync() {
    return false;
  }

  @Override
  public void handle(PlayerConnection connection, PacketPlayerRotation packetPlayerRotation) {

  }

  @Override
  public PacketStrategy getStrategy() {
    return strategy;
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
