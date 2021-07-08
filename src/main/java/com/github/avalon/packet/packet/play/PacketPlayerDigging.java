package com.github.avalon.packet.packet.play;

import com.github.avalon.block.BlockFace;
import com.github.avalon.common.data.DataType;
import com.github.avalon.network.ProtocolType;
import com.github.avalon.packet.annotation.PacketRegister;
import com.github.avalon.packet.packet.Packet;
import com.github.avalon.packet.schema.EnumScheme;
import com.github.avalon.packet.schema.FunctionScheme;
import com.github.avalon.packet.schema.PacketStrategy;
import com.github.avalon.player.PlayerConnection;

/**
 * Description of packet.
 *
 * <h3>Packet Strategy</h3>
 *
 * <ul>
 *   <li>1. Some Strategy.
 * </ul>
 *
 * @version 1.0
 */
@PacketRegister(
    operationCode = 0x1B,
    protocolType = ProtocolType.PLAY,
    direction = PacketRegister.Direction.SERVER)
public class PacketPlayerDigging extends Packet<PacketPlayerDigging> {

  public PacketStrategy strategy =
      new PacketStrategy(
          new EnumScheme<>(DiggingStatus.class, this::getStatus, this::setStatus),
          new FunctionScheme<>(DataType.POSITION, this::getPosition, this::setPosition),
          new EnumScheme<>(BlockFace.class, this::getBlockFace, this::setBlockFace));

  private DiggingStatus status;
  private long position;
  private BlockFace blockFace;

  public PacketPlayerDigging(DiggingStatus status, long location, BlockFace blockFace) {
    this.status = status;
    this.position = location;
    this.blockFace = blockFace;
  }

  public PacketPlayerDigging() {}

  @Override
  public PacketStrategy getStrategy() {
    return strategy;
  }

  @Override
  public boolean isAsync() {
    return false;
  }

  @Override
  public void handle(PlayerConnection connection, PacketPlayerDigging packet) {
    connection.getPlayer().getServer().getPacketModule().handle(packet, connection);
  }

  public DiggingStatus getStatus() {
    return status;
  }

  public void setStatus(DiggingStatus status) {
    this.status = status;
  }

  public long getPosition() {
    return position;
  }

  public void setPosition(long position) {
    this.position = position;
  }

  public BlockFace getBlockFace() {
    return blockFace;
  }

  public void setBlockFace(BlockFace blockFace) {
    this.blockFace = blockFace;
  }

  public enum DiggingStatus {
    STARTED_DIGGING,
    CANCELLED_DIGGING,
    FINISHED_DIGGING,
    DROP_ITEM_STACK,
    DROP_ITEM,
    SHOOT_ARROW,
    SWAP_ITEM;
  }
}
