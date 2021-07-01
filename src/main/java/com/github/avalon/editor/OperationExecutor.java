package com.github.avalon.editor;

import com.github.avalon.concurrent.NetworkTaskExecutor;
import com.github.avalon.editor.tools.basic.Operation;
import com.github.avalon.editor.tools.basic.OperationBlock;

import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;

public class OperationExecutor {

  private final NetworkTaskExecutor executor;
  private final Queue<Operation> currentTasks;

  public OperationExecutor(NetworkTaskExecutor executor) {
    this.executor = executor;

    currentTasks = new SynchronousQueue<>();
  }

  public void processOperation(Operation operation) {
    Future<Queue<OperationBlock>> blocksToProcess = executor.submitTask(operation);

    if (blocksToProcess.isCancelled()) {
      executeNextOperation();
    }

    executor.executeTask(
        () -> {
          try {
            Queue<OperationBlock> blockQueue = blocksToProcess.get();
            blockQueue.forEach(
                operationBlock ->
                    operation
                        .getDimension()
                        .getBlockAt(
                            operationBlock.getPosition().getXAsInteger(),
                            operationBlock.getPosition().getYAsInteger(),
                            operationBlock.getPosition().getZAsInteger())
                        .setMaterial(operationBlock.getMaterial()));
          } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            operation
                .getInform()
                .sendSystemMessage(
                    "%red%Something went wrong. Please contact server administrator.");
          }
        });
  }

  public void executeNextOperation() {
    if (currentTasks.isEmpty()) {
      return;
    }

    processOperation(currentTasks.poll());
  }

  public NetworkTaskExecutor getExecutor() {
    return executor;
  }

  public Queue<Operation> getCurrentTasks() {
    return currentTasks;
  }
}
