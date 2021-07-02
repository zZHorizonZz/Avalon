package com.github.avalon.player;

import com.github.avalon.account.PlayerProfile;
import com.github.avalon.data.Transform;
import com.github.avalon.packet.packet.play.PacketEntityAction;
import com.github.avalon.packet.packet.play.PacketPlayerPositionAndRotation;

/**
 * Provides methods for dealing with player incoming packets. Handling movement packets, chat
 * packets, block manipulation packets, and so on.
 *
 * @author Horizon
 * @version 1.2
 */
public interface IActionHandler {

  //TODO Maybe better name?

  /**
   * Should be called by handle method of {@link
   * com.github.avalon.packet.packet.play.PacketPlayerPositionAndRotation}. Process the given
   * packet to it's {@link Transform} form validate the move and add it to the movement await list.
   *
   * @since 1.0
   * @param packet Incoming packet that will be processed.
   */
  void handleMovementIn(PacketPlayerPositionAndRotation packet);

  /**
   * Handles all outgoing packets about the movement. If server doesn't received any movement
   * packets to process this method will do nothing.
   *
   * @since 1.1
   */
  void handleMovementOut();

  /**
   * Handles all incoming actions from client. Action are based on bukkit wiki and you can found
   * them in the {@link com.github.avalon.player.attributes.Action} class.
   *
   * @since 1.2
   */
  void handleAction(PacketEntityAction packet);

  /**
   * Handles the player's login, runs all necessary operations. This method is called online once by
   * {@link com.github.avalon.account.ProfileCallback} or if player is not in online mode then
   * it's called by {@link com.github.avalon.packet.packet.login.PacketLoginIn}.
   *
   * @since 1.1
   * @param profile Profile that will be handle should not be null.
   */
  void handleLogin(PlayerProfile profile);

  /**
   * This method is called when player connection is active.
   *
   * @since 1.1
   * @param pingId Id of the ping.
   */
  void handlePong(long pingId);

  /**
   * Handles the disconnection of the player from the server and safely removes him from all
   * currently running operations.
   *
   * @param reason Reason of disconnection.
   */
  void handleDisconnect(String reason);

  void handleChat();
}
