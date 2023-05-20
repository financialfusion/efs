package com.ffusion.ffs.bpw.interfaces;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.rmi.RemoteException;

public class ACHException
  extends RemoteException
{
  private Throwable a;
  private int jdField_if;
  
  public ACHException(int paramInt)
  {
    this.jdField_if = paramInt;
  }
  
  public ACHException(int paramInt, String paramString)
  {
    super(paramString);
    this.jdField_if = paramInt;
  }
  
  public ACHException(int paramInt, String paramString, Throwable paramThrowable)
  {
    super(paramString);
    this.jdField_if = paramInt;
    this.a = paramThrowable;
  }
  
  public ACHException(int paramInt, Throwable paramThrowable)
  {
    this.jdField_if = paramInt;
    this.a = paramThrowable;
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
  
  public Throwable getChildException()
  {
    return this.a;
  }
  
  public int getErrorCode()
  {
    return this.jdField_if;
  }
  
  public String getMessage()
  {
    if ((super.getMessage() == null) && (this.a != null)) {
      return this.a.getMessage();
    }
    return super.getMessage();
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.ACHException
 * JD-Core Version:    0.7.0.1
 */