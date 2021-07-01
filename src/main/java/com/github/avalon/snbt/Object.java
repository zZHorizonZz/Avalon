package com.github.avalon.snbt;

public abstract class Object {

  private String name;
  private int beginIndex;
  private int endIndex;

  public Object(String name) {
    this.name = name;
  }

  public Object(String name, int beginIndex) {
    this.name = name;
    this.beginIndex = beginIndex;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getBeginIndex() {
    return beginIndex;
  }

  public void setBeginIndex(int beginIndex) {
    this.beginIndex = beginIndex;
  }

  public int getEndIndex() {
    return endIndex;
  }

  public void setEndIndex(int endIndex) {
    this.endIndex = endIndex;
  }

  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    return getName() != null ? getName().equals(object.getName()) : object.getName() == null;
  }

  @Override
  public int hashCode() {
    int result = getName() != null ? getName().hashCode() : 0;
    result = 31 * result + getBeginIndex();
    result = 31 * result + getEndIndex();
    return result;
  }
}
