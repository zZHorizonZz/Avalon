package com.github.avalon.nbt.tag;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TagInteger extends Tag {

  private int integer;

  public TagInteger(String name, int integer) {
    super((byte) 3, name);

    this.integer = integer;
  }

  public TagInteger(int integer) {
    super((byte) 3);

    this.integer = integer;
  }

  public TagInteger() {
    super((byte) 3);
  }

  @Override
  public void write(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeInt(integer);
  }

  @Override
  public void read(DataInputStream dataInputStream) throws IOException {
    readTagName(dataInputStream);
    integer = dataInputStream.readInt();
  }

  @Override
  public String toPrettyString() {
    return "TAG_INTEGER: \"" + getName() + "\":" + integer;
  }

  public int getInteger() {
    return integer;
  }

  public void setInteger(int integer) {
    this.integer = integer;
  }
}
