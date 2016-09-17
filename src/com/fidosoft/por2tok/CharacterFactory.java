package com.fidosoft.por2tok;

public class CharacterFactory {

  public Character createCharacter(String xmlContent){
    Character character = new Character();
    new XMLParser(new HeroLabV76b(), xmlContent).loadCharacterFromXML(character);
    return character;
  }

}
