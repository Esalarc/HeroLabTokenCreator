package com.fidosoft.por2tok;

import java.util.*;

public class Abilities {
  private Map<String, String> abilityBonuses = new HashMap<>();
  private Map<String, String> abilityScores = new HashMap<>();

  public Abilities(){
  }
  
  public String getStr(){return abilityScores.get("str");}
  public String getDex(){return abilityScores.get("dex");}
  public String getCon(){return abilityScores.get("con");}
  public String getInt(){return abilityScores.get("int");}
  public String getWis(){return abilityScores.get("wis");}
  public String getCha(){return abilityScores.get("cha");}
  public String getStrBonus(){return abilityBonuses.get("str");}
  public String getDexBonus(){return abilityBonuses.get("dex");}
  public String getConBonus(){return abilityBonuses.get("con");}
  public String getIntBonus(){return abilityBonuses.get("int");}
  public String getWisBonus(){return abilityBonuses.get("wis");}
  public String getChaBonus(){return abilityBonuses.get("cha");}
  
  public void addAbilityBonus(String ability, String bonus){
    abilityBonuses.put(ability, bonus);
  }
  public void addAbilityScore(String ability, String score){
    abilityScores.put(ability, score);
  }

}
