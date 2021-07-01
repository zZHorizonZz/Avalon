package com.github.avalon.nbt;

import com.github.avalon.nbt.stream.NamedBinaryInputStream;
import com.github.avalon.nbt.tag.TagCompound;
import com.github.avalon.nbt.tag.TagType;

import javax.annotation.Nullable;
import java.io.IOException;

public class NamedBinaryReader {

  private final NamedBinaryInputStream inputStream;

  public NamedBinaryReader(NamedBinaryInputStream inputStream) {
    this.inputStream = inputStream;
  }

  @Nullable
  public TagCompound read() {
    TagCompound compound = null;
    try {
      compound = (TagCompound) TagType.getInstanceByIdentifier(inputStream.readByte());
      compound.read(inputStream);
    } catch (IOException exception) {
      exception.printStackTrace();
    }

    return compound;
  }
}
