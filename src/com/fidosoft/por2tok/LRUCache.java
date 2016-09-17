package com.fidosoft.por2tok;

import java.util.*;

@SuppressWarnings("serial")
public class LRUCache<K, V> extends LinkedHashMap<K, V> {
  private int cacheSize;

  public LRUCache(int cacheSize) {
    super(16, 0.75f, true);
    this.cacheSize = cacheSize;
  }

  protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
    return size() > cacheSize;
  }
}