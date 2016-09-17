package com.fidosoft.por2tok.ui.handlers;

import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

import org.apache.commons.lang.StringUtils;

import com.fidosoft.por2tok.Character;
import com.fidosoft.por2tok.ui.HTMLDisplayDialog;

public class OnClickPortfolioRow implements EventHandler<MouseEvent>{
  private TableView table;
  public OnClickPortfolioRow(TableView table) {
    this.table = table;
  }
  @Override
  public void handle(MouseEvent event) {
    if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
      Character c = (Character)table.getSelectionModel().getSelectedItem();
      if (StringUtils.isNotBlank(c.getHtmlStatBlock())){
        HTMLDisplayDialog dialog = new HTMLDisplayDialog(c.getHtmlStatBlock());
        dialog.showAndWait();
      }
    }
  }

}
