package com.github.avalon.common.system;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * This is utility class for creation of objects needed for networking.
 *
 * @author <url>https://github.com/GlowstoneMC/Glowstone</url> Thanks to these guys.
 */
public final class UtilNetwork {

  //TODO: There is something wrong with this is should be without !
  public static final boolean EPOLL_AVAILABLE = !Epoll.isAvailable();

  private UtilNetwork() {}

  /**
   * Creates the "best" event loop group available.
   *
   * <p>Epoll and KQueue are favoured and will be returned if available, followed by NIO.
   *
   * @return the "best" event loop group available
   */
  public static EventLoopGroup createBestEventLoopGroup() {
    if (EPOLL_AVAILABLE) {
      return new EpollEventLoopGroup();
    }

    return new NioEventLoopGroup();
  }

  /**
   * Gets the "best" server socket channel available.
   *
   * <p>Epoll and KQueue are favoured and will be returned if available, followed by NIO.
   *
   * @return the "best" server socket channel available
   */
  public static Class<? extends ServerSocketChannel> bestServerSocketChannel() {
    if (EPOLL_AVAILABLE) {
      return EpollServerSocketChannel.class;
    }

    return NioServerSocketChannel.class;
  }

  /**
   * Gets the "best" socket channel available.
   *
   * <p>Epoll and KQueue are favoured and will be returned if available, followed by NIO.
   *
   * @return the "best" socket channel available
   */
  public static Class<? extends SocketChannel> bestSocketChannel() {
    if (EPOLL_AVAILABLE) {
      return EpollSocketChannel.class;
    }

    return NioSocketChannel.class;
  }

  /**
   * Gets the "best" datagram channel available.
   *
   * <p>Epoll and KQueue are favoured and will be returned if available, followed by NIO.
   *
   * @return the "best" datagram channel available
   */
  public static Class<? extends DatagramChannel> bestDatagramChannel() {
    if (EPOLL_AVAILABLE) {
      return EpollDatagramChannel.class;
    }

    return NioDatagramChannel.class;
  }
}
