package com.ffusion.beans.applications;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Locale;

public class ApplicationHistories
  extends FilteredList
  implements Serializable
{
  public static final String HISTORY_GROUP = "HISTORY_GROUP";
  protected String datetype = "SHORT";
  
  protected ApplicationHistories() {}
  
  public ApplicationHistories(Locale paramLocale)
  {
    super(paramLocale);
    this.datetype = "SHORT";
  }
  
  public void setDateFormat(String paramString)
  {
    this.datetype = paramString;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      ApplicationHistory localApplicationHistory = (ApplicationHistory)localIterator.next();
      localApplicationHistory.setDateFormat(this.datetype);
    }
  }
  
  public String getDateFormat()
  {
    return this.datetype;
  }
  
  public ApplicationHistory add()
  {
    ApplicationHistory localApplicationHistory = new ApplicationHistory(this.locale);
    add(localApplicationHistory);
    return localApplicationHistory;
  }
  
  public ApplicationHistory create()
  {
    ApplicationHistory localApplicationHistory = new ApplicationHistory();
    return localApplicationHistory;
  }
  
  public ApplicationHistory getByID(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      ApplicationHistory localApplicationHistory = (ApplicationHistory)localIterator.next();
      if (paramString.equalsIgnoreCase(localApplicationHistory.getAppID()))
      {
        localObject = localApplicationHistory;
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
    XMLHandler.appendBeginTag(localStringBuffer, "HISTORY_GROUP");
    ApplicationHistory localApplicationHistory = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      localApplicationHistory = (ApplicationHistory)localIterator.next();
      localStringBuffer.append(localApplicationHistory.getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "HISTORY_GROUP");
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
      if (paramString.equals("HISTORY")) {
        ApplicationHistories.this.add().continueXMLParsing(getHandler());
      }
    }
    
    public void endElement(String paramString)
      throws Exception
    {}
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.applications.ApplicationHistories
 * JD-Core Version:    0.7.0.1
 */