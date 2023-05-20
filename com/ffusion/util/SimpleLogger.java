package com.ffusion.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

public class SimpleLogger
{
  public static final int FATAL = 1;
  public static final int ERROR = 2;
  public static final int WARN = 3;
  public static final int INFO = 4;
  public static final int DEBUG = 5;
  private static final String jdField_try = "simplelogger.properties";
  private static final int jdField_do = 3;
  private static final String a = "line.separator";
  private static final String jdField_if = ":";
  private static String[] jdField_byte = { "NONE", "FATAL", "ERROR", "WARN", "INFO", "DEBUG" };
  private static HashMap jdField_new;
  private static HashMap jdField_int;
  private static String jdField_for;
  
  private static void a()
    throws IOException
  {
    Properties localProperties = PropertiesUtil.getPropertiesFromResource(new SimpleLogger(), "simplelogger.properties");
    if (localProperties != null)
    {
      jdField_for = System.getProperty("line.separator");
      jdField_new = new HashMap();
      jdField_int = new HashMap();
      Iterator localIterator = localProperties.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str1 = (String)localIterator.next();
        String str2 = (String)localProperties.get(str1);
        int i = str2.indexOf(":");
        if (i > -1)
        {
          try
          {
            jdField_int.put(str1, new Integer(str2.substring(i + 1)));
          }
          catch (NumberFormatException localNumberFormatException)
          {
            jdField_int.put(str1, new Integer(3));
          }
          str2 = str2.substring(0, i);
        }
        else
        {
          jdField_int.put(str1, new Integer(3));
        }
        FileWriter localFileWriter = new FileWriter(str2, true);
        jdField_new.put(str1, localFileWriter);
      }
    }
  }
  
  private static boolean a(String paramString1, int paramInt, Object paramObject, String paramString2)
  {
    if (jdField_new == null) {
      return false;
    }
    FileWriter localFileWriter = (FileWriter)jdField_new.get(paramString1);
    if (localFileWriter == null) {
      return false;
    }
    Integer localInteger = (Integer)jdField_int.get(paramString1);
    if (paramInt > localInteger.intValue()) {
      return false;
    }
    Date localDate = GregorianCalendar.getInstance().getTime();
    StringBuffer localStringBuffer = new StringBuffer(localDate.toString()).append(": <").append(jdField_byte[paramInt]).append("> ");
    if (paramObject != null) {
      localStringBuffer.append("(").append(paramObject.getClass().getName()).append(") ");
    }
    localStringBuffer.append(paramString2);
    try
    {
      localFileWriter.write(localStringBuffer.toString());
      localFileWriter.write(jdField_for);
      localFileWriter.flush();
    }
    catch (IOException localIOException)
    {
      return false;
    }
    return true;
  }
  
  public static void close()
  {
    Iterator localIterator = jdField_new.entrySet().iterator();
    while (localIterator.hasNext())
    {
      FileWriter localFileWriter = (FileWriter)localIterator.next();
      if (localFileWriter != null) {
        try
        {
          localFileWriter.close();
        }
        catch (IOException localIOException) {}finally
        {
          localFileWriter = null;
        }
      }
    }
    jdField_new = null;
    jdField_int = null;
  }
  
  public static void close(String paramString)
  {
    FileWriter localFileWriter = (FileWriter)jdField_new.get(paramString);
    if (localFileWriter != null) {
      try
      {
        localFileWriter.close();
      }
      catch (IOException localIOException) {}finally
      {
        localFileWriter = null;
        jdField_new.remove(paramString);
        jdField_int.remove(paramString);
      }
    }
  }
  
  public static void reset()
    throws IOException
  {
    close();
    a();
  }
  
  public static boolean fatal(String paramString1, String paramString2)
  {
    return fatal(paramString1, null, paramString2);
  }
  
  public static boolean fatal(String paramString1, Object paramObject, String paramString2)
  {
    return a(paramString1, 1, paramObject, paramString2);
  }
  
  public static boolean error(String paramString1, String paramString2)
  {
    return error(paramString1, null, paramString2);
  }
  
  public static boolean error(String paramString1, Object paramObject, String paramString2)
  {
    return a(paramString1, 2, paramObject, paramString2);
  }
  
  public static boolean warn(String paramString1, String paramString2)
  {
    return warn(paramString1, null, paramString2);
  }
  
  public static boolean warn(String paramString1, Object paramObject, String paramString2)
  {
    return a(paramString1, 3, paramObject, paramString2);
  }
  
  public static boolean info(String paramString1, String paramString2)
  {
    return info(paramString1, null, paramString2);
  }
  
  public static boolean info(String paramString1, Object paramObject, String paramString2)
  {
    return a(paramString1, 4, paramObject, paramString2);
  }
  
  public static boolean debug(String paramString1, String paramString2)
  {
    return debug(paramString1, null, paramString2);
  }
  
  public static boolean debug(String paramString1, Object paramObject, String paramString2)
  {
    return a(paramString1, 5, paramObject, paramString2);
  }
  
  public static boolean log(String paramString1, String paramString2)
  {
    return log(paramString1, null, paramString2);
  }
  
  public static boolean log(String paramString1, Object paramObject, String paramString2)
  {
    return a(paramString1, 3, paramObject, paramString2);
  }
  
  protected void finalize() {}
  
  static
  {
    try
    {
      a();
    }
    catch (Throwable localThrowable)
    {
      throw new ExceptionInInitializerError("Error in SimpleLogger while reading simplelogger.properties: " + localThrowable.toString());
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.SimpleLogger
 * JD-Core Version:    0.7.0.1
 */