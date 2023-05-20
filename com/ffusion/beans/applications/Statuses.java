package com.ffusion.beans.applications;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import java.io.Serializable;
import java.util.Iterator;

public class Statuses
  extends FilteredList
  implements Serializable
{
  public static final String STATUS_GROUP = "STATUS_GROUP";
  
  public Status add()
  {
    Status localStatus = new Status();
    add(localStatus);
    return localStatus;
  }
  
  public Status create()
  {
    Status localStatus = new Status();
    return localStatus;
  }
  
  public Status getByID(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Status localStatus = (Status)localIterator.next();
      if (paramString.equalsIgnoreCase(localStatus.getID()))
      {
        localObject = localStatus;
        break;
      }
    }
    return localObject;
  }
  
  public Status getByName(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Status localStatus = (Status)localIterator.next();
      if (localStatus.getName().equalsIgnoreCase(paramString))
      {
        localObject = localStatus;
        break;
      }
    }
    return localObject;
  }
  
  public void setBankID(String paramString)
  {
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Status localStatus = (Status)localIterator.next();
      localStatus.setBankID(paramString);
    }
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "STATUS_GROUP");
    Status localStatus = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      localStatus = (Status)localIterator.next();
      localStringBuffer.append(localStatus.getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "STATUS_GROUP");
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
      if (paramString.equals("APP_STATUS")) {
        Statuses.this.add().continueXMLParsing(getHandler());
      }
    }
    
    public void endElement(String paramString)
      throws Exception
    {}
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.applications.Statuses
 * JD-Core Version:    0.7.0.1
 */