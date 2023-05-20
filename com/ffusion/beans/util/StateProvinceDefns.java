package com.ffusion.beans.util;

import com.ffusion.util.FilteredList;
import java.util.Iterator;

public class StateProvinceDefns
  extends FilteredList
{
  public String toString()
  {
    String str = System.getProperty("line.separator");
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("StateProvinceDefns Bean:" + str);
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      StateProvinceDefn localStateProvinceDefn = (StateProvinceDefn)localIterator.next();
      localStringBuffer.append(localStateProvinceDefn.toString());
    }
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.util.StateProvinceDefns
 * JD-Core Version:    0.7.0.1
 */