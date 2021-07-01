package com.github.avalon.nbt.tag;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TagString extends Tag {

  private String string;

  public TagString(String name, String string) {
    super((byte) 8, name);

    this.string = string;
  }

  public TagString(String string) {
    super((byte) 8);

    this.string = string;
  }

  public TagString() {
    super((byte) 8);
  }

  @Override
  public void write(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeUTF(string);
  }

  @Override
  public void read(DataInputStream dataInputStream) throws IOException {
    readTagName(dataInputStream);
    string = dataInputStream.readUTF();
  }

  @Override
  public String toPrettyString() {
    return "TAG_STRING: \"" + getName() + "\":" + string;
  }

  public String getString() {
    return string;
  }

  public void setString(String string) {
    this.string = string;
  }
}
