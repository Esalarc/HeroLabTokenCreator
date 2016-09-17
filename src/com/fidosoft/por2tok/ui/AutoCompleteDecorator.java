package com.fidosoft.por2tok.ui;

import java.beans.IntrospectionException;
import java.util.*;

import com.fidosoft.por2tok.AutoCompleteObject;

import javafx.beans.value.*;
import javafx.event.*;
import javafx.geometry.Side;
import javafx.scene.control.*;

public class AutoCompleteDecorator {
  private final SortedSet<String> entries;
  private ContextMenu entriesPopup;
  private TextInputControl control;

  public AutoCompleteDecorator(TextInputControl control) {
    entries = new TreeSet<>();
    entriesPopup = new ContextMenu();
    this.control = control;
    control.textProperty().addListener(new TextChanged());
    control.focusedProperty().addListener(new FocusChanged());
  }    
  
  public void initializeEntries(AutoCompleteObject... propertyObjects) throws IntrospectionException {
    entries.clear();
    for (AutoCompleteObject o : propertyObjects) {
      entries.addAll(o.getAutoCompleteFields());
    }
  }

  private List<String> filterResults(List<String> entries, String prefix) {
    List<String> results = new LinkedList<>();
    for (String s : entries){
      if (s.indexOf(".", prefix.length()) == -1)
        results.add(s);
    }
    return results;
  }

  private void populatePopup(List<String> searchResult) {
    List<CustomMenuItem> menuItems = new LinkedList<>();
    // If you'd like more entries, modify this line.
    int maxEntries = 10;
    int count = Math.min(searchResult.size(), maxEntries);
    for (int i = 0; i < count; i++) {
      final String result = searchResult.get(i);
      Label entryLabel = new Label(result);
      CustomMenuItem item = new CustomMenuItem(entryLabel, true);
      item.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
          control.setText(result);
          control.positionCaret(control.getCaretPosition() + result.length());
          entriesPopup.hide();
        }
      });
      menuItems.add(item);
    }
    entriesPopup.getItems().clear();
    entriesPopup.getItems().addAll(menuItems);

  }

  private class TextChanged implements ChangeListener<String>{
    @Override
    public void changed(ObservableValue<? extends String> paramObservableValue, String paramT1, String paramT2) {
      if (control.getText().length() == 0) {
        entriesPopup.hide();
      } else {
        LinkedList<String> searchResult = new LinkedList<>();
        searchResult.addAll(entries.subSet(control.getText(), control.getText() + Character.MAX_VALUE));
        searchResult.retainAll(filterResults(searchResult, control.getText()));
        if (entries.size() > 0) {
          populatePopup(searchResult);
          if (!entriesPopup.isShowing()) {
            entriesPopup.show(control, Side.BOTTOM, 0, 0);
          }
        } else {
          entriesPopup.hide();
        }
      }
    }
        
  }
  private class FocusChanged implements ChangeListener<Boolean>{
    @Override
    public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean aBoolean2) {
      entriesPopup.hide();
    }
  }
}
