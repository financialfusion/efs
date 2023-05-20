package com.ffusion.ffs.bpw.interfaces;

import java.io.Serializable;

public class ValueInfo
  implements Serializable
{
  protected Object jdField_if = null;
  protected String a = null;
  
  public String getAction()
  {
    return this.a;
  }
  
  public void setAction(String paramString)
  {
    this.a = paramString;
  }
  
  public Object getValue()
  {
    return this.jdField_if;
  }
  
  public void setValue(Object paramObject)
  {
    this.jdField_if = paramObject;
  }
  
  public String toString()
  {
    return this.jdField_if.toString();
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.ValueInfo
 * JD-Core Version:    0.7.0.1
 */