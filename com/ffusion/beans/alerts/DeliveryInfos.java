package com.ffusion.beans.alerts;

import com.ffusion.util.FilteredList;
import com.ffusion.util.Localeable;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Locale;

public class DeliveryInfos
  extends FilteredList
  implements Localeable, XMLable, Serializable
{
  public static final String DELIVERYINFOS = "DELIVERYINFOS";
  protected String datetype = "SHORT";
  
  public DeliveryInfos() {}
  
  public DeliveryInfos(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public Object create()
  {
    DeliveryInfo localDeliveryInfo = new DeliveryInfo(this.locale);
    add(localDeliveryInfo);
    return localDeliveryInfo;
  }
  
  public Object createNoAdd()
  {
    return new DeliveryInfo(this.locale);
  }
  
  public boolean add(Object paramObject)
  {
    DeliveryInfo localDeliveryInfo = (DeliveryInfo)paramObject;
    localDeliveryInfo.setLocale(this.locale);
    return super.add(localDeliveryInfo);
  }
  
  public void set(DeliveryInfos paramDeliveryInfos)
  {
    clear();
    addAll(paramDeliveryInfos);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    return bool;
  }
  
  public DeliveryInfo getByID(String paramString)
  {
    return (DeliveryInfo)getFirstByFilter("ID=" + paramString);
  }
  
  public DeliveryInfo getByProperty(String paramString1, String paramString2)
  {
    for (int i = 0; i < size(); i++)
    {
      DeliveryInfo localDeliveryInfo = (DeliveryInfo)get(i);
      localDeliveryInfo.setPropertyKey(paramString1);
      if (paramString2.equals(localDeliveryInfo.getProperty())) {
        return localDeliveryInfo;
      }
    }
    return null;
  }
  
  public void setDateFormat(String paramString)
  {
    this.datetype = paramString;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      DeliveryInfo localDeliveryInfo = (DeliveryInfo)localIterator.next();
      localDeliveryInfo.setDateFormat(this.datetype);
    }
  }
  
  public String getDateFormat()
  {
    return this.datetype;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "DELIVERYINFOS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((DeliveryInfo)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "DELIVERYINFOS");
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
      if (paramString.equals("DELIVERYINFO"))
      {
        DeliveryInfo localDeliveryInfo = new DeliveryInfo();
        DeliveryInfos.this.add(localDeliveryInfo);
        localDeliveryInfo.continueXMLParsing(getHandler());
      }
    }
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      DeliveryInfos.this.set(getElement(), str);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.alerts.DeliveryInfos
 * JD-Core Version:    0.7.0.1
 */