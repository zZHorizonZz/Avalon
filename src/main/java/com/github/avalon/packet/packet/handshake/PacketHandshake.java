package com.github.avalon.packet.packet.handshake;

import com.github.avalon.common.data.DataType;
import com.github.avalon.network.ProtocolType;
import com.github.avalon.packet.annotation.PacketRegister;
import com.github.avalon.packet.packet.Packet;
import com.github.avalon.packet.schema.FunctionScheme;
import com.github.avalon.packet.schema.PacketStrategy;
import com.github.avalon.player.IPlayer;
import com.github.avalon.player.PlayerConnection;
import com.github.avalon.server.ServerVersion;

import java.net.InetSocketAddress;

/**
 * Handshake packet is sent by client to the server to open connection between client and server.
 * Packet contains client's version, address, port and state.
 *
 * <h3>Packet Strategy</h3>
 *
 * <ul>
 *   <li>1. Version of protocol used by client.
 *   <li>2. Address of client.
 *   <li>3. Port of the client.
 *   <li>4. Address of client.
 * </ul>
 *
 * @version 1.1
 */
@PacketRegister(
    operationCode = 0x00,
    protocolType = ProtocolType.HANDSHAKE,
    direction = PacketRegister.Direction.SERVER)
public class PacketHandshake extends Packet<PacketHandshake> {

  public PacketStrategy strategy =
      new PacketStrategy(
          new FunctionScheme<>(DataType.VARINT, this::getVersion, this::setVersion),
          new FunctionScheme<>(DataType.STRING, this::getAddress, this::setAddress),
          new FunctionScheme<>(DataType.UNSIGNED_SHORT, this::getPort, this::setPort),
          new FunctionScheme<>(DataType.VARINT, this::getState, this::setState));

  private int version;
  private String address;
  private short port;
  private int state;

  public PacketHandshake(int version, String address, short port, int state) {
    this.version = version;
    this.address = address;
    this.port = port;
    this.state = state;
  }

  public PacketHandshake() {}

  @Override
  public void handle(PlayerConnection connection, PacketHandshake packet) {
    IPlayer player = connection.getPlayer();

    ProtocolType protocol;
    if (packet.getState() == 1) {
      protocol = ProtocolType.STATUS;
    } else if (packet.getState() == 2) {
      protocol = ProtocolType.LOGIN;
    } else {
      player.disconnect("Invalid state");
      return;
    }

    player.setVersion(packet.getVersion());
    player.setVirtualHost(
        InetSocketAddress.createUnresolved(packet.getAddress(), packet.getPort()));

    player.setProtocol(protocol);

    if (protocol.equals(ProtocolType.LOGIN)) {
      if (packet.getVersion() < ServerVersion.getSupportedProtocolVersion()) {
        player.disconnect("Outdated client! I'm running " + ServerVersion.getGameVersion());
      } else if (packet.getVersion() > ServerVersion.getProtocolVersion()) {
        player.disconnect("Outdated server! I'm running " + ServerVersion.getGameVersion());
      }
    }
  }

  @Override
  public boolean isAsync() {
    return true;
  }

  @Override
  public PacketStrategy getStrategy() {
    return strategy;
  }

  public int getVersion() {
    return version;
  }

  public void setVersion(int version) {
    this.version = version;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public short getPort() {
    return port;
  }

  public void setPort(short port) {
    this.port = port;
  }

  public int getState() {
    return state;
  }

  public void setState(int state) {
    this.state = state;
  }
}
