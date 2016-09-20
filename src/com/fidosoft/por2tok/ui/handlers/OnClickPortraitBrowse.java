package com.fidosoft.por2tok.ui.handlers;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.prefs.Preferences;

import javax.imageio.ImageIO;

import com.fidosoft.por2tok.HeroLabTokenCreator;
import com.fidosoft.por2tok.ui.callbacks.UpdateImageCallback;

import javafx.event.*;
import javafx.stage.FileChooser;

public class OnClickPortraitBrowse implements EventHandler<ActionEvent>{
  private static final String SELECT_IMAGE_FOLDER = "Select.Image.folder";
  private HeroLabTokenCreator application;
  private UpdateImageCallback callback;
  
  public OnClickPortraitBrowse(HeroLabTokenCreator application, UpdateImageCallback callback){
    this.application = application;
    this.callback = callback;
  }
  @Override
  public void handle(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    Preferences preferences = Preferences.userNodeForPackage(HeroLabTokenCreator.class);
    String initialDir = preferences.get(SELECT_IMAGE_FOLDER, System.getProperty("user.home"));
    
    fileChooser.setTitle("Select Portrait Image");
    fileChooser.setInitialDirectory(new File(initialDir));
    
    File selected = fileChooser.showOpenDialog(application.getStage());
    if (selected != null){
      preferences.put(SELECT_IMAGE_FOLDER, selected.getParent());
      openFile(selected);
    }
  }
  protected void openFile(File selected) {
    if (selected != null && selected.exists()){
      BufferedImage image;
      try {
        image = ImageIO.read(selected);
        if (callback != null)
          callback.updateImage(image);
      } catch (IOException e) {        
      }
    }
  }
}
