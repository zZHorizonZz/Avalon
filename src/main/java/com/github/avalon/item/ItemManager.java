package com.github.avalon.item;

import com.github.avalon.annotation.annotation.Manager;
import com.github.avalon.item.command.ItemCommand;
import com.github.avalon.manager.ServerManager;
import com.github.avalon.server.IServer;

@Manager(name = "Item Manager", asynchronous = false)
public class ItemManager extends ServerManager {

  public ItemManager(IServer host) {
    super(host);

    host.getChatManager().registerCommands(new ItemCommand());
  }
}
