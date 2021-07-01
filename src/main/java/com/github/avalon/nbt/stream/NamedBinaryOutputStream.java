package com.github.avalon.nbt.stream;

import java.io.DataOutputStream;
import java.io.OutputStream;

public class NamedBinaryOutputStream extends DataOutputStream {

  public NamedBinaryOutputStream(OutputStream out) {
    super(out);
  }
}
