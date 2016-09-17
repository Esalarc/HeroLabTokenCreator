package com.fidosoft.por2tok;

import java.beans.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.prefs.Preferences;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;

import javafx.beans.value.WritableObjectValue;

public class BeanSerializer {
  public static <T extends SerializableBean> T readFromPreferences(Class<T> type, Preferences prefs, String index){
    T result = null;
    Gson gson = new Gson();
    String value = prefs.get(group(type, index), "");
    if (StringUtils.isNotBlank(value)){
      result = gson.fromJson(value, type);
    }
    
    return result;
  }
  public static void writeToPreferences(Object object, Preferences prefs, String index){
    Gson gson = new Gson();
    String json = gson.toJson(object);
    prefs.put(group(object.getClass(), index), json);
  }

  private static <T> String group(Class<T> type, String index){
    if (index == null){
      return type.getSimpleName();
    } else { 
      return type.getSimpleName() + "." + index;
    }
  }
  
  public static <T extends SerializableBean> List<T> loadObjectsFromPrefs(Class<T> type, Preferences prefs){
    List<T> results = new LinkedList<>();
    int index = 1;
    
    while (true){
      T read = (T) readFromPreferences(type, prefs, String.valueOf(index));
      if (read == null)
        break;
      results.add(read);
      if (!read.isValid()){
        break;
      } else {
        index++;
      }
    }

    if (results.isEmpty()){
      try{
        results.add((T)type.newInstance());      
      } catch (InstantiationException | IllegalAccessException e) {
        e.printStackTrace();
      }
    }
    return results;    
  }
  public static <T extends SerializableBean> void writeObjectsToPrefs(Collection<T> objects, Preferences prefs, Class<T> type){
    int index = 1;
    boolean lastOneValid = true;
    for (T object : objects){
      lastOneValid = object.isValid();
      writeToPreferences(object, prefs, String.valueOf(index));
      index++;
    }
    if (lastOneValid)
      try {
        writeToPreferences(type.newInstance(), prefs, String.valueOf(index));
      } catch (InstantiationException | IllegalAccessException e) {
        e.printStackTrace();
      }
  }
}
