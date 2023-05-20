package com.ffusion.util;

import java.util.HashMap;

public class MapUtil
{
  public static String getStringValue(HashMap paramHashMap, String paramString1, String paramString2)
  {
    if (paramHashMap != null)
    {
      String str = (String)paramHashMap.get(paramString1);
      if (str != null) {
        return str;
      }
    }
    return paramString2;
  }
  
  public static int getIntValue(HashMap paramHashMap, String paramString, int paramInt)
  {
    int i = paramInt;
    if (paramHashMap != null)
    {
      Object localObject1 = paramHashMap.get(paramString);
      Object localObject2;
      if ((localObject1 instanceof Integer))
      {
        localObject2 = (Integer)localObject1;
        if (localObject2 != null) {
          i = ((Integer)localObject2).intValue();
        }
      }
      else
      {
        localObject2 = (String)localObject1;
        if (localObject2 != null) {
          try
          {
            i = Integer.parseInt((String)localObject2);
          }
          catch (NumberFormatException localNumberFormatException) {}
        }
      }
    }
    return i;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.MapUtil
 * JD-Core Version:    0.7.0.1
 */