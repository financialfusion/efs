package com.ffusion.util;

import java.io.PrintStream;
import java.io.PrintWriter;

public class FFIUtilException
  extends Exception
{
  public static final int UNKNOWN = 0;
  private int jdField_if = 0;
  private Throwable a = null;
  
  public FFIUtilException() {}
  
  public FFIUtilException(Throwable paramThrowable)
  {
    this.a = paramThrowable;
  }
  
  public FFIUtilException(int paramInt)
  {
    this.jdField_if = paramInt;
  }
  
  public FFIUtilException(int paramInt, Throwable paramThrowable)
  {
    this.jdField_if = paramInt;
    this.a = paramThrowable;
  }
  
  public void printStackTrace()
  {
    printStackTrace(System.err);
  }
  
  public void printStackTrace(PrintStream paramPrintStream)
  {
    this.a.printStackTrace(paramPrintStream);
    super.printStackTrace(paramPrintStream);
  }
  
  public void printStackTrace(PrintWriter paramPrintWriter)
  {
    this.a.printStackTrace(paramPrintWriter);
    super.printStackTrace(paramPrintWriter);
  }
  
  public Throwable getCause()
  {
    return this.a;
  }
  
  public void setCause(Throwable paramThrowable)
  {
    this.a = paramThrowable;
  }
  
  public int getCode()
  {
    return this.jdField_if;
  }
  
  public void setCode(int paramInt)
  {
    this.jdField_if = paramInt;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.FFIUtilException
 * JD-Core Version:    0.7.0.1
 */