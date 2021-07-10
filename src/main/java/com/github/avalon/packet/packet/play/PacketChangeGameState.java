package com.github.avalon.packet.packet.play;

import com.github.avalon.common.data.DataType;
import com.github.avalon.common.exception.NotSupportedException;
import com.github.avalon.network.ProtocolType;
import com.github.avalon.packet.annotation.PacketRegister;
import com.github.avalon.packet.packet.Packet;
import com.github.avalon.packet.schema.EnumScheme;
import com.github.avalon.packet.schema.FunctionScheme;
import com.github.avalon.packet.schema.PacketStrategy;
import com.github.avalon.player.PlayerConnection;
import com.github.avalon.player.attributes.GameState;

/**
 * Sends the client information about his current state respectively what state player should
 * currently render/see/hear (pufferfish).
 *
 * <h3>Packet Strategy</h3>
 *
 * <ul>
 *   <li>1. Game state that will be sent. Also there is protection so you can not set higher values
 *       that allowed for state.
 *   <li>2. State for game state
 * </ul>
 *
 * @version 1.0
 */
@PacketRegister(
    operationCode = 0x1D,
    protocolType = ProtocolType.PLAY,
    direction = PacketRegister.Direction.CLIENT)
public class PacketChangeGameState extends Packet<PacketChangeGameState> {

  public PacketStrategy strategy =
      new PacketStrategy(
          new EnumScheme<>(GameState.class, this::getGameStateEnum, this::setGameStateEnum),
          new FunctionScheme<>(DataType.FLOAT, this::getState, this::setState));

  private GameState gameStateEnum;
  private float state;

  public PacketChangeGameState(GameState gameStateEnum, float state) {
    this.gameStateEnum = gameStateEnum;

    assert !(state > gameStateEnum.getMaximumState())
        : "State for "
            + gameStateEnum.name()
            + " is invalid maximum is "
            + gameStateEnum.getMaximumState();

    this.state = state;
  }

  public PacketChangeGameState() {}

  @Override
  public boolean isAsync() {
    return false;
  }

  @Override
  public void handle(PlayerConnection connection, PacketChangeGameState packetPlayerAbilities) {
    throw new NotSupportedException("This type of operation is not valid for this packet.");
  }

  @Override
  public PacketStrategy getStrategy() {
    return strategy;
  }

  public GameState getGameStateEnum() {
    return gameStateEnum;
  }

  public void setGameStateEnum(GameState gameStateEnum) {
    assert !(state > gameStateEnum.getMaximumState())
        : "State for "
            + gameStateEnum.name()
            + " is invalid maximum is "
            + gameStateEnum.getMaximumState();

    this.gameStateEnum = gameStateEnum;
  }

  public float getState() {
    return state;
  }

  public void setState(float state) {
    assert !(state > gameStateEnum.getMaximumState())
        : "State for "
            + gameStateEnum.name()
            + " is invalid maximum is "
            + gameStateEnum.getMaximumState();

    this.state = state;
  }
}
