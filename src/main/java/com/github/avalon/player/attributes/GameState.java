package com.github.avalon.player.attributes;

/**
 * Game states are states of games that player can see for example raining, game win screen etc.
 * More info at <url>https://wiki.vg/Protocol#Change_Game_State</url>.
 *
 * @version 1.0
 */
public enum GameState {
  NO_RESPAWN_BLOCK_AVAILABLE(0),
  END_RAINING(0),
  BEGIN_RAINING(0),
  CHANGE_GAMEMODE(3),
  WIN_GAME(1),
  DEMO_EVENT(104),
  ARROW_HIT_PLAYER(0),
  RAIN_LEVEL_CHANGE(20),
  THUNDER_LEVEL_CHANGE(20),
  PLAY_PUFFERFISH_STING_SOUND(0),
  PLAY_ELDER_GUARDIAN_APPEARANCE(0),
  ENABLE_RESPAWN_SCREEN(1);

  private final int maximumState;

  GameState(int maximumState) {
    this.maximumState = maximumState;
  }

  public int getMaximumState() {
    return maximumState;
  }

  /**
   * Super secret events.
   *
   * @version 18.11.2.0.1.1
   */
  public enum DemoEvent {
    WELCOME_TO_DEMO(0),
    TELL_MOVEMENT_CONTROLS(101),
    TELL_JUMP_CONTROLS(102),
    TELL_INVENTORY_CONTROLS(103),
    TELL_DEMO_OVER(104);

    private final int index;

    DemoEvent(int index) {
      this.index = index;
    }

    public static DemoEvent getByIndex(int index) {
      switch (index) {
        case 101:
          return DemoEvent.TELL_MOVEMENT_CONTROLS;
        case 102:
          return DemoEvent.TELL_JUMP_CONTROLS;
        case 103:
          return DemoEvent.TELL_INVENTORY_CONTROLS;
        case 104:
          return DemoEvent.TELL_DEMO_OVER;
        default:
          return DemoEvent.WELCOME_TO_DEMO;
      }
    }

    public static DemoEvent getByName(String name) {
      return DemoEvent.valueOf(name.toUpperCase());
    }

    public int getIndex() {
      return index;
    }
  }
}
