package com.github.avalon.player.attributes;

import com.github.avalon.language.Language;

/**
 * IPlayer settings represents settings sent by client to the server. Settings includes Client view
 * distance, client language, chat mode, color of the chat. And part of skin that are being showed.
 *
 * @author Horizon
 * @version 1.0
 */
public class PlayerSettings implements Cloneable {

  private int viewDistance = 8;

  private Language locale = Language.UNKNOWN;
  private ChatMode chatMode = ChatMode.ENABLED;
  private boolean colors = true;

  private boolean rightHand = true;

  private boolean cape = true;
  private boolean jacket = true;
  private boolean leftSleeve = true;
  private boolean rightSleeve = true;
  private boolean leftPants = true;
  private boolean rightPants = true;
  private boolean hat = true;

  public PlayerSettings() {}

  public PlayerSettings(
          int viewDistance,
          Language locale,
          ChatMode chatMode,
          boolean colors,
          boolean rightHand,
          boolean cape,
          boolean jacket,
          boolean leftSleeve,
          boolean rightSleeve,
          boolean leftPants,
          boolean rightPants,
          boolean hat) {
    this.viewDistance = viewDistance;
    this.locale = locale;
    this.chatMode = chatMode;
    this.colors = colors;
    this.rightHand = rightHand;
    this.cape = cape;
    this.jacket = jacket;
    this.leftSleeve = leftSleeve;
    this.rightSleeve = rightSleeve;
    this.leftPants = leftPants;
    this.rightPants = rightPants;
    this.hat = hat;
  }

  public int getViewDistance() {
    return viewDistance;
  }

  public void setViewDistance(int viewDistance) {
    this.viewDistance = viewDistance;
  }

  public Language getLocale() {
    return locale;
  }

  public void setLocale(Language locale) {
    this.locale = locale;
  }

  public ChatMode getChatMode() {
    return chatMode;
  }

  public void setChatMode(ChatMode chatMode) {
    this.chatMode = chatMode;
  }

  public boolean isColors() {
    return colors;
  }

  public void setColors(boolean colors) {
    this.colors = colors;
  }

  public boolean isRightHand() {
    return rightHand;
  }

  public void setRightHand(boolean rightHand) {
    this.rightHand = rightHand;
  }

  public boolean hasCape() {
    return cape;
  }

  public void setCape(boolean cape) {
    this.cape = cape;
  }

  public boolean hasJacket() {
    return jacket;
  }

  public void setJacket(boolean jacket) {
    this.jacket = jacket;
  }

  public boolean hasLeftSleeve() {
    return leftSleeve;
  }

  public void setLeftSleeve(boolean leftSleeve) {
    this.leftSleeve = leftSleeve;
  }

  public boolean hasRightSleeve() {
    return rightSleeve;
  }

  public void setRightSleeve(boolean rightSleeve) {
    this.rightSleeve = rightSleeve;
  }

  public boolean hasLeftPants() {
    return leftPants;
  }

  public void setLeftPants(boolean leftPants) {
    this.leftPants = leftPants;
  }

  public boolean hasRightPants() {
    return rightPants;
  }

  public void setRightPants(boolean rightPants) {
    this.rightPants = rightPants;
  }

  public boolean hasHat() {
    return hat;
  }

  public void setHat(boolean hat) {
    this.hat = hat;
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    PlayerSettings settings = new PlayerSettings();
    settings.setLocale(locale);
    settings.setChatMode(chatMode);
    settings.setColors(colors);
    settings.setRightHand(rightHand);
    settings.setHat(hat);
    settings.setLeftPants(leftPants);
    settings.setRightPants(rightPants);
    settings.setCape(cape);
    settings.setJacket(jacket);
    settings.setLeftSleeve(leftSleeve);
    settings.setRightSleeve(rightSleeve);
    return settings;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;

    PlayerSettings settings = (PlayerSettings) object;

    if (getViewDistance() != settings.getViewDistance()) return false;
    if (isColors() != settings.isColors()) return false;
    if (isRightHand() != settings.isRightHand()) return false;
    if (hasCape() != settings.hasCape()) return false;
    if (hasJacket() != settings.hasJacket()) return false;
    if (hasLeftSleeve() != settings.hasLeftSleeve()) return false;
    if (hasRightSleeve() != settings.hasRightSleeve()) return false;
    if (hasLeftPants() != settings.hasLeftPants()) return false;
    if (hasRightPants() != settings.hasRightPants()) return false;
    if (hasHat() != settings.hasHat()) return false;
    if (getLocale() != settings.getLocale()) return false;
    return getChatMode() == settings.getChatMode();
  }

  @Override
  public int hashCode() {
    int result = getViewDistance();
    result = 31 * result + (getLocale() != null ? getLocale().hashCode() : 0);
    result = 31 * result + (getChatMode() != null ? getChatMode().hashCode() : 0);
    result = 31 * result + (isColors() ? 1 : 0);
    result = 31 * result + (isRightHand() ? 1 : 0);
    result = 31 * result + (hasCape() ? 1 : 0);
    result = 31 * result + (hasJacket() ? 1 : 0);
    result = 31 * result + (hasLeftSleeve() ? 1 : 0);
    result = 31 * result + (hasRightSleeve() ? 1 : 0);
    result = 31 * result + (hasLeftPants() ? 1 : 0);
    result = 31 * result + (hasRightPants() ? 1 : 0);
    result = 31 * result + (hasHat() ? 1 : 0);
    return result;
  }

  @Override
  public String toString() {
    return "PlayerSettings: "
        + " ViewDistance:"
        + viewDistance
        + ", Locale:"
        + locale
        + ", ChatMode:"
        + chatMode
        + ", Colors:"
        + colors
        + ", Right Hand: "
        + rightHand
        + ", Cape:"
        + cape
        + ", Jacket:"
        + jacket
        + ", Left Sleeve:"
        + leftSleeve
        + ", Right Sleeve:"
        + rightSleeve
        + ", Left Pants:"
        + leftPants
        + ", Right Pants:"
        + rightPants
        + ", Hat:"
        + hat;
  }
}
