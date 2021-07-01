package com.github.avalon.packet.packet.play;

import com.github.avalon.common.data.DataType;
import com.github.avalon.dimension.dimension.Difficulty;
import com.github.avalon.dimension.dimension.Dimension;
import com.github.avalon.network.ProtocolType;
import com.github.avalon.packet.annotation.PacketRegister;
import com.github.avalon.packet.packet.Packet;
import com.github.avalon.packet.schema.FunctionScheme;
import com.github.avalon.packet.schema.PacketStrategy;
import com.github.avalon.player.PlayerConnection;

/**
 * Packet Server difficulty send data about difficulty to the client. Basically it just change
 * difficulty button and optionally lock this button.
 *
 * <h3>Packet Strategy</h3>
 *
 * <ul>
 *   <li>1. Index of difficulty.
 *   <li>2. Information if difficulty should be locked or not.
 * </ul>
 *
 * @version 1.1
 */
@PacketRegister(
    operationCode = 0x0D,
    protocolType = ProtocolType.PLAY,
    direction = PacketRegister.Direction.CLIENT)
public class PacketServerDifficulty extends Packet<PacketServerDifficulty> {

  public PacketStrategy strategy =
      new PacketStrategy(
          new FunctionScheme<>(DataType.BYTE, this::getDifficulty, this::setDifficulty),
          new FunctionScheme<>(DataType.BOOLEAN, this::isLocked, this::setLocked));

  private byte difficulty;
  private boolean locked;

  public PacketServerDifficulty(Dimension dimension) {
    difficulty = (byte) dimension.getDifficulty().getIdentifier();
    locked = dimension.isDifficultyLocked();
  }

  public PacketServerDifficulty(Difficulty difficulty, boolean locked) {
    this.difficulty = (byte) difficulty.getIdentifier();
    this.locked = locked;
  }

  public PacketServerDifficulty() {}

  @Override
  public boolean isAsync() {
    return false;
  }

  @Override
  public void handle(PlayerConnection connection, PacketServerDifficulty packetServerDifficulty) {}

  @Override
  public PacketStrategy getStrategy() {
    return strategy;
  }

  public byte getDifficulty() {
    return difficulty;
  }

  public void setDifficulty(byte difficulty) {
    this.difficulty = difficulty;
  }

  public boolean isLocked() {
    return locked;
  }

  public void setLocked(boolean locked) {
    this.locked = locked;
  }
}
