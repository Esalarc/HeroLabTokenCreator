package com.fidosoft.por2tok;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;


public class HolePunch {
  private int imageWidth;
  private int imageHeight;
  private int diameter;
  private int horizontalOffset;
  private int verticalOffset;
  
  public HolePunch(int imageWidth, int imageHeight, int holeRadius){
    this.diameter = holeRadius * 2;
    if ((diameter > imageWidth) || (diameter > imageHeight)){
      throw new IllegalArgumentException("Radius must be < 1/2 height and width");
    }
    this.imageHeight = imageHeight;
    this.imageWidth = imageWidth;
    horizontalOffset = (imageWidth - diameter)/2;
    verticalOffset = (imageHeight - diameter)/2;    
  }
  public BufferedImage punchHole(BufferedImage original){
    BufferedImage result = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
    Graphics graphics = result.getGraphics();

    Color backgroundColor = new Color(1f, 1f, 1f, 1f);
    graphics.setColor(backgroundColor);
    graphics.fillRect(0, 0, imageWidth, imageHeight);
    
    Ellipse2D clipCircle = new Ellipse2D.Float();
    clipCircle.setFrame(horizontalOffset, verticalOffset, diameter, diameter);
    
    graphics.setClip((Shape) clipCircle);
    
    int imageOffsetX = ((imageWidth - original.getWidth())/2);
    int imageOffsetY = ((imageHeight - original.getHeight())/2);
    graphics.drawImage(original, imageOffsetX, imageOffsetY, original.getWidth(), original.getHeight(), null);
    graphics.dispose();
    return result;
  }
  
  public BufferedImage resizeAndPunchThumbnail(Image raw){
    BufferedImage image = SwingFXUtils.fromFXImage(raw, null);
    return resizeAndPunchThumbnail(image);
  }
  
  public BufferedImage resizeAndPunchThumbnail(BufferedImage image){
    BufferedImage resizedImage = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = resizedImage.createGraphics();

    g.drawImage(image, 0, 0, imageWidth, imageHeight, null);
    g.dispose();
    return punchHole(resizedImage);
  }

}
