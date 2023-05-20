package com.ffusion.csil.core;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.disbursement.DisbursementAccount;
import com.ffusion.beans.disbursement.DisbursementPresentmentSummaries;
import com.ffusion.beans.disbursement.DisbursementSummaries;
import com.ffusion.beans.disbursement.DisbursementTransactions;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.util.AccountUtil;
import com.ffusion.beans.util.UtilException;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.handlers.DisbursementHandler;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.beans.PagingContext;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.logging.PerfLog;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;

public class Disbursements
  extends Initialize
{
  private static Entitlement aDX = new Entitlement("Controlled Disbursements", null, null);
  private static final String aDV = "com.ffusion.util.logging.audit.disbursements";
  private static final String aDW = "AuditMessage_1";
  private static final String aDU = "AuditEntFault_1";
  private static final String aDY = "AuditEntFault_2";
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    String str = "Disbursements.initialize";
    debug(str);
    long l = System.currentTimeMillis();
    DisbursementHandler.initialize(paramHashMap);
    PerfLog.log(str, l, true);
  }
  
  public static Object getService()
  {
    return DisbursementHandler.getService();
  }
  
  public static DisbursementSummaries getSummaries(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Disbursements.getSummaries";
    debug(paramSecureUser, str);
    jdMethod_for(paramSecureUser, str, null);
    long l = System.currentTimeMillis();
    DisbursementSummaries localDisbursementSummaries = DisbursementHandler.getSummaries(paramSecureUser, paramHashMap);
    PerfLog.log(str, l, true);
    return localDisbursementSummaries;
  }
  
  public static DisbursementSummaries getSummaries(SecureUser paramSecureUser, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Disbursements.getSummaries";
    debug(paramSecureUser, str);
    jdMethod_for(paramSecureUser, str, null);
    long l = System.currentTimeMillis();
    DisbursementSummaries localDisbursementSummaries = DisbursementHandler.getSummaries(paramSecureUser, paramCalendar1, paramCalendar2, paramHashMap);
    PerfLog.log(str, l, true);
    return localDisbursementSummaries;
  }
  
  public static DisbursementPresentmentSummaries getPresentmentSummaries(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Disbursements.getPresentmentSummaries";
    debug(paramSecureUser, str);
    jdMethod_for(paramSecureUser, str, null);
    long l = System.currentTimeMillis();
    paramHashMap.put("SECURE_USER", paramSecureUser);
    DisbursementPresentmentSummaries localDisbursementPresentmentSummaries = DisbursementHandler.getPresentmentSummaries(paramSecureUser, paramHashMap);
    paramHashMap.remove("SECURE_USER");
    PerfLog.log(str, l, true);
    return localDisbursementPresentmentSummaries;
  }
  
  public static DisbursementPresentmentSummaries getPresentmentSummaries(SecureUser paramSecureUser, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Disbursements.getPresentmentSummaries";
    debug(paramSecureUser, str);
    jdMethod_for(paramSecureUser, str, null);
    long l = System.currentTimeMillis();
    paramHashMap.put("SECURE_USER", paramSecureUser);
    DisbursementPresentmentSummaries localDisbursementPresentmentSummaries = DisbursementHandler.getPresentmentSummaries(paramSecureUser, paramCalendar1, paramCalendar2, paramHashMap);
    paramHashMap.remove("SECURE_USER");
    PerfLog.log(str, l, true);
    return localDisbursementPresentmentSummaries;
  }
  
  public static DisbursementSummaries getSummariesForPresentment(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Disbursements.getSummariesForPresentment";
    debug(paramSecureUser, str);
    jdMethod_for(paramSecureUser, str, null);
    long l = System.currentTimeMillis();
    DisbursementSummaries localDisbursementSummaries = DisbursementHandler.getSummariesForPresentment(paramSecureUser, paramString, paramHashMap);
    PerfLog.log(str, l, true);
    return localDisbursementSummaries;
  }
  
  public static DisbursementSummaries getSummariesForPresentment(SecureUser paramSecureUser, String paramString, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Disbursements.getSummariesForPresentment";
    debug(paramSecureUser, str);
    jdMethod_for(paramSecureUser, str, null);
    long l = System.currentTimeMillis();
    DisbursementSummaries localDisbursementSummaries = DisbursementHandler.getSummariesForPresentment(paramSecureUser, paramString, paramCalendar1, paramCalendar2, paramHashMap);
    PerfLog.log(str, l, true);
    return localDisbursementSummaries;
  }
  
  public static DisbursementTransactions getTransactions(SecureUser paramSecureUser, DisbursementAccount paramDisbursementAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    return getTransactions(paramSecureUser, paramDisbursementAccount, paramCalendar1, paramCalendar2, null, paramHashMap);
  }
  
  public static DisbursementTransactions getTransactions(SecureUser paramSecureUser, DisbursementAccount paramDisbursementAccount, Calendar paramCalendar1, Calendar paramCalendar2, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Disbursements.getTransactions";
    debug(paramSecureUser, str);
    jdMethod_for(paramSecureUser, str, paramDisbursementAccount);
    long l = System.currentTimeMillis();
    DisbursementTransactions localDisbursementTransactions = DisbursementHandler.getTransactions(paramSecureUser, paramDisbursementAccount, paramCalendar1, paramCalendar2, paramString, paramHashMap);
    PerfLog.log(str, l, true);
    return localDisbursementTransactions;
  }
  
  public static DisbursementTransactions getPagedTransactions(SecureUser paramSecureUser, DisbursementAccount paramDisbursementAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Disbursements.getPagedTransactions";
    debug(paramSecureUser, str);
    jdMethod_for(paramSecureUser, str, paramDisbursementAccount);
    long l = System.currentTimeMillis();
    DisbursementTransactions localDisbursementTransactions = DisbursementHandler.getPagedTransactions(paramSecureUser, paramDisbursementAccount, paramPagingContext, paramHashMap);
    PerfLog.log(str, l, true);
    return localDisbursementTransactions;
  }
  
  public static DisbursementTransactions getPagedTransactions(SecureUser paramSecureUser, DisbursementAccount paramDisbursementAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    return getPagedTransactions(paramSecureUser, paramDisbursementAccount, paramCalendar1, paramCalendar2, null, paramHashMap);
  }
  
  public static DisbursementTransactions getPagedTransactions(SecureUser paramSecureUser, DisbursementAccount paramDisbursementAccount, Calendar paramCalendar1, Calendar paramCalendar2, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    PagingContext localPagingContext = new PagingContext(paramCalendar1, paramCalendar2);
    localPagingContext.getMap().put("Presentment", paramString);
    return getPagedTransactions(paramSecureUser, paramDisbursementAccount, localPagingContext, paramHashMap);
  }
  
  public static DisbursementTransactions getRecentTransactions(SecureUser paramSecureUser, DisbursementAccount paramDisbursementAccount, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Disbursements.getRecentTransactions";
    debug(paramSecureUser, str);
    jdMethod_for(paramSecureUser, str, paramDisbursementAccount);
    long l = System.currentTimeMillis();
    DisbursementTransactions localDisbursementTransactions = DisbursementHandler.getRecentTransactions(paramSecureUser, paramDisbursementAccount, paramHashMap);
    PerfLog.log(str, l, true);
    return localDisbursementTransactions;
  }
  
  public static DisbursementTransactions getNextTransactions(SecureUser paramSecureUser, DisbursementAccount paramDisbursementAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Disbursements.getNextTransactions";
    debug(paramSecureUser, str);
    jdMethod_for(paramSecureUser, str, paramDisbursementAccount);
    long l = System.currentTimeMillis();
    DisbursementTransactions localDisbursementTransactions = DisbursementHandler.getNextTransactions(paramSecureUser, paramDisbursementAccount, paramPagingContext, paramHashMap);
    PerfLog.log(str, l, true);
    return localDisbursementTransactions;
  }
  
  public static DisbursementTransactions getNextTransactions(SecureUser paramSecureUser, DisbursementAccount paramDisbursementAccount, long paramLong, HashMap paramHashMap)
    throws CSILException
  {
    PagingContext localPagingContext = new PagingContext(null, null);
    return getNextTransactions(paramSecureUser, paramDisbursementAccount, localPagingContext, paramHashMap);
  }
  
  public static DisbursementTransactions getPreviousTransactions(SecureUser paramSecureUser, DisbursementAccount paramDisbursementAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Disbursements.getPreviousTransactions";
    debug(paramSecureUser, str);
    jdMethod_for(paramSecureUser, str, paramDisbursementAccount);
    long l = System.currentTimeMillis();
    DisbursementTransactions localDisbursementTransactions = DisbursementHandler.getPreviousTransactions(paramSecureUser, paramDisbursementAccount, paramPagingContext, paramHashMap);
    PerfLog.log(str, l, true);
    return localDisbursementTransactions;
  }
  
  public static DisbursementTransactions getPreviousTransactions(SecureUser paramSecureUser, DisbursementAccount paramDisbursementAccount, long paramLong, HashMap paramHashMap)
    throws CSILException
  {
    PagingContext localPagingContext = new PagingContext(null, null);
    return getPreviousTransactions(paramSecureUser, paramDisbursementAccount, localPagingContext, paramHashMap);
  }
  
  public static IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Disbursements.getReportData";
    debug(paramSecureUser, str1);
    jdMethod_for(paramSecureUser, str1, null);
    long l = System.currentTimeMillis();
    IReportResult localIReportResult = DisbursementHandler.getReportData(paramSecureUser, paramReportCriteria, paramHashMap);
    String str2 = paramReportCriteria.getReportOptions().getProperty("REPORTTYPE");
    EntitlementsUtil.checkReportTypeEntitlement(paramSecureUser, str2, true);
    String str3 = paramReportCriteria.getReportOptions().getProperty("TITLE");
    if ((str3 == null) || (str3.length() == 0)) {
      str3 = ReportConsts.getReportName(str2, Locale.getDefault());
    }
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = str3;
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.disbursements", "AuditMessage_1", arrayOfObject);
    audit(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID(), 1908);
    PerfLog.log(str1, l, true);
    return localIReportResult;
  }
  
  private static void jdMethod_for(SecureUser paramSecureUser, String paramString, DisbursementAccount paramDisbursementAccount)
    throws CSILException
  {
    Object localObject;
    if (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aDX))
    {
      DebugLog.log(paramString + ": User:" + paramSecureUser.getUserName() + " not entitled for disbursements.");
      localObject = new LocalizableString("com.ffusion.util.logging.audit.disbursements", "AuditEntFault_1", null);
      logEntitlementFault(paramSecureUser, (ILocalizable)localObject, TrackingIDGenerator.GetNextID());
      throw new CSILException(paramString, 20001);
    }
    if ((paramDisbursementAccount != null) && (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), new Entitlement("Controlled Disbursements", null, null))))
    {
      DebugLog.log(paramString + ": User:" + paramSecureUser.getUserName() + " not entitled to account " + paramDisbursementAccount.getAccountID() + " for disbursements.");
      localObject = new Object[1];
      try
      {
        localObject[0] = AccountUtil.buildLocalizableAccountID(paramDisbursementAccount.getAccountID());
      }
      catch (UtilException localUtilException)
      {
        DebugLog.log(Level.WARNING, "Unable to build localizable account ID for " + paramDisbursementAccount.getAccountID());
        localUtilException.printStackTrace();
        localObject[0] = paramDisbursementAccount.getAccountID();
      }
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.disbursements", "AuditEntFault_2", (Object[])localObject);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException(paramString, 20001);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.Disbursements
 * JD-Core Version:    0.7.0.1
 */