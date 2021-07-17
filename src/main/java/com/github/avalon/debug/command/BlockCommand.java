package com.github.avalon.debug.command;

import com.github.avalon.chat.command.CommandExecutor;
import com.github.avalon.chat.command.CommandListener;
import com.github.avalon.chat.command.annotation.CommandPerformer;
import com.github.avalon.data.Transform;
import com.github.avalon.debug.DebugModule;
import com.github.avalon.packet.packet.play.PacketBlockChange;
import com.github.avalon.player.IPlayer;

public class BlockCommand extends CommandListener {

  public BlockCommand(DebugModule debugManager) {
    register("debug.block", this::debugBlock);
  }

  @CommandPerformer(command = "debug.block")
  public void debugBlock(CommandExecutor executor) {
    if (executor.getSender() instanceof IPlayer) {
      IPlayer player = (IPlayer) executor.getSender();

      Transform location = player.getLocation();
      player.sendSystemMessage(
          "%gray%Block at location: X: "
              + location.getBlockX()
              + " ,Y: "
              + (location.getBlockY() - 1)
              + " ,Z: "
              + location.getBlockZ()
              + " Material: "
              + location
                  .getDimension()
                  .getBlockAt(location.getBlockX(), (location.getBlockY() - 1), location.getBlockZ()).getMaterial().getName());
    }
  }
}
