package com.fidosoft.por2tok;

import java.io.*;
import java.util.prefs.Preferences;

import com.fidosoft.por2tok.ui.TokenCreatorMenuBar;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class HeroLabTokenCreator extends Application {
  private Portfolio portfolio = null;
  private Stage stage;
  private BorderPane layout;
  private Preferences preferences;
  private TokenCreatorMenuBar menu;
  
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    preferences = Preferences.userNodeForPackage(getClass());
    
    setStage(stage);
    getStage().setTitle("Hero Lab Token Creator");
    layout = new BorderPane();

    Scene scene = new Scene(layout, 800, 400);
    getStage().setScene(scene);
    
    MenuBar menuBar = createMenuBar();
    layout.setTop(menuBar);
    getStage().show();
  }

  public Stage getStage() {
    return stage;
  }

  public void setStage(Stage stage) {
    this.stage = stage;
  }

  private MenuBar createMenuBar() {
    menu = new TokenCreatorMenuBar(this); 
    return menu;
  }
  
  public boolean openPortfolio(File selected) {
    try{
      getStage().getScene().setCursor(Cursor.WAIT);
      portfolio = new Portfolio();
      try {
        portfolio.open(selected);
        menu.addRecent(selected.getAbsolutePath());
        return (portfolio.getNumCharacters() > 0);
      } catch (IOException e) {
        portfolio = null;
        return false;          
      }
    } finally {
      getStage().getScene().setCursor(Cursor.DEFAULT);
    }
  }

  public Portfolio getPortfolio() {
    return portfolio;
  }

  public BorderPane getLayout() {
    return layout;
  }

  public Preferences getPreferences() {
   return preferences;
  }

  public void setActiveView(TableView tableView) {
    StackPane pane = new StackPane();
    pane.getChildren().add(tableView);
    getLayout().setCenter(pane);  
  }
}
