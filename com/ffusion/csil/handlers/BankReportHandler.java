package com.ffusion.csil.handlers;

import com.ffusion.bankreport.BRException;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.bankreport.BankReport;
import com.ffusion.beans.bankreport.BankReportDefinitions;
import com.ffusion.beans.bankreport.BankReports;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.csil.CSILException;
import com.ffusion.reporting.IReportResult;
import com.ffusion.services.IBankReportService;
import com.ffusion.util.logging.DebugLog;
import java.io.InputStream;
import java.util.HashMap;

public class BankReportHandler
{
  private static final String a = "BankReports";
  private static IBankReportService jdField_if = null;
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    String str = "BankReportHandler.initialize";
    try
    {
      HashMap localHashMap = HandlerUtil.getServiceSettings(paramHashMap, "BankReports", str, 20107);
      jdField_if = (IBankReportService)HandlerUtil.instantiateService(localHashMap, str, 20107);
      jdField_if.initialize(paramHashMap);
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
  
  public static void processBankReportFile(InputStream paramInputStream, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BankReportHandler.processBankReportFile";
    try
    {
      jdField_if.processBankReportFile(paramInputStream, paramHashMap);
    }
    catch (BRException localBRException)
    {
      throw new CSILException(a(localBRException), localBRException.getErrorCode(), localBRException);
    }
  }
  
  public static BankReportDefinitions getReportDefinitions(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BankReportHandler.getReportDefinitions";
    BankReportDefinitions localBankReportDefinitions = null;
    try
    {
      localBankReportDefinitions = jdField_if.getReportDefinitions(paramSecureUser, paramHashMap);
    }
    catch (BRException localBRException)
    {
      throw new CSILException(a(localBRException), localBRException.getErrorCode(), localBRException);
    }
    return localBankReportDefinitions;
  }
  
  public static BankReports getReports(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BankReportHandler.getReports";
    BankReports localBankReports = null;
    try
    {
      localBankReports = jdField_if.getReports(paramSecureUser, paramString, paramHashMap);
    }
    catch (BRException localBRException)
    {
      throw new CSILException(a(localBRException), localBRException.getErrorCode(), localBRException);
    }
    return localBankReports;
  }
  
  public static Accounts getAccountsForReport(SecureUser paramSecureUser, BankReport paramBankReport, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BankReportHandler.getAccountsForReport";
    Accounts localAccounts = null;
    try
    {
      localAccounts = jdField_if.getAccountsForReport(paramSecureUser, paramBankReport, paramHashMap);
    }
    catch (BRException localBRException)
    {
      throw new CSILException(a(localBRException), localBRException.getErrorCode(), localBRException);
    }
    return localAccounts;
  }
  
  public static IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BankReportHandler.getReportData";
    IReportResult localIReportResult = null;
    try
    {
      localIReportResult = jdField_if.getReportData(paramSecureUser, paramReportCriteria, paramHashMap);
    }
    catch (BRException localBRException)
    {
      throw new CSILException(a(localBRException), localBRException.getErrorCode(), localBRException);
    }
    return localIReportResult;
  }
  
  public static void cleanupOldReports(int paramInt, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BankReportHandler.cleanupOldReports";
    try
    {
      jdField_if.cleanupOldReports(paramInt, paramString, paramHashMap);
    }
    catch (BRException localBRException)
    {
      throw new CSILException(a(localBRException), localBRException.getErrorCode(), localBRException);
    }
  }
  
  public static void removeReport(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BankReportHandler.removeReport";
    try
    {
      jdField_if.removeReport(paramSecureUser, paramInt, paramHashMap);
    }
    catch (BRException localBRException)
    {
      throw new CSILException(a(localBRException), localBRException.getErrorCode(), localBRException);
    }
  }
  
  public static void removeAccountData(int paramInt, Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BankReportHandler.removeAccountData";
    try
    {
      jdField_if.removeAccountData(paramInt, paramAccount, paramHashMap);
    }
    catch (BRException localBRException)
    {
      throw new CSILException(a(localBRException), localBRException.getErrorCode(), localBRException);
    }
  }
  
  public static void removeBusinessData(int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BankReportHandler.removeBusinessData";
    try
    {
      jdField_if.removeBusinessData(paramInt, paramHashMap);
    }
    catch (BRException localBRException)
    {
      throw new CSILException(a(localBRException), localBRException.getErrorCode(), localBRException);
    }
  }
  
  public static void removeUserData(int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BankReportHandler.removeUserData";
    try
    {
      jdField_if.removeUserData(paramInt, paramHashMap);
    }
    catch (BRException localBRException)
    {
      throw new CSILException(a(localBRException), localBRException.getErrorCode(), localBRException);
    }
  }
  
  private static int a(BRException paramBRException)
  {
    switch (paramBRException.getErrorCode())
    {
    case 60001: 
      return 60001;
    case 60002: 
      return 60002;
    case 60003: 
      return 60003;
    case 60010: 
      return 60010;
    case 60011: 
      return 60011;
    case 60012: 
      return 60012;
    case 60100: 
      return 60100;
    case 60101: 
      return 60101;
    case 60102: 
      return 60102;
    case 60103: 
      return 60103;
    case 60104: 
      return 60104;
    case 60105: 
      return 60105;
    case 60106: 
      return 60106;
    case 60107: 
      return 60107;
    case 60108: 
      return 60108;
    case 60109: 
      return 60109;
    case 60110: 
      return 60110;
    case 60111: 
      return 60111;
    case 60112: 
      return 60112;
    case 60113: 
      return 60113;
    case 60300: 
      return 60301;
    case 60301: 
      return 60301;
    case 60302: 
      return 60302;
    case 60400: 
      return 60400;
    }
    return 60000;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.BankReportHandler
 * JD-Core Version:    0.7.0.1
 */