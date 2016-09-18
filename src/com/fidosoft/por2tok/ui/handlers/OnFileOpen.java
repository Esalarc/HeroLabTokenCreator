package com.fidosoft.por2tok.ui.handlers;

import java.io.File;

import com.fidosoft.por2tok.HeroLabTokenCreator;
import com.fidosoft.por2tok.ui.PortfolioView;

import javafx.event.*;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class OnFileOpen implements EventHandler<ActionEvent>{
  private static final ExtensionFilter ALL_FILES = new FileChooser.ExtensionFilter("All Files", "*.*");
  private static final ExtensionFilter PORTFOLIO_FILES = new FileChooser.ExtensionFilter("Portfolio files (*.por)", "*.por");
  private HeroLabTokenCreator application;
  
  public OnFileOpen(HeroLabTokenCreator application){
    this.application = application;
  }
  @Override
  public void handle(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Portfolio");
    fileChooser.getExtensionFilters().addAll(ALL_FILES, PORTFOLIO_FILES);
    fileChooser.setSelectedExtensionFilter(PORTFOLIO_FILES);
    File startIn = new File(System.getProperty("user.home"));
    fileChooser.setInitialDirectory(startIn);
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
