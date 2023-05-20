package com.ffusion.beans.applications;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Locale;

public class Applications
  extends FilteredList
  implements Serializable
{
  public static final String APPLICATION_GROUP = "APPLICATION_GROUP";
  protected String datetype = "SHORT";
  
  protected Applications() {}
  
  public Applications(Locale paramLocale)
  {
    super(paramLocale);
    this.datetype = "SHORT";
  }
  
  public Application add()
  {
    Application localApplication = new Application(this.locale);
    localApplication.setDateFormat(this.datetype);
    add(localApplication);
    return localApplication;
  }
  
  public Application create()
  {
    Application localApplication = new Application();
    localApplication.setDateFormat(this.datetype);
    return localApplication;
  }
  
  public void setDateFormat(String paramString)
  {
    this.datetype = paramString;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Application localApplication = (Application)localIterator.next();
      localApplication.setDateFormat(this.datetype);
    }
  }
  
  public String getDateFormat()
  {
    return this.datetype;
  }
  
  public Application getByID(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Application localApplication = (Application)localIterator.next();
      if (paramString.equals(localApplication.getAppID()))
      {
        localObject = localApplication;
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
    XMLHandler.appendBeginTag(localStringBuffer, "APPLICATION_GROUP");
    Status localStatus = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      localStatus = (Status)localIterator.next();
      localStringBuffer.append(localStatus.getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "APPLICATION_GROUP");
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
      if (paramString.equals("APPLICATION")) {
        Applications.this.add().continueXMLParsing(getHandler());
      }
    }
    
    public void endElement(String paramString)
      throws Exception
    {}
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.applications.Applications
 * JD-Core Version:    0.7.0.1
 */