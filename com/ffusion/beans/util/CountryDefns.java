package com.ffusion.beans.util;

import com.ffusion.util.FilteredList;
import java.util.Iterator;

public class CountryDefns
  extends FilteredList
{
  public String toString()
  {
    String str = System.getProperty("line.separator");
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("CountryDefns Bean:" + str);
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      CountryDefn localCountryDefn = (CountryDefn)localIterator.next();
      localStringBuffer.append(localCountryDefn.toString());
    }
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.util.CountryDefns
 * JD-Core Version:    0.7.0.1
 */