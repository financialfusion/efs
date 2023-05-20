package com.ffusion.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Properties;

public class PropertiesUtil
{
  public static Properties getPropertiesFromResource(Object paramObject, String paramString)
  {
    Properties localProperties = null;
    InputStream localInputStream = ResourceUtil.getResourceAsStream(paramObject, paramString);
    if (localInputStream != null) {
      try
      {
        localProperties = new Properties();
        localProperties.load(localInputStream);
      }
      catch (IOException localIOException)
      {
        System.out.println("PropertiesUtil.getPropertiesFromResource:" + localIOException.toString());
        localProperties = null;
      }
    }
    return localProperties;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.PropertiesUtil
 * JD-Core Version:    0.7.0.1
 */