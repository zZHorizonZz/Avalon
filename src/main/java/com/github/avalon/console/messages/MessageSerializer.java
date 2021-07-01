package com.github.avalon.console.messages;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Map;

public class MessageSerializer implements JsonDeserializer<ServerMessage> {

  @Override
  public ServerMessage deserialize(
          JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
      throws JsonParseException {
    ServerMessage serverMessage = new ServerMessage();

    JsonObject jsonObject = jsonElement.getAsJsonObject();
    if (jsonObject.size() < 1) return serverMessage;

    for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
      String name = entry.getKey();
      JsonElement element = entry.getValue();

      if (element.isJsonObject()) {
        JsonObject object = element.getAsJsonObject();
        if (object.size() > 0) {
          object
              .entrySet()
              .forEach(
                  message -> {
                    StringBuilder messageIdentifier = new StringBuilder();
                    messageIdentifier.append(name).append('.').append(message.getKey());
                    serverMessage.registerMessage(
                        messageIdentifier.toString(),
                        jsonDeserializationContext.deserialize(message.getValue(), String.class));
                  });
        }
      }
    }

    return serverMessage;
  }
}
