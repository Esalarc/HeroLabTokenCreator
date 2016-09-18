package com.fidosoft.por2tok;

import static org.junit.Assert.*;

import java.io.*;
import java.util.prefs.Preferences;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class TokenTest {
  @Test
  public void testGenerateTokens_happyPath() throws IOException{
    Portfolio por = openFile("/Zanoki7.por");
    Token.generateTokens(new File("."), por);
  }
  private Portfolio openFile(String fileName) throws IOException{
    String path = null;
    try{
      path = copyTempFile(fileName);
      Portfolio por = new Portfolio();
      por.open(path);
      return por;
    } finally {
      if (path != null){
        File file = new File(path);
        file.delete();
      }
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
  
}
