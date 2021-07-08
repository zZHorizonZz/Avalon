package com.github.avalon.server;

import com.github.avalon.console.ConsoleModule;
import com.github.avalon.console.messages.ServerMessage;
import com.github.avalon.resource.ResourceModule;

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

  private final ResourceModule resourceModule;
  private final ConsoleModule consoleModule;
  private final Server server;

  public Bootstrap() {
    INSTANCE = this;

    resourceModule = new ResourceModule();
    message = new ServerMessage(resourceModule.loadResource(null, "messages.json"));
    consoleModule = new ConsoleModule(this);
    server = new Server(this);
    server.startServer();
  }

  @Override
  public void initialize() {}

  public ConsoleModule getConsoleModule() {
    return consoleModule;
  }

  public Server getServer() {
    return server;
  }

  public ServerMessage getMessage() {
    return message;
  }

  public ResourceModule getResourceModule() {
    return resourceModule;
  }
}
