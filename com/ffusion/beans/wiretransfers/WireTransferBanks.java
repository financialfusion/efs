package com.ffusion.beans.wiretransfers;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.util.Iterator;

public class WireTransferBanks
  extends FilteredList
  implements XMLable
{
  public WireTransferBank create()
  {
    WireTransferBank localWireTransferBank = new WireTransferBank();
    add(localWireTransferBank);
    return localWireTransferBank;
  }
  
  public WireTransferBank createNoAdd()
  {
    return new WireTransferBank();
  }
  
  public boolean add(Object paramObject)
  {
    if (!(paramObject instanceof WireTransferBank)) {
      throw new IllegalArgumentException("Object must be of type WireTransferBank");
    }
    WireTransferBank localWireTransferBank = (WireTransferBank)paramObject;
    return super.add(localWireTransferBank);
  }
  
  public WireTransferBank getByID(String paramString)
  {
    return (WireTransferBank)getFirstByFilter("ID=" + paramString);
  }
  
  public WireTransferBank getByName(String paramString)
  {
    return (WireTransferBank)getFirstByFilter("BANK_NAME=" + paramString);
  }
  
  public boolean isDuplicateBank(WireTransferBank paramWireTransferBank)
  {
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      WireTransferBank localWireTransferBank = (WireTransferBank)localIterator.next();
      if ((localWireTransferBank != null) && (localWireTransferBank.isSame(paramWireTransferBank))) {
        return true;
      }
    }
    return false;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "WIRE_TRANSFER_BANKS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((WireTransferBank)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "WIRE_TRANSFER_BANKS");
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
      if (paramString.equals("WIRE_TRANSFER_BANK"))
      {
        WireTransferBank localWireTransferBank = WireTransferBanks.this.create();
        localWireTransferBank.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.wiretransfers.WireTransferBanks
 * JD-Core Version:    0.7.0.1
 */