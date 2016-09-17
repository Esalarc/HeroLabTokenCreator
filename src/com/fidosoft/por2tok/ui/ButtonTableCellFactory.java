package com.fidosoft.por2tok.ui;

import javafx.scene.control.*;
import javafx.util.Callback;
import com.fidosoft.por2tok.Character;
import com.fidosoft.por2tok.HeroLabTokenCreator;
import com.fidosoft.por2tok.ui.controls.ButtonTableCell;

public class ButtonTableCellFactory implements Callback<TableColumn<Character, Boolean>,  TableCell<Character, Boolean>>{
  private HeroLabTokenCreator application;

  public ButtonTableCellFactory(HeroLabTokenCreator application) {
    this.application = application;
  }
  @Override
  public TableCell<Character, Boolean> call(TableColumn<Character, Boolean> p) {
    return new ButtonTableCell(application);
  }
}
