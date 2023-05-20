package com.ffusion.beans.portal;

import com.ffusion.beans.ToXml;
import com.ffusion.util.XMLHandler;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;

public class Forecasts
  extends ArrayList
  implements ToXml
{
  public static final String FORECASTS = "FORECASTS";
  public static final String SCALE = "SCALE";
  private String jdField_try = "F";
  private boolean jdField_new = false;
  
  public String getScale()
  {
    return this.jdField_try;
  }
  
  public void setScale(String paramString)
  {
    this.jdField_try = paramString;
  }
  
  public void setInForecasts(boolean paramBoolean)
  {
    this.jdField_new = paramBoolean;
  }
  
  public void setCheck(String paramString)
  {
    int i = size();
    for (int j = 0; j < i; j++)
    {
      Forecast localForecast2 = (Forecast)get(j);
      if (localForecast2.getCity().equals(paramString)) {
        return;
      }
    }
    Forecast localForecast1 = new Forecast();
    localForecast1.setCity(paramString);
    add(localForecast1);
  }
  
  public Forecast getForecast(String paramString)
  {
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Forecast localForecast = (Forecast)localIterator.next();
      if (localForecast.getCity().equalsIgnoreCase(paramString)) {
        return localForecast;
      }
    }
    return null;
  }
  
  public String toXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.openTag(localStringBuffer, "FORECASTS");
    XMLHandler.appendAttribute(localStringBuffer, "SCALE", this.jdField_try);
    XMLHandler.closeTag(localStringBuffer, false);
    Iterator localIterator = iterator();
    while (localIterator.hasNext()) {
      localStringBuffer.append(((Forecast)localIterator.next()).toXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "FORECASTS");
    return XMLHandler.format(localStringBuffer.toString());
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a(null));
  }
  
  public void startXMLParsing(Reader paramReader)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(null), paramReader);
    }
    catch (Exception localException) {}
  }
  
  public void startXMLParsing(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(null), paramString);
    }
    catch (Exception localException) {}
  }
  
  private class a
    extends XMLHandler
  {
    private boolean jdField_int = false;
    private String jdField_new = "";
    private String jdField_try = "";
    
    private a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equalsIgnoreCase("FORECASTS"))
      {
        this.jdField_int = true;
      }
      else if (((Forecasts.this.jdField_new) || (this.jdField_int)) && (paramString.equalsIgnoreCase("FORECAST")))
      {
        Forecast localForecast = new Forecast();
        if (this.jdField_new.equalsIgnoreCase("CITY")) {
          localForecast.setCity(this.jdField_try);
        }
        localForecast.continueXMLParsing(getHandler());
        Forecasts.this.add(localForecast);
      }
    }
    
    public void attribute(String paramString1, String paramString2, boolean paramBoolean)
    {
      this.jdField_new = paramString1;
      this.jdField_try = paramString2;
      if (this.jdField_new.equalsIgnoreCase("SCALE")) {
        Forecasts.this.jdField_try = this.jdField_try;
      }
    }
    
    public void endElement(String paramString)
    {
      if (paramString.equalsIgnoreCase("FORECASTS")) {
        this.jdField_int = false;
      }
      this.jdField_new = "";
      this.jdField_try = "";
    }
    
    a(Forecasts.1 param1)
    {
      this();
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.portal.Forecasts
 * JD-Core Version:    0.7.0.1
 */