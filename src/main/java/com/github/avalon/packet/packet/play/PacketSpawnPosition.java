package com.github.avalon.packet.packet.play;

import com.github.avalon.common.data.DataType;
import com.github.avalon.data.Transform;
import com.github.avalon.network.ProtocolType;
import com.github.avalon.packet.annotation.PacketRegister;
import com.github.avalon.packet.packet.Packet;
import com.github.avalon.packet.schema.FunctionScheme;
import com.github.avalon.packet.schema.PacketStrategy;
import com.github.avalon.player.PlayerConnection;

/**
 * Sets the default spawn position for player where will player spawn and where will point by
 * default his compass.
 *
 * <h3>Packet Strategy</h3>
 *
 * <ul>
 *   <li>1. Location (x, y, z), converted into long. (More at <url>www.wiki.vg</url>
 * </ul>
 */
@PacketRegister(
    operationCode = 0x42,
    protocolType = ProtocolType.PLAY,
    direction = PacketRegister.Direction.CLIENT)
public class PacketSpawnPosition extends Packet<PacketSpawnPosition> {

  public PacketStrategy strategy =
      new PacketStrategy(
          new FunctionScheme<>(DataType.POSITION, this::getLocation, this::setLocation));

  private long location;

  public PacketSpawnPosition(Transform location) {
    this.location = location.getLong();
  }

  public PacketSpawnPosition() {}

  @Override
  public boolean isAsync() {
    return false;
  }

  @Override
  public void handle(PlayerConnection connection, PacketSpawnPosition packetSpawnPosition) {}

  @Override
  public PacketStrategy getStrategy() {
    return strategy;
  }

  public long getLocation() {
    return location;
  }

  public void setLocation(long location) {
    this.location = location;
  }
}
