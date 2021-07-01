package com.github.avalon.common.system;

public class UtilMath {

  public static short toDelta(double a, double b) {
    System.out.println("A -> " + a);
    System.out.println("B -> " + b);
    double delta = ((a * 32 - b * 32) * 128);
    if (delta > Short.MAX_VALUE || delta < Short.MIN_VALUE)
      throw new IllegalStateException(
          "Delta can not be greater or smaller than SHORT (" + Short.MAX_VALUE + ") current result is " + delta);

    return (short) ((a * 32 - b * 32) * 128);
  }
}
