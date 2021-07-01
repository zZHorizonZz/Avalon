package com.github.avalon.snbt.object;

public abstract class Tag<T> extends com.github.avalon.snbt.Object {

  private String name;
  private T value;

  public Tag(String name, T value) {
    super(name);
    this.name = name;
    this.value = value;
  }

  public Tag(String name, T value, int beginIndex) {
    super(name, beginIndex);
    this.name = name;
    this.value = value;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  public T getValue() {
    return value;
  }

  public void setValue(T value) {
    this.value = value;
  }

  @Override
  public abstract String toString();
}
