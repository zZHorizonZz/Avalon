package com.github.avalon.console.messages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

public class ServerMessage {

  private Map<String, String> messages;

  public ServerMessage(String messages) {
    GsonBuilder builder =
        new GsonBuilder().registerTypeAdapter(ServerMessage.class, new MessageSerializer());
    Gson gson = builder.create();
    ServerMessage message = gson.fromJson(messages, ServerMessage.class);
    this.messages = message.getMessages();
  }

  public ServerMessage() {
      messages = new HashMap<>();
  }

  public String getMessage(String messageId) {
    String message = messages.get(messageId);
    assert message != null : "Message with this id doesn't exist.";
    return message;
  }

  public void registerMessage(String messageId, String message) {
    assert messageId != null && message != null
        : "Arguments of message that will be registered should not be null";
    messages.put(messageId, message);
  }

  public Map<String, String> getMessages() {
    return messages;
  }

  public void setMessages(Map<String, String> messages) {
    this.messages = messages;
  }
}
