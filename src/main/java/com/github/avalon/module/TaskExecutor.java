package com.github.avalon.module;

import com.github.avalon.scheduler.SchedulerModule;

/**
 * Provides an methods that are in {@link SchedulerModule} with same functionality.
 *
 * @version 1.0
 * @author Horizon
 */
public interface TaskExecutor {

  void runTask(Runnable task);

  void runTaskAsynchronously(Runnable task);

  void runRepeatingTask(Runnable task, long delay);

  void runRepeatingTaskAsynchronously(Runnable task, long delay);

  void runDelayedTask(Runnable task, long delay);

  void runDelayedTaskAsynchronously(Runnable task, long delay);
}
