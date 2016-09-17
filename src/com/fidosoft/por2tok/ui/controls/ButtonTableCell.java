package com.fidosoft.por2tok.ui.controls;

import com.fidosoft.por2tok.Character;
import com.fidosoft.por2tok.HeroLabTokenCreator;
import com.fidosoft.por2tok.ui.handlers.OnClickImageButton;

import javafx.scene.control.*;

public class ButtonTableCell extends TableCell<Character, Boolean> {
  private Button cellButton = new Button("Action");
  
  public ButtonTableCell(HeroLabTokenCreator application){
    cellButton.setOnAction(new OnClickImageButton(application));
  }
  @Override
  protected void updateItem(Boolean item, boolean empty) {
    super.updateItem(item, empty);
    TableRow row = getTableRow();
    if (row != null){
      Character character = (Character) row.getItem();
      if (character != null){
        StringBuilder label = new StringBuilder(" ");
        if (character.getImage() != null){
          label.append("\u2611");
        } else {
          label.append("\u2610");
        }
        label.append("  ");
        if (character.getTokenImage() != null){
          label.append("\u2611");
        } else {
          label.append("\u2610"); 
        }
        label.append(" ");
        cellButton.setText(label.toString());
      }
    }
    if(!empty){
      setGraphic(cellButton);
    }
  }
}
