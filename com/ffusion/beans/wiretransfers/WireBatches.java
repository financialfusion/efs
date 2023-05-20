package com.ffusion.beans.wiretransfers;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.util.Iterator;
import java.util.Locale;

public class WireBatches
  extends FilteredList
  implements XMLable
{
  protected String dateformat;
  
  public WireBatches() {}
  
  public WireBatches(Locale paramLocale)
  {
    super(paramLocale);
    this.dateformat = "SHORT";
  }
  
  public void setDateFormat(String paramString)
  {
    this.dateformat = paramString;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      WireBatch localWireBatch = (WireBatch)localIterator.next();
      localWireBatch.setDateFormat(paramString);
    }
  }
  
  public String getDateFormat()
  {
    return this.dateformat;
  }
  
  public Object create()
  {
    WireBatch localWireBatch = new WireBatch(this.locale);
    add(localWireBatch);
    return localWireBatch;
  }
  
  public Object createNoAdd()
  {
    return new WireBatch(this.locale);
  }
  
  public boolean add(Object paramObject)
  {
    if (!(paramObject instanceof WireBatch)) {
      throw new IllegalArgumentException("Object must be of type WireBatch");
    }
    WireBatch localWireBatch = (WireBatch)paramObject;
    localWireBatch.setLocale(this.locale);
    return super.add(localWireBatch);
  }
  
  public Object getByID(String paramString)
  {
    return getFirstByFilter("ID=" + paramString);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "WIRE_BATCHES");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((WireBatch)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "WIRE_BATCHES");
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
      if (paramString.equals("WIRE_BATCH"))
      {
        WireBatch localWireBatch = (WireBatch)WireBatches.this.create();
        localWireBatch.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.wiretransfers.WireBatches
 * JD-Core Version:    0.7.0.1
 */