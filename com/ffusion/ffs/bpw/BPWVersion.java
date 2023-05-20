package com.ffusion.ffs.bpw;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Properties;

public class BPWVersion
{
  private static String jdField_do = "BPWVersion.txt";
  private static String jdField_int = "Bill Payment And Transfer Warehouse";
  private static String jdField_for = "x";
  private static String jdField_if = "Beta";
  private static String jdField_new = null;
  private static Properties a = null;
  
  public static final String getProductVersionString()
  {
    if (jdField_new == null)
    {
      if (a == null) {
        a();
      }
      jdField_new = a.getProperty("BPW_PRODUCT", jdField_int) + " " + a.getProperty("BPW_MAJOR", jdField_for) + "." + a.getProperty("BPW_MINOR", jdField_for) + "." + a.getProperty("BPW_PATCH", jdField_for) + ", Build " + a.getProperty("BPW_BUILD_NUM", new StringBuffer().append(jdField_for).append(";").toString()) + " " + a.getProperty("RELEASE_STRING", jdField_if);
    }
    return jdField_new;
  }
  
  private static final void a()
  {
    if (a != null) {
      return;
    }
    String str = System.getProperty("VERSION_NAME", jdField_do);
    a = new Properties();
    try
    {
      ClassLoader localClassLoader = BPWServer.class.getClassLoader();
      if (localClassLoader == null) {
        localClassLoader = ClassLoader.getSystemClassLoader();
      }
      InputStream localInputStream = localClassLoader.getResourceAsStream(str);
      if (localInputStream == null)
      {
        System.out.println("Version file not found");
        return;
      }
      a.load(localInputStream);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.BPWVersion
 * JD-Core Version:    0.7.0.1
 */