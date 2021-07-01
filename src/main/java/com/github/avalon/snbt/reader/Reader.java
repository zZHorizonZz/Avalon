package com.github.avalon.snbt.reader;

import com.github.avalon.snbt.Object;
import com.github.avalon.snbt.object.Tag;
import com.github.avalon.snbt.parent.Node;
import com.github.avalon.snbt.parent.NodeArray;

/**
 * This interface provides methods that should be implemented by class that reads nbt.
 *
 * @version 1.0
 * @author Horizon
 */
public interface Reader {

  /**
   * Try to find the parent in current sequence.
   *
   * @since 1.0
   * @param currentName Name of the parent should be created before call of this method.
   * @param charset Nbt string converted to chars.
   * @param currentPosition Position of pointer in current sequence.
   * @return Node if is successfully found.
   */
  Node findParent(String currentName, char[] charset, int currentPosition);

  /**
   * Searches for {@link Node}s inside of the current {@link NodeArray}.
   *
   * @since 1.0
   * @param parent Current parent that will contain all found {@link Node}.
   * @param charset Charset that is being searched.
   * @param currentPosition Position of pointer in current sequence.
   * @return Inserted {@link NodeArray} with all founded parents.
   */
  NodeArray searchArrayParent(NodeArray parent, char[] charset, int currentPosition);

  /**
   * Searches for possible {@link Object} or {@link Node} inside of node.
   *
   * @since 1.0
   * @param node Node that will contain all found {@link Object} or {@link Node}.
   * @param charset Charset that is being searched.
   * @param currentPosition Position of pointer in current sequence.
   * @return Node that will contain all found {@link Object} or {@link Node}.
   */
  Node searchParent(Node node, char[] charset, int currentPosition);

  /**
   * Searches for possible values.
   *
   * @since 1.0
   * @param valueName Name of value.
   * @param charset Charset that is being searched.
   * @param beginIndex Position of pointer in current sequence.
   * @return Returns the value if is found.
   */
  Tag<?> searchValue(String valueName, char[] charset, int beginIndex);

  /**
   * This method is called when some exception is thrown.
   *
   * @since 1.0
   * @param throwable Thrown exception.
   */
  void throwable(Throwable throwable, char[] charset, int currentPositionOfPointer);
}
