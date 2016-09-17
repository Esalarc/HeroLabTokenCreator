package com.fidosoft.por2tok.ui.handlers;

import java.io.File;

import com.fidosoft.por2tok.HeroLabTokenCreator;
import com.fidosoft.por2tok.ui.PortfolioView;

import javafx.event.*;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;

public class OnFileOpen implements EventHandler<ActionEvent>{
  private HeroLabTokenCreator application;
  
  public OnFileOpen(HeroLabTokenCreator application){
    this.application = application;
  }
  @Override
  public void handle(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Portfolio");
    File selected = fileChooser.showOpenDialog(application.getStage());
    openFile(selected);
  }
  protected void openFile(File selected) {
    if (selected != null && selected.exists()){
      if (application.openPortfolio(selected)){
        createPortfolioView();
      }
    }
  }

  protected void createPortfolioView() {
    TableView tableView = new PortfolioView(application);
    application.setActiveView(tableView);
  }

}
