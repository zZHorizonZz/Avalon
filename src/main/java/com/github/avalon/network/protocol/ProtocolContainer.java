package com.github.avalon.network.protocol;

import com.github.avalon.network.ProtocolType;
import com.github.avalon.server.IServer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Protocol container stores and provides {@link ProtocolRegistry} that stores all registered {@link
 * com.github.avalon.packet.packet.Packet} under the given operational codes.
 *
 * @version 1.0
 */
public class ProtocolContainer {

  private final IServer server;
  private final Map<ProtocolType, ProtocolRegistry> protocols;

  public ProtocolContainer(IServer server) {
    this.server = server;

    protocols = new ConcurrentHashMap<>();
  }

  public void addProtocolRegistry(ProtocolRegistry registry) {
    protocols.put(registry.getProtocolType(), registry);
  }

  public ProtocolRegistry getProtocol(ProtocolType type) {
    return protocols.get(type);
  }

  public IServer getServer() {
    return server;
  }

  public Map<ProtocolType, ProtocolRegistry> getProtocols() {
    return protocols;
  }
}
