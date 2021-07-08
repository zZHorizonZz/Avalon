package com.github.avalon.resource;

import com.github.avalon.resource.data.Resource;

import java.util.HashMap;
import java.util.Map;

public class ResourceModule {

  private final Map<String, Resource> resources;

  public ResourceModule() {
      resources = new HashMap<>();
  }

  public String loadResource(String path, String name) {
    Resource resource = new Resource(path, name);
    return resource.getResourceContent();
  }
}
