package com.ffusion.csil.beans.entitlements;

import com.ffusion.util.FilteredList;
import java.util.Iterator;

public class EntitlementGroups
  extends FilteredList
{
  public EntitlementGroup getGroup(int paramInt)
  {
    return (EntitlementGroup)get(paramInt);
  }
  
  public EntitlementGroup getGroupByName(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      EntitlementGroup localEntitlementGroup = (EntitlementGroup)localIterator.next();
      if (localEntitlementGroup.getGroupName().equals(paramString))
      {
        localObject = localEntitlementGroup;
        break;
      }
    }
    return localObject;
  }
  
  public EntitlementGroup getGroupById(String paramString)
  {
    int i = 0;
    try
    {
      i = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
    return getGroupById(i);
  }
  
  public EntitlementGroup getGroupById(int paramInt)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      EntitlementGroup localEntitlementGroup = (EntitlementGroup)localIterator.next();
      if (localEntitlementGroup.getGroupId() == paramInt)
      {
        localObject = localEntitlementGroup;
        break;
      }
    }
    return localObject;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.csil.beans.entitlements.EntitlementGroups
 * JD-Core Version:    0.7.0.1
 */