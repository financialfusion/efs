package com.ffusion.beans.util;

import java.util.Calendar;
import java.util.Date;

public class UniqueIDGenerator
{
  private static String jdField_do = "01";
  private static long a = 0L;
  private static long jdField_if = 0L;
  
  public static void initialize(String paramString)
  {
    jdField_do = paramString;
  }
  
  public static synchronized String getNextID()
  {
    long l1 = System.currentTimeMillis();
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.set(11, 0);
    localCalendar.set(12, 0);
    localCalendar.set(13, 0);
    localCalendar.set(14, 0);
    long l2 = localCalendar.getTime().getTime();
    long l3 = Math.round((float)((l1 - l2) / 1000L));
    if (l3 == a) {
      jdField_if += 1L;
    } else {
      jdField_if = 0L;
    }
    a = l3;
    return jdField_do + l3 + jdField_if;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.util.UniqueIDGenerator
 * JD-Core Version:    0.7.0.1
 */