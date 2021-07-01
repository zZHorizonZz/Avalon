package com.github.avalon.snbt.object;

public class StringTag extends Tag<String> {

  public StringTag(String name, String value) {
    super(name, value);
  }

  public StringTag(String name, int beginIndex) {
    super(name, "", beginIndex);
  }

  public StringTag(String name, String value, int beginIndex) {
    this(name, value, beginIndex, -1);
  }

  public StringTag(String name, String value, int beginIndex, int endIndex) {
    super(name, value, beginIndex);

    if (endIndex != -1) setEndIndex(endIndex);
  }

  @Override
  public String toString() {
    return "\"" + getValue() + "\"";
  }
}
