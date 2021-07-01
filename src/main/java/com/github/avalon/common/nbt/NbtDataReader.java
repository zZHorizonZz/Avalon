package com.github.avalon.common.nbt;

import io.netty.util.internal.StringUtil;

public class NbtDataReader {

  public static boolean deserialize(String nbt) {
    if (StringUtil.isNullOrEmpty(nbt)) return false;

    if (!nbt.equalsIgnoreCase("1b") && !nbt.equalsIgnoreCase("0b")) return false;

    return nbt.equalsIgnoreCase("1b");
  }

  public static String serialize(boolean bool) {
    return bool ? "1b" : "0b";
  }
}
