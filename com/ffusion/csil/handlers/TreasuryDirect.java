package com.ffusion.csil.handlers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.csil.CSILException;
import com.ffusion.reporting.IReportResult;
import com.ffusion.treasurydirect.TDException;
import com.ffusion.util.logging.DebugLog;
import java.util.HashMap;

public class TreasuryDirect
  extends Initialize
{
  private static final String a7b = "TreasuryDirect";
  private static com.ffusion.services.TreasuryDirect a7c = null;
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.TreasuryDirect.initialize";
    debug(str);
    HashMap localHashMap = HandlerUtil.getServiceSettings(paramHashMap, "TreasuryDirect", str, 80000);
    a7c = (com.ffusion.services.TreasuryDirect)HandlerUtil.instantiateService(localHashMap, str, 80000);
    try
    {
      a7c.initialize(paramHashMap);
    }
    catch (Exception localException)
    {
      CSILException localCSILException = new CSILException(jdMethod_new(localException), localException);
      throw localCSILException;
    }
  }
  
  public static void addSubAccounts(SecureUser paramSecureUser, Business paramBusiness, Account paramAccount, Accounts paramAccounts, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.TreasuryDirect.addSubAccounts";
    try
    {
      a7c.addSubAccounts(paramSecureUser, paramBusiness, paramAccount, paramAccounts, paramHashMap);
    }
    catch (TDException localTDException)
    {
      CSILException localCSILException = new CSILException(jdMethod_new(localTDException), localTDException.getErrorCode(), localTDException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void deleteSubAccounts(SecureUser paramSecureUser, Business paramBusiness, Account paramAccount, Accounts paramAccounts, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.TreasuryDirect.deleteSubAccounts";
    try
    {
      a7c.deleteSubAccounts(paramSecureUser, paramBusiness, paramAccount, paramAccounts, paramHashMap);
    }
    catch (TDException localTDException)
    {
      CSILException localCSILException = new CSILException(jdMethod_new(localTDException), localTDException.getErrorCode(), localTDException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void deleteMasterAccount(SecureUser paramSecureUser, Business paramBusiness, Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.TreasuryDirect.deleteMasterAccount";
    try
    {
      a7c.deleteMasterAccount(paramSecureUser, paramBusiness, paramAccount, paramHashMap);
    }
    catch (TDException localTDException)
    {
      CSILException localCSILException = new CSILException(jdMethod_new(localTDException), localTDException.getErrorCode(), localTDException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Accounts getSubAccounts(SecureUser paramSecureUser, Business paramBusiness, Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.TreasuryDirect.getSubAccounts";
    Accounts localAccounts = null;
    try
    {
      localAccounts = a7c.getSubAccounts(paramSecureUser, paramBusiness, paramAccount, paramHashMap);
    }
    catch (TDException localTDException)
    {
      CSILException localCSILException = new CSILException(jdMethod_new(localTDException), localTDException.getErrorCode(), localTDException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return localAccounts;
  }
  
  public static void modifySubAccounts(SecureUser paramSecureUser, Business paramBusiness, Account paramAccount, Accounts paramAccounts, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.TreasuryDirect.modifySubAccounts";
    try
    {
      a7c.modifySubAccounts(paramSecureUser, paramBusiness, paramAccount, paramAccounts, paramHashMap);
    }
    catch (TDException localTDException)
    {
      CSILException localCSILException = new CSILException(jdMethod_new(localTDException), localTDException.getErrorCode(), localTDException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Accounts filterAvailableSubAccounts(SecureUser paramSecureUser, Business paramBusiness, Account paramAccount, Accounts paramAccounts, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.TreasuryDirect.filterAvailableSubAccounts";
    Accounts localAccounts = null;
    try
    {
      localAccounts = a7c.filterAvailableSubAccounts(paramSecureUser, paramBusiness, paramAccount, paramAccounts, paramHashMap);
    }
    catch (TDException localTDException)
    {
      CSILException localCSILException = new CSILException(jdMethod_new(localTDException), localTDException.getErrorCode(), localTDException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return localAccounts;
  }
  
  public static int getNumMasterAccounts(SecureUser paramSecureUser, Business paramBusiness, Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.TreasuryDirect.getNumMasterAccounts";
    try
    {
      return a7c.getNumMasterAccounts(paramSecureUser, paramBusiness, paramAccount, paramHashMap);
    }
    catch (TDException localTDException)
    {
      CSILException localCSILException = new CSILException(jdMethod_new(localTDException), localTDException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static int getNumSubAccounts(SecureUser paramSecureUser, Business paramBusiness, Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.TreasuryDirect.getNumSubAccounts";
    try
    {
      return a7c.getNumSubAccounts(paramSecureUser, paramBusiness, paramAccount, paramHashMap);
    }
    catch (TDException localTDException)
    {
      CSILException localCSILException = new CSILException(jdMethod_new(localTDException), localTDException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Accounts filterNonSubAccounts(SecureUser paramSecureUser, Business paramBusiness, Accounts paramAccounts, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.TreasuryDirect.filterNonSubAccounts";
    try
    {
      return a7c.filterNonSubAccounts(paramSecureUser, paramBusiness, paramAccounts, paramHashMap);
    }
    catch (TDException localTDException)
    {
      CSILException localCSILException = new CSILException(jdMethod_new(localTDException), localTDException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static HashMap getMasterSubStatistics(SecureUser paramSecureUser, Business paramBusiness, Accounts paramAccounts, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.TreasuryDirect.getMasterSubStatistics";
    try
    {
      return a7c.getMasterSubStatistics(paramSecureUser, paramBusiness, paramAccounts, paramHashMap);
    }
    catch (TDException localTDException)
    {
      CSILException localCSILException = new CSILException(jdMethod_new(localTDException), localTDException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws CSILException
  {
    String str = "TreasuryDirect.getReportData";
    IReportResult localIReportResult = null;
    try
    {
      localIReportResult = a7c.getReportData(paramSecureUser, paramReportCriteria, paramHashMap);
    }
    catch (TDException localTDException)
    {
      CSILException localCSILException = new CSILException(jdMethod_new(localTDException), localTDException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return localIReportResult;
  }
  
  private static int jdMethod_new(Exception paramException)
  {
    int i = 80001;
    if ((paramException instanceof TDException))
    {
      TDException localTDException = (TDException)paramException;
      switch (localTDException.getErrorCode())
      {
      case 80001: 
        i = 80000;
        break;
      case 80002: 
        i = 80000;
        break;
      case 80003: 
        i = 80002;
        break;
      case 80004: 
        i = 80003;
        break;
      case 80006: 
      case 80007: 
        i = 80004;
        break;
      case 80005: 
        i = 80005;
        break;
      case 80013: 
      case 80014: 
      case 80015: 
      case 80016: 
        i = -1009;
        break;
      case 80008: 
        i = 300;
        break;
      case 80009: 
        i = 80006;
        break;
      case 80010: 
        i = 80007;
        break;
      case 80011: 
        i = 80008;
        break;
      case 80012: 
        i = 80009;
        break;
      default: 
        i = 80001;
      }
    }
    return i;
  }
  
  public static Accounts getMasterAccounts(SecureUser paramSecureUser, Business paramBusiness, Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.TreasuryDirect.getMasterAccounts";
    try
    {
      return a7c.getMasterAccounts(paramSecureUser, paramBusiness, paramAccount, paramHashMap);
    }
    catch (TDException localTDException)
    {
      CSILException localCSILException = new CSILException(jdMethod_new(localTDException), localTDException.getErrorCode(), localTDException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Account getSubAccount(SecureUser paramSecureUser, Business paramBusiness, Account paramAccount1, Account paramAccount2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.TreasuryDirect.getSubAccounts";
    try
    {
      return a7c.getSubAccount(paramSecureUser, paramBusiness, paramAccount1, paramAccount2, paramHashMap);
    }
    catch (TDException localTDException)
    {
      CSILException localCSILException = new CSILException(jdMethod_new(localTDException), localTDException.getErrorCode(), localTDException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.TreasuryDirect
 * JD-Core Version:    0.7.0.1
 */