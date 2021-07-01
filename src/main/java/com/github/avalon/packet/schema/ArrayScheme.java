package com.github.avalon.packet.schema;

import com.github.avalon.common.data.DataType;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ArrayScheme<T> extends FunctionScheme<List<T>> {

  private final DataType<T> arrayType;

  public ArrayScheme(DataType<T> arrayType, Supplier<List<T>> get, Consumer<List<T>> set) {
    super(DataType.ARRAY, get, set);

    this.arrayType = arrayType;
  }

  public DataType<T> getArrayType() {
    return arrayType;
  }
}
