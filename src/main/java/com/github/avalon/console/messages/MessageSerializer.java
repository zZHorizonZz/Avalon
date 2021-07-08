package com.github.avalon.console.messages;

import com.github.avalon.resource.data.ResourceJson;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Map;

public class MessageSerializer implements JsonDeserializer<ResourceJson> {

  @Override
  public ResourceJson deserialize(
          JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
      throws JsonParseException {
    ResourceJson resourceJson = new ResourceJson();

    JsonObject jsonObject = jsonElement.getAsJsonObject();
    if (jsonObject.size() < 1) return resourceJson;

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
                    resourceJson.registerMessage(
                        messageIdentifier.toString(),
                        jsonDeserializationContext.deserialize(message.getValue(), String.class));
                  });
        }
      }
    }

    return resourceJson;
  }
}
