package com.ffusion.util.smartcalendar.adapter;

import com.ffusion.util.beans.BankIdentifier;
import com.ffusion.util.beans.smartcalendar.SCCalendar;
import com.ffusion.util.beans.smartcalendar.SCCalendars;
import com.ffusion.util.smartcalendar.SCException;
import java.util.Date;
import java.util.HashMap;

public abstract interface ISCAdapter
{
  public abstract void initialize(HashMap paramHashMap)
    throws SCException;
  
  public abstract boolean isBankingDay(String paramString, BankIdentifier paramBankIdentifier, Date paramDate, HashMap paramHashMap)
    throws SCException;
  
  public abstract boolean isHoliday(String paramString, BankIdentifier paramBankIdentifier, Date paramDate, HashMap paramHashMap)
    throws SCException;
  
  public abstract Date getPreviousDay(String paramString, BankIdentifier paramBankIdentifier, Date paramDate, HashMap paramHashMap)
    throws SCException;
  
  public abstract Date getOffsetBankingDay(String paramString, BankIdentifier paramBankIdentifier, Date paramDate, int paramInt, HashMap paramHashMap)
    throws SCException;
  
  public abstract Date getTransactionDay(String paramString, BankIdentifier paramBankIdentifier, Date paramDate, HashMap paramHashMap)
    throws SCException;
  
  public abstract void deleteCalendarHolidaysForDate(SCCalendar paramSCCalendar, Date paramDate, HashMap paramHashMap)
    throws SCException;
  
  public abstract SCCalendar addCalendar(String paramString, SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws SCException;
  
  public abstract void modifyCalendar(SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws SCException;
  
  public abstract void modifyCalendarName(SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws SCException;
  
  public abstract void modifyCalendarBusinessDays(SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws SCException;
  
  public abstract void modifyCalendarHolidays(SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws SCException;
  
  public abstract void deleteCalendar(SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws SCException;
  
  public abstract boolean isCalendarInUse(SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws SCException;
  
  public abstract SCCalendar getCalendar(int paramInt, HashMap paramHashMap)
    throws SCException;
  
  public abstract SCCalendar getCalendarForBank(String paramString, BankIdentifier paramBankIdentifier, HashMap paramHashMap)
    throws SCException;
  
  public abstract SCCalendar getCalendarForName(String paramString1, String paramString2, HashMap paramHashMap)
    throws SCException;
  
  public abstract SCCalendars getCalendars(String paramString, HashMap paramHashMap)
    throws SCException;
  
  public abstract void setCalendarForBank(String paramString, BankIdentifier paramBankIdentifier, SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws SCException;
  
  public abstract SCCalendar resolveCalendarForBank(String paramString, BankIdentifier paramBankIdentifier, HashMap paramHashMap)
    throws SCException;
  
  public abstract void setDefaultCalendar(String paramString, SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws SCException;
  
  public abstract SCCalendar getDefaultCalendar(String paramString, HashMap paramHashMap)
    throws SCException;
  
  public abstract boolean getIgnoreForTransfers(String paramString, BankIdentifier paramBankIdentifier, HashMap paramHashMap)
    throws SCException;
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.smartcalendar.adapter.ISCAdapter
 * JD-Core Version:    0.7.0.1
 */