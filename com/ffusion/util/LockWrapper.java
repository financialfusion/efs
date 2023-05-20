package com.ffusion.util;

class LockWrapper
{
  StackTraceElement[] jdField_for;
  Thread jdField_do;
  long a;
  String jdField_if;
  
  public LockWrapper(String paramString)
  {
    this.jdField_if = paramString;
    this.jdField_do = Thread.currentThread();
    Exception localException = new Exception();
    this.jdField_for = localException.getStackTrace();
    this.a = System.currentTimeMillis();
  }
  
  public int hashCode()
  {
    return this.jdField_do.hashCode();
  }
  
  public boolean equals(Object paramObject)
  {
    return ((LockWrapper)paramObject).jdField_do.equals(this.jdField_do);
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("Lock Type: ").append(this.jdField_if).append("\n");
    for (int i = 2; i < this.jdField_for.length; i++) {
      localStringBuffer.append("\tat ").append(this.jdField_for[i]).append("\n");
    }
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.LockWrapper
 * JD-Core Version:    0.7.0.1
 */