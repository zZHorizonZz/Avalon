package com.github.avalon.nbt.tag.array;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TagIntegerArray extends TagArray<Integer> {

  public TagIntegerArray(String name, Integer[] array) {
    super((byte) 11, name, array);
  }

  public TagIntegerArray(Integer[] array) {
    super((byte) 11, array);
  }

  public TagIntegerArray() {
    super((byte) 11);
  }

  @Override
  public void write(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeInt(getArray().length);
    for (int i = 0; i < getArray().length; i++) {
      dataOutputStream.writeInt(getArray()[i]);
    }
  }

  @Override
  public void read(DataInputStream dataInputStream) throws IOException {
    readTagName(dataInputStream);
    int size = dataInputStream.readInt();
    setArray(new Integer[size]);

    for (int i = 0; i < size; i++) {
      getArray()[i] = dataInputStream.readInt();
    }
  }
}
