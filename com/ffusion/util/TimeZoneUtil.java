package com.ffusion.util;

import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class TimeZoneUtil
{
  public static SimpleTimeZone getDefaultTimeZone()
  {
    TimeZone localTimeZone = TimeZone.getDefault();
    SimpleTimeZone localSimpleTimeZone = null;
    if (localTimeZone != null) {
      localSimpleTimeZone = new SimpleTimeZone(localTimeZone.getRawOffset(), localTimeZone.getID());
    }
    return localSimpleTimeZone;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.TimeZoneUtil
 * JD-Core Version:    0.7.0.1
 */