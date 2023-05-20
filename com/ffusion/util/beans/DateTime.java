package com.ffusion.util.beans;

import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.Filterable;
import com.ffusion.util.LocaleUtil;
import com.ffusion.util.Localeable;
import com.ffusion.util.Sortable;
import com.ffusion.util.Stringable;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import com.ffusion.util.logging.DebugLog;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.logging.Level;

public class DateTime
  extends GregorianCalendar
  implements Sortable, Filterable, Localeable, Stringable, XMLable
{
  public static final String DATETIME = "DATETIME";
  public static final String VALUE = "VALUE";
  private static final String ab = "yyyy.MM.dd.HH.mm.ss.z";
  public static final String SHORT = "SHORT";
  public static final String MEDIUM = "MEDIUM";
  public static final String LONG = "LONG";
  public static final String FULL = "FULL";
  public static final int SECONDS = 0;
  public static final int MINUTES = 1;
  public static final int HOURS = 2;
  public static final int DAYS = 3;
  private String aa;
  private boolean ad = true;
  private String ac;
  private Locale ae;
  
  public DateTime()
  {
    setLocale(null);
    setFormat("SHORT");
  }
  
  public DateTime(String paramString1, Locale paramLocale, String paramString2)
    throws InvalidDateTimeException
  {
    setLocale(paramLocale);
    setFormat(paramString2);
    fromString(paramString1);
  }
  
  public DateTime(String paramString, Locale paramLocale, int paramInt)
    throws InvalidDateTimeException
  {
    setLocale(paramLocale);
    setFormat(paramInt);
    fromString(paramString);
  }
  
  public DateTime(String paramString, Locale paramLocale)
    throws InvalidDateTimeException
  {
    setLocale(paramLocale);
    setFormat(3);
    fromString(paramString);
  }
  
  public DateTime(Calendar paramCalendar, Locale paramLocale)
  {
    setLocale(paramLocale);
    setFormat(3);
    setTime(paramCalendar.getTime());
  }
  
  public DateTime(Date paramDate, Locale paramLocale)
  {
    setLocale(paramLocale);
    setFormat(3);
    setTime(paramDate);
  }
  
  public DateTime(Date paramDate, Locale paramLocale, String paramString)
  {
    setLocale(paramLocale);
    setFormat(paramString);
    setTime(paramDate);
  }
  
  public DateTime(Locale paramLocale)
  {
    setLocale(paramLocale);
    setFormat(3);
  }
  
  public void setDate(String paramString)
  {
    try
    {
      fromString(paramString);
    }
    catch (Exception localException) {}
  }
  
  public void setString(String paramString)
  {
    try
    {
      fromString(paramString);
    }
    catch (Exception localException)
    {
      DebugLog.log(Level.INFO, "InvalidDateException caught: " + localException);
    }
  }
  
  public void fromString(String paramString)
    throws InvalidDateTimeException
  {
    this.aa = paramString;
    if (paramString != null) {
      try
      {
        DateFormat localDateFormat;
        if (getTimeZone() == null) {
          localDateFormat = DateFormatUtil.getFormatter(this.ac, this.ae);
        } else {
          localDateFormat = DateFormatUtil.getFormatter(this.ac, this.ae, getTimeZone());
        }
        Date localDate = localDateFormat.parse(paramString);
        Calendar localCalendar = Calendar.getInstance();
        if (getTimeZone() != null) {
          localCalendar.setTimeZone(getTimeZone());
        }
        localCalendar.setTime(localDate);
        if ((this.ac != null) && ((this.ac.equals("FULL")) || (this.ac.equals("LONG")) || (this.ac.toLowerCase().indexOf("yyyy") != -1)))
        {
          if (localCalendar.get(1) < 1000) {
            throw new ParseException("Invalid year", 0);
          }
          if (localCalendar.get(1) > 9999) {
            throw new ParseException("Invalid year", 0);
          }
        }
        for (int i = 0; i < 17; i++) {
          set(i, localCalendar.get(i));
        }
        this.ad = true;
      }
      catch (ParseException localParseException)
      {
        this.ad = false;
        throw new InvalidDateTimeException(localParseException.getMessage());
      }
    }
  }
  
  public String getString()
  {
    return toString();
  }
  
  public String toString()
  {
    if (this.ad) {
      return DateFormatUtil.getFormatter(this.ac, this.ae).format(getTime());
    }
    return this.aa;
  }
  
  public boolean isValid()
  {
    return this.ad;
  }
  
  public void setLocale(Locale paramLocale)
  {
    if (paramLocale == null) {
      this.ae = LocaleUtil.getDefaultLocale();
    } else {
      this.ae = paramLocale;
    }
  }
  
  public Locale getLocale()
  {
    if (this.ae != null) {
      return this.ae;
    }
    return LocaleUtil.getDefaultLocale();
  }
  
  public void setFormat(String paramString)
  {
    this.ac = paramString;
  }
  
  public void setFormat(int paramInt)
  {
    if (paramInt == 0) {
      setFormat("FULL");
    } else if (paramInt == 2) {
      setFormat("MEDIUM");
    } else if (paramInt == 1) {
      setFormat("LONG");
    } else if (paramInt == 3) {
      setFormat("SHORT");
    } else {
      setFormat("MEDIUM");
    }
  }
  
  public String getFormat()
  {
    return this.ac;
  }
  
  public int compare(Object paramObject, String paramString)
  {
    DateTime localDateTime = (DateTime)paramObject;
    return equals(localDateTime) ? 0 : before(localDateTime) ? -1 : 1;
  }
  
  public int compare(DateTime paramDateTime)
  {
    long l = getTimeInMillis() - paramDateTime.getTimeInMillis();
    if (l > 0L) {
      return 1;
    }
    if (l < 0L) {
      return -1;
    }
    return 0;
  }
  
  public boolean isFilterable(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "=<>!", true);
    String str1 = null;
    String str2 = null;
    String str3 = null;
    if ((localStringTokenizer.countTokens() == 3) || (localStringTokenizer.countTokens() == 4))
    {
      str1 = localStringTokenizer.nextToken();
      str2 = localStringTokenizer.nextToken();
      str3 = localStringTokenizer.nextToken();
      if (localStringTokenizer.countTokens() == 1)
      {
        str2 = str2 + str3;
        str3 = localStringTokenizer.nextToken();
      }
    }
    else
    {
      str2 = "=";
      str3 = paramString;
    }
    try
    {
      DateTime localDateTime = new DateTime(str3, this.ae, this.ac);
      if (str2.equals("=")) {
        return toString().equals(localDateTime.toString());
      }
      if (str2.equals("==")) {
        return toString().equalsIgnoreCase(localDateTime.toString());
      }
      if (str2.equals("!")) {
        return !toString().equals(localDateTime.toString());
      }
      if (str2.equals("!!")) {
        return !toString().equalsIgnoreCase(localDateTime.toString());
      }
      if (((str2.equals("=>")) || (str2.equals(">="))) && ((after(localDateTime)) || (toString().equals(localDateTime.toString())))) {
        return true;
      }
      if (((str2.equals("=<")) || (str2.equals("<="))) && ((before(localDateTime)) || (toString().equals(localDateTime.toString())))) {
        return true;
      }
      if ((str2.equals("<")) || (str2.equals("<<"))) {
        return before(localDateTime);
      }
      if ((str2.equals(">")) || (str2.equals(">>")))
      {
        if ((localDateTime.get(10) == 0) && (localDateTime.get(12) == 0) && (localDateTime.get(13) == 0))
        {
          localDateTime.set(11, 23);
          localDateTime.set(12, 59);
          localDateTime.set(13, 59);
        }
        return after(localDateTime);
      }
    }
    catch (Exception localException) {}
    return false;
  }
  
  public String toXMLFormat()
  {
    return DateFormatUtil.getFormatter("yyyy.MM.dd.HH.mm.ss.z").format(getTime());
  }
  
  public void fromXMLFormat(String paramString)
  {
    try
    {
      Date localDate = DateFormatUtil.getFormatter("yyyy.MM.dd.HH.mm.ss.z").parse(paramString);
      setTime(localDate);
      this.ad = true;
    }
    catch (Throwable localThrowable)
    {
      this.ad = false;
    }
  }
  
  public boolean isSameDayInYearAs(DateTime paramDateTime)
  {
    boolean bool = false;
    if ((get(6) == paramDateTime.get(6)) && (get(1) == paramDateTime.get(1))) {
      bool = true;
    }
    return bool;
  }
  
  public float getDiff(Calendar paramCalendar, int paramInt)
  {
    if (paramCalendar == null) {
      return -1.0F;
    }
    Date localDate1 = getTime();
    Date localDate2 = paramCalendar.getTime();
    long l1 = localDate1.getTime();
    long l2 = localDate2.getTime();
    if (paramInt == 0) {
      return (float)(l1 - l2) / 1000.0F;
    }
    if (paramInt == 1) {
      return (float)(l1 - l2) / 60000.0F;
    }
    if (paramInt == 2) {
      return (float)(l1 - l2) / 3600000.0F;
    }
    if (paramInt == 3) {
      return (float)(l1 - l2) / 86400000.0F;
    }
    return -1.0F;
  }
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
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
  
  public String toXML()
  {
    return getXML();
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "DATETIME");
    XMLHandler.appendTag(localStringBuffer, "VALUE", DateFormatUtil.getFormatter("yyyy.MM.dd.HH.mm.ss.z").format(getTime()));
    XMLHandler.appendEndTag(localStringBuffer, "DATETIME");
    return localStringBuffer.toString();
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new InternalXMLHandler());
  }
  
  public class InternalXMLHandler
    extends XMLHandler
  {
    public InternalXMLHandler() {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      if ((str.length() > 0) && (getElement().equals("VALUE"))) {
        try
        {
          Date localDate = DateFormatUtil.getFormatter("yyyy.MM.dd.HH.mm.ss.z").parse(str);
          DateTime.this.setTime(localDate);
        }
        catch (Throwable localThrowable) {}
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.beans.DateTime
 * JD-Core Version:    0.7.0.1
 */