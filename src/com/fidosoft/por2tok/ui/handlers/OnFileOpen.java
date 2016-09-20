package com.fidosoft.por2tok.ui.handlers;

import java.io.File;
import java.util.prefs.Preferences;

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
    Preferences preferences = Preferences.userNodeForPackage(HeroLabTokenCreator.class);
    String initialDir = preferences.get("file.open.initialDir", System.getProperty("user.home"));
    
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Portfolio");
    fileChooser.getExtensionFilters().addAll(ALL_FILES, PORTFOLIO_FILES);
    fileChooser.setSelectedExtensionFilter(PORTFOLIO_FILES);
    fileChooser.setInitialDirectory(new File(initialDir));

    File selected = fileChooser.showOpenDialog(application.getStage());
    if (selected != null){
      preferences.put("file.open.initialDir", selected.getParent());
      openFile(selected);
    }
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
