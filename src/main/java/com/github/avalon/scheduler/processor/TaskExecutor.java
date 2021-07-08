package com.github.avalon.scheduler.processor;

import com.github.avalon.scheduler.SchedulerModule;
import com.github.avalon.scheduler.task.Task;

import java.util.Iterator;
import java.util.Map;

/**
 * This class provides basic method for processing of the tasks in {@link
 * SchedulerModule} class. Usually method in this class validate,
 * run and if necessary remove the {@link Task}.
 *
 * @author Horizon
 * @version 1.0
 * @param <T> Type that will be used in processing task method.
 */
public interface TaskExecutor<T extends Task> {

  void processTasks(Iterator<Map.Entry<Long, T>> taskIterator);
}
