package com.github.avalon.snbt.reader;

import com.github.avalon.snbt.Object;
import com.github.avalon.snbt.exception.ReadException;
import com.github.avalon.snbt.object.*;
import com.github.avalon.snbt.parent.Node;
import com.github.avalon.snbt.parent.NodeArray;
import com.github.avalon.snbt.parent.NodeSingle;

import java.util.List;

public class DataReader implements Reader {

  public static int MAXIMUM_LENGTH = Integer.MAX_VALUE;
  public static int INVALIDATION_INDEX = 10;

  @Override
  public Node findParent(String currentName, char[] charset, int currentPosition) {
    for (int i = currentPosition; i < charset.length; i++) {
      try {
        char currentChar = charset[i];
        switch (currentChar) {
          case ' ':
            {
              continue;
            }
          case '[':
            {
              NodeArray parentArray = new NodeArray(currentName, i);
              return searchArrayParent(parentArray, charset, i + 1);
            }
          case '{':
            {
              NodeSingle parentSingle = new NodeSingle(currentName, i);
              return searchParent(parentSingle, charset, i + 1);
            }
        }
      } catch (Exception exception) {
        throwable(exception, charset, i);
        return null;
      }

      return null;
    }

    return null;
  }

  @Override
  public NodeArray searchArrayParent(NodeArray parent, char[] charset, int currentPosition) {
    int index = currentPosition;

    while (index < charset.length) {
      try {

        char currentChar = charset[index];
        switch (currentChar) {
          case ' ':
            {
              break;
            }
          case '{':
            {
              NodeSingle parentSingle = new NodeSingle("", index);
              searchParent(parentSingle, charset, index + 1);
              index = parentSingle.getEndIndex();
              parent.appendParent(parentSingle);
              break;
            }
          case ']':
            {
              parent.setEndIndex(index);
              return parent;
            }
        }
      } catch (Exception exception) {
        throwable(exception, charset, index);
        return null;
      }

      index++;
    }

    return null;
  }

  @Override
  public Node searchParent(Node node, char[] charset, int currentPosition) {
    StringBuilder parentName = new StringBuilder();
    boolean stringName = false;

    int index = currentPosition;

    while (index < charset.length) {
      try {
        char currentChar = charset[index];
        switch (currentChar) {
          case ',':
            {
              if (stringName) break;
              else continue;
            }
          case ' ':
            {
              continue;
            }
          case '"':
            {
              stringName = !stringName;
              break;
            }
          case ':':
            {
              if (stringName) break;

              Node subNode = findParent(parentName.toString(), charset, index + 1);
              if (subNode == null) {
                Tag<?> tag = searchValue(parentName.toString(), charset, index + 1);
                if (tag != null) {
                  index = tag.getEndIndex();

                  if (node instanceof NodeSingle)
                    ((NodeSingle) node).appendObject(tag);
                }
              } else {
                if (node instanceof NodeSingle) ((NodeSingle) node).appendObject(subNode);
                else if (node instanceof NodeArray)
                  ((NodeArray) node).appendParent((NodeSingle) subNode);
                index = subNode.getEndIndex();
              }

              parentName = new StringBuilder();

              continue;
            }
          case ']':
          case '}':
            {
              if (index + 1 < charset.length && charset[index + 1] == ',') {
                index++;
              }
              node.setEndIndex(index);
              return node;
            }
        }

        if (!Character.isLetter(currentChar) && currentChar != '_' && currentChar != ':') continue;

        parentName.append(currentChar);
      } catch (Exception exception) {
        throwable(exception, charset, index);
        return null;
      }

      index++;
    }

    return node;
  }

  @Override
  public Tag<?> searchValue(String valueName, char[] charset, int beginIndex) {
    StringBuilder value = new StringBuilder();
    Class<?> possibleValueType = null;

    int invalidationIndex = 0;

    for (int i = beginIndex; i < charset.length && i <= DataReader.MAXIMUM_LENGTH; i++) {
      try {
        char currentChar = charset[i];
        switch (currentChar) {
          case '"':
            {
              if (possibleValueType == String.class) {
                return new StringTag(valueName, value.toString(), beginIndex, i);
              } else possibleValueType = String.class;
              continue;
            }
          case '1':
            {
              if (possibleValueType == String.class) break;

              if (charset[i + 1] == 'b')
                return new BooleanTag(valueName, true, beginIndex, i + 1);
              break;
            }
          case '0':
            {
              if (possibleValueType == String.class) break;

              if (charset[i + 1] == 'b')
                return new BooleanTag(valueName, false, beginIndex, i + 1);
              break;
            }
          case ',':
            {
              if (possibleValueType == Integer.class)
                if (validateValue(value.toString().toCharArray()))
                  return new IntegerTag(
                      valueName, Integer.parseInt(value.toString()), beginIndex, i);
              return null;
            }
          case 'd':
          case 'D':
            {
              if (possibleValueType == String.class) break;

              possibleValueType = Double.class;
              if (value.length() < 1) {
                return null;
              } else {
                if (validateValue(value.toString().toCharArray()))
                  return new DoubleTag(
                      valueName, Double.parseDouble(value.toString()), beginIndex, i);
              }

              break;
            }
          case 'f':
          case 'F':
            {
              if (possibleValueType == String.class) break;

              possibleValueType = FloatTag.class;
              if (value.length() < 1) {
                return null;
              } else {
                if (validateValue(value.toString().toCharArray()))
                  return new FloatTag(
                      valueName, Float.parseFloat(value.toString()), beginIndex, i);
              }

              break;
            }
          case ' ':
            {
              if (possibleValueType == Integer.class)
                if (validateValue(value.toString().toCharArray()))
                  return new IntegerTag(
                      valueName, Integer.parseInt(value.toString()), beginIndex);
              invalidationIndex++;
              if (invalidationIndex >= DataReader.INVALIDATION_INDEX)
                return null; // Valus seems to be invalid.
              continue;
            }
        }

        if (Character.isDigit(currentChar)) {
          possibleValueType = Integer.class;
        }

        value.append(currentChar);
      } catch (Exception exception) {
        throwable(exception, charset, i);
        return null;
      }
    }

    return null;
  }

  @Override
  public void throwable(Throwable throwable, char[] charset, int currentPositionOfPointer) {
    throw new ReadException(
        "Error occurred on character " + currentPositionOfPointer + ". ", throwable);
  }

  public void debugParent(Node node, int deep) {
    System.out.println();
    if (node == null) return;

    if (node instanceof NodeSingle) {
      if (((NodeSingle) node).getObjects().isEmpty()) return;

      List<com.github.avalon.snbt.Object> dataObject = ((NodeSingle) node).getObjects();
      for (Object object : dataObject) {
        if (object instanceof Node) {
          System.out.println(
              lineFormatter(
                  "[Node " + object.getClass().getSimpleName() + "] " + object.getName(), deep));
          debugParent((Node) object, deep + 1);
        } else if (object instanceof Tag)
          System.out.println(
              lineFormatter(
                  "[Value] " + object.getName() + ": " + ((Tag<?>) object).getValue(),
                  deep));
      }
    } else if (node instanceof NodeArray) {
      if (((NodeArray) node).getParents().isEmpty()) return;

      List<NodeSingle> dataObject = ((NodeArray) node).getParents();
      for (Object object : dataObject) {
        if (object instanceof Node) {
          System.out.println(
              lineFormatter(
                  "[Node " + object.getClass().getSimpleName() + "] " + object.getName(), deep));
          debugParent((Node) object, deep + 1);
        } else if (object instanceof Tag)
          System.out.println(
              lineFormatter(
                  "[Value] " + object.getName() + ": " + ((Tag<?>) object).getValue(),
                  deep));
      }
    }
  }

  public String lineFormatter(String string, int currentIteration) {
    StringBuilder format = new StringBuilder();
    for (int i = 1; i <= currentIteration; i++) {
      if (i == 1) format.append("﹄");
      format.append("=");
    }

    format.append(" ");
    format.append(string);
    return format.toString();
  }

  public boolean validateValue(char[] charset) {
    for (char character : charset) {
      if (!Character.isDigit(character) && character != '.') return false;
    }
    return true;
  }
}
