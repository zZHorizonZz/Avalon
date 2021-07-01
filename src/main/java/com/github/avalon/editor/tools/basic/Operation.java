package com.github.avalon.editor.tools.basic;

import com.github.avalon.chat.command.ChatOperator;
import com.github.avalon.common.math.Vector3;
import com.github.avalon.data.Material;
import com.github.avalon.dimension.dimension.Dimension;

import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class Operation implements Callable<Queue<OperationBlock>> {

  private final ChatOperator inform;
  private final Queue<OperationBlock> blockQueue;

  private final Dimension dimension;

  protected Operation(ChatOperator inform, Dimension dimension) {
    this.inform = inform;
    this.dimension = dimension;

    blockQueue = new LinkedBlockingQueue<>();
  }

  @Override
  public abstract Queue<OperationBlock> call();

  public void insert(Vector3 position, Material material) {
    blockQueue.offer(new OperationBlock(position, material));
  }

  public Queue<OperationBlock> getBlockQueue() {
    return blockQueue;
  }

  public ChatOperator getInform() {
    return inform;
  }

  public Dimension getDimension() {
    return dimension;
  }
}
