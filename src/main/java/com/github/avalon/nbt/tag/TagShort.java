package com.github.avalon.nbt.tag;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TagShort extends Tag {

  private short aShort;

  public TagShort(String name, short aShort) {
    super((byte) 2, name);

    this.aShort = aShort;
  }

  public TagShort(short aShort) {
    super((byte) 2);

    this.aShort = aShort;
  }

  public TagShort() {
    super((byte) 2);
  }

  @Override
  public void write(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeShort(aShort);
  }

  @Override
  public void read(DataInputStream dataInputStream) throws IOException {
    readTagName(dataInputStream);
    aShort = dataInputStream.readShort();
  }

  @Override
  public String toPrettyString() {
    return "TAG_SHORT: \"" + getName() + "\":" + aShort;
  }

  public short getShort() {
    return aShort;
  }

  public void setShort(short aShort) {
    this.aShort = aShort;
  }
}
