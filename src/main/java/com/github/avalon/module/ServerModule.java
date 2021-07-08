package com.github.avalon.module;

import com.github.avalon.concurrent.NetworkTaskExecutor;
import com.github.avalon.server.IServer;

public abstract class ServerModule extends DefaultModule<IServer> {

  protected ServerModule(IServer host) {
    super(host);
  }

  protected ServerModule(String name, IServer host) {
    super(name, host);
  }

  protected ServerModule(
          IServer host,
          NetworkTaskExecutor executor,
          Class<? extends AbstractModule<?>>... dependencies) {
    super(host, executor, dependencies);
  }
}
