package com.ffusion.util;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reflection
{
  private static Map jdField_do = Collections.synchronizedMap(new HashMap());
  private static Map jdField_if = Collections.synchronizedMap(new HashMap());
  private static Class[] a = null;
  
  public static Object[] drillDownToObject(Object paramObject, String paramString1, String paramString2)
  {
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = paramObject;
    arrayOfObject[1] = paramString1;
    arrayOfObject[2] = paramString2;
    while (paramString1.indexOf(".") != -1)
    {
      int i = paramString1.indexOf(".");
      String str = paramString1.substring(i + 1);
      paramString1 = paramString1.substring(0, i);
      Method localMethod = jdField_if(jdField_do, paramObject, paramString1, "get", null);
      if (localMethod != null)
      {
        try
        {
          paramObject = localMethod.invoke(paramObject, null);
        }
        catch (Throwable localThrowable)
        {
          localThrowable.printStackTrace();
        }
      }
      else
      {
        if (!(paramObject instanceof List)) {
          break;
        }
        try
        {
          paramObject = ((List)paramObject).get(Integer.valueOf(paramString1).intValue());
        }
        catch (NumberFormatException localNumberFormatException) {}
      }
      paramString1 = str;
      if (str.length() > 0) {
        paramString1 = str.substring(0, 1).toUpperCase() + str.substring(1);
      }
      arrayOfObject[0] = paramObject;
      arrayOfObject[1] = paramString1;
      arrayOfObject[2] = str;
    }
    return arrayOfObject;
  }
  
  public static Method findSetMethod(Object paramObject, String paramString)
  {
    return jdField_if(jdField_if, paramObject, paramString, "set", a);
  }
  
  public static Method findGetMethod(Object paramObject, String paramString)
  {
    return jdField_if(jdField_do, paramObject, paramString, "get", null);
  }
  
  private static Method jdField_if(Map paramMap, Object paramObject, String paramString1, String paramString2, Class[] paramArrayOfClass)
  {
    Method localMethod = null;
    String str = null;
    Map localMap = null;
    if (paramObject != null)
    {
      str = paramObject.getClass().getName();
      localMap = (Map)paramMap.get(str);
      if (localMap != null)
      {
        Object localObject = localMap.get(paramString1);
        if ((localObject instanceof Method)) {
          localMethod = (Method)localObject;
        } else if (localObject == null) {
          localMethod = a(localMap, paramObject, paramString1, paramString2, paramArrayOfClass);
        }
      }
      else
      {
        localMap = Collections.synchronizedMap(new HashMap());
        paramMap.put(str, localMap);
        localMethod = a(localMap, paramObject, paramString1, paramString2, paramArrayOfClass);
      }
    }
    return localMethod;
  }
  
  private static Method a(Map paramMap, Object paramObject, String paramString1, String paramString2, Class[] paramArrayOfClass)
  {
    Method localMethod = null;
    String str = paramString2 + paramString1;
    try
    {
      localMethod = paramObject.getClass().getMethod(str, paramArrayOfClass);
      paramMap.put(paramString1, localMethod);
    }
    catch (Exception localException)
    {
      paramMap.put(paramString1, "x");
    }
    return localMethod;
  }
  
  static
  {
    a = new Class[1];
    try
    {
      a[0] = Class.forName("java.lang.String");
    }
    catch (Exception localException) {}
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.Reflection
 * JD-Core Version:    0.7.0.1
 */