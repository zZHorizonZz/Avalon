package com.github.avalon.debug;

import com.github.avalon.annotation.annotation.Module;
import com.github.avalon.debug.command.BlockCommand;
import com.github.avalon.module.ServerModule;
import com.github.avalon.server.IServer;

@Module(name = "Debug Module", asynchronous = false)
public class DebugModule extends ServerModule {

  public DebugModule(IServer host) {
    super(host);

    host.getChatModule().registerCommands(new BlockCommand(this));
  }
}
