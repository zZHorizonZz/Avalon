package com.github.avalon.chat.command;

import com.github.avalon.player.IPlayer;

import java.util.List;
import java.util.TreeMap;
import java.util.function.Consumer;

public class CommandNode {

  private final String name;

  private CommandNode parentNode;
  private TreeMap<String, CommandNode> childNodes;

  private CommandListener listener;
  private Consumer<CommandExecutor> method;

  public CommandNode(String name) {
    this.name = name;

    childNodes = new TreeMap<>();
  }

  public boolean handleCommand(
      ChatOperator sender, Command superNode, String[] currentArguments, String label) {
    if (sender == null) {
      return false;
    }

    if (sender instanceof IPlayer) {
      String[] newArguments = new String[currentArguments.length - 1];
      for (int i = 0; i < newArguments.length; i++) {
        if (i + 1 >= currentArguments.length) {
          continue;
        }

        newArguments[i] = currentArguments[i + 1];
      }

      if (newArguments.length == 0) {
        return performAction(sender, superNode, newArguments, label);
      }

      CommandNode childNode = findChild(newArguments[0]);
      if (childNode == null) {
        return performAction(sender, superNode, newArguments, label);
      }

      return childNode.handleCommand(sender, superNode, newArguments, label);
    }

    return false;
  }

  /**
   * Tries to invoke the registered method in current node and returns the result if invocation is
   * successful then true is returned otherwise false is returned.
   *
   * @param sender Sender of command can be {@link IPlayer} or console.
   * @param superNode Main node of this command.
   * @param arguments Current arguments left.
   * @param label Whole command label message.
   * @return Is command is performed successfully.
   */
  private boolean performAction(
          ChatOperator sender, Command superNode, String[] arguments, String label) {
    try {
      CommandExecutor executor = new CommandExecutor(sender);
      executor.setArguments(arguments);
      executor.setCommand(this);
      executor.setLabel(label);
      method.accept(executor);
      return true;
    } catch (RuntimeException exception) {
      exception.printStackTrace();
      sender.sendSystemMessage(superNode.getErrorOccurredMessage());
      return false;
    }
  }

  /** @return Returns the name of command node. */
  public String getName() {
    return name;
  }

  /** @return If node has parent node then that node is returned otherwise null is returned. */
  public CommandNode getParentNode() {
    return parentNode;
  }

  /**
   * @return Returns true if parent node is null. That means if this node is at beginning of
   *     command.
   */
  public boolean isSuperNode() {
    return parentNode == null;
  }

  /**
   * Sets the parent node for current node.
   *
   * @param parentNode Parent node.
   */
  public void setParent(CommandNode parentNode) {
    this.parentNode = parentNode;
  }

  /**
   * Returns the child nodes if there are any. Otherwise empty {@link List} is returned and system
   * should classify all next arguments in commands command message consider as command arguments.
   *
   * @return Child nodes
   */
  public TreeMap<String, CommandNode> getChildrens() {
    return childNodes;
  }

  /**
   * Sets the child nodes for current node.
   *
   * @param childNodes Child nodes.
   */
  public void setChildrens(TreeMap<String, CommandNode> childNodes) {
    this.childNodes = childNodes;
  }

  /**
   * Adds node as child node for current node.
   *
   * @param node Command node.
   */
  public void addChildren(CommandNode node) {
    childNodes.put(node.getName(), node);
  }

  /**
   * Tries to find child node if exists. Otherwise returns null.
   *
   * @param name Name of the node.
   * @return Node if is found.
   */
  public CommandNode findChild(String name) {
    return childNodes.get(name);
  }

  public CommandListener getListener() {
    return listener;
  }

  public void setListener(CommandListener listener) {
    this.listener = listener;
  }

  public Consumer<CommandExecutor> getMethod() {
    return method;
  }

  public void setAction(Consumer<CommandExecutor> method) {
    this.method = method;
  }

  @Override
  public String toString() {
    StringBuilder builder =
        new StringBuilder("CommandNode:" + " Name:" + name + " , ChildNodes:" + childNodes.size());
    childNodes.forEach((name, node) -> builder.append(node.toString()));
    return builder.toString();
  }
}
