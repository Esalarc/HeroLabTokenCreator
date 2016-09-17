package com.fidosoft.por2tok;

import java.util.*;
import java.util.prefs.Preferences;

import org.apache.commons.lang.StringUtils;

public class MacroDefinition implements SerializableBean{
  private MacroType macroType;
  private boolean allowPlayerEdits;
  private boolean applyToTokens;
  private boolean autoExecute;
  private String buttonBackgroundColor;
  private String buttonGoup;
  private String buttonLabel;
  private String buttonTextColor;
  private String definition;
  private String hotKey;
  private boolean includeLabel;
  private String macroName;
  private String toolTip;
  private int width;

  public MacroDefinition(){
    macroName = "";
    definition = "";
    macroType = MacroType.CHARACTER;
    autoExecute = true;
    applyToTokens = true;
    allowPlayerEdits = true;  
    width = 40;
    includeLabel = false;
  }

  public String getButtonBackgroundColor() {
    return buttonBackgroundColor;
  }
  public String getButtonGoup() {
    return buttonGoup;
  }
  public String getButtonLabel() {
    return buttonLabel;
  }
  public String getButtonTextColor() {
    return buttonTextColor;
  }
  public String getDefinition() {
    return definition;
  }
  public String getHotKey() {
    return hotKey;
  }
  public String getMacroName() {
    return macroName;
  }
  public String getToolTip() {
    return toolTip;
  }
  public int getWidth() {
    return width;
  }
  public boolean isAllowPlayerEdits() {
    return allowPlayerEdits;
  }
  public boolean isApplyToTokens() {
    return applyToTokens;
  }
  public boolean isAutoExecute() {
    return autoExecute;
  }
  public boolean isIncludeLabel() {
    return includeLabel;
  }
  @Override
  public boolean isValid() {
    return !StringUtils.isEmpty(macroName);
  }

  public void setAllowPlayerEdits(boolean allowPlayerEdits) {
    this.allowPlayerEdits = allowPlayerEdits;
  }
  public void setApplyToTokens(boolean applyToTokens) {
    this.applyToTokens = applyToTokens;
  }
  public void setAutoExecute(boolean autoExecute) {
    this.autoExecute = autoExecute;
  }
  public void setButtonBackgroundColor(String buttonBackgroundColor) {
    this.buttonBackgroundColor = buttonBackgroundColor;
  }
  public void setButtonGoup(String buttonGoup) {
    this.buttonGoup = buttonGoup;
  }
  public void setButtonLabel(String buttonLabel) {
    this.buttonLabel = buttonLabel;
  }
  public void setButtonTextColor(String buttonTextColor) {
    this.buttonTextColor = buttonTextColor;
  }
  public void setDefinition(String definition) {
    this.definition = definition;
  }
  public void setHotKey(String hotKey) {
    this.hotKey = hotKey;
  }
  public void setIncludeLabel(boolean includeLabel) {
    this.includeLabel = includeLabel;
  }
  public void setMacroName(String name) {
    this.macroName = name;
  }
  public void setToolTip(String toolTip) {
    this.toolTip = toolTip;
  }
  public void setWidth(int width) {
    this.width = width;
  }

  public void store(Preferences preferences, int index) {
    if (isValid()){
      preferences.put("Macros.name." + index, macroName);
      preferences.put("Macros.definition." + index, definition);
    } else {
      preferences.remove("Macros.name." + index);
      preferences.remove("Macros.definition." + index);
    }
  }

  public static List<MacroDefinition> getMacros(Preferences preferences){
    return BeanSerializer.loadObjectsFromPrefs(MacroDefinition.class, preferences);
  }

  public MacroType getMacroType() {
    return macroType;
  }

  public void setMacroType(MacroType macroType) {
    this.macroType = macroType;
  }

  public enum MacroType{
    CHARACTER,
    RANGED_ATTACK,
    MELE_ATTACK
  }
}
