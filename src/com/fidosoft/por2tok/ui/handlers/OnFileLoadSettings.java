package com.fidosoft.por2tok.ui.handlers;

import java.io.File;

import com.fidosoft.por2tok.HeroLabTokenCreator;
import com.fidosoft.por2tok.Settings;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class OnFileLoadSettings implements EventHandler<ActionEvent>{
  private static final ExtensionFilter ALL_FILES = new FileChooser.ExtensionFilter("All Files", "*.*");
  private static final ExtensionFilter SETTINGS_FILES = new FileChooser.ExtensionFilter("Settings files (*.conf)", "*.conf");
  private HeroLabTokenCreator application;
  
  public OnFileLoadSettings(HeroLabTokenCreator application){
    this.application = application;
  }
  @Override
  public void handle(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Select settings file");
    fileChooser.getExtensionFilters().addAll(ALL_FILES, SETTINGS_FILES);
    fileChooser.setSelectedExtensionFilter(SETTINGS_FILES);
    File startIn = new File(System.getProperty("user.home"));
    fileChooser.setInitialDirectory(startIn);
    File selected = fileChooser.showOpenDialog(application.getStage());
    if (selected != null && selected.exists()){
      Settings.loadSettings(selected);
    }
  }

}
