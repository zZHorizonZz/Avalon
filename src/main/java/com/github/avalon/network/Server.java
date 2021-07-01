package com.github.avalon.network;

import com.github.avalon.server.NetworkServer;
import io.netty.channel.ChannelFuture;

import java.net.InetSocketAddress;
import java.util.concurrent.CountDownLatch;

/**
 * Represents a network packet server that handles communication between server and client. Modified
 * implementation of {@link com.flowpowered.network.NetworkServer}.
 *
 * @author <url>https://github.com/GlowstoneMC/Glowstone</url> Thanks to these guys.
 */
public abstract class Server {

  private final NetworkServer server;
  protected CountDownLatch latch;

  /**
   * Creates an instance for the specified server.
   *
   * @param server the associated {@link NetworkServer}
   * @param latch The countdown latch used during server startup to wait for network server binding.
   */
  public Server(NetworkServer server, CountDownLatch latch) {
    this.server = server;
    this.latch = latch;
  }

  public abstract ChannelFuture bind(InetSocketAddress address);

  public void onBindSuccess(InetSocketAddress address) {
    latch.countDown();
  }

  public abstract void onBindFailure(InetSocketAddress address, Throwable t);

  public abstract void shutdown();

  public NetworkServer getServer() {
    return server;
  }
}
