package com.fidosoft.por2tok;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.thoughtworks.xstream.XStream;

public class Settings {
  private List<PropertyDefinition> properties = new LinkedList<>();
  private List<MacroDefinition> macros = new LinkedList<>();

  private static Settings theSettings = new Settings();
  
  private Settings(){
    properties.add(new PropertyDefinition());
    macros.add(new MacroDefinition());
  }
  
  public static void loadSettings(File inFile){
    if (inFile.exists()){
      XStream xstream = new XStream();
      Settings.theSettings = (Settings) xstream.fromXML(inFile);
    } else {
      Settings.theSettings = new Settings();
    }
  }
  
  public static void saveSettings(File outFile) throws IOException{
    XStream xstream = new XStream();
    FileOutputStream out = new FileOutputStream(outFile);
    xstream.toXML(Settings.theSettings, out);
    out.close();
  }
  public static List<PropertyDefinition> getProperties() {
    return theSettings.properties;
  }
  public static void setProperties(List<PropertyDefinition> properties) {
    theSettings.properties = properties;
  }
  public static List<MacroDefinition> getMacros() {
    return theSettings.macros;
  }
  public static void setMacros(List<MacroDefinition> macros) {
    theSettings.macros = macros;
  }
  
}
