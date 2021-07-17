package com.github.avalon.data;

import com.github.avalon.common.math.Vector3;
import com.github.avalon.dimension.chunk.IChunk;
import com.github.avalon.dimension.dimension.Dimension;
import com.google.common.math.IntMath;

/**
 * This class provides an implementation of transform system (Location system in other words). This
 * transform system includes some mathematical calculation methods for better handling of
 * coordinates.
 *
 * @version 1.0
 */
public class Transform {

  private final Dimension dimension;

  private final double x;
  private final double y;
  private final double z;

  private float yaw;
  private float pitch;

  public Transform(Dimension dimension, long position) {
    this.dimension = dimension;

    x = position >> 38;
    y = position & 0xFFF;
    z = (position << 26 >> 38);
  }

  public Transform(Dimension dimension, double x, double y, double z) {
    this.dimension = dimension;
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public Transform(Dimension dimension, double x, double y, double z, float yaw, float pitch) {
    this.dimension = dimension;
    this.x = x;
    this.y = y;
    this.z = z;
    this.yaw = yaw;
    this.pitch = pitch;
  }

  public Transform(Dimension dimension, Transform transform) {
    this.dimension = dimension;
    x = transform.getX();
    y = transform.getY();
    z = transform.getZ();
    yaw = transform.getYaw();
    pitch = transform.getPitch();
  }

  public Dimension getDimension() {
    return dimension;
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

  public int getBlockX() {
    return (int) Math.round(x);
  }

  public int getBlockY() {
    return (int) Math.round(y);
  }

  public int getBlockZ() {
    return (int) Math.round(z);
  }

  /**
   * This method is based on calculation from <url>https://wiki.vg/Data_types#Position</url>
   *
   * @return Long of the position.
   */
  public long getLong() {
    return (((long) getBlockX() & 0x3FFFFFF) << 38)
        | (((long) getBlockZ() & 0x3FFFFFF) << 12)
        | ((long) getBlockY() & 0xFFF);
  }

  /**
   * Returns location of block in chunk section converted to integer position.
   *
   * @return Location integer.
   */
  public int getChunkSectionLocation() {
    return getBlockX() % IChunk.CHUNK_SECTION_SIZE
        | (getBlockY() % IChunk.CHUNK_SECTION_SIZE << 0x08)
        | (getBlockZ() % IChunk.CHUNK_SECTION_SIZE << 0x10);
  }

  public Vector3 toVector() {
    return new Vector3(x, y, z);
  }

  public float getYaw() {
    return yaw;
  }

  public float getPitch() {
    return pitch;
  }

  public IChunk getChunk() {
    return getDimension()
        .getChunkAt(
            getBlockX() / (IChunk.CHUNK_SECTION_SIZE), getBlockZ() / (IChunk.CHUNK_SECTION_SIZE));
  }

  public Transform setX(double x) {
    return new Transform(dimension, x, y, z, yaw, pitch);
  }

  public Transform setY(double y) {
    return new Transform(dimension, x, y, z, yaw, pitch);
  }

  public Transform setZ(double z) {
    return new Transform(dimension, x, y, z, yaw, pitch);
  }

  public Transform setYaw(float yaw) {
    return new Transform(dimension, x, y, z, yaw, pitch);
  }

  public Transform setPitch(float pitch) {
    return new Transform(dimension, x, y, z, yaw, pitch);
  }

  public Transform setTransform(double x, double y, double z) {
    return new Transform(dimension, x, y, z, yaw, pitch);
  }

  public Transform setTransform(double x, double y, double z, float yaw, float pitch) {
    return new Transform(dimension, x, y, z, yaw, pitch);
  }

  public Transform subtract(Transform transform) {
    return new Transform(
        dimension, x - transform.getX(), y - transform.getY(), z - transform.getZ(), yaw, pitch);
  }

  @Override
  public String toString() {
    return "Transform: "
        + " Dimension: "
        + dimension
        + ", X: "
        + x
        + ", Y:"
        + y
        + ", Z: "
        + z
        + ", Yaw: "
        + yaw
        + ", Pitch: "
        + pitch;
  }
}
