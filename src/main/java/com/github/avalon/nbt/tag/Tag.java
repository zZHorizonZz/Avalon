package com.github.avalon.nbt.tag;

import io.netty.util.internal.StringUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class Tag {

  private final byte identifier;
  private String name;

  protected Tag(byte identifier) {
    this(identifier, "");
  }

  protected Tag(byte identifier, String name) {
    this.identifier = identifier;
    this.name = name;
  }

  public void writeNamedTag(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeByte(identifier);
    if (!(this instanceof TagEnd)) {
      dataOutputStream.writeUTF(name);
    }

    write(dataOutputStream);
  }

  public void writeNamelessTag(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeByte(identifier);
    write(dataOutputStream);
  }

  public abstract void write(DataOutputStream dataOutputStream) throws IOException;

  public void readTagName(DataInputStream dataInputStream) throws IOException {
    name = dataInputStream.readUTF();
  }

  public abstract void read(DataInputStream dataInputStream) throws IOException;

  public int getIdentifier() {
    return identifier;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isNamedTag() {
    return !StringUtil.isNullOrEmpty(name);
  }

  public abstract String toPrettyString();

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Tag tag = (Tag) o;

    if (getIdentifier() != tag.getIdentifier()) {
      return false;
    }
    return getName() != null ? getName().equals(tag.getName()) : tag.getName() == null;
  }

  @Override
  public int hashCode() {
    int result = getIdentifier();
    result = 31 * result + (getName() != null ? getName().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Tag " + identifier;
  }
}
