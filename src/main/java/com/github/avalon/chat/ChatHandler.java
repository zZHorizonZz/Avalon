package com.github.avalon.chat;

import com.github.avalon.chat.command.ChatOperator;
import com.github.avalon.packet.PacketListener;
import com.github.avalon.packet.annotation.PacketHandler;
import com.github.avalon.packet.packet.play.PacketChatMessageServer;
import com.github.avalon.player.PlayerConnection;

public class ChatHandler implements PacketListener {

  @PacketHandler
  public void handleChat(PacketChatMessageServer packet, PlayerConnection connection) {
    ChatOperator operator = connection.getPlayer();

    if (packet.getMessage().isEmpty()) {
      return;
    }

    String message = packet.getMessage();
    if (message.startsWith("/")) {
      operator.handleCommand(message);
    }
  }
}
