package com.ffusion.beans.accountgroups;

import com.ffusion.util.FilteredList;
import java.util.Iterator;

public class BusinessAccountGroups
  extends FilteredList
{
  public BusinessAccountGroup getByID(int paramInt)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      BusinessAccountGroup localBusinessAccountGroup = (BusinessAccountGroup)localIterator.next();
      if (localBusinessAccountGroup.getId() == paramInt)
      {
        localObject = localBusinessAccountGroup;
        break;
      }
    }
    return localObject;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.accountgroups.BusinessAccountGroups
 * JD-Core Version:    0.7.0.1
 */