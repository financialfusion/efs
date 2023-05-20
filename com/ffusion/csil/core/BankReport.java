package com.ffusion.csil.core;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.bankreport.BankReportDefinition;
import com.ffusion.beans.bankreport.BankReportDefinitions;
import com.ffusion.beans.bankreport.BankReports;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.MultiEntitlement;
import com.ffusion.csil.handlers.BankReportHandler;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.logging.PerfLog;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Level;

public class BankReport
  extends Initialize
{
  private static final String atA = "com.ffusion.util.logging.audit.bankreport";
  private static final String atB = "AuditEntFault_1";
  private static Entitlement atC = new Entitlement("Bank Reporting", null, null);
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    String str = "BankReport.initialize";
    debug(str);
    try
    {
      DebugLog.log(Level.INFO, "Initializing BankReport module");
      BankReportHandler.initialize(paramHashMap);
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
    return BankReportHandler.getService();
  }
  
  public static void processBankReportFile(InputStream paramInputStream, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BankReport.processBankReportFile";
    debug(str);
    try
    {
      long l = System.currentTimeMillis();
      BankReportHandler.processBankReportFile(paramInputStream, paramHashMap);
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
  }
  
  public static BankReportDefinitions getReportDefinitions(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BankReport.getReportDefinitions";
    debug(str);
    BankReportDefinitions localBankReportDefinitions = null;
    try
    {
      jdMethod_else(paramSecureUser);
      long l = System.currentTimeMillis();
      localBankReportDefinitions = BankReportHandler.getReportDefinitions(paramSecureUser, paramHashMap);
      Iterator localIterator = localBankReportDefinitions.iterator();
      while (localIterator.hasNext())
      {
        BankReportDefinition localBankReportDefinition = (BankReportDefinition)localIterator.next();
        if (!EntitlementsUtil.checkReportTypeEntitlement(paramSecureUser, localBankReportDefinition.getReportType(), false)) {
          localIterator.remove();
        }
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
    return localBankReportDefinitions;
  }
  
  public static BankReports getReports(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BankReport.getReports";
    debug(str);
    BankReports localBankReports = null;
    try
    {
      jdMethod_else(paramSecureUser);
      EntitlementsUtil.checkReportTypeEntitlement(paramSecureUser, paramString, true);
      long l = System.currentTimeMillis();
      localBankReports = BankReportHandler.getReports(paramSecureUser, paramString, paramHashMap);
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
    return localBankReports;
  }
  
  public static Accounts getAccountsForReport(SecureUser paramSecureUser, com.ffusion.beans.bankreport.BankReport paramBankReport, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BankReport.getAccountsForReport";
    debug(str);
    Accounts localAccounts = null;
    try
    {
      long l = System.currentTimeMillis();
      localAccounts = BankReportHandler.getAccountsForReport(paramSecureUser, paramBankReport, paramHashMap);
      localAccounts = jdMethod_int(paramSecureUser, paramBankReport.getType(), localAccounts);
      localAccounts = jdMethod_for(paramSecureUser, paramBankReport.getType(), localAccounts);
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
    return localAccounts;
  }
  
  public static IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "BankReport.getReportData";
    debug(str1);
    IReportResult localIReportResult = null;
    try
    {
      String str2 = paramReportCriteria.getReportOptions().getProperty("REPORTTYPE");
      if (str2 == null) {
        throw new CSILException(60100);
      }
      String str3 = paramReportCriteria.getSearchCriteria().getProperty("Report_HIDE");
      if (str3 == null) {
        throw new CSILException(60112);
      }
      Reporting.prepareForReport(paramSecureUser, paramReportCriteria, paramHashMap);
      jdMethod_else(paramSecureUser);
      EntitlementsUtil.checkReportTypeEntitlement(paramSecureUser, str2, true);
      Accounts localAccounts = (Accounts)paramHashMap.get("ACCOUNTS");
      if ((localAccounts == null) || (localAccounts.size() == 0))
      {
        com.ffusion.beans.bankreport.BankReport localBankReport = new com.ffusion.beans.bankreport.BankReport();
        int i = Integer.parseInt(str3);
        localBankReport.setReportID(i);
        localBankReport.setType(str2);
        paramHashMap.put("ACCOUNTS", getAccountsForReport(paramSecureUser, localBankReport, paramHashMap));
      }
      long l = System.currentTimeMillis();
      localIReportResult = BankReportHandler.getReportData(paramSecureUser, paramReportCriteria, paramHashMap);
      PerfLog.log(str1, l, true);
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
    return localIReportResult;
  }
  
  public static void cleanupOldReports(int paramInt, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BankReport.cleanupOldReports";
    debug(str);
    try
    {
      long l = System.currentTimeMillis();
      BankReportHandler.cleanupOldReports(paramInt, paramString, paramHashMap);
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
  }
  
  public static void removeReport(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BankReport.removeReport";
    debug(str);
    try
    {
      jdMethod_else(paramSecureUser);
      long l = System.currentTimeMillis();
      BankReportHandler.removeReport(paramSecureUser, paramInt, paramHashMap);
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
  }
  
  public static void removeAccountData(int paramInt, Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BankReport.removeAccountData";
    debug(str);
    try
    {
      long l = System.currentTimeMillis();
      BankReportHandler.removeAccountData(paramInt, paramAccount, paramHashMap);
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
  }
  
  public static void removeBusinessData(int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BankReport.removeBusinessData";
    debug(str);
    try
    {
      long l = System.currentTimeMillis();
      BankReportHandler.removeBusinessData(paramInt, paramHashMap);
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
  }
  
  public static void removeUserData(int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "BankReport.removeUserData";
    debug(str);
    try
    {
      long l = System.currentTimeMillis();
      BankReportHandler.removeUserData(paramInt, paramHashMap);
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
  }
  
  private static void jdMethod_else(SecureUser paramSecureUser)
    throws CSILException
  {
    if (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), atC))
    {
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.bankreport", "AuditEntFault_1", null);
      Initialize.logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException(60400);
    }
  }
  
  private static Accounts jdMethod_int(SecureUser paramSecureUser, String paramString, Accounts paramAccounts)
    throws CSILException
  {
    if ((paramAccounts == null) || (paramAccounts.size() == 0)) {
      return null;
    }
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    MultiEntitlement localMultiEntitlement = new MultiEntitlement();
    localMultiEntitlement.setOperations(new String[] { "Bank Reporting" });
    String[] arrayOfString1 = { "Account" };
    String[] arrayOfString2 = new String[1];
    Iterator localIterator = paramAccounts.iterator();
    while (localIterator.hasNext())
    {
      Account localAccount = (Account)localIterator.next();
      arrayOfString2[0] = EntitlementsUtil.getEntitlementObjectId(localAccount);
      localMultiEntitlement.setObjects(arrayOfString1, arrayOfString2);
      if (EntitlementsUtil.checkAccountEntitlement(localEntitlementGroupMember, localMultiEntitlement, paramSecureUser.getBusinessID()) != null) {
        localIterator.remove();
      }
    }
    return paramAccounts;
  }
  
  private static Accounts jdMethod_for(SecureUser paramSecureUser, String paramString, Accounts paramAccounts)
    throws CSILException
  {
    if ((paramAccounts == null) || (paramAccounts.size() == 0)) {
      return null;
    }
    ReportCriteria localReportCriteria = Reporting.getDefaultReportCriteria(paramString);
    ArrayList localArrayList = localReportCriteria.getEntitlementTypes();
    if (localArrayList != null)
    {
      EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
      MultiEntitlement localMultiEntitlement = new MultiEntitlement();
      String[] arrayOfString1 = new String[1];
      String[] arrayOfString2 = new String[1];
      String[] arrayOfString3 = { "Account" };
      Iterator localIterator1 = paramAccounts.iterator();
      while (localIterator1.hasNext())
      {
        Account localAccount = (Account)localIterator1.next();
        arrayOfString2[0] = EntitlementsUtil.getEntitlementObjectId(localAccount);
        localMultiEntitlement.setObjects(arrayOfString3, arrayOfString2);
        Iterator localIterator2 = localArrayList.iterator();
        while (localIterator2.hasNext())
        {
          arrayOfString1[0] = ((String)localIterator2.next());
          localMultiEntitlement.setOperations(arrayOfString1);
          if (EntitlementsUtil.checkAccountEntitlement(localEntitlementGroupMember, localMultiEntitlement, paramSecureUser.getBusinessID()) != null) {
            localIterator1.remove();
          }
        }
      }
    }
    return paramAccounts;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.BankReport
 * JD-Core Version:    0.7.0.1
 */