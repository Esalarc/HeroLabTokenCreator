package com.fidosoft.por2tok;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.xpath.XPathExpressionException;

public class XMLParser {
  private XPaths xpaths; 
  private XML xml;
  private Character target = null;
  private static final Pattern REGEX_DARKVISION_WITH_DISTANCE = Pattern.compile("Darkvision \\((\\d+)");
  private static final Pattern REGEX_DARKVISION = Pattern.compile("Darkvision");

  public XMLParser(XPaths xpaths, String xmlContent) {
    this.xpaths = xpaths;
    this.xml = new XML();
    this.xml.load(xmlContent);
  }
  public void loadCharacterFromXML(Character character) {
    try{
      target = character;
      xml = xml.find(xpaths.BASE_CHARACTER);
      
      parseBase();
      parseLanguages();
      parseAttributes();
      parseSaves();
      parseResists();
      parseManeuvers();
      parseSkills();
      parseFeats();
      parseTraits();
      parseSenses();
      parseWeapons();
      parseItems();
      parseSpecials();
      parseItempowers();
      parseSpells();
    } catch (XPathExpressionException ex) {
      ex.printStackTrace();
      throw new IllegalArgumentException(ex);
    }
  }
  private void parseSpells() throws XPathExpressionException {
    parseSpellsFromSource(TypeOfSpell.SPELLS, xpaths.SPELLBOOK, xpaths.SPELLBOOK_SPELL,
                          xpaths.SPELLBOOK_NAME, xpaths.SPELLBOOK_LEVEL, xpaths.SPELLBOOK_CASTTIME, 
                          xpaths.SPELLBOOK_RANGE, xpaths.SPELLBOOK_TARGET, xpaths.SPELLBOOK_AREA, 
                          xpaths.SPELLBOOK_EFFECT, xpaths.SPELLBOOK_DURATION, xpaths.SPELLBOOK_SAVE, 
                          xpaths.SPELLBOOK_RESIST, xpaths.SPELLBOOK_DC, xpaths.SPELLBOOK_COMPONENTTEXT, 
                          xpaths.SPELLBOOK_SCHOOLTEXT, xpaths.SPELLBOOK_DESCRIPTION, xpaths.SPELLBOOK_CASTERLEVEL);
    parseSpellsFromSource(TypeOfSpell.SPELLS_MEMORIZED, xpaths.MEMORIZED_SPELLS, xpaths.MEMORIZED_SPELLS_SPELL,
                          xpaths.MEMORIZED_SPELLS_NAME, xpaths.MEMORIZED_SPELLS_LEVEL, xpaths.MEMORIZED_SPELLS_CASTTIME, 
                          xpaths.MEMORIZED_SPELLS_RANGE, xpaths.MEMORIZED_SPELLS_TARGET, xpaths.MEMORIZED_SPELLS_AREA, 
                          xpaths.MEMORIZED_SPELLS_EFFECT, xpaths.MEMORIZED_SPELLS_DURATION, xpaths.MEMORIZED_SPELLS_SAVE, 
                          xpaths.MEMORIZED_SPELLS_RESIST, xpaths.MEMORIZED_SPELLS_DC, xpaths.MEMORIZED_SPELLS_COMPONENTTEXT, 
                          xpaths.MEMORIZED_SPELLS_SCHOOLTEXT, xpaths.MEMORIZED_SPELLS_DESCRIPTION, xpaths.MEMORIZED_SPELLS_CASTERLEVEL);
    parseSpellsFromSource(TypeOfSpell.SPELLS_KNOWN, xpaths.KNOWN_SPELLS, xpaths.KNOWN_SPELLS_SPELL,
                          xpaths.KNOWN_SPELLS_NAME, xpaths.KNOWN_SPELLS_LEVEL, xpaths.KNOWN_SPELLS_CASTTIME, 
                          xpaths.KNOWN_SPELLS_RANGE, xpaths.KNOWN_SPELLS_TARGET, xpaths.KNOWN_SPELLS_AREA, 
                          xpaths.KNOWN_SPELLS_EFFECT, xpaths.KNOWN_SPELLS_DURATION, xpaths.KNOWN_SPELLS_SAVE, 
                          xpaths.KNOWN_SPELLS_RESIST, xpaths.KNOWN_SPELLS_DC, xpaths.KNOWN_SPELLS_COMPONENTTEXT, 
                          xpaths.KNOWN_SPELLS_SCHOOLTEXT, xpaths.KNOWN_SPELLS_DESCRIPTION, xpaths.KNOWN_SPELLS_CASTERLEVEL);
  }
  private void parseSpellsFromSource(TypeOfSpell type, String parent, String child, 
                                    String name, String level, String casttime, 
                                    String range, String target, String area,
                                    String effect, String duration, String save,
                                    String resist, String dc, String componentText,
                                    String schoolText, String description, String casterLevel) throws XPathExpressionException{
    for (XML spell : xml.find(parent).list(child)){
      String spellName = createSpellName(spell, name, level, casttime, range, target, area, effect, duration, save, resist, dc, componentText, schoolText, description, casterLevel);
      this.target.addSpell(type, spellName);
    }    
  }
  private String createSpellName(XML spell, String name, String level, String casttime, 
                                 String range, String target, String area,
                                 String effect, String duration, String save,
                                 String resist, String dc, String componentText,
                                 String schoolText, String description, String casterLevel) throws XPathExpressionException{    
    StringBuilder spellName = new StringBuilder();
    spellName.append("name:"); 
    spellName.append(spell.get(name)).append("\n");
    spellName.append("level:");
    spellName.append(spell.get(level)).append("\n");
    spellName.append("casttime:");
    spellName.append(spell.get(casttime)).append("\n");
    spellName.append("range:");
    spellName.append(spell.get(range)).append("\n"); 
    spellName.append("target:");
    spellName.append(spell.get(target)).append("\n"); 
    spellName.append("area:");
    spellName.append(spell.get(area)).append("\n");
    spellName.append("effect:");
    spellName.append(spell.get(effect)).append("\n"); 
    spellName.append("duration:");
    spellName.append(spell.get(duration)).append("\n"); 
    spellName.append("save:");
    String saveValue = spell.get(save).replaceAll("DC\\s*\\d+\\s*", "");
    if (saveValue.trim().length() == 0)
      saveValue = "None";
    spellName.append(saveValue).append("\n");
    spellName.append("resist:");
    String resistValue = spell.get(resist);
    if (resistValue.trim().length() == 0)
      resistValue = "NA";      
    spellName.append(resistValue).append("\n"); 
    spellName.append("dc:");
    spellName.append(spell.get(dc)).append("\n"); 
    spellName.append("componenttext:");
    spellName.append(spell.get(componentText)).append("\n");
    spellName.append("schooltext:");
    spellName.append(spell.get(schoolText)).append("\n"); 
    spellName.append("description:");
    spellName.append(spell.find(description).text()).append("\n");
    spellName.append("casterlevel:");
    spellName.append(spell.get(casterLevel)).append("\n");

    return spellName.toString();        
  }
  
  private void parseItempowers() throws XPathExpressionException {
    for (XML special : xml.find(xpaths.MAGICITEMS).list(xpaths.MAGICITEMS_ITEM)){
      for (XML power : special.list(xpaths.MAGICITEMS_ITEM_ITEMPOWER)){
        target.addItemPower(power.get(xpaths.MAGICITEMS_ITEM_ITEMPOWER_NAME), power.find(xpaths.MAGICITEMS_ITEM_ITEMPOWER_DESCRIPTION).text());
      }
    }
  }
  private void parseSpecials() throws XPathExpressionException {
    parseSpecial(xpaths.MOVEMENT, xpaths.MOVEMENT_SPECIAL, xpaths.MOVEMENT_SPECIAL_NAME, xpaths.MOVEMENT_SPECIAL_DESCRIPTION);
    parseSpecial(xpaths.ATTACK, xpaths.ATTACK_SPECIAL, xpaths.ATTACK_SPECIAL_NAME, xpaths.ATTACK_SPECIAL_DESCRIPTION);
    parseSpecial(xpaths.SKILLABILITIES, xpaths.SKILLABILITIES_SPECIAL, xpaths.SKILLABILITIES_SPECIAL_NAME, xpaths.SKILLABILITIES_SPECIAL_DESCRIPTION);
    parseSpecial(xpaths.OTHERSPECIALS, xpaths.OTHERSPECIALS_SPECIAL, xpaths.OTHERSPECIALS_SPECIAL_NAME, xpaths.OTHERSPECIALS_SPECIAL_DESCRIPTION);
    parseSpecial(xpaths.AURAS, xpaths.AURAS_SPECIAL, xpaths.AURAS_SPECIAL_NAME, xpaths.AURAS_SPECIAL_DESCRIPTION);
    parseSpecial(xpaths.SPELLLIKE, xpaths.SPELLLIKE_SPECIAL, xpaths.SPELLLIKE_SPECIAL_NAME, xpaths.SPELLLIKE_SPECIAL_DESCRIPTION);
    parseSpecial(xpaths.DEFENSIVE, xpaths.DEFENSIVE_SPECIAL, xpaths.DEFENSIVE_SPECIAL_NAME, xpaths.DEFENSIVE_SPECIAL_DESCRIPTION);
    parseSpecial(xpaths.HEALTH, xpaths.HEALTH_SPECIAL, xpaths.HEALTH_SPECIAL_NAME, xpaths.HEALTH_SPECIAL_DESCRIPTION);
  }
  private void parseSpecial(String parent, String child, String name, String description) throws XPathExpressionException{
    for (XML special : xml.find(parent).list(child)){
      target.addSpecial(special.get(name), special.find(description).text());
    }
  }
  private void parseItems() throws XPathExpressionException {
    parseGear();
    parseMagic();
  }
  private void parseGear() throws XPathExpressionException {
    for (XML item : xml.find(xpaths.GEAR).list(xpaths.GEAR_ITEM)){
      String itemName = createItemName(item, xpaths.GEAR_ITEM_NAME, xpaths.GEAR_ITEM_QUANTITY, xpaths.GEAR_ITEM_COST, xpaths.GEAR_ITEM_COST_TEXT);
      target.addItem(itemName);
    }
  }
  private void parseMagic() throws XPathExpressionException{
    for (XML item : xml.find(xpaths.MAGICITEMS).list(xpaths.MAGICITEMS_ITEM)){
      String itemName = createItemName(item, xpaths.MAGICITEMS_ITEM_NAME, xpaths.MAGICITEMS_ITEM_QUANTITY, xpaths.MAGICITEMS_ITEM_COST, xpaths.MAGICITEMS_ITEM_COST_TEXT);
      target.addItem(itemName);
    }
  }
  private String createItemName(XML item, String name, String quantity, String cost, String costText) throws XPathExpressionException{
    StringBuilder itemName = new StringBuilder();
    
    itemName.append("name:"); 
    itemName.append(item.get(name)); 
    itemName.append("\n");
    itemName.append("num:"); 
    itemName.append(item.get(quantity)); 
    itemName.append("\n");
    itemName.append("value:");
    itemName.append(item.find(cost).get(costText));
    itemName.append("\n");
    return itemName.toString();
  }
  private void parseWeapons() throws XPathExpressionException {
    parseMeleeWeapons();
    parseRangedWeapons();
  }
  private void parseMeleeWeapons() throws XPathExpressionException{
    for (XML weapon : xml.find(xpaths.MELEE).list(xpaths.MELEE_WEAPON)){
      target.addWeapon(createWeapon(weapon, "", xpaths.MELEE_WEAPON_NAME, xpaths.MELEE_WEAPON_ATTACK, xpaths.MELEE_WEAPON_DAMAGE, xpaths.MELEE_WEAPON_CRIT, false));
      if (weapon.get(xpaths.MELEE_WEAPON_CATEGORY_TEXT).indexOf("Thrown") > 0){
        target.addWeapon(createWeapon(weapon, "Thrown ", xpaths.MELEE_WEAPON_NAME, xpaths.MELEE_WEAPON_RANGED_ATTACK, xpaths.MELEE_WEAPON_DAMAGE, xpaths.MELEE_WEAPON_CRIT, true));
      }
    }
  }
  private void parseRangedWeapons() throws XPathExpressionException{
    for (XML weapon : xml.find(xpaths.RANGED).list(xpaths.RANGED_WEAPON)){
      target.addWeapon(createWeapon(weapon, "", xpaths.RANGED_WEAPON_NAME, xpaths.RANGED_WEAPON_ATTACK, xpaths.RANGED_WEAPON_DAMAGE, xpaths.RANGED_WEAPON_CRIT, true));
    }
  }
  private Weapon createWeapon(XML weaponXML, String namePrefix, String name, String attack, String damage, String crit, boolean isRanged) throws XPathExpressionException{
    Weapon weapon = new Weapon();
    weapon.setName(namePrefix + weaponXML.get(name).replaceAll("\\s+\\(.+\\)", ""));
    weapon.setAttack(weaponXML.get(attack));
    weapon.setDamage(weaponXML.get(damage));
    weapon.setCritRange(weaponXML.get(crit));
    weapon.setRanged(isRanged);
    return weapon;
  }
  private void parseSenses() throws XPathExpressionException {
    for (XML sense : xml.find(xpaths.SENSES).list(xpaths.SENSES_SPECIAL)){
      if (sense.get(xpaths.SENSES_SPECIAL_SHORTNAME).equals("Low-Light Vision")){
        target.setLowLightVision(1);
      } else {
        Matcher m = REGEX_DARKVISION_WITH_DISTANCE.matcher(sense.get(xpaths.SENSES_SPECIAL_SHORTNAME));
        if (m.matches()){
          target.setDarkvision(Integer.valueOf(m.group(1)));
        } else {
          m = REGEX_DARKVISION.matcher(sense.get(xpaths.SENSES_SPECIAL_SHORTNAME));
          if (m.matches()){
            target.setDarkvision(60);
          } else {
            target.addSpecial(sense.get(xpaths.SENSES_SPECIAL_NAME), sense.find(xpaths.SENSES_SPECIAL_DESCRIPTION).text());
          }
          
        }
      }
    }
  }
  private void parseTraits() throws XPathExpressionException {
    for (XML trait : xml.find(xpaths.TRAITS).list(xpaths.TRAITS_TRAIT)){
      target.addTrait(trait.get(xpaths.TRAITS_TRAIT_NAME), trait.find(xpaths.TRAITS_TRAIT_DESCRIPTION).text());
    }
  }
  private void parseFeats() throws XPathExpressionException {
    for (XML feat : xml.find(xpaths.FEATS).list(xpaths.FEATS_FEAT)){
      target.addFeat(feat.get(xpaths.FEATS_FEAT_NAME), feat.find(xpaths.FEATS_FEAT_DESCRIPTION).text());
    }
  }
  private void parseSkills() throws XPathExpressionException {
    for (XML skill : xml.find(xpaths.SKILLS).list(xpaths.SKILLS_SKILL)){
      target.addSkill(skill.get(xpaths.SKILLS_NAME), skill.get(xpaths.SKILLS_VALUE));
    }
  }
  private void parseManeuvers() throws XPathExpressionException {    
    XML child = xml.find(xpaths.MANEUVERS);
    target.setCmb(child.get(xpaths.MANEUVERS_CMB));
    target.setCmd(child.get(xpaths.MANEUVERS_CMD));
    target.setCmd_flat(child.get(xpaths.MANEUVERS_CMDFLATFOOTED));

    for (XML maneuver : child.list(xpaths.MANEUVERS_MANEUVERTYPE)){
      target.addManeuver(maneuver.get(xpaths.MANEUVERS_MANEUVER_NAME), maneuver.get(xpaths.MANEUVERS_MANEUVER_CMB));
    }
  }
  private void parseResists() throws XPathExpressionException {
    parseResist(xpaths.RESISTS_IMMUNITIES);
    parseResist(xpaths.RESISTS_DAMAGEREDUCTION);
    parseResist(xpaths.RESISTS_RESISTANCES);
    parseResist(xpaths.RESISTS_WEAKNESSES);
  }
  private void parseResist(String parent) throws XPathExpressionException{
    for (XML resistance : xml.find(parent).list(xpaths.RESIST_SPECIAL)){
      target.addResist(resistance.get(xpaths.RESIST_NAME), resistance.find(xpaths.RESIST_DESCRIPTION).text());
    }
  }
  private void parseSaves() throws XPathExpressionException {
    for (XML save : xml.find(xpaths.SAVES).list(xpaths.SAVES_SAVE)) {
      target.addSave(save.get(xpaths.SAVES_SAVE_ABBR), save.get(xpaths.SAVES_SAVE_SAVE));
    }
  }
  private void parseAttributes() throws XPathExpressionException {
    for (XML attribute : xml.find(xpaths.ATTRIBUTES).list(xpaths.ATTRIBUTES_ATTRIBUTE)){      
      target.addAbilityScore(attribute.get(xpaths.ATTRIBUTES_ATTRIBUTE_NAME), attribute.find(xpaths.ATTRIBUTES_ATTRIBUTE_ATTRVALUE).get(xpaths.ATTRIBUTES_ATTRIBUTE_ATTRVALUE_MODIFIED));
      target.addAbilityBonus(attribute.get(xpaths.ATTRIBUTES_ATTRIBUTE_NAME), attribute.find(xpaths.ATTRIBUTES_ATTRIBUTE_ATTRBONUS).get(xpaths.ATTRIBUTES_ATTRIBUTE_ATTRBONUS_MODIFIED));
    }
  }
  private void parseLanguages() throws XPathExpressionException {
    for (XML language : xml.find(xpaths.LANGUAGES).list(xpaths.LANGUAGES_LANGUAGE)){
      target.addLanguage(language.get(xpaths.LANGUAGES_LANGUAGE_NAME));
    }
  }
  private void parseBase() throws XPathExpressionException {
    target.setRole(xml.get(xpaths.BASE_ROLE));
    target.setName(xml.get(xpaths.BASE_CHARACTER_NAME));
    target.setTokenName(xml.get(xpaths.BASE_CHARACTER_NAME) + ".token");
    target.setPlayer(xml.get(xpaths.BASE_PLAYERNAME));
    target.setClasses(xml.get(xpaths.BASE_CLASSES));
    target.setRace(xml.find(xpaths.BASE_RACE).get(xpaths.BASE_RACETEXT));
    target.setAlignment(xml.find(xpaths.BASE_ALIGNMENT).get(xpaths.BASE_ALIGNMENT_NAME));
    parseSize();
    parseAC();
    parseHealth();
    target.setMovement(xml.find(xpaths.BASE_MOVEMENT).find(xpaths.BASE_MOVEMENT_SPEED).get(xpaths.BASE_MOVEMENT_SPEED_VALUE));
    target.setInitiative(xml.find(xpaths.BASE_INITIATIVE).get(xpaths.BASE_INITIATIVE_TOTAL));
    parseAttack();
    target.setGender(xml.find(xpaths.BASE_PERSONAL).get(xpaths.BASE_PERSONAL_GENDER));
    parseMoney();
    target.setXpvalue("0");
    if (xml.find(xpaths.BASE_XPAWARD).isValid())
      target.setXpvalue(xml.find(xpaths.BASE_XPAWARD).get(xpaths.BASE_XPAWARD_VALUE));
  }
  void parseMoney() throws XPathExpressionException {
    XML child = xml.find(xpaths.MONEY);
    target.setPp(child.get(xpaths.MONEY_PP));
    target.setGp(child.get(xpaths.MONEY_GP));
    target.setSp(child.get(xpaths.MONEY_SP));
    target.setCp(child.get(xpaths.MONEY_CP));
  }
  void parseAttack() throws XPathExpressionException {
    XML child = xml.find(xpaths.ATTACK);
    target.setAtk_melee(child.get(xpaths.ATTACK_MELEEATTACK));
    target.setAtk_ranged(child.get(xpaths.ATTACK_RANGEDATTACK));
    target.setBab(child.get(xpaths.ATTACK_BASEATTACK));
  }
  void parseHealth() throws XPathExpressionException {
    XML child = xml.find(xpaths.HEALTH);
    target.setHpc(child.get(xpaths.HEALTH_CURRENTHP));
    target.setHpm(child.get(xpaths.HEALTH_HITPOINTS));
    target.setHd(child.get(xpaths.HEALTH_HITDICE));
  }
  void parseAC() throws XPathExpressionException {
    XML child = xml.find(xpaths.ARMORCLASS);
    target.setAc(child.get(xpaths.ARMORCLASS_AC));
    target.setAc_touch(child.get(xpaths.ARMORCLASS_TOUCH));
    target.setAc_flat(child.get(xpaths.ARMORCLASS_FLATFOOTED));
  }
  void parseSize() throws XPathExpressionException {
    XML child = xml.find(xpaths.SIZE);
    target.setSize(child.get(xpaths.SIZE_NAME).toLowerCase());
    if (target.getSize().trim().length() == 0)
      target.setSize("medium");
    target.setSpace(child.find(xpaths.SIZE_SPACE).get(xpaths.SIZE_SPACE_VALUE));
    target.setReach(child.find(xpaths.SIZE_REACH).get(xpaths.SIZE_REACH_VALUE));
  }

}
