package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSUtil;
import java.io.PrintStream;
import java.util.Properties;

public class Semimonthly
{
  private static Properties a = null;
  private static boolean jdField_do = false;
  private static boolean jdField_if = false;
  
  public Semimonthly(String paramString)
  {
    init(paramString);
  }
  
  public static void init(String paramString)
  {
    if (!jdField_do) {
      try
      {
        a = a(paramString);
        jdField_do = true;
      }
      catch (Exception localException)
      {
        FFSDebug.console("*** Semimonthly.init: Exception: " + localException.toString());
        FFSDebug.log("*** Semimonthly.init: Exception: " + localException.toString());
      }
    }
  }
  
  public static int getNext(int paramInt)
    throws Exception
  {
    int i = 0;
    if (jdField_do)
    {
      String str = a.getProperty(Integer.toString(paramInt));
      if (str != null) {
        i = Integer.parseInt(str);
      }
    }
    return i;
  }
  
  public static void reset()
  {
    jdField_do = false;
  }
  
  public static boolean isInitialized()
  {
    return jdField_do;
  }
  
  private static Properties a(String paramString)
    throws Exception
  {
    Properties localProperties = new Properties();
    try
    {
      localProperties = FFSUtil.loadPropsFromClasspath(paramString);
    }
    catch (Exception localException)
    {
      FFSDebug.console(FFSDebug.stackTrace(localException));
      FFSDebug.log(FFSDebug.stackTrace(localException));
      throw localException;
    }
    return localProperties;
  }
  
  public static void print()
    throws Exception
  {
    for (int i = 1; i <= 12; i++) {
      for (int j = 1; j <= 31; j++)
      {
        int k = i * 100 + j;
        String str = a.getProperty(Integer.toString(k));
        if (str != null)
        {
          int m = Integer.parseInt(str);
          System.out.println(k + "=" + m);
        }
      }
    }
  }
  
  public static void setDebug(boolean paramBoolean)
  {
    jdField_if = paramBoolean;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.Semimonthly
 * JD-Core Version:    0.7.0.1
 */