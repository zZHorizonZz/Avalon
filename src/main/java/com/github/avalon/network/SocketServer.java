package com.github.avalon.network;

import com.flowpowered.network.ConnectionManager;
import com.flowpowered.network.session.Session;
import com.github.avalon.common.system.UtilNetwork;
import com.github.avalon.console.logging.DefaultLogger;
import com.github.avalon.player.Player;
import com.github.avalon.player.PlayerConnection;
import com.github.avalon.server.Server;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;

import java.net.InetSocketAddress;
import java.util.concurrent.CountDownLatch;

/**
 * Network socket server implements {@link ConnectionManager} and create new {@link
 * io.netty.channel.socket.SocketChannel} through {@link ServerBootstrap}. Binding of the {@link
 * ServerBootstrap} must be onResponse through {@code bind(InetSocketAddress address)}
 *
 * @author Horizon
 * @version 1.0
 */
public class SocketServer implements ConnectionManager {

  public static final DefaultLogger LOGGER = new DefaultLogger(SocketServer.class);

  private final Server server;
  protected CountDownLatch latch;

  protected final EventLoopGroup bossGroup;
  protected final EventLoopGroup workerGroup;
  protected final ServerBootstrap bootstrap;
  protected Channel channel;

  /**
   * Creates an instance for the specified server.
   *
   * @param server the associated {@link Server}
   * @param latch The countdown latch used during server startup to wait for network server binding.
   */
  public SocketServer(Server server, CountDownLatch latch) {
    this.server = server;
    this.latch = latch;

    bossGroup = UtilNetwork.createBestEventLoopGroup();
    workerGroup = UtilNetwork.createBestEventLoopGroup();
    bootstrap = new ServerBootstrap();

    bootstrap
        .group(bossGroup, workerGroup)
        .channel(UtilNetwork.bestServerSocketChannel())
        .childOption(ChannelOption.TCP_NODELAY, true)
        .childOption(ChannelOption.SO_KEEPALIVE, true)
        .childHandler(new PlayerConnectionInitializer(this));
  }

  @Override
  public PlayerConnection newSession(Channel channel) {
    LOGGER.info("Opening new channel on address %s", channel.remoteAddress().toString());
    Player player = new Player(server, channel, this);
    return player.getConnection();
  }

  @Override
  public void sessionInactivated(Session session) {
    server.getPlayerSessionRegistry().remove(((PlayerConnection) session).getPlayer());
  }

  @Override
  public void shutdown() {
    LOGGER.info("Stopping socket server...");
    channel.close();
    bootstrap.group().shutdownGracefully();
    bootstrap.childGroup().shutdownGracefully();

    try {
      bootstrap.group().terminationFuture().sync();
      bootstrap.childGroup().terminationFuture().sync();
    } catch (InterruptedException exception) {
      LOGGER.error("Shutdown process of socket server was been interrupted!", exception);
    } finally {
      LOGGER.info("Socket server has been successfully stopped.");
    }
  }

  public ChannelFuture bind(InetSocketAddress address) {
    LOGGER.info("Binding the server address...");
    ChannelFuture channelFuture =
        bootstrap
            .bind(address)
            .addListener(
                future -> {
                  if (future.isSuccess()) {
                    onBindSuccess(address);
                  } else {
                    onBindFailure(address, future.cause());
                  }
                });
    channel = channelFuture.channel();
    return channelFuture;
  }

  public void onBindSuccess(InetSocketAddress address) {
    server.getServerData().setPort(address.getPort());
    server.getServerData().setHostname(address.getHostString());
    latch.countDown();
    LOGGER.info("Server successfully bound to address.");
  }

  public void onBindFailure(InetSocketAddress address, Throwable t) {
    System.exit(1);
  }

  public Server getServer() {
    return server;
  }
}
