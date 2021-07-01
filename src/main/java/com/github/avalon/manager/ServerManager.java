package com.github.avalon.manager;

import com.github.avalon.concurrent.NetworkTaskExecutor;
import com.github.avalon.server.IServer;

public abstract class ServerManager extends DefaultManager<IServer> {

  public ServerManager(IServer host) {
    super(host);
  }

  public ServerManager(String managerName, IServer host) {
    super(managerName, host);
  }

  public ServerManager(
          IServer host,
          NetworkTaskExecutor executor,
          Class<? extends AbstractManager<?>>... dependencies) {
    super(host, executor, dependencies);
  }
}
