package com.ffusion.beans.util;

import java.io.Serializable;

public class MapEntry
  implements Serializable
{
  private Object jdField_if;
  private Object a;
  
  public MapEntry(Object paramObject)
  {
    this.jdField_if = paramObject;
  }
  
  public MapEntry(Object paramObject1, Object paramObject2)
  {
    this.jdField_if = paramObject1;
    this.a = paramObject2;
  }
  
  public Object getKey()
  {
    return this.jdField_if;
  }
  
  public Object getValue()
  {
    return this.a;
  }
  
  public void setValue(Object paramObject)
  {
    this.a = paramObject;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.util.MapEntry
 * JD-Core Version:    0.7.0.1
 */