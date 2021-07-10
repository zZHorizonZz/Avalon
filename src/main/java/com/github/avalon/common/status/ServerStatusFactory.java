package com.github.avalon.common.status;

import com.github.avalon.common.json.ObjectSerializer;
import com.github.avalon.chat.message.Message;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.List;

public class ServerStatusFactory extends ObjectSerializer<ServerStatusFactory> {

  private String gameVersion;
  private int protocolVersion;

  private Message motd;
  private String favicon;

  private boolean hidePlayers;
  private int maximumPlayers;
  private int currentPlayers; // This is for customization purposes.
  private List<String> playerSample; // Null because we don's care currently about players.

  public ServerStatusFactory() {}

  public ServerStatusFactory(
      String gameVersion, int protocolVersion, Message motd, String favicon) {
    this.gameVersion = gameVersion;
    this.protocolVersion = protocolVersion;
    this.motd = motd;
    this.favicon = favicon;
    hidePlayers = true;
  }

  public ServerStatusFactory(
      String gameVersion,
      int protocolVersion,
      Message motd,
      String favicon,
      int maximumPlayers,
      int currentPlayers,
      List<String> playerSample) {
    this.gameVersion = gameVersion;
    this.protocolVersion = protocolVersion;
    this.motd = motd;
    this.favicon = favicon;
    this.maximumPlayers = maximumPlayers;
    this.currentPlayers = currentPlayers;
    this.playerSample = playerSample;
  }

  @Override
  public ServerStatusFactory deserialize(
      JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
      throws JsonParseException {
    JsonObject json = jsonElement.getAsJsonObject();
    if (json.has("description")) {
      motd = jsonDeserializationContext.deserialize(json.get("description"), String.class);
    }

    if (json.has("players")) {
      JsonObject playerObject = json.getAsJsonObject("players");

      if (playerObject.has("max")) {
        maximumPlayers =
            jsonDeserializationContext.deserialize(playerObject.get("max"), Integer.class);
      }
      if (playerObject.has("online")) {
        currentPlayers =
            jsonDeserializationContext.deserialize(playerObject.get("online"), Integer.class);
      }

      // TODO player sample.
    }

    if (json.has("version")) {
      JsonObject versionObject = json.getAsJsonObject("version");
      if (versionObject.has("name")) {
        gameVersion =
            jsonDeserializationContext.deserialize(versionObject.get("name"), String.class);
      }
      if (versionObject.has("protocol")) {
        protocolVersion =
            jsonDeserializationContext.deserialize(versionObject.get("protocol"), Integer.class);
      }
    }

    if (json.has("favicon")) {
      motd = jsonDeserializationContext.deserialize(json.get("favicon"), String.class);
    }

    return this;
  }

  @Override
  public JsonElement serialize(
      ServerStatusFactory serverStatusFactory,
      Type type,
      JsonSerializationContext jsonSerializationContext) {

    JsonObject json = new JsonObject();
    if (serverStatusFactory.getMotd() != null) {
      json.add(
          "description",
          jsonSerializationContext.serialize(serverStatusFactory.getMotd(), Message.class));
    }

    if (serverStatusFactory.getGameVersion() != null) {
      JsonObject versionObject;

      if (json.has("version")) {
        versionObject = json.getAsJsonObject("version");
      } else {
        versionObject = new JsonObject();
        json.add("version", versionObject);
      }

      versionObject.addProperty("name", serverStatusFactory.getGameVersion());
    }
    if (serverStatusFactory.getProtocolVersion() != 0) {
      JsonObject versionObject;

      if (json.has("version")) {
        versionObject = json.getAsJsonObject("version");
      } else {
        versionObject = new JsonObject();
        json.add("version", versionObject);
      }

      versionObject.addProperty("protocol", serverStatusFactory.getProtocolVersion());
    }
    if (!hidePlayers) {
      JsonObject playersObject = new JsonObject();
      json.add("players", playersObject);

      playersObject.addProperty("max", serverStatusFactory.getMaximumPlayers());
      playersObject.addProperty("online", serverStatusFactory.getCurrentPlayers());

      // TODO IPlayer sample
    }
    if (favicon != null) {
      json.addProperty("favicon", favicon);
    }

    return json;
  }

  public String getGameVersion() {
    return gameVersion;
  }

  public void setGameVersion(String gameVersion) {
    this.gameVersion = gameVersion;
  }

  public int getProtocolVersion() {
    return protocolVersion;
  }

  public void setProtocolVersion(int protocolVersion) {
    this.protocolVersion = protocolVersion;
  }

  public Message getMotd() {
    return motd;
  }

  public void setMotd(Message motd) {
    this.motd = motd;
  }

  public String getFavicon() {
    return favicon;
  }

  public void setFavicon(String favicon) {
    this.favicon = favicon;
  }

  public List<String> getPlayerSample() {
    return playerSample;
  }

  public void setPlayerSample(List<String> playerSample) {
    this.playerSample = playerSample;
  }

  public int getCurrentPlayers() {
    return currentPlayers;
  }

  public void setCurrentPlayers(int currentPlayers) {
    this.currentPlayers = currentPlayers;
  }

  public int getMaximumPlayers() {
    return maximumPlayers;
  }

  public void setMaximumPlayers(int maximumPlayers) {
    this.maximumPlayers = maximumPlayers;
  }

  public boolean isHidePlayers() {
    return hidePlayers;
  }

  public void setHidePlayers(boolean hidePlayers) {
    this.hidePlayers = hidePlayers;
  }
}
