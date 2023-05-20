package com.ffusion.csil.beans.entitlements;

import com.ffusion.util.FilteredList;
import java.util.Iterator;

public class ObjectTypePropertyLists
  extends FilteredList
{
  public ObjectTypePropertyList getByObjectType(String paramString)
  {
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      ObjectTypePropertyList localObjectTypePropertyList = (ObjectTypePropertyList)localIterator.next();
      if (localObjectTypePropertyList.getObjectType().equals(paramString)) {
        return localObjectTypePropertyList;
      }
    }
    return null;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.csil.beans.entitlements.ObjectTypePropertyLists
 * JD-Core Version:    0.7.0.1
 */