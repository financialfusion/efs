package com.ffusion.ffs.bpw.interfaces;

import java.io.Serializable;

public class SortCriterion
  implements Serializable
{
  protected String a;
  private boolean jdField_if = true;
  
  public String getName()
  {
    return this.a;
  }
  
  public void setName(String paramString)
  {
    this.a = paramString;
  }
  
  public boolean isAscending()
  {
    return this.jdField_if;
  }
  
  public void setAscending()
  {
    this.jdField_if = true;
  }
  
  public void setDescending()
  {
    this.jdField_if = false;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.SortCriterion
 * JD-Core Version:    0.7.0.1
 */