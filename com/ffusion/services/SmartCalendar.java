package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.util.beans.BankIdentifier;
import com.ffusion.util.beans.smartcalendar.SCCalendar;
import com.ffusion.util.beans.smartcalendar.SCCalendars;
import com.ffusion.util.smartcalendar.SCException;
import java.util.Date;
import java.util.HashMap;

public abstract interface SmartCalendar
{
  public static final String SERVICE_INIT_XML = "smartcalendar.xml";
  
  public abstract void initialize()
    throws SCException;
  
  public abstract boolean isBankingDay(SecureUser paramSecureUser, BankIdentifier paramBankIdentifier, Date paramDate, HashMap paramHashMap)
    throws SCException;
  
  public abstract boolean isHoliday(SecureUser paramSecureUser, BankIdentifier paramBankIdentifier, Date paramDate, HashMap paramHashMap)
    throws SCException;
  
  public abstract Date getPreviousDay(SecureUser paramSecureUser, BankIdentifier paramBankIdentifier, Date paramDate, HashMap paramHashMap)
    throws SCException;
  
  public abstract Date getOffsetBankingDay(SecureUser paramSecureUser, BankIdentifier paramBankIdentifier, Date paramDate, int paramInt, HashMap paramHashMap)
    throws SCException;
  
  public abstract Date getTransactionDay(SecureUser paramSecureUser, BankIdentifier paramBankIdentifier, Date paramDate, HashMap paramHashMap)
    throws SCException;
  
  public abstract void deleteCalendarHolidaysForDate(SecureUser paramSecureUser, SCCalendar paramSCCalendar, Date paramDate, HashMap paramHashMap)
    throws SCException;
  
  public abstract SCCalendar addCalendar(SecureUser paramSecureUser, SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws SCException;
  
  public abstract void modifyCalendar(SecureUser paramSecureUser, SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws SCException;
  
  public abstract void modifyCalendarName(SecureUser paramSecureUser, SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws SCException;
  
  public abstract void modifyCalendarBusinessDays(SecureUser paramSecureUser, SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws SCException;
  
  public abstract void modifyCalendarHolidays(SecureUser paramSecureUser, SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws SCException;
  
  public abstract void deleteCalendar(SecureUser paramSecureUser, SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws SCException;
  
  public abstract boolean isCalendarInUse(SecureUser paramSecureUser, SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws SCException;
  
  public abstract SCCalendar getCalendar(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws SCException;
  
  public abstract SCCalendar getCalendarForBank(SecureUser paramSecureUser, BankIdentifier paramBankIdentifier, HashMap paramHashMap)
    throws SCException;
  
  public abstract SCCalendar resolveCalendarForBank(SecureUser paramSecureUser, BankIdentifier paramBankIdentifier, HashMap paramHashMap)
    throws SCException;
  
  public abstract SCCalendar getCalendarForName(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws SCException;
  
  public abstract SCCalendars getCalendars(SecureUser paramSecureUser, HashMap paramHashMap)
    throws SCException;
  
  public abstract void setCalendarForBank(SecureUser paramSecureUser, BankIdentifier paramBankIdentifier, SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws SCException;
  
  public abstract void setDefaultCalendar(SecureUser paramSecureUser, SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws SCException;
  
  public abstract SCCalendar getDefaultCalendar(SecureUser paramSecureUser, HashMap paramHashMap)
    throws SCException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.SmartCalendar
 * JD-Core Version:    0.7.0.1
 */