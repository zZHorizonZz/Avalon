package com.github.avalon.nbt.tag;

import com.github.avalon.nbt.tag.array.TagByteArray;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public enum TagType {
  TAG_END((byte) 0, TagEnd.class),
  TAG_BYTE((byte) 1, TagByte.class),
  TAG_SHORT((byte) 2, TagShort.class),
  TAG_INTEGER((byte) 3, TagInteger.class),
  TAG_LONG((byte) 4, TagLong.class),
  TAG_FLOAT((byte) 5, TagFloat.class),
  TAG_DOUBLE((byte) 6, TagDouble.class),
  TAG_BYTE_ARRAY((byte) 7, TagByteArray.class),
  TAG_STRING((byte) 8, TagString.class),
  TAG_LIST((byte) 9, TagList.class),
  TAG_COMPOUND((byte) 10, TagCompound.class),
  TAG_INTEGER_ARRAY((byte) 11, TagEnd.class),
  TAG_LONG_ARRAY((byte) 12, TagEnd.class);

  private final byte identifier;
  private final Class<? extends Tag> clazz;

  TagType(byte identifier, Class<? extends Tag> clazz) {
    this.identifier = identifier;
    this.clazz = clazz;
  }

  public static Tag getInstanceByIdentifier(byte identifier) {
    Class<? extends Tag> clazz = getByIdentifier(identifier);

    try {
      assert clazz != null;

      return clazz.getConstructor().newInstance();
    } catch (InstantiationException
        | IllegalAccessException
        | InvocationTargetException
        | NoSuchMethodException e) {
      e.printStackTrace();
      return new TagEnd();
    }
  }

  public static Class<? extends Tag> getByIdentifier(byte identifier) {
    if (identifier < 0 || identifier > 13) {
      return null;
    }

    return Arrays.stream(TagType.values())
        .filter(tag -> tag.getIdentifier() == identifier)
        .findFirst()
        .get()
        .getClazz();
  }

  public static byte getByClass(Class<? extends Tag> clazz) {
    return Arrays.stream(TagType.values())
        .filter(tag -> tag.getClazz().equals(clazz))
        .findFirst()
        .get()
        .getIdentifier();
  }

  public byte getIdentifier() {
    return identifier;
  }

  public Class<? extends Tag> getClazz() {
    return clazz;
  }
}
