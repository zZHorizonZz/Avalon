package com.github.avalon.nbt.stream;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

public class NamedBinaryInputStream extends DataInputStream {

  public NamedBinaryInputStream(InputStream in) throws IOException {
    super(new GZIPInputStream(in));
  }
}
