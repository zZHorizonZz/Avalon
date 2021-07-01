package com.github.avalon.concurrent;

import java.util.concurrent.*;

public class NetworkTaskExecutor implements ITaskExecutor, ITaskExecution {

  private int corePoolSize = 1;
  private int maximumPoolSize = Integer.MAX_VALUE;
  private int keepAlive = 60;
  private int queueCapacity = Integer.MAX_VALUE;

  private boolean allowCoreThreadTimeOut;

  private ThreadPoolExecutor threadPoolExecutor;

  @Override
  public ThreadPoolExecutor initialize(ThreadFactory threadFactory) {
    BlockingQueue<Runnable> queue = createQueue(queueCapacity);

    ThreadPoolExecutor executor =
        new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAlive,
            TimeUnit.SECONDS,
            queue,
            threadFactory);

    if (allowCoreThreadTimeOut) {
      executor.allowCoreThreadTimeOut(true);
    }

    threadPoolExecutor = executor;
    return executor;
  }

  @Override
  public void useThreadPool(ThreadPoolExecutor poolExecutor) {
    if (threadPoolExecutor != null) {
      interrupt();
      threadPoolExecutor = null;
    }

    corePoolSize = poolExecutor.getCorePoolSize();
    maximumPoolSize = poolExecutor.getMaximumPoolSize();
    keepAlive = (int) poolExecutor.getKeepAliveTime(TimeUnit.SECONDS);
    allowCoreThreadTimeOut = poolExecutor.allowsCoreThreadTimeOut();

    threadPoolExecutor = poolExecutor;
  }

  @Override
  public BlockingQueue<Runnable> createQueue(int queueCapacity) {
    if (queueCapacity > 0) {
      return new LinkedBlockingQueue<>(queueCapacity);
    } else {
      return new SynchronousQueue<>();
    }
  }

  @Override
  public int getCorePoolSize() {
    return corePoolSize;
  }

  @Override
  public void setCorePoolSize(int corePoolSize) {
    this.corePoolSize = corePoolSize;
    if (threadPoolExecutor != null) threadPoolExecutor.setCorePoolSize(corePoolSize);
  }

  @Override
  public int getMaximumPoolSize() {
    return maximumPoolSize;
  }

  @Override
  public void setMaximumPoolSize(int maximumPoolSize) {
    this.maximumPoolSize = maximumPoolSize;
    if (threadPoolExecutor != null) threadPoolExecutor.setMaximumPoolSize(maximumPoolSize);
  }

  @Override
  public int getKeepAlive() {
    return keepAlive;
  }

  @Override
  public void setKeepAlive(int seconds) {
    keepAlive = seconds;
    if (threadPoolExecutor != null)
      threadPoolExecutor.setKeepAliveTime(seconds, TimeUnit.SECONDS);
  }

  @Override
  public int getMaxQueueCapacity() {
    return queueCapacity;
  }

  @Override
  public void setMaxQueueCapacity(int queueCapacity) {
    this.queueCapacity = queueCapacity;
  }

  @Override
  public boolean isAllowedThreadTimeout() {
    return allowCoreThreadTimeOut;
  }

  @Override
  public void setThreadTimeout(boolean threadTimeout) {
    allowCoreThreadTimeOut = threadTimeout;
    if (threadPoolExecutor != null)
      threadPoolExecutor.allowCoreThreadTimeOut(threadTimeout);
  }

  @Override
  public void interrupt() {
    if (threadPoolExecutor == null) return;

    for (Runnable runnable : threadPoolExecutor.shutdownNow()) {
      if (runnable instanceof Future) {
        ((Future<?>) runnable).cancel(true);
      }
    }
  }

  @Override
  public ThreadPoolExecutor getThreadPoolExecutor() {
    return threadPoolExecutor;
  }

  @Override
  public void executeTask(Runnable task) {
    if (threadPoolExecutor != null)
      try {
        threadPoolExecutor.execute(task);
      } catch (RejectedExecutionException exception) {
        throw new RejectedExecutionException(
            "[" + threadPoolExecutor + "] This task was not been accepted: " + task,
            exception);
      }
    else
      throw new NullPointerException(
          "[" + this + "] This task can not be accepted because Executor is null: " + task);
  }

  @Override
  public Future<?> submitTask(Runnable task) {
    if (threadPoolExecutor != null)
      try {
        return threadPoolExecutor.submit(task);
      } catch (RejectedExecutionException exception) {
        throw new RejectedExecutionException(
            "[" + threadPoolExecutor + "] This task was not been accepted: " + task,
            exception);
      }
    else
      throw new NullPointerException(
          "[" + this + "] This task can not be accepted because Executor is null: " + task);
  }

  @Override
  public <T> Future<T> submitTask(Callable<T> task) {
    if (threadPoolExecutor != null) {
      try {
        return threadPoolExecutor.submit(task);
      } catch (RejectedExecutionException exception) {
        throw new RejectedExecutionException(
            "[" + threadPoolExecutor + "] This task was not been accepted: " + task,
            exception);
      }
    } else {
      throw new NullPointerException(
          "[" + this + "] This task can not be accepted because Executor is null: " + task);
    }
  }
}
