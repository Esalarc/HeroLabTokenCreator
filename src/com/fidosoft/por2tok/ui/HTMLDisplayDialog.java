package com.fidosoft.por2tok.ui;

import java.util.LinkedList;
import java.util.List;

import com.fidosoft.por2tok.Character;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.web.WebView;

public class HTMLDisplayDialog extends Dialog{
  private String content;
  private WebView contentPane;
  
  public HTMLDisplayDialog(String content) {
    this.content = content;
    initialize();    
  }

  private void initialize() {
    setResizable(true);
    
    getDialogPane().getButtonTypes().addAll(getButtons());
    
    initializeContentPane();
    getDialogPane().setContent(contentPane);
  }

  private void initializeContentPane() {
    contentPane = new WebView();
    contentPane.getEngine().setJavaScriptEnabled(false);
    contentPane.getEngine().loadContent(content);
  }

  private List<ButtonType> getButtons() {
    List<ButtonType> results = new LinkedList<>();
    
    results.add(ButtonType.OK);

    return results;
  }

}
