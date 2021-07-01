package com.github.avalon.snbt.object;

public class DoubleTag extends Tag<Double> {

  public DoubleTag(String name, double value) {
    super(name, value);
  }

  public DoubleTag(String name, double value, int beginIndex) {
    this(name, value, beginIndex, -1);
  }

  public DoubleTag(String name, double value, int beginIndex, int endIndex) {
    super(name, value, beginIndex);

    if (endIndex != -1) setEndIndex(endIndex);
  }

  @Override
  public String toString() {
    return getValue() + "d";
  }
}
