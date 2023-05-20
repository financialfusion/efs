package com.ffusion.services.FFServer.OFX200;

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
import com.ffusion.msgbroker.generated.MessageBroker.api.OFX200.Callback.IOFX200Callback;
import com.ffusion.msgbroker.generated.MessageBroker.api.OFX200.Callback.OFX200CallbackBean;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumCorrectiveActEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumCurrencyEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumTransactionEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumXferStatusEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBCCAcctFromUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBCCAcctToUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankAcctFromAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankMsgsRqUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankMsgsRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankTranListAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeCCAcctFromAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeCCStmtRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeCCStmtRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeCCStmtTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeCCStmtTrnRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeCCStmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeCCStmtTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeCreditCardMsgsRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeCreditCardMsgsRqV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIncTranAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraCanRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraCanRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraModRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraModRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraSyncRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraSyncRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRqUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeOFXRequest;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeOFXRqMsgSetsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraCanRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraCanRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraModRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraModRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraRsUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraSyncRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraSyncRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRqUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecurrInstAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStmtRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStmtRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStmtTrnAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStmtTrnCorrectCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStmtTrnNamePayeeUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStmtTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStmtTrnRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStmtTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeSyncRqCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTokenRqUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTrnRqCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTrnRsCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeXferInfoAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeXferPrcStsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.ValueSetCurrencyEnum;
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
  private static final String bI = "MaxTransactionDays";
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
  
  private void d()
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
    d();
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
    d();
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
    d();
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
    d();
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
    d();
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
    d();
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
    d();
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
    d();
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
    d();
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
    d();
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
      this.rqmes.OFXRequest.BankMsgsRqV1Exists = true;
      this.rqmes.OFXRequest.BankMsgsRqV1 = new TypeBankMsgsRqV1Aggregate();
      localObject1 = new TypeBankMsgsRqUn[1];
      this.rqmes.OFXRequest.BankMsgsRqV1.BankMsgsRqUn = ((TypeBankMsgsRqUn[])localObject1);
      localObject1[0] = new TypeBankMsgsRqUn();
      localObject1[0].__memberName = "StmtTrnRq";
      localObject2 = new TypeStmtTrnRqV1Aggregate();
      localObject1[0].StmtTrnRq = ((TypeStmtTrnRqV1Aggregate)localObject2);
      ((TypeStmtTrnRqV1Aggregate)localObject2).TrnRqCm = new TypeTrnRqCm();
      ((TypeStmtTrnRqV1Aggregate)localObject2).TrnRqCm.TrnUID = getUniqueSeqNum();
      ((TypeStmtTrnRqV1Aggregate)localObject2).StmtRq = new TypeStmtRqAggregate();
      ((TypeStmtTrnRqV1Aggregate)localObject2).StmtRq.BankAcctFrom = new TypeBankAcctFromAggregate();
      formatBANKACCTFROM(((TypeStmtTrnRqV1Aggregate)localObject2).StmtRq.BankAcctFrom, paramAccount);
      ((TypeStmtTrnRqV1Aggregate)localObject2).StmtRq.IncTranExists = true;
      ((TypeStmtTrnRqV1Aggregate)localObject2).StmtRq.IncTran = localTypeIncTranAggregate;
    }
    else
    {
      this.rqmes.OFXRequest.CreditCardMsgsRqV1Exists = true;
      this.rqmes.OFXRequest.CreditCardMsgsRqV1 = new TypeCreditCardMsgsRqV1Aggregate();
      localObject1 = new TypeCreditCardMsgsRqV1Un[1];
      this.rqmes.OFXRequest.CreditCardMsgsRqV1.CreditCardMsgsRqV1Un = ((TypeCreditCardMsgsRqV1Un[])localObject1);
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
  
  private TypeTrnRqCm jdMethod_if(Transfer paramTransfer)
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
    localTypeIntraTrnRqV1Aggregate.TrnRqCm = jdMethod_if(paramTransfer);
    Double localDouble = new Double(paramTransfer.getAmountValue().doubleValue());
    localTypeIntraTrnRqV1Aggregate.IntraTrnRqUn.IntraRq = new TypeIntraRqAggregate();
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
        localTypeIntraTrnRqV1Aggregate.IntraTrnRqUn.IntraRq.CurDef = localEnumCurrencyEnum;
        localTypeIntraTrnRqV1Aggregate.IntraTrnRqUn.IntraRq.CurDefExists = true;
      }
    }
    localTypeIntraTrnRqV1Aggregate.IntraTrnRqUn.IntraRq.XferInfo = formatXFERINFO(localDouble.doubleValue(), this.fromAccount, this.toAccount, localDateTime1);
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
    localTypeIntraTrnRqV1Aggregate.TrnRqCm = jdMethod_if(paramTransfer);
    localTypeIntraTrnRqV1Aggregate.IntraTrnRqUn.IntraCanRq = new TypeIntraCanRqAggregate();
    localTypeIntraTrnRqV1Aggregate.IntraTrnRqUn.IntraCanRq.SrvrTID = paramTransfer.getID();
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
    localTypeIntraTrnRqV1Aggregate.TrnRqCm = jdMethod_if(paramTransfer);
    localTypeIntraTrnRqV1Aggregate.IntraTrnRqUn.IntraModRq = new TypeIntraModRqAggregate();
    localTypeIntraTrnRqV1Aggregate.IntraTrnRqUn.IntraModRq.SrvrTID = paramTransfer.getID();
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
        localTypeIntraTrnRqV1Aggregate.IntraTrnRqUn.IntraModRq.CurDef = localEnumCurrencyEnum;
        localTypeIntraTrnRqV1Aggregate.IntraTrnRqUn.IntraModRq.CurDefExists = true;
      }
    }
    localTypeIntraTrnRqV1Aggregate.IntraTrnRqUn.IntraModRq.XferInfo = formatXFERINFO(localDouble.doubleValue(), this.fromAccount, this.toAccount, paramTransfer.getDateValue());
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
    localTypeRecIntraTrnRqV1Aggregate.TrnRqCm = jdMethod_if(paramRecTransfer);
    localTypeRecIntraTrnRqV1Aggregate.RecIntraTrnRqUn.RecIntraRq = new TypeRecIntraRqAggregate();
    Double localDouble = new Double(paramRecTransfer.getAmountValue().doubleValue());
    localTypeRecIntraTrnRqV1Aggregate.RecIntraTrnRqUn.RecIntraRq.IntraRq = new TypeIntraRqAggregate();
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
        localTypeRecIntraTrnRqV1Aggregate.RecIntraTrnRqUn.RecIntraRq.IntraRq.CurDef = localEnumCurrencyEnum;
        localTypeRecIntraTrnRqV1Aggregate.RecIntraTrnRqUn.RecIntraRq.IntraRq.CurDefExists = true;
      }
    }
    localTypeRecIntraTrnRqV1Aggregate.RecIntraTrnRqUn.RecIntraRq.IntraRq.XferInfo = formatXFERINFO(localDouble.doubleValue(), this.fromAccount, this.toAccount, paramRecTransfer.getDateValue());
    localTypeRecIntraTrnRqV1Aggregate.RecIntraTrnRqUn.RecIntraRq.RecurrInst = formatRECURRINST(paramRecTransfer.getFrequencyValue(), paramRecTransfer.getNumberTransfersValue());
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
    localTypeRecIntraTrnRqV1Aggregate.TrnRqCm = jdMethod_if(paramRecTransfer);
    localTypeRecIntraTrnRqV1Aggregate.RecIntraTrnRqUn.RecIntraCanRq = new TypeRecIntraCanRqAggregate();
    localTypeRecIntraTrnRqV1Aggregate.RecIntraTrnRqUn.RecIntraCanRq.CanPending = true;
    localTypeRecIntraTrnRqV1Aggregate.RecIntraTrnRqUn.RecIntraCanRq.RecSrvrTID = paramRecTransfer.getRecTransferID();
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
    localTypeRecIntraTrnRqV1Aggregate.TrnRqCm = jdMethod_if(paramRecTransfer);
    localTypeRecIntraTrnRqV1Aggregate.RecIntraTrnRqUn.RecIntraModRq = new TypeRecIntraModRqAggregate();
    localTypeRecIntraTrnRqV1Aggregate.RecIntraTrnRqUn.RecIntraModRq.ModPending = true;
    localTypeRecIntraTrnRqV1Aggregate.RecIntraTrnRqUn.RecIntraModRq.RecSrvrTID = paramRecTransfer.getRecTransferID();
    Double localDouble = new Double(paramRecTransfer.getAmountValue().doubleValue());
    localTypeRecIntraTrnRqV1Aggregate.RecIntraTrnRqUn.RecIntraModRq.IntraRq = new TypeIntraRqAggregate();
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
        localTypeRecIntraTrnRqV1Aggregate.RecIntraTrnRqUn.RecIntraModRq.IntraRq.CurDef = localEnumCurrencyEnum;
        localTypeRecIntraTrnRqV1Aggregate.RecIntraTrnRqUn.RecIntraModRq.IntraRq.CurDefExists = true;
      }
    }
    localTypeRecIntraTrnRqV1Aggregate.RecIntraTrnRqUn.RecIntraModRq.IntraRq.XferInfo = formatXFERINFO(localDouble.doubleValue(), this.fromAccount, this.toAccount, paramRecTransfer.getDateValue());
    localTypeRecIntraTrnRqV1Aggregate.RecIntraTrnRqUn.RecIntraModRq.RecurrInst = formatRECURRINST(paramRecTransfer.getFrequencyValue(), paramRecTransfer.getNumberTransfersValue());
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
      if (this.account.isFilterable("TransferFrom")) {
        i++;
      }
    }
    if (i > 0)
    {
      this.rqmes.OFXRequest.BankMsgsRqV1Exists = true;
      this.rqmes.OFXRequest.BankMsgsRqV1 = new TypeBankMsgsRqV1Aggregate();
      this.rqmes.OFXRequest.BankMsgsRqV1.BankMsgsRqUn = new TypeBankMsgsRqUn[i];
      i = 0;
      for (j = 0; j < this.accounts.size(); j++)
      {
        this.account = ((Account)this.accounts.get(j));
        if (this.account.isFilterable("TransferFrom"))
        {
          TypeBankMsgsRqUn localTypeBankMsgsRqUn = new TypeBankMsgsRqUn();
          this.rqmes.OFXRequest.BankMsgsRqV1.BankMsgsRqUn[i] = localTypeBankMsgsRqUn;
          localTypeBankMsgsRqUn.__memberName = "IntraSyncRq";
          localTypeBankMsgsRqUn.IntraSyncRq = new TypeIntraSyncRqV1Aggregate();
          localTypeBankMsgsRqUn.IntraSyncRq.BCCAcctFromUn = new TypeBCCAcctFromUn();
          formatFromAccount(localTypeBankMsgsRqUn.IntraSyncRq.BCCAcctFromUn, this.account);
          localTypeBankMsgsRqUn.IntraSyncRq.SyncRqCm = new TypeSyncRqCm();
          localTypeBankMsgsRqUn.IntraSyncRq.SyncRqCm.RejectIfMissing = false;
          TypeTokenRqUn localTypeTokenRqUn = new TypeTokenRqUn();
          localTypeBankMsgsRqUn.IntraSyncRq.SyncRqCm.TokenRqUn = localTypeTokenRqUn;
          localTypeTokenRqUn.__memberName = "Token";
          if ((this.m_TransferToken != null) && (this.m_TransferToken.length() > 0)) {
            localTypeTokenRqUn.Token = this.m_TransferToken;
          } else {
            localTypeTokenRqUn.Token = "0";
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
      if (this.account.isFilterable("TransferFrom")) {
        i++;
      }
    }
    if (i > 0)
    {
      this.rqmes.OFXRequest.BankMsgsRqV1 = new TypeBankMsgsRqV1Aggregate();
      this.rqmes.OFXRequest.BankMsgsRqV1Exists = true;
      this.rqmes.OFXRequest.BankMsgsRqV1.BankMsgsRqUn = new TypeBankMsgsRqUn[i];
      i = 0;
      for (j = 0; j < this.accounts.size(); j++)
      {
        this.account = ((Account)this.accounts.get(j));
        if (this.account.isFilterable("TransferFrom"))
        {
          TypeBankMsgsRqUn localTypeBankMsgsRqUn = new TypeBankMsgsRqUn();
          this.rqmes.OFXRequest.BankMsgsRqV1.BankMsgsRqUn[i] = localTypeBankMsgsRqUn;
          localTypeBankMsgsRqUn.__memberName = "RecIntraSyncRq";
          localTypeBankMsgsRqUn.RecIntraSyncRq = new TypeRecIntraSyncRqV1Aggregate();
          localTypeBankMsgsRqUn.RecIntraSyncRq.BCCAcctFromUn = new TypeBCCAcctFromUn();
          formatFromAccount(localTypeBankMsgsRqUn.RecIntraSyncRq.BCCAcctFromUn, this.account);
          localTypeBankMsgsRqUn.RecIntraSyncRq.SyncRqCm = new TypeSyncRqCm();
          localTypeBankMsgsRqUn.RecIntraSyncRq.SyncRqCm.RejectIfMissing = false;
          TypeTokenRqUn localTypeTokenRqUn = new TypeTokenRqUn();
          localTypeBankMsgsRqUn.RecIntraSyncRq.SyncRqCm.TokenRqUn = localTypeTokenRqUn;
          localTypeTokenRqUn.__memberName = "Token";
          if ((this.m_RecTransferToken != null) && (this.m_RecTransferToken.length() > 0)) {
            localTypeTokenRqUn.Token = this.m_RecTransferToken;
          } else {
            localTypeTokenRqUn.Token = "0";
          }
          i++;
        }
      }
    }
  }
  
  protected TypeIntraTrnRqV1Aggregate formatINTRATRNRQ(TypeOFXRqMsgSetsAggregate paramTypeOFXRqMsgSetsAggregate, String paramString)
  {
    paramTypeOFXRqMsgSetsAggregate.BankMsgsRqV1 = new TypeBankMsgsRqV1Aggregate();
    paramTypeOFXRqMsgSetsAggregate.BankMsgsRqV1Exists = true;
    paramTypeOFXRqMsgSetsAggregate.BankMsgsRqV1.BankMsgsRqUn = new TypeBankMsgsRqUn[1];
    paramTypeOFXRqMsgSetsAggregate.BankMsgsRqV1.BankMsgsRqUn[0] = new TypeBankMsgsRqUn();
    paramTypeOFXRqMsgSetsAggregate.BankMsgsRqV1.BankMsgsRqUn[0].__memberName = "IntraTrnRq";
    TypeIntraTrnRqV1Aggregate localTypeIntraTrnRqV1Aggregate = new TypeIntraTrnRqV1Aggregate();
    paramTypeOFXRqMsgSetsAggregate.BankMsgsRqV1.BankMsgsRqUn[0].IntraTrnRq = localTypeIntraTrnRqV1Aggregate;
    localTypeIntraTrnRqV1Aggregate.IntraTrnRqUn = new TypeIntraTrnRqUn();
    localTypeIntraTrnRqV1Aggregate.IntraTrnRqUn.__memberName = paramString;
    return localTypeIntraTrnRqV1Aggregate;
  }
  
  protected TypeXferInfoAggregate formatXFERINFO(double paramDouble, Account paramAccount1, Account paramAccount2, DateTime paramDateTime)
  {
    TypeXferInfoAggregate localTypeXferInfoAggregate = new TypeXferInfoAggregate();
    localTypeXferInfoAggregate.TrnAmt = paramDouble;
    localTypeXferInfoAggregate.BCCAcctFromUn = new TypeBCCAcctFromUn();
    formatFromAccount(localTypeXferInfoAggregate.BCCAcctFromUn, paramAccount1);
    localTypeXferInfoAggregate.BCCAcctToUn = new TypeBCCAcctToUn();
    formatToAccount(localTypeXferInfoAggregate.BCCAcctToUn, paramAccount2);
    if (paramDateTime != null)
    {
      localTypeXferInfoAggregate.DtDueExists = true;
      localTypeXferInfoAggregate.DtDue = setDate(paramDateTime, "yyyyMMdd");
    }
    return localTypeXferInfoAggregate;
  }
  
  protected TypeRecIntraTrnRqV1Aggregate formatRECINTRATRNRQ(TypeOFXRqMsgSetsAggregate paramTypeOFXRqMsgSetsAggregate, String paramString)
  {
    paramTypeOFXRqMsgSetsAggregate.BankMsgsRqV1 = new TypeBankMsgsRqV1Aggregate();
    paramTypeOFXRqMsgSetsAggregate.BankMsgsRqV1Exists = true;
    paramTypeOFXRqMsgSetsAggregate.BankMsgsRqV1.BankMsgsRqUn = new TypeBankMsgsRqUn[1];
    paramTypeOFXRqMsgSetsAggregate.BankMsgsRqV1.BankMsgsRqUn[0] = new TypeBankMsgsRqUn();
    paramTypeOFXRqMsgSetsAggregate.BankMsgsRqV1.BankMsgsRqUn[0].__memberName = "RecIntraTrnRq";
    TypeRecIntraTrnRqV1Aggregate localTypeRecIntraTrnRqV1Aggregate = new TypeRecIntraTrnRqV1Aggregate();
    paramTypeOFXRqMsgSetsAggregate.BankMsgsRqV1.BankMsgsRqUn[0].RecIntraTrnRq = localTypeRecIntraTrnRqV1Aggregate;
    localTypeRecIntraTrnRqV1Aggregate.RecIntraTrnRqUn = new TypeRecIntraTrnRqUn();
    localTypeRecIntraTrnRqV1Aggregate.RecIntraTrnRqUn.__memberName = paramString;
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
      processTransactionsInSignOnMsgsRqV1(localTypeOFXRequest.OFXRequest.SignOnMsgsRqV1);
      if (localTypeOFXRequest.OFXRequest.SignUpMsgsRqV1Exists) {
        processTransactionsInSignUpMsgsRqV1(localTypeOFXRequest.OFXRequest.SignUpMsgsRqV1);
      }
      if (localTypeOFXRequest.OFXRequest.BankMsgsRqV1Exists) {
        processTransactionsInBankMsgsRqV1(localTypeOFXRequest.OFXRequest.BankMsgsRqV1);
      }
      if (localTypeOFXRequest.OFXRequest.CreditCardMsgsRqV1Exists) {
        processTransactionsInCreditCardMsgsRqV1(localTypeOFXRequest.OFXRequest.CreditCardMsgsRqV1);
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
    for (int i = 0; i < paramTypeBankMsgsRqV1Aggregate.BankMsgsRqUn.length; i++)
    {
      Object localObject1;
      Object localObject2;
      if (paramTypeBankMsgsRqV1Aggregate.BankMsgsRqUn[i].__memberName.equals("StmtTrnRq"))
      {
        localObject1 = new TypeStmtTrnRqV1(paramTypeBankMsgsRqV1Aggregate.BankMsgsRqUn[i].StmtTrnRq);
        localObject2 = null;
        localObject2 = processStmtTrnRqV1((TypeStmtTrnRqV1)localObject1);
        if (localObject2 != null) {
          processStmtTrnRs(((TypeStmtTrnRsV1)localObject2).StmtTrnRs);
        }
      }
      if (paramTypeBankMsgsRqV1Aggregate.BankMsgsRqUn[i].__memberName.equals("IntraTrnRq"))
      {
        localObject1 = new TypeIntraTrnRqV1(paramTypeBankMsgsRqV1Aggregate.BankMsgsRqUn[i].IntraTrnRq);
        localObject2 = null;
        localObject2 = processIntraTrnRqV1((TypeIntraTrnRqV1)localObject1);
        if (localObject2 != null) {
          processIntraTrnRs(((TypeIntraTrnRsV1)localObject2).IntraTrnRs);
        }
      }
      if (paramTypeBankMsgsRqV1Aggregate.BankMsgsRqUn[i].__memberName.equals("RecIntraTrnRq"))
      {
        localObject1 = new TypeRecIntraTrnRqV1(paramTypeBankMsgsRqV1Aggregate.BankMsgsRqUn[i].RecIntraTrnRq);
        localObject2 = null;
        localObject2 = processRecIntraTrnRqV1((TypeRecIntraTrnRqV1)localObject1);
        if (localObject2 != null) {
          processRecIntraTrnRs(((TypeRecIntraTrnRsV1)localObject2).RecIntraTrnRs);
        }
      }
      if (paramTypeBankMsgsRqV1Aggregate.BankMsgsRqUn[i].__memberName.equals("IntraSyncRq"))
      {
        localObject1 = new TypeIntraSyncRqV1(paramTypeBankMsgsRqV1Aggregate.BankMsgsRqUn[i].IntraSyncRq);
        localObject2 = null;
        localObject2 = processIntraSyncRqV1((TypeIntraSyncRqV1)localObject1);
        if (localObject2 != null) {
          processIntraSyncRs(((TypeIntraSyncRsV1)localObject2).IntraSyncRs);
        }
      }
      if (paramTypeBankMsgsRqV1Aggregate.BankMsgsRqUn[i].__memberName.equals("RecIntraSyncRq"))
      {
        localObject1 = new TypeRecIntraSyncRqV1(paramTypeBankMsgsRqV1Aggregate.BankMsgsRqUn[i].RecIntraSyncRq);
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
  
  protected void processStmtTrn(TypeStmtTrnAggregate[] paramArrayOfTypeStmtTrnAggregate)
  {
    if (this.transactions == null) {
      return;
    }
    for (int i = 0; i < paramArrayOfTypeStmtTrnAggregate.length; i++)
    {
      String str1 = paramArrayOfTypeStmtTrnAggregate[i].FITID;
      this.transaction = ((Transaction)this.transactions.getFirstByFilter("ID=" + str1));
      if (this.transaction == null)
      {
        this.transaction = this.transactions.create();
        this.transaction.setID(str1);
      }
      if (this.transaction != null)
      {
        int j = 0;
        if ((!paramArrayOfTypeStmtTrnAggregate[i].BCCAcctToUnExists) || (paramArrayOfTypeStmtTrnAggregate[i].CheckNumExists)) {
          this.transaction.setReferenceNumber(paramArrayOfTypeStmtTrnAggregate[i].CheckNum);
        }
        if (paramArrayOfTypeStmtTrnAggregate[i].RefNumExists) {
          if ((this.transaction.getReferenceNumber() != null) && (this.transaction.getReferenceNumber().length() > 0)) {
            this.transaction.setReferenceNumber(this.transaction.getReferenceNumber() + " " + paramArrayOfTypeStmtTrnAggregate[i].RefNum);
          } else {
            this.transaction.setReferenceNumber(paramArrayOfTypeStmtTrnAggregate[i].RefNum);
          }
        }
        if (this.transaction.getReferenceNumber() == null) {
          this.transaction.setReferenceNumber(str1);
        }
        if (paramArrayOfTypeStmtTrnAggregate[i].MemoExists) {
          this.transaction.setMemo(paramArrayOfTypeStmtTrnAggregate[i].Memo);
        }
        this.transaction.setDate(getDate(paramArrayOfTypeStmtTrnAggregate[i].DtPosted));
        this.transaction.setAmount(new BigDecimal(paramArrayOfTypeStmtTrnAggregate[i].TrnAmt));
        EnumTransactionEnum localEnumTransactionEnum = paramArrayOfTypeStmtTrnAggregate[i].TrnType;
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
        if (paramArrayOfTypeStmtTrnAggregate[i].StmtTrnNamePayeeUnExists) {
          if (paramArrayOfTypeStmtTrnAggregate[i].StmtTrnNamePayeeUn.__memberName.equals("Name")) {
            this.transaction.setDescription(paramArrayOfTypeStmtTrnAggregate[i].StmtTrnNamePayeeUn.Name);
          } else if (paramArrayOfTypeStmtTrnAggregate[i].StmtTrnNamePayeeUn.__memberName.equals("Payee")) {
            this.transaction.setDescription(paramArrayOfTypeStmtTrnAggregate[i].StmtTrnNamePayeeUn.Payee.Name);
          }
        }
        if (paramArrayOfTypeStmtTrnAggregate[i].StmtTrnCorrectCmExists) {
          if (paramArrayOfTypeStmtTrnAggregate[i].StmtTrnCorrectCm.CorrectAction.value() == 0)
          {
            this.transactions.remove(this.transaction);
            this.transaction = null;
          }
          else if ((paramArrayOfTypeStmtTrnAggregate[i].StmtTrnCorrectCm.CorrectAction.value() == 1) && (paramArrayOfTypeStmtTrnAggregate[i].StmtTrnCorrectCm.CorrectFITIDExists))
          {
            String str2 = paramArrayOfTypeStmtTrnAggregate[i].StmtTrnCorrectCm.CorrectFITID;
            if (this.transactions.getFirstByFilter("ID=" + str2) == null) {
              this.transaction.setID(str2);
            }
          }
        }
        if ((!paramArrayOfTypeStmtTrnAggregate[i].StmtTrnNameServerCmExists) || ((this.debugService) && (this.transaction != null))) {
          DebugLog.log(Level.INFO, XMLHandler.format(this.transaction.toXML()));
        }
      }
    }
  }
  
  protected void processIntraTrnRs(TypeIntraTrnRsV1Aggregate paramTypeIntraTrnRsV1Aggregate)
  {
    processSTATUS(paramTypeIntraTrnRsV1Aggregate.TrnRsCm.Status);
    String str1 = null;
    String str2 = paramTypeIntraTrnRsV1Aggregate.TrnRsCm.TrnUID;
    TypeXferInfoAggregate localTypeXferInfoAggregate = null;
    TypeXferPrcStsAggregate localTypeXferPrcStsAggregate = null;
    String str3 = null;
    String str4 = null;
    if (paramTypeIntraTrnRsV1Aggregate.IntraTrnRsUnExists)
    {
      if (paramTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.__memberName.equals("IntraRs"))
      {
        str1 = paramTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.IntraRs.SrvrTID;
        localTypeXferInfoAggregate = paramTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.IntraRs.XferInfo;
        if (paramTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.IntraRs.CurDef != null) {
          try
          {
            str4 = ValueSetCurrencyEnum.getValue(paramTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.IntraRs.CurDef.value());
          }
          catch (Throwable localThrowable1) {}
        }
        if (paramTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.IntraRs.RecSrvrTIDExists) {
          str3 = paramTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.IntraRs.RecSrvrTID;
        }
        if (paramTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.IntraRs.XferPrcStsExists) {
          localTypeXferPrcStsAggregate = paramTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.IntraRs.XferPrcSts;
        }
      }
      else if (paramTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.__memberName.equals("IntraModRs"))
      {
        str1 = paramTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.IntraModRs.SrvrTID;
        localTypeXferInfoAggregate = paramTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.IntraModRs.XferInfo;
        if (paramTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.IntraModRs.CurDef != null) {
          try
          {
            str4 = ValueSetCurrencyEnum.getValue(paramTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.IntraModRs.CurDef.value());
          }
          catch (Throwable localThrowable2) {}
        }
        if (paramTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.IntraModRs.XferPrcStsExists) {
          localTypeXferPrcStsAggregate = paramTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.IntraModRs.XferPrcSts;
        }
      }
      else if (paramTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.__memberName.equals("IntraCanRs"))
      {
        str1 = paramTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.IntraCanRs.SrvrTID;
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
        if (paramTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.__memberName.equals("IntraCanRs"))
        {
          this.transfer.setStatus(3);
        }
        else
        {
          if ((this.status == 0) && (this.transfer.getStatus() == 1)) {
            this.transfer.setStatus(2);
          }
          if (localTypeXferInfoAggregate != null) {
            processXFERINFO(this.transfer, this.accounts, localTypeXferInfoAggregate);
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
    processSTATUS(paramTypeRecIntraTrnRsV1Aggregate.TrnRsCm.Status);
    String str1 = null;
    String str2 = paramTypeRecIntraTrnRsV1Aggregate.TrnRsCm.TrnUID;
    TypeRecurrInstAggregate localTypeRecurrInstAggregate = null;
    TypeXferPrcStsAggregate localTypeXferPrcStsAggregate = null;
    TypeXferInfoAggregate localTypeXferInfoAggregate = null;
    String str3 = null;
    if (paramTypeRecIntraTrnRsV1Aggregate.RecIntraRsUnExists)
    {
      if (paramTypeRecIntraTrnRsV1Aggregate.RecIntraRsUn.__memberName.equals("RecIntraRs"))
      {
        str1 = paramTypeRecIntraTrnRsV1Aggregate.RecIntraRsUn.RecIntraRs.RecSrvrTID;
        localTypeRecurrInstAggregate = paramTypeRecIntraTrnRsV1Aggregate.RecIntraRsUn.RecIntraRs.RecurrInst;
        if (paramTypeRecIntraTrnRsV1Aggregate.RecIntraRsUn.RecIntraRs.IntraRs.CurDef != null) {
          try
          {
            str3 = ValueSetCurrencyEnum.getValue(paramTypeRecIntraTrnRsV1Aggregate.RecIntraRsUn.RecIntraRs.IntraRs.CurDef.value());
          }
          catch (Throwable localThrowable1) {}
        }
        if (paramTypeRecIntraTrnRsV1Aggregate.RecIntraRsUn.RecIntraRs.IntraRs.XferPrcStsExists) {
          localTypeXferPrcStsAggregate = paramTypeRecIntraTrnRsV1Aggregate.RecIntraRsUn.RecIntraRs.IntraRs.XferPrcSts;
        }
        localTypeXferInfoAggregate = paramTypeRecIntraTrnRsV1Aggregate.RecIntraRsUn.RecIntraRs.IntraRs.XferInfo;
      }
      else if (paramTypeRecIntraTrnRsV1Aggregate.RecIntraRsUn.__memberName.equals("RecIntraModRs"))
      {
        str1 = paramTypeRecIntraTrnRsV1Aggregate.RecIntraRsUn.RecIntraModRs.RecSrvrTID;
        localTypeRecurrInstAggregate = paramTypeRecIntraTrnRsV1Aggregate.RecIntraRsUn.RecIntraModRs.RecurrInst;
        if (paramTypeRecIntraTrnRsV1Aggregate.RecIntraRsUn.RecIntraModRs.IntraRs.CurDef != null) {
          try
          {
            str3 = ValueSetCurrencyEnum.getValue(paramTypeRecIntraTrnRsV1Aggregate.RecIntraRsUn.RecIntraModRs.IntraRs.CurDef.value());
          }
          catch (Throwable localThrowable2) {}
        }
        if (paramTypeRecIntraTrnRsV1Aggregate.RecIntraRsUn.RecIntraModRs.IntraRs.XferPrcStsExists) {
          localTypeXferPrcStsAggregate = paramTypeRecIntraTrnRsV1Aggregate.RecIntraRsUn.RecIntraModRs.IntraRs.XferPrcSts;
        }
        localTypeXferInfoAggregate = paramTypeRecIntraTrnRsV1Aggregate.RecIntraRsUn.RecIntraModRs.IntraRs.XferInfo;
      }
      else if (paramTypeRecIntraTrnRsV1Aggregate.RecIntraRsUn.__memberName.equals("RecIntraCanRs"))
      {
        str1 = paramTypeRecIntraTrnRsV1Aggregate.RecIntraRsUn.RecIntraCanRs.RecSrvrTID;
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
        if (paramTypeRecIntraTrnRsV1Aggregate.RecIntraRsUn.__memberName.equals("RecIntraCanRs"))
        {
          this.rectransfer.setStatus(3);
        }
        else
        {
          if ((this.status == 0) && (this.rectransfer.getStatus() == 1)) {
            this.rectransfer.setStatus(2);
          }
          if (localTypeXferInfoAggregate != null) {
            processXFERINFO(this.rectransfer, this.accounts, localTypeXferInfoAggregate);
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
  
  protected void processXFERINFO(Transfer paramTransfer, Accounts paramAccounts, TypeXferInfoAggregate paramTypeXferInfoAggregate)
  {
    paramTransfer.setAmount(new BigDecimal(paramTypeXferInfoAggregate.TrnAmt));
    if (paramAccounts != null)
    {
      paramTransfer.setFromAccount(processBCCAcctFromUn(paramTypeXferInfoAggregate.BCCAcctFromUn, paramAccounts));
      paramTransfer.setToAccount(processBCCAcctToUn(paramTypeXferInfoAggregate.BCCAcctToUn, paramAccounts));
    }
    else
    {
      paramTransfer.setFromAccount(this.fromAccount);
      paramTransfer.setToAccount(this.toAccount);
    }
    if (paramTypeXferInfoAggregate.DtDueExists) {
      paramTransfer.setDate(getDate(paramTypeXferInfoAggregate.DtDue));
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
 * Qualified Name:     com.ffusion.services.FFServer.OFX200.Banking
 * JD-Core Version:    0.7.0.1
 */