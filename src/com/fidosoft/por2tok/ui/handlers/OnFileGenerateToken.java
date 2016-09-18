package com.fidosoft.por2tok.ui.handlers;

import java.io.File;

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
    File startIn = new File(System.getProperty("user.home"));
    chooser.setInitialDirectory(startIn);
    File selected = chooser.showDialog(application.getStage());
    if (selected != null && selected.exists())
      Token.generateTokens(selected, application.getPortfolio());
  }

}
  