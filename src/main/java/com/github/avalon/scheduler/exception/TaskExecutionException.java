package com.github.avalon.scheduler.exception;

import com.github.avalon.scheduler.task.Task;

public class TaskExecutionException extends Exception {

  private Task task;

  public TaskExecutionException(Task task, String message) {
    super(message);
  }

  public Task getTask() {
    return task;
  }

  public void setTask(Task task) {
    this.task = task;
  }
}
