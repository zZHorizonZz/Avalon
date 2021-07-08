package com.github.avalon.resource.data;

import com.github.avalon.console.messages.MessageSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Resource json will convert json string format into map from which we can crab strings via it's
 * identifier {@code 'player.command.gamemode'} will return message that is sent when player change
 * his gamemode.
 *
 * @version 1.1
 */
public class ResourceJson {

  private Map<String, String> messages;

  public ResourceJson(String messages) {
    GsonBuilder builder =
        new GsonBuilder().registerTypeAdapter(ResourceJson.class, new MessageSerializer());
    Gson gson = builder.create();
    ResourceJson message = gson.fromJson(messages, ResourceJson.class);
    this.messages = message.getMessages();
  }

  public ResourceJson() {
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
