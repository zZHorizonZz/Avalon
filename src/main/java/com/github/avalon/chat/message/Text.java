package com.github.avalon.chat.message;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.EnumSet;

public class Text implements JsonSerializer<Text>, JsonDeserializer<Text> {

  private ChatColor chatColor = ChatColor.WHITE;
  private EnumSet<ChatEffect> effects = EnumSet.noneOf(ChatEffect.class);

  private String text;

  public Text() {}

  @Override
  public Text deserialize(
      JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
      throws JsonParseException {

    Text text = new Text();
    JsonObject jsonObject = jsonElement.getAsJsonObject();

    if (jsonObject.has("color")) {
      text.setColor(
          ChatColor.BY_NAME.get(
              (String)
                  jsonDeserializationContext.deserialize(jsonObject.get("color"), String.class)));
    }

    if (jsonObject.has("bold")) {
      if (jsonDeserializationContext.deserialize(jsonObject.get("bold"), Boolean.class)) {
        text.addEffect(ChatEffect.BOLD);
      }
    }
    if (jsonObject.has("underline")) {
      if (jsonDeserializationContext.deserialize(jsonObject.get("underline"), Boolean.class)) {
        text.addEffect(ChatEffect.UNDERLINE);
      }
    }
    if (jsonObject.has("italic")) {
      if (jsonDeserializationContext.deserialize(jsonObject.get("italic"), Boolean.class)) {
        text.addEffect(ChatEffect.ITALIC);
      }
    }
    if (jsonObject.has("obfuscated")) {
      if (jsonDeserializationContext.deserialize(jsonObject.get("obfuscated"), Boolean.class)) {
        text.addEffect(ChatEffect.MAGIC);
      }
    }
    if (jsonObject.has("strikethrough")) {
      if (jsonDeserializationContext.deserialize(jsonObject.get("strikethrough"), Boolean.class)) {
        text.addEffect(ChatEffect.STRIKETHROUGH);
      }
    }

    return text;
  }

  @Override
  public JsonElement serialize(
      Text text, Type type, JsonSerializationContext jsonSerializationContext) {
    JsonObject jsonObject = new JsonObject();
    ChatColor color = text.getColor();

    jsonObject.addProperty("text", text.getText());
    jsonObject.addProperty("color", color.toHex());
    jsonObject.addProperty("bold", text.getEffects().contains(ChatEffect.BOLD));
    jsonObject.addProperty("underline", text.getEffects().contains(ChatEffect.UNDERLINE));
    jsonObject.addProperty("italic", text.getEffects().contains(ChatEffect.ITALIC));
    jsonObject.addProperty("obfuscated", text.getEffects().contains(ChatEffect.MAGIC));
    jsonObject.addProperty("strikethrough", text.getEffects().contains(ChatEffect.STRIKETHROUGH));

    return jsonObject;
  }

  public ChatColor getColor() {
    return chatColor;
  }

  public void setColor(ChatColor chatColor) {
    this.chatColor = chatColor;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public EnumSet<ChatEffect> getEffects() {
    return effects;
  }

  public void setEffects(EnumSet<ChatEffect> effects) {
    this.effects = effects;
  }

  public void addEffect(ChatEffect effect) {
    effects.add(effect);
  }

  @Override
  public String toString() {
    return "Text{"
            + "chatColor="
            + chatColor
            + ", effects="
            + effects
            + ", text='"
            + text
            + '\''
            + '}';
  }

  @Override
  public Text clone() {
    Text text = new Text();
    text.setText(this.text);
    text.setColor(chatColor);
    text.setEffects(effects);

    return text;
  }
}
