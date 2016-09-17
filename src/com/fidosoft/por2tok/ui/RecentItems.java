package com.fidosoft.por2tok.ui;

import java.util.*;
import java.util.prefs.Preferences;

import org.apache.commons.lang.StringUtils;

public class RecentItems {
  public static final String RECENT_ITEM_STRING = ".recent.item.";

  private int maxItems;
  private Preferences preferences;
  private String preferencesKeyPrefix;

  private List<String> items = new ArrayList<String>();
  private List<RecentItemsObserver> observers = new ArrayList<RecentItemsObserver>();

  public RecentItems(String prefix, int maxItems, Preferences preferences) {
    this.maxItems = maxItems;
    this.preferences = preferences;
    this.preferencesKeyPrefix = prefix + RECENT_ITEM_STRING;
    loadFromPreferences();
  }

  public void push(String item) {
    items.remove(item);
    items.add(0, item);

    if (items.size() > maxItems) {
      items.remove(items.size() - 1);
    }

    update();
  }

  public void remove(Object item) {
    items.remove(item);
    update();
  }

  public String get(int index) {
    return items.get(index);
  }

  public List<String> getItems() {
    return items;
  }

  public int size() {
    return items.size();
  }

  private void loadFromPreferences() {
    // load recent files from properties
    for (int i = 0; i < maxItems; i++) {
      String val = preferences.get(preferencesKeyPrefix + i, "");

      if (StringUtils.isNotBlank(val)) {
        items.add(val);
      } else {
        break;
      }
    }
  }

  public void addObserver(RecentItemsObserver observer) {
    observers.add(observer);
  }

  public void removeObserver(RecentItemsObserver observer) {
    observers.remove(observer);
  }

  private void update() {
    for (RecentItemsObserver observer : observers) {
      observer.onRecentItemChange(this);
    }

    storeToPreferences();
  }

  private void storeToPreferences() {
    for (int i = 0; i < maxItems; i++) {
      if (i < items.size()) {
        preferences.put(preferencesKeyPrefix + i, (String) items.get(i));
      } else {
        preferences.remove(preferencesKeyPrefix + i);
      }
    }
  }
}
