package com.ffusion.services.bptw;

import java.io.PrintStream;
import java.io.PrintWriter;

public class BPTWException
  extends Exception
{
  public long when = 0L;
  public String where = null;
  public String why = null;
  public Throwable childException = null;
  public int serviceError = 0;
  private int a;
  public static final int ERROR_NOT_SUPPORTED = 3200;
  
  public int getCode()
  {
    return this.a;
  }
  
  public void setCode(int paramInt)
  {
    this.a = paramInt;
  }
  
  public BPTWException(String paramString1, int paramInt, String paramString2)
  {
    this.where = paramString1;
    this.when = System.currentTimeMillis();
    this.why = paramString2;
    this.a = paramInt;
  }
  
  public BPTWException(String paramString, int paramInt)
  {
    this.where = paramString;
    this.when = System.currentTimeMillis();
    this.a = paramInt;
  }
  
  public BPTWException(int paramInt)
  {
    this.when = System.currentTimeMillis();
    this.a = paramInt;
  }
  
  public BPTWException(int paramInt1, int paramInt2)
  {
    this.when = System.currentTimeMillis();
    this.a = paramInt1;
    this.serviceError = paramInt2;
  }
  
  public BPTWException(int paramInt, Exception paramException)
  {
    this.when = System.currentTimeMillis();
    this.a = paramInt;
    this.childException = paramException;
  }
  
  public void setServiceError(int paramInt)
  {
    this.serviceError = paramInt;
  }
  
  public int getServiceError()
  {
    return this.serviceError;
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
    if (this.a != 0) {
      localStringBuffer.append(": error code:" + String.valueOf(this.a) + " ");
    }
    if (this.childException != null) {
      localStringBuffer.append(this.childException.getMessage());
    }
    return localStringBuffer.toString();
  }
  
  public BPTWException(int paramInt, String paramString)
  {
    super(paramString);
    this.a = paramInt;
  }
  
  public BPTWException(int paramInt, String paramString, Throwable paramThrowable)
  {
    super(paramString);
    this.a = paramInt;
    this.childException = paramThrowable;
  }
  
  public BPTWException(int paramInt, Throwable paramThrowable)
  {
    this.a = paramInt;
    this.childException = paramThrowable;
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
  
  public Throwable getChained()
  {
    return this.childException;
  }
  
  public int getErrorCode()
  {
    return this.a;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.bptw.BPTWException
 * JD-Core Version:    0.7.0.1
 */