package com.ffusion.csil.core;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.stoppayments.StopCheck;
import com.ffusion.beans.stoppayments.StopChecks;
import com.ffusion.beans.util.AccountUtil;
import com.ffusion.beans.util.UtilException;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.beans.PagingContext;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.logging.PerfLog;
import java.util.Calendar;
import java.util.HashMap;
import java.util.logging.Level;

public class Stops
  extends Initialize
{
  private static Entitlement at9 = new Entitlement("Stops", null, null);
  public static final String STOPPAYMENT_AUDIT_STATE_ADDED = "ADDED";
  public static final String STOPPAYMENT_AUDIT_STATE_DELETED = "DELETED";
  public static final String STOPPAYMENT_AUDIT_STATE_MODIFIED = "MODIFIED";
  private static final String at8 = "com.ffusion.util.logging.audit.stops";
  private static final String aud = "AuditMessage_1.1";
  private static final String aub = "AuditMessage_1.2";
  private static final String at4 = "AuditMessage_1.3";
  private static final String auc = "AuditMessage_1.4";
  private static final String at7 = "AuditMessage_2.1";
  private static final String at2 = "AuditMessage_2.2";
  private static final String at5 = "AuditMessage_2.3";
  private static final String at1 = "AuditMessage_2.4";
  private static final String at3 = "AuditMessage_3.1";
  private static final String auf = "AuditMessage_3.2";
  private static final String aua = "AuditMessage_3.3";
  private static final String at6 = "AuditMessage_3.4";
  private static final String aue = "AuditEntFault_1";
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.core.Stops.initialize");
    com.ffusion.csil.handlers.Stops.initialize(paramHashMap);
  }
  
  public static Object getService()
  {
    return com.ffusion.csil.handlers.Stops.getService();
  }
  
  public static StopCheck addStopPayment(SecureUser paramSecureUser, StopCheck paramStopCheck, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Stops.AddStopPayment";
    if (jdMethod_goto(paramSecureUser))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      paramStopCheck.setTrackingID(str2);
      StopCheck localStopCheck = com.ffusion.csil.handlers.Stops.addStopPayment(paramSecureUser, paramStopCheck, paramHashMap);
      PerfLog.log(str1, l, true);
      Object localObject;
      try
      {
        localObject = AccountUtil.buildLocalizableAccountID(paramStopCheck.getAccountID());
      }
      catch (UtilException localUtilException)
      {
        DebugLog.log(Level.WARNING, "Unable to build localizable account ID for " + paramStopCheck.getAccountID());
        localUtilException.printStackTrace();
        localObject = paramStopCheck.getAccountID();
      }
      int i;
      String str3;
      Object[] arrayOfObject;
      LocalizableString localLocalizableString;
      if ((paramStopCheck.getPayeeName() == null) || (paramStopCheck.getPayeeName().trim().length() == 0))
      {
        i = 2;
        str3 = "AuditMessage_1.1";
        if ((paramStopCheck.getCheckNumbers() != null) && (paramStopCheck.getCheckNumbers().length() > 0))
        {
          i++;
          str3 = "AuditMessage_1.3";
        }
        arrayOfObject = new Object[i];
        arrayOfObject[0] = localObject;
        arrayOfObject[1] = paramStopCheck.getAmountValue();
        if (i == 3) {
          arrayOfObject[2] = paramStopCheck.getCheckNumbers();
        }
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.stops", str3, arrayOfObject);
      }
      else
      {
        i = 3;
        str3 = "AuditMessage_1.2";
        if ((paramStopCheck.getCheckNumbers() != null) && (paramStopCheck.getCheckNumbers().length() > 0))
        {
          i++;
          str3 = "AuditMessage_1.4";
        }
        arrayOfObject = new Object[i];
        arrayOfObject[0] = localObject;
        arrayOfObject[1] = paramStopCheck.getPayeeName();
        arrayOfObject[2] = paramStopCheck.getAmountValue();
        if (i == 4) {
          arrayOfObject[3] = paramStopCheck.getCheckNumbers();
        }
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.stops", str3, arrayOfObject);
      }
      audit(paramStopCheck.getAuditRecord(paramSecureUser, localLocalizableString, 3100, "ADDED"));
      debug(paramSecureUser, str1);
      return localStopCheck;
    }
    throw new CSILException(str1, 20001);
  }
  
  public static StopCheck deleteStopPayment(SecureUser paramSecureUser, StopCheck paramStopCheck, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Stops.DeleteStopPayment";
    if (jdMethod_goto(paramSecureUser))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      StopCheck localStopCheck = com.ffusion.csil.handlers.Stops.deleteStopPayment(paramSecureUser, paramStopCheck, paramHashMap);
      PerfLog.log(str1, l, true);
      Object localObject;
      try
      {
        localObject = AccountUtil.buildLocalizableAccountID(paramStopCheck.getAccountID());
      }
      catch (UtilException localUtilException)
      {
        DebugLog.log(Level.WARNING, "Unable to build localizable account ID for " + paramStopCheck.getAccountID());
        localUtilException.printStackTrace();
        localObject = paramStopCheck.getAccountID();
      }
      int i;
      String str2;
      Object[] arrayOfObject;
      LocalizableString localLocalizableString;
      if ((paramStopCheck.getPayeeName() == null) || (paramStopCheck.getPayeeName().trim().length() == 0))
      {
        i = 2;
        str2 = "AuditMessage_2.1";
        if ((paramStopCheck.getCheckNumbers() != null) && (paramStopCheck.getCheckNumbers().length() > 0))
        {
          i++;
          str2 = "AuditMessage_2.3";
        }
        arrayOfObject = new Object[i];
        arrayOfObject[0] = localObject;
        arrayOfObject[1] = paramStopCheck.getAmountValue();
        if (i == 3) {
          arrayOfObject[2] = paramStopCheck.getCheckNumbers();
        }
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.stops", str2, arrayOfObject);
      }
      else
      {
        i = 3;
        str2 = "AuditMessage_2.2";
        if ((paramStopCheck.getCheckNumbers() != null) && (paramStopCheck.getCheckNumbers().length() > 0))
        {
          i++;
          str2 = "AuditMessage_2.4";
        }
        arrayOfObject = new Object[i];
        arrayOfObject[0] = localObject;
        arrayOfObject[1] = paramStopCheck.getPayeeName();
        arrayOfObject[2] = paramStopCheck.getAmountValue();
        if (i == 4) {
          arrayOfObject[3] = paramStopCheck.getCheckNumbers();
        }
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.stops", str2, arrayOfObject);
      }
      audit(paramStopCheck.getAuditRecord(paramSecureUser, localLocalizableString, 3101, "DELETED"));
      debug(paramSecureUser, str1);
      return localStopCheck;
    }
    throw new CSILException(str1, 20001);
  }
  
  public static StopChecks getStopPayments(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Stops.GetStopPayments";
    if (jdMethod_goto(paramSecureUser))
    {
      long l = System.currentTimeMillis();
      StopChecks localStopChecks = com.ffusion.csil.handlers.Stops.getStopPayments(paramSecureUser, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localStopChecks;
    }
    throw new CSILException(str, 20001);
  }
  
  public static StopChecks getStopPayments(SecureUser paramSecureUser, Accounts paramAccounts, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Stops.GetStopPayments";
    if (jdMethod_goto(paramSecureUser))
    {
      long l = System.currentTimeMillis();
      StopChecks localStopChecks = com.ffusion.csil.handlers.Stops.getStopPayments(paramSecureUser, paramAccounts, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localStopChecks;
    }
    throw new CSILException(str, 20001);
  }
  
  public static StopChecks getStopPayments(SecureUser paramSecureUser, Accounts paramAccounts, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Stops.GetStopPayments";
    if (jdMethod_goto(paramSecureUser))
    {
      long l = System.currentTimeMillis();
      StopChecks localStopChecks = com.ffusion.csil.handlers.Stops.getStopPayments(paramSecureUser, paramAccounts, paramCalendar1, paramCalendar2, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localStopChecks;
    }
    throw new CSILException(str, 20001);
  }
  
  public static StopChecks getStopPayments(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Stops.getStopPayments";
    if (jdMethod_goto(paramSecureUser))
    {
      long l = System.currentTimeMillis();
      StopChecks localStopChecks = com.ffusion.csil.handlers.Stops.getStopPayments(paramSecureUser, paramPagingContext, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localStopChecks;
    }
    throw new CSILException(str, 20001);
  }
  
  public static StopCheck modStopPayment(SecureUser paramSecureUser, StopCheck paramStopCheck, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Stops.modStopPayments";
    if (jdMethod_goto(paramSecureUser))
    {
      long l = System.currentTimeMillis();
      paramStopCheck = com.ffusion.csil.handlers.Stops.modStopPayment(paramSecureUser, paramStopCheck, paramHashMap);
      PerfLog.log(str1, l, true);
      Object localObject;
      try
      {
        localObject = AccountUtil.buildLocalizableAccountID(paramStopCheck.getAccountID());
      }
      catch (UtilException localUtilException)
      {
        DebugLog.log(Level.WARNING, "Unable to build localizable account ID for " + paramStopCheck.getAccountID());
        localUtilException.printStackTrace();
        localObject = paramStopCheck.getAccountID();
      }
      int i;
      String str2;
      Object[] arrayOfObject;
      LocalizableString localLocalizableString;
      if ((paramStopCheck.getPayeeName() == null) || (paramStopCheck.getPayeeName().trim().length() == 0))
      {
        i = 2;
        str2 = "AuditMessage_3.1";
        if ((paramStopCheck.getCheckNumbers() != null) && (paramStopCheck.getCheckNumbers().length() > 0))
        {
          i++;
          str2 = "AuditMessage_3.3";
        }
        arrayOfObject = new Object[i];
        arrayOfObject[0] = localObject;
        arrayOfObject[1] = paramStopCheck.getAmountValue();
        if (i == 3) {
          arrayOfObject[2] = paramStopCheck.getCheckNumbers();
        }
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.stops", str2, arrayOfObject);
      }
      else
      {
        i = 3;
        str2 = "AuditMessage_3.2";
        if ((paramStopCheck.getCheckNumbers() != null) && (paramStopCheck.getCheckNumbers().length() > 0))
        {
          i++;
          str2 = "AuditMessage_3.4";
        }
        arrayOfObject = new Object[i];
        arrayOfObject[0] = localObject;
        arrayOfObject[1] = paramStopCheck.getPayeeName();
        arrayOfObject[2] = paramStopCheck.getAmountValue();
        if (i == 4) {
          arrayOfObject[3] = paramStopCheck.getCheckNumbers();
        }
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.stops", str2, arrayOfObject);
      }
      audit(paramStopCheck.getAuditRecord(paramSecureUser, localLocalizableString, 3102, "MODIFIED"));
      debug(paramSecureUser, str1);
      return paramStopCheck;
    }
    throw new CSILException(str1, 20001);
  }
  
  private static boolean jdMethod_goto(SecureUser paramSecureUser)
    throws CSILException
  {
    boolean bool = Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), at9);
    if (!bool)
    {
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.stops", "AuditEntFault_1", null);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    }
    return bool;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.Stops
 * JD-Core Version:    0.7.0.1
 */