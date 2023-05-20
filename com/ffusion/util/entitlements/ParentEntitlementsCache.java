package com.ffusion.util.entitlements;

import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.beans.entitlements.MultiEntitlement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ParentEntitlementsCache
{
  private static HashMap a = new HashMap();
  
  public static void initialize(EntitlementTypePropertyLists paramEntitlementTypePropertyLists)
  {
    synchronized (a)
    {
      Iterator localIterator = paramEntitlementTypePropertyLists.iterator();
      while (localIterator.hasNext())
      {
        EntitlementTypePropertyList localEntitlementTypePropertyList = (EntitlementTypePropertyList)localIterator.next();
        ArrayList localArrayList1 = new ArrayList();
        ArrayList localArrayList2 = new ArrayList();
        String str1 = localEntitlementTypePropertyList.getOperationName();
        if (!a.containsKey(str1))
        {
          a(str1, paramEntitlementTypePropertyLists, localArrayList1, localArrayList2);
          a.put(str1, localArrayList1);
          if (localEntitlementTypePropertyList.isPropertySet("admin partner"))
          {
            String str2 = localEntitlementTypePropertyList.getPropertyValue("admin partner", 0);
            a.put(str2, localArrayList2);
          }
        }
      }
    }
  }
  
  private static void a(String paramString, EntitlementTypePropertyLists paramEntitlementTypePropertyLists, ArrayList paramArrayList1, ArrayList paramArrayList2)
  {
    Iterator localIterator = paramEntitlementTypePropertyLists.iterator();
    while (localIterator.hasNext())
    {
      EntitlementTypePropertyList localEntitlementTypePropertyList1 = (EntitlementTypePropertyList)localIterator.next();
      if ((localEntitlementTypePropertyList1.getOperationName().equals(paramString)) && (localEntitlementTypePropertyList1.isPropertySet("control parent")))
      {
        int i = localEntitlementTypePropertyList1.numPropertyValues("control parent");
        for (int j = 0; j < i; j++)
        {
          String str1 = localEntitlementTypePropertyList1.getPropertyValue("control parent", j);
          paramArrayList1.add(str1);
          EntitlementTypePropertyList localEntitlementTypePropertyList2 = paramEntitlementTypePropertyLists.getByOperationName(str1);
          if (localEntitlementTypePropertyList2.isPropertySet("admin partner"))
          {
            String str2 = localEntitlementTypePropertyList2.getPropertyValue("admin partner", 0);
            paramArrayList2.add(str2);
          }
          a(str1, paramEntitlementTypePropertyLists, paramArrayList1, paramArrayList2);
        }
      }
    }
  }
  
  public static MultiEntitlement appendParentEntitlements(MultiEntitlement paramMultiEntitlement)
  {
    String[] arrayOfString1 = paramMultiEntitlement.getOperations();
    if (arrayOfString1 != null)
    {
      int i = arrayOfString1.length;
      ArrayList localArrayList = new ArrayList();
      for (int j = 0; j < i; j++) {
        if ((arrayOfString1[j] != null) && (a.get(arrayOfString1[j]) != null)) {
          localArrayList.addAll((ArrayList)a.get(arrayOfString1[j]));
        }
      }
      j = localArrayList.size();
      String[] arrayOfString2 = new String[i + j];
      int k = 0;
      for (int m = 0; m < i; m++)
      {
        arrayOfString2[k] = arrayOfString1[m];
        k++;
      }
      for (m = 0; m < j; m++)
      {
        arrayOfString2[k] = ((String)localArrayList.get(m));
        k++;
      }
      paramMultiEntitlement.setOperations(arrayOfString2);
    }
    return paramMultiEntitlement;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.entitlements.ParentEntitlementsCache
 * JD-Core Version:    0.7.0.1
 */