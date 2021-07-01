package com.github.avalon.scheduler.task;

import com.github.avalon.scheduler.SchedulerManager;
import com.github.avalon.server.ServerThread;

/**
 * This class implements {@link Runnable} and provides repeatable run of code through {@link
 * SchedulerManager}.
 *
 * @author Horizon
 * @version 1.0
 */
public class RepeatingTask extends Task {

  /** Timer should be set in ticks. */
  private long timer;

  private long lastExecutionTime;

  public RepeatingTask(
          long taskID,
          Runnable task,
          long timer,
          boolean asynchronous,
          SchedulerManager schedulerManager) {
    super(taskID, task, asynchronous, schedulerManager);

    this.timer = timer;
    lastExecutionTime = System.currentTimeMillis();
  }

  public RepeatingTask(long taskID, Runnable task, long timer) {
    super(taskID, task);

    this.timer = timer;
    lastExecutionTime = System.currentTimeMillis();
  }

  public void repeat() {
    try {
      if (System.currentTimeMillis() - lastExecutionTime > timer * ServerThread.TIME_OF_TICK
          || lastExecutionTime == 0) {
        lastExecutionTime = System.currentTimeMillis();
        run();
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  public long getTimer() {
    return timer;
  }

  public void setTimer(long timer) {
    this.timer = timer;
  }

  public long getLastExecutionTime() {
    return lastExecutionTime;
  }

  public void setLastExecutionTime(long lastExecutionTime) {
    this.lastExecutionTime = lastExecutionTime;
  }
}
