package com.github.avalon.nbt.tag;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TagFloat extends Tag {

  private float aFloat;

  public TagFloat(String name, float aFloat) {
    super((byte) 5, name);

    this.aFloat = aFloat;
  }

  public TagFloat(float aFloat) {
    super((byte) 5);

    this.aFloat = aFloat;
  }

  public TagFloat() {
    super((byte) 5);
  }

  @Override
  public void write(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeFloat(aFloat);
  }

  @Override
  public void read(DataInputStream dataInputStream) throws IOException {
    readTagName(dataInputStream);
    aFloat = dataInputStream.readFloat();
  }

  @Override
  public String toPrettyString() {
    return "TAG_FLOAT: \"" + getName() + "\":" + aFloat;
  }

  public float getFloat() {
    return aFloat;
  }

  public void setFloat(float aFloat) {
    this.aFloat = aFloat;
  }
}
