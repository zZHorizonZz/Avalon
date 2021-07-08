package com.github.avalon.account;

import com.github.avalon.common.uuid.UtilUuid;
import com.github.avalon.console.logging.DefaultLogger;
import com.github.avalon.data.Properties;
import com.github.avalon.http.HttpCallback;
import com.github.avalon.player.IPlayer;
import com.google.gson.*;

import javax.annotation.Nullable;
import java.lang.reflect.Type;
import java.util.UUID;

public class ProfileCallback implements HttpCallback, JsonDeserializer<PlayerProfile> {

  public static final DefaultLogger LOGGER = new DefaultLogger(ProfileCallback.class);

  private final IPlayer player;

  public ProfileCallback(IPlayer player) {
    this.player = player;
  }

  @Nullable
  @Override
  public PlayerProfile deserialize(
      JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
      throws JsonParseException {
    JsonObject jsonObject = jsonElement.getAsJsonObject();

    String name = null;
    String id = null;

    if (jsonObject.has("name")) {
      name = jsonDeserializationContext.deserialize(jsonObject.get("name"), String.class);
    }

    if (jsonObject.has("id")) {
      id = jsonDeserializationContext.deserialize(jsonObject.get("id"), String.class);
    }

    if (name == null || id == null) {
      LOGGER.error(
          "Name or is of profile is null.",
          new NullPointerException("Profile or identifier is null."));
      return null;
    }

    UUID uuid = UtilUuid.getUuidFromString(id);
    Properties properties = new Properties();

    if (jsonObject.has("properties")) {
      JsonObject jsonProperties = jsonObject.getAsJsonObject("properties");

      properties.setName(
          jsonDeserializationContext.deserialize(
              jsonProperties.getAsJsonObject().get("name"), String.class));
      properties.setValue(
          jsonDeserializationContext.deserialize(
              jsonProperties.getAsJsonObject().get("value"), String.class));
      properties.setSignature(
          jsonDeserializationContext.deserialize(
              jsonProperties.getAsJsonObject().get("signature"), String.class));
    } else {
      LOGGER.error(
          "Profile response does not contain properties!",
          new NullPointerException("Profile does not contain any properties."));
      return null;
    }

    PlayerProfile profile = new PlayerProfile(name, uuid);
    profile.setProperties(properties);

    return profile;
  }

  @Override
  public void onResponse(String response) {
    response = response.replace("[", "").replace("]", "");

    Gson gson = new GsonBuilder().registerTypeAdapter(PlayerProfile.class, this).create();

    PlayerProfile profile = gson.fromJson(response, PlayerProfile.class);
    if (profile == null) {
      player.disconnect("Something went wrong with loading of your profile.");
      return;
    }

    if (profile.getUUID() == null) {
      player.disconnect("Invalid uuid exception occurred.");
      return;
    }
    profile.setProfileStatus(ProfileStatus.MOJANG);

    LOGGER.info(
        "[%s] Profile of player with uuid %s has been loaded successfully.",
        profile.getName(), profile.getUUID());

    player
        .getServer()
        .getSchedulerModule()
        .runTask(() -> player.getActionHandler().handleLogin(profile));
  }

  @Override
  public void onError(Throwable throwable) {
    LOGGER.error("Authentication internal error.", throwable);
    player.disconnect("Authentication internal error.");
  }
}
