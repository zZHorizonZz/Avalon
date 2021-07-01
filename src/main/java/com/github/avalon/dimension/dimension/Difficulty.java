package com.github.avalon.dimension.dimension;

public enum Difficulty {
  PEACEFUL(0),
  EASY(1),
  NORMAL(2),
  HARD(3);

  private final int identifier;

  Difficulty(int identifier) {
    this.identifier = identifier;
  }

  public static Difficulty getByIdentifier(int index) {
    if (index >= values().length || index < 0) {
      throw new IllegalArgumentException(
          "Invalid identifier of gamemode. Index must be greater or equal to 0 or smaller than "
              + values().length);
    }

    return values()[index];
  }

  public int getIdentifier() {
    return identifier;
  }
}
