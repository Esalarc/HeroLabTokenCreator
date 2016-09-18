package com.fidosoft.por2tok.ui.handlers;

import java.util.*;

import com.fidosoft.por2tok.*;
import com.fidosoft.por2tok.ui.PropertySettings;

import javafx.event.*;
import javafx.scene.control.ButtonType;

public class OnSettingsProperties implements EventHandler<ActionEvent> {
  private HeroLabTokenCreator application;
  
  public OnSettingsProperties(HeroLabTokenCreator application) {
    this.application = application;
  }
  @Override
  public void handle(ActionEvent arg0) {
    PropertySettings dialog = new PropertySettings(application.getPreferences());
    Optional<ButtonType> result = dialog.showAndWait();
    if (result.get() == ButtonType.OK){
      Settings.setProperties(dialog.getProperties());
    }
  }
}
