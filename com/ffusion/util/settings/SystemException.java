package com.ffusion.util.settings;

import java.io.PrintStream;
import java.io.PrintWriter;

public class SystemException
  extends Exception
{
  private Throwable a = null;
  private String jdField_if = null;
  
  public SystemException(String paramString, Throwable paramThrowable)
  {
    this.jdField_if = paramString;
    this.a = paramThrowable;
  }
  
  public SystemException(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public SystemException(Throwable paramThrowable)
  {
    this.a = paramThrowable;
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
 * Qualified Name:     com.ffusion.util.settings.SystemException
 * JD-Core Version:    0.7.0.1
 */