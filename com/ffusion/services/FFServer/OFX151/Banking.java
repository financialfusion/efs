package com.ffusion.services.FFServer.OFX151;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.banking.RecTransfer;
import com.ffusion.beans.banking.RecTransfers;
import com.ffusion.beans.banking.Transaction;
import com.ffusion.beans.banking.Transactions;
import com.ffusion.beans.banking.Transfer;
import com.ffusion.beans.banking.Transfers;
import com.ffusion.msgbroker.generated.MessageBroker.api.OFX151.Callback.IOFX151Callback;
import com.ffusion.msgbroker.generated.MessageBroker.api.OFX151.Callback.OFX151CallbackBean;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumCorrectiveActEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumCurrencyEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumTransactionEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumXferStatusEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBCCAcctFromV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBCCAcctToV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBankAcctFromV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBankMsgsRqUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBankMsgsRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBankMsgsRqV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBankTranListV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeCCAcctFromAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeCCStmtRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeCCStmtRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeCCStmtTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeCCStmtTrnRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeCCStmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeCCStmtTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeCreditCardMsgsRqUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeCreditCardMsgsRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeCreditCardMsgsRqV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIncTranAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraCanRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraCanRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraModRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraModRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRqV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeOFXRequest;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeOFXRqMsgSetsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraCanRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraCanRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraModRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraModRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraRsV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraSyncRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraSyncRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRqV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecurrInstAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSignOnMsgsRqUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSignUpMsgsRqUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStmtRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStmtRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStmtTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStmtTrnRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStmtTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStmtTrnV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStmtTrnV1CorrectCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStmtTrnV1NamePayeeUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSyncRqV1Cm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTokenRqV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTrnRqCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTrnRsV1Cm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeXferInfoV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeXferPrcStsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.ValueSetCurrencyEnum;
import com.ffusion.services.Banking2;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.logging.Level;

public class Banking
  extends Base
  implements BankingDefines, Banking2
{
  private static final String bk = "MaxTransactionDays";
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
  
  public int initialize(String paramString)
  {
    return initialize(paramString, new a());
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
  
  private void jdMethod_null()
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
    this.account = paramAccount;
    this.transactions = paramAccount.getTransactions();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".getTransactions:");
    }
    formatGetTransactionsRequest(paramAccount, paramCalendar1, paramCalendar2, paramString);
    processOFXRequest();
    jdMethod_null();
    if (this.objStatus.get("ServiceDown") != null)
    {
      logError(4);
      return this.lastError;
    }
    if (this.status != 0)
    {
      logError(mapError(this.status));
      return this.lastError;
    }
    return 0;
  }
  
  public int getTransfers(Accounts paramAccounts, Transfers paramTransfers)
  {
    this.accounts = ((Accounts)paramAccounts.clone());
    this.accounts.setFilter("All");
    this.transfers = paramTransfers;
    this.rectransfers = null;
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".getTransfers:");
    }
    formatGetTransfersSyncRequest();
    processOFXRequest();
    jdMethod_null();
    this.status = 0;
    if (this.objStatus.get("ServiceDown") != null)
    {
      logError(4);
      return this.lastError;
    }
    if (this.status != 0)
    {
      logError(mapError(this.status));
      return this.lastError;
    }
    return 0;
  }
  
  public int getRecTransfers(Accounts paramAccounts, RecTransfers paramRecTransfers)
  {
    this.accounts = ((Accounts)paramAccounts.clone());
    this.accounts.setFilter("All");
    this.rectransfers = paramRecTransfers;
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".getRecTransfers:");
    }
    formatGetRecTransfersSyncRequest();
    processOFXRequest();
    jdMethod_null();
    this.status = 0;
    if (this.objStatus.get("ServiceDown") != null)
    {
      logError(4);
      return this.lastError;
    }
    if (this.status != 0)
    {
      logError(mapError(this.status));
      return this.lastError;
    }
    return 0;
  }
  
  public int sendTransfer(Account paramAccount1, Account paramAccount2, Transfer paramTransfer, HashMap paramHashMap)
  {
    return sendTransfer(paramAccount1, paramAccount2, paramTransfer);
  }
  
  public int sendTransfer(Account paramAccount1, Account paramAccount2, Transfer paramTransfer)
  {
    this.fromAccount = paramAccount1;
    this.toAccount = paramAccount2;
    this.transfer = paramTransfer;
    this.transfers = new Transfers(paramTransfer.getLocale());
    this.transfers.add(paramTransfer);
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".sendTransfer:");
    }
    formatSendTransferRequest(paramTransfer);
    this._ud._privateTagContainer = new Hashtable();
    processOFXRequest();
    this._ud._privateTagContainer = null;
    jdMethod_null();
    if (this.objStatus.get("ServiceDown") != null)
    {
      logError(4);
      return this.lastError;
    }
    if (this.status != 0)
    {
      logError(mapError(this.status));
      return this.lastError;
    }
    return 0;
  }
  
  public int sendRecTransfer(Account paramAccount1, Account paramAccount2, RecTransfer paramRecTransfer)
  {
    this.fromAccount = paramAccount1;
    this.toAccount = paramAccount2;
    this.rectransfer = paramRecTransfer;
    this.rectransfers = new RecTransfers(paramRecTransfer.getLocale());
    this.rectransfers.add(paramRecTransfer);
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".sendRecTransfer:");
    }
    formatSendRecTransferRequest(paramRecTransfer);
    this._ud._privateTagContainer = new Hashtable();
    processOFXRequest();
    this._ud._privateTagContainer = null;
    jdMethod_null();
    if (this.objStatus.get("ServiceDown") != null)
    {
      logError(4);
      return this.lastError;
    }
    if (this.status != 0)
    {
      logError(mapError(this.status));
      return this.lastError;
    }
    return 0;
  }
  
  public int cancelTransfer(Transfer paramTransfer)
  {
    this.transfer = paramTransfer;
    this.transfers = new Transfers(paramTransfer.getLocale());
    this.transfers.add(paramTransfer);
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".cancelTransfer:");
    }
    formatDeleteTransferRequest(paramTransfer);
    processOFXRequest();
    jdMethod_null();
    if (this.objStatus.get("ServiceDown") != null)
    {
      logError(4);
      return this.lastError;
    }
    if (this.status != 0)
    {
      logError(mapError(this.status));
      return this.lastError;
    }
    return 0;
  }
  
  public int cancelRecTransfer(RecTransfer paramRecTransfer)
  {
    this.rectransfer = paramRecTransfer;
    this.rectransfers = new RecTransfers(paramRecTransfer.getLocale());
    this.rectransfers.add(paramRecTransfer);
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".cancelRecTransfer:");
    }
    formatDeleteRecTransferRequest(paramRecTransfer);
    processOFXRequest();
    jdMethod_null();
    if (this.objStatus.get("ServiceDown") != null)
    {
      logError(4);
      return this.lastError;
    }
    if (this.status != 0)
    {
      logError(mapError(this.status));
      return this.lastError;
    }
    return 0;
  }
  
  public int modifyTransfer(Account paramAccount1, Account paramAccount2, Transfer paramTransfer, HashMap paramHashMap)
  {
    return modifyTransfer(paramAccount1, paramAccount2, paramTransfer);
  }
  
  public int modifyTransfer(Account paramAccount1, Account paramAccount2, Transfer paramTransfer)
  {
    this.fromAccount = paramAccount1;
    this.toAccount = paramAccount2;
    this.transfer = paramTransfer;
    this.transfers = new Transfers(paramTransfer.getLocale());
    this.transfers.add(paramTransfer);
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".modifyTransfer:");
    }
    formatModifyTransferRequest(paramTransfer);
    this._ud._privateTagContainer = new Hashtable();
    processOFXRequest();
    this._ud._privateTagContainer = null;
    jdMethod_null();
    if (this.objStatus.get("ServiceDown") != null)
    {
      logError(4);
      return this.lastError;
    }
    if (this.status != 0)
    {
      logError(mapError(this.status));
      return this.lastError;
    }
    return 0;
  }
  
  public int modifyRecTransfer(Account paramAccount1, Account paramAccount2, RecTransfer paramRecTransfer)
  {
    this.fromAccount = paramAccount1;
    this.toAccount = paramAccount2;
    this.rectransfer = paramRecTransfer;
    this.rectransfers = new RecTransfers(paramRecTransfer.getLocale());
    this.rectransfers.add(paramRecTransfer);
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".modifyRecTransfer:");
    }
    formatModifyRecTransferRequest(paramRecTransfer);
    this._ud._privateTagContainer = new Hashtable();
    processOFXRequest();
    this._ud._privateTagContainer = null;
    jdMethod_null();
    if (this.objStatus.get("ServiceDown") != null)
    {
      logError(4);
      return this.lastError;
    }
    if (this.status != 0)
    {
      logError(mapError(this.status));
      return this.lastError;
    }
    return 0;
  }
  
  public int skipRecTransfer(Account paramAccount1, Account paramAccount2, RecTransfer paramRecTransfer)
  {
    this.fromAccount = paramAccount1;
    this.toAccount = paramAccount2;
    this.rectransfer = paramRecTransfer;
    this.rectransfers = new RecTransfers(paramRecTransfer.getLocale());
    this.rectransfers.add(paramRecTransfer);
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".skipRecTransfer:");
    }
    formatSkipRecTransferRequest(paramRecTransfer);
    processOFXRequest();
    jdMethod_null();
    if (this.objStatus.get("ServiceDown") != null)
    {
      logError(4);
      return this.lastError;
    }
    if (this.status != 0)
    {
      logError(mapError(this.status));
      return this.lastError;
    }
    return 0;
  }
  
  protected void formatGetTransactionsRequest(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, String paramString)
  {
    this.rqmes = new TypeOFXRequest();
    this.rqmes.OFXRequest = new TypeOFXRqMsgSetsAggregate();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatGetTransactionsRequest:");
    }
    formatSIGNONMSGSRQV1(this.rqmes.OFXRequest);
    TypeIncTranAggregate localTypeIncTranAggregate = new TypeIncTranAggregate();
    DateFormat localDateFormat = DateFormatUtil.getFormatter(paramString);
    localTypeIncTranAggregate.Include = true;
    localTypeIncTranAggregate.DtStartExists = true;
    localTypeIncTranAggregate.DtStart = localDateFormat.format(paramCalendar1.getTime());
    localTypeIncTranAggregate.DtEndExists = true;
    localTypeIncTranAggregate.DtEnd = localDateFormat.format(paramCalendar2.getTime());
    Object localObject1;
    Object localObject2;
    if (paramAccount.getTypeValue() != 3)
    {
      this.rqmes.OFXRequest.BankMsgsRqUnExists = true;
      this.rqmes.OFXRequest.BankMsgsRqUn = new TypeBankMsgsRqUn();
      this.rqmes.OFXRequest.BankMsgsRqUn.__memberName = "BankMsgsRqV1";
      this.rqmes.OFXRequest.BankMsgsRqUn.BankMsgsRqV1 = new TypeBankMsgsRqV1Aggregate();
      localObject1 = new TypeBankMsgsRqV1Un[1];
      this.rqmes.OFXRequest.BankMsgsRqUn.BankMsgsRqV1.BankMsgsRqV1Un = ((TypeBankMsgsRqV1Un[])localObject1);
      localObject1[0] = new TypeBankMsgsRqV1Un();
      localObject1[0].__memberName = "StmtTrnRq";
      localObject2 = new TypeStmtTrnRqV1Aggregate();
      localObject1[0].StmtTrnRq = ((TypeStmtTrnRqV1Aggregate)localObject2);
      ((TypeStmtTrnRqV1Aggregate)localObject2).TrnRqCm = new TypeTrnRqCm();
      ((TypeStmtTrnRqV1Aggregate)localObject2).TrnRqCm.TrnUID = getUniqueSeqNum();
      ((TypeStmtTrnRqV1Aggregate)localObject2).StmtRq = new TypeStmtRqV1Aggregate();
      ((TypeStmtTrnRqV1Aggregate)localObject2).StmtRq.BankAcctFrom = new TypeBankAcctFromV1Aggregate();
      formatBANKACCTFROM(((TypeStmtTrnRqV1Aggregate)localObject2).StmtRq.BankAcctFrom, paramAccount);
      ((TypeStmtTrnRqV1Aggregate)localObject2).StmtRq.IncTranExists = true;
      ((TypeStmtTrnRqV1Aggregate)localObject2).StmtRq.IncTran = localTypeIncTranAggregate;
    }
    else
    {
      this.rqmes.OFXRequest.CreditCardMsgsRqUnExists = true;
      this.rqmes.OFXRequest.CreditCardMsgsRqUn = new TypeCreditCardMsgsRqUn();
      this.rqmes.OFXRequest.CreditCardMsgsRqUn.__memberName = "CreditCardMsgsRqV1";
      this.rqmes.OFXRequest.CreditCardMsgsRqUn.CreditCardMsgsRqV1 = new TypeCreditCardMsgsRqV1Aggregate();
      localObject1 = new TypeCreditCardMsgsRqV1Un[1];
      this.rqmes.OFXRequest.CreditCardMsgsRqUn.CreditCardMsgsRqV1.CreditCardMsgsRqV1Un = ((TypeCreditCardMsgsRqV1Un[])localObject1);
      localObject1[0] = new TypeCreditCardMsgsRqV1Un();
      localObject1[0].__memberName = "CCStmtTrnRq";
      localObject2 = new TypeCCStmtTrnRqV1Aggregate();
      localObject1[0].CCStmtTrnRq = ((TypeCCStmtTrnRqV1Aggregate)localObject2);
      ((TypeCCStmtTrnRqV1Aggregate)localObject2).TrnRqCm = new TypeTrnRqCm();
      ((TypeCCStmtTrnRqV1Aggregate)localObject2).TrnRqCm.TrnUID = getUniqueSeqNum();
      ((TypeCCStmtTrnRqV1Aggregate)localObject2).CCStmtRq = new TypeCCStmtRqAggregate();
      ((TypeCCStmtTrnRqV1Aggregate)localObject2).CCStmtRq.CCAcctFrom = new TypeCCAcctFromAggregate();
      formatCCACCTFROM(((TypeCCStmtTrnRqV1Aggregate)localObject2).CCStmtRq.CCAcctFrom, paramAccount);
      ((TypeCCStmtTrnRqV1Aggregate)localObject2).CCStmtRq.IncTranExists = true;
      ((TypeCCStmtTrnRqV1Aggregate)localObject2).CCStmtRq.IncTran = localTypeIncTranAggregate;
    }
  }
  
  private TypeTrnRqCm a(Transfer paramTransfer)
  {
    TypeTrnRqCm localTypeTrnRqCm = new TypeTrnRqCm();
    String str = getUniqueSeqNum();
    paramTransfer.set("TRNUID", str);
    localTypeTrnRqCm.TrnUID = str;
    return localTypeTrnRqCm;
  }
  
  protected void formatSendTransferRequest(Transfer paramTransfer)
  {
    this.rqmes = new TypeOFXRequest();
    this.rqmes.OFXRequest = new TypeOFXRqMsgSetsAggregate();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatSendTransferRequest:");
    }
    formatSIGNONMSGSRQV1(this.rqmes.OFXRequest);
    TypeIntraTrnRqV1Aggregate localTypeIntraTrnRqV1Aggregate = formatINTRATRNRQ(this.rqmes.OFXRequest, "IntraRq");
    localTypeIntraTrnRqV1Aggregate.TrnRqCm = a(paramTransfer);
    Double localDouble = new Double(paramTransfer.getAmountValue().doubleValue());
    localTypeIntraTrnRqV1Aggregate.IntraTrnRqV1Un.IntraRq = new TypeIntraRqV1Aggregate();
    DateTime localDateTime1 = null;
    DateTime localDateTime2 = new DateTime(paramTransfer.getDateValue().getLocale());
    DateTime localDateTime3 = paramTransfer.getDateValue();
    if ((localDateTime2.get(1) <= localDateTime3.get(1)) && ((localDateTime2.get(1) != localDateTime3.get(1)) || (localDateTime2.get(6) < localDateTime3.get(6)))) {
      localDateTime1 = paramTransfer.getDateValue();
    }
    String str = paramTransfer.getAmtCurrency();
    if (str == null) {
      str = paramTransfer.getFromAccount().getCurrencyCode();
    }
    if (str != null)
    {
      EnumCurrencyEnum localEnumCurrencyEnum = null;
      try
      {
        int i = ValueSetCurrencyEnum.getIndex(str);
        localEnumCurrencyEnum = EnumCurrencyEnum.from_int(i);
      }
      catch (Throwable localThrowable) {}
      if (localEnumCurrencyEnum != null)
      {
        localTypeIntraTrnRqV1Aggregate.IntraTrnRqV1Un.IntraRq.CurDef = localEnumCurrencyEnum;
        localTypeIntraTrnRqV1Aggregate.IntraTrnRqV1Un.IntraRq.CurDefExists = true;
      }
    }
    localTypeIntraTrnRqV1Aggregate.IntraTrnRqV1Un.IntraRq.XferInfo = formatXFERINFO(localDouble.doubleValue(), this.fromAccount, this.toAccount, localDateTime1);
  }
  
  protected void formatDeleteTransferRequest(Transfer paramTransfer)
  {
    this.rqmes = new TypeOFXRequest();
    this.rqmes.OFXRequest = new TypeOFXRqMsgSetsAggregate();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatDeleteTransferRequest:");
    }
    formatSIGNONMSGSRQV1(this.rqmes.OFXRequest);
    TypeIntraTrnRqV1Aggregate localTypeIntraTrnRqV1Aggregate = formatINTRATRNRQ(this.rqmes.OFXRequest, "IntraCanRq");
    localTypeIntraTrnRqV1Aggregate.TrnRqCm = a(paramTransfer);
    localTypeIntraTrnRqV1Aggregate.IntraTrnRqV1Un.IntraCanRq = new TypeIntraCanRqV1Aggregate();
    localTypeIntraTrnRqV1Aggregate.IntraTrnRqV1Un.IntraCanRq.SrvrTID = paramTransfer.getID();
  }
  
  protected void formatModifyTransferRequest(Transfer paramTransfer)
  {
    this.rqmes = new TypeOFXRequest();
    this.rqmes.OFXRequest = new TypeOFXRqMsgSetsAggregate();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatModifyTransferRequest:");
    }
    formatSIGNONMSGSRQV1(this.rqmes.OFXRequest);
    TypeIntraTrnRqV1Aggregate localTypeIntraTrnRqV1Aggregate = formatINTRATRNRQ(this.rqmes.OFXRequest, "IntraModRq");
    localTypeIntraTrnRqV1Aggregate.TrnRqCm = a(paramTransfer);
    localTypeIntraTrnRqV1Aggregate.IntraTrnRqV1Un.IntraModRq = new TypeIntraModRqV1Aggregate();
    localTypeIntraTrnRqV1Aggregate.IntraTrnRqV1Un.IntraModRq.SrvrTID = paramTransfer.getID();
    Double localDouble = new Double(paramTransfer.getAmountValue().doubleValue());
    String str = paramTransfer.getAmtCurrency();
    if (str == null) {
      str = paramTransfer.getFromAccount().getCurrencyCode();
    }
    if (str != null)
    {
      EnumCurrencyEnum localEnumCurrencyEnum = null;
      try
      {
        int i = ValueSetCurrencyEnum.getIndex(str);
        localEnumCurrencyEnum = EnumCurrencyEnum.from_int(i);
      }
      catch (Throwable localThrowable) {}
      if (localEnumCurrencyEnum != null)
      {
        localTypeIntraTrnRqV1Aggregate.IntraTrnRqV1Un.IntraModRq.CurDef = localEnumCurrencyEnum;
        localTypeIntraTrnRqV1Aggregate.IntraTrnRqV1Un.IntraModRq.CurDefExists = true;
      }
    }
    localTypeIntraTrnRqV1Aggregate.IntraTrnRqV1Un.IntraModRq.XferInfo = formatXFERINFO(localDouble.doubleValue(), this.fromAccount, this.toAccount, paramTransfer.getDateValue());
  }
  
  protected void formatSendRecTransferRequest(RecTransfer paramRecTransfer)
  {
    this.rqmes = new TypeOFXRequest();
    this.rqmes.OFXRequest = new TypeOFXRqMsgSetsAggregate();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatSendRecTransferRequest:");
    }
    formatSIGNONMSGSRQV1(this.rqmes.OFXRequest);
    TypeRecIntraTrnRqV1Aggregate localTypeRecIntraTrnRqV1Aggregate = formatRECINTRATRNRQ(this.rqmes.OFXRequest, "RecIntraRq");
    localTypeRecIntraTrnRqV1Aggregate.TrnRqCm = a(paramRecTransfer);
    localTypeRecIntraTrnRqV1Aggregate.RecIntraTrnRqV1Un.RecIntraRq = new TypeRecIntraRqV1Aggregate();
    Double localDouble = new Double(paramRecTransfer.getAmountValue().doubleValue());
    localTypeRecIntraTrnRqV1Aggregate.RecIntraTrnRqV1Un.RecIntraRq.IntraRq = new TypeIntraRqV1Aggregate();
    String str = paramRecTransfer.getAmtCurrency();
    if (str == null) {
      str = paramRecTransfer.getFromAccount().getCurrencyCode();
    }
    if (str != null)
    {
      EnumCurrencyEnum localEnumCurrencyEnum = null;
      try
      {
        int i = ValueSetCurrencyEnum.getIndex(str);
        localEnumCurrencyEnum = EnumCurrencyEnum.from_int(i);
      }
      catch (Throwable localThrowable) {}
      if (localEnumCurrencyEnum != null)
      {
        localTypeRecIntraTrnRqV1Aggregate.RecIntraTrnRqV1Un.RecIntraRq.IntraRq.CurDef = localEnumCurrencyEnum;
        localTypeRecIntraTrnRqV1Aggregate.RecIntraTrnRqV1Un.RecIntraRq.IntraRq.CurDefExists = true;
      }
    }
    localTypeRecIntraTrnRqV1Aggregate.RecIntraTrnRqV1Un.RecIntraRq.IntraRq.XferInfo = formatXFERINFO(localDouble.doubleValue(), this.fromAccount, this.toAccount, paramRecTransfer.getDateValue());
    localTypeRecIntraTrnRqV1Aggregate.RecIntraTrnRqV1Un.RecIntraRq.RecurrInst = formatRECURRINST(paramRecTransfer.getFrequencyValue(), paramRecTransfer.getNumberTransfersValue());
  }
  
  protected void formatDeleteRecTransferRequest(RecTransfer paramRecTransfer)
  {
    this.rqmes = new TypeOFXRequest();
    this.rqmes.OFXRequest = new TypeOFXRqMsgSetsAggregate();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatSendRecTransferRequest:");
    }
    formatSIGNONMSGSRQV1(this.rqmes.OFXRequest);
    TypeRecIntraTrnRqV1Aggregate localTypeRecIntraTrnRqV1Aggregate = formatRECINTRATRNRQ(this.rqmes.OFXRequest, "RecIntraCanRq");
    localTypeRecIntraTrnRqV1Aggregate.TrnRqCm = a(paramRecTransfer);
    localTypeRecIntraTrnRqV1Aggregate.RecIntraTrnRqV1Un.RecIntraCanRq = new TypeRecIntraCanRqV1Aggregate();
    localTypeRecIntraTrnRqV1Aggregate.RecIntraTrnRqV1Un.RecIntraCanRq.CanPending = true;
    localTypeRecIntraTrnRqV1Aggregate.RecIntraTrnRqV1Un.RecIntraCanRq.RecSrvrTID = paramRecTransfer.getRecTransferID();
  }
  
  protected void formatModifyRecTransferRequest(RecTransfer paramRecTransfer)
  {
    this.rqmes = new TypeOFXRequest();
    this.rqmes.OFXRequest = new TypeOFXRqMsgSetsAggregate();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatSendRecTransferRequest:");
    }
    formatSIGNONMSGSRQV1(this.rqmes.OFXRequest);
    TypeRecIntraTrnRqV1Aggregate localTypeRecIntraTrnRqV1Aggregate = formatRECINTRATRNRQ(this.rqmes.OFXRequest, "RecIntraModRq");
    localTypeRecIntraTrnRqV1Aggregate.TrnRqCm = a(paramRecTransfer);
    localTypeRecIntraTrnRqV1Aggregate.RecIntraTrnRqV1Un.RecIntraModRq = new TypeRecIntraModRqV1Aggregate();
    localTypeRecIntraTrnRqV1Aggregate.RecIntraTrnRqV1Un.RecIntraModRq.ModPending = true;
    localTypeRecIntraTrnRqV1Aggregate.RecIntraTrnRqV1Un.RecIntraModRq.RecSrvrTID = paramRecTransfer.getRecTransferID();
    Double localDouble = new Double(paramRecTransfer.getAmountValue().doubleValue());
    localTypeRecIntraTrnRqV1Aggregate.RecIntraTrnRqV1Un.RecIntraModRq.IntraRq = new TypeIntraRqV1Aggregate();
    String str = paramRecTransfer.getAmtCurrency();
    if (str == null) {
      str = paramRecTransfer.getFromAccount().getCurrencyCode();
    }
    if (str != null)
    {
      EnumCurrencyEnum localEnumCurrencyEnum = null;
      try
      {
        int i = ValueSetCurrencyEnum.getIndex(str);
        localEnumCurrencyEnum = EnumCurrencyEnum.from_int(i);
      }
      catch (Throwable localThrowable) {}
      if (localEnumCurrencyEnum != null)
      {
        localTypeRecIntraTrnRqV1Aggregate.RecIntraTrnRqV1Un.RecIntraModRq.IntraRq.CurDef = localEnumCurrencyEnum;
        localTypeRecIntraTrnRqV1Aggregate.RecIntraTrnRqV1Un.RecIntraModRq.IntraRq.CurDefExists = true;
      }
    }
    localTypeRecIntraTrnRqV1Aggregate.RecIntraTrnRqV1Un.RecIntraModRq.IntraRq.XferInfo = formatXFERINFO(localDouble.doubleValue(), this.fromAccount, this.toAccount, paramRecTransfer.getDateValue());
    localTypeRecIntraTrnRqV1Aggregate.RecIntraTrnRqV1Un.RecIntraModRq.RecurrInst = formatRECURRINST(paramRecTransfer.getFrequencyValue(), paramRecTransfer.getNumberTransfersValue());
  }
  
  protected void formatSkipRecTransferRequest(RecTransfer paramRecTransfer)
  {
    this.rqmes = new TypeOFXRequest();
    this.rqmes.OFXRequest = new TypeOFXRqMsgSetsAggregate();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatSkipRecTransferRequest:");
    }
    formatSIGNONMSGSRQV1(this.rqmes.OFXRequest);
  }
  
  protected void formatGetTransfersSyncRequest()
  {
    this.rqmes = new TypeOFXRequest();
    this.rqmes.OFXRequest = new TypeOFXRqMsgSetsAggregate();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatGetTransfersSyncRequest:");
    }
    formatSIGNONMSGSRQV1(this.rqmes.OFXRequest);
    int i = 0;
    for (int j = 0; j < this.accounts.size(); j++)
    {
      this.account = ((Account)this.accounts.get(j));
      if ((this.account.isFilterable("TransferFrom")) && (this.account.getTypeValue() != 3)) {
        i++;
      }
    }
    if (i > 0)
    {
      this.rqmes.OFXRequest.BankMsgsRqUnExists = true;
      this.rqmes.OFXRequest.BankMsgsRqUn = new TypeBankMsgsRqUn();
      this.rqmes.OFXRequest.BankMsgsRqUn.__memberName = "BankMsgsRqV1";
      this.rqmes.OFXRequest.BankMsgsRqUn.BankMsgsRqV1 = new TypeBankMsgsRqV1Aggregate();
      this.rqmes.OFXRequest.BankMsgsRqUn.BankMsgsRqV1.BankMsgsRqV1Un = new TypeBankMsgsRqV1Un[i];
      i = 0;
      for (j = 0; j < this.accounts.size(); j++)
      {
        this.account = ((Account)this.accounts.get(j));
        if ((this.account.isFilterable("TransferFrom")) && (this.account.getTypeValue() != 3))
        {
          TypeBankMsgsRqV1Un localTypeBankMsgsRqV1Un = new TypeBankMsgsRqV1Un();
          this.rqmes.OFXRequest.BankMsgsRqUn.BankMsgsRqV1.BankMsgsRqV1Un[i] = localTypeBankMsgsRqV1Un;
          localTypeBankMsgsRqV1Un.__memberName = "IntraSyncRq";
          localTypeBankMsgsRqV1Un.IntraSyncRq = new TypeIntraSyncRqV1Aggregate();
          localTypeBankMsgsRqV1Un.IntraSyncRq.BankAcctFrom = new TypeBankAcctFromV1Aggregate();
          formatBANKACCTFROM(localTypeBankMsgsRqV1Un.IntraSyncRq.BankAcctFrom, this.account);
          localTypeBankMsgsRqV1Un.IntraSyncRq.SyncRqV1Cm = new TypeSyncRqV1Cm();
          localTypeBankMsgsRqV1Un.IntraSyncRq.SyncRqV1Cm.RejectIfMissing = false;
          TypeTokenRqV1Un localTypeTokenRqV1Un = new TypeTokenRqV1Un();
          localTypeBankMsgsRqV1Un.IntraSyncRq.SyncRqV1Cm.TokenRqV1Un = localTypeTokenRqV1Un;
          localTypeTokenRqV1Un.__memberName = "Token";
          if ((this.m_TransferToken != null) && (this.m_TransferToken.length() > 0)) {
            localTypeTokenRqV1Un.Token = this.m_TransferToken;
          } else {
            localTypeTokenRqV1Un.Token = "0";
          }
          i++;
        }
      }
    }
  }
  
  protected void formatGetRecTransfersSyncRequest()
  {
    this.rqmes = new TypeOFXRequest();
    this.rqmes.OFXRequest = new TypeOFXRqMsgSetsAggregate();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatGetRecTransfersSyncRequest:");
    }
    formatSIGNONMSGSRQV1(this.rqmes.OFXRequest);
    int i = 0;
    for (int j = 0; j < this.accounts.size(); j++)
    {
      this.account = ((Account)this.accounts.get(j));
      if ((this.account.isFilterable("TransferFrom")) && (this.account.getTypeValue() != 3)) {
        i++;
      }
    }
    if (i > 0)
    {
      this.rqmes.OFXRequest.BankMsgsRqUnExists = true;
      this.rqmes.OFXRequest.BankMsgsRqUn = new TypeBankMsgsRqUn();
      this.rqmes.OFXRequest.BankMsgsRqUn.__memberName = "BankMsgsRqV1";
      this.rqmes.OFXRequest.BankMsgsRqUn.BankMsgsRqV1 = new TypeBankMsgsRqV1Aggregate();
      this.rqmes.OFXRequest.BankMsgsRqUn.BankMsgsRqV1.BankMsgsRqV1Un = new TypeBankMsgsRqV1Un[i];
      i = 0;
      for (j = 0; j < this.accounts.size(); j++)
      {
        this.account = ((Account)this.accounts.get(j));
        if ((this.account.isFilterable("TransferFrom")) && (this.account.getTypeValue() != 3))
        {
          TypeBankMsgsRqV1Un localTypeBankMsgsRqV1Un = new TypeBankMsgsRqV1Un();
          this.rqmes.OFXRequest.BankMsgsRqUn.BankMsgsRqV1.BankMsgsRqV1Un[i] = localTypeBankMsgsRqV1Un;
          localTypeBankMsgsRqV1Un.__memberName = "RecIntraSyncRq";
          localTypeBankMsgsRqV1Un.RecIntraSyncRq = new TypeRecIntraSyncRqV1Aggregate();
          localTypeBankMsgsRqV1Un.RecIntraSyncRq.BankAcctFrom = new TypeBankAcctFromV1Aggregate();
          formatBANKACCTFROM(localTypeBankMsgsRqV1Un.RecIntraSyncRq.BankAcctFrom, this.account);
          localTypeBankMsgsRqV1Un.RecIntraSyncRq.SyncRqV1Cm = new TypeSyncRqV1Cm();
          localTypeBankMsgsRqV1Un.RecIntraSyncRq.SyncRqV1Cm.RejectIfMissing = false;
          TypeTokenRqV1Un localTypeTokenRqV1Un = new TypeTokenRqV1Un();
          localTypeBankMsgsRqV1Un.RecIntraSyncRq.SyncRqV1Cm.TokenRqV1Un = localTypeTokenRqV1Un;
          localTypeTokenRqV1Un.__memberName = "Token";
          if ((this.m_RecTransferToken != null) && (this.m_RecTransferToken.length() > 0)) {
            localTypeTokenRqV1Un.Token = this.m_RecTransferToken;
          } else {
            localTypeTokenRqV1Un.Token = "0";
          }
          i++;
        }
      }
    }
  }
  
  protected TypeIntraTrnRqV1Aggregate formatINTRATRNRQ(TypeOFXRqMsgSetsAggregate paramTypeOFXRqMsgSetsAggregate, String paramString)
  {
    paramTypeOFXRqMsgSetsAggregate.BankMsgsRqUnExists = true;
    paramTypeOFXRqMsgSetsAggregate.BankMsgsRqUn = new TypeBankMsgsRqUn();
    paramTypeOFXRqMsgSetsAggregate.BankMsgsRqUn.__memberName = "BankMsgsRqV1";
    paramTypeOFXRqMsgSetsAggregate.BankMsgsRqUn.BankMsgsRqV1 = new TypeBankMsgsRqV1Aggregate();
    paramTypeOFXRqMsgSetsAggregate.BankMsgsRqUn.BankMsgsRqV1.BankMsgsRqV1Un = new TypeBankMsgsRqV1Un[1];
    paramTypeOFXRqMsgSetsAggregate.BankMsgsRqUn.BankMsgsRqV1.BankMsgsRqV1Un[0] = new TypeBankMsgsRqV1Un();
    paramTypeOFXRqMsgSetsAggregate.BankMsgsRqUn.BankMsgsRqV1.BankMsgsRqV1Un[0].__memberName = "IntraTrnRq";
    TypeIntraTrnRqV1Aggregate localTypeIntraTrnRqV1Aggregate = new TypeIntraTrnRqV1Aggregate();
    paramTypeOFXRqMsgSetsAggregate.BankMsgsRqUn.BankMsgsRqV1.BankMsgsRqV1Un[0].IntraTrnRq = localTypeIntraTrnRqV1Aggregate;
    localTypeIntraTrnRqV1Aggregate.IntraTrnRqV1Un = new TypeIntraTrnRqV1Un();
    localTypeIntraTrnRqV1Aggregate.IntraTrnRqV1Un.__memberName = paramString;
    return localTypeIntraTrnRqV1Aggregate;
  }
  
  protected TypeXferInfoV1Aggregate formatXFERINFO(double paramDouble, Account paramAccount1, Account paramAccount2, DateTime paramDateTime)
  {
    TypeXferInfoV1Aggregate localTypeXferInfoV1Aggregate = new TypeXferInfoV1Aggregate();
    localTypeXferInfoV1Aggregate.TrnAmt = paramDouble;
    localTypeXferInfoV1Aggregate.BCCAcctFromV1Un = new TypeBCCAcctFromV1Un();
    formatFromAccount(localTypeXferInfoV1Aggregate.BCCAcctFromV1Un, paramAccount1);
    localTypeXferInfoV1Aggregate.BCCAcctToV1Un = new TypeBCCAcctToV1Un();
    formatToAccount(localTypeXferInfoV1Aggregate.BCCAcctToV1Un, paramAccount2);
    if (paramDateTime != null)
    {
      localTypeXferInfoV1Aggregate.DtDueExists = true;
      localTypeXferInfoV1Aggregate.DtDue = setDate(paramDateTime, "yyyyMMdd");
    }
    return localTypeXferInfoV1Aggregate;
  }
  
  protected TypeRecIntraTrnRqV1Aggregate formatRECINTRATRNRQ(TypeOFXRqMsgSetsAggregate paramTypeOFXRqMsgSetsAggregate, String paramString)
  {
    paramTypeOFXRqMsgSetsAggregate.BankMsgsRqUnExists = true;
    paramTypeOFXRqMsgSetsAggregate.BankMsgsRqUn = new TypeBankMsgsRqUn();
    paramTypeOFXRqMsgSetsAggregate.BankMsgsRqUn.__memberName = "BankMsgsRqV1";
    paramTypeOFXRqMsgSetsAggregate.BankMsgsRqUn.BankMsgsRqV1 = new TypeBankMsgsRqV1Aggregate();
    paramTypeOFXRqMsgSetsAggregate.BankMsgsRqUn.BankMsgsRqV1.BankMsgsRqV1Un = new TypeBankMsgsRqV1Un[1];
    paramTypeOFXRqMsgSetsAggregate.BankMsgsRqUn.BankMsgsRqV1.BankMsgsRqV1Un[0] = new TypeBankMsgsRqV1Un();
    paramTypeOFXRqMsgSetsAggregate.BankMsgsRqUn.BankMsgsRqV1.BankMsgsRqV1Un[0].__memberName = "RecIntraTrnRq";
    TypeRecIntraTrnRqV1Aggregate localTypeRecIntraTrnRqV1Aggregate = new TypeRecIntraTrnRqV1Aggregate();
    paramTypeOFXRqMsgSetsAggregate.BankMsgsRqUn.BankMsgsRqV1.BankMsgsRqV1Un[0].RecIntraTrnRq = localTypeRecIntraTrnRqV1Aggregate;
    localTypeRecIntraTrnRqV1Aggregate.RecIntraTrnRqV1Un = new TypeRecIntraTrnRqV1Un();
    localTypeRecIntraTrnRqV1Aggregate.RecIntraTrnRqV1Un.__memberName = paramString;
    return localTypeRecIntraTrnRqV1Aggregate;
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
  
  protected void processOFXRequest()
  {
    TypeOFXRequest localTypeOFXRequest = this.rqmes;
    this.objStatus.clear();
    getHandler();
    try
    {
      if (localTypeOFXRequest.OFXRequest.SignOnMsgsRqUn.__memberName.equals("SignOnMsgsRqV1")) {
        processTransactionsInSignOnMsgsRqV1(localTypeOFXRequest.OFXRequest.SignOnMsgsRqUn.SignOnMsgsRqV1);
      }
      if ((localTypeOFXRequest.OFXRequest.SignUpMsgsRqUnExists) && (localTypeOFXRequest.OFXRequest.SignUpMsgsRqUn.__memberName.equals("SignUpMsgsRqV1"))) {
        processTransactionsInSignUpMsgsRqV1(localTypeOFXRequest.OFXRequest.SignUpMsgsRqUn.SignUpMsgsRqV1);
      }
      if ((localTypeOFXRequest.OFXRequest.BankMsgsRqUnExists) && (localTypeOFXRequest.OFXRequest.BankMsgsRqUn.__memberName.equals("BankMsgsRqV1"))) {
        processTransactionsInBankMsgsRqV1(localTypeOFXRequest.OFXRequest.BankMsgsRqUn.BankMsgsRqV1);
      }
      if ((localTypeOFXRequest.OFXRequest.CreditCardMsgsRqUnExists) && (localTypeOFXRequest.OFXRequest.CreditCardMsgsRqUn.__memberName.equals("CreditCardMsgsRqV1"))) {
        processTransactionsInCreditCardMsgsRqV1(localTypeOFXRequest.OFXRequest.CreditCardMsgsRqUn.CreditCardMsgsRqV1);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    if (this.signonProcessed == true) {
      processDisconnectRq(null);
    }
    removeHandler();
  }
  
  protected void processTransactionsInBankMsgsRqV1(TypeBankMsgsRqV1Aggregate paramTypeBankMsgsRqV1Aggregate)
  {
    for (int i = 0; i < paramTypeBankMsgsRqV1Aggregate.BankMsgsRqV1Un.length; i++)
    {
      Object localObject1;
      Object localObject2;
      if (paramTypeBankMsgsRqV1Aggregate.BankMsgsRqV1Un[i].__memberName.equals("StmtTrnRq"))
      {
        localObject1 = new TypeStmtTrnRqV1(paramTypeBankMsgsRqV1Aggregate.BankMsgsRqV1Un[i].StmtTrnRq);
        localObject2 = null;
        localObject2 = processStmtTrnRqV1((TypeStmtTrnRqV1)localObject1);
        if (localObject2 != null) {
          processStmtTrnRs(((TypeStmtTrnRsV1)localObject2).StmtTrnRs);
        }
      }
      if (paramTypeBankMsgsRqV1Aggregate.BankMsgsRqV1Un[i].__memberName.equals("IntraTrnRq"))
      {
        localObject1 = new TypeIntraTrnRqV1(paramTypeBankMsgsRqV1Aggregate.BankMsgsRqV1Un[i].IntraTrnRq);
        localObject2 = null;
        localObject2 = processIntraTrnRqV1((TypeIntraTrnRqV1)localObject1);
        if (localObject2 != null) {
          processIntraTrnRs(((TypeIntraTrnRsV1)localObject2).IntraTrnRs);
        }
      }
      if (paramTypeBankMsgsRqV1Aggregate.BankMsgsRqV1Un[i].__memberName.equals("RecIntraTrnRq"))
      {
        localObject1 = new TypeRecIntraTrnRqV1(paramTypeBankMsgsRqV1Aggregate.BankMsgsRqV1Un[i].RecIntraTrnRq);
        localObject2 = null;
        localObject2 = processRecIntraTrnRqV1((TypeRecIntraTrnRqV1)localObject1);
        if (localObject2 != null) {
          processRecIntraTrnRs(((TypeRecIntraTrnRsV1)localObject2).RecIntraTrnRs);
        }
      }
      if (paramTypeBankMsgsRqV1Aggregate.BankMsgsRqV1Un[i].__memberName.equals("IntraSyncRq"))
      {
        localObject1 = new TypeIntraSyncRqV1(paramTypeBankMsgsRqV1Aggregate.BankMsgsRqV1Un[i].IntraSyncRq);
        localObject2 = null;
        localObject2 = processIntraSyncRqV1((TypeIntraSyncRqV1)localObject1);
        if (localObject2 != null) {
          processIntraSyncRs(((TypeIntraSyncRsV1)localObject2).IntraSyncRs);
        }
      }
      if (paramTypeBankMsgsRqV1Aggregate.BankMsgsRqV1Un[i].__memberName.equals("RecIntraSyncRq"))
      {
        localObject1 = new TypeRecIntraSyncRqV1(paramTypeBankMsgsRqV1Aggregate.BankMsgsRqV1Un[i].RecIntraSyncRq);
        localObject2 = null;
        localObject2 = processRecIntraSyncRqV1((TypeRecIntraSyncRqV1)localObject1);
        if (localObject2 != null) {
          processRecIntraSyncRs(((TypeRecIntraSyncRsV1)localObject2).RecIntraSyncRs);
        }
      }
    }
  }
  
  protected void processStmtTrnRs(TypeStmtTrnRsV1Aggregate paramTypeStmtTrnRsV1Aggregate)
  {
    super.processStmtTrnRs(paramTypeStmtTrnRsV1Aggregate);
    if ((paramTypeStmtTrnRsV1Aggregate.StmtRsExists) && (paramTypeStmtTrnRsV1Aggregate.StmtRs.BankTranListExists)) {
      processStmtTrn(paramTypeStmtTrnRsV1Aggregate.StmtRs.BankTranList.StmtTrn);
    }
  }
  
  protected void processCCStmtTrnRs(TypeCCStmtTrnRsV1Aggregate paramTypeCCStmtTrnRsV1Aggregate)
  {
    super.processCCStmtTrnRs(paramTypeCCStmtTrnRsV1Aggregate);
    if ((paramTypeCCStmtTrnRsV1Aggregate.CCStmtRsExists) && (paramTypeCCStmtTrnRsV1Aggregate.CCStmtRs.BankTranListExists)) {
      processStmtTrn(paramTypeCCStmtTrnRsV1Aggregate.CCStmtRs.BankTranList.StmtTrn);
    }
  }
  
  protected void processStmtTrn(TypeStmtTrnV1Aggregate[] paramArrayOfTypeStmtTrnV1Aggregate)
  {
    if (this.transactions == null) {
      return;
    }
    for (int i = 0; i < paramArrayOfTypeStmtTrnV1Aggregate.length; i++)
    {
      String str1 = paramArrayOfTypeStmtTrnV1Aggregate[i].FITID;
      this.transaction = ((Transaction)this.transactions.getFirstByFilter("ID=" + str1));
      if (this.transaction == null)
      {
        this.transaction = this.transactions.create();
        this.transaction.setID(str1);
      }
      if (this.transaction != null)
      {
        int j = 0;
        if ((!paramArrayOfTypeStmtTrnV1Aggregate[i].BCCAcctToV1UnExists) || (paramArrayOfTypeStmtTrnV1Aggregate[i].CheckNumExists)) {
          this.transaction.setReferenceNumber(paramArrayOfTypeStmtTrnV1Aggregate[i].CheckNum);
        }
        if (paramArrayOfTypeStmtTrnV1Aggregate[i].RefNumExists) {
          if ((this.transaction.getReferenceNumber() != null) && (this.transaction.getReferenceNumber().length() > 0)) {
            this.transaction.setReferenceNumber(this.transaction.getReferenceNumber() + " " + paramArrayOfTypeStmtTrnV1Aggregate[i].RefNum);
          } else {
            this.transaction.setReferenceNumber(paramArrayOfTypeStmtTrnV1Aggregate[i].RefNum);
          }
        }
        if (this.transaction.getReferenceNumber() == null) {
          this.transaction.setReferenceNumber(str1);
        }
        if (paramArrayOfTypeStmtTrnV1Aggregate[i].MemoExists) {
          this.transaction.setMemo(paramArrayOfTypeStmtTrnV1Aggregate[i].Memo);
        }
        this.transaction.setDate(getDate(paramArrayOfTypeStmtTrnV1Aggregate[i].DtPosted));
        this.transaction.setAmount(new BigDecimal(paramArrayOfTypeStmtTrnV1Aggregate[i].TrnAmt));
        EnumTransactionEnum localEnumTransactionEnum = paramArrayOfTypeStmtTrnV1Aggregate[i].TrnType;
        for (j = 0; (j < BankingDefines.transType.length) && (BankingDefines.transType[j].value() != localEnumTransactionEnum.value()); j++) {}
        if (j < BankingDefines.transMap.length)
        {
          j = BankingDefines.transMap[j];
        }
        else
        {
          if (this.debugService) {
            DebugLog.log(Level.INFO, "NON-Standard setTransaction() EnumTransactionEnum = " + localEnumTransactionEnum.value());
          }
          throw new IllegalArgumentException("setTransaction: type=" + localEnumTransactionEnum.value());
        }
        if (j != 0)
        {
          double d = this.transaction.getAmountValue().doubleValue();
          if ((j == 7) && (d > 0.0D)) {
            j = 6;
          }
          if ((j == 9) && (d > 0.0D)) {
            j = 8;
          }
          if ((j == 11) && (d < 0.0D)) {
            j = 24;
          }
          this.transaction.setType(j);
        }
        if (paramArrayOfTypeStmtTrnV1Aggregate[i].StmtTrnV1NamePayeeUnExists) {
          if (paramArrayOfTypeStmtTrnV1Aggregate[i].StmtTrnV1NamePayeeUn.__memberName.equals("Name")) {
            this.transaction.setDescription(paramArrayOfTypeStmtTrnV1Aggregate[i].StmtTrnV1NamePayeeUn.Name);
          } else if (paramArrayOfTypeStmtTrnV1Aggregate[i].StmtTrnV1NamePayeeUn.__memberName.equals("Payee")) {
            this.transaction.setDescription(paramArrayOfTypeStmtTrnV1Aggregate[i].StmtTrnV1NamePayeeUn.Payee.Name);
          }
        }
        if (paramArrayOfTypeStmtTrnV1Aggregate[i].StmtTrnV1CorrectCmExists) {
          if (paramArrayOfTypeStmtTrnV1Aggregate[i].StmtTrnV1CorrectCm.CorrectAction.value() == 0)
          {
            this.transactions.remove(this.transaction);
            this.transaction = null;
          }
          else if ((paramArrayOfTypeStmtTrnV1Aggregate[i].StmtTrnV1CorrectCm.CorrectAction.value() == 1) && (paramArrayOfTypeStmtTrnV1Aggregate[i].StmtTrnV1CorrectCm.CorrectFITIDExists))
          {
            String str2 = paramArrayOfTypeStmtTrnV1Aggregate[i].StmtTrnV1CorrectCm.CorrectFITID;
            if (this.transactions.getFirstByFilter("ID=" + str2) == null) {
              this.transaction.setID(str2);
            }
          }
        }
        if ((!paramArrayOfTypeStmtTrnV1Aggregate[i].StmtTrnV1NameServerCmExists) || ((this.debugService) && (this.transaction != null))) {
          DebugLog.log(Level.INFO, XMLHandler.format(this.transaction.toXML()));
        }
      }
    }
  }
  
  protected void processIntraTrnRs(TypeIntraTrnRsV1Aggregate paramTypeIntraTrnRsV1Aggregate)
  {
    processSTATUS(paramTypeIntraTrnRsV1Aggregate.TrnRsV1Cm.Status);
    String str1 = null;
    String str2 = paramTypeIntraTrnRsV1Aggregate.TrnRsV1Cm.TrnUID;
    TypeXferInfoV1Aggregate localTypeXferInfoV1Aggregate = null;
    TypeXferPrcStsAggregate localTypeXferPrcStsAggregate = null;
    String str3 = null;
    String str4 = null;
    if (paramTypeIntraTrnRsV1Aggregate.IntraTrnRsV1UnExists)
    {
      if (paramTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un.__memberName.equals("IntraRs"))
      {
        str1 = paramTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un.IntraRs.SrvrTID;
        localTypeXferInfoV1Aggregate = paramTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un.IntraRs.XferInfo;
        if (paramTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un.IntraRs.CurDef != null) {
          try
          {
            str4 = ValueSetCurrencyEnum.getValue(paramTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un.IntraRs.CurDef.value());
          }
          catch (Throwable localThrowable1) {}
        }
        if (paramTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un.IntraRs.RecSrvrTIDExists) {
          str3 = paramTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un.IntraRs.RecSrvrTID;
        }
        if (paramTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un.IntraRs.XferPrcStsExists) {
          localTypeXferPrcStsAggregate = paramTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un.IntraRs.XferPrcSts;
        }
      }
      else if (paramTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un.__memberName.equals("IntraModRs"))
      {
        str1 = paramTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un.IntraModRs.SrvrTID;
        localTypeXferInfoV1Aggregate = paramTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un.IntraModRs.XferInfo;
        if (paramTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un.IntraModRs.CurDef != null) {
          try
          {
            str4 = ValueSetCurrencyEnum.getValue(paramTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un.IntraModRs.CurDef.value());
          }
          catch (Throwable localThrowable2) {}
        }
        if (paramTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un.IntraModRs.XferPrcStsExists) {
          localTypeXferPrcStsAggregate = paramTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un.IntraModRs.XferPrcSts;
        }
      }
      else if (paramTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un.__memberName.equals("IntraCanRs"))
      {
        str1 = paramTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un.IntraCanRs.SrvrTID;
      }
      if (this.transfers != null)
      {
        if ((str2 != null) && (!str2.equals("0"))) {
          this.transfer = ((Transfer)this.transfers.getFirstByFilter("TRNUID=" + str2));
        }
        if (this.transfer == null) {
          this.transfer = ((Transfer)this.transfers.getFirstByFilter("ID=" + str1));
        }
        if (this.transfer == null) {
          this.transfer = ((Transfer)this.transfers.create());
        }
      }
      if (this.transfer != null)
      {
        this.transfer.setID(str1);
        Integer localInteger = new Integer(this.status);
        this.transfer.setError(mapError(localInteger.intValue()));
        this.objStatus.put(str1, localInteger.toString());
        if (str4 != null) {
          this.transfer.setAmtCurrency(str4);
        }
        if (paramTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un.__memberName.equals("IntraCanRs"))
        {
          this.transfer.setStatus(3);
        }
        else
        {
          if ((this.status == 0) && (this.transfer.getStatus() == 1)) {
            this.transfer.setStatus(2);
          }
          if (localTypeXferInfoV1Aggregate != null) {
            processXFERINFO(this.transfer, this.accounts, localTypeXferInfoV1Aggregate);
          }
          if (str3 != null) {
            this.transfer.setRecTransferID(str3);
          }
          if (localTypeXferPrcStsAggregate != null)
          {
            this.transfer.setDate(getDate(localTypeXferPrcStsAggregate.DtXferPrc));
            EnumXferStatusEnum localEnumXferStatusEnum = localTypeXferPrcStsAggregate.XferPrcCode;
            this.transfer.setStatus(a(localEnumXferStatusEnum));
          }
          if (this.transfer.getReferenceNumber() == null) {
            this.transfer.setReferenceNumber(str1);
          }
        }
      }
    }
  }
  
  protected void processIntraSyncRs(TypeIntraSyncRsV1Aggregate paramTypeIntraSyncRsV1Aggregate)
  {
    if ((paramTypeIntraSyncRsV1Aggregate != null) && (paramTypeIntraSyncRsV1Aggregate.IntraTrnRs != null))
    {
      TypeIntraTrnRsV1Aggregate[] arrayOfTypeIntraTrnRsV1Aggregate = paramTypeIntraSyncRsV1Aggregate.IntraTrnRs;
      for (int i = 0; i < arrayOfTypeIntraTrnRsV1Aggregate.length; i++)
      {
        this.transfer = null;
        processIntraTrnRs(arrayOfTypeIntraTrnRsV1Aggregate[i]);
      }
    }
  }
  
  protected void processRecIntraTrnRs(TypeRecIntraTrnRsV1Aggregate paramTypeRecIntraTrnRsV1Aggregate)
  {
    processSTATUS(paramTypeRecIntraTrnRsV1Aggregate.TrnRsV1Cm.Status);
    String str1 = null;
    String str2 = paramTypeRecIntraTrnRsV1Aggregate.TrnRsV1Cm.TrnUID;
    TypeRecurrInstAggregate localTypeRecurrInstAggregate = null;
    TypeXferPrcStsAggregate localTypeXferPrcStsAggregate = null;
    TypeXferInfoV1Aggregate localTypeXferInfoV1Aggregate = null;
    String str3 = null;
    if (paramTypeRecIntraTrnRsV1Aggregate.RecIntraRsV1UnExists)
    {
      if (paramTypeRecIntraTrnRsV1Aggregate.RecIntraRsV1Un.__memberName.equals("RecIntraRs"))
      {
        str1 = paramTypeRecIntraTrnRsV1Aggregate.RecIntraRsV1Un.RecIntraRs.RecSrvrTID;
        localTypeRecurrInstAggregate = paramTypeRecIntraTrnRsV1Aggregate.RecIntraRsV1Un.RecIntraRs.RecurrInst;
        if (paramTypeRecIntraTrnRsV1Aggregate.RecIntraRsV1Un.RecIntraRs.IntraRs.CurDef != null) {
          try
          {
            str3 = ValueSetCurrencyEnum.getValue(paramTypeRecIntraTrnRsV1Aggregate.RecIntraRsV1Un.RecIntraRs.IntraRs.CurDef.value());
          }
          catch (Throwable localThrowable1) {}
        }
        if (paramTypeRecIntraTrnRsV1Aggregate.RecIntraRsV1Un.RecIntraRs.IntraRs.XferPrcStsExists) {
          localTypeXferPrcStsAggregate = paramTypeRecIntraTrnRsV1Aggregate.RecIntraRsV1Un.RecIntraRs.IntraRs.XferPrcSts;
        }
        localTypeXferInfoV1Aggregate = paramTypeRecIntraTrnRsV1Aggregate.RecIntraRsV1Un.RecIntraRs.IntraRs.XferInfo;
      }
      else if (paramTypeRecIntraTrnRsV1Aggregate.RecIntraRsV1Un.__memberName.equals("RecIntraModRs"))
      {
        str1 = paramTypeRecIntraTrnRsV1Aggregate.RecIntraRsV1Un.RecIntraModRs.RecSrvrTID;
        localTypeRecurrInstAggregate = paramTypeRecIntraTrnRsV1Aggregate.RecIntraRsV1Un.RecIntraModRs.RecurrInst;
        if (paramTypeRecIntraTrnRsV1Aggregate.RecIntraRsV1Un.RecIntraModRs.IntraRs.CurDef != null) {
          try
          {
            str3 = ValueSetCurrencyEnum.getValue(paramTypeRecIntraTrnRsV1Aggregate.RecIntraRsV1Un.RecIntraModRs.IntraRs.CurDef.value());
          }
          catch (Throwable localThrowable2) {}
        }
        if (paramTypeRecIntraTrnRsV1Aggregate.RecIntraRsV1Un.RecIntraModRs.IntraRs.XferPrcStsExists) {
          localTypeXferPrcStsAggregate = paramTypeRecIntraTrnRsV1Aggregate.RecIntraRsV1Un.RecIntraModRs.IntraRs.XferPrcSts;
        }
        localTypeXferInfoV1Aggregate = paramTypeRecIntraTrnRsV1Aggregate.RecIntraRsV1Un.RecIntraModRs.IntraRs.XferInfo;
      }
      else if (paramTypeRecIntraTrnRsV1Aggregate.RecIntraRsV1Un.__memberName.equals("RecIntraCanRs"))
      {
        str1 = paramTypeRecIntraTrnRsV1Aggregate.RecIntraRsV1Un.RecIntraCanRs.RecSrvrTID;
      }
      if (this.rectransfers != null)
      {
        if ((str2 != null) && (!str2.equals("0"))) {
          this.rectransfer = ((RecTransfer)this.rectransfers.getFirstByFilter("TRNUID=" + str2));
        }
        if (this.rectransfer == null) {
          this.rectransfer = ((RecTransfer)this.rectransfers.getFirstByFilter("ID=" + str1));
        }
        if (this.rectransfer == null) {
          this.rectransfer = ((RecTransfer)this.rectransfers.create());
        }
      }
      if (this.rectransfer != null)
      {
        this.rectransfer.setID(str1);
        this.rectransfer.setRecTransferID(str1);
        Integer localInteger = new Integer(this.status);
        this.rectransfer.setError(mapError(localInteger.intValue()));
        this.objStatus.put(str1, localInteger.toString());
        if (str3 != null) {
          this.rectransfer.setAmtCurrency(str3);
        }
        if (paramTypeRecIntraTrnRsV1Aggregate.RecIntraRsV1Un.__memberName.equals("RecIntraCanRs"))
        {
          this.rectransfer.setStatus(3);
        }
        else
        {
          if ((this.status == 0) && (this.rectransfer.getStatus() == 1)) {
            this.rectransfer.setStatus(2);
          }
          if (localTypeXferInfoV1Aggregate != null) {
            processXFERINFO(this.rectransfer, this.accounts, localTypeXferInfoV1Aggregate);
          }
          if (localTypeRecurrInstAggregate != null)
          {
            this.rectransfer.setFrequency(getFrequency(localTypeRecurrInstAggregate.Freq));
            if (localTypeRecurrInstAggregate.NInstsExists)
            {
              this.rectransfer.setNumberTransfers(localTypeRecurrInstAggregate.NInsts);
              if (localTypeRecurrInstAggregate.NInsts < 0) {
                this.rectransfer.setStatus(3);
              }
            }
            else
            {
              this.rectransfer.setNumberTransfers(999);
            }
          }
          if (localTypeXferPrcStsAggregate != null)
          {
            this.rectransfer.setDate(getDate(localTypeXferPrcStsAggregate.DtXferPrc));
            EnumXferStatusEnum localEnumXferStatusEnum = localTypeXferPrcStsAggregate.XferPrcCode;
            this.rectransfer.setStatus(a(localEnumXferStatusEnum));
          }
          if (this.rectransfer.getReferenceNumber() == null) {
            this.rectransfer.setReferenceNumber(str1);
          }
        }
      }
    }
  }
  
  protected void processRecIntraSyncRs(TypeRecIntraSyncRsV1Aggregate paramTypeRecIntraSyncRsV1Aggregate)
  {
    if ((paramTypeRecIntraSyncRsV1Aggregate != null) && (paramTypeRecIntraSyncRsV1Aggregate.RecIntraTrnRs != null))
    {
      TypeRecIntraTrnRsV1Aggregate[] arrayOfTypeRecIntraTrnRsV1Aggregate = paramTypeRecIntraSyncRsV1Aggregate.RecIntraTrnRs;
      for (int i = 0; i < arrayOfTypeRecIntraTrnRsV1Aggregate.length; i++)
      {
        this.rectransfer = null;
        processRecIntraTrnRs(arrayOfTypeRecIntraTrnRsV1Aggregate[i]);
      }
    }
  }
  
  private int a(EnumXferStatusEnum paramEnumXferStatusEnum)
  {
    for (int i = 0; i < BankingDefines.xferStatTypes.length; i++) {
      if (BankingDefines.xferStatTypes[i].value() == paramEnumXferStatusEnum.value()) {
        return BankingDefines.xferStatMap[i];
      }
    }
    if (this.debugService) {
      DebugLog.log(Level.INFO, "NON-Standard getXferStatus() EnumXferStatusEnum = " + paramEnumXferStatusEnum.value());
    }
    throw new IllegalArgumentException("getXferStatus: XferStatus=" + paramEnumXferStatusEnum.value());
  }
  
  protected void processXFERINFO(Transfer paramTransfer, Accounts paramAccounts, TypeXferInfoV1Aggregate paramTypeXferInfoV1Aggregate)
  {
    paramTransfer.setAmount(new BigDecimal(paramTypeXferInfoV1Aggregate.TrnAmt));
    if (paramAccounts != null)
    {
      paramTransfer.setFromAccount(processBCCAcctFromV1Un(paramTypeXferInfoV1Aggregate.BCCAcctFromV1Un, paramAccounts));
      paramTransfer.setToAccount(processBCCAcctToV1Un(paramTypeXferInfoV1Aggregate.BCCAcctToV1Un, paramAccounts));
    }
    else
    {
      paramTransfer.setFromAccount(this.fromAccount);
      paramTransfer.setToAccount(this.toAccount);
    }
    if (paramTypeXferInfoV1Aggregate.DtDueExists) {
      paramTransfer.setDate(getDate(paramTypeXferInfoV1Aggregate.DtDue));
    }
  }
  
  protected void processTransactionsInCreditCardMsgsRqV1(TypeCreditCardMsgsRqV1Aggregate paramTypeCreditCardMsgsRqV1Aggregate)
  {
    for (int i = 0; i < paramTypeCreditCardMsgsRqV1Aggregate.CreditCardMsgsRqV1Un.length; i++) {
      if (paramTypeCreditCardMsgsRqV1Aggregate.CreditCardMsgsRqV1Un[i].__memberName.equals("CCStmtTrnRq"))
      {
        TypeCCStmtTrnRqV1 localTypeCCStmtTrnRqV1 = new TypeCCStmtTrnRqV1(paramTypeCreditCardMsgsRqV1Aggregate.CreditCardMsgsRqV1Un[i].CCStmtTrnRq);
        TypeCCStmtTrnRsV1 localTypeCCStmtTrnRsV1 = null;
        localTypeCCStmtTrnRsV1 = processCCStmtTrnRqV1(localTypeCCStmtTrnRqV1);
        if (localTypeCCStmtTrnRsV1 != null) {
          processCCStmtTrnRs(localTypeCCStmtTrnRsV1.CCStmtTrnRs);
        }
      }
    }
  }
  
  protected TypeIntraTrnRsV1 processIntraTrnRqV1(TypeIntraTrnRqV1 paramTypeIntraTrnRqV1)
  {
    try
    {
      if (this._OFXTransactionBean != null) {
        return this._OFXTransactionBean.processIntraTrnRqV1(paramTypeIntraTrnRqV1, this._ud);
      }
      return this._OFXTransactionHandler.processIntraTrnRqV1(paramTypeIntraTrnRqV1, this._ud);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception in processIntraTrnRqV1", localException);
    }
    return null;
  }
  
  protected TypeRecIntraTrnRsV1 processRecIntraTrnRqV1(TypeRecIntraTrnRqV1 paramTypeRecIntraTrnRqV1)
  {
    try
    {
      if (this._OFXTransactionBean != null) {
        return this._OFXTransactionBean.processRecIntraTrnRqV1(paramTypeRecIntraTrnRqV1, this._ud);
      }
      return this._OFXTransactionHandler.processRecIntraTrnRqV1(paramTypeRecIntraTrnRqV1, this._ud);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception in processRecIntraTrnRqV1", localException);
    }
    return null;
  }
  
  protected TypeIntraSyncRsV1 processIntraSyncRqV1(TypeIntraSyncRqV1 paramTypeIntraSyncRqV1)
  {
    try
    {
      if (this._OFXTransactionBean != null) {
        return this._OFXTransactionBean.processIntraSyncRqV1(paramTypeIntraSyncRqV1, this._ud);
      }
      return this._OFXTransactionHandler.processIntraSyncRqV1(paramTypeIntraSyncRqV1, this._ud);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception in processIntraSyncRqV1", localException);
    }
    return null;
  }
  
  protected TypeRecIntraSyncRsV1 processRecIntraSyncRqV1(TypeRecIntraSyncRqV1 paramTypeRecIntraSyncRqV1)
  {
    try
    {
      if (this._OFXTransactionBean != null) {
        return this._OFXTransactionBean.processRecIntraSyncRqV1(paramTypeRecIntraSyncRqV1, this._ud);
      }
      return this._OFXTransactionHandler.processRecIntraSyncRqV1(paramTypeRecIntraSyncRqV1, this._ud);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception in processRecIntraSyncRqV1", localException);
    }
    return null;
  }
  
  public class a
    extends Base.a
  {
    public a()
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
 * Qualified Name:     com.ffusion.services.FFServer.OFX151.Banking
 * JD-Core Version:    0.7.0.1
 */