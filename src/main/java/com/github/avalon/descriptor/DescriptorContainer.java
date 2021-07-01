package com.github.avalon.descriptor;

import com.github.avalon.data.Container;
import com.github.avalon.resource.data.ResourceIdentifier;

import java.util.Map;
import java.util.Optional;

public class DescriptorContainer extends Container<ResourceIdentifier, Descriptor<?>> {

  public synchronized void addDescriptor(ResourceIdentifier identifier, Descriptor<?> descriptor) {
    add(identifier, descriptor);
  }

  public synchronized void createDescriptor(String name, Descriptor<?> descriptor) {
    add(new ResourceIdentifier(name), descriptor);
  }

  public synchronized void removeDescriptor(ResourceIdentifier identifier) {
    remove(identifier);
  }

  public Descriptor<?> getDescriptor(ResourceIdentifier identifier) {
    return get(identifier);
  }

  public Optional<Map.Entry<ResourceIdentifier, Descriptor<?>>> getDescriptor(String name) {
    return getRegistry().entrySet().stream().filter(entry -> entry.getKey().getName().equalsIgnoreCase(name)).findFirst();
  }

  public boolean isDescriptor(ResourceIdentifier identifier) {
    return containsKey(identifier);
  }

  public boolean isDescriptor(Descriptor<?> descriptor) {
    return containsValue(descriptor);
  }
}
