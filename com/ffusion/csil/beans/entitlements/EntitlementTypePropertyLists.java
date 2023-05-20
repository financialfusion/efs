package com.ffusion.csil.beans.entitlements;

import com.ffusion.util.FilteredList;
import java.util.Iterator;

public class EntitlementTypePropertyLists
  extends FilteredList
{
  public EntitlementTypePropertyList getByOperationName(String paramString)
  {
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      EntitlementTypePropertyList localEntitlementTypePropertyList = (EntitlementTypePropertyList)localIterator.next();
      if (localEntitlementTypePropertyList.getOperationName().equals(paramString)) {
        return localEntitlementTypePropertyList;
      }
    }
    return null;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists
 * JD-Core Version:    0.7.0.1
 */