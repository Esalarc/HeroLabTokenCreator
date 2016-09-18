package com.fidosoft.por2tok.ui;

import java.beans.IntrospectionException;
import java.util.*;

import com.fidosoft.por2tok.Character;
import com.fidosoft.por2tok.PropertyDefinition;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class PropertyDefinitionDialog extends Dialog {
  PropertyDefinition def = null;
  public TextField nameField;
  public TextField valueField;
  
  public PropertyDefinitionDialog(PropertyDefinition def) {
    this.def = def;
    initialize();    
  }
  private void initialize() {
    setTitle("Editing Property Definition");
    setResizable(true);
    
    GridPane pane = new GridPane();      
    pane.setVgap(5);
    pane.setHgap(10);
    
    nameField = new TextField(def.getPropertyName());
    pane.add(new Label("Property Name"), 0, 0);
    pane.add(nameField, 1, 0);

    try{
      valueField = new TextField(def.getDefinition());
      AutoCompleteDecorator autocomplete = new AutoCompleteDecorator(valueField);
      autocomplete.initializeEntries(new Character());
    } catch (IntrospectionException ex) {
      throw new RuntimeException (ex);
    }
    pane.add(new Label("Property Value"), 0, 1);
    pane.add(valueField, 1, 1);

    getDialogPane().getButtonTypes().addAll(getButtons());

    getDialogPane().setContent(pane);
    
    Platform.runLater(new Runnable() {
      public void run() {
        nameField.requestFocus();
      }
    });
  }
  private Collection<ButtonType> getButtons() {
    List<ButtonType> results = new LinkedList<>();
      
    results.add(ButtonType.OK);
    results.add(ButtonType.CANCEL);

    return results;
  }
}
