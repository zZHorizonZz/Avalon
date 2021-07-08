package com.github.avalon.module;

import com.github.avalon.concurrent.NetworkTaskExecutor;

public abstract class DefaultModule<T> extends AbstractModule<T> implements TaskExecutor {

  protected DefaultModule(T host) {
    super(host);
  }

  protected DefaultModule(String managerName, T host) {
    super(managerName, host);
  }

  protected DefaultModule(
      T host, NetworkTaskExecutor executor, Class<? extends AbstractModule<?>>... dependencies) {
    super(host, executor, dependencies);
  }

  @Override
  public void runTask(Runnable task) {
    if (getSchedulerManager() == null) {
      task.run();
    } else {
      getSchedulerManager().runTask(task);
    }
  }

  @Override
  public void runTaskAsynchronously(Runnable task) {
    if (getSchedulerManager() == null) {
      if (getTaskExecutor() != null) {
        getTaskExecutor().executeTask(task);
      } else {
        AbstractModule.LOGGER.error(
            "Task can not be executed. Because there is not any assigned thread.",
            new NullPointerException("Task executor is not assigned."));
      }
    } else {
      getSchedulerManager().runTaskAsynchronously(task);
    }
  }

  @Override
  public void runRepeatingTask(Runnable task, long delay) {
    if (getSchedulerManager() != null) {
      getSchedulerManager().runRepeatingTask(task, delay);
    } else {
      LOGGER.error(
          "Task can not be executed. Because Scheduler Module is not assigned.",
          new NullPointerException("Scheduler manager is not assigned."));
    }
  }

  @Override
  public void runRepeatingTaskAsynchronously(Runnable task, long delay) {
    if (getSchedulerManager() != null) {
      getSchedulerManager().runRepeatingTaskAsynchronously(task, delay);
    } else {
      LOGGER.error(
          "Task can not be executed. Because Scheduler Module is not assigned.",
          new NullPointerException("Scheduler manager is not assigned."));
    }
  }

  @Override
  public void runDelayedTask(Runnable task, long delay) {
    if (getSchedulerManager() != null) {
      getSchedulerManager().runDelayedTask(task, delay);
    } else {
      LOGGER.error(
          "Task can not be executed. Because Scheduler Module is not assigned.",
          new NullPointerException("Scheduler manager is not assigned."));
    }
  }

  @Override
  public void runDelayedTaskAsynchronously(Runnable task, long delay) {
    if (getSchedulerManager() != null) {
      getSchedulerManager().runDelayedTaskAsynchronously(task, delay);
    } else {
      LOGGER.error(
          "Task can not be executed. Because Scheduler Module is not assigned.",
          new NullPointerException("Scheduler manager is not assigned."));
    }
  }
}
