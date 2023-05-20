package com.ffusion.services.dataconsolidator;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.AccountHistories;
import com.ffusion.beans.accounts.AccountKey;
import com.ffusion.beans.accounts.AccountSummaries;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.business.Businesses;
import com.ffusion.beans.dataconsolidator.ImportedFile;
import com.ffusion.beans.dataconsolidator.ImportedFiles;
import com.ffusion.beans.dataconsolidator.MasterSubRelation;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.systemadmin.DRKey;
import com.ffusion.beans.systemadmin.DRSetting;
import com.ffusion.beans.user.User;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMembers;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import com.ffusion.csil.core.Banking;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.Reporting;
import com.ffusion.csil.core.SmartCalendar;
import com.ffusion.dataconsolidator.adapter.DCAdapter;
import com.ffusion.dataconsolidator.adapter.DCException;
import com.ffusion.dataconsolidator.adapter.DCUtil;
import com.ffusion.efs.adapters.profile.AccountAdapter;
import com.ffusion.efs.adapters.profile.BusinessAdapter;
import com.ffusion.efs.adapters.profile.CustomerAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.reporting.IReportResult;
import com.ffusion.reporting.RptException;
import com.ffusion.services.DataConsolidator3;
import com.ffusion.systemadmin.SystemAdminUtil;
import com.ffusion.systemadmin.adapter.SystemAdminAdapter;
import com.ffusion.util.FFIStringTokenizer;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.MapUtil;
import com.ffusion.util.TransactionTypeCache;
import com.ffusion.util.XMLUtil;
import com.ffusion.util.beans.BankIdentifier;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.beans.filemonitor.FMException;
import com.ffusion.util.beans.filemonitor.FMLogRecord;
import com.ffusion.util.db.DBUtil;
import com.ffusion.util.filemonitor.FMLog;
import com.ffusion.util.logging.DebugLog;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;

public class DataConsolidatorService
  extends BaseDataConsolidatorService
  implements DataConsolidator3
{
  public static final String TRANSACTION_TYPES_FILE = "transactionTypes.xml";
  private static final String jdField_long = "SELECT a.AccountID, RoutingNumber, BankID, DataDate FROM DC_Account a, ";
  private static final String jdField_else = " b WHERE b.DCAccountID=a.DCAccountID AND DataSourceFileName=? AND DataSourceFileDate=? AND DataSource=? GROUP BY a.DCAccountID, a.AccountID, RoutingNumber, BankID, DataDate ORDER BY a.DCAccountID";
  private static final String jdField_case = "DELETE FROM DC_Transactions WHERE DC_Transactions.DCAccountID=(SELECT a.DCAccountID FROM DC_Account a WHERE a.AccountID=? ";
  private static final String jdField_byte = " ) AND DC_Transactions.DataDate =?";
  private static final String jdField_try = " AND a.RoutingNumber is null ";
  private static final String c = " AND a.RoutingNumber = ? ";
  private static final String j = " AND DC_Transactions.BankRefNum = ? ";
  private static final String f = " AND DC_Transactions.CustRefNum = ? ";
  private static final String jdField_new = "com.ffusion.util.logging.audit.dataconsolidator";
  private static final String i = "ServiceMessage_0";
  private static final String h = "ServiceMessage_1";
  private static final String g = "ServiceMessage_2";
  private static final String e = "ServiceMessage_3";
  private static final String d = "ServiceMessage_4";
  private static final String b = "ServiceMessage_5";
  private static final String jdField_void = "ServiceMessage_6";
  private static final String jdField_null = "ServiceMessage_7";
  private static final String jdField_goto = "ServiceMessage_8";
  private static final String jdField_char = "ServiceMessage_9";
  
  public void initialize()
    throws DCException
  {
    Connection localConnection = null;
    HashMap localHashMap1 = null;
    HashMap localHashMap2 = null;
    try
    {
      DebugLog.log(Level.INFO, "Data Consolidator Service initializing...");
      localHashMap1 = XMLUtil.readXmlToHashMap(this, "dataconsolidator.xml");
      localHashMap2 = (HashMap)localHashMap1.get("DCADAPTER");
      DCAdapter.initialize((HashMap)localHashMap2.get("DCADAPTER_SETTINGS"));
      TransactionTypeCache.initTransactionTypesCache("transactionTypes.xml");
      localConnection = DCAdapter.getDBConnection();
      DCAdapter.clearTransactionTypes(localConnection);
      HashMap localHashMap3 = Banking.getTransactionTypes(0, new HashMap(0));
      DCAdapter.loadTransactionTypes(localConnection, localHashMap3);
      localConnection.commit();
      super.initialize();
      try
      {
        DCAdapter.releaseDBConnection(localConnection);
      }
      catch (Exception localException1) {}
      try
      {
        PrintWriter localPrintWriter;
        localConnection = DCAdapter.getDBConnection();
        HashMap localHashMap4 = new HashMap();
        Object localObject1 = (HashMap)localHashMap2.get("DCADAPTER_SETTINGS");
        int k = 1;
        if (localObject1 != null)
        {
          String str = (String)((HashMap)localObject1).get("MaxSubTypeDescriptionAge");
          try
          {
            if (str != null) {
              k = Integer.parseInt(str);
            }
          }
          catch (NumberFormatException localNumberFormatException) {}
        }
        if ((DCAdapter.CONNECTIONTYPE != null) && (DCAdapter.CONNECTIONTYPE.startsWith("DB2"))) {
          try
          {
            localConnection.setTransactionIsolation(8);
          }
          catch (Exception localException5) {}
        }
        DCAdapter.loadBAISubTypeDescriptions(localConnection, k, int, Locale.US, localHashMap4);
      }
      finally
      {
        try
        {
          if ((DCAdapter.CONNECTIONTYPE != null) && (DCAdapter.CONNECTIONTYPE.startsWith("DB2"))) {
            localConnection.setTransactionIsolation(2);
          }
          DCAdapter.releaseDBConnection(localConnection);
        }
        catch (Exception localException7) {}
      }
    }
    catch (Exception localException2)
    {
      try
      {
        if (localConnection != null) {
          localConnection.rollback();
        }
      }
      catch (Exception localException3) {}
      localObject1 = new StringWriter();
      localPrintWriter = new PrintWriter((Writer)localObject1);
      localException2.printStackTrace(localPrintWriter);
      DebugLog.log(Level.SEVERE, ((StringWriter)localObject1).toString());
      return;
    }
    finally
    {
      try
      {
        DCAdapter.releaseDBConnection(localConnection);
      }
      catch (Exception localException6) {}
    }
    DebugLog.log(Level.INFO, "Data Consolidator Service initialized");
  }
  
  public void cleanup(String paramString, int paramInt, HashMap paramHashMap)
    throws DCException
  {
    if (paramInt >= 0)
    {
      DCAdapter.cleanup(paramString, paramInt, paramHashMap);
    }
    else
    {
      HashMap localHashMap1 = null;
      HashMap localHashMap2 = null;
      HashMap localHashMap3 = null;
      HashMap localHashMap4 = null;
      HashMap localHashMap5 = null;
      EntitlementGroups localEntitlementGroups1 = null;
      DRKey localDRKey = null;
      DRSetting localDRSetting = null;
      String str = null;
      str = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
      try
      {
        localHashMap1 = SystemAdminAdapter.getDataRetentionSettings(0, 0, paramHashMap);
        localEntitlementGroups1 = Entitlements.getEntitlementGroupsByType("MarketSegment");
        Iterator localIterator1 = localEntitlementGroups1.iterator();
        while (localIterator1.hasNext())
        {
          EntitlementGroup localEntitlementGroup1 = (EntitlementGroup)localIterator1.next();
          EntitlementGroups localEntitlementGroups2 = Entitlements.getChildrenByGroupType(localEntitlementGroup1.getGroupId(), "ServicesPackage");
          localHashMap3 = SystemAdminAdapter.getDataRetentionSettings(2, localEntitlementGroup1.getGroupId(), paramHashMap);
          Iterator localIterator2 = localEntitlementGroups2.iterator();
          while (localIterator2.hasNext())
          {
            EntitlementGroup localEntitlementGroup2 = (EntitlementGroup)localIterator2.next();
            EntitlementGroups localEntitlementGroups3 = Entitlements.getChildrenByGroupType(localEntitlementGroup2.getGroupId(), "BusinessAdmin");
            EntitlementGroups localEntitlementGroups4 = Entitlements.getChildrenByGroupType(localEntitlementGroup2.getGroupId(), "ConsumerAdmin");
            localHashMap4 = SystemAdminAdapter.getDataRetentionSettings(3, localEntitlementGroup2.getGroupId(), paramHashMap);
            Iterator localIterator3 = localEntitlementGroups3.iterator();
            EntitlementGroup localEntitlementGroup3;
            Object localObject1;
            Iterator localIterator4;
            Object localObject2;
            Object localObject3;
            Object localObject4;
            Object localObject5;
            Object localObject6;
            while (localIterator3.hasNext())
            {
              localEntitlementGroup3 = (EntitlementGroup)localIterator3.next();
              localObject1 = Entitlements.getMembers(localEntitlementGroup3.getGroupId());
              localIterator4 = ((EntitlementGroupMembers)localObject1).iterator();
              while (localIterator4.hasNext())
              {
                localObject2 = (EntitlementGroupMember)localIterator4.next();
                int k = -1;
                localObject3 = null;
                localObject4 = null;
                HashMap localHashMap6 = null;
                localObject5 = null;
                try
                {
                  k = Integer.parseInt(((EntitlementGroupMember)localObject2).getId());
                }
                catch (Exception localException2)
                {
                  k = -1;
                }
                localObject3 = BusinessAdapter.getBusiness(k);
                if (localObject3 != null)
                {
                  localHashMap5 = SystemAdminAdapter.getDataRetentionSettings(4, ((Business)localObject3).getIdValue(), paramHashMap);
                  localHashMap2 = SystemAdminAdapter.getDataRetentionSettings(1, ((Business)localObject3).getAffiliateBankID(), paramHashMap);
                  localObject4 = new ArrayList(5);
                  ((ArrayList)localObject4).add(0, localHashMap5);
                  ((ArrayList)localObject4).add(0, localHashMap4);
                  ((ArrayList)localObject4).add(0, localHashMap3);
                  ((ArrayList)localObject4).add(0, localHashMap2);
                  ((ArrayList)localObject4).add(0, localHashMap1);
                  localHashMap6 = SystemAdminUtil.getOverriddenDataRetentionSettings((ArrayList)localObject4, paramHashMap);
                  if (str.equals("A"))
                  {
                    localObject6 = new String[] { "I", "P" };
                    for (int n = 0; n < localObject6.length; n++)
                    {
                      localDRKey = new DRKey();
                      localDRKey.setDataType(paramString);
                      localDRKey.setDataClass(localObject6[n]);
                      localDRSetting = null;
                      localDRSetting = (DRSetting)localHashMap6.get(localDRKey);
                      if (localDRSetting != null)
                      {
                        localObject5 = AccountAdapter.getAccountsById(((Business)localObject3).getIdValue());
                        DCAdapter.cleanup((Accounts)localObject5, paramString, localDRKey.getDataClass(), localDRSetting.getRetentionDays(), paramHashMap);
                      }
                    }
                  }
                  else
                  {
                    localDRKey = new DRKey();
                    localDRKey.setDataType(paramString);
                    localDRKey.setDataClass(str);
                    localDRSetting = (DRSetting)localHashMap6.get(localDRKey);
                    if (localDRSetting != null)
                    {
                      localObject5 = AccountAdapter.getAccountsById(((Business)localObject3).getIdValue());
                      DCAdapter.cleanup((Accounts)localObject5, paramString, localDRKey.getDataClass(), localDRSetting.getRetentionDays(), paramHashMap);
                    }
                  }
                }
              }
            }
            localIterator3 = localEntitlementGroups4.iterator();
            while (localIterator3.hasNext())
            {
              localEntitlementGroup3 = (EntitlementGroup)localIterator3.next();
              localObject1 = Entitlements.getChildrenByGroupType(localEntitlementGroup3.getGroupId(), "USER");
              localIterator4 = ((EntitlementGroups)localObject1).iterator();
              while (localIterator4.hasNext())
              {
                localObject2 = (EntitlementGroup)localIterator4.next();
                EntitlementGroupMembers localEntitlementGroupMembers = Entitlements.getMembers(((EntitlementGroup)localObject2).getGroupId());
                localObject3 = localEntitlementGroupMembers.iterator();
                while (((Iterator)localObject3).hasNext())
                {
                  localObject4 = (EntitlementGroupMember)((Iterator)localObject3).next();
                  int m = -1;
                  localObject5 = null;
                  localObject6 = null;
                  HashMap localHashMap7 = null;
                  Accounts localAccounts = null;
                  try
                  {
                    m = Integer.parseInt(((EntitlementGroupMember)localObject4).getId());
                  }
                  catch (Exception localException3)
                  {
                    m = -1;
                  }
                  localObject5 = CustomerAdapter.getUserById(m);
                  if (localObject5 != null)
                  {
                    localHashMap2 = SystemAdminAdapter.getDataRetentionSettings(1, ((User)localObject5).getAffiliateBankID(), paramHashMap);
                    localObject6 = new ArrayList(5);
                    ((ArrayList)localObject6).add(0, localHashMap4);
                    ((ArrayList)localObject6).add(0, localHashMap3);
                    ((ArrayList)localObject6).add(0, localHashMap2);
                    ((ArrayList)localObject6).add(0, localHashMap1);
                    localHashMap7 = SystemAdminUtil.getOverriddenDataRetentionSettings((ArrayList)localObject6, paramHashMap);
                    if (str.equals("A"))
                    {
                      String[] arrayOfString = { "I", "P" };
                      for (int i1 = 0; i1 < arrayOfString.length; i1++)
                      {
                        localDRKey = new DRKey();
                        localDRKey.setDataType(paramString);
                        localDRKey.setDataClass(arrayOfString[i1]);
                        localDRSetting = null;
                        localDRSetting = (DRSetting)localHashMap7.get(localDRKey);
                        if (localDRSetting != null)
                        {
                          localAccounts = AccountAdapter.getAccountsById(((User)localObject5).getIdValue());
                          DCAdapter.cleanup(localAccounts, paramString, localDRKey.getDataClass(), localDRSetting.getRetentionDays(), paramHashMap);
                        }
                      }
                    }
                    else
                    {
                      localDRKey = new DRKey();
                      localDRKey.setDataType(paramString);
                      localDRKey.setDataClass(str);
                      localDRSetting = (DRSetting)localHashMap7.get(localDRKey);
                      if (localDRSetting != null)
                      {
                        localAccounts = AccountAdapter.getAccountsById(((User)localObject5).getIdValue());
                        DCAdapter.cleanup(localAccounts, paramString, localDRKey.getDataClass(), localDRSetting.getRetentionDays(), paramHashMap);
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
      catch (Exception localException1)
      {
        if ((localException1 instanceof DCException)) {
          throw ((DCException)localException1);
        }
        throw new DCException(localException1);
      }
    }
  }
  
  public AccountHistories getHistory(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException
  {
    try
    {
      return DCUtil.combineAccountHistoriesByDate(DCAdapter.getHistory(paramAccount, paramCalendar1, paramCalendar2, paramHashMap));
    }
    catch (Exception localException)
    {
      if ((localException instanceof DCException)) {
        throw ((DCException)localException);
      }
      throw new DCException(localException);
    }
  }
  
  public AccountSummaries getSummary(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException
  {
    try
    {
      return DCUtil.combineAccountSummariesByDate(DCAdapter.getSummary(paramAccount, paramCalendar1, paramCalendar2, paramHashMap));
    }
    catch (Exception localException)
    {
      if ((localException instanceof DCException)) {
        throw ((DCException)localException);
      }
      throw new DCException(localException);
    }
  }
  
  public IReportResult getAccountReportData(ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws DCException
  {
    try
    {
      Properties localProperties = paramReportCriteria.getSearchCriteria();
      if (localProperties == null) {
        throw new DCException(403, "Report search criteria does not exist.");
      }
      String str1 = localProperties.getProperty("Accounts");
      if (str1 == null) {
        str1 = "AllAccounts";
      }
      Object localObject3;
      if ((str1.equals("AllAccounts")) || (str1.equals("AllAccounts")))
      {
        localObject1 = (Accounts)paramHashMap.get("Accounts");
        HashMap localHashMap1 = new HashMap();
        localObject2 = new HashMap();
        try
        {
          Reporting.calculateDateRange(new SecureUser(), (Accounts)localObject1, paramReportCriteria, localHashMap1, (HashMap)localObject2, paramHashMap);
          paramHashMap.put("StartDates", localHashMap1);
          paramHashMap.put("EndDates", localObject2);
        }
        catch (CSILException localCSILException2)
        {
          localObject3 = "Unable to calcualte the date ranges. A report cannot be run.";
          DebugLog.log((String)localObject3);
          if (localCSILException2.getCode() == -1009) {
            throw new DCException(localCSILException2.getServiceError(), "Unable to generate report.");
          }
          throw new DCException(localCSILException2.getCode(), "Unable to generate report.");
        }
      }
      else
      {
        localObject1 = new StringTokenizer(str1, ",");
        int k = ((StringTokenizer)localObject1).countTokens();
        if (k < 1) {
          throw new DCException(405, "Value for SearchCriteria key Accounts has incorrect format.");
        }
        localObject2 = new Accounts();
        for (int m = 0; m < k; m++)
        {
          localObject3 = ((StringTokenizer)localObject1).nextToken();
          FFIStringTokenizer localFFIStringTokenizer = new FFIStringTokenizer((String)localObject3, ":");
          int n = localFFIStringTokenizer.countTokens();
          if (n < 2) {
            throw new DCException(405, "Value for SearchCriteria key Accounts has incorrect format.");
          }
          Account localAccount = new Account();
          localAccount.setID(localFFIStringTokenizer.nextToken());
          localAccount.setBankID(localFFIStringTokenizer.nextToken());
          if (n >= 3) {
            localAccount.setRoutingNum(localFFIStringTokenizer.nextToken());
          }
          if (n >= 4) {
            localAccount.setNickName(localFFIStringTokenizer.nextToken());
          }
          if (n >= 5) {
            localAccount.setCurrencyCode(localFFIStringTokenizer.nextToken());
          }
          ((Accounts)localObject2).add(localAccount);
        }
        try
        {
          HashMap localHashMap2 = new HashMap();
          localObject3 = new HashMap();
          Reporting.calculateDateRange(new SecureUser(), (Accounts)localObject2, paramReportCriteria, localHashMap2, (HashMap)localObject3, paramHashMap);
          paramHashMap.put("StartDates", localHashMap2);
          paramHashMap.put("EndDates", localObject3);
        }
        catch (CSILException localCSILException3)
        {
          localObject3 = "Unable to calcualte the date ranges. A report cannot be run.";
          DebugLog.log((String)localObject3);
          if (localCSILException3.getCode() == -1009) {
            throw new DCException(localCSILException3.getServiceError(), "Unable to generate report.");
          }
          throw new DCException(localCSILException3.getCode(), "Unable to generate report.");
        }
      }
      Object localObject1 = null;
      String str2 = paramReportCriteria.getReportOptions().getProperty("REPORTTYPE");
      Object localObject2 = new SecureUser(Locale.getDefault());
      ((SecureUser)localObject2).setBaseCurrency("USD");
      if (str2.equals("BalanceSummaryReport")) {
        localObject1 = DCServiceUtil.createBalanceReport((SecureUser)localObject2, paramReportCriteria, true, false, paramHashMap);
      } else if (str2.equals("BalanceDetailReport")) {
        localObject1 = DCServiceUtil.createBalanceReport((SecureUser)localObject2, paramReportCriteria, true, true, paramHashMap);
      } else if (str2.equals("BalanceDetailOnlyReport")) {
        localObject1 = DCServiceUtil.createBalanceReport((SecureUser)localObject2, paramReportCriteria, false, true, paramHashMap);
      } else if ((str2.equals("TransactionDetail")) || (str2.equals("DepositDetail")) || (str2.equals("AccountHistory")) || (str2.equals("CreditReport")) || (str2.equals("DebitReport"))) {
        localObject1 = DCServiceUtil.createTransactionDetailReport(paramReportCriteria, paramHashMap);
      } else if (str2.equals("CustomSummaryReport")) {
        localObject1 = DCServiceUtil.createCustomSummaryReport(paramReportCriteria, paramHashMap);
      } else if (str2.equals("GeneralLedgerReport")) {
        localObject1 = DCServiceUtil.createGeneralLedgerReport(paramReportCriteria, paramHashMap);
      } else if ((str2.equals("BalanceSheetReport")) || (str2.equals("BalanceSheetSummary"))) {
        localObject1 = DCServiceUtil.createBalanceSheetReport(paramReportCriteria, paramHashMap);
      } else if (str2.equals("CashFlowForecastReport")) {
        localObject1 = DCServiceUtil.createCashFlowForeCastReport(paramReportCriteria, paramHashMap);
      } else if (str2.equals("CashFlowReport")) {
        localObject1 = DCServiceUtil.createCashFlowReport(paramReportCriteria, paramHashMap);
      } else if (str2.equals("AlertTransaction")) {
        localObject1 = DCServiceUtil.createAlertTransactionReport(paramReportCriteria, paramHashMap);
      } else {
        throw new DCException("Invalid report type `" + str2 + "'.");
      }
      return localObject1;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (RptException localRptException)
    {
      throw new DCException(localRptException.getErrorCode(), "Failed to generate report from database.");
    }
    catch (CSILException localCSILException1)
    {
      throw new DCException(localCSILException1.getServiceError(), "Failed to generate report from database.");
    }
  }
  
  public void undoFile(ImportedFile paramImportedFile, ImportedFiles paramImportedFiles, HashMap paramHashMap)
    throws DCException
  {
    Connection localConnection = null;
    LocalizableString localLocalizableString = null;
    paramHashMap.put("STATUS", "In Process");
    localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.dataconsolidator", "ServiceMessage_0", null);
    paramHashMap.put("COMMENT", localLocalizableString);
    a(paramImportedFile, paramHashMap);
    try
    {
      paramHashMap.put("LOCALE", paramImportedFile.getLocale());
      localConnection = DCAdapter.getDBConnection();
      HashMap localHashMap = a(localConnection, paramImportedFile, paramImportedFiles, paramHashMap);
      undoRolledupTransactions(localConnection, localHashMap, paramHashMap);
      DCAdapter.undoFile(localConnection, paramImportedFile, paramImportedFiles, paramHashMap);
      DCUtil.recalculateTransactionRunningBalances(localConnection, localHashMap, paramHashMap);
      DCUtil.recalculateIRCalculations(localConnection, localHashMap, paramHashMap);
      DBUtil.commit(localConnection);
      paramHashMap.put("STATUS", "Complete");
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.dataconsolidator", "ServiceMessage_1", null);
      paramHashMap.put("COMMENT", localLocalizableString);
      for (int k = 0; k < paramImportedFiles.size(); k++)
      {
        ImportedFile localImportedFile = (ImportedFile)paramImportedFiles.get(k);
        a(localImportedFile, paramHashMap);
      }
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.dataconsolidator", "ServiceMessage_2", null);
      paramHashMap.put("COMMENT", localLocalizableString);
      a(paramImportedFile, paramHashMap);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      localException.printStackTrace(new PrintWriter(System.err));
      paramHashMap.put("STATUS", "Failed");
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.dataconsolidator", "ServiceMessage_3", null);
      paramHashMap.put("COMMENT", localLocalizableString);
      a(paramImportedFile, paramHashMap);
      throw new DCException(localException);
    }
    finally
    {
      if (localConnection != null) {
        DCAdapter.releaseDBConnection(localConnection);
      }
    }
  }
  
  private static void a(ImportedFile paramImportedFile, HashMap paramHashMap)
  {
    String str1 = (String)paramHashMap.get("HOST_NAME");
    String str2 = (String)paramHashMap.get("STATUS");
    FMLogRecord localFMLogRecord = new FMLogRecord();
    localFMLogRecord.setFileType("BAI");
    localFMLogRecord.setFileName(paramImportedFile.getFileName());
    localFMLogRecord.setHostName(str1);
    localFMLogRecord.setActivityType("Undo");
    localFMLogRecord.setFromSystem("CBS DC");
    localFMLogRecord.setStatus(str2);
    try
    {
      ILocalizable localILocalizable = (ILocalizable)paramHashMap.get("COMMENT");
      localFMLogRecord.setLocalizableMessage(localILocalizable);
    }
    catch (ClassCastException localClassCastException)
    {
      String str3 = (String)paramHashMap.get("COMMENT");
      localFMLogRecord.setMessage(str3);
    }
    localFMLogRecord.setMillis(System.currentTimeMillis());
    try
    {
      FMLog.log(localFMLogRecord);
    }
    catch (FMException localFMException)
    {
      DebugLog.throwing("logUndoFile, logging failed", localFMException);
    }
  }
  
  public ImportedFiles getImportedFileList(int paramInt, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException
  {
    return DCAdapter.getImportedFileList(paramInt, paramCalendar1, paramCalendar2, paramHashMap);
  }
  
  public ImportedFiles getDependentFiles(ImportedFile paramImportedFile, HashMap paramHashMap)
    throws DCException
  {
    return DCAdapter.getDependentFiles(paramImportedFile, paramHashMap);
  }
  
  private HashMap a(Connection paramConnection, ImportedFile paramImportedFile, ImportedFiles paramImportedFiles, HashMap paramHashMap)
    throws DCException
  {
    HashMap localHashMap = new HashMap();
    ImportedFiles localImportedFiles = new ImportedFiles();
    localImportedFiles.add(paramImportedFile);
    localImportedFiles.addAll(paramImportedFiles);
    for (int k = 0; k < localImportedFiles.size(); k++)
    {
      ImportedFile localImportedFile = (ImportedFile)localImportedFiles.get(k);
      a(paramConnection, localHashMap, " DC_AccountHistory ", localImportedFile, paramHashMap);
      a(paramConnection, localHashMap, " DC_AccountSummary ", localImportedFile, paramHashMap);
      a(paramConnection, localHashMap, " DC_LoanAcctSummary ", localImportedFile, paramHashMap);
      a(paramConnection, localHashMap, " DC_CCAcctSummary ", localImportedFile, paramHashMap);
      a(paramConnection, localHashMap, " DC_ExtAcctSummary ", localImportedFile, paramHashMap);
      a(paramConnection, localHashMap, " DC_Transactions ", localImportedFile, paramHashMap);
    }
    return localHashMap;
  }
  
  private static void a(Connection paramConnection, HashMap paramHashMap1, String paramString, ImportedFile paramImportedFile, HashMap paramHashMap2)
    throws DCException
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("SELECT a.AccountID, RoutingNumber, BankID, DataDate FROM DC_Account a, ");
    localStringBuffer.append(paramString);
    localStringBuffer.append(" b WHERE b.DCAccountID=a.DCAccountID AND DataSourceFileName=? AND DataSourceFileDate=? AND DataSource=? GROUP BY a.DCAccountID, a.AccountID, RoutingNumber, BankID, DataDate ORDER BY a.DCAccountID");
    try
    {
      localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
      localPreparedStatement.setString(1, paramImportedFile.getFileName());
      DCUtil.fillTimestampColumn(localPreparedStatement, 2, paramImportedFile.getImportTimeValue());
      localPreparedStatement.setInt(3, paramImportedFile.getDataSource());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      while (localResultSet.next())
      {
        String str1 = localResultSet.getString(1);
        String str2 = localResultSet.getString(2);
        String str3 = localResultSet.getString(3);
        DateTime localDateTime1 = DCUtil.getTimestampColumn(localResultSet.getTimestamp(4), paramImportedFile.getLocale());
        AccountKey localAccountKey = new AccountKey(str1, str3, str2, 0);
        ArrayList localArrayList = (ArrayList)paramHashMap1.get(localAccountKey);
        if (localArrayList == null)
        {
          localArrayList = new ArrayList();
          paramHashMap1.put(localAccountKey, localArrayList);
        }
        if (!localArrayList.contains(localDateTime1)) {
          localArrayList.add(localDateTime1);
        }
        if (paramImportedFile.getDataClassification().equals("P"))
        {
          SecureUser localSecureUser = new SecureUser();
          localSecureUser.setBankID(str3);
          BankIdentifier localBankIdentifier = new BankIdentifier(paramImportedFile.getLocale());
          localBankIdentifier.setBankDirectoryId(str2);
          Date localDate = null;
          try
          {
            localDate = SmartCalendar.getOffsetBankingDay(localSecureUser, localBankIdentifier, localDateTime1.getTime(), 1, paramHashMap2);
          }
          catch (Exception localException2)
          {
            throw new DCException(360, "Unable to retrieve the previous business day from the smart calendar for the following bank:  routingnumber=" + str2 + ", bankid=" + str3 + ".", localException2);
          }
          DateTime localDateTime2 = new DateTime(localDate, paramImportedFile.getLocale());
          if (!localArrayList.contains(localDateTime2)) {
            localArrayList.add(localDateTime2);
          }
        }
      }
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (Exception localException1)
    {
      throw new DCException(localException1);
    }
    finally
    {
      localPreparedStatement = null;
      DBUtil.closeResultSet(localResultSet);
    }
  }
  
  protected void undoRolledupTransactions(Connection paramConnection, HashMap paramHashMap1, HashMap paramHashMap2)
    throws DCException
  {
    HashMap localHashMap1 = new HashMap();
    Set localSet1 = paramHashMap1.entrySet();
    Iterator localIterator = localSet1.iterator();
    Object localObject3;
    Object localObject4;
    Object localObject5;
    Object localObject6;
    while (localIterator.hasNext())
    {
      localObject1 = (Map.Entry)localIterator.next();
      localObject2 = (AccountKey)((Map.Entry)localObject1).getKey();
      localObject3 = (ArrayList)((Map.Entry)localObject1).getValue();
      localObject4 = new Account(Locale.getDefault(), ((AccountKey)localObject2).getAccountID(), ((AccountKey)localObject2).getRoutingNumber(), ((AccountKey)localObject2).getBankID());
      localObject5 = null;
      try
      {
        localObject5 = AccountAdapter.getBusinessesForAccount((Account)localObject4, paramHashMap2);
      }
      catch (ProfileException localProfileException1)
      {
        throw new DCException(localProfileException1);
      }
      if ((localObject5 == null) || (((Businesses)localObject5).isEmpty())) {
        return;
      }
      for (int k = 0; k < ((ArrayList)localObject3).size(); k++)
      {
        DateTime localDateTime = (DateTime)((ArrayList)localObject3).get(k);
        localObject6 = DCAdapter.getDatesWithNonZeroClosingLedger(paramConnection, (Account)localObject4, localDateTime, paramHashMap2);
        HashMap localHashMap2 = new HashMap();
        ArrayList localArrayList2 = new ArrayList();
        Object localObject8;
        Object localObject9;
        MasterSubRelation localMasterSubRelation;
        Object localObject10;
        for (int n = ((ArrayList)localObject6).size() - 1; n >= 0; n--)
        {
          localObject7 = (DateTime)((ArrayList)localObject6).get(n);
          localObject8 = (Business)((Businesses)localObject5).get(0);
          localObject9 = DCAdapter.getMasterAccounts(paramConnection, (DateTime)localObject7, ((Business)localObject8).getIdValue(), (Account)localObject4, paramHashMap2);
          for (int i1 = 0; i1 < ((Accounts)localObject9).size(); i1++)
          {
            Account localAccount = (Account)((Accounts)localObject9).get(i1);
            if (!localArrayList2.contains(localAccount))
            {
              localMasterSubRelation = DCAdapter.getMasterSubRelation(paramConnection, ((Business)localObject8).getIdValue(), (DateTime)localObject7, localAccount, (Account)localObject4, paramHashMap2);
              if (!localMasterSubRelation.getWithholdNZBSubAccounts())
              {
                localArrayList2.add(localAccount);
              }
              else
              {
                localObject10 = (ArrayList)localHashMap2.get(localObject7);
                if (localObject10 == null)
                {
                  localObject10 = new ArrayList();
                  localHashMap2.put(localObject7, localObject10);
                }
                ((ArrayList)localObject10).add(localMasterSubRelation);
              }
            }
          }
        }
        Set localSet2 = localHashMap2.entrySet();
        Object localObject7 = localSet2.iterator();
        while (((Iterator)localObject7).hasNext())
        {
          localObject8 = (Map.Entry)((Iterator)localObject7).next();
          localObject9 = (DateTime)((Map.Entry)localObject8).getKey();
          ArrayList localArrayList3 = (ArrayList)((Map.Entry)localObject8).getValue();
          for (int i2 = 0; i2 < localArrayList3.size(); i2++)
          {
            localMasterSubRelation = (MasterSubRelation)localArrayList3.get(i2);
            removeRolledTransactionsForRelationship(paramConnection, localDateTime, localMasterSubRelation, paramHashMap2);
            localObject10 = null;
            try
            {
              localObject10 = AccountAdapter.getAccount(localMasterSubRelation.getBankId(), localMasterSubRelation.getMasterRoutingNumber(), localMasterSubRelation.getMasterAccountId());
            }
            catch (ProfileException localProfileException2)
            {
              throw new DCException(localProfileException2);
            }
            AccountKey localAccountKey = null;
            if (localObject10 != null) {
              localAccountKey = new AccountKey(((Account)localObject10).getID(), ((Account)localObject10).getBankID(), ((Account)localObject10).getRoutingNum(), ((Account)localObject10).getTypeValue());
            } else {
              throw new DCException("The account with ID " + localMasterSubRelation.getMasterAccountId() + ", routing number " + localMasterSubRelation.getMasterRoutingNumber() + " and bank id " + "currentRelationship.getBankId() " + "could not be retrieved from profile, but it exists in the dataconsolidator.");
            }
            ArrayList localArrayList4 = (ArrayList)localHashMap1.get(localAccountKey);
            if (localArrayList4 == null)
            {
              localArrayList4 = new ArrayList();
              localHashMap1.put(localAccountKey, localArrayList4);
            }
            if (!localArrayList4.contains(localDateTime)) {
              localArrayList4.add(localDateTime);
            }
          }
        }
      }
    }
    Object localObject1 = localHashMap1.entrySet();
    Object localObject2 = ((Set)localObject1).iterator();
    while (((Iterator)localObject2).hasNext())
    {
      localObject3 = (Map.Entry)((Iterator)localObject2).next();
      localObject4 = (AccountKey)((Map.Entry)localObject3).getKey();
      localObject5 = (ArrayList)((Map.Entry)localObject3).getValue();
      ArrayList localArrayList1 = (ArrayList)paramHashMap1.get(localObject4);
      if (localArrayList1 == null)
      {
        localArrayList1 = new ArrayList();
        paramHashMap1.put(localObject4, localArrayList1);
      }
      for (int m = 0; m < ((ArrayList)localObject5).size(); m++)
      {
        localObject6 = (DateTime)((ArrayList)localObject5).get(m);
        if (!localArrayList1.contains(localObject6)) {
          localArrayList1.add(localObject6);
        }
      }
    }
  }
  
  protected static void removeRolledTransactionsForRelationship(Connection paramConnection, DateTime paramDateTime, MasterSubRelation paramMasterSubRelation, HashMap paramHashMap)
    throws DCException
  {
    int k = paramMasterSubRelation.getLocationIdPlacement();
    String str1 = paramMasterSubRelation.getLocationId();
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("DELETE FROM DC_Transactions WHERE DC_Transactions.DCAccountID=(SELECT a.DCAccountID FROM DC_Account a WHERE a.AccountID=? ");
    if (paramMasterSubRelation.getMasterRoutingNumber() == null) {
      localStringBuffer.append(" AND a.RoutingNumber is null ");
    } else {
      localStringBuffer.append(" AND a.RoutingNumber = ? ");
    }
    localStringBuffer.append(" ) AND DC_Transactions.DataDate =?");
    String str2 = null;
    if (k == 1)
    {
      str2 = " AND DC_Transactions.BankRefNum = ? ";
    }
    else if (k == 2)
    {
      str2 = " AND DC_Transactions.CustRefNum = ? ";
    }
    else
    {
      if (k == 0) {
        throw new DCException(458, "The location ID placement value provided in a historical master sub relationship indicated that the location id placement was none.  Since this entry should only have made when transactions were rolled up, and roll up does not occur without a location placement id it has been determined that the data retrieved is incorrect.");
      }
      throw new DCException(458, "The location ID placement value provided in a historical master sub relationship ( " + k + " ) was not valid.  The valid values for this argument are " + 1 + " for bank reference number and " + 2 + " for customer reference number.");
    }
    localStringBuffer.append(str2);
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
      int m = 1;
      localPreparedStatement.setString(m++, paramMasterSubRelation.getMasterAccountId());
      if (paramMasterSubRelation.getMasterRoutingNumber() != null) {
        localPreparedStatement.setString(m++, paramMasterSubRelation.getMasterRoutingNumber());
      }
      DCUtil.fillTimestampColumn(localPreparedStatement, m++, paramDateTime);
      localPreparedStatement.setString(m++, str1);
      DBUtil.executeUpdate(localPreparedStatement, localStringBuffer.toString());
    }
    catch (Exception localException)
    {
      throw new DCException(localException);
    }
    finally
    {
      localPreparedStatement = null;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.dataconsolidator.DataConsolidatorService
 * JD-Core Version:    0.7.0.1
 */