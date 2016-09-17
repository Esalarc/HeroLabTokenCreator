package com.fidosoft.por2tok;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.prefs.Preferences;
import java.util.zip.*;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;

import freemarker.template.TemplateException;

public class Token {

  private Character character;

  public Token(Character character){
    this.character = character;
  }
  
  public static void generateTokens(Preferences prefs, Portfolio portfolio){
    int numCharacters = portfolio.getNumCharacters();
    for (int i = 0; i < numCharacters; ++i){
      Character c = portfolio.getCharacter(i);
      if (c.isGenerateToken()){
        Token token = new Token(c);
        token.generateToken(prefs);
      }
    }
  }
  public void generateToken(Preferences prefs){
    try{
      String outputDirectory = createOutputDirectory(prefs);
      FileOutputStream out = new FileOutputStream(createFileNameForCharacter(outputDirectory));
      ZipOutputStream zip = new ZipOutputStream(out);
      
      addFileToZip(zip, "properties.xml", tokenProperties());
      addFileToZip(zip, "content.xml", character.toToken(prefs));
      addPortraitToZip(zip);
      if (StringUtils.isNotEmpty(character.getTokenMD5())){
        addTokenImageToZip(zip);
        addThumbnailToZip(zip);
      }
      zip.close();
    } catch (Exception ex){
      throw new RuntimeException("Unable to gerenrate token for " + character.getName(), ex);
    }
  }

  private void addThumbnailToZip(ZipOutputStream zip) throws IOException {
    BufferedImage sourceImage = character.getTokenImage();
    
    Image thumbnail = sourceImage.getScaledInstance(128, -1, Image.SCALE_SMOOTH);
    BufferedImage bufferedThumbnail = new BufferedImage(thumbnail.getWidth(null),
                                                        thumbnail.getHeight(null),
                                                        BufferedImage.TYPE_INT_ARGB);
    bufferedThumbnail.getGraphics().drawImage(thumbnail, 0, 0, null);
    
    ZipEntry entry = new ZipEntry("thumbnail");
    zip.putNextEntry(entry);
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    ImageIO.write(bufferedThumbnail, "png", out);
    out.close();
    zip.write(out.toByteArray());
    zip.closeEntry();
  }

  private void addTokenImageToZip(ZipOutputStream zip) throws IOException, TemplateException {
    addImageToZip(zip, character.getTokenMD5(), character.getTokenImage(), "_pog");
  }

  private void addPortraitToZip(ZipOutputStream zip) throws IOException, TemplateException {
    addImageToZip(zip, character.getPortraitMD5(), character.getImage(), "_portrait");
  }
  
  private void addImageToZip(ZipOutputStream zip, String imageMD5, BufferedImage image, String suffix) throws IOException, TemplateException{
    ZipEntry entry = new ZipEntry("assets/" + imageMD5);
    zip.putNextEntry(entry);
    TemplateParser parser = new TemplateParser();
    parser.setObject("assetID", imageMD5);
    parser.setObject("assetName", imageMD5 + suffix);
    zip.write(parser.parseResource("image_asset.ftl").getBytes());
    zip.closeEntry();
    
    entry = new ZipEntry("assets/" + imageMD5 + ".png");
    zip.putNextEntry(entry);
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    ImageIO.write(image, "png", out);
    out.close();
    zip.write(out.toByteArray());
    zip.closeEntry();
    
  }

  private void addFileToZip(ZipOutputStream out, String fileName, String tokenProperties) throws IOException {
    ZipEntry e = new ZipEntry(fileName);
    out.putNextEntry(e);
    out.write(tokenProperties.getBytes());
    out.closeEntry();
  }

  private String tokenProperties() throws TemplateException {
    String version = "1.3.b15";
    TemplateParser parser = new TemplateParser();
    parser.setObject("version", version);
    return parser.parseResource("properties.xml.ftl");
  }

  private String createOutputDirectory(Preferences prefs) {
    String outputDirectory = prefs.get("settings.tokenFolder", ".");
    outputDirectory = outputDirectory.replaceAll("[\\/]", File.separator);
    File outDir = new File(outputDirectory);
    if (!outDir.exists()){
      outDir.mkdirs();
    }
    return outputDirectory;
  }

  private String createFileNameForCharacter(String outputDirectory) {
    StringBuilder fileName = new StringBuilder(outputDirectory);
    if (!outputDirectory.endsWith(File.separator))
      fileName.append(File.separator);
    fileName.append(character.getName().replaceAll("[^-_.A-Za-z0-9]+", ""));
    fileName.append(".rptok");        
    return fileName.toString();
  }
}
