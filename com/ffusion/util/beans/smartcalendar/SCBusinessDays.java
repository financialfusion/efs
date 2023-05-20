package com.ffusion.util.beans.smartcalendar;

import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean;
import java.util.Locale;

public class SCBusinessDays
  extends ExtendABean
{
  public static final String SC_BUSINESS_DAYS = "SC_BUSINESS_DAYS";
  public static final String BUSINESS_DAYS = "BUSINESS_DAYS";
  public static final String ACTIONS = "ACTIONS";
  private String u;
  private String v;
  
  public SCBusinessDays(Locale paramLocale)
  {
    this.locale = paramLocale;
  }
  
  public String getBusinessDays()
  {
    return this.u;
  }
  
  public void setBusinessDays(String paramString)
  {
    if (paramString.length() != 7) {
      throw new IllegalArgumentException();
    }
    this.u = paramString;
  }
  
  public String getActions()
  {
    return this.v;
  }
  
  public void setActions(String paramString)
  {
    if (paramString.length() != 7) {
      throw new IllegalArgumentException();
    }
    this.v = paramString;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("businessDays=").append(this.u);
    localStringBuffer.append("actions=").append(this.v);
    return localStringBuffer.toString();
  }
  
  public int compare(Object paramObject, String paramString)
  {
    SCBusinessDays localSCBusinessDays = (SCBusinessDays)paramObject;
    int i = 1;
    if (paramString.equals("BUSINESS_DAYS")) {
      i = this.u.compareToIgnoreCase(localSCBusinessDays.getBusinessDays());
    } else if (paramString.equals("ACTIONS")) {
      i = this.v.compareToIgnoreCase(localSCBusinessDays.getActions());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "SC_BUSINESS_DAYS");
    if (this.u != null) {
      XMLHandler.appendTag(localStringBuffer, "BUSINESS_DAYS", this.u);
    }
    if (this.v != null) {
      XMLHandler.appendTag(localStringBuffer, "ACTIONS", this.v);
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "SC_BUSINESS_DAYS");
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new InternalXMLHandler(), paramString);
    }
    catch (Throwable localThrowable) {}
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new InternalXMLHandler());
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("BUSINESS_DAYS")) {
      setBusinessDays(paramString2);
    } else if (paramString1.equals("ACTIONS")) {
      setActions(paramString2);
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  class InternalXMLHandler
    extends XMLHandler
  {
    public InternalXMLHandler() {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      SCBusinessDays.this.set(getElement(), str);
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.beans.smartcalendar.SCBusinessDays
 * JD-Core Version:    0.7.0.1
 */