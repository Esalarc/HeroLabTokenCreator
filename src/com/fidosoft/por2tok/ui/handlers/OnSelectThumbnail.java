package com.fidosoft.por2tok.ui.handlers;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

import com.fidosoft.por2tok.ui.*;
import com.fidosoft.por2tok.ui.callbacks.UpdateImageCallback;
import com.fidosoft.por2tok.ui.controls.*;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

public class OnSelectThumbnail implements EventHandler<ActionEvent> {
  private ImageView sourceImageView;
  private RubberBandSelection rubberBandSelection;
  private UpdateImageCallback callback;
  
  public OnSelectThumbnail(PortraitImagePane pane, UpdateImageCallback callback) {
    this.sourceImageView = pane.getImageView();
    this.rubberBandSelection = pane.getRubberBandSelection();
    this.callback = callback;
  }

  @Override
  public void handle(ActionEvent paramT) {
    // get bounds for image crop
    Bounds selectionBounds = rubberBandSelection.getBounds();

    // crop the image
    crop( selectionBounds);
  }
  private void crop( Bounds bounds) {
    int width = (int) bounds.getWidth();
    int height = (int) bounds.getHeight();

    SnapshotParameters parameters = new SnapshotParameters();
    parameters.setFill(Color.TRANSPARENT);
    parameters.setViewport(new Rectangle2D( bounds.getMinX(), bounds.getMinY(), width, height));

    WritableImage wi = new WritableImage( width, height);
    sourceImageView.snapshot(parameters, wi);

    callback.updateImage(SwingFXUtils.fromFXImage(wi, null));
  }
}
