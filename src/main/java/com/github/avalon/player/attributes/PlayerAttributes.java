package com.github.avalon.player.attributes;

/**
 * Player attributes contains all attributes that are provided to the player through packet {@link
 * com.github.avalon.packet.packet.play.PacketPlayerAbilities}.
 *
 * @version 1.0
 * @author Horizon
 */
public class PlayerAttributes {

  private boolean invulnerable;
  private boolean flying = true;
  private boolean allowedFlying = true;

  private float flyingSpeed = 0.05f;
  private float fieldOfViewModifier = 0.1f;

  private int viewDistance = 5;

  private GameMode gameMode = GameMode.CREATIVE;
  private GameMode previousGameMode = GameMode.UNKNOWN;

  public boolean isInvulnerable() {
    return invulnerable;
  }

  public void setInvulnerable(boolean invulnerable) {
    this.invulnerable = invulnerable;
  }

  public boolean isFlying() {
    return flying;
  }

  public void setFlying(boolean flying) {
    this.flying = flying;
  }

  public boolean hasAllowedFlying() {
    return allowedFlying;
  }

  public void setAllowedFlying(boolean allowedFlying) {
    this.allowedFlying = allowedFlying;
  }

  public float getFlyingSpeed() {
    return flyingSpeed;
  }

  public void setFlyingSpeed(float flyingSpeed) {
    this.flyingSpeed = flyingSpeed;
  }

  public float getFieldOfViewModifier() {
    return fieldOfViewModifier;
  }

  public void setFieldOfViewModifier(float fieldOfViewModifier) {
    this.fieldOfViewModifier = fieldOfViewModifier;
  }

  public GameMode getGameMode() {
    return gameMode;
  }

  public void setGameMode(GameMode gameMode) {
    this.gameMode = gameMode;
  }

  public GameMode getPreviousGameMode() {
    return previousGameMode;
  }

  public void setPreviousGameMode(GameMode previousGameMode) {
    this.previousGameMode = previousGameMode;
  }

  public int getViewDistance() {
    return viewDistance;
  }

  public void setViewDistance(int viewDistance) {
    this.viewDistance = viewDistance;
  }
}
