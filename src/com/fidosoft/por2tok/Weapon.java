package com.fidosoft.por2tok;

import java.util.*;
import java.util.regex.*;

public class Weapon {
  private String name;
  private List<Attack> attacks = new LinkedList<>();
  private int critMultiplier;
  private int critLow;
  private int critHigh;
  private boolean ranged;
  private List<Damage> damage = new LinkedList<>();
    
  public Weapon(){
    
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setAttack(String attack) {
    Pattern pat = Pattern.compile("([\\+\\-]\\d+)");
    Matcher m = pat.matcher(attack);
    while (m.find()){
      attacks.add(new Attack(m.group(1)));
    }
  }

  public List<Attack> getAttacks(){
    return attacks;
  }
  public List<Damage> getDamage() {
    return damage;
  }

  public void setDamage(String damageString) {
    Pattern pat = Pattern.compile("(\\d*d\\d+[-+0-9]*)([^+]*)");
    Matcher m = pat.matcher(damageString);
    while (m.find()){
      Damage damage = new Damage();
      damage.setDamage(m.group(1));
      damage.setType(m.group(2).trim());
      this.damage.add(damage);
    }
  }

  public String getCritRange() {
    if (critLow == critHigh){
      return "x" + critMultiplier;
    } else {
      return critLow + "-" + critHigh + "/x" + critMultiplier;
    }
  }

  public void setCritRange(String critRange) {
    Pattern pat = Pattern.compile("(\\d+)\\s*\\-\\s*(\\d+)");
    Matcher m = pat.matcher(critRange);
    if (!m.find()){
      critLow = 20;
      critHigh = 20;
    } else {
      critLow = Integer.valueOf(m.group(1));
      critHigh = Integer.valueOf(m.group(2));
    }
    Pattern patMult = Pattern.compile("x(\\d+)");
    m = patMult.matcher(critRange);
    if (m.find()){
      setCritMultiplier(Integer.valueOf(m.group(1)));
    } else {
      setCritMultiplier(2);
    }
  }

  public boolean isRanged() {
    return ranged;
  }

  public void setRanged(boolean ranged) {
    this.ranged = ranged;
  }

  public int getCritLow() {
    return critLow;
  }

  public int getCritHigh() {
    return critHigh;
  }

  public int getCritMultiplier() {
    return critMultiplier;
  }

  public void setCritMultiplier(int critMultiplier) {
    this.critMultiplier = critMultiplier;
  }
}
