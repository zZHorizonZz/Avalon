package com.github.avalon.scheduler.processor;

import com.github.avalon.scheduler.SchedulerManager;
import com.github.avalon.scheduler.task.DelayedTask;
import com.github.avalon.scheduler.task.TaskStatus;

import java.util.Iterator;
import java.util.Map;

public class DelayedTaskExecutor implements TaskExecutor<DelayedTask> {

  @Override
  public void processTasks(Iterator<Map.Entry<Long, DelayedTask>> taskIterator) {
    while (taskIterator.hasNext()) {
      Map.Entry<Long, DelayedTask> task = taskIterator.next();
      DelayedTask delayedTask = task.getValue();

      try {
        if (delayedTask.getTaskStatus().equals(TaskStatus.STOPPED)
            || delayedTask.getTaskStatus().equals(TaskStatus.SUSPENDED)) {
          taskIterator.remove();
        }

        if (delayedTask.checkDelay()) {
          taskIterator.remove();
        }
      } catch (RuntimeException exception) {
        SchedulerManager.LOGGER.error(
            "[Task #%s] Execution of delayed task failed.", exception, delayedTask.getTaskId());
      }
    }
  }
}
