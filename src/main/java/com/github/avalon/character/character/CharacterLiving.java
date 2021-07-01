package com.github.avalon.character.character;

import com.github.avalon.character.Controllable;
import com.github.avalon.common.system.UtilMath;
import com.github.avalon.data.Transform;
import com.github.avalon.player.IPlayer;

public abstract class CharacterLiving extends Character implements Controllable {

  private String displayName;
  private IPlayer controller;

  private short deltaX;
  private short deltaY;
  private short deltaZ;

  protected CharacterLiving(int identifier, Transform transform) {
    super(identifier, transform);
  }

  @Override
  public IPlayer getController() {
    return controller;
  }

  @Override
  public void setController(IPlayer player) {
      controller = player;
  }

  public void move(Transform newTransform) {
    resetDeltas();

    Transform delta = getTransform().subtract(newTransform);
      deltaX = UtilMath.toDelta(deltaX, delta.getX());
      deltaY = UtilMath.toDelta(deltaY, delta.getY());
      deltaZ = UtilMath.toDelta(deltaZ, delta.getZ());

    /*PacketEntityPositionAndRotation packet =
        new PacketEntityPositionAndRotation(
            getIdentifier(), deltaX, deltaY, deltaZ, delta.getYaw(), delta.getPitch(), true);

    getController().sendPacket(packet);*/
    setTransform(newTransform);
  }

  public void resetDeltas() {
      deltaX = 0;
      deltaY = 0;
      deltaZ = 0;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }
}
