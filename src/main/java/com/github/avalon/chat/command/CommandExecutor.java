package com.github.avalon.chat.command;

public class CommandExecutor {

  private final ChatOperator sender;
  private CommandNode command;

  private String label;
  private String[] arguments;

  public CommandExecutor(ChatOperator sender) {
    this.sender = sender;
  }

  public ChatOperator getSender() {
    return sender;
  }

  public CommandNode getCommand() {
    return command;
  }

  public String getLabel() {
    return label;
  }

  public String[] getArguments() {
    return arguments;
  }

  public void setCommand(CommandNode command) {
    this.command = command;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public void setArguments(String[] arguments) {
    this.arguments = arguments;
  }
}
