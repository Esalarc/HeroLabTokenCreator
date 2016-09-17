package com.fidosoft.por2tok.ui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.fidosoft.por2tok.Character;
import com.fidosoft.por2tok.HolePunch;
import com.fidosoft.por2tok.ui.callbacks.UpdateImageCallback;
import com.fidosoft.por2tok.ui.controls.PortraitImagePane;
import com.fidosoft.por2tok.ui.handlers.OnSelectThumbnail;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.GridPane;

public class TumbnailImagePane extends TitledPane implements UpdateImageCallback{
  private ImageView imageViewThumbnail;
  private BufferedImage tokenImage;
  
  public TumbnailImagePane(Character character, PortraitImagePane portraitPane){
    setText("Character Token");
    setCollapsible(false);

    imageViewThumbnail = new ImageView();
    tokenImage = character.getTokenImage();
    
    if (tokenImage != null){
      imageViewThumbnail.setImage(SwingFXUtils.toFXImage(tokenImage, null));
    } else if (character.getImage() != null) {
      updateImage(character.getImage());
    }

    imageViewThumbnail.setFitHeight(100);
    imageViewThumbnail.setFitWidth(100);

    GridPane thumbnailGrid = new GridPane();
    thumbnailGrid.add(imageViewThumbnail, 1, 1);
    
    Button selectThumbnail = new Button("Select...");
    selectThumbnail.setOnAction(new OnSelectThumbnail(portraitPane, this));
    thumbnailGrid.add(selectThumbnail, 1, 3);
    GridPane.setHalignment(selectThumbnail, HPos.CENTER);
    thumbnailGrid.setVgap(10);
    setContent(thumbnailGrid);

  }
  
  @Override
  public void updateImage(BufferedImage newImage) {
    tokenImage = new HolePunch(256, 256, 120).resizeAndPunchThumbnail(newImage);
    imageViewThumbnail.setImage(SwingFXUtils.toFXImage(tokenImage, null));
  }

  public BufferedImage getImage() {
    return tokenImage;
  }

}
