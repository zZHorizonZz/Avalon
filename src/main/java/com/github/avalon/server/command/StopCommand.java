package com.github.avalon.server.command;

import com.github.avalon.chat.command.CommandExecutor;
import com.github.avalon.chat.command.CommandListener;
import com.github.avalon.chat.command.annotation.CommandPerformer;
import com.github.avalon.player.IPlayer;

public class StopCommand extends CommandListener {

    public StopCommand() {
        register("stop", this::commandItem);
    }

    @CommandPerformer(command = "stop")
    public void commandItem(CommandExecutor executor) {
        if (executor.getSender() instanceof IPlayer) {
            IPlayer player = (IPlayer) executor.getSender();

            player.getServer().shutdown();
        }
    }
}
