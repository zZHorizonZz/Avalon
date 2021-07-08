package com.github.avalon.chat.message;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Message implements JsonSerializer<Message>, JsonDeserializer<Message> {

  // TODO Apply effect that has text objects before on next text object.

  private List<Text> textEntities;

  public Message() {
    textEntities = new LinkedList<>();
  }

  public Message(String message) {
    MessageFactory factory = new MessageFactory(message);
    textEntities = factory.toChat().getTextEntities();
  }

  @Override
  public Message deserialize(
      JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
      throws JsonParseException {
    JsonObject jsonObject = jsonElement.getAsJsonObject();

    return null;
  }

  @Override
  public JsonElement serialize(
      Message message, Type type, JsonSerializationContext jsonSerializationContext) {
    JsonObject jsonObject = new JsonObject();
    if (message.getTextEntities().isEmpty()) {
      return jsonObject;
    }

    jsonObject =
        jsonSerializationContext.serialize(message.getTextEntities().get(0)).getAsJsonObject();
    if (message.getTextEntities().size() > 1) {
      jsonObject.add("extra", new JsonArray());

      JsonArray array = jsonObject.get("extra").getAsJsonArray();
      for (int i = 1; i < message.getTextEntities().size(); i++) {
        Text text = message.getTextEntities().get(i);
        array.add(jsonSerializationContext.serialize(text));
      }
    }
    return jsonObject;
  }

  @Override
  public String toString() {
    String builder =
        textEntities.stream().map(Text::toString).collect(Collectors.joining("", "[", "]"));
    return builder;
  }

  public void append(Text text) {
    textEntities.add(text);
  }

  public List<Text> getTextEntities() {
    return textEntities;
  }

  public void setTextEntities(List<Text> textEntities) {
    this.textEntities = textEntities;
  }

  public String getRawText() {
    StringBuilder builder = new StringBuilder();
    textEntities.forEach(text -> builder.append(text.getText()));
    return builder.toString();
  }
}
