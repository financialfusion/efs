package com.ffusion.beans.util;

import java.util.ArrayList;

public class StringList
  extends ArrayList
{
  private int jdField_if;
  private String a;
  
  public void setAdd(String paramString)
  {
    add(paramString);
  }
  
  public void setValue(String paramString)
  {
    if (size() != 0) {
      remove(this.jdField_if);
    }
    add(this.jdField_if, paramString);
  }
  
  public void setCurrent(String paramString)
  {
    try
    {
      this.jdField_if = Integer.parseInt(paramString);
    }
    catch (Exception localException)
    {
      this.jdField_if = 0;
    }
    if (this.jdField_if >= size()) {
      this.jdField_if = (size() - 1);
    }
    if (this.jdField_if < 0) {
      this.jdField_if = 0;
    }
  }
  
  public void setCheckValue(String paramString)
  {
    this.a = paramString;
  }
  
  public String getCheckValue()
  {
    return this.a;
  }
  
  public boolean getContainsValue()
  {
    return contains(this.a);
  }
  
  public String getValue()
  {
    return (String)get(this.jdField_if);
  }
  
  public String getSize()
  {
    return String.valueOf(size());
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.util.StringList
 * JD-Core Version:    0.7.0.1
 */