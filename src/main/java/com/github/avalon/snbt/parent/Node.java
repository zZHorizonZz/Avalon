package com.github.avalon.snbt.parent;

public abstract class Node extends com.github.avalon.snbt.Object {

  public Node(String name) {
    super(name);
  }

  public Node(String name, int beginIndex) {
    super(name, beginIndex);
  }
}
