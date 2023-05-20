package com.ffusion.csil.handlers;

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
import com.ffusion.csil.CSILException;
import com.ffusion.services.PAException;
import com.ffusion.services.PaymentsAdminService2;
import com.ffusion.services.PaymentsAdminService3;
import com.ffusion.util.beans.PagingContext;
import com.ffusion.util.logging.DebugLog;
import java.util.ArrayList;
import java.util.HashMap;

public class PaymentsAdminHandler
{
  private static PaymentsAdminService2 jdField_do = null;
  private static String a;
  private static final String jdField_if = "PaymentsAdmin";
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.PaymentsAdminHandler.initialize";
    try
    {
      HashMap localHashMap = HandlerUtil.getServiceSettings(paramHashMap, "PaymentsAdmin", str, 20107);
      jdField_do = (PaymentsAdminService2)HandlerUtil.instantiateService(localHashMap, str, 20107);
      jdField_do.initialize("");
      a = HandlerUtil.getGlobalPayeeDisplaySize(paramHashMap);
    }
    catch (Exception localException)
    {
      CSILException localCSILException = new CSILException(20107, localException);
      DebugLog.throwing(str, localException);
      throw localCSILException;
    }
  }
  
  public static Object getService()
  {
    return jdField_do;
  }
  
  public static ProcessingWindow addProcessingWindow(SecureUser paramSecureUser, ProcessingWindow paramProcessingWindow, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PaymentsAdminHandler.addProcessingWindow";
    try
    {
      paramProcessingWindow = jdField_do.addProcessingWindow(paramSecureUser, paramProcessingWindow, paramHashMap);
    }
    catch (PAException localPAException)
    {
      throw new CSILException(-1009, a(localPAException.getErrorCode()));
    }
    return paramProcessingWindow;
  }
  
  public static ProcessingWindow modifyProcessingWindow(SecureUser paramSecureUser, ProcessingWindow paramProcessingWindow, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PaymentsAdminHandler.modifyProcessingWindow";
    try
    {
      paramProcessingWindow = jdField_do.modifyProcessingWindow(paramSecureUser, paramProcessingWindow, paramHashMap);
    }
    catch (PAException localPAException)
    {
      throw new CSILException(-1009, a(localPAException.getErrorCode()));
    }
    return paramProcessingWindow;
  }
  
  public static void deleteProcessingWindow(SecureUser paramSecureUser, ProcessingWindow paramProcessingWindow, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PaymentsAdminHandler.deleteProcessingWindow";
    try
    {
      jdField_do.deleteProcessingWindow(paramSecureUser, paramProcessingWindow, paramHashMap);
    }
    catch (PAException localPAException)
    {
      throw new CSILException(-1009, a(localPAException.getErrorCode()));
    }
  }
  
  public static ProcessingWindows getProcessingWindows(SecureUser paramSecureUser, ProcessingWindow paramProcessingWindow, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PaymentsAdminHandler.getProcessingWindows";
    ProcessingWindows localProcessingWindows = null;
    try
    {
      localProcessingWindows = jdField_do.getProcessingWindows(paramSecureUser, paramProcessingWindow, paramHashMap);
    }
    catch (PAException localPAException)
    {
      throw new CSILException(-1009, a(localPAException.getErrorCode()));
    }
    return localProcessingWindows;
  }
  
  public static FulfillmentSystems getFulfillmentSystems(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    FulfillmentSystems localFulfillmentSystems = null;
    try
    {
      localFulfillmentSystems = jdField_do.getFulfillmentSystems(paramSecureUser, paramHashMap);
    }
    catch (PAException localPAException)
    {
      throw new CSILException(-1009, a(localPAException.getErrorCode()));
    }
    return localFulfillmentSystems;
  }
  
  public static ArrayList getGlobalPayeeGroups(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    ArrayList localArrayList = null;
    try
    {
      if ((jdField_do instanceof PaymentsAdminService3)) {
        localArrayList = ((PaymentsAdminService3)jdField_do).getGlobalPayeeGroups(paramSecureUser, paramHashMap);
      }
    }
    catch (PAException localPAException)
    {
      throw new CSILException(-1009, a(localPAException.getErrorCode()));
    }
    return localArrayList;
  }
  
  public static Payees searchGlobalPayees(SecureUser paramSecureUser, Payee paramPayee, HashMap paramHashMap)
    throws CSILException
  {
    String str = "searchGlobalPayees";
    Payees localPayees = null;
    try
    {
      if ((jdField_do instanceof PaymentsAdminService3)) {
        localPayees = ((PaymentsAdminService3)jdField_do).searchGlobalPayees(paramSecureUser, paramPayee, paramHashMap);
      }
    }
    catch (PAException localPAException)
    {
      throw new CSILException(-1009, a(localPAException.getErrorCode()));
    }
    return localPayees;
  }
  
  public static Payee getGlobalPayee(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "getGlobalPayee";
    Payee localPayee = null;
    try
    {
      if ((jdField_do instanceof PaymentsAdminService3)) {
        localPayee = ((PaymentsAdminService3)jdField_do).getGlobalPayee(paramSecureUser, paramInt, paramHashMap);
      }
    }
    catch (PAException localPAException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localPAException.getErrorCode()));
      DebugLog.throwing(str, localPAException);
      throw localCSILException;
    }
    return localPayee;
  }
  
  public static Payee modifyGlobalPayee(SecureUser paramSecureUser, Payee paramPayee, HashMap paramHashMap)
    throws CSILException
  {
    String str = "modifyGlobalPayee";
    Payee localPayee = null;
    try
    {
      if ((jdField_do instanceof PaymentsAdminService3)) {
        localPayee = ((PaymentsAdminService3)jdField_do).modifyGlobalPayee(paramSecureUser, paramPayee, paramHashMap);
      }
    }
    catch (PAException localPAException)
    {
      CSILException localCSILException = new CSILException(-1009, a(localPAException.getErrorCode()));
      DebugLog.throwing(str, localPAException);
      throw localCSILException;
    }
    return localPayee;
  }
  
  public static ScheduleCategory getScheduleCategory(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PaymentsAdminHandler.getScheduleCategory";
    ScheduleCategory localScheduleCategory = null;
    try
    {
      localScheduleCategory = jdField_do.getScheduleCategory(paramSecureUser, paramString1, paramString2, paramHashMap);
    }
    catch (PAException localPAException)
    {
      throw new CSILException(-1009, a(localPAException.getErrorCode()));
    }
    return localScheduleCategory;
  }
  
  public static ScheduleType addScheduleType(SecureUser paramSecureUser, ScheduleType paramScheduleType, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PaymentsAdminHandler.addScheduleType";
    try
    {
      paramScheduleType = jdField_do.addScheduleType(paramSecureUser, paramScheduleType, paramHashMap);
    }
    catch (PAException localPAException)
    {
      throw new CSILException(-1009, a(localPAException.getErrorCode()));
    }
    return paramScheduleType;
  }
  
  public static ScheduleType modifyScheduleType(SecureUser paramSecureUser, ScheduleType paramScheduleType, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PaymentsAdminHandler.modifyScheduleType";
    try
    {
      paramScheduleType = jdField_do.modifyScheduleType(paramSecureUser, paramScheduleType, paramHashMap);
    }
    catch (PAException localPAException)
    {
      throw new CSILException(-1009, a(localPAException.getErrorCode()));
    }
    return paramScheduleType;
  }
  
  public static void deleteScheduleType(SecureUser paramSecureUser, ScheduleType paramScheduleType, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PaymentsAdminHandler.deleteScheduleType";
    try
    {
      jdField_do.deleteScheduleType(paramSecureUser, paramScheduleType, paramHashMap);
    }
    catch (PAException localPAException)
    {
      throw new CSILException(-1009, a(localPAException.getErrorCode()));
    }
  }
  
  public static ScheduleType getScheduleType(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PaymentsAdminHandler.getScheduleType";
    ScheduleType localScheduleType = null;
    try
    {
      localScheduleType = jdField_do.getScheduleType(paramSecureUser, paramString1, paramString2, paramHashMap);
    }
    catch (PAException localPAException)
    {
      throw new CSILException(-1009, a(localPAException.getErrorCode()));
    }
    return localScheduleType;
  }
  
  public static CutOffTime addCutOffTime(SecureUser paramSecureUser, CutOffTime paramCutOffTime, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PaymentsAdminHandler.addCutOffTime";
    try
    {
      paramCutOffTime = jdField_do.addCutOffTime(paramSecureUser, paramCutOffTime, paramHashMap);
    }
    catch (PAException localPAException)
    {
      throw new CSILException(-1009, a(localPAException.getErrorCode()));
    }
    return paramCutOffTime;
  }
  
  public static CutOffTime modifyCutOffTime(SecureUser paramSecureUser, CutOffTime paramCutOffTime, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PaymentsAdminHandler.modifyCutOffTime";
    try
    {
      paramCutOffTime = jdField_do.modifyCutOffTime(paramSecureUser, paramCutOffTime, paramHashMap);
    }
    catch (PAException localPAException)
    {
      throw new CSILException(-1009, a(localPAException.getErrorCode()));
    }
    return paramCutOffTime;
  }
  
  public static void deleteCutOffTime(SecureUser paramSecureUser, CutOffTime paramCutOffTime, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PaymentsAdminHandler.deleteCutOffTime";
    try
    {
      jdField_do.deleteCutOffTime(paramSecureUser, paramCutOffTime, paramHashMap);
    }
    catch (PAException localPAException)
    {
      throw new CSILException(-1009, a(localPAException.getErrorCode()));
    }
  }
  
  public static CutOffTimes getCutOffTimes(SecureUser paramSecureUser, CutOffTime paramCutOffTime, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PaymentsAdminHandler.getCutOffTimes";
    CutOffTimes localCutOffTimes = null;
    try
    {
      localCutOffTimes = jdField_do.getCutOffTimes(paramSecureUser, paramCutOffTime, paramHashMap);
    }
    catch (PAException localPAException)
    {
      throw new CSILException(-1009, a(localPAException.getErrorCode()));
    }
    return localCutOffTimes;
  }
  
  public static void resubmitSchedule(String paramString1, String paramString2, String paramString3, String paramString4)
    throws CSILException
  {
    String str = "PaymentsAdminHandler.resubmitSchedule";
    try
    {
      jdField_do.resubmitSchedule(paramString1, paramString2, paramString3, paramString4);
    }
    catch (PAException localPAException)
    {
      throw new CSILException(-1009, a(localPAException.getErrorCode()));
    }
  }
  
  public static void runSchedule(String paramString1, String paramString2)
    throws CSILException
  {
    String str = "PaymentsAdminHandler.runSchedule";
    try
    {
      jdField_do.runSchedule(paramString1, paramString2);
    }
    catch (PAException localPAException)
    {
      throw new CSILException(-1009, a(localPAException.getErrorCode()));
    }
  }
  
  public static CutOffActivities getCutOffActivities(SecureUser paramSecureUser, String paramString, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PaymentsAdminHandler.getCutOffActivities";
    CutOffActivities localCutOffActivities = null;
    try
    {
      localCutOffActivities = jdField_do.getCutOffActivities(paramSecureUser, paramString, paramPagingContext, paramHashMap);
    }
    catch (PAException localPAException)
    {
      throw new CSILException(-1009, a(localPAException.getErrorCode()));
    }
    return localCutOffActivities;
  }
  
  public static void rerunCutOffProcess(SecureUser paramSecureUser, CutOffTime paramCutOffTime, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PaymentsAdminHandler.rerunCutOffProcess";
    try
    {
      jdField_do.rerunCutOffProcess(paramSecureUser, paramCutOffTime, paramString, paramHashMap);
    }
    catch (PAException localPAException)
    {
      throw new CSILException(-1009, a(localPAException.getErrorCode()));
    }
  }
  
  public static String getGeneratedFileName(SecureUser paramSecureUser, CutOffTime paramCutOffTime, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PaymentsAdminHandler.getGeneratedFileName";
    try
    {
      return jdField_do.getGeneratedFileName(paramSecureUser, paramCutOffTime, paramString, paramHashMap);
    }
    catch (PAException localPAException)
    {
      throw new CSILException(-1009, a(localPAException.getErrorCode()));
    }
  }
  
  public static void cleanup(HashMap paramHashMap)
    throws CSILException
  {
    String str = "PaymentsAdminHandler.cleanup";
    try
    {
      jdField_do.cleanup(paramHashMap);
    }
    catch (PAException localPAException)
    {
      throw new CSILException(-1009, a(localPAException.getErrorCode()));
    }
  }
  
  public static ScheduleActivities getScheduleActivities(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PaymentsAdminHandler.getScheduleActivities";
    ScheduleActivities localScheduleActivities = null;
    try
    {
      localScheduleActivities = jdField_do.getScheduleActivities(paramSecureUser, paramString1, paramString2, paramPagingContext, paramHashMap);
    }
    catch (PAException localPAException)
    {
      throw new CSILException(-1009, a(localPAException.getErrorCode()));
    }
    return localScheduleActivities;
  }
  
  private static int a(int paramInt)
  {
    int i = 1;
    switch (paramInt)
    {
    case 1: 
      i = 25018;
      break;
    case 2: 
      i = 37500;
      break;
    case 3: 
      i = 37501;
      break;
    case 4: 
      i = 37502;
      break;
    case 5: 
      i = 37503;
      break;
    case 6: 
      i = 37504;
      break;
    case 7: 
      i = 37505;
      break;
    case 8: 
      i = 37506;
      break;
    case 9: 
      i = 37507;
      break;
    case 10: 
      i = 37508;
      break;
    case 11: 
      i = 37509;
      break;
    case 12: 
      i = 37510;
      break;
    case 13: 
      i = 37511;
      break;
    case 14: 
      i = 37512;
      break;
    case 15: 
      i = 37513;
      break;
    case 16: 
      i = 37514;
      break;
    case 17: 
      i = 37515;
      break;
    case 18: 
      i = 37516;
      break;
    case 19: 
      i = 37517;
      break;
    case 20: 
      i = 37518;
      break;
    case 21: 
      i = 37519;
      break;
    case 22: 
      i = 37520;
      break;
    case 23: 
      i = 37521;
      break;
    case 24: 
      i = 37522;
      break;
    case 25: 
      i = 37523;
      break;
    case 26: 
      i = 37524;
      break;
    case 27: 
      i = 37525;
      break;
    case 28: 
      i = 37526;
      break;
    case 29: 
      i = 37527;
      break;
    case 30: 
      i = 37528;
      break;
    case 31: 
      i = 37529;
      break;
    case 32: 
      i = 37530;
      break;
    case 33: 
      i = 37531;
      break;
    case 34: 
      i = 37532;
      break;
    case 35: 
      i = 37533;
      break;
    case 36: 
      i = 37534;
      break;
    case 37: 
      i = 37535;
      break;
    case 38: 
      i = 37536;
      break;
    case 39: 
      i = 37537;
      break;
    case 40: 
      i = 37538;
      break;
    case 41: 
      i = 37539;
      break;
    case 42: 
      i = 37540;
      break;
    case 43: 
      i = 37541;
      break;
    case 44: 
      i = 37542;
      break;
    case 45: 
      i = 37543;
      break;
    case 46: 
      i = 37544;
      break;
    case 47: 
      i = 37545;
      break;
    case 48: 
      i = 37546;
      break;
    case 49: 
      i = 37547;
      break;
    case 50: 
      i = 37548;
      break;
    case 51: 
      i = 37549;
      break;
    case 52: 
      i = 37550;
      break;
    case 53: 
      i = 37551;
      break;
    case 54: 
      i = 2112;
      break;
    case 55: 
      i = 2113;
      break;
    case 56: 
      i = 2116;
      break;
    case 59: 
      i = 2117;
      break;
    case 57: 
      i = 2114;
      break;
    case 58: 
      i = 2115;
      break;
    case 60: 
      i = 2118;
      break;
    case 61: 
      i = 2119;
      break;
    case 62: 
      i = 2120;
    }
    return i;
  }
  
  public static BankingDays getBankingDaysInRange(BankingDays paramBankingDays, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PaymentsAdminHandler.getBankingDaysInRange";
    try
    {
      paramBankingDays = jdField_do.getBankingDaysInRange(paramBankingDays, paramHashMap);
    }
    catch (PAException localPAException)
    {
      throw new CSILException(-1009, a(localPAException.getErrorCode()));
    }
    return paramBankingDays;
  }
  
  public static String getGlobalPayeeDisplayCount()
  {
    return a;
  }
  
  public static void addGlobalPayee(SecureUser paramSecureUser, Payee paramPayee, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PaymentsAdminHandler.addGlobalPayee";
    try
    {
      if ((jdField_do instanceof PaymentsAdminService3)) {
        ((PaymentsAdminService3)jdField_do).addGlobalPayee(paramSecureUser, paramPayee, paramHashMap);
      }
    }
    catch (PAException localPAException)
    {
      DebugLog.throwing(str, localPAException);
      throw new CSILException(-1009, a(localPAException.getErrorCode()));
    }
  }
  
  public static void deleteGlobalPayee(SecureUser paramSecureUser, Payee paramPayee, HashMap paramHashMap)
    throws CSILException
  {
    String str = "PaymentsAdminHandler.deleteGlobalPayee";
    try
    {
      if ((jdField_do instanceof PaymentsAdminService3)) {
        ((PaymentsAdminService3)jdField_do).deleteGlobalPayee(paramSecureUser, paramPayee, paramHashMap);
      }
    }
    catch (PAException localPAException)
    {
      DebugLog.throwing(str, localPAException);
      throw new CSILException(-1009, a(localPAException.getErrorCode()));
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.PaymentsAdminHandler
 * JD-Core Version:    0.7.0.1
 */