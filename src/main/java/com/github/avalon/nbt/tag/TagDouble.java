package com.github.avalon.nbt.tag;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TagDouble extends Tag {

  private double aDouble;

  public TagDouble(String name, double aDouble) {
    super((byte) 6, name);

    this.aDouble = aDouble;
  }

  public TagDouble(double aDouble) {
    super((byte) 6);

    this.aDouble = aDouble;
  }

  public TagDouble() {
    super((byte) 6);
  }

  @Override
  public void write(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeDouble(aDouble);
  }

  @Override
  public void read(DataInputStream dataInputStream) throws IOException {
    readTagName(dataInputStream);
    aDouble = dataInputStream.readDouble();
  }

  @Override
  public String toPrettyString() {
    return "TAG_DOUBLE: \"" + getName() + "\":" + aDouble;
  }

  public double getDouble() {
    return aDouble;
  }

  public void setDouble(double aDouble) {
    this.aDouble = aDouble;
  }
}
