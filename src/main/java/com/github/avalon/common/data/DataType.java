package com.github.avalon.common.data;

import com.github.avalon.chat.message.Message;
import com.github.avalon.common.bytes.BitField;
import com.github.avalon.item.Item;
import com.github.avalon.nbt.tag.TagCompound;
import com.github.avalon.network.PacketBuffer;
import com.github.avalon.resource.data.ResourceIdentifier;

import javax.annotation.Nullable;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Data Type are types of values that are used in minecraft packets. These type are defined in
 * <url>www.wiki.vg</url>
 *
 * @version 1.0
 */
public class DataType<T> {
  public static final DataType<Boolean> BOOLEAN;
  public static final DataType<Byte> BYTE;
  public static final DataType<Byte> UNSIGNED_BYTE;
  public static final DataType<Short> SHORT;
  public static final DataType<Short> UNSIGNED_SHORT;
  public static final DataType<Integer> INTEGER;
  public static final DataType<Long> LONG;
  public static final DataType<Float> FLOAT;
  public static final DataType<Double> DOUBLE;
  public static final DataType<String> STRING;
  public static final DataType<Message> CHAT;
  public static final DataType<ResourceIdentifier> IDENTIFIER;
  public static final DataType<Integer> VARINT;
  public static final DataType VARLONG = null;
  public static final DataType ENTITY_METADATA = null;
  public static final DataType<Item> ITEM;
  public static final DataType<TagCompound> NBT_TAG;
  public static final DataType<Long> POSITION; // TODO: Maybe create vector?
  public static final DataType<Float> ANGLE;
  public static final DataType<java.util.UUID> UUID;
  public static final DataType<Array<?>> ARRAY;
  public static final DataType<Enum<?>> ENUM;
  public static final DataType<byte[]> BYTE_ARRAY;
  public static final DataType<BitField> BIT_FIELD;

  static {
    BOOLEAN = new DataType<>(PacketBuffer::writeBoolean, PacketBuffer::readBoolean);
    BYTE =
        new DataType<>(
            (BiConsumer<PacketBuffer, Byte>) PacketBuffer::writeByte, PacketBuffer::readByte);
    UNSIGNED_BYTE =
        new DataType<>(
            (BiConsumer<PacketBuffer, Byte>) PacketBuffer::writeByte, PacketBuffer::readByte);
    SHORT =
        new DataType<>(
            (BiConsumer<PacketBuffer, Short>) PacketBuffer::writeShort, PacketBuffer::readShort);
    UNSIGNED_SHORT =
        new DataType<>(
            (BiConsumer<PacketBuffer, Short>) PacketBuffer::writeShort, PacketBuffer::readShort);
    INTEGER = new DataType<>(PacketBuffer::writeInt, PacketBuffer::readInt);
    LONG = new DataType<>(PacketBuffer::writeLong, PacketBuffer::readLong);
    FLOAT = new DataType<>(PacketBuffer::writeFloat, PacketBuffer::readFloat);
    DOUBLE = new DataType<>(PacketBuffer::writeDouble, PacketBuffer::readDouble);
    STRING = new DataType<>(PacketBuffer::writeUTF8, PacketBuffer::readUTF8);
    CHAT = new DataType<>(PacketBuffer::writeChat, PacketBuffer::readChat);
    IDENTIFIER = new DataType<>(PacketBuffer::writeIdentifier, PacketBuffer::readIdentifier);
    VARINT = new DataType<>(PacketBuffer::writeVarInt, PacketBuffer::readVarInt);
    ITEM = new DataType<>(PacketBuffer::writeItem, null);
    NBT_TAG = new DataType<>(PacketBuffer::writeNamedBinaryTag, PacketBuffer::readNamedBinaryTag);
    POSITION = new DataType<>(PacketBuffer::writeLong, PacketBuffer::readLong);
    ANGLE = new DataType<>(PacketBuffer::writeAngle, PacketBuffer::readAngle);
    UUID = new DataType<>(PacketBuffer::writeUUID, PacketBuffer::readUUID);
    ARRAY = new DataType<>();
    ENUM = new DataType<>();
    BYTE_ARRAY = new DataType<>(PacketBuffer::writeByteArray, PacketBuffer::readByteArray);
    BIT_FIELD = new DataType<>(PacketBuffer::writeField, null);
  }

  @Nullable private final BiConsumer<PacketBuffer, T> writeMethod;
  @Nullable private final Function<PacketBuffer, T> readMethod;

  DataType() {
    writeMethod = null;
    readMethod = null;
  }

  DataType(
      @Nullable BiConsumer<PacketBuffer, T> writeMethod,
      @Nullable Function<PacketBuffer, T> readMethod) {
    this.writeMethod = writeMethod;
    this.readMethod = readMethod;
  }

  public void write(PacketBuffer buffer, T value) {
    if (writeMethod != null) {
      writeMethod.accept(buffer, value);
    }
  }

  @Nullable
  public T read(PacketBuffer buffer) {
    if (readMethod != null) {
      return readMethod.apply(buffer);
    }

    return null;
  }
}
