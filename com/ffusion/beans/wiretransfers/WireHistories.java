package com.ffusion.beans.wiretransfers;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.util.Iterator;
import java.util.Locale;

public class WireHistories
  extends FilteredList
  implements XMLable
{
  protected String dateformat;
  
  public WireHistories() {}
  
  public WireHistories(Locale paramLocale)
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
      WireHistory localWireHistory = (WireHistory)localIterator.next();
      localWireHistory.setDateFormat(paramString);
    }
  }
  
  public String getDateFormat()
  {
    return this.dateformat;
  }
  
  public Object create()
  {
    WireHistory localWireHistory = new WireHistory(this.locale);
    add(localWireHistory);
    return localWireHistory;
  }
  
  public Object createNoAdd()
  {
    WireHistory localWireHistory = new WireHistory(this.locale);
    return localWireHistory;
  }
  
  public boolean add(Object paramObject)
  {
    if (!(paramObject instanceof WireHistory)) {
      throw new IllegalArgumentException("Object must be of type WireHistory");
    }
    WireHistory localWireHistory = (WireHistory)paramObject;
    localWireHistory.setLocale(this.locale);
    return super.add(localWireHistory);
  }
  
  public Object getByID(String paramString)
  {
    return getFirstByFilter("ID=" + paramString);
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
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "WIRE_HISTORIES");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((WireHistory)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "WIRE_HISTORIES");
    return localStringBuffer.toString();
  }
  
  public String toXML()
  {
    return getXML();
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
      if (paramString.equals("WIRE_HISTORY"))
      {
        WireHistory localWireHistory = (WireHistory)WireHistories.this.create();
        localWireHistory.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.wiretransfers.WireHistories
 * JD-Core Version:    0.7.0.1
 */