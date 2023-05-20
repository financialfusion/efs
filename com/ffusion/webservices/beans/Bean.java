package com.ffusion.webservices.beans;

import java.io.Serializable;
import java.util.HashMap;

public abstract class Bean
  implements Serializable
{
  protected HashMap map = new HashMap();
  
  public Object put(Object paramObject1, Object paramObject2)
  {
    return this.map.put(paramObject1, paramObject2);
  }
  
  public Object get(Object paramObject)
  {
    return this.map.get(paramObject);
  }
  
  public HashMap getMap()
  {
    return this.map;
  }
  
  public void setMap(HashMap paramHashMap)
  {
    this.map.clear();
    this.map.putAll(paramHashMap);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.webservices.beans.Bean
 * JD-Core Version:    0.7.0.1
 */