package com.ffusion.services.aggregation;

import java.io.PrintStream;
import java.io.PrintWriter;

public class AggregationException
  extends Exception
{
  public static final int SERVICE_ERROR_UNIMPLEMENTED = 11005;
  private int jdField_if = 0;
  private Throwable a = null;
  
  public AggregationException() {}
  
  public AggregationException(int paramInt)
  {
    this.jdField_if = paramInt;
  }
  
  public AggregationException(int paramInt, Throwable paramThrowable)
  {
    this.jdField_if = paramInt;
    this.a = paramThrowable;
  }
  
  public void printStackTrace(PrintWriter paramPrintWriter)
  {
    if (this.a != null) {
      this.a.printStackTrace(paramPrintWriter);
    }
    super.printStackTrace(paramPrintWriter);
  }
  
  public void printStackTrace(PrintStream paramPrintStream)
  {
    if (this.a != null) {
      this.a.printStackTrace(paramPrintStream);
    }
    super.printStackTrace(paramPrintStream);
  }
  
  public void printStackTrace()
  {
    printStackTrace(System.err);
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


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.aggregation.AggregationException
 * JD-Core Version:    0.7.0.1
 */