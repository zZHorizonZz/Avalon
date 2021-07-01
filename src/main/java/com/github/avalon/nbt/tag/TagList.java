package com.github.avalon.nbt.tag;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;

public class TagList<T extends Tag> extends Tag implements List<T> {

  private final List<T> list;
  private Class<? extends Tag> objectClass;

  public TagList(String name, Class<? extends Tag> objectClass, List<T> list) {
    super((byte) 9, name);

    this.objectClass = objectClass;
    this.list = list;
  }

  public TagList(Class<? extends Tag> objectClass, List<T> list) {
    super((byte) 9);

    this.objectClass = objectClass;
    this.list = list;
  }

  public TagList(String name, Class<? extends Tag> objectClass, T... array) {
    super((byte) 9, name);

    this.objectClass = objectClass;
    list = Arrays.asList(array);
  }

  public TagList(Class<? extends Tag> objectClass, T... array) {
    super((byte) 9);

    this.objectClass = objectClass;
    list = Arrays.asList(array);
  }

  public TagList(String name, Class<? extends Tag> objectClass) {
    super((byte) 9, name);

    this.objectClass = objectClass;
    list = new ArrayList<>();
  }

  public TagList(Class<? extends Tag> objectClass) {
    super((byte) 9);

    this.objectClass = objectClass;
    list = new ArrayList<>();
  }

  public TagList() {
    super((byte) 9);
    list = new ArrayList<>();
  }

  @Override
  public void write(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeByte(
        !list.isEmpty() ? TagType.getByClass(objectClass) : TagType.TAG_END.getIdentifier());
    dataOutputStream.writeInt(list.size());

    for (Tag tag : list) {
      assert !tag.isNamedTag() : "All tags in the list should be a nameless tags!";

      tag.write(dataOutputStream);
    }
  }

  @Override
  public void read(DataInputStream dataInputStream) throws IOException {
    readTagName(dataInputStream);
    byte identifier = dataInputStream.readByte();
    objectClass = TagType.getByIdentifier(identifier);

    int size = dataInputStream.readInt();
    for (int i = 0; i < size; i++) {
      Tag tag = TagType.getInstanceByIdentifier(identifier);
      tag.read(dataInputStream);
      add((T) tag);
    }
  }

  @Override
  public String toPrettyString() {
    StringBuilder builder = new StringBuilder("TAG_LIST: \"" + getName() + '"');
    list.forEach(tag -> builder.append(Character.LINE_SEPARATOR).append(tag.toPrettyString()));
    return builder.toString();
  }

  @Override
  public int size() {
    return list.size();
  }

  @Override
  public boolean isEmpty() {
    return list.isEmpty();
  }

  @Override
  public boolean contains(Object o) {
    return list.contains(o);
  }

  @Override
  public Iterator<T> iterator() {
    return list.iterator();
  }

  @Override
  public Object[] toArray() {
    return list.toArray();
  }

  @Override
  public <T1> T1[] toArray(T1[] t1s) {
    return list.toArray(t1s);
  }

  @Override
  public boolean add(T t) {
    return list.add(t);
  }

  @Override
  public boolean remove(Object o) {
    return list.remove(o);
  }

  @Override
  public boolean containsAll(Collection<?> collection) {
    return list.containsAll(collection);
  }

  @Override
  public boolean addAll(Collection<? extends T> collection) {
    return list.addAll(collection);
  }

  @Override
  public boolean addAll(int i, Collection<? extends T> collection) {
    return list.addAll(i, collection);
  }

  @Override
  public boolean removeAll(Collection<?> collection) {
    return list.removeAll(collection);
  }

  @Override
  public boolean retainAll(Collection<?> collection) {
    return list.retainAll(collection);
  }

  @Override
  public void clear() {
    list.clear();
  }

  @Override
  public T get(int i) {
    return list.get(i);
  }

  @Override
  public T set(int i, T t) {
    return list.set(i, t);
  }

  @Override
  public void add(int i, T t) {
    list.add(i, t);
  }

  @Override
  public T remove(int i) {
    return list.remove(i);
  }

  @Override
  public int indexOf(Object o) {
    return list.indexOf(o);
  }

  @Override
  public int lastIndexOf(Object o) {
    return list.lastIndexOf(o);
  }

  @Override
  public ListIterator<T> listIterator() {
    return list.listIterator();
  }

  @Override
  public ListIterator<T> listIterator(int i) {
    return list.listIterator(i);
  }

  @Override
  public List<T> subList(int i, int i1) {
    return list.subList(i, i1);
  }
}
