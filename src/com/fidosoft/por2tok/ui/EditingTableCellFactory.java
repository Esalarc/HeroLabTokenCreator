package com.fidosoft.por2tok.ui;

import com.fidosoft.por2tok.ui.controls.EditingTableCell;

import javafx.scene.control.*;
import javafx.util.Callback;

public class EditingTableCellFactory implements Callback<TableColumn, TableCell> {
  @Override
  public TableCell call(TableColumn p) {
      return new EditingTableCell();
  }
}