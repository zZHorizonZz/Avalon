package com.github.avalon.common.math;

public class Vector2 {

  private final float x;
  private final float z;

  public Vector2(float x, float z) {
    this.x = x;
    this.z = z;
  }

  public Vector2 set(float x, float z) {
    return new Vector2(x, z);
  }

  public float getX() {
    return x;
  }

  public float getZ() {
    return z;
  }

  public int getXAsInteger() {
    return (int) x;
  }

  public int getZAsInteger() {
    return (int) z;
  }

  @Override
  public String toString() {
    return "Vector2: " + "X:" + x + ", Z: " + z;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Vector2 vector2 = (Vector2) o;

    if (Float.compare(vector2.getX(), getX()) != 0) return false;
    return Float.compare(vector2.getZ(), getZ()) == 0;
  }

  @Override
  public int hashCode() {
    int result = (getX() != +0.0f ? Float.floatToIntBits(getX()) : 0);
    result = 31 * result + (getZ() != +0.0f ? Float.floatToIntBits(getZ()) : 0);
    return result;
  }
}
