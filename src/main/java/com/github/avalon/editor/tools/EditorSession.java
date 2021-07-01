package com.github.avalon.editor.tools;

import com.github.avalon.common.math.Vector3;
import com.github.avalon.player.IPlayer;

public class EditorSession {

  private final IPlayer player;

  private Vector3 position1;
  private Vector3 position2;

  public EditorSession(IPlayer player) {
    this.player = player;
  }

  public IPlayer getPlayer() {
    return player;
  }

  public Vector3 getPosition1() {
    return position1;
  }

  public Vector3 getPosition2() {
    return position2;
  }

  public void setPosition1(Vector3 position1) {
    this.position1 = position1;
  }

  public void setPosition2(Vector3 position2) {
    this.position2 = position2;
  }
}
