package com.ffusion.ffs.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

public class FFSProperties
  extends Properties
  implements FFSConst, Serializable
{
  private static final long Y = 12345678910L;
  
  public FFSProperties() {}
  
  public FFSProperties(Properties paramProperties)
  {
    super(paramProperties);
  }
  
  public FFSProperties(HashMap paramHashMap)
  {
    if (paramHashMap != null)
    {
      Iterator localIterator = paramHashMap.keySet().iterator();
      String str = null;
      while (localIterator.hasNext())
      {
        str = (String)localIterator.next();
        setProperty(str, (String)paramHashMap.get(str));
      }
    }
  }
  
  public FFSProperties(InputStream paramInputStream)
  {
    try
    {
      super.load(paramInputStream);
      paramInputStream.close();
    }
    catch (IOException localIOException)
    {
      FFSDebug.log("Failed to load properties. Error: " + localIOException.getMessage());
    }
  }
  
  public FFSProperties(String paramString)
  {
    try
    {
      ClassLoader localClassLoader = FFSProperties.class.getClassLoader();
      if (localClassLoader == null) {
        localClassLoader = ClassLoader.getSystemClassLoader();
      }
      InputStream localInputStream = localClassLoader.getResourceAsStream(paramString);
      if (localInputStream != null)
      {
        super.load(localInputStream);
        localInputStream.close();
        FFSDebug.log("Properities file: ", paramString, " loaded", 6);
      }
      else
      {
        FFSDebug.log("Failed to load file: ", paramString, 0);
      }
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      FFSDebug.log("Properties file: ", paramString, " not found. ", " Error: ", localFileNotFoundException.getMessage(), 0);
    }
    catch (IOException localIOException)
    {
      FFSDebug.log("Failed to load file: ", paramString, " Error: ", localIOException.getMessage(), 0);
    }
  }
  
  public boolean getBoolean(String paramString)
  {
    String str = super.getProperty(paramString);
    if ((str != null) && (str.length() != 0)) {
      return FFSUtil.booleanValue(str);
    }
    return false;
  }
  
  public int getInt(String paramString)
  {
    String str = super.getProperty(paramString);
    if ((str != null) && (str.length() != 0)) {
      return FFSUtil.intValue(str);
    }
    return 0;
  }
  
  public FFSProperties getPureKeyProperties()
  {
    StringTokenizer localStringTokenizer = null;
    FFSProperties localFFSProperties = new FFSProperties();
    String str1 = null;
    String str2 = null;
    Enumeration localEnumeration = super.propertyNames();
    while (localEnumeration.hasMoreElements())
    {
      str2 = (String)localEnumeration.nextElement();
      localStringTokenizer = new StringTokenizer(str2, ".");
      while (localStringTokenizer.hasMoreTokens()) {
        str1 = localStringTokenizer.nextToken();
      }
      localFFSProperties.setProperty(str1, super.getProperty(str2));
    }
    return localFFSProperties;
  }
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.util.FFSProperties
 * JD-Core Version:    0.7.0.1
 */