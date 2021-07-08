package com.github.avalon.editor.command;

import com.github.avalon.chat.command.CommandExecutor;
import com.github.avalon.chat.command.CommandListener;
import com.github.avalon.chat.command.annotation.CommandPerformer;
import com.github.avalon.common.math.Vector3;
import com.github.avalon.common.text.Format;
import com.github.avalon.data.Material;
import com.github.avalon.editor.EditModule;
import com.github.avalon.editor.tools.EditorSession;
import com.github.avalon.editor.tools.basic.FillOperation;
import com.github.avalon.player.IPlayer;

public class BasicCommand extends CommandListener {

  private final EditModule editManager;

  public BasicCommand(EditModule editManager) {
    this.editManager = editManager;

    register("pos1", this::cornerA);
    register("pos2", this::cornerB);
    register("fill", this::fill);
    register("up", this::up);
  }

  @CommandPerformer(command = "pos1")
  public void cornerA(CommandExecutor executor) {
    if (executor.getSender() instanceof IPlayer) {
      EditorSession session = editManager.getSession((IPlayer) executor.getSender());

      if (session == null) {
        executor
            .getSender()
            .sendSystemMessage(
                "%red%Something went wrong and we can not move you into editor session.");
      } else {
        Vector3 corner = ((IPlayer) executor.getSender()).getLocation().toVector();
        executor
            .getSender()
            .sendSystemMessage(
                Format.defaultMessage(
                    "Editor",
                    "%green%Corner A was been successfully set to X: "
                        + corner.getXAsInteger()
                        + " Y: "
                        + corner.getYAsInteger()
                        + " Z: "
                        + corner.getZAsInteger()));

        session.setPosition1(corner);
      }
    }
  }

  @CommandPerformer(command = "pos2")
  public void cornerB(CommandExecutor executor) {
    if (executor.getSender() instanceof IPlayer) {
      EditorSession session = editManager.getSession((IPlayer) executor.getSender());

      if (session == null) {
        executor
            .getSender()
            .sendSystemMessage(
                "%red%Something went wrong and we can not move you into editor session.");
      } else {
        Vector3 corner = ((IPlayer) executor.getSender()).getLocation().toVector();
        executor
            .getSender()
            .sendSystemMessage(
                Format.defaultMessage(
                    "Editor",
                    "%green%Corner B was been successfully set to X: "
                        + corner.getXAsInteger()
                        + " Y: "
                        + corner.getYAsInteger()
                        + " Z: "
                        + corner.getZAsInteger()));

        session.setPosition2(corner);
      }
    }
  }

  @CommandPerformer(command = "fill")
  public void fill(CommandExecutor executor) {
    if (executor.getSender() instanceof IPlayer) {
      EditorSession session = editManager.getSession((IPlayer) executor.getSender());

      if (session == null) {
        executor
            .getSender()
            .sendSystemMessage(
                "%red%Something went wrong and we can not move you into editor session.");
      } else {
        Material material = Material.getByName(executor.getArguments()[0]);
        FillOperation operation =
            new FillOperation(
                executor.getSender(), ((IPlayer) executor.getSender()).getDimension());

        operation.setCornerA(session.getPosition1());
        operation.setCornerB(session.getPosition2());
        operation.setNewMaterial(material);

        editManager.submitOperation(operation);
        executor
            .getSender()
            .sendSystemMessage(Format.defaultMessage("Editor", "Operation submitted..."));
      }
    }
  }

  @CommandPerformer(command = "up")
  public void up(CommandExecutor executor) {
    if (executor.getSender() instanceof IPlayer) {
      IPlayer player = (IPlayer) executor.getSender();
      player
          .getCurrentChunk()
          .getProvider()
          .placeBlockAsSystem(
              player.getLocation().setY(player.getLocation().getBlockY() - 1), Material.STONE);

      executor
          .getSender()
          .sendSystemMessage(Format.defaultMessage("Editor", "Block under you is now solid."));
    }
  }

  public EditModule getEditManager() {
    return editManager;
  }
}
