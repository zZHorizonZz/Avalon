package com.github.avalon.nbt.tag.array;

import com.github.avalon.nbt.tag.Tag;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

public abstract class TagArray<T> extends Tag {

  private T[] array;

  protected TagArray(byte identifier, String name, T[] array) {
    super(identifier, name);

    this.array = array;
  }

  protected TagArray(byte identifier, T[] array) {
    super(identifier);

    this.array = array;
  }

  protected TagArray(byte identifier) {
    super(identifier);
  }

  @Override
  public abstract void write(DataOutputStream dataOutputStream) throws IOException;

  @Override
  public String toPrettyString() {
    StringBuilder builder = new StringBuilder("TAG_ARRAY: \"" + getName() + '"');
    Arrays.stream(array).forEach(value -> builder.append(Character.LINE_SEPARATOR).append(value));
    return builder.toString();
  }

  public int length() {
    return array.length;
  }

  public T[] getArray() {
    return array;
  }

  public void setArray(T[] array) {
    this.array = array;
  }
}
