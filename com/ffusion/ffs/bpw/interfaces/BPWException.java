package com.ffusion.ffs.bpw.interfaces;

import com.ffusion.ffs.interfaces.FFSException;

public class BPWException
  extends FFSException
{
  private int jdField_int = -1;
  private int jdField_new = -1;
  
  public BPWException()
  {
    super("BPW general error.");
  }
  
  public BPWException(String paramString)
  {
    super(paramString);
  }
  
  public BPWException(String paramString, int paramInt)
  {
    super(paramString);
    this.jdField_int = paramInt;
  }
  
  public BPWException(String paramString, int paramInt1, int paramInt2)
  {
    super(paramString);
    this.jdField_int = paramInt1;
    this.jdField_new = paramInt2;
  }
  
  public BPWException(Throwable paramThrowable, String paramString)
  {
    super(paramThrowable, paramString);
  }
  
  public String getExceptionMsg()
  {
    return getMessage();
  }
  
  public int getErrorCode()
  {
    return this.jdField_int;
  }
  
  public int getStatus()
  {
    return this.jdField_new;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.BPWException
 * JD-Core Version:    0.7.0.1
 */