package com.github.avalon.player.command;

import com.github.avalon.chat.command.CommandExecutor;
import com.github.avalon.chat.command.CommandListener;
import com.github.avalon.player.IPlayer;

public class GamemodeCommand extends CommandListener {

    public GamemodeCommand() {
    }

    public void gamemode(CommandExecutor executor) {
        if(executor.getSender() instanceof IPlayer) {

        }
    }
}
