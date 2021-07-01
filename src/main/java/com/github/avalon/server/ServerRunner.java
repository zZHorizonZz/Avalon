package com.github.avalon.server;

/**
 * This class provides all necessary methods for server running.
 *
 * @author Horizon
 * @version 1.0
 */
public interface ServerRunner {

  /**
   * This is main heartbeat of the server here are included all things that must be onResponse every tick
   * (50 milliseconds).
   *
   * @since 1.0
   */
  void heartbeat();

  /**
   * This method should shutdown the server safely. All tasks that are currently running should be
   * terminated. All files that server using like worlds etc should be saved or deleted.
   *
   * @since 1.0
   */
  void shutdown();

  /**
   * This is for running the task on main thread.
   *
   * @since 1.0
   * @param runnable Task that will be from main thread.
   */
  void run(Runnable runnable);

  /**
   * This method should provide man thread that is used for server run.
   *
   * @return Main Server thread.
   */
  ServerThread getServerThread();
}
