package com.github.avalon.snbt.writer;

import com.github.avalon.snbt.Object;
import com.github.avalon.snbt.exception.ReadException;
import com.github.avalon.snbt.object.Tag;
import com.github.avalon.snbt.parent.Node;
import com.github.avalon.snbt.parent.NodeArray;
import com.github.avalon.snbt.parent.NodeSingle;
import io.netty.util.internal.StringUtil;

public class DataWriter implements Writer {

  @Override
  public StringBuilder writeParentSingle(NodeSingle parent, StringBuilder builder) {
    try {
      if (parent.getName().contains(":"))
        builder.append("\"").append(parent.getName()).append("\"");
      else builder.append(parent.getName());

      if (!StringUtil.isNullOrEmpty(parent.getName())) builder.append(":");

      builder.append("{");

      if (!parent.getObjects().isEmpty()) {
        for (int i = 0; i < parent.getObjects().size(); i++) {
          Object dataObject = parent.getObjects().get(i);

          if (dataObject instanceof Node) {
            if (dataObject instanceof NodeSingle) {
              writeParentSingle((NodeSingle) dataObject, builder);
            }

            if (dataObject instanceof NodeArray) {
              writeParentArray((NodeArray) dataObject, builder);
            }
          }

          if (dataObject instanceof Tag) {
            builder
                .append(dataObject.getName())
                .append(":")
                .append(dataObject)
                .append(i == parent.getObjects().size() - 1 ? "" : ",");
          }
        }
      }

      builder.append("}");
    } catch (Exception exception) {
      throwable(exception);
      return null;
    }

    return builder;
  }

  @Override
  public StringBuilder writeParentArray(NodeArray parent, StringBuilder builder) {
    try {
      if (parent.getName().contains(":"))
        builder.append("\"").append(parent.getName()).append("\n");
      else builder.append(parent.getName());

      if (!StringUtil.isNullOrEmpty(parent.getName())) builder.append(":");

      builder.append("[");

      if (!parent.getParents().isEmpty()) {
        for (int i = 0; i < parent.getParents().size(); i++) {
          NodeSingle dataObject = parent.getParents().get(i);
          writeParentSingle(dataObject, builder);
          builder.append(i == parent.getParents().size() - 1 ? "" : ",");
        }
      }

      builder.append("]");
    } catch (Exception exception) {
      throwable(exception);
      return null;
    }

    return builder;
  }

  @Override
  public void throwable(Throwable throwable) {
    throw new ReadException("Error occurred on creation of nbt string.", throwable);
  }
}
