package com.github.avalon.chat.command;

public class Command extends CommandNode {

  private char[] startingPrefix = {'/'};
  private String notEnoughPermissionMessage = null;
  private String errorOccurredMessage = null;

  public Command(String name) {
    super(name);
  }

  public char[] getStartingPrefix() {
    return startingPrefix;
  }

  public void setStartingPrefix(char[] startingPrefix) {
    assert startingPrefix[0] == '/' : "Prefix must start with '/'.";
    this.startingPrefix = startingPrefix;
  }

  public String getNotEnoughPermissionMessage() {
    return notEnoughPermissionMessage;
  }

  public void setNotEnoughPermissionMessage(String notEnoughPermissionMessage) {
    this.notEnoughPermissionMessage = notEnoughPermissionMessage;
  }

  public String getErrorOccurredMessage() {
    return errorOccurredMessage;
  }

  public void setErrorOccurredMessage(String errorOccurredMessage) {
    this.errorOccurredMessage = errorOccurredMessage;
  }
}
