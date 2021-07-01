package com.github.avalon.descriptor;

import com.github.avalon.resource.data.ResourceIdentifier;

public abstract class Descriptor<T> {

  private String name;

  public Descriptor(String name) {
    this.name = name;
  }

  public ResourceIdentifier getResourceLocation(ResourceType resourceType) {
    return new ResourceIdentifier(resourceType.getResourceName(), getName());
  }

  public ResourceIdentifier getResourceLocation() {
    return new ResourceIdentifier(getName());
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
