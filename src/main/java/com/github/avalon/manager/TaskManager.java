package com.github.avalon.manager;

/**
 * Provides an methods that are in {@link com.github.avalon.scheduler.SchedulerManager} with
 * same functionality.
 *
 * @version 1.0
 * @author Horizon
 */
public interface TaskManager {

  void runTask(Runnable task);

  void runTaskAsynchronously(Runnable task);

  void runRepeatingTask(Runnable task, long delay);

  void runRepeatingTaskAsynchronously(Runnable task, long delay);

  void runDelayedTask(Runnable task, long delay);

  void runDelayedTaskAsynchronously(Runnable task, long delay);
}
