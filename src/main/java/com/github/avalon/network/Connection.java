package com.github.avalon.network;

import com.flowpowered.network.AsyncableMessage;
import com.flowpowered.network.Message;
import com.flowpowered.network.MessageHandler;
import com.flowpowered.network.protocol.AbstractProtocol;
import com.flowpowered.network.session.BasicSession;
import com.github.avalon.console.logging.DefaultLogger;
import com.github.avalon.network.handler.CodecsHandler;
import com.github.avalon.network.handler.CompressionHandler;
import com.github.avalon.network.handler.EncryptionHandler;
import com.github.avalon.network.protocol.ProtocolRegistry;
import com.github.avalon.packet.packet.login.PacketCompression;
import com.github.avalon.player.ConnectionHandler;
import com.github.avalon.player.IPlayer;
import com.github.avalon.player.attributes.Status;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.CodecException;

import javax.crypto.SecretKey;
import java.net.InetSocketAddress;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * IPlayer connection is responsible for handling incoming and outgoing packets. Also handles
 * enabling of encryption only if player is in online mode.
 *
 * @version 1.1
 */
public abstract class Connection extends BasicSession implements ConnectionHandler {

  public static final DefaultLogger LOGGER = new DefaultLogger(Connection.class);

  private final IPlayer player;
  private final Queue<Message> messageQueue = new ConcurrentLinkedDeque<>();

  private final InetSocketAddress playerAddress;
  private volatile boolean compressionSent;

  protected Connection(IPlayer player, Channel channel) {
    super(channel, player.getServer().getProtocolContainer().getProtocol(ProtocolType.HANDSHAKE));

    this.player = player;
    playerAddress = getAddress();
  }

  @Override
  public void handleMessages() {
    Message message;
    while ((message = messageQueue.poll()) != null) {
      if (player.getPlayerStatus() != Status.ONLINE) {
        break;
      }

      super.messageReceived(message);
    }

    if (player.getPlayerStatus() != Status.ONLINE) {
      player.getConnectionManager().sessionInactivated(this);
    }
  }

  @Override
  public void enableEncryption(SecretKey sharedSecret) {
    updatePipeline("encryption", new EncryptionHandler(sharedSecret));
  }

  @Override
  public void enableCompression(int threshold) {
    if (!compressionSent) {
      send(new PacketCompression(threshold));
      updatePipeline("compression", new CompressionHandler(threshold));
      compressionSent = true;
    }
  }

  @Override
  public void updatePipeline(String key, ChannelHandler handler) {
    getChannel().pipeline().replace(key, key, handler);
  }

  @Override
  public void sendAndRelease(Message message, ByteBuf buf) {
    sendWithFuture(message).addListener(future -> buf.release());
  }

  @Override
  public void sendAndRelease(Message message, ByteBuf... bufs) {
    sendWithFuture(message)
        .addListener(
            future -> {
              for (ByteBuf buf : bufs) {
                buf.release();
              }
            });
  }

  @Override
  public IPlayer getPlayer() {
    return player;
  }

  @Override
  public ChannelFuture sendWithFuture(Message message) {
    if (!isActive()) {
      return null;
    }
    return super.sendWithFuture(message);
  }

  @Override
  public void setProtocol(AbstractProtocol protocol) {
    getChannel().flush();

    updatePipeline("codecs", new CodecsHandler(player.getServer(), (ProtocolRegistry) protocol));
    super.setProtocol(protocol);
  }

  @Override
  public void onDisconnect() {
    player.setPlayerStatus(Status.DISCONNECTED);
  }

  @Override
  public void messageReceived(Message message) {
    if (message instanceof AsyncableMessage && ((AsyncableMessage) message).isAsync()) {
      super.messageReceived(message);
    } else {
      messageQueue.add(message);
    }
  }

  @Override
  public void onInboundThrowable(Throwable throwable) {
    throwable.printStackTrace();
    if (throwable instanceof CodecException) {
      LOGGER.error("Network error occurred on input.", throwable);
    } else {
      disconnect("An error occurred when receiving a packet from you.");
    }
  }

  @Override
  public void onOutboundThrowable(Throwable throwable) {
    throwable.printStackTrace();
    if (throwable instanceof CodecException) {
      LOGGER.error("Network error occurred on output.", throwable);
    } else {
      disconnect("An error occurred when sending a packet to you.");
    }
  }

  @Override
  public void onHandlerThrowable(
      Message message, MessageHandler<?, ?> handler, Throwable throwable) {
    LOGGER.error(
        "Error occurred while handling %s by handler %s.",
        throwable, message, handler.getClass().getSimpleName());
  }

  @Override
  public String toString() {
    return "[ " + playerAddress + " ]";
  }

  @Override
  public InetSocketAddress getPlayerAddress() {
    return playerAddress;
  }
}
