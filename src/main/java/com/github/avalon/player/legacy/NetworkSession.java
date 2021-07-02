package com.github.avalon.player.legacy;

/**
 * This is players network session that handles the communication between player and the server.
 * Also this class provides player session update method {@code tick()} that is in default called
 * every tick (50 milliseconds).
 *
 * @author Horizon
 * @version 1.0
 */
@Deprecated
public class NetworkSession {

  /*private final Server server;

  private final ConnectionManager connectionManager;

  private final PlayerConnection playerConnection;
  private volatile boolean online;

  private volatile byte[] verifyToken;

  private volatile String verifyUsername;
  private volatile InetSocketAddress virtualHost;

  private volatile int version = -1;

  private volatile IPlayer player;
  private volatile long pingMessageId;

  private volatile int previousPlacementTicks;

  /**
   * Data regarding a user who has connected through a proxy, used to provide online-mode UUID and
   * properties and other data even if the server is running in offline mode.
   *
   * <p>Null for non-proxied sessions.
   *
   * @return The proxy data to use, or null for an unproxied connection.
   */
  // private volatile ProxyData proxyData;

  /*public NetworkSession(
      Server server, Channel channel, ConnectionManager connectionManager) {
    this.server = server;
    this.connectionManager = connectionManager;
    this.playerConnection = new PlayerConnection(this, channel);
  }

  @Override
  public void tick() {
    playerConnection.handleMessages();

    if (player != null) {
      player.tick();
    }

    // check if the client is disconnected
    if (!isOnline()) {
      if (player == null) {
        return;
      }

      /*player.remove();

      Message userListMessage = UserListItemMessage.removeOne(player.getUniqueId());
      for (GlowPlayer player : server.getRawOnlinePlayers()) {
        if (player.canSee(this.player)) {
          player.getConnection().send(userListMessage);
        } else {
          player.stopHidingDisconnectedPlayer(this.player);
        }
      }

      GlowServer.logger.info(player.getName() + " [" + address + "] lost connection");

      if (player.isSleeping()) {
        player.leaveBed(false);
      }

      Collection<BossBar> bars;
      do {
        bars = player.getBossBars();
        for (BossBar bar : bars) {
          bar.removePlayer(player);
          player.removeBossBar(bar);
        }
      } while (!bars.isEmpty());

      String text = EventFactory.getInstance().onPlayerQuit(player).getQuitMessage();
      if (online && text != null && !text.isEmpty()) {
        server.broadcastMessage(text);
      }
      // statistics
      player.incrementStatistic(Statistic.LEAVE_GAME);
      for (IPlayer p : server.getOnlinePlayers()) {
        if (p.getUniqueId().equals(player.getUniqueId())) {
          continue;
        }
        GlowPlayer other = (GlowPlayer) p;
        if (!other.canSee(player)) {
          continue;
        }
        other
            .getConnection()
            .send(new DestroyEntitiesMessage(Collections.singletonList(player.getEntityId())));
      }
      player = null; // in case we are disposed twice*/
  /*   }
  }

  @Override
  public void idle() {
    if (
    /*pingMessageId == 0 && */
  /*playerConnection.getProtocol() instanceof PlayProtocol) {
  /*     pingMessageId = System.currentTimeMillis();
       sendPacket(new PacketKeepAlive(pingMessageId));
     } else {
       disconnect("Timed out");
     }
   }

   @Override
   public void pong(long pingId) {
     if (pingId == pingMessageId) {
       pingMessageId = 0;
     }
   }

   @Override
   public void sendPacket(LegacyPacket<?> packet) {
     ConsoleManager.logInfo("Sending LegacyPacket: %s", packet.getClass().getSimpleName());
     playerConnection.send(packet);
   }

   @Override
   public void sendPacket(PacketBatch batch) {
     batch.forEach(playerConnection::send);
   }

   /**
    * Sets the player associated with this session.
    *
    * @throws IllegalStateException if there is already a player associated with this session.
    */
  /* public void setPlayer(PlayerProfile profile) {
  if (player != null) {
    throw new IllegalStateException("Cannot set player twice");
  }

  // isActive check here in case player disconnected during authentication
  if (!playerConnection.isActive()) {
    // no need to call onDisconnect() since it only does anything if there's a player set
    return;
  }

  player = new BetaPlayer(profile, this);
  completeLogin(profile);
  getServer().getPlayerSessionRegistry().addPlayer(this);
  // initialize the player
  /*PlayerReader reader = server.getPlayerDataService().beginReadingData(profile.getId());
  player = new GlowPlayer(this, profile, reader);
  finalizeLogin(profile);

  // isActive check here in case player disconnected after authentication,
  // but before the GlowPlayer initialization was completed
  if (!isActive()) {
    reader.close();
    onDisconnect();
    return;
  }

  // Kick other players with the same UUID
  for (GlowPlayer other : getServer().getRawOnlinePlayers()) {
    if (other != player && other.getUniqueId().equals(player.getUniqueId())) {
      other.getConnection().disconnect("You logged in from another location.", true);
      break;
    }
  }

  // login event
  PlayerLoginEvent event =
      EventFactory.getInstance().onPlayerLogin(player, virtualHost.toString());
  if (event.getResult() != Result.ALLOWED) {
    disconnect(event.getKickMessage(), true);
    return;
  }

  // joins the player
  player.join(this, reader);

  player.getWorld().getRawPlayers().addPlayer(player);

  online = true;

  GlowServer.logger.info(
      player.getName()
          + " ["
          + address
          + "] connected, UUID: "
          + UuidUtils.toString(player.getUniqueId()));

  // message and user list
  String message = EventFactory.getInstance().onPlayerJoin(player).getJoinMessage();
  if (message != null && !message.isEmpty()) {
    server.broadcastMessage(message);
  }

  Message addMessage = new UserListItemMessage(Action.ADD_PLAYER, player.getUserListEntry());
  List<Entry> entries = new ArrayList<>();
  for (GlowPlayer other : server.getRawOnlinePlayers()) {
    if (other != player && other.canSee(player)) {
      other.getConnection().send(addMessage);
    }
    if (player.canSee(other)) {
      entries.addPlayer(other.getUserListEntry());
    }
  }
  send(new UserListItemMessage(Action.ADD_PLAYER, entries));
  send(server.createAdvancementsMessage(false, Collections.emptyList(), player));*/
  // }

  /**
   * Disconnects the session with the specified reason.
   *
   * <p>This causes a KickMessage to be sent. When it has been delivered, the channel is closed.
   *
   * @param reason The reason for disconnection.
   */
  /* public void disconnect(String reason) {
    disconnect(reason, false);
  }

  /**
   * Disconnects the session with the specified reason.
   *
   * <p>This causes a KickMessage to be sent. When it has been delivered, the channel is closed.
   *
   * @param reason The reason for disconnection.
   * @param overrideKick Whether to skip the kick event.
   */
  /* public void disconnect(String reason, boolean overrideKick) {
  /*if (player != null && !overrideKick) {
    PlayerKickEvent event = EventFactory.getInstance().onPlayerKick(player, reason);
    if (event.isCancelled()) {
      return;
    }

    reason = event.getReason();

    if (player.isOnline() && event.getLeaveMessage() != null) {
      server.broadcastMessage(event.getLeaveMessage());
    }
  }*/

  // log that the player was kicked
  /* if (player != null) {
    GlowServer.logger.info(player.getName() + " kicked: " + reason);
  } else {*/

  // }
  /*  playerConnection.disconnect(reason);
  }

  @Override
  public void setProtocol(AbstractProtocol protocol) {}

  @Override
  public Server getServer() {
    return server;
  }

  @Override
  public PlayerConnection getConnection() {
    return playerConnection;
  }

  /**
   * public void setProxyData(ProxyData proxyData) { this.proxyData = proxyData; address =
   * proxyData.getAddress(); virtualHost =
   * InetSocketAddress.createUnresolved(proxyData.getHostname(), virtualHost.getPort()); }/
   *
   * <p>/** Sets the version. Must only be called once.
   *
   * @param version the version
   * @throws IllegalStateException if the version has already been set
   */
  /*public void setVersion(int version) {
    if (this.version != -1) {
      throw new IllegalStateException("Cannot set version twice");
    }
    this.version = version;
  }

  public InetSocketAddress getVirtualHost() {
    return virtualHost;
  }

  public void setVirtualHost(InetSocketAddress virtualHost) {
    this.virtualHost = virtualHost;
  }

  public byte[] getVerifyToken() {
    return verifyToken;
  }

  public void setVerifyToken(byte[] verifyToken) {
    this.verifyToken = verifyToken;
  }

  public String getVerifyUsername() {
    return verifyUsername;
  }

  public void setVerifyUsername(String verifyUsername) {
    this.verifyUsername = verifyUsername;
  }

  public ConnectionManager getConnectionManager() {
    return connectionManager;
  }

  public int getVersion() {
    return version;
  }

  public IPlayer getPlayer() {
    return player;
  }*/
}
