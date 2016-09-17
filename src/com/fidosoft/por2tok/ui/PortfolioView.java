package com.fidosoft.por2tok.ui;

import java.util.*;

import org.apache.commons.lang.WordUtils;

import com.fidosoft.por2tok.HeroLabTokenCreator;
import com.fidosoft.por2tok.ui.callbacks.*;
import com.fidosoft.por2tok.ui.handlers.OnClickPortfolioRow;

import javafx.scene.control.*;
import javafx.scene.control.cell.*;

public class PortfolioView extends TableView {
  private HeroLabTokenCreator application;
  private RecentItems displayedColumns;
  public PortfolioView(HeroLabTokenCreator application) {
    this.application = application;
    this.displayedColumns = new RecentItems("portfolio_columns.", 10, application.getPreferences());
    
    initializeDefaultColumns();
    initialize();
  }
  
  private void initializeDefaultColumns() {
    if (displayedColumns.size() == 0){
      displayedColumns.push("classes");
      displayedColumns.push("gender");
      displayedColumns.push("race");
      displayedColumns.push("name");
    }
  }

  private void initialize(){
    setItems(application.getPortfolio().getObservableCharacterList());
    getColumns().addAll(getStockColumns());
    getColumns().addAll(getPortfolioColumns());
    setEditable(true);
    setOnMousePressed(new OnClickPortfolioRow(this));
  }

  private Collection<TableColumn> getStockColumns(){
    List<TableColumn> columns = new LinkedList<>();
    
    TableColumn include = new TableColumn("Generate");
    include.setCellValueFactory(new GenerateTokenCallback());
    include.setCellFactory(CheckBoxTableCell.forTableColumn(include));
    columns.add(include);
    TableColumn tokenName = createColumn("Token Name", "tokenName");
    tokenName.setCellFactory(new EditingTableCellFactory());
    columns.add(tokenName);
    
    TableColumn imageSelection = new TableColumn<>("Image");
    imageSelection.setSortable(false);
    imageSelection.setCellValueFactory(new SetImageCallback());
    imageSelection.setCellFactory(new ButtonTableCellFactory(application));
    columns.add(imageSelection);
    
    return columns;
  }
  
  private Collection<TableColumn> getPortfolioColumns() {
    List<TableColumn> columns = new LinkedList<>();
    
    for(String column : displayedColumns.getItems()){
      String name = column.replaceAll("_", " ");
      name = WordUtils.capitalize(name);
      columns.add(createColumn(name, column));
    }
    return columns;
  }
  
  private TableColumn createColumn(String columnName, String fieldName) {
    TableColumn column = new TableColumn(columnName);
    column.setCellValueFactory(new PropertyValueFactory(fieldName));
    return column;
  }

}
