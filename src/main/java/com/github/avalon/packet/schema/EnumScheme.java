package com.github.avalon.packet.schema;

import com.github.avalon.common.data.DataType;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class EnumScheme<T extends Enum<?>> extends FunctionScheme<T> {

  private final Class<T> clazz;

  public EnumScheme(Class<T> clazz, Supplier<T> get, Consumer<T> set) {
    super(DataType.ENUM, get, set);

    this.clazz = clazz;
  }

  public Class<T> getClazz() {
    return clazz;
  }
}
