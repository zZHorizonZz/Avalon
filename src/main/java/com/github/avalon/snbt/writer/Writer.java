package com.github.avalon.snbt.writer;

import com.github.avalon.snbt.parent.NodeArray;
import com.github.avalon.snbt.parent.NodeSingle;

/**
 * This interface provides an methods that are used to create nbt string.
 *
 * @author Horizon
 * @version 1.0
 */
public interface Writer {

  /**
   * Create a nbt string from given {@link NodeSingle} and addPlayer him to the {@link StringBuilder}.
   *
   * @since 1.0
   * @param parent Node to create string from.
   * @param builder Builder to append string.
   * @return Nbt string.
   */
  StringBuilder writeParentSingle(NodeSingle parent, StringBuilder builder);

  /**
   * Create a nbt string array from given {@link NodeArray} and addPlayer him to the {@link
   * StringBuilder}.
   *
   * @since 1.0
   * @param parent Node to create string array from.
   * @param builder Builder to append string array.
   * @return Nbt string.
   */
  StringBuilder writeParentArray(NodeArray parent, StringBuilder builder);

  /**
   * This method is called when some exception is thrown.
   *
   * @since 1.0
   * @param throwable Thrown exception.
   */
  void throwable(Throwable throwable);
}
