package com.github.avalon.player.attributes;

public enum ChatMode {
  ENABLED,
  COMMANDS_ONLY,
  HIDDEN;

  public static ChatMode getChatMode(int value) {
    if (value < 0 || value > 2) return ChatMode.ENABLED;

    switch (value) {
      case 0:
        return ChatMode.ENABLED;
      case 1:
        return ChatMode.COMMANDS_ONLY;
      case 2:
        return ChatMode.HIDDEN;
    }

    return ChatMode.ENABLED;
  }
}
