package com.ffusion.csil.handlers;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.services.SmartCalendar2;
import com.ffusion.util.beans.BankIdentifier;
import com.ffusion.util.beans.smartcalendar.SCCalendar;
import com.ffusion.util.beans.smartcalendar.SCCalendars;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.smartcalendar.SCException;
import java.util.Date;
import java.util.HashMap;

public class SmartCalendarHandler
{
  private static final String a = "SmartCalendar";
  private static SmartCalendar2 jdField_if = null;
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    String str = "SmartCalendarHandler.initialize";
    try
    {
      HashMap localHashMap = HandlerUtil.getServiceSettings(paramHashMap, "SmartCalendar", str, 20107);
      jdField_if = (SmartCalendar2)HandlerUtil.instantiateService(localHashMap, str, 20107);
      jdField_if.initialize();
    }
    catch (Exception localException)
    {
      DebugLog.throwing(str, localException);
      throw new CSILException(-1007, localException);
    }
  }
  
  public static Object getService()
  {
    return jdField_if;
  }
  
  public static Date getPreviousDay(SecureUser paramSecureUser, BankIdentifier paramBankIdentifier, Date paramDate, HashMap paramHashMap)
    throws CSILException
  {
    String str = "SmartCalendarHandler.getPreviousDay";
    try
    {
      return jdField_if.getPreviousDay(paramSecureUser, paramBankIdentifier, paramDate, paramHashMap);
    }
    catch (SCException localSCException)
    {
      throw new CSILException(a(localSCException), localSCException.getErrorCode(), localSCException);
    }
  }
  
  public static Date getOffsetBankingDay(SecureUser paramSecureUser, BankIdentifier paramBankIdentifier, Date paramDate, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "SmartCalendarHandler.getOffsetBankingDay";
    try
    {
      return jdField_if.getOffsetBankingDay(paramSecureUser, paramBankIdentifier, paramDate, paramInt, paramHashMap);
    }
    catch (SCException localSCException)
    {
      throw new CSILException(a(localSCException), localSCException.getErrorCode(), localSCException);
    }
  }
  
  public static Date getTransactionDay(SecureUser paramSecureUser, BankIdentifier paramBankIdentifier, Date paramDate, HashMap paramHashMap)
    throws CSILException
  {
    String str = "SmartCalendarHandler.getTransactionDay";
    try
    {
      return jdField_if.getTransactionDay(paramSecureUser, paramBankIdentifier, paramDate, paramHashMap);
    }
    catch (SCException localSCException)
    {
      throw new CSILException(a(localSCException), localSCException.getErrorCode(), localSCException);
    }
  }
  
  public static void deleteCalendarHolidaysForDate(SecureUser paramSecureUser, SCCalendar paramSCCalendar, Date paramDate, HashMap paramHashMap)
    throws CSILException
  {
    String str = "SmartCalendarHandler.deleteCalendarHolidaysForDate";
    try
    {
      jdField_if.deleteCalendarHolidaysForDate(paramSecureUser, paramSCCalendar, paramDate, paramHashMap);
    }
    catch (SCException localSCException)
    {
      throw new CSILException(a(localSCException), localSCException.getErrorCode(), localSCException);
    }
  }
  
  public static SCCalendar addCalendar(SecureUser paramSecureUser, SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws CSILException
  {
    String str = "SmartCalendarHandler.addCalendar";
    try
    {
      return jdField_if.addCalendar(paramSecureUser, paramSCCalendar, paramHashMap);
    }
    catch (SCException localSCException)
    {
      throw new CSILException(a(localSCException), localSCException.getErrorCode(), localSCException);
    }
  }
  
  public static void modifyCalendar(SecureUser paramSecureUser, SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws CSILException
  {
    String str = "SmartCalendarHandler.modifyCalendar";
    try
    {
      jdField_if.modifyCalendar(paramSecureUser, paramSCCalendar, paramHashMap);
    }
    catch (SCException localSCException)
    {
      throw new CSILException(a(localSCException), localSCException.getErrorCode(), localSCException);
    }
  }
  
  public static void modifyCalendarName(SecureUser paramSecureUser, SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws CSILException
  {
    String str = "SmartCalendarHandler.modifyCalendarName";
    try
    {
      jdField_if.modifyCalendarName(paramSecureUser, paramSCCalendar, paramHashMap);
    }
    catch (SCException localSCException)
    {
      throw new CSILException(a(localSCException), localSCException.getErrorCode(), localSCException);
    }
  }
  
  public static void modifyCalendarBusinessDays(SecureUser paramSecureUser, SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws CSILException
  {
    String str = "SmartCalendarHandler.modifyCalendarBusinessDays";
    try
    {
      jdField_if.modifyCalendarBusinessDays(paramSecureUser, paramSCCalendar, paramHashMap);
    }
    catch (SCException localSCException)
    {
      throw new CSILException(a(localSCException), localSCException.getErrorCode(), localSCException);
    }
  }
  
  public static void modifyCalendarHolidays(SecureUser paramSecureUser, SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws CSILException
  {
    String str = "SmartCalendarHandler.modifyCalendarHolidays";
    try
    {
      jdField_if.modifyCalendarHolidays(paramSecureUser, paramSCCalendar, paramHashMap);
    }
    catch (SCException localSCException)
    {
      throw new CSILException(a(localSCException), localSCException.getErrorCode(), localSCException);
    }
  }
  
  public static void deleteCalendar(SecureUser paramSecureUser, SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws CSILException
  {
    String str = "SmartCalendarHandler.deleteCalendar";
    try
    {
      jdField_if.deleteCalendar(paramSecureUser, paramSCCalendar, paramHashMap);
    }
    catch (SCException localSCException)
    {
      throw new CSILException(a(localSCException), localSCException.getErrorCode(), localSCException);
    }
  }
  
  public static boolean isCalendarInUse(SecureUser paramSecureUser, SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws CSILException
  {
    String str = "SmartCalendarHandler.isCalendarInUse";
    try
    {
      return jdField_if.isCalendarInUse(paramSecureUser, paramSCCalendar, paramHashMap);
    }
    catch (SCException localSCException)
    {
      throw new CSILException(a(localSCException), localSCException.getErrorCode(), localSCException);
    }
  }
  
  public static SCCalendar getCalendar(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "SmartCalendarHandler.getCalendar";
    try
    {
      return jdField_if.getCalendar(paramSecureUser, paramInt, paramHashMap);
    }
    catch (SCException localSCException)
    {
      throw new CSILException(a(localSCException), localSCException.getErrorCode(), localSCException);
    }
  }
  
  public static SCCalendar getCalendarForBank(SecureUser paramSecureUser, BankIdentifier paramBankIdentifier, HashMap paramHashMap)
    throws CSILException
  {
    String str = "SmartCalendarHandler.getCalendarForBank";
    try
    {
      return jdField_if.getCalendarForBank(paramSecureUser, paramBankIdentifier, paramHashMap);
    }
    catch (SCException localSCException)
    {
      throw new CSILException(a(localSCException), localSCException.getErrorCode(), localSCException);
    }
  }
  
  public static SCCalendar resolveCalendarForBank(SecureUser paramSecureUser, BankIdentifier paramBankIdentifier, HashMap paramHashMap)
    throws CSILException
  {
    String str = "SmartCalendarHandler.resolveCalendarForBank";
    try
    {
      return jdField_if.resolveCalendarForBank(paramSecureUser, paramBankIdentifier, paramHashMap);
    }
    catch (SCException localSCException)
    {
      throw new CSILException(a(localSCException), localSCException.getErrorCode(), localSCException);
    }
  }
  
  public static SCCalendar getCalendarForName(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "SmartCalendarHandler.getCalendarForName";
    try
    {
      return jdField_if.getCalendarForName(paramSecureUser, paramString, paramHashMap);
    }
    catch (SCException localSCException)
    {
      throw new CSILException(a(localSCException), localSCException.getErrorCode(), localSCException);
    }
  }
  
  public static SCCalendars getCalendars(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "SmartCalendarHandler.getCalendars";
    try
    {
      return jdField_if.getCalendars(paramSecureUser, paramHashMap);
    }
    catch (SCException localSCException)
    {
      throw new CSILException(a(localSCException), localSCException.getErrorCode(), localSCException);
    }
  }
  
  public static void setCalendarForBank(SecureUser paramSecureUser, BankIdentifier paramBankIdentifier, SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws CSILException
  {
    String str = "SmartCalendarHandler.setCalendarForBank";
    try
    {
      jdField_if.setCalendarForBank(paramSecureUser, paramBankIdentifier, paramSCCalendar, paramHashMap);
    }
    catch (SCException localSCException)
    {
      throw new CSILException(a(localSCException), localSCException.getErrorCode(), localSCException);
    }
  }
  
  public static void setDefaultCalendar(SecureUser paramSecureUser, SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws CSILException
  {
    String str = "SmartCalendarHandler.setDefaultCalendar";
    try
    {
      jdField_if.setDefaultCalendar(paramSecureUser, paramSCCalendar, paramHashMap);
    }
    catch (SCException localSCException)
    {
      throw new CSILException(a(localSCException), localSCException.getErrorCode(), localSCException);
    }
  }
  
  public static SCCalendar getDefaultCalendar(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "SmartCalendarHandler.getDefaultCalendar";
    try
    {
      return jdField_if.getDefaultCalendar(paramSecureUser, paramHashMap);
    }
    catch (SCException localSCException)
    {
      throw new CSILException(a(localSCException), localSCException.getErrorCode(), localSCException);
    }
  }
  
  public static boolean isBankingDay(SecureUser paramSecureUser, BankIdentifier paramBankIdentifier, Date paramDate, HashMap paramHashMap)
    throws CSILException
  {
    String str = "SmartCalendarHandler.isBankingDay";
    try
    {
      return jdField_if.isBankingDay(paramSecureUser, paramBankIdentifier, paramDate, paramHashMap);
    }
    catch (SCException localSCException)
    {
      throw new CSILException(a(localSCException), localSCException.getErrorCode(), localSCException);
    }
  }
  
  public static boolean isHoliday(SecureUser paramSecureUser, BankIdentifier paramBankIdentifier, Date paramDate, HashMap paramHashMap)
    throws CSILException
  {
    String str = "SmartCalendarHandler.isHoliday";
    try
    {
      return jdField_if.isHoliday(paramSecureUser, paramBankIdentifier, paramDate, paramHashMap);
    }
    catch (SCException localSCException)
    {
      throw new CSILException(a(localSCException), localSCException.getErrorCode(), localSCException);
    }
  }
  
  public static boolean getIgnoreForTransfers(SecureUser paramSecureUser, BankIdentifier paramBankIdentifier, HashMap paramHashMap)
    throws CSILException
  {
    String str = "SmartCalendarService.getIgnoreForTransfers";
    try
    {
      return jdField_if.getIgnoreForTransfers(paramSecureUser, paramBankIdentifier, paramHashMap);
    }
    catch (SCException localSCException)
    {
      throw new CSILException(a(localSCException), localSCException.getErrorCode(), localSCException);
    }
  }
  
  private static int a(SCException paramSCException)
  {
    switch (paramSCException.getErrorCode())
    {
    case 0: 
      return 35500;
    case 1: 
      return 35501;
    case 2: 
      return 35502;
    case 3: 
      return 35503;
    case 10: 
      return 35510;
    case 11: 
      return 35511;
    case 20: 
      return 35520;
    case 21: 
      return 35521;
    case 22: 
      return 35522;
    case 23: 
      return 35523;
    case 24: 
      return 35524;
    case 25: 
      return 35525;
    case 26: 
      return 35526;
    }
    return 35500;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.SmartCalendarHandler
 * JD-Core Version:    0.7.0.1
 */