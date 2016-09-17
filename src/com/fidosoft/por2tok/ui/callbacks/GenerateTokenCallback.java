package com.fidosoft.por2tok.ui.callbacks;

import com.fidosoft.por2tok.Character;

import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class GenerateTokenCallback implements Callback<CellDataFeatures<Character, Boolean>, ObservableValue<Boolean>> {
  @Override
  public ObservableValue<Boolean> call(CellDataFeatures<Character, Boolean> param) {
    BooleanProperty result = new SimpleBooleanProperty();   
    result.set(param.getValue().isGenerateToken());
    return result;
  }
}