package com.fidosoft.por2tok;

import org.apache.commons.lang.StringUtils;

public class Attack {
  private int modifier;

  public Attack(String text){
    if (StringUtils.isBlank(text)){
      throw new IllegalArgumentException(text + " is not a numeric value");
    }
    setModifier(Integer.valueOf(text));
  }
  
  public int getModifier() {
    return modifier;
  }
  
  public void setModifier(int modifier) {
    this.modifier = modifier;
  }

}
