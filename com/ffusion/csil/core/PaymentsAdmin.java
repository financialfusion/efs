package com.ffusion.csil.core;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.CutOffActivities;
import com.ffusion.beans.affiliatebank.CutOffTime;
import com.ffusion.beans.affiliatebank.CutOffTimes;
import com.ffusion.beans.affiliatebank.FulfillmentSystems;
import com.ffusion.beans.affiliatebank.ScheduleActivities;
import com.ffusion.beans.affiliatebank.ScheduleCategory;
import com.ffusion.beans.affiliatebank.ScheduleType;
import com.ffusion.beans.banking.BankingDays;
import com.ffusion.beans.billpay.Payee;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.paymentsadmin.ProcessingWindow;
import com.ffusion.beans.paymentsadmin.ProcessingWindows;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.handlers.PaymentsAdminHandler;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.beans.PagingContext;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.logging.PerfLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

public class PaymentsAdmin
  extends Initialize
{
  private static Entitlement avf = new Entitlement("BC Wire Processing Windows", null, null);
  private static Entitlement avb = new Entitlement("BC Positive Pay Cut-offs View", null, null);
  private static Entitlement auZ = new Entitlement("BC Positive Pay Cut-offs Administration", null, null);
  private static Entitlement auR = new Entitlement("BC ACH Batch Cut-offs View", null, null);
  private static Entitlement au0 = new Entitlement("BC ACH Batch Cut-offs Administration", null, null);
  private static Entitlement au1 = new Entitlement("BC ACH File Upload Cut-offs View", null, null);
  private static Entitlement au7 = new Entitlement("BC ACH File Upload Cut-offs Administration", null, null);
  private static Entitlement au2 = new Entitlement("BC Bill Pay Cut-offs View", null, null);
  private static Entitlement auO = new Entitlement("BC Bill Pay Cut-offs Administration", null, null);
  private static Entitlement auS = new Entitlement("BC Book Transfer Pay Cut-offs View", null, null);
  private static Entitlement avc = new Entitlement("BC Book Transfer Pay Cut-offs Administration", null, null);
  private static Entitlement auV = new Entitlement("BC Cash Con Cut-offs Administration", null, null);
  private static Entitlement au8 = new Entitlement("BC Cash Con Cut-offs Administration", null, null);
  private static Entitlement auX = new Entitlement("BC External Transfer Pay Cut-offs View", null, null);
  private static Entitlement avd = new Entitlement("BC External Transfer Pay Cut-offs Administration", null, null);
  private static Entitlement auU = new Entitlement("BC Wires Cut-offs View", null, null);
  private static Entitlement au6 = new Entitlement("BC Wires Cut-offs Administration", null, null);
  private static Entitlement auT = new Entitlement("BC System Cut-offs View", null, null);
  private static Entitlement auQ = new Entitlement("BC System Cut-offs Administration", null, null);
  private static Entitlement auW = new Entitlement("BC Other Cut-offs View", null, null);
  private static Entitlement avg = new Entitlement("BC Other Cut-offs Administration", null, null);
  private static Entitlement au4 = new Entitlement("BC Global Payees Administration", null, null);
  private static final String au3 = "com.ffusion.util.logging.audit.paymentsadmin";
  private static final String au5 = "AuditMessage_1";
  private static final String au9 = "AuditMessage_2";
  private static final String auN = "AuditMessage_3";
  private static final String auP = "AuditMessage_4";
  private static final String ave = "AuditMessage_5";
  private static final String auY = "AuditMessage_6";
  private static final String ava = "AuditMessage_7";
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.core.PaymentsAdmin.initialize");
    PaymentsAdminHandler.initialize(paramHashMap);
  }
  
  public static Object getService()
  {
    return PaymentsAdminHandler.getService();
  }
  
  public static ProcessingWindow addProcessingWindow(SecureUser paramSecureUser, ProcessingWindow paramProcessingWindow, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PaymentsAdmin.addProcessingWindow";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    if (!Entitlements.checkEntitlement(localEntitlementGroupMember, avf))
    {
      debug("User: " + paramSecureUser.getUserName() + " is not entitled to add Wire processing window.");
      throw new CSILException(str, 20001);
    }
    paramProcessingWindow.setSubmittedBy(paramSecureUser.getProfileID());
    paramProcessingWindow = PaymentsAdminHandler.addProcessingWindow(paramSecureUser, paramProcessingWindow, paramHashMap);
    debug(paramSecureUser, str);
    return paramProcessingWindow;
  }
  
  public static ProcessingWindow modifyProcessingWindow(SecureUser paramSecureUser, ProcessingWindow paramProcessingWindow, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PaymentsAdmin.modifyProcessingWindow";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    if (!Entitlements.checkEntitlement(localEntitlementGroupMember, avf))
    {
      debug("User: " + paramSecureUser.getUserName() + " is not entitled to modify Wire processing window.");
      throw new CSILException(str, 20001);
    }
    paramProcessingWindow.setSubmittedBy(paramSecureUser.getProfileID());
    paramProcessingWindow = PaymentsAdminHandler.modifyProcessingWindow(paramSecureUser, paramProcessingWindow, paramHashMap);
    debug(paramSecureUser, str);
    return paramProcessingWindow;
  }
  
  public static ProcessingWindows getProcessingWindows(SecureUser paramSecureUser, ProcessingWindow paramProcessingWindow, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PaymentsAdmin.getProcessingWindows";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    if (!Entitlements.checkEntitlement(localEntitlementGroupMember, avf))
    {
      debug("User: " + paramSecureUser.getUserName() + " is not entitled to get Wire processing windows.");
      throw new CSILException(str, 20001);
    }
    ProcessingWindows localProcessingWindows = PaymentsAdminHandler.getProcessingWindows(paramSecureUser, paramProcessingWindow, paramHashMap);
    debug(paramSecureUser, str);
    return localProcessingWindows;
  }
  
  public static void deleteProcessingWindow(SecureUser paramSecureUser, ProcessingWindow paramProcessingWindow, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PaymentsAdmin.deleteProcessingWindows";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    if (!Entitlements.checkEntitlement(localEntitlementGroupMember, avf))
    {
      debug("User: " + paramSecureUser.getUserName() + " is not entitled to delete Wire processing window.");
      throw new CSILException(str, 20001);
    }
    PaymentsAdminHandler.deleteProcessingWindow(paramSecureUser, paramProcessingWindow, paramHashMap);
    debug(paramSecureUser, str);
  }
  
  public static FulfillmentSystems getFulfillmentSystems(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    return PaymentsAdminHandler.getFulfillmentSystems(paramSecureUser, paramHashMap);
  }
  
  public static ArrayList getGlobalPayeeGroups(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    return PaymentsAdminHandler.getGlobalPayeeGroups(paramSecureUser, paramHashMap);
  }
  
  public static Payees searchGlobalPayees(SecureUser paramSecureUser, Payee paramPayee, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PaymentsAdmin.searchGlobalPayees";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), au4))
    {
      localObject = PaymentsAdminHandler.searchGlobalPayees(paramSecureUser, paramPayee, paramHashMap);
      return localObject;
    }
    Object localObject = new LocalizableString("com.ffusion.util.logging.audit.paymentsadmin", "AuditMessage_7", null);
    logEntitlementFault(paramSecureUser, (ILocalizable)localObject, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static Payee getGlobalPayee(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PaymentsAdmin.getGlobalPayee";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), au4))
    {
      localObject = PaymentsAdminHandler.getGlobalPayee(paramSecureUser, paramInt, paramHashMap);
      return localObject;
    }
    Object localObject = new LocalizableString("com.ffusion.util.logging.audit.paymentsadmin", "AuditMessage_7", null);
    logEntitlementFault(paramSecureUser, (ILocalizable)localObject, TrackingIDGenerator.GetNextID());
    throw new CSILException(str, 20001);
  }
  
  public static Payee modifyGlobalPayee(SecureUser paramSecureUser, Payee paramPayee, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "PaymentsAdmin.modifyGlobalPayee";
    long l = System.currentTimeMillis();
    String str2 = TrackingIDGenerator.GetNextID();
    paramPayee.setTrackingID(str2);
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), au4))
    {
      localObject = null;
      try
      {
        localObject = PaymentsAdminHandler.modifyGlobalPayee(paramSecureUser, paramPayee, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        PerfLog.log(str1, l, true);
        LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.paymentsadmin", "AuditMessage_3", null);
        audit(paramSecureUser, localLocalizableString1, str2, 6303);
        debug(paramSecureUser, str1);
        throw localCSILException;
      }
      HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 28, paramPayee.getID());
      paramPayee.logCreation(localHistoryTracker, localHistoryTracker.buildLocalizableComment(17));
      try
      {
        HistoryAdapter.addHistory(localHistoryTracker.getHistories());
      }
      catch (ProfileException localProfileException)
      {
        DebugLog.log(Level.SEVERE, "Modify History failed for ModifyGlobalPayee: " + localProfileException.toString());
      }
      PerfLog.log(str1, l, true);
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.paymentsadmin", "AuditMessage_4", null);
      audit(paramSecureUser, localLocalizableString2, str2, 6303);
      debug(paramSecureUser, str1);
      return localObject;
    }
    Object localObject = new LocalizableString("com.ffusion.util.logging.audit.paymentsadmin", "AuditMessage_7", null);
    logEntitlementFault(paramSecureUser, (ILocalizable)localObject, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static ScheduleCategory getScheduleCategory(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PaymentsAdmin.getScheduleCategory";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    Entitlement localEntitlement = Q(paramString2);
    if (!Entitlements.checkEntitlement(localEntitlementGroupMember, localEntitlement))
    {
      debug("User: " + paramSecureUser.getUserName() + " is not entitled to view Schedules and Cut-off times.");
      throw new CSILException(str, 20001);
    }
    debug(paramSecureUser, str);
    return PaymentsAdminHandler.getScheduleCategory(paramSecureUser, paramString1, paramString2, paramHashMap);
  }
  
  public static ScheduleType addScheduleType(SecureUser paramSecureUser, ScheduleType paramScheduleType, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PaymentsAdmin.addScheduleType";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    Entitlement localEntitlement = R(paramScheduleType.getCategory());
    if (!Entitlements.checkEntitlement(localEntitlementGroupMember, localEntitlement))
    {
      debug("User: " + paramSecureUser.getUserName() + " is not entitled to add " + paramScheduleType.getCategory() + " Schedule.");
      throw new CSILException(str, 20001);
    }
    paramScheduleType = PaymentsAdminHandler.addScheduleType(paramSecureUser, paramScheduleType, paramHashMap);
    debug(paramSecureUser, str);
    return paramScheduleType;
  }
  
  public static ScheduleType modifyScheduleType(SecureUser paramSecureUser, ScheduleType paramScheduleType, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PaymentsAdmin.modifyScheduleType";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    Entitlement localEntitlement = R(paramScheduleType.getCategory());
    if (!Entitlements.checkEntitlement(localEntitlementGroupMember, localEntitlement))
    {
      debug("User: " + paramSecureUser.getUserName() + " is not entitled to modify " + paramScheduleType.getCategory() + " Schedule.");
      throw new CSILException(str, 20001);
    }
    paramScheduleType = PaymentsAdminHandler.modifyScheduleType(paramSecureUser, paramScheduleType, paramHashMap);
    debug(paramSecureUser, str);
    return paramScheduleType;
  }
  
  public static void deleteScheduleType(SecureUser paramSecureUser, ScheduleType paramScheduleType, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PaymentsAdmin.deleteScheduleType";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    Entitlement localEntitlement = R(paramScheduleType.getCategory());
    if (!Entitlements.checkEntitlement(localEntitlementGroupMember, localEntitlement))
    {
      debug("User: " + paramSecureUser.getUserName() + " is not entitled to delete " + paramScheduleType.getCategory() + " Schedule.");
      throw new CSILException(str, 20001);
    }
    PaymentsAdminHandler.deleteScheduleType(paramSecureUser, paramScheduleType, paramHashMap);
    debug(paramSecureUser, str);
  }
  
  public static ScheduleType getScheduleType(SecureUser paramSecureUser, String paramString1, String paramString2, String paramString3, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PaymentsAdmin.getScheduleType";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    Entitlement localEntitlement = Q(paramString3);
    if (!Entitlements.checkEntitlement(localEntitlementGroupMember, localEntitlement))
    {
      debug("User: " + paramSecureUser.getUserName() + " is not entitled to get " + paramString3 + " Schedule.");
      throw new CSILException(str, 20001);
    }
    ScheduleType localScheduleType = PaymentsAdminHandler.getScheduleType(paramSecureUser, paramString1, paramString2, paramHashMap);
    debug(paramSecureUser, str);
    return localScheduleType;
  }
  
  public static CutOffTime addCutOffTime(SecureUser paramSecureUser, CutOffTime paramCutOffTime, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PaymentsAdmin.addCutOffTime";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    Entitlement localEntitlement = R(paramCutOffTime.getCategory());
    if (!Entitlements.checkEntitlement(localEntitlementGroupMember, localEntitlement))
    {
      debug("User: " + paramSecureUser.getUserName() + " is not entitled to add " + paramCutOffTime.getCategory() + " Cut-Off.");
      throw new CSILException(str, 20001);
    }
    paramCutOffTime.setLogId(TrackingIDGenerator.GetNextID());
    paramCutOffTime.setSubmittedBy(String.valueOf(paramSecureUser.getProfileID()));
    paramCutOffTime = PaymentsAdminHandler.addCutOffTime(paramSecureUser, paramCutOffTime, paramHashMap);
    debug(paramSecureUser, str);
    return paramCutOffTime;
  }
  
  public static CutOffTime modifyCutOffTime(SecureUser paramSecureUser, CutOffTime paramCutOffTime, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PaymentsAdmin.modifyCutOffTime";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    Entitlement localEntitlement = R(paramCutOffTime.getCategory());
    if (!Entitlements.checkEntitlement(localEntitlementGroupMember, localEntitlement))
    {
      debug("User: " + paramSecureUser.getUserName() + " is not entitled to modify " + paramCutOffTime.getCategory() + " Cut-Off.");
      throw new CSILException(str, 20001);
    }
    paramCutOffTime = PaymentsAdminHandler.modifyCutOffTime(paramSecureUser, paramCutOffTime, paramHashMap);
    debug(paramSecureUser, str);
    return paramCutOffTime;
  }
  
  public static void deleteCutOffTime(SecureUser paramSecureUser, CutOffTime paramCutOffTime, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PaymentsAdmin.deleteCutOffTime";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    Entitlement localEntitlement = R(paramCutOffTime.getCategory());
    if (!Entitlements.checkEntitlement(localEntitlementGroupMember, localEntitlement))
    {
      debug("User: " + paramSecureUser.getUserName() + " is not entitled to delete " + paramCutOffTime.getCategory() + " Cut-Off.");
      throw new CSILException(str, 20001);
    }
    PaymentsAdminHandler.deleteCutOffTime(paramSecureUser, paramCutOffTime, paramHashMap);
    debug(paramSecureUser, str);
  }
  
  public static CutOffTimes getCutOffTimes(SecureUser paramSecureUser, CutOffTime paramCutOffTime, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PaymentsAdmin.getCutOffTimes";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    Entitlement localEntitlement = Q(paramCutOffTime.getCategory());
    if (!Entitlements.checkEntitlement(localEntitlementGroupMember, localEntitlement))
    {
      debug("User: " + paramSecureUser.getUserName() + " is not entitled to get " + paramCutOffTime.getCategory() + " Cut-Offs.");
      throw new CSILException(str, 20001);
    }
    CutOffTimes localCutOffTimes = PaymentsAdminHandler.getCutOffTimes(paramSecureUser, paramCutOffTime, paramHashMap);
    debug(paramSecureUser, str);
    return localCutOffTimes;
  }
  
  public static void resubmitSchedule(SecureUser paramSecureUser, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "PaymentsAdminHandler.resubmitSchedule";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    Entitlement localEntitlement = R(paramString5);
    if (!Entitlements.checkEntitlement(localEntitlementGroupMember, localEntitlement))
    {
      debug("User: " + paramSecureUser.getUserName() + " is not entitled to resubmit " + paramString5 + " Schedules.");
      throw new CSILException(str1, 20001);
    }
    debug(paramSecureUser, str1);
    String str2 = Banking.getSrvrTIdByTrackingId(paramSecureUser, paramString4);
    if ((str2 == null) || (str2.length() == 0))
    {
      debug(str1 + ": SrvrTId not found for transaction with TrackingId = " + paramString4);
      debug(str1 + ": Resubmitting Schedule with LogDate only.");
    }
    PaymentsAdminHandler.resubmitSchedule(paramString1, paramString2, paramString3, str2);
  }
  
  public static void runSchedule(SecureUser paramSecureUser, String paramString1, String paramString2, String paramString3, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PaymentsAdminHandler.runSchedule";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    Entitlement localEntitlement = R(paramString3);
    if (!Entitlements.checkEntitlement(localEntitlementGroupMember, localEntitlement))
    {
      debug("User: " + paramSecureUser.getUserName() + " is not entitled to run " + paramString3 + " Schedules.");
      throw new CSILException(str, 20001);
    }
    debug(paramSecureUser, str);
    PaymentsAdminHandler.runSchedule(paramString1, paramString2);
  }
  
  public static CutOffActivities getCutOffActivities(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PaymentsAdmin.getCutOffActivities";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    Entitlement localEntitlement = Q(paramString1);
    if (!Entitlements.checkEntitlement(localEntitlementGroupMember, localEntitlement))
    {
      debug("User: " + paramSecureUser.getUserName() + " is not entitled to get " + paramString1 + " Cut-Off Activities.");
      throw new CSILException(str, 20001);
    }
    CutOffActivities localCutOffActivities = PaymentsAdminHandler.getCutOffActivities(paramSecureUser, paramString2, paramPagingContext, paramHashMap);
    debug(paramSecureUser, str);
    return localCutOffActivities;
  }
  
  public static void rerunCutOffProcess(SecureUser paramSecureUser, CutOffTime paramCutOffTime, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PaymentsAdminHandler.rerunCutOff";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    Entitlement localEntitlement = R(paramCutOffTime.getCategory());
    if (!Entitlements.checkEntitlement(localEntitlementGroupMember, localEntitlement))
    {
      debug("User: " + paramSecureUser.getUserName() + " is not entitled to rerun " + paramCutOffTime.getCategory() + " CutOff process.");
      throw new CSILException(str, 20001);
    }
    debug(paramSecureUser, str);
    PaymentsAdminHandler.rerunCutOffProcess(paramSecureUser, paramCutOffTime, paramString, paramHashMap);
  }
  
  public static String getGeneratedFileName(SecureUser paramSecureUser, CutOffTime paramCutOffTime, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PaymentsAdmin.getGeneratedFileName";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    Entitlement localEntitlement = R(paramCutOffTime.getCategory());
    if (!Entitlements.checkEntitlement(localEntitlementGroupMember, localEntitlement))
    {
      debug("User: " + paramSecureUser.getUserName() + " is not entitled to get generated file name of " + paramCutOffTime.getCategory() + " CutOff process.");
      throw new CSILException(str, 20001);
    }
    debug(paramSecureUser, str);
    return PaymentsAdminHandler.getGeneratedFileName(paramSecureUser, paramCutOffTime, paramString, paramHashMap);
  }
  
  public static void cleanup(HashMap paramHashMap)
    throws CSILException
  {
    String str = "PaymentsAdmin.cleanup";
    long l = System.currentTimeMillis();
    PaymentsAdminHandler.cleanup(paramHashMap);
    debug(str);
  }
  
  public static ScheduleActivities getScheduleActivities(SecureUser paramSecureUser, String paramString1, String paramString2, String paramString3, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PaymentsAdmin.getScheduleActivities";
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    Entitlement localEntitlement = Q(paramString1);
    if (!Entitlements.checkEntitlement(localEntitlementGroupMember, localEntitlement))
    {
      debug("User: " + paramSecureUser.getUserName() + " is not entitled to get " + paramString1 + " Schedule Activities.");
      throw new CSILException(str, 20001);
    }
    ScheduleActivities localScheduleActivities = PaymentsAdminHandler.getScheduleActivities(paramSecureUser, paramString2, paramString3, paramPagingContext, paramHashMap);
    debug(paramSecureUser, str);
    return localScheduleActivities;
  }
  
  private static Entitlement Q(String paramString)
  {
    if (paramString.equalsIgnoreCase("ACHBatch")) {
      return auR;
    }
    if (paramString.equalsIgnoreCase("ACHFileUpload")) {
      return au1;
    }
    if (paramString.equalsIgnoreCase("BillPay")) {
      return au2;
    }
    if (paramString.equalsIgnoreCase("BookTransfer")) {
      return auS;
    }
    if (paramString.equalsIgnoreCase("CashConcentration")) {
      return auV;
    }
    if (paramString.equalsIgnoreCase("ExternalTransfer")) {
      return auX;
    }
    if (paramString.equalsIgnoreCase("PositivePay")) {
      return avb;
    }
    if (paramString.equalsIgnoreCase("Wires")) {
      return auU;
    }
    if (paramString.equalsIgnoreCase("System")) {
      return auT;
    }
    if (paramString.equalsIgnoreCase("Other")) {
      return auW;
    }
    return auR;
  }
  
  private static Entitlement R(String paramString)
  {
    if (paramString.equalsIgnoreCase("ACHBatch")) {
      return au0;
    }
    if (paramString.equalsIgnoreCase("ACHFileUpload")) {
      return au7;
    }
    if (paramString.equalsIgnoreCase("BillPay")) {
      return auO;
    }
    if (paramString.equalsIgnoreCase("BookTransfer")) {
      return avc;
    }
    if (paramString.equalsIgnoreCase("CashConcentration")) {
      return au8;
    }
    if (paramString.equalsIgnoreCase("ExternalTransfer")) {
      return avd;
    }
    if (paramString.equalsIgnoreCase("PositivePay")) {
      return auZ;
    }
    if (paramString.equalsIgnoreCase("Wires")) {
      return au6;
    }
    if (paramString.equalsIgnoreCase("System")) {
      return auQ;
    }
    if (paramString.equalsIgnoreCase("Other")) {
      return avg;
    }
    return au0;
  }
  
  public static BankingDays getBankingDaysInRange(BankingDays paramBankingDays, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PaymentsAdmin.getBankingDaysInRange";
    paramBankingDays = PaymentsAdminHandler.getBankingDaysInRange(paramBankingDays, paramHashMap);
    debug(str);
    return paramBankingDays;
  }
  
  public static String getGlobalPayeeDisplayCount(SecureUser paramSecureUser)
    throws CSILException
  {
    String str1 = "PaymentsAdmin.getGlobalPayeeDisplayCount";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), au4))
    {
      long l = System.currentTimeMillis();
      String str2 = PaymentsAdminHandler.getGlobalPayeeDisplayCount();
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return str2;
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.paymentsadmin", "AuditMessage_7", null);
    logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    throw new CSILException(str1, 20001);
  }
  
  public static void addGlobalPayee(SecureUser paramSecureUser, Payee paramPayee, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "PaymentsAdmin.addGlobalPayee";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), au4))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      paramPayee.setTrackingID(str2);
      try
      {
        PaymentsAdminHandler.addGlobalPayee(paramSecureUser, paramPayee, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        PerfLog.log(str1, l, true);
        LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.paymentsadmin", "AuditMessage_1", null);
        audit(paramSecureUser, localLocalizableString2, str2, 6302);
        debug(paramSecureUser, str1);
        throw localCSILException;
      }
      HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 28, paramPayee.getID());
      paramPayee.logCreation(localHistoryTracker, localHistoryTracker.buildLocalizableComment(1));
      try
      {
        HistoryAdapter.addHistory(localHistoryTracker.getHistories());
      }
      catch (ProfileException localProfileException)
      {
        DebugLog.log(Level.SEVERE, "Add History failed for AddGlobalPayee: " + localProfileException.toString());
      }
      PerfLog.log(str1, l, true);
      LocalizableString localLocalizableString3 = new LocalizableString("com.ffusion.util.logging.audit.paymentsadmin", "AuditMessage_2", null);
      audit(paramSecureUser, localLocalizableString3, str2, 6302);
      debug(paramSecureUser, str1);
    }
    else
    {
      LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.paymentsadmin", "AuditMessage_7", null);
      logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
  }
  
  public static void deleteGlobalPayee(SecureUser paramSecureUser, Payee paramPayee, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "PaymentsAdmin.deleteGlobalPayee";
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), au4))
    {
      long l = System.currentTimeMillis();
      String str2 = TrackingIDGenerator.GetNextID();
      paramPayee.setTrackingID(str2);
      try
      {
        PaymentsAdminHandler.deleteGlobalPayee(paramSecureUser, paramPayee, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        PerfLog.log(str1, l, true);
        LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.paymentsadmin", "AuditMessage_5", null);
        audit(paramSecureUser, localLocalizableString2, str2, 6304);
        debug(paramSecureUser, str1);
        throw localCSILException;
      }
      HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 28, paramPayee.getID());
      paramPayee.logCreation(localHistoryTracker, localHistoryTracker.buildLocalizableComment(2));
      try
      {
        HistoryAdapter.addHistory(localHistoryTracker.getHistories());
      }
      catch (ProfileException localProfileException)
      {
        DebugLog.log(Level.SEVERE, "Add History failed for DeleteGlobalPayee: " + localProfileException.toString());
      }
      PerfLog.log(str1, l, true);
      LocalizableString localLocalizableString3 = new LocalizableString("com.ffusion.util.logging.audit.paymentsadmin", "AuditMessage_6", null);
      audit(paramSecureUser, localLocalizableString3, str2, 6304);
      debug(paramSecureUser, str1);
    }
    else
    {
      LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.paymentsadmin", "AuditMessage_7", null);
      logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
      throw new CSILException(str1, 20001);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.PaymentsAdmin
 * JD-Core Version:    0.7.0.1
 */