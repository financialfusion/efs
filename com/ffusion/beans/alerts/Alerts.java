package com.ffusion.beans.alerts;

import com.ffusion.util.FilteredList;
import com.ffusion.util.Localeable;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Locale;

public class Alerts
  extends FilteredList
  implements Localeable, XMLable, Serializable
{
  public static final String ALERTS = "ALERTS";
  protected String datetype = "SHORT";
  
  public Alerts() {}
  
  public Alerts(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public Object create()
  {
    Alert localAlert = new Alert(this.locale);
    add(localAlert);
    return localAlert;
  }
  
  public Object createNoAdd()
  {
    return new Alert(this.locale);
  }
  
  public boolean add(Object paramObject)
  {
    Alert localAlert = (Alert)paramObject;
    localAlert.setLocale(this.locale);
    return super.add(localAlert);
  }
  
  public void set(Alerts paramAlerts)
  {
    clear();
    addAll(paramAlerts);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    return bool;
  }
  
  public Alert getByID(String paramString)
  {
    return (Alert)getFirstByFilter("ID=" + paramString);
  }
  
  public void setDateFormat(String paramString)
  {
    this.datetype = paramString;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Alert localAlert = (Alert)localIterator.next();
      localAlert.setDateFormat(this.datetype);
    }
  }
  
  public String getDateFormat()
  {
    return this.datetype;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "ALERTS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((Alert)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "ALERTS");
    return localStringBuffer.toString();
  }
  
  public String toXML()
  {
    return getXML();
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
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  class a
    extends XMLHandler
  {
    public a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equals("ALERT"))
      {
        Alert localAlert = new Alert();
        Alerts.this.add(localAlert);
        localAlert.continueXMLParsing(getHandler());
      }
    }
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      Alerts.this.set(getElement(), str);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.alerts.Alerts
 * JD-Core Version:    0.7.0.1
 */