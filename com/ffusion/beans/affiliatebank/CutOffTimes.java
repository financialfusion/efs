package com.ffusion.beans.affiliatebank;

import com.ffusion.util.FilteredList;
import java.util.Iterator;
import java.util.Locale;

public class CutOffTimes
  extends FilteredList
{
  public CutOffTimes() {}
  
  public CutOffTimes(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
  }
  
  public CutOffTime add()
  {
    CutOffTime localCutOffTime = new CutOffTime();
    add(localCutOffTime);
    return localCutOffTime;
  }
  
  public CutOffTime createNoAdd()
  {
    CutOffTime localCutOffTime = new CutOffTime();
    return localCutOffTime;
  }
  
  public void removeByID(String paramString)
  {
    CutOffTime localCutOffTime = null;
    if (paramString != null)
    {
      Iterator localIterator = iterator();
      while (localIterator.hasNext())
      {
        localCutOffTime = (CutOffTime)localIterator.next();
        if (paramString.equals(localCutOffTime.getCutOffId())) {
          localIterator.remove();
        }
      }
    }
  }
  
  public CutOffTime getByID(String paramString)
  {
    CutOffTime localCutOffTime = null;
    if (paramString != null)
    {
      Iterator localIterator = iterator();
      while (localIterator.hasNext())
      {
        localCutOffTime = (CutOffTime)localIterator.next();
        if (paramString.equals(localCutOffTime.getCutOffId())) {
          return localCutOffTime;
        }
      }
    }
    return null;
  }
  
  public String getNextCutOffTime()
  {
    CutOffTime localCutOffTime = null;
    String str = "";
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      localCutOffTime = (CutOffTime)localIterator.next();
      if (localCutOffTime.getActiveValue())
      {
        if ((str == null) || (str.length() == 0)) {
          str = localCutOffTime.getNextProcessTime();
        }
        if ((str != null) && (str.compareTo(localCutOffTime.getNextProcessTime()) > 0)) {
          str = localCutOffTime.getNextProcessTime();
        }
      }
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.affiliatebank.CutOffTimes
 * JD-Core Version:    0.7.0.1
 */