package com.github.avalon.scheduler;

import com.github.avalon.annotation.annotation.Module;
import com.github.avalon.console.logging.DefaultLogger;
import com.github.avalon.module.AbstractModule;
import com.github.avalon.scheduler.processor.DelayedTaskExecutor;
import com.github.avalon.scheduler.processor.RepeatingTaskExecutor;
import com.github.avalon.scheduler.task.DelayedTask;
import com.github.avalon.scheduler.task.RepeatingTask;
import com.github.avalon.scheduler.task.Task;
import com.github.avalon.server.IServer;
import com.github.avalon.server.ServerThread;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Scheduler manager manages and handles tasks, repeating tasks, delayed tasks and more. Tasks can
 * be run asynchronously or synchronously with main thread.
 *
 * @version 1.1
 * @author Horizon
 */
@Module(name = "Scheduler Module", asynchronous = true)
public class SchedulerModule extends AbstractModule<IServer> {

  public static final DefaultLogger LOGGER = new DefaultLogger(SchedulerModule.class);

  private final DelayedTaskExecutor delayedTaskExecutor;
  private final RepeatingTaskExecutor repeatingTaskExecutor;

  private final Map<Long, Task> taskMap;

  private final Map<Long, RepeatingTask> repeatingTasks;
  private final Map<Long, DelayedTask> delayedTasks;

  private final AtomicLong lastTaskId = new AtomicLong(1);

  public SchedulerModule(IServer server) {
    super(server);

      taskMap = new ConcurrentHashMap<>();
      repeatingTasks = new ConcurrentHashMap<>();
      delayedTasks = new ConcurrentHashMap<>();

      delayedTaskExecutor = new DelayedTaskExecutor();
      repeatingTaskExecutor = new RepeatingTaskExecutor();
  }

  /**
   * This method will process, validate and execute tasks that are in {@code repeatingTasks} map or
   * in {@code delayedTasks} in this class.
   *
   * @since 1.0
   */
  public void heartbeat() {
    repeatingTaskExecutor.processTasks(repeatingTasks.entrySet().iterator());
    delayedTaskExecutor.processTasks(delayedTasks.entrySet().iterator());
  }

  /**
   * Run the {@link Runnable} on main thread. And return the created {@link Task}.
   *
   * @since 1.0
   * @param task {@link Runnable} to run.
   * @return New created task.
   */
  public Task runTask(Runnable task) {
    Task networkTask = new Task(lastTaskId.getAndIncrement(), task);

    task.run();
    return networkTask;
  }

  /**
   * Run the {@link Runnable} on asynchronous thread. And return the created task. If there is no
   * assigned {@link com.github.avalon.concurrent.NetworkTaskExecutor} then the {@link Task} is
   * run on main thread.
   *
   * @since 1.0
   * @param task {@link Runnable} to run.
   * @return New created task.
   */
  public Task runTaskAsynchronously(Runnable task) {
    Task networkTask = new Task(lastTaskId.getAndIncrement(), task, true, this);
    try {
      networkTask.run();
    } catch (Exception exception) {
      exception.printStackTrace();
    }
    return networkTask;
  }

  /**
   * Run the {@link Runnable} after the specified delay. And return the created {@link DelayedTask}.
   *
   * @since 1.0
   * @param task {@link Runnable} that will run after the specified delay.
   * @param time Time in server ticks. Default time of tick is {@value ServerThread#TIME_OF_TICK}
   *     milliseconds.
   * @return Created {@link DelayedTask}.
   */
  public DelayedTask runDelayedTask(Runnable task, long time) {
    DelayedTask networkTask = new DelayedTask(lastTaskId.getAndIncrement(), task, time);

    delayedTasks.put(networkTask.getTaskId(), networkTask);
    return networkTask;
  }

  /**
   * Run the {@link Runnable} after the specified delay on asynchronous thread. If {@link
   * com.github.avalon.concurrent.NetworkTaskExecutor} is not set, then task will be executed
   * synchronously. And return the created {@link DelayedTask}.
   *
   * @since 1.0
   * @param task {@link Runnable} that will run after the specified delay.
   * @param time Time in server ticks. Default time of tick is {@value ServerThread#TIME_OF_TICK}
   *     milliseconds.
   * @return Created {@link DelayedTask}.
   */
  public DelayedTask runDelayedTaskAsynchronously(Runnable task, long time) {
    DelayedTask networkTask = new DelayedTask(lastTaskId.getAndIncrement(), task, time, true, this);

    delayedTasks.put(networkTask.getTaskId(), networkTask);
    return networkTask;
  }

  /**
   * Run the {@link Runnable} after the specified delay and then repeat the task after the specified
   * delay again. Method also creates a new {@link RepeatingTask} class that will contain {@link
   * Runnable}.
   *
   * @since 1.0
   * @param task Task that will be executed.
   * @param time Time in server ticks. Default time of tick is {@value ServerThread#TIME_OF_TICK}
   *     milliseconds.
   * @return New {@link RepeatingTask} class.
   */
  public RepeatingTask runRepeatingTask(Runnable task, long time) {
    RepeatingTask networkTask = new RepeatingTask(lastTaskId.getAndIncrement(), task, time);

    repeatingTasks.put(networkTask.getTaskId(), networkTask);
    return networkTask;
  }

  /**
   * Run the {@link Runnable} after the specified delay and then repeat the task after the specified
   * delay again on asynchronous thread. If {@link
   * com.github.avalon.concurrent.NetworkTaskExecutor} is not provided then task is executed
   * synchronously. Method also creates a new {@link RepeatingTask} class that will contain {@link
   * Runnable}.
   *
   * @since 1.0
   * @param task Task that will be executed.
   * @param time Time in server ticks. Default time of tick is {@value ServerThread#TIME_OF_TICK}
   *     milliseconds.
   * @return New {@link RepeatingTask} class.
   */
  public RepeatingTask runRepeatingTaskAsynchronously(Runnable task, long time) {
    RepeatingTask networkTask =
        new RepeatingTask(lastTaskId.getAndIncrement(), task, time, true, this);

    repeatingTasks.put(networkTask.getTaskId(), networkTask);
    return networkTask;
  }

  public Map<Long, Task> getTaskMap() {
    return taskMap;
  }

  public Map<Long, RepeatingTask> getRepeatingTasks() {
    return repeatingTasks;
  }

  public Map<Long, DelayedTask> getDelayedTasks() {
    return delayedTasks;
  }

  public AtomicLong getLastTaskIdAtomic() {
    return lastTaskId;
  }

  public long getLastTaskId() {
    return lastTaskId.get();
  }
}
