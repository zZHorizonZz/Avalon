package com.github.avalon.network;

import com.github.avalon.chat.message.Message;
import com.github.avalon.chat.message.Text;
import com.github.avalon.common.bytes.BitField;
import com.github.avalon.common.data.Array;
import com.github.avalon.data.Material;
import com.github.avalon.descriptor.ClassDescriptor;
import com.github.avalon.descriptor.DescriptorModule;
import com.github.avalon.dimension.chunk.IChunk;
import com.github.avalon.dimension.chunk.IChunkSection;
import com.github.avalon.item.Item;
import com.github.avalon.nbt.stream.NamedBinaryOutputStream;
import com.github.avalon.nbt.tag.TagCompound;
import com.github.avalon.resource.data.ResourceIdentifier;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.ByteBufProcessor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * LegacyPacket Buffer class is basically byte buffer but with some additional methods created
 * specific for minecraft server.
 *
 * @author Horizon
 * @version 1.2
 */
public class PacketBuffer extends ByteBuf {

  public static final Gson CHAT_SERIALIZER =
      new GsonBuilder()
          .registerTypeAdapter(Message.class, new Message.Serialization())
          .registerTypeAdapter(Text.class, new Text())
          .create();

  private final ByteBuf buffer;
  private DescriptorModule descriptorManager;

  public PacketBuffer(DescriptorModule descriptorManager, ByteBuf buffer) {
    this.buffer = buffer;
    this.descriptorManager = descriptorManager;
  }

  public PacketBuffer(ByteBuf buffer) {
    this.buffer = buffer;
  }

  /**
   * Write all tags that are neede for {@link com.github.avalon.packet.packet.play.PacketTags}.
   * Blocks, Items, Fluids, Characters.
   *
   * @since 1.0
   */
  public void writeTags() {
    writeTag(
        (ClassDescriptor<?>)
            descriptorManager.getRegistry().getDescriptor("blocks").get().getValue());

    writeTag(
        (ClassDescriptor<?>)
            descriptorManager.getRegistry().getDescriptor("items").get().getValue());

    writeTag(
        (ClassDescriptor<?>)
            descriptorManager.getRegistry().getDescriptor("fluids").get().getValue());

    writeTag(
        (ClassDescriptor<?>)
            descriptorManager.getRegistry().getDescriptor("characters").get().getValue());
  }

  /**
   * Write tag to the buffer.
   *
   * @since 1.0
   * @param tagClass Class of tag that will be registered.
   */
  public void writeTag(ClassDescriptor<?> tagClass) {
    writeVarInt(tagClass.getClasses().size());

    tagClass
        .getClasses()
        .forEach(
            (name, type) -> {
              writeUTF8(name);
              writeVarInt(0); // TODO Here should be registration of tags.
            });
  }

  /**
   * This method is based on {@link com.flowpowered.network.util.ByteBufUtils} method. But for
   * simple usage and editability is here.
   *
   * @since 1.0
   * @param integer Integer that will be written.
   */
  public void writeVarInt(int integer) {
    do {
      byte part = (byte) (integer & 127);
      integer >>>= 7;
      if (integer != 0) {
        part = (byte) (part | 128);
      }

      buffer.writeByte(part);
    } while (integer != 0);
  }

  /**
   * This method is based on {@link com.flowpowered.network.util.ByteBufUtils} method. But for
   * simple usage and editability is here.
   *
   * @since 1.0
   * @return Integer that is written as var int in buffer.
   */
  public int readVarInt() {
    int out = 0;
    int bytes = 0;

    byte in;
    do {
      in = readByte();
      out |= (in & 127) << bytes++ * 7;
      assert bytes <= 5 : "Attempt to read int bigger than allowed for a varint! (" + bytes + ')';
    } while ((in & 128) == 128);

    return out;
  }

  /**
   * This method is based on {@link com.flowpowered.network.util.ByteBufUtils} method. But for
   * simple usage and editability is here.
   *
   * @since 1.0
   * @param string String that will be written.
   */
  public void writeUTF8(String string) {
    writeUTF8(string, Short.MAX_VALUE);
  }

  /**
   * This method is based on {@link com.flowpowered.network.util.ByteBufUtils} method. But for
   * simple usage and editability is here.
   *
   * @since 1.0
   * @param string String that will be written.
   * @param length Maximum length of string.
   */
  public void writeUTF8(String string, int length) {
    byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
    if (bytes.length >= length) {
      throw new StringIndexOutOfBoundsException(
          "Attempt to write a string with a length greater than Short.MAX_VALUE to ByteBuf!");
    }

    writeVarInt(bytes.length);
    buffer.writeBytes(bytes);
  }

  /**
   * This method is based on {@link com.flowpowered.network.util.ByteBufUtils} method. But for
   * simple usage and editability is here.
   *
   * @since 1.0
   * @return String that is written in buffer.
   */
  public String readUTF8() {
    int len = readVarInt();
    byte[] bytes = new byte[len];
    readBytes(bytes);
    return new String(bytes, StandardCharsets.UTF_8);
  }

  /**
   * Writes a byte array into the buffer.
   *
   * @since 1.2
   * @param array Array that will be written into buffer.
   */
  public void writeByteArray(byte[] array) {
    writeVarInt(array.length);
    writeBytes(array);
  }

  /**
   * Reads a byte array from buffer.
   *
   * @return Array of bytes.
   */
  public byte[] readByteArray() {
    byte[] array = new byte[readVarInt()];
    readBytes(array);
    return array;
  }

  /**
   * Will write bitfield to the buffer. Bitfield is used to create smaller boolean values and store
   * them in one {@link Byte}.
   *
   * @param booleans Boolean or array of booleans that will be written to buffer.
   * @return Value of byte that is written to the buffer.
   */
  public byte writeField(Boolean... booleans) {
    BitField field = new BitField(booleans);
    return writeField(field);
  }

  /**
   * Will write bitfield to the buffer. Bitfield is used to create smaller boolean values and store
   * them in one {@link Byte}.
   *
   * @param field Bitfield that will be written to the buffer.
   * @return Value of byte that is written to the buffer.
   */
  public byte writeField(BitField field) {
    return field.write(this);
  }

  /**
   * Read a UUID encoded as two longs from the buffer.
   *
   * @since 1.1
   * @return The UUID read from the buffer.
   */
  public UUID readUUID() {
    return new UUID(readLong(), readLong());
  }

  /**
   * Write a UUID encoded as two longs to the buffer.
   *
   * @since 1.1
   * @param uuid The UUID to write.
   */
  public void writeUUID(UUID uuid) {
    writeLong(uuid.getMostSignificantBits());
    writeLong(uuid.getLeastSignificantBits());
  }

  /**
   * Automatically handles and converts the specified angle to the buffer. A rotation angle in steps
   * of 1/256 of a full turn. Source <url>www.wiki.vg</url>.
   *
   * @param angle Angle between the -180 and 180.
   */
  public void writeAngle(float angle) {
    writeByte((int) (angle * 256.0F / 360.0F));
  }

  /** @return Read the angle and returns the converted value. */
  public float readAngle() {
    return readByte() * 360 / 256.0F;
  }

  /**
   * Write array int to stream. Basically this will write the length of the array and then it writes
   * array values.
   *
   * @param array Array with values.
   * @param <T> Type of objects inside of array.
   */
  public <T> void writeArray(Array<T> array) {
    writeVarInt(array.getSize());
    Objects.requireNonNull(array.get()).forEach(object -> array.getType().write(this, (T) object));
  }

  /**
   * Reads the array from stream and then stores these values into new {@link Array}.
   *
   * @param array Array that contains type of object that will be stored in new {@link Array}.
   * @param <T> Type of objects.
   * @return New created {@link Array} with values.
   */
  public <T> Array<T> readArray(Array<T> array) {
    int size = readVarInt();
    List<T> list =
        IntStream.range(0, size)
            .mapToObj(i -> array.getType().read(this))
            .collect(Collectors.toCollection(LinkedList::new));
    return new Array<>(array.getType(), list);
  }

  /**
   * Writes the {@link TagCompound} to the stream.
   *
   * @param compound {@link TagCompound} that will be written into stream.
   */
  public void writeNamedBinaryTag(TagCompound compound) {
    try {
      NamedBinaryOutputStream stream = new NamedBinaryOutputStream(new ByteBufOutputStream(this));
      // stream.writeByte(10);
      compound.writeNamedTag(stream);
    } catch (IOException exception) {
      exception.printStackTrace();
    }
  }

  // TODO: Client currently doesn't send any nbt tags.
  public TagCompound readNamedBinaryTag() {
    return null;
  }

  /**
   * Writes the {@link ResourceIdentifier} as string into the stream.
   *
   * @param location {@link ResourceIdentifier} to be written into stream.
   */
  public void writeIdentifier(ResourceIdentifier location) {
    writeUTF8(location.getLocation());
  }

  /**
   * Reads the identifier from stream. But currently it seems that client does not send any
   * identifiers.
   *
   * @return Identifier.
   */
  public ResourceIdentifier readIdentifier() {
    String[] identifier = readUTF8().split(":");
    return new ResourceIdentifier(identifier[0], identifier[1]);
  }

  public void writeItem(Item item) {
    writeBoolean(item.getMaterial() != Material.AIR);
    if (item.getMaterial() != Material.AIR) {
      TagCompound data = item.toNamedBinaryTag();
      writeVarInt(item.getMaterial().getIdentifier());
      writeByte(item.getAmount());
      if (data == null || data.isEmpty()) {
        writeByte(0);
      } else {
        writeNamedBinaryTag(data);
      }
    }
  }

  public void writeChat(Message message) {
    writeUTF8(CHAT_SERIALIZER.toJson(message, Message.class));
  }

  public Message readChat() {
    String json = readUTF8();
    return CHAT_SERIALIZER.fromJson(json, Message.class);
  }

  public void writeChunkSections(IChunkSection[] section) {
    for (IChunkSection chunkSection : section) {
      chunkSection.write(this);
    }
  }

  @Override
  public int capacity() {
    return buffer.capacity();
  }

  @Override
  public ByteBuf capacity(int i) {
    return buffer.capacity(i);
  }

  @Override
  public int maxCapacity() {
    return buffer.maxCapacity();
  }

  @Override
  public ByteBufAllocator alloc() {
    return buffer.alloc();
  }

  @Override
  public ByteOrder order() {
    return buffer.order();
  }

  @Override
  public ByteBuf order(ByteOrder byteOrder) {
    return buffer.order(byteOrder);
  }

  @Override
  public ByteBuf unwrap() {
    return buffer.unwrap();
  }

  @Override
  public boolean isDirect() {
    return buffer.isDirect();
  }

  @Override
  public int readerIndex() {
    return buffer.readerIndex();
  }

  @Override
  public ByteBuf readerIndex(int i) {
    return buffer.readerIndex(i);
  }

  @Override
  public int writerIndex() {
    return buffer.writerIndex();
  }

  @Override
  public ByteBuf writerIndex(int i) {
    return buffer.writerIndex(i);
  }

  @Override
  public ByteBuf setIndex(int i, int i1) {
    return buffer.setIndex(i, i1);
  }

  @Override
  public int readableBytes() {
    return buffer.readableBytes();
  }

  @Override
  public int writableBytes() {
    return buffer.writableBytes();
  }

  @Override
  public int maxWritableBytes() {
    return buffer.maxWritableBytes();
  }

  @Override
  public boolean isReadable() {
    return buffer.isReadable();
  }

  @Override
  public boolean isReadable(int i) {
    return buffer.isReadable(i);
  }

  @Override
  public boolean isWritable() {
    return buffer.isWritable();
  }

  @Override
  public boolean isWritable(int i) {
    return buffer.isWritable(i);
  }

  @Override
  public ByteBuf clear() {
    return buffer.clear();
  }

  @Override
  public ByteBuf markReaderIndex() {
    return buffer.markReaderIndex();
  }

  @Override
  public ByteBuf resetReaderIndex() {
    return buffer.resetReaderIndex();
  }

  @Override
  public ByteBuf markWriterIndex() {
    return buffer.markWriterIndex();
  }

  @Override
  public ByteBuf resetWriterIndex() {
    return buffer.resetWriterIndex();
  }

  @Override
  public ByteBuf discardReadBytes() {
    return buffer.discardReadBytes();
  }

  @Override
  public ByteBuf discardSomeReadBytes() {
    return buffer.discardSomeReadBytes();
  }

  @Override
  public ByteBuf ensureWritable(int i) {
    return buffer.ensureWritable(i);
  }

  @Override
  public int ensureWritable(int i, boolean b) {
    return buffer.ensureWritable(i, b);
  }

  @Override
  public boolean getBoolean(int i) {
    return buffer.getBoolean(i);
  }

  @Override
  public byte getByte(int i) {
    return buffer.getByte(i);
  }

  @Override
  public short getUnsignedByte(int i) {
    return buffer.getUnsignedByte(i);
  }

  @Override
  public short getShort(int i) {
    return buffer.getShort(i);
  }

  @Override
  public int getUnsignedShort(int i) {
    return buffer.getUnsignedShort(i);
  }

  @Override
  public int getMedium(int i) {
    return buffer.getMedium(i);
  }

  @Override
  public int getUnsignedMedium(int i) {
    return buffer.getUnsignedMedium(i);
  }

  @Override
  public int getInt(int i) {
    return buffer.getInt(i);
  }

  @Override
  public long getUnsignedInt(int i) {
    return buffer.getUnsignedInt(i);
  }

  @Override
  public long getLong(int i) {
    return buffer.getLong(i);
  }

  @Override
  public char getChar(int i) {
    return buffer.getChar(i);
  }

  @Override
  public float getFloat(int i) {
    return buffer.getFloat(i);
  }

  @Override
  public double getDouble(int i) {
    return buffer.getDouble(i);
  }

  @Override
  public ByteBuf getBytes(int i, ByteBuf byteBuf) {
    return buffer.getBytes(i, byteBuf);
  }

  @Override
  public ByteBuf getBytes(int i, ByteBuf byteBuf, int i1) {
    return buffer.getBytes(i, byteBuf, i1);
  }

  @Override
  public ByteBuf getBytes(int i, ByteBuf byteBuf, int i1, int i2) {
    return buffer.getBytes(i, byteBuf, i1, i2);
  }

  @Override
  public ByteBuf getBytes(int i, byte[] bytes) {
    return buffer.getBytes(i, bytes);
  }

  @Override
  public ByteBuf getBytes(int i, byte[] bytes, int i1, int i2) {
    return buffer.getBytes(i, bytes, i1, i2);
  }

  @Override
  public ByteBuf getBytes(int i, ByteBuffer byteBuffer) {
    return buffer.getBytes(i, byteBuffer);
  }

  @Override
  public ByteBuf getBytes(int i, OutputStream outputStream, int i1) throws IOException {
    return buffer.getBytes(i, outputStream, i1);
  }

  @Override
  public int getBytes(int i, GatheringByteChannel gatheringByteChannel, int i1) throws IOException {
    return buffer.getBytes(i, gatheringByteChannel, i1);
  }

  @Override
  public ByteBuf setBoolean(int i, boolean b) {
    return buffer.setBoolean(i, b);
  }

  @Override
  public ByteBuf setByte(int i, int i1) {
    return buffer.setByte(i, i1);
  }

  @Override
  public ByteBuf setShort(int i, int i1) {
    return buffer.setShort(i, i1);
  }

  @Override
  public ByteBuf setMedium(int i, int i1) {
    return buffer.setMedium(i, i1);
  }

  @Override
  public ByteBuf setInt(int i, int i1) {
    return buffer.setInt(i, i1);
  }

  @Override
  public ByteBuf setLong(int i, long l) {
    return buffer.setLong(i, l);
  }

  @Override
  public ByteBuf setChar(int i, int i1) {
    return buffer.setChar(i, i1);
  }

  @Override
  public ByteBuf setFloat(int i, float v) {
    return buffer.setFloat(i, v);
  }

  @Override
  public ByteBuf setDouble(int i, double v) {
    return buffer.setDouble(i, v);
  }

  @Override
  public ByteBuf setBytes(int i, ByteBuf byteBuf) {
    return buffer.setBytes(i, byteBuf);
  }

  @Override
  public ByteBuf setBytes(int i, ByteBuf byteBuf, int i1) {
    return buffer.setBytes(i, byteBuf, i1);
  }

  @Override
  public ByteBuf setBytes(int i, ByteBuf byteBuf, int i1, int i2) {
    return buffer.setBytes(i, byteBuf, i1, i2);
  }

  @Override
  public ByteBuf setBytes(int i, byte[] bytes) {
    return buffer.setBytes(i, bytes);
  }

  @Override
  public ByteBuf setBytes(int i, byte[] bytes, int i1, int i2) {
    return buffer.setBytes(i, bytes, i1, i2);
  }

  @Override
  public ByteBuf setBytes(int i, ByteBuffer byteBuffer) {
    return buffer.setBytes(i, byteBuffer);
  }

  @Override
  public int setBytes(int i, InputStream inputStream, int i1) throws IOException {
    return buffer.setBytes(i, inputStream, i1);
  }

  @Override
  public int setBytes(int i, ScatteringByteChannel scatteringByteChannel, int i1)
      throws IOException {
    return buffer.setBytes(i, scatteringByteChannel, i1);
  }

  @Override
  public ByteBuf setZero(int i, int i1) {
    return buffer.setZero(i, i1);
  }

  @Override
  public boolean readBoolean() {
    return buffer.readBoolean();
  }

  @Override
  public byte readByte() {
    return buffer.readByte();
  }

  @Override
  public short readUnsignedByte() {
    return buffer.readUnsignedByte();
  }

  @Override
  public short readShort() {
    return buffer.readShort();
  }

  @Override
  public int readUnsignedShort() {
    return buffer.readUnsignedShort();
  }

  @Override
  public int readMedium() {
    return buffer.readMedium();
  }

  @Override
  public int readUnsignedMedium() {
    return buffer.readUnsignedMedium();
  }

  @Override
  public int readInt() {
    return buffer.readInt();
  }

  @Override
  public long readUnsignedInt() {
    return buffer.readUnsignedInt();
  }

  @Override
  public long readLong() {
    return buffer.readLong();
  }

  @Override
  public char readChar() {
    return buffer.readChar();
  }

  @Override
  public float readFloat() {
    return buffer.readFloat();
  }

  @Override
  public double readDouble() {
    return buffer.readDouble();
  }

  @Override
  public ByteBuf readBytes(int i) {
    return buffer.readBytes(i);
  }

  @Override
  public ByteBuf readSlice(int i) {
    return buffer.readSlice(i);
  }

  @Override
  public ByteBuf readBytes(ByteBuf byteBuf) {
    return buffer.readBytes(byteBuf);
  }

  @Override
  public ByteBuf readBytes(ByteBuf byteBuf, int i) {
    return buffer.readBytes(byteBuf, i);
  }

  @Override
  public ByteBuf readBytes(ByteBuf byteBuf, int i, int i1) {
    return buffer.readBytes(byteBuf, i, i1);
  }

  @Override
  public ByteBuf readBytes(byte[] bytes) {
    return buffer.readBytes(bytes);
  }

  @Override
  public ByteBuf readBytes(byte[] bytes, int i, int i1) {
    return buffer.readBytes(bytes, i, i1);
  }

  @Override
  public ByteBuf readBytes(ByteBuffer byteBuffer) {
    return buffer.readBytes(byteBuffer);
  }

  @Override
  public ByteBuf readBytes(OutputStream outputStream, int i) throws IOException {
    return buffer.readBytes(outputStream, i);
  }

  @Override
  public int readBytes(GatheringByteChannel gatheringByteChannel, int i) throws IOException {
    return buffer.readBytes(gatheringByteChannel, i);
  }

  @Override
  public ByteBuf skipBytes(int i) {
    return buffer.skipBytes(i);
  }

  @Override
  public ByteBuf writeBoolean(boolean b) {
    return buffer.writeBoolean(b);
  }

  @Override
  public ByteBuf writeByte(int i) {
    return buffer.writeByte(i);
  }

  @Override
  public ByteBuf writeShort(int i) {
    return buffer.writeShort(i);
  }

  @Override
  public ByteBuf writeMedium(int i) {
    return buffer.writeMedium(i);
  }

  @Override
  public ByteBuf writeInt(int i) {
    return buffer.writeInt(i);
  }

  @Override
  public ByteBuf writeLong(long l) {
    return buffer.writeLong(l);
  }

  @Override
  public ByteBuf writeChar(int i) {
    return buffer.writeChar(i);
  }

  @Override
  public ByteBuf writeFloat(float v) {
    return buffer.writeFloat(v);
  }

  @Override
  public ByteBuf writeDouble(double v) {
    return buffer.writeDouble(v);
  }

  @Override
  public ByteBuf writeBytes(ByteBuf byteBuf) {
    return buffer.writeBytes(byteBuf);
  }

  @Override
  public ByteBuf writeBytes(ByteBuf byteBuf, int i) {
    return buffer.writeBytes(byteBuf, i);
  }

  @Override
  public ByteBuf writeBytes(ByteBuf byteBuf, int i, int i1) {
    return buffer.writeBytes(byteBuf, i, i1);
  }

  @Override
  public ByteBuf writeBytes(byte[] bytes) {
    return buffer.writeBytes(bytes);
  }

  @Override
  public ByteBuf writeBytes(byte[] bytes, int i, int i1) {
    return buffer.writeBytes(bytes, i, i1);
  }

  @Override
  public ByteBuf writeBytes(ByteBuffer byteBuffer) {
    return buffer.writeBytes(byteBuffer);
  }

  @Override
  public int writeBytes(InputStream inputStream, int i) throws IOException {
    return buffer.writeBytes(inputStream, i);
  }

  @Override
  public int writeBytes(ScatteringByteChannel scatteringByteChannel, int i) throws IOException {
    return buffer.writeBytes(scatteringByteChannel, i);
  }

  @Override
  public ByteBuf writeZero(int i) {
    return buffer.writeZero(i);
  }

  @Override
  public int indexOf(int i, int i1, byte b) {
    return buffer.indexOf(i, i1, b);
  }

  @Override
  public int bytesBefore(byte b) {
    return buffer.bytesBefore(b);
  }

  @Override
  public int bytesBefore(int i, byte b) {
    return buffer.bytesBefore(i, b);
  }

  @Override
  public int bytesBefore(int i, int i1, byte b) {
    return buffer.bytesBefore(i, i1, b);
  }

  @Override
  public int forEachByte(ByteBufProcessor byteBufProcessor) {
    return buffer.forEachByte(byteBufProcessor);
  }

  @Override
  public int forEachByte(int i, int i1, ByteBufProcessor byteBufProcessor) {
    return buffer.forEachByte(i, i1, byteBufProcessor);
  }

  @Override
  public int forEachByteDesc(ByteBufProcessor byteBufProcessor) {
    return buffer.forEachByteDesc(byteBufProcessor);
  }

  @Override
  public int forEachByteDesc(int i, int i1, ByteBufProcessor byteBufProcessor) {
    return buffer.forEachByteDesc(i, i1, byteBufProcessor);
  }

  @Override
  public ByteBuf copy() {
    return buffer.copy();
  }

  @Override
  public ByteBuf copy(int i, int i1) {
    return buffer.copy(i, i1);
  }

  @Override
  public ByteBuf slice() {
    return buffer.slice();
  }

  @Override
  public ByteBuf slice(int i, int i1) {
    return buffer.slice(i, i1);
  }

  @Override
  public ByteBuf duplicate() {
    return buffer.duplicate();
  }

  @Override
  public int nioBufferCount() {
    return buffer.nioBufferCount();
  }

  @Override
  public ByteBuffer nioBuffer() {
    return buffer.nioBuffer();
  }

  @Override
  public ByteBuffer nioBuffer(int i, int i1) {
    return buffer.nioBuffer(i, i1);
  }

  @Override
  public ByteBuffer internalNioBuffer(int i, int i1) {
    return buffer.internalNioBuffer(i, i1);
  }

  @Override
  public ByteBuffer[] nioBuffers() {
    return buffer.nioBuffers();
  }

  @Override
  public ByteBuffer[] nioBuffers(int i, int i1) {
    return buffer.nioBuffers(i, i1);
  }

  @Override
  public boolean hasArray() {
    return buffer.hasArray();
  }

  @Override
  public byte[] array() {
    return buffer.array();
  }

  @Override
  public int arrayOffset() {
    return buffer.arrayOffset();
  }

  @Override
  public boolean hasMemoryAddress() {
    return buffer.hasMemoryAddress();
  }

  @Override
  public long memoryAddress() {
    return buffer.memoryAddress();
  }

  @Override
  public String toString(Charset charset) {
    return buffer.toString(charset);
  }

  @Override
  public String toString(int i, int i1, Charset charset) {
    return buffer.toString(i, i1, charset);
  }

  @Override
  public int hashCode() {
    return buffer.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    return buffer.equals(o);
  }

  @Override
  public int compareTo(ByteBuf byteBuf) {
    return buffer.compareTo(byteBuf);
  }

  @Override
  public String toString() {
    return buffer.toString();
  }

  @Override
  public ByteBuf retain(int i) {
    return buffer.retain();
  }

  @Override
  public boolean release() {
    return buffer.release();
  }

  @Override
  public boolean release(int i) {
    return buffer.release(i);
  }

  @Override
  public int refCnt() {
    return buffer.refCnt();
  }

  @Override
  public ByteBuf retain() {
    return buffer.retain();
  }

  public DescriptorModule getDescriptorManager() {
    return descriptorManager;
  }

  public ByteBuf getBuffer() {
    return buffer;
  }
}
