package com.ffusion.ffs.util;

import com.ffusion.banksim.interfaces.BSDBParams;
import com.ffusion.banksim.proxy.BankSim;
import java.util.Properties;

public class FFSBanksimUtil
{
  private static final String jdField_try = " * Banksim: ";
  private static final String jdField_byte = "banksim.properties";
  private static final String jdField_for = "user";
  private static final String jdField_if = "passwd";
  private static final String jdField_char = "dbname";
  private static final String jdField_do = "server";
  private static final String a = "port";
  private static final String jdField_new = "dbtype";
  private static final String jdField_int = "usenative";
  private static final int jdField_case = 10;
  
  public static void initBanksim()
    throws Exception
  {
    initBanksim("banksim.properties");
  }
  
  public static void initBanksim(String paramString)
    throws Exception
  {
    Properties localProperties = new Properties();
    try
    {
      localProperties.load(ClassLoader.getSystemResourceAsStream(paramString));
    }
    catch (Exception localException1)
    {
      log("Unable to load the banksim properties file from the classpath:" + paramString);
      throw localException1;
    }
    try
    {
      if (!BankSim.isInitialized())
      {
        BSDBParams localBSDBParams = BankSim.getBSDBParams(localProperties.getProperty("user"), localProperties.getProperty("passwd"), localProperties.getProperty("server"), localProperties.getProperty("port"), localProperties.getProperty("dbname"), localProperties.getProperty("dbtype"), Boolean.valueOf(localProperties.getProperty("usenative", "false")).booleanValue());
        BankSim.initialize(localBSDBParams, 10);
      }
    }
    catch (Exception localException2)
    {
      log(localException2, "Banksim could not be initialized from properties.");
      throw localException2;
    }
  }
  
  public static void log(String paramString)
  {
    FFSDebug.log(" * Banksim: " + paramString);
  }
  
  public static void log(Throwable paramThrowable, String paramString)
  {
    FFSDebug.log(paramThrowable, " * Banksim: " + paramString);
  }
  
  public static void warning(String paramString)
  {
    FFSDebug.log("WARNING!  * Banksim: " + paramString);
  }
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.util.FFSBanksimUtil
 * JD-Core Version:    0.7.0.1
 */