package com.github.avalon.snbt.object;

public class BooleanTag extends Tag<Boolean> {

  public BooleanTag(String name, boolean value) {
    super(name, value);
  }

  public BooleanTag(String name, boolean value, int beginIndex) {
    this(name, value, beginIndex, -1);
  }

  public BooleanTag(String name, boolean value, int beginIndex, int endIndex) {
    super(name, value, beginIndex);

    if (endIndex != -1) setEndIndex(endIndex);
  }

  @Override
  public String toString() {
    return getValue() ? "1b" : "0b";
  }
}
