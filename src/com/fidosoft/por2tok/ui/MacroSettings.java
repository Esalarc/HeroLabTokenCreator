package com.fidosoft.por2tok.ui;

import java.util.*;
import java.util.prefs.Preferences;

import com.fidosoft.por2tok.MacroDefinition;
import com.fidosoft.por2tok.ui.handlers.OnMacrosClick;

import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;

public class MacroSettings extends Dialog {
  private ObservableList<MacroDefinition> macros;
  
  public MacroSettings(Preferences preferences) {
    loadProperties(preferences);
    setTitle("Configure Macros");
    setResizable(true);
    initModality(Modality.APPLICATION_MODAL);
    getDialogPane().getButtonTypes().addAll(getButtons());
    
    TableView tableView = new TableView();
    tableView.getColumns().addAll(getColumns());
    tableView.setItems(this.macros);
    tableView.setOnMousePressed(new OnMacrosClick(tableView));
    getDialogPane().setContent(tableView);
  }
  
  private void loadProperties(Preferences preferences) {
    macros = FXCollections.observableList(MacroDefinition.getMacros(preferences));
  }

  private List<TableColumn> getColumns() {
    List<TableColumn> columns = new LinkedList<>();
    columns.add(createColumn("Name", "macroName"));
    columns.add(createColumn("Applies To", "macroType"));
    columns.add(createColumn("Group", "group"));
    columns.add(createColumn("Hot Key", "hotKey"));

    return columns;
  }
  private TableColumn createColumn(String columnName, String fieldName) {

    TableColumn column = new TableColumn(columnName);
    column.setCellValueFactory(new PropertyValueFactory(fieldName));
    return column;
  }

  private Collection<ButtonType> getButtons() {
    List<ButtonType> results = new LinkedList<>();
      
    results.add(ButtonType.OK);
    results.add(ButtonType.CANCEL);

    return results;
  }

  public List<MacroDefinition> getMacros() {
    return macros;
  }

}
