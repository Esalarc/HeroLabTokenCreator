package com.fidosoft.por2tok.ui.controls;

import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.*;

public class EditingTableCell extends TableCell<Character, String> {
  private TextField textField;

  @Override
  public void startEdit() {
    super.startEdit();
    
    if (textField == null) {
        createTextField();
    }
      
    setGraphic(textField);
    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    textField.selectAll();
  }
  private void createTextField() {
    textField = new TextField(getString());
    textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()*2);
    textField.setOnKeyPressed(new KeyEventHandler());
  }
  
  private String getString() {
    return getItem() == null ? "" : getItem().toString();
  }
  
  @Override
  public void cancelEdit() {
      super.cancelEdit();
        
      setText(String.valueOf(getItem()));
      setContentDisplay(ContentDisplay.TEXT_ONLY);
  }
  @Override
  protected void updateItem(String item, boolean empty) {
    super.updateItem(item, empty);
    if (empty) {
      setText(null);
      setGraphic(null);
    } else {
      if (isEditing()) {
          if (textField != null) {
              textField.setText(getString());
          }
          setGraphic(textField);
          setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
      } else {
          setText(getString());
          setContentDisplay(ContentDisplay.TEXT_ONLY);
      }
    }
  }
    
  private final class KeyEventHandler implements EventHandler<KeyEvent> {
    @Override
    public void handle(KeyEvent t) {
      if (t.getCode() == KeyCode.ENTER) {
        commitEdit(textField.getText());
      } else if (t.getCode() == KeyCode.ESCAPE) {
        cancelEdit();
      }
    }
  }

}
