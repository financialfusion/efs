package com.ffusion.beans.billpresentment;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.util.Iterator;
import java.util.Locale;

public class Publishers
  extends FilteredList
{
  public static final String PUBLISHERS = "PUBLISHERS";
  private String jdField_byte;
  
  public Publishers() {}
  
  public Publishers(Locale paramLocale)
  {
    super(paramLocale);
    this.jdField_byte = "SHORT";
  }
  
  public Publisher add()
  {
    Publisher localPublisher = new Publisher();
    add(localPublisher);
    return localPublisher;
  }
  
  public static Publisher create()
  {
    Publisher localPublisher = new Publisher();
    return localPublisher;
  }
  
  public Publisher getByPublisherID(long paramLong)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Publisher localPublisher = (Publisher)localIterator.next();
      if (localPublisher.getPublisherIDValue() == paramLong)
      {
        localObject = localPublisher;
        break;
      }
    }
    return localObject;
  }
  
  public Publisher getByPublisherID(String paramString)
  {
    long l;
    try
    {
      l = Long.parseLong(paramString);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception parsing ID:", localException);
      return null;
    }
    return getByPublisherID(l);
  }
  
  public void removeByPublisherID(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Publisher localPublisher = (Publisher)localIterator.next();
      if (localPublisher.getPublisherID().equals(paramString))
      {
        localObject = localPublisher;
        break;
      }
    }
    if (localObject != null) {
      remove(localObject);
    }
  }
  
  public void setDateFormat(String paramString)
  {
    this.jdField_byte = paramString;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Publisher localPublisher = (Publisher)localIterator.next();
      localPublisher.setDateFormat(this.jdField_byte);
    }
  }
  
  public String getDateFormat()
  {
    return this.jdField_byte;
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
    XMLHandler.appendBeginTag(localStringBuffer, "PUBLISHERS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((Publisher)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "PUBLISHERS");
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
      if (paramString.equals("PUBLISHER"))
      {
        Publisher localPublisher = new Publisher();
        Publishers.this.add(localPublisher);
        localPublisher.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.billpresentment.Publishers
 * JD-Core Version:    0.7.0.1
 */