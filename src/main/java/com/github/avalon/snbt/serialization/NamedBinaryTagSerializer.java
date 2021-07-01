package com.github.avalon.snbt.serialization;

import com.github.avalon.snbt.parent.Node;

public interface NamedBinaryTagSerializer {

  Node serialize(Object object);
}
