package com.github.avalon.data;

import java.util.concurrent.ConcurrentHashMap;

public abstract class Container<K, V> {

  private final ConcurrentHashMap<K, V> registry;

  public Container() {
      registry = new ConcurrentHashMap<>();
  }

  public ConcurrentHashMap<K, V> getRegistry() {
    return registry;
  }

  public void add(K key, V value) {
    registry.put(key, value);
  }

  public void remove(K key) {
    registry.remove(key);
  }

  public V get(K key) {
    return registry.get(key);
  }

  public boolean containsKey(K key) {
    return registry.containsKey(key);
  }

  public boolean containsValue(V value) {
    return registry.containsValue(value);
  }
}
