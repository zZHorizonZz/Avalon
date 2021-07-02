package com.github.avalon.server;

import com.github.avalon.console.ConsoleManager;
import com.github.avalon.console.messages.ServerMessage;
import com.github.avalon.resource.ResourceManager;

/**
 * This class implements the {@link ServerRunner} and manages the server running this class includes
 * the main server thread.
 *
 * @author Horizon
 * @version 1.0
 */
public class Bootstrap implements Initializer {

  public static Bootstrap INSTANCE;

  private final ServerMessage message;

  private final ResourceManager resourceManager;
  private final ConsoleManager consoleManager;
  private final Server server;

  public Bootstrap() {
    INSTANCE = this;

    resourceManager = new ResourceManager();
    message = new ServerMessage(resourceManager.loadResource(null, "messages.json"));
    consoleManager = new ConsoleManager(this);
    server = new Server(this);
    server.startServer();
  }

  @Override
  public void initialize() {}

  public ConsoleManager getConsoleManager() {
    return consoleManager;
  }

  public Server getServer() {
    return server;
  }

  public ServerMessage getMessage() {
    return message;
  }

  public ResourceManager getResourceManager() {
    return resourceManager;
  }
}
