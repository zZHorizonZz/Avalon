package com.github.avalon.chat.message;

import javax.annotation.Nullable;

public enum ChatEffect {
  MAGIC("obfuscated"),
  BOLD("bold"),
  STRIKETHROUGH("strikethrough"),
  UNDERLINE("underline"),
  ITALIC("italic"),
  RESET("reset");

  private final String name;

  ChatEffect(String name) {
    this.name = name;
  }

  @Nullable
  public static ChatEffect getByName(String name) {
    for (ChatEffect effect : values()) {
      if (effect.getName().equalsIgnoreCase(name)) {
        return effect;
      }
    }
    return null;
  }

  public String getName() {
    return name;
  }
}
