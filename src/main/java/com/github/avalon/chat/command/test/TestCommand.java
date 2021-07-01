package com.github.avalon.chat.command.test;

import com.github.avalon.chat.command.CommandExecutor;
import com.github.avalon.chat.command.CommandListener;
import com.github.avalon.chat.command.annotation.CommandPerformer;
import com.github.avalon.player.IPlayer;

import java.util.Arrays;

public class TestCommand extends CommandListener {

  @CommandPerformer(command = "test")
  public void commandTest(CommandExecutor executor) {
    if (executor.getSender() instanceof IPlayer) {
      IPlayer player = (IPlayer) executor.getSender();
      player.sendSystemMessage("%red%It work's");
      player.sendSystemMessage(
          "%blue%Parameters: Label: "
              + executor.getLabel()
              + " Arguments: "
              + Arrays.toString(executor.getArguments()));
    }
  }
}
