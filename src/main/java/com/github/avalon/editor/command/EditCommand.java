package com.github.avalon.editor.command;

import com.github.avalon.chat.command.CommandExecutor;
import com.github.avalon.chat.command.CommandListener;
import com.github.avalon.chat.command.annotation.CommandPerformer;
import com.github.avalon.editor.EditManager;
import com.github.avalon.editor.tools.EditorSession;
import com.github.avalon.player.IPlayer;

public class EditCommand extends CommandListener {

  private final EditManager editManager;

  public EditCommand(EditManager editManager) {
    this.editManager = editManager;

    register("edit", this::edit);
  }

  @CommandPerformer(command = "edit")
  public void edit(CommandExecutor executor) {
    if (executor.getSender() instanceof IPlayer) {
      EditorSession session = editManager.getSession((IPlayer) executor.getSender());

      if (session == null) {
        executor
            .getSender()
            .sendSystemMessage(
                "%red%Something went wrong and we can not move you into editor session.");
      } else {
        executor.getSender().sendSystemMessage("%green%You are now in editing session.");
      }
    }
  }

  public EditManager getEditManager() {
    return editManager;
  }
}
