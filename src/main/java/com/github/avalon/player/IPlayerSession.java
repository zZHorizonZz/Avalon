package com.github.avalon.player;

import com.flowpowered.network.ConnectionManager;
import com.github.avalon.network.ProtocolType;
import com.github.avalon.packet.PacketBatch;
import com.github.avalon.packet.packet.Packet;
import com.github.avalon.server.Server;

import java.net.InetSocketAddress;
import java.util.LinkedList;

/**
 * This interface represents provides methods that should be used to manage player connection.
 *
 * @author Horizon
 * @version 1.0
 */
public interface IPlayerSession {

  /**
   * Tick is called by the server every 50 milliseconds by default. This method should handle player
   * movement, reading messages from the client and some things that are required to be called every
   * tick.
   *
   * @since 1.1
   */
  void tick();

  /**
   * This method is called when player connection is idle.
   *
   * @since 1.0
   */
  void idle();

  /**
   * This method should send packet to the player. With method {@code super.send(packet)}
   *
   * @since 1.0
   * @param packet LegacyPacket to send.
   */
  void sendPacket(Packet<?> packet);

  /**
   * Sends {@link PacketBatch} to player in one call. Basically {@link PacketBatch} is just {@link
   * LinkedList} that contains {@link Packet}.
   *
   * @since 1.0
   * @param batch Batch of packets to send.
   */
  void sendPacket(PacketBatch batch);

  /**
   * Sets the protocol of players channel.
   *
   * @since 1.0
   * @param type Type of protocol that will player use.
   */
  void setProtocol(ProtocolType type);

  void setVersion(int version);

  void setVerifyToken(byte[] token);

  void setVerifyUsername(String username);

  void setVirtualHost(InetSocketAddress address);

  int getVersion();

  byte[] getVerifyToken();

  String getVerifyUsername();

  InetSocketAddress getVirtualHost();

  ConnectionManager getConnectionManager();

  /** @return Returns player connection handler. */
  PlayerConnection getConnection();

  /** @return The {@link Server} */
  Server getServer();
}
