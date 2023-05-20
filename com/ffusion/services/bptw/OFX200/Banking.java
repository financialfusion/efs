package com.ffusion.services.bptw.OFX200;

import com.ffusion.beans.Currency;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.banking.RecTransfer;
import com.ffusion.beans.banking.RecTransfers;
import com.ffusion.beans.banking.Transaction;
import com.ffusion.beans.banking.Transactions;
import com.ffusion.beans.banking.Transfer;
import com.ffusion.beans.banking.Transfers;
import com.ffusion.beans.util.BeansConverter;
import com.ffusion.csil.CSILException;
import com.ffusion.ffs.bpw.interfaces.BPWHist;
import com.ffusion.ffs.bpw.interfaces.IntraTrnInfo;
import com.ffusion.msgbroker.generated.MessageBroker.api.OFX200.BPWServices.IOFX200BPWServices;
import com.ffusion.msgbroker.generated.MessageBroker.api.OFX200.BPWServices.OFX200BPWServicesHome;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumCurrencyEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumXferStatusEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBCCAcctFromUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankAcctFromAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankMsgsRqUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankMsgsRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeCCAcctFromAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraCanRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraModRsAggregate;
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
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraCanRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraModRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraRsUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraSyncRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraSyncRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecurrInstAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeSignOnMsgsRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStatusAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeSyncRqCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTokenRqUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTrnRqCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTrnRsCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeXferInfoAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeXferPrcStsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.ValueSetCurrencyEnum;
import com.ffusion.services.banksim2.BankingDefines;
import com.ffusion.util.ArrayUtil;
import com.ffusion.util.logging.DebugLog;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import javax.ejb.CreateException;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.RemoveException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.transaction.TransactionRolledbackException;

public class Banking
  extends com.ffusion.services.banksim2.Banking
  implements com.ffusion.services.bptw.Banking
{
  private IOFX200BPWServices ck = null;
  private static final String ci = "com.ibm.websphere.naming.jndicache.cacheobject";
  private static final String cj = "none";
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
  private static final String cl = "[ErrorCode ";
  
  private Context jdMethod_int(String paramString1, String paramString2, String paramString3)
    throws Exception
  {
    Properties localProperties = new Properties();
    localProperties.put("java.naming.factory.initial", this.context_factory);
    localProperties.put("java.naming.provider.url", paramString1);
    localProperties.put("java.naming.security.principal", paramString2);
    localProperties.put("java.naming.security.credentials", paramString3);
    localProperties.put("com.ibm.websphere.naming.jndicache.cacheobject", "none");
    return new InitialContext(localProperties);
  }
  
  protected void getHandler()
  {
    if (this.ck != null) {
      return;
    }
    Context localContext = null;
    for (int i = 0; i < this.provider_url_list.size(); i++)
    {
      String str = (String)this.provider_url_list.get(i);
      try
      {
        localContext = jdMethod_int(str, this.context_username, this.context_password);
        Object localObject = localContext.lookup(this.callback_JNDI_name);
        OFX200BPWServicesHome localOFX200BPWServicesHome = (OFX200BPWServicesHome)PortableRemoteObject.narrow(localObject, OFX200BPWServicesHome.class);
        this.ck = localOFX200BPWServicesHome.create();
        if (this.ck != null) {
          return;
        }
      }
      catch (TransactionRolledbackException localTransactionRolledbackException)
      {
        System.out.println("Unexpected TransactionRolledbackException: " + localTransactionRolledbackException.toString());
      }
      catch (RemoteException localRemoteException)
      {
        System.out.println("RMI RemoteException: " + localRemoteException.toString());
      }
      catch (CreateException localCreateException)
      {
        System.out.println("EJB CreateException: " + localCreateException.toString());
      }
      catch (RemoveException localRemoveException)
      {
        System.out.println("EJB RemoteException: " + localRemoveException.toString());
      }
      catch (ObjectNotFoundException localObjectNotFoundException)
      {
        System.out.println("EJB ObjecNotFound Exception: " + localObjectNotFoundException.toString());
      }
      catch (NamingException localNamingException)
      {
        System.out.println("NamingException: " + localNamingException.toString());
      }
      catch (Throwable localThrowable)
      {
        System.out.println("UNKNOWN: " + localThrowable.toString());
      }
    }
    System.out.println("bpw handler is null");
  }
  
  protected void removeHandler()
  {
    try
    {
      if (debugService) {
        System.out.println("##################### Removing Handler ################");
      }
      if (this.ck == null)
      {
        System.out.println("bpw handler is null");
      }
      else
      {
        this.ck.remove();
        this.ck = null;
      }
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace(System.out);
    }
  }
  
  public int signOn(String paramString1, String paramString2)
  {
    this.userID = paramString1;
    setUserID(paramString1);
    this.password = paramString2;
    return 0;
  }
  
  public boolean signOn(SecureUser paramSecureUser, String paramString1, String paramString2)
    throws CSILException
  {
    this.userID = paramString1;
    setUserID(paramString1);
    this.secureUser.set(paramSecureUser);
    this.password = paramString2;
    return true;
  }
  
  protected void processTransactionsInSignOnMsgsRqV1(TypeSignOnMsgsRqV1Aggregate paramTypeSignOnMsgsRqV1Aggregate) {}
  
  protected TypeIntraTrnRsV1 processIntraTrnRqV1(TypeIntraTrnRqV1 paramTypeIntraTrnRqV1)
  {
    try
    {
      return this.ck.processIntraTrnRqV1(paramTypeIntraTrnRqV1, this._ud);
    }
    catch (Exception localException)
    {
      System.out.println(localException.getMessage());
    }
    return null;
  }
  
  protected TypeRecIntraTrnRsV1 processRecIntraTrnRqV1(TypeRecIntraTrnRqV1 paramTypeRecIntraTrnRqV1)
  {
    try
    {
      return this.ck.processRecIntraTrnRqV1(paramTypeRecIntraTrnRqV1, this._ud);
    }
    catch (Exception localException)
    {
      System.out.println(localException.getMessage());
    }
    return null;
  }
  
  protected TypeIntraSyncRsV1 processIntraSyncRqV1(TypeIntraSyncRqV1 paramTypeIntraSyncRqV1)
  {
    try
    {
      return this.ck.processIntraSyncRqV1(paramTypeIntraSyncRqV1, this._ud);
    }
    catch (Exception localException)
    {
      System.out.println(localException.getMessage());
    }
    return null;
  }
  
  protected TypeRecIntraSyncRsV1 processRecIntraSyncRqV1(TypeRecIntraSyncRqV1 paramTypeRecIntraSyncRqV1)
  {
    try
    {
      return this.ck.processRecIntraSyncRqV1(paramTypeRecIntraSyncRqV1, this._ud);
    }
    catch (Exception localException)
    {
      System.out.println(localException.getMessage());
    }
    return null;
  }
  
  public int getTransfers(Accounts paramAccounts, Transfers paramTransfers)
  {
    if ((paramAccounts != null) && (paramAccounts.size() > 0))
    {
      this.account = ((Account)paramAccounts.get(0));
      if ((this.account != null) && (this.account.getNumber() != null) && (this.account.getNumber().length() > 0)) {
        return super.getTransfers(paramAccounts, paramTransfers);
      }
    }
    BPWHist localBPWHist = new BPWHist();
    localBPWHist.setVersion("200");
    localBPWHist.setCursorId(null);
    localBPWHist.setHistId(null);
    localBPWHist.setCustId(getProfileID());
    getHandler();
    try
    {
      localBPWHist = this.ck.getIntraHistory(localBPWHist);
    }
    catch (Exception localException1)
    {
      DebugLog.throwing("getPageTransfers ", localException1);
      removeHandler();
      return 0;
    }
    removeHandler();
    if (localBPWHist != null)
    {
      IntraTrnInfo[] arrayOfIntraTrnInfo = null;
      try
      {
        localBPWHist.setTrans(ArrayUtil.convertReferences(localBPWHist.getTrans(), new IntraTrnInfo[0].getClass()));
      }
      catch (Exception localException2) {}
      if ((localBPWHist.getTrans() instanceof IntraTrnInfo[])) {
        arrayOfIntraTrnInfo = (IntraTrnInfo[])localBPWHist.getTrans();
      } else {
        DebugLog.log("getPageTransfers: NOT IntraTrnInfo object");
      }
      IntraTrnInfo localIntraTrnInfo = null;
      if (arrayOfIntraTrnInfo != null) {
        for (int i = 0; i < arrayOfIntraTrnInfo.length; i++)
        {
          Transfer localTransfer = (Transfer)paramTransfers.create();
          localIntraTrnInfo = arrayOfIntraTrnInfo[i];
          BeansConverter.setXferFromIntraTrnInfo(localTransfer, localIntraTrnInfo, paramAccounts, false);
          if (((localIntraTrnInfo.srvrTid == null) || (localIntraTrnInfo.srvrTid.length() == 0)) && (localIntraTrnInfo.recSrvrTid != null) && (localIntraTrnInfo.recSrvrTid.length() > 0)) {
            paramTransfers.remove(localTransfer);
          }
        }
      }
    }
    return 0;
  }
  
  public int getRecTransfers(Accounts paramAccounts, RecTransfers paramRecTransfers)
  {
    if ((paramAccounts != null) && (paramAccounts.size() > 0))
    {
      this.account = ((Account)paramAccounts.get(0));
      if ((this.account != null) && (this.account.getNumber() != null) && (this.account.getNumber().length() > 0)) {
        return super.getRecTransfers(paramAccounts, paramRecTransfers);
      }
    }
    BPWHist localBPWHist = new BPWHist();
    localBPWHist.setVersion("200");
    localBPWHist.setCursorId(null);
    localBPWHist.setHistId(null);
    localBPWHist.setCustId(getProfileID());
    getHandler();
    try
    {
      localBPWHist = this.ck.getIntraHistory(localBPWHist);
    }
    catch (Exception localException1)
    {
      DebugLog.throwing("getPageTransfers ", localException1);
      removeHandler();
      return 0;
    }
    removeHandler();
    paramAccounts = new Accounts();
    if (localBPWHist != null)
    {
      IntraTrnInfo[] arrayOfIntraTrnInfo = null;
      try
      {
        localBPWHist.setTrans(ArrayUtil.convertReferences(localBPWHist.getTrans(), new IntraTrnInfo[0].getClass()));
      }
      catch (Exception localException2) {}
      if ((localBPWHist.getTrans() instanceof IntraTrnInfo[])) {
        arrayOfIntraTrnInfo = (IntraTrnInfo[])localBPWHist.getTrans();
      } else {
        DebugLog.log("getPageTransfers: NOT IntraTrnInfo object");
      }
      IntraTrnInfo localIntraTrnInfo = null;
      if (arrayOfIntraTrnInfo != null)
      {
        for (int i = 0; i < arrayOfIntraTrnInfo.length; i++)
        {
          localIntraTrnInfo = arrayOfIntraTrnInfo[i];
          String str1 = localIntraTrnInfo.acctTypeFrom;
          int j = com.ffusion.services.banksim2.Banking.getBPWAccountType(str1);
          String str2 = localIntraTrnInfo.acctIdFrom;
          Account localAccount = paramAccounts.getByAccountNumberAndType(str2, j);
          if (localAccount == null)
          {
            localAccount = paramAccounts.create(localIntraTrnInfo.bankId, str2, j);
            localAccount.setFilterable("TransferFrom");
          }
          localAccount.setCurrencyCode(localIntraTrnInfo.curDef);
        }
        if (paramAccounts.size() > 0) {
          return super.getRecTransfers(paramAccounts, paramRecTransfers);
        }
      }
    }
    return 0;
  }
  
  public String getBankID()
  {
    return this.bankID;
  }
  
  private static TypeTrnRqCm a(Transfer paramTransfer, String paramString)
  {
    TypeTrnRqCm localTypeTrnRqCm = new TypeTrnRqCm();
    String str = getUniqueSeqNum(paramString);
    paramTransfer.setTRNUID(str);
    localTypeTrnRqCm.TrnUID = str;
    return localTypeTrnRqCm;
  }
  
  protected void formatSkipRecTransferRequest(RecTransfer paramRecTransfer)
  {
    this.rqmes = new TypeOFXRequest();
    this.rqmes.OFXRequest = new TypeOFXRqMsgSetsAggregate();
    if (debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatSkipRecTransferRequest:");
    }
    formatSIGNONMSGSRQV1(this.rqmes.OFXRequest, paramRecTransfer.getTrackingID());
  }
  
  protected void formatGetTransfersSyncRequest()
  {
    this.rqmes = new TypeOFXRequest();
    this.rqmes.OFXRequest = new TypeOFXRqMsgSetsAggregate();
    if (debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatGetTransfersSyncRequest:");
    }
    formatSIGNONMSGSRQV1(this.rqmes.OFXRequest);
    int i = 0;
    this.accounts.setFilter("All");
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
    if (debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatGetRecTransfersSyncRequest:");
    }
    formatSIGNONMSGSRQV1(this.rqmes.OFXRequest);
    int i = 0;
    this.accounts.setFilter("All");
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
  
  protected static TypeIntraTrnRqV1Aggregate formatINTRATRNRQ(TypeOFXRqMsgSetsAggregate paramTypeOFXRqMsgSetsAggregate, String paramString)
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
  
  protected boolean checkXferIsScheduled(Account paramAccount1, Account paramAccount2)
  {
    return false;
  }
  
  protected void processOFXRequest()
  {
    TypeOFXRequest localTypeOFXRequest = this.rqmes;
    this.objStatus.clear();
    IOFX200BPWServices localIOFX200BPWServices = null;
    try
    {
      localIOFX200BPWServices = getOFXHandler();
      processTransactionsInSignOnMsgsRqV1(localTypeOFXRequest.OFXRequest.SignOnMsgsRqV1, localIOFX200BPWServices);
      if (localTypeOFXRequest.OFXRequest.SignUpMsgsRqV1Exists) {
        processTransactionsInSignUpMsgsRqV1(localTypeOFXRequest.OFXRequest.SignUpMsgsRqV1, localIOFX200BPWServices);
      }
      if (localTypeOFXRequest.OFXRequest.BankMsgsRqV1Exists) {
        processTransactionsInBankMsgsRqV1(localTypeOFXRequest.OFXRequest.BankMsgsRqV1, localIOFX200BPWServices);
      }
    }
    catch (RemoteException localRemoteException)
    {
      this.objStatus.put("ServiceDown", "-1");
      localRemoteException.printStackTrace();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    finally
    {
      if (localIOFX200BPWServices != null) {
        removeOFXHandler(localIOFX200BPWServices);
      }
    }
  }
  
  protected void processTransactionsInBankMsgsRqV1(TypeBankMsgsRqV1Aggregate paramTypeBankMsgsRqV1Aggregate, IOFX200BPWServices paramIOFX200BPWServices)
    throws Exception
  {
    for (int i = 0; i < paramTypeBankMsgsRqV1Aggregate.BankMsgsRqUn.length; i++)
    {
      Object localObject1;
      Object localObject2;
      if ("IntraSyncRq".equals(paramTypeBankMsgsRqV1Aggregate.BankMsgsRqUn[i].__memberName))
      {
        localObject1 = new TypeIntraSyncRqV1(paramTypeBankMsgsRqV1Aggregate.BankMsgsRqUn[i].IntraSyncRq);
        localObject2 = null;
        localObject2 = paramIOFX200BPWServices.processIntraSyncRqV1((TypeIntraSyncRqV1)localObject1, this._ud);
        if (localObject2 != null) {
          processIntraSyncRs(((TypeIntraSyncRsV1)localObject2).IntraSyncRs);
        }
      }
      if ("RecIntraSyncRq".equals(paramTypeBankMsgsRqV1Aggregate.BankMsgsRqUn[i].__memberName))
      {
        localObject1 = new TypeRecIntraSyncRqV1(paramTypeBankMsgsRqV1Aggregate.BankMsgsRqUn[i].RecIntraSyncRq);
        localObject2 = null;
        localObject2 = paramIOFX200BPWServices.processRecIntraSyncRqV1((TypeRecIntraSyncRqV1)localObject1, this._ud);
        if (localObject2 != null) {
          processRecIntraSyncRs(((TypeRecIntraSyncRsV1)localObject2).RecIntraSyncRs);
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
  
  protected void processIntraTrnRs(TypeIntraTrnRsV1Aggregate paramTypeIntraTrnRsV1Aggregate)
  {
    processSTATUS(paramTypeIntraTrnRsV1Aggregate.TrnRsCm.Status);
    String str1 = null;
    String str2 = paramTypeIntraTrnRsV1Aggregate.TrnRsCm.TrnUID;
    TypeXferInfoAggregate localTypeXferInfoAggregate = null;
    TypeXferPrcStsAggregate localTypeXferPrcStsAggregate = null;
    String str3 = null;
    String str4 = null;
    Object localObject;
    if (paramTypeIntraTrnRsV1Aggregate.IntraTrnRsUnExists)
    {
      if ("IntraRs".equals(paramTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.__memberName))
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
      else if ("IntraModRs".equals(paramTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.__memberName))
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
      else if ("IntraCanRs".equals(paramTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.__memberName))
      {
        str1 = paramTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.IntraCanRs.SrvrTID;
      }
      if (this.transfers != null)
      {
        if ((str2 != null) && (!"0".equals(str2))) {
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
        localObject = new Integer(this.status);
        this.transfer.setError(mapError(((Integer)localObject).intValue()));
        this.objStatus.put(str1, ((Integer)localObject).toString());
        if (str4 != null) {
          this.transfer.setAmtCurrency(str4);
        }
        if ("IntraCanRs".equals(paramTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.__memberName))
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
            if (this.transfer.getDateValue() == null) {
              this.transfer.setDate(getDate(localTypeXferPrcStsAggregate.DtXferPrc));
            }
            EnumXferStatusEnum localEnumXferStatusEnum = localTypeXferPrcStsAggregate.XferPrcCode;
            this.transfer.setStatus(getXferStatus(localEnumXferStatusEnum));
          }
          if (this.transfer.getReferenceNumber() == null) {
            this.transfer.setReferenceNumber(str1);
          }
        }
      }
    }
    else if ((paramTypeIntraTrnRsV1Aggregate.TrnRsCm.Status.Code == 2000) && (paramTypeIntraTrnRsV1Aggregate.TrnRsCm.Status.MessageExists) && (paramTypeIntraTrnRsV1Aggregate.TrnRsCm.Status.Message.startsWith("[ErrorCode ")))
    {
      localObject = paramTypeIntraTrnRsV1Aggregate.TrnRsCm.Status.Message;
      int i = ((String)localObject).indexOf("[ErrorCode ") + "[ErrorCode ".length();
      int j = ((String)localObject).indexOf("]");
      String str5 = ((String)localObject).substring(i, j);
      this.status = Integer.parseInt(str5);
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
      if ("RecIntraRs".equals(paramTypeRecIntraTrnRsV1Aggregate.RecIntraRsUn.__memberName))
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
      else if ("RecIntraModRs".equals(paramTypeRecIntraTrnRsV1Aggregate.RecIntraRsUn.__memberName))
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
      else if ("RecIntraCanRs".equals(paramTypeRecIntraTrnRsV1Aggregate.RecIntraRsUn.__memberName))
      {
        str1 = paramTypeRecIntraTrnRsV1Aggregate.RecIntraRsUn.RecIntraCanRs.RecSrvrTID;
      }
      if (this.rectransfers != null)
      {
        if ((str2 != null) && (!"0".equals(str2))) {
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
        if ("RecIntraCanRs".equals(paramTypeRecIntraTrnRsV1Aggregate.RecIntraRsUn.__memberName))
        {
          if (this.status == 0) {
            this.rectransfer.setStatus(3);
          }
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
            if (this.rectransfer.getDateValue() == null) {
              this.rectransfer.setDate(getDate(localTypeXferPrcStsAggregate.DtXferPrc));
            }
            EnumXferStatusEnum localEnumXferStatusEnum = localTypeXferPrcStsAggregate.XferPrcCode;
            this.rectransfer.setStatus(getXferStatus(localEnumXferStatusEnum));
          }
          if (this.rectransfer.getReferenceNumber() == null) {
            this.rectransfer.setReferenceNumber(str1);
          }
        }
      }
    }
  }
  
  protected void processXFERINFO(Transfer paramTransfer, Accounts paramAccounts, TypeXferInfoAggregate paramTypeXferInfoAggregate)
  {
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
    String str = paramTransfer.getFromAccount() != null ? paramTransfer.getFromAccount().getCurrencyCode() : "USD";
    if (str == null) {
      str = "USD";
    }
    Currency localCurrency = new Currency(new BigDecimal(paramTypeXferInfoAggregate.TrnAmt), paramTransfer.getFromAccount() != null ? paramTransfer.getFromAccount().getCurrencyCode() : "USD", paramTransfer.getLocale());
    paramTransfer.setAmount(localCurrency);
    if (paramTypeXferInfoAggregate.DtDueExists) {
      paramTransfer.setDate(getDate(paramTypeXferInfoAggregate.DtDue));
    }
    if ("CCAcctFrom".equals(paramTypeXferInfoAggregate.BCCAcctFromUn.__memberName)) {
      paramTransfer.setConfirmationNum(paramTypeXferInfoAggregate.BCCAcctFromUn.CCAcctFrom.AcctKey);
    } else {
      paramTransfer.setConfirmationNum(paramTypeXferInfoAggregate.BCCAcctFromUn.BankAcctFrom.AcctKey);
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
  
  protected static int getXferStatus(EnumXferStatusEnum paramEnumXferStatusEnum)
  {
    for (int i = 0; i < BankingDefines.xferStatTypes.length; i++) {
      if (BankingDefines.xferStatTypes[i].value() == paramEnumXferStatusEnum.value()) {
        return BankingDefines.xferStatMap[i];
      }
    }
    DebugLog.log(Level.INFO, "NON-Standard getXferStatus() EnumXferStatusEnum = " + paramEnumXferStatusEnum.value());
    throw new IllegalArgumentException("getXferStatus: XferStatus=" + paramEnumXferStatusEnum.value());
  }
  
  protected void cleanupRequest()
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
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.bptw.OFX200.Banking
 * JD-Core Version:    0.7.0.1
 */