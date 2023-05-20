package com.ffusion.beans.portal;

import com.ffusion.util.XMLHandler;
import java.util.ArrayList;
import java.util.Iterator;

public class Forecast
  extends ArrayList
{
  public static final String FORECAST = "FORECAST";
  public static final String CITY = "CITY";
  private String a = "";
  
  public String getCity()
  {
    return this.a;
  }
  
  public void setCity(String paramString)
  {
    this.a = paramString;
  }
  
  public String toXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.openTag(localStringBuffer, "FORECAST");
    XMLHandler.appendAttribute(localStringBuffer, "CITY", this.a);
    XMLHandler.closeTag(localStringBuffer, false);
    Iterator localIterator = iterator();
    while (localIterator.hasNext()) {
      localStringBuffer.append(((ForecastDay)localIterator.next()).toXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "FORECAST");
    return XMLHandler.format(localStringBuffer.toString());
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a(null));
  }
  
  private class a
    extends XMLHandler
  {
    private a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equalsIgnoreCase("DAY"))
      {
        ForecastDay localForecastDay = new ForecastDay();
        localForecastDay.continueXMLParsing(getHandler());
        Forecast.this.add(localForecastDay);
      }
    }
    
    a(Forecast.1 param1)
    {
      this();
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.portal.Forecast
 * JD-Core Version:    0.7.0.1
 */