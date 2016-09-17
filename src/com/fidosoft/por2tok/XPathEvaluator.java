package com.fidosoft.por2tok;

import java.io.*;

import javax.xml.parsers.*;
import javax.xml.xpath.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class XPathEvaluator {
  private Document doc;
  private XPath xpath;
  
  public XPathEvaluator(){
    XPathFactory xPathfactory = XPathFactory.newInstance();
    this.xpath = xPathfactory.newXPath();
  }
  
  public void parse(InputStream in) throws ParserConfigurationException, SAXException, IOException{
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    this.doc = builder.parse(in);
  }
  
  public boolean isPresent(String xpathExpression, Node parent) throws XPathExpressionException{
    XPathExpression expr = xpath.compile(xpathExpression);
    if (parent == null)
      parent = doc;
    return (expr.evaluate(parent, XPathConstants.NODE) != null);    
  }
  public NodeList getNodes(String xpathExpression, Node parent) throws XPathExpressionException{
    XPathExpression expr = xpath.compile(xpathExpression);
    if (parent == null)
      parent = doc;
    return (NodeList) (expr.evaluate(parent, XPathConstants.NODESET));    
  }
  public Node getNode(String xpathExpression, Node parent) throws XPathExpressionException{
    XPathExpression expr = xpath.compile(xpathExpression);
    if (parent == null)
      parent = doc;
    return (Node) (expr.evaluate(parent, XPathConstants.NODE));    
  }
}
