package com.ffusion.util.beans.smartcalendar;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.util.Locale;

public class SCCalendars
  extends FilteredList
  implements XMLable
{
  public static final String SC_CALENDARS = "SC_CALENDARS";
  
  public SCCalendars(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public SCCalendar add()
  {
    SCCalendar localSCCalendar = new SCCalendar(this.locale);
    add(localSCCalendar);
    return localSCCalendar;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof SCCalendars)) {
      return false;
    }
    SCCalendars localSCCalendars = (SCCalendars)paramObject;
    if (size() != localSCCalendars.size()) {
      return false;
    }
    Locale localLocale1 = this.locale;
    Locale localLocale2 = localSCCalendars.locale;
    if (localLocale1 != null)
    {
      if (localLocale2 != null)
      {
        if (!localLocale1.equals(localLocale2)) {
          return false;
        }
      }
      else {
        return false;
      }
    }
    else if (localLocale2 != null) {
      return false;
    }
    return containsAll(localSCCalendars);
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
    XMLHandler.appendBeginTag(localStringBuffer, "SC_CALENDARS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((SCCalendar)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "SC_CALENDARS");
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new InternalXMLHandler(), paramString);
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new InternalXMLHandler());
  }
  
  class InternalXMLHandler
    extends XMLHandler
  {
    public InternalXMLHandler() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equals("SC_CALENDAR"))
      {
        SCCalendar localSCCalendar = new SCCalendar(SCCalendars.this.locale);
        SCCalendars.this.add(localSCCalendar);
        localSCCalendar.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.beans.smartcalendar.SCCalendars
 * JD-Core Version:    0.7.0.1
 */