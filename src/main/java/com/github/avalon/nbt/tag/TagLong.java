package com.github.avalon.nbt.tag;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TagLong extends Tag {

  private long aLong;

  public TagLong(String name, long aLong) {
    super((byte) 4, name);

    this.aLong = aLong;
  }

  public TagLong(long aLong) {
    super((byte) 4);

    this.aLong = aLong;
  }

  public TagLong() {
    super((byte) 4);
  }

  @Override
  public void write(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeLong(aLong);
  }

  @Override
  public void read(DataInputStream dataInputStream) throws IOException {
    readTagName(dataInputStream);
    aLong = dataInputStream.readLong();
  }

  @Override
  public String toPrettyString() {
    return "TAG_LONG: \"" + getName() + "\":" + aLong;
  }

  public long getLong() {
    return aLong;
  }

  public void setLong(long aLong) {
    this.aLong = aLong;
  }
}
