package com.github.avalon.packet.packet.play;

import com.github.avalon.common.data.DataType;
import com.github.avalon.network.ProtocolType;
import com.github.avalon.packet.annotation.PacketRegister;
import com.github.avalon.packet.packet.Packet;
import com.github.avalon.packet.schema.EnumScheme;
import com.github.avalon.packet.schema.FunctionScheme;
import com.github.avalon.packet.schema.PacketStrategy;
import com.github.avalon.player.PlayerConnection;
import com.github.avalon.player.attributes.Action;

/**
 * Packet is sent as informative packet to the client containing reason of kick. Can be also
 * formatted.
 *
 * <h3>Packet Strategy</h3>
 *
 * <ul>
 *   <li>1. Reason of kick.
 * </ul>
 *
 * @version 1.1
 */
@PacketRegister(
    operationCode = 0x1C,
    protocolType = ProtocolType.PLAY,
    direction = PacketRegister.Direction.SERVER)
public class PacketEntityAction extends Packet<PacketEntityAction> {

  public PacketStrategy strategy =
      new PacketStrategy(
          new FunctionScheme<>(DataType.VARINT, this::getIdentifier, this::setIdentifier),
          new EnumScheme<>(Action.class, this::getAction, this::setAction),
          new FunctionScheme<>(DataType.VARINT, this::getJumpBoost, this::setJumpBoost));

  private int identifier;
  private Action action;
  private int jumpBoost;

  public PacketEntityAction(int identifier, Action action, int jumpBoost) {
    this.identifier = identifier;
    this.action = action;
    this.jumpBoost = jumpBoost;
  }

  public PacketEntityAction() {}

  @Override
  public boolean isAsync() {
    return false;
  }

  @Override
  public void handle(PlayerConnection connection, PacketEntityAction packetEntityAction) {}

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

  public Action getAction() {
    return action;
  }

  public void setAction(Action action) {
    this.action = action;
  }

  public int getJumpBoost() {
    return jumpBoost;
  }

  public void setJumpBoost(int jumpBoost) {
    this.jumpBoost = jumpBoost;
  }
}
