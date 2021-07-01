package com.github.avalon.network;

public enum ProtocolType {
  HANDSHAKE(0x00),
  LOGIN(0x05),
  PLAY(0x5B),
  STATUS(0x02);

  private final int highestOperationCode;

  ProtocolType(int highestOperationCode) {
    this.highestOperationCode = highestOperationCode;
  }

  public int getHighestOperationCode() {
    return highestOperationCode;
  }
}
