package com.fidosoft.por2tok.ui;

import java.util.*;

import org.apache.commons.lang.StringUtils;

import com.fidosoft.por2tok.HeroLabTokenCreator;
import com.fidosoft.por2tok.ui.handlers.*;

import javafx.event.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;

public class TokenCreatorMenuBar extends javafx.scene.control.MenuBar implements RecentItemsObserver{
  private HeroLabTokenCreator application;
  private RecentItems recentPortfolios;

  public TokenCreatorMenuBar(HeroLabTokenCreator application) {
    this.application = application;
    this.recentPortfolios = new RecentItems("files", 5, application.getPreferences());
    recentPortfolios.addObserver(this);
    
    getMenus().addAll(createFileMenu(), createSettingsMenu());
    
  }
  private Menu createSettingsMenu() {
    Menu fileMenu = new Menu("_Settings");
    fileMenu.getItems().addAll(getSettingsMenuItems());
    return fileMenu;
  }

  private Menu createFileMenu() {
    Menu fileMenu = new Menu("_File");
    fileMenu.getItems().addAll(getFileMenuItems());
    return fileMenu;
  }
  private Collection<MenuItem> getSettingsMenuItems() {
    List<MenuItem> result = new LinkedList<>();
    result.add(createMenuItem("Token _Properties...", "SHORTCUT+P", new OnSettingsProperties(application)));
    result.add(createMenuItem("Token _Macros...", "SHORTCUT+M", new OnSettingsMacros(application)));
    return result;
  }
  private Collection<MenuItem> getFileMenuItems() {
    List<MenuItem> result = new LinkedList<>();
    result.add(createMenuItem("_Open Portfolio...", "SHORTCUT+O", new OnFileOpen(application)));
    result.add(createMenuItem("_Generate Tokens...", "SHORTCUT+G", null));
    result.addAll(recentFiles());
    result.add(new SeparatorMenuItem());
    result.add(createMenuItem("_Settings...", "SHORTCUT+S", null));
    result.add(new SeparatorMenuItem());
    result.add(createMenuItem("E_xit...", "SHORTCUT+X", null));
    return result;
  }

  private Collection<MenuItem> recentFiles() {
    List<MenuItem> results = new LinkedList<>();
    
    if (!recentPortfolios.getItems().isEmpty()){
      results.add(new SeparatorMenuItem());
      for (String item : recentPortfolios.getItems()){
        MenuItem menuItem = new MenuItem(item);
        menuItem.setOnAction(new OnRecentOpen(application, item));
        results.add(menuItem);
      }
    }
    return results;
  }

  private MenuItem createMenuItem(String text, String shortcut, EventHandler<ActionEvent> eventHandler) {
    MenuItem result = new MenuItem(text);

    if (StringUtils.isNotBlank(shortcut))
      result.setAccelerator(KeyCombination.keyCombination(shortcut));

    if (eventHandler != null)
      result.setOnAction(eventHandler);

    return result;
  }

  public void addRecent(String path){
    recentPortfolios.push(path);
  }

  @Override
  public void onRecentItemChange(RecentItems src) {
    Menu newFileMenu = createFileMenu();
    Menu oldFileMenu = getMenus().get(0);
    
    oldFileMenu.getItems().clear();
    oldFileMenu.getItems().addAll(newFileMenu.getItems());        
  }
}
