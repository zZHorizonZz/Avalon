package com.github.avalon.packet.packet.status;

import com.github.avalon.chat.message.ChatColor;
import com.github.avalon.common.status.ServerStatusFactory;
import com.github.avalon.network.ProtocolType;
import com.github.avalon.packet.annotation.PacketRegister;
import com.github.avalon.packet.packet.Packet;
import com.github.avalon.packet.schema.PacketStrategy;
import com.github.avalon.player.IPlayer;
import com.github.avalon.player.PlayerConnection;
import com.github.avalon.server.ServerVersion;

/**
 * Packet status request is sent by client as a request of {@link PacketStatusResponse} that
 * contains the information about server converted to json.
 *
 * <h3>Packet Strategy</h3>
 *
 * <ul>
 *   <li>1. Json response.
 * </ul>
 *
 * @version 1.1
 */
@PacketRegister(
    operationCode = 0x00,
    protocolType = ProtocolType.STATUS,
    direction = PacketRegister.Direction.SERVER)
public class PacketStatusRequest extends Packet<PacketStatusRequest> {

  public PacketStrategy strategy = new PacketStrategy();

  public PacketStatusRequest() {}

  @Override
  public boolean isAsync() {
    return true;
  }

  @Override
  public void handle(PlayerConnection connection, PacketStatusRequest packet) {
    IPlayer player = connection.getPlayer();
    ServerStatusFactory statusFactory = new ServerStatusFactory();
    statusFactory.setGameVersion(ServerVersion.getGameVersion());
    statusFactory.setProtocolVersion(ServerVersion.getProtocolVersion());

    statusFactory.setMaximumPlayers(128);
    statusFactory.setCurrentPlayers(64);
    statusFactory.setPlayerSample(null);

    statusFactory.setMotd(ChatColor.toChat("%#42e362%%bold%Toast %reset%%#2abd47%is best... and kinda fun"));
    statusFactory.setFavicon(null);

    player.sendPacket(new PacketStatusResponse(statusFactory));
  }

  @Override
  public PacketStrategy getStrategy() {
    return strategy;
  }
}
