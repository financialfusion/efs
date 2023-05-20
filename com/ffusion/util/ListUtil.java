package com.ffusion.util;

import java.util.List;

public class ListUtil
{
  public static List toList(Object[] paramArrayOfObject, Class paramClass)
    throws IllegalAccessException, InstantiationException
  {
    List localList = null;
    Object localObject = paramClass.newInstance();
    localList = (List)localObject;
    for (int i = 0; i < paramArrayOfObject.length; i++) {
      localList.add(paramArrayOfObject[i]);
    }
    return localList;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.ListUtil
 * JD-Core Version:    0.7.0.1
 */