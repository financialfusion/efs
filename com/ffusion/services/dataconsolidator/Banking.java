package com.ffusion.services.dataconsolidator;

import com.ffusion.beans.FundsTransactions;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.AccountHistories;
import com.ffusion.beans.accounts.AccountSummaries;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.accounts.ExtendedAccountSummaries;
import com.ffusion.beans.accounts.FixedDepositInstrument;
import com.ffusion.beans.accounts.FixedDepositInstruments;
import com.ffusion.beans.banking.RecTransfer;
import com.ffusion.beans.banking.RecTransfers;
import com.ffusion.beans.banking.Transaction;
import com.ffusion.beans.banking.TransactionTypeInfo;
import com.ffusion.beans.banking.Transactions;
import com.ffusion.beans.banking.Transfer;
import com.ffusion.beans.banking.TransferBatch;
import com.ffusion.beans.banking.Transfers;
import com.ffusion.beans.dataconsolidator.BAITypeCodeInfo;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.BusinessEmployees;
import com.ffusion.beans.util.AccountUtil;
import com.ffusion.beans.util.UtilException;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.MultiEntitlement;
import com.ffusion.csil.core.DataConsolidator;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.csil.core.Reporting;
import com.ffusion.dataconsolidator.adapter.DCAdapter;
import com.ffusion.dataconsolidator.adapter.DCException;
import com.ffusion.dataconsolidator.adapter.DCUtil;
import com.ffusion.efs.adapters.profile.CustomerAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.reporting.IReportResult;
import com.ffusion.reporting.RptException;
import com.ffusion.services.AccountService;
import com.ffusion.services.Banking8;
import com.ffusion.services.BankingException;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.FFIStringTokenizer;
import com.ffusion.util.FFIUtilException;
import com.ffusion.util.MapUtil;
import com.ffusion.util.TransactionTypeCache;
import com.ffusion.util.XMLUtil;
import com.ffusion.util.beans.InvalidDateTimeException;
import com.ffusion.util.beans.PagingContext;
import com.ffusion.util.logging.DebugLog;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.Level;

public class Banking
  implements Banking8, AccountService
{
  private String cm;
  public static final String FIRST_PAGING_INDEX = "FIRST_PAGING_INDEX";
  public static final String LAST_PAGING_INDEX = "LAST_PAGING_INDEX";
  public static final int NUMBER_OF_DAYS_IN_ALL_DATES_SEARCH = 31;
  
  public void setInitURL(String paramString)
  {
    initialize(paramString);
  }
  
  public int signOn(String paramString1, String paramString2)
  {
    return 0;
  }
  
  public int changePIN(String paramString1, String paramString2)
  {
    return 2;
  }
  
  public int initialize(String paramString)
  {
    try
    {
      HashMap localHashMap = XMLUtil.readXmlToHashMap(this, paramString);
      localObject = (HashMap)localHashMap.get("DCADAPTER");
      DCAdapter.initialize((HashMap)((HashMap)localObject).get("DCADAPTER_SETTINGS"));
    }
    catch (Exception localException)
    {
      Object localObject = new StringWriter();
      PrintWriter localPrintWriter = new PrintWriter((Writer)localObject);
      localException.printStackTrace(localPrintWriter);
      DebugLog.log(Level.SEVERE, ((StringWriter)localObject).toString());
      return 1;
    }
    return 0;
  }
  
  public void setSettings(String paramString)
  {
    this.cm = paramString;
  }
  
  public String getSettings()
  {
    return this.cm;
  }
  
  public int getAccounts(Accounts paramAccounts)
  {
    int i = 0;
    Connection localConnection = null;
    try
    {
      localConnection = DCAdapter.getDBConnection();
      Accounts localAccounts = new Accounts(paramAccounts.getLocale());
      HashMap localHashMap = new HashMap();
      for (int j = 0; j < paramAccounts.size(); j++)
      {
        Account localAccount1 = (Account)paramAccounts.get(j);
        if (localAccount1 != null)
        {
          Account localAccount2 = DCAdapter.getAccount(localAccount1, localConnection, localHashMap);
          if (localAccount2 != null) {
            localAccounts.add(localAccount2);
          }
        }
      }
      paramAccounts.clear();
      paramAccounts.set(localAccounts);
    }
    catch (DCException localDCException)
    {
      i = localDCException.getCode();
    }
    finally
    {
      if (localConnection != null) {
        try
        {
          DCAdapter.releaseDBConnection(localConnection);
        }
        catch (Exception localException) {}
      }
    }
    return i;
  }
  
  public int getAccount(Account paramAccount)
  {
    int i = 0;
    try
    {
      paramAccount = DCAdapter.getAccount(paramAccount, new HashMap());
    }
    catch (DCException localDCException)
    {
      i = localDCException.getCode();
    }
    return i;
  }
  
  public int getTransactions(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2)
  {
    int i = 0;
    try
    {
      paramAccount.setTransactions(DCAdapter.getTransactions(paramAccount, paramCalendar1, paramCalendar2, new HashMap()));
    }
    catch (DCException localDCException)
    {
      i = localDCException.getCode();
    }
    return i;
  }
  
  public int getTransactions(Account paramAccount)
  {
    return getTransactions(paramAccount, null, null);
  }
  
  public int getTransfers(Accounts paramAccounts, Transfers paramTransfers)
  {
    return 2;
  }
  
  public int getRecTransfers(Accounts paramAccounts, RecTransfers paramRecTransfers)
  {
    return 2;
  }
  
  public int sendTransfer(Account paramAccount1, Account paramAccount2, Transfer paramTransfer)
  {
    return 2;
  }
  
  public int sendTransfer(Account paramAccount1, Account paramAccount2, Transfer paramTransfer, HashMap paramHashMap)
  {
    return 2;
  }
  
  public int cancelTransfer(Transfer paramTransfer)
  {
    return 2;
  }
  
  public int sendRecTransfer(Account paramAccount1, Account paramAccount2, RecTransfer paramRecTransfer)
  {
    return 2;
  }
  
  public int sendRecTransfer(Account paramAccount1, Account paramAccount2, RecTransfer paramRecTransfer, HashMap paramHashMap)
  {
    return 2;
  }
  
  public int cancelRecTransfer(RecTransfer paramRecTransfer)
  {
    return 2;
  }
  
  public int modifyTransfer(Account paramAccount1, Account paramAccount2, Transfer paramTransfer)
  {
    return 2;
  }
  
  public int modifyTransfer(Account paramAccount1, Account paramAccount2, Transfer paramTransfer, HashMap paramHashMap)
  {
    return 2;
  }
  
  public int modifyRecTransfer(Account paramAccount1, Account paramAccount2, RecTransfer paramRecTransfer)
  {
    return 2;
  }
  
  public int modifyRecTransfer(Account paramAccount1, Account paramAccount2, RecTransfer paramRecTransfer, HashMap paramHashMap)
  {
    return 2;
  }
  
  public int skipRecTransfer(Account paramAccount1, Account paramAccount2, RecTransfer paramRecTransfer)
  {
    return 2;
  }
  
  public int addTransferBatch(TransferBatch paramTransferBatch)
  {
    return 2;
  }
  
  public int cancelTransferBatch(TransferBatch paramTransferBatch)
  {
    return 2;
  }
  
  public int modifyTransferBatch(TransferBatch paramTransferBatch)
  {
    return 2;
  }
  
  public FundsTransactions getPagedFundsTransactions(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    throw new BankingException(1, "Method Not Supported");
  }
  
  public FixedDepositInstruments getFixedDepositInstruments(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws BankingException
  {
    try
    {
      FixedDepositInstruments localFixedDepositInstruments = DCAdapter.getFixedDepositInstruments(paramAccount, paramCalendar1, paramCalendar2, paramHashMap);
      return localFixedDepositInstruments;
    }
    catch (DCException localDCException)
    {
      BankingException localBankingException = new BankingException(localDCException.getCode(), localDCException.getMessage());
      throw localBankingException;
    }
  }
  
  public void updateFixedDepositInstrument(FixedDepositInstrument paramFixedDepositInstrument, HashMap paramHashMap)
    throws BankingException
  {
    Connection localConnection = null;
    try
    {
      localConnection = DCAdapter.getDBConnection();
      DCAdapter.addFixedDepositInstrument(paramFixedDepositInstrument, 0, localConnection, paramHashMap);
      DCAdapter.commit(localConnection);
      BankingException localBankingException;
      return;
    }
    catch (DCException localDCException)
    {
      try
      {
        DCAdapter.rollback(localConnection);
      }
      catch (Exception localException2) {}
      localBankingException = new BankingException(localDCException.getCode(), localDCException.getMessage());
      throw localBankingException;
    }
    finally
    {
      try
      {
        DCAdapter.releaseDBConnection(localConnection);
      }
      catch (Exception localException3) {}
    }
  }
  
  public Transactions getRecentTransactions(Account paramAccount, HashMap paramHashMap)
    throws BankingException
  {
    PagingContext localPagingContext = new PagingContext(null, null);
    ReportCriteria localReportCriteria = new ReportCriteria();
    HashMap localHashMap1 = new HashMap();
    localPagingContext.setMap(localHashMap1);
    localPagingContext.getMap().put("ReportCriteria", localReportCriteria);
    Transactions localTransactions = getRecentTransactions(paramAccount, localPagingContext, paramHashMap);
    localTransactions.setSortedBy("TRANS_IDX");
    HashMap localHashMap2 = localPagingContext.getMap();
    paramHashMap.put("MINIMUM_TRANSACTION_INDEX_FOR_RANGE", localHashMap2.get("LOWER_BOUND_TransactionIndex"));
    paramHashMap.put("MAXIMUM_TRANSACTION_INDEX_FOR_RANGE", localHashMap2.get("UPPER_BOUND_TransactionIndex"));
    if (localTransactions.size() > 0)
    {
      Transaction localTransaction = (Transaction)localTransactions.get(0);
      localPagingContext.setFirstIndex(localTransaction.getTransactionIndex());
      localTransaction = (Transaction)localTransactions.get(localTransactions.size() - 1);
      localPagingContext.setLastIndex(localTransaction.getTransactionIndex());
      HashMap localHashMap3 = new HashMap();
      localHashMap3.put("FIRST_PAGING_INDEX", paramHashMap.get("MINIMUM_TRANSACTION_INDEX_FOR_RANGE"));
      localHashMap3.put("LAST_PAGING_INDEX", paramHashMap.get("MAXIMUM_TRANSACTION_INDEX_FOR_RANGE"));
      localPagingContext.setMap(localHashMap3);
      long l = ((Long)paramHashMap.get("MINIMUM_TRANSACTION_INDEX_FOR_RANGE")).longValue();
      localPagingContext.setFirstPage(localPagingContext.getFirstIndex() <= l);
      localPagingContext.setLastPage(true);
    }
    else
    {
      localPagingContext.setFirstPage(true);
      localPagingContext.setLastPage(true);
    }
    localTransactions.setPagingContext(localPagingContext);
    return localTransactions;
  }
  
  public Transactions getRecentTransactions(Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    try
    {
      validatePagingContext(paramPagingContext, paramHashMap);
      if (paramPagingContext == null) {
        paramPagingContext = new PagingContext(null, null);
      }
      Transactions localTransactions = DCAdapter.getRecentTransactions(paramAccount, paramPagingContext, paramHashMap);
      localTransactions.setPagingContext(paramPagingContext);
      return localTransactions;
    }
    catch (DCException localDCException)
    {
      throw new BankingException(localDCException.getCode(), localDCException.getMessage());
    }
  }
  
  public Transactions getPagedTransactions(Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    int i = paramPagingContext.getMap().get("ReportCriteria") == null ? 1 : 0;
    try
    {
      Transactions localTransactions = null;
      if (i != 0)
      {
        localTransactions = DCAdapter.getPagedTransactions(paramAccount, paramPagingContext.getStartDate(), paramPagingContext.getEndDate(), paramHashMap);
        if (localTransactions.size() > 0)
        {
          Transaction localTransaction = (Transaction)localTransactions.get(0);
          paramPagingContext.setFirstIndex(localTransaction.getTransactionIndex());
          localTransaction = (Transaction)localTransactions.get(localTransactions.size() - 1);
          paramPagingContext.setLastIndex(localTransaction.getTransactionIndex());
          HashMap localHashMap = new HashMap();
          localHashMap.put("FIRST_PAGING_INDEX", paramHashMap.get("MINIMUM_TRANSACTION_INDEX_FOR_RANGE"));
          localHashMap.put("LAST_PAGING_INDEX", paramHashMap.get("MAXIMUM_TRANSACTION_INDEX_FOR_RANGE"));
          paramPagingContext.setMap(localHashMap);
          long l = ((Long)paramHashMap.get("MINIMUM_TRANSACTION_INDEX_FOR_RANGE")).longValue();
          paramPagingContext.setFirstPage(paramPagingContext.getFirstIndex() <= l);
          paramPagingContext.setLastPage(true);
        }
        else
        {
          paramPagingContext.setFirstPage(true);
          paramPagingContext.setLastPage(true);
        }
        paramPagingContext.getMap().remove("ReportCriteria");
      }
      else
      {
        validatePagingContext(paramPagingContext, paramHashMap);
        localTransactions = DCAdapter.getPagedTransactions(paramAccount, paramPagingContext, paramHashMap);
      }
      localTransactions.setPagingContext(paramPagingContext);
      return localTransactions;
    }
    catch (DCException localDCException)
    {
      throw new BankingException(localDCException.getCode(), localDCException.getMessage());
    }
  }
  
  public Transactions getNextTransactions(Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    int i = paramPagingContext.getMap().get("ReportCriteria") == null ? 1 : 0;
    try
    {
      Transactions localTransactions = null;
      if (i != 0)
      {
        HashMap localHashMap1 = new HashMap();
        localHashMap1.putAll(paramHashMap);
        HashMap localHashMap2 = paramPagingContext.getMap();
        Object localObject;
        if (localHashMap2 != null)
        {
          localObject = (Long)localHashMap2.get("FIRST_PAGING_INDEX");
          Long localLong1 = (Long)localHashMap2.get("LAST_PAGING_INDEX");
          if (localObject != null) {
            localHashMap1.put("MINIMUM_TRANSACTION_INDEX_FOR_RANGE", localObject);
          }
          if (localLong1 != null) {
            localHashMap1.put("MAXIMUM_TRANSACTION_INDEX_FOR_RANGE", localLong1);
          }
        }
        localTransactions = DCAdapter.getNextTransactions(paramAccount, paramPagingContext.getLastIndex() + 1L, localHashMap1);
        if (localTransactions.size() > 0)
        {
          localObject = (Transaction)localTransactions.get(0);
          paramPagingContext.setFirstIndex(((Transaction)localObject).getTransactionIndex());
          localObject = (Transaction)localTransactions.get(localTransactions.size() - 1);
          paramPagingContext.setLastIndex(((Transaction)localObject).getTransactionIndex());
          boolean bool = false;
          HashMap localHashMap3 = paramPagingContext.getMap();
          if (localHashMap3 != null)
          {
            Long localLong2 = (Long)localHashMap3.get("LAST_PAGING_INDEX");
            if (localLong2 != null) {
              bool = localLong2.longValue() <= paramPagingContext.getLastIndex();
            }
          }
          paramPagingContext.setFirstPage(false);
          paramPagingContext.setLastPage(bool);
        }
        else
        {
          paramPagingContext.setFirstPage(true);
          paramPagingContext.setLastPage(true);
        }
        paramPagingContext.getMap().remove("ReportCriteria");
      }
      else
      {
        validatePagingContext(paramPagingContext, paramHashMap);
        localTransactions = DCAdapter.getNextTransactions(paramAccount, paramPagingContext, paramHashMap);
      }
      localTransactions.setPagingContext(paramPagingContext);
      return localTransactions;
    }
    catch (DCException localDCException)
    {
      throw new BankingException(localDCException.getCode(), localDCException.getMessage());
    }
  }
  
  public Transactions getPreviousTransactions(Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    try
    {
      int i = paramPagingContext.getMap().get("ReportCriteria") == null ? 1 : 0;
      Transactions localTransactions = null;
      if (i != 0)
      {
        HashMap localHashMap1 = new HashMap();
        localHashMap1.putAll(paramHashMap);
        HashMap localHashMap2 = paramPagingContext.getMap();
        Object localObject;
        if (localHashMap2 != null)
        {
          localObject = (Long)localHashMap2.get("FIRST_PAGING_INDEX");
          Long localLong1 = (Long)localHashMap2.get("LAST_PAGING_INDEX");
          if (localObject != null) {
            localHashMap1.put("MINIMUM_TRANSACTION_INDEX_FOR_RANGE", localObject);
          }
          if (localLong1 != null) {
            localHashMap1.put("MAXIMUM_TRANSACTION_INDEX_FOR_RANGE", localLong1);
          }
        }
        localTransactions = DCAdapter.getPreviousTransactions(paramAccount, paramPagingContext.getFirstIndex() - 1L, localHashMap1);
        int j = localTransactions.size();
        if (j > 0)
        {
          localObject = (Transaction)localTransactions.get(0);
          paramPagingContext.setFirstIndex(((Transaction)localObject).getTransactionIndex());
          localObject = (Transaction)localTransactions.get(j - 1);
          paramPagingContext.setLastIndex(((Transaction)localObject).getTransactionIndex());
          boolean bool = false;
          HashMap localHashMap3 = paramPagingContext.getMap();
          if (localHashMap3 != null)
          {
            Long localLong2 = (Long)localHashMap3.get("FIRST_PAGING_INDEX");
            if (localLong2 != null) {
              bool = localLong2.longValue() >= paramPagingContext.getFirstIndex();
            }
          }
          paramPagingContext.setFirstPage(bool);
          paramPagingContext.setLastPage(false);
        }
        else
        {
          paramPagingContext.setFirstPage(true);
          paramPagingContext.setLastPage(true);
        }
        paramPagingContext.getMap().remove("ReportCriteria");
      }
      else
      {
        validatePagingContext(paramPagingContext, paramHashMap);
        localTransactions = DCAdapter.getPreviousTransactions(paramAccount, paramPagingContext, paramHashMap);
      }
      localTransactions.setPagingContext(paramPagingContext);
      return localTransactions;
    }
    catch (DCException localDCException)
    {
      throw new BankingException(localDCException.getCode(), localDCException.getMessage());
    }
  }
  
  public Transactions getFDInstrumentTransactions(FixedDepositInstrument paramFixedDepositInstrument, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws BankingException
  {
    try
    {
      return DCAdapter.getFDInstrumentTransactions(paramFixedDepositInstrument, paramCalendar1, paramCalendar2, paramHashMap);
    }
    catch (DCException localDCException)
    {
      throw new BankingException(localDCException.getCode(), localDCException.getMessage());
    }
  }
  
  public int getNumTransactions(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws BankingException
  {
    try
    {
      return DCAdapter.getNumTransactions(paramAccount, paramCalendar1, paramCalendar2, paramHashMap);
    }
    catch (DCException localDCException)
    {
      throw new BankingException(localDCException.getCode(), localDCException.getMessage());
    }
  }
  
  public int getNumTransactions(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, Properties paramProperties, HashMap paramHashMap)
    throws BankingException
  {
    try
    {
      return DCAdapter.getNumTransactions(paramAccount, paramCalendar1, paramCalendar2, paramProperties, paramHashMap);
    }
    catch (DCException localDCException)
    {
      throw new BankingException(localDCException.getCode(), localDCException.getMessage());
    }
  }
  
  public AccountHistories getHistory(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws BankingException
  {
    try
    {
      return DCUtil.combineAccountHistoriesByDate(DCAdapter.getHistory(paramAccount, paramCalendar1, paramCalendar2, paramHashMap));
    }
    catch (DCException localDCException)
    {
      throw new BankingException(localDCException.getCode(), localDCException.getMessage());
    }
  }
  
  public AccountSummaries getSummary(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws BankingException
  {
    try
    {
      return DCUtil.combineAccountSummariesByDate(DCAdapter.getSummary(paramAccount, paramCalendar1, paramCalendar2, paramHashMap));
    }
    catch (DCException localDCException)
    {
      throw new BankingException(localDCException.getCode(), localDCException.getMessage());
    }
  }
  
  public ExtendedAccountSummaries getExtendedSummary(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws BankingException
  {
    try
    {
      return DCUtil.combineExtendedAccountSummariesByDate(DCAdapter.getExtendedSummary(paramAccount, paramCalendar1, paramCalendar2, paramHashMap));
    }
    catch (DCException localDCException)
    {
      throw new BankingException(localDCException.getCode(), localDCException.getMessage());
    }
  }
  
  public IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws BankingException
  {
    String str1 = "Banking.getReportData";
    a(paramReportCriteria, paramHashMap);
    Object localObject1 = null;
    int i = 0;
    com.ffusion.beans.DateTime localDateTime1 = null;
    BusinessEmployee localBusinessEmployee = null;
    Properties localProperties1 = null;
    try
    {
      localProperties1 = paramReportCriteria.getSearchCriteria();
      Properties localProperties2 = paramReportCriteria.getReportOptions();
      String str2 = localProperties2.getProperty("REPORTTYPE");
      String str3 = localProperties1.getProperty("Accounts");
      Object localObject3;
      Object localObject4;
      Object localObject5;
      if (DCServiceUtil.accountCriterionHasAllAccounts(str3))
      {
        a(paramHashMap, "attempt to leave only entitled accounts in extra and to place date ranges in extra");
        localObject2 = (Accounts)paramHashMap.get("Accounts");
        localObject2 = a(paramSecureUser, (Accounts)localObject2, paramReportCriteria, paramHashMap);
        paramHashMap.put("Accounts", localObject2);
        HashMap localHashMap1 = new HashMap();
        localObject3 = new HashMap();
        try
        {
          Reporting.calculateDateRange(paramSecureUser, (Accounts)localObject2, paramReportCriteria, localHashMap1, (HashMap)localObject3, paramHashMap);
          if (!str2.equals("AccountHistory")) {
            a((Accounts)localObject2, localHashMap1, (HashMap)localObject3, paramReportCriteria);
          }
          paramHashMap.put("StartDates", localHashMap1);
        }
        catch (CSILException localCSILException2)
        {
          localObject4 = "Unable to calculate the date ranges. A report cannot be run.";
          DebugLog.log((String)localObject4);
          if (localCSILException2.getCode() == -1009) {
            throw new BankingException(localCSILException2.getServiceError());
          }
          throw new BankingException(localCSILException2.getCode());
        }
      }
      else
      {
        localObject2 = new StringTokenizer(str3, ",");
        int j = ((StringTokenizer)localObject2).countTokens();
        if (j < 1) {
          throw new BankingException(405, "Value for SearchCriteria key Accounts has incorrect format.");
        }
        localObject3 = new Accounts();
        for (int k = 0; k < j; k++)
        {
          localObject4 = ((StringTokenizer)localObject2).nextToken();
          localObject5 = new FFIStringTokenizer((String)localObject4, ":");
          int m = ((FFIStringTokenizer)localObject5).countTokens();
          if (m < 2) {
            throw new BankingException(405, "Value for SearchCriteria key Accounts has incorrect format.");
          }
          Account localAccount = new Account();
          localAccount.setID(((FFIStringTokenizer)localObject5).nextToken());
          localAccount.setBankID(((FFIStringTokenizer)localObject5).nextToken());
          if (m >= 3) {
            localAccount.setRoutingNum(((FFIStringTokenizer)localObject5).nextToken());
          }
          if (m >= 4) {
            localAccount.setNickName(((FFIStringTokenizer)localObject5).nextToken());
          }
          if (m >= 5) {
            localAccount.setCurrencyCode(((FFIStringTokenizer)localObject5).nextToken());
          }
          if (!a(paramSecureUser, localAccount, paramReportCriteria)) {
            throw new BankingException(442, "User is not entitled to the requested account (" + (String)localObject4 + ")");
          }
          ((Accounts)localObject3).add(localAccount);
        }
        if (paramHashMap == null) {
          paramHashMap = new HashMap();
        }
        try
        {
          HashMap localHashMap2 = new HashMap();
          localObject4 = new HashMap();
          Reporting.calculateDateRange(paramSecureUser, (Accounts)localObject3, paramReportCriteria, localHashMap2, (HashMap)localObject4, paramHashMap);
          if ((!str2.equals("AccountHistory")) && (!str2.equals("ConsumerAccountHistory"))) {
            a((Accounts)localObject3, localHashMap2, (HashMap)localObject4, paramReportCriteria);
          }
          paramHashMap.put("StartDates", localHashMap2);
        }
        catch (CSILException localCSILException3)
        {
          localObject4 = "Unable to calcualte the date ranges. A report cannot be run.";
          DebugLog.log((String)localObject4);
          if (localCSILException3.getCode() == -1009) {
            throw new BankingException(localCSILException3.getServiceError());
          }
          throw new BankingException(localCSILException3.getCode());
        }
      }
      paramReportCriteria.setCurrentSearchCriterion("Date Range Type");
      Object localObject2 = paramReportCriteria.getCurrentSearchCriterionValue();
      if ((localObject2 != null) && (((String)localObject2).equals("Relative")))
      {
        paramReportCriteria.setCurrentSearchCriterion("Date Range");
        String str4 = paramReportCriteria.getCurrentSearchCriterionValue();
        if ((str4 != null) && (str4.equals("Today(new)"))) {
          i = 1;
        }
      }
      if (i != 0)
      {
        boolean bool2 = localProperties1.containsKey("Datasource Load Start Time");
        if (!bool2)
        {
          localObject3 = new ArrayList();
          ((ArrayList)localObject3).add(Integer.toString(paramSecureUser.getProfileID()));
          BusinessEmployees localBusinessEmployees = null;
          try
          {
            localBusinessEmployees = CustomerAdapter.getBusinessEmployeesByIds((ArrayList)localObject3);
          }
          catch (ProfileException localProfileException2)
          {
            DebugLog.throwing("Unable to get the business employee object to determain the last time this user viewed intra-day transactions.", localProfileException2);
            throw new BankingException(363, "Unable to get the business employee object to determain the last time this user viewed intra-day transactions.");
          }
          com.ffusion.beans.DateTime localDateTime2 = new com.ffusion.beans.DateTime();
          localDateTime2.set(11, 0);
          localDateTime2.set(12, 0);
          localDateTime2.set(13, 0);
          localDateTime2.set(14, 0);
          localObject5 = localDateTime2;
          if (localBusinessEmployees.size() != 0)
          {
            localBusinessEmployee = (BusinessEmployee)localBusinessEmployees.get(0);
            if ((localBusinessEmployee.getLastViewedIntradayTransDate() != null) && (localBusinessEmployee.getLastViewedIntradayTransDate().isSameDayInYearAs(localDateTime2))) {
              localObject5 = localBusinessEmployee.getLastViewedIntradayTransDate();
            }
          }
          localProperties1.put("Datasource Load Start Time", DateFormatUtil.getFormatter("MM/dd/yyyy HH:mm:ss").format(((com.ffusion.beans.DateTime)localObject5).getTime()));
        }
      }
      if (paramHashMap == null) {
        paramHashMap = new HashMap();
      }
      paramHashMap.put("SecureUser", paramSecureUser);
      String str5 = (String)localProperties1.get("DataClassification");
      paramHashMap.put("DATA_CLASSIFICATION", str5);
      a(paramReportCriteria, paramSecureUser.getLocale());
      DCServiceUtil.hideEmptySearchCriteria(paramReportCriteria);
      localDateTime1 = new com.ffusion.beans.DateTime();
      if (str2.equals("BalanceSummaryReport")) {
        localObject1 = DCServiceUtil.createBalanceReport(paramSecureUser, paramReportCriteria, true, false, paramHashMap);
      } else if (str2.equals("BalanceDetailReport")) {
        localObject1 = DCServiceUtil.createBalanceReport(paramSecureUser, paramReportCriteria, true, true, paramHashMap);
      } else if (str2.equals("BalanceDetailOnlyReport")) {
        localObject1 = DCServiceUtil.createBalanceReport(paramSecureUser, paramReportCriteria, false, true, paramHashMap);
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
      } else if (str2.equals("ConsumerAccountHistory")) {
        localObject1 = DCServiceUtil.generateConsumerAcctHistReport(paramReportCriteria, paramSecureUser, paramHashMap);
      } else {
        throw new DCException("Invalid report type `" + str2 + "'.");
      }
    }
    catch (CSILException localCSILException1)
    {
      DebugLog.throwing(str1, localCSILException1);
      a(localCSILException1);
    }
    catch (DCException localDCException)
    {
      DebugLog.throwing(str1, localDCException);
      throw new BankingException(localDCException.getCode(), localDCException.getMessage());
    }
    catch (RptException localRptException)
    {
      DebugLog.throwing(str1, localRptException);
      throw new BankingException(413, localRptException.getMessage());
    }
    finally
    {
      paramHashMap.remove("SecureUser");
    }
    if (i != 0)
    {
      boolean bool1 = localProperties1.containsKey("Datasource Load End Time");
      if (!bool1)
      {
        localBusinessEmployee.setLastViewedIntradayTransDate(localDateTime1);
        try
        {
          CustomerAdapter.modifyBusinessEmployee(localBusinessEmployee);
        }
        catch (ProfileException localProfileException1)
        {
          DebugLog.throwing("Unable to update the business employee to reflect the last time this user viewed intra-day transactions.", localProfileException1);
          throw new BankingException(364, "Unable to update the business employee to reflect the last time this user viewed intra-day transactions.");
        }
        localProperties1.put("Datasource Load End Time", DateFormatUtil.getFormatter("MM/dd/yyyy HH:mm:ss").format(localDateTime1.getTime()));
      }
    }
    return localObject1;
  }
  
  private void a(ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws BankingException
  {
    String str1 = "Banking.validateCriteria";
    if (paramReportCriteria == null)
    {
      localObject1 = "Report criteria is missing - report cannot be run.";
      localObject2 = new BankingException(401, (String)localObject1);
      DebugLog.throwing(str1, (Throwable)localObject2);
      throw ((Throwable)localObject2);
    }
    Object localObject1 = paramReportCriteria.getSearchCriteria();
    if (localObject1 == null)
    {
      localObject2 = "Search criteria is missing - report cannot be run.";
      localObject3 = new BankingException(403, (String)localObject2);
      DebugLog.throwing(str1, (Throwable)localObject3);
      throw ((Throwable)localObject3);
    }
    Object localObject2 = paramReportCriteria.getReportOptions();
    if (localObject2 == null)
    {
      localObject3 = "The report criteria used in a call to getAccountReportData did not contain a valid report options object.  This object is required for report retrieval.";
      throw new BankingException(1022, (String)localObject3);
    }
    Object localObject3 = ((Properties)localObject2).getProperty("REPORTTYPE");
    if (localObject3 == null)
    {
      localObject4 = "The report options contained within the Report Criteria used in a call to getAccountReportData does not contain a report type.";
      throw new BankingException(1023, (String)localObject4);
    }
    Object localObject4 = paramReportCriteria.getSortCriteria();
    if (localObject4 == null)
    {
      String str2 = "A sort criteria object was not found within the given report criteria.";
      BankingException localBankingException = new BankingException(402, str2);
      DebugLog.throwing(str1, localBankingException);
      throw localBankingException;
    }
    a((Properties)localObject1, paramHashMap);
    jdMethod_do((Properties)localObject1, paramHashMap);
  }
  
  private void a(HashMap paramHashMap, String paramString)
    throws BankingException
  {
    String str1 = "Banking.checkExtraNotNull";
    if (paramHashMap == null)
    {
      String str2 = "extra is null when it is required ";
      if (paramString != null) {
        str2 = str2 + "(" + paramString + ")";
      }
      BankingException localBankingException = new BankingException(443, str2);
      DebugLog.throwing(str1, localBankingException);
      throw localBankingException;
    }
  }
  
  private void a(Properties paramProperties, HashMap paramHashMap)
    throws BankingException
  {
    String str1 = "Banking.validateAccounts";
    String str2 = paramProperties.getProperty("Accounts");
    Object localObject1;
    Object localObject2;
    if ((str2 == null) || (str2.equals("")))
    {
      localObject1 = "No accounts specified in the search criteria";
      localObject2 = new BankingException(404, (String)localObject1);
      DebugLog.throwing(str1, (Throwable)localObject2);
      throw ((Throwable)localObject2);
    }
    if (!DCServiceUtil.accountCriterionHasAllAccounts(str2))
    {
      localObject1 = new StringTokenizer(str2, ",");
      while (((StringTokenizer)localObject1).hasMoreTokens())
      {
        localObject2 = ((StringTokenizer)localObject1).nextToken();
        FFIStringTokenizer localFFIStringTokenizer = new FFIStringTokenizer((String)localObject2, ":");
        if (localFFIStringTokenizer.countTokens() < 3)
        {
          String str3 = "The account (value '" + (String)localObject2 + "') was not formatted properly as search criteria.";
          BankingException localBankingException = new BankingException(405, str3);
          DebugLog.throwing(str1, localBankingException);
          throw localBankingException;
        }
      }
    }
  }
  
  private void jdMethod_do(Properties paramProperties, HashMap paramHashMap)
    throws BankingException
  {
    String str1 = "Banking.validateDataClassification";
    String str2 = paramProperties.getProperty("DataClassification");
    String str3;
    BankingException localBankingException;
    if (str2 == null)
    {
      str3 = "The data classification is missing.";
      localBankingException = new BankingException(330, str3);
      DebugLog.throwing(str1, localBankingException);
      throw localBankingException;
    }
    if ((!str2.equals("P")) && (!str2.equals("I")) && (!str2.equals("A")))
    {
      str3 = "The data classification '" + str2 + "' is invalid.";
      localBankingException = new BankingException(331, str3);
      DebugLog.throwing(str1, localBankingException);
      throw localBankingException;
    }
  }
  
  private void jdMethod_if(Properties paramProperties, HashMap paramHashMap)
    throws BankingException
  {
    String str1 = "Banking.validateTransactionGroups";
    String str2 = paramProperties.getProperty("TransactionType");
    if ((str2 == null) || (str2.length() == 0)) {
      return;
    }
    StringTokenizer localStringTokenizer1 = new StringTokenizer(str2, ",");
    int i = 0;
    a(paramHashMap, "attempt to retrieve data with key GenericBankingRptConsts.CUSTOM_TRANS_GROUP_CODES_FOR_REPORT");
    String str3 = (String)paramHashMap.get("CustomTransGroupCodesForReport");
    if ((str3 == null) || (str3.length() <= 0)) {
      return;
    }
    StringTokenizer localStringTokenizer2 = new StringTokenizer(str3, ",");
    while (localStringTokenizer2.hasMoreTokens())
    {
      String str4 = localStringTokenizer2.nextToken();
      try
      {
        Integer.parseInt(str4);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        String str5 = "Non-integer value '" + str4 + "' specified as BAI code for custom transaction group";
        BankingException localBankingException = new BankingException(339, str5);
        DebugLog.throwing(str1, localBankingException);
        throw localBankingException;
      }
    }
  }
  
  private void a(Accounts paramAccounts, HashMap paramHashMap1, HashMap paramHashMap2, ReportCriteria paramReportCriteria)
    throws BankingException
  {
    for (int i = 0; i < paramAccounts.size(); i++)
    {
      Account localAccount = (Account)paramAccounts.get(i);
      String str = localAccount.getRoutingNum();
      if (str != null)
      {
        com.ffusion.beans.DateTime localDateTime1 = (com.ffusion.beans.DateTime)paramHashMap1.get(str);
        com.ffusion.beans.DateTime localDateTime2 = (com.ffusion.beans.DateTime)paramHashMap2.get(str);
        Object localObject;
        com.ffusion.beans.DateTime localDateTime3;
        if ((localDateTime1 == null) && (localDateTime2 == null))
        {
          localObject = new com.ffusion.beans.DateTime(Locale.getDefault());
          ((com.ffusion.beans.DateTime)localObject).add(5, 1);
          ((com.ffusion.beans.DateTime)localObject).add(14, -1);
          localDateTime3 = new com.ffusion.beans.DateTime((Calendar)localObject, Locale.getDefault());
          localDateTime3.add(5, -1 * DCServiceUtil.a(paramReportCriteria));
          localDateTime3.set(11, 0);
          localDateTime3.set(12, 0);
          localDateTime3.set(13, 0);
          localDateTime3.set(14, 0);
          paramHashMap1.put(str, localDateTime3);
          paramHashMap2.put(str, localObject);
        }
        else if ((localDateTime1 != null) && (localDateTime2 == null))
        {
          localDateTime3 = new com.ffusion.beans.DateTime(localDateTime1, Locale.getDefault());
          localDateTime3.add(5, DCServiceUtil.a(paramReportCriteria));
          localDateTime3.add(5, 1);
          localDateTime3.add(14, -1);
          paramHashMap2.put(str, localDateTime3);
        }
        else if ((localDateTime1 == null) && (localDateTime2 != null))
        {
          localDateTime3 = new com.ffusion.beans.DateTime(localDateTime2, Locale.getDefault());
          localDateTime3.add(5, -1 * DCServiceUtil.a(paramReportCriteria));
          localDateTime3.set(11, 0);
          localDateTime3.set(12, 0);
          localDateTime3.set(13, 0);
          localDateTime3.set(14, 0);
          paramHashMap1.put(str, localDateTime3);
        }
        else
        {
          localDateTime3 = new com.ffusion.beans.DateTime(localDateTime2, Locale.getDefault());
          localDateTime3.add(5, -1 * DCServiceUtil.a(paramReportCriteria));
          localDateTime3.set(11, 0);
          localDateTime3.set(12, 0);
          localDateTime3.set(13, 0);
          localDateTime3.set(14, 0);
          if (localDateTime1.before(localDateTime3))
          {
            localObject = "The date range for routing number " + str + " is too long - " + "the maximum range allowed is " + DCServiceUtil.a(paramReportCriteria) + " days.";
            BankingException localBankingException = new BankingException(444, (String)localObject);
            DebugLog.throwing("Banking.updateDates", localBankingException);
            throw localBankingException;
          }
        }
      }
    }
  }
  
  public Transfers getPagedPendingTransfers(SecureUser paramSecureUser, Accounts paramAccounts, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    throw new BankingException(2);
  }
  
  public Transfers getNextPendingTransfers(SecureUser paramSecureUser, Accounts paramAccounts, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    throw new BankingException(2);
  }
  
  public Transfers getPreviousPendingTransfers(SecureUser paramSecureUser, Accounts paramAccounts, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    throw new BankingException(2);
  }
  
  public Transfers getPagedCompletedTransfers(SecureUser paramSecureUser, Accounts paramAccounts, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    throw new BankingException(2);
  }
  
  public Transfers getNextCompletedTransfers(SecureUser paramSecureUser, Accounts paramAccounts, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    throw new BankingException(2);
  }
  
  public Transfers getPreviousCompletedTransfers(SecureUser paramSecureUser, Accounts paramAccounts, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    throw new BankingException(2);
  }
  
  public HashMap getTransactionTypes()
    throws BankingException
  {
    return getTransactionTypes(null, null);
  }
  
  public HashMap getTransactionTypes(Locale paramLocale, HashMap paramHashMap)
    throws BankingException
  {
    if (TransactionTypeCache.getTransactionTypeCache(paramLocale) == null) {
      throw new BankingException(5);
    }
    return TransactionTypeCache.getTransactionTypeCache(paramLocale);
  }
  
  public ArrayList getTransactionTypeDescriptions()
    throws BankingException
  {
    return getTransactionTypeDescriptions(null, null);
  }
  
  public ArrayList getTransactionTypeDescriptions(Locale paramLocale, HashMap paramHashMap)
    throws BankingException
  {
    HashMap localHashMap = getTransactionTypes(0, null);
    if ((localHashMap == null) || (localHashMap.isEmpty())) {
      return new ArrayList(0);
    }
    ArrayList localArrayList = new ArrayList(localHashMap.size());
    Iterator localIterator = localHashMap.values().iterator();
    TransactionTypeInfo localTransactionTypeInfo;
    if (paramLocale == null) {
      while (localIterator.hasNext())
      {
        localTransactionTypeInfo = (TransactionTypeInfo)localIterator.next();
        localArrayList.add(localTransactionTypeInfo.getDescription());
      }
    }
    while (localIterator.hasNext())
    {
      localTransactionTypeInfo = (TransactionTypeInfo)localIterator.next();
      localArrayList.add(localTransactionTypeInfo.getDescription(paramLocale));
    }
    return localArrayList;
  }
  
  public boolean signOn(SecureUser paramSecureUser, String paramString1, String paramString2)
    throws CSILException
  {
    return true;
  }
  
  public RecTransfer getRecTransferByID(SecureUser paramSecureUser, String paramString, Accounts paramAccounts, HashMap paramHashMap)
    throws CSILException
  {
    throw new CSILException(19003);
  }
  
  public RecTransfer getRecTransferByID(SecureUser paramSecureUser, Transfer paramTransfer, HashMap paramHashMap)
    throws BankingException
  {
    throw new BankingException(19003);
  }
  
  public Transfer getTransferByID(SecureUser paramSecureUser, Transfer paramTransfer, HashMap paramHashMap)
    throws BankingException
  {
    throw new BankingException(19003);
  }
  
  public Transfers getTransferTemplates(SecureUser paramSecureUser, Transfer paramTransfer, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    throw new BankingException(19003);
  }
  
  private void a(ReportCriteria paramReportCriteria, Locale paramLocale)
  {
    Properties localProperties = paramReportCriteria.getSearchCriteria();
    String str1 = (String)paramReportCriteria.getSearchCriteria().get("Accounts");
    if (str1 == null) {
      str1 = "AllAccounts";
    }
    if (DCServiceUtil.accountCriterionHasAllAccounts(str1)) {
      paramReportCriteria.setDisplayValue("Accounts", ReportConsts.getText(10024, paramLocale));
    } else {
      try
      {
        StringTokenizer localStringTokenizer = new StringTokenizer(str1, ",");
        int i = localStringTokenizer.countTokens();
        localObject2 = new StringBuffer();
        for (int j = 0; j < i; j++)
        {
          String str3 = localStringTokenizer.nextToken();
          if (j > 0) {
            ((StringBuffer)localObject2).append("&&");
          }
          localObject5 = DCServiceUtil.getAccountFromAccountCriterion(str3);
          if ((((Account)localObject5).getRoutingNum() == null) || (((Account)localObject5).getRoutingNum().length() <= 0))
          {
            try
            {
              ((StringBuffer)localObject2).append(AccountUtil.buildAccountDisplayText(((Account)localObject5).getID(), paramLocale));
            }
            catch (UtilException localUtilException1)
            {
              ((StringBuffer)localObject2).append(((Account)localObject5).getID());
            }
          }
          else
          {
            ((StringBuffer)localObject2).append(((Account)localObject5).getRoutingNum());
            ((StringBuffer)localObject2).append(" ");
            ((StringBuffer)localObject2).append(":");
            ((StringBuffer)localObject2).append(" ");
            try
            {
              ((StringBuffer)localObject2).append(AccountUtil.buildAccountDisplayText(((Account)localObject5).getID(), paramLocale));
            }
            catch (UtilException localUtilException2)
            {
              ((StringBuffer)localObject2).append(((Account)localObject5).getID());
            }
          }
          if ((((Account)localObject5).getNickName() != null) && (((Account)localObject5).getNickName().length() > 0))
          {
            ((StringBuffer)localObject2).append(" - ");
            ((StringBuffer)localObject2).append(((Account)localObject5).getNickName());
          }
          ((StringBuffer)localObject2).append(" - ");
          ((StringBuffer)localObject2).append(((Account)localObject5).getCurrencyCode());
        }
        paramReportCriteria.setDisplayValue("Accounts", ((StringBuffer)localObject2).toString());
      }
      catch (DCException localDCException) {}
    }
    String str2 = localProperties.getProperty("TransactionType");
    if (str2 != null)
    {
      localObject1 = new StringTokenizer(str2, ",", false);
      localObject2 = null;
      localObject3 = null;
      int k = 0;
      while (((StringTokenizer)localObject1).hasMoreTokens())
      {
        localObject5 = ((StringTokenizer)localObject1).nextToken().trim();
        if (!"------------------------------".equals(localObject5))
        {
          if ("AllTransactionTypes".equals(localObject5))
          {
            localObject2 = ReportConsts.getText(10025, paramLocale);
            break;
          }
          if ((k == 0) && (localObject3 == null)) {
            try
            {
              localObject3 = getTransactionTypes(paramLocale, new HashMap());
            }
            catch (BankingException localBankingException)
            {
              k = 1;
            }
          }
          if (k == 0)
          {
            String str4;
            if (((String)localObject5).startsWith("CustTransGrp_")) {
              str4 = ((String)localObject5).substring("CustTransGrp_".length());
            } else if (((String)localObject5).equals("AllCreditTransactions")) {
              str4 = ReportConsts.getText(10500, paramLocale);
            } else if (((String)localObject5).equals("AllDebitTransactions")) {
              str4 = ReportConsts.getText(10501, paramLocale);
            } else {
              str4 = (String)((HashMap)localObject3).get(new Integer((String)localObject5));
            }
            if (str4 != null)
            {
              if (localObject2 == null) {
                localObject2 = str4;
              } else {
                localObject2 = (String)localObject2 + ", " + str4;
              }
            }
            else if (localObject2 == null) {
              localObject2 = localObject5;
            } else {
              localObject2 = (String)localObject2 + ", " + (String)localObject5;
            }
          }
        }
      }
      if (localObject2 != null) {
        paramReportCriteria.setDisplayValue("TransactionType", (String)localObject2);
      }
    }
    Object localObject1 = localProperties.getProperty("TransactionSubType");
    if (localObject1 != null)
    {
      localObject2 = new StringTokenizer((String)localObject1, ",", false);
      localObject3 = null;
      while (((StringTokenizer)localObject2).hasMoreTokens())
      {
        localObject4 = ((StringTokenizer)localObject2).nextToken().trim();
        if ("AllTransactionSubtypes".equals(localObject4))
        {
          localObject3 = ReportConsts.getText(10502, paramLocale);
          break;
        }
        localObject5 = null;
        try
        {
          int m = Integer.parseInt((String)localObject4);
          BAITypeCodeInfo localBAITypeCodeInfo1 = DataConsolidator.getBAITypeCodeInfo(m);
          localObject5 = localBAITypeCodeInfo1.getCode() + " - " + localBAITypeCodeInfo1.getDescription(paramLocale);
        }
        catch (Exception localException1)
        {
          localObject5 = localObject4;
        }
        if (localObject3 == null) {
          localObject3 = localObject5;
        } else {
          localObject3 = (String)localObject3 + ", " + (String)localObject5;
        }
      }
      if (localObject3 != null) {
        paramReportCriteria.setDisplayValue("TransactionSubType", (String)localObject3);
      }
    }
    Object localObject2 = localProperties.getProperty("BAISummaryCode");
    Object localObject6;
    if (localObject2 != null)
    {
      localObject3 = new StringTokenizer((String)localObject2, ",", false);
      localObject4 = null;
      while (((StringTokenizer)localObject3).hasMoreTokens())
      {
        localObject5 = ((StringTokenizer)localObject3).nextToken().trim();
        if ("AllSummaryCodes".equals(localObject5))
        {
          localObject4 = ReportConsts.getText(10503, paramLocale);
          break;
        }
        localObject6 = null;
        try
        {
          int n = Integer.parseInt((String)localObject5);
          BAITypeCodeInfo localBAITypeCodeInfo2 = DataConsolidator.getBAITypeCodeInfo(n);
          localObject6 = localBAITypeCodeInfo2.getCode() + " - " + localBAITypeCodeInfo2.getDescription(paramLocale);
        }
        catch (Exception localException2)
        {
          localObject6 = localObject5;
        }
        if (localObject4 == null) {
          localObject4 = localObject6;
        } else {
          localObject4 = (String)localObject4 + ", " + (String)localObject6;
        }
      }
      if (localObject4 != null) {
        paramReportCriteria.setDisplayValue("BAISummaryCode", (String)localObject4);
      }
    }
    Object localObject3 = localProperties.getProperty("DataClassification");
    if (localObject3 != null) {
      if ("I".equals(localObject3)) {
        paramReportCriteria.setDisplayValue("DataClassification", ReportConsts.getText(10027, paramLocale));
      } else if ("P".equals(localObject3)) {
        paramReportCriteria.setDisplayValue("DataClassification", ReportConsts.getText(10026, paramLocale));
      }
    }
    Object localObject4 = localProperties.getProperty("ZBA Display");
    if ((localObject4 != null) && (((String)localObject4).length() > 0))
    {
      localObject5 = null;
      if (((String)localObject4).equalsIgnoreCase("N")) {
        localObject5 = ReportConsts.getText(10410, paramLocale);
      } else if (((String)localObject4).equalsIgnoreCase("C")) {
        localObject5 = ReportConsts.getText(10411, paramLocale);
      } else if (((String)localObject4).equalsIgnoreCase("D")) {
        localObject5 = ReportConsts.getText(10412, paramLocale);
      } else if (((String)localObject4).equalsIgnoreCase("B")) {
        localObject5 = ReportConsts.getText(10413, paramLocale);
      } else {
        localObject5 = ReportConsts.getText(10413, paramLocale);
      }
      paramReportCriteria.setDisplayValue("ZBA Display", (String)localObject5);
    }
    Object localObject5 = localProperties.getProperty("DisplayZBATransactions");
    if ((localObject5 != null) && (((String)localObject5).length() > 0))
    {
      localObject6 = null;
      if (((String)localObject5).equals("MasterAndSubAccounts")) {
        localObject6 = ReportConsts.getText(10416, paramLocale);
      } else if (((String)localObject5).equals("MasterAccounts")) {
        localObject6 = ReportConsts.getText(10417, paramLocale);
      } else if (((String)localObject5).equals("SubAccounts")) {
        localObject6 = ReportConsts.getText(10418, paramLocale);
      } else if (((String)localObject5).equals("None")) {
        localObject6 = ReportConsts.getText(10419, paramLocale);
      }
      if (localObject6 != null) {
        paramReportCriteria.setDisplayValue("DisplayZBATransactions", (String)localObject6);
      }
    }
    boolean bool = new Boolean(localProperties.getProperty("SupressSubAccounts")).booleanValue();
    if (bool) {
      paramReportCriteria.setDisplayValue("SupressSubAccounts", ReportConsts.getText(10420, paramLocale));
    } else {
      paramReportCriteria.setDisplayValue("SupressSubAccounts", ReportConsts.getText(10421, paramLocale));
    }
    paramReportCriteria.setHiddenSearchCriterion("SortByLocation", true);
    if (localProperties.containsKey("Show Prev-day Opening Ledger"))
    {
      String str5 = localProperties.getProperty("Show Prev-day Opening Ledger");
      if (str5.equals("N")) {
        paramReportCriteria.setDisplayValue("Show Prev-day Opening Ledger", ReportConsts.getText(10084, paramLocale));
      } else if (str5.equals("Y")) {
        paramReportCriteria.setDisplayValue("Show Prev-day Opening Ledger", ReportConsts.getText(10083, paramLocale));
      }
    }
  }
  
  private static void a(CSILException paramCSILException)
    throws BankingException
  {
    if (paramCSILException.getCode() == -1009) {
      throw new BankingException(paramCSILException.getServiceError(), paramCSILException.getMessage());
    }
    throw new BankingException(paramCSILException.getCode(), paramCSILException.getMessage());
  }
  
  private static ArrayList a(ReportCriteria paramReportCriteria)
    throws BankingException
  {
    Properties localProperties1 = paramReportCriteria.getReportOptions();
    String str1 = localProperties1.getProperty("REPORTTYPE");
    Properties localProperties2 = paramReportCriteria.getSearchCriteria();
    String str2 = (String)localProperties2.get("DataClassification");
    if (str2 == null) {
      throw new BankingException(21120, "The data classification cannot be null.");
    }
    ArrayList localArrayList = new ArrayList();
    if (str2.equals("I"))
    {
      if ((str1.equals("DepositDetail")) || (str1.equals("TransactionDetail")) || (str1.equals("CreditReport")) || (str1.equals("DebitReport")) || (str1.equals("TransactionDetail")) || (str1.equals("BalanceDetailOnlyReport")) || (str1.equals("GeneralLedgerReport")))
      {
        localArrayList.add("Information Reporting - Intra Day Detail");
      }
      else if (str1.equals("BalanceDetailReport"))
      {
        localArrayList.add("Information Reporting - Intra Day Summary");
        localArrayList.add("Information Reporting - Intra Day Detail");
      }
      else if ((str1.equals("BalanceSummaryReport")) || (str1.equals("CustomSummaryReport")) || (str1.equals("BalanceSheetReport")) || (str1.equals("BalanceSheetSummary")) || (str1.equals("CashFlowReport")) || (str1.equals("CashFlowForecastReport")))
      {
        localArrayList.add("Information Reporting - Intra Day Summary");
      }
    }
    else if (str2.equals("P"))
    {
      if ((str1.equals("DepositDetail")) || (str1.equals("TransactionDetail")) || (str1.equals("CreditReport")) || (str1.equals("DebitReport")) || (str1.equals("TransactionDetail")) || (str1.equals("BalanceDetailOnlyReport")) || (str1.equals("GeneralLedgerReport")))
      {
        localArrayList.add("Information Reporting - Previous Day Detail");
      }
      else if (str1.equals("BalanceDetailReport"))
      {
        localArrayList.add("Information Reporting - Previous Day Summary");
        localArrayList.add("Information Reporting - Previous Day Detail");
      }
      else if ((str1.equals("BalanceSummaryReport")) || (str1.equals("CustomSummaryReport")) || (str1.equals("BalanceSheetReport")) || (str1.equals("BalanceSheetSummary")) || (str1.equals("CashFlowReport")) || (str1.equals("CashFlowForecastReport")))
      {
        localArrayList.add("Information Reporting - Previous Day Summary");
      }
    }
    else if (str2.equals("A"))
    {
      if ((str1.equals("DepositDetail")) || (str1.equals("TransactionDetail")) || (str1.equals("CreditReport")) || (str1.equals("DebitReport")) || (str1.equals("TransactionDetail")) || (str1.equals("BalanceDetailOnlyReport")) || (str1.equals("GeneralLedgerReport")))
      {
        localArrayList.add("Information Reporting - Previous Day Detail");
        localArrayList.add("Information Reporting - Intra Day Detail");
      }
      else if (str1.equals("BalanceDetailReport"))
      {
        localArrayList.add("Information Reporting - Previous Day Summary");
        localArrayList.add("Information Reporting - Previous Day Detail");
        localArrayList.add("Information Reporting - Intra Day Summary");
        localArrayList.add("Information Reporting - Intra Day Detail");
      }
      else if ((str1.equals("BalanceSummaryReport")) || (str1.equals("CustomSummaryReport")) || (str1.equals("BalanceSheetReport")) || (str1.equals("BalanceSheetSummary")) || (str1.equals("CashFlowReport")) || (str1.equals("CashFlowForecastReport")))
      {
        localArrayList.add("Information Reporting - Previous Day Summary");
        localArrayList.add("Information Reporting - Intra Day Summary");
      }
    }
    else {
      throw new BankingException(21120, "The data classification '" + str2 + "' is not a valid data " + "classification.  Acceptable values include '" + "I" + "' and '" + "P" + "'.");
    }
    return localArrayList;
  }
  
  private static boolean a(SecureUser paramSecureUser, Account paramAccount, ReportCriteria paramReportCriteria)
    throws BankingException
  {
    ArrayList localArrayList = a(paramReportCriteria);
    return a(paramSecureUser, paramAccount, localArrayList);
  }
  
  private static boolean a(SecureUser paramSecureUser, Account paramAccount, ArrayList paramArrayList)
    throws BankingException
  {
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    MultiEntitlement localMultiEntitlement = new MultiEntitlement();
    if (paramArrayList.size() > 0) {
      localMultiEntitlement.setOperations((String[])paramArrayList.toArray(new String[paramArrayList.size()]));
    } else {
      localMultiEntitlement.setOperations(new String[] { null });
    }
    localMultiEntitlement.setObjects(new String[] { "Account" }, new String[] { EntitlementsUtil.getEntitlementObjectId(paramAccount) });
    try
    {
      return EntitlementsUtil.checkAccountEntitlement(localEntitlementGroupMember, localMultiEntitlement, paramSecureUser.getBusinessID()) == null;
    }
    catch (CSILException localCSILException)
    {
      a(localCSILException);
    }
    return true;
  }
  
  private static Accounts a(SecureUser paramSecureUser, Accounts paramAccounts, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws BankingException
  {
    ArrayList localArrayList = a(paramReportCriteria);
    return a(paramSecureUser, paramAccounts, localArrayList, paramHashMap);
  }
  
  private static Accounts a(SecureUser paramSecureUser, Accounts paramAccounts, ArrayList paramArrayList, HashMap paramHashMap)
    throws BankingException
  {
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    MultiEntitlement localMultiEntitlement = new MultiEntitlement();
    Accounts localAccounts = new Accounts(paramAccounts.getLocale());
    String[] arrayOfString1 = { "Account" };
    String[] arrayOfString2 = new String[1];
    String[] arrayOfString3 = new String[1];
    for (int i = 0; i < paramAccounts.size(); i++)
    {
      int j = 1;
      arrayOfString2[0] = EntitlementsUtil.getEntitlementObjectId((Account)paramAccounts.get(i));
      for (int k = 0; k < paramArrayList.size(); k++)
      {
        String str = (String)paramArrayList.get(k);
        localMultiEntitlement.setObjects(arrayOfString1, arrayOfString2);
        arrayOfString3[0] = str;
        localMultiEntitlement.setOperations(arrayOfString3);
        try
        {
          if (EntitlementsUtil.checkAccountEntitlement(localEntitlementGroupMember, localMultiEntitlement, paramSecureUser.getBusinessID()) != null)
          {
            j = 0;
            break;
          }
        }
        catch (CSILException localCSILException)
        {
          a(localCSILException);
        }
      }
      if (j != 0) {
        localAccounts.add((Account)paramAccounts.get(i));
      }
    }
    return localAccounts;
  }
  
  public int addAccount(Account paramAccount)
  {
    return 2;
  }
  
  public int modifyAccount(Account paramAccount)
  {
    return 2;
  }
  
  public int deleteAccount(Account paramAccount)
  {
    return 2;
  }
  
  public Date getEffectiveDate(SecureUser paramSecureUser, Transfer paramTransfer, HashMap paramHashMap)
    throws CSILException
  {
    throw new CSILException(19003);
  }
  
  public Transfers getPagedTransfers(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    return new Transfers();
  }
  
  public static void validatePagingContext(PagingContext paramPagingContext, HashMap paramHashMap)
    throws DCException
  {
    ReportCriteria localReportCriteria = (ReportCriteria)paramPagingContext.getMap().get("ReportCriteria");
    if (localReportCriteria == null)
    {
      localObject1 = "No report criteria object was found within the given paging context.";
      localObject2 = new DCException(1021, (String)localObject1);
      DebugLog.throwing((String)localObject1, (Throwable)localObject2);
      throw ((Throwable)localObject2);
    }
    Object localObject1 = localReportCriteria.getSearchCriteria();
    if (localObject1 == null)
    {
      localObject2 = "A search criteria object was not found within the given paging context's report criteria.  Without a search criteria object, a report cannot be run.";
      localObject3 = new DCException(314, (String)localObject2);
      DebugLog.throwing((String)localObject2, (Throwable)localObject3);
      throw ((Throwable)localObject3);
    }
    Object localObject2 = localReportCriteria.getSortCriteria();
    if (localObject2 == null)
    {
      localObject3 = "A sort criteria object was not found within the given paging context's report criteria.";
      localObject4 = new DCException(316, (String)localObject3);
      DebugLog.throwing((String)localObject3, (Throwable)localObject4);
      throw ((Throwable)localObject4);
    }
    Object localObject3 = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
    ((Properties)localObject1).getProperty("DataClassification");
    Object localObject4 = new ArrayList();
    ((ArrayList)localObject4).add("P");
    ((ArrayList)localObject4).add("I");
    ((ArrayList)localObject4).add("A");
    DCServiceUtil.a((String)localObject3, (ArrayList)localObject4);
    String str1 = ((Properties)localObject1).getProperty("StartDate");
    String str2 = ((Properties)localObject1).getProperty("EndDate");
    if ((str1 == null) || (str1.equals("")))
    {
      localObject5 = new DCException(320, "Missing start date.");
      throw ((Throwable)localObject5);
    }
    if ((str2 == null) || (str2.equals("")))
    {
      localObject5 = new DCException(321, "Missing end date.");
      throw ((Throwable)localObject5);
    }
    Object localObject5 = null;
    try
    {
      localObject5 = new com.ffusion.util.beans.DateTime(str1, localReportCriteria.getLocale(), "MM/dd/yyyy HH:mm:ss");
    }
    catch (InvalidDateTimeException localInvalidDateTimeException1)
    {
      throw new DCException(21112, "The start date is not in the correct format.");
    }
    com.ffusion.util.beans.DateTime localDateTime = null;
    try
    {
      localDateTime = new com.ffusion.util.beans.DateTime(str2, localReportCriteria.getLocale(), "MM/dd/yyyy HH:mm:ss");
    }
    catch (InvalidDateTimeException localInvalidDateTimeException2)
    {
      throw new DCException(21113, "The end date is not in the correct format.");
    }
    String str3 = ((Properties)localObject1).getProperty("Customer Reference Number (Min)");
    DCServiceUtil.a(str3);
    str3 = ((Properties)localObject1).getProperty("Customer Reference Number (Max)");
    DCServiceUtil.a(str3);
    String str4 = ((Properties)localObject1).getProperty("MinimumAmount");
    String str5 = ((Properties)localObject1).getProperty("MaximumAmount");
    DCServiceUtil.a(str4, str5);
  }
  
  public HashMap getTransactionTypes(int paramInt, HashMap paramHashMap)
    throws BankingException
  {
    try
    {
      return TransactionTypeCache.getTransactionTypes(paramInt, paramHashMap);
    }
    catch (FFIUtilException localFFIUtilException)
    {
      BankingException localBankingException = new BankingException(localFFIUtilException.getCode());
      throw localBankingException;
    }
  }
  
  public TransactionTypeInfo getTransactionTypeInfo(int paramInt, HashMap paramHashMap)
    throws BankingException
  {
    HashMap localHashMap = getTransactionTypes(0, paramHashMap);
    TransactionTypeInfo localTransactionTypeInfo = (TransactionTypeInfo)localHashMap.get(new Integer(paramInt));
    return localTransactionTypeInfo;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.dataconsolidator.Banking
 * JD-Core Version:    0.7.0.1
 */