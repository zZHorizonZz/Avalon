package com.github.avalon.snbt.parent;

import io.netty.util.internal.StringUtil;

import java.util.LinkedList;
import java.util.List;

public class NodeArray extends Node {

  private List<NodeSingle> objects;

  public NodeArray(String name) {
    super(name);

      objects = new LinkedList<>();
  }

  public NodeArray(String name, int beginIndex) {
    super(name, beginIndex);

      objects = new LinkedList<>();
  }

  public List<NodeSingle> getParents() {
    return objects;
  }

  public void setParents(List<NodeSingle> objects) {
    this.objects = objects;
  }

  public void appendParent(NodeSingle parent) {
      objects.add(parent);
  }

  public boolean has(String name) {
    for (NodeSingle parent : objects) {
      if (StringUtil.isNullOrEmpty(name)) {
        return parent.has(name);
      } else if (parent.getName().equals(name)) return true;
    }
    return false;
  }
}
