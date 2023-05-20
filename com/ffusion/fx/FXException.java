package com.ffusion.fx;

import java.io.PrintStream;
import java.io.PrintWriter;

public class FXException
  extends Exception
{
  private int jdField_do = -1;
  private Throwable a = null;
  private String jdField_if = null;
  
  public FXException(int paramInt, String paramString, Throwable paramThrowable)
  {
    this.jdField_do = paramInt;
    this.jdField_if = paramString;
    this.a = paramThrowable;
  }
  
  public FXException(String paramString, Throwable paramThrowable)
  {
    this.jdField_if = paramString;
    this.a = paramThrowable;
  }
  
  public FXException(int paramInt, String paramString)
  {
    this.jdField_do = paramInt;
    this.jdField_if = paramString;
  }
  
  public FXException(int paramInt, Throwable paramThrowable)
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
  
  private String a()
  {
    String str = null;
    switch (this.jdField_do)
    {
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.fx.FXException
 * JD-Core Version:    0.7.0.1
 */