package com.github.avalon.player;

import com.github.avalon.account.PlayerProfile;
import com.github.avalon.character.character.CharacterLiving;
import com.github.avalon.chat.command.ChatOperator;
import com.github.avalon.common.math.Vector2;
import com.github.avalon.data.Transform;
import com.github.avalon.dimension.chunk.IChunk;
import com.github.avalon.dimension.dimension.Dimension;
import com.github.avalon.inventory.Inventory;
import com.github.avalon.inventory.inventory.PlayerInventory;
import com.github.avalon.player.attributes.*;
import com.github.avalon.server.Server;

import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * This interface provides all methods that can be useful for player management.
 *
 * @author Horizon
 * @version 1.1
 */
public interface IPlayer extends IPlayerSession, ChatOperator {

  /**
   * Should send packet about player teleportation to specified location.
   *
   * @since 1.1
   * @param destination Location that will be player teleported to.
   */
  void teleport(Transform destination);

  /**
   * Rather than this method we should use {@code teleport(Transform destination)} method. This
   * method sets set location of player on server side. But on client side nothing happens so we
   * should not use this otherwise desynchronization can occur.
   *
   * @param transform Location that will be set.
   */
  void setLocation(Transform transform);

  /**
   * Handles the disconnection of player from the server.
   *
   * @since 1.1
   * @param reason Reason of disconnection.
   */
  void disconnect(String reason);

  /**
   * Sets the current players {@link Dimension}. Should be called after the player receive packet
   * about transport to another {@link Dimension}.
   *
   * @param dimension Dimension
   */
  void setDimension(Dimension dimension);

  Map<Integer, Inventory> getInventories();

  void openInventory(Inventory inventory);

  void closeInventory(int identifier);

  PlayerInventory getInventory();

  void setPlayerProfile(PlayerProfile profile);

  void setPlayerStatus(Status status);

  void setControllingCharacter(CharacterLiving character);

  void setSneaking(boolean sneaking);

  long getLifeTime();

  boolean isSneaking();

  IActionHandler getActionHandler();

  Status getPlayerStatus();

  List<Vector2> getChunkView();

  IChunk getCurrentChunk();

  CharacterLiving getControllingCharacter();

  Queue<Transform> getIncomingMovements();

  Transform getLocation();

  Dimension getDimension();

  @Override
  Server getServer();

  PlayerProfile getPlayerProfile();

  PlayerSettings getSettings();

  PlayerAttributes getPlayerAttributes();

  void setGameMode(GameMode gameMode);

  void showDemoEvent(GameState.DemoEvent demoEvent);
}
