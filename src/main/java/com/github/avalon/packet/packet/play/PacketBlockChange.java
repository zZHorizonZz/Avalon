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
 * Block change packet handles the sending of data to the client about a change of the block. We can
 * send this packet through the client class itself. But we must keep in mind that these block
 * changes will not be considered as server-side changes. So if we want to change block even on server
 * side we should use method in dimension class.
 *
 * <h3>Packet Strategy</h3>
 *
 * <ul>
 *   <li>1. Location of the block converted to the long based on <url>www.wiki.vg</url>
 *   <li>2. Block identifier
 * </ul>
 *
 * @version 1.1
 */
@PacketRegister(
    operationCode = 0x0B,
    protocolType = ProtocolType.PLAY,
    direction = PacketRegister.Direction.CLIENT)
public class PacketBlockChange extends Packet<PacketBlockChange> {

  public PacketStrategy strategy =
      new PacketStrategy(
          new FunctionScheme<>(DataType.LONG, this::getLocation, this::setLocation),
          new FunctionScheme<>(DataType.VARINT, this::getIdentifier, this::setIdentifier));

  private long location;
  private int identifier;

  public PacketBlockChange(long location, int identifier) {
    this.location = location;
    this.identifier = identifier;
  }

  public PacketBlockChange(int x, int y, int z, int identifier) {
    location = (((long) x & 0x3FFFFFF) << 38) | (((long) z & 0x3FFFFFF) << 12) | ((long) y & 0xFFF);
    this.identifier = identifier;
  }

  public PacketBlockChange(Transform transform, int identifier) {
    location = transform.getLong();
    this.identifier = identifier;
  }

  public PacketBlockChange() {}

  @Override
  public boolean isAsync() {
    return false;
  }

  @Override
  public void handle(PlayerConnection connection, PacketBlockChange packetBlockChange) {}

  public long getLocation() {
    return location;
  }

  public int getIdentifier() {
    return identifier;
  }

  public void setLocation(long location) {
    this.location = location;
  }

  public void setIdentifier(int identifier) {
    this.identifier = identifier;
  }

  @Override
  public PacketStrategy getStrategy() {
    return strategy;
  }
}
