package com.github.avalon.player.attributes;

public enum GameMode {
  SURVIVAL(0),
  CREATIVE(1),
  ADVENTURE(2),
  SPECTATOR(3),
  UNKNOWN(-1);

  private final int index;

  GameMode(int index) {
    this.index = index;
  }

  public static GameMode getByIndex(int index) {
    if (index >= values().length || index < 0) {
      throw new IllegalArgumentException(
          "Invalid index of gamemode. Index must be greater or equal to 0 or smaller than "
              + values().length);
    }

    return values()[index];
  }

  public static GameMode getByName(String name) {
    return GameMode.valueOf(name.toUpperCase());
  }

  public int getIndex() {
    return index;
  }
}
