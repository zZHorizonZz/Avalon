package com.github.avalon.editor.tools.basic;

import com.github.avalon.chat.command.ChatOperator;
import com.github.avalon.common.math.UtilMath;
import com.github.avalon.common.math.Vector3;
import com.github.avalon.dimension.dimension.Dimension;

import javax.annotation.Nullable;
import java.util.Queue;

public abstract class CornerOperation extends Operation {

  private Vector3 cornerA;
  private Vector3 cornerB;

  protected CornerOperation(ChatOperator inform, Dimension dimension) {
    super(inform, dimension);
  }

  @Nullable
  @Override
  public Queue<OperationBlock> call() {
    if (cornerA == null || cornerB == null) {
      getInform().sendSystemMessage("%red%You must have selected 2 locations.");
      return null;
    }

    Vector3 min = UtilMath.min(cornerA, cornerB);
    Vector3 max = UtilMath.max(cornerA, cornerB);

    collectBlocks(min, max);

    return getBlockQueue();
  }

  public abstract void collectBlocks(Vector3 cornerA, Vector3 cornerB);

  public Vector3 getCornerA() {
    return cornerA;
  }

  public void setCornerA(Vector3 cornerA) {
    this.cornerA = cornerA;
  }

  public Vector3 getCornerB() {
    return cornerB;
  }

  public void setCornerB(Vector3 cornerB) {
    this.cornerB = cornerB;
  }
}
