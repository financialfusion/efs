package com.ffusion.csil.core;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.handlers.SmartCalendarHandler;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.util.beans.BankIdentifier;
import com.ffusion.util.beans.DateTime;
import com.ffusion.util.beans.LocalizableDate;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.beans.smartcalendar.SCBusinessDays;
import com.ffusion.util.beans.smartcalendar.SCCalendar;
import com.ffusion.util.beans.smartcalendar.SCCalendars;
import com.ffusion.util.beans.smartcalendar.SCHoliday;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.logging.PerfLog;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;

public class SmartCalendar
  extends Initialize
{
  private static Entitlement aAg = new Entitlement("BC Smart Calendar View", null, null);
  private static Entitlement aAj = new Entitlement("BC Smart Calendar Add", null, null);
  private static Entitlement aAc = new Entitlement("BC Smart Calendar Delete", null, null);
  private static Entitlement aAk = new Entitlement("BC Smart Calendar Modify", null, null);
  private static String az0 = "com.ffusion.util.beans.smartcalendar.SCCalendar";
  private static String az4 = "CALENDAR_ID";
  private static String az9 = "CALENDAR_NAME";
  private static String az6 = "CALENDAR_BUSINESS_DAYS";
  private static String az3 = "CALENDAR_BUSINESS_DAYS_ACTIONS";
  private static String az7 = "CALENDAR_HOLIDAY";
  private static String az2 = "CALENDAR_IS_DEFAULT";
  private static final String az8 = "com.ffusion.util.logging.audit.smartcalendar";
  private static final String aAn = "AuditMessage_1";
  private static final String azZ = "AuditMessage_2";
  private static final String aAd = "AuditMessage_3";
  private static final String aAa = "AuditMessage_4";
  private static final String aAm = "AuditMessage_5";
  private static final String azY = "AuditMessage_6";
  private static final String aAf = "AuditMessage_7";
  private static final String az1 = "AuditMessage_8";
  private static final String aAb = "AuditMessage_9";
  private static final String azX = "AuditMessage_10";
  private static final String aAi = "AuditMessage_11";
  private static final String az5 = "AuditEntFault_1";
  private static final String aAh = "AuditEntFault_2";
  private static final String aAe = "AuditEntFault_3";
  private static final String aAl = "AuditEntFault_4";
  public static final String EXTRA_BANK_NAME = "BankName";
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    String str = "SmartCalendar.initialize";
    debug(str);
    try
    {
      SmartCalendarHandler.initialize(paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    catch (Exception localException)
    {
      DebugLog.throwing(str, localException);
      throw new CSILException(-1007, localException);
    }
  }
  
  public static Object getService()
  {
    return SmartCalendarHandler.getService();
  }
  
  public static Date getPreviousDay(SecureUser paramSecureUser, BankIdentifier paramBankIdentifier, Date paramDate, HashMap paramHashMap)
    throws CSILException
  {
    Date localDate = null;
    String str = "SmartCalendar.getPreviousDay";
    debug(str);
    try
    {
      long l = System.currentTimeMillis();
      localDate = SmartCalendarHandler.getPreviousDay(paramSecureUser, paramBankIdentifier, paramDate, paramHashMap);
      PerfLog.log(str, l, true);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    catch (Exception localException)
    {
      DebugLog.throwing(str, localException);
    }
    return localDate;
  }
  
  public static Date getOffsetBankingDay(SecureUser paramSecureUser, BankIdentifier paramBankIdentifier, Date paramDate, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    Date localDate = null;
    String str = "SmartCalendar.getOffsetBankingDay";
    debug(str);
    try
    {
      long l = System.currentTimeMillis();
      localDate = SmartCalendarHandler.getOffsetBankingDay(paramSecureUser, paramBankIdentifier, paramDate, paramInt, paramHashMap);
      PerfLog.log(str, l, true);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    catch (Exception localException)
    {
      DebugLog.throwing(str, localException);
    }
    return localDate;
  }
  
  public static Date getTransactionDay(SecureUser paramSecureUser, BankIdentifier paramBankIdentifier, Date paramDate, HashMap paramHashMap)
    throws CSILException
  {
    Date localDate = null;
    String str = "SmartCalendar.getTransactionDay";
    debug(str);
    try
    {
      long l = System.currentTimeMillis();
      localDate = SmartCalendarHandler.getTransactionDay(paramSecureUser, paramBankIdentifier, paramDate, paramHashMap);
      PerfLog.log(str, l, true);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    catch (Exception localException)
    {
      DebugLog.throwing(str, localException);
    }
    return localDate;
  }
  
  public static void deleteCalendarHolidaysForDate(SecureUser paramSecureUser, SCCalendar paramSCCalendar, Date paramDate, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "SmartCalendar.deleteCalendarHolidaysForDate";
    debug(str1);
    try
    {
      d(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      SCCalendar localSCCalendar = SmartCalendarHandler.getCalendar(paramSecureUser, paramSCCalendar.getCalendarId(), paramHashMap);
      SmartCalendarHandler.deleteCalendarHolidaysForDate(paramSecureUser, paramSCCalendar, paramDate, paramHashMap);
      PerfLog.log(str1, l, true);
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = str1;
      arrayOfObject[1] = new LocalizableDate(paramDate.getTime(), null, false);
      arrayOfObject[2] = new Integer(paramSCCalendar.getCalendarId());
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.smartcalendar", "AuditMessage_3", arrayOfObject);
      audit(paramSecureUser, localLocalizableString, str2, 5006);
      jdMethod_case(paramSecureUser, paramSCCalendar, localSCCalendar);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str1, localCSILException);
      throw localCSILException;
    }
    catch (Exception localException)
    {
      DebugLog.throwing(str1, localException);
    }
  }
  
  public static SCCalendar addCalendar(SecureUser paramSecureUser, SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "SmartCalendar.addCalendar";
    debug(str1);
    try
    {
      e(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      paramSCCalendar = SmartCalendarHandler.addCalendar(paramSecureUser, paramSCCalendar, paramHashMap);
      PerfLog.log(str1, l, true);
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = str1;
      arrayOfObject[1] = new Integer(paramSCCalendar.getCalendarId());
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.smartcalendar", "AuditMessage_4", arrayOfObject);
      audit(paramSecureUser, localLocalizableString, str2, 5000);
      jdMethod_int(paramSecureUser, paramSCCalendar);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str1, localCSILException);
      throw localCSILException;
    }
    catch (Exception localException)
    {
      DebugLog.throwing(str1, localException);
    }
    return paramSCCalendar;
  }
  
  public static void modifyCalendar(SecureUser paramSecureUser, SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "SmartCalendar.modifyCalendar";
    debug(str1);
    try
    {
      d(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      SCCalendar localSCCalendar = SmartCalendarHandler.getCalendar(paramSecureUser, paramSCCalendar.getCalendarId(), paramHashMap);
      SmartCalendarHandler.modifyCalendar(paramSecureUser, paramSCCalendar, paramHashMap);
      PerfLog.log(str1, l, true);
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = str1;
      arrayOfObject[1] = new Integer(paramSCCalendar.getCalendarId());
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.smartcalendar", "AuditMessage_5", arrayOfObject);
      audit(paramSecureUser, localLocalizableString, str2, 5001);
      jdMethod_int(paramSecureUser, paramSCCalendar, localSCCalendar);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str1, localCSILException);
      throw localCSILException;
    }
    catch (Exception localException)
    {
      DebugLog.throwing(str1, localException);
    }
  }
  
  public static void modifyCalendarName(SecureUser paramSecureUser, SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "SmartCalendar.modifyCalendarName";
    debug(str1);
    try
    {
      d(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      SCCalendar localSCCalendar = SmartCalendarHandler.getCalendar(paramSecureUser, paramSCCalendar.getCalendarId(), paramHashMap);
      SmartCalendarHandler.modifyCalendarName(paramSecureUser, paramSCCalendar, paramHashMap);
      PerfLog.log(str1, l, true);
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = str1;
      arrayOfObject[1] = new Integer(paramSCCalendar.getCalendarId());
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.smartcalendar", "AuditMessage_6", arrayOfObject);
      audit(paramSecureUser, localLocalizableString, str2, 5002);
      jdMethod_new(paramSecureUser, paramSCCalendar, localSCCalendar);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str1, localCSILException);
      throw localCSILException;
    }
    catch (Exception localException)
    {
      DebugLog.throwing(str1, localException);
    }
  }
  
  public static void modifyCalendarBusinessDays(SecureUser paramSecureUser, SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "SmartCalendar.modifyCalendarBusinessDays";
    debug(str1);
    try
    {
      d(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      SCCalendar localSCCalendar = SmartCalendarHandler.getCalendar(paramSecureUser, paramSCCalendar.getCalendarId(), paramHashMap);
      SmartCalendarHandler.modifyCalendarBusinessDays(paramSecureUser, paramSCCalendar, paramHashMap);
      PerfLog.log(str1, l, true);
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = str1;
      arrayOfObject[1] = new Integer(paramSCCalendar.getCalendarId());
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.smartcalendar", "AuditMessage_7", arrayOfObject);
      audit(paramSecureUser, localLocalizableString, str2, 5003);
      jdMethod_byte(paramSecureUser, paramSCCalendar, localSCCalendar);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str1, localCSILException);
      throw localCSILException;
    }
    catch (Exception localException)
    {
      DebugLog.throwing(str1, localException);
    }
  }
  
  public static void modifyCalendarHolidays(SecureUser paramSecureUser, SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "SmartCalendar.modifyCalendarHolidays";
    debug(str1);
    try
    {
      d(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      SCCalendar localSCCalendar = SmartCalendarHandler.getCalendar(paramSecureUser, paramSCCalendar.getCalendarId(), paramHashMap);
      SmartCalendarHandler.modifyCalendarHolidays(paramSecureUser, paramSCCalendar, paramHashMap);
      PerfLog.log(str1, l, true);
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = str1;
      arrayOfObject[1] = new Integer(paramSCCalendar.getCalendarId());
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.smartcalendar", "AuditMessage_8", arrayOfObject);
      audit(paramSecureUser, localLocalizableString, str2, 5004);
      jdMethod_for(paramSecureUser, paramSCCalendar, localSCCalendar);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str1, localCSILException);
      throw localCSILException;
    }
    catch (Exception localException)
    {
      DebugLog.throwing(str1, localException);
    }
  }
  
  public static void deleteCalendar(SecureUser paramSecureUser, SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "SmartCalendar.deleteCalendar";
    debug(str1);
    try
    {
      f(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      SmartCalendarHandler.deleteCalendar(paramSecureUser, paramSCCalendar, paramHashMap);
      PerfLog.log(str1, l, true);
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = str1;
      arrayOfObject[1] = new Integer(paramSCCalendar.getCalendarId());
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.smartcalendar", "AuditMessage_9", arrayOfObject);
      audit(paramSecureUser, localLocalizableString, str2, 5005);
      jdMethod_for(paramSecureUser, paramSCCalendar);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str1, localCSILException);
      throw localCSILException;
    }
    catch (Exception localException)
    {
      DebugLog.throwing(str1, localException);
    }
  }
  
  public static boolean isCalendarInUse(SecureUser paramSecureUser, SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws CSILException
  {
    boolean bool = false;
    String str = "SmartCalendar.isCalendarInUse";
    debug(str);
    try
    {
      long l = System.currentTimeMillis();
      bool = SmartCalendarHandler.isCalendarInUse(paramSecureUser, paramSCCalendar, paramHashMap);
      PerfLog.log(str, l, true);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    catch (Exception localException)
    {
      DebugLog.throwing(str, localException);
    }
    return bool;
  }
  
  public static SCCalendar getCalendarForBank(SecureUser paramSecureUser, BankIdentifier paramBankIdentifier, HashMap paramHashMap)
    throws CSILException
  {
    SCCalendar localSCCalendar = null;
    String str = "SmartCalendar.getCalendarForBank";
    debug(str);
    try
    {
      c(paramSecureUser);
      long l = System.currentTimeMillis();
      localSCCalendar = SmartCalendarHandler.getCalendarForBank(paramSecureUser, paramBankIdentifier, paramHashMap);
      PerfLog.log(str, l, true);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    catch (Exception localException)
    {
      DebugLog.throwing(str, localException);
    }
    return localSCCalendar;
  }
  
  public static SCCalendar resolveCalendarForBank(SecureUser paramSecureUser, BankIdentifier paramBankIdentifier, HashMap paramHashMap)
    throws CSILException
  {
    SCCalendar localSCCalendar = null;
    String str = "SmartCalendar.resolveCalendarForBank";
    debug(str);
    try
    {
      c(paramSecureUser);
      long l = System.currentTimeMillis();
      localSCCalendar = SmartCalendarHandler.resolveCalendarForBank(paramSecureUser, paramBankIdentifier, paramHashMap);
      PerfLog.log(str, l, true);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    catch (Exception localException)
    {
      DebugLog.throwing(str, localException);
    }
    return localSCCalendar;
  }
  
  public static SCCalendar getCalendarForName(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    SCCalendar localSCCalendar = null;
    String str = "SmartCalendar.getCalendarForName";
    debug(str);
    try
    {
      c(paramSecureUser);
      long l = System.currentTimeMillis();
      localSCCalendar = SmartCalendarHandler.getCalendarForName(paramSecureUser, paramString, paramHashMap);
      PerfLog.log(str, l, true);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    catch (Exception localException)
    {
      DebugLog.throwing(str, localException);
    }
    return localSCCalendar;
  }
  
  public static SCCalendars getCalendars(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    SCCalendars localSCCalendars = null;
    String str = "SmartCalendar.getCalendars";
    debug(str);
    try
    {
      c(paramSecureUser);
      long l = System.currentTimeMillis();
      localSCCalendars = SmartCalendarHandler.getCalendars(paramSecureUser, paramHashMap);
      PerfLog.log(str, l, true);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    catch (Exception localException)
    {
      DebugLog.throwing(str, localException);
    }
    return localSCCalendars;
  }
  
  public static void setCalendarForBank(SecureUser paramSecureUser, BankIdentifier paramBankIdentifier, SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "SmartCalendar.setCalendarForBank";
    debug(str1);
    try
    {
      d(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      SCCalendar localSCCalendar = SmartCalendarHandler.getCalendarForBank(paramSecureUser, paramBankIdentifier, paramHashMap);
      String str3 = (String)paramHashMap.get("BankName");
      if (str3 == null) {
        str3 = "";
      }
      if (((localSCCalendar != null) && (paramSCCalendar == null)) || ((localSCCalendar == null) && (paramSCCalendar != null)) || ((localSCCalendar != null) && (paramSCCalendar != null) && (localSCCalendar.getCalendarId() != paramSCCalendar.getCalendarId())))
      {
        SmartCalendarHandler.setCalendarForBank(paramSecureUser, paramBankIdentifier, paramSCCalendar, paramHashMap);
        PerfLog.log(str1, l, true);
        LocalizableString localLocalizableString = null;
        Object[] arrayOfObject;
        if (paramSCCalendar == null)
        {
          arrayOfObject = new Object[1];
          arrayOfObject[0] = str3;
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.smartcalendar", "AuditMessage_2", arrayOfObject);
          audit(paramSecureUser, localLocalizableString, str2, 5008);
        }
        else
        {
          arrayOfObject = new Object[2];
          arrayOfObject[0] = str3;
          arrayOfObject[1] = paramSCCalendar.getCalendarName();
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.smartcalendar", "AuditMessage_1", arrayOfObject);
          audit(paramSecureUser, localLocalizableString, str2, 5007);
        }
        jdMethod_for(paramSecureUser, paramBankIdentifier, paramSCCalendar, localSCCalendar);
      }
      else
      {
        PerfLog.log(str1, l, true);
      }
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str1, localCSILException);
      throw localCSILException;
    }
    catch (Exception localException)
    {
      DebugLog.throwing(str1, localException);
    }
  }
  
  public static void setDefaultCalendar(SecureUser paramSecureUser, SCCalendar paramSCCalendar, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "SmartCalendar.setDefaultCalendar";
    debug(str1);
    try
    {
      d(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      SCCalendar localSCCalendar = SmartCalendarHandler.getDefaultCalendar(paramSecureUser, paramHashMap);
      SmartCalendarHandler.setDefaultCalendar(paramSecureUser, paramSCCalendar, paramHashMap);
      PerfLog.log(str1, l, true);
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = str1;
      arrayOfObject[2] = paramSecureUser.getBankID();
      LocalizableString localLocalizableString = null;
      if (paramSCCalendar != null)
      {
        arrayOfObject[1] = new Integer(paramSCCalendar.getCalendarId());
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.smartcalendar", "AuditMessage_10", arrayOfObject);
      }
      else if (localSCCalendar != null)
      {
        arrayOfObject[1] = new Integer(localSCCalendar.getCalendarId());
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.smartcalendar", "AuditMessage_11", arrayOfObject);
      }
      audit(paramSecureUser, localLocalizableString, str2, 5008);
      jdMethod_try(paramSecureUser, paramSCCalendar, localSCCalendar);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str1, localCSILException);
      throw localCSILException;
    }
    catch (Exception localException)
    {
      DebugLog.throwing(str1, localException);
    }
  }
  
  public static SCCalendar getDefaultCalendar(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    SCCalendar localSCCalendar = null;
    String str = "SmartCalendar.getDefaultCalendar";
    debug(str);
    try
    {
      c(paramSecureUser);
      long l = System.currentTimeMillis();
      localSCCalendar = SmartCalendarHandler.getDefaultCalendar(paramSecureUser, paramHashMap);
      PerfLog.log(str, l, true);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    catch (Exception localException)
    {
      DebugLog.throwing(str, localException);
    }
    return localSCCalendar;
  }
  
  public static boolean isBankingDay(SecureUser paramSecureUser, BankIdentifier paramBankIdentifier, Date paramDate, HashMap paramHashMap)
    throws CSILException
  {
    boolean bool = false;
    String str = "SmartCalendar.isBankingDay";
    debug(str);
    try
    {
      long l = System.currentTimeMillis();
      bool = SmartCalendarHandler.isBankingDay(paramSecureUser, paramBankIdentifier, paramDate, paramHashMap);
      PerfLog.log(str, l, true);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    catch (Exception localException)
    {
      DebugLog.throwing(str, localException);
    }
    return bool;
  }
  
  public static boolean[] getBankingDaysInRange(SecureUser paramSecureUser, BankIdentifier paramBankIdentifier, Date paramDate1, Date paramDate2, HashMap paramHashMap)
    throws CSILException
  {
    ArrayList localArrayList = new ArrayList();
    boolean[] arrayOfBoolean = new boolean[0];
    String str = "SmartCalendar.getBankingDaysInRange";
    debug(str);
    try
    {
      long l = System.currentTimeMillis();
      c(paramSecureUser);
      GregorianCalendar localGregorianCalendar1 = jdMethod_case(paramDate1);
      GregorianCalendar localGregorianCalendar2 = jdMethod_case(paramDate2);
      for (;;)
      {
        localArrayList.add(new Boolean(isBankingDay(paramSecureUser, paramBankIdentifier, jdMethod_for(localGregorianCalendar1), paramHashMap)));
        if ((localGregorianCalendar1.after(localGregorianCalendar2)) || (localGregorianCalendar1.equals(localGregorianCalendar2))) {
          break;
        }
        localGregorianCalendar1.add(5, 1);
      }
      arrayOfBoolean = new boolean[localArrayList.size()];
      for (int i = 0; i < arrayOfBoolean.length; i++) {
        arrayOfBoolean[i] = ((Boolean)localArrayList.get(i)).booleanValue();
      }
      PerfLog.log(str, l, true);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    catch (Exception localException)
    {
      DebugLog.throwing(str, localException);
    }
    return arrayOfBoolean;
  }
  
  private static GregorianCalendar jdMethod_case(Date paramDate)
  {
    GregorianCalendar localGregorianCalendar = new GregorianCalendar();
    localGregorianCalendar.clear();
    localGregorianCalendar.set(1, paramDate.getYear() + 1900);
    localGregorianCalendar.set(2, paramDate.getMonth());
    localGregorianCalendar.set(5, paramDate.getDate());
    localGregorianCalendar.set(11, paramDate.getHours());
    localGregorianCalendar.set(12, paramDate.getMinutes());
    localGregorianCalendar.set(13, paramDate.getSeconds());
    return localGregorianCalendar;
  }
  
  private static Date jdMethod_for(GregorianCalendar paramGregorianCalendar)
  {
    Date localDate = new Date();
    localDate.setYear(paramGregorianCalendar.get(1) - 1900);
    localDate.setMonth(paramGregorianCalendar.get(2));
    localDate.setDate(paramGregorianCalendar.get(5));
    localDate.setHours(paramGregorianCalendar.get(11));
    localDate.setMinutes(paramGregorianCalendar.get(12));
    localDate.setSeconds(paramGregorianCalendar.get(13));
    return localDate;
  }
  
  public static boolean isHoliday(SecureUser paramSecureUser, BankIdentifier paramBankIdentifier, Date paramDate, HashMap paramHashMap)
    throws CSILException
  {
    boolean bool = false;
    String str = "SmartCalendar.isHoliday";
    debug(str);
    try
    {
      long l = System.currentTimeMillis();
      bool = SmartCalendarHandler.isHoliday(paramSecureUser, paramBankIdentifier, paramDate, paramHashMap);
      PerfLog.log(str, l, true);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    catch (Exception localException)
    {
      DebugLog.throwing(str, localException);
    }
    return bool;
  }
  
  public static boolean getIgnoreForTransfers(SecureUser paramSecureUser, BankIdentifier paramBankIdentifier, HashMap paramHashMap)
    throws CSILException
  {
    String str = "SmartCalendarService.getIgnoreForTransfers";
    boolean bool = false;
    try
    {
      long l = System.currentTimeMillis();
      bool = SmartCalendarHandler.getIgnoreForTransfers(paramSecureUser, paramBankIdentifier, paramHashMap);
      PerfLog.log(str, l, true);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    catch (Exception localException)
    {
      DebugLog.throwing(str, localException);
    }
    return bool;
  }
  
  private static void c(SecureUser paramSecureUser)
    throws CSILException
  {
    if (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aAg))
    {
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.smartcalendar", "AuditEntFault_1", null);
      Initialize.logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException(35527);
    }
  }
  
  private static void e(SecureUser paramSecureUser)
    throws CSILException
  {
    if (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aAj))
    {
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.smartcalendar", "AuditEntFault_2", null);
      Initialize.logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException(35528);
    }
  }
  
  private static void d(SecureUser paramSecureUser)
    throws CSILException
  {
    if (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aAk))
    {
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.smartcalendar", "AuditEntFault_3", null);
      Initialize.logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException(35529);
    }
  }
  
  private static void f(SecureUser paramSecureUser)
    throws CSILException
  {
    if (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aAc))
    {
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.smartcalendar", "AuditEntFault_4", null);
      Initialize.logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException(35530);
    }
  }
  
  private static void jdMethod_int(SecureUser paramSecureUser, SCCalendar paramSCCalendar)
  {
    int i = paramSCCalendar.getCalendarId();
    HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 19, Integer.toString(i));
    localHistoryTracker.logCreate(az0, az4, i, localHistoryTracker.lookupComment(7));
    try
    {
      HistoryAdapter.addHistory(localHistoryTracker.getHistories());
    }
    catch (ProfileException localProfileException)
    {
      DebugLog.log(Level.SEVERE, "Add history failed for SmartCalendar.addCalendar: " + localProfileException.toString());
    }
  }
  
  private static void jdMethod_for(SecureUser paramSecureUser, SCCalendar paramSCCalendar)
  {
    int i = paramSCCalendar.getCalendarId();
    HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 19, Integer.toString(i));
    localHistoryTracker.logDelete(az0, az4, i, localHistoryTracker.lookupComment(9));
    try
    {
      HistoryAdapter.addHistory(localHistoryTracker.getHistories());
    }
    catch (ProfileException localProfileException)
    {
      DebugLog.log(Level.SEVERE, "Add history failed for SmartCalendar.removeCalendar: " + localProfileException.toString());
    }
  }
  
  private static void jdMethod_int(SecureUser paramSecureUser, SCCalendar paramSCCalendar1, SCCalendar paramSCCalendar2)
  {
    int i = paramSCCalendar1.getCalendarId();
    HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 19, Integer.toString(i));
    jdMethod_try(localHistoryTracker, paramSCCalendar1, paramSCCalendar2);
    jdMethod_int(localHistoryTracker, paramSCCalendar1, paramSCCalendar2);
    jdMethod_new(localHistoryTracker, paramSCCalendar1, paramSCCalendar2);
    jdMethod_for(localHistoryTracker, paramSCCalendar1, paramSCCalendar2);
    try
    {
      HistoryAdapter.addHistory(localHistoryTracker.getHistories());
    }
    catch (ProfileException localProfileException)
    {
      DebugLog.log(Level.SEVERE, "Add history failed for SmartCalendar.modifyCalendar: " + localProfileException.toString());
    }
  }
  
  private static void jdMethod_new(SecureUser paramSecureUser, SCCalendar paramSCCalendar1, SCCalendar paramSCCalendar2)
  {
    int i = paramSCCalendar1.getCalendarId();
    HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 19, Integer.toString(i));
    jdMethod_try(localHistoryTracker, paramSCCalendar1, paramSCCalendar2);
    try
    {
      HistoryAdapter.addHistory(localHistoryTracker.getHistories());
    }
    catch (ProfileException localProfileException)
    {
      DebugLog.log(Level.SEVERE, "Add history failed for SmartCalendar.modifyCalendarName: " + localProfileException.toString());
    }
  }
  
  private static void jdMethod_byte(SecureUser paramSecureUser, SCCalendar paramSCCalendar1, SCCalendar paramSCCalendar2)
  {
    int i = paramSCCalendar1.getCalendarId();
    HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 19, Integer.toString(i));
    jdMethod_int(localHistoryTracker, paramSCCalendar1, paramSCCalendar2);
    try
    {
      HistoryAdapter.addHistory(localHistoryTracker.getHistories());
    }
    catch (ProfileException localProfileException)
    {
      DebugLog.log(Level.SEVERE, "Add history failed for SmartCalendar.modifyCalendarBusinessDays: " + localProfileException.toString());
    }
  }
  
  private static void jdMethod_for(SecureUser paramSecureUser, SCCalendar paramSCCalendar1, SCCalendar paramSCCalendar2)
  {
    int i = paramSCCalendar1.getCalendarId();
    HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 19, Integer.toString(i));
    jdMethod_new(localHistoryTracker, paramSCCalendar1, paramSCCalendar2);
    try
    {
      HistoryAdapter.addHistory(localHistoryTracker.getHistories());
    }
    catch (ProfileException localProfileException)
    {
      DebugLog.log(Level.SEVERE, "Add history failed for SmartCalendar.modifyCalendarHolidays: " + localProfileException.toString());
    }
  }
  
  private static void jdMethod_case(SecureUser paramSecureUser, SCCalendar paramSCCalendar1, SCCalendar paramSCCalendar2)
  {
    int i = paramSCCalendar1.getCalendarId();
    HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 19, Integer.toString(i));
    jdMethod_new(localHistoryTracker, paramSCCalendar1, paramSCCalendar2);
    try
    {
      HistoryAdapter.addHistory(localHistoryTracker.getHistories());
    }
    catch (ProfileException localProfileException)
    {
      DebugLog.log(Level.SEVERE, "Add history failed for SmartCalendar.deleteCalendarHolidaysForDate: " + localProfileException.toString());
    }
  }
  
  private static void jdMethod_try(SecureUser paramSecureUser, SCCalendar paramSCCalendar1, SCCalendar paramSCCalendar2)
  {
    HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 21, paramSecureUser.getBankID());
    if (paramSCCalendar1 == paramSCCalendar2) {
      return;
    }
    if (paramSCCalendar1 == null) {
      localHistoryTracker.logChange("Default Smart Calendar Id", Integer.toString(paramSCCalendar2.getCalendarId()), null, localHistoryTracker.lookupComment(13));
    } else if (paramSCCalendar2 == null) {
      localHistoryTracker.logChange("Default Smart Calendar Id", null, Integer.toString(paramSCCalendar1.getCalendarId()), localHistoryTracker.lookupComment(13));
    } else {
      localHistoryTracker.detectChange("Default Smart Calendar Id", paramSCCalendar2.getCalendarId(), paramSCCalendar1.getCalendarId(), localHistoryTracker.lookupComment(13));
    }
    try
    {
      HistoryAdapter.addHistory(localHistoryTracker.getHistories());
    }
    catch (ProfileException localProfileException)
    {
      DebugLog.log(Level.SEVERE, "Add history failed for SmartCalendar.setDefaultCalendar: " + localProfileException.toString());
    }
  }
  
  private static void jdMethod_for(SecureUser paramSecureUser, BankIdentifier paramBankIdentifier, SCCalendar paramSCCalendar1, SCCalendar paramSCCalendar2)
  {
    int i = -1;
    int j = -1;
    if (paramSCCalendar2 != null) {
      j = paramSCCalendar2.getCalendarId();
    }
    if (paramSCCalendar1 != null) {
      i = paramSCCalendar1.getCalendarId();
    }
    HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 20, null, paramBankIdentifier.getBankDirectoryId());
    if (i == j) {
      return;
    }
    if (j == -1) {
      localHistoryTracker.logCreate(az0, az4, i, localHistoryTracker.lookupComment(10));
    } else if (i == -1) {
      localHistoryTracker.logDelete(az0, az4, i, localHistoryTracker.lookupComment(12));
    } else {
      localHistoryTracker.logChange(localHistoryTracker.lookupField(az0, az4), j, i, localHistoryTracker.lookupComment(11));
    }
    try
    {
      HistoryAdapter.addHistory(localHistoryTracker.getHistories());
    }
    catch (ProfileException localProfileException)
    {
      DebugLog.log(Level.SEVERE, "Add history failed for SmartCalendar.setCalendarForBank: " + localProfileException.toString());
    }
  }
  
  private static void jdMethod_try(HistoryTracker paramHistoryTracker, SCCalendar paramSCCalendar1, SCCalendar paramSCCalendar2)
  {
    paramHistoryTracker.detectChange(az0, az9, paramSCCalendar2.getCalendarName(), paramSCCalendar1.getCalendarName(), paramHistoryTracker.lookupComment(8));
  }
  
  private static void jdMethod_int(HistoryTracker paramHistoryTracker, SCCalendar paramSCCalendar1, SCCalendar paramSCCalendar2)
  {
    paramHistoryTracker.detectChange(az0, az6, paramSCCalendar2.getBusinessDays().getBusinessDays(), paramSCCalendar1.getBusinessDays().getBusinessDays(), paramHistoryTracker.lookupComment(8));
    paramHistoryTracker.detectChange(az0, az3, paramSCCalendar2.getBusinessDays().getActions(), paramSCCalendar1.getBusinessDays().getActions(), paramHistoryTracker.lookupComment(8));
  }
  
  private static void jdMethod_new(HistoryTracker paramHistoryTracker, SCCalendar paramSCCalendar1, SCCalendar paramSCCalendar2)
  {
    HashMap localHashMap1 = paramSCCalendar1.getHolidays();
    HashMap localHashMap2 = paramSCCalendar2.getHolidays();
    if (localHashMap1.equals(localHashMap2)) {
      return;
    }
    Iterator localIterator = localHashMap2.keySet().iterator();
    DateTime localDateTime;
    SCHoliday localSCHoliday;
    while (localIterator.hasNext())
    {
      localDateTime = (DateTime)localIterator.next();
      localSCHoliday = (SCHoliday)localHashMap2.get(localDateTime);
      paramHistoryTracker.logDelete(az0, az7, localDateTime + " - " + localSCHoliday.getName(), paramHistoryTracker.lookupComment(8));
    }
    localIterator = localHashMap1.keySet().iterator();
    while (localIterator.hasNext())
    {
      localDateTime = (DateTime)localIterator.next();
      localSCHoliday = (SCHoliday)localHashMap1.get(localDateTime);
      paramHistoryTracker.logCreate(az0, az7, localDateTime + " - " + localSCHoliday.getName(), paramHistoryTracker.lookupComment(8));
    }
  }
  
  private static void jdMethod_for(HistoryTracker paramHistoryTracker, SCCalendar paramSCCalendar1, SCCalendar paramSCCalendar2)
  {
    paramHistoryTracker.detectChange(az0, az2, new Boolean(paramSCCalendar2.getIsDefault()).toString(), new Boolean(paramSCCalendar1.getIsDefault()).toString(), paramHistoryTracker.lookupComment(8));
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.SmartCalendar
 * JD-Core Version:    0.7.0.1
 */