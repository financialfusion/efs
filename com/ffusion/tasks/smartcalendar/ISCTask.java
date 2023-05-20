package com.ffusion.tasks.smartcalendar;

import com.ffusion.tasks.Task;

public abstract interface ISCTask
  extends Task
{
  public static final String SC_CURRENT_DAY = "SCCurrentDay";
  public static final String SC_PREVIOUS_DAY = "SCPreviousDay";
  public static final String SC_OFFSET = "SCOffset";
  public static final String SC_OFFSET_BANKING_DAY = "SCOffsetBankingDay";
  public static final String SC_TRANSACTION_DAY = "SCTransactionDay";
  public static final String SC_BANKING_DAY = "SCBankingDay";
  public static final String SC_IS_BANKING_DAY = "SCIsBankingDay";
  public static final String SC_IS_HOLIDAY = "SCIsHoliday";
  public static final String SC_DELETE_CALENDAR_HOLIDAYS_DATE = "SCDeleteCalendarHolidaysDate";
  public static final String SC_BANKIDENTIFIER = "SCBankIdentifier";
  public static final String SC_CALENDAR = "SCCalendar";
  public static final String SC_CALENDARS = "SCCalendars";
  public static final String SC_CALENDAR_IN_USE = "SCCalendarInUse";
  public static final String SC_BANK_NAME = "SCBankName";
  public static final String SC_START_DATE = "SCStartDate";
  public static final String SC_END_DATE = "SCEndDate";
  public static final int ERROR_NO_CALENDAR_IN_SESSION = 35531;
  public static final int ERROR_INVALID_NAME = 35532;
  public static final int ERROR_NO_BUSINESS_DAYS = 35533;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.smartcalendar.ISCTask
 * JD-Core Version:    0.7.0.1
 */