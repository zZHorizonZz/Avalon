package com.github.avalon.common.data;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Array is just an object that represents some sort of {@link Set} that will be used in {@link
 * com.github.avalon.packet.packet.Packet} and its automatic encoding and decoding these {@link
 * Set}.
 *
 * @version 1.0
 * @param <T> Type of object that will contain this array.
 */
public class Array<T> {

  private final DataType<T> type;
  private List<T> set;

  public Array(DataType<T> type) {
    this.type = type;
  }

  public Array(DataType<T> type, List<T> set) {
    this.type = type;
    this.set = set;
  }

  public Array(DataType<T> type, T[] array) {
    this.type = type;
    set = Arrays.stream(array).collect(Collectors.toList());
  }

  public Array(DataType<T> type, Set<T> list) {
    this.type = type;
    set = new ArrayList<>(list);
  }

  public DataType<T> getType() {
    return type;
  }

  /**
   * Returns the set of objects. Under some circumstances this can be null.
   *
   * @return Set of objects.
   */
  @Nullable
  public List<?> get() {
    return set;
  }

  public void set(List<T> set) {
    this.set = set;
  }

  public void setArray(T[] array) {
    set = Arrays.stream(array).collect(Collectors.toList());
  }

  public void setArray(List<T> list) {
    set = new ArrayList<>(list);
  }

  public int getSize() {
    return set.size();
  }
}
