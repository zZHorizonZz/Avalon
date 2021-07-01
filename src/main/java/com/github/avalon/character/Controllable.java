package com.github.avalon.character;

import com.github.avalon.player.IPlayer;

/**
 * This interface defines methods that can be used to control the behavior of the character by
 * client. In default, this is used to control the player character. But we can use this for example
 * to control behavior of the zombie character.
 *
 * @author Horizon
 * @version 1.0
 */
public interface Controllable {

  IPlayer getController();

  void setController(IPlayer player);

  default void synchronize() {

  }

  default void attack() {

  }
}
