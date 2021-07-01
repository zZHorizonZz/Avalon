package com.github.avalon.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * This interface provides all necessary methods for creation of {@link ThreadPoolExecutor}. This
 * interface does not provide methods for task execution. These methods are provided by {@link
 * ITaskExecution}.
 *
 * <p>This interface is inspired by Spring Task executor.
 *
 * @author Horizon with help of Spring Task execution
 * @version 1.0
 */
public interface ITaskExecutor {

  ThreadPoolExecutor initialize(ThreadFactory threadFactory);

  void useThreadPool(ThreadPoolExecutor poolExecutor);

  BlockingQueue<Runnable> createQueue(int queueCapacity);

  int getCorePoolSize();

  void setCorePoolSize(int corePoolSize);

  int getMaximumPoolSize();

  void setMaximumPoolSize(int maximumPoolSize);

  int getKeepAlive();

  void setKeepAlive(int seconds);

  int getMaxQueueCapacity();

  void setMaxQueueCapacity(int queueCapacity);

  boolean isAllowedThreadTimeout();

  void setThreadTimeout(boolean threadTimeout);

  void interrupt();

  ThreadPoolExecutor getThreadPoolExecutor();
}
