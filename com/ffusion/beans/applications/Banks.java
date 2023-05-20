package com.ffusion.beans.applications;

import com.ffusion.util.XMLHandler;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class Banks
  extends ArrayList
  implements Serializable
{
  public static final String BANKS = "BANKS";
  
  public Bank add()
  {
    Bank localBank = new Bank();
    add(localBank);
    return localBank;
  }
  
  public Bank create()
  {
    Bank localBank = new Bank();
    return localBank;
  }
  
  public Bank getByID(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Bank localBank = (Bank)localIterator.next();
      if (paramString.equalsIgnoreCase(localBank.getBankID()))
      {
        localObject = localBank;
        break;
      }
    }
    return localObject;
  }
  
  public Bank getByName(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Bank localBank = (Bank)localIterator.next();
      if (localBank.getBankName().equalsIgnoreCase(paramString))
      {
        localObject = localBank;
        break;
      }
    }
    return localObject;
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "BANKS");
    Bank localBank = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      localBank = (Bank)localIterator.next();
      localStringBuffer.append(localBank.getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "BANKS");
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(), paramString);
    }
    catch (Throwable localThrowable) {}
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  class a
    extends XMLHandler
  {
    a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equals("BANK")) {
        Banks.this.add().continueXMLParsing(getHandler());
      }
    }
    
    public void endElement(String paramString)
      throws Exception
    {}
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.applications.Banks
 * JD-Core Version:    0.7.0.1
 */