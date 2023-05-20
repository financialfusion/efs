package com.ffusion.csil.beans.entitlements;

import java.util.Comparator;

public class GroupEntitlementComparator
  implements Comparator
{
  public int compare(Object paramObject1, Object paramObject2)
  {
    Entitlement localEntitlement1 = (Entitlement)paramObject1;
    Entitlement localEntitlement2 = (Entitlement)paramObject2;
    if ((localEntitlement1 == null) && (localEntitlement2 == null)) {
      return 0;
    }
    if (localEntitlement1 == null) {
      return -1;
    }
    if (localEntitlement2 == null) {
      return 1;
    }
    int i = a(localEntitlement1.getOperationName(), localEntitlement2.getOperationName());
    if (i != 0) {
      return i;
    }
    i = a(localEntitlement1.getObjectType(), localEntitlement2.getObjectType());
    if (i != 0) {
      return i;
    }
    return a(localEntitlement1.getObjectId(), localEntitlement2.getObjectId());
  }
  
  private int a(String paramString1, String paramString2)
  {
    if ((paramString1 == null) && (paramString2 == null)) {
      return 0;
    }
    if (paramString1 == null) {
      return -1;
    }
    if (paramString2 == null) {
      return 1;
    }
    return paramString1.compareTo(paramString2);
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.csil.beans.entitlements.GroupEntitlementComparator
 * JD-Core Version:    0.7.0.1
 */