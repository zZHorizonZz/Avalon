package com.github.avalon.player.attributes;

import java.util.Arrays;
import java.util.Optional;

/**
 * Actions are used for {@link com.github.avalon.packet.packet.play.PacketEntityAction} based
 * on the <url>www.wiki.vg</url>. We can catch these action with PacketHandlers annotation.
 *
 * @author Horizon
 * @version 1.0
 */
public enum Action {
  START_SNEAKING(0),
  STOP_SNEAKING(1),
  LEAVE_BED(2),
  START_SPRINTING(3),
  STOP_SPRINTING(4),
  START_JUMP_HORSE(5),
  STOP_JUMP_HORSE(6),
  OPEN_HORSE_INVENTORY(7),
  START_ELYTRA(8),
  UNKNOWN(-1);

  private final int identifier;

  Action(int identifier) {
    this.identifier = identifier;
  }

  /**
   * Returns the action based on it's identifier. If any action with specified identifier can no be
   * found then {@code Action.UNKNOWN} is returned. Or if identifier is invalid then {@code
   * Action.UNKNOWN} based on this code {@code if (identifier < 0 || identifier >= values().length)
   * return Action.UNKNOWN;}
   *
   * @since 1.1
   * @param identifier Identifier should be valid but if is not then {@code Action.UNKNOWN} is
   *     returned.
   * @return Action based on identifier.
   */
  public static Action getByIdentifier(int identifier) {
    if (identifier < 0 || identifier >= values().length) return Action.UNKNOWN;

    Optional<Action> optional =
        Arrays.stream(Action.values())
            .filter(action -> action.getIdentifier() == identifier)
            .findFirst();
    return optional.orElse(Action.UNKNOWN);
  }

  public int getIdentifier() {
    return identifier;
  }
}
