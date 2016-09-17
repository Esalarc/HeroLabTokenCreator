package com.fidosoft.por2tok;

import static org.junit.Assert.*;

import java.io.*;
import java.security.NoSuchAlgorithmException;

import javax.imageio.ImageIO;

import org.junit.*;

public class CharacterTest {
  private Character sut;
  
  @Before
  public void setUp(){
    sut = createTestObject();
  }

  private Character createTestObject() {
    return new Character();
  }
  @Test
  public void testGetVision_Normal() throws Exception {
    assertEquals("Normal", sut.getVision());
  }
  @Test
  public void testGetVision_Lowlight() throws Exception {
    sut.setLowLightVision(1);
    assertEquals("LowLight", sut.getVision());
  }
  @Test
  public void testGetVision_Dark() throws Exception {
    sut.setDarkvision(30);
    assertEquals("Darkvision 30", sut.getVision());
  }
  @Test
  public void testGetVision_LowlightAndDark() throws Exception {
    sut.setLowLightVision(1);
    sut.setDarkvision(60);
    assertEquals("Darkvision 60 and LowLight", sut.getVision());
  }

  @Test
  public void testGetMD5_HappyPath() throws IOException, NoSuchAlgorithmException{
    sut.setTokenImageImage(ImageIO.read(getClass().getResourceAsStream("/images/DSC_0913.JPG")));
    assertEquals("bc5517de2b88d0b743701ac2fbeff836", sut.getTokenMD5());    
  }
}
