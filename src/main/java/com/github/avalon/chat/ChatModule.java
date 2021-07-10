package com.github.avalon.chat;

import com.github.avalon.annotation.annotation.Module;
import com.github.avalon.chat.command.Command;
import com.github.avalon.chat.command.CommandExecutor;
import com.github.avalon.chat.command.CommandListener;
import com.github.avalon.chat.command.CommandNode;
import com.github.avalon.chat.command.annotation.CommandPerformer;
import com.github.avalon.console.logging.DefaultLogger;
import com.github.avalon.module.ServerModule;
import com.github.avalon.player.IPlayer;
import com.github.avalon.player.command.GamemodeCommand;
import com.github.avalon.player.command.SuperSecretCommand;
import com.github.avalon.server.IServer;

import java.util.*;
import java.util.function.Consumer;

@Module(name = "Chat Module", asynchronous = true)
public class ChatModule extends ServerModule {

  public static final DefaultLogger LOGGER = new DefaultLogger(ChatModule.class);

  private final List<char[]> registeredPrefixes;
  private final Map<String, Command> commandMap;

  public ChatModule(IServer host) {
    super(host);

    registeredPrefixes = new ArrayList<>();
    commandMap = new HashMap<>();
  }

  @Override
  public void enable() {
    super.enable();

    registerBasicCommands();
  }

  public void registerBasicCommands() {
    registerCommands(new GamemodeCommand());
    registerCommands(new SuperSecretCommand());
  }

  public void registerCommands(CommandListener listener) {
    listener.getCommands().forEach((command, method) -> registerCommand(listener, command, method));
    LOGGER.info("Registering command listener %s", listener.getClass().getSimpleName());
  }

  public void registerCommand(
      CommandListener listener, String name, Consumer<CommandExecutor> method) {
    Optional<CommandPerformer> optionalPerformer =
        Arrays.stream(listener.getClass().getMethods())
            .map(
                commandMethod -> {
                  CommandPerformer annotation = commandMethod.getAnnotation(CommandPerformer.class);
                  if (annotation != null && annotation.command().equalsIgnoreCase(name)) {
                    return annotation;
                  }

                  return null;
                })
            .filter(Objects::nonNull)
            .findFirst();

    if (optionalPerformer.isEmpty()) {
      return;
    }

    CommandPerformer performer = optionalPerformer.get();

    String[] arguments = performer.command().split("\\.");

    String permissionMessage = performer.permissionMessage();
    String errorMessage = performer.errorMessage();

    char[] prefix = performer.prefix();

    if (arguments.length == 0) {
      return;
    }

    Command command = commandMap.getOrDefault(arguments[0], new Command(arguments[0]));
    if (!commandMap.containsKey(arguments[0])) {
      commandMap.put(arguments[0], command);
    }

    if (!registeredPrefixes.contains(prefix)) {
      registeredPrefixes.add(prefix);
    }

    command.setNotEnoughPermissionMessage(permissionMessage);
    command.setErrorOccurredMessage(errorMessage);
    command.setStartingPrefix(prefix);

    registerCommandNode(command, listener, method, arguments);
  }

  private void registerCommandNode(
      CommandNode parent,
      CommandListener listener,
      Consumer<CommandExecutor> method,
      String[] arguments) {
    String[] newArguments = new String[arguments.length - 1];
    for (int i = 0; i < newArguments.length; i++) {
      if (i + 1 >= arguments.length) {
        continue;
      }

      newArguments[i] = arguments[i + 1];
    }

    if (newArguments.length < 1) {
      parent.setListener(listener);
      parent.setAction(method);
      return;
    }

    CommandNode node =
        parent.getChildrens().getOrDefault(newArguments[0], new CommandNode(newArguments[0]));
    if (!parent.getChildrens().containsKey(newArguments[0])) {
      parent.getChildrens().put(newArguments[0], node);
    }

    registerCommandNode(node, listener, method, newArguments);
  }

  public void broadcastMessage(String message) {
    Enumeration<IPlayer> players = getHost().getPlayerSessionRegistry().getRegistry().keys();
    players.asIterator().forEachRemaining(player -> player.sendSystemMessage(message));
  }

  public void broadcastMessage(String message, Collection<IPlayer> receivers) {
    receivers.forEach(player -> player.sendSystemMessage(message));
  }

  public List<char[]> getRegisteredPrefixes() {
    return registeredPrefixes;
  }

  public Map<String, Command> getCommandMap() {
    return commandMap;
  }
}
