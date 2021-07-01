package com.github.avalon.packet.packet.play;

import com.github.avalon.network.ProtocolType;
import com.github.avalon.packet.annotation.PacketRegister;
import com.github.avalon.packet.packet.Packet;
import com.github.avalon.packet.schema.EnumScheme;
import com.github.avalon.packet.schema.PacketStrategy;
import com.github.avalon.player.PlayerConnection;
import com.github.avalon.player.attributes.Hand;

/**
 * Packet sent by the client to server as information about animation.
 *
 * <h3>Packet Strategy</h3>
 *
 * <ul>
 *   <li>1. Hand that with player played animation.
 * </ul>
 *
 * @version 1.0
 */
@PacketRegister(
    operationCode = 0x2C,
    protocolType = ProtocolType.PLAY,
    direction = PacketRegister.Direction.SERVER)
public class PacketHandAnimation extends Packet<PacketHandAnimation> {

  public PacketStrategy strategy =
      new PacketStrategy(new EnumScheme<>(Hand.class, this::getHand, this::setHand));

  private Hand hand;

  public PacketHandAnimation(Hand hand) {
    this.hand = hand;
  }

  public PacketHandAnimation() {}

  @Override
  public PacketStrategy getStrategy() {
    return strategy;
  }

  @Override
  public boolean isAsync() {
    return false;
  }

  @Override
  public void handle(PlayerConnection connection, PacketHandAnimation packetHandAnimation) {}

  public Hand getHand() {
    return hand;
  }

  public void setHand(Hand hand) {
    this.hand = hand;
  }
}
