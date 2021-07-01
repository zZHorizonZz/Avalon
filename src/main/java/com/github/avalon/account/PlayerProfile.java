package com.github.avalon.account;

import com.github.avalon.data.Properties;

import java.util.UUID;

public class PlayerProfile {

  public static final int MAX_USERNAME_LENGTH = 16;

  private ProfileStatus profileStatus = ProfileStatus.GENERATED;

  private String name;
  private UUID uuid;

  private Properties properties;

  public PlayerProfile(String name, UUID uuid, ProfileStatus profileStatus) {
    this.name = name;
    this.uuid = uuid;
    this.profileStatus = profileStatus;
  }

  public PlayerProfile(String name, UUID uuid) {
    this.name = name;
    this.uuid = uuid;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public UUID getUUID() {
    return uuid;
  }

  public void setUUID(UUID uuid) {
    this.uuid = uuid;
  }

  public Properties getSkin() {
    return properties;
  }

  public void setProperties(Properties properties) {
    this.properties = properties;
  }

  public ProfileStatus getProfileStatus() {
    return profileStatus;
  }

  public void setProfileStatus(ProfileStatus profileStatus) {
    this.profileStatus = profileStatus;
  }
}
