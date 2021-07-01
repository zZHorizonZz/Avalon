package com.github.avalon.network;

import com.github.avalon.console.logging.DefaultLogger;
import com.github.avalon.network.handler.CodecsHandler;
import com.github.avalon.network.handler.FramingHandler;
import com.github.avalon.network.handler.NoopHandler;
import com.github.avalon.packet.PlayerConnectionHandler;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * This class provides channel initialization and setup of {@link SocketChannel}
 *
 * @version 1.0
 */
public class PlayerConnectionInitializer extends ChannelInitializer<SocketChannel> {

  public static final DefaultLogger LOGGER = new DefaultLogger(SocketServer.class);

  private static final int READ_IDLE_TIMEOUT = 20;
  private static final int WRITE_IDLE_TIMEOUT = 15;

  private final SocketServer connectionManager;

  public PlayerConnectionInitializer(SocketServer connectionManager) {
    this.connectionManager = connectionManager;
  }

  @Override
  protected void initChannel(SocketChannel channel) {
    try {
      channel.config().setOption(ChannelOption.IP_TOS, 0x18);
    } catch (ChannelException exception) {
      LOGGER.error("Operation system does not support IP_TOS.", exception);
    }

    ChannelPipeline pipeline = channel.pipeline();

    pipeline.addLast(
        "idle_timeout", new IdleStateHandler(READ_IDLE_TIMEOUT, WRITE_IDLE_TIMEOUT, 0));
    pipeline.addLast("encryption", NoopHandler.INSTANCE);
    pipeline.addLast("framing", new FramingHandler());
    pipeline.addLast("compression", NoopHandler.INSTANCE);
    pipeline.addLast(
        "codecs",
        new CodecsHandler(
            connectionManager.getServer(),
            connectionManager
                .getServer()
                .getProtocolContainer()
                .getProtocol(ProtocolType.HANDSHAKE)));
    pipeline.addLast("handler", new PlayerConnectionHandler(connectionManager));
  }
}
