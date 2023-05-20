package com.ffusion.csil.beans.entitlements;

import com.ffusion.util.FilteredList;
import java.util.Iterator;

public class LimitTypePropertyLists
  extends FilteredList
{
  private String Z;
  
  public LimitTypePropertyList getByOperationName(String paramString)
  {
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      LimitTypePropertyList localLimitTypePropertyList = (LimitTypePropertyList)localIterator.next();
      if (localLimitTypePropertyList.getOperationName().equals(paramString)) {
        return localLimitTypePropertyList;
      }
    }
    return null;
  }
  
  public LimitTypePropertyList getByOperationName()
  {
    if (this.Z == null) {
      return null;
    }
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      LimitTypePropertyList localLimitTypePropertyList = (LimitTypePropertyList)localIterator.next();
      if (localLimitTypePropertyList.getOperationName().equals(this.Z)) {
        return localLimitTypePropertyList;
      }
    }
    return null;
  }
  
  public void setOperationName(String paramString)
  {
    this.Z = paramString;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.csil.beans.entitlements.LimitTypePropertyLists
 * JD-Core Version:    0.7.0.1
 */