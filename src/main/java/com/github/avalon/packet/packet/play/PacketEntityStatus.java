package com.github.avalon.packet.packet.play;

import com.github.avalon.common.data.DataType;
import com.github.avalon.network.ProtocolType;
import com.github.avalon.packet.annotation.PacketRegister;
import com.github.avalon.packet.packet.Packet;
import com.github.avalon.packet.schema.FunctionScheme;
import com.github.avalon.packet.schema.PacketStrategy;
import com.github.avalon.player.PlayerConnection;

/**
 * Entity status send current status of entity to the client. What it mean practically is that
 * client will trigger animation with specific id that is send as status.
 *
 * <h3>Packet Strategy</h3>
 *
 * <ul>
 *   <li>1. Identifier of entity
 *   <li>2. Entity status (Animation id depends on entity type. More info:
 *       <url>wiki.vg/Entity_statuses</url>)
 * </ul>
 *
 * @version 1.1
 */
@PacketRegister(
    operationCode = 0x1A,
    protocolType = ProtocolType.PLAY,
    direction = PacketRegister.Direction.CLIENT)
public class PacketEntityStatus extends Packet<PacketEntityStatus> {

  public PacketStrategy strategy =
      new PacketStrategy(
          new FunctionScheme<>(DataType.INTEGER, this::getIdentifier, this::setIdentifier),
          new FunctionScheme<>(DataType.BYTE, this::getStatus, this::setStatus));

  private int identifier;
  private byte status;

  public PacketEntityStatus(int identifier, byte status) {
    this.identifier = identifier;
    this.status = status;
  }

  public PacketEntityStatus() {}

  @Override
  public boolean isAsync() {
    return false;
  }

  @Override
  public void handle(PlayerConnection connection, PacketEntityStatus packetEntityStatus) {}

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

  public byte getStatus() {
    return status;
  }

  public void setStatus(byte status) {
    this.status = status;
  }
}
