package com.github.avalon.player.handler;

import com.github.avalon.block.BlockFace;
import com.github.avalon.data.Material;
import com.github.avalon.data.Transform;
import com.github.avalon.dimension.dimension.Dimension;
import com.github.avalon.item.Item;
import com.github.avalon.packet.PacketListener;
import com.github.avalon.packet.annotation.PacketHandler;
import com.github.avalon.packet.packet.play.PacketPlayerBlockPlace;
import com.github.avalon.packet.packet.play.PacketPlayerDigging;
import com.github.avalon.player.IPlayer;
import com.github.avalon.player.PlayerConnection;

public class BlockHandler implements PacketListener {

  @PacketHandler
  public void handleBlockPlace(PacketPlayerBlockPlace packet, PlayerConnection connection) {
    IPlayer player = connection.getPlayer();
    Dimension dimension = player.getDimension();
    BlockFace face = packet.getFace();
    Item blockItem =
        player.getInventory().getHotbarContest().getItem(player.getInventory().getCurrentSlot());
    Transform placeLocation = new Transform(dimension, packet.getPosition());
    placeLocation =
        placeLocation.setTransform(
            placeLocation.getBlockX() + face.getXOffset(),
            placeLocation.getBlockY() + face.getYOffset(),
            placeLocation.getBlockZ() + face.getZOffset());

    // TODO: Add counter cancel packet if blockitem is null or air.

    placeLocation
        .getChunk()
        .getProvider()
        .placeBlockAsPlayer(player, placeLocation, blockItem.getMaterial());

    player
        .getServer()
        .getChatManager()
        .broadcastMessage(
            "%#f01f23%%bold%Manager %#7cedf2%Avalon %gray%> Block has been placed at X:"
                + placeLocation.getBlockX()
                + " ,Y: "
                + placeLocation.getBlockY()
                + " ,Z: "
                + placeLocation.getBlockZ());
  }

  @PacketHandler
  public void handleBlockDig(PacketPlayerDigging packet, PlayerConnection connection) {
    IPlayer player = connection.getPlayer();
    Dimension dimension = player.getDimension();
    Transform placeLocation = new Transform(dimension, packet.getPosition());
    placeLocation.getChunk().getProvider().placeBlockAsPlayer(player, placeLocation, Material.AIR);

    player
        .getServer()
        .getChatManager()
        .broadcastMessage(
            "%#f01f23%%bold%Manager %#7cedf2%Avalon %gray%> Player digging block is now "
                + placeLocation.getChunk().getProvider().getMaterial(placeLocation));
  }
}
