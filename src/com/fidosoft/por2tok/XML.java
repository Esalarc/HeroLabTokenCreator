package com.fidosoft.por2tok;

import java.security.InvalidParameterException;
import java.util.*;

import javax.xml.parsers.*;
import javax.xml.xpath.*;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.*;

public class XML {
  private XPath xpath;
  private Document doc;
  Node rootNode;
  
  private static Map<String, XPathExpression> xpathCache = new LRUCache<String, XPathExpression>(200);

  public XML(){    
  }
  
  private XML(XML xml, Node root) {
    this.xpath = xml.xpath;
    this.doc = xml.doc;
    this.rootNode = root;
  }
  public String get(String expression) throws XPathExpressionException{
    if (!isValid())
      return "";
    Node node = getNode(expression);
    if (node == null)
      return "";
    return node.getTextContent();        
  }
  public XML find(String expression) throws XPathExpressionException{
    if (!isValid())
      return null;
    return new XML(this, getNode(expression));
  }
  public String text(){
    if (!isValid())
      return "";
    try {
      return get(".");
    } catch (XPathExpressionException e) {
      return "";
    }
  }

  public List<XML> list(String expression) throws XPathExpressionException{
    if (!isValid())
      return null;
    XPathExpression xpathExpression = compileExpression(expression);
    NodeList nodes = (NodeList) xpathExpression.evaluate(rootNode, XPathConstants.NODESET);
    ArrayList<XML> results = new ArrayList<>(nodes.getLength());
    for (int i = 0; i < nodes.getLength(); ++i){
      Node node = nodes.item(i);
      results.add(new XML(this, node));
    }
    return results;
  }
  
  public void load(String xmlContent){
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      doc = builder.parse(IOUtils.toInputStream(xmlContent, "UTF-8"));
      rootNode = doc.getDocumentElement();
      
      XPathFactory xPathfactory = XPathFactory.newInstance();
      xpath = xPathfactory.newXPath();      
    } catch (Exception e) {
      e.printStackTrace();
      throw new InvalidParameterException();
    }
  }
  private Node getNode(String expression) throws XPathExpressionException {
    XPathExpression xpathExpression = compileExpression(expression);
    return (Node) xpathExpression.evaluate(rootNode, XPathConstants.NODE);
  }
  
  private XPathExpression compileExpression(String expression) throws XPathExpressionException {
    synchronized(xpathCache){
      if (xpathCache.containsKey(expression)){
        XPathExpression xpathExpression = xpathCache.get(expression);
        xpathCache.put(expression, xpathExpression);
        return xpathExpression;
      }
      XPathExpression xpathExpression = xpath.compile(expression);
      xpathCache.put(expression, xpathExpression);
      return xpathExpression;
    }
  }
  public boolean isValid() {
    if (rootNode == null || doc == null || xpath == null)
      return false;
    return true;
  }
}
