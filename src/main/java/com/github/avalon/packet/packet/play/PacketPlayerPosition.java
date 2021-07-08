package com.github.avalon.packet.packet.play;

import com.github.avalon.common.data.DataType;
import com.github.avalon.network.ProtocolType;
import com.github.avalon.packet.annotation.PacketRegister;
import com.github.avalon.packet.packet.Packet;
import com.github.avalon.packet.schema.FunctionScheme;
import com.github.avalon.packet.schema.PacketStrategy;
import com.github.avalon.player.PlayerConnection;

@PacketRegister(
    operationCode = 0x12,
    protocolType = ProtocolType.PLAY,
    direction = PacketRegister.Direction.SERVER)
public class PacketPlayerPosition extends Packet<PacketPlayerPosition> {

  public PacketStrategy strategy =
      new PacketStrategy(
          new FunctionScheme<>(DataType.DOUBLE, this::getX, this::setX),
          new FunctionScheme<>(DataType.DOUBLE, this::getY, this::setY),
          new FunctionScheme<>(DataType.DOUBLE, this::getZ, this::setZ),
          new FunctionScheme<>(DataType.BOOLEAN, this::isOnGround, this::setOnGround));

  private double x;
  private double y;
  private double z;
  private boolean onGround;

  public PacketPlayerPosition(double x, double y, double z, boolean onGround) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.onGround = onGround;
  }

  public PacketPlayerPosition() {}

  @Override
  public PacketStrategy getStrategy() {
    return strategy;
  }

  @Override
  public boolean isAsync() {
    return false;
  }

  @Override
  public void handle(PlayerConnection connection, PacketPlayerPosition packet) {
    connection.getPlayer().getServer().getPacketModule().handle(packet, connection);
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

  public boolean isOnGround() {
    return onGround;
  }

  public void setOnGround(boolean onGround) {
    this.onGround = onGround;
  }
}
