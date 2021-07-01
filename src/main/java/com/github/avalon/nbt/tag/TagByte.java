package com.github.avalon.nbt.tag;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TagByte extends Tag {

  private byte aByte;

  public TagByte(String name, byte aByte) {
    super((byte) 1, name);

    this.aByte = aByte;
  }

  public TagByte(byte aByte) {
    super((byte) 1, null);

    this.aByte = aByte;
  }

  public TagByte(String name, boolean bool) {
    super((byte) 1, name);

    aByte = (byte) (bool ? 1 : 0);
  }

  public TagByte(boolean bool) {
    super((byte) 1, null);

    aByte = (byte) (bool ? 1 : 0);
  }

  public TagByte() {
    super((byte) 1, null);
  }

  @Override
  public void write(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeByte(aByte);
  }

  @Override
  public void read(DataInputStream dataInputStream) throws IOException {
    readTagName(dataInputStream);
    aByte = dataInputStream.readByte();
  }

  @Override
  public String toPrettyString() {
    return "TAG_BYTE: \"" + getName() + "\":" + aByte;
  }

  public byte getByte() {
    return aByte;
  }

  public void setByte(byte aByte) {
    this.aByte = aByte;
  }

  public boolean getBoolean() {
    return aByte == 1;
  }

  public void setBoolean(boolean bool) {
    aByte = (byte) (bool ? 1 : 0);
  }
}
