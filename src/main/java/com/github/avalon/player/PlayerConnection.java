package com.github.avalon.player;

import com.github.avalon.chat.message.ChatColor;
import com.github.avalon.network.Connection;
import com.github.avalon.network.ProtocolType;
import com.github.avalon.packet.packet.play.PacketKick;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

/**
 * IPlayer connection is responsible for handling incoming and outgoing packets. Also handles
 * enabling of encryption only if player is in online mode.
 *
 * @author Horizon
 * @version 1.0
 */
public class PlayerConnection extends Connection {

  private ProtocolType protocolType = ProtocolType.HANDSHAKE;

  public PlayerConnection(IPlayer player, Channel channel) {
    super(player, channel);
  }

  @Override
  public void disconnect(String reason) {
    if (isActive()
        && (protocolType.equals(ProtocolType.LOGIN) || protocolType.equals(ProtocolType.PLAY))) {
      Connection.LOGGER.info("[%s] Session was been closed. Reason: %s", this, reason);

      ChannelFuture future;
      if (protocolType.equals(ProtocolType.LOGIN)) {
        future =
            sendWithFuture(
                new com.github.avalon.packet.packet.login.PacketKick(
                    ChatColor.toChat(reason)));
      } else {
        future = sendWithFuture(new PacketKick(ChatColor.toChat(reason)));
      }

      if (future != null) {
        future.addListener(ChannelFutureListener.CLOSE);
      }
    } else {
      getChannel().close();
    }
  }

  @Override
  public void disconnect() {
    disconnect("Reason of the kick was not been specified.");
  }

  @Override
  public void setCurrentProtocol(ProtocolType type) {
    protocolType = type;
  }

  @Override
  public ProtocolType getProtocolType() {
    return protocolType;
  }
}
