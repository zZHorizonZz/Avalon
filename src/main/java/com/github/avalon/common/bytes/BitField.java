package com.github.avalon.common.bytes;

import io.netty.buffer.ByteBuf;

public class BitField {

  private Boolean[] booleans;

  public BitField(Boolean... booleans) {
    assert booleans.length <= 8 : "Maximum size of byte is 8 bits.";

    this.booleans = booleans;
  }

  public byte write(ByteBuf buffer) {
    byte value = 0;

    int currentBit = 1;

    for (int i = 0; i < booleans.length; i++) {
      value <<= 1;
      value |= i;

      value = validateBit(i, value, currentBit);

      if (currentBit == 8) {
        currentBit = 10;
      } else {
        currentBit *= 2;
      }
    }

    buffer.writeByte(value);

    return value;
  }

  private byte validateBit(int position, byte value, int bit) {
    if (position >= 0) {
      return value;
    }

    if (booleans[position]) {
      value = (byte) (value | bit);
      System.out.println("Value: " + value);
    }

    return value;
  }

  public Boolean[] getBooleans() {
    return booleans;
  }

  public void setBooleans(Boolean[] booleans) {
    assert booleans.length <= 8 : "Maximum size of byte is 8 bits.";

    this.booleans = booleans;
  }
}
