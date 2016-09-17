package com.fidosoft.por2tok.ui;

import java.util.*;
import java.util.prefs.Preferences;

import com.fidosoft.por2tok.PropertyDefinition;
import com.fidosoft.por2tok.ui.handlers.OnPropertiesClick;

import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;

public class PropertySettings extends Dialog {
  private ObservableList<PropertyDefinition> properties;
  
  public PropertySettings(Preferences preferences) {
    loadProperties(preferences);
    setTitle("Comfigure Properties");
    setResizable(true);
    initModality(Modality.APPLICATION_MODAL);
    getDialogPane().getButtonTypes().addAll(getButtons());
    
    TableView tableView = new TableView();
    tableView.getColumns().addAll(getColumns());
    tableView.setItems(this.properties);
    tableView.setOnMousePressed(new OnPropertiesClick(tableView));
    getDialogPane().setContent(tableView);
  }
  
  private void loadProperties(Preferences preferences) {
    properties = FXCollections.observableList(PropertyDefinition.getProperties(preferences));
  }

  private List<TableColumn> getColumns() {
    List<TableColumn> columns = new LinkedList<>();
    TableColumn propertyName = createColumn("Name", "propertyName");
    columns.add(propertyName);

    TableColumn propertyDefinition = createColumn("Value", "definition");
    columns.add(propertyDefinition);

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

  public List<PropertyDefinition> getProperties() {
    return properties;
  }

}
