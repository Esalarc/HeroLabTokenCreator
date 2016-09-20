package com.fidosoft.por2tok.ui.handlers;

import java.io.File;
import java.util.prefs.Preferences;

import com.fidosoft.por2tok.HeroLabTokenCreator;
import com.fidosoft.por2tok.Token;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.DirectoryChooser;

public class OnFileGenerateToken implements EventHandler<ActionEvent>{
  HeroLabTokenCreator application;
  
  public OnFileGenerateToken(HeroLabTokenCreator application) {
   this.application = application;
  }
  
  @Override
  public void handle(ActionEvent event) {
    DirectoryChooser chooser = new DirectoryChooser();
    Preferences preferences = Preferences.userNodeForPackage(HeroLabTokenCreator.class);
    String initialDir = preferences.get("file.generateToken.initialDir", System.getProperty("user.home"));

    chooser.setInitialDirectory(new File(initialDir));
    File selected = chooser.showDialog(application.getStage());
    if (selected != null && selected.exists()){
      preferences.put("file.generateToken.initialDir", selected.getAbsolutePath());
      Token.generateTokens(selected, application.getPortfolio());
    }
  }

}
  