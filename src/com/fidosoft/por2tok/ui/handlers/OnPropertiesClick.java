package com.fidosoft.por2tok.ui.handlers;

import java.util.Optional;

import com.fidosoft.por2tok.*;
import com.fidosoft.por2tok.ui.PropertyDefinitionDialog;

import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class OnPropertiesClick implements EventHandler<MouseEvent> {
  private TableView table;
  public OnPropertiesClick(TableView table) {
    this.table = table;
  }
  @Override
  public void handle(MouseEvent event) {
    if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
      PropertyDefinition def = (PropertyDefinition) table.getSelectionModel().getSelectedItem();
      PropertyDefinitionDialog dialog = new PropertyDefinitionDialog(def);
      Optional<ButtonType> result = dialog.showAndWait();
      if (result.get() == ButtonType.OK){
        def.setPropertyName(dialog.nameField.getText());
        def.setDefinition(dialog.valueField.getText());
        ((TableColumn)(table.getColumns().get(0))).setVisible(false);
        ((TableColumn)(table.getColumns().get(0))).setVisible(true);
        if (!def.isValid()){
          table.getItems().remove(def);
        } else if (table.getSelectionModel().getSelectedIndex() == (table.getItems().size() - 1)  ){
          table.getItems().add(new PropertyDefinition());
        }
      }
    }
  }
}
