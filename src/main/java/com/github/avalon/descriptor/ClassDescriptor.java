package com.github.avalon.descriptor;

import com.github.avalon.annotation.AnnotationScanner;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ClassDescriptor<T> extends Descriptor<T> {

  private final Map<String, ResourceType> classes; //TODO In the future this should be Map<ResourceIdentifier, List<Object>>

  public ClassDescriptor(String name) {
    super(name);

      classes = new HashMap<>();
  }

  public void scanForDescriptors(Class<T> classToScan) {
    AnnotationScanner scanner = new AnnotationScanner();

    try {
      Set<Class<?>> classes =
          scanner.scanForClassesByAnnotation(DescriptorIdentifier.class);

      for (Class<?> clazz : classes) {
        DescriptorIdentifier descriptorIdentifier = clazz.getAnnotation(DescriptorIdentifier.class);

        ResourceType type = descriptorIdentifier.type();
        String name = descriptorIdentifier.name();
        Class<?> descriptorClass = descriptorIdentifier.descriptorClass();

        if (descriptorClass.equals(classToScan))
          this.classes.put(name, type);
      }
    } catch (IOException ignored) {
      // Error can occur but is not a problem.
    }
  }

  public Map<String, ResourceType> getClasses() {
    return classes;
  }

  public void add(String name, ResourceType resourceType) {
    if (classes.containsKey(name))
      throw new KeyAlreadyExistsException("Class with name " + name + " already exist!");

      classes.put(name, resourceType);
  }
}
