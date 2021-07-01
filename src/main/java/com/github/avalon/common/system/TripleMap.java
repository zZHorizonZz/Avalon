package com.github.avalon.common.system;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * This class is similar to {@link java.util.Map} but it has one more key. This class was written
 * for TownRealm to save TownBlock data. But you can use it as you want basically it's map with
 * similar functionality.
 *
 * @author Horizon
 * @apiNote This class may change in future versions because of for example implementation some sort
 *     of getting algorithms, etc.
 * @version 1.0
 * @param <K> Main key
 * @param <S> Sub key
 * @param <V> Value
 */
public class TripleMap<K, S, V> implements Serializable, Cloneable {

  private final Set<Entry<K, S, V>> entrySet;

  public TripleMap() {
    entrySet = new HashSet<>();
  }

  public TripleMap(TripleMap<K, S, V> tripleMap) {
    entrySet = new HashSet<>();
    entrySet.addAll(tripleMap.getEntrySet());
  }

  public void put(K key, S subKey, V value) {
    entrySet.add(new Entry<>(key, subKey, value));
  }

  public void removeByKey(K key) {
    entrySet.removeIf(entry -> entry.getKey().equals(key));
  }

  public void removeBySubKey(S subKey) {
    entrySet.removeIf(entry -> entry.getSubKey().equals(subKey));
  }

  public void remove(K key, S subKey) {
    entrySet.removeIf(entry -> entry.getKey().equals(key) && entry.getSubKey().equals(subKey));
  }

  public boolean containsKey(K key) {
    return getByKey(key) != null;
  }

  public boolean containsSubKey(S subKey) {
    return getBySubKey(subKey) != null;
  }

  public boolean contains(K key, S subKey) {
    return get(key, subKey) != null;
  }

  @Nullable
  public V getByKey(K key) {
    if (size() == 0) {
      return null;
    }

    assert entrySet != null : "Entry Set for this triple map is null!";

    Iterator<Entry<K, S, V>> entryIterator = iterator();
    while (entryIterator.hasNext()) {
      Entry<K, S, V> entry = entryIterator.next();

      if (entry.getKey().equals(key)) {
        return entry.getValue();
      }
    }

    return null;
  }

  @Nullable
  public V getBySubKey(S subKey) {
    if (size() == 0) {
      return null;
    }

    assert entrySet != null : "Entry Set for this triple map is null!";

    Iterator<Entry<K, S, V>> entryIterator = iterator();
    while (entryIterator.hasNext()) {
      Entry<K, S, V> entry = entryIterator.next();

      if (entry.getSubKey().equals(subKey)) {
        return entry.getValue();
      }
    }

    return null;
  }

  @Nullable
  public V get(K key, S subKey) {
    return getOrDefault(key, subKey, null);
  }

  @Nullable
  public V getOrDefault(K key, S subKey, @Nullable V defaultValue) {
    if (size() == 0) {
      return defaultValue;
    }

    assert entrySet != null : "Entry Set for this triple map is null!";

    Iterator<Entry<K, S, V>> entryIterator = iterator();
    while (entryIterator.hasNext()) {
      Entry<K, S, V> entry = entryIterator.next();

      if (entry.getKey().equals(key) && entry.getSubKey().equals(subKey)) {
        return entry.getValue();
      }
    }

    return defaultValue;
  }

  public void clear() {
    entrySet.clear();
  }

  public int size() {
    return entrySet.size();
  }

  public Iterator<Entry<K, S, V>> iterator() {
    return entrySet.iterator();
  }

  public Set<Entry<K, S, V>> getEntrySet() {
    return entrySet;
  }

  public static class Entry<K, S, V> {

    private final K key;
    private final S subKey;
    private final V value;

    public Entry(K key, S subKey, V value) {
      this.key = key;
      this.subKey = subKey;
      this.value = value;
    }

    public K getKey() {
      return key;
    }

    public S getSubKey() {
      return subKey;
    }

    public V getValue() {
      return value;
    }
  }
}
