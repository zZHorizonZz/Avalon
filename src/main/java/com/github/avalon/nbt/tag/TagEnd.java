package com.github.avalon.nbt.tag;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TagEnd extends Tag {

  public TagEnd() {
    super((byte) 0);
  }

  @Override
  public void write(DataOutputStream dataOutputStream) throws IOException {
    // Operation does not have any payload.
  }

  @Override
  public void read(DataInputStream dataInputStream) throws IOException {}

  @Override
  public String toPrettyString() {
    return "TAG_END";
  }
}
