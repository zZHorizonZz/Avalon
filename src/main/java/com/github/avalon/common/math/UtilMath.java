package com.github.avalon.common.math;

public final class UtilMath {

  /**
   * Creates new {@link Vector3} with smaller values from vectorA and vectorB.
   *
   * @param vectorA First vector.
   * @param vectorB Second vector.
   * @return New vector with minimum values.
   */
  public static Vector3 min(Vector3 vectorA, Vector3 vectorB) {
    return new Vector3(
        Math.min(vectorA.getXAsInteger(), vectorB.getXAsInteger()),
        Math.min(vectorA.getYAsInteger(), vectorB.getYAsInteger()),
        Math.min(vectorA.getZAsInteger(), vectorB.getZAsInteger()));
  }

  /**
   * Creates new {@link Vector3} with highest values from vectorA and vectorB.
   *
   * @param vectorA First vector.
   * @param vectorB Second vector.
   * @return New vector with maximum values.
   */
  public static Vector3 max(Vector3 vectorA, Vector3 vectorB) {
    return new Vector3(
        Math.max(vectorA.getXAsInteger(), vectorB.getXAsInteger()),
        Math.max(vectorA.getYAsInteger(), vectorB.getYAsInteger()),
        Math.max(vectorA.getZAsInteger(), vectorB.getZAsInteger()));
  }
}
