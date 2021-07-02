package com.github.avalon.annotation;

import org.reflections.Reflections;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Objects;
import java.util.Set;

/**
 * This class provides and scanning tools for scan of annotated classes based on Google Guava
 * Reflection.
 *
 * @author Horizon
 * @version 1.0
 */
public class AnnotationScanner {

  public static final String DEFAULT_REFLECTION_PATH = "com.github.avalon";

  public <A extends Annotation> Set<Class<?>> scanForClassesByAnnotation(Class<A> annotation) throws IOException {
    return scanForClassesByAnnotation(null, annotation);
  }
  /**
   * Scans for the top level classes annotated with given {@link Annotation}.
   *
   * @param annotation Annotation that will be checked.
   * @param path {@link Class} is path that will be used for search of annotated
   *     classes.
   * @param <A> Annotation type.
   * @return List of found annotated classes.
   */
  public <A extends Annotation> Set<Class<?>> scanForClassesByAnnotation(
          String path, Class<A> annotation) {
    Objects.requireNonNull(annotation, "Annotation should be set!");

    Reflections reflections = new Reflections(path == null ? AnnotationScanner.DEFAULT_REFLECTION_PATH : path);
    Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(annotation);

    return annotated;
  }
}
