package com.github.avalon.packet;

import com.flowpowered.network.Message;
import com.github.avalon.network.SocketServer;
import com.github.avalon.player.PlayerConnection;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Thanks to Glowstone team for this implementation of experimental {@link
 * com.flowpowered.network.pipeline.MessageHandler}
 *
 * @author Glowstone team.
 */
public final class PlayerConnectionHandler extends SimpleChannelInboundHandler<Message> {

  private final AtomicReference<PlayerConnection> connection = new AtomicReference<>(null);
  private final SocketServer connectionManager;

  public PlayerConnectionHandler(SocketServer connectionManager) {
    this.connectionManager = connectionManager;
  }

  @Override
  public void channelActive(ChannelHandlerContext channelHandlerContext) {
    Channel channel = channelHandlerContext.channel();
    PlayerConnection connection = connectionManager.newSession(channel);

    assert this.connection.compareAndSet(null, connection)
        : "Session may not be set more than once";

    setConnection(connection);
    connection.onReady();
  }

  @Override
  public void channelInactive(ChannelHandlerContext channelHandlerContext) {
    connection.get().onDisconnect();
  }

  @Override
  protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message message) {
    connection.get().messageReceived(message);
  }

  @Override
  public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object event) {
    if (event instanceof IdleStateEvent) {
      connection.get().getPlayer().idle();
    }
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) {
    connection.get().onInboundThrowable(cause);
  }

  public PlayerConnection getConnection() {
    return connection.get();
  }

  public void setConnection(PlayerConnection connection) {
    this.connection.set(connection);
  }
}
