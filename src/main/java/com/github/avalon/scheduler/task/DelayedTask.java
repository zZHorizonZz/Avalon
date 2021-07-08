package com.github.avalon.scheduler.task;

import com.github.avalon.scheduler.SchedulerModule;
import com.github.avalon.server.ServerThread;

public class DelayedTask extends Task {

  private long timer;
  private long startTime;

  public DelayedTask(
          long taskID,
          Runnable task,
          long timer,
          boolean asynchronous,
          SchedulerModule schedulerManager) {
    super(taskID, task, asynchronous, schedulerManager);

    this.timer = timer;
    startTime = System.currentTimeMillis();
  }

  public DelayedTask(long taskID, Runnable task, long timer) {
    super(taskID, task);

    this.timer = timer;
    startTime = System.currentTimeMillis();
  }

  public boolean checkDelay() {
    try {
      if (System.currentTimeMillis() < startTime + (timer * ServerThread.TIME_OF_TICK)
          || timer == 0) {
        run();
        return true;
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    }

    return false;
  }

  public long getTimer() {
    return timer;
  }

  public void setTimer(long timer) {
    this.timer = timer;
  }

  public long getStartTime() {
    return startTime;
  }

  public void setStartTime(long startTime) {
    this.startTime = startTime;
  }
}
