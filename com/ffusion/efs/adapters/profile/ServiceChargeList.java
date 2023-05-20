package com.ffusion.efs.adapters.profile;

import java.util.HashMap;

public class ServiceChargeList
{
  private HashMap a = null;
  
  public HashMap getValues()
  {
    return this.a;
  }
  
  public void setValues(HashMap paramHashMap)
  {
    this.a = paramHashMap;
  }
  
  public String getServiceCodeByOperationName(HashMap paramHashMap, String paramString)
  {
    return (String)paramHashMap.get(paramString);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.adapters.profile.ServiceChargeList
 * JD-Core Version:    0.7.0.1
 */