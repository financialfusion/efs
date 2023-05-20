package com.ffusion.beans.business;

import com.ffusion.util.FilteredList;
import java.util.Iterator;
import java.util.Locale;

public class MarketSegments
  extends FilteredList
{
  public MarketSegments() {}
  
  public MarketSegments(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
  }
  
  public MarketSegment add()
  {
    MarketSegment localMarketSegment = new MarketSegment(this.locale);
    add(localMarketSegment);
    return localMarketSegment;
  }
  
  public MarketSegment create()
  {
    MarketSegment localMarketSegment = new MarketSegment(this.locale);
    return localMarketSegment;
  }
  
  public void set(MarketSegments paramMarketSegments)
  {
    Iterator localIterator = paramMarketSegments.iterator();
    while (localIterator.hasNext())
    {
      MarketSegment localMarketSegment = (MarketSegment)localIterator.next();
      if (localMarketSegment != null) {
        add(localMarketSegment);
      }
    }
  }
  
  public MarketSegment getById(int paramInt)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      MarketSegment localMarketSegment = (MarketSegment)localIterator.next();
      if (localMarketSegment.getIdValue() == paramInt)
      {
        localObject = localMarketSegment;
        break;
      }
    }
    return localObject;
  }
  
  public MarketSegment getByMarketSegmentName(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      MarketSegment localMarketSegment = (MarketSegment)localIterator.next();
      if (localMarketSegment.getMarketSegmentName().equalsIgnoreCase(paramString))
      {
        localObject = localMarketSegment;
        break;
      }
    }
    return localObject;
  }
  
  public void removeById(int paramInt)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      MarketSegment localMarketSegment = (MarketSegment)localIterator.next();
      if (localMarketSegment.getIdValue() == paramInt)
      {
        localObject = localMarketSegment;
        break;
      }
    }
    if (localObject != null) {
      remove(localObject);
    }
  }
  
  public void removeByName(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      MarketSegment localMarketSegment = (MarketSegment)localIterator.next();
      if (localMarketSegment.getMarketSegmentName().equals(paramString) == true)
      {
        localObject = localMarketSegment;
        break;
      }
    }
    if (localObject != null) {
      remove(localObject);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.business.MarketSegments
 * JD-Core Version:    0.7.0.1
 */