package com.ffusion.services.FFServer.OFX200;

import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.messages.Message;
import com.ffusion.beans.messages.Messages;
import com.ffusion.msgbroker.generated.MessageBroker.api.OFX200.Callback.IOFX200Callback;
import com.ffusion.msgbroker.generated.MessageBroker.api.OFX200.Callback.OFX200CallbackBean;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBCCAcctFromUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankMailRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankMailRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankMailRsUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankMailSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankMailSyncRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankMailSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankMailSyncRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankMailTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankMailTrnRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankMailTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankMailTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankMsgsRqUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankMsgsRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBillPayMsgsRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBillPayMsgsRqV1MessageUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeEmailMsgsRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeEmailMsgsRqV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeMailAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeMailRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeMailRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeMailSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeMailSyncRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeMailSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeMailSyncRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeMailTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeMailTrnRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeMailTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeMailTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeOFXRequest;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeOFXRqMsgSetsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtInfoAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtMailRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtMailRqCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtMailRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtMailSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtMailSyncRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtMailSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtMailSyncRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtMailTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtMailTrnRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtMailTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtMailTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeSyncRqCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTokenRqUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTrnRqCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTrnRsCm;
import com.ffusion.services.Messaging2;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.util.HashMap;
import java.util.logging.Level;

public class Messaging
  extends BillPayBase
  implements BankingDefines, BillPayDefines, Messaging2
{
  protected Messages messages;
  protected Message message;
  
  public int initialize(String paramString)
  {
    return initialize(paramString, new Base.a(this));
  }
  
  public String getSettings()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "MESSAGINGSERVICE");
    localStringBuffer.append(super.getSettings());
    XMLHandler.appendEndTag(localStringBuffer, "MESSAGINGSERVICE");
    return localStringBuffer.toString();
  }
  
  public void setUserName(String paramString)
  {
    setUserID(paramString);
  }
  
  public void setPassword(String paramString)
  {
    this.password = paramString;
  }
  
  public int getMessageList(Object paramObject, Messages paramMessages)
  {
    return 2;
  }
  
  public int deleteMessage(Object paramObject)
  {
    return 2;
  }
  
  public int getNumberOfUnreadMessages(Object paramObject)
  {
    return 2;
  }
  
  public int markMessageAsRead(Object paramObject)
  {
    return 2;
  }
  
  private String b()
  {
    String str = this.message.getMemo();
    if ((str == null) || (str.length() == 0)) {
      str = this.message.getSubject();
    }
    int i = str.length();
    StringBuffer localStringBuffer = new StringBuffer();
    for (int j = 0; j < i; j++)
    {
      char c = str.charAt(j);
      switch (c)
      {
      case '\n': 
        localStringBuffer.append(' ');
        break;
      default: 
        localStringBuffer.append(c);
      }
    }
    return localStringBuffer.toString();
  }
  
  private void c()
  {
    this.messages = null;
    this.message = null;
    this.accounts = null;
    this.account = null;
    this.payees = null;
    this.payee = null;
    this.payments = null;
    this.payment = null;
  }
  
  public int getMessages(Messages paramMessages)
  {
    int i = getGeneralMail(paramMessages);
    return jdMethod_try(i);
  }
  
  public int getMessages(Object paramObject, Messages paramMessages)
  {
    int i;
    if ((paramObject instanceof Accounts))
    {
      i = getBankMail(paramMessages, this.accounts);
      i = jdMethod_try(i);
    }
    else if (paramObject == null)
    {
      i = getPaymentMail(paramMessages);
      i = jdMethod_try(i);
    }
    else
    {
      i = 2;
    }
    return i;
  }
  
  private int jdMethod_try(int paramInt)
  {
    if (paramInt == 0)
    {
      if (this.status != 0)
      {
        logError(mapError(this.status));
        paramInt = this.lastError;
      }
    }
    else {
      paramInt = this.lastError;
    }
    return paramInt;
  }
  
  public int sendMessage(Message paramMessage)
  {
    this.message = paramMessage;
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".sendMessage:");
    }
    formatSendMailRequest();
    processOFXRequest();
    c();
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
  
  public int sendMessage(Message paramMessage, Object paramObject)
  {
    int i = 2;
    if ((paramObject instanceof Account)) {
      i = sendBankMail(paramMessage, (Account)paramObject);
    } else if ((paramObject instanceof Payment)) {
      i = sendPaymentMail(paramMessage, (Payment)paramObject);
    }
    return i;
  }
  
  public int sendBankMail(Message paramMessage, Account paramAccount)
  {
    this.message = paramMessage;
    this.account = paramAccount;
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".sendBankMail:");
    }
    formatSendBankMailRequest();
    processOFXRequest();
    c();
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
  
  protected int getGeneralMail(Messages paramMessages)
  {
    this.messages = paramMessages;
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".getGeneralMail:");
    }
    formatGetGeneralMailRequest();
    processOFXRequest();
    c();
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
  
  protected int getBankMail(Messages paramMessages, Accounts paramAccounts)
  {
    this.accounts = paramAccounts;
    this.messages = paramMessages;
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".getBankMail:");
    }
    formatGetBankMailRequest();
    processOFXRequest();
    c();
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
  
  public int getPaymentMail(Messages paramMessages)
  {
    this.messages = paramMessages;
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".getPaymentMail:");
    }
    formatGetPaymentMailRequest();
    processOFXRequest();
    c();
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
  
  public int sendPaymentMail(Message paramMessage, Payment paramPayment)
  {
    this.account = paramPayment.getAccount();
    this.payment = paramPayment;
    this.message = paramMessage;
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".sendPaymentMail:");
    }
    formatSendPaymentMailRequest();
    processOFXRequest();
    c();
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
  
  protected void formatMail(TypeMailAggregate paramTypeMailAggregate)
  {
    if ((this.message.getSubject() == null) || (this.message.getSubject().length() == 0)) {
      this.message.setSubject("Mail Subject");
    }
    paramTypeMailAggregate.UserID = this.userID;
    paramTypeMailAggregate.DtCreated = setDate(this.message.getDateValue(), "yyyyMMdd");
    paramTypeMailAggregate.From = this.message.getFrom();
    paramTypeMailAggregate.To = this.message.getTo();
    paramTypeMailAggregate.Subject = this.message.getSubject();
    paramTypeMailAggregate.MsgBody = b();
    paramTypeMailAggregate.IncImages = false;
    paramTypeMailAggregate.UseHTML = false;
  }
  
  protected void formatGetGeneralMailRequest()
  {
    this.rqmes = new TypeOFXRequest();
    this.rqmes.OFXRequest = new TypeOFXRqMsgSetsAggregate();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatGetGeneralMailRequest:");
    }
    formatSIGNONMSGSRQV1(this.rqmes.OFXRequest);
    this.rqmes.OFXRequest.EmailMsgsRqV1Exists = true;
    this.rqmes.OFXRequest.EmailMsgsRqV1 = new TypeEmailMsgsRqV1Aggregate();
    this.rqmes.OFXRequest.EmailMsgsRqV1.EmailMsgsRqV1Un = new TypeEmailMsgsRqV1Un[1];
    this.rqmes.OFXRequest.EmailMsgsRqV1.EmailMsgsRqV1Un[0] = new TypeEmailMsgsRqV1Un();
    this.rqmes.OFXRequest.EmailMsgsRqV1.EmailMsgsRqV1Un[0].__memberName = "MailSyncRq";
    TypeMailSyncRqV1Aggregate localTypeMailSyncRqV1Aggregate = new TypeMailSyncRqV1Aggregate();
    this.rqmes.OFXRequest.EmailMsgsRqV1.EmailMsgsRqV1Un[0].MailSyncRq = localTypeMailSyncRqV1Aggregate;
    localTypeMailSyncRqV1Aggregate.IncImages = false;
    localTypeMailSyncRqV1Aggregate.UseHTML = false;
    localTypeMailSyncRqV1Aggregate.SyncRqCm = new TypeSyncRqCm();
    localTypeMailSyncRqV1Aggregate.SyncRqCm.RejectIfMissing = false;
    localTypeMailSyncRqV1Aggregate.SyncRqCm.TokenRqUn = new TypeTokenRqUn();
    localTypeMailSyncRqV1Aggregate.SyncRqCm.TokenRqUn.__memberName = "Refresh";
    localTypeMailSyncRqV1Aggregate.SyncRqCm.TokenRqUn.Refresh = true;
  }
  
  protected void formatSendMailRequest()
  {
    this.rqmes = new TypeOFXRequest();
    this.rqmes.OFXRequest = new TypeOFXRqMsgSetsAggregate();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatSendMailRequest:");
    }
    formatSIGNONMSGSRQV1(this.rqmes.OFXRequest);
    this.rqmes.OFXRequest.EmailMsgsRqV1Exists = true;
    this.rqmes.OFXRequest.EmailMsgsRqV1 = new TypeEmailMsgsRqV1Aggregate();
    this.rqmes.OFXRequest.EmailMsgsRqV1.EmailMsgsRqV1Un = new TypeEmailMsgsRqV1Un[1];
    this.rqmes.OFXRequest.EmailMsgsRqV1.EmailMsgsRqV1Un[0] = new TypeEmailMsgsRqV1Un();
    this.rqmes.OFXRequest.EmailMsgsRqV1.EmailMsgsRqV1Un[0].__memberName = "MailSyncRq";
    TypeMailTrnRqV1Aggregate localTypeMailTrnRqV1Aggregate = new TypeMailTrnRqV1Aggregate();
    this.rqmes.OFXRequest.EmailMsgsRqV1.EmailMsgsRqV1Un[0].MailTrnRq = localTypeMailTrnRqV1Aggregate;
    this.message.setID(getUniqueSeqNum());
    localTypeMailTrnRqV1Aggregate.TrnRqCm = new TypeTrnRqCm();
    localTypeMailTrnRqV1Aggregate.TrnRqCm.TrnUID = this.message.getID();
    localTypeMailTrnRqV1Aggregate.MailRq = new TypeMailRqAggregate();
    localTypeMailTrnRqV1Aggregate.MailRq.Mail = new TypeMailAggregate();
    formatMail(localTypeMailTrnRqV1Aggregate.MailRq.Mail);
  }
  
  protected void formatGetBankMailRequest()
  {
    this.rqmes = new TypeOFXRequest();
    this.rqmes.OFXRequest = new TypeOFXRqMsgSetsAggregate();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatGetBankMailRequest:");
    }
    formatSIGNONMSGSRQV1(this.rqmes.OFXRequest);
    if (this.accounts != null)
    {
      this.rqmes.OFXRequest.BankMsgsRqV1Exists = true;
      this.rqmes.OFXRequest.BankMsgsRqV1 = new TypeBankMsgsRqV1Aggregate();
      int i = this.accounts.size();
      this.rqmes.OFXRequest.BankMsgsRqV1.BankMsgsRqUn = new TypeBankMsgsRqUn[i];
      for (int j = 0; j < i; j++)
      {
        Account localAccount = (Account)this.accounts.get(j);
        if (localAccount != null)
        {
          this.rqmes.OFXRequest.BankMsgsRqV1.BankMsgsRqUn[j] = new TypeBankMsgsRqUn();
          this.rqmes.OFXRequest.BankMsgsRqV1.BankMsgsRqUn[j].__memberName = "BankMailSyncRq";
          TypeBankMailSyncRqV1Aggregate localTypeBankMailSyncRqV1Aggregate = new TypeBankMailSyncRqV1Aggregate();
          this.rqmes.OFXRequest.BankMsgsRqV1.BankMsgsRqUn[j].BankMailSyncRq = localTypeBankMailSyncRqV1Aggregate;
          localTypeBankMailSyncRqV1Aggregate.SyncRqCm = new TypeSyncRqCm();
          localTypeBankMailSyncRqV1Aggregate.SyncRqCm.RejectIfMissing = false;
          localTypeBankMailSyncRqV1Aggregate.SyncRqCm.TokenRqUn = new TypeTokenRqUn();
          localTypeBankMailSyncRqV1Aggregate.SyncRqCm.TokenRqUn.__memberName = "Refresh";
          localTypeBankMailSyncRqV1Aggregate.SyncRqCm.TokenRqUn.Refresh = true;
          localTypeBankMailSyncRqV1Aggregate.IncImages = false;
          localTypeBankMailSyncRqV1Aggregate.UseHTML = false;
          localTypeBankMailSyncRqV1Aggregate.BCCAcctFromUn = new TypeBCCAcctFromUn();
          formatFromAccount(localTypeBankMailSyncRqV1Aggregate.BCCAcctFromUn, localAccount);
        }
      }
    }
  }
  
  protected void formatSendBankMailRequest()
  {
    this.rqmes = new TypeOFXRequest();
    this.rqmes.OFXRequest = new TypeOFXRqMsgSetsAggregate();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatSendBankMailRequest:");
    }
    formatSIGNONMSGSRQV1(this.rqmes.OFXRequest);
    this.message.setID(getUniqueSeqNum());
    this.rqmes.OFXRequest.BankMsgsRqV1Exists = true;
    this.rqmes.OFXRequest.BankMsgsRqV1 = new TypeBankMsgsRqV1Aggregate();
    this.rqmes.OFXRequest.BankMsgsRqV1.BankMsgsRqUn = new TypeBankMsgsRqUn[1];
    this.rqmes.OFXRequest.BankMsgsRqV1.BankMsgsRqUn[0] = new TypeBankMsgsRqUn();
    this.rqmes.OFXRequest.BankMsgsRqV1.BankMsgsRqUn[0].__memberName = "BankMailTrnRq";
    TypeBankMailTrnRqV1Aggregate localTypeBankMailTrnRqV1Aggregate = new TypeBankMailTrnRqV1Aggregate();
    this.rqmes.OFXRequest.BankMsgsRqV1.BankMsgsRqUn[0].BankMailTrnRq = localTypeBankMailTrnRqV1Aggregate;
    localTypeBankMailTrnRqV1Aggregate.TrnRqCm = new TypeTrnRqCm();
    localTypeBankMailTrnRqV1Aggregate.TrnRqCm.TrnUID = this.message.getID();
    localTypeBankMailTrnRqV1Aggregate.BankMailRq = new TypeBankMailRqAggregate();
    localTypeBankMailTrnRqV1Aggregate.BankMailRq.BCCAcctFromUn = new TypeBCCAcctFromUn();
    formatFromAccount(localTypeBankMailTrnRqV1Aggregate.BankMailRq.BCCAcctFromUn, this.account);
    localTypeBankMailTrnRqV1Aggregate.BankMailRq.Mail = new TypeMailAggregate();
    formatMail(localTypeBankMailTrnRqV1Aggregate.BankMailRq.Mail);
  }
  
  protected void formatGetPaymentMailRequest()
  {
    this.rqmes = new TypeOFXRequest();
    this.rqmes.OFXRequest = new TypeOFXRqMsgSetsAggregate();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatGetPaymentMailRequest:");
    }
    formatSIGNONMSGSRQV1(this.rqmes.OFXRequest);
    TypeBillPayMsgsRqV1Aggregate localTypeBillPayMsgsRqV1Aggregate = formatBillPayMsgsRqV1(this.rqmes.OFXRequest);
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn = new TypeBillPayMsgsRqV1MessageUn[1];
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[0] = new TypeBillPayMsgsRqV1MessageUn();
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[0].__memberName = "PmtMailSyncRq";
    TypePmtMailSyncRqV1Aggregate localTypePmtMailSyncRqV1Aggregate = new TypePmtMailSyncRqV1Aggregate();
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[0].PmtMailSyncRq = localTypePmtMailSyncRqV1Aggregate;
    localTypePmtMailSyncRqV1Aggregate.IncImages = false;
    localTypePmtMailSyncRqV1Aggregate.UseHTML = false;
    localTypePmtMailSyncRqV1Aggregate.SyncRqCm = new TypeSyncRqCm();
    localTypePmtMailSyncRqV1Aggregate.SyncRqCm.RejectIfMissing = false;
    localTypePmtMailSyncRqV1Aggregate.SyncRqCm.TokenRqUn = new TypeTokenRqUn();
    localTypePmtMailSyncRqV1Aggregate.SyncRqCm.TokenRqUn.__memberName = "Refresh";
    localTypePmtMailSyncRqV1Aggregate.SyncRqCm.TokenRqUn.Refresh = true;
  }
  
  protected void formatSendPaymentMailRequest()
  {
    this.rqmes = new TypeOFXRequest();
    this.rqmes.OFXRequest = new TypeOFXRqMsgSetsAggregate();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatSendPaymentMailRequest:");
    }
    formatSIGNONMSGSRQV1(this.rqmes.OFXRequest);
    TypeBillPayMsgsRqV1Aggregate localTypeBillPayMsgsRqV1Aggregate = formatBillPayMsgsRqV1(this.rqmes.OFXRequest);
    this.message.setID(getUniqueSeqNum());
    if ((this.message.getSubject() == null) || (this.message.getSubject().length() == 0)) {
      this.message.setSubject("Payment Inquiry");
    }
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn = new TypeBillPayMsgsRqV1MessageUn[1];
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[0] = new TypeBillPayMsgsRqV1MessageUn();
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[0].__memberName = "PmtMailTrnRq";
    TypePmtMailTrnRqV1Aggregate localTypePmtMailTrnRqV1Aggregate = new TypePmtMailTrnRqV1Aggregate();
    localTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[0].PmtMailTrnRq = localTypePmtMailTrnRqV1Aggregate;
    localTypePmtMailTrnRqV1Aggregate.TrnRqCm = new TypeTrnRqCm();
    localTypePmtMailTrnRqV1Aggregate.TrnRqCm.TrnUID = this.message.getID();
    localTypePmtMailTrnRqV1Aggregate.PmtMailRq = new TypePmtMailRqAggregate();
    localTypePmtMailTrnRqV1Aggregate.PmtMailRq.Mail = new TypeMailAggregate();
    formatMail(localTypePmtMailTrnRqV1Aggregate.PmtMailRq.Mail);
    if (this.payment != null)
    {
      localTypePmtMailTrnRqV1Aggregate.PmtMailRq.PmtMailRqCmExists = true;
      localTypePmtMailTrnRqV1Aggregate.PmtMailRq.PmtMailRqCm = new TypePmtMailRqCm();
      localTypePmtMailTrnRqV1Aggregate.PmtMailRq.PmtMailRqCm.SrvrTID = this.payment.getID();
      localTypePmtMailTrnRqV1Aggregate.PmtMailRq.PmtMailRqCm.PmtInfo = new TypePmtInfoAggregate();
      formatPmtInfo(localTypePmtMailTrnRqV1Aggregate.PmtMailRq.PmtMailRqCm.PmtInfo, this.account, this.payment, -1);
    }
  }
  
  protected int mapError(int paramInt)
  {
    int i = 1;
    switch (paramInt)
    {
    case 16500: 
      i = 2;
      break;
    case 16501: 
      i = 3;
      break;
    case 16502: 
      i = 3;
      break;
    case 16503: 
      i = 2;
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
      if (localTypeOFXRequest.OFXRequest.BankMsgsRqV1Exists) {
        processTransactionsInBankMsgsRqV1(localTypeOFXRequest.OFXRequest.BankMsgsRqV1);
      }
      if (localTypeOFXRequest.OFXRequest.BillPayMsgsRqV1Exists) {
        processTransactionsInBillPayMsgsRqV1(localTypeOFXRequest.OFXRequest.BillPayMsgsRqV1);
      }
      if (localTypeOFXRequest.OFXRequest.EmailMsgsRqV1Exists) {
        processTransactionsInEmailMsgsRqV1(localTypeOFXRequest.OFXRequest.EmailMsgsRqV1);
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
      if (paramTypeBankMsgsRqV1Aggregate.BankMsgsRqUn[i].__memberName.equals("BankMailTrnRq"))
      {
        localObject1 = new TypeBankMailTrnRqV1(paramTypeBankMsgsRqV1Aggregate.BankMsgsRqUn[i].BankMailTrnRq);
        localObject2 = null;
        localObject2 = processBankMailTrnRqV1((TypeBankMailTrnRqV1)localObject1);
        if (localObject2 != null) {
          processBankMailTrnRs(((TypeBankMailTrnRsV1)localObject2).BankMailTrnRs);
        }
      }
      if (paramTypeBankMsgsRqV1Aggregate.BankMsgsRqUn[i].__memberName.equals("BankMailSyncRq"))
      {
        localObject1 = new TypeBankMailSyncRqV1(paramTypeBankMsgsRqV1Aggregate.BankMsgsRqUn[i].BankMailSyncRq);
        localObject2 = null;
        localObject2 = processBankMailSyncRqV1((TypeBankMailSyncRqV1)localObject1);
        if (localObject2 != null) {
          processBankMailSyncRs(((TypeBankMailSyncRsV1)localObject2).BankMailSyncRs);
        }
      }
    }
  }
  
  protected void processTransactionsInBillPayMsgsRqV1(TypeBillPayMsgsRqV1Aggregate paramTypeBillPayMsgsRqV1Aggregate)
  {
    for (int i = 0; i < paramTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn.length; i++)
    {
      Object localObject1;
      Object localObject2;
      if (paramTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[i].__memberName.equals("PmtMailTrnRq"))
      {
        localObject1 = new TypePmtMailTrnRqV1(paramTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[i].PmtMailTrnRq);
        localObject2 = null;
        localObject2 = processPmtMailTrnRqV1((TypePmtMailTrnRqV1)localObject1);
        if (localObject2 != null) {
          processPmtMailTrnRs(((TypePmtMailTrnRsV1)localObject2).PmtMailTrnRs);
        }
      }
      if (paramTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[i].__memberName.equals("PmtMailSyncRq"))
      {
        localObject1 = new TypePmtMailSyncRqV1(paramTypeBillPayMsgsRqV1Aggregate.BillPayMsgsRqV1MessageUn[i].PmtMailSyncRq);
        localObject2 = null;
        localObject2 = processPmtMailSyncRqV1((TypePmtMailSyncRqV1)localObject1);
        if (localObject2 != null) {
          processPmtMailSyncRs(((TypePmtMailSyncRsV1)localObject2).PmtMailSyncRs);
        }
      }
    }
  }
  
  protected void processTransactionsInEmailMsgsRqV1(TypeEmailMsgsRqV1Aggregate paramTypeEmailMsgsRqV1Aggregate)
  {
    for (int i = 0; i < paramTypeEmailMsgsRqV1Aggregate.EmailMsgsRqV1Un.length; i++)
    {
      Object localObject1;
      Object localObject2;
      if (paramTypeEmailMsgsRqV1Aggregate.EmailMsgsRqV1Un[i].__memberName.equals("MailTrnRq"))
      {
        localObject1 = new TypeMailTrnRqV1(paramTypeEmailMsgsRqV1Aggregate.EmailMsgsRqV1Un[i].MailTrnRq);
        localObject2 = null;
        localObject2 = processMailTrnRqV1((TypeMailTrnRqV1)localObject1);
        if (localObject2 != null) {
          processMailTrnRs(((TypeMailTrnRsV1)localObject2).MailTrnRs);
        }
      }
      if (paramTypeEmailMsgsRqV1Aggregate.EmailMsgsRqV1Un[i].__memberName.equals("MailSyncRq"))
      {
        localObject1 = new TypeMailSyncRqV1(paramTypeEmailMsgsRqV1Aggregate.EmailMsgsRqV1Un[i].MailSyncRq);
        localObject2 = null;
        localObject2 = processMailSyncRqV1((TypeMailSyncRqV1)localObject1);
        if (localObject2 != null) {
          processMailSyncRs(((TypeMailSyncRsV1)localObject2).MailSyncRs);
        }
      }
    }
  }
  
  protected void processBankMailTrnRs(TypeBankMailTrnRsV1Aggregate paramTypeBankMailTrnRsV1Aggregate)
  {
    processSTATUS(paramTypeBankMailTrnRsV1Aggregate.TrnRsCm.Status);
    if (paramTypeBankMailTrnRsV1Aggregate.BankMailRsUnExists) {
      if (paramTypeBankMailTrnRsV1Aggregate.BankMailRsUn.__memberName.equals("BankMailRs"))
      {
        if (this.messages != null)
        {
          this.message = this.messages.create();
          this.message.setID(String.valueOf(this.message.hashCode()));
          processMAIL(paramTypeBankMailTrnRsV1Aggregate.BankMailRsUn.BankMailRs.Mail);
        }
      }
      else if ((paramTypeBankMailTrnRsV1Aggregate.BankMailRsUn.__memberName.equals("ChkMailRs")) || (!paramTypeBankMailTrnRsV1Aggregate.BankMailRsUn.__memberName.equals("DepMailRs"))) {}
    }
  }
  
  protected void processMAIL(TypeMailAggregate paramTypeMailAggregate)
  {
    this.message.setDate(paramTypeMailAggregate.DtCreated);
    this.message.setFrom(paramTypeMailAggregate.From);
    this.message.setTo(paramTypeMailAggregate.To);
    this.message.setMemo(paramTypeMailAggregate.MsgBody);
    this.message.setSubject(paramTypeMailAggregate.Subject);
  }
  
  protected void processBankMailSyncRs(TypeBankMailSyncRsV1Aggregate paramTypeBankMailSyncRsV1Aggregate)
  {
    if ((paramTypeBankMailSyncRsV1Aggregate != null) && (paramTypeBankMailSyncRsV1Aggregate.BankMailTrnRs != null))
    {
      TypeBankMailTrnRsV1Aggregate[] arrayOfTypeBankMailTrnRsV1Aggregate = paramTypeBankMailSyncRsV1Aggregate.BankMailTrnRs;
      for (int i = 0; i < arrayOfTypeBankMailTrnRsV1Aggregate.length; i++) {
        processBankMailTrnRs(arrayOfTypeBankMailTrnRsV1Aggregate[i]);
      }
    }
  }
  
  protected void processPmtMailTrnRs(TypePmtMailTrnRsV1Aggregate paramTypePmtMailTrnRsV1Aggregate)
  {
    processSTATUS(paramTypePmtMailTrnRsV1Aggregate.TrnRsCm.Status);
    if ((paramTypePmtMailTrnRsV1Aggregate.PmtMailRsExists) && (this.messages != null))
    {
      this.message = this.messages.create();
      this.message.setID(String.valueOf(this.message.hashCode()));
      processMAIL(paramTypePmtMailTrnRsV1Aggregate.PmtMailRs.Mail);
    }
  }
  
  protected void processPmtMailSyncRs(TypePmtMailSyncRsV1Aggregate paramTypePmtMailSyncRsV1Aggregate)
  {
    if ((paramTypePmtMailSyncRsV1Aggregate != null) && (paramTypePmtMailSyncRsV1Aggregate.PmtMailTrnRs != null))
    {
      TypePmtMailTrnRsV1Aggregate[] arrayOfTypePmtMailTrnRsV1Aggregate = paramTypePmtMailSyncRsV1Aggregate.PmtMailTrnRs;
      for (int i = 0; i < arrayOfTypePmtMailTrnRsV1Aggregate.length; i++) {
        processPmtMailTrnRs(arrayOfTypePmtMailTrnRsV1Aggregate[i]);
      }
    }
  }
  
  protected void processMailTrnRs(TypeMailTrnRsV1Aggregate paramTypeMailTrnRsV1Aggregate)
  {
    processSTATUS(paramTypeMailTrnRsV1Aggregate.TrnRsCm.Status);
    if ((paramTypeMailTrnRsV1Aggregate.MailRsExists) && (this.messages != null))
    {
      this.message = this.messages.create();
      this.message.setID(String.valueOf(this.message.hashCode()));
      processMAIL(paramTypeMailTrnRsV1Aggregate.MailRs.Mail);
    }
  }
  
  protected void processMailSyncRs(TypeMailSyncRsV1Aggregate paramTypeMailSyncRsV1Aggregate)
  {
    if ((paramTypeMailSyncRsV1Aggregate != null) && (paramTypeMailSyncRsV1Aggregate.MailTrnRs != null))
    {
      TypeMailTrnRsV1Aggregate[] arrayOfTypeMailTrnRsV1Aggregate = paramTypeMailSyncRsV1Aggregate.MailTrnRs;
      for (int i = 0; i < arrayOfTypeMailTrnRsV1Aggregate.length; i++) {
        processMailTrnRs(arrayOfTypeMailTrnRsV1Aggregate[i]);
      }
    }
  }
  
  protected TypeBankMailTrnRsV1 processBankMailTrnRqV1(TypeBankMailTrnRqV1 paramTypeBankMailTrnRqV1)
  {
    try
    {
      if (this._OFXTransactionBean != null) {
        return this._OFXTransactionBean.processBankMailTrnRqV1(paramTypeBankMailTrnRqV1, this._ud);
      }
      return this._OFXTransactionHandler.processBankMailTrnRqV1(paramTypeBankMailTrnRqV1, this._ud);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception in processBankMailTrnRqV1", localException);
    }
    return null;
  }
  
  protected TypeBankMailSyncRsV1 processBankMailSyncRqV1(TypeBankMailSyncRqV1 paramTypeBankMailSyncRqV1)
  {
    try
    {
      if (this._OFXTransactionBean != null) {
        return this._OFXTransactionBean.processBankMailSyncRqV1(paramTypeBankMailSyncRqV1, this._ud);
      }
      return this._OFXTransactionHandler.processBankMailSyncRqV1(paramTypeBankMailSyncRqV1, this._ud);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception in processBankMailSyncRqV1", localException);
    }
    return null;
  }
  
  protected TypePmtMailTrnRsV1 processPmtMailTrnRqV1(TypePmtMailTrnRqV1 paramTypePmtMailTrnRqV1)
  {
    try
    {
      if (this._OFXTransactionBean != null) {
        return this._OFXTransactionBean.processPmtMailTrnRqV1(paramTypePmtMailTrnRqV1, this._ud);
      }
      return this._OFXTransactionHandler.processPmtMailTrnRqV1(paramTypePmtMailTrnRqV1, this._ud);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception in processPmtMailTrnRqV1", localException);
    }
    return null;
  }
  
  protected TypePmtMailSyncRsV1 processPmtMailSyncRqV1(TypePmtMailSyncRqV1 paramTypePmtMailSyncRqV1)
  {
    try
    {
      if (this._OFXTransactionBean != null) {
        return this._OFXTransactionBean.processPmtMailSyncRqV1(paramTypePmtMailSyncRqV1, this._ud);
      }
      return this._OFXTransactionHandler.processPmtMailSyncRqV1(paramTypePmtMailSyncRqV1, this._ud);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception in processPmtMailSyncRqV1", localException);
    }
    return null;
  }
  
  protected TypeMailTrnRsV1 processMailTrnRqV1(TypeMailTrnRqV1 paramTypeMailTrnRqV1)
  {
    try
    {
      if (this._OFXTransactionBean != null) {
        return this._OFXTransactionBean.processMailTrnRqV1(paramTypeMailTrnRqV1, this._ud);
      }
      return this._OFXTransactionHandler.processMailTrnRqV1(paramTypeMailTrnRqV1, this._ud);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception in processMailTrnRqV1", localException);
    }
    return null;
  }
  
  protected TypeMailSyncRsV1 processMailSyncRqV1(TypeMailSyncRqV1 paramTypeMailSyncRqV1)
  {
    try
    {
      if (this._OFXTransactionBean != null) {
        return this._OFXTransactionBean.processMailSyncRqV1(paramTypeMailSyncRqV1, this._ud);
      }
      return this._OFXTransactionHandler.processMailSyncRqV1(paramTypeMailSyncRqV1, this._ud);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception in processMailSyncRqV1", localException);
    }
    return null;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.FFServer.OFX200.Messaging
 * JD-Core Version:    0.7.0.1
 */