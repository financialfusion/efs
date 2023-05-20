package com.ffusion.util.smartcalendar;

import java.io.PrintStream;
import java.io.PrintWriter;

public class SCException
  extends Exception
{
  private int jdField_do = -1;
  private Throwable a = null;
  private String jdField_if = null;
  public static final int UNKNOWN_ERROR = 0;
  public static final int INITIALIZATION_FAILED = 1;
  public static final int NOT_INITIALIZED = 2;
  public static final int CACHE_ERROR = 3;
  public static final int DB_EXCEPTION = 10;
  public static final int CONNECTION_POOL_EXCEPTION = 11;
  public static final int INVALID_CALENDAR_ID = 20;
  public static final int INVALID_CALENDAR_ID_FOR_SERVICE_BUREAU = 21;
  public static final int CANNOT_RESOLVE_CALENDAR = 22;
  public static final int DUPLICATE_CALENDAR_NAME = 23;
  public static final int CALENDAR_IN_USE = 24;
  public static final int CALENDAR_LOOP_DETECTED = 25;
  public static final int INVALID_ACTION = 26;
  
  public SCException(int paramInt, String paramString, Throwable paramThrowable)
  {
    this.jdField_do = paramInt;
    this.jdField_if = paramString;
    this.a = paramThrowable;
  }
  
  public SCException(String paramString, Throwable paramThrowable)
  {
    this.jdField_if = paramString;
    this.a = paramThrowable;
  }
  
  public SCException(int paramInt, String paramString)
  {
    this.jdField_do = paramInt;
    this.jdField_if = paramString;
  }
  
  public SCException(int paramInt, Throwable paramThrowable)
  {
    this.jdField_do = paramInt;
    this.a = paramThrowable;
  }
  
  public int getErrorCode()
  {
    return this.jdField_do;
  }
  
  public String getMessage()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.jdField_if != null) {
      localStringBuffer.append(this.jdField_if).append(" ");
    }
    if (this.a != null) {
      localStringBuffer.append(this.a.getMessage());
    }
    return localStringBuffer.toString();
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
 * Qualified Name:     com.ffusion.util.smartcalendar.SCException
 * JD-Core Version:    0.7.0.1
 */