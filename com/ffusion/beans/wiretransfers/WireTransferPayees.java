package com.ffusion.beans.wiretransfers;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.util.Iterator;

public class WireTransferPayees
  extends FilteredList
  implements XMLable
{
  public WireTransferPayee create()
  {
    WireTransferPayee localWireTransferPayee = new WireTransferPayee();
    add(localWireTransferPayee);
    return localWireTransferPayee;
  }
  
  public WireTransferPayee createNoAdd()
  {
    return new WireTransferPayee();
  }
  
  public boolean add(Object paramObject)
  {
    if (!(paramObject instanceof WireTransferPayee)) {
      throw new IllegalArgumentException("Object must be of type WireTransferPayee");
    }
    WireTransferPayee localWireTransferPayee = (WireTransferPayee)paramObject;
    return super.add(localWireTransferPayee);
  }
  
  public WireTransferPayee getByID(String paramString)
  {
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      WireTransferPayee localWireTransferPayee = (WireTransferPayee)localIterator.next();
      if (localWireTransferPayee.getID().equals(paramString) == true) {
        return localWireTransferPayee;
      }
    }
    return null;
  }
  
  public WireTransferPayee getByName(String paramString)
  {
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      WireTransferPayee localWireTransferPayee = (WireTransferPayee)localIterator.next();
      if (localWireTransferPayee.getPayeeName().equals(paramString) == true) {
        return localWireTransferPayee;
      }
    }
    return null;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "WIRE_TRANSFER_PAYEES");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((WireTransferPayee)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "WIRE_TRANSFER_PAYEES");
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
      if (paramString.equals("WIRE_TRANSFER_PAYEE"))
      {
        WireTransferPayee localWireTransferPayee = WireTransferPayees.this.create();
        localWireTransferPayee.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.wiretransfers.WireTransferPayees
 * JD-Core Version:    0.7.0.1
 */