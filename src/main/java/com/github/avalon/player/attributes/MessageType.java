package com.github.avalon.player.attributes;

public enum MessageType {
  CHAT(0),
  SYSTEM_INFO(1),
  GAME_INFO(2);

  private final byte type;

  MessageType(byte type) {
    this.type = type;
  }

  MessageType(int type) {
    this((byte) type);
  }

  public byte getType() {
    return type;
  }
}
