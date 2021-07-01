package com.github.avalon.common.bytes;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * This class provides a storage for {@link byte} that can be easily managed and used for any
 * purpose. This class was created for {@code ByteBuf.writeBytes(byte[] bytes);}.
 *
 * @author Horizon
 * @version 1.0
 */
public class ByteList implements Iterable<Byte>, Cloneable {

  private byte[] byteList;

  public ByteList(byte[] byteList) {
    this.byteList = byteList;
  }

  public ByteList() {
      byteList = new byte[0];
  }

  @Override
  public Iterator<Byte> iterator() {
    return Stream.of(toArray()).iterator();
  }

  @Override
  public void forEach(Consumer<? super Byte> action) {
    iterator().forEachRemaining(action);
  }

  public byte[] getArray() {
    return byteList;
  }

  public Byte[] toArray() {
    Byte[] byteArray = new Byte[byteList.length];

    for (int i = 0; i < byteList.length; i++) {
      byteArray[i] = Byte.valueOf(byteList[i]);
    }

    return byteArray;
  }

  public int size() {
    return byteList.length;
  }

  public boolean isEmpty() {
    return byteList == null || byteList.length == 0;
  }

  public boolean contains(byte aByte) {
    for (byte b : byteList) {
      if (b == aByte) return true;
    }

    return false;
  }

  public boolean add(byte aByte) {
    byte[] bytes = new byte[byteList.length + 1];

    for (int i = 0; i < bytes.length; i++) {
      bytes[i] = i >= byteList.length ? aByte : byteList[i];
    }

    byteList = bytes;
    return true;
  }

  public void clear() {
    byteList = new byte[0];
  }
}
