package com.github.avalon.common.system;

public final class UtilMath {

  public static short toDelta(double a, double b) {
    double delta = ((a * 32 - b * 32) * 128);
    assert !(delta > Short.MAX_VALUE) && !(delta < Short.MIN_VALUE)
        : "Delta can not be greater or smaller than SHORT ("
            + Short.MAX_VALUE
            + ") current result is "
            + delta;

    return (short) ((a * 32 - b * 32) * 128);
  }
}
