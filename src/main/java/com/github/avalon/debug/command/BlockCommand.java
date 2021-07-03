package com.github.avalon.debug.command;

import com.github.avalon.chat.command.CommandExecutor;
import com.github.avalon.chat.command.CommandListener;
import com.github.avalon.chat.command.annotation.CommandPerformer;
import com.github.avalon.debug.DebugManager;
import com.github.avalon.packet.packet.play.PacketBlockChange;
import com.github.avalon.player.IPlayer;

public class BlockCommand extends CommandListener {

  public BlockCommand(DebugManager debugManager) {
    register("debug.block", this::debugBlock);
  }

  @CommandPerformer(command = "debug.block")
  public void debugBlock(CommandExecutor executor) {
    if (executor.getSender() instanceof IPlayer) {
      IPlayer player = (IPlayer) executor.getSender();

      int maximumIdentifiers = Integer.parseInt(executor.getArguments()[0]);
      int currentIdentifier = 0;

      for (int x = 0; x <= maximumIdentifiers / 2; x++) {
        for (int z = 0; z <= maximumIdentifiers / 2; z++) {
          PacketBlockChange packet =
              new PacketBlockChange(x, player.getLocation().getBlockY(), z, currentIdentifier++);
          player.sendPacket(packet);
        }
      }
    }
  }
}
