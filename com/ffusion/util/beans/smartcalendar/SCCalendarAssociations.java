package com.ffusion.util.beans.smartcalendar;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.util.Locale;

public class SCCalendarAssociations
  extends FilteredList
  implements XMLable
{
  public static final String SC_CALENDAR_ASSOCIATIONS = "SC_CALENDAR_ASSOCIATIONS";
  
  public SCCalendarAssociations(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public SCCalendarAssociation add()
  {
    SCCalendarAssociation localSCCalendarAssociation = new SCCalendarAssociation(this.locale);
    add(localSCCalendarAssociation);
    return localSCCalendarAssociation;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof SCCalendarAssociations)) {
      return false;
    }
    SCCalendarAssociations localSCCalendarAssociations = (SCCalendarAssociations)paramObject;
    if (size() != localSCCalendarAssociations.size()) {
      return false;
    }
    Locale localLocale1 = this.locale;
    Locale localLocale2 = localSCCalendarAssociations.locale;
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
    return containsAll(localSCCalendarAssociations);
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
    XMLHandler.appendBeginTag(localStringBuffer, "SC_CALENDAR_ASSOCIATIONS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((SCCalendarAssociation)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "SC_CALENDAR_ASSOCIATIONS");
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
      if (paramString.equals("SC_CALENDAR_ASSOCIATION"))
      {
        SCCalendarAssociation localSCCalendarAssociation = new SCCalendarAssociation(SCCalendarAssociations.this.locale);
        SCCalendarAssociations.this.add(localSCCalendarAssociation);
        localSCCalendarAssociation.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.beans.smartcalendar.SCCalendarAssociations
 * JD-Core Version:    0.7.0.1
 */