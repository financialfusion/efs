package com.ffusion.util;

import java.util.ArrayList;

public class Qsort
{
  public static final int ASCENDING_ORDER = 1;
  public static final int DESCENDING_ORDER = -1;
  
  public static void sortStrings(ArrayList paramArrayList, int paramInt)
  {
    a(paramArrayList, 0, paramArrayList.size() - 1, null, paramInt, false);
  }
  
  public static void sortStringsIgnoreCase(ArrayList paramArrayList, int paramInt)
  {
    a(paramArrayList, 0, paramArrayList.size() - 1, null, paramInt, true);
  }
  
  public static void sortSortables(ArrayList paramArrayList, String paramString, int paramInt, boolean paramBoolean)
  {
    String[] arrayOfString = new String[1];
    arrayOfString[0] = new String(paramString);
    a(paramArrayList, 0, paramArrayList.size() - 1, arrayOfString, paramInt, paramBoolean);
  }
  
  public static void sortSortables(ArrayList paramArrayList, String paramString, int paramInt)
  {
    String[] arrayOfString = new String[1];
    arrayOfString[0] = new String(paramString);
    a(paramArrayList, 0, paramArrayList.size() - 1, arrayOfString, paramInt, false);
  }
  
  public static void sortSortables(ArrayList paramArrayList, String[] paramArrayOfString, int paramInt, boolean paramBoolean)
  {
    a(paramArrayList, 0, paramArrayList.size() - 1, paramArrayOfString, paramInt, paramBoolean);
  }
  
  public static void sortSortables(ArrayList paramArrayList, String[] paramArrayOfString, int paramInt)
  {
    a(paramArrayList, 0, paramArrayList.size() - 1, paramArrayOfString, paramInt, false);
  }
  
  private static void a(ArrayList paramArrayList, int paramInt1, int paramInt2, String[] paramArrayOfString, int paramInt3, boolean paramBoolean)
  {
    int i = paramInt1;
    int j = paramInt2;
    if (i >= j) {
      return;
    }
    if (i == j - 1)
    {
      if (a(paramArrayList.get(i), paramArrayList.get(j), paramArrayOfString, paramInt3, paramBoolean) > 0)
      {
        Object localObject1 = paramArrayList.get(i);
        paramArrayList.set(i, paramArrayList.get(j));
        paramArrayList.set(j, localObject1);
      }
      return;
    }
    int k = (i + j) / 2;
    Object localObject2 = paramArrayList.set(k, paramArrayList.get(j));
    paramArrayList.set(j, localObject2);
    while (i < j)
    {
      while ((a(paramArrayList.get(i), localObject2, paramArrayOfString, paramInt3, paramBoolean) <= 0) && (i < j)) {
        i++;
      }
      while ((a(localObject2, paramArrayList.get(j), paramArrayOfString, paramInt3, paramBoolean) <= 0) && (i < j)) {
        j--;
      }
      if (i < j)
      {
        Object localObject3 = paramArrayList.get(i);
        paramArrayList.set(i, paramArrayList.get(j));
        paramArrayList.set(j, localObject3);
      }
    }
    paramArrayList.set(paramInt2, paramArrayList.get(j));
    paramArrayList.set(j, localObject2);
    a(paramArrayList, paramInt1, i - 1, paramArrayOfString, paramInt3, paramBoolean);
    a(paramArrayList, j + 1, paramInt2, paramArrayOfString, paramInt3, paramBoolean);
  }
  
  private static int a(Object paramObject1, Object paramObject2, String[] paramArrayOfString, int paramInt, boolean paramBoolean)
  {
    int i = 0;
    int j = 0;
    String str1;
    if (paramArrayOfString == null)
    {
      str1 = (String)paramObject1;
      String str2 = (String)paramObject2;
      if ((str1 == null) && (str2 == null)) {
        i = 0;
      } else if ((str1 != null) && (str2 == null)) {
        i = 1;
      } else if ((str1 == null) && (str2 != null)) {
        i = -1;
      } else if (!paramBoolean) {
        i = str1.compareTo(str2);
      } else {
        i = str1.compareToIgnoreCase(str2);
      }
    }
    else if (((paramObject1 instanceof Sortable)) && ((paramObject2 instanceof Sortable)))
    {
      while ((i == 0) && (j < paramArrayOfString.length))
      {
        str1 = paramArrayOfString[j];
        int k = str1.indexOf(" ASC");
        if (k > 0)
        {
          paramInt = 1;
          str1 = str1.substring(0, k);
        }
        k = str1.indexOf(" DESC");
        if (k > 0)
        {
          paramInt = -1;
          str1 = str1.substring(0, k);
        }
        i = ((Sortable)paramObject1).compare((Sortable)paramObject2, str1);
        j++;
      }
    }
    return i * paramInt;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.Qsort
 * JD-Core Version:    0.7.0.1
 */