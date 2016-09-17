package com.fidosoft.por2tok;

import static org.junit.Assert.*;

import org.junit.Test;

public class AttackTest {
  @Test
  public void testAttack_positiveHappy(){
    Attack sut = new Attack("+3");
    assertEquals(3, sut.getModifier());
  }
  @Test
  public void testAttack_negativeHappy(){
    Attack sut = new Attack("-3");
    assertEquals(-3, sut.getModifier());
  }
  @Test(expected=IllegalArgumentException.class)
  public void testAttack_nullString(){
    Attack sut = new Attack(null);
  }
  @Test(expected=IllegalArgumentException.class)
  public void testAttack_emptyString(){
    Attack sut = new Attack("");
  }
  @Test(expected=IllegalArgumentException.class)
  public void testAttack_invalidString(){
    Attack sut = new Attack("cat");
  }
}
