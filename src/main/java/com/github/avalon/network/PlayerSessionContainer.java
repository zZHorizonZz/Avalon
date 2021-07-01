package com.github.avalon.network;

import com.github.avalon.character.character.CharacterPlayer;
import com.github.avalon.data.Container;
import com.github.avalon.data.Transform;
import com.github.avalon.packet.PacketBatch;
import com.github.avalon.packet.packet.play.*;
import com.github.avalon.player.IPlayer;
import com.github.avalon.player.attributes.Status;

/**
 * This is the class that stores the players sessions.
 *
 * @author Glostone team with modifications by Horizon
 * @version 1.0
 */
public class PlayerSessionContainer extends Container<IPlayer, Boolean> {

  public PlayerSessionContainer() {}

  /**
   * Call tick method in all player sessions.
   *
   * @since 1.0
   */
  public synchronized void tick() {
    getRegistry().keySet().forEach(IPlayer::tick);
  }

  /**
   * Adds a new session.
   *
   * @param player The session to addPlayer.
   */
  public void addPlayer(IPlayer player) {
    Transform spawnLocation = player.getLocation();

    PacketJoinGame joinGame = new PacketJoinGame(player);
    PacketServerDifficulty serverDifficulty =
        new PacketServerDifficulty(spawnLocation.getDimension());
    PacketPlayerAbilities playerAbilities = new PacketPlayerAbilities(player);
    PacketHeldSlotChangeClient slotItemChange = new PacketHeldSlotChangeClient((byte) 0);
    PacketDeclareRecipe declareRecipe = new PacketDeclareRecipe();
    PacketTags tags = new PacketTags();
    PacketEntityStatus entityStatus =
        new PacketEntityStatus(1, CharacterPlayer.PlayerStatus.SET_OP_LEVEL1.getStatus());
    PacketSpawnPosition spawnPosition = new PacketSpawnPosition(spawnLocation);

    PacketBatch batch =
        new PacketBatch(
            joinGame,
            serverDifficulty,
            playerAbilities,
            slotItemChange,
            declareRecipe,
            tags,
            entityStatus,
            spawnPosition);

    player.sendPacket(batch);
    player.teleport(player.getLocation());

    spawnLocation
        .getDimension()
        .spawnControllableCharacter(
            new CharacterPlayer(
                player,
                1,
                spawnLocation,
                player.getPlayerProfile().getName(),
                null,
                player.getPlayerProfile()),
            player,
            spawnLocation);

    player.setPlayerStatus(Status.ONLINE);
    add(player, true);
  }

  /**
   * Removes a session.
   *
   * @param player The session to remove.
   */
  public void removePlayer(IPlayer player) {
    remove(player);
  }
}
