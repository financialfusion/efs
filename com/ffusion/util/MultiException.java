package com.ffusion.util;

import java.io.PrintStream;
import java.io.PrintWriter;

public class MultiException
  extends Exception
{
  public long when = 0L;
  public String where = null;
  public String why = null;
  public int code = -1;
  public Exception childException = null;
  
  public int getCode()
  {
    return this.code;
  }
  
  public void setCode(int paramInt)
  {
    this.code = paramInt;
  }
  
  public MultiException(String paramString1, int paramInt, String paramString2)
  {
    this.where = paramString1;
    this.when = System.currentTimeMillis();
    this.why = paramString2;
    this.code = paramInt;
  }
  
  public MultiException(String paramString, int paramInt)
  {
    this.where = paramString;
    this.when = System.currentTimeMillis();
    this.code = paramInt;
  }
  
  public MultiException(String paramString, int paramInt, Exception paramException)
  {
    this.where = paramString;
    this.when = System.currentTimeMillis();
    this.code = paramInt;
    this.childException = paramException;
  }
  
  public MultiException(int paramInt)
  {
    this.when = System.currentTimeMillis();
    this.code = paramInt;
  }
  
  public MultiException(int paramInt, Exception paramException)
  {
    this.when = System.currentTimeMillis();
    this.code = paramInt;
    this.childException = paramException;
  }
  
  public String getMessage()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if ((this.where != null) && (this.where.length() > 0)) {
      localStringBuffer.append(this.where);
    }
    if ((this.why != null) && (this.why.length() > 0)) {
      localStringBuffer.append(": ").append(this.why);
    }
    if (this.code != 0) {
      localStringBuffer.append(": error code:" + String.valueOf(this.code) + " ");
    }
    if (this.childException != null) {
      localStringBuffer.append(this.childException.getMessage());
    }
    return localStringBuffer.toString();
  }
  
  public void printStackTrace()
  {
    super.printStackTrace();
    if (this.childException != null) {
      this.childException.printStackTrace();
    }
  }
  
  public void printStackTrace(PrintWriter paramPrintWriter)
  {
    super.printStackTrace(paramPrintWriter);
    if (this.childException != null) {
      this.childException.printStackTrace(paramPrintWriter);
    }
  }
  
  public void printStackTrace(PrintStream paramPrintStream)
  {
    super.printStackTrace(paramPrintStream);
    if (this.childException != null) {
      this.childException.printStackTrace(paramPrintStream);
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.MultiException
 * JD-Core Version:    0.7.0.1
 */