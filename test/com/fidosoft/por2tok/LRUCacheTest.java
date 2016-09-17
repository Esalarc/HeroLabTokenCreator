package com.fidosoft.por2tok;

import static org.junit.Assert.*;

import org.junit.Test;

public class LRUCacheTest {
  @Test
  public void testAdd(){
    LRUCache<Integer, Boolean> sut = new LRUCache<>(2);
    sut.put(1, true);
    sut.put(2, true);
    assertEquals(2, sut.size());
    sut.put(3, false);
    assertEquals(2, sut.size());
    assertFalse(sut.containsKey(1));
    assertTrue(sut.containsKey(2));
    assertTrue(sut.containsKey(3));
    sut.put(2, true);
    sut.put(1, true);
    assertFalse(sut.containsKey(3));
    assertTrue(sut.containsKey(2));
    assertTrue(sut.containsKey(1));
  }
}
