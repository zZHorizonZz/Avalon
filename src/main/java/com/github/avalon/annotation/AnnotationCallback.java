package com.github.avalon.annotation;

import java.lang.annotation.Annotation;
import java.util.Set;
import java.util.concurrent.*;

public class AnnotationCallback<A extends Annotation> implements Callable<Set<Class<?>>> {

  private final Class<A> annotation;

  public AnnotationCallback(Class<A> annotation) {
    this.annotation = annotation;
  }

  @Override
  public Set<Class<?>> call() throws ExecutionException, InterruptedException, TimeoutException {
    CompletableFuture<Set<Class<?>>> future = new CompletableFuture<>();

    AnnotationManager.ANNOTATION_TASK_EXECUTOR.submitTask(
        () -> {
          AnnotationScanner scanner = new AnnotationScanner();
          Set<Class<?>> result = scanner.scanForClassesByAnnotation(null, annotation);
          future.complete(result);
        });

    return future.get(10, TimeUnit.SECONDS);
  }
}
