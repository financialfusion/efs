package com.ffusion.beans.cashcon;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import com.ffusion.util.beans.XMLStrings;
import java.util.Iterator;

public class LocationSearchResults
  extends FilteredList
  implements XMLStrings, XMLable
{
  public LocationSearchResult add()
  {
    LocationSearchResult localLocationSearchResult = new LocationSearchResult();
    add(localLocationSearchResult);
    return localLocationSearchResult;
  }
  
  public LocationSearchResult getByBPWID(String paramString)
  {
    LocationSearchResult localLocationSearchResult = null;
    if (paramString != null)
    {
      Iterator localIterator = iterator();
      while (localIterator.hasNext())
      {
        localLocationSearchResult = (LocationSearchResult)localIterator.next();
        if (paramString.equals(localLocationSearchResult.getLocationBPWID())) {
          return localLocationSearchResult;
        }
      }
    }
    return null;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof LocationSearchResults)) {
      return false;
    }
    LocationSearchResults localLocationSearchResults = (LocationSearchResults)paramObject;
    if (size() != localLocationSearchResults.size()) {
      return false;
    }
    return containsAll(localLocationSearchResults);
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "LOCATION_SEARCH_RESULTS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((LocationSearchResult)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "LOCATION_SEARCH_RESULTS");
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(), paramString);
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  class a
    extends XMLHandler
  {
    a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equals("LOCATION_SEARCH_RESULT"))
      {
        LocationSearchResult localLocationSearchResult = new LocationSearchResult();
        LocationSearchResults.this.add(localLocationSearchResult);
        localLocationSearchResult.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.cashcon.LocationSearchResults
 * JD-Core Version:    0.7.0.1
 */