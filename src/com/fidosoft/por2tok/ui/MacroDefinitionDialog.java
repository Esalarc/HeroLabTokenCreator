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
  
  private static final String[] COLORS = {"default", "black","blue", "cyan", "gray", "magenta", "red", "white", "yellow", 
                                          "darkgray", "green", "lightgray", "orange", "pink", "aqua", "fuchsia",
                                          "lime", "maroon", "navy", "olive", "purple", "silver", "teal", 
                                          "gray25", "gray50", "gray75"};
  private static final String[] HOTKEYS = { "None", "F2", "F3", "F4", "F5", "F6", "F7", "F8",
                                            "F9", "F10", "F11", "F12", "alt F1", "alt F2", "alt F3", "alt F5", "alt F6",
                                            "alt F7", "alt F8", "alt F9", "alt F10", "alt F11", "alt F12", "ctrl F1", "ctrl F2",
                                            "ctrl F3", "ctrl F4", "ctrl F5", "ctrl F6", "ctrl F7", "ctrl F8", "ctrl F9",
                                            "ctrl F10", "ctrl F11", "ctrl F12", "shift F1", "shift F2", "shift F3", "shift F4",
                                            "shift F5", "shift F6", "shift F7", "shift F8", "shift F9", "shift F10",
                                            "shift F11", "shift F12"};

  
  private ComboBox<MacroType> macroTypeField;
  private TextField nameField;
  private TextArea valueField;
  private CheckBox allowPlayerEditsField;
  private CheckBox applyToTokensField;
  private CheckBox autoExecuteField;
  private TextField buttonGroupField;
  private TextField buttonLabelField;
  private TextField buttonWidthField;
  private ComboBox<String> hotKeyField;
  private CheckBox includeLabelField;
  private TextField toolTipField;

  private ComboBox<String> buttonBackgroundColor;
  private ComboBox<String> buttonForegroundColor;
  
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
    hotKeyField = addInput("Hot Key", new ComboBox<String>(), row++);
    includeLabelField = addInput("Include Label", new CheckBox(), row++);
    toolTipField = addInput("Tool Tip", new TextField(), row++);
    buttonBackgroundColor = addInput("Button Background", new ComboBox<String>(), row++);
    buttonForegroundColor = addInput("Button Foreground", new ComboBox<String>(), row++);
    
    buttonBackgroundColor.setItems(FXCollections.observableArrayList(COLORS));
    buttonForegroundColor.setItems(FXCollections.observableArrayList(COLORS));
    hotKeyField.setItems(FXCollections.observableArrayList(HOTKEYS));
    
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
      target.setHotKey(hotKeyField.getValue());
      target.setIncludeLabel(includeLabelField.isSelected());
      target.setToolTip(toolTipField.getText());
      target.setButtonBackgroundColor(buttonBackgroundColor.getValue());
      target.setButtonTextColor(buttonForegroundColor.getValue());
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
      hotKeyField.setValue(target.getHotKey());
      includeLabelField.setSelected(target.isIncludeLabel());
      toolTipField.setText(target.getToolTip());
      buttonBackgroundColor.setValue(target.getButtonBackgroundColor());
      buttonForegroundColor.setValue(target.getButtonTextColor());
    }
  }
}
