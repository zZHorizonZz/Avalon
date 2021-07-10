package com.github.avalon.player.command;

import com.github.avalon.chat.command.CommandExecutor;
import com.github.avalon.chat.command.CommandListener;
import com.github.avalon.chat.command.annotation.CommandPerformer;
import com.github.avalon.chat.message.TranslatedMessage;
import com.github.avalon.player.IPlayer;
import com.github.avalon.player.attributes.GameMode;
import com.github.avalon.player.attributes.GameState;
import org.apache.commons.lang3.StringUtils;

public class SuperSecretCommand extends CommandListener {

  public SuperSecretCommand() {
    register("supersecret", this::superSecret);
  }

  @CommandPerformer(command = "supersecret")
  public void superSecret(CommandExecutor executor) {
    if (executor.getSender() instanceof IPlayer) {
      IPlayer player = (IPlayer) executor.getSender();

      if (executor.getArguments().length < 1) {
        player.sendSystemMessage(new TranslatedMessage("command.not_enough_arguments"));
      } else if (executor.getArguments().length == 1) {
        String eventArgument = executor.getArguments()[0];

        boolean number = StringUtils.isNumeric(eventArgument);
        GameState.DemoEvent event =
            number
                ? GameState.DemoEvent.getByIndex(Integer.parseInt(eventArgument))
                : GameState.DemoEvent.getByName(eventArgument);

        player.showDemoEvent(event);
        player.sendSystemMessage("Showing super secret %yellow%" + event.getIndex());
      } else {

      }
    }
  }
}
