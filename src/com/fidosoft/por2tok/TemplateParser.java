package com.fidosoft.por2tok;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import org.apache.commons.io.IOUtils;

import freemarker.template.*;

public class TemplateParser {
  Map<String, Object> objectMap = new HashMap<>();
  
  public void setObject(String key, Object value) {
    if (value == null){
      objectMap.remove(key);
    } else {
      objectMap.put(key, value);
    }
  }

  public Object getObject(String key) {
    return objectMap.get(key);
  }

  public String parseTemplate(String templateText) throws TemplateException {
    Configuration config = createConfiguration();
    StringWriter out = new StringWriter();
    try{
      Template template = new Template("root", new StringReader(templateText), config);
      template.process(objectMap, out);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return out.toString();
  }
  public String parseResource(String resourcePath) throws TemplateException {
    InputStream in = this.getClass().getResourceAsStream("/templates/" + resourcePath);
    try {
      String templateText = IOUtils.toString(in);
      in.close();
      return parseTemplate(templateText);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "";
  }
  private Configuration createConfiguration() {
    // Create your Configuration instance, and specify if up to what FreeMarker
    // version (here 2.3.25) do you want to apply the fixes that are not 100%
    // backward-compatible. See the Configuration JavaDoc for details.
    Configuration cfg = new Configuration(Configuration.VERSION_2_3_25);
    // Set the preferred charset template files are stored in. UTF-8 is
    // a good choice in most applications:
    cfg.setDefaultEncoding("UTF-8");

    // Sets how errors will appear.
    // During web page *development* TemplateExceptionHandler.HTML_DEBUG_HANDLER is better.
    cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    
    cfg.setLogTemplateExceptions(false);
    
    return cfg;
  }

}
