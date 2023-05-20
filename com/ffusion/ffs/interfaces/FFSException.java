package com.ffusion.ffs.interfaces;

import java.io.PrintStream;

public class FFSException
  extends Exception
{
  private static final long serialVersionUID = 12345678910L;
  private int a = -1;
  private String jdField_for = null;
  private FFSException jdField_do = null;
  private transient Throwable jdField_if = null;
  
  public FFSException(String paramString)
  {
    super(paramString);
  }
  
  public FFSException(int paramInt, String paramString)
  {
    super(paramString);
    this.a = paramInt;
    this.jdField_for = null;
  }
  
  public FFSException(int paramInt, String paramString1, String paramString2)
  {
    super(paramString1);
    this.a = paramInt;
    this.jdField_for = paramString2;
  }
  
  public FFSException(Throwable paramThrowable, String paramString)
  {
    super(paramString);
    this.jdField_if = paramThrowable;
  }
  
  public FFSException(Throwable paramThrowable, int paramInt, String paramString)
  {
    super(paramString);
    this.jdField_if = paramThrowable;
    this.a = paramInt;
  }
  
  public FFSException(Throwable paramThrowable, String paramString1, String paramString2)
  {
    super(paramString1);
    this.jdField_if = paramThrowable;
    this.jdField_for = paramString2;
  }
  
  public String getErrType()
  {
    return this.jdField_for;
  }
  
  public int getErrCode()
  {
    return this.a;
  }
  
  public FFSException getNextException()
  {
    return this.jdField_do;
  }
  
  public void setNextException(FFSException paramFFSException)
  {
    if (this.jdField_do != null)
    {
      this.jdField_do.setNextException(paramFFSException);
    }
    else
    {
      if (paramFFSException == this) {
        throw new RuntimeException("Exception chained to itself!");
      }
      this.jdField_do = paramFFSException;
    }
  }
  
  public String getMessage()
  {
    String str1 = super.getMessage();
    String str2;
    if (this.jdField_if != null)
    {
      str2 = this.jdField_if.getMessage();
      if ((str2 != null) && (!str2.equals("null"))) {
        str1 = str1 + ":\n\t" + this.jdField_if.getMessage();
      }
    }
    if (this.jdField_do != null)
    {
      str2 = this.jdField_do.getMessage();
      if ((str2 != null) && (!str2.equals("null"))) {
        str1 = str1 + ", \n\t" + str2 + "\n";
      }
    }
    System.out.println(str1);
    return str1;
  }
  
  public Throwable getNestedException()
  {
    return this.jdField_if;
  }
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.interfaces.FFSException
 * JD-Core Version:    0.7.0.1
 */