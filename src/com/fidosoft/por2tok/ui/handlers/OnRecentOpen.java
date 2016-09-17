package com.fidosoft.por2tok.ui.handlers;

import java.io.File;

import com.fidosoft.por2tok.HeroLabTokenCreator;

import javafx.event.*;

public class OnRecentOpen extends OnFileOpen{
  private String path;
  
  public OnRecentOpen(HeroLabTokenCreator application, String path) {
    super(application);
    this.path = path;
  }
  
  @Override
  public void handle(ActionEvent event) {
    openFile(new File(path));
  }
}
