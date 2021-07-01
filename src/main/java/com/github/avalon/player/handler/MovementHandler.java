package com.github.avalon.player.handler;

import com.github.avalon.data.Transform;
import com.github.avalon.packet.PacketListener;
import com.github.avalon.packet.annotation.PacketHandler;
import com.github.avalon.packet.packet.play.PacketPlayerPosition;
import com.github.avalon.packet.packet.play.PacketPlayerPositionAndRotation;
import com.github.avalon.player.IPlayer;
import com.github.avalon.player.PlayerConnection;

public class MovementHandler implements PacketListener {

  @PacketHandler
  public void handlePlayerPositionAndRotation(
      PacketPlayerPositionAndRotation packet, PlayerConnection connection) {
    IPlayer player = connection.getPlayer();

    double x = packet.getX();
    double y = packet.getY();
    double z = packet.getZ();
    float yaw = packet.getYaw();
    float pitch = packet.getPitch();

    player.setLocation(player.getLocation().setTransform(x, y, z, yaw, pitch));
    Transform moveTransform = new Transform(player.getDimension(), x, y, z, yaw, pitch);
    player.getIncomingMovements().offer(moveTransform);
  }

  @PacketHandler
  public void handlePlayerPosition(
          PacketPlayerPosition packet, PlayerConnection connection) {
    IPlayer player = connection.getPlayer();

    double x = packet.getX();
    double y = packet.getY();
    double z = packet.getZ();

    player.setLocation(player.getLocation().setTransform(x, y, z));
    Transform moveTransform = new Transform(player.getDimension(), x, y, z);
    player.getIncomingMovements().offer(moveTransform);
  }
}
