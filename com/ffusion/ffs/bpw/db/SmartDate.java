package com.ffusion.ffs.bpw.db;

import java.util.Calendar;

public class SmartDate
{
  public int _thisDay;
  public int _skipMode;
  public int _payDay;
  public int _preDay;
  public int _sucDay;
  public static final int PRECEDING = -1;
  public static final int SAMEDAY = 0;
  public static final int SUCEEDING = 1;
  
  public SmartDate() {}
  
  public SmartDate(int paramInt1, int paramInt2)
  {
    this._thisDay = paramInt1;
    this._skipMode = paramInt2;
  }
  
  public int computePreDay(int paramInt)
  {
    int i = paramInt;
    int j = i / 10000;
    i %= 10000;
    int k = i / 100 - 1;
    i %= 100;
    int m = i;
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.set(1, j);
    localCalendar.set(2, k);
    localCalendar.set(5, m);
    localCalendar.add(6, -1);
    int n = localCalendar.get(1) * 10000 + (localCalendar.get(2) + 1) * 100 + localCalendar.get(5);
    return n;
  }
  
  public int computeSucDay(int paramInt)
  {
    int i = paramInt;
    int j = i / 10000;
    i %= 10000;
    int k = i / 100 - 1;
    i %= 100;
    int m = i;
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.set(1, j);
    localCalendar.set(2, k);
    localCalendar.set(5, m);
    localCalendar.add(6, 1);
    int n = localCalendar.get(1) * 10000 + (localCalendar.get(2) + 1) * 100 + localCalendar.get(5);
    return n;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.SmartDate
 * JD-Core Version:    0.7.0.1
 */