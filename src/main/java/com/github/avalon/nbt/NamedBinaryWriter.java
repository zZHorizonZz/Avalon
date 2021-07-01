package com.github.avalon.nbt;

import com.github.avalon.nbt.serialization.NamedBinarySerializer;
import com.github.avalon.nbt.stream.NamedBinaryOutputStream;
import com.github.avalon.nbt.tag.TagCompound;

import java.io.IOException;

public class NamedBinaryWriter {

  private final NamedBinaryOutputStream outputStream;

  public NamedBinaryWriter(NamedBinaryOutputStream outputStream) {
    this.outputStream = outputStream;
  }

  public void write(TagCompound compound) {
    if (compound == null) {
      throw new NullPointerException("Compound can not be null!");
    }
    try {
      compound.writeNamedTag(outputStream);
    } catch (IOException exception) {
      exception.printStackTrace();
    }
  }

  public void write(NamedBinarySerializer serialization, Object object) {
    write((TagCompound) serialization.serialize(object));
  }
}
