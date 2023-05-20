package com.ffusion.ffs.bpw.interfaces;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class BPWCalendar
  extends GregorianCalendar
{
  public BPWCalendar() {}
  
  public BPWCalendar(Date paramDate)
  {
    setTime(paramDate);
  }
  
  public BPWCalendar(int paramInt1, int paramInt2, int paramInt3)
  {
    super(paramInt1, paramInt2, paramInt3);
  }
  
  public boolean before(BPWCalendar paramBPWCalendar)
  {
    if (get(1) < paramBPWCalendar.get(1)) {
      return true;
    }
    if (get(1) == paramBPWCalendar.get(1))
    {
      if (get(2) < paramBPWCalendar.get(2)) {
        return true;
      }
      if ((get(2) == paramBPWCalendar.get(2)) && (get(5) < paramBPWCalendar.get(5))) {
        return true;
      }
    }
    return false;
  }
  
  public boolean after(BPWCalendar paramBPWCalendar)
  {
    if (get(1) > paramBPWCalendar.get(1)) {
      return true;
    }
    if (get(1) == paramBPWCalendar.get(1))
    {
      if (get(2) > paramBPWCalendar.get(2)) {
        return true;
      }
      if ((get(2) == paramBPWCalendar.get(2)) && (get(5) > paramBPWCalendar.get(5))) {
        return true;
      }
    }
    return false;
  }
  
  public boolean equals(Calendar paramCalendar)
  {
    if (get(1) != paramCalendar.get(1)) {
      return false;
    }
    if (get(2) != paramCalendar.get(2)) {
      return false;
    }
    return get(5) == paramCalendar.get(5);
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.BPWCalendar
 * JD-Core Version:    0.7.0.1
 */