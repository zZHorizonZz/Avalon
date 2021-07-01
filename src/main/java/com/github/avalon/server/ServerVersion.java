package com.github.avalon.server;

public class ServerVersion {

  private static String gameVersion = "1.16.4";
  private static String supportedVersion = "1.16.4";

  private static int protocolVersion = 754;
  private static int supportedProtocolVersion = 754;

  public boolean isProtocolSupported(int protocolVersion) {
    return protocolVersion <= ServerVersion.getProtocolVersion()
        && protocolVersion >= ServerVersion.getSupportedProtocolVersion();
  }

  public static void setGameVersion(String gameVersion) {
    ServerVersion.gameVersion = gameVersion;
  }

  public static void setSupportedVersion(String supportedVersion) {
    ServerVersion.supportedVersion = supportedVersion;
  }

  public static void setProtocolVersion(int protocolVersion) {
    ServerVersion.protocolVersion = protocolVersion;
  }

  public static void setSupportedProtocolVersion(int supportedProtocolVersion) {
    ServerVersion.supportedProtocolVersion = supportedProtocolVersion;
  }

  public static String getGameVersion() {
    return gameVersion;
  }

  public static String getSupportedVersion() {
    return supportedVersion;
  }

  public static int getProtocolVersion() {
    return protocolVersion;
  }

  public static int getSupportedProtocolVersion() {
    return supportedProtocolVersion;
  }
}
