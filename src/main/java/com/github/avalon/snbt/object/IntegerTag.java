package com.github.avalon.snbt.object;

public class IntegerTag extends Tag<Integer> {

  public IntegerTag(String name, int value) {
    super(name, value);
  }

  public IntegerTag(String name, int value, int beginIndex) {
    this(name, value, beginIndex, -1);
  }

  public IntegerTag(String name, int value, int beginIndex, int endIndex) {
    super(name, value, beginIndex);

    if (endIndex != -1) setEndIndex(endIndex);
  }

  @Override
  public String toString() {
    return getValue().toString();
  }
}
