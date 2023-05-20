package com.ffusion.beans.alerts;

import com.ffusion.util.FilteredList;
import com.ffusion.util.Localeable;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.io.Serializable;
import java.util.Locale;

public class AlertStocks
  extends FilteredList
  implements Localeable, XMLable, Serializable
{
  public static final String ALERTSTOCKS = "ALERTSTOCKS";
  
  public AlertStocks() {}
  
  public AlertStocks(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public Object create()
  {
    AlertStock localAlertStock = new AlertStock(this.locale);
    add(localAlertStock);
    return localAlertStock;
  }
  
  public Object createNoAdd()
  {
    return new AlertStock(this.locale);
  }
  
  public boolean add(Object paramObject)
  {
    boolean bool = true;
    AlertStock localAlertStock = (AlertStock)paramObject;
    localAlertStock.setLocale(this.locale);
    if (!contains(localAlertStock)) {
      bool = super.add(localAlertStock);
    }
    return bool;
  }
  
  public void set(AlertStocks paramAlertStocks)
  {
    clear();
    addAll(paramAlertStocks);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    return bool;
  }
  
  public AlertStock getByID(String paramString)
  {
    return (AlertStock)getFirstByFilter("ID=" + paramString);
  }
  
  public void removeByID(String paramString)
  {
    remove(getByID(paramString));
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "ALERTSTOCKS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((AlertStock)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "ALERTSTOCKS");
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
      if (paramString.equals("ALERTSTOCK"))
      {
        AlertStock localAlertStock = new AlertStock();
        AlertStocks.this.add(localAlertStock);
        localAlertStock.continueXMLParsing(getHandler());
      }
    }
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      AlertStocks.this.set(getElement(), str);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.alerts.AlertStocks
 * JD-Core Version:    0.7.0.1
 */