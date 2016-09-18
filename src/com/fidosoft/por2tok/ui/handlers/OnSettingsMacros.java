package com.fidosoft.por2tok.ui.handlers;

import java.util.*;

import com.fidosoft.por2tok.*;
import com.fidosoft.por2tok.ui.MacroSettings;

import javafx.event.*;
import javafx.scene.control.ButtonType;

public class OnSettingsMacros implements EventHandler<ActionEvent> {
  private HeroLabTokenCreator application;
  
  public OnSettingsMacros(HeroLabTokenCreator application) {
    this.application = application;
  } 
  @Override
  public void handle(ActionEvent arg0) {
    MacroSettings dialog = new MacroSettings(application.getPreferences());
    Optional<ButtonType> result = dialog.showAndWait();
    if (result.get() == ButtonType.OK){
      Settings.setMacros(dialog.getMacros());
    }
  }
}
