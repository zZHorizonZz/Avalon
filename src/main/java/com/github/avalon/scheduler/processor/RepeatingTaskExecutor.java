package com.github.avalon.scheduler.processor;

import com.github.avalon.scheduler.SchedulerModule;
import com.github.avalon.scheduler.task.RepeatingTask;
import com.github.avalon.scheduler.task.TaskStatus;

import java.util.Iterator;
import java.util.Map;

public class RepeatingTaskExecutor implements TaskExecutor<RepeatingTask> {

  @Override
  public void processTasks(Iterator<Map.Entry<Long, RepeatingTask>> taskIterator) {
    while (taskIterator.hasNext()) {
      Map.Entry<Long, RepeatingTask> task = taskIterator.next();
      RepeatingTask repeatingTask = task.getValue();

      try {
        if (repeatingTask.getTaskStatus().equals(TaskStatus.STOPPED)
            || repeatingTask.getTaskStatus().equals(TaskStatus.SUSPENDED)) {
          taskIterator.remove();
        }

        repeatingTask.repeat();
      } catch (RuntimeException exception) {
        SchedulerModule.LOGGER.error(
            "[Task #%s] Execution of repeating task failed.", exception, repeatingTask.getTaskId());
      }
    }
  }
}
