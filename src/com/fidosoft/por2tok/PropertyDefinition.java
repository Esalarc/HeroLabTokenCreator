package com.fidosoft.por2tok;

import java.util.*;
import java.util.prefs.Preferences;

import org.apache.commons.lang.StringUtils;

public class PropertyDefinition implements SerializableBean{
  private String propertyName;
  private String definition;
  
  public PropertyDefinition(){
    propertyName = "";
    definition = "";
  }

  public String getPropertyName() {
    return propertyName;
  }

  public void setPropertyName(String name) {
    this.propertyName = name;
  }

  public String getDefinition() {
    return definition;
  }

  public void setDefinition(String definition) {
    this.definition = definition;
  }

  @Override
  public boolean isValid() {
    return StringUtils.isNotEmpty(propertyName);
  }

  public void store(Preferences preferences, int index) {
    if (isValid()){
      preferences.put("Properties.name." + index, propertyName);
      preferences.put("Properties.definition." + index, definition);
    } else {
      preferences.remove("Properties.name." + index);
      preferences.remove("Properties.definition." + index);
    }
  }

  public static List<PropertyDefinition> getProperties(Preferences preferences){
    return BeanSerializer.loadObjectsFromPrefs(PropertyDefinition.class, preferences);
  }
  
}
