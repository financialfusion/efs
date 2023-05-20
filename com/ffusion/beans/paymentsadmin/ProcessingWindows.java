package com.ffusion.beans.paymentsadmin;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import java.util.Iterator;
import java.util.Locale;

public class ProcessingWindows
  extends FilteredList
{
  protected String dateformat;
  
  public ProcessingWindows() {}
  
  public ProcessingWindows(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException("Locale cannot be null");
    }
    this.dateformat = "SHORT";
  }
  
  public void setDateFormat(String paramString)
  {
    this.dateformat = paramString;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      ProcessingWindow localProcessingWindow = (ProcessingWindow)localIterator.next();
      localProcessingWindow.setDateFormat(paramString);
    }
  }
  
  public String getDateFormat()
  {
    return this.dateformat;
  }
  
  public Object create()
  {
    ProcessingWindow localProcessingWindow = new ProcessingWindow(this.locale);
    add(localProcessingWindow);
    return localProcessingWindow;
  }
  
  public Object createNoAdd()
  {
    ProcessingWindow localProcessingWindow = new ProcessingWindow(this.locale);
    return localProcessingWindow;
  }
  
  public boolean add(Object paramObject)
  {
    if (!(paramObject instanceof ProcessingWindow)) {
      throw new IllegalArgumentException("Object must be of type ProcessingWindow");
    }
    ProcessingWindow localProcessingWindow = (ProcessingWindow)paramObject;
    localProcessingWindow.setLocale(this.locale);
    return super.add(localProcessingWindow);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "PROCESSING_WINDOW");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((ProcessingWindow)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "PROCESSING_WINDOW");
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
      if (paramString.equals("PROCESSING_WINDOW"))
      {
        ProcessingWindow localProcessingWindow = (ProcessingWindow)ProcessingWindows.this.create();
        localProcessingWindow.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.paymentsadmin.ProcessingWindows
 * JD-Core Version:    0.7.0.1
 */