package com.github.avalon.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * This interface provides necessary functionality for task execution.
 *
 * @author Horizon
 * @version 1.0
 */
public interface ITaskExecution {

  /**
   * This method will addPlayer {@link Runnable} task to execution queue. This method will throw an
   * exception if {@link java.util.concurrent.ThreadPoolExecutor} is null. If we want some results
   * from asynchronous task execution, we need to use {@code submitTask(Runnable task)}.
   *
   * @param task Runnable that will be added for execution.
   * @since 1.0
   */
  void executeTask(Runnable task);

  /**
   * This method will submit {@link Runnable} task to execution queue. This method will throw an
   * exception if {@link java.util.concurrent.ThreadPoolExecutor} is null. This is used if we want
   * to get results from submitted task.
   *
   * @param task Task that will be submitted.
   * @return Future that represents the completion of task.
   * @since 1.0
   */
  Future<?> submitTask(Runnable task);

  /**
   * This method will submit {@link Callable} task to execution queue. This method will throw an
   * exception if {@link java.util.concurrent.ThreadPoolExecutor} is null. This is used if we want
   * to get results from submitted task.
   *
   * @param task Task that will be submitted.
   * @return Future that represents the completion of task.
   * @since 1.0
   */
  <T> Future<T> submitTask(Callable<T> task);
}
