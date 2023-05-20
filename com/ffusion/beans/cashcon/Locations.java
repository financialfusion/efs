package com.ffusion.beans.cashcon;

import com.ffusion.util.FilteredList;
import com.ffusion.util.Localeable;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import com.ffusion.util.beans.XMLStrings;
import java.util.Iterator;
import java.util.Locale;

public class Locations
  extends FilteredList
  implements Localeable, XMLStrings, XMLable
{
  public Locations(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public Location getByBPWID(String paramString)
  {
    Location localLocation = null;
    if (paramString != null)
    {
      Iterator localIterator = iterator();
      while (localIterator.hasNext())
      {
        localLocation = (Location)localIterator.next();
        if (paramString.equals(localLocation.getLocationBPWID())) {
          return localLocation;
        }
      }
    }
    return null;
  }
  
  public Location add()
  {
    Location localLocation = new Location(this.locale);
    add(localLocation);
    return localLocation;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof Locations)) {
      return false;
    }
    Locations localLocations = (Locations)paramObject;
    if (size() != localLocations.size()) {
      return false;
    }
    Locale localLocale1 = this.locale;
    Locale localLocale2 = localLocations.locale;
    if (localLocale1 != null)
    {
      if (localLocale2 != null)
      {
        if (!localLocale1.equals(localLocale2)) {
          return false;
        }
      }
      else {
        return false;
      }
    }
    else if (localLocale2 != null) {
      return false;
    }
    return containsAll(localLocations);
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
    XMLHandler.appendBeginTag(localStringBuffer, "LOCATIONS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((Location)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "LOCATIONS");
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
      if (paramString.equals("LOCATION"))
      {
        Location localLocation = new Location(Locations.this.locale);
        Locations.this.add(localLocation);
        localLocation.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.cashcon.Locations
 * JD-Core Version:    0.7.0.1
 */