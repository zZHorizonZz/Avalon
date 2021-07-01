package com.github.avalon.snbt.parent;

import com.github.avalon.snbt.Object;
import io.netty.util.internal.StringUtil;

import java.util.LinkedList;
import java.util.List;

public class NodeSingle extends Node {

  private List<com.github.avalon.snbt.Object> objects;

  public NodeSingle(String name) {
    super(name);

      objects = new LinkedList<>();
  }

  public NodeSingle(String name, int beginIndex) {
    super(name, beginIndex);

      objects = new LinkedList<>();
  }

  public List<com.github.avalon.snbt.Object> getObjects() {
    return objects;
  }

  public void setObjects(List<com.github.avalon.snbt.Object> objects) {
    this.objects = objects;
  }

  public void appendObjects(com.github.avalon.snbt.Object... objects) {
    for (Object object : objects) {
      appendObject(object);
    }
  }

  public void appendObject(com.github.avalon.snbt.Object object) {
      objects.add(object);
  }

  public boolean has(String name) {
    for (com.github.avalon.snbt.Object object : objects) {
      if (StringUtil.isNullOrEmpty(name)) {
        return false;
      } else if (object.getName().equals(name)) return true;
    }

    return false;
  }
}
