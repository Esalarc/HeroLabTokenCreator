package com.fidosoft.por2tok.ui;

import java.awt.image.BufferedImage;
import java.util.*;

import com.fidosoft.por2tok.Character;
import com.fidosoft.por2tok.HeroLabTokenCreator;
import com.fidosoft.por2tok.ui.controls.PortraitImagePane;

import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class ImageSelectionDialog extends Dialog {
  private HeroLabTokenCreator application;
  private Character character;
  private PortraitImagePane portraitPane;
  private TumbnailImagePane tokenImagePane;
  
  public ImageSelectionDialog(HeroLabTokenCreator application, Character character) {
    this.character = character;
    this.application = application;
    initialize();
  }
  
  private void initialize(){
    setTitle("Select Images for " + character.getName());
    setResizable(true);
    getDialogPane().getButtonTypes().addAll(getButtons());

    portraitPane = new PortraitImagePane(character, application);

    tokenImagePane = new TumbnailImagePane(character, portraitPane);
    
    GridPane dialogContents = new GridPane();
    dialogContents.add(portraitPane, 1, 1);
    dialogContents.add(tokenImagePane, 2, 1);
    GridPane.setValignment(portraitPane, VPos.TOP);
    GridPane.setValignment(tokenImagePane, VPos.TOP);
    dialogContents.setHgap(10);
    dialogContents.setPadding(new Insets(10, 10, 10, 0));
    
    getDialogPane().setContent(dialogContents);
  }

  private Collection<ButtonType> getButtons() {
    List<ButtonType> results = new LinkedList<>();
      
    results.add(ButtonType.OK);
    results.add(ButtonType.CANCEL);

    return results;
  }

  public BufferedImage getPortrait(){
    return portraitPane.getImage();
  }
  public BufferedImage getToken(){
    return tokenImagePane.getImage();
  }
}
