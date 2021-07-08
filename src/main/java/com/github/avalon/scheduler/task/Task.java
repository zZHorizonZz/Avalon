package com.github.avalon.scheduler.task;

import com.github.avalon.scheduler.SchedulerModule;
import com.github.avalon.scheduler.exception.TaskExecutionException;

/**
 * This class provides and basic implementation of the task execution logic based on execution of
 * the {@link Runnable}. Also we can set if task should be executed asynchronously or not. This is
 * handled by {@link SchedulerModule}. {@link SchedulerModule} handles status of this task.
 *
 * @version 1.0
 * @author Horizon
 */
public class Task {

  private final Runnable task;
  private final long taskId;

  private TaskStatus taskStatus;

  private boolean asynchronous;

  private SchedulerModule schedulerManager;

  public Task(long taskId, Runnable task, boolean asynchronous, SchedulerModule schedulerManager) {
    this.taskId = taskId;
    this.task = task;
    this.asynchronous = asynchronous;
    this.schedulerManager = schedulerManager;

    taskStatus = TaskStatus.RUNNING;
  }

  public Task(long taskId, Runnable task) {
    this.taskId = taskId;
    this.task = task;

    taskStatus = TaskStatus.RUNNING;
  }

  /**
   * Run the {@link Runnable} if the asynchronous is set to true then {@link Runnable} will be
   * executed on asynchronous thread.
   *
   * @throws TaskExecutionException
   */
  public void run() throws TaskExecutionException {
    try {
      if (task != null) {
        if (asynchronous && schedulerManager != null) {
          schedulerManager.getTaskExecutor().executeTask(task);
        } else {
          task.run();
        }
      }
    } catch (RuntimeException exception) {
      SchedulerModule.LOGGER.error("[Task #%s] Execution of task failed.", exception, taskId);
    }
  }

  public Runnable getTask() {
    return task;
  }

  public long getTaskId() {
    return taskId;
  }

  public TaskStatus getTaskStatus() {
    return taskStatus;
  }

  public void setTaskStatus(TaskStatus taskStatus) {
    this.taskStatus = taskStatus;
  }

  public boolean isAsynchronous() {
    return asynchronous;
  }

  public void setAsynchronous(boolean asynchronous, SchedulerModule schedulerManager) {
    this.asynchronous = asynchronous;
    this.schedulerManager = schedulerManager;
  }

  public SchedulerModule getSchedulerManager() {
    return schedulerManager;
  }
}
