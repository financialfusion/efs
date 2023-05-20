package com.ffusion.beans.wiretransfers;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.util.Iterator;
import java.util.Locale;

public class WireTransfers
  extends FilteredList
  implements XMLable
{
  protected String dateformat;
  
  public WireTransfers() {}
  
  public WireTransfers(Locale paramLocale)
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
      WireTransfer localWireTransfer = (WireTransfer)localIterator.next();
      localWireTransfer.setDateFormat(paramString);
    }
  }
  
  public String getDateFormat()
  {
    return this.dateformat;
  }
  
  public Object create()
  {
    WireTransfer localWireTransfer = new WireTransfer(this.locale);
    add(localWireTransfer);
    return localWireTransfer;
  }
  
  public Object createNoAdd()
  {
    WireTransfer localWireTransfer = new WireTransfer(this.locale);
    return localWireTransfer;
  }
  
  public boolean add(Object paramObject)
  {
    if (!(paramObject instanceof WireTransfer)) {
      throw new IllegalArgumentException("Object must be of type WireTransfer");
    }
    WireTransfer localWireTransfer = (WireTransfer)paramObject;
    localWireTransfer.setLocale(this.locale);
    return super.add(localWireTransfer);
  }
  
  public Object getByID(String paramString)
  {
    return getFirstByFilter("ID=" + paramString);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "WIRE_TRANSFERS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((WireTransfer)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "WIRE_TRANSFERS");
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
      if (paramString.equals("WIRE_TRANSFER"))
      {
        WireTransfer localWireTransfer = (WireTransfer)WireTransfers.this.create();
        localWireTransfer.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.wiretransfers.WireTransfers
 * JD-Core Version:    0.7.0.1
 */