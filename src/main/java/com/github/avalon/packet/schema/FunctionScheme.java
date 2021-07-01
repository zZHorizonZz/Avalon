package com.github.avalon.packet.schema;

import com.github.avalon.common.data.DataType;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Function Scheme represents a getter/setter function that are usually used in {@link
 * PacketStrategy}.
 *
 * @author Horizon
 * @version 1.0
 * @param <T> Type of the value returned by {@code get()} or accepted by {@code set()}.
 */
public class FunctionScheme<T> {

  private final DataType type;

  private final Supplier<T> get;
  private final Consumer<T> set;

  public FunctionScheme(DataType type, Supplier<T> get, Consumer<T> set) {
    this.type = type;

    this.get = get;
    this.set = set;
  }

  public DataType getType() {
    return type;
  }

  public Supplier<T> getFunction() {
    return get;
  }

  public Consumer<T> setFunction() {
    return set;
  }

  public T get() {
    return get.get();
  }

  public void set(Object value) {
    set.accept((T) value);
  }
}
