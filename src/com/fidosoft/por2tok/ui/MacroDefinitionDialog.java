package com.fidosoft.por2tok.ui;

import java.beans.IntrospectionException;
import java.util.*;

import com.fidosoft.por2tok.Character;
import com.fidosoft.por2tok.MacroDefinition;
import com.fidosoft.por2tok.MacroDefinition.MacroType;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.GridPane;

public class MacroDefinitionDialog extends Dialog {
  private GridPane contentPane;

  private ComboBox<MacroType> macroTypeField;
  private TextField nameField;
  private TextArea valueField;
  private CheckBox allowPlayerEditsField;
  private CheckBox applyToTokensField;
  private CheckBox autoExecuteField;
  private TextField buttonGroupField;
  private TextField buttonLabelField;
  private TextField buttonWidthField;
  private TextField hotKeyField;
  private CheckBox includeLabelField;
  private TextField toolTipField;

  private TextField buttonBackgroundColor;

  private TextField buttonForegroundColor;
  
  public MacroDefinitionDialog(MacroDefinition def) {
    initialize(def.getMacroName());    
    doDataExchange(def, false);
  }
  
  private void initialize(String macroName) {
    setTitle("Editing Macro " + macroName);
    setResizable(true);
    
    getDialogPane().getButtonTypes().addAll(getButtons());
    
    initializeContentPane();

    int row = 0;
    macroTypeField = addInput("Generate macro for each", new ComboBox<MacroDefinition.MacroType>(), row++);
    nameField = addInput("Macro Name", new TextField(), row++);
    valueField = addInput("Macro Code", new TextArea(), row++);
    allowPlayerEditsField = addInput("Allow Player Edits?", new CheckBox(), row++);
    applyToTokensField = addInput("Apply to tookens?", new CheckBox(), row++);
    autoExecuteField = addInput("Auto execute?", new CheckBox(), row++);
    buttonGroupField = addInput("Button Group", new TextField(), row++);
    buttonLabelField = addInput("Button Label", new TextField(), row++);
    buttonWidthField = addInput("Button Width", new TextField(), row++);
    hotKeyField = addInput("Hot Key", new TextField(), row++);
    includeLabelField = addInput("Include Label", new CheckBox(), row++);
    toolTipField = addInput("Tool Tip", new TextField(), row++);
    buttonBackgroundColor = addInput("Button Background", new TextField(), row++);
    buttonForegroundColor = addInput("Button Foreground", new TextField(), row++);
    
    macroTypeField.setItems(FXCollections.observableArrayList(MacroType.values()));
    buttonWidthField.setTextFormatter(new TextFormatter<Change>(new NumericFormatter()));
    try{
      AutoCompleteDecorator autocomplete = new AutoCompleteDecorator((TextArea)valueField);
      autocomplete.initializeEntries(new Character());
    } catch (IntrospectionException ex) {
      throw new RuntimeException (ex);
    }

    Platform.runLater(new Runnable() {
      public void run() {
        nameField.requestFocus();
      }
    });

    getDialogPane().setContent(contentPane);
  }
  private void initializeContentPane() {
    contentPane = new GridPane();      
    contentPane.setVgap(5);
    contentPane.setHgap(10);
  }
  private <T extends Control> T addInput(String labelText, T inputField, int row) {
    Label label = new Label(labelText);
    contentPane.add(label, 0, row);
    contentPane.add(inputField, 1, row);
    GridPane.setValignment(label, VPos.TOP);
    return inputField;
  }
  private Collection<ButtonType> getButtons() {
    List<ButtonType> results = new LinkedList<>();
      
    results.add(ButtonType.OK);
    results.add(ButtonType.CANCEL);

    return results;
  }
  public void doDataExchange(MacroDefinition target, boolean isSaving) {
    if (isSaving){
      target.setMacroName(nameField.getText());
      target.setDefinition(valueField.getText());
      target.setMacroType(macroTypeField.getValue());
      target.setAllowPlayerEdits(allowPlayerEditsField.isSelected());
      target.setApplyToTokens(applyToTokensField.isSelected());
      target.setAutoExecute(autoExecuteField.isSelected());
      target.setButtonGoup(buttonGroupField.getText());
      target.setButtonLabel(buttonLabelField.getText());
      target.setWidth(Integer.valueOf(buttonWidthField.getText()));
      target.setHotKey(hotKeyField.getText());
      target.setIncludeLabel(includeLabelField.isSelected());
      target.setToolTip(toolTipField.getText());
      target.setButtonBackgroundColor(buttonBackgroundColor.getText());
      target.setButtonTextColor(buttonForegroundColor.getText());
    } else {
      nameField.setText(target.getMacroName());
      valueField.setText(target.getDefinition());
      macroTypeField.setValue(target.getMacroType());
      allowPlayerEditsField.setSelected(target.isAllowPlayerEdits());
      applyToTokensField.setSelected(target.isApplyToTokens());
      autoExecuteField.setSelected(target.isAutoExecute());
      buttonGroupField.setText(target.getButtonGoup());
      buttonLabelField.setText(target.getButtonLabel());
      buttonWidthField.setText(String.valueOf(target.getWidth()));
      hotKeyField.setText(target.getHotKey());
      includeLabelField.setSelected(target.isIncludeLabel());
      toolTipField.setText(target.getToolTip());
      buttonBackgroundColor.setText(target.getButtonBackgroundColor());
      buttonForegroundColor.setText(target.getButtonTextColor());
    }
  }
}
