package com.ffusion.systemadmin;

import java.io.PrintStream;
import java.io.PrintWriter;

public class SAException
  extends Exception
{
  public static final int ERROR_UNABLE_TO_COMPLETE_REQUEST = 38201;
  public static final int ERROR_MISSING_DATABASE_CONFIG = 38202;
  public static final int ERROR_INVALID_DATABASE_CONFIG = 38203;
  public static final int ERROR_INVALID_SETTINGS_LIST = 38204;
  public static final int ERROR_INVALID_DATA_RETENTION_TYPE = 38205;
  public static final int ERROR_INVALID_BUSINESS_ID = 38206;
  public static final int ERROR_INVALID_SETTING = 38207;
  private Throwable jdField_if;
  private int a;
  
  public SAException(int paramInt)
  {
    this.a = paramInt;
  }
  
  public SAException(int paramInt, String paramString)
  {
    super(paramString);
    this.a = paramInt;
  }
  
  public SAException(int paramInt, String paramString, Throwable paramThrowable)
  {
    super(paramString);
    this.a = paramInt;
    this.jdField_if = paramThrowable;
  }
  
  public SAException(int paramInt, Throwable paramThrowable)
  {
    this.a = paramInt;
    this.jdField_if = paramThrowable;
  }
  
  public void printStackTrace()
  {
    super.printStackTrace();
    if (this.jdField_if != null) {
      this.jdField_if.printStackTrace();
    }
  }
  
  public void printStackTrace(PrintStream paramPrintStream)
  {
    super.printStackTrace(paramPrintStream);
    if (this.jdField_if != null) {
      this.jdField_if.printStackTrace(paramPrintStream);
    }
  }
  
  public void printStackTrace(PrintWriter paramPrintWriter)
  {
    super.printStackTrace(paramPrintWriter);
    if (this.jdField_if != null) {
      this.jdField_if.printStackTrace(paramPrintWriter);
    }
  }
  
  public Throwable getChained()
  {
    return this.jdField_if;
  }
  
  public int getErrorCode()
  {
    return this.a;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.systemadmin.SAException
 * JD-Core Version:    0.7.0.1
 */