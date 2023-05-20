package com.ffusion.util.beans.smartcalendar;

import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.DateTime;
import com.ffusion.util.beans.ExtendABean;
import java.util.Locale;

public class SCHoliday
  extends ExtendABean
{
  public static final String SC_HOLIDAY = "SC_HOLIDAY";
  public static final String ACTION = "ACTION";
  public static final String NAME = "NAME";
  public static final String DATE = "DATE";
  private DateTime n;
  private String m;
  private char l;
  
  public SCHoliday(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public DateTime getDate()
  {
    return this.n;
  }
  
  public void setDate(DateTime paramDateTime)
  {
    this.n = paramDateTime;
  }
  
  public String getName()
  {
    return this.m;
  }
  
  public void setName(String paramString)
  {
    this.m = paramString;
  }
  
  public char getAction()
  {
    return this.l;
  }
  
  public void setAction(char paramChar)
  {
    this.l = paramChar;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("date=").append(this.n);
    localStringBuffer.append("name=").append(this.m);
    localStringBuffer.append("action=").append(this.l);
    return localStringBuffer.toString();
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.n != null) {
      this.n.setLocale(paramLocale);
    }
  }
  
  public int compare(Object paramObject, String paramString)
  {
    SCHoliday localSCHoliday = (SCHoliday)paramObject;
    int i = 1;
    if (paramString.equals("DATE")) {
      i = this.n.compare(localSCHoliday.getDate());
    } else if (paramString.equals("NAME")) {
      i = this.m.compareToIgnoreCase(localSCHoliday.getName());
    } else if (paramString.equals("ACTION")) {
      i = this.l - localSCHoliday.getAction();
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "SC_HOLIDAY");
    if (this.n != null) {
      localStringBuffer.append(this.n.getXML());
    }
    if (this.m != null) {
      XMLHandler.appendTag(localStringBuffer, "NAME", this.m);
    }
    XMLHandler.appendTag(localStringBuffer, "ACTION", String.valueOf(this.l));
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "SC_HOLIDAY");
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
    if (paramString1.equals("DATE"))
    {
      try
      {
        setDate(new DateTime(paramString2, this.locale));
      }
      catch (Exception localException)
      {
        throw new IllegalArgumentException();
      }
    }
    else if (paramString1.equals("NAME"))
    {
      setName(paramString2);
    }
    else if (paramString1.equals("ACTION"))
    {
      if (paramString2.length() != 1) {
        throw new IllegalArgumentException();
      }
      setAction(paramString2.charAt(0));
    }
    else
    {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  class InternalXMLHandler
    extends XMLHandler
  {
    public InternalXMLHandler() {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      SCHoliday.this.set(getElement(), str);
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("DATETIME"))
      {
        if (SCHoliday.this.n == null) {
          SCHoliday.this.n = new DateTime(SCHoliday.this.locale);
        }
        SCHoliday.this.n.continueXMLParsing(getHandler());
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.beans.smartcalendar.SCHoliday
 * JD-Core Version:    0.7.0.1
 */