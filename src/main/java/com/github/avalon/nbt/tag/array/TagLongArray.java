package com.github.avalon.nbt.tag.array;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TagLongArray extends TagArray<Long> {

  public TagLongArray(String name, Long[] array) {
    super((byte) 12, name, array);
  }

  public TagLongArray(Long[] array) {
    super((byte) 12, array);
  }

  public TagLongArray() {
    super((byte) 12);
  }

  @Override
  public void write(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeInt(length());
    for (int i = 0; i < length(); i++) {
      dataOutputStream.writeLong(getArray()[i]);
    }
  }

  @Override
  public void read(DataInputStream dataInputStream) throws IOException {
    readTagName(dataInputStream);
    int size = dataInputStream.readInt();
    setArray(new Long[size]);

    for (int i = 0; i < size; i++) {
      getArray()[i] = dataInputStream.readLong();
    }
  }
}
