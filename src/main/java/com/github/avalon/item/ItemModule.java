package com.github.avalon.item;

import com.github.avalon.annotation.annotation.Module;
import com.github.avalon.item.command.ItemCommand;
import com.github.avalon.module.ServerModule;
import com.github.avalon.server.IServer;

@Module(name = "Item Module", asynchronous = false)
public class ItemModule extends ServerModule {

  public ItemModule(IServer host) {
    super(host);

    host.getChatModule().registerCommands(new ItemCommand());
  }
}
