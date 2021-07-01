package com.github.avalon.snbt;

import com.github.avalon.snbt.parent.Node;
import com.github.avalon.snbt.parent.NodeSingle;
import com.github.avalon.snbt.reader.DataReader;
import com.github.avalon.snbt.serialization.NamedBinaryTagSerializer;
import com.github.avalon.snbt.writer.DataWriter;
import io.netty.util.internal.StringUtil;

/**
 * Nbt builder to read or create nbt.
 *
 * @version 1.0
 * @author Horizon
 */
public class NamedBinaryTagBuilder {

  /**
   * Convert a nbt serialize to nbt string format.
   *
   * @param parent Nbt serialize to convert.
   * @return Nbt string format.
   */
  public String toNamedBinaryTag(NodeSingle parent) {
    DataWriter dataWriter = new DataWriter();

    if (parent == null) throw new IllegalArgumentException("Data can not be null or empty!");

    return dataWriter.writeParentSingle(parent, new StringBuilder()).toString();
  }

  /**
   * Convert the nbt string to nbt serialize.
   *
   * @param nbt Nbt string to convert from.
   * @return Nbt parent serialize.
   */
  public Node fromNamedBinaryTag(String nbt) {
    DataReader dataReader = new DataReader();

    if (StringUtil.isNullOrEmpty(nbt))
      throw new IllegalArgumentException("Data can not be null or empty!");

    char[] charset = nbt.toCharArray();
    return dataReader.findParent("", charset, 0);
  }

  public <T extends NamedBinaryTagSerializer> String toNamedBinaryTag(T serializer, java.lang.Object objectToSerialize) {
    return toNamedBinaryTag((NodeSingle) serializer.serialize(objectToSerialize));
  }
}
