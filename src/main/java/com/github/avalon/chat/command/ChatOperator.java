package com.github.avalon.chat.command;

import com.github.avalon.chat.ChatModule;
import com.github.avalon.chat.message.ChatColor;
import com.github.avalon.chat.message.Message;
import com.github.avalon.player.attributes.MessageType;
import com.github.avalon.server.Server;

/**
 * Represents an object that can receive messages from server side. Or send messages to the server.
 * This should be used mainly for {@link com.github.avalon.player.IPlayer} and Console because
 * console can also send and receive messages (Commands).
 *
 * @version 1.0
 */
public interface ChatOperator {

  /**
   * Sends the text message to the receiver. This should be used when for example another {@link
   * com.github.avalon.player.IPlayer} sends the public message.
   *
   * @since 1.0
   * @param message Message to send.
   */
  default void sendMessage(String message) {
    sendMessage(MessageType.CHAT, ChatColor.toChat(message));
  }

  /**
   * Sends the system text message to the receiver. This type of message is considered as top
   * priority message so client should not ignore it, but there is still a chance that client has
   * only command chat enabled so this will be ignored in default client.
   *
   * @since 1.0
   * @param message Message to send.
   */
  default void sendSystemMessage(String message) {
    sendMessage(MessageType.CHAT, ChatColor.toChat(message));
  }

  /**
   * Sends the text message to the receiver. If message is sent to the {@link
   * com.github.avalon.player.IPlayer} then this message is showed as HotBar message.
   *
   * @since 1.0
   * @param message Message to send.
   */
  default void sendGameInfo(String message) {
    sendMessage(MessageType.CHAT, ChatColor.toChat(message));
  }

  /**
   * Sends the text message to the receiver. This should be used when for example another {@link
   * com.github.avalon.player.IPlayer} sends the public message.
   *
   * @since 1.0
   * @param message Message to send.
   */
  default void sendMessage(Message message) {
    sendMessage(MessageType.CHAT, message);
  }

  /**
   * Sends the system text message to the receiver. This type of message is considered as top
   * priority message so client should not ignore it, but there is still a chance that client has
   * only command chat enabled so this will be ignored in default client.
   *
   * @since 1.0
   * @param message Message to send.
   */
  default void sendSystemMessage(Message message) {
    sendMessage(MessageType.CHAT, message);
  }

  /**
   * Sends the text message to the receiver. If message is sent to the {@link
   * com.github.avalon.player.IPlayer} then this message is showed as HotBar message.
   *
   * @since 1.0
   * @param message Message to send.
   */
  default void sendGameInfo(Message message) {
    sendMessage(MessageType.CHAT, message);
  }

  /**
   * Sends the text message to the receiver.
   *
   * @since 1.0
   * @param message Message to send.
   */
  void sendMessage(MessageType type, Message message);

  /**
   * Handles the message that should be sent to the default chat.
   *
   * @param message Message to send.
   */
  default void handleMessage(String message) {}

  /**
   * If incoming message starts with / then this method should be called
   *
   * @param commandString
   */
  default void handleCommand(String commandString) {
    ChatModule chatModule = getServer().getChatModule();
    String[] arguments = commandString.split(" ");

    for (char[] registeredPrefix : chatModule.getRegisteredPrefixes()) {
      String firstArgument = arguments[0];
      if (firstArgument.startsWith(String.valueOf(registeredPrefix))) {
        arguments[0] = firstArgument.substring(registeredPrefix.length);
        firstArgument = arguments[0];
        if (!firstArgument.isEmpty()) {
          Command command = chatModule.getCommandMap().get(firstArgument);
          if (command != null) {
            command.handleCommand(this, command, arguments, commandString);
            return;
          }
        }
      }
    }
  }

  /** @return Returns the {@link Server} */
  Server getServer();
}
