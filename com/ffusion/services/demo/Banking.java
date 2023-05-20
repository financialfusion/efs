package com.ffusion.services.demo;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.AccountHistories;
import com.ffusion.beans.accounts.AccountHistory;
import com.ffusion.beans.accounts.AccountSummaries;
import com.ffusion.beans.accounts.AccountSummary;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.accounts.AcctBalanceSheetRpt;
import com.ffusion.beans.accounts.AcctCashFlowFcstRpt;
import com.ffusion.beans.accounts.AcctCashFlowRpt;
import com.ffusion.beans.accounts.AcctGeneralLedgerRpt;
import com.ffusion.beans.accounts.AcctTransactionRpt;
import com.ffusion.beans.accounts.BalanceSummaryRecords;
import com.ffusion.beans.accounts.BalanceSummaryRpt;
import com.ffusion.beans.accounts.ExtendedAccountSummaries;
import com.ffusion.beans.accounts.ExtendedAccountSummary;
import com.ffusion.beans.accounts.FixedDepositInstrument;
import com.ffusion.beans.accounts.FixedDepositInstruments;
import com.ffusion.beans.banking.RecTransfer;
import com.ffusion.beans.banking.RecTransfers;
import com.ffusion.beans.banking.Transaction;
import com.ffusion.beans.banking.Transactions;
import com.ffusion.beans.banking.Transfer;
import com.ffusion.beans.banking.Transfers;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.reporting.IReportResult;
import com.ffusion.services.Banking3;
import com.ffusion.services.BankingException;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.PagingContext;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;

public class Banking
  extends Service
  implements Banking3
{
  private Accounts ag;
  private Transfers aj;
  private RecTransfers al;
  private AccountSummaries ah;
  private AccountHistories ak;
  private ExtendedAccountSummaries ai;
  private FixedDepositInstruments am;
  public static final String FIRST_PAGING_INDEX = "FIRST_PAGING_INDEX";
  public static final String LAST_PAGING_INDEX = "LAST_PAGING_INDEX";
  
  public int initialize(String paramString)
  {
    return super.initialize("demo.xml");
  }
  
  public int getTransactions(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2)
  {
    return getTransactions(paramAccount);
  }
  
  public int getTransactions(Account paramAccount)
  {
    if (paramAccount == null) {
      return 2;
    }
    this.accounts = new Accounts(paramAccount.getLocale());
    jdMethod_if(true);
    Account localAccount = this.accounts.getByID(paramAccount.getID());
    if (localAccount != null)
    {
      GregorianCalendar localGregorianCalendar = new GregorianCalendar();
      int i = localGregorianCalendar.get(1);
      int j = localGregorianCalendar.get(2);
      Transactions localTransactions1 = localAccount.getTransactions();
      if (localTransactions1 == null) {
        localTransactions1 = new Transactions();
      }
      Transactions localTransactions2 = paramAccount.getTransactions();
      if (localTransactions2 != null)
      {
        localTransactions2.clear();
      }
      else
      {
        paramAccount.setTransactions(new Transactions(paramAccount.getLocale()));
        localTransactions2 = paramAccount.getTransactions();
      }
      for (int k = 0; k < localTransactions1.size(); k++)
      {
        Transaction localTransaction = (Transaction)localTransactions1.get(k);
        DateTime localDateTime = localTransaction.getDateValue();
        localDateTime.set(1, i);
        localDateTime.set(2, j);
        if (localDateTime.after(localGregorianCalendar)) {
          localDateTime.add(2, -1);
        }
        localTransactions2.add(localTransaction);
      }
      localTransactions2.setSortedBy("DATE");
    }
    this.accounts = null;
    if (paramAccount.getTransactions().size() > 0) {
      return 0;
    }
    return 0;
  }
  
  public int getTransfers(Accounts paramAccounts, Transfers paramTransfers)
  {
    this.aj = paramTransfers;
    this.ag = paramAccounts;
    jdMethod_if(false);
    this.aj = null;
    this.ag = null;
    int i = 0;
    if (paramTransfers.size() > 0)
    {
      GregorianCalendar localGregorianCalendar = new GregorianCalendar();
      int j = localGregorianCalendar.get(1);
      int k = localGregorianCalendar.get(2);
      Iterator localIterator = paramTransfers.iterator();
      while (localIterator.hasNext())
      {
        Transfer localTransfer = (Transfer)localIterator.next();
        DateTime localDateTime = localTransfer.getDateValue();
        localDateTime.set(1, j);
        localDateTime.set(2, k);
        if (localDateTime.before(localGregorianCalendar)) {
          localDateTime.add(2, 1);
        }
      }
      paramTransfers.setSortedBy("DATE");
      i = 0;
    }
    return i;
  }
  
  public int getRecTransfers(Accounts paramAccounts, RecTransfers paramRecTransfers)
  {
    this.al = paramRecTransfers;
    this.ag = paramAccounts;
    jdMethod_if(false);
    this.al = null;
    this.ag = null;
    int i = 0;
    if (paramRecTransfers.size() > 0)
    {
      GregorianCalendar localGregorianCalendar = new GregorianCalendar();
      int j = localGregorianCalendar.get(1);
      int k = localGregorianCalendar.get(2);
      Iterator localIterator = paramRecTransfers.iterator();
      while (localIterator.hasNext())
      {
        Transfer localTransfer = (Transfer)localIterator.next();
        DateTime localDateTime = localTransfer.getDateValue();
        localDateTime.set(1, j);
        localDateTime.set(2, k);
        if (localDateTime.before(localGregorianCalendar)) {
          localDateTime.add(2, 1);
        }
      }
      paramRecTransfers.setSortedBy("DATE");
      i = 0;
    }
    return i;
  }
  
  public int sendTransfer(Account paramAccount1, Account paramAccount2, Transfer paramTransfer, HashMap paramHashMap)
  {
    return sendTransfer(paramAccount1, paramAccount2, paramTransfer);
  }
  
  public int sendTransfer(Account paramAccount1, Account paramAccount2, Transfer paramTransfer)
  {
    paramTransfer.setID(Integer.toString((int)(Math.random() * 100000.0D)));
    paramTransfer.setStatus(2);
    paramTransfer.setReferenceNumber(String.valueOf((int)(Math.random() * 1000000.0D)));
    return 0;
  }
  
  public int cancelTransfer(Transfer paramTransfer)
  {
    paramTransfer.setStatus(3);
    return 0;
  }
  
  public int sendRecTransfer(Account paramAccount1, Account paramAccount2, RecTransfer paramRecTransfer)
  {
    paramRecTransfer.setID(Integer.toString((int)(Math.random() * 100000.0D)));
    paramRecTransfer.setStatus(2);
    paramRecTransfer.setReferenceNumber(String.valueOf((int)(Math.random() * 1000000.0D)));
    return 0;
  }
  
  public int sendRecTransfer(Account paramAccount1, Account paramAccount2, RecTransfer paramRecTransfer, HashMap paramHashMap)
  {
    paramRecTransfer.setID(Integer.toString((int)(Math.random() * 100000.0D)));
    paramRecTransfer.setStatus(2);
    paramRecTransfer.setReferenceNumber(String.valueOf((int)(Math.random() * 1000000.0D)));
    return 0;
  }
  
  public int cancelRecTransfer(RecTransfer paramRecTransfer)
  {
    if (paramRecTransfer != null) {
      paramRecTransfer.setStatus(3);
    }
    return 0;
  }
  
  public int modifyTransfer(Account paramAccount1, Account paramAccount2, Transfer paramTransfer)
  {
    paramTransfer.setReferenceNumber(String.valueOf((int)(Math.random() * 1000000.0D)));
    return 0;
  }
  
  public int modifyTransfer(Account paramAccount1, Account paramAccount2, Transfer paramTransfer, HashMap paramHashMap)
  {
    paramTransfer.setReferenceNumber(String.valueOf((int)(Math.random() * 1000000.0D)));
    return 0;
  }
  
  public int modifyRecTransfer(Account paramAccount1, Account paramAccount2, RecTransfer paramRecTransfer)
  {
    paramRecTransfer.setReferenceNumber(String.valueOf((int)(Math.random() * 1000000.0D)));
    return 0;
  }
  
  public int modifyRecTransfer(Account paramAccount1, Account paramAccount2, RecTransfer paramRecTransfer, HashMap paramHashMap)
  {
    paramRecTransfer.setReferenceNumber(String.valueOf((int)(Math.random() * 1000000.0D)));
    return 0;
  }
  
  public int skipRecTransfer(Account paramAccount1, Account paramAccount2, RecTransfer paramRecTransfer)
  {
    return 0;
  }
  
  public FixedDepositInstruments getFixedDepositInstruments(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws BankingException
  {
    if (paramAccount == null) {
      throw new BankingException(2);
    }
    this.am = new FixedDepositInstruments();
    jdMethod_if(false);
    String str1 = paramAccount.getNumber();
    String str2 = paramAccount.getBankID();
    String str3 = paramAccount.getRoutingNum();
    FixedDepositInstruments localFixedDepositInstruments = new FixedDepositInstruments();
    localFixedDepositInstruments.setLocale(paramAccount.getLocale());
    if ((str1 != null) && (str2 != null) && (str3 != null))
    {
      Iterator localIterator = this.am.iterator();
      while (localIterator.hasNext())
      {
        FixedDepositInstrument localFixedDepositInstrument = (FixedDepositInstrument)localIterator.next();
        localFixedDepositInstrument.setDataDate(new DateTime());
        if ((str1.equals(localFixedDepositInstrument.getAccountNumber())) && (str2.equals(localFixedDepositInstrument.getBankID())) && (str3.equals(localFixedDepositInstrument.getRoutingNumber())) && ((paramCalendar1 == null) || (jdMethod_if(localFixedDepositInstrument.getDataDate(), paramCalendar1))) && ((paramCalendar2 == null) || (a(localFixedDepositInstrument.getDataDate(), paramCalendar2)))) {
          localFixedDepositInstruments.add(localFixedDepositInstrument);
        }
      }
    }
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
    int i = getTransactions(paramAccount);
    if (i == 0)
    {
      Transactions localTransactions1 = paramAccount.getTransactions();
      localTransactions1.setSortedBy("TRANS_IDX");
      int j = 0;
      int k = jdMethod_if(paramHashMap);
      Transactions localTransactions2 = new Transactions();
      for (int m = localTransactions1.size() - 1; (m >= 0) && (j < k); m--)
      {
        localTransactions2.add(localTransactions1.get(m));
        j++;
      }
      PagingContext localPagingContext = new PagingContext(null, null);
      if (localTransactions2.size() > 0)
      {
        Transaction localTransaction = (Transaction)localTransactions2.get(localTransactions2.size() - 1);
        localPagingContext.setFirstIndex(localTransaction.getTransactionIndex());
        localTransaction = (Transaction)localTransactions2.get(0);
        localPagingContext.setLastIndex(localTransaction.getTransactionIndex());
        localTransaction = (Transaction)localTransactions1.get(0);
        long l = localTransaction.getTransactionIndex();
        HashMap localHashMap = new HashMap();
        localHashMap.put("FIRST_PAGING_INDEX", new Long(l));
        localHashMap.put("LAST_PAGING_INDEX", new Long(localPagingContext.getLastIndex()));
        localPagingContext.setMap(localHashMap);
        localPagingContext.setFirstPage(localTransactions1.size() <= k);
        localPagingContext.setLastPage(true);
      }
      else
      {
        localPagingContext.setFirstPage(true);
        localPagingContext.setLastPage(true);
      }
      localTransactions2.setPagingContext(localPagingContext);
      return localTransactions2;
    }
    throw new BankingException(i);
  }
  
  public Transactions getPagedTransactions(Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    int i = getTransactions(paramAccount);
    if (i == 0)
    {
      Transactions localTransactions1 = paramAccount.getTransactions();
      localTransactions1.setSortedBy("TRANS_IDX");
      int j = 0;
      int k = jdMethod_if(paramHashMap);
      Calendar localCalendar1 = paramPagingContext.getStartDate();
      Calendar localCalendar2 = paramPagingContext.getEndDate();
      Transactions localTransactions2 = new Transactions();
      Iterator localIterator = localTransactions1.iterator();
      while (localIterator.hasNext())
      {
        Transaction localTransaction1 = (Transaction)localIterator.next();
        if (((localCalendar1 == null) || (jdMethod_if(localTransaction1.getDateValue(), localCalendar1))) && ((localCalendar2 == null) || (a(localTransaction1.getDateValue(), localCalendar2))))
        {
          localTransactions2.add(localTransaction1);
          j++;
        }
      }
      long l1 = -1L;
      long l2 = -1L;
      Transaction localTransaction2;
      if (localTransactions2.size() > 0)
      {
        localTransaction2 = (Transaction)localTransactions2.get(0);
        l1 = localTransaction2.getTransactionIndex();
        localTransaction2 = (Transaction)localTransactions2.get(localTransactions2.size() - 1);
        l2 = localTransaction2.getTransactionIndex();
      }
      Transactions localTransactions3 = new Transactions();
      for (int m = 0; (m < localTransactions2.size()) && (m < k); m++) {
        localTransactions3.add(localTransactions2.get(m));
      }
      if (localTransactions3.size() > 0)
      {
        localTransaction2 = (Transaction)localTransactions3.get(0);
        paramPagingContext.setFirstIndex(localTransaction2.getTransactionIndex());
        localTransaction2 = (Transaction)localTransactions3.get(localTransactions3.size() - 1);
        paramPagingContext.setLastIndex(localTransaction2.getTransactionIndex());
        HashMap localHashMap = new HashMap();
        localHashMap.put("FIRST_PAGING_INDEX", new Long(l1));
        localHashMap.put("LAST_PAGING_INDEX", new Long(l2));
        paramPagingContext.setMap(localHashMap);
        paramPagingContext.setFirstPage(true);
        paramPagingContext.setLastPage(localTransactions2.size() <= k);
      }
      else
      {
        paramPagingContext.setFirstPage(true);
        paramPagingContext.setLastPage(true);
      }
      localTransactions3.setPagingContext(paramPagingContext);
      return localTransactions3;
    }
    throw new BankingException(i);
  }
  
  public Transactions getNextTransactions(Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    int i = getTransactions(paramAccount);
    if (i == 0)
    {
      Transactions localTransactions1 = paramAccount.getTransactions();
      localTransactions1.setSortedBy("TRANS_IDX");
      int j = 0;
      int k = jdMethod_if(paramHashMap);
      long l = paramPagingContext.getLastIndex() + 1L;
      Calendar localCalendar1 = paramPagingContext.getStartDate();
      Calendar localCalendar2 = paramPagingContext.getEndDate();
      Transactions localTransactions2 = new Transactions();
      for (int m = 0; (m < localTransactions1.size()) && (j < k); m++)
      {
        Transaction localTransaction2 = (Transaction)localTransactions1.get(m);
        if ((localTransaction2.getTransactionIndex() >= l) && ((localCalendar1 == null) || (jdMethod_if(localTransaction2.getDateValue(), localCalendar1))) && ((localCalendar2 == null) || (a(localTransaction2.getDateValue(), localCalendar2))))
        {
          localTransactions2.add(localTransaction2);
          j++;
        }
      }
      if (localTransactions2.size() > 0)
      {
        Transaction localTransaction1 = (Transaction)localTransactions2.get(0);
        paramPagingContext.setFirstIndex(localTransaction1.getTransactionIndex());
        localTransaction1 = (Transaction)localTransactions2.get(localTransactions2.size() - 1);
        paramPagingContext.setLastIndex(localTransaction1.getTransactionIndex());
        boolean bool = false;
        HashMap localHashMap = paramPagingContext.getMap();
        if (localHashMap != null)
        {
          Long localLong = (Long)localHashMap.get("LAST_PAGING_INDEX");
          if (localLong != null) {
            bool = localLong.longValue() == paramPagingContext.getLastIndex();
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
      localTransactions2.setPagingContext(paramPagingContext);
      return localTransactions2;
    }
    throw new BankingException(i);
  }
  
  public Transactions getPreviousTransactions(Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    int i = getTransactions(paramAccount);
    if (i == 0)
    {
      Transactions localTransactions1 = paramAccount.getTransactions();
      localTransactions1.setSortedBy("TRANS_IDX");
      int j = 0;
      int k = jdMethod_if(paramHashMap);
      long l = paramPagingContext.getFirstIndex();
      Calendar localCalendar1 = paramPagingContext.getStartDate();
      Calendar localCalendar2 = paramPagingContext.getEndDate();
      Transactions localTransactions2 = new Transactions();
      for (int m = localTransactions1.size() - 1; (m >= 0) && (j < k); m--)
      {
        Transaction localTransaction2 = (Transaction)localTransactions1.get(m);
        if ((localTransaction2.getTransactionIndex() < l) && ((localCalendar1 == null) || (jdMethod_if(localTransaction2.getDateValue(), localCalendar1))) && ((localCalendar2 == null) || (a(localTransaction2.getDateValue(), localCalendar2))))
        {
          localTransactions2.add(localTransaction2);
          j++;
        }
      }
      if (localTransactions2.size() > 0)
      {
        Transaction localTransaction1 = (Transaction)localTransactions2.get(localTransactions2.size() - 1);
        paramPagingContext.setFirstIndex(localTransaction1.getTransactionIndex());
        localTransaction1 = (Transaction)localTransactions2.get(0);
        paramPagingContext.setLastIndex(localTransaction1.getTransactionIndex());
        boolean bool = false;
        HashMap localHashMap = paramPagingContext.getMap();
        if (localHashMap != null)
        {
          Long localLong = (Long)localHashMap.get("FIRST_PAGING_INDEX");
          if (localLong != null) {
            bool = localLong.longValue() == paramPagingContext.getFirstIndex();
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
      localTransactions2.setPagingContext(paramPagingContext);
      return localTransactions2;
    }
    throw new BankingException(i);
  }
  
  public Transactions getFDInstrumentTransactions(FixedDepositInstrument paramFixedDepositInstrument, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws BankingException
  {
    if (paramFixedDepositInstrument == null) {
      throw new BankingException(4);
    }
    this.accounts = new Accounts(paramFixedDepositInstrument.getLocale());
    jdMethod_if(true);
    String str1 = paramFixedDepositInstrument.getAccountNumber();
    String str2 = paramFixedDepositInstrument.getBankID();
    Account localAccount = this.accounts.getByNumberAndBankID(str1, str2);
    if (localAccount == null) {
      throw new BankingException(2);
    }
    String str3 = paramFixedDepositInstrument.getInstrumentNumber();
    String str4 = paramFixedDepositInstrument.getInstrumentBankName();
    Transactions localTransactions1 = localAccount.getTransactions();
    Transactions localTransactions2 = new Transactions(paramFixedDepositInstrument.getLocale());
    if ((localTransactions1 != null) && (str3 != null) && (str4 != null))
    {
      Iterator localIterator = localTransactions1.iterator();
      while (localIterator.hasNext())
      {
        Transaction localTransaction = (Transaction)localIterator.next();
        if ((str3.equals(localTransaction.getInstrumentNumber())) && (str4.equals(localTransaction.getInstrumentBankName())) && ((paramCalendar1 == null) || (jdMethod_if(localTransaction.getDateValue(), paramCalendar1))) && ((paramCalendar2 == null) || (a(localTransaction.getDateValue(), paramCalendar2)))) {
          localTransactions2.add(localTransaction);
        }
      }
    }
    return localTransactions2;
  }
  
  public int getNumTransactions(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws BankingException
  {
    int i = getTransactions(paramAccount);
    if (i == 0)
    {
      Transactions localTransactions = paramAccount.getTransactions();
      int j = 0;
      Iterator localIterator = localTransactions.iterator();
      while (localIterator.hasNext())
      {
        Transaction localTransaction = (Transaction)localIterator.next();
        if (((paramCalendar1 == null) || (jdMethod_if(localTransaction.getDateValue(), paramCalendar1))) && ((paramCalendar2 == null) || (a(localTransaction.getDateValue(), paramCalendar2)))) {
          j++;
        }
      }
      return j;
    }
    throw new BankingException(i);
  }
  
  public AccountHistories getHistory(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws BankingException
  {
    if (paramAccount == null) {
      throw new BankingException(2);
    }
    this.ak = new AccountHistories();
    jdMethod_if(false);
    String str1 = paramAccount.getNumber();
    String str2 = paramAccount.getBankID();
    String str3 = paramAccount.getRoutingNum();
    AccountHistories localAccountHistories = new AccountHistories();
    if ((str1 != null) && (str2 != null) && (str3 != null))
    {
      Iterator localIterator = this.ak.iterator();
      while (localIterator.hasNext())
      {
        AccountHistory localAccountHistory = (AccountHistory)localIterator.next();
        if ((str1.equals(localAccountHistory.getAccountNumber())) && (str2.equals(localAccountHistory.getBankID())) && (str3.equals(localAccountHistory.getRoutingNumber())) && ((paramCalendar1 == null) || (jdMethod_if(localAccountHistory.getHistoryDate(), paramCalendar1))) && ((paramCalendar2 == null) || (a(localAccountHistory.getHistoryDate(), paramCalendar2)))) {
          localAccountHistories.add(localAccountHistory);
        }
      }
    }
    return localAccountHistories;
  }
  
  public AccountSummaries getSummary(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws BankingException
  {
    if (paramAccount == null) {
      throw new BankingException(2);
    }
    this.ah = new AccountSummaries();
    jdMethod_if(false);
    String str1 = paramAccount.getNumber();
    String str2 = paramAccount.getBankID();
    String str3 = paramAccount.getRoutingNum();
    AccountSummaries localAccountSummaries = new AccountSummaries();
    if ((str1 != null) && (str2 != null) && (str3 != null))
    {
      Iterator localIterator = this.ah.iterator();
      while (localIterator.hasNext())
      {
        AccountSummary localAccountSummary = (AccountSummary)localIterator.next();
        localAccountSummary.setSummaryDate(new DateTime());
        if ((str1.equals(localAccountSummary.getAccountNumber())) && (str2.equals(localAccountSummary.getBankID())) && (str3.equals(localAccountSummary.getRoutingNumber())) && ((paramCalendar1 == null) || (jdMethod_if(localAccountSummary.getSummaryDate(), paramCalendar1))) && ((paramCalendar2 == null) || (a(localAccountSummary.getSummaryDate(), paramCalendar2)))) {
          localAccountSummaries.add(localAccountSummary);
        }
      }
    }
    return localAccountSummaries;
  }
  
  public ExtendedAccountSummaries getExtendedSummary(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws BankingException
  {
    if (paramAccount == null) {
      throw new BankingException(2);
    }
    this.ai = new ExtendedAccountSummaries();
    jdMethod_if(false);
    String str1 = paramAccount.getNumber();
    String str2 = paramAccount.getBankID();
    String str3 = paramAccount.getRoutingNum();
    ExtendedAccountSummaries localExtendedAccountSummaries = new ExtendedAccountSummaries();
    if ((str1 != null) && (str2 != null) && (str3 != null))
    {
      Iterator localIterator = this.ai.iterator();
      while (localIterator.hasNext())
      {
        ExtendedAccountSummary localExtendedAccountSummary = (ExtendedAccountSummary)localIterator.next();
        if ((str1.equals(localExtendedAccountSummary.getAccountNumber())) && (str2.equals(localExtendedAccountSummary.getBankID())) && (str3.equals(localExtendedAccountSummary.getRoutingNumber())) && ((paramCalendar1 == null) || (jdMethod_if(localExtendedAccountSummary.getSummaryDate(), paramCalendar1))) && ((paramCalendar2 == null) || (a(localExtendedAccountSummary.getSummaryDate(), paramCalendar2)))) {
          localExtendedAccountSummaries.add(localExtendedAccountSummary);
        }
      }
    }
    return localExtendedAccountSummaries;
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
    String str2 = localProperties1.getProperty("DATEFORMAT");
    Properties localProperties2 = paramReportCriteria.getSearchCriteria();
    if (localProperties2 == null) {
      throw new BankingException(403);
    }
    String str3 = localProperties2.getProperty("Accounts");
    if (str3 == null) {
      throw new BankingException(404, "Search Criteria key Account does not exist.");
    }
    this.accounts = new Accounts();
    this.ak = new AccountHistories();
    jdMethod_if(true);
    Accounts localAccounts = null;
    AccountHistories localAccountHistories = null;
    Locale localLocale = this.accounts.getLocale();
    Object localObject3;
    Object localObject4;
    Object localObject5;
    Object localObject6;
    String str4;
    Object localObject7;
    if ((str3.equals("AllAccounts")) || (str3.equals("AllAccounts")))
    {
      localAccounts = this.accounts;
      localAccountHistories = this.ak;
    }
    else
    {
      localObject1 = new StringTokenizer(str3, ":");
      int i = ((StringTokenizer)localObject1).countTokens();
      if (i == 3)
      {
        localObject3 = ((StringTokenizer)localObject1).nextToken();
        localObject4 = ((StringTokenizer)localObject1).nextToken();
        localObject5 = ((StringTokenizer)localObject1).nextToken();
        localAccounts = new Accounts();
        localAccountHistories = new AccountHistories();
        Iterator localIterator = this.accounts.iterator();
        while (localIterator.hasNext())
        {
          localObject6 = (Account)localIterator.next();
          if ((((String)localObject3).equals(((Account)localObject6).getNumber())) && (((String)localObject4).equals(((Account)localObject6).getBankID())))
          {
            this.errorCode = getTransactions((Account)localObject6);
            if (this.errorCode != 0) {
              throw new BankingException(this.errorCode);
            }
            Transactions localTransactions = ((Account)localObject6).getTransactions();
            str4 = localProperties2.getProperty("StartDate");
            localObject7 = localProperties2.getProperty("EndDate");
            String str5 = localProperties2.getProperty("FixedDepositInstrumentNumber");
            String str6 = localProperties2.getProperty("FixedDepositInstrumentBankName");
            localTransactions.setFilter("All");
            if (str4 != null) {
              localTransactions.setFilter("DATE>=" + str4);
            }
            if (localObject7 != null) {
              if (localTransactions.getFilter().equals("All")) {
                localTransactions.setFilter("DATE<=" + (String)localObject7);
              } else {
                localTransactions.setFilterOnFilter("DATE<=" + (String)localObject7);
              }
            }
            if (str5 != null) {
              if (localTransactions.getFilter().equals("All")) {
                localTransactions.setFilter("INSTRUMENT_NUMBER=" + str5);
              } else {
                localTransactions.setFilterOnFilter("INSTRUMENT_NUMBER=" + str5);
              }
            }
            if (str6 != null) {
              if (localTransactions.getFilter().equals("All")) {
                localTransactions.setFilter("INSTRUMENT_BANK_NAME=" + str6);
              } else {
                localTransactions.setFilterOnFilter("INSTRUMENT_BANK_NAME=" + str6);
              }
            }
            localAccounts.add(localObject6);
            break;
          }
        }
        localIterator = this.ak.iterator();
        while (localIterator.hasNext())
        {
          localObject6 = (AccountHistory)localIterator.next();
          if ((((String)localObject3).equals(((AccountHistory)localObject6).getAccountNumber())) && (((String)localObject4).equals(((AccountHistory)localObject6).getBankID())))
          {
            localAccountHistories.add(localObject6);
            break;
          }
        }
      }
      else
      {
        throw new BankingException(405, "Value for SearchCriteria key Account has incorrect format.");
      }
    }
    Object localObject1 = null;
    Object localObject2;
    if ((str1.equals("DepositDetail")) || (str1.equals("TransactionDetail")) || (str1.equals("CreditReport")) || (str1.equals("DebitReport")))
    {
      localObject2 = new AcctTransactionRpt(localLocale);
      localAccounts.setDateFormat(str2);
      ((AcctTransactionRpt)localObject2).setAccounts(localAccounts);
      localObject1 = localObject2;
    }
    else if (str1.equals("GeneralLedgerReport"))
    {
      localObject2 = new AcctGeneralLedgerRpt(localLocale);
      localAccounts.setDateFormat(str2);
      ((AcctGeneralLedgerRpt)localObject2).setAccounts(localAccounts);
      localObject1 = localObject2;
    }
    else if ((str1.equals("BalanceSheetReport")) || (str1.equals("BalanceSheetSummary")))
    {
      localObject2 = new AcctBalanceSheetRpt(localLocale);
      localAccounts.setDateFormat(str2);
      ((AcctBalanceSheetRpt)localObject2).setRequestedAccounts(localAccounts);
      localObject1 = localObject2;
    }
    else if (str1.equals("CashFlowForecastReport"))
    {
      localObject2 = new AcctCashFlowFcstRpt(localLocale);
      localAccounts.setDateFormat(str2);
      localAccountHistories.setDateFormat(str2);
      ((AcctCashFlowFcstRpt)localObject2).setAccounts(localAccounts);
      ((AcctCashFlowFcstRpt)localObject2).setAccountHistories(localAccountHistories);
      localObject1 = localObject2;
    }
    else if (str1.equals("CashFlowReport"))
    {
      localObject2 = new AcctCashFlowRpt(localLocale);
      localAccountHistories.setDateFormat(str2);
      ((AcctCashFlowRpt)localObject2).setBeginningAccountHistories(localAccountHistories);
      ((AcctCashFlowRpt)localObject2).setEndingAccountHistories(localAccountHistories);
      localObject1 = localObject2;
    }
    else if (str1.equals("BalanceSummaryReport"))
    {
      localObject2 = new BalanceSummaryRpt(localLocale);
      localAccounts.setDateFormat(str2);
      localAccountHistories.setDateFormat(str2);
      localObject3 = new BalanceSummaryRecords();
      ((BalanceSummaryRecords)localObject3).setDateFormat(str2);
      ((BalanceSummaryRpt)localObject2).setBalanceSummaryRecords((BalanceSummaryRecords)localObject3);
      localObject4 = null;
      localObject5 = null;
      int j = 0;
      localObject6 = null;
      int k = 0;
      str4 = null;
      localObject7 = localAccountHistories.iterator();
      while (((Iterator)localObject7).hasNext())
      {
        localObject4 = (AccountHistory)((Iterator)localObject7).next();
        localObject5 = localAccounts.getByNumberAndBankIDAndRoutingNumber(((AccountHistory)localObject4).getAccountNumber(), ((AccountHistory)localObject4).getBankID(), ((AccountHistory)localObject4).getRoutingNumber());
        ((BalanceSummaryRecords)localObject3).create((Account)localObject5, (AccountHistory)localObject4, j, (Currency)localObject6, k, str4);
      }
      localObject1 = localObject2;
    }
    else
    {
      throw new BankingException(408, str1 + " is an invalid Account Report Type");
    }
    return localObject1;
  }
  
  public Transfers getPagedPendingTransfers(SecureUser paramSecureUser, Accounts paramAccounts, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    paramPagingContext.setDirection("FIRST");
    return a(paramAccounts, paramPagingContext, 2, paramHashMap);
  }
  
  public Transfers getNextPendingTransfers(SecureUser paramSecureUser, Accounts paramAccounts, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    paramPagingContext.setDirection("NEXT");
    return a(paramAccounts, paramPagingContext, 2, paramHashMap);
  }
  
  public Transfers getPreviousPendingTransfers(SecureUser paramSecureUser, Accounts paramAccounts, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    paramPagingContext.setDirection("PREVIOUS");
    return a(paramAccounts, paramPagingContext, 2, paramHashMap);
  }
  
  public Transfers getPagedCompletedTransfers(SecureUser paramSecureUser, Accounts paramAccounts, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    paramPagingContext.setDirection("FIRST");
    return a(paramAccounts, paramPagingContext, 5, paramHashMap);
  }
  
  public Transfers getNextCompletedTransfers(SecureUser paramSecureUser, Accounts paramAccounts, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    paramPagingContext.setDirection("NEXT");
    return a(paramAccounts, paramPagingContext, 5, paramHashMap);
  }
  
  public Transfers getPreviousCompletedTransfers(SecureUser paramSecureUser, Accounts paramAccounts, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    paramPagingContext.setDirection("PREVIOUS");
    return a(paramAccounts, paramPagingContext, 5, paramHashMap);
  }
  
  private Transfers a(Accounts paramAccounts, PagingContext paramPagingContext, int paramInt, HashMap paramHashMap)
  {
    if (paramAccounts == null) {
      return null;
    }
    Transfers localTransfers1 = new Transfers();
    try
    {
      Transfers localTransfers2 = new Transfers();
      int i = getTransfers(paramAccounts, localTransfers2);
      if ((i == 0) && (localTransfers2 != null))
      {
        long l = 0L;
        String str1 = "DATE,REVERSE";
        if (paramPagingContext.getDirection().equalsIgnoreCase("NEXT"))
        {
          str1 = "DATE";
          l = paramPagingContext.getFirstIndex();
        }
        else if (paramPagingContext.getDirection().equalsIgnoreCase("PREVIOUS"))
        {
          l = paramPagingContext.getLastIndex();
        }
        String str2 = String.valueOf(l);
        localTransfers2.setSortedBy(str1);
        int j = 0;
        int k = jdMethod_if(paramHashMap);
        int m = 0;
        if (l == 0L) {
          m = 1;
        }
        Object localObject;
        for (int n = 0; (n < localTransfers2.size()) && (j < k); n++)
        {
          localObject = (Transfer)localTransfers2.get(n);
          if ((m != 0) && (((Transfer)localObject).getStatus() == paramInt) && ((paramPagingContext.getStartDate() == null) || (((Transfer)localObject).getDateValue().after(paramPagingContext.getStartDate()))) && ((paramPagingContext.getEndDate() == null) || (((Transfer)localObject).getDateValue().before(paramPagingContext.getEndDate()))))
          {
            localTransfers1.add(localObject);
            j++;
          }
          else if ((m == 0) && (((Transfer)localObject).getID().equals(str2)))
          {
            m = 1;
          }
        }
        paramPagingContext.setFirstPage(false);
        paramPagingContext.setLastPage(false);
        if (localTransfers1.size() < k) {
          paramPagingContext.setLastPage(true);
        }
        HashMap localHashMap;
        if (paramPagingContext.getDirection().equalsIgnoreCase("FIRST"))
        {
          paramPagingContext.setFirstPage(true);
          if (localTransfers1.size() > 0)
          {
            localHashMap = new HashMap();
            localHashMap.put("FIRSTPAGEINDEX", ((Transfer)localTransfers1.get(0)).getID());
            paramPagingContext.setMap(localHashMap);
          }
        }
        else
        {
          localHashMap = paramPagingContext.getMap();
          if ((localHashMap != null) && (localTransfers1.size() > 0))
          {
            localObject = (String)localHashMap.get("FIRSTPAGEINDEX");
            if (localTransfers1.getByID((String)localObject) != null) {
              paramPagingContext.setFirstPage(true);
            }
          }
        }
        if (localTransfers1.size() == 0)
        {
          paramPagingContext.setFirstIndex(0L);
          paramPagingContext.setLastIndex(0L);
        }
        else
        {
          if (str1.equals("DATE")) {
            localTransfers1.setSortedBy("DATE,REVERSE");
          }
          paramPagingContext.setFirstIndex(Integer.parseInt(((Transfer)localTransfers1.get(0)).getID()));
          paramPagingContext.setLastIndex(Integer.parseInt(((Transfer)localTransfers1.get(localTransfers1.size() - 1)).getID()));
        }
        localTransfers1.setPagingContext(paramPagingContext);
      }
    }
    catch (Exception localException)
    {
      System.out.println("error");
    }
    return localTransfers1;
  }
  
  private boolean a(DateTime paramDateTime, Calendar paramCalendar)
  {
    if ((paramDateTime == null) || (paramCalendar == null)) {
      return false;
    }
    return (paramDateTime.get(1) < paramCalendar.get(1)) || ((paramDateTime.get(1) == paramCalendar.get(1)) && (paramDateTime.get(2) < paramCalendar.get(2))) || ((paramDateTime.get(1) == paramCalendar.get(1)) && (paramDateTime.get(2) == paramCalendar.get(2)) && (paramDateTime.get(5) <= paramCalendar.get(5)));
  }
  
  private boolean jdMethod_if(DateTime paramDateTime, Calendar paramCalendar)
  {
    if ((paramDateTime == null) || (paramCalendar == null)) {
      return false;
    }
    return (paramDateTime.get(1) > paramCalendar.get(1)) || ((paramDateTime.get(1) == paramCalendar.get(1)) && (paramDateTime.get(2) > paramCalendar.get(2))) || ((paramDateTime.get(1) == paramCalendar.get(1)) && (paramDateTime.get(2) == paramCalendar.get(2)) && (paramDateTime.get(5) >= paramCalendar.get(5)));
  }
  
  private int jdMethod_if(HashMap paramHashMap)
    throws BankingException
  {
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
  
  private void jdMethod_if(boolean paramBoolean)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(paramBoolean), getXMLReader(this, this.m_URL));
    }
    catch (Throwable localThrowable) {}
  }
  
  protected class a
    extends Service.InternalXMLHandler
  {
    public a(boolean paramBoolean)
    {
      super();
      this.getTransactions = paramBoolean;
    }
    
    public void startElement(String paramString)
    {
      if ((paramString.equals("TRANSFERS")) && (Banking.this.aj != null)) {
        Banking.this.aj.continueXMLParsing(getHandler(), Banking.this.ag);
      } else if ((paramString.equals("RECTRANSFERS")) && (Banking.this.al != null)) {
        Banking.this.al.continueXMLParsing(getHandler(), Banking.this.ag);
      } else if ((paramString.equals("ACCOUNT_SUMMARIES")) && (Banking.this.ah != null)) {
        Banking.this.ah.continueXMLParsing(getHandler());
      } else if ((paramString.equals("ACCOUNT_HISTORIES")) && (Banking.this.ak != null)) {
        Banking.this.ak.continueXMLParsing(getHandler());
      } else if ((paramString.equals("EXTENDED_ACCOUNT_SUMMARIES")) && (Banking.this.ai != null)) {
        Banking.this.ai.continueXMLParsing(getHandler());
      } else if ((paramString.equals("FIXED_DEP_INSTRUMENTS")) && (Banking.this.am != null)) {
        Banking.this.am.continueXMLParsing(getHandler());
      } else {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.demo.Banking
 * JD-Core Version:    0.7.0.1
 */