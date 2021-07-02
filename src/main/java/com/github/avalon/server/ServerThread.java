package com.github.avalon.server;

import com.github.avalon.console.logging.DefaultLogger;

public class ServerThread extends Thread {

  public static final DefaultLogger LOGGER = new DefaultLogger(Server.class);

  private ServerRunner serverRunner;

  public static final int TICK = 20;
  public static final int TIME_OF_TICK = 50;

  private long lastOverloadTime;

  private long lastTick;

  private long currentTick;
  private final Server server;

  public ServerThread(Server server) {
    LOGGER.info("Starting server thread...");
    this.server = server;
  }

  @Override
  public void run() {
    LOGGER.info("Server thread was been started.");
    try {
      long currentTime;

      while (server.getServerState().equals(ServerState.RUNNING)) {
        currentTime = System.currentTimeMillis();

        server.heartbeat();
        lastTick = TIME_OF_TICK - (System.currentTimeMillis() - currentTime);
        if (lastTick > 0) {
          Thread.sleep(lastTick);
        }

        currentTick++;
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
      server.shutdown();
    }
  }

  public long getLastTick() {
    return lastTick;
  }

  public long getCurrentTick() {
    return currentTick;
  }
}
