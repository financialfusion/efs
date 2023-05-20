package com.ffusion.services.banking;

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
import com.ffusion.beans.banking.TransactionTypeInfo;
import com.ffusion.beans.banking.Transactions;
import com.ffusion.beans.banking.Transfer;
import com.ffusion.beans.banking.TransferBatch;
import com.ffusion.beans.banking.Transfers;
import com.ffusion.beans.business.Businesses;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Reporting;
import com.ffusion.efs.adapters.profile.AccountAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.reporting.IReportResult;
import com.ffusion.services.AccountService;
import com.ffusion.services.Banking8;
import com.ffusion.services.BankingException;
import com.ffusion.util.FFIUtilException;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.Strings;
import com.ffusion.util.TransactionTypeCache;
import com.ffusion.util.XMLTag;
import com.ffusion.util.beans.PagingContext;
import com.ffusion.util.logging.DebugLog;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;

public class Banking
  implements Banking8, AccountService
{
  static final String co = " Unable to load Banking Service configuration file bankingservice.xml";
  static final String cn = " An error occurred while parsing the Banking Service configuration file bankingservice.xml";
  static final String cu = " Unable to load Banking Service real-time class ";
  static final String cq = " Unable to load Banking Service stored class ";
  static final String ct = " The following Banking Service realtime class could not be loaded as it does not implement Banking8: ";
  static final String cy = " The following Banking Service stored class could not be loaded as it does not implement Banking8: ";
  static final String cv = " An error occurred while instantiating the Banking Service realtime class ";
  static final String cr = " An error occurred while instantiating the Banking Service stored class ";
  static final String cw = " An error occurred while trying to determine whether user is of type consumer or corporate ";
  private static final String cx = "sUser";
  protected Banking8 _realtimeService;
  protected Banking8 _storedService;
  protected Banking8 _corporateService;
  protected Banking8 _consumerService;
  private boolean cs = true;
  private String cp;
  
  public int initialize(String paramString)
  {
    try
    {
      InputStream localInputStream = null;
      localObject1 = null;
      try
      {
        localInputStream = ResourceUtil.getResourceAsStream(this, paramString);
        localObject1 = Strings.streamToString(localInputStream);
      }
      catch (Exception localException2)
      {
        throw new BankingException(465);
      }
      localObject2 = null;
      try
      {
        localObject2 = new XMLTag(true);
        ((XMLTag)localObject2).build((String)localObject1);
        localObject1 = null;
      }
      catch (Exception localException3)
      {
        throw new BankingException(466);
      }
      DebugLog.log(Level.INFO, "Processing Banking Service config xml " + paramString);
      localObject3 = null;
      Class localClass = null;
      XMLTag localXMLTag1 = ((XMLTag)localObject2).getContainedTag("REALTIME");
      String str1 = localXMLTag1.getContainedTag("JAVA_CLASS").getTagContent();
      String str2 = localXMLTag1.getContainedTag("INIT_URL").getTagContent();
      XMLTag localXMLTag2 = ((XMLTag)localObject2).getContainedTag("STORED");
      String str3 = localXMLTag2.getContainedTag("JAVA_CLASS").getTagContent();
      String str4 = localXMLTag2.getContainedTag("INIT_URL").getTagContent();
      try
      {
        localObject3 = Class.forName(str1);
      }
      catch (ClassNotFoundException localClassNotFoundException1)
      {
        DebugLog.log(Level.SEVERE, " Unable to load Banking Service real-time class " + str1);
        throw new BankingException(467);
      }
      try
      {
        localClass = Class.forName(str3);
      }
      catch (ClassNotFoundException localClassNotFoundException2)
      {
        DebugLog.log(Level.SEVERE, " Unable to load Banking Service stored class " + str3);
        throw new BankingException(468);
      }
      try
      {
        this._realtimeService = ((Banking8)((Class)localObject3).newInstance());
      }
      catch (ClassCastException localClassCastException1)
      {
        DebugLog.log(Level.SEVERE, " The following Banking Service realtime class could not be loaded as it does not implement Banking8: " + str1);
        throw new BankingException(469);
      }
      catch (Exception localException4)
      {
        DebugLog.log(Level.SEVERE, " An error occurred while instantiating the Banking Service realtime class " + str1 + ": " + localException4.getMessage());
        throw new BankingException(471);
      }
      try
      {
        this._storedService = ((Banking8)localClass.newInstance());
      }
      catch (ClassCastException localClassCastException2)
      {
        DebugLog.log(Level.SEVERE, " The following Banking Service stored class could not be loaded as it does not implement Banking8: " + str3);
        throw new BankingException(470);
      }
      catch (Exception localException5)
      {
        DebugLog.log(Level.SEVERE, " An error occurred while instantiating the Banking Service stored class " + str3 + ": " + localException5.getMessage());
        throw new BankingException(472);
      }
      if (this._realtimeService.initialize(str2) != 0) {
        throw new BankingException(475);
      }
      if (this._storedService.initialize(str4) != 0) {
        throw new BankingException(476);
      }
      this._corporateService = this._storedService;
      this._consumerService = this._realtimeService;
      String str5 = null;
      if (((XMLTag)localObject2).getContainedTag("CONSUMER_TRANS_SOURCE") != null) {
        str5 = ((XMLTag)localObject2).getContainedTag("CONSUMER_TRANS_SOURCE").getTagContent();
      }
      if ((str5 != null) && (str5.equalsIgnoreCase("Stored")))
      {
        this._consumerService = this._storedService;
        this.cs = false;
      }
      DebugLog.log(Level.INFO, "Banking Service initialized");
      return 0;
    }
    catch (Exception localException1)
    {
      Object localObject1;
      Object localObject2;
      Object localObject3;
      if (!(localException1 instanceof BankingException))
      {
        localObject1 = new StringWriter();
        localObject2 = new PrintWriter((Writer)localObject1);
        localException1.printStackTrace((PrintWriter)localObject2);
        localObject3 = "An error occurred while initializing the Banking Service: " + ((StringWriter)localObject1).toString();
        DebugLog.log(Level.SEVERE, (String)localObject3);
      }
    }
    return 1;
  }
  
  private static boolean a(Account paramAccount, HashMap paramHashMap)
    throws BankingException
  {
    boolean bool = true;
    SecureUser localSecureUser = (SecureUser)paramHashMap.get("sUser");
    if (localSecureUser != null)
    {
      if (localSecureUser.getBusinessID() == 0) {
        bool = false;
      }
    }
    else if (paramAccount != null) {
      try
      {
        Businesses localBusinesses = AccountAdapter.getBusinessesForAccount(paramAccount, paramHashMap);
        if (localBusinesses.size() == 0) {
          bool = false;
        }
      }
      catch (ProfileException localProfileException)
      {
        throw new BankingException(474, " An error occurred while trying to determine whether user is of type consumer or corporate ", localProfileException);
      }
    } else {
      throw new BankingException(474);
    }
    return bool;
  }
  
  public int addAccount(Account paramAccount)
  {
    try
    {
      if (a(paramAccount, new HashMap()))
      {
        if ((this._corporateService instanceof AccountService)) {
          return ((AccountService)this._corporateService).addAccount(paramAccount);
        }
        return 2;
      }
      return 2;
    }
    catch (BankingException localBankingException)
    {
      return localBankingException.getErrorCode();
    }
  }
  
  public int modifyAccount(Account paramAccount)
  {
    try
    {
      if (a(paramAccount, new HashMap()))
      {
        if ((this._corporateService instanceof AccountService)) {
          return ((AccountService)this._corporateService).modifyAccount(paramAccount);
        }
        return 2;
      }
      return 2;
    }
    catch (BankingException localBankingException)
    {
      return localBankingException.getErrorCode();
    }
  }
  
  public int deleteAccount(Account paramAccount)
  {
    try
    {
      if (a(paramAccount, new HashMap()))
      {
        if ((this._corporateService instanceof AccountService)) {
          return ((AccountService)this._corporateService).deleteAccount(paramAccount);
        }
        return 2;
      }
      return 2;
    }
    catch (BankingException localBankingException)
    {
      return localBankingException.getErrorCode();
    }
  }
  
  public int getAccount(Account paramAccount)
  {
    try
    {
      if (a(paramAccount, new HashMap()))
      {
        if ((this._corporateService instanceof AccountService)) {
          return ((AccountService)this._corporateService).getAccount(paramAccount);
        }
        return 2;
      }
      return 2;
    }
    catch (BankingException localBankingException)
    {
      StringWriter localStringWriter = new StringWriter();
      PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
      localBankingException.printStackTrace(localPrintWriter);
      String str = "An error occurred while retreiving accounts from the Banking Service: " + localStringWriter.toString();
      DebugLog.log(Level.SEVERE, str);
      return localBankingException.getErrorCode();
    }
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
  
  public int addTransferBatch(TransferBatch paramTransferBatch)
  {
    return this._realtimeService.addTransferBatch(paramTransferBatch);
  }
  
  public int cancelTransferBatch(TransferBatch paramTransferBatch)
  {
    return this._realtimeService.cancelTransferBatch(paramTransferBatch);
  }
  
  public int modifyTransferBatch(TransferBatch paramTransferBatch)
  {
    return this._realtimeService.modifyTransferBatch(paramTransferBatch);
  }
  
  public FundsTransactions getPagedFundsTransactions(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    return this._realtimeService.getPagedFundsTransactions(paramSecureUser, paramPagingContext, paramHashMap);
  }
  
  public Transactions getRecentTransactions(Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    if (a(paramAccount, paramHashMap)) {
      return this._corporateService.getRecentTransactions(paramAccount, paramPagingContext, paramHashMap);
    }
    return this._consumerService.getRecentTransactions(paramAccount, paramPagingContext, paramHashMap);
  }
  
  public int getNumTransactions(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, Properties paramProperties, HashMap paramHashMap)
    throws BankingException
  {
    if (a(paramAccount, paramHashMap)) {
      return this._corporateService.getNumTransactions(paramAccount, paramCalendar1, paramCalendar2, paramProperties, paramHashMap);
    }
    return this._consumerService.getNumTransactions(paramAccount, paramCalendar1, paramCalendar2, paramProperties, paramHashMap);
  }
  
  public Date getEffectiveDate(SecureUser paramSecureUser, Transfer paramTransfer, HashMap paramHashMap)
    throws CSILException
  {
    return this._realtimeService.getEffectiveDate(paramSecureUser, paramTransfer, paramHashMap);
  }
  
  public Transfers getPagedTransfers(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    return this._realtimeService.getPagedTransfers(paramSecureUser, paramPagingContext, paramHashMap);
  }
  
  public HashMap getTransactionTypes(Locale paramLocale, HashMap paramHashMap)
    throws BankingException
  {
    if (TransactionTypeCache.getTransactionTypeCache(paramLocale) == null) {
      throw new BankingException(5);
    }
    return TransactionTypeCache.getTransactionTypeCache(paramLocale);
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
  
  public RecTransfer getRecTransferByID(SecureUser paramSecureUser, Transfer paramTransfer, HashMap paramHashMap)
    throws BankingException
  {
    return this._realtimeService.getRecTransferByID(paramSecureUser, paramTransfer, paramHashMap);
  }
  
  public Transfer getTransferByID(SecureUser paramSecureUser, Transfer paramTransfer, HashMap paramHashMap)
    throws BankingException
  {
    return this._realtimeService.getTransferByID(paramSecureUser, paramTransfer, paramHashMap);
  }
  
  public Transfers getTransferTemplates(SecureUser paramSecureUser, Transfer paramTransfer, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    return this._realtimeService.getTransferTemplates(paramSecureUser, paramTransfer, paramPagingContext, paramHashMap);
  }
  
  public RecTransfer getRecTransferByID(SecureUser paramSecureUser, String paramString, Accounts paramAccounts, HashMap paramHashMap)
    throws CSILException
  {
    return this._realtimeService.getRecTransferByID(paramSecureUser, paramString, paramAccounts, paramHashMap);
  }
  
  public HashMap getTransactionTypes()
    throws BankingException
  {
    return getTransactionTypes(null, null);
  }
  
  public ArrayList getTransactionTypeDescriptions()
    throws BankingException
  {
    return getTransactionTypeDescriptions(null, null);
  }
  
  public FixedDepositInstruments getFixedDepositInstruments(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws BankingException
  {
    if (a(paramAccount, paramHashMap)) {
      return this._corporateService.getFixedDepositInstruments(paramAccount, paramCalendar1, paramCalendar2, paramHashMap);
    }
    return this._consumerService.getFixedDepositInstruments(paramAccount, paramCalendar1, paramCalendar2, paramHashMap);
  }
  
  public void updateFixedDepositInstrument(FixedDepositInstrument paramFixedDepositInstrument, HashMap paramHashMap)
    throws BankingException
  {
    if (a(null, paramHashMap)) {
      this._corporateService.updateFixedDepositInstrument(paramFixedDepositInstrument, paramHashMap);
    } else {
      this._consumerService.updateFixedDepositInstrument(paramFixedDepositInstrument, paramHashMap);
    }
  }
  
  public Transactions getRecentTransactions(Account paramAccount, HashMap paramHashMap)
    throws BankingException
  {
    if (a(paramAccount, paramHashMap)) {
      return this._corporateService.getRecentTransactions(paramAccount, paramHashMap);
    }
    return this._consumerService.getRecentTransactions(paramAccount, paramHashMap);
  }
  
  public Transactions getPagedTransactions(Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    if (a(paramAccount, paramHashMap)) {
      return this._corporateService.getPagedTransactions(paramAccount, paramPagingContext, paramHashMap);
    }
    PagingContext localPagingContext = a(paramAccount, paramPagingContext, paramHashMap);
    return this._consumerService.getPagedTransactions(paramAccount, localPagingContext, paramHashMap);
  }
  
  private PagingContext a(Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    PagingContext localPagingContext = null;
    if (!this.cs)
    {
      localPagingContext = paramPagingContext != null ? (PagingContext)paramPagingContext.clone() : null;
      ReportCriteria localReportCriteria = (localPagingContext == null) || (localPagingContext.getMap() == null) ? null : (ReportCriteria)localPagingContext.getMap().get("ReportCriteria");
      Properties localProperties = localReportCriteria == null ? null : localReportCriteria.getSearchCriteria();
      String str1 = localProperties.getProperty("Date Range Type");
      if ("Relative".equalsIgnoreCase(str1))
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
          String str2 = "Unable to calculate the date range.";
          DebugLog.log(str2);
          if (localCSILException.getCode() == -1009) {
            throw new BankingException(localCSILException.getServiceError());
          }
          throw new BankingException(localCSILException.getCode());
        }
      }
    }
    else
    {
      localPagingContext = paramPagingContext;
    }
    return localPagingContext;
  }
  
  public Transactions getNextTransactions(Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    if (a(paramAccount, paramHashMap)) {
      return this._corporateService.getNextTransactions(paramAccount, paramPagingContext, paramHashMap);
    }
    return this._consumerService.getNextTransactions(paramAccount, paramPagingContext, paramHashMap);
  }
  
  public Transactions getPreviousTransactions(Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    if (a(paramAccount, paramHashMap)) {
      return this._corporateService.getPreviousTransactions(paramAccount, paramPagingContext, paramHashMap);
    }
    return this._consumerService.getPreviousTransactions(paramAccount, paramPagingContext, paramHashMap);
  }
  
  public Transactions getFDInstrumentTransactions(FixedDepositInstrument paramFixedDepositInstrument, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws BankingException
  {
    if (a(null, paramHashMap)) {
      return this._corporateService.getFDInstrumentTransactions(paramFixedDepositInstrument, paramCalendar1, paramCalendar2, paramHashMap);
    }
    return this._consumerService.getFDInstrumentTransactions(paramFixedDepositInstrument, paramCalendar1, paramCalendar2, paramHashMap);
  }
  
  public int getNumTransactions(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws BankingException
  {
    if (a(paramAccount, paramHashMap)) {
      return this._corporateService.getNumTransactions(paramAccount, paramCalendar1, paramCalendar2, paramHashMap);
    }
    return this._consumerService.getNumTransactions(paramAccount, paramCalendar1, paramCalendar2, paramHashMap);
  }
  
  public AccountHistories getHistory(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws BankingException
  {
    if (a(paramAccount, paramHashMap)) {
      return this._corporateService.getHistory(paramAccount, paramCalendar1, paramCalendar2, paramHashMap);
    }
    return this._consumerService.getHistory(paramAccount, paramCalendar1, paramCalendar2, paramHashMap);
  }
  
  public AccountSummaries getSummary(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws BankingException
  {
    if (a(paramAccount, paramHashMap)) {
      return this._corporateService.getSummary(paramAccount, paramCalendar1, paramCalendar2, paramHashMap);
    }
    return this._consumerService.getSummary(paramAccount, paramCalendar1, paramCalendar2, paramHashMap);
  }
  
  public ExtendedAccountSummaries getExtendedSummary(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws BankingException
  {
    if (a(paramAccount, paramHashMap)) {
      return this._corporateService.getExtendedSummary(paramAccount, paramCalendar1, paramCalendar2, paramHashMap);
    }
    return this._consumerService.getExtendedSummary(paramAccount, paramCalendar1, paramCalendar2, paramHashMap);
  }
  
  public IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws BankingException
  {
    Properties localProperties = paramReportCriteria.getReportOptions();
    String str = localProperties.getProperty("REPORTTYPE");
    if ((str.equals("Transfer History Report")) || (str.equals("Transfer Detail Report")) || (str.equals("Pending Transfer Report")) || (str.equals("Transfer By Status"))) {
      return this._realtimeService.getReportData(paramSecureUser, paramReportCriteria, paramHashMap);
    }
    if (paramSecureUser.getBusinessID() != 0) {
      return this._corporateService.getReportData(paramSecureUser, paramReportCriteria, paramHashMap);
    }
    return this._consumerService.getReportData(paramSecureUser, paramReportCriteria, paramHashMap);
  }
  
  public Transfers getPagedPendingTransfers(SecureUser paramSecureUser, Accounts paramAccounts, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    return this._realtimeService.getPagedPendingTransfers(paramSecureUser, paramAccounts, paramPagingContext, paramHashMap);
  }
  
  public Transfers getNextPendingTransfers(SecureUser paramSecureUser, Accounts paramAccounts, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    return this._realtimeService.getNextPendingTransfers(paramSecureUser, paramAccounts, paramPagingContext, paramHashMap);
  }
  
  public Transfers getPreviousPendingTransfers(SecureUser paramSecureUser, Accounts paramAccounts, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    return this._realtimeService.getPreviousPendingTransfers(paramSecureUser, paramAccounts, paramPagingContext, paramHashMap);
  }
  
  public Transfers getPagedCompletedTransfers(SecureUser paramSecureUser, Accounts paramAccounts, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    return this._realtimeService.getPagedCompletedTransfers(paramSecureUser, paramAccounts, paramPagingContext, paramHashMap);
  }
  
  public Transfers getNextCompletedTransfers(SecureUser paramSecureUser, Accounts paramAccounts, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    return this._realtimeService.getNextCompletedTransfers(paramSecureUser, paramAccounts, paramPagingContext, paramHashMap);
  }
  
  public Transfers getPreviousCompletedTransfers(SecureUser paramSecureUser, Accounts paramAccounts, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    return this._realtimeService.getPreviousCompletedTransfers(paramSecureUser, paramAccounts, paramPagingContext, paramHashMap);
  }
  
  public void setSettings(String paramString)
  {
    this.cp = paramString;
  }
  
  public String getSettings()
  {
    return this.cp;
  }
  
  public int getAccounts(Accounts paramAccounts)
  {
    boolean bool = true;
    if (paramAccounts == null) {
      return 0;
    }
    SecureUser localSecureUser = paramAccounts.getSecureUser();
    if (localSecureUser != null)
    {
      if (localSecureUser.getUserType() == 2) {
        return this._realtimeService.getAccounts(paramAccounts);
      }
      if ((localSecureUser.getUserType() == 1) && (localSecureUser.getBusinessID() == 0)) {
        bool = false;
      } else if ((localSecureUser.getUserType() == 1) && (localSecureUser.getBusinessID() != 0)) {
        bool = true;
      }
    }
    else
    {
      if (paramAccounts.size() == 0) {
        return 0;
      }
      Account localAccount = (Account)paramAccounts.get(0);
      try
      {
        bool = a(localAccount, new HashMap());
      }
      catch (BankingException localBankingException)
      {
        return localBankingException.getErrorCode();
      }
    }
    if (bool) {
      return this._corporateService.getAccounts(paramAccounts);
    }
    int i = -1;
    if (!this.cs)
    {
      Accounts localAccounts = null;
      try
      {
        localAccounts = AccountAdapter.getAccounts(paramAccounts.getSecureUser());
      }
      catch (ProfileException localProfileException)
      {
        localObject1 = new StringWriter();
        localObject2 = new PrintWriter((Writer)localObject1);
        localProfileException.printStackTrace((PrintWriter)localObject2);
        localObject3 = "An error occurred while retreiving accounts from the Banking Service: " + ((StringWriter)localObject1).toString();
        DebugLog.log(Level.SEVERE, (String)localObject3);
        return 414;
      }
      Iterator localIterator = localAccounts.iterator();
      Object localObject1 = null;
      while (localIterator.hasNext())
      {
        localObject1 = (Account)localIterator.next();
        if (!paramAccounts.contains(localObject1)) {
          paramAccounts.add(localObject1);
        }
      }
      i = this._consumerService.getAccounts(localAccounts);
      Object localObject2 = null;
      Object localObject3 = null;
      localIterator = localAccounts.iterator();
      while (localIterator.hasNext())
      {
        localObject2 = (Account)localIterator.next();
        if (localObject2 != null) {
          localObject3 = paramAccounts.getByID(((Account)localObject2).getID());
        } else {
          localObject3 = null;
        }
        if (localObject3 != null)
        {
          ((Account)localObject3).setCurrentBalance(((Account)localObject2).getCurrentBalance());
          ((Account)localObject3).setAvailableBalance(((Account)localObject2).getAvailableBalance());
          ((Account)localObject3).setIntradayCurrentBalance(((Account)localObject2).getIntradayCurrentBalance());
          ((Account)localObject3).setIntradayAvailableBalance(((Account)localObject2).getIntradayAvailableBalance());
          ((Account)localObject3).putAll(((Account)localObject2).getHash());
          setFilters((Account)localObject3);
        }
      }
    }
    else
    {
      i = this._consumerService.getAccounts(paramAccounts);
    }
    return i;
  }
  
  protected static void setFilters(Account paramAccount)
  {
    int i = paramAccount.getTypeValue();
    paramAccount.setFilterable("Transactions");
    if (i == 1)
    {
      paramAccount.setFilterable("TransferTo");
      paramAccount.setFilterable("TransferFrom");
      paramAccount.setFilterable("BillPay");
    }
    else if (i == 2)
    {
      paramAccount.setFilterable("TransferTo");
      paramAccount.setFilterable("TransferFrom");
    }
    else if (i == 3)
    {
      paramAccount.setFilterable("TransferTo");
      paramAccount.setFilterable("TransferFrom");
    }
    else
    {
      paramAccount.setFilterable("TransferTo");
      paramAccount.setFilterable("TransferFrom");
      paramAccount.setFilterable("BillPay");
    }
  }
  
  public int getTransactions(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2)
  {
    HashMap localHashMap = new HashMap();
    int i = 0;
    try
    {
      if (a(paramAccount, localHashMap)) {
        this._corporateService.getTransactions(paramAccount, paramCalendar1, paramCalendar2);
      } else {
        this._consumerService.getTransactions(paramAccount, paramCalendar1, paramCalendar2);
      }
    }
    catch (BankingException localBankingException)
    {
      i = localBankingException.getErrorCode();
    }
    return i;
  }
  
  public int getTransactions(Account paramAccount)
  {
    HashMap localHashMap = new HashMap();
    try
    {
      if (a(paramAccount, localHashMap)) {
        return this._corporateService.getTransactions(paramAccount);
      }
      return this._consumerService.getTransactions(paramAccount);
    }
    catch (BankingException localBankingException)
    {
      int i = localBankingException.getErrorCode();
      return i;
    }
  }
  
  public int getTransfers(Accounts paramAccounts, Transfers paramTransfers)
  {
    return this._realtimeService.getTransfers(paramAccounts, paramTransfers);
  }
  
  public int getRecTransfers(Accounts paramAccounts, RecTransfers paramRecTransfers)
  {
    return this._realtimeService.getRecTransfers(paramAccounts, paramRecTransfers);
  }
  
  public int sendTransfer(Account paramAccount1, Account paramAccount2, Transfer paramTransfer)
  {
    return this._realtimeService.sendTransfer(paramAccount1, paramAccount2, paramTransfer);
  }
  
  public int sendTransfer(Account paramAccount1, Account paramAccount2, Transfer paramTransfer, HashMap paramHashMap)
  {
    return this._realtimeService.sendTransfer(paramAccount1, paramAccount2, paramTransfer, paramHashMap);
  }
  
  public int cancelTransfer(Transfer paramTransfer)
  {
    return this._realtimeService.cancelTransfer(paramTransfer);
  }
  
  public int sendRecTransfer(Account paramAccount1, Account paramAccount2, RecTransfer paramRecTransfer)
  {
    return this._realtimeService.sendRecTransfer(paramAccount1, paramAccount2, paramRecTransfer);
  }
  
  public int cancelRecTransfer(RecTransfer paramRecTransfer)
  {
    return this._realtimeService.cancelRecTransfer(paramRecTransfer);
  }
  
  public int modifyTransfer(Account paramAccount1, Account paramAccount2, Transfer paramTransfer)
  {
    return this._realtimeService.modifyTransfer(paramAccount1, paramAccount2, paramTransfer);
  }
  
  public int modifyRecTransfer(Account paramAccount1, Account paramAccount2, RecTransfer paramRecTransfer)
  {
    return this._realtimeService.modifyRecTransfer(paramAccount1, paramAccount2, paramRecTransfer);
  }
  
  public int modifyTransfer(Account paramAccount1, Account paramAccount2, Transfer paramTransfer, HashMap paramHashMap)
  {
    return this._realtimeService.modifyTransfer(paramAccount1, paramAccount2, paramTransfer, paramHashMap);
  }
  
  public int skipRecTransfer(Account paramAccount1, Account paramAccount2, RecTransfer paramRecTransfer)
  {
    return this._realtimeService.skipRecTransfer(paramAccount1, paramAccount2, paramRecTransfer);
  }
  
  public void setInitURL(String paramString)
  {
    initialize(paramString);
  }
  
  public int signOn(String paramString1, String paramString2)
  {
    return 2;
  }
  
  public int changePIN(String paramString1, String paramString2)
  {
    return this._consumerService.changePIN(paramString1, paramString2);
  }
  
  public boolean signOn(SecureUser paramSecureUser, String paramString1, String paramString2)
    throws CSILException
  {
    if (paramSecureUser == null) {
      throw new CSILException(18001);
    }
    boolean bool1 = this._realtimeService.signOn(paramSecureUser, paramString1, paramString2);
    boolean bool2 = false;
    if (paramSecureUser.getBusinessID() != 0) {
      bool2 = this._corporateService.signOn(paramSecureUser, paramString1, paramString2);
    } else if (this.cs) {
      bool2 = bool1;
    } else {
      bool2 = this._consumerService.signOn(paramSecureUser, paramString1, paramString2);
    }
    return (bool1) && (bool2);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.banking.Banking
 * JD-Core Version:    0.7.0.1
 */