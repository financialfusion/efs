package com.ffusion.util;

public abstract interface Stringable
{
  public abstract void setString(String paramString);
  
  public abstract void fromString(String paramString)
    throws Exception;
  
  public abstract String getString();
  
  public abstract String toString();
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.Stringable
 * JD-Core Version:    0.7.0.1
 */