package com.ffusion.ffs.util;

public class Property
{
  private String a = null;
  private String jdField_if = null;
  
  public Property() {}
  
  public Property(String paramString1, String paramString2)
  {
    this.a = paramString1;
    this.jdField_if = paramString2;
  }
  
  public String getName()
  {
    return this.a;
  }
  
  public String getValue()
  {
    return this.jdField_if;
  }
  
  public void setName(String paramString)
  {
    this.a = paramString;
  }
  
  public void setValue(String paramString)
  {
    this.jdField_if = paramString;
  }
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.util.Property
 * JD-Core Version:    0.7.0.1
 */