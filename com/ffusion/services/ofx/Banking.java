package com.ffusion.services.ofx;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
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
import com.ffusion.beans.banking.Transactions;
import com.ffusion.beans.banking.Transfer;
import com.ffusion.beans.banking.Transfers;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.reporting.IReportResult;
import com.ffusion.services.Banking3;
import com.ffusion.services.BankingException;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.PagingContext;
import com.ffusion.util.logging.DebugLog;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;

public class Banking
  extends Base
  implements BankingDefines, Banking3
{
  protected static final String MAX_TRANSACTION_DAYS = "MaxTransactionDays";
  protected static final int ISBANK = 0;
  protected static final int ISTRANSFER = 1;
  protected static final int ISRECTRANSFER = 2;
  protected int maxTransactionDays = 90;
  protected Transactions transactions;
  protected Transaction transaction;
  protected Transfers transfers;
  protected Transfer transfer;
  protected Account fromAccount;
  protected Account toAccount;
  protected RecTransfers rectransfers;
  protected RecTransfer rectransfer;
  protected String m_TransferToken;
  protected String m_RecTransferToken;
  protected SimpleDateFormat xferProcessDateFormatter = new SimpleDateFormat("MM/dd/yyyy");
  
  public int initialize(String paramString)
  {
    return initialize(paramString, new BankingXMLHandler());
  }
  
  public String getSettings()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "BANKINGSERVICE");
    XMLHandler.appendTag(localStringBuffer, "MaxTransactionDays", this.maxTransactionDays);
    localStringBuffer.append(super.getSettings());
    XMLHandler.appendEndTag(localStringBuffer, "BANKINGSERVICE");
    return localStringBuffer.toString();
  }
  
  protected boolean handleResponse(char[] paramArrayOfChar)
  {
    boolean bool = false;
    this.objStatus.clear();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".handleResponse:");
    }
    if ((paramArrayOfChar != null) && (startResponse(paramArrayOfChar)))
    {
      String str;
      while (((str = getToken()) != null) && (!str.equals("OFX"))) {
        if (str.equals("SIGNONMSGSRSV1")) {
          parseSIGNONMSGSRSV1();
        } else if (str.equals("BANKMSGSRSV1")) {
          parseBANKMSGSRSV1();
        } else if (str.equals("CREDITCARDMSGSRSV1")) {
          parseCREDITCARDMSGSRSV1();
        } else if (!handleResponseNotHandled(str)) {
          logError(3);
        }
      }
      if ((str != null) && (str.equals("OFX"))) {
        bool = true;
      }
    }
    return bool;
  }
  
  protected boolean handleEResponse(char[] paramArrayOfChar)
  {
    boolean bool = false;
    this.objStatus.clear();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".handleEResponse:");
    }
    if ((paramArrayOfChar != null) && (startResponse(paramArrayOfChar)))
    {
      String str;
      while (((str = getToken()) != null) && (!str.equals("OFX"))) {
        if (str.equals("SIGNONMSGSRSV1")) {
          parseSIGNONMSGSRSV1();
        } else if (!handleEResponseNotHandled(str)) {
          logError(3);
        }
      }
      if ((str != null) && (str.equals("OFX"))) {
        bool = true;
      }
    }
    return bool;
  }
  
  protected boolean cleanupRequest()
  {
    this.accounts = null;
    this.account = null;
    this.fromAccount = null;
    this.toAccount = null;
    this.transactions = null;
    this.transaction = null;
    this.transfers = null;
    this.transfer = null;
    this.rectransfers = null;
    this.rectransfer = null;
    return checkStatus();
  }
  
  public int transfer(Currency paramCurrency, Account paramAccount1, Account paramAccount2, StringBuffer paramStringBuffer)
  {
    boolean bool = false;
    logStart(getClass().getName() + ".transfer:");
    char[] arrayOfChar = sendRequest(formatGetTransferRequest(paramCurrency, paramAccount1, paramAccount2));
    bool = handleResponse(arrayOfChar);
    logEnd();
    if (!cleanupRequest()) {
      bool = false;
    }
    if (bool)
    {
      paramStringBuffer.setLength(0);
      if (this.savedValues.get("SRVRTID") != null) {
        paramStringBuffer.append(this.savedValues.get("SRVRTID"));
      }
    }
    if ((bool) && (this.status != 0))
    {
      bool = false;
      logError(mapError(this.status));
    }
    if (bool) {
      return 0;
    }
    return this.lastError;
  }
  
  public int getTransactions(Account paramAccount)
  {
    GregorianCalendar localGregorianCalendar1 = new GregorianCalendar();
    GregorianCalendar localGregorianCalendar2 = new GregorianCalendar();
    localGregorianCalendar1.add(5, -1 * this.maxTransactionDays);
    localGregorianCalendar2.add(5, 1);
    GregorianCalendar localGregorianCalendar3 = new GregorianCalendar(localGregorianCalendar1.get(1), localGregorianCalendar1.get(2), localGregorianCalendar1.get(5));
    return getTransactions(paramAccount, localGregorianCalendar3, localGregorianCalendar2, "yyyyMMdd");
  }
  
  public int getTransactions(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2)
  {
    GregorianCalendar localGregorianCalendar = new GregorianCalendar(paramCalendar1.get(1), paramCalendar1.get(2), paramCalendar1.get(5));
    return getTransactions(paramAccount, localGregorianCalendar, paramCalendar2, "yyyyMMdd");
  }
  
  protected int getTransactions(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, String paramString)
  {
    boolean bool = false;
    this.account = paramAccount;
    this.transactions = paramAccount.getTransactions();
    logStart(getClass().getName() + ".getTransactions:");
    char[] arrayOfChar = sendRequest(formatGetTransactionsRequest(paramAccount, paramCalendar1, paramCalendar2, paramString));
    bool = handleResponse(arrayOfChar);
    logEnd();
    cleanupRequest();
    if ((bool) && (this.status != 0))
    {
      bool = false;
      logError(mapError(this.status));
    }
    if (bool) {
      return 0;
    }
    return this.lastError;
  }
  
  public int getTransfers(Accounts paramAccounts, Transfers paramTransfers)
  {
    boolean bool = false;
    this.accounts = ((Accounts)paramAccounts.clone());
    this.accounts.setFilter("All");
    this.transfers = paramTransfers;
    this.rectransfers = null;
    logStart(getClass().getName() + ".getTransfers:");
    char[] arrayOfChar = sendRequest(formatGetTransfersSyncRequest());
    bool = handleResponse(arrayOfChar);
    logEnd();
    cleanupRequest();
    this.status = 0;
    if ((bool) && (this.status != 0))
    {
      bool = false;
      logError(mapError(this.status));
    }
    if (bool) {
      return 0;
    }
    return this.lastError;
  }
  
  public int getRecTransfers(Accounts paramAccounts, RecTransfers paramRecTransfers)
  {
    boolean bool = false;
    this.accounts = ((Accounts)paramAccounts.clone());
    this.accounts.setFilter("All");
    this.rectransfers = paramRecTransfers;
    logStart(getClass().getName() + ".getRecTransfers:");
    char[] arrayOfChar = sendRequest(formatGetRecTransfersSyncRequest());
    bool = handleResponse(arrayOfChar);
    logEnd();
    cleanupRequest();
    this.status = 0;
    if ((bool) && (this.status != 0))
    {
      bool = false;
      logError(mapError(this.status));
    }
    if (bool) {
      return 0;
    }
    return this.lastError;
  }
  
  public int sendTransfer(Account paramAccount1, Account paramAccount2, Transfer paramTransfer, HashMap paramHashMap)
  {
    return sendTransfer(paramAccount1, paramAccount2, paramTransfer);
  }
  
  public int sendTransfer(Account paramAccount1, Account paramAccount2, Transfer paramTransfer)
  {
    boolean bool = false;
    this.fromAccount = paramAccount1;
    this.toAccount = paramAccount2;
    this.transfer = paramTransfer;
    this.transfers = new Transfers(paramTransfer.getLocale());
    this.transfers.add(paramTransfer);
    logStart(getClass().getName() + ".sendTransfer:");
    char[] arrayOfChar = sendRequest(formatSendTransferRequest(paramTransfer));
    bool = handleResponse(arrayOfChar);
    logEnd();
    cleanupRequest();
    if ((bool) && (this.status != 0))
    {
      bool = false;
      logError(mapError(this.status));
    }
    if (bool) {
      return 0;
    }
    return this.lastError;
  }
  
  public int cancelTransfer(Transfer paramTransfer)
  {
    boolean bool = false;
    this.transfer = paramTransfer;
    this.transfers = null;
    logStart(getClass().getName() + ".cancelTransfer:");
    char[] arrayOfChar = sendRequest(formatDeleteTransferRequest(paramTransfer));
    bool = handleResponse(arrayOfChar);
    logEnd();
    cleanupRequest();
    if ((bool) && (this.status != 0))
    {
      bool = false;
      logError(mapError(this.status));
    }
    if (bool) {
      return 0;
    }
    return this.lastError;
  }
  
  public int sendRecTransfer(Account paramAccount1, Account paramAccount2, RecTransfer paramRecTransfer)
  {
    boolean bool = false;
    this.fromAccount = paramAccount1;
    this.toAccount = paramAccount2;
    this.rectransfer = paramRecTransfer;
    this.rectransfers = new RecTransfers(paramRecTransfer.getLocale());
    this.rectransfers.add(paramRecTransfer);
    logStart(getClass().getName() + ".sendRecTransfer:");
    char[] arrayOfChar = sendRequest(formatSendRecTransferRequest(paramRecTransfer));
    bool = handleResponse(arrayOfChar);
    logEnd();
    cleanupRequest();
    if ((bool) && (this.status != 0))
    {
      bool = false;
      logError(mapError(this.status));
    }
    if (bool) {
      return 0;
    }
    return this.lastError;
  }
  
  public int cancelRecTransfer(RecTransfer paramRecTransfer)
  {
    boolean bool = false;
    this.rectransfer = paramRecTransfer;
    this.rectransfers = null;
    logStart(getClass().getName() + ".cancelRecTransfer:");
    char[] arrayOfChar = sendRequest(formatDeleteRecTransferRequest(paramRecTransfer));
    bool = handleResponse(arrayOfChar);
    logEnd();
    cleanupRequest();
    if ((bool) && (this.status != 0))
    {
      bool = false;
      logError(mapError(this.status));
    }
    if (bool) {
      return 0;
    }
    return this.lastError;
  }
  
  public int modifyTransfer(Account paramAccount1, Account paramAccount2, Transfer paramTransfer, HashMap paramHashMap)
  {
    return modifyTransfer(paramAccount1, paramAccount2, paramTransfer);
  }
  
  public int modifyTransfer(Account paramAccount1, Account paramAccount2, Transfer paramTransfer)
  {
    boolean bool = false;
    this.fromAccount = paramAccount1;
    this.toAccount = paramAccount2;
    this.transfer = paramTransfer;
    this.transfers = new Transfers(paramTransfer.getLocale());
    this.transfers.add(paramTransfer);
    logStart(getClass().getName() + ".modifyTransfer:");
    char[] arrayOfChar = sendRequest(formatModifyTransferRequest(paramTransfer));
    bool = handleResponse(arrayOfChar);
    logEnd();
    cleanupRequest();
    if ((bool) && (this.status != 0))
    {
      bool = false;
      logError(mapError(this.status));
    }
    if (bool) {
      return 0;
    }
    return this.lastError;
  }
  
  public int modifyRecTransfer(Account paramAccount1, Account paramAccount2, RecTransfer paramRecTransfer)
  {
    boolean bool = false;
    this.fromAccount = paramAccount1;
    this.toAccount = paramAccount2;
    this.rectransfer = paramRecTransfer;
    this.rectransfers = new RecTransfers(paramRecTransfer.getLocale());
    this.rectransfers.add(paramRecTransfer);
    logStart(getClass().getName() + ".modifyRecTransfer:");
    char[] arrayOfChar = sendRequest(formatModifyRecTransferRequest(paramRecTransfer));
    bool = handleResponse(arrayOfChar);
    logEnd();
    cleanupRequest();
    if ((bool) && (this.status != 0))
    {
      bool = false;
      logError(mapError(this.status));
    }
    if (bool) {
      return 0;
    }
    return this.lastError;
  }
  
  public int skipRecTransfer(Account paramAccount1, Account paramAccount2, RecTransfer paramRecTransfer)
  {
    boolean bool = false;
    this.fromAccount = paramAccount1;
    this.toAccount = paramAccount2;
    this.rectransfer = paramRecTransfer;
    this.rectransfers = new RecTransfers(paramRecTransfer.getLocale());
    this.rectransfers.add(paramRecTransfer);
    logStart(getClass().getName() + ".skipRecTransfer:");
    char[] arrayOfChar = sendRequest(formatSkipRecTransferRequest(paramRecTransfer));
    bool = handleResponse(arrayOfChar);
    logEnd();
    cleanupRequest();
    if ((bool) && (this.status != 0))
    {
      bool = false;
      logError(mapError(this.status));
    }
    if (bool) {
      return 0;
    }
    return this.lastError;
  }
  
  public FixedDepositInstruments getFixedDepositInstruments(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws BankingException
  {
    throw new BankingException(2);
  }
  
  public void updateFixedDepositInstrument(FixedDepositInstrument paramFixedDepositInstrument, HashMap paramHashMap)
    throws BankingException
  {
    throw new BankingException(2);
  }
  
  public Transactions getRecentTransactions(Account paramAccount, HashMap paramHashMap)
    throws BankingException
  {
    throw new BankingException(2);
  }
  
  public Transactions getPagedTransactions(Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    throw new BankingException(2);
  }
  
  public Transactions getNextTransactions(Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    throw new BankingException(2);
  }
  
  public Transactions getPreviousTransactions(Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException
  {
    throw new BankingException(2);
  }
  
  public Transactions getFDInstrumentTransactions(FixedDepositInstrument paramFixedDepositInstrument, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws BankingException
  {
    throw new BankingException(2);
  }
  
  public int getNumTransactions(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws BankingException
  {
    throw new BankingException(2);
  }
  
  public AccountHistories getHistory(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws BankingException
  {
    throw new BankingException(2);
  }
  
  public AccountSummaries getSummary(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws BankingException
  {
    throw new BankingException(2);
  }
  
  public ExtendedAccountSummaries getExtendedSummary(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws BankingException
  {
    throw new BankingException(2);
  }
  
  public IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws BankingException
  {
    throw new BankingException(2);
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
  
  protected char[] formatGetTransactionsRequest(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, String paramString)
  {
    String[] arrayOfString1 = { "CREDITCARDMSGSRQV1", "CCSTMTTRNRQ", "CCSTMTRQ" };
    String[] arrayOfString2 = { "BANKMSGSRQV1", "STMTTRNRQ", "STMTRQ" };
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatGetTransactionsRequest:");
    }
    String[] arrayOfString3;
    if (paramAccount.getTypeValue() != 3) {
      arrayOfString3 = arrayOfString2;
    } else {
      arrayOfString3 = arrayOfString1;
    }
    appendBeginTag(localStringBuffer, "OFX");
    formatSIGNONMSGSRQV1(localStringBuffer);
    appendBeginTag(localStringBuffer, arrayOfString3[0]);
    appendBeginTag(localStringBuffer, arrayOfString3[1]);
    appendTag(localStringBuffer, "TRNUID", getUniqueSeqNum());
    appendBeginTag(localStringBuffer, arrayOfString3[2]);
    formatFromAccount(localStringBuffer, paramAccount);
    appendBeginTag(localStringBuffer, "INCTRAN");
    appendTag(localStringBuffer, "DTSTART", paramCalendar1, paramString);
    appendTag(localStringBuffer, "DTEND", paramCalendar2, paramString);
    appendTag(localStringBuffer, "INCLUDE", "Y");
    appendEndTag(localStringBuffer, "INCTRAN");
    appendEndTag(localStringBuffer, arrayOfString3[2]);
    appendEndTag(localStringBuffer, arrayOfString3[1]);
    appendEndTag(localStringBuffer, arrayOfString3[0]);
    appendEndTag(localStringBuffer, "OFX");
    return localStringBuffer.toString().toCharArray();
  }
  
  protected char[] formatGetTransferRequest(Currency paramCurrency, Account paramAccount1, Account paramAccount2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatTransferRequest:");
    }
    appendBeginTag(localStringBuffer, "OFX");
    formatSIGNONMSGSRQV1(localStringBuffer);
    appendBeginTag(localStringBuffer, "BANKMSGSRQV1");
    appendBeginTag(localStringBuffer, "INTRATRNRQ");
    appendTag(localStringBuffer, "TRNUID", getUniqueSeqNum());
    appendBeginTag(localStringBuffer, "INTRARQ");
    appendBeginTag(localStringBuffer, "XFERINFO");
    formatFromAccount(localStringBuffer, paramAccount1);
    formatToAccount(localStringBuffer, paramAccount2);
    appendTag(localStringBuffer, "TRNAMT", paramCurrency);
    appendEndTag(localStringBuffer, "XFERINFO");
    appendEndTag(localStringBuffer, "INTRARQ");
    appendEndTag(localStringBuffer, "INTRATRNRQ");
    appendEndTag(localStringBuffer, "BANKMSGSRQV1");
    appendEndTag(localStringBuffer, "OFX");
    return localStringBuffer.toString().toCharArray();
  }
  
  protected void formatINTRARQ(StringBuffer paramStringBuffer, Transfer paramTransfer, Account paramAccount1, Account paramAccount2)
  {
    appendBeginTag(paramStringBuffer, "INTRARQ");
    appendBeginTag(paramStringBuffer, "XFERINFO");
    formatFromAccount(paramStringBuffer, paramAccount1);
    formatToAccount(paramStringBuffer, paramAccount2);
    appendTag(paramStringBuffer, "TRNAMT", paramTransfer.getAmountValue());
    DateTime localDateTime1 = new DateTime(paramTransfer.getDateValue().getLocale());
    DateTime localDateTime2 = paramTransfer.getDateValue();
    if ((localDateTime1.get(1) <= localDateTime2.get(1)) && ((localDateTime1.get(1) != localDateTime2.get(1)) || (localDateTime1.get(6) < localDateTime2.get(6)))) {
      appendTag(paramStringBuffer, "DTDUE", paramTransfer.getDateValue(), "yyyyMMdd");
    }
    appendEndTag(paramStringBuffer, "XFERINFO");
    appendEndTag(paramStringBuffer, "INTRARQ");
  }
  
  protected char[] formatSendTransferRequest(Transfer paramTransfer)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatTransferRequest:");
    }
    appendBeginTag(localStringBuffer, "OFX");
    formatSIGNONMSGSRQV1(localStringBuffer);
    appendBeginTag(localStringBuffer, "BANKMSGSRQV1");
    appendBeginTag(localStringBuffer, "INTRATRNRQ");
    String str = getUniqueSeqNum();
    paramTransfer.set("TRNUID", str);
    appendTag(localStringBuffer, "TRNUID", str);
    formatINTRARQ(localStringBuffer, paramTransfer, this.fromAccount, this.toAccount);
    appendEndTag(localStringBuffer, "INTRATRNRQ");
    appendEndTag(localStringBuffer, "BANKMSGSRQV1");
    appendEndTag(localStringBuffer, "OFX");
    return localStringBuffer.toString().toCharArray();
  }
  
  protected char[] formatDeleteTransferRequest(Transfer paramTransfer)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatTransferRequest:");
    }
    appendBeginTag(localStringBuffer, "OFX");
    formatSIGNONMSGSRQV1(localStringBuffer);
    appendBeginTag(localStringBuffer, "BANKMSGSRQV1");
    appendBeginTag(localStringBuffer, "INTRATRNRQ");
    String str = getUniqueSeqNum();
    paramTransfer.set("TRNUID", str);
    appendTag(localStringBuffer, "TRNUID", str);
    appendBeginTag(localStringBuffer, "INTRACANRQ");
    appendTag(localStringBuffer, "SRVRTID", paramTransfer.getID());
    appendEndTag(localStringBuffer, "INTRACANRQ");
    appendEndTag(localStringBuffer, "INTRATRNRQ");
    appendEndTag(localStringBuffer, "BANKMSGSRQV1");
    appendEndTag(localStringBuffer, "OFX");
    return localStringBuffer.toString().toCharArray();
  }
  
  protected char[] formatModifyTransferRequest(Transfer paramTransfer)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatTransferRequest:");
    }
    appendBeginTag(localStringBuffer, "OFX");
    formatSIGNONMSGSRQV1(localStringBuffer);
    appendBeginTag(localStringBuffer, "BANKMSGSRQV1");
    appendBeginTag(localStringBuffer, "INTRATRNRQ");
    String str = getUniqueSeqNum();
    paramTransfer.set("TRNUID", str);
    appendTag(localStringBuffer, "TRNUID", str);
    appendBeginTag(localStringBuffer, "INTRAMODRQ");
    appendTag(localStringBuffer, "SRVRTID", paramTransfer.getID());
    appendBeginTag(localStringBuffer, "XFERINFO");
    formatFromAccount(localStringBuffer, this.fromAccount);
    formatToAccount(localStringBuffer, this.toAccount);
    appendTag(localStringBuffer, "TRNAMT", paramTransfer.getAmountValue());
    appendTag(localStringBuffer, "DTDUE", paramTransfer.getDateValue(), "yyyyMMdd");
    appendEndTag(localStringBuffer, "XFERINFO");
    appendEndTag(localStringBuffer, "INTRAMODRQ");
    appendEndTag(localStringBuffer, "INTRATRNRQ");
    appendEndTag(localStringBuffer, "BANKMSGSRQV1");
    appendEndTag(localStringBuffer, "OFX");
    return localStringBuffer.toString().toCharArray();
  }
  
  protected char[] formatSendRecTransferRequest(RecTransfer paramRecTransfer)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatSendRecTransferRequest:");
    }
    appendBeginTag(localStringBuffer, "OFX");
    formatSIGNONMSGSRQV1(localStringBuffer);
    appendBeginTag(localStringBuffer, "BANKMSGSRQV1");
    appendBeginTag(localStringBuffer, "RECINTRATRNRQ");
    String str = getUniqueSeqNum();
    paramRecTransfer.set("TRNUID", str);
    appendTag(localStringBuffer, "TRNUID", str);
    appendBeginTag(localStringBuffer, "RECINTRARQ");
    formatRECURRINST(localStringBuffer, paramRecTransfer.getNumberTransfersValue(), getFrequency(paramRecTransfer.getFrequencyValue()));
    formatINTRARQ(localStringBuffer, paramRecTransfer, this.fromAccount, this.toAccount);
    appendEndTag(localStringBuffer, "RECINTRARQ");
    appendEndTag(localStringBuffer, "RECINTRATRNRQ");
    appendEndTag(localStringBuffer, "BANKMSGSRQV1");
    appendEndTag(localStringBuffer, "OFX");
    return localStringBuffer.toString().toCharArray();
  }
  
  protected char[] formatDeleteRecTransferRequest(RecTransfer paramRecTransfer)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatTransferRequest:");
    }
    appendBeginTag(localStringBuffer, "OFX");
    formatSIGNONMSGSRQV1(localStringBuffer);
    appendBeginTag(localStringBuffer, "BANKMSGSRQV1");
    appendBeginTag(localStringBuffer, "RECINTRATRNRQ");
    String str = getUniqueSeqNum();
    paramRecTransfer.set("TRNUID", str);
    appendTag(localStringBuffer, "TRNUID", str);
    appendBeginTag(localStringBuffer, "RECINTRACANRQ");
    appendTag(localStringBuffer, "RECSRVRTID", paramRecTransfer.getID());
    appendTag(localStringBuffer, "CANPENDING", "Y");
    appendEndTag(localStringBuffer, "RECINTRACANRQ");
    appendEndTag(localStringBuffer, "RECINTRATRNRQ");
    appendEndTag(localStringBuffer, "BANKMSGSRQV1");
    appendEndTag(localStringBuffer, "OFX");
    return localStringBuffer.toString().toCharArray();
  }
  
  protected char[] formatModifyRecTransferRequest(RecTransfer paramRecTransfer)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatTransferRequest:");
    }
    appendBeginTag(localStringBuffer, "OFX");
    formatSIGNONMSGSRQV1(localStringBuffer);
    appendBeginTag(localStringBuffer, "BANKMSGSRQV1");
    appendBeginTag(localStringBuffer, "RECINTRATRNRQ");
    String str = getUniqueSeqNum();
    paramRecTransfer.set("TRNUID", str);
    appendTag(localStringBuffer, "TRNUID", str);
    appendBeginTag(localStringBuffer, "RECINTRAMODRQ");
    appendTag(localStringBuffer, "RECSRVRTID", paramRecTransfer.getID());
    formatRECURRINST(localStringBuffer, paramRecTransfer.getNumberTransfersValue(), getFrequency(paramRecTransfer.getFrequencyValue()));
    formatINTRARQ(localStringBuffer, paramRecTransfer, this.fromAccount, this.toAccount);
    appendTag(localStringBuffer, "MODPENDING", "Y");
    appendEndTag(localStringBuffer, "RECINTRAMODRQ");
    appendEndTag(localStringBuffer, "RECINTRATRNRQ");
    appendEndTag(localStringBuffer, "BANKMSGSRQV1");
    appendEndTag(localStringBuffer, "OFX");
    return localStringBuffer.toString().toCharArray();
  }
  
  protected char[] formatSkipRecTransferRequest(RecTransfer paramRecTransfer)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatTransferRequest:");
    }
    appendBeginTag(localStringBuffer, "OFX");
    formatSIGNONMSGSRQV1(localStringBuffer);
    appendEndTag(localStringBuffer, "OFX");
    return localStringBuffer.toString().toCharArray();
  }
  
  protected char[] formatGetTransfersSyncRequest()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatTransferRequest:");
    }
    appendBeginTag(localStringBuffer, "OFX");
    formatSIGNONMSGSRQV1(localStringBuffer);
    int i = 0;
    for (int j = 0; j < this.accounts.size(); j++)
    {
      this.account = ((Account)this.accounts.get(j));
      if ((this.account.isFilterable("TransferFrom")) && ((this.OFX_Version >= 200) || (this.account.getTypeValue() != 3))) {
        i++;
      }
    }
    if (i > 0)
    {
      appendBeginTag(localStringBuffer, "BANKMSGSRQV1");
      for (j = 0; j < this.accounts.size(); j++)
      {
        this.account = ((Account)this.accounts.get(j));
        if ((this.account.isFilterable("TransferFrom")) && ((this.OFX_Version >= 200) || (this.account.getTypeValue() != 3)))
        {
          appendBeginTag(localStringBuffer, "INTRASYNCRQ");
          if ((this.m_TransferToken != null) && (this.m_TransferToken.length() > 0)) {
            appendTag(localStringBuffer, "TOKEN", this.m_TransferToken);
          } else {
            appendTag(localStringBuffer, "TOKEN", "0");
          }
          appendTag(localStringBuffer, "REJECTIFMISSING", "N");
          formatFromAccount(localStringBuffer, this.account);
          appendEndTag(localStringBuffer, "INTRASYNCRQ");
        }
      }
      appendEndTag(localStringBuffer, "BANKMSGSRQV1");
    }
    appendEndTag(localStringBuffer, "OFX");
    return localStringBuffer.toString().toCharArray();
  }
  
  protected char[] formatGetRecTransfersSyncRequest()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatTransferRequest:");
    }
    appendBeginTag(localStringBuffer, "OFX");
    formatSIGNONMSGSRQV1(localStringBuffer);
    int i = 0;
    for (int j = 0; j < this.accounts.size(); j++)
    {
      this.account = ((Account)this.accounts.get(j));
      if ((this.account.isFilterable("TransferFrom")) && ((this.OFX_Version >= 200) || (this.account.getTypeValue() != 3))) {
        i++;
      }
    }
    if (i > 0)
    {
      appendBeginTag(localStringBuffer, "BANKMSGSRQV1");
      for (j = 0; j < this.accounts.size(); j++)
      {
        this.account = ((Account)this.accounts.get(j));
        if ((this.account.isFilterable("TransferFrom")) && ((this.OFX_Version >= 200) || (this.account.getTypeValue() != 3)))
        {
          appendBeginTag(localStringBuffer, "RECINTRASYNCRQ");
          if ((this.m_RecTransferToken != null) && (this.m_RecTransferToken.length() > 0)) {
            appendTag(localStringBuffer, "TOKEN", this.m_RecTransferToken);
          } else {
            appendTag(localStringBuffer, "TOKEN", "0");
          }
          appendTag(localStringBuffer, "REJECTIFMISSING", "N");
          formatFromAccount(localStringBuffer, this.account);
          appendEndTag(localStringBuffer, "RECINTRASYNCRQ");
        }
      }
      appendEndTag(localStringBuffer, "BANKMSGSRQV1");
    }
    appendEndTag(localStringBuffer, "OFX");
    return localStringBuffer.toString().toCharArray();
  }
  
  public void setTransaction()
  {
    if (this.transactions != null)
    {
      String str1 = (String)this.savedValues.get("FITID");
      if (str1 == null) {
        throw new IllegalArgumentException("setTransaction: FITID=" + str1);
      }
      this.transaction = ((Transaction)this.transactions.getFirstByFilter("ID=" + str1));
      if (this.transaction == null)
      {
        this.transaction = this.transactions.create();
        this.transaction.setID(str1);
      }
      Iterator localIterator = this.savedValues.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str2 = (String)localIterator.next();
        setTransaction(str2, (String)this.savedValues.get(str2));
      }
    }
    this.savedValues.clear();
  }
  
  public void setTransaction(String paramString1, String paramString2)
  {
    if (this.transaction != null)
    {
      int i = 0;
      if (paramString1.equals("TRNTYPE"))
      {
        for (i = 0; (i < BankingDefines.transType.length) && (!BankingDefines.transType[i].equals(paramString2)); i++) {}
        if (i < BankingDefines.transMap.length) {
          i = BankingDefines.transMap[i];
        } else {
          throw new IllegalArgumentException("setTransaction: type=" + paramString2);
        }
      }
      else if (paramString1.equals("NAME"))
      {
        this.transaction.setDescription(paramString2);
      }
      else if (paramString1.equals("MEMO"))
      {
        this.transaction.setMemo(paramString2);
      }
      else if (paramString1.equals("DTPOSTED"))
      {
        this.transaction.setDate(getDate(paramString2));
      }
      else if (paramString1.equals("TRNAMT"))
      {
        i = this.transaction.getTypeValue();
        this.transaction.setAmount(paramString2);
      }
      else if (paramString1.equals("CHECKNUM"))
      {
        this.transaction.setReferenceNumber(paramString2);
      }
      else if (paramString1.equals("REFNUM"))
      {
        if ((this.transaction.getReferenceNumber() != null) && (this.transaction.getReferenceNumber().length() > 0)) {
          this.transaction.setReferenceNumber(this.transaction.getReferenceNumber() + " " + paramString2);
        } else {
          this.transaction.setReferenceNumber(paramString2);
        }
      }
      else if (paramString1.equals("CORRECTACTION"))
      {
        if (paramString2.indexOf("DELETE") != -1)
        {
          this.transactions.remove(this.transaction);
          this.transaction = null;
        }
        else if (paramString2.indexOf("REPLACE") != -1)
        {
          String str = (String)this.savedValues.get("CORRECTFITID");
          if ((((str != null ? 1 : 0) & (str.length() > 0 ? 1 : 0)) != 0) && (this.transactions.getFirstByFilter("ID=" + str) == null)) {
            this.transaction.setID(str);
          }
        }
      }
      else if (this.extendedInfo)
      {
        saveTagInExtendABean(paramString1, paramString2, this.transaction);
      }
      if (i != 0)
      {
        double d = this.transaction.getAmountValue().doubleValue();
        if ((i == 7) && (d > 0.0D)) {
          i = 6;
        }
        if ((i == 9) && (d > 0.0D)) {
          i = 8;
        }
        if ((i == 11) && (d < 0.0D)) {
          i = 24;
        }
        this.transaction.setType(i);
      }
    }
    else
    {
      this.savedValues.put(paramString1, paramString2);
    }
  }
  
  public void parseBANKTRANLIST()
  {
    if (this.transactions == null)
    {
      skipTokens("BANKTRANLIST");
    }
    else
    {
      String str;
      while (((str = getToken()) != null) && (!str.equals("BANKTRANLIST"))) {
        if (str.equals("STMTTRN")) {
          parseSTMTTRN();
        } else if ((str.equals("DTSTART")) || (str.equals("DTEND"))) {
          getField();
        } else {
          throw new IllegalArgumentException("parseBANKTRANLIST failed on tag=" + str);
        }
      }
    }
  }
  
  public void parseSTMTTRN()
  {
    this.transaction = null;
    String str;
    while (((str = getToken()) != null) && (!str.equals("STMTTRN"))) {
      if (str.equals("PAYEE")) {
        parseNotSupported(str);
      } else if (str.equals("BANKACCTTO")) {
        parseNotSupported(str);
      } else if (str.equals("CCACCTTO")) {
        parseNotSupported(str);
      } else if (str.equals("CURRENCY")) {
        parseNotSupported(str);
      } else if (str.equals("ORIGCURRENCY")) {
        parseNotSupported(str);
      } else if (str.equals("INV401KSOURCE")) {
        parseNotSupported(str);
      } else if ((str.equals("TRNTYPE")) || (str.equals("DTPOSTED")) || (str.equals("DTUSER")) || (str.equals("DTAVAIL")) || (str.equals("TRNAMT")) || (str.equals("FITID")) || (str.equals("CORRECTFITID")) || (str.equals("CORRECTACTION")) || (str.equals("SRVRTID")) || (str.equals("CHECKNUM")) || (str.equals("REFNUM")) || (str.equals("SIC")) || (str.equals("PAYEEID")) || (str.equals("NAME")) || (str.equals("MEMO"))) {
        setTransaction(str, getField());
      } else if (this.extendedInfo) {
        this.savedValues.put(str, getField());
      } else {
        throw new IllegalArgumentException("parseSTMTTRN failed on tag=" + str);
      }
    }
    setTransaction();
  }
  
  public void parseINTRATRNRS()
  {
    if (this.transfers != null) {
      this.transfer = null;
    }
    String str;
    while (((str = getToken()) != null) && (!str.equals("INTRATRNRS"))) {
      if (str.equals("STATUS")) {
        parseSTATUS(1);
      } else if (str.equals("INTRARS")) {
        parseINTRARS();
      } else if (str.equals("INTRAMODRS")) {
        parseINTRAMODRS();
      } else if (str.equals("INTRACANRS")) {
        parseINTRACANRS();
      } else if ((str.equals("TRNUID")) || (str.equals("CLTCOOKIE"))) {
        setTransfer(str, getField());
      } else if (this.extendedInfo) {
        this.savedValues.put(str, getField());
      } else {
        throw new IllegalArgumentException("parseINTRATRNRS failed on tag=" + str);
      }
    }
    if (this.transfers != null) {
      setTransfer();
    }
  }
  
  public void parseRECINTRATRNRS()
  {
    if (this.rectransfers != null) {
      this.rectransfer = null;
    }
    String str;
    while (((str = getToken()) != null) && (!str.equals("RECINTRATRNRS"))) {
      if (str.equals("STATUS")) {
        parseSTATUS(2);
      } else if (str.equals("RECINTRARS")) {
        parseRECINTRARS();
      } else if (str.equals("RECINTRAMODRS")) {
        parseRECINTRAMODRS();
      } else if (str.equals("RECINTRACANRS")) {
        parseRECINTRACANRS();
      } else if ((str.equals("TRNUID")) || (str.equals("CLTCOOKIE"))) {
        setRecTransfer(str, getField());
      } else if (this.extendedInfo) {
        this.savedValues.put(str, getField());
      } else {
        throw new IllegalArgumentException("parseRECINTRATRNRS failed on tag=" + str);
      }
    }
    if (this.rectransfers != null) {
      setRecTransfer();
    }
  }
  
  public void parseINTRARS()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("INTRARS"))) {
      if (str.equals("XFERINFO")) {
        parseXFERINFO();
      } else if (str.equals("XFERPRCSTS")) {
        parseXFERPRCSTS();
      } else if ((str.equals("CURDEF")) || (str.equals("SRVRTID")) || (str.equals("DTXFERPRJ")) || (str.equals("DTPOSTED")) || (str.equals("RECSRVRTID"))) {
        setTransfer(str, getField());
      } else if (this.extendedInfo) {
        this.savedValues.put(str, getField());
      } else {
        throw new IllegalArgumentException("parseINTRARS failed on tag=" + str);
      }
    }
  }
  
  public void parseINTRAMODRS()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("INTRAMODRS"))) {
      if (str.equals("XFERINFO")) {
        parseXFERINFO();
      } else if (str.equals("XFERPRCSTS")) {
        parseXFERPRCSTS();
      } else if (str.equals("SRVRTID")) {
        setTransfer(str, getField());
      } else if (this.extendedInfo) {
        this.savedValues.put(str, getField());
      } else {
        throw new IllegalArgumentException("parseINTRAMODRS failed on tag=" + str);
      }
    }
  }
  
  public void parseINTRACANRS()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("INTRACANRS"))) {
      if (str.equals("SRVRTID"))
      {
        str = getField();
        if ((this.transfer == null) && (this.transfers != null)) {
          this.transfer = ((Transfer)this.transfers.getFirstByFilter("ID=" + str));
        }
        if (this.transfer != null) {
          this.transfer.setStatus(3);
        }
        this.savedValues.put("SRVRTID", str);
      }
      else
      {
        throw new IllegalArgumentException("parseINTRACANRS failed on tag=" + str);
      }
    }
  }
  
  public void parseRECINTRARS()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("RECINTRARS"))) {
      if (str.equals("INTRARS")) {
        parseINTRARS();
      } else if (str.equals("RECURRINST")) {
        parseRECURRINST();
      } else if (str.equals("RECSRVRTID")) {
        setRecTransfer(str, getField());
      } else if (this.extendedInfo) {
        this.savedValues.put(str, getField());
      } else {
        throw new IllegalArgumentException("parseRECINTRARS failed on tag=" + str);
      }
    }
  }
  
  public void parseRECINTRAMODRS()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("RECINTRAMODRS"))) {
      if (str.equals("INTRARS")) {
        parseINTRARS();
      } else if (str.equals("RECURRINST")) {
        parseRECURRINST();
      } else if ((str.equals("RECSRVRTID")) || (str.equals("MODPENDING"))) {
        setRecTransfer(str, getField());
      } else if (this.extendedInfo) {
        this.savedValues.put(str, getField());
      } else {
        throw new IllegalArgumentException("parseRECINTRAMODRS failed on tag=" + str);
      }
    }
  }
  
  public void parseRECINTRACANRS()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("RECINTRACANRS"))) {
      if (str.equals("RECSRVRTID"))
      {
        str = getField();
        if ((this.rectransfer == null) && (this.rectransfers != null)) {
          this.rectransfer = ((RecTransfer)this.rectransfers.getFirstByFilter("ID=" + str));
        }
        if (this.rectransfer != null) {
          this.rectransfer.setStatus(3);
        }
        this.savedValues.put("RECSRVRTID", str);
      }
      else if (str.equals("CANPENDING"))
      {
        skipField();
      }
      else
      {
        throw new IllegalArgumentException("parseRECINTRACANRS failed on tag=" + str);
      }
    }
  }
  
  public void parseINTRASYNCRS()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("INTRASYNCRS"))) {
      if (str.equals("BANKACCTFROM"))
      {
        this.account = null;
        parseBANKACCTFROM();
      }
      else if (str.equals("CCACCTFROM"))
      {
        this.account = null;
        parseCCACCTFROM();
      }
      else if (str.equals("INTRATRNRS"))
      {
        parseINTRATRNRS();
      }
      else if ((str.equals("TOKEN")) || (str.equals("LOSTSYNC")))
      {
        skipField();
      }
      else
      {
        throw new IllegalArgumentException("parseINTRASYNCRS failed on tag=" + str);
      }
    }
  }
  
  public void parseRECINTRASYNCRS()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("RECINTRASYNCRS"))) {
      if (str.equals("BANKACCTFROM"))
      {
        this.account = null;
        parseBANKACCTFROM();
      }
      else if (str.equals("CCACCTFROM"))
      {
        this.account = null;
        parseCCACCTFROM();
      }
      else if (str.equals("RECINTRATRNRS"))
      {
        parseRECINTRATRNRS();
      }
      else if ((str.equals("TOKEN")) || (str.equals("LOSTSYNC")))
      {
        skipField();
      }
      else
      {
        throw new IllegalArgumentException("parseRECINTRASYNCRS failed on tag=" + str);
      }
    }
  }
  
  protected void setTransferCommon(Transfer paramTransfer, String paramString1, String paramString2)
  {
    if (paramString1.equals("RECSRVRTID"))
    {
      paramTransfer.setRecTransferID(paramString2);
    }
    else if (paramString1.equals("TRNAMT"))
    {
      paramTransfer.setAmount(paramString2);
    }
    else if (paramString1.equals("DTDUE"))
    {
      paramTransfer.setDate(getDate(paramString2));
    }
    else if (paramString1.equals("MEMO"))
    {
      paramTransfer.setMemo(paramString2);
    }
    else if (paramString1.equals("REFNUM"))
    {
      paramTransfer.setReferenceNumber(paramString2);
    }
    else if (paramString1.equals("TRNUID"))
    {
      paramTransfer.set("TRNUID", paramString2);
    }
    else if (paramString1.equals("STATUS"))
    {
      paramTransfer.setError(mapError(Integer.parseInt(paramString2)));
      this.objStatus.put(paramTransfer.getID(), paramString2);
      if ((paramString2.equals("0")) && (paramTransfer.getStatus() == 1)) {
        paramTransfer.setStatus(2);
      }
    }
    else
    {
      String str;
      Account localAccount;
      if (paramString1.equals("FROMACCTTYPE"))
      {
        str = (String)this.savedValues.get("FROMACCTID");
        if ((str != null) && (this.accounts != null))
        {
          localAccount = this.accounts.getByAccountNumberAndType(str, getAccountType(paramString2));
          if (localAccount != null) {
            paramTransfer.setFromAccount(localAccount);
          } else {
            paramTransfer.setFromAccountID(str);
          }
          localAccount = null;
        }
      }
      else if (paramString1.equals("TOACCTTYPE"))
      {
        str = (String)this.savedValues.get("TOACCTID");
        if ((str != null) && (this.accounts != null))
        {
          localAccount = this.accounts.getByAccountNumberAndType(str, getAccountType(paramString2));
          if (localAccount != null)
          {
            paramTransfer.setToAccount(localAccount);
          }
          else
          {
            localAccount = this.accounts.create(str, getAccountType(paramString2));
            paramTransfer.setToAccount(localAccount);
          }
          localAccount = null;
        }
      }
    }
  }
  
  protected void setTransfer()
  {
    String str1 = (String)this.savedValues.get("TRNUID");
    String str2 = (String)this.savedValues.get("SRVRTID");
    if ((str1 != null) && (this.transfers != null) && (!str1.equals("0"))) {
      this.transfer = ((Transfer)this.transfers.getFirstByFilter("TRNUID=" + str1));
    }
    if ((this.transfer == null) && (str2 != null)) {
      this.transfer = ((Transfer)this.transfers.getFirstByFilter("ID=" + str2));
    }
    Object localObject;
    if (this.transfer == null)
    {
      localObject = (String)this.savedValues.get("TRNAMT");
      if ((localObject != null) && (((String)localObject).length() != 0)) {
        this.transfer = ((Transfer)this.transfers.create());
      }
    }
    if (this.transfer != null)
    {
      localObject = this.savedValues.keySet().iterator();
      while (((Iterator)localObject).hasNext())
      {
        String str3 = (String)((Iterator)localObject).next();
        setTransfer(str3, (String)this.savedValues.get(str3));
      }
    }
    this.savedValues.clear();
  }
  
  protected void setTransfer(String paramString1, String paramString2)
  {
    if (this.transfer != null)
    {
      if ((paramString1.equals("RECSRVRTID")) || (paramString1.equals("TRNAMT")) || (paramString1.equals("DTDUE")) || (paramString1.equals("MEMO")) || (paramString1.equals("REFNUM")) || (paramString1.equals("TRNUID")) || (paramString1.equals("STATUS")) || (paramString1.startsWith("FROM")) || (paramString1.startsWith("TO")))
      {
        setTransferCommon(this.transfer, paramString1, paramString2);
      }
      else if (paramString1.equals("SRVRTID"))
      {
        this.transfer.setID(paramString2);
      }
      else if (paramString1.equals("TOKEN"))
      {
        this.m_TransferToken = paramString2;
      }
      else if (paramString1.equals("DTXFERPRC"))
      {
        this.transfer.set("XferProcessDate", this.xferProcessDateFormatter.format(getDate(paramString2).getTime()));
      }
      else if (paramString1.equals("DTXFERPRJ"))
      {
        this.transfer.put("DTXFERPRJ", getDate(paramString2));
      }
      else if (paramString1.equals("STATUS"))
      {
        if (Integer.parseInt(paramString2) == 2014) {
          this.transfer.setStatus(7);
        }
      }
      else if (paramString1.equals("XFERPRCCODE"))
      {
        this.transfer.set("XferProcessCode", paramString2);
        this.transfer.setStatus(getProcessCode(paramString2));
      }
      else if (this.extendedInfo)
      {
        saveTagInExtendABean(paramString1, paramString2, this.transfer);
      }
    }
    else {
      this.savedValues.put(paramString1, paramString2);
    }
  }
  
  public void setRecTransfer()
  {
    if (this.savedValues.get("RECSRVRTID") != null)
    {
      String str1 = (String)this.savedValues.get("TRNUID");
      if ((str1 != null) && (this.rectransfers != null) && (!str1.equals("0"))) {
        this.rectransfer = ((RecTransfer)this.rectransfers.getFirstByFilter("TRNUID=" + str1));
      }
      if (this.rectransfer == null) {
        this.rectransfer = ((RecTransfer)this.rectransfers.getFirstByFilter("ID=" + (String)this.savedValues.get("RECSRVRTID")));
      }
      if (this.rectransfer == null)
      {
        localObject = (String)this.savedValues.get("TRNAMT");
        if ((localObject != null) && (((String)localObject).length() != 0)) {
          this.rectransfer = ((RecTransfer)this.rectransfers.create());
        }
      }
      if (this.savedValues.get("NINSTS") == null) {
        this.savedValues.put("NINSTS", "999");
      }
      if (this.rectransfer != null)
      {
        localObject = this.savedValues.keySet().iterator();
        String str2;
        while (((Iterator)localObject).hasNext())
        {
          str2 = (String)((Iterator)localObject).next();
          setRecTransfer(str2, (String)this.savedValues.get(str2));
        }
        if (this.savedValues.get("NINSTS") != null)
        {
          str2 = (String)this.savedValues.get("NINSTS");
          if (Integer.parseInt(str2) < 0) {
            this.rectransfer.setStatus(3);
          }
        }
      }
      Object localObject = (String)this.savedValues.get("XFERPRCCODE");
      if ((localObject != null) && (((String)localObject).length() != 0)) {
        this.rectransfer.setStatus(getProcessCode((String)localObject));
      }
    }
    this.savedValues.clear();
  }
  
  public void setRecTransfer(String paramString1, String paramString2)
  {
    if (this.rectransfer != null)
    {
      if (paramString1.equals("RECSRVRTID"))
      {
        this.rectransfer.setID(paramString2);
        this.rectransfer.setRecTransferID(paramString2);
      }
      else if ((paramString1.equals("TRNAMT")) || (paramString1.equals("DTDUE")) || (paramString1.equals("MEMO")) || (paramString1.equals("REFNUM")) || (paramString1.equals("TRNUID")) || (paramString1.equals("STATUS")) || (paramString1.startsWith("FROM")) || (paramString1.startsWith("TO")))
      {
        setTransferCommon(this.rectransfer, paramString1, paramString2);
      }
      else if (paramString1.equals("FREQ"))
      {
        this.rectransfer.setFrequency(getFrequency(paramString2));
      }
      else if (paramString1.equals("NINSTS"))
      {
        this.rectransfer.setNumberTransfers(Integer.parseInt(paramString2));
      }
      else if (paramString1.equals("TOKEN"))
      {
        this.m_RecTransferToken = paramString2;
      }
      else if (this.extendedInfo)
      {
        saveTagInExtendABean(paramString1, paramString2, this.rectransfer);
      }
    }
    else {
      this.savedValues.put(paramString1, paramString2);
    }
  }
  
  public void parseSTATUS(int paramInt)
  {
    super.parseSTATUS();
    switch (paramInt)
    {
    case 0: 
      this.savedValues.put("STATUS", String.valueOf(this.status));
      break;
    case 1: 
      setTransfer("STATUS", String.valueOf(this.status));
      break;
    case 2: 
      setRecTransfer("STATUS", String.valueOf(this.status));
    }
  }
  
  protected int getProcessCode(String paramString)
  {
    int i = 0;
    if (paramString.equals("WILLPROCESSON")) {
      i = 2;
    } else if (paramString.equals("POSTEDON")) {
      i = 5;
    } else if (paramString.equals("NOFUNDSON")) {
      i = 6;
    } else if (paramString.equals("FAILEDON")) {
      i = 6;
    } else if (paramString.equals("CANCELEDON")) {
      i = 3;
    }
    return i;
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
    default: 
      i = super.mapError(paramInt);
    }
    return i;
  }
  
  public class BankingXMLHandler
    extends Base.ServiceXMLHandler
  {
    public BankingXMLHandler()
    {
      super();
    }
    
    public void attribute(String paramString1, String paramString2, boolean paramBoolean)
    {
      if (paramString1.equals("MaxTransactionDays")) {
        Banking.this.maxTransactionDays = Integer.valueOf(paramString2).intValue();
      } else {
        super.attribute(paramString1, paramString2, paramBoolean);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.ofx.Banking
 * JD-Core Version:    0.7.0.1
 */