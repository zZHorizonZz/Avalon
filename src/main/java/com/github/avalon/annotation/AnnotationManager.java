package com.github.avalon.annotation;

import com.github.avalon.concurrent.NetworkTaskExecutor;
import com.github.avalon.console.logging.DefaultLogger;
import com.github.avalon.manager.ServerManager;
import com.github.avalon.server.IServer;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class AnnotationManager extends ServerManager {

  public static final DefaultLogger LOGGER = new DefaultLogger(AnnotationManager.class);

  public static final NetworkTaskExecutor ANNOTATION_TASK_EXECUTOR = new NetworkTaskExecutor();

  public AnnotationManager(IServer host) {
    super("Annotation Manager", host);
  }

  public static <A extends Annotation> Set<Class<?>> scanForClassesByAnnotation(
      Class<A> annotation) {
    Set<Class<?>> result = new HashSet<>();

    try {
      AnnotationCallback<A> callback = new AnnotationCallback<>(annotation);
      result = callback.call();

    } catch (ExecutionException | InterruptedException | TimeoutException exception) {
      LOGGER.error(
          "Something went wrong with finding classes annotated with %s",
          exception, annotation.getCanonicalName());
      return result;
    }
    return result;
  }
}
