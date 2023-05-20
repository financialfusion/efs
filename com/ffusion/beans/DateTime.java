package com.ffusion.beans;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateTime
  extends com.ffusion.util.beans.DateTime
{
  public DateTime() {}
  
  public DateTime(String paramString1, Locale paramLocale, TimeZone paramTimeZone, String paramString2)
    throws InvalidDateTimeException
  {
    try
    {
      setLocale(paramLocale);
      setFormat(paramString2);
      setTimeZone(paramTimeZone);
      fromString(paramString1);
    }
    catch (com.ffusion.util.beans.InvalidDateTimeException localInvalidDateTimeException)
    {
      throw new InvalidDateTimeException(localInvalidDateTimeException.getMessage());
    }
  }
  
  public DateTime(String paramString1, Locale paramLocale, String paramString2)
    throws InvalidDateTimeException
  {
    try
    {
      setLocale(paramLocale);
      setFormat(paramString2);
      fromString(paramString1);
    }
    catch (com.ffusion.util.beans.InvalidDateTimeException localInvalidDateTimeException)
    {
      throw new InvalidDateTimeException(localInvalidDateTimeException.getMessage());
    }
  }
  
  public DateTime(String paramString, Locale paramLocale, int paramInt)
    throws InvalidDateTimeException
  {
    try
    {
      setLocale(paramLocale);
      setFormat(paramInt);
      fromString(paramString);
    }
    catch (com.ffusion.util.beans.InvalidDateTimeException localInvalidDateTimeException)
    {
      throw new InvalidDateTimeException(localInvalidDateTimeException.getMessage());
    }
  }
  
  public DateTime(String paramString, Locale paramLocale)
    throws InvalidDateTimeException
  {
    try
    {
      setLocale(paramLocale);
      setFormat(3);
      fromString(paramString);
    }
    catch (com.ffusion.util.beans.InvalidDateTimeException localInvalidDateTimeException)
    {
      throw new InvalidDateTimeException(localInvalidDateTimeException.getMessage());
    }
  }
  
  public DateTime(Calendar paramCalendar, Locale paramLocale)
  {
    super(paramCalendar, paramLocale);
  }
  
  public DateTime(Date paramDate, Locale paramLocale)
  {
    super(paramDate, paramLocale);
  }
  
  public DateTime(Date paramDate, Locale paramLocale, String paramString)
  {
    super(paramDate, paramLocale, paramString);
  }
  
  public DateTime(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public void fromString(String paramString)
    throws InvalidDateTimeException
  {
    try
    {
      super.fromString(paramString);
    }
    catch (com.ffusion.util.beans.InvalidDateTimeException localInvalidDateTimeException)
    {
      throw new InvalidDateTimeException(localInvalidDateTimeException.getMessage());
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.DateTime
 * JD-Core Version:    0.7.0.1
 */