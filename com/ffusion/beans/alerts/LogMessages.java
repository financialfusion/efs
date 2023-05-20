package com.ffusion.beans.alerts;

import com.ffusion.util.FilteredList;
import com.ffusion.util.Localeable;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Locale;

public class LogMessages
  extends FilteredList
  implements Localeable, XMLable, Serializable
{
  public static final String LOGMESSAGES = "LOGMESSAGES";
  protected String datetype = "SHORT";
  
  public LogMessages() {}
  
  public LogMessages(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public Object create()
  {
    LogMessage localLogMessage = new LogMessage(this.locale);
    add(localLogMessage);
    return localLogMessage;
  }
  
  public Object createNoAdd()
  {
    return new LogMessage(this.locale);
  }
  
  public boolean add(Object paramObject)
  {
    LogMessage localLogMessage = (LogMessage)paramObject;
    localLogMessage.setLocale(this.locale);
    return super.add(localLogMessage);
  }
  
  public void set(LogMessages paramLogMessages)
  {
    clear();
    addAll(paramLogMessages);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    return bool;
  }
  
  public LogMessage getByID(String paramString)
  {
    return (LogMessage)getFirstByFilter("ID=" + paramString);
  }
  
  public void setDateFormat(String paramString)
  {
    this.datetype = paramString;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      LogMessage localLogMessage = (LogMessage)localIterator.next();
      localLogMessage.setDateFormat(this.datetype);
    }
  }
  
  public String getDateFormat()
  {
    return this.datetype;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "LOGMESSAGES");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((LogMessage)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "LOGMESSAGES");
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
      if (paramString.equals("LOGMESSAGE"))
      {
        LogMessage localLogMessage = new LogMessage();
        LogMessages.this.add(localLogMessage);
        localLogMessage.continueXMLParsing(getHandler());
      }
    }
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      LogMessages.this.set(getElement(), str);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.alerts.LogMessages
 * JD-Core Version:    0.7.0.1
 */