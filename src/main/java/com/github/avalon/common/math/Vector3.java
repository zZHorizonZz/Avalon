package com.github.avalon.common.math;

public class Vector3 {

  private final double x;
  private final double y;
  private final double z;

  public Vector3(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public Vector3 set(double x, double y, double z) {
    return new Vector3(x, y, z);
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public double getZ() {
    return z;
  }

  public int getXAsInteger() {
    return (int) Math.round(x);
  }

  public int getYAsInteger() {
    return (int) Math.round(y);
  }

  public int getZAsInteger() {
    return (int) Math.round(z);
  }

  @Override
  public String toString() {
    return "Vector3: " + "X:" + x + ",Y: " + y + ", Z: " + z;
  }
}
