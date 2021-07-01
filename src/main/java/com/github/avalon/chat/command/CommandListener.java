package com.github.avalon.chat.command;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class CommandListener {

  private final Map<String, Consumer<CommandExecutor>> commands;

  protected CommandListener() {
    commands = new HashMap<>();
  }

  public void register(String commandName, Consumer<CommandExecutor> command) {
    commands.put(commandName, command);
  }

  public Map<String, Consumer<CommandExecutor>> getCommands() {
    return commands;
  }
}
