package com.ffusion.beans.billpresentment;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.util.Iterator;

public class Billers
  extends FilteredList
{
  public static final String BILLERS = "BILLERS";
  
  public Biller add()
  {
    Biller localBiller = new Biller();
    add(localBiller);
    return localBiller;
  }
  
  public static Biller create()
  {
    Biller localBiller = new Biller();
    return localBiller;
  }
  
  public Biller getByBillerID(long paramLong)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Biller localBiller = (Biller)localIterator.next();
      if (localBiller.getBillerIDValue() == paramLong)
      {
        localObject = localBiller;
        break;
      }
    }
    return localObject;
  }
  
  public Biller getByBillerID(String paramString)
  {
    try
    {
      long l = Long.parseLong(paramString);
      return getByBillerID(l);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception:", localException);
    }
    return null;
  }
  
  public Biller getByBillerName(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Biller localBiller = (Biller)localIterator.next();
      if (localBiller.getBillerName().equalsIgnoreCase(paramString))
      {
        localObject = localBiller;
        break;
      }
    }
    return localObject;
  }
  
  public void removeByBillerID(long paramLong)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Biller localBiller = (Biller)localIterator.next();
      if (localBiller.getBillerIDValue() == paramLong)
      {
        localObject = localBiller;
        break;
      }
    }
    if (localObject != null) {
      remove(localObject);
    }
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "BILLERS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((Biller)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "BILLERS");
    return localStringBuffer.toString();
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
      if (paramString.equals("BILLER"))
      {
        Biller localBiller = new Biller();
        Billers.this.add(localBiller);
        localBiller.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.billpresentment.Billers
 * JD-Core Version:    0.7.0.1
 */