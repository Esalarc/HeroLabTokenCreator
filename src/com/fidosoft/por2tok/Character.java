package com.fidosoft.por2tok;

import java.awt.image.BufferedImage;
import java.beans.IntrospectionException;
import java.io.*;
import java.security.*;
import java.util.*;
import java.util.prefs.Preferences;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;

import freemarker.template.TemplateException;

public class Character implements AutoCompleteObject {
  private static final MessageDigest digest;
  static{
    MessageDigest d = null;
    try {
      d = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    digest = d;
  }

  private Abilities abilities = new Abilities();
  private String ac;  
  private String ac_flat;
  private String ac_touch;
  private String alignment;
  private String atk_melee;
  private String atk_ranged;
  private String bab;
  private String classes;
  private String cmb;
  private String cmd;
  private String cmd_flat;
  private String cp;
  private Map<String, String> feats = new HashMap<>();
  private String gender;
  private boolean generateToken = true;
  private String gp;
  private String hd;
  private String hpc;
  private String hpm;
  private BufferedImage image = null;
  private BufferedImage tokenImage = null;
  private String initiative;
  private Map<String, String> itemPowers = new HashMap<>();
  private List<String> items = new LinkedList<>();
  private List<String> languages = new LinkedList<String>();
  private Map<String, String> maneuvers = new HashMap<>();
  private String movement;
  private String name;
  private String player;
  private String pp;
  private String race;
  private String reach;
  private Map<String, String> resists = new HashMap<>();
  private String role;
  private Map<String, String> saves = new HashMap<>();
  private String size;
  private Map<String, String> skills = new HashMap<>();
  private String sp;
  private String space;
  private Map<String, String> specials = new HashMap<>();
  private List<String> spells = new LinkedList<>();
  private List<String> spellsKnown = new LinkedList<>();
  private List<String> spellsMemorized = new LinkedList<>();
  private String tokenName;
  private Map<String, String> traits = new HashMap<>();
  private int vision_dark;
  private int vision_lowlight;
  private Set<Weapon> weapons = new HashSet<>();
  private String xpvalue;
  private String htmlStatBlock;
  
  public void addAbilityBonus(String ability, String bonus){
    abilities.addAbilityBonus(ability, bonus);
  }
  public void addAbilityScore(String ability, String score){
    abilities.addAbilityScore(ability, score);
  }
  public void addFeat(String feat, String text){
    feats.put(feat, text);
  }
  public void addItem(String item){
    items.add(item);
  }
  public void addItemPower(String name, String description){
    itemPowers.put(name, description);
  }
  public void addKnownSpell(String spell){
    spellsKnown.add(spell);
  }
  public void addLanguage(String language){
    languages.add(language);
  }
  public void addManeuver(String type, String score){
    maneuvers.put(type, score);
  }
  
  public void addMemorizedSpell(String spell){
    spellsMemorized.add(spell);
  }
  public void addResist(String type, String text){
    resists.put(type, text);
  }
  public void addSave(String type, String save){
    saves.put(type, save);
  }
  public void addSkill(String skill, String score){
    skills.put(skill, score);
  }
  public void addSpecial(String special, String text){
    specials.put(special, text);
  }
  public void addSpell(String spell){
    spells.add(spell);
  }
  public void addSpell(TypeOfSpell type, String spell){
    switch (type){
    case SPELLS:
      addSpell(spell);
      break;
    case SPELLS_MEMORIZED:
      addMemorizedSpell(spell);
      break;
    case SPELLS_KNOWN:
      addKnownSpell(spell);
      break;
    }
  }
  public void addTrait(String trait, String text){
    traits.put(trait, text);
  }
  public void addWeapon(Weapon weapon){
    weapons.add(weapon);
  }
  public String getAc() {
    return ac;
  }
  public String getAc_flat() {
    return ac_flat;
  }
  public String getAc_touch() {
    return ac_touch;
  }
  public String getAlignment() {
    return alignment;
  }
  public String getAtk_melee() {
    return atk_melee;
  }
  public String getAtk_ranged() {
    return atk_ranged;
  }
  public String getBab() {
    return bab;
  }
  public String getClasses() {
    return classes;
  }
  public String getCmb() {
    return cmb;
  }
  public String getCmd() {
    return cmd;
  }
  
  public String getCmd_flat() {
    return cmd_flat;
  }
  public String getCp() {
    return cp;
  }
  public String getGender() {
    return gender;
  }
  public String getGp() {
    return gp;
  }
  public String getHd() {
    return hd;
  }
  public String getHpc() {
    return hpc;
  }
  public String getHpm() {
    return hpm;
  }
  public BufferedImage getImage() {
    return image;
  }
  public BufferedImage getTokenImage() {
    return tokenImage;
  }
  public String getInitiative() {
    return initiative;
  }
  public String getMovement() {
    return movement;
  }
  public String getName() {
    return name;
  }
  public String getPlayer() {
    return player;
  }
  public String getPp() {
    return pp;
  }
  public String getRace() {
    return race;
  }
  public String getReach() {
    return reach;
  }
  public String getRole() {
    return role;
  }
  public String getSize() {
    return size;
  }
  public String getSp() {
    return sp;
  }
  public String getSpace() {
    return space;
  }
  public String getTokenName() {
    return tokenName;
  }
  public int getDarkvision() {
    return vision_dark;
  }
  public int getLowLightVision() {
    return vision_lowlight;
  }
  public String getXpvalue() {
    return xpvalue;
  }
  public boolean isGenerateToken() {
    return generateToken;
  }
  public void setAc(String ac) {
    this.ac = ac;
  }
  public void setAc_flat(String ac_flat) {
    this.ac_flat = ac_flat;
  }
  public void setAc_touch(String ac_touch) {
    this.ac_touch = ac_touch;
  }
  public void setAlignment(String alignment) {
    this.alignment = alignment;
  }
  public void setAtk_melee(String atk_melee) {
    this.atk_melee = atk_melee;
  }
  public void setAtk_ranged(String atk_ranged) {
    this.atk_ranged = atk_ranged;
  }
  public void setBab(String bab) {
    this.bab = bab;
  }
  public void setClasses(String classes) {
    this.classes = classes;
  }
  public void setCmb(String cmb) {
    this.cmb = cmb;
  }
  public void setCmd(String cmd) {
    this.cmd = cmd;
  }
  public void setCmd_flat(String cmd_flat) {
    this.cmd_flat = cmd_flat;
  }
  public void setCp(String cp) {
    this.cp = cp;
  }
  public void setGender(String gender) {
    this.gender = gender;
  }
  public void setGenerateToken(boolean generateToken) {
    this.generateToken = generateToken;
  }
  public void setGp(String gp) {
    this.gp = gp;
  }
  public void setHd(String hd) {
    this.hd = hd;
  }
  public void setHpc(String hpc) {
    this.hpc = hpc;
  }
  public void setHpm(String hpm) {
    this.hpm = hpm;
  }
  public void setImage(BufferedImage image) {
    this.image = image;
  }
  public void setTokenImageImage(BufferedImage image) {
    this.tokenImage = image;
  }
  public void setInitiative(String initiative) {
    this.initiative = initiative;
  }
  public void setMovement(String movement) {
    this.movement = movement;
  }
  public void setName(String name) {
    this.name = name;
  }
  public void setPlayer(String player) {
    this.player = player;
  }
  public void setPp(String pp) {
    this.pp = pp;
  }
  public void setRace(String race) {
    this.race = race;
  }
  public void setReach(String reach) {
    this.reach = reach;
  }
  public void setRole(String role) {
    this.role = role;
  }
  public void setSize(String size) {
    this.size = size;
  }
  public void setSp(String sp) {
    this.sp = sp;
  }
  public void setSpace(String space) {
    this.space = space;
  }
  public void setTokenName(String tokenName) {
    this.tokenName = tokenName;
  }
  public void setDarkvision(int darkVisionDistance) {
    this.vision_dark = darkVisionDistance;
  }
  public void setLowLightVision(int lowlightDistance) {
    this.vision_lowlight = lowlightDistance;
  }
  public void setXpvalue(String xpvalue) {
    this.xpvalue = xpvalue;
  }
  public Abilities getAbilities(){
    return abilities;
  }
  public String getVision(){
    String text = "Normal";
    if (vision_dark > 0){
      if (vision_lowlight > 0){
        text = "Darkvision " + vision_dark + " and LowLight";
      } else {
        text = "Darkvision " + vision_dark;
      }
    } else if (vision_lowlight > 0){
      text = "LowLight";
    }
    return text;      
  }

  public String getTokenMD5() throws IOException{
    return computeMD5(getTokenImage());
  }
  public String getTokenSize(){
    String size = getSize();
    if (size == null)
      size = "medium";
    switch(getSize()){
      case "fine":  
        return "fwABAc1lFSoBAAAAKgABAQ==";
      case "diminutive": 
        return "fwABAc1lFSoCAAAAKgABAQ==";
      case "tiny": 
        return "fwABAc5lFSoDAAAAKgABAA==";
      case "small": 
        return "fwABAc5lFSoEAAAAKgABAA==";
      default:
      case "medium": 
        return "fwABAc9lFSoFAAAAKgABAQ==";
      case "large": 
        return "fwABAdBlFSoGAAAAKgABAA==";
      case "huge": 
        return "fwABAdBlFSoHAAAAKgABAA==";
      case "gargantuan": 
        return "fwABAdFlFSoIAAAAKgABAQ==";
      case "colossal": 
        return "fwABAeFlFSoJAAAAKgABAQ==";
    }
  }
  private String computeMD5(BufferedImage image) throws IOException {
    if (image == null)
      return "";
    
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    ImageIO.write(image, "PNG", out);
    out.flush();
    byte[] result = digest.digest(out.toByteArray());
    return bytesToHex(result);
  }

  public static String bytesToHex(byte[] in) {
    final StringBuilder builder = new StringBuilder();
    for (byte b : in) {
      builder.append(String.format("%02x", b));
    }
    return builder.toString();
  }

	  public String getPortraitMD5() throws IOException{
    return computeMD5(getImage());
  }
  @Override
  public List<String> getAutoCompleteFields() {
    try {
      List<String> results = AutoCompleteUtils.getProperties(getClass(), null);
      results.remove(AutoCompleteUtils.formatFieldName(null, "character", "image"));
      results.remove(AutoCompleteUtils.formatFieldName(null, "character", "tokenImage"));
      results.remove(AutoCompleteUtils.formatFieldName(null, "character", "tokenImageImage"));
      results.remove(AutoCompleteUtils.formatFieldName(null, "character", "abilities"));
      
      results.addAll(AutoCompleteUtils.getProperties(Abilities.class, "character"));
      return results;
    } catch (IntrospectionException e) {
      e.printStackTrace();
    }
    return null;
  }

  public String toToken(Preferences prefs) throws TemplateException, IOException{
    TemplateParser parser = new TemplateParser();
    parser.setObject("character", this);
    parser.setObject("properties", parseParameters(prefs));
    parser.setObject("macros", "");
    
    String template = parser.parseResource("token.ftl");
    return parser.parseTemplate(template);
  }
  private Object parseParameters(Preferences prefs) throws TemplateException, IOException {
    TemplateParser parser = new TemplateParser();
    List<PropertyDefinition> props = Settings.getProperties();
    String template = IOUtils.toString(getClass().getResourceAsStream("/templates/property.ftl"));
    StringBuilder sb = new StringBuilder();
    for (PropertyDefinition prop : props){
      if (prop.isValid()){
        parser.setObject("propertyDef", prop);
        sb.append(parser.parseTemplate(template)).append("\n");
      }
    }
    return sb.toString();
  }
  public List<Weapon> getRangedWeapons(){
    List<Weapon> results = new LinkedList<>();
    for (Weapon weapon : weapons){
      if (weapon.isRanged())
        results.add(weapon);
    }
    return results;
  }
  public List<Weapon> getMeleWeapons(){
    List<Weapon> results = new LinkedList<>();
    for (Weapon weapon : weapons){
      if (!weapon.isRanged())
        results.add(weapon);
    }
    return results;
  }
  public String getHtmlStatBlock() {
    return htmlStatBlock;
  }
  public void setHtmlStatBlock(String htmlStatBlock) {
    this.htmlStatBlock = htmlStatBlock;
  }
}

