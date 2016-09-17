package com.fidosoft.por2tok.ui.callbacks;

import com.fidosoft.por2tok.Character;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class SetImageCallback implements Callback<CellDataFeatures<Character, Boolean>, ObservableValue<Boolean>> {
  @Override
  public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Character, Boolean> p) {
    return new SimpleBooleanProperty(p.getValue() != null);
  }  
}

