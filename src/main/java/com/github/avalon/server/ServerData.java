package com.github.avalon.server;

import com.github.avalon.resource.data.ResourceJson;

/**
 * This class provides whole server settings from {@link ServerVersion} to server mode. This class
 * contains default settings in the case if loading of server settings file fails.
 *
 * @author Horizon
 * @version 1.0
 */
public class ServerData {

  private ResourceJson resourceJson = new ResourceJson();

  private ServerVersion serverVersion = new ServerVersion();
  private String motd = "&eCurrently -> &aRunning.";

  private int maxPlayers = 256;

  private String hostname;
  private int port = 25565;

  private long serverStartTime;

  // This should be set to false only for testing purposes. We do not want to support non-online
  // mode players.
  private boolean developerMode = true;
  private boolean debugWorld = false;

  public String getMotd() {
    return motd;
  }

  public void setMotd(String motd) {
    this.motd = motd;
  }

  public int getMaxPlayers() {
    return maxPlayers;
  }

  public void setMaxPlayers(int maxPlayers) {
    this.maxPlayers = maxPlayers;
  }

  public String getHostname() {
    return hostname;
  }

  public void setHostname(String hostname) {
    this.hostname = hostname;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public boolean isDeveloperMode() {
    return developerMode;
  }

  public void setDeveloperMode(boolean developerMode) {
    this.developerMode = developerMode;
  }

  public ServerVersion getServerVersion() {
    return serverVersion;
  }

  public void setServerVersion(ServerVersion serverVersion) {
    this.serverVersion = serverVersion;
  }

  public long getServerStartTime() {
    return serverStartTime;
  }

  public void setServerStartTime(long serverStartTime) {
    this.serverStartTime = serverStartTime;
  }

  public ResourceJson getServerMessage() {
    return resourceJson;
  }

  public void setServerMessage(ResourceJson resourceJson) {
    this.resourceJson = resourceJson;
  }

  public boolean isDebugWorld() {
    return debugWorld;
  }

  public void setDebugWorld(boolean debugWorld) {
    this.debugWorld = debugWorld;
  }
}
