package com.ffusion.beans.util;

import com.ffusion.util.FilteredList;
import java.util.Iterator;

public class FieldValidationDefns
  extends FilteredList
{
  public String toString()
  {
    String str = System.getProperty("line.separator");
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("FieldValidationDefns Bean:" + str);
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      FieldValidationDefn localFieldValidationDefn = (FieldValidationDefn)localIterator.next();
      localStringBuffer.append(localFieldValidationDefn.toString());
    }
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.util.FieldValidationDefns
 * JD-Core Version:    0.7.0.1
 */