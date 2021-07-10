package com.github.avalon.editor.command;

import com.github.avalon.chat.command.CommandExecutor;
import com.github.avalon.chat.command.CommandListener;
import com.github.avalon.chat.command.annotation.CommandPerformer;
import com.github.avalon.chat.message.TranslatedMessage;
import com.github.avalon.common.math.Vector3;
import com.github.avalon.common.text.Format;
import com.github.avalon.data.Material;
import com.github.avalon.data.Transform;
import com.github.avalon.editor.EditModule;
import com.github.avalon.editor.tools.EditorSession;
import com.github.avalon.editor.tools.basic.FillOperation;
import com.github.avalon.player.IPlayer;
import org.apache.commons.lang3.StringUtils;

public class BasicCommand extends CommandListener {

  public static final Material DEFAULT_BLOCK = Material.STONE;

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
        executor.getSender().sendSystemMessage(new TranslatedMessage("generics.error"));
      } else {
        Vector3 corner = ((IPlayer) executor.getSender()).getLocation().toVector();
        executor
            .getSender()
            .sendSystemMessage(
                new TranslatedMessage(
                    "editor.pos1",
                    corner.getXAsInteger(),
                    corner.getYAsInteger(),
                    corner.getZAsInteger()));

        session.setPosition1(corner);
      }
    }
  }

  @CommandPerformer(command = "pos2")
  public void cornerB(CommandExecutor executor) {
    if (executor.getSender() instanceof IPlayer) {
      EditorSession session = editManager.getSession((IPlayer) executor.getSender());

      if (session == null) {
        executor.getSender().sendSystemMessage(new TranslatedMessage("generics.error"));
      } else {
        Vector3 corner = ((IPlayer) executor.getSender()).getLocation().toVector();
        executor
            .getSender()
            .sendSystemMessage(
                new TranslatedMessage(
                    "editor.pos2",
                    corner.getXAsInteger(),
                    corner.getYAsInteger(),
                    corner.getZAsInteger()));

        session.setPosition2(corner);
      }
    }
  }

  @CommandPerformer(command = "fill")
  public void fill(CommandExecutor executor) {
    if (executor.getSender() instanceof IPlayer) {
      EditorSession session = editManager.getSession((IPlayer) executor.getSender());

      if (session == null) {
        executor.getSender().sendSystemMessage(new TranslatedMessage("generics.error"));
      } else {
        Material material = Material.getByName(executor.getArguments()[0]);
        FillOperation operation =
            new FillOperation(
                executor.getSender(), ((IPlayer) executor.getSender()).getDimension());

        operation.setCornerA(session.getPosition1());
        operation.setCornerB(session.getPosition2());
        operation.setNewMaterial(material);

        editManager.submitOperation(operation);
        executor.getSender().sendSystemMessage(new TranslatedMessage("editor.operation_start"));
      }
    }
  }

  @CommandPerformer(command = "up")
  public void up(CommandExecutor executor) {
    if (executor.getSender() instanceof IPlayer) {
      IPlayer player = (IPlayer) executor.getSender();
      if (executor.getArguments().length == 0) {
        player
            .getCurrentChunk()
            .getProvider()
            .placeBlockAsSystem(
                player.getLocation().setY(player.getLocation().getBlockY() - 1), DEFAULT_BLOCK);

        executor.getSender().sendSystemMessage(new TranslatedMessage("editor.up_one"));
      } else {
        int blocks = Integer.parseInt(executor.getArguments()[0]);
        Transform transform = player.getLocation();
        transform = transform.setY(transform.getY() + blocks);

        player.teleport(transform);
        player
            .getCurrentChunk()
            .getProvider()
            .placeBlockAsSystem(transform.setY(transform.getBlockY() - 1), DEFAULT_BLOCK);

        executor.getSender().sendSystemMessage(new TranslatedMessage("editor.up_multiple", blocks));
      }
    }
  }

  public EditModule getEditManager() {
    return editManager;
  }
}
