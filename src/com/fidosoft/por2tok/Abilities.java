package com.fidosoft.por2tok;

import java.util.*;

public class Abilities {
  private Map<String, String> abilityBonuses = new HashMap<>();
  private Map<String, String> abilityScores = new HashMap<>();

  public Abilities(){
  }
  
  public String getStr(){return abilityScores.get("Strength");}
  public String getDex(){return abilityScores.get("Dexterity");}
  public String getCon(){return abilityScores.get("Constitution");}
  public String getInt(){return abilityScores.get("Intelligence");}
  public String getWis(){return abilityScores.get("Wisdom");}
  public String getCha(){return abilityScores.get("Charisma");}
  public String getStrBonus(){return abilityBonuses.get("Strength");}
  public String getDexBonus(){return abilityBonuses.get("Dexterity");}
  public String getConBonus(){return abilityBonuses.get("Constitution");}
  public String getIntBonus(){return abilityBonuses.get("Intelligence");}
  public String getWisBonus(){return abilityBonuses.get("Wisdom");}
  public String getChaBonus(){return abilityBonuses.get("Charisma");}
  
  public void addAbilityBonus(String ability, String bonus){
    abilityBonuses.put(ability, bonus);
  }
  public void addAbilityScore(String ability, String score){
    abilityScores.put(ability, score);
  }

}
