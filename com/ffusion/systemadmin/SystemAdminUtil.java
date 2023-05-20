package com.ffusion.systemadmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class SystemAdminUtil
{
  public static HashMap getOverriddenDataRetentionSettings(ArrayList paramArrayList, HashMap paramHashMap)
    throws SAException
  {
    String str = "SystemAdminService.getOverrideDataRetentionSettings";
    HashMap localHashMap1 = new HashMap();
    try
    {
      Iterator localIterator = paramArrayList.iterator();
      while (localIterator.hasNext())
      {
        HashMap localHashMap2 = (HashMap)localIterator.next();
        localHashMap1.putAll(localHashMap2);
      }
    }
    catch (Exception localException)
    {
      throw new SAException(38204, "The specified settings list contained invalid data.");
    }
    return localHashMap1;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.systemadmin.SystemAdminUtil
 * JD-Core Version:    0.7.0.1
 */