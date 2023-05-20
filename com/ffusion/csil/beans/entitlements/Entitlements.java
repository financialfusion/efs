package com.ffusion.csil.beans.entitlements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Entitlements
  extends ArrayList
{
  public Entitlements() {}
  
  public Entitlements(Collection paramCollection)
  {
    super(paramCollection);
  }
  
  public Entitlement getEntitlement(int paramInt)
  {
    return (Entitlement)get(paramInt);
  }
  
  public boolean equals(Entitlements paramEntitlements)
  {
    Iterator localIterator1 = iterator();
    Iterator localIterator2 = paramEntitlements.iterator();
    if (size() != paramEntitlements.size()) {
      return false;
    }
    while (localIterator1.hasNext())
    {
      Entitlement localEntitlement = (Entitlement)localIterator1.next();
      if (!paramEntitlements.contains(localEntitlement)) {
        return false;
      }
    }
    return true;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.csil.beans.entitlements.Entitlements
 * JD-Core Version:    0.7.0.1
 */