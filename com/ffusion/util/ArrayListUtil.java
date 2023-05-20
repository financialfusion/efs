package com.ffusion.util;

import java.util.ArrayList;

public class ArrayListUtil
{
  public static Object getValueFromList(ArrayList paramArrayList, int paramInt, Object paramObject)
  {
    Object localObject1 = paramObject;
    try
    {
      Object localObject2 = null;
      if ((paramInt < 0) || (paramInt >= paramArrayList.size()))
      {
        localObject2 = paramArrayList.get(paramInt);
        if (localObject2 != null) {
          localObject1 = localObject2;
        }
      }
    }
    catch (IndexOutOfBoundsException localIndexOutOfBoundsException) {}
    return localObject1;
  }
  
  public static void setValueInList(ArrayList paramArrayList, int paramInt, Object paramObject1, Object paramObject2)
  {
    int i = paramArrayList.size();
    if (paramInt >= i) {
      for (int j = 0; j <= paramInt - i; j++) {
        paramArrayList.add(paramObject2);
      }
    }
    paramArrayList.set(paramInt, paramObject1);
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.ArrayListUtil
 * JD-Core Version:    0.7.0.1
 */