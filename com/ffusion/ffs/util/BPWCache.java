package com.ffusion.ffs.util;

public class BPWCache
{
  private String jdField_do = null;
  private String a = null;
  private Object jdField_if = null;
  
  public BPWCache(String paramString)
  {
    this.jdField_do = paramString;
    this.a = null;
    this.jdField_if = null;
  }
  
  public BPWCache(String paramString1, String paramString2)
  {
    this.jdField_do = paramString1;
    this.a = paramString2;
    this.jdField_if = null;
  }
  
  public BPWCache(String paramString1, String paramString2, Object paramObject)
  {
    this.jdField_do = paramString1;
    this.a = paramString2;
    this.jdField_if = paramObject;
  }
  
  public String getCacheId()
  {
    return this.jdField_do;
  }
  
  public void setCacheId(String paramString)
  {
    this.jdField_do = paramString;
  }
  
  public String getKey()
  {
    return this.a;
  }
  
  public void setKey(String paramString)
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
  
  public void reset()
  {
    this.jdField_do = null;
    this.a = null;
    this.jdField_if = null;
  }
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.util.BPWCache
 * JD-Core Version:    0.7.0.1
 */