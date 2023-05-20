package com.ffusion.util.beans.filemonitor;

import java.io.PrintStream;
import java.io.PrintWriter;

public class FMException
  extends Exception
{
  public static final int ERROR_UNKNOWN = -1;
  public static final int ERROR_GENERAL_CONFIG = 1;
  public static final int ERROR_DB_CONFIG = 2;
  public static final int ERROR_INVALID_REPORT_CRITERIA = 3;
  public static final int ERROR_GENERAL_DB = 4;
  public static final int ERROR_LOGGER_NOT_INITIALIZED = 5;
  public static final int ERROR_LOG_HANDLER_NOT_INITIALIZED = 6;
  public static final int ERROR_FM_LOG_GENERAL_CONFIG = 7;
  public static final int ERROR_FM_LOG_DB_CONFIG = 8;
  public static final int ERROR_FM_LOG_FAILURE = 9;
  private int jdField_if;
  private Throwable a;
  
  public FMException()
  {
    this.jdField_if = -1;
  }
  
  public FMException(String paramString)
  {
    super(paramString);
    this.jdField_if = -1;
  }
  
  public FMException(String paramString, int paramInt)
  {
    super(paramString);
    this.jdField_if = paramInt;
  }
  
  public FMException(Throwable paramThrowable, int paramInt)
  {
    this.a = paramThrowable;
    this.jdField_if = paramInt;
  }
  
  public FMException(String paramString, Throwable paramThrowable, int paramInt)
  {
    super(paramString);
    this.a = paramThrowable;
    this.jdField_if = paramInt;
  }
  
  public void setCode(int paramInt)
  {
    this.jdField_if = paramInt;
  }
  
  public int getCode()
  {
    return this.jdField_if;
  }
  
  public void setCause(Throwable paramThrowable)
  {
    this.a = paramThrowable;
  }
  
  public Throwable getCause()
  {
    return this.a;
  }
  
  public void printStackTrace()
  {
    super.printStackTrace();
    if (this.a != null) {
      this.a.printStackTrace();
    }
  }
  
  public void printStackTrace(PrintWriter paramPrintWriter)
  {
    super.printStackTrace(paramPrintWriter);
    if (this.a != null) {
      this.a.printStackTrace(paramPrintWriter);
    }
  }
  
  public void printStackTrace(PrintStream paramPrintStream)
  {
    super.printStackTrace(paramPrintStream);
    if (this.a != null) {
      this.a.printStackTrace(paramPrintStream);
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.beans.filemonitor.FMException
 * JD-Core Version:    0.7.0.1
 */