package com.github.avalon.common.bytes;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class ByteListTest {

  @Test
  public void testConstructor() throws UnsupportedEncodingException {
    byte[] bytes = "AAAAAAAAAAAAAAAAAAAAAAAA".getBytes(StandardCharsets.UTF_8);
    ByteList actualByteList = new ByteList(bytes);
    byte[] array = actualByteList.getArray();
    assertEquals(24, array.length);
    assertEquals(24, actualByteList.size());
    assertFalse(actualByteList.isEmpty());
    assertSame(array, bytes);
  }

  @Test
  public void testConstructor2() {
    assertEquals(0, (new ByteList()).getArray().length);
  }

  @Test
  public void testIterator() throws UnsupportedEncodingException {
    byte[] bytes = "AAAAAAAAAAAAAAAAAAAAAAAA".getBytes(StandardCharsets.UTF_8);
    ByteList byteList = new ByteList(bytes);
    byteList
        .iterator()
        .forEachRemaining(
            aByte -> {
              assertEquals((byte) aByte, 65);
            });
  }

  @Test
  public void testToArray() throws UnsupportedEncodingException {
    assertEquals(0, (new ByteList()).toArray().length);
    assertEquals(24, (new ByteList("AAAAAAAAAAAAAAAAAAAAAAAA".getBytes(StandardCharsets.UTF_8))).toArray().length);
  }

  @Test
  public void testSize() {
    assertEquals(0, (new ByteList()).size());
  }

  @Test
  public void testIsEmpty() throws UnsupportedEncodingException {
    assertTrue((new ByteList()).isEmpty());
    assertFalse((new ByteList("AAAAAAAAAAAAAAAAAAAAAAAA".getBytes(StandardCharsets.UTF_8))).isEmpty());
    assertTrue((new ByteList(null)).isEmpty());
  }

  @Test
  public void testContains() throws UnsupportedEncodingException {
    assertFalse((new ByteList()).contains((byte) 'A'));
    assertTrue((new ByteList("AAAAAAAAAAAAAAAAAAAAAAAA".getBytes(StandardCharsets.UTF_8))).contains((byte) 'A'));
  }

  @Test
  public void testContains2() {
    ByteList byteList = new ByteList();
    byteList.add((byte) 'X');
    assertFalse(byteList.contains((byte) 'A'));
  }

  @Test
  public void testAdd() {
    ByteList byteList = new ByteList();
    assertTrue(byteList.add((byte) 'A'));
    assertEquals(1, byteList.getArray().length);
  }

  @Test
  public void testAdd2() throws UnsupportedEncodingException {
    ByteList byteList = new ByteList("AAAAAAAAAAAAAAAAAAAAAAAA".getBytes(StandardCharsets.UTF_8));
    assertTrue(byteList.add((byte) 'A'));
    assertEquals(25, byteList.getArray().length);
  }
}
