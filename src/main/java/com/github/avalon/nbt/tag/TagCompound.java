package com.github.avalon.nbt.tag;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;

public class TagCompound extends Tag implements List<Tag> {

  private final List<Tag> childrens;

  public TagCompound(String name, List<Tag> childrens) {
    super((byte) 10, name);

    this.childrens = childrens;
  }

  public TagCompound(List<Tag> childrens) {
    super((byte) 10);

    this.childrens = childrens;
  }

  public TagCompound(String name, Tag... childrens) {
    super((byte) 10, name);

    this.childrens = Arrays.asList(childrens);
  }

  public TagCompound(Tag... childrens) {
    super((byte) 10);

    this.childrens = Arrays.asList(childrens);
  }

  public TagCompound(String name) {
    super((byte) 10, name);

    childrens = new ArrayList<>();
  }

  public TagCompound() {
    super((byte) 10);

    childrens = new ArrayList<>();
  }

  @Override
  public void write(DataOutputStream dataOutputStream) throws IOException {
    TagEnd endWriter = new TagEnd();

    if (childrens.isEmpty()) {
      endWriter.writeNamelessTag(dataOutputStream);
      return;
    }

    for (int i = 0; i <= childrens.size(); i++) {
      if (i == childrens.size()) {
        endWriter.writeNamelessTag(dataOutputStream);
        return;
      }

      Tag tag = childrens.get(i);
      assert tag.isNamedTag() : "All tags in the compound should have name!";

      tag.writeNamedTag(dataOutputStream);
    }
  }

  @Override
  public void read(DataInputStream dataInputStream) throws IOException {
    readTagName(dataInputStream);

    Tag tag = TagType.getInstanceByIdentifier(dataInputStream.readByte());
    tag.read(dataInputStream);
    add(tag);
  }

  @Override
  public String toPrettyString() {
    StringBuilder builder = new StringBuilder("TAG_COMPOUND: \"" + getName() + '"');
    childrens.forEach(tag -> builder.append(Character.LINE_SEPARATOR).append(tag.toPrettyString()));
    return builder.toString();
  }

  @Override
  public int size() {
    return childrens.size();
  }

  @Override
  public boolean isEmpty() {
    return childrens.isEmpty();
  }

  @Override
  public boolean contains(Object o) {
    return childrens.contains(o);
  }

  @Override
  public Iterator<Tag> iterator() {
    return childrens.iterator();
  }

  @Override
  public Object[] toArray() {
    return childrens.toArray();
  }

  @Override
  public <T> T[] toArray(T[] ts) {
    return null;
  }

  @Override
  public boolean add(Tag t) {
    return childrens.add(t);
  }

  @Override
  public boolean remove(Object o) {
    return childrens.remove(o);
  }

  @Override
  public boolean containsAll(Collection<?> collection) {
    return childrens.containsAll(collection);
  }

  @Override
  public boolean addAll(Collection<? extends Tag> collection) {
    return childrens.addAll(collection);
  }

  @Override
  public boolean addAll(int i, Collection<? extends Tag> collection) {
    return childrens.addAll(i, collection);
  }

  @Override
  public boolean removeAll(Collection<?> collection) {
    return childrens.removeAll(collection);
  }

  @Override
  public boolean retainAll(Collection<?> collection) {
    return childrens.retainAll(collection);
  }

  @Override
  public void clear() {
    childrens.clear();
  }

  @Override
  public Tag get(int i) {
    return childrens.get(i);
  }

  @Override
  public Tag set(int i, Tag t) {
    return childrens.set(i, t);
  }

  @Override
  public void add(int i, Tag t) {
    childrens.add(i, t);
  }

  @Override
  public Tag remove(int i) {
    return childrens.remove(i);
  }

  @Override
  public int indexOf(Object o) {
    return childrens.indexOf(o);
  }

  @Override
  public int lastIndexOf(Object o) {
    return childrens.lastIndexOf(o);
  }

  @Override
  public ListIterator<Tag> listIterator() {
    return childrens.listIterator();
  }

  @Override
  public ListIterator<Tag> listIterator(int i) {
    return childrens.listIterator(i);
  }

  @Override
  public List<Tag> subList(int i, int i1) {
    return childrens.subList(i, i1);
  }
}
