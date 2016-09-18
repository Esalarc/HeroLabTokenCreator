package com.fidosoft.por2tok;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.*;
import java.util.prefs.Preferences;

import org.apache.commons.io.IOUtils;
import org.junit.*;

import freemarker.template.TemplateException;

public class PortfolioTest {

  private Portfolio sut;
  
  @Before
  public void setUp(){
    sut = createTestObject();
  }

  @Test(expected=FileNotFoundException.class)
  public void testOpen_ThrowsFileNotFound() throws Exception {    
    sut.open("c:\\some\\file\\that\\doesn't\\exist.por");    
  }
  @Test(expected=IllegalArgumentException.class)
  public void testOpen_ThrowsInvalidArgument() throws Exception {    
    sut.open((String)null);        
  }

  @Test(expected=IllegalArgumentException.class)
  public void testOpen_IllegalArgumentExceptionForInvalidPor() throws Exception {
    openFile("/NotAPortfolio.por");
  }
  @Test(expected=IllegalArgumentException.class)
  public void testOpen_IllegalArgumentExceptionForPorWithNoIndex() throws Exception {
    openFile("/Zanoki7 - No Index.por");
  }
  @Test(expected=IllegalArgumentException.class)
  public void testOpen_IllegalArgumentExceptionForPorWithWrongProgram() throws Exception {
    openFile("/Zanoki7 - BadProgram.por");
  }
  @Ignore
  @Test(expected=IllegalArgumentException.class)
  public void testOpen_IllegalArgumentExceptionForPorWithWrongVersion() throws Exception {
    openFile("/Zanoki7 - BadVersion.por");
  }

  @Test
  public void testOpen_HappyPath() throws Exception {
    openFile("/Zanoki7.por");
    assertEquals(1, sut.getNumCharacters());
    assertNotNull(sut.getCharacter(0).getImage());
    assertEquals("52d88fcdf5e0075f89fb67b2195d72f7", sut.getCharacter(0).getPortraitMD5());
    Preferences prefs = mock(Preferences.class);
    Settings.loadSettings(null);
    Settings.getProperties().add(createPropertyDefinition("Static Text", "text"));
    Settings.getProperties().add(createPropertyDefinition("blank", ""));
    Settings.getProperties().add(createPropertyDefinition("Substitution-Name", "${character.name}"));
    
    assertNotNull(sut.getCharacter(0).toToken());
  }
  private PropertyDefinition createPropertyDefinition(String name, String value) {
    PropertyDefinition result = new PropertyDefinition();
    result.setPropertyName(name);
    result.setDefinition(value);
    return result;
  }

  @Test
  public void testOpen_HappyPathNoStatblocks() throws Exception {
    openFile("/Zanoki7 - No Statblock.por");
    assertEquals(0, sut.getNumCharacters());
  }
  @Test
  public void testOpen_HappyPathNoImages() throws Exception {
    openFile("/Zanoki7 - No Image.por");
    assertEquals(1, sut.getNumCharacters());
    assertNull(sut.getCharacter(0).getImage());
  }

  @Test(expected=IndexOutOfBoundsException.class)
  public void testGetCharacter_throwsIndexOutOfBounds() throws Exception {
    sut.getCharacter(1);
  }

  @Test
  public void testWeaponTemplateMelee() throws IOException, TemplateException{
    openFile("/Zanoki7.por");
    assertEquals(1, sut.getNumCharacters());
    assertNotNull(sut.getCharacter(0).getImage());
    Character c = sut.getCharacter(0);
    TemplateParser parser = new TemplateParser();
    for (Weapon weapon : c.getMeleWeapons()){
      parser.setObject("weapon", weapon);
      System.out.println(parser.parseResource("weapon_test.ftl"));
    }
  }
  private String copyTempFile(String resource) throws IOException {
    File temp = File.createTempFile("TestPortfolio", ".por");
    InputStream in = this.getClass().getResourceAsStream("/por_files" + resource);
    OutputStream out = new FileOutputStream(temp);
    IOUtils.copy(in, out);
    out.close();
    in.close();
    return temp.getAbsolutePath();
  }
  private Portfolio createTestObject(){
    Portfolio sut = new Portfolio();
    return sut;
  }
  private void openFile(String fileName) throws IOException{
    String path = null;
    try{
      path = copyTempFile(fileName);
      sut.open(path);
    } finally {
      if (path != null){
        File file = new File(path);
        file.delete();
      }
    }
  }

}
