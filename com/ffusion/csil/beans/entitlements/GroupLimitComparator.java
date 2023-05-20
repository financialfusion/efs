package com.ffusion.csil.beans.entitlements;

import java.math.BigDecimal;
import java.util.Comparator;

public class GroupLimitComparator
  implements Comparator
{
  public int compare(Object paramObject1, Object paramObject2)
  {
    Limit localLimit1 = (Limit)paramObject1;
    Limit localLimit2 = (Limit)paramObject2;
    if ((localLimit1 == null) && (localLimit2 == null)) {
      return 0;
    }
    if (localLimit1 == null) {
      return -1;
    }
    if (localLimit2 == null) {
      return 1;
    }
    int i = a(localLimit1.getObjectType(), localLimit2.getObjectType());
    if (i != 0) {
      return i;
    }
    i = a(localLimit1.getObjectId(), localLimit2.getObjectId());
    if (i != 0) {
      return i;
    }
    i = a(localLimit1.getLimitName(), localLimit2.getLimitName());
    if (i != 0) {
      return i;
    }
    if (localLimit1.getPeriod() < localLimit2.getPeriod()) {
      return -1;
    }
    if (localLimit1.getPeriod() > localLimit2.getPeriod()) {
      return 1;
    }
    return localLimit1.getAmount().compareTo(localLimit2.getAmount());
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
 * Qualified Name:     com.ffusion.csil.beans.entitlements.GroupLimitComparator
 * JD-Core Version:    0.7.0.1
 */