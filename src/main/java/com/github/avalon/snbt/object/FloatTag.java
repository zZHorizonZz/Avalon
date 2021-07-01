package com.github.avalon.snbt.object;

public class FloatTag extends Tag<Float> {

  public FloatTag(String name, float value) {
    super(name, value);
  }

  public FloatTag(String name, float value, int beginIndex) {
    this(name, value, beginIndex, -1);
  }

  public FloatTag(String name, float value, int beginIndex, int endIndex) {
    super(name, value, beginIndex);

    if (endIndex != -1) setEndIndex(endIndex);
  }

  @Override
  public String toString() {
    return getValue() + "f";
  }
}
