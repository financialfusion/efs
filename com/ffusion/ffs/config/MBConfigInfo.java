package com.ffusion.ffs.config;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

public class MBConfigInfo
  implements Serializable
{
  private static final long jdField_if = 12345678910L;
  private boolean jdField_try = false;
  private String jdField_int = null;
  private String jdField_case = null;
  private String jdField_for = null;
  private String a = null;
  private String jdField_byte = null;
  private String jdField_new = null;
  private String jdField_do = null;
  
  public MBConfigInfo()
  {
    this.jdField_int = "localhost";
    this.jdField_case = "3002";
    this.jdField_for = "mbofx";
    this.a = "DBA";
    this.jdField_byte = "SQL";
    this.jdField_new = "ASA";
  }
  
  public MBConfigInfo(String paramString)
  {
    InputStream localInputStream = null;
    Properties localProperties = new Properties();
    try
    {
      localInputStream = loadProperties(paramString);
      localProperties.load(localInputStream);
      this.jdField_new = localProperties.getProperty("MBDBType");
      this.jdField_int = localProperties.getProperty("MBHOST");
      this.a = localProperties.getProperty("MBDBUser");
      this.jdField_byte = localProperties.getProperty("MBDBPwd");
      this.jdField_case = localProperties.getProperty("MBPORT");
      this.jdField_for = localProperties.getProperty("MBDBName");
      this.jdField_do = localProperties.getProperty("MBURL");
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
    }
    finally
    {
      try
      {
        if (localInputStream != null) {
          localInputStream.close();
        }
      }
      catch (Exception localException2) {}
    }
  }
  
  public String getHost()
  {
    return this.jdField_int;
  }
  
  public String getPort()
  {
    return this.jdField_case;
  }
  
  public String getDatabaseName()
  {
    return this.jdField_for;
  }
  
  public String getUser()
  {
    return this.a;
  }
  
  public String getPassword()
  {
    return this.jdField_byte;
  }
  
  public String getDBType()
  {
    return this.jdField_new;
  }
  
  public String getUrl()
  {
    return this.jdField_do;
  }
  
  public boolean isConfigured()
  {
    return this.jdField_try;
  }
  
  public void setHost(String paramString)
  {
    this.jdField_int = paramString;
  }
  
  public void setPort(String paramString)
  {
    this.jdField_case = paramString;
  }
  
  public void setDatabaseName(String paramString)
  {
    this.jdField_for = paramString;
  }
  
  public void setUser(String paramString)
  {
    this.a = paramString;
  }
  
  public void setPassword(String paramString)
  {
    this.jdField_byte = paramString;
  }
  
  public void setDBType(String paramString)
  {
    this.jdField_new = paramString;
  }
  
  public void setConfigured(boolean paramBoolean)
  {
    this.jdField_try = paramBoolean;
  }
  
  public void setUrl(String paramString)
  {
    this.jdField_do = paramString;
  }
  
  public static InputStream loadProperties(String paramString)
    throws Exception
  {
    InputStream localInputStream = null;
    try
    {
      ClassLoader localClassLoader = MBConfigInfo.class.getClassLoader();
      if (localClassLoader == null) {
        localClassLoader = ClassLoader.getSystemClassLoader();
      }
      localInputStream = localClassLoader.getResourceAsStream(paramString);
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
      throw new Exception("Failed to get properties");
    }
    return localInputStream;
  }
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.config.MBConfigInfo
 * JD-Core Version:    0.7.0.1
 */