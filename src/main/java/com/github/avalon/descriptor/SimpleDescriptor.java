package com.github.avalon.descriptor;

import io.netty.buffer.ByteBuf;

public abstract class SimpleDescriptor<T> extends Descriptor<T> {

  public SimpleDescriptor(String name) {
    super(name);
  }

  public abstract void write(ByteBuf byteBuf);
}
