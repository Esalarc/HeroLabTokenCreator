package com.fidosoft.por2tok;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.util.zip.ZipException;

import javax.imageio.ImageIO;
import javax.xml.parsers.*;
import javax.xml.xpath.*;

import org.apache.commons.compress.archivers.zip.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javafx.collections.*;

public class Portfolio {
  private static final String XP_PROGRAM_NAME_CHECK = "/document/program[@name='Hero Lab']"; 
  private static final String XP_VERSION_CHECK = "/document/program/version[@version='7.6b']";
  private static final String XP_CHARACTERS = "/document/characters/character";
  private static final String XP_XML_STATBLOCK = "./statblocks/statblock[@format='xml']";
  private static final String XP_HTML_STATBLOCK = "./statblocks/statblock[@format='html']";
  private static final String XP_IMAGE = "./images/image";

  private ArrayList<Character> characters = new ArrayList<>();
  private XPathEvaluator xpath;
  private ZipFile zip = null;
  
  public void open(String path) throws IOException{
    open(openFile(path));
  }
  public void open(File file) throws IOException{
    try{
      zip = new ZipFile(file);       
      initializeXPathEvaluatorForIndexFile();

      validateFileVersion(file.getAbsolutePath());
      
      extractCharacters();
      determineInitialInclusion();
      
    } catch (IOException | XPathExpressionException | SAXException | ParserConfigurationException ex){
      throw new IllegalArgumentException(file.getAbsolutePath() + " is not a valid .por file", ex);
    } finally{
      if (zip != null)
        zip.close();
    }
  }

  private void determineInitialInclusion() {
    Pattern patDuplicates = Pattern.compile("^(.*?)(#[ 0123456789]+)$");
    Set<String> names = new HashSet<>();
    for (Character character : characters){
      String characterName = character.getName();
      Matcher matcher = patDuplicates.matcher(characterName);
      if (matcher.matches()){
        characterName = matcher.group(1);
        if (names.contains(characterName)){
          character.setGenerateToken(false);
        } else {
          character.setGenerateToken(true);
          names.add(characterName);
          character.setTokenName(characterName + ".token");
        }
      }
    }
  }
  
  private void initializeXPathEvaluatorForIndexFile() throws ParserConfigurationException, SAXException, IOException, ZipException {
    xpath = new XPathEvaluator();
    xpath.parse(getIndexInputStream());
  }

  private void extractCharacters() throws XPathExpressionException, ZipException, ParserConfigurationException, SAXException, IOException {
    NodeList charactersInFile = xpath.getNodes(XP_CHARACTERS, null);      
    for (int i = 0; i < charactersInFile.getLength(); ++i){
      Node characterFromFile = charactersInFile.item(i);
      extractCharacter(characterFromFile);          
    }
  }

  private void extractCharacter(Node characterFromFile) throws XPathExpressionException, ZipException, IOException {
    Node characterNode = xpath.getNode(XP_XML_STATBLOCK, characterFromFile);
    if (characterNode != null){
      NamedNodeMap characterNodeAttributes = characterNode.getAttributes();
      
      String characterXML = readCharacterContents(characterNodeAttributes.getNamedItem("folder"),characterNodeAttributes.getNamedItem("filename"));
      
      Character character = new CharacterFactory().createCharacter(characterXML);
      extractImage(characterFromFile, character);        
    
      characterNode = xpath.getNode(XP_HTML_STATBLOCK, characterFromFile);
      if (characterNode != null){
        characterNodeAttributes = characterNode.getAttributes();
        
        String characterHTML = readCharacterContents(characterNodeAttributes.getNamedItem("folder"),characterNodeAttributes.getNamedItem("filename"));
        
        if (StringUtils.isNotEmpty(characterHTML)){
          character.setHtmlStatBlock(characterHTML);
        }
      }
      characters.add(character);
    }
  }

  private String readCharacterContents(Node pathNode, Node fileNameNode) throws ZipException, IOException {
    String path = pathNode.getTextContent();
    String fileName = fileNameNode.getTextContent();

    ZipArchiveEntry statBlockEntry = zip.getEntry(path + "/" + fileName);
    InputStream in = zip.getInputStream(statBlockEntry);
    return IOUtils.toString(in, "UTF-8");
  }

  private void extractImage(Node characterFromFile, Character character) throws XPathExpressionException, IOException {
    if (xpath.isPresent(XP_IMAGE, characterFromFile)){
      Node imageNode = xpath.getNode(XP_IMAGE, characterFromFile);
      NamedNodeMap imageNodeAttributes = imageNode.getAttributes();

      String imagePath = String.format("%s/%s", imageNodeAttributes.getNamedItem("folder").getTextContent(), imageNodeAttributes.getNamedItem("filename").getTextContent());
      ZipArchiveEntry imageFile = zip.getEntry(imagePath);
      InputStream in = zip.getInputStream(imageFile);
      BufferedImage image = ImageIO.read(in);
      character.setImage(image);
    }
  }

  private void validateFileVersion(String path) throws XPathExpressionException {
    if (!xpath.isPresent(XP_PROGRAM_NAME_CHECK, null)){
      throw new IllegalArgumentException(path + " is not a valid .por file - bad program name");
    }
//    if (!xpath.isPresent(XP_VERSION_CHECK, null)){
//      throw new IllegalArgumentException(path + " is not a valid .por file - bad program version");
//    }
  }

  private InputStream getIndexInputStream() throws ZipException, IOException {
    ZipArchiveEntry indexEntry = zip.getEntry("index.xml");
    InputStream in = zip.getInputStream(indexEntry);
    if (in == null ){
      throw new IOException();
    }
    return in;
  }

  private File openFile(String path) throws FileNotFoundException {
    if (StringUtils.isEmpty(path))
      throw new IllegalArgumentException("path must be specified"); 
    File file = new File(path);
    if (!file.exists())
      throw new FileNotFoundException(path);
    return file;
  }

  public int getNumCharacters() {
    return characters.size();
  }
  public Character getCharacter(int index) {
    return characters.get(index);
  }
  public ObservableList getObservableCharacterList(){
    return FXCollections.observableArrayList(characters);
  }
  
}
