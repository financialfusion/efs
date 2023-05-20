package com.ffusion.services.banksim2;

import com.ffusion.banksim.interfaces.BSDBParams;
import com.ffusion.banksim.interfaces.BSException;
import com.ffusion.banksim.proxy.BankSim;
import com.ffusion.beans.Contact;
import com.ffusion.beans.Currency;
import com.ffusion.beans.FundsTransaction;
import com.ffusion.beans.FundsTransactions;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.AccountHistories;
import com.ffusion.beans.accounts.AccountHistory;
import com.ffusion.beans.accounts.AccountSummaries;
import com.ffusion.beans.accounts.AccountSummary;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.accounts.AssetAcctSummary;
import com.ffusion.beans.accounts.CreditCardAcctSummary;
import com.ffusion.beans.accounts.DepositAcctSummary;
import com.ffusion.beans.accounts.ExtendedAccountSummaries;
import com.ffusion.beans.accounts.ExtendedAccountSummary;
import com.ffusion.beans.accounts.FixedDepositInstrument;
import com.ffusion.beans.accounts.FixedDepositInstruments;
import com.ffusion.beans.accounts.LoanAcctSummary;
import com.ffusion.beans.banking.RecTransfer;
import com.ffusion.beans.banking.RecTransfers;
import com.ffusion.beans.banking.Transaction;
import com.ffusion.beans.banking.TransactionTypeInfo;
import com.ffusion.beans.banking.Transactions;
import com.ffusion.beans.banking.Transfer;
import com.ffusion.beans.banking.TransferBatch;
import com.ffusion.beans.banking.Transfers;
import com.ffusion.beans.exttransfers.ExtTransferAccount;
import com.ffusion.beans.reporting.ReportColumn;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportDataDimensions;
import com.ffusion.beans.reporting.ReportDataItem;
import com.ffusion.beans.reporting.ReportDataItems;
import com.ffusion.beans.reporting.ReportHeading;
import com.ffusion.beans.reporting.ReportResult;
import com.ffusion.beans.reporting.ReportRow;
import com.ffusion.beans.reporting.ReportSortCriteria;
import com.ffusion.beans.reporting.ReportSortCriterion;
import com.ffusion.beans.user.User;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Limit;
import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.csil.core.Reporting;
import com.ffusion.ffs.bpw.BPWServices.BPWServices;
import com.ffusion.ffs.bpw.BPWServices.BPWServicesHome;
import com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo;
import com.ffusion.ffs.bpw.interfaces.PagingInfo;
import com.ffusion.ffs.bpw.interfaces.RecTransferInfo;
import com.ffusion.ffs.bpw.interfaces.SortCriterion;
import com.ffusion.ffs.bpw.interfaces.TransferBatchInfo;
import com.ffusion.ffs.bpw.interfaces.TransferInfo;
import com.ffusion.ffs.bpw.interfaces.ValueInfo;
import com.ffusion.reporting.IReportResult;
import com.ffusion.reporting.RptException;
import com.ffusion.services.Banking8;
import com.ffusion.services.BankingException;
import com.ffusion.util.ContextPool;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.FFIUtilException;
import com.ffusion.util.FilteredList;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.TransactionTypeCache;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.beans.PagingContext;
import com.ffusion.util.enums.UserAssignedAmount;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.logging.TrackingIDGenerator;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import javax.naming.Context;
import javax.rmi.PortableRemoteObject;

public class Banking
  extends Base
  implements Banking8, BankingDefines
{
  private String cc;
  private String b9;
  private String cf;
  private String ce;
  private String bY;
  private String b7;
  private String bV;
  private String b0;
  private User b1;
  public static final String FIRST_PAGING_INDEX = "FIRST_PAGING_INDEX";
  public static final String LAST_PAGING_INDEX = "LAST_PAGING_INDEX";
  private static final String b6 = "Statuses";
  private static final String bU = "transactionTypes.xml";
  private static final String bT = "BPWCallBackJNDIName";
  private String bZ = "BPWServices";
  private final int b3 = 10;
  private static final a[] b2 = { new a(266, "com.ffusion.util.beans.DateTime", 10), new a(243, "java.lang.String", 10), new a(245, "java.lang.String", 30), new a(246, "java.lang.String", 10), new a(271, "com.ffusion.beans.Currency", 10), new a(270, "com.ffusion.beans.Currency", 10), new a(272, "com.ffusion.beans.Currency", 10) };
  private static final a[] b8 = { new a(266, "com.ffusion.util.beans.DateTime", 10), new a(245, "java.lang.String", 30), new a(276, "com.ffusion.beans.Currency", 10), new a(253, "com.ffusion.util.beans.DateTime", 10), new a(277, "com.ffusion.beans.Currency", 10) };
  private static final a[] b5 = { new a(266, "com.ffusion.util.beans.DateTime", 10), new a(243, "java.lang.String", 10), new a(245, "java.lang.String", 30), new a(270, "com.ffusion.beans.Currency", 10), new a(271, "com.ffusion.beans.Currency", 10), new a(273, "com.ffusion.beans.Currency", 10) };
  private static final a[] cd = { new a(266, "com.ffusion.util.beans.DateTime", 10), new a(245, "java.lang.String", 10), new a(274, "com.ffusion.beans.Currency", 10), new a(275, "com.ffusion.beans.Currency", 10), new a(272, "com.ffusion.beans.Currency", 10) };
  private static final a[] ch = { new a(290, "com.ffusion.util.beans.DateTime", 12), new a(291, "java.lang.String", 30), new a(292, "java.lang.String", 13), new a(293, "java.lang.String", 8), new a(294, "com.ffusion.beans.Currency", 14), new a(295, "com.ffusion.beans.Currency", 14), new a(296, "com.ffusion.beans.Currency", 14) };
  private static final a[] b4 = { new a(null, "com.ffusion.util.beans.DateTime", 12), new a(null, "java.lang.String", 30), new a(null, "java.lang.String", 13), new a(null, "java.lang.String", 8), new a(null, "com.ffusion.beans.Currency", 14), new a(null, "com.ffusion.beans.Currency", 14), new a(null, "com.ffusion.beans.Currency", 14) };
  private static final a[] bX = { new a(290, "com.ffusion.util.beans.DateTime", 12), new a(291, "java.lang.String", 30), new a(292, "java.lang.String", 13), new a(293, "java.lang.String", 8), new a(294, "com.ffusion.beans.Currency", 14), new a(295, "com.ffusion.beans.Currency", 14) };
  private static final a[] cg = { new a(null, "com.ffusion.util.beans.DateTime", 12), new a(null, "java.lang.String", 30), new a(null, "java.lang.String", 13), new a(null, "java.lang.String", 8), new a(null, "com.ffusion.beans.Currency", 14), new a(null, "com.ffusion.beans.Currency", 14) };
  private static final a[] bW = { new a(null, "java.lang.String", 12), new a(null, "java.lang.String", 88) };
  private static final a[] ca = { new a(-1, "java.lang.String", 100) };
  private static final a[] cb = { new a("Account ID", "java.lang.String", 1), new a("Bank ID", "java.lang.String", 1), new a("Routing Number", "java.lang.String", 1), new a("Currency Code", "java.lang.String", 1), new a("ID", "java.lang.String", 1), new a("Type", "java.lang.Integer", 1), new a("Category", "java.lang.Integer", 1), new a("Description", "java.lang.String", 1), new a("Reference Number", "java.lang.String", 1), new a("Memo", "java.lang.String", 1), new a("Amount", "com.ffusion.beans.Currency", 1), new a("Running Balance", "com.ffusion.beans.Currency", 1), new a("Tracking ID", "java.lang.String", 1), new a("Transaction Index", "java.lang.Integer", 1), new a("Posting Date", "com.ffusion.util.beans.DateTime", 1), new a("Due Date", "com.ffusion.util.beans.DateTime", 1), new a("Fixed Deposit Rate", "java.lang.Float", 1), new a("Payee/Payor", "java.lang.String", 1), new a("Payor Num", "java.lang.String", 1), new a("Orig User", "java.lang.String", 1), new a("PO Num", "java.lang.String", 1), new a("Immediate Avalaible Amount", "com.ffusion.beans.Currency", 1), new a("One Day Available Amount", "com.ffusion.beans.Currency", 1), new a("More Than One Day Available Amount", "com.ffusion.beans.Currency", 1), new a("Bank Reference Number", "java.lang.String", 1), new a("Customer Reference Number", "java.lang.String", 1), new a("Processing Date", "com.ffusion.util.beans.DateTime", 1), new a("Instrument Number", "java.lang.String", 1), new a("Instrument Bank Name", "java.lang.String", 1), new a("Value Date", "com.ffusion.util.beans.DateTime", 1), new a("Sub Type", "java.lang.Integer", 1), new a("Date", "com.ffusion.util.beans.DateTime", 1), new a("Data Classification", "java.lang.String", 1) };
  
  public int initialize(String paramString)
  {
    int i = 0;
    BSDBParams localBSDBParams = null;
    if ((paramString == null) || (paramString.length() == 0)) {
      paramString = "banksim.xml";
    }
    i = initialize(paramString, new b());
    try
    {
      TransactionTypeCache.initTransactionTypesCache("transactionTypes.xml");
    }
    catch (Exception localException)
    {
      StringWriter localStringWriter = new StringWriter();
      PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
      localException.printStackTrace(localPrintWriter);
      DebugLog.log(Level.SEVERE, localStringWriter.toString());
      localException.printStackTrace();
    }
    if (BankSim.isInitialized()) {
      return i;
    }
    try
    {
      localBSDBParams = BankSim.getBSDBParams(this.cf, this.bY, this.cc, this.bV, false);
      localBSDBParams.setDBDriver(this.b7);
      int j = Integer.valueOf(this.b0).intValue();
      BankSim.initialize(localBSDBParams, j);
    }
    catch (BSException localBSException)
    {
      i = 1;
      localBSException.printStackTrace();
    }
    return i;
  }
  
  public boolean signOn(SecureUser paramSecureUser, String paramString1, String paramString2)
    throws CSILException
  {
    boolean bool = true;
    int i = 0;
    try
    {
      this.b1 = BankSim.signOn(paramString1, paramString2);
      this.userID = paramString1;
      setUserID(paramString1);
      this.password = paramString2;
      this.secureUser.set(paramSecureUser);
    }
    catch (BSException localBSException)
    {
      bool = false;
      switch (localBSException.getErrorCode())
      {
      case 1006: 
        i = 101;
      }
    }
    i = 100;
    break label105;
    i = 1;
    throw new CSILException("Banking.signOn", i);
    label105:
    return bool;
  }
  
  public int signOn(String paramString1, String paramString2)
  {
    int i = 0;
    try
    {
      this.b1 = BankSim.signOn(paramString1, paramString2);
      this.userID = paramString1;
      setUserID(paramString1);
      this.password = paramString2;
    }
    catch (BSException localBSException)
    {
      switch (localBSException.getErrorCode())
      {
      case 1006: 
        i = 101;
      }
    }
    return 100;
    i = 1;
    return i;
  }
  
  public int changePIN(String paramString1, String paramString2)
  {
    int i = 0;
    try
    {
      BankSim.setPassword(this.b1, paramString2);
    }
    catch (BSException localBSException)
    {
      i = 1;
    }
    return i;
  }
  
  public String getSettings()
  {
    return null;
  }
  
  protected BPWServices getBPWHandler()
  {
    BPWServices localBPWServices = null;
    ContextPool localContextPool = null;
    Context localContext = null;
    for (int i = 0; i < this.provider_url_list.size(); i++)
    {
      String str = (String)this.provider_url_list.get(i);
      try
      {
        if (debugService)
        {
          DebugLog.log(Level.INFO, "##################### Ping BPW Server ################");
          DebugLog.log(Level.INFO, "provider_url = " + str);
        }
        if (!ping(str)) {
          continue;
        }
      }
      catch (CSILException localCSILException)
      {
        continue;
      }
      try
      {
        DebugLog.log(Level.INFO, "##################### Getting BPW Handler ################");
        DebugLog.log(Level.INFO, "provider_url = " + str);
        localContextPool = getContextPool(str);
        localContext = localContextPool.getContext();
        Object localObject1 = localContext.lookup(this.bZ);
        localObject2 = (BPWServicesHome)PortableRemoteObject.narrow(localObject1, BPWServicesHome.class);
        localBPWServices = ((BPWServicesHome)localObject2).create();
        localContextPool.returnContext(localContext);
        return localBPWServices;
      }
      catch (Throwable localThrowable)
      {
        Object localObject2;
        DebugLog.throwing("Couldn't get handler in BankSim service for " + str, localThrowable);
        if ((localContext != null) && (localContextPool != null)) {
          try
          {
            localContext = localContextPool.refreshContext(localContext);
            localObject2 = localContext.lookup(this.bZ);
            BPWServicesHome localBPWServicesHome = (BPWServicesHome)PortableRemoteObject.narrow(localObject2, BPWServicesHome.class);
            localBPWServices = localBPWServicesHome.create();
            localContextPool.returnContext(localContext);
            return localBPWServices;
          }
          catch (Exception localException)
          {
            DebugLog.throwing("Couldn't refresh the contexts for " + str, localException);
            if ((localContextPool != null) && (localContext != null)) {
              localContextPool.returnContext(localContext);
            }
          }
        }
      }
    }
    return null;
  }
  
  protected static void removeBPWHandler(BPWServices paramBPWServices)
  {
    if (paramBPWServices != null) {
      try
      {
        if (debugService) {
          DebugLog.log(Level.INFO, "##################### Removing BPW Handler ################");
        }
        paramBPWServices.remove();
      }
      catch (Throwable localThrowable)
      {
        DebugLog.throwing("Error removing bpw handler", localThrowable);
      }
    }
  }
  
  public int addAccount(Account paramAccount)
  {
    return 0;
  }
  
  public int modifyAccount(Account paramAccount)
  {
    int i = 0;
    try
    {
      BankSim.updateAccount(paramAccount);
    }
    catch (BSException localBSException)
    {
      switch (localBSException.getErrorCode())
      {
      case 1011: 
        i = 1011;
      }
    }
    i = 1;
    return i;
  }
  
  public int deleteAccount(Account paramAccount)
  {
    return 0;
  }
  
  public int getAccount(Account paramAccount)
  {
    return 0;
  }
  
  public int getAccounts(Accounts paramAccounts)
  {
    int i = 0;
    Enumeration localEnumeration = null;
    try
    {
      localEnumeration = BankSim.getAccounts(this.b1);
      paramAccounts.clear();
      while (localEnumeration.hasMoreElements())
      {
        Account localAccount = (Account)localEnumeration.nextElement();
        if (paramAccounts.getSecureUser() != null) {
          localAccount.setLocale(paramAccounts.getSecureUser().getLocale());
        }
        paramAccounts.add(localAccount);
      }
    }
    catch (BSException localBSException)
    {
      i = 1;
    }
    return i;
  }
  
  public int getTransactions(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2)
  {
    int i = 0;
    Transactions localTransactions = paramAccount.getTransactions();
    if (localTransactions != null)
    {
      localTransactions.clear();
    }
    else
    {
      paramAccount.setTransactions(new Transactions(paramAccount.getLocale()));
      localTransactions = paramAccount.getTransactions();
    }
    Enumeration localEnumeration = null;
    try
    {
      localEnumeration = BankSim.getTransactions(paramAccount, paramCalendar1, paramCalendar2);
      while (localEnumeration.hasMoreElements()) {
        localTransactions.add(localEnumeration.nextElement());
      }
    }
    catch (BSException localBSException)
    {
      switch (localBSException.getErrorCode())
      {
      case 1011: 
        i = 1011;
      }
    }
    i = 1;
    return i;
  }
  
  public int getTransactions(Account paramAccount)
  {
    return getTransactions(paramAccount, null, null);
  }
  
  public int getTransfers(Accounts paramAccounts, Transfers paramTransfers)
  {
    PagingInfo localPagingInfo = new PagingInfo();
    PagingContext localPagingContext = new PagingContext(null, null);
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    paramAccounts.setFilter("TransferFrom");
    if (paramAccounts.size() == 0) {
      return 0;
    }
    StringBuffer localStringBuffer = new StringBuffer();
    Iterator localIterator = paramAccounts.iterator();
    while (localIterator.hasNext())
    {
      localObject1 = (Account)localIterator.next();
      localStringBuffer.append(((Account)localObject1).getNumber()).append(",");
    }
    localHashMap2.put("AcctId", localStringBuffer.toString());
    localHashMap1.put("PAGE_SIZE", "250");
    Object localObject1 = GregorianCalendar.getInstance();
    ((Calendar)localObject1).add(5, -30);
    localPagingContext.setStartDate((Calendar)localObject1);
    localObject1 = GregorianCalendar.getInstance();
    ((Calendar)localObject1).add(5, 30);
    localPagingContext.setEndDate((Calendar)localObject1);
    String[] arrayOfString1 = { "ITOI", "INTERNAL", "ITOE", "ETOI" };
    localHashMap2.put("Dests", arrayOfString1);
    String[] arrayOfString2 = { "Current", "Repetitive", "Recurring" };
    localHashMap2.put("TransType", arrayOfString2);
    String[] arrayOfString3 = { "WILLPROCESSON", "BATCH_INPROCESS", "IMMED_INPROCESS", "INPROCESS", "FUNDSPROCESSED", "APPROVAL_PENDING", "PROCESSEDON", "POSTEDON" };
    localHashMap2.put("StatusList", arrayOfString3);
    localHashMap1.put("SEARCH_CRITERIA", localHashMap2);
    localPagingContext.setMap(localHashMap1);
    localPagingInfo.setPagingContext(localPagingContext);
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null)
    {
      logError(1020);
      return this.lastError;
    }
    try
    {
      localPagingInfo = localBPWServices.getPagedTransfer(localPagingInfo);
    }
    catch (Exception localException)
    {
      logError(1033);
      int i = this.lastError;
      return i;
    }
    finally
    {
      removeBPWHandler(localBPWServices);
    }
    ArrayList localArrayList = localPagingInfo.getPagingResult();
    if (localArrayList != null)
    {
      HashMap localHashMap3 = new HashMap();
      SecureUser localSecureUser = new SecureUser(paramTransfers.getLocale());
      HashMap localHashMap4 = new HashMap();
      localHashMap4.put("ACCOUNTS", paramAccounts);
      localIterator = localArrayList.iterator();
      while (localIterator.hasNext())
      {
        TransferInfo localTransferInfo = (TransferInfo)localIterator.next();
        Transfer localTransfer1 = (Transfer)paramTransfers.create();
        localTransfer1.setDisplayRestrictedAccount(this.restrictAccounts);
        localTransfer1.setTransferInfo(localTransferInfo, paramAccounts);
        String str = localTransferInfo.getSourceRecSrvrTId();
        if (str != null)
        {
          localTransfer1.setType(2);
          RecTransfer localRecTransfer = (RecTransfer)localHashMap3.get(str);
          if (localRecTransfer == null)
          {
            Transfer localTransfer2 = new Transfer();
            localTransfer2.setID(str);
            localTransfer2.setTransferDestination(localTransfer1.getTransferDestination());
            try
            {
              localRecTransfer = getRecTransferByID(localSecureUser, localTransfer2, localHashMap4);
              if (localRecTransfer != null) {
                localHashMap3.put(str, localRecTransfer);
              }
            }
            catch (BankingException localBankingException)
            {
              DebugLog.log(Level.WARNING, "Unable to get rectransfer by id " + str + ": " + localBankingException);
            }
          }
          if (localRecTransfer != null)
          {
            localTransfer1.put("Frequency", localRecTransfer.getFrequency());
            localTransfer1.put("NumberTransfers", localRecTransfer.getNumberTransfers());
          }
        }
      }
    }
    return 0;
  }
  
  public int getRecTransfers(Accounts paramAccounts, RecTransfers paramRecTransfers)
  {
    PagingInfo localPagingInfo = new PagingInfo();
    PagingContext localPagingContext = new PagingContext(null, null);
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    paramAccounts.setFilter("TransferFrom");
    if (paramAccounts.size() == 0) {
      return 0;
    }
    StringBuffer localStringBuffer = new StringBuffer();
    Iterator localIterator = paramAccounts.iterator();
    while (localIterator.hasNext())
    {
      localObject1 = (Account)localIterator.next();
      localStringBuffer.append(((Account)localObject1).getNumber()).append(",");
    }
    localHashMap2.put("AcctId", localStringBuffer.toString());
    localHashMap1.put("PAGE_SIZE", "250");
    Object localObject1 = GregorianCalendar.getInstance();
    ((Calendar)localObject1).add(5, -30);
    localPagingContext.setStartDate((Calendar)localObject1);
    localObject1 = GregorianCalendar.getInstance();
    ((Calendar)localObject1).add(5, 30);
    localPagingContext.setEndDate((Calendar)localObject1);
    String[] arrayOfString1 = { "ITOI", "INTERNAL", "ITOE", "ETOI" };
    localHashMap2.put("Dests", arrayOfString1);
    String[] arrayOfString2 = { "Recmodel" };
    localHashMap2.put("TransType", arrayOfString2);
    String[] arrayOfString3 = { "WILLPROCESSON", "BATCH_INPROCESS", "IMMED_INPROCESS", "INPROCESS", "FUNDSPROCESSED", "APPROVAL_PENDING", "PROCESSEDON", "POSTEDON" };
    localHashMap2.put("StatusList", arrayOfString3);
    localHashMap1.put("SEARCH_CRITERIA", localHashMap2);
    localPagingContext.setMap(localHashMap1);
    localPagingInfo.setPagingContext(localPagingContext);
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null)
    {
      logError(1020);
      return this.lastError;
    }
    try
    {
      localPagingInfo = localBPWServices.getPagedTransfer(localPagingInfo);
    }
    catch (Exception localException)
    {
      logError(1033);
      int i = this.lastError;
      return i;
    }
    finally
    {
      removeBPWHandler(localBPWServices);
    }
    ArrayList localArrayList = localPagingInfo.getPagingResult();
    if (localArrayList != null)
    {
      localIterator = localArrayList.iterator();
      while (localIterator.hasNext())
      {
        RecTransferInfo localRecTransferInfo = (RecTransferInfo)localIterator.next();
        RecTransfer localRecTransfer = (RecTransfer)paramRecTransfers.create();
        localRecTransfer.setDisplayRestrictedAccount(this.restrictAccounts);
        localRecTransfer.setRecTransferInfo(localRecTransferInfo, paramAccounts);
        localRecTransfer.setFrequency(localRecTransferInfo.getFrequency());
      }
    }
    return 0;
  }
  
  public int sendTransfer(Account paramAccount1, Account paramAccount2, Transfer paramTransfer, HashMap paramHashMap)
  {
    return sendTransfer(paramAccount1, paramAccount2, paramTransfer);
  }
  
  public int sendTransfer(Account paramAccount1, Account paramAccount2, Transfer paramTransfer)
  {
    int i = 0;
    paramTransfer.setID(null);
    if (paramTransfer.getTransferDestination() == null) {
      paramTransfer.setTransferDestination("INTERNAL");
    }
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      return 31023;
    }
    TransferInfo localTransferInfo = paramTransfer.getTransferInfo("add");
    try
    {
      localTransferInfo.setTrnId(TrackingIDGenerator.GetNextID());
      localTransferInfo.setDateToPost(null);
      DebugLog.log("*** BPW.AddTransfer: Adding TransferInfo = [" + localTransferInfo.toString() + "]");
      populateOBOAgentInfo(this.secureUser, localTransferInfo);
      localTransferInfo = localBPWServices.addTransfer(localTransferInfo);
    }
    catch (Exception localException)
    {
      DebugLog.log("Exception occured during adding a Transfer:");
      localException.printStackTrace();
      int j = 1030;
      return j;
    }
    finally
    {
      removeBPWHandler(localBPWServices);
    }
    i = a(paramTransfer, localTransferInfo);
    if (i != 0)
    {
      DebugLog.log("Error occurred when adding a Transfer:");
      DebugLog.log("*** BPW ErrorCode: " + localTransferInfo.getStatusCode());
      DebugLog.log("*** BPW ErrorMsg: " + localTransferInfo.getStatusMsg());
      return jdMethod_if(localTransferInfo.getStatusCode(), 1030);
    }
    paramTransfer.setTransferInfo(localTransferInfo, null);
    paramTransfer.setFromAccount(paramAccount1);
    paramTransfer.setToAccount(paramAccount2);
    return i;
  }
  
  public int cancelTransfer(Transfer paramTransfer)
  {
    int i = 0;
    if (paramTransfer.getTransferDestination() == null) {
      paramTransfer.setTransferDestination("INTERNAL");
    }
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      return 31023;
    }
    TransferInfo localTransferInfo = paramTransfer.getTransferInfo("del");
    try
    {
      localTransferInfo.setTrnId(TrackingIDGenerator.GetNextID());
      populateOBOAgentInfo(this.secureUser, localTransferInfo);
      localTransferInfo = localBPWServices.cancTransfer(localTransferInfo);
    }
    catch (Exception localException)
    {
      DebugLog.log("Exception occured during deleting an External Transfer:");
      localException.printStackTrace();
      int j = 1032;
      return j;
    }
    finally
    {
      removeBPWHandler(localBPWServices);
    }
    if (localTransferInfo.getStatusCode() != 0)
    {
      DebugLog.log("Error occurred when deleting an External Transfer:");
      DebugLog.log("*** BPW ErrorCode: " + localTransferInfo.getStatusCode());
      DebugLog.log("*** BPW ErrorMsg: " + localTransferInfo.getStatusMsg());
      return jdMethod_if(localTransferInfo.getStatusCode(), 1032);
    }
    return i;
  }
  
  public int modifyTransfer(Account paramAccount1, Account paramAccount2, Transfer paramTransfer, HashMap paramHashMap)
  {
    return modifyTransfer(paramAccount1, paramAccount2, paramTransfer);
  }
  
  public int modifyTransfer(Account paramAccount1, Account paramAccount2, Transfer paramTransfer)
  {
    int i = 0;
    if (paramTransfer.getTransferDestination() == null) {
      paramTransfer.setTransferDestination("INTERNAL");
    }
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      return 31023;
    }
    TransferInfo localTransferInfo = paramTransfer.getTransferInfo("mod");
    try
    {
      localTransferInfo.setTrnId(TrackingIDGenerator.GetNextID());
      localTransferInfo.setDateToPost(null);
      DebugLog.log("*** BPW.ModTransfer: Modifying TransferInfo = [" + localTransferInfo.toString() + "]");
      populateOBOAgentInfo(this.secureUser, localTransferInfo);
      localTransferInfo = localBPWServices.modTransfer(localTransferInfo);
    }
    catch (Exception localException)
    {
      DebugLog.log("Exception occured during modifying an External Transfer:");
      localException.printStackTrace();
      int j = 1031;
      return j;
    }
    finally
    {
      removeBPWHandler(localBPWServices);
    }
    i = a(paramTransfer, localTransferInfo);
    if (i != 0)
    {
      DebugLog.log("Error occurred when modifying an External Transfer:");
      DebugLog.log("*** BPW ErrorCode: " + localTransferInfo.getStatusCode());
      DebugLog.log("*** BPW ErrorMsg: " + localTransferInfo.getStatusMsg());
      return jdMethod_if(localTransferInfo.getStatusCode(), 1031);
    }
    paramTransfer.setTransferInfo(localTransferInfo, null);
    paramTransfer.setFromAccount(paramAccount1);
    paramTransfer.setToAccount(paramAccount2);
    return i;
  }
  
  public int sendRecTransfer(Account paramAccount1, Account paramAccount2, RecTransfer paramRecTransfer)
  {
    int i = 0;
    if (paramRecTransfer.getTransferDestination() == null) {
      paramRecTransfer.setTransferDestination("INTERNAL");
    }
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      return 31023;
    }
    RecTransferInfo localRecTransferInfo = paramRecTransfer.getRecTransferInfo("add");
    try
    {
      localRecTransferInfo.setFrequency(getBPWFrequencyString(paramRecTransfer.getFrequencyValue()));
      localRecTransferInfo.setTrnId(TrackingIDGenerator.GetNextID());
      DebugLog.log("*** BPW.AddRecTransfer: Adding RecTransferInfo = [" + localRecTransferInfo.toString() + "]");
      populateOBOAgentInfo(this.secureUser, localRecTransferInfo);
      localRecTransferInfo = localBPWServices.addRecTransfer(localRecTransferInfo);
    }
    catch (Exception localException)
    {
      DebugLog.log("Exception occured during adding a recurring External Transfer:");
      localException.printStackTrace();
      int j = 1030;
      return j;
    }
    finally
    {
      removeBPWHandler(localBPWServices);
    }
    i = a(paramRecTransfer, localRecTransferInfo);
    if (i != 0)
    {
      DebugLog.log("Error occurred when adding a recurring External Transfer:");
      DebugLog.log("*** BPW ErrorCode: " + localRecTransferInfo.getStatusCode());
      DebugLog.log("*** BPW ErrorMsg: " + localRecTransferInfo.getStatusMsg());
      return jdMethod_if(localRecTransferInfo.getStatusCode(), 1030);
    }
    paramRecTransfer.setRecTransferInfo(localRecTransferInfo, null);
    paramRecTransfer.setFrequency(getFrequency(localRecTransferInfo.getFrequency()));
    paramRecTransfer.setFromAccount(paramAccount1);
    paramRecTransfer.setToAccount(paramAccount2);
    return i;
  }
  
  public static String getBPWFrequencyString(int paramInt)
  {
    if (paramInt == 9) {
      return "ANNUALLY";
    }
    if (paramInt == 6) {
      return "BIMONTHLY";
    }
    if (paramInt == 2) {
      return "BIWEEKLY";
    }
    if (paramInt == 5) {
      return "FOURWEEKS";
    }
    if (paramInt == 4) {
      return "MONTHLY";
    }
    if (paramInt == 7) {
      return "QUARTERLY";
    }
    if (paramInt == 8) {
      return "SEMIANNUALLY";
    }
    if (paramInt == 3) {
      return "TWICEMONTHLY";
    }
    if (paramInt == 1) {
      return "WEEKLY";
    }
    return "WEEKLY";
  }
  
  public int cancelRecTransfer(RecTransfer paramRecTransfer)
  {
    int i = 0;
    if (paramRecTransfer.getTransferDestination() == null) {
      paramRecTransfer.setTransferDestination("INTERNAL");
    }
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      return 31023;
    }
    RecTransferInfo localRecTransferInfo = paramRecTransfer.getRecTransferInfo("del");
    try
    {
      localRecTransferInfo.setTrnId(TrackingIDGenerator.GetNextID());
      populateOBOAgentInfo(this.secureUser, localRecTransferInfo);
      localRecTransferInfo = localBPWServices.cancRecTransfer(localRecTransferInfo);
    }
    catch (Exception localException)
    {
      DebugLog.log("Exception occured during deleting an External Transfer:");
      localException.printStackTrace();
      int j = 1032;
      return j;
    }
    finally
    {
      removeBPWHandler(localBPWServices);
    }
    if (localRecTransferInfo.getStatusCode() != 0)
    {
      DebugLog.log("Error occurred when deleting an External Transfer:");
      DebugLog.log("*** BPW ErrorCode: " + localRecTransferInfo.getStatusCode());
      DebugLog.log("*** BPW ErrorMsg: " + localRecTransferInfo.getStatusMsg());
      return jdMethod_if(localRecTransferInfo.getStatusCode(), 1032);
    }
    return i;
  }
  
  public int modifyRecTransfer(Account paramAccount1, Account paramAccount2, RecTransfer paramRecTransfer)
  {
    int i = 0;
    if (paramRecTransfer.getTransferDestination() == null) {
      paramRecTransfer.setTransferDestination("INTERNAL");
    }
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      return 31023;
    }
    RecTransferInfo localRecTransferInfo = paramRecTransfer.getRecTransferInfo("mod");
    if (paramRecTransfer.getTransferDestination().equals("INTERNAL")) {
      localRecTransferInfo.setTransferType("Repetitive");
    }
    try
    {
      localRecTransferInfo.setFrequency(getBPWFrequencyString(paramRecTransfer.getFrequencyValue()));
      localRecTransferInfo.setTrnId(TrackingIDGenerator.GetNextID());
      DebugLog.log("*** BPW.ModTransfer: Modifying RecTransferInfo = [" + localRecTransferInfo.toString() + "]");
      populateOBOAgentInfo(this.secureUser, localRecTransferInfo);
      localRecTransferInfo = localBPWServices.modRecTransfer(localRecTransferInfo);
    }
    catch (Exception localException)
    {
      DebugLog.log("Exception occured during modifying a recurring External Transfer:");
      localException.printStackTrace();
      int j = 1031;
      return j;
    }
    finally
    {
      removeBPWHandler(localBPWServices);
    }
    i = a(paramRecTransfer, localRecTransferInfo);
    if (i != 0)
    {
      DebugLog.log("Error occurred when modifying a recurring External Transfer:");
      DebugLog.log("*** BPW ErrorCode: " + localRecTransferInfo.getStatusCode());
      DebugLog.log("*** BPW ErrorMsg: " + localRecTransferInfo.getStatusMsg());
      return jdMethod_if(localRecTransferInfo.getStatusCode(), 1031);
    }
    paramRecTransfer.setRecTransferInfo(localRecTransferInfo, null);
    paramRecTransfer.setFrequency(getFrequency(localRecTransferInfo.getFrequency()));
    paramRecTransfer.setFromAccount(paramAccount1);
    paramRecTransfer.setToAccount(paramAccount2);
    return i;
  }
  
  public int skipRecTransfer(Account paramAccount1, Account paramAccount2, RecTransfer paramRecTransfer)
  {
    return 0;
  }
  
  protected void processOFXRequest() {}
  
  public int addTransferBatch(TransferBatch paramTransferBatch)
  {
    paramTransferBatch.setID(null);
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      return 31023;
    }
    TransferBatchInfo localTransferBatchInfo = paramTransferBatch.getTransferBatchInfo("add");
    try
    {
      localTransferBatchInfo.setTrnId(TrackingIDGenerator.GetNextID());
      for (int i = 0; i < localTransferBatchInfo.getTransfers().length; i++)
      {
        TransferInfo localTransferInfo = localTransferBatchInfo.getTransfers()[i];
        localTransferBatchInfo.getTransfers()[i].setTrnId(TrackingIDGenerator.GetNextID());
      }
      DebugLog.log("*** BPW.AddTransferBatch: Adding TransferBatchInfo = [" + localTransferBatchInfo.toString() + "]");
      populateOBOAgentInfo(this.secureUser, localTransferBatchInfo);
      localTransferBatchInfo = localBPWServices.addTransferBatch(localTransferBatchInfo);
      if (localTransferBatchInfo.getStatusCode() != 0)
      {
        DebugLog.log("Error occurred when adding a Transfer Batch:");
        DebugLog.log("*** BPW ErrorCode: " + localTransferBatchInfo.getStatusCode());
        DebugLog.log("*** BPW ErrorMsg: " + localTransferBatchInfo.getStatusMsg());
        i = jdMethod_if(localTransferBatchInfo.getStatusCode(), 1081);
        return i;
      }
    }
    catch (Exception localException)
    {
      DebugLog.log("Exception occured during adding a Transfer Batch:");
      localException.printStackTrace();
      int j = 1030;
      return j;
    }
    finally
    {
      removeBPWHandler(localBPWServices);
    }
    paramTransferBatch.setTransferBatchInfo(localTransferBatchInfo);
    return 0;
  }
  
  public int modifyTransferBatch(TransferBatch paramTransferBatch)
  {
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      return 31023;
    }
    TransferBatchInfo localTransferBatchInfo = paramTransferBatch.getTransferBatchInfo("mod");
    try
    {
      localTransferBatchInfo.setTrnId(TrackingIDGenerator.GetNextID());
      for (int i = 0; i < localTransferBatchInfo.getTransfers().length; i++)
      {
        TransferInfo localTransferInfo = localTransferBatchInfo.getTransfers()[i];
        localTransferInfo.setTrnId(TrackingIDGenerator.GetNextID());
        if (localTransferInfo.getSrvrTId().length() < 3)
        {
          localTransferInfo.setAction("add");
          Hashtable localHashtable = localTransferInfo.getExtInfo();
          if (localHashtable != null)
          {
            Iterator localIterator = localHashtable.values().iterator();
            while (localIterator.hasNext())
            {
              Object localObject1 = localIterator.next();
              if ((localObject1 instanceof ValueInfo))
              {
                ValueInfo localValueInfo = (ValueInfo)localObject1;
                localValueInfo.setAction("add");
              }
            }
          }
        }
        else if (localTransferInfo.getAmount().equals("0.00"))
        {
          localTransferInfo.setAction("del");
        }
        else
        {
          localTransferInfo.setAction("mod");
        }
      }
      DebugLog.log("*** BPW.ModifyTransferBatch: Modifying TransferBatchInfo = [" + localTransferBatchInfo.toString() + "]");
      populateOBOAgentInfo(this.secureUser, localTransferBatchInfo);
      localTransferBatchInfo = localBPWServices.modifyTransferBatch(localTransferBatchInfo);
      if (localTransferBatchInfo.getStatusCode() != 0)
      {
        DebugLog.log("Error occurred when modifying a Transfer Batch:");
        DebugLog.log("*** BPW ErrorCode: " + localTransferBatchInfo.getStatusCode());
        DebugLog.log("*** BPW ErrorMsg: " + localTransferBatchInfo.getStatusMsg());
        i = jdMethod_if(localTransferBatchInfo.getStatusCode(), 1082);
        return i;
      }
    }
    catch (Exception localException)
    {
      DebugLog.log("Exception occured during modifying a Transfer Batch:");
      localException.printStackTrace();
      int j = 1030;
      return j;
    }
    finally
    {
      removeBPWHandler(localBPWServices);
    }
    paramTransferBatch.setTransferBatchInfo(localTransferBatchInfo);
    return 0;
  }
  
  public int cancelTransferBatch(TransferBatch paramTransferBatch)
  {
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      return 31023;
    }
    TransferBatchInfo localTransferBatchInfo = paramTransferBatch.getTransferBatchInfo("del");
    try
    {
      localTransferBatchInfo.setTrnId(TrackingIDGenerator.GetNextID());
      populateOBOAgentInfo(this.secureUser, localTransferBatchInfo);
      localTransferBatchInfo = localBPWServices.cancelTransferBatch(localTransferBatchInfo);
      if (localTransferBatchInfo.getStatusCode() != 0)
      {
        DebugLog.log("Error occurred when deleting a Transfer Batch:");
        DebugLog.log("*** BPW ErrorCode: " + localTransferBatchInfo.getStatusCode());
        DebugLog.log("*** BPW ErrorMsg: " + localTransferBatchInfo.getStatusMsg());
        int i = jdMethod_if(localTransferBatchInfo.getStatusCode(), 1083);
        return i;
      }
    }
    catch (Exception localException)
    {
      DebugLog.log("Exception occured during deleting a transfer batch:");
      localException.printStackTrace();
      int j = 1032;
      return j;
    }
    finally
    {
      removeBPWHandler(localBPWServices);
    }
    if (localTransferBatchInfo.getStatusCode() != 0)
    {
      DebugLog.log("Error occurred when deleting a transfer batch:");
      DebugLog.log("*** BPW ErrorCode: " + localTransferBatchInfo.getStatusCode());
      DebugLog.log("*** BPW ErrorMsg: " + localTransferBatchInfo.getStatusMsg());
      return jdMethod_if(localTransferBatchInfo.getStatusCode(), 1032);
    }
    return 0;
  }
  
  public FixedDepositInstruments getFixedDepositInstruments(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws BankingException
  {
    if (paramAccount == null) {
      throw new BankingException(2);
    }
    Locale localLocale = paramAccount.getLocale();
    com.ffusion.beans.DateTime localDateTime = paramCalendar1 != null ? new com.ffusion.beans.DateTime(paramCalendar1, localLocale) : new com.ffusion.beans.DateTime(localLocale);
    FixedDepositInstruments localFixedDepositInstruments = new FixedDepositInstruments();
    FixedDepositInstrument localFixedDepositInstrument = localFixedDepositInstruments.createFixedDepositInstrument();
    localFixedDepositInstrument.setAccountNumber(paramAccount.getNumber());
    localFixedDepositInstrument.setAccountID(paramAccount.getID());
    localFixedDepositInstrument.setBankID(paramAccount.getBankID());
    localFixedDepositInstrument.setRoutingNumber(paramAccount.getRoutingNum());
    localFixedDepositInstrument.setInstrumentNumber("12345");
    localFixedDepositInstrument.setInstrumentBankName("Fusion Bank");
    localFixedDepositInstrument.setCurrency(paramAccount.getCurrencyCode());
    Currency localCurrency1 = new Currency("100.0", localLocale);
    localFixedDepositInstrument.setPrincipalAmount(localCurrency1);
    localFixedDepositInstrument.setInterestRate(2.0355F);
    Currency localCurrency2 = new Currency("200.0", localLocale);
    localFixedDepositInstrument.setAccruedInterest(localCurrency2);
    localFixedDepositInstrument.setMaturityDate(localDateTime);
    Currency localCurrency3 = new Currency("300.0", localLocale);
    localFixedDepositInstrument.setInterestAtMaturity(localCurrency3);
    Currency localCurrency4 = new Currency("400.0", localLocale);
    localFixedDepositInstrument.setProceedsAtMaturity(localCurrency4);
    localFixedDepositInstrument.setValueDate(localDateTime);
    localFixedDepositInstrument.setDaysInTerm(90);
    Currency localCurrency5 = new Currency("500.0", localLocale);
    localFixedDepositInstrument.setRestrictedAmount(localCurrency5);
    localFixedDepositInstrument.setNumberOfRollovers(5);
    Contact localContact1 = new Contact();
    localContact1.setStreet("742 Evergreen Terrace");
    localContact1.setCity("Springfield");
    localContact1.setState("MA");
    localContact1.setCountry("United States of America");
    localContact1.setEmail("hsimpson@fox.com");
    localContact1.setPhone("555-1234");
    localContact1.setPreferredContactMethod("e-mail");
    localFixedDepositInstrument.setStatementMailingAddr1(localContact1);
    Contact localContact2 = new Contact();
    localContact2.setStreet("24 Sussex Drive");
    localContact2.setCity("Ottawa");
    localContact2.setState("ON");
    localContact2.setCountry("Canada");
    localContact2.setEmail("pm@pm.gc.ca");
    localContact2.setPhone("(613) 992-4211");
    localContact2.setZipCode("K1A 0A2");
    localContact2.setFaxPhone("(613) 941-6900");
    localContact2.setPreferredContactMethod("phone");
    localFixedDepositInstrument.setStatementMailingAddr2(localContact2);
    Contact localContact3 = new Contact();
    localContact3.setStreet("Skywalker Ranch");
    localContact3.setStreet2("5858 Lucas Valley Rd.");
    localContact3.setCity("Nicasio");
    localContact3.setState("CA");
    localContact3.setCountry("United States of Ameria");
    localContact3.setZipCode("94946");
    localContact3.setPreferredContactMethod("none");
    localFixedDepositInstrument.setStatementMailingAddr3(localContact3);
    localFixedDepositInstrument.setSettlementInstructionType(1);
    localFixedDepositInstrument.setSettlementTargetRoutingNumber("55555");
    localFixedDepositInstrument.setSettlementTargetAccountNumber("12345");
    localFixedDepositInstrument.setDataDate(localDateTime);
    localFixedDepositInstrument.setLocale(localLocale);
    localFixedDepositInstrument.setDateFormat(paramAccount.getDateFormat());
    return localFixedDepositInstruments;
  }
  
  public void updateFixedDepositInstrument(FixedDepositInstrument paramFixedDepositInstrument, HashMap paramHashMap)
    throws BankingException
  {
    if (paramFixedDepositInstrument == null) {
      throw new BankingException(4);
    }
  }
  
  public Transactions getRecentTransactions(Account paramAccount, HashMap paramHashMap)
    throws BankingException
  {
    if (paramAccount == null) {
      throw new BankingException(2);
    }
    try
    {
      BankSim.openPagedTransactions(paramAccount, new PagingContext(null, null), paramHashMap);
      int i = jdMethod_new(paramHashMap);
      Enumeration localEnumeration = BankSim.getPrevPage(paramAccount, i);
      Transactions localTransactions = paramAccount.getTransactions();
      if (localTransactions != null)
      {
        localTransactions.clear();
      }
      else
      {
        paramAccount.setTransactions(new Transactions(paramAccount.getLocale()));
        localTransactions = paramAccount.getTransactions();
      }
      while (localEnumeration.hasMoreElements()) {
        localTransactions.add(localEnumeration.nextElement());
      }
      BankSim.closePagedTransactions(paramAccount);
      PagingContext localPagingContext = new PagingContext(null, null);
      if (localTransactions.size() > 0)
      {
        Transaction localTransaction = (Transaction)localTransactions.get(localTransactions.size() - 1);
        localPagingContext.setFirstIndex(localTransaction.getTransactionIndex());
        localTransaction = (Transaction)localTransactions.get(0);
        localPagingContext.setLastIndex(localTransaction.getTransactionIndex());
        HashMap localHashMap = new HashMap();
        long l1 = 0L;
        long l2 = localPagingContext.getLastIndex();
        localHashMap.put("FIRST_PAGING_INDEX", new Long(l1));
        localHashMap.put("LAST_PAGING_INDEX", new Long(l2));
        localPagingContext.setMap(localHashMap);
        localPagingContext.setFirstPage(localPagingContext.getFirstIndex() <= l1);
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
    catch (BSException localBSException)
    {
      throw new BankingException(localBSException.getErrorCode(), localBSException.getMessage());
    }
  }
  
  public Transactions getPagedTransactions(Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    if (paramAccount == null) {
      throw new BankingException(2);
    }
    ReportCriteria localReportCriteria = (ReportCriteria)paramPagingContext.getMap().get("ReportCriteria");
    if (localReportCriteria == null) {
      throw new BankingException(401);
    }
    String str = localReportCriteria.getSearchCriteria().getProperty("Date Range Type");
    Object localObject1;
    Object localObject2;
    if ("Relative".equalsIgnoreCase(str))
    {
      HashMap localHashMap1 = new HashMap();
      HashMap localHashMap2 = new HashMap();
      localObject1 = (SecureUser)paramHashMap.get("SecureUser");
      try
      {
        Reporting.calculateDateRange((SecureUser)localObject1, null, localReportCriteria, localHashMap1, localHashMap2, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        localObject2 = "Unable to calculate the date range.";
        DebugLog.log((String)localObject2);
        if (localCSILException.getCode() == -1009) {
          throw new BankingException(localCSILException.getServiceError());
        }
        throw new BankingException(localCSILException.getCode());
      }
    }
    try
    {
      BankSim.openPagedTransactions(paramAccount, paramPagingContext, paramHashMap);
      int i = jdMethod_new(paramHashMap);
      int j = BankSim.getNumberOfTransactions(paramAccount);
      localObject1 = BankSim.getPrevPage(paramAccount, i);
      Transactions localTransactions = paramAccount.getTransactions();
      if (localTransactions != null)
      {
        localTransactions.clear();
      }
      else
      {
        paramAccount.setTransactions(new Transactions(paramAccount.getLocale()));
        localTransactions = paramAccount.getTransactions();
      }
      while (((Enumeration)localObject1).hasMoreElements()) {
        localTransactions.add(((Enumeration)localObject1).nextElement());
      }
      BankSim.closePagedTransactions(paramAccount);
      if (localTransactions.size() > 0)
      {
        localObject2 = (Transaction)localTransactions.get(0);
        paramPagingContext.setLastIndex(((Transaction)localObject2).getTransactionIndex());
        localObject2 = (Transaction)localTransactions.get(localTransactions.size() - 1);
        paramPagingContext.setFirstIndex(((Transaction)localObject2).getTransactionIndex());
        HashMap localHashMap3 = new HashMap();
        long l1 = paramPagingContext.getLastIndex() - j + 1L;
        long l2 = paramPagingContext.getLastIndex();
        localHashMap3.put("FIRST_PAGING_INDEX", new Long(l1));
        localHashMap3.put("LAST_PAGING_INDEX", new Long(l2));
        localHashMap3.put("ReportCriteria", localReportCriteria);
        paramPagingContext.setMap(localHashMap3);
        paramPagingContext.setFirstPage(paramPagingContext.getFirstIndex() <= l1);
        paramPagingContext.setLastPage(true);
      }
      else
      {
        paramPagingContext.setFirstPage(true);
        paramPagingContext.setLastPage(true);
      }
      localTransactions.setPagingContext(paramPagingContext);
      return localTransactions;
    }
    catch (BSException localBSException)
    {
      throw new BankingException(localBSException.getErrorCode(), localBSException.getMessage());
    }
  }
  
  public Transactions getNextTransactions(Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    if (paramAccount == null) {
      throw new BankingException(2);
    }
    ReportCriteria localReportCriteria = (ReportCriteria)paramPagingContext.getMap().get("ReportCriteria");
    if (localReportCriteria == null) {
      throw new BankingException(401);
    }
    String str = localReportCriteria.getSearchCriteria().getProperty("Date Range Type");
    Object localObject;
    if ("Relative".equalsIgnoreCase(str))
    {
      HashMap localHashMap1 = new HashMap();
      HashMap localHashMap2 = new HashMap();
      SecureUser localSecureUser = (SecureUser)paramHashMap.get("SecureUser");
      try
      {
        Reporting.calculateDateRange(localSecureUser, null, localReportCriteria, localHashMap1, localHashMap2, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        localObject = "Unable to calculate the date range.";
        DebugLog.log((String)localObject);
        if (localCSILException.getCode() == -1009) {
          throw new BankingException(localCSILException.getServiceError());
        }
        throw new BankingException(localCSILException.getCode());
      }
    }
    try
    {
      BankSim.openPagedTransactions(paramAccount, paramPagingContext, paramHashMap);
      int i = jdMethod_new(paramHashMap);
      long l = paramPagingContext.getLastIndex() + 1L;
      Long localLong1 = new Long(l);
      localObject = BankSim.getNextPage(paramAccount, i, localLong1.intValue());
      Transactions localTransactions = paramAccount.getTransactions();
      if (localTransactions != null)
      {
        localTransactions.clear();
      }
      else
      {
        paramAccount.setTransactions(new Transactions(paramAccount.getLocale()));
        localTransactions = paramAccount.getTransactions();
      }
      while (((Enumeration)localObject).hasMoreElements()) {
        localTransactions.add(((Enumeration)localObject).nextElement());
      }
      BankSim.closePagedTransactions(paramAccount);
      if (localTransactions.size() > 0)
      {
        Transaction localTransaction = (Transaction)localTransactions.get(0);
        paramPagingContext.setFirstIndex(localTransaction.getTransactionIndex());
        localTransaction = (Transaction)localTransactions.get(localTransactions.size() - 1);
        paramPagingContext.setLastIndex(localTransaction.getTransactionIndex());
        boolean bool = false;
        HashMap localHashMap3 = paramPagingContext.getMap();
        if (localHashMap3 != null)
        {
          Long localLong2 = (Long)localHashMap3.get("LAST_PAGING_INDEX");
          if (localLong2 != null) {
            bool = localLong2.longValue() == paramPagingContext.getLastIndex();
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
      localTransactions.setPagingContext(paramPagingContext);
      return localTransactions;
    }
    catch (BSException localBSException)
    {
      throw new BankingException(localBSException.getErrorCode(), localBSException.getMessage());
    }
  }
  
  public Transactions getPreviousTransactions(Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    if (paramAccount == null) {
      throw new BankingException(2);
    }
    ReportCriteria localReportCriteria = (ReportCriteria)paramPagingContext.getMap().get("ReportCriteria");
    if (localReportCriteria == null) {
      throw new BankingException(401);
    }
    String str = localReportCriteria.getSearchCriteria().getProperty("Date Range Type");
    Object localObject;
    if ("Relative".equalsIgnoreCase(str))
    {
      HashMap localHashMap1 = new HashMap();
      HashMap localHashMap2 = new HashMap();
      SecureUser localSecureUser = (SecureUser)paramHashMap.get("SecureUser");
      try
      {
        Reporting.calculateDateRange(localSecureUser, null, localReportCriteria, localHashMap1, localHashMap2, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        localObject = "Unable to calcualte the date range.";
        DebugLog.log((String)localObject);
        if (localCSILException.getCode() == -1009) {
          throw new BankingException(localCSILException.getServiceError());
        }
        throw new BankingException(localCSILException.getCode());
      }
    }
    try
    {
      BankSim.openPagedTransactions(paramAccount, paramPagingContext, paramHashMap);
      int i = jdMethod_new(paramHashMap);
      long l = paramPagingContext.getFirstIndex();
      Long localLong1 = new Long(l);
      localObject = BankSim.getPrevPage(paramAccount, i, localLong1.intValue());
      Transactions localTransactions = paramAccount.getTransactions();
      if (localTransactions != null)
      {
        localTransactions.clear();
      }
      else
      {
        paramAccount.setTransactions(new Transactions(paramAccount.getLocale()));
        localTransactions = paramAccount.getTransactions();
      }
      while (((Enumeration)localObject).hasMoreElements()) {
        localTransactions.add(((Enumeration)localObject).nextElement());
      }
      BankSim.closePagedTransactions(paramAccount);
      if (localTransactions.size() > 0)
      {
        Transaction localTransaction = (Transaction)localTransactions.get(localTransactions.size() - 1);
        paramPagingContext.setFirstIndex(localTransaction.getTransactionIndex());
        localTransaction = (Transaction)localTransactions.get(0);
        paramPagingContext.setLastIndex(localTransaction.getTransactionIndex());
        boolean bool = false;
        HashMap localHashMap3 = paramPagingContext.getMap();
        if (localHashMap3 != null)
        {
          Long localLong2 = (Long)localHashMap3.get("FIRST_PAGING_INDEX");
          if (localLong2 != null) {
            bool = localLong2.longValue() == paramPagingContext.getFirstIndex();
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
      localTransactions.setPagingContext(paramPagingContext);
      return localTransactions;
    }
    catch (BSException localBSException)
    {
      throw new BankingException(localBSException.getErrorCode(), localBSException.getMessage());
    }
  }
  
  public Transactions getFDInstrumentTransactions(FixedDepositInstrument paramFixedDepositInstrument, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws BankingException
  {
    if (paramFixedDepositInstrument == null) {
      throw new BankingException(4);
    }
    Locale localLocale = paramFixedDepositInstrument.getLocale();
    com.ffusion.beans.DateTime localDateTime = paramCalendar1 != null ? new com.ffusion.beans.DateTime(paramCalendar1, localLocale) : new com.ffusion.beans.DateTime(localLocale);
    Transactions localTransactions = new Transactions();
    Transaction localTransaction1 = localTransactions.create();
    localTransaction1.setID("12345");
    localTransaction1.setType(1);
    localTransaction1.setCategory(1);
    localTransaction1.setDescription("Rollover Principal");
    localTransaction1.setDate(localDateTime);
    localTransaction1.setAmount("1000.0");
    localTransaction1.setFixedDepositRate(2.2205F);
    localTransaction1.setInstrumentNumber(paramFixedDepositInstrument.getInstrumentNumber());
    localTransaction1.setInstrumentBankName(paramFixedDepositInstrument.getInstrumentBankName());
    Transaction localTransaction2 = localTransactions.create();
    localTransaction2.setID("67890");
    localTransaction2.setType(1);
    localTransaction2.setCategory(1);
    localTransaction2.setDescription("Rollover Principal/Interest");
    localTransaction2.setDate(localDateTime);
    localTransaction2.setAmount("2000.0");
    localTransaction2.setFixedDepositRate(3.14159F);
    localTransaction2.setInstrumentNumber(paramFixedDepositInstrument.getInstrumentNumber());
    localTransaction1.setInstrumentBankName(paramFixedDepositInstrument.getInstrumentBankName());
    localTransactions.setLocale(localLocale);
    localTransactions.setDateFormat(paramFixedDepositInstrument.getDateFormat());
    return localTransactions;
  }
  
  public int getNumTransactions(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws BankingException
  {
    if (paramAccount == null) {
      throw new BankingException(2);
    }
    try
    {
      Enumeration localEnumeration = BankSim.getTransactions(paramAccount, paramCalendar1, paramCalendar2);
      for (int i = 0; localEnumeration.hasMoreElements(); i++) {
        localEnumeration.nextElement();
      }
      return i;
    }
    catch (BSException localBSException)
    {
      throw new BankingException(localBSException.getErrorCode(), localBSException.getMessage());
    }
  }
  
  public AccountHistories getHistory(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws BankingException
  {
    if (paramAccount == null) {
      throw new BankingException(2);
    }
    Locale localLocale = paramAccount.getLocale();
    com.ffusion.beans.DateTime localDateTime = paramCalendar1 != null ? new com.ffusion.beans.DateTime(paramCalendar1, localLocale) : new com.ffusion.beans.DateTime(localLocale);
    AccountHistories localAccountHistories = new AccountHistories();
    AccountHistory localAccountHistory = localAccountHistories.create();
    localAccountHistory.setAccountNumber(paramAccount.getNumber());
    localAccountHistory.setAccountID(paramAccount.getID());
    localAccountHistory.setBankID(paramAccount.getBankID());
    localAccountHistory.setRoutingNumber(paramAccount.getRoutingNum());
    localAccountHistory.setHistoryDate(localDateTime);
    localAccountHistory.setOpeningLedger("27500");
    localAccountHistory.setAvgOpeningLedgerMTD("85055");
    localAccountHistory.setAvgOpeningLedgerYTD("54900");
    localAccountHistory.setClosingLedger("14955");
    localAccountHistory.setAvgClosingLedgerMTD("852900");
    localAccountHistory.setAvgMonth("27555");
    localAccountHistory.setAggregateBalAdjustment("1000");
    localAccountHistory.setAvgClosingLedgerYTDPrevMonth("85055");
    localAccountHistory.setAvgClosingLedgerYTD("14900");
    localAccountHistory.setCurrentLedger("52055");
    localAccountHistory.setNetPositionACH("857500");
    localAccountHistory.setOpenAvailSameDayACHDTC("1055");
    localAccountHistory.setOpeningAvail("5000");
    localAccountHistory.setAvgOpenAvailMTD("85055");
    localAccountHistory.setAvgOpenAvailYTD("22900");
    localAccountHistory.setAvgAvailPrevMonth("57555");
    localAccountHistory.setDisbursingOpeningAvailBal("85000");
    localAccountHistory.setClosingAvail("1055");
    localAccountHistory.setAvgClosingAvailMTD("5000");
    localAccountHistory.setAvgClosingAvailPrevMonth("852055");
    localAccountHistory.setAvgClosingAvailYTDPrevMonth("274900");
    localAccountHistory.setAvgClosingAvailYTD("5055");
    localAccountHistory.setLoanBal("85000");
    localAccountHistory.setTotalInvestmentPosition("1055");
    localAccountHistory.setCurrentAvailCRSSurpressed("52000");
    localAccountHistory.setCurrentAvail("857555");
    localAccountHistory.setAvgCurrentAvailMTD("14900");
    localAccountHistory.setAvgCurrentAvailYTD("5055");
    localAccountHistory.setTotalFloat("85000");
    localAccountHistory.setTargetBal("22055");
    localAccountHistory.setAdjustedBal("57500");
    localAccountHistory.setAdjustedBalMTD("85055");
    localAccountHistory.setAdjustedBalYTD("14900");
    localAccountHistory.setZeroDayFloat("5055");
    localAccountHistory.setOneDayFloat("852000");
    localAccountHistory.setFloatAdjusted("27555");
    localAccountHistory.setTwoOrMoreDayFloat("5000");
    localAccountHistory.setThreeOrMoreDayFloat("854955");
    localAccountHistory.setAdjustmentToBal("1000");
    localAccountHistory.setAvgAdjustmentToBalMTD("52055");
    localAccountHistory.setAvgAdjustmentToBalYTD("857500");
    localAccountHistory.setFourDayFloat("1055");
    localAccountHistory.setFiveDayFloat("54900");
    localAccountHistory.setSixDayFloat("85055");
    localAccountHistory.setAvgOneDayFloatMTD("22000");
    localAccountHistory.setAvgOneDayFloatYTD("57555");
    localAccountHistory.setAvgTwoDayFloatMTD("854900");
    localAccountHistory.setAvgTwoDayFloatYTD("1055");
    localAccountHistory.setTransferCalculation("5000");
    localAccountHistory.setTargetBalDeficiency("852955");
    localAccountHistory.setTotalFundingRequirement("27500");
    localAccountHistory.setLocale(paramAccount.getLocale());
    localAccountHistory.setDateFormat(paramAccount.getDateFormat());
    return localAccountHistories;
  }
  
  public AccountSummaries getSummary(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws BankingException
  {
    if (paramAccount == null) {
      throw new BankingException(2);
    }
    Locale localLocale = paramAccount.getLocale();
    com.ffusion.beans.DateTime localDateTime = paramCalendar1 != null ? new com.ffusion.beans.DateTime(paramCalendar1, localLocale) : new com.ffusion.beans.DateTime(localLocale);
    AccountSummaries localAccountSummaries = new AccountSummaries();
    int i = paramAccount.getAccountGroup();
    int j = Account.getAccountSystemTypeFromGroup(i);
    int k = paramAccount.getTypeValue();
    Object localObject1 = new AccountSummary();
    Object localObject2;
    if ((j == 1) || (k == 12))
    {
      localObject2 = new DepositAcctSummary();
      ((DepositAcctSummary)localObject2).setTotalCredits(new Currency("52174", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((DepositAcctSummary)localObject2).setTotalCreditAmtMTD(new Currency("500", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((DepositAcctSummary)localObject2).setCreditsNotDetailed(new Currency("2510", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((DepositAcctSummary)localObject2).setDepositsSubjectToFloat(new Currency("5370", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((DepositAcctSummary)localObject2).setTotalAdjCreditsYTD(new Currency("2516", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((DepositAcctSummary)localObject2).setTotalLockboxDeposits(new Currency("540", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((DepositAcctSummary)localObject2).setTotalDebits(new Currency("52310", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((DepositAcctSummary)localObject2).setTotalDebitAmtMTD(new Currency("733", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((DepositAcctSummary)localObject2).setTodaysTotalDebits(new Currency("2510", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((DepositAcctSummary)localObject2).setTotalDebitsLessWireAndCharge(new Currency("5370", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((DepositAcctSummary)localObject2).setTotalAdjDebitsYTD(new Currency("2510", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((DepositAcctSummary)localObject2).setTotalDebitsExcludeReturns(new Currency("500", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((DepositAcctSummary)localObject2).setImmedAvailAmt(new Currency("52310", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((DepositAcctSummary)localObject2).setOneDayAvailAmt(new Currency("507", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((DepositAcctSummary)localObject2).setMoreThanOneDayAvailAmt(new Currency("2510", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((DepositAcctSummary)localObject2).setAvailOverdraft(new Currency("5370", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((DepositAcctSummary)localObject2).setRestrictedCash(new Currency("2510", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((DepositAcctSummary)localObject2).setAccruedInterest(new Currency("450", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((DepositAcctSummary)localObject2).setAccruedDividend(new Currency("52310", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((DepositAcctSummary)localObject2).setTotalOverdraftAmt(new Currency("400", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((DepositAcctSummary)localObject2).setNextOverdraftPmtDate(localDateTime);
      ((DepositAcctSummary)localObject2).setInterestRate(1.2004F);
      ((DepositAcctSummary)localObject2).setOpeningLedger(new Currency("2510", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((DepositAcctSummary)localObject2).setClosingLedger(new Currency("501", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((DepositAcctSummary)localObject2).setCurrentAvailBal(new Currency("52310", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((DepositAcctSummary)localObject2).setLedgerBal(new Currency("500", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((DepositAcctSummary)localObject2).setOneDayFloat(new Currency("2510", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((DepositAcctSummary)localObject2).setTwoDayFloat(new Currency("5375", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((DepositAcctSummary)localObject2).setTotalFloat(new Currency("2510", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((DepositAcctSummary)localObject2).setCurrentLedger(new Currency("52100", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((DepositAcctSummary)localObject2).setInterestYTD(new Currency("352", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((DepositAcctSummary)localObject2).setPriorYearInterest(new Currency("350", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localObject1 = localObject2;
    }
    else if (j == 2)
    {
      localObject2 = new AssetAcctSummary();
      ((AssetAcctSummary)localObject2).setBookValue(new Currency("3370", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((AssetAcctSummary)localObject2).setMarketValue(new Currency("700", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localObject1 = localObject2;
    }
    else if (j == 3)
    {
      localObject2 = new LoanAcctSummary();
      ((LoanAcctSummary)localObject2).setAvailCredit(new Currency("5050", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((LoanAcctSummary)localObject2).setAmtDue(new Currency("370", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((LoanAcctSummary)localObject2).setInterestRate(5.4444F);
      ((LoanAcctSummary)localObject2).setDueDate(localDateTime);
      ((LoanAcctSummary)localObject2).setMaturityDate(localDateTime);
      ((LoanAcctSummary)localObject2).setAccruedInterest(new Currency("911", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((LoanAcctSummary)localObject2).setOpeningBal(new Currency("700", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((LoanAcctSummary)localObject2).setCollateralDescription("Beach House");
      ((LoanAcctSummary)localObject2).setPrincipalPastDue(new Currency("70045", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((LoanAcctSummary)localObject2).setInterestPastDue(new Currency("737", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((LoanAcctSummary)localObject2).setLateFees(new Currency("7035", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((LoanAcctSummary)localObject2).setNextPrincipalAmt(new Currency("700", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((LoanAcctSummary)localObject2).setNextInterestAmt(new Currency("7370", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((LoanAcctSummary)localObject2).setOpenDate(localDateTime);
      ((LoanAcctSummary)localObject2).setCurrentBalance(new Currency("4000", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((LoanAcctSummary)localObject2).setNextPaymentDate(localDateTime);
      ((LoanAcctSummary)localObject2).setNextPaymentAmt(new Currency("1050", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((LoanAcctSummary)localObject2).setInterestYTD(new Currency("200", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((LoanAcctSummary)localObject2).setPriorYearInterest(new Currency("180", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((LoanAcctSummary)localObject2).setLoanTerm("1");
      ((LoanAcctSummary)localObject2).setTodaysPayoff(new Currency("50", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((LoanAcctSummary)localObject2).setPayoffGoodThru(localDateTime);
      localObject1 = localObject2;
    }
    else if (j == 4)
    {
      localObject2 = new CreditCardAcctSummary();
      ((CreditCardAcctSummary)localObject2).setAvailCredit(new Currency("50370", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((CreditCardAcctSummary)localObject2).setAmtDue(new Currency("50", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((CreditCardAcctSummary)localObject2).setInterestRate(10.766F);
      ((CreditCardAcctSummary)localObject2).setDueDate(localDateTime);
      ((CreditCardAcctSummary)localObject2).setCardHolderName("Brad Bowman");
      ((CreditCardAcctSummary)localObject2).setCardExpDate(localDateTime);
      ((CreditCardAcctSummary)localObject2).setCreditLimit(new Currency("5800", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((CreditCardAcctSummary)localObject2).setLastPaymentAmt(new Currency("7370", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((CreditCardAcctSummary)localObject2).setNextPaymentMinAmt(new Currency("6600", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((CreditCardAcctSummary)localObject2).setLastPaymentDate(localDateTime);
      ((CreditCardAcctSummary)localObject2).setNextPaymentDue(localDateTime);
      ((CreditCardAcctSummary)localObject2).setCurrentBalance(new Currency("43700", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((CreditCardAcctSummary)localObject2).setLastAdvanceDate(localDateTime);
      ((CreditCardAcctSummary)localObject2).setLastAdvanceAmt(new Currency("2000", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      ((CreditCardAcctSummary)localObject2).setPayoffAmount(new Currency("6600", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localObject1 = localObject2;
    }
    ((AccountSummary)localObject1).setAccountNumber(paramAccount.getNumber());
    ((AccountSummary)localObject1).setAccountID(paramAccount.getID());
    ((AccountSummary)localObject1).setBankID(paramAccount.getBankID());
    ((AccountSummary)localObject1).setRoutingNumber(paramAccount.getRoutingNum());
    ((AccountSummary)localObject1).setSummaryDate(localDateTime);
    ((AccountSummary)localObject1).setValueDate(localDateTime);
    ((AccountSummary)localObject1).setLocale(paramAccount.getLocale());
    ((AccountSummary)localObject1).setDateFormat(paramAccount.getDateFormat());
    localAccountSummaries.add(localObject1);
    return localAccountSummaries;
  }
  
  public ExtendedAccountSummaries getExtendedSummary(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws BankingException
  {
    if (paramAccount == null) {
      throw new BankingException(2);
    }
    Locale localLocale = paramAccount.getLocale();
    com.ffusion.beans.DateTime localDateTime = paramCalendar1 != null ? new com.ffusion.beans.DateTime(paramCalendar1, localLocale) : new com.ffusion.beans.DateTime(localLocale);
    ExtendedAccountSummaries localExtendedAccountSummaries = new ExtendedAccountSummaries();
    ExtendedAccountSummary localExtendedAccountSummary = localExtendedAccountSummaries.create();
    localExtendedAccountSummary.setAccountNumber(paramAccount.getNumber());
    localExtendedAccountSummary.setAccountID(paramAccount.getID());
    localExtendedAccountSummary.setBankID(paramAccount.getBankID());
    localExtendedAccountSummary.setRoutingNumber(paramAccount.getRoutingNum());
    localExtendedAccountSummary.setSummaryDate(localDateTime);
    localExtendedAccountSummary.setImmedAvailAmt(new Currency("500", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
    localExtendedAccountSummary.setOneDayAvailAmt(new Currency("1055", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
    localExtendedAccountSummary.setMoreThanOneDayAvailAmt(new Currency("10100", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
    localExtendedAccountSummary.setValueDateTime(localDateTime);
    localExtendedAccountSummary.setAmt(new Currency("350075", paramAccount.getCurrencyCode(), paramAccount.getLocale()));
    localExtendedAccountSummary.setSummaryType(1);
    localExtendedAccountSummary.setLocale(paramAccount.getLocale());
    localExtendedAccountSummary.setDateFormat(paramAccount.getDateFormat());
    return localExtendedAccountSummaries;
  }
  
  protected Transfers filterTransfersInReport(SecureUser paramSecureUser, Transfers paramTransfers, HashMap paramHashMap)
  {
    return paramTransfers;
  }
  
  public IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws BankingException
  {
    if (paramReportCriteria == null) {
      throw new BankingException(401);
    }
    Properties localProperties1 = paramReportCriteria.getReportOptions();
    if (localProperties1 == null) {
      throw new BankingException(1022);
    }
    String str1 = localProperties1.getProperty("REPORTTYPE");
    if (str1 == null) {
      throw new BankingException(1023, "Report options key REPORTTYPE does not exist.");
    }
    Properties localProperties2 = paramReportCriteria.getSearchCriteria();
    if (localProperties2 == null) {
      throw new BankingException(403);
    }
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    try
    {
      Reporting.calculateDateRange(paramSecureUser, null, paramReportCriteria, localHashMap1, localHashMap2, paramHashMap);
      paramHashMap.put("StartDates", localHashMap1);
      paramHashMap.put("EndDates", localHashMap2);
    }
    catch (CSILException localCSILException)
    {
      str3 = "Unable to calcualte the date ranges. A report cannot be run.";
      DebugLog.log(str3);
      if (localCSILException.getCode() == -1009) {
        throw new BankingException(localCSILException.getServiceError());
      }
      throw new BankingException(localCSILException.getCode());
    }
    String str2 = localProperties1.getProperty("DATEFORMAT");
    String str3 = localProperties2.getProperty("Accounts");
    if (str3 == null) {
      str3 = "AllAccounts";
    }
    com.ffusion.beans.DateTime localDateTime1 = null;
    String str4 = localProperties2.getProperty("StartDate");
    if ((str4 != null) && (str4.length() != 0)) {
      try
      {
        Date localDate1 = DateFormatUtil.getFormatter("MM/dd/yyyy HH:mm:ss").parse(str4);
        localDateTime1 = new com.ffusion.beans.DateTime(localDate1, Locale.getDefault(), "MM/dd/yyyy HH:mm:ss");
      }
      catch (Exception localException1)
      {
        DebugLog.throwing("Banking:getReportData", localException1);
        throw new BankingException(406, "StartDate is not in valid format.");
      }
    }
    com.ffusion.beans.DateTime localDateTime2 = null;
    String str5 = localProperties2.getProperty("EndDate");
    if ((str5 != null) && (str5.length() != 0)) {
      try
      {
        Date localDate2 = DateFormatUtil.getFormatter("MM/dd/yyyy HH:mm:ss").parse(str5);
        localDateTime2 = new com.ffusion.beans.DateTime(localDate2, Locale.getDefault(), "MM/dd/yyyy HH:mm:ss");
      }
      catch (Exception localException2)
      {
        DebugLog.throwing("Banking:getReportData", localException2);
        throw new BankingException(406, "EndDate is not in valid format.");
      }
    }
    Object localObject1 = null;
    Accounts localAccounts = new Accounts(paramReportCriteria.getLocale());
    Object localObject4;
    Object localObject7;
    Object localObject8;
    Object localObject9;
    Object localObject10;
    Object localObject11;
    if (jdMethod_int(str3))
    {
      localAccounts = (Accounts)paramHashMap.get("Accounts");
      localAccounts.setLocale(paramReportCriteria.getLocale());
    }
    else
    {
      localObject2 = new StringTokenizer(str3, ",");
      int i = ((StringTokenizer)localObject2).countTokens();
      localObject4 = new Accounts();
      int j = getAccounts((Accounts)localObject4);
      if (j != 0) {
        throw new BankingException(j);
      }
      for (int k = 0; k < i; k++)
      {
        String str6 = ((StringTokenizer)localObject2).nextToken();
        StringTokenizer localStringTokenizer = new StringTokenizer(str6, ":");
        int i1 = localStringTokenizer.countTokens();
        if ((i1 == 3) || (i1 == 5))
        {
          localObject7 = localStringTokenizer.nextToken();
          localObject8 = localStringTokenizer.nextToken();
          localObject9 = localStringTokenizer.nextToken();
          localObject10 = ((Accounts)localObject4).iterator();
          while (((Iterator)localObject10).hasNext())
          {
            localObject11 = (Account)((Iterator)localObject10).next();
            if ((((String)localObject7).equals(((Account)localObject11).getID())) && (((String)localObject8).equals(((Account)localObject11).getBankID())) && (((String)localObject9).equals(((Account)localObject11).getRoutingNum())))
            {
              localAccounts.add(localObject11);
              break;
            }
          }
        }
        else
        {
          throw new BankingException(405, "Value for SearchCriteria key Accounts has incorrect format.");
        }
      }
    }
    Object localObject2 = paramReportCriteria.getLocale();
    Object localObject3;
    Object localObject5;
    Object localObject6;
    if (str1.equals("AccountHistory"))
    {
      localObject3 = new ReportResult((Locale)localObject2);
      try
      {
        ((ReportResult)localObject3).init(paramReportCriteria);
        localObject4 = new ReportDataDimensions((Locale)localObject2);
        ((ReportDataDimensions)localObject4).setNumColumns(0);
        ((ReportDataDimensions)localObject4).setNumRows(0);
        ((ReportResult)localObject3).setDataDimensions((ReportDataDimensions)localObject4);
        localAccounts.setDateFormat(str2);
        localObject5 = localAccounts.iterator();
        while (((Iterator)localObject5).hasNext())
        {
          Account localAccount = (Account)((Iterator)localObject5).next();
          int m = getTransactions(localAccount, localDateTime1, localDateTime2);
          if (m != 0) {
            throw new BankingException(m);
          }
          int n = 0;
          localObject6 = localProperties1.getProperty("FORMAT");
          if (("BAI2".equals(localObject6)) || ("QIF".equals(localObject6)) || ("QFX".equals(localObject6)) || ("QBO".equals(localObject6)) || ("IIF".equals(localObject6))) {
            n = 1;
          }
          if (n != 0) {
            jdMethod_do(localAccount, (ReportResult)localObject3);
          } else if ("COMMA".equals(localProperties1.getProperty("FORMAT"))) {
            jdMethod_if(localAccount, (ReportResult)localObject3);
          }
        }
        localObject1 = localObject3;
      }
      catch (Exception localException3)
      {
        ((ReportResult)localObject3).setError(localException3);
        throw new BankingException(413);
      }
      finally
      {
        try
        {
          ((ReportResult)localObject3).fini();
        }
        catch (Throwable localThrowable2) {}
      }
    }
    else if (str1.equals("ConsumerAccountHistory"))
    {
      localObject1 = a(paramReportCriteria, localAccounts, paramSecureUser, (Locale)localObject2, paramHashMap);
    }
    else
    {
      if ((str1.equals("DepositDetail")) || (str1.equals("TransactionDetail")) || (str1.equals("CreditReport")) || (str1.equals("DebitReport"))) {
        throw new BankingException(411);
      }
      if (str1.equals("BalanceSummaryReport")) {
        throw new BankingException(411);
      }
      if (str1.equals("BalanceSummaryReport")) {
        throw new BankingException(411);
      }
      if (str1.equals("BalanceDetailOnlyReport")) {
        throw new BankingException(411);
      }
      if ((str1.equals("Transfer History Report")) || (str1.equals("Transfer Detail Report")) || (str1.equals("Pending Transfer Report")) || (str1.equals("Transfer By Status")))
      {
        localAccounts = (Accounts)paramHashMap.get("Accounts");
        localObject3 = new Transfers((Locale)localObject2);
        com.ffusion.beans.DateTime localDateTime3 = null;
        localObject5 = localProperties2.getProperty("StartDate");
        if ((localObject5 != null) && (((String)localObject5).length() > 0)) {
          try
          {
            localDateTime3 = new com.ffusion.beans.DateTime(DateFormatUtil.getFormatter("MM/dd/yyyy HH:mm:ss").parse((String)localObject5), Locale.getDefault(), "MM/dd/yyyy HH:mm:ss");
          }
          catch (Exception localException4) {}
        } else {
          paramReportCriteria.setHiddenSearchCriterion("StartDate", true);
        }
        com.ffusion.beans.DateTime localDateTime4 = null;
        String str7 = localProperties2.getProperty("EndDate");
        if ((str7 != null) && (str7.length() > 0)) {
          try
          {
            localDateTime4 = new com.ffusion.beans.DateTime(DateFormatUtil.getFormatter("MM/dd/yyyy HH:mm:ss").parse(str7), Locale.getDefault(), "MM/dd/yyyy HH:mm:ss");
          }
          catch (Exception localException5) {}
        } else {
          paramReportCriteria.setHiddenSearchCriterion("EndDate", true);
        }
        PagingContext localPagingContext = new PagingContext(localDateTime3, localDateTime4);
        if (paramHashMap == null) {
          paramHashMap = new HashMap();
        }
        paramHashMap.put("PAGESIZE", "0");
        localObject6 = new ArrayList();
        localObject7 = new StringBuffer();
        if (str1.equals("Transfer History Report"))
        {
          ((ArrayList)localObject6).add("PROCESSEDON");
          ((ArrayList)localObject6).add("POSTEDON");
          ((ArrayList)localObject6).add("NOFUNDSON");
          ((ArrayList)localObject6).add("NOFUNDSON_NOTIF");
          ((ArrayList)localObject6).add("FAILEDON");
          ((ArrayList)localObject6).add("FAILEDON_NOTIF");
          ((ArrayList)localObject6).add("LIMIT_CHECK_FAILED");
          ((ArrayList)localObject6).add("LIMIT_CHECK_FAILED_NOTIF");
          ((ArrayList)localObject6).add("LIMIT_REVERT_FAILED");
          ((ArrayList)localObject6).add("APPROVAL_FAILED");
          ((StringBuffer)localObject7).append("PROCESSEDON");
          ((StringBuffer)localObject7).append(",");
          ((StringBuffer)localObject7).append("POSTEDON");
          ((StringBuffer)localObject7).append(",");
          ((StringBuffer)localObject7).append("NOFUNDSON");
          ((StringBuffer)localObject7).append(",");
          ((StringBuffer)localObject7).append("NOFUNDSON_NOTIF");
          ((StringBuffer)localObject7).append(",");
          ((StringBuffer)localObject7).append("FAILEDON");
          ((StringBuffer)localObject7).append(",");
          ((StringBuffer)localObject7).append("FAILEDON_NOTIF");
          ((StringBuffer)localObject7).append(",");
          ((StringBuffer)localObject7).append("LIMIT_CHECK_FAILED");
          ((StringBuffer)localObject7).append(",");
          ((StringBuffer)localObject7).append("LIMIT_CHECK_FAILED_NOTIF");
          ((StringBuffer)localObject7).append(",");
          ((StringBuffer)localObject7).append("LIMIT_REVERT_FAILED");
          ((StringBuffer)localObject7).append(",");
          ((StringBuffer)localObject7).append("APPROVAL_FAILED");
        }
        else if (str1.equals("Pending Transfer Report"))
        {
          ((ArrayList)localObject6).add("WILLPROCESSON");
          ((ArrayList)localObject6).add("BATCH_INPROCESS");
          ((StringBuffer)localObject7).append("WILLPROCESSON");
          ((StringBuffer)localObject7).append(",");
          ((StringBuffer)localObject7).append("BATCH_INPROCESS");
        }
        else if (str1.equals("Transfer Detail Report"))
        {
          ((ArrayList)localObject6).add("PROCESSEDON");
          ((ArrayList)localObject6).add("POSTEDON");
          ((ArrayList)localObject6).add("NOFUNDSON");
          ((ArrayList)localObject6).add("NOFUNDSON_NOTIF");
          ((ArrayList)localObject6).add("FAILEDON");
          ((ArrayList)localObject6).add("FAILEDON_NOTIF");
          ((ArrayList)localObject6).add("WILLPROCESSON");
          ((ArrayList)localObject6).add("BATCH_INPROCESS");
          ((ArrayList)localObject6).add("LIMIT_CHECK_FAILED");
          ((ArrayList)localObject6).add("LIMIT_CHECK_FAILED_NOTIF");
          ((ArrayList)localObject6).add("LIMIT_REVERT_FAILED");
          ((ArrayList)localObject6).add("APPROVAL_FAILED");
          ((StringBuffer)localObject7).append("PROCESSEDON");
          ((StringBuffer)localObject7).append(",");
          ((StringBuffer)localObject7).append("POSTEDON");
          ((StringBuffer)localObject7).append(",");
          ((StringBuffer)localObject7).append("NOFUNDSON");
          ((StringBuffer)localObject7).append(",");
          ((StringBuffer)localObject7).append("NOFUNDSON_NOTIF");
          ((StringBuffer)localObject7).append(",");
          ((StringBuffer)localObject7).append("FAILEDON");
          ((StringBuffer)localObject7).append(",");
          ((StringBuffer)localObject7).append("FAILEDON_NOTIF");
          ((StringBuffer)localObject7).append(",");
          ((StringBuffer)localObject7).append("WILLPROCESSON");
          ((StringBuffer)localObject7).append(",");
          ((StringBuffer)localObject7).append("BATCH_INPROCESS");
          ((StringBuffer)localObject7).append(",");
          ((StringBuffer)localObject7).append("LIMIT_CHECK_FAILED");
          ((StringBuffer)localObject7).append(",");
          ((StringBuffer)localObject7).append("LIMIT_CHECK_FAILED_NOTIF");
          ((StringBuffer)localObject7).append(",");
          ((StringBuffer)localObject7).append("LIMIT_REVERT_FAILED");
          ((StringBuffer)localObject7).append(",");
          ((StringBuffer)localObject7).append("APPROVAL_FAILED");
        }
        else if (str1.equals("Transfer By Status"))
        {
          localObject8 = localProperties2.getProperty("Status");
          if (localObject8 == null) {
            localObject8 = "";
          }
          if (((String)localObject8).equals(""))
          {
            paramReportCriteria.setDisplayValue("Status", ReportConsts.getText(10018, (Locale)localObject2));
          }
          else
          {
            localObject9 = ((String)localObject8).substring(0, ((String)localObject8).length() - 1);
            localObject10 = new StringBuffer();
            localObject11 = new StringTokenizer((String)localObject8, ",");
            while (((StringTokenizer)localObject11).hasMoreTokens())
            {
              ((StringBuffer)localObject10).append(((StringTokenizer)localObject11).nextToken());
              if (((StringTokenizer)localObject11).hasMoreTokens()) {
                ((StringBuffer)localObject10).append(", ");
              }
            }
            paramReportCriteria.setDisplayValue("Status", ((StringBuffer)localObject10).toString());
          }
          localObject9 = new StringTokenizer((String)localObject8, ",");
          localObject10 = null;
          while (((StringTokenizer)localObject9).hasMoreTokens())
          {
            localObject10 = ((StringTokenizer)localObject9).nextToken();
            if (localObject10 != null) {
              if (((String)localObject10).equals("Completed"))
              {
                ((ArrayList)localObject6).add("POSTEDON");
                ((ArrayList)localObject6).add("PROCESSEDON");
                ((StringBuffer)localObject7).append("POSTEDON");
                ((StringBuffer)localObject7).append(",");
                ((StringBuffer)localObject7).append("PROCESSEDON");
                ((StringBuffer)localObject7).append(",");
              }
              else if (((String)localObject10).equals("No Funds Available"))
              {
                ((ArrayList)localObject6).add("NOFUNDSON");
                ((ArrayList)localObject6).add("NOFUNDSON_NOTIF");
                ((StringBuffer)localObject7).append("NOFUNDSON");
                ((StringBuffer)localObject7).append(",");
                ((StringBuffer)localObject7).append("NOFUNDSON_NOTIF");
                ((StringBuffer)localObject7).append(",");
              }
              else if (((String)localObject10).equals("Failed"))
              {
                ((ArrayList)localObject6).add("FAILEDON");
                ((ArrayList)localObject6).add("FAILEDON_NOTIF");
                ((StringBuffer)localObject7).append("FAILEDON");
                ((StringBuffer)localObject7).append(",");
                ((StringBuffer)localObject7).append("FAILEDON_NOTIF");
                ((StringBuffer)localObject7).append(",");
              }
              else if (((String)localObject10).equals("Scheduled"))
              {
                ((ArrayList)localObject6).add("WILLPROCESSON");
                ((StringBuffer)localObject7).append("WILLPROCESSON");
                ((StringBuffer)localObject7).append(",");
              }
              else if (((String)localObject10).equals("Batch In Process"))
              {
                ((ArrayList)localObject6).add("BATCH_INPROCESS");
                ((StringBuffer)localObject7).append("BATCH_INPROCESS");
                ((StringBuffer)localObject7).append(",");
              }
              else if (((String)localObject10).equals("Limit Check Failed"))
              {
                ((ArrayList)localObject6).add("LIMIT_CHECK_FAILED");
                ((ArrayList)localObject6).add("LIMIT_CHECK_FAILED_NOTIF");
                ((StringBuffer)localObject7).append("LIMIT_CHECK_FAILED");
                ((StringBuffer)localObject7).append(",");
                ((StringBuffer)localObject7).append("LIMIT_CHECK_FAILED_NOTIF");
                ((StringBuffer)localObject7).append(",");
              }
              else if (((String)localObject10).equals("Limit Revert Failed"))
              {
                ((ArrayList)localObject6).add("LIMIT_REVERT_FAILED");
                ((StringBuffer)localObject7).append("LIMIT_REVERT_FAILED");
                ((StringBuffer)localObject7).append(",");
              }
              else if (((String)localObject10).equals("Approval Failed"))
              {
                ((ArrayList)localObject6).add("APPROVAL_FAILED");
                ((StringBuffer)localObject7).append("APPROVAL_FAILED");
                ((StringBuffer)localObject7).append(",");
              }
              else if (((String)localObject10).equals("Cancelled"))
              {
                ((ArrayList)localObject6).add("CANCELEDON");
                ((StringBuffer)localObject7).append("CANCELEDON");
                ((StringBuffer)localObject7).append(",");
              }
              else if (((String)localObject10).equals("Approval Pending"))
              {
                ((ArrayList)localObject6).add("APPROVAL_PENDING");
                ((StringBuffer)localObject7).append("APPROVAL_PENDING");
                ((StringBuffer)localObject7).append(",");
              }
              else if (((String)localObject10).equals("Approval Rejected"))
              {
                ((ArrayList)localObject6).add("APPROVAL_REJECTED");
                ((StringBuffer)localObject7).append("APPROVAL_REJECTED");
                ((StringBuffer)localObject7).append(",");
              }
              else if (((String)localObject10).equals("Immediate In Process"))
              {
                DebugLog.log("*** TCG: RPT_STATUS_IMMED_INPROCESS chosen");
                ((ArrayList)localObject6).add("IMMED_INPROCESS");
                ((StringBuffer)localObject7).append("IMMED_INPROCESS");
                ((StringBuffer)localObject7).append(",");
              }
            }
          }
          localObject11 = ((StringBuffer)localObject7).toString();
          if ((localObject11 != null) && (((String)localObject11).length() > 0))
          {
            localObject11 = ((String)localObject11).substring(0, ((String)localObject11).length() - 1);
            localObject7 = new StringBuffer((String)localObject11);
          }
        }
        localObject8 = (String[])((ArrayList)localObject6).toArray(new String[0]);
        paramHashMap.put("Statuses", localObject8);
        localObject9 = a(paramSecureUser, localAccounts, localPagingContext, 0, paramHashMap);
        localObject10 = localProperties1.getProperty("DATEFORMAT");
        if (localObject10 == null) {
          localObject10 = "SHORT";
        }
        ((Transfers)localObject9).setDateFormat((String)localObject10);
        ((Transfers)localObject9).setFilter("All");
        boolean bool1 = false;
        if ((localProperties2.getProperty("FromAccount") != null) && (!jdMethod_int(localProperties2.getProperty("FromAccount"))))
        {
          setFiltersOnList((FilteredList)localObject9, localProperties2, "FromAccount", "FROMACCOUNTID", "=", bool1);
          bool1 = true;
        }
        if ((localProperties2.getProperty("ToAccount") != null) && (!jdMethod_int(localProperties2.getProperty("ToAccount"))))
        {
          setFiltersOnList((FilteredList)localObject9, localProperties2, "ToAccount", "TOACCOUNTID", "=", bool1);
          bool1 = true;
        }
        if (!jdMethod_for(localProperties2.getProperty("AmountFrom")))
        {
          setFiltersOnList((FilteredList)localObject9, localProperties2, "AmountFrom", "AMOUNT", ">=", bool1);
          bool1 = true;
        }
        if (!jdMethod_for(localProperties2.getProperty("AmountTo")))
        {
          setFiltersOnList((FilteredList)localObject9, localProperties2, "AmountTo", "AMOUNT", "<=", bool1);
          bool1 = true;
        }
        localObject9 = filterTransfersInReport(paramSecureUser, (Transfers)localObject9, paramHashMap);
        Iterator localIterator1 = ((Transfers)localObject9).iterator();
        Currency localCurrency = new Currency("0.00", paramSecureUser.getBaseCurrency(), (Locale)localObject2);
        HashMap localHashMap3 = new HashMap();
        HashMap localHashMap4 = new HashMap();
        boolean bool2 = false;
        while (localIterator1.hasNext())
        {
          Transfer localTransfer = (Transfer)localIterator1.next();
          if ((!str1.equals("Pending Transfer Report")) || (localTransfer.getStatus() == 2) || (localTransfer.getStatus() == 8) || (localTransfer.getStatus() == 12) || (localTransfer.getStatus() == 9))
          {
            ((Transfers)localObject3).add(localTransfer);
            if ((localTransfer.getUserAssignedAmountFlag() == UserAssignedAmount.FROM) || (localTransfer.getUserAssignedAmountFlag() == UserAssignedAmount.TO)) {
              bool2 = true;
            }
            if (localTransfer.getAmountValue().getCurrencyCode().equals(paramSecureUser.getBaseCurrency())) {
              localCurrency.addAmount(localTransfer.getAmountValue());
            } else if (localTransfer.getToAmountValue().getCurrencyCode().equals(paramSecureUser.getBaseCurrency())) {
              localCurrency.addAmount(localTransfer.getToAmountValue());
            }
            localObject13 = (Currency)localHashMap3.get(localTransfer.getAmountValue().getCurrencyCode());
            if (localObject13 == null) {
              localObject13 = new Currency(localTransfer.getAmountValue().getAmountValue(), localTransfer.getAmountValue().getCurrencyCode(), Locale.getDefault());
            } else {
              ((Currency)localObject13).addAmount(localTransfer.getAmountValue());
            }
            localHashMap3.put(localTransfer.getAmountValue().getCurrencyCode(), localObject13);
            localObject13 = (Currency)localHashMap4.get(localTransfer.getToAmountValue().getCurrencyCode());
            if (localObject13 == null) {
              localObject13 = new Currency(localTransfer.getToAmountValue().getAmountValue(), localTransfer.getToAmountValue().getCurrencyCode(), Locale.getDefault());
            } else {
              ((Currency)localObject13).addAmount(localTransfer.getToAmountValue());
            }
            localHashMap4.put(localTransfer.getToAmountValue().getCurrencyCode(), localObject13);
            if ((!localHashMap3.containsKey(localTransfer.getToAmountValue().getCurrencyCode())) && (localTransfer.getToAmountValue().getCurrencyCode() != null)) {
              localHashMap3.put(localTransfer.getToAmountValue().getCurrencyCode(), new Currency("0.00", paramSecureUser.getBaseCurrency(), (Locale)localObject2));
            }
            if ((!localHashMap4.containsKey(localTransfer.getAmountValue().getCurrencyCode())) && (localTransfer.getAmountValue().getCurrencyCode() != null)) {
              localHashMap4.put(localTransfer.getAmountValue().getCurrencyCode(), new Currency("0.00", paramSecureUser.getBaseCurrency(), (Locale)localObject2));
            }
          }
        }
        Object localObject13 = paramReportCriteria.getSortCriteria();
        StringBuffer localStringBuffer = new StringBuffer();
        if ((localObject13 != null) && (((ReportSortCriteria)localObject13).size() > 0))
        {
          ((ReportSortCriteria)localObject13).setSortedBy("ORDINAL");
          Iterator localIterator2 = ((ReportSortCriteria)localObject13).iterator();
          while (localIterator2.hasNext())
          {
            ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)localIterator2.next();
            String str8 = localReportSortCriterion.getName();
            String str9 = localReportSortCriterion.getAsc() ? "ASC" : "DESC";
            localStringBuffer.append(str8);
            localStringBuffer.append(" ");
            localStringBuffer.append(str9);
            if (localIterator2.hasNext()) {
              localStringBuffer.append(", ");
            }
          }
          ((Transfers)localObject3).setSortedBy(localStringBuffer.toString());
        }
        if (str1.equals("Transfer By Status")) {
          localObject1 = a(paramReportCriteria, (Transfers)localObject3, localCurrency, str1, paramHashMap, bool2, localHashMap3, localHashMap4);
        } else {
          localObject1 = a(paramSecureUser, paramReportCriteria, (Transfers)localObject3, localCurrency, str1, paramHashMap, bool2, localHashMap3, localHashMap4);
        }
      }
      else
      {
        if (str1.equals("GeneralLedgerReport")) {
          throw new BankingException(411);
        }
        if ((str1.equals("BalanceSheetReport")) || (str1.equals("BalanceSheetSummary"))) {
          throw new BankingException(411);
        }
        if (str1.equals("CashFlowForecastReport")) {
          throw new BankingException(411);
        }
        if (str1.equals("CashFlowReport")) {
          throw new BankingException(411);
        }
        throw new BankingException(408, str1 + " is an invalid Account Report Type");
      }
    }
    return localObject1;
  }
  
  private ReportResult a(ReportCriteria paramReportCriteria, Accounts paramAccounts, SecureUser paramSecureUser, Locale paramLocale, HashMap paramHashMap)
    throws BankingException
  {
    ReportResult localReportResult = new ReportResult(paramLocale);
    Properties localProperties1 = paramReportCriteria.getSearchCriteria();
    Properties localProperties2 = paramReportCriteria.getReportOptions();
    String str1 = localProperties2.getProperty("DATEFORMAT");
    String str2 = localProperties1.getProperty("Date Range Type");
    paramReportCriteria.setHiddenSearchCriterion("DataClassification", true);
    String str3 = localProperties1.getProperty("TransactionType");
    Object localObject2;
    Object localObject1;
    if (str3 != null) {
      if (!str3.equals("AllTransactionTypes"))
      {
        int i = Integer.parseInt(localProperties1.getProperty("TransactionType"));
        HashMap localHashMap1 = new HashMap();
        localHashMap1 = getTransactionTypes(paramLocale, new HashMap());
        localObject2 = (String)localHashMap1.get(new Integer(i));
        paramReportCriteria.setDisplayValue("TransactionType", (String)localObject2);
      }
      else
      {
        localObject1 = new LocalizableString("com.ffusion.beans.reporting.reports", "AllTransactionTypes", null);
        paramReportCriteria.setDisplayValue("TransactionType", (String)((ILocalizable)localObject1).localize(paramLocale));
      }
    }
    a(paramReportCriteria, paramAccounts);
    try
    {
      localObject1 = localProperties2.getProperty("FORMAT");
      if (("QIF".equals(localObject1)) || ("QFX".equals(localObject1)) || ("OFX".equals(localObject1)))
      {
        paramReportCriteria.setDisplayValue("EndDate", localProperties1.getProperty("EndDate"));
        paramReportCriteria.setDisplayValue("StartDate", localProperties1.getProperty("StartDate"));
      }
      paramReportCriteria.setHiddenSearchCriterion("ShowBalance", true);
      paramReportCriteria.setHiddenSearchCriterion("ShowMemo", true);
      int j = paramAccounts.size();
      if ("Since Last Export".equalsIgnoreCase(str2)) {
        if (j > 1)
        {
          localObject2 = new LocalizableString("com.ffusion.beans.reporting.reports", "Text_Since_Last_Export_Multi", null);
          paramReportCriteria.getSearchCriteria().setProperty("StartDate", "");
          paramReportCriteria.setDisplayValue("StartDate", (String)((ILocalizable)localObject2).localize(paramLocale));
          paramReportCriteria.setHiddenSearchCriterion("Date Range", true);
          paramReportCriteria.setHiddenSearchCriterion("EndDate", true);
        }
        else if (j == 1)
        {
          a(paramSecureUser, (Account)paramAccounts.get(0), paramReportCriteria, paramHashMap);
        }
      }
      localReportResult.init(paramReportCriteria);
      localObject2 = new ReportDataDimensions(paramLocale);
      ((ReportDataDimensions)localObject2).setNumColumns(0);
      ((ReportDataDimensions)localObject2).setNumRows(0);
      localReportResult.setDataDimensions((ReportDataDimensions)localObject2);
      paramAccounts.setDateFormat(str1);
      Iterator localIterator = paramAccounts.iterator();
      PagingContext localPagingContext = new PagingContext(null, null);
      paramHashMap.put("PAGESIZE", "10000000");
      HashMap localHashMap2 = new HashMap();
      localHashMap2.put("ReportCriteria", paramReportCriteria);
      localPagingContext.setMap(localHashMap2);
      paramHashMap.put("SecureUser", paramSecureUser);
      while (localIterator.hasNext())
      {
        localObject3 = (Account)localIterator.next();
        a(paramSecureUser, (Account)localObject3, paramReportCriteria, paramHashMap);
        Transactions localTransactions = getPagedTransactions((Account)localObject3, localPagingContext, paramHashMap);
        int k = 0;
        if (("QIF".equals(localObject1)) || ("QFX".equals(localObject1)) || ("OFX".equals(localObject1))) {
          k = 1;
        }
        if (k != 0) {
          a((Account)localObject3, localReportResult);
        } else {
          a((Account)localObject3, localReportResult, paramReportCriteria, paramHashMap);
        }
      }
      Object localObject3 = localReportResult;
      return localObject3;
    }
    catch (BankingException localBankingException)
    {
      throw localBankingException;
    }
    catch (Exception localException)
    {
      localReportResult.setError(localException);
      throw new BankingException(413);
    }
    finally
    {
      try
      {
        localReportResult.fini();
      }
      catch (Throwable localThrowable2) {}
    }
  }
  
  private void a(ReportCriteria paramReportCriteria, Accounts paramAccounts)
  {
    int i = paramAccounts.size();
    StringBuffer localStringBuffer = new StringBuffer();
    for (int j = 0; j < i; j++)
    {
      if (j != 0) {
        localStringBuffer.append(", ");
      }
      localStringBuffer.append(((Account)paramAccounts.get(j)).getConsumerExportDisplayText());
    }
    paramReportCriteria.setDisplayValue("Accounts", localStringBuffer.toString());
  }
  
  private void a(SecureUser paramSecureUser, Account paramAccount, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws BankingException
  {
    try
    {
      String str = paramReportCriteria.getSearchCriteria().getProperty("Date Range Type");
      if ("Since Last Export".equalsIgnoreCase(str))
      {
        localObject1 = (HashMap)paramHashMap.get("LAST EXPORT DATES");
        localObject2 = paramAccount.getID() + ":" + paramAccount.getBankID() + ":" + paramAccount.getRoutingNum();
        com.ffusion.util.beans.DateTime localDateTime = (com.ffusion.util.beans.DateTime)((HashMap)localObject1).get(localObject2);
        paramHashMap.put("LAST EXPORT DATE", localDateTime);
      }
      paramHashMap.remove("CURRENT EXPORT DATE");
      Reporting.calculateDateRange(paramSecureUser, null, paramReportCriteria, null, null, paramHashMap);
      localObject1 = (com.ffusion.util.beans.DateTime)paramHashMap.get("CURRENT EXPORT DATE");
      Object localObject2 = (HashMap)paramHashMap.get("CURRENT EXPORT DATES");
      if (localObject2 == null)
      {
        localObject2 = new HashMap();
        paramHashMap.put("CURRENT EXPORT DATES", localObject2);
      }
      if (localObject1 == null) {
        localObject1 = new com.ffusion.util.beans.DateTime(new Date(), paramAccount.getLocale());
      }
      ((HashMap)paramHashMap.get("CURRENT EXPORT DATES")).put(paramAccount, localObject1);
      paramHashMap.remove("CURRENT EXPORT DATE");
    }
    catch (CSILException localCSILException)
    {
      Object localObject1 = "Unable to calculate the date ranges. A report cannot be run.";
      DebugLog.log((String)localObject1);
      if (localCSILException.getCode() == -1009) {
        throw new BankingException(localCSILException.getServiceError());
      }
      throw new BankingException(localCSILException.getCode());
    }
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
    HashMap localHashMap1 = getTransactionTypes(0, null);
    if ((localHashMap1 == null) || (localHashMap1.isEmpty())) {
      return new HashMap(0);
    }
    HashMap localHashMap2 = new HashMap();
    Iterator localIterator = localHashMap1.values().iterator();
    TransactionTypeInfo localTransactionTypeInfo;
    Integer localInteger;
    if (paramLocale == null) {
      while (localIterator.hasNext())
      {
        localTransactionTypeInfo = (TransactionTypeInfo)localIterator.next();
        localInteger = new Integer(localTransactionTypeInfo.getID());
        localHashMap2.put(localInteger, localTransactionTypeInfo.getDescription());
      }
    }
    while (localIterator.hasNext())
    {
      localTransactionTypeInfo = (TransactionTypeInfo)localIterator.next();
      localInteger = new Integer(localTransactionTypeInfo.getID());
      localHashMap2.put(localInteger, localTransactionTypeInfo.getDescription(paramLocale));
    }
    return localHashMap2;
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
  
  private IReportResult a(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, Transfers paramTransfers, Currency paramCurrency, String paramString, HashMap paramHashMap1, boolean paramBoolean, HashMap paramHashMap2, HashMap paramHashMap3)
    throws BankingException
  {
    ReportResult localReportResult = new ReportResult(paramReportCriteria.getLocale());
    Locale localLocale = localReportResult.getLocale();
    String str1 = "≈";
    int i = 0;
    Properties localProperties = paramReportCriteria.getReportOptions();
    String str2 = localProperties.getProperty("DATEFORMAT");
    if (str2 == null) {
      str2 = "SHORT";
    }
    Accounts localAccounts = (Accounts)paramHashMap1.get("Accounts");
    int j = Integer.parseInt(a(paramReportCriteria.getReportOptions(), "MAXDISPLAYSIZE", String.valueOf(2147483647)));
    int k = 0;
    String str3 = a(paramReportCriteria.getSearchCriteria(), "FromAccount", "");
    if (("".equals(str3)) || (jdMethod_int(str3))) {
      paramReportCriteria.setDisplayValue("FromAccount", ReportConsts.getText(10024, localLocale));
    } else {
      paramReportCriteria.setDisplayValue("FromAccount", jdMethod_if(localAccounts, str3));
    }
    String str4 = a(paramReportCriteria.getSearchCriteria(), "ToAccount", "");
    if (("".equals(str4)) || (jdMethod_int(str4))) {
      paramReportCriteria.setDisplayValue("ToAccount", ReportConsts.getText(10024, localLocale));
    } else {
      paramReportCriteria.setDisplayValue("ToAccount", jdMethod_if(localAccounts, str4));
    }
    String str5 = a(paramReportCriteria.getSearchCriteria(), "AmountFrom", "").trim();
    paramReportCriteria.setHiddenSearchCriterion("AmountFrom", "".equals(str5));
    String str6 = a(paramReportCriteria.getSearchCriteria(), "AmountTo", "").trim();
    paramReportCriteria.setHiddenSearchCriterion("AmountTo", "".equals(str6));
    try
    {
      localReportResult.init(paramReportCriteria);
      Object localObject1;
      Object localObject2;
      Object localObject3;
      Object localObject4;
      Object localObject5;
      Object localObject6;
      Object localObject7;
      Object localObject8;
      Object localObject9;
      Object localObject10;
      if (paramTransfers.size() > 0)
      {
        localObject1 = paramTransfers.iterator();
        localObject2 = null;
        localObject3 = paramSecureUser.getBaseCurrency();
        localObject4 = new ReportResult(localLocale);
        localReportResult.addSubReport((ReportResult)localObject4);
        localObject5 = new ReportDataDimensions(localLocale);
        ((ReportDataDimensions)localObject5).setNumColumns(paramBoolean ? 11 : 10);
        ((ReportDataDimensions)localObject5).setNumRows(-1);
        ((ReportResult)localObject4).setDataDimensions((ReportDataDimensions)localObject5);
        jdMethod_do((ReportResult)localObject4, 1906, "java.lang.String", 10, null);
        jdMethod_do((ReportResult)localObject4, 1890, "java.lang.String", 10, null);
        jdMethod_do((ReportResult)localObject4, 1899, "java.lang.String", 10, null);
        jdMethod_do((ReportResult)localObject4, 1891, "java.lang.String", 10, null);
        jdMethod_do((ReportResult)localObject4, 1892, "java.lang.String", 10, null);
        jdMethod_do((ReportResult)localObject4, 1893, "java.lang.String", 12, null);
        jdMethod_do((ReportResult)localObject4, 1898, "java.lang.String", 8, null);
        jdMethod_do((ReportResult)localObject4, 1894, "java.lang.String", 8, null);
        jdMethod_do((ReportResult)localObject4, 1895, "java.lang.String", 8, null);
        jdMethod_do((ReportResult)localObject4, paramBoolean ? 1907 : 1896, "java.lang.String", 8, "RIGHT");
        if (paramBoolean) {
          jdMethod_do((ReportResult)localObject4, 1908, "java.lang.String", 8, "RIGHT");
        }
        localObject1 = paramTransfers.iterator();
        localObject2 = null;
        while ((((Iterator)localObject1).hasNext()) && (i < j))
        {
          localObject2 = (Transfer)((Iterator)localObject1).next();
          localObject6 = new ReportRow(((ReportResult)localObject4).getLocale());
          if (i % 2 == 1) {
            ((ReportRow)localObject6).set("CELLBACKGROUND", "reportDataShaded");
          }
          localObject7 = new ReportDataItems(((ReportResult)localObject4).getLocale());
          ((ReportRow)localObject6).setDataItems((ReportDataItems)localObject7);
          localObject8 = ((ReportDataItems)localObject7).add();
          a((ReportDataItem)localObject8, ((Transfer)localObject2).getDateToPost());
          localObject8 = ((ReportDataItems)localObject7).add();
          a((ReportDataItem)localObject8, ((Transfer)localObject2).getDatePosted());
          localObject8 = ((ReportDataItems)localObject7).add();
          localObject9 = ((Transfer)localObject2).getCreateDate();
          if (jdMethod_for((String)localObject9))
          {
            a((ReportDataItem)localObject8, "");
          }
          else
          {
            if (((String)localObject9).length() > 10) {
              localObject9 = ((String)localObject9).substring(0, 10);
            }
            localObject9 = jdMethod_for((String)localObject9, "yyyy/MM/dd", str2);
            a((ReportDataItem)localObject8, localObject9);
          }
          localObject8 = ((ReportDataItems)localObject7).add();
          localObject10 = (String)((Transfer)localObject2).get("Frequency");
          if (localObject10 != null)
          {
            localObject10 = ((String)localObject10).replace(' ', '_');
            localObject10 = ((String)localObject10).replace('-', '_');
            a((ReportDataItem)localObject8, ResourceUtil.getString((String)localObject10, "com.ffusion.beans.reporting.reports", localLocale));
          }
          else
          {
            a((ReportDataItem)localObject8, ResourceUtil.getString("One_Time", "com.ffusion.beans.reporting.reports", localLocale));
          }
          localObject8 = ((ReportDataItems)localObject7).add();
          String str7 = ((Transfer)localObject2).getFromAccount().getDisplayTextRoutingNumNickNameCurrency();
          a((ReportDataItem)localObject8, str7);
          localObject8 = ((ReportDataItems)localObject7).add();
          str7 = ((Transfer)localObject2).getToAccount().getDisplayTextRoutingNumNickNameCurrency();
          if ((((Transfer)localObject2).getTransferDestination().equals("ITOE")) || (((Transfer)localObject2).getTransferDestination().equals("ETOI")))
          {
            localObject11 = jdMethod_do(((Transfer)localObject2).getExtAcctId());
            if (((ExtTransferAcctInfo)localObject11).getRecipientName() != null) {
              str7 = str7 + " - " + ((ExtTransferAcctInfo)localObject11).getRecipientName();
            }
          }
          a((ReportDataItem)localObject8, str7);
          localObject8 = ((ReportDataItems)localObject7).add();
          if ((((Transfer)localObject2).getTransferDestination().equals("ITOE")) || (((Transfer)localObject2).getTransferDestination().equals("ETOI"))) {
            a((ReportDataItem)localObject8, ResourceUtil.getString("Internal to External".replace(' ', '_'), "com.ffusion.beans.reporting.reports", localLocale));
          } else {
            a((ReportDataItem)localObject8, ResourceUtil.getString("Internal to Internal".replace(' ', '_'), "com.ffusion.beans.reporting.reports", localLocale));
          }
          localObject8 = ((ReportDataItems)localObject7).add();
          a((ReportDataItem)localObject8, ((Transfer)localObject2).getID());
          localObject8 = ((ReportDataItems)localObject7).add();
          a((ReportDataItem)localObject8, ((Transfer)localObject2).getStatusName());
          localObject8 = ((ReportDataItems)localObject7).add();
          Object localObject11 = null;
          if (((Transfer)localObject2).getAmountValue() != null) {
            localObject11 = ((Transfer)localObject2).getAmountValue().toString();
          }
          if ((((Transfer)localObject2).getIsAmountEstimated()) && (((Transfer)localObject2).getAmountValue() != null)) {
            localObject11 = str1.concat(((Transfer)localObject2).getAmountValue().toString());
          }
          a((ReportDataItem)localObject8, localObject11);
          localObject11 = null;
          if (((Transfer)localObject2).getToAmountValue() != null) {
            localObject11 = ((Transfer)localObject2).getToAmountValue().toString();
          }
          if (paramBoolean)
          {
            localObject8 = ((ReportDataItems)localObject7).add();
            if ((((Transfer)localObject2).getIsToAmountEstimated()) && (((Transfer)localObject2).getToAmountValue() != null)) {
              localObject11 = str1.concat(((Transfer)localObject2).getToAmountValue().toString());
            }
            a((ReportDataItem)localObject8, localObject11);
          }
          ((ReportResult)localObject4).addRow((ReportRow)localObject6);
          i++;
        }
        ((ReportDataDimensions)localObject5).setNumRows(i);
      }
      else
      {
        localObject1 = new ReportResult(localLocale);
        localReportResult.addSubReport((ReportResult)localObject1);
        localObject2 = null;
        localObject3 = new ReportDataDimensions(localLocale);
        ((ReportDataDimensions)localObject3).setNumColumns(1);
        ((ReportDataDimensions)localObject3).setNumRows(1);
        ((ReportResult)localObject1).setDataDimensions((ReportDataDimensions)localObject3);
        localObject2 = new ReportColumn(localLocale);
        ((ReportColumn)localObject2).setDataType("java.lang.String");
        ((ReportColumn)localObject2).setWidthAsPercent(100);
        ((ReportResult)localObject1).addColumn((ReportColumn)localObject2);
        localObject2 = null;
        localObject4 = new ReportRow(localLocale);
        localObject5 = new ReportDataItems(localLocale);
        localObject6 = null;
        ((ReportRow)localObject4).setDataItems((ReportDataItems)localObject5);
        localObject6 = ((ReportDataItems)localObject5).add();
        if (paramString.equals("Transfer History Report")) {
          ((ReportDataItem)localObject6).setData(ReportConsts.getText(2500, localLocale));
        }
        if (paramString.equals("Transfer Detail Report")) {
          ((ReportDataItem)localObject6).setData(ReportConsts.getText(2501, localLocale));
        }
        if (paramString.equals("Pending Transfer Report")) {
          ((ReportDataItem)localObject6).setData(ReportConsts.getText(2502, localLocale));
        }
        localObject6 = null;
        localObject5 = null;
        ((ReportResult)localObject1).addRow((ReportRow)localObject4);
        localObject4 = null;
      }
      if (paramBoolean)
      {
        localObject1 = paramHashMap2.keySet().iterator();
        while (((Iterator)localObject1).hasNext())
        {
          localObject2 = (String)((Iterator)localObject1).next();
          localObject3 = (Currency)paramHashMap2.get(localObject2);
          localObject4 = (Currency)paramHashMap3.get(localObject2);
          localObject5 = new ReportResult(localLocale);
          localReportResult.addSubReport((ReportResult)localObject5);
          localObject6 = new ReportDataDimensions(localLocale);
          ((ReportDataDimensions)localObject6).setNumColumns(3);
          ((ReportDataDimensions)localObject6).setNumRows(1);
          ((ReportResult)localObject5).setDataDimensions((ReportDataDimensions)localObject6);
          jdMethod_do((ReportResult)localObject5, -1, "java.lang.String", 85, "RIGHT");
          jdMethod_do((ReportResult)localObject5, -1, "java.lang.String", 8, "RIGHT");
          jdMethod_do((ReportResult)localObject5, -1, "java.lang.String", 8, "RIGHT");
          localObject7 = new ReportRow(((ReportResult)localObject5).getLocale());
          localObject8 = new ReportDataItems(((ReportResult)localObject5).getLocale());
          ((ReportRow)localObject7).setDataItems((ReportDataItems)localObject8);
          localObject9 = ((ReportDataItems)localObject8).add();
          localObject10 = new StringBuffer(ReportConsts.getText(1068, localLocale));
          ((StringBuffer)localObject10).append(" (");
          ((StringBuffer)localObject10).append((String)localObject2);
          ((StringBuffer)localObject10).append("): ");
          a((ReportDataItem)localObject9, ((StringBuffer)localObject10).toString());
          localObject9 = ((ReportDataItems)localObject8).add();
          a((ReportDataItem)localObject9, ((Currency)localObject3).getCurrencyStringNoSymbol());
          localObject9 = ((ReportDataItems)localObject8).add();
          a((ReportDataItem)localObject9, ((Currency)localObject4).getCurrencyStringNoSymbol());
          ((ReportResult)localObject5).addRow((ReportRow)localObject7);
        }
      }
      if ((i < j) && (i != 0))
      {
        localObject1 = new ReportResult(localLocale);
        localReportResult.addSubReport((ReportResult)localObject1);
        localObject2 = new ReportDataDimensions(localLocale);
        ((ReportDataDimensions)localObject2).setNumColumns(2);
        ((ReportDataDimensions)localObject2).setNumRows(1);
        ((ReportResult)localObject1).setDataDimensions((ReportDataDimensions)localObject2);
        jdMethod_do((ReportResult)localObject1, -1, "java.lang.String", 85, "RIGHT");
        jdMethod_do((ReportResult)localObject1, -1, "java.lang.String", 15, "RIGHT");
        localObject3 = new ReportRow(((ReportResult)localObject1).getLocale());
        localObject4 = new ReportDataItems(((ReportResult)localObject1).getLocale());
        ((ReportRow)localObject3).setDataItems((ReportDataItems)localObject4);
        localObject5 = ((ReportDataItems)localObject4).add();
        localObject6 = new StringBuffer(ReportConsts.getText(10049, localLocale));
        ((StringBuffer)localObject6).append(" (");
        ((StringBuffer)localObject6).append(paramCurrency.getCurrencyCode());
        ((StringBuffer)localObject6).append("): ");
        a((ReportDataItem)localObject5, ((StringBuffer)localObject6).toString());
        localObject5 = ((ReportDataItems)localObject4).add();
        a((ReportDataItem)localObject5, paramCurrency.getCurrencyStringNoSymbol());
        ((ReportResult)localObject1).addRow((ReportRow)localObject3);
      }
    }
    catch (Exception localException)
    {
      k = 1;
      localReportResult.setError(localException);
      throw new BankingException(413);
    }
    finally
    {
      if (i == j)
      {
        HashMap localHashMap = new HashMap();
        localHashMap.put("TRUNCATED", new Integer(i));
        localReportResult.setProperties(localHashMap);
      }
      try
      {
        localReportResult.fini();
      }
      catch (RptException localRptException)
      {
        if (k == 0) {
          throw new BankingException(413);
        }
      }
    }
    return localReportResult;
  }
  
  private static void a(ReportDataItem paramReportDataItem, Object paramObject)
  {
    if (paramObject == null) {
      paramReportDataItem.setData("");
    } else {
      paramReportDataItem.setData(paramObject);
    }
  }
  
  private static String a(Properties paramProperties, String paramString1, String paramString2)
  {
    String str = paramProperties.getProperty(paramString1);
    if ((str == null) || ("".equals(str))) {
      str = paramString2;
    } else {
      str = str.trim();
    }
    return str;
  }
  
  public Transfers getPagedPendingTransfers(SecureUser paramSecureUser, Accounts paramAccounts, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    paramPagingContext.setDirection("FIRST");
    return a(paramSecureUser, paramAccounts, paramPagingContext, 2, paramHashMap);
  }
  
  public Transfers getNextPendingTransfers(SecureUser paramSecureUser, Accounts paramAccounts, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    paramPagingContext.setDirection("NEXT");
    return a(paramSecureUser, paramAccounts, paramPagingContext, 2, paramHashMap);
  }
  
  public Transfers getPreviousPendingTransfers(SecureUser paramSecureUser, Accounts paramAccounts, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    paramPagingContext.setDirection("PREVIOUS");
    return a(paramSecureUser, paramAccounts, paramPagingContext, 2, paramHashMap);
  }
  
  public Transfers getPagedCompletedTransfers(SecureUser paramSecureUser, Accounts paramAccounts, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    paramPagingContext.setDirection("FIRST");
    return a(paramSecureUser, paramAccounts, paramPagingContext, 5, paramHashMap);
  }
  
  public Transfers getNextCompletedTransfers(SecureUser paramSecureUser, Accounts paramAccounts, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    paramPagingContext.setDirection("NEXT");
    return a(paramSecureUser, paramAccounts, paramPagingContext, 5, paramHashMap);
  }
  
  public Transfers getPreviousCompletedTransfers(SecureUser paramSecureUser, Accounts paramAccounts, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    paramPagingContext.setDirection("PREVIOUS");
    return a(paramSecureUser, paramAccounts, paramPagingContext, 5, paramHashMap);
  }
  
  private Transfers a(SecureUser paramSecureUser, Accounts paramAccounts, PagingContext paramPagingContext, int paramInt, HashMap paramHashMap)
    throws BankingException
  {
    Transfers localTransfers = new Transfers(paramSecureUser.getLocale());
    localTransfers.setPagingContext(paramPagingContext);
    HashMap localHashMap1 = paramPagingContext.getMap();
    if (localHashMap1 == null) {
      localHashMap1 = new HashMap();
    }
    HashMap localHashMap2 = new HashMap();
    localHashMap1.put("SEARCH_CRITERIA", localHashMap2);
    paramPagingContext.setMap(localHashMap1);
    getClass();
    int i = 10;
    try
    {
      i = jdMethod_new(paramHashMap);
    }
    catch (Exception localException1)
    {
      DebugLog.throwing("getTransfersPage ", localException1);
    }
    localHashMap1.put("PAGE_SIZE", String.valueOf(i));
    Object localObject3;
    Object localObject4;
    if (paramPagingContext.getDirection().equalsIgnoreCase("FIRST"))
    {
      localHashMap2.put("CustomerId", String.valueOf(paramSecureUser.getBusinessID()));
      localHashMap2.put("FIID", paramSecureUser.getBPWFIID());
      localHashMap2.put("CategoryList", "USER_ENTRY");
      localObject1 = null;
      localObject2 = (String)paramHashMap.get("Dests");
      if ((localObject2 == null) || (((String)localObject2).equals("BOTH"))) {
        localObject1 = new String[] { "ITOI", "INTERNAL", "ITOE", "ETOI" };
      } else {
        localObject1 = new String[] { localObject2 };
      }
      localHashMap2.put("Dests", localObject1);
      String[] arrayOfString = null;
      localObject3 = (String)paramHashMap.get("TransType");
      if ((localObject3 == null) || (((String)localObject3).equals("TRANS_TYPE_SINGLE")))
      {
        arrayOfString = new String[] { "Current", "Repetitive", "Recurring" };
      }
      else
      {
        localObject4 = new StringTokenizer((String)localObject3, ",");
        arrayOfString = new String[((StringTokenizer)localObject4).countTokens()];
        for (int j = 0; ((StringTokenizer)localObject4).hasMoreTokens(); j++) {
          arrayOfString[j] = ((StringTokenizer)localObject4).nextToken();
        }
      }
      localHashMap2.put("TransType", arrayOfString);
      if (paramInt == 2)
      {
        if (paramPagingContext.getStartDate() == null) {
          paramPagingContext.setStartDate(GregorianCalendar.getInstance());
        }
        localObject4 = new String[] { "WILLPROCESSON", "BATCH_INPROCESS", "IMMED_INPROCESS", "INPROCESS", "FUNDSPROCESSED" };
        localHashMap2.put("StatusList", localObject4);
      }
      else if (paramInt == 5)
      {
        if (paramPagingContext.getStartDate() == null)
        {
          localObject4 = GregorianCalendar.getInstance();
          ((Calendar)localObject4).add(5, -90);
          paramPagingContext.setStartDate((Calendar)localObject4);
        }
        localObject4 = new String[] { "PROCESSEDON", "POSTEDON", "NOFUNDSON", "NOFUNDSON_NOTIF", "FAILEDON", "FAILEDON_NOTIF", "LIMIT_CHECK_FAILED", "LIMIT_CHECK_FAILED_NOTIF", "LIMIT_REVERT_FAILED", "APPROVAL_FAILED" };
        localHashMap2.put("StatusList", localObject4);
      }
      else if (paramHashMap.get("Statuses") != null)
      {
        localHashMap2.put("StatusList", paramHashMap.get("Statuses"));
      }
    }
    Object localObject1 = new PagingInfo();
    ((PagingInfo)localObject1).setPagingContext(paramPagingContext);
    Object localObject2 = getBPWHandler();
    if (localObject2 == null) {
      throw new BankingException(31023);
    }
    try
    {
      localObject1 = ((BPWServices)localObject2).getPagedTransfer((PagingInfo)localObject1);
    }
    catch (Exception localException2)
    {
      DebugLog.throwing("getTransfersPage ", localException2);
      throw new BankingException(1033);
    }
    finally
    {
      removeBPWHandler((BPWServices)localObject2);
    }
    ArrayList localArrayList = ((PagingInfo)localObject1).getPagingResult();
    if (localArrayList != null)
    {
      localObject3 = new HashMap();
      localObject4 = localArrayList.iterator();
      while (((Iterator)localObject4).hasNext())
      {
        TransferInfo localTransferInfo = (TransferInfo)((Iterator)localObject4).next();
        Transfer localTransfer1 = (Transfer)localTransfers.create();
        localTransfer1.setDisplayRestrictedAccount(this.restrictAccounts);
        localTransfer1.setTransferInfo(localTransferInfo, paramAccounts);
        String str = localTransferInfo.getSourceRecSrvrTId();
        if (str != null)
        {
          localTransfer1.setType(2);
          RecTransfer localRecTransfer = (RecTransfer)((HashMap)localObject3).get(str);
          if (localRecTransfer == null)
          {
            Transfer localTransfer2 = new Transfer();
            localTransfer2.setID(str);
            localTransfer2.setTransferDestination(localTransfer1.getTransferDestination());
            localRecTransfer = getRecTransferByID(paramSecureUser, localTransfer2, paramHashMap);
            if (localRecTransfer != null) {
              ((HashMap)localObject3).put(str, localRecTransfer);
            }
          }
          if (localRecTransfer != null)
          {
            localTransfer1.put("Frequency", localRecTransfer.getFrequency());
            localTransfer1.put("NumberTransfers", localRecTransfer.getNumberTransfers());
          }
        }
      }
    }
    return localTransfers;
  }
  
  public RecTransfer getRecTransferByID(SecureUser paramSecureUser, String paramString, Accounts paramAccounts, HashMap paramHashMap)
    throws CSILException
  {
    if (paramHashMap == null) {
      paramHashMap = new HashMap();
    }
    paramHashMap.put("ACCOUNTS", paramAccounts);
    Transfer localTransfer = new Transfer();
    localTransfer.setID(paramString);
    RecTransfer localRecTransfer = null;
    try
    {
      localRecTransfer = getRecTransferByID(paramSecureUser, localTransfer, paramHashMap);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("getRecTransferByID ", localException);
    }
    return localRecTransfer;
  }
  
  public RecTransfer getRecTransferByID(SecureUser paramSecureUser, Transfer paramTransfer, HashMap paramHashMap)
    throws BankingException
  {
    String str = "Banking.getRecTransferByID";
    Locale localLocale = paramSecureUser.getLocale();
    Accounts localAccounts = (Accounts)paramHashMap.get("ACCOUNTS");
    if (paramTransfer.getTransferDestination() == null) {
      paramTransfer.setTransferDestination("INTERNAL");
    }
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new BankingException(31023);
    }
    RecTransfer localRecTransfer = new RecTransfer(localLocale);
    try
    {
      RecTransferInfo localRecTransferInfo = localBPWServices.getRecTransferBySrvrTId(paramTransfer.getID());
      if (localRecTransferInfo.getStatusCode() != 0)
      {
        DebugLog.log(Level.INFO, "Error retrieving recurring transfer with id " + paramTransfer.getID());
        DebugLog.log(Level.INFO, "*** BPW ErrorCode: " + localRecTransferInfo.getStatusCode());
        DebugLog.log(Level.INFO, "*** BPW ErrorMsg: " + localRecTransferInfo.getStatusMsg());
      }
      else
      {
        localRecTransfer.setDisplayRestrictedAccount(this.restrictAccounts);
        localRecTransfer.setRecTransferInfo(localRecTransferInfo, localAccounts);
        localRecTransfer.setFrequency(getFrequency(localRecTransferInfo.getFrequency()));
      }
    }
    catch (Exception localException)
    {
      DebugLog.throwing(str, localException);
    }
    finally
    {
      removeBPWHandler(localBPWServices);
    }
    return localRecTransfer;
  }
  
  public Transfer getTransferByID(SecureUser paramSecureUser, Transfer paramTransfer, HashMap paramHashMap)
    throws BankingException
  {
    String str = "Banking.getTransferByID";
    Accounts localAccounts = (Accounts)paramHashMap.get("ACCOUNTS");
    if (paramTransfer.getTransferDestination() == null) {
      paramTransfer.setTransferDestination("INTERNAL");
    }
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new BankingException(31023);
    }
    Transfer localTransfer = new Transfer(paramSecureUser.getLocale());
    try
    {
      TransferInfo localTransferInfo = localBPWServices.getTransferBySrvrTId(paramTransfer.getID(), paramTransfer.getTransferDestination());
      if (localTransferInfo.getStatusCode() != 0)
      {
        DebugLog.log(Level.INFO, "Error retrieving transfer with id " + paramTransfer.getID());
        DebugLog.log(Level.INFO, "*** BPW ErrorCode: " + localTransferInfo.getStatusCode());
        DebugLog.log(Level.INFO, "*** BPW ErrorMsg: " + localTransferInfo.getStatusMsg());
      }
      else
      {
        localTransfer.setDisplayRestrictedAccount(this.restrictAccounts);
        localTransfer.setTransferInfo(localTransferInfo, localAccounts);
      }
    }
    catch (Exception localException)
    {
      DebugLog.throwing(str, localException);
    }
    finally
    {
      removeBPWHandler(localBPWServices);
    }
    return localTransfer;
  }
  
  public Transfers getTransferTemplates(SecureUser paramSecureUser, Transfer paramTransfer, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    return a(paramSecureUser, paramTransfer, paramPagingContext, paramHashMap);
  }
  
  private Transfers a(SecureUser paramSecureUser, Transfer paramTransfer, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    Transfers localTransfers = new Transfers(paramSecureUser.getLocale());
    localTransfers.setPagingContext(paramPagingContext);
    HashMap localHashMap1 = paramPagingContext.getMap();
    if (localHashMap1 == null) {
      localHashMap1 = new HashMap();
    }
    HashMap localHashMap2 = new HashMap();
    localHashMap1.put("SEARCH_CRITERIA", localHashMap2);
    paramPagingContext.setMap(localHashMap1);
    getClass();
    int i = 10;
    try
    {
      i = jdMethod_new(paramHashMap);
    }
    catch (Exception localException1)
    {
      DebugLog.throwing("getTransfersPage ", localException1);
    }
    localHashMap1.put("PAGE_SIZE", String.valueOf(i));
    Object localObject3;
    Object localObject4;
    if (paramPagingContext.getDirection().equalsIgnoreCase("FIRST"))
    {
      localHashMap2.put("CustomerId", paramTransfer.getCustomerID());
      localHashMap2.put("FIID", paramSecureUser.getBPWFIID());
      localHashMap2.put("CategoryList", "USER_ENTRY");
      localObject1 = new String[] { "TEMPLATE", "RECTEMPLATE" };
      localHashMap2.put("TransType", localObject1);
      localObject2 = new String[] { "TEMPLATE", "RECTEMPLATE" };
      localHashMap2.put("StatusList", localObject2);
      String[] arrayOfString = null;
      localObject3 = paramTransfer.getTransferDestination();
      if ((localObject3 == null) || (((String)localObject3).equals("BOTH"))) {
        arrayOfString = new String[] { "ITOI", "INTERNAL", "ITOE", "ETOI" };
      } else {
        arrayOfString = new String[] { localObject3 };
      }
      localHashMap2.put("Dests", arrayOfString);
      if (paramPagingContext.getStartDate() == null)
      {
        localObject4 = GregorianCalendar.getInstance();
        ((Calendar)localObject4).add(1, -5);
        paramPagingContext.setStartDate((Calendar)localObject4);
      }
      if (paramPagingContext.getEndDate() == null)
      {
        localObject4 = GregorianCalendar.getInstance();
        ((Calendar)localObject4).add(5, 30);
        paramPagingContext.setEndDate((Calendar)localObject4);
      }
    }
    Object localObject1 = new PagingInfo();
    ((PagingInfo)localObject1).setPagingContext(paramPagingContext);
    Object localObject2 = getBPWHandler();
    if (localObject2 == null) {
      throw new BankingException(31023);
    }
    try
    {
      localObject1 = ((BPWServices)localObject2).getPagedTransfer((PagingInfo)localObject1);
    }
    catch (Exception localException2)
    {
      DebugLog.throwing("getTransfersPage ", localException2);
      throw new BankingException(1033);
    }
    finally
    {
      removeBPWHandler((BPWServices)localObject2);
    }
    ArrayList localArrayList = ((PagingInfo)localObject1).getPagingResult();
    if (localArrayList != null)
    {
      localObject3 = new HashMap();
      localObject4 = (Accounts)paramHashMap.get("ACCOUNTS");
      Iterator localIterator = localArrayList.iterator();
      while (localIterator.hasNext())
      {
        TransferInfo localTransferInfo = (TransferInfo)localIterator.next();
        Transfer localTransfer1 = (Transfer)localTransfers.createNoAdd();
        localTransfer1.setDisplayRestrictedAccount(this.restrictAccounts);
        localTransfer1.setTransferInfo(localTransferInfo, (Accounts)localObject4);
        if (localTransfer1.getAccountEntitledValue())
        {
          localTransfers.add(localTransfer1);
          String str = localTransferInfo.getSourceRecSrvrTId();
          if (str != null)
          {
            localTransfer1.setType(2);
            RecTransfer localRecTransfer = (RecTransfer)((HashMap)localObject3).get(str);
            if (localRecTransfer == null)
            {
              Transfer localTransfer2 = new Transfer();
              localTransfer2.setID(str);
              localTransfer2.setTransferDestination(localTransfer1.getTransferDestination());
              localRecTransfer = getRecTransferByID(paramSecureUser, localTransfer2, paramHashMap);
              if (localRecTransfer != null) {
                ((HashMap)localObject3).put(str, localRecTransfer);
              }
            }
            if (localRecTransfer != null)
            {
              localTransfer1.put("Frequency", localRecTransfer.getFrequency());
              localTransfer1.put("NumberTransfers", localRecTransfer.getNumberTransfers());
            }
          }
        }
      }
    }
    return localTransfers;
  }
  
  private int jdMethod_new(HashMap paramHashMap)
    throws BankingException
  {
    getClass();
    int i = 10;
    if (paramHashMap != null)
    {
      Object localObject = paramHashMap.get("PAGESIZE");
      if (localObject != null) {
        try
        {
          String str = (String)localObject;
          i = Integer.parseInt(str);
        }
        catch (ClassCastException localClassCastException)
        {
          throw new BankingException(3);
        }
        catch (NumberFormatException localNumberFormatException)
        {
          throw new BankingException(3);
        }
      }
    }
    return i;
  }
  
  private ExtTransferAcctInfo jdMethod_do(String paramString)
  {
    ExtTransferAcctInfo localExtTransferAcctInfo = new ExtTransferAcctInfo();
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {}
    localExtTransferAcctInfo.setAcctId(paramString);
    try
    {
      localExtTransferAcctInfo = localBPWServices.getExtTransferAccount(localExtTransferAcctInfo);
    }
    catch (Exception localException)
    {
      localException.printStackTrace(System.err);
    }
    finally
    {
      if (localBPWServices != null) {
        removeBPWHandler(localBPWServices);
      }
    }
    return localExtTransferAcctInfo;
  }
  
  protected int mapError(int paramInt)
  {
    int i = 1;
    switch (paramInt)
    {
    case 0: 
      i = 0;
      break;
    case 2000: 
      i = 1;
      break;
    case 2001: 
      i = 1011;
      break;
    case 2002: 
      i = 1011;
      break;
    case 2003: 
      i = 1011;
      break;
    case 2004: 
      i = 1011;
      break;
    case 2005: 
      i = 1011;
      break;
    case 2006: 
      i = 1000;
      break;
    case 2007: 
      i = 1011;
      break;
    case 2008: 
      i = 1011;
      break;
    case 2009: 
      i = 1001;
      break;
    case 2010: 
      i = 1011;
      break;
    case 2011: 
      i = 1011;
      break;
    case 2012: 
      i = 1008;
      break;
    case 2014: 
      i = 1009;
      break;
    case 2015: 
      i = 1010;
      break;
    case 2016: 
      i = 1012;
      break;
    case 2017: 
      i = 1012;
      break;
    case 2018: 
      i = 3;
      break;
    case 2019: 
      i = 1012;
      break;
    case 10504: 
      i = 1006;
      break;
    case 10505: 
      i = 2;
      break;
    case 10508: 
      i = 1013;
      break;
    case 10509: 
      i = 1012;
      break;
    case 10518: 
      i = 3;
      break;
    case 1002000: 
      i = 1075;
      break;
    case 100000: 
      i = 1045;
      break;
    case 100002: 
      i = 1076;
      break;
    default: 
      i = super.mapError(paramInt);
    }
    return i;
  }
  
  private static String jdMethod_if(Accounts paramAccounts, String paramString)
  {
    if ((paramAccounts == null) || (paramAccounts.isEmpty())) {
      return paramString;
    }
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",");
    StringBuffer localStringBuffer1 = new StringBuffer();
    while (localStringTokenizer.hasMoreTokens())
    {
      String str = localStringTokenizer.nextToken();
      StringBuffer localStringBuffer2 = new StringBuffer();
      for (int i = 0; i < paramAccounts.size(); i++)
      {
        Account localAccount = (Account)paramAccounts.get(i);
        if (localAccount.getID().equals(str))
        {
          localStringBuffer2.append(localAccount.getDisplayTextRoutingNumNickNameCurrency());
          break;
        }
      }
      if (localStringBuffer2.length() > 0)
      {
        if (localStringBuffer1.length() > 0) {
          localStringBuffer1.append("&&");
        }
        localStringBuffer1.append(localStringBuffer2);
      }
      else
      {
        return paramString;
      }
    }
    return localStringBuffer1.length() > 0 ? localStringBuffer1.toString() : paramString;
  }
  
  private static int jdMethod_do(Account paramAccount, ReportResult paramReportResult)
    throws Exception
  {
    Locale localLocale = paramReportResult.getLocale();
    a[] arrayOfa = cb;
    ReportResult localReportResult = new ReportResult(localLocale);
    paramReportResult.addSubReport(localReportResult);
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(localLocale);
    localReportDataDimensions.setNumColumns(arrayOfa.length);
    localReportDataDimensions.setNumRows(-1);
    localReportResult.setDataDimensions(localReportDataDimensions);
    a.a(localReportResult, localLocale, arrayOfa);
    int i = 0;
    Transactions localTransactions = paramAccount.getTransactions();
    Iterator localIterator = localTransactions.iterator();
    while (localIterator.hasNext())
    {
      Transaction localTransaction = (Transaction)localIterator.next();
      ReportRow localReportRow = new ReportRow(localLocale);
      ReportDataItems localReportDataItems = new ReportDataItems(localLocale);
      localReportRow.setDataItems(localReportDataItems);
      localReportDataItems.add().setData(paramAccount.getID());
      localReportDataItems.add().setData(paramAccount.getBankID());
      localReportDataItems.add().setData(paramAccount.getRoutingNum());
      localReportDataItems.add().setData(paramAccount.getCurrencyCode());
      localReportDataItems.add().setData(localTransaction.getID());
      localReportDataItems.add().setData(new Integer(localTransaction.getTypeValue()));
      localReportDataItems.add().setData(new Integer(localTransaction.getCategory()));
      localReportDataItems.add().setData(localTransaction.getDescription());
      localReportDataItems.add().setData(localTransaction.getReferenceNumber());
      localReportDataItems.add().setData(localTransaction.getMemo());
      localReportDataItems.add().setData(localTransaction.getAmountValue());
      localReportDataItems.add().setData(localTransaction.getRunningBalanceValue());
      localReportDataItems.add().setData(localTransaction.getTrackingID());
      localReportDataItems.add().setData(new Integer((int)localTransaction.getTransactionIndex()));
      localReportDataItems.add().setData(localTransaction.getPostingDate());
      localReportDataItems.add().setData(localTransaction.getDueDate());
      localReportDataItems.add().setData(new Float(localTransaction.getFixedDepositRate()));
      localReportDataItems.add().setData(localTransaction.getPayeePayor());
      localReportDataItems.add().setData(localTransaction.getPayorNum());
      localReportDataItems.add().setData(localTransaction.getOrigUser());
      localReportDataItems.add().setData(localTransaction.getPONum());
      localReportDataItems.add().setData(localTransaction.getImmediateAvailAmount());
      localReportDataItems.add().setData(localTransaction.getOneDayAvailAmount());
      localReportDataItems.add().setData(localTransaction.getMoreThanOneDayAvailAmount());
      localReportDataItems.add().setData(localTransaction.getBankReferenceNumber());
      localReportDataItems.add().setData(localTransaction.getCustomerReferenceNumber());
      localReportDataItems.add().setData(localTransaction.getProcessingDate());
      localReportDataItems.add().setData(localTransaction.getInstrumentNumber());
      localReportDataItems.add().setData(localTransaction.getInstrumentBankName());
      localReportDataItems.add().setData(localTransaction.getValueDate());
      localReportDataItems.add().setData(new Integer(localTransaction.getSubTypeValue()));
      localReportDataItems.add().setData(localTransaction.getDateValue());
      localReportDataItems.add().setData("P");
      localReportResult.addRow(localReportRow);
      i++;
    }
    return i;
  }
  
  private static int jdMethod_if(Account paramAccount, ReportResult paramReportResult)
    throws Exception
  {
    Locale localLocale = paramReportResult.getLocale();
    a[] arrayOfa = b2;
    int i = Account.getAccountSystemTypeFromGroup(paramAccount.getAccountGroup());
    if (i == 1) {
      arrayOfa = b2;
    } else if (i == 3) {
      arrayOfa = b8;
    } else if (i == 2) {
      arrayOfa = b5;
    } else if (i == 4) {
      arrayOfa = cd;
    }
    ReportResult localReportResult = new ReportResult(localLocale);
    paramReportResult.addSubReport(localReportResult);
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(localLocale);
    localReportDataDimensions.setNumColumns(arrayOfa.length);
    localReportDataDimensions.setNumRows(-1);
    localReportResult.setDataDimensions(localReportDataDimensions);
    a.a(localReportResult, localLocale, arrayOfa);
    int j = 0;
    Transactions localTransactions = paramAccount.getTransactions();
    Iterator localIterator = localTransactions.iterator();
    while (localIterator.hasNext())
    {
      Transaction localTransaction = (Transaction)localIterator.next();
      ReportRow localReportRow = new ReportRow(localLocale);
      if (j % 2 == 1) {
        localReportRow.set("CELLBACKGROUND", "reportDataShaded");
      }
      ReportDataItems localReportDataItems = new ReportDataItems(localLocale);
      localReportRow.setDataItems(localReportDataItems);
      if (i == 1)
      {
        localReportDataItems.add().setData(localTransaction.getProcessingDate());
        localReportDataItems.add().setData(localTransaction.getType());
        localReportDataItems.add().setData(localTransaction.getDescription());
        localReportDataItems.add().setData(localTransaction.getReferenceNumber());
        localReportDataItems.add().setData(localTransaction.getDebitValueAbsolute());
        localReportDataItems.add().setData(localTransaction.getCreditValue());
        localReportDataItems.add().setData(localTransaction.getRunningBalanceValue());
      }
      else if (i == 3)
      {
        localReportDataItems.add().setData(localTransaction.getProcessingDate());
        localReportDataItems.add().setData(localTransaction.getDescription());
        localReportDataItems.add().setData(localTransaction.getAmountValue());
        localReportDataItems.add().setData(localTransaction.getDueDate());
        localReportDataItems.add().setData(localTransaction.getRunningBalanceValue());
      }
      else if (i == 2)
      {
        localReportDataItems.add().setData(localTransaction.getProcessingDate());
        localReportDataItems.add().setData(localTransaction.getType());
        localReportDataItems.add().setData(localTransaction.getDescription());
        localReportDataItems.add().setData(localTransaction.getCreditValue());
        localReportDataItems.add().setData(localTransaction.getDebitValueAbsolute());
        localReportDataItems.add().setData(localTransaction.getRunningBalanceValue());
      }
      else if (i == 4)
      {
        localReportDataItems.add().setData(localTransaction.getProcessingDate());
        localReportDataItems.add().setData(localTransaction.getDescription());
        localReportDataItems.add().setData(localTransaction.getDebitValueAbsolute());
        localReportDataItems.add().setData(localTransaction.getCreditValue());
        localReportDataItems.add().setData(localTransaction.getRunningBalanceValue());
      }
      localReportResult.addRow(localReportRow);
      j++;
    }
    return j;
  }
  
  private static int a(Account paramAccount, ReportResult paramReportResult)
    throws Exception
  {
    Locale localLocale = paramReportResult.getLocale();
    a[] arrayOfa = cb;
    ReportResult localReportResult = new ReportResult(localLocale);
    paramReportResult.addSubReport(localReportResult);
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(localLocale);
    localReportDataDimensions.setNumColumns(arrayOfa.length);
    localReportDataDimensions.setNumRows(-1);
    localReportResult.setDataDimensions(localReportDataDimensions);
    a.a(localReportResult, localLocale, arrayOfa);
    int i = 0;
    Transactions localTransactions = paramAccount.getTransactions();
    Iterator localIterator = localTransactions.iterator();
    while (localIterator.hasNext())
    {
      Transaction localTransaction = (Transaction)localIterator.next();
      ReportRow localReportRow = new ReportRow(localLocale);
      ReportDataItems localReportDataItems = new ReportDataItems(localLocale);
      localReportRow.setDataItems(localReportDataItems);
      localReportDataItems.add().setData(paramAccount.getID());
      localReportDataItems.add().setData(paramAccount.getBankID());
      localReportDataItems.add().setData(paramAccount.getRoutingNum());
      localReportDataItems.add().setData(paramAccount.getCurrencyCode());
      localReportDataItems.add().setData(localTransaction.getID());
      localReportDataItems.add().setData(new Integer(localTransaction.getTypeValue()));
      localReportDataItems.add().setData(new Integer(localTransaction.getCategory()));
      localReportDataItems.add().setData(localTransaction.getDescription());
      localReportDataItems.add().setData(localTransaction.getReferenceNumber());
      localReportDataItems.add().setData(localTransaction.getMemo());
      localReportDataItems.add().setData(localTransaction.getAmountValue());
      localReportDataItems.add().setData(localTransaction.getRunningBalanceValue());
      localReportDataItems.add().setData(localTransaction.getTrackingID());
      localReportDataItems.add().setData(new Integer((int)localTransaction.getTransactionIndex()));
      localReportDataItems.add().setData(localTransaction.getPostingDate());
      localReportDataItems.add().setData(localTransaction.getDueDate());
      localReportDataItems.add().setData(new Float(localTransaction.getFixedDepositRate()));
      localReportDataItems.add().setData(localTransaction.getPayeePayor());
      localReportDataItems.add().setData(localTransaction.getPayorNum());
      localReportDataItems.add().setData(localTransaction.getOrigUser());
      localReportDataItems.add().setData(localTransaction.getPONum());
      localReportDataItems.add().setData(localTransaction.getImmediateAvailAmount());
      localReportDataItems.add().setData(localTransaction.getOneDayAvailAmount());
      localReportDataItems.add().setData(localTransaction.getMoreThanOneDayAvailAmount());
      localReportDataItems.add().setData(localTransaction.getBankReferenceNumber());
      localReportDataItems.add().setData(localTransaction.getCustomerReferenceNumber());
      localReportDataItems.add().setData(localTransaction.getProcessingDate());
      localReportDataItems.add().setData(localTransaction.getInstrumentNumber());
      localReportDataItems.add().setData(localTransaction.getInstrumentBankName());
      localReportDataItems.add().setData(localTransaction.getValueDate());
      localReportDataItems.add().setData(new Integer(localTransaction.getSubTypeValue()));
      localReportDataItems.add().setData(localTransaction.getDateValue());
      localReportDataItems.add().setData(localTransaction.getDataClassification());
      localReportResult.addRow(localReportRow);
      i++;
    }
    return i;
  }
  
  private static int a(Account paramAccount, ReportResult paramReportResult, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws Exception
  {
    Locale localLocale = paramReportResult.getLocale();
    int i = 0;
    int j = 1;
    int k = 1;
    String str1 = (String)paramReportCriteria.getSearchCriteria().get("ShowBalance");
    if ((str1 == null) || (!str1.equalsIgnoreCase("true"))) {
      j = 0;
    }
    String str2 = (String)paramReportCriteria.getSearchCriteria().get("ShowMemo");
    if ((str2 == null) || (!str2.equalsIgnoreCase("true"))) {
      k = 0;
    }
    a[] arrayOfa1 = bW;
    a[] arrayOfa2 = b4;
    a[] arrayOfa3 = ch;
    if (j == 0)
    {
      arrayOfa3 = bX;
      arrayOfa2 = cg;
    }
    Transactions localTransactions = paramAccount.getTransactions();
    Object localObject1;
    Object localObject2;
    Object localObject3;
    Object localObject4;
    if (localTransactions.size() == 0)
    {
      arrayOfa3 = ca;
      ReportResult localReportResult = new ReportResult(localLocale);
      paramReportResult.addSubReport(localReportResult);
      ReportHeading localReportHeading = new ReportHeading(localLocale);
      localObject1 = new Object[1];
      localObject1[0] = paramAccount.getConsumerExportDisplayText();
      localObject2 = new LocalizableString("com.ffusion.beans.reporting.reports", "Dyn_Account", (Object[])localObject1);
      localReportHeading.setLabel((String)((ILocalizable)localObject2).localize(paramReportCriteria.getLocale()));
      localReportResult.setHeading(localReportHeading);
      a(localReportResult, arrayOfa3.length, -1);
      a.jdMethod_if(paramReportResult, localLocale, arrayOfa3);
      localObject3 = new ReportRow(localLocale);
      localObject4 = new ReportDataItems(localLocale);
      ((ReportRow)localObject3).setDataItems((ReportDataItems)localObject4);
      ((ReportDataItems)localObject4).add().setData(ReportConsts.getText(10504, paramReportCriteria.getLocale()));
      paramReportResult.addRow((ReportRow)localObject3);
      i = 2;
    }
    else
    {
      int m = 1;
      int n = 0;
      localObject1 = localTransactions.iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = new ReportResult(localLocale);
        paramReportResult.addSubReport((ReportResult)localObject2);
        if (n == 0)
        {
          localObject3 = new ReportHeading(localLocale);
          localObject4 = new Object[1];
          localObject4[0] = paramAccount.getConsumerExportDisplayText();
          localObject5 = new LocalizableString("com.ffusion.beans.reporting.reports", "Dyn_Account", (Object[])localObject4);
          ((ReportHeading)localObject3).setLabel((String)((ILocalizable)localObject5).localize(paramReportCriteria.getLocale()));
          ((ReportResult)localObject2).setHeading((ReportHeading)localObject3);
          a((ReportResult)localObject2, arrayOfa2.length, -1);
          a.a((ReportResult)localObject2, localLocale, arrayOfa3);
          n = 1;
        }
        else
        {
          ((ReportResult)localObject2).setHeading(null);
          a((ReportResult)localObject2, arrayOfa2.length, -1);
          a.jdMethod_if((ReportResult)localObject2, localLocale, arrayOfa2);
        }
        localObject3 = (Transaction)((Iterator)localObject1).next();
        localObject4 = new ReportRow(localLocale);
        if (m % 2 == 0) {
          ((ReportRow)localObject4).set("CELLBACKGROUND", "reportDataShaded");
        }
        Object localObject5 = new ReportDataItems(localLocale);
        ((ReportRow)localObject4).setDataItems((ReportDataItems)localObject5);
        ((ReportDataItems)localObject5).add().setData(((Transaction)localObject3).getProcessingDate());
        ((ReportDataItems)localObject5).add().setData(((Transaction)localObject3).getDescription());
        ((ReportDataItems)localObject5).add().setData(((Transaction)localObject3).getType());
        ((ReportDataItems)localObject5).add().setData(((Transaction)localObject3).getReferenceNumber());
        ((ReportDataItems)localObject5).add().setData(((Transaction)localObject3).getDebitValueAbsolute());
        ((ReportDataItems)localObject5).add().setData(((Transaction)localObject3).getCreditValue());
        if ((j != 0) && (!"P".equals(((Transaction)localObject3).getDataClassification()))) {
          ((ReportDataItems)localObject5).add().setData(null);
        } else if (j != 0) {
          ((ReportDataItems)localObject5).add().setData(((Transaction)localObject3).getRunningBalanceValue());
        }
        ((ReportResult)localObject2).addRow((ReportRow)localObject4);
        i++;
        if (k != 0)
        {
          localObject2 = new ReportResult(localLocale);
          paramReportResult.addSubReport((ReportResult)localObject2);
          ((ReportResult)localObject2).setHeading(null);
          a((ReportResult)localObject2, arrayOfa1.length, -1);
          a.jdMethod_if((ReportResult)localObject2, localLocale, arrayOfa1);
          localObject4 = new ReportRow(localLocale);
          if (m % 2 == 0) {
            ((ReportRow)localObject4).set("CELLBACKGROUND", "reportDataShaded");
          }
          localObject5 = new ReportDataItems(localLocale);
          ((ReportRow)localObject4).setDataItems((ReportDataItems)localObject5);
          ((ReportDataItems)localObject5).add().setData(null);
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = ((Transaction)localObject3).getMemo();
          LocalizableString localLocalizableString = new LocalizableString("com.ffusion.beans.reporting.reports", "Dyn_Memo", arrayOfObject);
          ((ReportDataItems)localObject5).add().setData((String)localLocalizableString.localize(paramReportCriteria.getLocale()));
          ((ReportResult)localObject2).addRow((ReportRow)localObject4);
          i++;
        }
        m++;
      }
    }
    a(paramReportResult);
    return i;
  }
  
  private static boolean jdMethod_int(String paramString)
  {
    if (("".equals(paramString)) || (paramString.startsWith(","))) {
      return true;
    }
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",", false);
    while (localStringTokenizer.hasMoreTokens())
    {
      String str = localStringTokenizer.nextToken();
      if (("AllAccounts".equals(str)) || ("AllAccounts".equals(str)) || ("".equals(str))) {
        return true;
      }
    }
    return false;
  }
  
  private static int jdMethod_if(int paramInt1, int paramInt2)
  {
    int i = 1;
    switch (paramInt1)
    {
    case 23610: 
      i = 1009;
      break;
    case 19180: 
      i = 1035;
      break;
    case 21040: 
      i = 1036;
      break;
    case 21050: 
      i = 1037;
      break;
    case 21060: 
      i = 1074;
      break;
    case 19100: 
      i = 1038;
      break;
    case 17200: 
      i = 1039;
      break;
    case 19350: 
      i = 1040;
      break;
    case 21030: 
      i = 1041;
      break;
    case 17201: 
      i = 1042;
      break;
    case 19130: 
      i = 1043;
      break;
    case 21080: 
      i = 1044;
      break;
    case 17203: 
      i = 1045;
      break;
    case 21000: 
      i = 1046;
      break;
    case 21010: 
      i = 1047;
      break;
    case 21020: 
      i = 1048;
      break;
    case 21140: 
      i = 1049;
      break;
    case 21150: 
      i = 1050;
      break;
    case 21170: 
      i = 1051;
      break;
    case 19170: 
      i = 1052;
      break;
    case 19200: 
      i = 1053;
      break;
    case 17510: 
      i = 1054;
      break;
    case 21160: 
      i = 1055;
      break;
    case 19280: 
      i = 1056;
      break;
    case 19340: 
      i = 1057;
      break;
    case 19390: 
      i = 1058;
      break;
    case 17205: 
      i = 1059;
      break;
    case 17206: 
      i = 1060;
      break;
    case 17080: 
      i = 1061;
      break;
    case 17521: 
      i = 20003;
      break;
    case 17523: 
      i = 20011;
      break;
    case 17524: 
      i = 30216;
      break;
    case 27012: 
      i = 1062;
      break;
    case 27013: 
      i = 1063;
      break;
    case 27014: 
      i = 1064;
      break;
    case 27015: 
      i = 1065;
      break;
    case 27016: 
      i = 1066;
      break;
    case 21070: 
      i = 1068;
      break;
    case 21180: 
      i = 1069;
      break;
    case 21261: 
      i = 1070;
      break;
    case 21262: 
      i = 1071;
      break;
    case 21263: 
      i = 1072;
      break;
    case 21264: 
      i = 1073;
      break;
    case 21710: 
      i = 1084;
      break;
    case 17522: 
      i = 30200;
      break;
    default: 
      i = paramInt2;
    }
    return i;
  }
  
  private static boolean jdMethod_for(String paramString)
  {
    return (paramString == null) || (paramString.length() == 0);
  }
  
  private static String jdMethod_for(String paramString1, String paramString2, String paramString3)
  {
    try
    {
      SimpleDateFormat localSimpleDateFormat1 = new SimpleDateFormat(paramString2);
      SimpleDateFormat localSimpleDateFormat2 = new SimpleDateFormat(paramString3);
      Date localDate = localSimpleDateFormat1.parse(paramString1);
      return localSimpleDateFormat2.format(localDate);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return paramString1;
  }
  
  private static IReportResult a(ReportCriteria paramReportCriteria, Transfers paramTransfers, Currency paramCurrency, String paramString, HashMap paramHashMap1, boolean paramBoolean, HashMap paramHashMap2, HashMap paramHashMap3)
    throws BankingException
  {
    ReportResult localReportResult = new ReportResult(paramReportCriteria.getLocale());
    Locale localLocale = localReportResult.getLocale();
    int i = 0;
    String str1 = "≈";
    Properties localProperties = paramReportCriteria.getReportOptions();
    String str2 = localProperties.getProperty("DATEFORMAT");
    if (str2 == null) {
      str2 = "SHORT";
    }
    Accounts localAccounts = (Accounts)paramHashMap1.get("Accounts");
    int j = Integer.parseInt(a(paramReportCriteria.getReportOptions(), "MAXDISPLAYSIZE", String.valueOf(2147483647)));
    int k = 0;
    String str3 = a(paramReportCriteria.getSearchCriteria(), "FromAccount", "");
    if ("".equals(str3)) {
      paramReportCriteria.setDisplayValue("FromAccount", ReportConsts.getText(10024, localLocale));
    } else {
      paramReportCriteria.setDisplayValue("FromAccount", jdMethod_if(localAccounts, str3));
    }
    String str4 = a(paramReportCriteria.getSearchCriteria(), "ToAccount", "");
    if ("".equals(str4)) {
      paramReportCriteria.setDisplayValue("ToAccount", ReportConsts.getText(10024, localLocale));
    } else {
      paramReportCriteria.setDisplayValue("ToAccount", jdMethod_if(localAccounts, str4));
    }
    String str5 = a(paramReportCriteria.getSearchCriteria(), "AmountFrom", "").trim();
    paramReportCriteria.setHiddenSearchCriterion("AmountFrom", "".equals(str5));
    String str6 = a(paramReportCriteria.getSearchCriteria(), "AmountTo", "").trim();
    paramReportCriteria.setHiddenSearchCriterion("AmountTo", "".equals(str6));
    try
    {
      localReportResult.init(paramReportCriteria);
      Object localObject1;
      Object localObject2;
      Object localObject3;
      Object localObject4;
      Object localObject5;
      Object localObject6;
      Object localObject7;
      Object localObject8;
      Object localObject9;
      Object localObject10;
      if (paramTransfers.size() > 0)
      {
        localObject1 = new ReportResult(localLocale);
        localReportResult.addSubReport((ReportResult)localObject1);
        localObject2 = new ReportDataDimensions(localLocale);
        ((ReportDataDimensions)localObject2).setNumColumns(paramBoolean ? 9 : 8);
        ((ReportDataDimensions)localObject2).setNumRows(-1);
        ((ReportResult)localObject1).setDataDimensions((ReportDataDimensions)localObject2);
        jdMethod_do((ReportResult)localObject1, 1906, "java.lang.String", 10, null);
        jdMethod_do((ReportResult)localObject1, 1890, "java.lang.String", 10, null);
        jdMethod_do((ReportResult)localObject1, 1899, "java.lang.String", 10, null);
        jdMethod_do((ReportResult)localObject1, 1892, "java.lang.String", 10, null);
        jdMethod_do((ReportResult)localObject1, 1893, "java.lang.String", 12, null);
        jdMethod_do((ReportResult)localObject1, 1894, "java.lang.String", 8, null);
        jdMethod_do((ReportResult)localObject1, 1895, "java.lang.String", 8, null);
        jdMethod_do((ReportResult)localObject1, paramBoolean ? 1907 : 1896, "java.lang.String", 8, "RIGHT");
        if (paramBoolean) {
          jdMethod_do((ReportResult)localObject1, 1908, "java.lang.String", 8, "RIGHT");
        }
        localObject3 = paramTransfers.iterator();
        localObject4 = null;
        localObject3 = paramTransfers.iterator();
        localObject4 = null;
        while ((((Iterator)localObject3).hasNext()) && (i < j))
        {
          localObject4 = (Transfer)((Iterator)localObject3).next();
          localObject5 = new ReportRow(((ReportResult)localObject1).getLocale());
          if (i % 2 == 1) {
            ((ReportRow)localObject5).set("CELLBACKGROUND", "reportDataShaded");
          }
          localObject6 = new ReportDataItems(((ReportResult)localObject1).getLocale());
          ((ReportRow)localObject5).setDataItems((ReportDataItems)localObject6);
          localObject7 = ((ReportDataItems)localObject6).add();
          a((ReportDataItem)localObject7, ((Transfer)localObject4).getDateToPost());
          localObject7 = ((ReportDataItems)localObject6).add();
          a((ReportDataItem)localObject7, ((Transfer)localObject4).getDatePosted());
          localObject7 = ((ReportDataItems)localObject6).add();
          localObject8 = ((Transfer)localObject4).getCreateDate();
          if (jdMethod_for((String)localObject8))
          {
            a((ReportDataItem)localObject7, "");
          }
          else
          {
            if (((String)localObject8).length() > 10) {
              localObject8 = ((String)localObject8).substring(0, 10);
            }
            localObject8 = jdMethod_for((String)localObject8, "yyyy/MM/dd", str2);
            a((ReportDataItem)localObject7, localObject8);
          }
          localObject7 = ((ReportDataItems)localObject6).add();
          localObject9 = ((Transfer)localObject4).getFromAccount().getDisplayTextRoutingNumNickNameCurrency();
          a((ReportDataItem)localObject7, localObject9);
          localObject7 = ((ReportDataItems)localObject6).add();
          localObject9 = ((Transfer)localObject4).getToAccount().getDisplayTextRoutingNumNickNameCurrency();
          a((ReportDataItem)localObject7, localObject9);
          localObject7 = ((ReportDataItems)localObject6).add();
          a((ReportDataItem)localObject7, ((Transfer)localObject4).getID());
          localObject7 = ((ReportDataItems)localObject6).add();
          a((ReportDataItem)localObject7, ((Transfer)localObject4).getStatusName());
          localObject7 = ((ReportDataItems)localObject6).add();
          localObject10 = null;
          if (((Transfer)localObject4).getAmountValue() != null) {
            localObject10 = ((Transfer)localObject4).getAmountValue().toString();
          }
          if ((((Transfer)localObject4).getIsAmountEstimated()) && (((Transfer)localObject4).getAmountValue() != null)) {
            localObject10 = str1.concat(((Transfer)localObject4).getAmountValue().toString());
          }
          a((ReportDataItem)localObject7, localObject10);
          localObject10 = null;
          if (((Transfer)localObject4).getToAmountValue() != null) {
            localObject10 = ((Transfer)localObject4).getToAmountValue().toString();
          }
          if (paramBoolean)
          {
            localObject7 = ((ReportDataItems)localObject6).add();
            if ((((Transfer)localObject4).getIsToAmountEstimated()) && (((Transfer)localObject4).getToAmountValue() != null)) {
              localObject10 = str1.concat(((Transfer)localObject4).getToAmountValue().toString());
            }
            a((ReportDataItem)localObject7, localObject10);
          }
          ((ReportResult)localObject1).addRow((ReportRow)localObject5);
          i++;
        }
        ((ReportDataDimensions)localObject2).setNumRows(i);
      }
      else
      {
        localObject1 = new ReportResult(localLocale);
        localReportResult.addSubReport((ReportResult)localObject1);
        localObject2 = null;
        localObject3 = new ReportDataDimensions(localLocale);
        ((ReportDataDimensions)localObject3).setNumColumns(1);
        ((ReportDataDimensions)localObject3).setNumRows(1);
        ((ReportResult)localObject1).setDataDimensions((ReportDataDimensions)localObject3);
        localObject2 = new ReportColumn(localLocale);
        ((ReportColumn)localObject2).setDataType("java.lang.String");
        ((ReportColumn)localObject2).setWidthAsPercent(100);
        ((ReportResult)localObject1).addColumn((ReportColumn)localObject2);
        localObject2 = null;
        localObject4 = new ReportRow(localLocale);
        localObject5 = new ReportDataItems(localLocale);
        localObject6 = null;
        ((ReportRow)localObject4).setDataItems((ReportDataItems)localObject5);
        localObject6 = ((ReportDataItems)localObject5).add();
        ((ReportDataItem)localObject6).setData(ReportConsts.getText(2501, localLocale));
        localObject6 = null;
        localObject5 = null;
        ((ReportResult)localObject1).addRow((ReportRow)localObject4);
        localObject4 = null;
      }
      if (paramBoolean)
      {
        localObject1 = paramHashMap2.keySet().iterator();
        while (((Iterator)localObject1).hasNext())
        {
          localObject2 = (String)((Iterator)localObject1).next();
          localObject3 = (Currency)paramHashMap2.get(localObject2);
          localObject4 = (Currency)paramHashMap3.get(localObject2);
          localObject5 = new ReportResult(localLocale);
          localReportResult.addSubReport((ReportResult)localObject5);
          localObject6 = new ReportDataDimensions(localLocale);
          ((ReportDataDimensions)localObject6).setNumColumns(3);
          ((ReportDataDimensions)localObject6).setNumRows(1);
          ((ReportResult)localObject5).setDataDimensions((ReportDataDimensions)localObject6);
          jdMethod_do((ReportResult)localObject5, -1, "java.lang.String", 67, "RIGHT");
          jdMethod_do((ReportResult)localObject5, -1, "java.lang.String", 8, "RIGHT");
          jdMethod_do((ReportResult)localObject5, -1, "java.lang.String", 8, "RIGHT");
          localObject7 = new ReportRow(((ReportResult)localObject5).getLocale());
          localObject8 = new ReportDataItems(((ReportResult)localObject5).getLocale());
          ((ReportRow)localObject7).setDataItems((ReportDataItems)localObject8);
          localObject9 = ((ReportDataItems)localObject8).add();
          localObject10 = new StringBuffer(ReportConsts.getText(1068, localLocale));
          ((StringBuffer)localObject10).append(" (");
          ((StringBuffer)localObject10).append((String)localObject2);
          ((StringBuffer)localObject10).append("): ");
          a((ReportDataItem)localObject9, ((StringBuffer)localObject10).toString());
          localObject9 = ((ReportDataItems)localObject8).add();
          a((ReportDataItem)localObject9, ((Currency)localObject3).getCurrencyStringNoSymbol());
          localObject9 = ((ReportDataItems)localObject8).add();
          a((ReportDataItem)localObject9, ((Currency)localObject4).getCurrencyStringNoSymbol());
          ((ReportResult)localObject5).addRow((ReportRow)localObject7);
        }
      }
      if ((i < j) && (i != 0))
      {
        localObject1 = new ReportResult(localLocale);
        localReportResult.addSubReport((ReportResult)localObject1);
        localObject2 = new ReportDataDimensions(localLocale);
        ((ReportDataDimensions)localObject2).setNumColumns(2);
        ((ReportDataDimensions)localObject2).setNumRows(1);
        ((ReportResult)localObject1).setDataDimensions((ReportDataDimensions)localObject2);
        jdMethod_do((ReportResult)localObject1, -1, "java.lang.String", 67, "RIGHT");
        jdMethod_do((ReportResult)localObject1, -1, "java.lang.String", 16, "RIGHT");
        localObject3 = new ReportRow(((ReportResult)localObject1).getLocale());
        localObject4 = new ReportDataItems(((ReportResult)localObject1).getLocale());
        ((ReportRow)localObject3).setDataItems((ReportDataItems)localObject4);
        localObject5 = ((ReportDataItems)localObject4).add();
        localObject6 = new StringBuffer(ReportConsts.getText(10049, localLocale));
        ((StringBuffer)localObject6).append(" (");
        ((StringBuffer)localObject6).append(paramCurrency.getCurrencyCode());
        ((StringBuffer)localObject6).append("): ");
        a((ReportDataItem)localObject5, ((StringBuffer)localObject6).toString());
        localObject5 = ((ReportDataItems)localObject4).add();
        a((ReportDataItem)localObject5, paramCurrency.getCurrencyStringNoSymbol());
        ((ReportResult)localObject1).addRow((ReportRow)localObject3);
      }
    }
    catch (Exception localException)
    {
      k = 1;
      localReportResult.setError(localException);
      throw new BankingException(413);
    }
    finally
    {
      if (i == j)
      {
        HashMap localHashMap = new HashMap();
        localHashMap.put("TRUNCATED", new Integer(i));
        localReportResult.setProperties(localHashMap);
      }
      try
      {
        localReportResult.fini();
      }
      catch (RptException localRptException)
      {
        if (k == 0) {
          throw new BankingException(413);
        }
      }
    }
    return localReportResult;
  }
  
  private static void jdMethod_do(ReportResult paramReportResult, int paramInt1, String paramString1, int paramInt2, String paramString2)
    throws RptException
  {
    Locale localLocale = paramReportResult.getLocale();
    ReportColumn localReportColumn = new ReportColumn(localLocale);
    if (paramInt1 > 0)
    {
      ArrayList localArrayList = new ArrayList();
      localArrayList.add(ReportConsts.getColumnName(paramInt1, localLocale));
      localReportColumn.setLabels(localArrayList);
    }
    if (paramString1 != null) {
      localReportColumn.setDataType(paramString1);
    }
    if (paramInt2 != 0) {
      localReportColumn.setWidthAsPercent(paramInt2);
    }
    if (paramString2 != null) {
      localReportColumn.setJustification(paramString2);
    }
    paramReportResult.addColumn(localReportColumn);
  }
  
  public Transactions getRecentTransactions(Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    return getRecentTransactions(paramAccount, paramHashMap);
  }
  
  public int getNumTransactions(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, Properties paramProperties, HashMap paramHashMap)
    throws BankingException
  {
    return getNumTransactions(paramAccount, paramCalendar1, paramCalendar2, paramHashMap);
  }
  
  public Transfers getPagedTransfers(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    FundsTransactions localFundsTransactions = getPagedFundsTransactions(paramSecureUser, paramPagingContext, paramHashMap);
    Transfers localTransfers = new Transfers();
    Iterator localIterator = localFundsTransactions.iterator();
    while (localIterator.hasNext())
    {
      FundsTransaction localFundsTransaction = (FundsTransaction)localIterator.next();
      if ((localFundsTransaction instanceof Transfer)) {
        localTransfers.add(localFundsTransaction);
      }
    }
    return localTransfers;
  }
  
  public FundsTransactions getPagedFundsTransactions(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    Locale localLocale = paramSecureUser.getLocale();
    Accounts localAccounts = (Accounts)paramHashMap.get("TRANSFER_ACCOUNTS");
    String str1 = (String)paramHashMap.get("TRANSFER_STATUS");
    String str2 = (String)paramHashMap.get("Dests");
    String str3 = (String)paramHashMap.get("TransType");
    if ((str3 == null) || (str2 == null)) {
      throw new BankingException(1033);
    }
    HashMap localHashMap = paramPagingContext.getMap();
    if (localHashMap == null)
    {
      localHashMap = new HashMap();
      paramPagingContext.setMap(localHashMap);
    }
    int i = 10;
    try
    {
      i = jdMethod_new(paramHashMap);
    }
    catch (Exception localException1) {}
    localHashMap.put("PAGE_SIZE", String.valueOf(i));
    ReportCriteria localReportCriteria = (ReportCriteria)localHashMap.get("ReportCriteria");
    Object localObject7;
    Object localObject10;
    if (paramPagingContext.getDirection().equals("FIRST"))
    {
      localObject1 = null;
      if (localReportCriteria != null) {
        localObject1 = localReportCriteria.getSortCriteria();
      }
      localObject2 = new ArrayList();
      if (localObject1 != null) {
        for (int j = 0; j < ((ReportSortCriteria)localObject1).size(); j++)
        {
          localObject4 = (ReportSortCriterion)((ReportSortCriteria)localObject1).get(j);
          localObject5 = new SortCriterion();
          ((SortCriterion)localObject5).setName(((ReportSortCriterion)localObject4).getName());
          if (((ReportSortCriterion)localObject4).getAsc() == true) {
            ((SortCriterion)localObject5).setAscending();
          } else {
            ((SortCriterion)localObject5).setDescending();
          }
          ((ArrayList)localObject2).add(localObject5);
        }
      }
      paramPagingContext.setSortCriteriaList((ArrayList)localObject2);
      if (str3.equals("TRANS_TYPE_TEMPLATE"))
      {
        localObject3 = GregorianCalendar.getInstance();
        ((Calendar)localObject3).add(1, -5);
        paramPagingContext.setStartDate((Calendar)localObject3);
      }
      else if (paramPagingContext.getStartDate() == null)
      {
        localObject3 = GregorianCalendar.getInstance();
        ((Calendar)localObject3).add(5, -30);
        paramPagingContext.setStartDate((Calendar)localObject3);
      }
      if (paramPagingContext.getEndDate() == null)
      {
        localObject3 = GregorianCalendar.getInstance();
        ((Calendar)localObject3).add(5, 30);
        paramPagingContext.setEndDate((Calendar)localObject3);
      }
      localObject3 = new HashMap();
      if (paramSecureUser.getBusinessID() == 0)
      {
        if (str3.indexOf("TEMPLATE") > -1)
        {
          localObject4 = new String[] { String.valueOf(paramSecureUser.getProfileID()) };
          ((HashMap)localObject3).put("SubmittedBys", localObject4);
        }
        else
        {
          ((HashMap)localObject3).put("CustomerId", String.valueOf(paramSecureUser.getPrimaryUserID()));
        }
      }
      else {
        ((HashMap)localObject3).put("CustomerId", String.valueOf(paramSecureUser.getBusinessID()));
      }
      ((HashMap)localObject3).put("FIID", paramSecureUser.getBPWFIID());
      localObject4 = null;
      if (str2.equals("BOTH")) {
        localObject4 = new String[] { "ITOI", "INTERNAL", "ITOE", "ETOI" };
      } else if ((str2.equals("ITOI")) || (str2.equals("INTERNAL")) || (str2.equals("ITOE")) || (str2.equals("ETOI"))) {
        localObject4 = new String[] { str2 };
      }
      ((HashMap)localObject3).put("Dests", localObject4);
      Object localObject5 = null;
      if (str3.equals("TRANS_TYPE_SINGLE"))
      {
        localObject5 = new String[] { "Current", "Repetitive", "Recurring" };
      }
      else if (str3.equals("TRANS_TYPE_TEMPLATE"))
      {
        localObject5 = new String[] { "TEMPLATE", "RECTEMPLATE" };
      }
      else if (str3.equals("TRANS_TYPE_MODEL"))
      {
        localObject5 = new String[] { "Current", "Recmodel" };
      }
      else
      {
        localObject6 = new StringTokenizer(str3, ",");
        localObject5 = new String[((StringTokenizer)localObject6).countTokens()];
        for (int k = 0; ((StringTokenizer)localObject6).hasMoreTokens(); k++) {
          localObject5[k] = ((StringTokenizer)localObject6).nextToken();
        }
      }
      ((HashMap)localObject3).put("TransType", localObject5);
      localObject6 = null;
      if ("TRANSFER_STATUS_PENDING".equals(str1)) {
        localObject6 = new String[] { "WILLPROCESSON", "BATCH_INPROCESS", "IMMED_INPROCESS", "INPROCESS", "FUNDSPROCESSED" };
      } else if ("TRANSFER_STATUS_APPROVAL".equals(str1)) {
        localObject6 = new String[] { "APPROVAL_PENDING", "APPROVAL_REJECTED" };
      } else if ("TRANSFER_STATUS_COMPLETED".equals(str1)) {
        localObject6 = new String[] { "PROCESSEDON", "POSTEDON", "NOFUNDSON", "FAILEDON", "FAILEDON_NOTIF", "LIMIT_CHECK_FAILED", "LIMIT_CHECK_FAILED_NOTIF", "LIMIT_REVERT_FAILED", "APPROVAL_FAILED", "BACKENDFAILED", "BACKENDFAILED_NOTIF", "BACKENDREJECTED", "BACKENDREJECTED_NOTIF" };
      } else if ("TRANSFER_STATUS_TEMPLATE".equals(str1)) {
        localObject6 = new String[] { "TEMPLATE", "RECTEMPLATE" };
      } else {
        localObject6 = new String[] { "WILLPROCESSON", "BATCH_INPROCESS", "IMMED_INPROCESS", "INPROCESS", "FUNDSPROCESSED", "APPROVAL_PENDING", "PROCESSEDON", "POSTEDON" };
      }
      ((HashMap)localObject3).put("StatusList", localObject6);
      ((HashMap)localObject3).put("CategoryList", "USER_ENTRY");
      ExtTransferAcctInfo localExtTransferAcctInfo;
      if (paramHashMap.get("FromAcct") != null)
      {
        localObject7 = (String)paramHashMap.get("FromAcct");
        localExtTransferAcctInfo = new ExtTransferAcctInfo();
        if ((localAccounts != null) && (localAccounts.getByID((String)localObject7) != null))
        {
          Account localAccount1 = localAccounts.getByID((String)localObject7);
          if (localAccount1.get("ExternalTransferACCOUNT") != null)
          {
            localObject10 = (ExtTransferAccount)localAccount1.get("ExternalTransferACCOUNT");
            if (localObject10 != null) {
              localObject7 = ((ExtTransferAccount)localObject10).getBpwID();
            }
          }
        }
        int m = ((String)localObject7).lastIndexOf("-");
        if (m == -1)
        {
          localExtTransferAcctInfo = jdMethod_do((String)localObject7);
        }
        else
        {
          localExtTransferAcctInfo.setAcctNum(((String)localObject7).substring(0, m));
          localExtTransferAcctInfo.setAcctType(((String)localObject7).substring(m + 1));
        }
        ((HashMap)localObject3).put("FromAcct", localExtTransferAcctInfo);
      }
      if (paramHashMap.get("ToAcct") != null)
      {
        localObject7 = (String)paramHashMap.get("ToAcct");
        localExtTransferAcctInfo = new ExtTransferAcctInfo();
        if ((localAccounts != null) && (localAccounts.getByID((String)localObject7) != null))
        {
          Account localAccount2 = localAccounts.getByID((String)localObject7);
          if (localAccount2.get("ExternalTransferACCOUNT") != null)
          {
            localObject10 = (ExtTransferAccount)localAccount2.get("ExternalTransferACCOUNT");
            if (localObject10 != null) {
              localObject7 = ((ExtTransferAccount)localObject10).getBpwID();
            }
          }
        }
        int n = ((String)localObject7).lastIndexOf("-");
        if (n == -1)
        {
          localExtTransferAcctInfo = jdMethod_do((String)localObject7);
        }
        else
        {
          localExtTransferAcctInfo.setAcctNum(((String)localObject7).substring(0, n));
          localExtTransferAcctInfo.setAcctType(((String)localObject7).substring(n + 1));
        }
        ((HashMap)localObject3).put("ToAcct", localExtTransferAcctInfo);
      }
      if (paramHashMap.get("Amount") != null) {
        ((HashMap)localObject3).put("Amount", paramHashMap.get("Amount"));
      }
      localHashMap.put("SEARCH_CRITERIA", localObject3);
    }
    Object localObject1 = localHashMap.get("SORT_VALUE_MIN_TransactionIndex");
    Object localObject2 = localHashMap.get("SORT_VALUE_MAX_TransactionIndex");
    if (localObject1 != null) {
      localHashMap.put("SORT_VALUE_MIN_TransactionIndex", String.valueOf(localObject1));
    }
    if (localObject2 != null) {
      localHashMap.put("SORT_VALUE_MAX_TransactionIndex", String.valueOf(localObject2));
    }
    Object localObject3 = new PagingInfo();
    ((PagingInfo)localObject3).setPagingContext(paramPagingContext);
    Object localObject4 = getBPWHandler();
    if (localObject4 == null) {
      throw new BankingException(31023);
    }
    try
    {
      localHashMap.remove("ReportCriteria");
      paramPagingContext.setMap(localHashMap);
      ((PagingInfo)localObject3).setPagingContext(paramPagingContext);
      localObject3 = ((BPWServices)localObject4).getPagedTransfer((PagingInfo)localObject3);
    }
    catch (Exception localException2)
    {
      DebugLog.throwing("getPagedTransfers ", localException2);
      throw new BankingException(1033);
    }
    finally
    {
      removeBPWHandler((BPWServices)localObject4);
    }
    ArrayList localArrayList = ((PagingInfo)localObject3).getPagingResult();
    paramPagingContext.setMap(((PagingInfo)localObject3).getPagingContext().getMap());
    paramPagingContext.setSessionId(((PagingInfo)localObject3).getPagingContext().getSessionId());
    localHashMap = paramPagingContext.getMap();
    if (localHashMap != null)
    {
      localHashMap.put("ReportCriteria", localReportCriteria);
      localObject6 = localHashMap.get("LOWER_BOUND_TransactionIndex");
      localObject7 = localHashMap.get("UPPER_BOUND_TransactionIndex");
      try
      {
        long l;
        if (localObject6 != null)
        {
          l = Long.parseLong(localObject6.toString());
          localHashMap.put("LOWER_BOUND_TransactionIndex", new Long(l));
        }
        if (localObject7 != null)
        {
          l = Long.parseLong(localObject7.toString());
          localHashMap.put("UPPER_BOUND_TransactionIndex", new Long(l));
        }
      }
      catch (Exception localException3)
      {
        DebugLog.throwing("getPagedTransfers ", localException3);
        throw new BankingException(1033);
      }
      localHashMap.put("TOTAL_TRANS", String.valueOf(((PagingInfo)localObject3).getTotalTrans()));
    }
    Object localObject6 = new FundsTransactions(paramSecureUser.getLocale());
    ((FundsTransactions)localObject6).setPagingContext(((PagingInfo)localObject3).getPagingContext());
    if ((localArrayList != null) && (!localArrayList.isEmpty()))
    {
      localObject7 = localArrayList.iterator();
      while (((Iterator)localObject7).hasNext())
      {
        Object localObject8 = ((Iterator)localObject7).next();
        Object localObject9;
        if ((localObject8 instanceof TransferInfo))
        {
          localObject9 = (TransferInfo)localObject8;
          localObject10 = null;
          Object localObject12;
          Object localObject13;
          if ((localObject9 instanceof RecTransferInfo))
          {
            localObject12 = (RecTransferInfo)localObject9;
            localObject10 = new RecTransfer(((FundsTransactions)localObject6).getLocale());
            localObject13 = (RecTransfer)localObject10;
            ((RecTransfer)localObject13).setRecTransferInfo((RecTransferInfo)localObject12, localAccounts);
            ((RecTransfer)localObject13).setFrequency(getFrequency(((RecTransferInfo)localObject12).getFrequency()));
            if (((((RecTransferInfo)localObject12).getEndDate() != null) && (((RecTransferInfo)localObject12).getEndDate().equals("-1"))) || (((RecTransfer)localObject13).getNumberTransfersValue() >= 750)) {
              ((RecTransfer)localObject13).setNumberTransfers(999);
            }
            ((FundsTransactions)localObject6).add(localObject13);
          }
          else
          {
            localObject10 = new Transfer(((FundsTransactions)localObject6).getLocale());
            ((Transfer)localObject10).setTransferInfo((TransferInfo)localObject9, localAccounts);
            localObject12 = ((TransferInfo)localObject9).getSourceRecSrvrTId();
            if (localObject12 != null)
            {
              ((Transfer)localObject10).setType(2);
              localObject13 = new Transfer(localLocale);
              ((Transfer)localObject13).setID((String)localObject12);
              ((Transfer)localObject13).setTransferDestination(((Transfer)localObject10).getTransferDestination());
              RecTransfer localRecTransfer = getRecTransferByID(paramSecureUser, (Transfer)localObject13, paramHashMap);
              ((Transfer)localObject10).put("FrequencyValue", getFrequencyString(localRecTransfer.getFrequencyValue()));
              ((Transfer)localObject10).put("Frequency", localRecTransfer.getFrequency());
              if ((localObject9 instanceof RecTransferInfo))
              {
                String str4 = String.valueOf(((RecTransferInfo)localObject9).getPmtsCount());
                ((Transfer)localObject10).put("NumberTransfers", str4);
              }
              else
              {
                ((Transfer)localObject10).put("NumberTransfers", localRecTransfer.getNumberTransfers());
              }
            }
            else
            {
              ((Transfer)localObject10).put("FrequencyValue", "NONE");
            }
            ((FundsTransactions)localObject6).add(localObject10);
          }
          ((Transfer)localObject10).setDisplayRestrictedAccount(this.restrictAccounts);
          ((Transfer)localObject10).setTransactionIndex(((TransferInfo)localObject9).getRecordCursor());
        }
        else if ((localObject8 instanceof TransferBatchInfo))
        {
          localObject9 = (TransferBatchInfo)localObject8;
          localObject10 = new TransferBatch(((FundsTransactions)localObject6).getLocale());
          ((TransferBatch)localObject10).setTransferBatchInfo((TransferBatchInfo)localObject9);
          ((FundsTransactions)localObject6).add(localObject10);
        }
      }
    }
    return localObject6;
  }
  
  public Date getEffectiveDate(SecureUser paramSecureUser, Transfer paramTransfer, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Banking.getEffectiveDate";
    int i = 0;
    com.ffusion.beans.DateTime localDateTime = new com.ffusion.beans.DateTime(paramSecureUser.getLocale());
    BPWServices localBPWServices = getBPWHandler();
    if (localBPWServices == null) {
      throw new CSILException(31023);
    }
    try
    {
      if (paramTransfer == null) {
        throw new CSILException(20006);
      }
      TransferInfo localTransferInfo = paramTransfer.getTransferInfo();
      i = localBPWServices.getValidTransferDateDue(localTransferInfo);
      localDateTime.setFormat("yyyyMMdd");
      localDateTime.fromString(String.valueOf(i));
      localDateTime.setFormat("SHORT");
    }
    catch (Exception localException)
    {
      DebugLog.log("Banking.getEffectiveDate: Failed to get effective date  for specified date " + paramTransfer.getDate());
      localException.printStackTrace(System.err);
    }
    finally
    {
      removeBPWHandler(localBPWServices);
    }
    return localDateTime.getTime();
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
  
  private static int a(Transfer paramTransfer, TransferInfo paramTransferInfo)
  {
    int i = paramTransferInfo.getStatusCode();
    if ((i != 0) && (i == 17521))
    {
      Hashtable localHashtable = paramTransferInfo.getExtInfo();
      if (localHashtable != null)
      {
        Limits localLimits = (Limits)localHashtable.get("NOT_ALLOWED_APPROVAL_LIMITS");
        if ((localLimits != null) && (localLimits.size() > 0)) {
          paramTransfer.put("FAILED_LIMITS", localLimits);
        }
      }
    }
    return i;
  }
  
  private static void a(Transfer paramTransfer, int paramInt, String paramString)
  {
    if ((paramInt != -1) && (paramTransfer.get("FAILED_LIMITS") == null))
    {
      Limits localLimits = new Limits();
      Object localObject;
      if (paramString != null)
      {
        localObject = new StringTokenizer(paramString, ",");
        while (((StringTokenizer)localObject).hasMoreTokens())
        {
          Limit localLimit = new Limit();
          int i = Integer.parseInt(((StringTokenizer)localObject).nextToken());
          localLimit.setLimitId(i);
          localLimit.setGroupId(-1);
          localLimits.add(localLimit);
        }
      }
      else
      {
        localObject = new Limit();
        ((Limit)localObject).setLimitId(paramInt);
        ((Limit)localObject).setGroupId(-1);
        localLimits.add(localObject);
      }
      paramTransfer.put("FAILED_LIMITS", localLimits);
    }
  }
  
  private static void a(ReportResult paramReportResult, int paramInt1, int paramInt2)
    throws RptException
  {
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(paramReportResult.getLocale());
    localReportDataDimensions.setNumColumns(paramInt1);
    localReportDataDimensions.setNumRows(paramInt2);
    paramReportResult.setDataDimensions(localReportDataDimensions);
  }
  
  private static void a(ReportResult paramReportResult)
    throws RptException
  {
    ReportResult localReportResult = new ReportResult(paramReportResult.getLocale());
    a[] arrayOfa = ca;
    paramReportResult.addSubReport(localReportResult);
    localReportResult.setHeading(null);
    a(localReportResult, arrayOfa.length, -1);
    a.jdMethod_if(localReportResult, paramReportResult.getLocale(), arrayOfa);
    ReportRow localReportRow = new ReportRow(paramReportResult.getLocale());
    ReportDataItems localReportDataItems = new ReportDataItems(paramReportResult.getLocale());
    localReportRow.setDataItems(localReportDataItems);
    localReportDataItems.add().setData(null);
    paramReportResult.addRow(localReportRow);
  }
  
  private static class a
  {
    private int jdField_int;
    private String jdField_do;
    private String a;
    private int jdField_for;
    private String jdField_if;
    
    public a(String paramString1, String paramString2)
    {
      this(-1, paramString2, 0, null);
      this.jdField_do = paramString1;
    }
    
    public a(String paramString1, String paramString2, int paramInt)
    {
      this(-1, paramString2, paramInt, null);
      this.jdField_do = paramString1;
    }
    
    public a(int paramInt1, String paramString, int paramInt2)
    {
      this(paramInt1, paramString, paramInt2, null);
    }
    
    public a(int paramInt1, String paramString1, int paramInt2, String paramString2)
    {
      this.jdField_int = paramInt1;
      this.a = paramString1;
      this.jdField_for = paramInt2;
      this.jdField_if = paramString2;
    }
    
    public static void a(ReportResult paramReportResult, Locale paramLocale, a[] paramArrayOfa)
      throws RptException
    {
      ReportColumn localReportColumn = new ReportColumn(paramLocale);
      for (int i = 0; i < paramArrayOfa.length; i++)
      {
        a locala = paramArrayOfa[i];
        ArrayList localArrayList = new ArrayList(1);
        if (locala.jdField_do != null) {
          localArrayList.add(locala.jdField_do);
        } else if (locala.jdField_int != -1) {
          localArrayList.add(ReportConsts.getColumnName(locala.jdField_int, paramLocale));
        } else {
          localArrayList.add("");
        }
        localReportColumn.setLabels(localArrayList);
        localReportColumn.setDataType(locala.a);
        localReportColumn.setWidthAsPercent(locala.jdField_for);
        if (locala.jdField_if != null) {
          localReportColumn.setJustification(locala.jdField_if);
        }
        paramReportResult.addColumn((ReportColumn)localReportColumn.clone());
      }
    }
    
    public static void jdField_if(ReportResult paramReportResult, Locale paramLocale, a[] paramArrayOfa)
      throws RptException
    {
      ReportColumn localReportColumn = new ReportColumn(paramLocale);
      for (int i = 0; i < paramArrayOfa.length; i++)
      {
        a locala = paramArrayOfa[i];
        localReportColumn.setLabels(null);
        localReportColumn.setDataType(locala.a);
        localReportColumn.setWidthAsPercent(locala.jdField_for);
        if (locala.jdField_if != null) {
          localReportColumn.setJustification(locala.jdField_if);
        }
        paramReportResult.addColumn((ReportColumn)localReportColumn.clone());
      }
    }
  }
  
  public class b
    extends Base.a
  {
    public b()
    {
      super();
    }
    
    public void attribute(String paramString1, String paramString2, boolean paramBoolean)
    {
      if (paramString1.equals("TYPE")) {
        Banking.this.bV = paramString2;
      } else if (paramString1.equals("DEFAULT_CONNECTIONS")) {
        Banking.this.ce = paramString2;
      } else if (paramString1.equals("MAX_CONNECTIONS")) {
        Banking.this.b0 = paramString2;
      } else if (paramString1.equals("DRIVER")) {
        Banking.this.b7 = paramString2;
      } else if (paramString1.equals("SERVER")) {
        Banking.this.b9 = paramString2;
      } else if (paramString1.equals("USER")) {
        Banking.this.cf = paramString2;
      } else if (paramString1.equals("PASSWORD")) {
        Banking.this.bY = paramString2;
      } else if (paramString1.equals("URL")) {
        Banking.this.cc = paramString2;
      } else if (paramString1.equals("BPWCallBackJNDIName")) {
        Banking.this.bZ = paramString2;
      } else {
        super.attribute(paramString1, paramString2, paramBoolean);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.banksim2.Banking
 * JD-Core Version:    0.7.0.1
 */