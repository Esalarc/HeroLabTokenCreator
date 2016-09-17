package com.fidosoft.por2tok.ui.controls;

import java.awt.image.BufferedImage;

import com.fidosoft.por2tok.Character;
import com.fidosoft.por2tok.HeroLabTokenCreator;
import com.fidosoft.por2tok.ui.callbacks.UpdateImageCallback;
import com.fidosoft.por2tok.ui.handlers.*;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.HPos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class PortraitImagePane extends TitledPane implements UpdateImageCallback{
  
  private static final int HEIGHT = 400;
  private static final int WIDTH = 200;
  
  private HeroLabTokenCreator application;
  private ImageView imageViewPortrait;
  private BufferedImage portraitImage;
  private Group imageLayer;
  private RubberBandSelection rubberBandSelection;
  
  public PortraitImagePane(Character character, HeroLabTokenCreator application) {
    this.application = application;
    setText("Character Portrait");
    setCollapsible(false);
    
    initializeImage(character);

    imageLayer = new Group(); 
    imageLayer.getChildren().add(imageViewPortrait);
    
    rubberBandSelection = new RubberBandSelection(imageLayer);

    GridPane portraitGrid = new GridPane();
    portraitGrid.add(imageLayer, 1, 1);

    Node buttons = createButtons();
    portraitGrid.add(buttons, 1, 2);
    GridPane.setHalignment(buttons, HPos.CENTER);
    portraitGrid.setVgap(10);
  
    setContent(portraitGrid);
  }

  private Node createButtons() {
    HBox box = new HBox();
    box.setSpacing(10);
    box.getChildren().addAll(createBrowseButton(), createCropButton());
    return box;
  }

  protected Button createBrowseButton() {
    Button browsePortrait = new Button("Browse...");
    browsePortrait.setOnAction(new OnClickPortraitBrowse(application, this));
    
    return browsePortrait;
  }

  protected Button createCropButton() {
    Button browsePortrait = new Button("Crop");
    browsePortrait.setOnAction(new OnSelectThumbnail(this, this));
    
    return browsePortrait;
  }

  protected void initializeImage(Character character) {
    imageViewPortrait = new ImageView();
    portraitImage = character.getImage();
    updateImage(portraitImage);
  }

  public ImageView getImageView() {
    return imageViewPortrait;
  }

  public RubberBandSelection getRubberBandSelection() {
    return rubberBandSelection;
  }

  public BufferedImage getImage() {
    return portraitImage;
  }

  @Override
  public void updateImage(BufferedImage newImage) {
    portraitImage = newImage;
    if (portraitImage != null){
      float rs = (float)WIDTH/HEIGHT;
      float ri = (float)newImage.getWidth()/newImage.getHeight();
      if (rs > ri){
        imageViewPortrait.setFitWidth(newImage.getWidth() * HEIGHT / newImage.getHeight());
        imageViewPortrait.setFitHeight(HEIGHT);
      } else {
        imageViewPortrait.setFitWidth(WIDTH);
        imageViewPortrait.setFitHeight(newImage.getHeight() * WIDTH / newImage.getWidth());
      }
      imageViewPortrait.setImage(SwingFXUtils.toFXImage(portraitImage, null));
    }
  }
}
