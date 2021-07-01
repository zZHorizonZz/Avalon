package com.github.avalon.player;

import com.flowpowered.network.Message;
import com.github.avalon.network.ProtocolType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;

import javax.crypto.SecretKey;
import java.net.InetSocketAddress;

public interface ConnectionHandler {
    void handleMessages();

    void enableEncryption(SecretKey sharedSecret);

    /**
     * Enables compression if not already enabled.
     *
     * @param threshold the minimum message size in bytes to compress
     */
    void enableCompression(int threshold);

    void updatePipeline(String key, ChannelHandler handler);

    /**
     * Send the message and release the specified byte buffer after it is sent.
     *
     * @param message The message.
     * @param buf The byte buffer.
     */
    void sendAndRelease(Message message, ByteBuf buf);

    /**
     * Send the message and release the specified byte buffers after it is sent.
     *
     * @param message The message.
     * @param bufs The byte buffers.
     */
    void sendAndRelease(Message message, ByteBuf... bufs);

    void setCurrentProtocol(ProtocolType type);

    ProtocolType getProtocolType();

    void disconnect(String reason);

    IPlayer getPlayer();

    InetSocketAddress getPlayerAddress();
}
