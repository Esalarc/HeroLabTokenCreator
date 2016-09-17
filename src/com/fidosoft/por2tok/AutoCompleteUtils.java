package com.fidosoft.por2tok;

import java.beans.*;
import java.util.*;

import org.apache.commons.lang.StringUtils;

public class AutoCompleteUtils {
  public static String formatFieldName(String grandParents, String parent, String field){
    if (StringUtils.isEmpty(grandParents)){
      return String.format("${%s.%s}", parent, field);
    } else {
      return String.format("${%s.%s.%s}", grandParents, parent, field);
    }
  }
  public static <T> List<String> getProperties(Class<T> c, String parents) throws IntrospectionException{
    List<String> entries = new LinkedList<>();
    String parent = c.getSimpleName().toLowerCase();
    if (parents != null){
      entries.add(String.format("${%s.%s", parents, parent));
    } else {
      entries.add(String.format("${%s", parent));
    }
    BeanInfo info = Introspector.getBeanInfo(c, Object.class);
    PropertyDescriptor[] descriptors = info.getPropertyDescriptors();
    for (PropertyDescriptor propertyDescriptor : descriptors) {
      entries.add(formatFieldName(parents, parent, propertyDescriptor.getName()));
    }
    return entries;
  }
}
