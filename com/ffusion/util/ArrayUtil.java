package com.ffusion.util;

import java.lang.reflect.Array;

public class ArrayUtil
{
  public static Object[] convertReferences(Object[] paramArrayOfObject, Class paramClass)
  {
    if (paramClass.isInstance(paramArrayOfObject)) {
      return paramArrayOfObject;
    }
    if (paramArrayOfObject != null)
    {
      Class localClass = paramClass.getComponentType();
      Object localObject = Array.newInstance(localClass, paramArrayOfObject.length);
      for (int i = 0; i < paramArrayOfObject.length; i++)
      {
        if (!localClass.isInstance(paramArrayOfObject[i])) {
          throw new ClassCastException();
        }
        Array.set(localObject, i, paramArrayOfObject[i]);
      }
      paramArrayOfObject = (Object[])localObject;
    }
    return paramArrayOfObject;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.ArrayUtil
 * JD-Core Version:    0.7.0.1
 */