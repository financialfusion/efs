package com.ffusion.util.beans.smartcalendar;

import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.DateTime;
import com.ffusion.util.beans.ExtendABean;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

public class SCCalendar
  extends ExtendABean
{
  public static final String SC_CALENDAR = "SC_CALENDAR";
  public static final String CALENDAR_ID = "CALENDAR_ID";
  public static final String CALENDAR_NAME = "CALENDAR_NAME";
  public static final String IGNORE_FOR_TRANSFERS = "IGNORE_FOR_TRANSFERS";
  public static final String SC_BUSINESS_DAYS = "SC_BUSINESS_DAYS";
  public static final String HOLIDAYS = "HOLIDAYS";
  public static final String IS_DEFAULT = "IS_DEFAULT";
  public static final String BUSINESS_DAYS = "BUSINESS_DAYS";
  private int t;
  private String p;
  private SCBusinessDays r;
  private HashMap s;
  private boolean q;
  private boolean o;
  
  public SCCalendar(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public int getCalendarId()
  {
    return this.t;
  }
  
  public void setCalendarId(int paramInt)
  {
    this.t = paramInt;
  }
  
  public String getIgnoreForTransfers()
  {
    return getIgnoreForTransfersValue() + "";
  }
  
  public void setIgnoreForTransfers(String paramString)
  {
    this.o = Boolean.valueOf(paramString).booleanValue();
  }
  
  public boolean getIgnoreForTransfersValue()
  {
    return this.o;
  }
  
  public void setIgnoreForTransfersValue(boolean paramBoolean)
  {
    this.o = paramBoolean;
  }
  
  public String getCalendarName()
  {
    return this.p;
  }
  
  public void setCalendarName(String paramString)
  {
    this.p = paramString;
  }
  
  public SCBusinessDays getBusinessDays()
  {
    return this.r;
  }
  
  public void setBusinessDays(SCBusinessDays paramSCBusinessDays)
  {
    this.r = paramSCBusinessDays;
  }
  
  public HashMap getHolidays()
  {
    return this.s;
  }
  
  public void setHolidays(HashMap paramHashMap)
  {
    this.s = paramHashMap;
  }
  
  public boolean getIsDefault()
  {
    return this.q;
  }
  
  public void setIsDefault(boolean paramBoolean)
  {
    this.q = paramBoolean;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("calendarId=").append(this.t);
    localStringBuffer.append("calendarName=").append(this.p);
    localStringBuffer.append("businessDays=(").append(this.r).append(")");
    localStringBuffer.append("holidays=").append(this.s);
    localStringBuffer.append("isDefault=").append(this.q);
    return localStringBuffer.toString();
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.s != null)
    {
      HashMap localHashMap = new HashMap(this.s.size());
      Set localSet = this.s.keySet();
      Iterator localIterator = localSet.iterator();
      while (localIterator.hasNext())
      {
        DateTime localDateTime = (DateTime)localIterator.next();
        SCHoliday localSCHoliday = (SCHoliday)this.s.get(localDateTime);
        localDateTime.setLocale(paramLocale);
        localSCHoliday.setLocale(paramLocale);
        localHashMap.put(localDateTime, localSCHoliday);
      }
      this.s = localHashMap;
    }
  }
  
  public int compare(Object paramObject, String paramString)
  {
    SCCalendar localSCCalendar = (SCCalendar)paramObject;
    int i = 1;
    if (paramString.equals("CALENDAR_ID"))
    {
      i = this.t - localSCCalendar.getCalendarId();
    }
    else if (paramString.equals("CALENDAR_NAME"))
    {
      i = this.p.compareToIgnoreCase(localSCCalendar.getCalendarName());
    }
    else if (paramString.equals("BUSINESS_DAYS"))
    {
      i = this.r.compare(localSCCalendar.getBusinessDays(), "BUSINESS_DAYS");
      if (i == 0) {
        i = this.r.compare(localSCCalendar.getBusinessDays(), "ACTIONS");
      }
    }
    else if (paramString.equals("HOLIDAYS"))
    {
      i = this.s.equals(localSCCalendar.getHolidays()) ? 0 : 1;
    }
    else if (paramString.equals("IGNORE_FOR_TRANSFERS"))
    {
      i = getIgnoreForTransfers().equals(localSCCalendar.getIgnoreForTransfers()) ? 0 : 1;
    }
    else if (paramString.equals("IS_DEFAULT"))
    {
      i = this.q == localSCCalendar.getIsDefault() ? 0 : 1;
    }
    else
    {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof SCCalendar))
    {
      SCCalendar localSCCalendar = (SCCalendar)paramObject;
      if (localSCCalendar == this) {
        return true;
      }
      if (localSCCalendar.getCalendarId() != getCalendarId()) {
        return false;
      }
    }
    else
    {
      return false;
    }
    return true;
  }
  
  public int hashCode()
  {
    return this.t;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "SC_CALENDAR");
    XMLHandler.appendTag(localStringBuffer, "CALENDAR_ID", this.t);
    if (this.p != null) {
      XMLHandler.appendTag(localStringBuffer, "CALENDAR_NAME", this.p);
    }
    XMLHandler.appendTag(localStringBuffer, "IGNORE_FOR_TRANSFERS", getIgnoreForTransfers());
    if (this.r != null) {
      localStringBuffer.append(this.r.getXML());
    }
    if (this.s != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "HOLIDAYS");
      Iterator localIterator = this.s.values().iterator();
      while (localIterator.hasNext())
      {
        SCHoliday localSCHoliday = (SCHoliday)localIterator.next();
        localStringBuffer.append(localSCHoliday.getXML());
      }
      XMLHandler.appendEndTag(localStringBuffer, "HOLIDAYS");
    }
    XMLHandler.appendTag(localStringBuffer, "IS_DEFAULT", new Boolean(this.q).toString());
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "SC_CALENDAR");
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new InternalXMLHandler(), paramString);
    }
    catch (Throwable localThrowable) {}
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new InternalXMLHandler());
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("CALENDAR_ID")) {
      this.t = Integer.parseInt(paramString2);
    } else if (paramString1.equals("CALENDAR_NAME")) {
      this.p = paramString2;
    } else if (paramString1.equals("IS_DEFAULT")) {
      this.q = Boolean.valueOf(paramString2).booleanValue();
    } else if (paramString1.equals("IGNORE_FOR_TRANSFERS")) {
      setIgnoreForTransfers(paramString2);
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  class InternalXMLHandlerForHolidays
    extends XMLHandler
  {
    private HashMap jdField_new;
    private ArrayList jdField_int;
    
    public InternalXMLHandlerForHolidays(HashMap paramHashMap)
    {
      this.jdField_new = paramHashMap;
      this.jdField_int = new ArrayList();
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("SC_HOLIDAY"))
      {
        SCHoliday localSCHoliday = new SCHoliday(SCCalendar.this.locale);
        localSCHoliday.continueXMLParsing(getHandler());
        this.jdField_int.add(localSCHoliday);
      }
    }
    
    public void endElement(String paramString)
      throws Exception
    {
      if (paramString.equals("HOLIDAYS"))
      {
        Iterator localIterator = this.jdField_int.iterator();
        while (localIterator.hasNext())
        {
          SCHoliday localSCHoliday = (SCHoliday)localIterator.next();
          this.jdField_new.put(localSCHoliday.getDate(), localSCHoliday);
        }
      }
    }
  }
  
  class InternalXMLHandler
    extends XMLHandler
  {
    public InternalXMLHandler() {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      SCCalendar.this.set(getElement(), str);
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("SC_BUSINESS_DAYS"))
      {
        if (SCCalendar.this.r == null) {
          SCCalendar.this.r = new SCBusinessDays(SCCalendar.this.locale);
        }
        SCCalendar.this.r.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("HOLIDAYS"))
      {
        if (SCCalendar.this.s == null) {
          SCCalendar.this.s = new HashMap();
        }
        getHandler().continueWith(new SCCalendar.InternalXMLHandlerForHolidays(SCCalendar.this, SCCalendar.this.s));
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.beans.smartcalendar.SCCalendar
 * JD-Core Version:    0.7.0.1
 */