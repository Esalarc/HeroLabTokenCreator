package com.fidosoft.por2tok.ui.handlers;

import java.util.Optional;

import com.fidosoft.por2tok.*;
import com.fidosoft.por2tok.ui.MacroDefinitionDialog;

import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class OnMacrosClick implements EventHandler<MouseEvent> {
  private TableView table;
  public OnMacrosClick(TableView table) {
    this.table = table;
  }
  @Override
  public void handle(MouseEvent event) {
    if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
      MacroDefinition def = (MacroDefinition) table.getSelectionModel().getSelectedItem();
      MacroDefinitionDialog dialog = new MacroDefinitionDialog(def);
      Optional<ButtonType> result = dialog.showAndWait();
      if (result.get() == ButtonType.OK){
        dialog.doDataExchange(def, true);
        ((TableColumn)(table.getColumns().get(0))).setVisible(false);
        ((TableColumn)(table.getColumns().get(0))).setVisible(true);
        if (!def.isValid()){
          table.getItems().remove(def);
        } else if (table.getSelectionModel().getSelectedIndex() == (table.getItems().size() - 1)  ){
          table.getItems().add(new MacroDefinition());
        }
      }
    }
  }
}
