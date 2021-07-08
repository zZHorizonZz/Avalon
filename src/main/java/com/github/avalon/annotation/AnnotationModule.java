package com.github.avalon.annotation;

import com.github.avalon.concurrent.NetworkTaskExecutor;
import com.github.avalon.console.logging.DefaultLogger;
import com.github.avalon.module.ServerModule;
import com.github.avalon.server.IServer;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class AnnotationModule extends ServerModule {

  public static final DefaultLogger LOGGER = new DefaultLogger(AnnotationModule.class);

  public static final NetworkTaskExecutor ANNOTATION_TASK_EXECUTOR = new NetworkTaskExecutor();

  public AnnotationModule(IServer host) {
    super("Annotation Module", host);
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
