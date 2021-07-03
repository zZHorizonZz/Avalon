package com.github.avalon.debug;

import com.github.avalon.annotation.annotation.Manager;
import com.github.avalon.debug.command.BlockCommand;
import com.github.avalon.manager.ServerManager;
import com.github.avalon.server.IServer;

@Manager(name = "Debug Manager", asynchronous = false)
public class DebugManager extends ServerManager {

  public DebugManager(IServer host) {
    super(host);

    host.getChatManager().registerCommands(new BlockCommand(this));
  }
}
