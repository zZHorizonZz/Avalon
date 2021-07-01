package com.github.avalon.nbt.tag.array;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TagByteArray extends TagArray<Byte> {

  public TagByteArray(String name, Byte[] array) {
    super((byte) 7, name, array);
  }

  public TagByteArray(Byte[] array) {
    super((byte) 7, array);
  }

  public TagByteArray() {
    super((byte) 7);
  }

  @Override
  public void write(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeInt(getArray().length);
    for (int i = 0; i < getArray().length; i++) {
      dataOutputStream.writeByte(getArray()[i]);
    }
  }

  @Override
  public void read(DataInputStream dataInputStream) throws IOException {
    readTagName(dataInputStream);
    int size = dataInputStream.readInt();
    setArray(new Byte[size]);

    for (int i = 0; i < size; i++) {
      getArray()[i] = dataInputStream.readByte();
    }
  }
}
