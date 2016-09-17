package com.fidosoft.por2tok;

import java.lang.reflect.Field;

import javax.xml.xpath.*;

import org.junit.*;
import org.junit.rules.ErrorCollector;

public class HeroLabV76bTest {
  private XPath xpath;
  
  @Rule
  public ErrorCollector collector = new ErrorCollector();
  
  @SuppressWarnings("rawtypes")
  @Test
  public void compileAllXPaths(){
    HeroLabV76b sut = createSUT();
    Class type = sut.getClass();
    while (type != null){
      for (Field field : type.getDeclaredFields()) {
        String xpathExpression = "";
        try {
          xpathExpression = (String) field.get(sut);
        } catch (IllegalArgumentException | IllegalAccessException e1) {
          collector.addError(e1);
        }
        try {
          xpath.compile(xpathExpression);
        } catch (XPathExpressionException e) {
          collector.addError(new IllegalArgumentException(String.format("%s: %s", field.getName(), xpathExpression)));
        }
      }
      type = type.getSuperclass();
      if (type == Object.class)
        break;
    }
  }

  private HeroLabV76b createSUT() {
    return new HeroLabV76b();
  }

  @Before
  public void setupXPath() {
    XPathFactory xPathfactory = XPathFactory.newInstance();
    xpath = xPathfactory.newXPath();      
  }
}
