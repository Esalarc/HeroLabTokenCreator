package com.fidosoft.por2tok;

import static org.junit.Assert.*;

import java.util.List;
import java.security.InvalidParameterException;

import javax.xml.xpath.XPathExpressionException;

import org.junit.Test;

public class XMLTest {
  private static final String XML_TEST = "<?xml version=\"1.0\"?>" +
                                         "<howto>"+
                                         "  <topic name=\"Java\">"+
                                         "    <url>http://www.rgagnonjavahowto.htm</url>"+
                                         "  <car>taxi</car>"+
                                         "  </topic>"+
                                         "  <topic name=\"PowerBuilder\">"+
                                         "    <url>http://www.rgagnon/pbhowto.htm</url>"+
                                         "    <url>http://www.rgagnon/pbhowtonew.htm</url>"+
                                         "  </topic>"+
                                         "  <topic name=\"Javascript\">"+
                                         "    <url>http://www.rgagnon/jshowto.htm</url>"+
                                         "  </topic>"+
                                         "  <topic name=\"VBScript\">"+
                                         "    <url>http://www.rgagnon/vbshowto.htm</url>"+
                                         "  </topic>"+
                                         "</howto>";     

  @Test
  public void testGet_GoodAttribute() throws XPathExpressionException {
    XML sut = createAndLoadSUT(XML_TEST);
    XML child = sut.find("topic");
    String result = child.get("@name");
    assertEquals("Java", result);
  }
  @Test
  public void testGet_Good() throws XPathExpressionException {
    XML sut = createAndLoadSUT(XML_TEST);
    XML child = sut.find("topic");
    String result = child.get("url");
    assertEquals("http://www.rgagnonjavahowto.htm", result);
  }
  @Test(expected=XPathExpressionException.class)
  public void testGet_BadXPath() throws XPathExpressionException {
    XML sut = createAndLoadSUT(XML_TEST);
    XML child = sut.find("topic");
    String result = child.get("!@!url");
  }
  @Test
  public void testGet_NotFound() throws XPathExpressionException {
    XML sut = createAndLoadSUT(XML_TEST);
    XML child = sut.find("topic");
    String result = child.get("urlz");
    assertEquals("", result);
  }
  @Test
  public void testGet_nested() throws XPathExpressionException {
    XML sut = createAndLoadSUT(XML_TEST);
    String result = sut.get("url");
    assertEquals("", result);
  }

  @Test
  public void testFind_Good() throws XPathExpressionException {    
    XML sut = createAndLoadSUT(XML_TEST);
    XML result = sut.find("topic");
    assertEquals("topic", result.rootNode.getNodeName());    
  }
  @Test(expected=XPathExpressionException.class)
  public void testFind_BadXPath() throws XPathExpressionException {    
    XML sut = createAndLoadSUT(XML_TEST);
    XML result = sut.find("topic/\\/");
    assertEquals("topic", result.rootNode.getNodeName());    
  }
  @Test
  public void testFind_NotFound() throws XPathExpressionException {    
    XML sut = createAndLoadSUT(XML_TEST);
    XML result = sut.find("topix");
    assertNull(result.rootNode);    
  }

  @Test
  public void testText_Good() throws XPathExpressionException {
    XML sut = createAndLoadSUT(XML_TEST);
    XML child = sut.find("topic/url");
    String result = child.text();
    assertEquals("http://www.rgagnonjavahowto.htm", result);
  }
  @Test
  public void testText_GoodAttribute() throws XPathExpressionException {
    XML sut = createAndLoadSUT(XML_TEST);
    XML child = sut.find("topic/@name");
    String result = child.text();
    assertEquals("Java", result);
  }
  @Test
  public void testText_NotFound() throws XPathExpressionException {
    XML sut = createAndLoadSUT(XML_TEST);
    XML child = sut.find("topic/URL");
    String result = child.text();
    assertEquals("", result);
  }

  @Test
  public void testList_Good() throws XPathExpressionException {
    XML sut = createAndLoadSUT(XML_TEST);
    List<XML> result = sut.list("topic");
    assertEquals(4, result.size());
  }
  @Test
  public void testList_NotFound() throws XPathExpressionException {
    XML sut = createAndLoadSUT(XML_TEST);
    List<XML> result = sut.list("topix");
    assertEquals(0, result.size());
  }
  @Test(expected=XPathExpressionException.class)
  public void testList_BadXPath() throws XPathExpressionException {
    XML sut = createAndLoadSUT(XML_TEST);
    List<XML> result = sut.list("/\\/topic");
    assertEquals(0, result.size());
  }

  @Test
  public void testLoad_Good() {
    XML sut = createSUT();
    sut.load(XML_TEST);
    assertEquals("howto", sut.rootNode.getNodeName());
  }
  @Test(expected=InvalidParameterException.class)
  public void testLoad_Invalid(){
    XML sut = createSUT();
    sut.load(XML_TEST.replaceAll("/", ""));
  }

  private XML createSUT() {
    return new XML();
  }
  private XML createAndLoadSUT(String xmlContent){
    XML result = createSUT();
    result.load(xmlContent);
    return result;
  }

}
