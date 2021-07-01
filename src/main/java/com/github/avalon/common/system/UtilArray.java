package com.github.avalon.common.system;

public final class UtilArray {

  public static byte[] toPrimitives(Byte[] oBytes) {

    byte[] bytes = new byte[oBytes.length];
    for (int i = 0; i < oBytes.length; i++) {
      bytes[i] = oBytes[i];
    }
    return bytes;
  }

  Byte[] toObjects(byte[] bytesPrim) {

    Byte[] bytes = new Byte[bytesPrim.length];
    int i = 0;
    for (byte b : bytesPrim) bytes[i++] = b; //Autoboxing
    return bytes;

  }
}
