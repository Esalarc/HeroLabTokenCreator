package com.fidosoft.por2tok;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import freemarker.template.TemplateException;

public class WeaponTest {

  @Test
  public void testSetAttack_singleHappy() throws Exception {
    Weapon weapon = new Weapon();
    weapon.setAttack("+3");
    assertEquals(1, weapon.getAttacks().size());
  }
  @Test
  public void testSetAttack_multipleHappy() throws Exception {
    Weapon weapon = new Weapon();
    weapon.setAttack("+6/+1");
    assertEquals(2, weapon.getAttacks().size());
    assertEquals(6, weapon.getAttacks().get(0).getModifier());
    assertEquals(1, weapon.getAttacks().get(1).getModifier());
  }
  @Test
  public void testSetAttack_multipleMixedHappy() throws Exception {
    Weapon weapon = new Weapon();
    weapon.setAttack("+4/-1");
    assertEquals(2, weapon.getAttacks().size());
    assertEquals(4, weapon.getAttacks().get(0).getModifier());
    assertEquals(-1, weapon.getAttacks().get(1).getModifier());
  }
  @Test
  public void testSetAttack_multipleWithZeroMixedHappy() throws Exception {
    Weapon weapon = new Weapon();
    weapon.setAttack("+5/+0");
    assertEquals(2, weapon.getAttacks().size());
    assertEquals(5, weapon.getAttacks().get(0).getModifier());
    assertEquals(0, weapon.getAttacks().get(1).getModifier());
  }
  @Test
  public void testSetCritRange_rangeHappy() throws Exception {
    Weapon weapon = new Weapon();
    weapon.setCritRange("19-20/x3");
    assertEquals(19, weapon.getCritLow());
    assertEquals(20, weapon.getCritHigh());
    assertEquals(3, weapon.getCritMultiplier());
  }
  @Test
  public void testSetCritRange_singleHappy() throws Exception {
    Weapon weapon = new Weapon();
    weapon.setCritRange("20/x3");
    assertEquals(20, weapon.getCritLow());
    assertEquals(20, weapon.getCritHigh());
  }
  @Test
  public void testSetCritRange() throws Exception {
    String critRange = "19-20/x3";
    Weapon sut = new Weapon();
    sut.setCritRange(critRange);
    assertEquals(critRange, sut.getCritRange()); 
  }
  
  @Test 
  public void testTemplate() throws TemplateException{
    Weapon weapon = new Weapon();
    weapon.setAttack("+5/+2/+1/-4");
    weapon.setCritRange("19-20/x3");
    weapon.setDamage("1d8+5 +1d6 cold");
    weapon.setName("TestWeapon");
    
    TemplateParser parser = new TemplateParser();
    parser.setObject("weapon", weapon);
    System.out.println(parser.parseResource("weapon_test.ftl"));
  }
  @Test
  public void testSetDamage_noMod() throws Exception {
    Weapon weapon = new Weapon();
    weapon.setDamage("1d6");
    assertEquals(1, weapon.getDamage().size());
    assertEquals("1d6", weapon.getDamage().get(0).getDamage());
    assertEquals("", weapon.getDamage().get(0).getType());
  }
  @Test
  public void testSetDamage_withMod() throws Exception {
    Weapon weapon = new Weapon();
    weapon.setDamage("1d3 nonlethal");
    assertEquals("1d3", weapon.getDamage().get(0).getDamage());
    assertEquals("nonlethal", weapon.getDamage().get(0).getType());
  }
  @Test
  public void testSetDamage_multiple() throws Exception {
    Weapon weapon = new Weapon();
    weapon.setDamage("1d8+5 + 1d6 cold");
    assertEquals(2, weapon.getDamage().size());
    assertEquals("1d8+5", weapon.getDamage().get(0).getDamage());
    assertEquals("", weapon.getDamage().get(0).getType());
    assertEquals("1d6", weapon.getDamage().get(1).getDamage());
    assertEquals("cold", weapon.getDamage().get(1).getType());
  }
}
