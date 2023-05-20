package com.ffusion.alert.interfaces;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class AEScheduleInfo
  implements Serializable
{
  public static final long SEC = 1000L;
  public static final long MIN = 60000L;
  public static final long HRS = 3600000L;
  public static final long DAY = 86400000L;
  public static final long WEEK = 604800000L;
  public static final int SI_INTERVAL_ABSOLUTE = 0;
  public static final int SI_INTERVAL_MONTH_START = 1;
  public static final int SI_INTERVAL_MONTH_END = 2;
  public static final int SI_INTERVAL_SEMI_MONTHLY = 3;
  private static final long[] jdField_try = { -1L, 1L, 2L, 4L, 8L, 16L, 32L, 64L, 128L, 256L, 512L, 1024L, 2048L, 4096L, 8192L, 16384L, 32768L, 65536L, 131072L, 262144L, 524288L, 1048576L, 2097152L, 4194304L, 8388608L, 16777216L, 33554432L, 67108864L, 134217728L, 268435456L, 536870912L, 1073741824L, -2147483648L };
  private long jdField_do;
  private long jdField_new;
  private long jdField_if;
  private int jdField_int;
  private SimpleTimeZone jdField_for;
  private TimeZone jdField_byte;
  private boolean a;
  
  public AEScheduleInfo(Date paramDate1, Date paramDate2, long paramLong, SimpleTimeZone paramSimpleTimeZone)
  {
    this(paramDate1.getTime(), paramDate2.getTime(), paramLong, paramSimpleTimeZone);
  }
  
  public AEScheduleInfo(Date paramDate1, Date paramDate2, long paramLong, TimeZone paramTimeZone)
  {
    this(paramDate1.getTime(), paramDate2.getTime(), paramLong, paramTimeZone);
  }
  
  public AEScheduleInfo(long paramLong1, long paramLong2, long paramLong3, SimpleTimeZone paramSimpleTimeZone)
  {
    this(paramLong1, paramLong2, paramLong3, 0, paramSimpleTimeZone, true);
  }
  
  public AEScheduleInfo(long paramLong1, long paramLong2, long paramLong3, TimeZone paramTimeZone)
  {
    this(paramLong1, paramLong2, paramLong3, 0, paramTimeZone, true);
  }
  
  public AEScheduleInfo(long paramLong1, long paramLong2, long paramLong3, int paramInt, SimpleTimeZone paramSimpleTimeZone, boolean paramBoolean)
  {
    this.jdField_do = paramLong1;
    this.jdField_new = paramLong2;
    this.jdField_if = paramLong3;
    this.jdField_int = paramInt;
    this.jdField_byte = paramSimpleTimeZone;
    this.jdField_for = paramSimpleTimeZone;
    this.a = paramBoolean;
  }
  
  public AEScheduleInfo(long paramLong1, long paramLong2, long paramLong3, int paramInt, TimeZone paramTimeZone, boolean paramBoolean)
  {
    this.jdField_do = paramLong1;
    this.jdField_new = paramLong2;
    this.jdField_if = paramLong3;
    this.jdField_int = paramInt;
    this.jdField_byte = paramTimeZone;
    this.a = paramBoolean;
  }
  
  public AEScheduleInfo(Date paramDate1, Date paramDate2, long paramLong, int paramInt, SimpleTimeZone paramSimpleTimeZone, boolean paramBoolean)
  {
    this(paramDate1.getTime(), paramDate2.getTime(), paramLong, paramInt, paramSimpleTimeZone, paramBoolean);
  }
  
  public AEScheduleInfo(Date paramDate1, Date paramDate2, long paramLong, int paramInt, TimeZone paramTimeZone, boolean paramBoolean)
  {
    this(paramDate1.getTime(), paramDate2.getTime(), paramLong, paramInt, paramTimeZone, paramBoolean);
  }
  
  public AEScheduleInfo(long paramLong1, long paramLong2, int paramInt1, int paramInt2, SimpleTimeZone paramSimpleTimeZone)
  {
    this.jdField_do = paramLong1;
    this.jdField_new = paramLong2;
    this.jdField_if = a(paramInt1, paramInt2);
    this.jdField_int = 3;
    this.jdField_byte = paramSimpleTimeZone;
    this.jdField_for = paramSimpleTimeZone;
    GregorianCalendar localGregorianCalendar = new GregorianCalendar(this.jdField_byte);
    localGregorianCalendar.setTime(new Date(paramLong1));
    int i = localGregorianCalendar.get(5);
    int j = getFirstSemiMonthlyDay();
    int k = getSecondSemiMonthlyDay();
    if ((j != -1) && (k != -1))
    {
      if ((i != j) && (i != k)) {
        if (i < j)
        {
          localGregorianCalendar.set(5, j);
        }
        else if (i < k)
        {
          localGregorianCalendar.set(5, k);
        }
        else if (k < i)
        {
          localGregorianCalendar.add(2, 1);
          localGregorianCalendar.set(5, j);
        }
      }
      this.jdField_do = localGregorianCalendar.getTime().getTime();
    }
  }
  
  public AEScheduleInfo(long paramLong1, long paramLong2, int paramInt1, int paramInt2, TimeZone paramTimeZone)
  {
    this.jdField_do = paramLong1;
    this.jdField_new = paramLong2;
    this.jdField_if = a(paramInt1, paramInt2);
    this.jdField_int = 3;
    this.jdField_byte = paramTimeZone;
    GregorianCalendar localGregorianCalendar = new GregorianCalendar(this.jdField_byte);
    localGregorianCalendar.setTime(new Date(paramLong1));
    int i = localGregorianCalendar.get(5);
    int j = getFirstSemiMonthlyDay();
    int k = getSecondSemiMonthlyDay();
    if ((j != -1) && (k != -1))
    {
      if ((i != j) && (i != k)) {
        if (i < j)
        {
          localGregorianCalendar.set(5, j);
        }
        else if (i < k)
        {
          localGregorianCalendar.set(5, k);
        }
        else if (k < i)
        {
          localGregorianCalendar.add(2, 1);
          localGregorianCalendar.set(5, j);
        }
      }
      this.jdField_do = localGregorianCalendar.getTime().getTime();
    }
  }
  
  public AEScheduleInfo(Date paramDate1, Date paramDate2, int paramInt1, int paramInt2, SimpleTimeZone paramSimpleTimeZone)
  {
    this(paramDate1.getTime(), paramDate2.getTime(), paramInt1, paramInt2, paramSimpleTimeZone);
  }
  
  public AEScheduleInfo(Date paramDate1, Date paramDate2, int paramInt1, int paramInt2, TimeZone paramTimeZone)
  {
    this(paramDate1.getTime(), paramDate2.getTime(), paramInt1, paramInt2, paramTimeZone);
  }
  
  public Date getStartDate()
  {
    return new Date(this.jdField_do);
  }
  
  public long getStart()
  {
    return this.jdField_do;
  }
  
  public Date getEndDate()
  {
    return new Date(this.jdField_new);
  }
  
  public long getEnd()
  {
    return this.jdField_new;
  }
  
  public long getInterval()
  {
    return this.jdField_if;
  }
  
  public int getIntervalType()
  {
    return this.jdField_int;
  }
  
  public SimpleTimeZone getTimeZone()
  {
    return this.jdField_for;
  }
  
  public TimeZone getRegularTimeZone()
  {
    return this.jdField_byte;
  }
  
  public boolean respectsDST()
  {
    return this.a;
  }
  
  public Date getNextDateFromLast(Date paramDate)
  {
    if (paramDate == null) {
      return null;
    }
    long l = getNextFromLast(paramDate.getTime());
    return l == 0L ? null : new Date(l);
  }
  
  public Date getNextDate(Date paramDate)
  {
    if (paramDate == null) {
      return null;
    }
    long l = getNext(paramDate.getTime());
    return l == 0L ? null : new Date(l);
  }
  
  private void a(Calendar paramCalendar)
  {
    paramCalendar.set(paramCalendar.get(1), paramCalendar.get(2), paramCalendar.getActualMinimum(5), paramCalendar.getActualMinimum(11), paramCalendar.getActualMinimum(12), paramCalendar.getActualMinimum(13));
    paramCalendar.set(14, paramCalendar.getActualMinimum(14));
  }
  
  private void jdField_if(Calendar paramCalendar)
  {
    paramCalendar.set(paramCalendar.get(1), paramCalendar.get(2), paramCalendar.getActualMaximum(5), paramCalendar.getActualMaximum(11), paramCalendar.getActualMaximum(12), paramCalendar.getActualMaximum(13));
    paramCalendar.set(14, paramCalendar.getActualMaximum(14));
  }
  
  private void a(Calendar paramCalendar, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    paramCalendar.set(5, paramInt1);
    paramCalendar.set(11, paramInt2);
    paramCalendar.set(12, paramInt3);
    paramCalendar.set(13, paramInt4);
    paramCalendar.set(14, paramInt5);
  }
  
  private int a(TimeZone paramTimeZone)
  {
    if (paramTimeZone.useDaylightTime()) {
      return 3600000;
    }
    return 0;
  }
  
  private long a(long paramLong1, long paramLong2, boolean paramBoolean)
  {
    boolean bool1 = this.jdField_byte.inDaylightTime(new Date(paramLong1));
    boolean bool2 = this.jdField_byte.inDaylightTime(new Date(paramLong2));
    long l1 = 0L;
    long l2;
    if (bool2 != bool1)
    {
      l2 = a(this.jdField_byte);
      l1 = bool1 ? l2 : -l2;
      if (bool2 != this.jdField_byte.inDaylightTime(new Date(paramLong2 + l1))) {
        l1 = 0L;
      }
    }
    else if ((bool1) && (bool2) && (paramBoolean))
    {
      l2 = a(this.jdField_byte);
      if (!this.jdField_byte.inDaylightTime(new Date(paramLong1 - l2))) {
        l1 = -l2;
      }
    }
    return l1;
  }
  
  public long getNextFromLast(long paramLong)
  {
    if (paramLong >= this.jdField_new) {
      return 0L;
    }
    if (paramLong < this.jdField_do) {
      return this.jdField_do;
    }
    GregorianCalendar localGregorianCalendar = new GregorianCalendar(this.jdField_byte);
    Date localDate = new Date(this.jdField_do);
    long l1 = 0L;
    int i;
    int j;
    int k;
    int m;
    long l2;
    long l3;
    long l5;
    long l6;
    long l7;
    switch (this.jdField_int)
    {
    case 0: 
      l1 = paramLong + this.jdField_if;
      if ((this.a) && (this.jdField_byte.useDaylightTime()) && (this.jdField_if > 2 * a(this.jdField_byte))) {
        l1 += a(paramLong, l1, true);
      }
      break;
    case 1: 
      localGregorianCalendar.setTime(localDate);
      i = localGregorianCalendar.get(11);
      j = localGregorianCalendar.get(12);
      k = localGregorianCalendar.get(13);
      m = localGregorianCalendar.get(14);
      a(localGregorianCalendar);
      l2 = localGregorianCalendar.getTime().getTime();
      l3 = this.jdField_do - l2;
      localGregorianCalendar.setTime(new Date(paramLong));
      localGregorianCalendar.add(2, (int)this.jdField_if);
      l5 = localGregorianCalendar.getTime().getTime();
      a(localGregorianCalendar);
      l6 = localGregorianCalendar.getTime().getTime();
      jdField_if(localGregorianCalendar);
      l7 = localGregorianCalendar.getTime().getTime();
      l1 = l6 + l3;
      if (l1 > l7)
      {
        localGregorianCalendar.setTime(new Date(l5));
        a(localGregorianCalendar, localGregorianCalendar.getActualMaximum(5), i, j, k, m);
        l1 = localGregorianCalendar.getTime().getTime();
        if ((this.a) && (this.jdField_byte.useDaylightTime())) {
          l1 -= a(l6, l1, false);
        }
      }
      if ((this.a) && (this.jdField_byte.useDaylightTime())) {
        l1 += a(l6, l1, true);
      }
      break;
    case 2: 
      localGregorianCalendar.setTime(localDate);
      i = localGregorianCalendar.get(11);
      j = localGregorianCalendar.get(12);
      k = localGregorianCalendar.get(13);
      m = localGregorianCalendar.get(14);
      jdField_if(localGregorianCalendar);
      l2 = localGregorianCalendar.getTime().getTime();
      l3 = l2 - this.jdField_do;
      localGregorianCalendar.setTime(new Date(paramLong));
      localGregorianCalendar.add(2, (int)this.jdField_if);
      l5 = localGregorianCalendar.getTime().getTime();
      jdField_if(localGregorianCalendar);
      l6 = localGregorianCalendar.getTime().getTime();
      a(localGregorianCalendar);
      l7 = localGregorianCalendar.getTime().getTime();
      l1 = l6 - l3;
      if (l1 < l7)
      {
        localGregorianCalendar.setTime(new Date(l5));
        a(localGregorianCalendar, 1, i, j, k, m);
        l1 = localGregorianCalendar.getTime().getTime();
        if ((this.a) && (this.jdField_byte.useDaylightTime())) {
          l1 += a(l1, l6, false);
        }
      }
      if ((this.a) && (this.jdField_byte.useDaylightTime())) {
        l1 -= a(l1, l6, false);
      }
      break;
    case 3: 
      localGregorianCalendar.setTime(localDate);
      i = localGregorianCalendar.get(11);
      j = localGregorianCalendar.get(12);
      k = localGregorianCalendar.get(13);
      m = localGregorianCalendar.get(14);
      localGregorianCalendar.setTime(new Date(paramLong));
      l2 = localGregorianCalendar.getTime().getTime();
      int n = getFirstSemiMonthlyDay();
      if (n == -1) {
        return 0L;
      }
      if (localGregorianCalendar.getActualMaximum(5) < n) {
        n = localGregorianCalendar.getActualMaximum(5);
      }
      a(localGregorianCalendar, n, 0, 0, 0, 0);
      long l4 = localGregorianCalendar.getTime().getTime();
      localGregorianCalendar.setTime(new Date(paramLong));
      int i1 = getSecondSemiMonthlyDay();
      if (i1 == -1) {
        return 0L;
      }
      if (localGregorianCalendar.getActualMaximum(5) < i1) {
        i1 = localGregorianCalendar.getActualMaximum(5);
      }
      a(localGregorianCalendar, i1, 0, 0, 0, 0);
      l6 = localGregorianCalendar.getTime().getTime();
      localGregorianCalendar.setTime(new Date(paramLong));
      if (l6 <= l2)
      {
        localGregorianCalendar.add(2, 1);
        n = getFirstSemiMonthlyDay();
        if (localGregorianCalendar.getActualMaximum(5) < n) {
          n = localGregorianCalendar.getActualMaximum(5);
        }
        a(localGregorianCalendar, n, i, j, k, m);
      }
      else if (l4 <= l2)
      {
        a(localGregorianCalendar, i1, i, j, k, m);
      }
      else
      {
        a(localGregorianCalendar, n, i, j, k, m);
      }
      l1 = localGregorianCalendar.getTime().getTime();
      break;
    }
    if (l1 >= this.jdField_new) {
      return 0L;
    }
    return l1;
  }
  
  public int getFirstSemiMonthlyDay()
  {
    if (this.jdField_int != 3) {
      return -1;
    }
    int i = 0;
    int j = 0;
    for (int k = 1; k < jdField_try.length; k++) {
      if ((this.jdField_if & jdField_try[k]) != 0L)
      {
        if (i == 0) {
          j = k;
        }
        i++;
      }
    }
    if (i == 2) {
      return j;
    }
    return -1;
  }
  
  public int getSecondSemiMonthlyDay()
  {
    if (this.jdField_int != 3) {
      return -1;
    }
    int i = 0;
    int j = 0;
    for (int k = jdField_try.length - 1; k > 0; k--) {
      if ((this.jdField_if & jdField_try[k]) != 0L)
      {
        if (i == 0) {
          j = k;
        }
        i++;
      }
    }
    if (i == 2) {
      return j;
    }
    return -1;
  }
  
  private static long a(int paramInt1, int paramInt2)
  {
    int i = 0;
    if ((1 <= paramInt1) && (paramInt1 <= 31)) {
      i = (int)(i | jdField_try[paramInt1]);
    }
    if ((1 <= paramInt2) && (paramInt2 <= 31)) {
      i = (int)(i | jdField_try[paramInt2]);
    }
    return i;
  }
  
  public long getNext(long paramLong)
  {
    if (paramLong < this.jdField_do) {
      return this.jdField_do;
    }
    if (paramLong >= this.jdField_new) {
      return 0L;
    }
    long l = this.jdField_do;
    do
    {
      l = getNextFromLast(l);
    } while ((l <= paramLong) && (l != 0L));
    return l;
  }
  
  public boolean equals(Object paramObject)
  {
    try
    {
      AEScheduleInfo localAEScheduleInfo = (AEScheduleInfo)paramObject;
      if ((this.jdField_do == localAEScheduleInfo.jdField_do) && (this.jdField_new == localAEScheduleInfo.jdField_new) && (this.jdField_if == localAEScheduleInfo.jdField_if) && (this.jdField_int == localAEScheduleInfo.jdField_int) && (this.jdField_byte.hasSameRules(localAEScheduleInfo.jdField_byte)) && (this.a == localAEScheduleInfo.a)) {
        return true;
      }
    }
    catch (Exception localException) {}
    return false;
  }
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.interfaces.AEScheduleInfo
 * JD-Core Version:    0.7.0.1
 */