package com.ffusion.beans.portal;

import com.ffusion.util.XMLHandler;
import java.io.Serializable;

public class ForecastDay
  implements Serializable
{
  public static final String FORECAST_DAY = "DAY";
  public static final String DATE = "DATE";
  public static final String HIGH = "HIGH";
  public static final String LOW = "LOW";
  public static final String SKY = "SKY";
  private String a = "";
  private String jdField_if = "";
  private String jdField_do = "";
  private String jdField_int = "";
  private String jdField_for = "";
  
  public String getDate()
  {
    return this.a;
  }
  
  public void setDate(String paramString)
  {
    this.a = paramString;
  }
  
  public String getLow()
  {
    return this.jdField_do;
  }
  
  public void setLow(String paramString)
  {
    this.jdField_do = paramString;
  }
  
  public String getHigh()
  {
    return this.jdField_if;
  }
  
  public void setHigh(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public String getSkyCondition()
  {
    return this.jdField_int;
  }
  
  public void setSkyCondition(String paramString)
  {
    this.jdField_int = paramString;
  }
  
  public String getDay()
  {
    return this.jdField_for;
  }
  
  public void setDay(String paramString)
  {
    this.jdField_for = paramString;
  }
  
  public String toXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "DAY");
    XMLHandler.appendTag(localStringBuffer, "DATE", this.a);
    XMLHandler.appendTag(localStringBuffer, "HIGH", this.jdField_if);
    XMLHandler.appendTag(localStringBuffer, "LOW", this.jdField_do);
    XMLHandler.appendTag(localStringBuffer, "SKY", this.jdField_int);
    XMLHandler.appendEndTag(localStringBuffer, "DAY");
    return localStringBuffer.toString();
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a(null));
  }
  
  private class a
    extends XMLHandler
  {
    String jdField_int = "";
    
    private a() {}
    
    public void endElement(String paramString)
    {
      if (paramString.equalsIgnoreCase("DATE")) {
        ForecastDay.this.a = this.jdField_int;
      } else if (paramString.equalsIgnoreCase("HIGH")) {
        ForecastDay.this.jdField_if = this.jdField_int;
      } else if (paramString.equalsIgnoreCase("LOW")) {
        ForecastDay.this.jdField_do = this.jdField_int;
      } else if (paramString.equalsIgnoreCase("SKY")) {
        ForecastDay.this.jdField_int = this.jdField_int;
      }
    }
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      this.jdField_int = new String(paramArrayOfChar, paramInt1, paramInt2);
    }
    
    a(ForecastDay.1 param1)
    {
      this();
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.portal.ForecastDay
 * JD-Core Version:    0.7.0.1
 */