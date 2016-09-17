package com.fidosoft.por2tok;

import static org.junit.Assert.*;

import org.junit.*;

import freemarker.template.TemplateException;

public class TemplateParserTest {
  private static final String TEMPLATE_NO_SYMBOLS = "Hello, world!";
  private static final String TEMPLATE_ONE_SYMBOL = "${greeting}, world!";
  private static final String TEMPLATE_TWO_SYMBOLS = "${greeting}, ${Recipient}!";
  private static final String TEMPLATE_OBJECT = "Hello, ${character.name}!";
  private static final String TEMPLATE_OBJECT2 = "Hello, ${character.abilities.str}!";
  
  private TemplateParser sut;

  @Before
  public void setUp() {
    createTestObject();
  }

  @Test
  public void testSetObject_ShouldSetObject() {
    sut.setObject("Test1", "Test");
    assertNotNull(sut.getObject("Test1"));
  }

  @Test
  public void testSetObject_ShouldRemoveObjectWhenValueIsNull() {
    assertEquals(0, sut.objectMap.size());
    sut.setObject("Test1", "Test");
    assertEquals(1, sut.objectMap.size());
    sut.setObject("Test1", null);
    assertEquals(0, sut.objectMap.size());
    assertNull(sut.getObject("Test1"));
  }

  @Test
  public void testParseTemplate_ShouldReturnOriginalWithNoSubstitutionSymbols() throws TemplateException {
    assertEquals(TEMPLATE_NO_SYMBOLS, sut.parseTemplate(TEMPLATE_NO_SYMBOLS));
  }

  @Test(expected=TemplateException.class)
  public void testParseTemplate_ShouldThrowTemplateExceptionWithMissingSubstitutionSymbols() throws TemplateException {
    assertEquals(TEMPLATE_ONE_SYMBOL, sut.parseTemplate(TEMPLATE_ONE_SYMBOL));
  }
  @Test
  public void testParseTemplate_ShouldCalMethodsOnObject() throws TemplateException {
    Character c = new Character();
    c.setRole("lower");
    sut.setObject("character", c);
    assertEquals("LOWER", sut.parseTemplate("${character.roleUpper}"));    
  }
  @Test
  public void testParseTemplate_ShouldPerformSubstitutions() throws TemplateException {
    sut.setObject("greeting", "Hello");
    assertEquals(TEMPLATE_NO_SYMBOLS, sut.parseTemplate(TEMPLATE_ONE_SYMBOL));
  }
  @Test
  public void testParseTemplate_ShouldPerformSubstitutionsWithObjects() throws TemplateException {
    Character c = new Character();
    c.setName("world");
    sut.setObject("character", c);
    assertEquals(TEMPLATE_NO_SYMBOLS, sut.parseTemplate(TEMPLATE_OBJECT));
  }

  @Test
  public void testParseTemplate_ShouldPerformSubstitutionsWithObjectsThatReturnObjects() throws TemplateException {
    Character c = new Character();
    c.addAbilityScore("str","world");
    sut.setObject("character", c);
    
    assertEquals(TEMPLATE_NO_SYMBOLS, sut.parseTemplate(TEMPLATE_OBJECT2));
  }

  @Test
  public void parseResource_ShouldPerformSubstutution() throws TemplateException{
    Character c = new Character();
    c.setName("world");
    sut.setObject("character", c);
    assertEquals(TEMPLATE_NO_SYMBOLS, sut.parseResource("Test_HelloCharacter.ftl"));
  }
  private void createTestObject() {
    sut = new TemplateParser();
  }
}
