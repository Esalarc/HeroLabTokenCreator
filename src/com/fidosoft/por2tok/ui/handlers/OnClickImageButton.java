package com.fidosoft.por2tok.ui.handlers;


import java.util.Optional;

import com.fidosoft.por2tok.Character;
import com.fidosoft.por2tok.HeroLabTokenCreator;
import com.fidosoft.por2tok.ui.*;
import com.fidosoft.por2tok.ui.controls.ButtonTableCell;

import javafx.event.*;
import javafx.scene.Parent;
import javafx.scene.control.*;

public class OnClickImageButton implements EventHandler<ActionEvent> {
  private HeroLabTokenCreator application;
  public OnClickImageButton(HeroLabTokenCreator application) {
    this.application = application;
  }
  
  @Override
  public void handle(ActionEvent event) {
    Object o = event.getSource();
    if (o instanceof Button) {
      Button b = (Button)o;
      Parent p = b.getParent();
      if (p instanceof ButtonTableCell){
        ButtonTableCell cell = (ButtonTableCell)p;
        TableRow row = cell.getTableRow();
        if (row != null){
          Character character = (Character)row.getItem();
          if (character != null){
            ImageSelectionDialog dialog = new ImageSelectionDialog(application, character);
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.get() == ButtonType.OK){
              character.setImage(dialog.getPortrait());
              character.setTokenImageImage(dialog.getToken());
              ((TableColumn)(row.getTableView().getColumns().get(0))).setVisible(false);
              ((TableColumn)(row.getTableView().getColumns().get(0))).setVisible(true);
            }
          }
        }
      }
    }
  }
}