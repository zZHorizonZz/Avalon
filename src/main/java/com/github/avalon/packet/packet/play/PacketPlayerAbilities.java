package com.github.avalon.packet.packet.play;

import com.github.avalon.common.bytes.BitField;
import com.github.avalon.common.data.DataType;
import com.github.avalon.common.exception.NotSupportedException;
import com.github.avalon.network.ProtocolType;
import com.github.avalon.packet.annotation.PacketRegister;
import com.github.avalon.packet.packet.Packet;
import com.github.avalon.packet.schema.FunctionScheme;
import com.github.avalon.packet.schema.PacketStrategy;
import com.github.avalon.player.IPlayer;
import com.github.avalon.player.PlayerConnection;
import com.github.avalon.player.attributes.GameMode;
import com.github.avalon.player.attributes.PlayerAttributes;

/**
 * This packet send information's to the player about his abilities like invulnerability, if is
 * flying, if has allowed flying, creative mode, flying speed and field of view modifier that
 * modifies player's fov when flying or sprinting.
 *
 * <h3>Packet Strategy</h3>
 *
 * <ul>
 *   <li>1. Bit field of first 4 abilities are just booleans. (From <url>www.wiki.vg</url>
 *       Invulnerable 0x01 Flying 0x02 Allow Flying 0x04 Creative Mode (Instant Break) 0x08)
 *   <li>2. Player flight speed.
 *   <li>2. Player fov modifier.
 * </ul>
 *
 * @version 1.1
 */
@PacketRegister(
    operationCode = 0x30,
    protocolType = ProtocolType.PLAY,
    direction = PacketRegister.Direction.CLIENT)
public class PacketPlayerAbilities extends Packet<PacketPlayerAbilities> {

  public PacketStrategy strategy =
      new PacketStrategy(
          new FunctionScheme<>(DataType.BIT_FIELD, this::getFlags, this::setFlags),
          new FunctionScheme<>(DataType.FLOAT, this::getFlyingSpeed, this::setFlyingSpeed),
          new FunctionScheme<>(
              DataType.FLOAT, this::getFieldOfViewModifier, this::setFieldOfViewModifier));

  private BitField flags;

  private float flyingSpeed;
  private float fieldOfViewModifier;

  public PacketPlayerAbilities(IPlayer player) {
    PlayerAttributes attributes = player.getPlayerAttributes();

    flags =
        new BitField(
            attributes.isInvulnerable(),
            attributes.isFlying(),
            attributes.hasAllowedFlying(),
            attributes.getGameMode().equals(GameMode.CREATIVE));
    flyingSpeed = player.getPlayerAttributes().getFlyingSpeed();
    fieldOfViewModifier = player.getPlayerAttributes().getFieldOfViewModifier();
  }

  public PacketPlayerAbilities() {}

  @Override
  public boolean isAsync() {
    return false;
  }

  @Override
  public void handle(PlayerConnection connection, PacketPlayerAbilities packetPlayerAbilities) {
    throw new NotSupportedException("This type of operation is not valid for this packet.");
  }

  @Override
  public PacketStrategy getStrategy() {
    return strategy;
  }

  public BitField getFlags() {
    return flags;
  }

  public void setFlags(BitField flags) {
    this.flags = flags;
  }

  public float getFlyingSpeed() {
    return flyingSpeed;
  }

  public void setFlyingSpeed(float flyingSpeed) {
    this.flyingSpeed = flyingSpeed;
  }

  public float getFieldOfViewModifier() {
    return fieldOfViewModifier;
  }

  public void setFieldOfViewModifier(float fieldOfViewModifier) {
    this.fieldOfViewModifier = fieldOfViewModifier;
  }
}
