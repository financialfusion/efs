package com.ffusion.csil.beans.entitlements;

import com.ffusion.util.FilteredList;
import java.util.Iterator;

public class Limits
  extends FilteredList
  implements Cloneable
{
  public Object clone()
  {
    Limits localLimits = new Limits();
    for (int i = 0; i < size(); i++)
    {
      Limit localLimit = (Limit)get(i);
      localLimits.add(localLimit.clone());
    }
    return localLimits;
  }
  
  public Limit getLimitById(int paramInt)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Limit localLimit = (Limit)localIterator.next();
      if (localLimit.getLimitId() == paramInt)
      {
        localObject = localLimit;
        break;
      }
    }
    return localObject;
  }
  
  public boolean equals(Limits paramLimits)
  {
    Iterator localIterator1 = iterator();
    Iterator localIterator2 = paramLimits.iterator();
    if (size() != paramLimits.size()) {
      return false;
    }
    while (localIterator1.hasNext())
    {
      Limit localLimit = (Limit)localIterator1.next();
      if (!paramLimits.contains(localLimit)) {
        return false;
      }
    }
    return true;
  }
  
  public boolean remove(Object paramObject)
  {
    boolean bool = false;
    if ((paramObject != null) && ((paramObject instanceof Limit)))
    {
      Limit localLimit1 = (Limit)paramObject;
      Iterator localIterator = iterator();
      while (localIterator.hasNext())
      {
        Limit localLimit2 = (Limit)localIterator.next();
        if (localLimit1.equals(localLimit2))
        {
          localIterator.remove();
          bool = true;
          break;
        }
      }
    }
    return bool;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.csil.beans.entitlements.Limits
 * JD-Core Version:    0.7.0.1
 */