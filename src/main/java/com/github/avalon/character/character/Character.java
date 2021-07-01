package com.github.avalon.character.character;

import com.github.avalon.data.Transform;
import com.github.avalon.player.IPlayer;

/**
 * Default class for all character (entities) that includes their default attributes and methods.
 *
 * @author Horizon
 * @version 1.0
 */
public abstract class Character {

  private final int identifier;
  private String name;

  private Transform transform;

  public Character(int identifier, Transform transform) {
    this.identifier = identifier;
    this.transform = transform;
  }

  /**
   * Teleports entity to the specified location.
   *
   * @param player IPlayer that will receive the necessary packets.
   * @param transform Location for teleport.
   */
  public abstract void teleport(IPlayer player, Transform transform);

  /**
   * Spawns entity
   * @param player
   */
  public abstract void spawn(IPlayer player);

  public int getIdentifier() {
    return identifier;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Transform getTransform() {
    return transform;
  }

  public void setTransform(Transform transform) {
    this.transform = transform;
  }
}
