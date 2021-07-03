package com.github.avalon.item.command;

import com.github.avalon.chat.command.CommandExecutor;
import com.github.avalon.chat.command.CommandListener;
import com.github.avalon.chat.command.annotation.CommandPerformer;
import com.github.avalon.common.text.Format;
import com.github.avalon.data.Material;
import com.github.avalon.item.Item;
import com.github.avalon.player.IPlayer;

public class ItemCommand extends CommandListener {

  public ItemCommand() {
    register("item", this::commandItem);
    register("item.give", this::commandItemGive);
  }

  @CommandPerformer(command = "item")
  public void commandItem(CommandExecutor executor) {
    if (executor.getSender() instanceof IPlayer) {
      IPlayer player = (IPlayer) executor.getSender();
    }
  }

  @CommandPerformer(command = "item.give")
  public void commandItemGive(CommandExecutor executor) {
    if (executor.getSender() instanceof IPlayer) {
      IPlayer player = (IPlayer) executor.getSender();
      String[] arguments = executor.getArguments();

      if (arguments.length == 0) {
        player.sendSystemMessage("%red%Not enough arguments.");
        return;
      }

      boolean digit = true;
      for (char c : arguments[0].toCharArray()) {
        if (!Character.isDigit(c)) {
          digit = false;
        }
      }

      Material material =
          digit
              ? Material.getByIdentifier(Integer.parseInt(arguments[0]))
              : Material.getByName(arguments[0]);

      int size = 1;

      if (arguments.length > 1) {
        size = Integer.parseInt(arguments[1]);
      }

      player.getInventory().setCurrentHeldItem(new Item(material, size));
      player.sendSystemMessage(
          Format.defaultMessage(
              "Item", "You received %yellow%" + size + "x %gray%of%yellow% " + material.getName()));
    }
  }
}
