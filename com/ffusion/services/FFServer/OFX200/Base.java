package com.ffusion.services.FFServer.OFX200;

import com.ffusion.beans.Balance;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.ffs.ofx.interfaces.TypeUserData;
import com.ffusion.msgbroker.generated.MessageBroker.api.OFX200.Callback.IOFX200Callback;
import com.ffusion.msgbroker.generated.MessageBroker.api.OFX200.Callback.OFX200CallbackBean;
import com.ffusion.msgbroker.generated.MessageBroker.api.OFX200.Callback.OFX200CallbackHome;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumAccountEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumFreqEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumServiceStatusEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeAcctInfoAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeAcctInfoRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeAcctInfoRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeAcctInfoTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeAcctInfoTrnRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeAcctInfoTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeAcctInfoTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeAcctInfoUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeAvailBalAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBCCAcctFromUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBCCAcctToUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBPAcctInfoAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankAcctFromAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankAcctInfoAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankAcctToAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankMsgsRqUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankMsgsRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeCCAcctFromAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeCCAcctInfoAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeCCAcctToAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeCCStmtRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeCCStmtRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeCCStmtTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeCCStmtTrnRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeCCStmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeCCStmtTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeCreditCardMsgsRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeCreditCardMsgsRqV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeDisconnectRq;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeDisconnectRs;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeFIAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeLedgerBalAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeOFXRequest;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeOFXRqMsgSetsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePinChRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePinChTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePinChTrnRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePinChTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePinChTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecurrInstAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeSignOnMsgsRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeSignUpMsgsRqUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeSignUpMsgsRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeSonRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeSonRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeSonRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeSonRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStatusAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStmtRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStmtRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStmtTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStmtTrnRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStmtTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTrnRqCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTrnRsCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeUserCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeUserInfoUn;
import com.ffusion.services.AccountService;
import com.ffusion.services.FFServer.Core;
import com.ffusion.services.InitFileHandler;
import com.ffusion.services.SignOn2;
import com.ffusion.util.ContextPool;
import com.ffusion.util.ContextPoolList;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import com.microstar.xml.HandlerBase;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.Vector;
import java.util.logging.Level;
import javax.naming.Context;
import javax.rmi.PortableRemoteObject;

public abstract class Base
  extends Core
  implements SignOn2, Defines, AccountService
{
  private static final String bG = "ConnectionURL";
  private static final String bt = "CertificatesDirectory";
  private static final String bz = "BankID";
  private static final String bE = "BankName";
  private static final String bu = "ApplicationID";
  private static final String bw = "ApplicationVersion";
  private static final String by = "CallBackBeanURL";
  private static final String bs = "ContextFactory";
  private static final String bx = "CallBackJNDIName";
  private static final String bC = "ContextUserName";
  private static final String bB = "ContextPassword";
  private static final String bF = "ContextProperty";
  private static final String br = "PrincipalClass";
  private static final String bv = "UseEJB";
  private static final String bA = "DebugService";
  protected final String SERVICE_DOWN = "ServiceDown";
  protected final int SERVICE_DOWN_ERROR = 4;
  protected String url;
  protected String certsDirectory;
  protected String applicationID;
  protected int applicationVersion;
  protected String bankID;
  protected String bankName;
  protected Vector provider_url_list = new Vector();
  protected String provider_url;
  protected String context_factory;
  protected String callback_JNDI_name = "OFX200.OFX200CallbackBean";
  protected String context_username = "system";
  protected String context_password = "weblogic";
  protected String principalClass = "com.ffusion.services.FFServer.iPlanet";
  protected HashMap contextProperty = null;
  protected Account account;
  protected Accounts accounts;
  protected transient IOFX200Callback _OFXTransactionHandler = null;
  protected boolean reloadHome = false;
  protected boolean signonProcessed = false;
  protected OFX200CallbackBean _OFXTransactionBean = null;
  protected boolean useNoEJB = false;
  protected TypeUserData _ud;
  private static ContextPoolList bD = null;
  public TypeOFXRequest rqmes = null;
  
  public Base()
  {
    Date localDate = new Date();
    this.random = new Random(localDate.getTime());
    this.savedValues = new HashMap();
    this.objStatus = new HashMap();
  }
  
  public void setInitURL(String paramString)
  {
    initialize(paramString);
  }
  
  public int initialize(String paramString)
  {
    return initialize(paramString, new a());
  }
  
  protected int initialize(String paramString, HandlerBase paramHandlerBase)
  {
    int i = 0;
    try
    {
      InitFileHandler localInitFileHandler = new InitFileHandler();
      i = localInitFileHandler.initialize(paramString, paramHandlerBase);
      if ((!this.useNoEJB) && ((this.context_factory == null) || (this.provider_url == null))) {
        i = 8;
      }
      this._ud = new TypeUserData();
      if (this.useNoEJB) {
        this._OFXTransactionBean = new OFX200CallbackBean();
      } else if (bD == null) {
        bD = new ContextPoolList();
      }
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace(System.out);
      i = 8;
    }
    return i;
  }
  
  protected void getHandler()
  {
    if (this.useNoEJB) {
      return;
    }
    ContextPool localContextPool = null;
    Context localContext = null;
    for (int i = 0; i < this.provider_url_list.size(); i++)
    {
      String str = (String)this.provider_url_list.get(i);
      try
      {
        this.reloadHome = true;
        if (this.debugService) {
          DebugLog.log(Level.INFO, "##################### Getting Handler ################");
        }
        OFX200CallbackHome localOFX200CallbackHome = null;
        localContextPool = bD.getContextPool(str);
        if (localContextPool == null) {
          try
          {
            localContextPool = new ContextPool(str, this.context_username, this.context_password, this.context_factory);
            localContextPool.setInitialContexts(1);
            localContextPool.setIncrementalContexts(1);
            localContextPool.setMaxContexts(50);
            if (this.contextProperty != null)
            {
              Iterator localIterator = this.contextProperty.entrySet().iterator();
              localObject2 = localContextPool.getProperties();
              while (localIterator.hasNext())
              {
                Map.Entry localEntry = (Map.Entry)localIterator.next();
                ((Properties)localObject2).put(localEntry.getKey(), localEntry.getValue().toString());
              }
            }
            localContextPool.createPool();
            bD.add(localContextPool);
          }
          catch (Throwable localThrowable2)
          {
            DebugLog.throwing("Exception in getHandler", localThrowable2);
          }
        }
        if (this.debugService) {
          DebugLog.log(Level.INFO, "provider_url = " + str);
        }
        localContext = localContextPool.getContext();
        localObject1 = localContext.lookup(this.callback_JNDI_name);
        localOFX200CallbackHome = (OFX200CallbackHome)PortableRemoteObject.narrow(localObject1, OFX200CallbackHome.class);
        this._OFXTransactionHandler = localOFX200CallbackHome.create();
        localContextPool.returnContext(localContext);
      }
      catch (Throwable localThrowable1)
      {
        Object localObject2;
        Object localObject1;
        if ((localContext != null) && (localContextPool != null)) {
          try
          {
            localContext = localContextPool.refreshContext(localContext);
            localObject1 = localContext.lookup(this.callback_JNDI_name);
            localObject2 = (OFX200CallbackHome)PortableRemoteObject.narrow(localObject1, OFX200CallbackHome.class);
            this._OFXTransactionHandler = ((OFX200CallbackHome)localObject2).create();
            localContextPool.returnContext(localContext);
          }
          catch (Exception localException)
          {
            localThrowable1.printStackTrace(System.out);
            this.objStatus.put("ServiceDown", "-1");
            if ((localContextPool != null) && (localContext != null)) {
              localContextPool.returnContext(localContext);
            }
          }
        }
      }
      if (this._OFXTransactionHandler != null) {
        return;
      }
    }
  }
  
  protected void removeHandler()
  {
    if ((this.useNoEJB) || (this._OFXTransactionHandler == null)) {
      return;
    }
    try
    {
      this.reloadHome = false;
      if (this.debugService) {
        DebugLog.log(Level.INFO, "##################### Removing Handler ################");
      }
      this._OFXTransactionHandler.remove();
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace(System.out);
      this.objStatus.put("ServiceDown", "-1");
    }
  }
  
  private void jdMethod_do(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    paramObjectInputStream.defaultReadObject();
    if (this.reloadHome == true) {
      getHandler();
    }
  }
  
  protected String getSettings()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "BASESERVICE");
    XMLHandler.appendTag(localStringBuffer, "ConnectionURL", this.url);
    XMLHandler.appendTag(localStringBuffer, "CertificatesDirectory", this.certsDirectory);
    XMLHandler.appendTag(localStringBuffer, "BankID", this.bankID);
    XMLHandler.appendTag(localStringBuffer, "BankName", this.bankName);
    XMLHandler.appendTag(localStringBuffer, "ApplicationID", this.applicationID);
    XMLHandler.appendTag(localStringBuffer, "ApplicationVersion", this.applicationVersion);
    XMLHandler.appendTag(localStringBuffer, "getLastError", getLastError());
    XMLHandler.appendTag(localStringBuffer, "getLastErrorMessage", getLastErrorMessage());
    XMLHandler.appendTag(localStringBuffer, "DebugService", new Boolean(this.debugService).toString());
    XMLHandler.appendTag(localStringBuffer, "OFX_TEST", new Boolean(this.OFX_Test).toString());
    XMLHandler.appendEndTag(localStringBuffer, "BASESERVICE");
    return localStringBuffer.toString();
  }
  
  public int signOn(String paramString1, String paramString2)
  {
    setUserID(paramString1);
    this.password = paramString2;
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".signOn:");
    }
    formatSignOnRequest();
    processOFXRequest();
    if (this.objStatus.get("ServiceDown") != null)
    {
      logError(4);
      return this.lastError;
    }
    if (this.status != 0)
    {
      logError(mapError(this.status));
      if (this.status != 15000)
      {
        setUserID(null);
        this.password = null;
      }
      return this.lastError;
    }
    return 0;
  }
  
  public int changePIN(String paramString1, String paramString2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (paramString1.equals(this.password))
    {
      if (this.debugService) {
        DebugLog.log(Level.INFO, getClass().getName() + ".changePIN:");
      }
      formatChangePinRequest(paramString2, localStringBuffer);
      processOFXRequest();
      String str = (String)this.savedValues.get("TRNUID");
      if (this.objStatus.get("ServiceDown") != null)
      {
        logError(4);
        return this.lastError;
      }
      if ((str != null) && (this.status == 0) && (str.equals(localStringBuffer.toString()))) {
        this.password = paramString2;
      }
      return 0;
    }
    logError(101);
    return this.lastError;
  }
  
  public int getAccounts(Accounts paramAccounts)
  {
    int i = 0;
    this.accounts = paramAccounts;
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".getAccounts:");
    }
    formatGetAccountsRequest();
    processOFXRequest();
    this.accounts = null;
    this.account = null;
    if (this.objStatus.get("ServiceDown") != null)
    {
      logError(4);
      return this.lastError;
    }
    if (this.status != 0)
    {
      logError(mapError(this.status));
      i = this.lastError;
    }
    jdMethod_if(paramAccounts);
    if ((this.lastError == 0) && (i == 0)) {
      return 0;
    }
    if (i != 0) {
      return i;
    }
    return this.lastError;
  }
  
  public int getAccount(Account paramAccount)
  {
    return 2;
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
  
  private int jdMethod_if(Accounts paramAccounts)
  {
    this.accounts = paramAccounts;
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".getBalances:");
    }
    formatGetBalancesRequest();
    processOFXRequest();
    if (this.debugService) {
      DebugLog.log(Level.INFO, XMLHandler.format(paramAccounts.toXML()));
    }
    this.accounts = null;
    this.account = null;
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
  
  protected void formatSignOnRequest()
  {
    this.rqmes = new TypeOFXRequest();
    this.rqmes.OFXRequest = new TypeOFXRqMsgSetsAggregate();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatSignOnRequest:");
    }
    formatSIGNONMSGSRQV1(this.rqmes.OFXRequest);
  }
  
  protected void formatGetAccountsRequest()
  {
    this.rqmes = new TypeOFXRequest();
    this.rqmes.OFXRequest = new TypeOFXRqMsgSetsAggregate();
    GregorianCalendar localGregorianCalendar = new GregorianCalendar();
    String str = DateFormatUtil.getFormatter("yyyyMMddHHmmss").format(localGregorianCalendar.getTime());
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatGetAccountsRequest:");
    }
    localGregorianCalendar.set(1989, 1, 1, 0, 0, 0);
    formatSIGNONMSGSRQV1(this.rqmes.OFXRequest);
    this.rqmes.OFXRequest.SignUpMsgsRqV1 = new TypeSignUpMsgsRqV1Aggregate();
    this.rqmes.OFXRequest.SignUpMsgsRqV1Exists = true;
    TypeSignUpMsgsRqV1Aggregate localTypeSignUpMsgsRqV1Aggregate = this.rqmes.OFXRequest.SignUpMsgsRqV1;
    localTypeSignUpMsgsRqV1Aggregate.SignUpMsgsRqUn = new TypeSignUpMsgsRqUn[1];
    localTypeSignUpMsgsRqV1Aggregate.SignUpMsgsRqUn[0] = new TypeSignUpMsgsRqUn();
    localTypeSignUpMsgsRqV1Aggregate.SignUpMsgsRqUn[0].__memberName = "AcctInfoTrnRq";
    localTypeSignUpMsgsRqV1Aggregate.SignUpMsgsRqUn[0].AcctInfoTrnRq = new TypeAcctInfoTrnRqV1Aggregate();
    TypeAcctInfoTrnRqV1Aggregate localTypeAcctInfoTrnRqV1Aggregate = localTypeSignUpMsgsRqV1Aggregate.SignUpMsgsRqUn[0].AcctInfoTrnRq;
    localTypeAcctInfoTrnRqV1Aggregate.AcctInfoRq = new TypeAcctInfoRqAggregate();
    localTypeAcctInfoTrnRqV1Aggregate.TrnRqCm = new TypeTrnRqCm();
    localTypeAcctInfoTrnRqV1Aggregate.TrnRqCm.TrnUID = getUniqueSeqNum();
    localTypeAcctInfoTrnRqV1Aggregate.AcctInfoRq.DtAcctUp = str;
  }
  
  protected void formatChangePinRequest(String paramString, StringBuffer paramStringBuffer)
  {
    this.rqmes = new TypeOFXRequest();
    this.rqmes.OFXRequest = new TypeOFXRqMsgSetsAggregate();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatChangePinRequest:");
    }
    paramStringBuffer.setLength(0);
    paramStringBuffer.append(getUniqueSeqNum());
    formatSIGNONMSGSRQV1(this.rqmes.OFXRequest);
    this.rqmes.OFXRequest.SignOnMsgsRqV1.PinChTrnRq = new TypePinChTrnRqV1Aggregate();
    this.rqmes.OFXRequest.SignOnMsgsRqV1.PinChTrnRqExists = true;
    TypePinChTrnRqV1Aggregate localTypePinChTrnRqV1Aggregate = this.rqmes.OFXRequest.SignOnMsgsRqV1.PinChTrnRq;
    localTypePinChTrnRqV1Aggregate.TrnRqCm = new TypeTrnRqCm();
    localTypePinChTrnRqV1Aggregate.TrnRqCm.TrnUID = paramStringBuffer.toString();
    localTypePinChTrnRqV1Aggregate.PinChRq = new TypePinChRqAggregate();
    localTypePinChTrnRqV1Aggregate.PinChRq.UserID = this.userID;
    localTypePinChTrnRqV1Aggregate.PinChRq.NewUserPass = paramString;
  }
  
  protected void formatGetBalancesRequest()
  {
    this.rqmes = new TypeOFXRequest();
    this.rqmes.OFXRequest = new TypeOFXRqMsgSetsAggregate();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatGetBalancesRequest:");
    }
    formatSIGNONMSGSRQV1(this.rqmes.OFXRequest);
    formatSTMTTRNRQ(this.accounts, this.rqmes.OFXRequest);
    formatCCSTMTTRNRQ(this.accounts, this.rqmes.OFXRequest);
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
    case 15000: 
      i = 102;
      break;
    case 15500: 
      i = 101;
      break;
    case 15501: 
      i = 5;
      break;
    case 15502: 
      i = 6;
      break;
    case 15503: 
      i = 2;
      break;
    case 15504: 
      i = 2;
      break;
    case 15505: 
      i = 2;
      break;
    case 15506: 
      i = 2;
      break;
    case 15507: 
      i = 2;
      break;
    default: 
      if (this.debugService) {
        DebugLog.log(Level.INFO, "mapError: unknown result code=" + paramInt + " returning ERROR_UNKNOWN");
      }
      i = 1;
    }
    return i;
  }
  
  protected void formatSIGNONMSGSRQV1(TypeOFXRqMsgSetsAggregate paramTypeOFXRqMsgSetsAggregate)
  {
    TimeZone localTimeZone = TimeZone.getTimeZone("UTC");
    GregorianCalendar localGregorianCalendar = new GregorianCalendar(localTimeZone);
    String str1 = DateFormatUtil.getFormatter("yyyyMMddHHmmss").format(localGregorianCalendar.getTime());
    paramTypeOFXRqMsgSetsAggregate.SignOnMsgsRqV1 = new TypeSignOnMsgsRqV1Aggregate();
    TypeSignOnMsgsRqV1Aggregate localTypeSignOnMsgsRqV1Aggregate = paramTypeOFXRqMsgSetsAggregate.SignOnMsgsRqV1;
    if (localTypeSignOnMsgsRqV1Aggregate != null)
    {
      localTypeSignOnMsgsRqV1Aggregate.SonRq = new TypeSonRqV1Aggregate();
      localTypeSignOnMsgsRqV1Aggregate.SonRq.DtClient = str1;
      localTypeSignOnMsgsRqV1Aggregate.SonRq.UserInfoUn = new TypeUserInfoUn();
      localTypeSignOnMsgsRqV1Aggregate.SonRq.UserInfoUn.__memberName = "UserCm";
      localTypeSignOnMsgsRqV1Aggregate.SonRq.UserInfoUn.UserCm = new TypeUserCm();
      localTypeSignOnMsgsRqV1Aggregate.SonRq.UserInfoUn.UserCm.UserId = this.userID;
      localTypeSignOnMsgsRqV1Aggregate.SonRq.UserInfoUn.UserCm.UserPass = this.password;
      localTypeSignOnMsgsRqV1Aggregate.SonRq.AppID = this.applicationID;
      localTypeSignOnMsgsRqV1Aggregate.SonRq.AppVer = this.applicationVersion;
      localTypeSignOnMsgsRqV1Aggregate.SonRq.DtClient = str1;
      localTypeSignOnMsgsRqV1Aggregate.SonRq.Language = "ENG";
      localTypeSignOnMsgsRqV1Aggregate.SonRq.FIExists = true;
      localTypeSignOnMsgsRqV1Aggregate.SonRq.FI = new TypeFIAggregate();
      localTypeSignOnMsgsRqV1Aggregate.SonRq.FI.FIDExists = true;
      localTypeSignOnMsgsRqV1Aggregate.SonRq.FI.FID = this.bankID;
      localTypeSignOnMsgsRqV1Aggregate.SonRq.FI.Org = this.bankName;
      this._ud._uid = this.userID;
      this._ud._org = this.bankName;
      this._ud._fid = this.bankID;
      String str2 = this.userID + "_" + System.currentTimeMillis();
      this._ud._sessionID = str2;
      this._ud._userDefined = str2;
    }
  }
  
  protected void formatCCSTMTTRNRQ(Accounts paramAccounts, TypeOFXRqMsgSetsAggregate paramTypeOFXRqMsgSetsAggregate)
  {
    int i = 0;
    int j = paramAccounts.size();
    for (int k = 0; k < j; k++) {
      if (((Account)paramAccounts.get(k)).getTypeValue() == 3) {
        i++;
      }
    }
    if (i != 0)
    {
      paramTypeOFXRqMsgSetsAggregate.CreditCardMsgsRqV1 = new TypeCreditCardMsgsRqV1Aggregate();
      paramTypeOFXRqMsgSetsAggregate.CreditCardMsgsRqV1Exists = true;
      paramTypeOFXRqMsgSetsAggregate.CreditCardMsgsRqV1.CreditCardMsgsRqV1Un = new TypeCreditCardMsgsRqV1Un[i];
      i = 0;
      for (k = 0; k < j; k++)
      {
        Account localAccount = (Account)paramAccounts.get(k);
        if (localAccount.getTypeValue() == 3)
        {
          paramTypeOFXRqMsgSetsAggregate.CreditCardMsgsRqV1.CreditCardMsgsRqV1Un[i] = new TypeCreditCardMsgsRqV1Un();
          paramTypeOFXRqMsgSetsAggregate.CreditCardMsgsRqV1.CreditCardMsgsRqV1Un[i].__memberName = "CCStmtTrnRq";
          TypeCCStmtTrnRqV1Aggregate localTypeCCStmtTrnRqV1Aggregate = new TypeCCStmtTrnRqV1Aggregate();
          paramTypeOFXRqMsgSetsAggregate.CreditCardMsgsRqV1.CreditCardMsgsRqV1Un[i].CCStmtTrnRq = localTypeCCStmtTrnRqV1Aggregate;
          localAccount.set("TRNUID", getUniqueSeqNum());
          localTypeCCStmtTrnRqV1Aggregate.TrnRqCm = new TypeTrnRqCm();
          localTypeCCStmtTrnRqV1Aggregate.TrnRqCm.TrnUID = ((String)localAccount.get("TRNUID"));
          localTypeCCStmtTrnRqV1Aggregate.CCStmtRq = new TypeCCStmtRqAggregate();
          TypeCCAcctFromAggregate localTypeCCAcctFromAggregate = new TypeCCAcctFromAggregate();
          formatCCACCTFROM(localTypeCCAcctFromAggregate, localAccount);
          localTypeCCStmtTrnRqV1Aggregate.CCStmtRq.CCAcctFrom = localTypeCCAcctFromAggregate;
          i++;
        }
      }
    }
  }
  
  protected void formatSTMTTRNRQ(Accounts paramAccounts, TypeOFXRqMsgSetsAggregate paramTypeOFXRqMsgSetsAggregate)
  {
    int i = 0;
    int j = paramAccounts.size();
    for (int k = 0; k < j; k++) {
      if (((Account)paramAccounts.get(k)).getTypeValue() != 3) {
        i++;
      }
    }
    if (i != 0)
    {
      paramTypeOFXRqMsgSetsAggregate.BankMsgsRqV1 = new TypeBankMsgsRqV1Aggregate();
      paramTypeOFXRqMsgSetsAggregate.BankMsgsRqV1Exists = true;
      paramTypeOFXRqMsgSetsAggregate.BankMsgsRqV1.BankMsgsRqUn = new TypeBankMsgsRqUn[i];
      i = 0;
      for (k = 0; k < j; k++)
      {
        Account localAccount = (Account)paramAccounts.get(k);
        if (localAccount.getTypeValue() != 3)
        {
          paramTypeOFXRqMsgSetsAggregate.BankMsgsRqV1.BankMsgsRqUn[i] = new TypeBankMsgsRqUn();
          paramTypeOFXRqMsgSetsAggregate.BankMsgsRqV1.BankMsgsRqUn[i].__memberName = "StmtTrnRq";
          TypeStmtTrnRqV1Aggregate localTypeStmtTrnRqV1Aggregate = new TypeStmtTrnRqV1Aggregate();
          paramTypeOFXRqMsgSetsAggregate.BankMsgsRqV1.BankMsgsRqUn[i].StmtTrnRq = localTypeStmtTrnRqV1Aggregate;
          localAccount.set("TRNUID", getUniqueSeqNum());
          localTypeStmtTrnRqV1Aggregate.TrnRqCm = new TypeTrnRqCm();
          localTypeStmtTrnRqV1Aggregate.TrnRqCm.TrnUID = ((String)localAccount.get("TRNUID"));
          localTypeStmtTrnRqV1Aggregate.StmtRq = new TypeStmtRqAggregate();
          TypeBankAcctFromAggregate localTypeBankAcctFromAggregate = new TypeBankAcctFromAggregate();
          formatBANKACCTFROM(localTypeBankAcctFromAggregate, localAccount);
          localTypeStmtTrnRqV1Aggregate.StmtRq.BankAcctFrom = localTypeBankAcctFromAggregate;
          i++;
        }
      }
    }
  }
  
  protected void formatFromAccount(TypeBCCAcctFromUn paramTypeBCCAcctFromUn, Account paramAccount)
  {
    if (paramAccount.getTypeValue() == 3)
    {
      paramTypeBCCAcctFromUn.__memberName = "CCAcctFrom";
      paramTypeBCCAcctFromUn.CCAcctFrom = new TypeCCAcctFromAggregate();
      formatCCACCTFROM(paramTypeBCCAcctFromUn.CCAcctFrom, paramAccount);
    }
    else
    {
      paramTypeBCCAcctFromUn.__memberName = "BankAcctFrom";
      paramTypeBCCAcctFromUn.BankAcctFrom = new TypeBankAcctFromAggregate();
      formatBANKACCTFROM(paramTypeBCCAcctFromUn.BankAcctFrom, paramAccount);
    }
  }
  
  protected void formatToAccount(TypeBCCAcctToUn paramTypeBCCAcctToUn, Account paramAccount)
  {
    if (paramAccount.getTypeValue() == 3)
    {
      paramTypeBCCAcctToUn.__memberName = "CCAcctTo";
      paramTypeBCCAcctToUn.CCAcctTo = new TypeCCAcctToAggregate();
      formatCCACCTTO(paramTypeBCCAcctToUn.CCAcctTo, paramAccount);
    }
    else
    {
      paramTypeBCCAcctToUn.__memberName = "BankAcctTo";
      paramTypeBCCAcctToUn.BankAcctTo = new TypeBankAcctToAggregate();
      formatBANKACCTTO(paramTypeBCCAcctToUn.BankAcctTo, paramAccount);
    }
  }
  
  protected void formatBANKACCTFROM(TypeBankAcctFromAggregate paramTypeBankAcctFromAggregate, Account paramAccount)
  {
    paramTypeBankAcctFromAggregate.BankID = paramAccount.getBankID();
    paramTypeBankAcctFromAggregate.AcctID = paramAccount.getNumber();
    paramTypeBankAcctFromAggregate.AcctType = getAccountType(paramAccount.getTypeValue());
  }
  
  protected void formatBANKACCTTO(TypeBankAcctToAggregate paramTypeBankAcctToAggregate, Account paramAccount)
  {
    paramTypeBankAcctToAggregate.BankID = paramAccount.getBankID();
    paramTypeBankAcctToAggregate.AcctID = paramAccount.getNumber();
    paramTypeBankAcctToAggregate.AcctType = getAccountType(paramAccount.getTypeValue());
  }
  
  protected void formatCCACCTFROM(TypeCCAcctFromAggregate paramTypeCCAcctFromAggregate, Account paramAccount)
  {
    paramTypeCCAcctFromAggregate.AcctID = paramAccount.getNumber();
  }
  
  protected void formatCCACCTTO(TypeCCAcctToAggregate paramTypeCCAcctToAggregate, Account paramAccount)
  {
    paramTypeCCAcctToAggregate.AcctID = paramAccount.getNumber();
  }
  
  protected TypeRecurrInstAggregate formatRECURRINST(int paramInt1, int paramInt2)
  {
    TypeRecurrInstAggregate localTypeRecurrInstAggregate = new TypeRecurrInstAggregate();
    localTypeRecurrInstAggregate.Freq = getFrequency(paramInt1);
    if (paramInt2 < 750)
    {
      localTypeRecurrInstAggregate.NInsts = paramInt2;
      localTypeRecurrInstAggregate.NInstsExists = true;
    }
    return localTypeRecurrInstAggregate;
  }
  
  protected abstract void processOFXRequest();
  
  protected void processTransactionsInSignOnMsgsRqV1(TypeSignOnMsgsRqV1Aggregate paramTypeSignOnMsgsRqV1Aggregate)
  {
    TypeSonRsV1 localTypeSonRsV1 = null;
    TypeSonRqV1 localTypeSonRqV1 = new TypeSonRqV1(paramTypeSignOnMsgsRqV1Aggregate.SonRq);
    localTypeSonRsV1 = processSonRqV1(localTypeSonRqV1);
    processSONRS(localTypeSonRsV1);
    if (paramTypeSignOnMsgsRqV1Aggregate.PinChTrnRqExists)
    {
      TypePinChTrnRsV1 localTypePinChTrnRsV1 = null;
      TypePinChTrnRqV1 localTypePinChTrnRqV1 = new TypePinChTrnRqV1(paramTypeSignOnMsgsRqV1Aggregate.PinChTrnRq);
      localTypePinChTrnRsV1 = processPinChTrnRqV1(localTypePinChTrnRqV1);
      if (localTypePinChTrnRsV1 != null) {
        processPINCHTRNRS(localTypePinChTrnRsV1.PinChTrnRs);
      }
    }
  }
  
  protected void processSONRS(TypeSonRsV1 paramTypeSonRsV1)
  {
    this.signonProcessed = false;
    if ((paramTypeSonRsV1 != null) && (paramTypeSonRsV1.SonRs != null))
    {
      processSTATUS(paramTypeSonRsV1.SonRs.Status);
      if (paramTypeSonRsV1.SonRs.Status.Code == 0) {
        this.signonProcessed = true;
      }
    }
  }
  
  protected void processSTATUS(TypeStatusAggregate paramTypeStatusAggregate)
  {
    this.status = paramTypeStatusAggregate.Code;
    if (paramTypeStatusAggregate.MessageExists)
    {
      logError(paramTypeStatusAggregate.Message);
      if (this.debugService) {
        DebugLog.log(Level.INFO, "ProcessSTATUS message = " + paramTypeStatusAggregate.Message);
      }
    }
  }
  
  protected void processPINCHTRNRS(TypePinChTrnRsV1Aggregate paramTypePinChTrnRsV1Aggregate)
  {
    if (paramTypePinChTrnRsV1Aggregate.PinChRsExists) {}
    processSTATUS(paramTypePinChTrnRsV1Aggregate.TrnRsCm.Status);
    this.savedValues.put("TRNUID", paramTypePinChTrnRsV1Aggregate.TrnRsCm.TrnUID);
  }
  
  protected void processTransactionsInSignUpMsgsRqV1(TypeSignUpMsgsRqV1Aggregate paramTypeSignUpMsgsRqV1Aggregate)
  {
    for (int i = 0; i < paramTypeSignUpMsgsRqV1Aggregate.SignUpMsgsRqUn.length; i++) {
      if (paramTypeSignUpMsgsRqV1Aggregate.SignUpMsgsRqUn[i].__memberName.equals("AcctInfoTrnRq"))
      {
        TypeAcctInfoTrnRqV1 localTypeAcctInfoTrnRqV1 = new TypeAcctInfoTrnRqV1(paramTypeSignUpMsgsRqV1Aggregate.SignUpMsgsRqUn[i].AcctInfoTrnRq);
        TypeAcctInfoTrnRsV1 localTypeAcctInfoTrnRsV1 = null;
        localTypeAcctInfoTrnRsV1 = processAcctInfoTrnRqV1(localTypeAcctInfoTrnRqV1);
        if (localTypeAcctInfoTrnRsV1 != null) {
          processACCTINFOTRNRS(localTypeAcctInfoTrnRsV1.AcctInfoTrnRs);
        }
      }
    }
  }
  
  protected void processACCTINFOTRNRS(TypeAcctInfoTrnRsV1Aggregate paramTypeAcctInfoTrnRsV1Aggregate)
  {
    processSTATUS(paramTypeAcctInfoTrnRsV1Aggregate.TrnRsCm.Status);
    if (paramTypeAcctInfoTrnRsV1Aggregate.AcctInfoRsExists)
    {
      String str = null;
      TypeAcctInfoRsAggregate localTypeAcctInfoRsAggregate = paramTypeAcctInfoTrnRsV1Aggregate.AcctInfoRs;
      int i = localTypeAcctInfoRsAggregate.AcctInfo.length;
      TypeAcctInfoAggregate[] arrayOfTypeAcctInfoAggregate = localTypeAcctInfoRsAggregate.AcctInfo;
      for (int j = 0; j < i; j++)
      {
        str = null;
        if (arrayOfTypeAcctInfoAggregate[j].DescExists) {
          str = arrayOfTypeAcctInfoAggregate[j].Desc;
        }
        processAcctInfoUn(arrayOfTypeAcctInfoAggregate[j].AcctInfoUn);
        if ((this.account != null) && (str != null)) {
          this.account.setNickName(str);
        }
        if (this.debugService) {
          DebugLog.log(Level.INFO, XMLHandler.format(this.account.toXML()));
        }
      }
    }
  }
  
  protected void processServiceStatusEnum(EnumServiceStatusEnum paramEnumServiceStatusEnum)
  {
    if (paramEnumServiceStatusEnum.value() == 1) {
      this.account.setStatus(0);
    } else if (paramEnumServiceStatusEnum.value() == 2) {
      this.account.setStatus(1);
    } else if (paramEnumServiceStatusEnum.value() == 0) {
      this.account.setStatus(2);
    }
  }
  
  protected void processAcctInfoUn(TypeAcctInfoUn[] paramArrayOfTypeAcctInfoUn)
  {
    for (int i = 0; i < paramArrayOfTypeAcctInfoUn.length; i++)
    {
      String str = null;
      int j = 0;
      this.account = null;
      EnumAccountEnum localEnumAccountEnum;
      if (paramArrayOfTypeAcctInfoUn[i].__memberName.equals("BankAcctInfo"))
      {
        str = paramArrayOfTypeAcctInfoUn[i].BankAcctInfo.BankAcctFrom.AcctID;
        localEnumAccountEnum = paramArrayOfTypeAcctInfoUn[i].BankAcctInfo.BankAcctFrom.AcctType;
        j = getAccountType(localEnumAccountEnum);
        this.account = this.accounts.getByAccountNumberAndType(str, j);
        if (this.account == null) {
          this.account = this.accounts.create(str, j);
        }
        this.account.setBankID(paramArrayOfTypeAcctInfoUn[i].BankAcctInfo.BankAcctFrom.BankID);
        if (paramArrayOfTypeAcctInfoUn[i].BankAcctInfo.SupTXDL) {
          this.account.setFilterable("Transactions");
        }
        if (paramArrayOfTypeAcctInfoUn[i].BankAcctInfo.XferDest) {
          this.account.setFilterable("TransferTo");
        }
        if (paramArrayOfTypeAcctInfoUn[i].BankAcctInfo.XferSrc) {
          this.account.setFilterable("TransferFrom");
        }
        processServiceStatusEnum(paramArrayOfTypeAcctInfoUn[i].BankAcctInfo.SvcStatus);
      }
      else if (paramArrayOfTypeAcctInfoUn[i].__memberName.equals("BPAcctInfo"))
      {
        str = paramArrayOfTypeAcctInfoUn[i].BPAcctInfo.BankAcctFrom.AcctID;
        localEnumAccountEnum = paramArrayOfTypeAcctInfoUn[i].BPAcctInfo.BankAcctFrom.AcctType;
        j = getAccountType(localEnumAccountEnum);
        this.account = this.accounts.getByAccountNumberAndType(str, j);
        if (this.account == null) {
          this.account = this.accounts.create(str, j);
        }
        this.account.setBankID(paramArrayOfTypeAcctInfoUn[i].BPAcctInfo.BankAcctFrom.BankID);
        EnumServiceStatusEnum localEnumServiceStatusEnum = paramArrayOfTypeAcctInfoUn[i].BPAcctInfo.SvcStatus;
        processServiceStatusEnum(localEnumServiceStatusEnum);
        if (localEnumServiceStatusEnum.value() == 0) {
          this.account.setFilterable("BillPay");
        }
      }
      else if (paramArrayOfTypeAcctInfoUn[i].__memberName.equals("CCAcctInfo"))
      {
        str = paramArrayOfTypeAcctInfoUn[i].CCAcctInfo.CCAcctFrom.AcctID;
        j = 3;
        this.account = this.accounts.getByAccountNumberAndType(str, j);
        if (this.account == null) {
          this.account = this.accounts.create(str, j);
        }
        processServiceStatusEnum(paramArrayOfTypeAcctInfoUn[i].CCAcctInfo.SvcStatus);
        if (paramArrayOfTypeAcctInfoUn[i].CCAcctInfo.SupTXDL) {
          this.account.setFilterable("Transactions");
        }
        if (paramArrayOfTypeAcctInfoUn[i].CCAcctInfo.XferDest) {
          this.account.setFilterable("TransferTo");
        }
        if (paramArrayOfTypeAcctInfoUn[i].CCAcctInfo.XferSrc) {
          this.account.setFilterable("TransferFrom");
        }
      }
      else if ((paramArrayOfTypeAcctInfoUn[i].__memberName.equals("InvAcctInfo")) || (!paramArrayOfTypeAcctInfoUn[i].__memberName.equals("PresAcctInfo"))) {}
    }
  }
  
  protected void processStmtTrnRs(TypeStmtTrnRsV1Aggregate paramTypeStmtTrnRsV1Aggregate)
  {
    processSTATUS(paramTypeStmtTrnRsV1Aggregate.TrnRsCm.Status);
    if (paramTypeStmtTrnRsV1Aggregate.StmtRsExists)
    {
      String str = paramTypeStmtTrnRsV1Aggregate.StmtRs.BankAcctFrom.AcctID;
      EnumAccountEnum localEnumAccountEnum = paramTypeStmtTrnRsV1Aggregate.StmtRs.BankAcctFrom.AcctType;
      int i = getAccountType(localEnumAccountEnum);
      if (this.accounts != null) {
        this.account = this.accounts.getByAccountNumberAndType(str, i);
      }
      if (this.account.getCurrentBalance() == null) {
        this.account.setCurrentBalance(new Balance(this.account.getLocale()));
      }
      this.account.getCurrentBalance().setAmount(Double.toString(paramTypeStmtTrnRsV1Aggregate.StmtRs.LedgerBal.BalAmt));
      this.account.getCurrentBalance().setDate(getDate(paramTypeStmtTrnRsV1Aggregate.StmtRs.LedgerBal.DtAsOf));
      if (this.account.getAvailableBalance() == null) {
        this.account.setAvailableBalance(new Balance(this.account.getLocale()));
      }
      if (paramTypeStmtTrnRsV1Aggregate.StmtRs.AvailBalExists)
      {
        this.account.getAvailableBalance().setAmount(Double.toString(paramTypeStmtTrnRsV1Aggregate.StmtRs.AvailBal.BalAmt));
        this.account.getAvailableBalance().setDate(getDate(paramTypeStmtTrnRsV1Aggregate.StmtRs.AvailBal.DtAsOf));
      }
    }
  }
  
  protected void processTransactionsInBankMsgsRqV1(TypeBankMsgsRqV1Aggregate paramTypeBankMsgsRqV1Aggregate)
  {
    for (int i = 0; i < paramTypeBankMsgsRqV1Aggregate.BankMsgsRqUn.length; i++) {
      if (paramTypeBankMsgsRqV1Aggregate.BankMsgsRqUn[i].__memberName.equals("StmtTrnRq"))
      {
        TypeStmtTrnRqV1 localTypeStmtTrnRqV1 = new TypeStmtTrnRqV1(paramTypeBankMsgsRqV1Aggregate.BankMsgsRqUn[i].StmtTrnRq);
        TypeStmtTrnRsV1 localTypeStmtTrnRsV1 = null;
        localTypeStmtTrnRsV1 = processStmtTrnRqV1(localTypeStmtTrnRqV1);
        if (localTypeStmtTrnRsV1 != null) {
          processStmtTrnRs(localTypeStmtTrnRsV1.StmtTrnRs);
        }
      }
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
  
  protected void processCCStmtTrnRs(TypeCCStmtTrnRsV1Aggregate paramTypeCCStmtTrnRsV1Aggregate)
  {
    processSTATUS(paramTypeCCStmtTrnRsV1Aggregate.TrnRsCm.Status);
    if (paramTypeCCStmtTrnRsV1Aggregate.CCStmtRsExists)
    {
      String str = paramTypeCCStmtTrnRsV1Aggregate.CCStmtRs.CCAcctFrom.AcctID;
      int i = 3;
      if (this.accounts != null) {
        this.account = this.accounts.getByAccountNumberAndType(str, i);
      }
      if (this.account.getCurrentBalance() == null) {
        this.account.setCurrentBalance(new Balance(this.account.getLocale()));
      }
      this.account.getCurrentBalance().setAmount(Double.toString(paramTypeCCStmtTrnRsV1Aggregate.CCStmtRs.LedgerBal.BalAmt));
      this.account.getCurrentBalance().setDate(getDate(paramTypeCCStmtTrnRsV1Aggregate.CCStmtRs.LedgerBal.DtAsOf));
      if (this.account.getAvailableBalance() == null) {
        this.account.setAvailableBalance(new Balance(this.account.getLocale()));
      }
      if (paramTypeCCStmtTrnRsV1Aggregate.CCStmtRs.AvailBalExists)
      {
        this.account.getAvailableBalance().setAmount(Double.toString(paramTypeCCStmtTrnRsV1Aggregate.CCStmtRs.AvailBal.BalAmt));
        this.account.getAvailableBalance().setDate(getDate(paramTypeCCStmtTrnRsV1Aggregate.CCStmtRs.AvailBal.DtAsOf));
      }
    }
  }
  
  protected Account processBCCAcctFromUn(TypeBCCAcctFromUn paramTypeBCCAcctFromUn, Accounts paramAccounts)
  {
    String str = null;
    Account localAccount = null;
    int i;
    if (paramTypeBCCAcctFromUn.__memberName.equals("BankAcctFrom"))
    {
      str = paramTypeBCCAcctFromUn.BankAcctFrom.AcctID;
      EnumAccountEnum localEnumAccountEnum = paramTypeBCCAcctFromUn.BankAcctFrom.AcctType;
      i = getAccountType(localEnumAccountEnum);
      localAccount = paramAccounts.getByAccountNumberAndType(str, i);
      if (localAccount == null) {
        localAccount = paramAccounts.create(str, i);
      }
    }
    else if (paramTypeBCCAcctFromUn.__memberName.equals("CCAcctFrom"))
    {
      str = paramTypeBCCAcctFromUn.CCAcctFrom.AcctID;
      i = 3;
      localAccount = paramAccounts.getByAccountNumberAndType(str, i);
      if (localAccount == null) {
        localAccount = paramAccounts.create(str, i);
      }
    }
    return localAccount;
  }
  
  protected Account processBCCAcctToUn(TypeBCCAcctToUn paramTypeBCCAcctToUn, Accounts paramAccounts)
  {
    String str = null;
    Account localAccount = null;
    int i;
    if (paramTypeBCCAcctToUn.__memberName.equals("BankAcctTo"))
    {
      str = paramTypeBCCAcctToUn.BankAcctTo.AcctID;
      EnumAccountEnum localEnumAccountEnum = paramTypeBCCAcctToUn.BankAcctTo.AcctType;
      i = getAccountType(localEnumAccountEnum);
      localAccount = paramAccounts.getByAccountNumberAndType(str, i);
      if (localAccount == null) {
        localAccount = paramAccounts.create(str, i);
      }
    }
    else if (paramTypeBCCAcctToUn.__memberName.equals("CCAcctTo"))
    {
      str = paramTypeBCCAcctToUn.CCAcctTo.AcctID;
      i = 3;
      localAccount = paramAccounts.getByAccountNumberAndType(str, i);
      if (localAccount == null) {
        localAccount = paramAccounts.create(str, i);
      }
    }
    return localAccount;
  }
  
  protected TypeAcctInfoTrnRsV1 processAcctInfoTrnRqV1(TypeAcctInfoTrnRqV1 paramTypeAcctInfoTrnRqV1)
  {
    try
    {
      if (this._OFXTransactionBean != null) {
        return this._OFXTransactionBean.processAcctInfoTrnRqV1(paramTypeAcctInfoTrnRqV1, this._ud);
      }
      return this._OFXTransactionHandler.processAcctInfoTrnRqV1(paramTypeAcctInfoTrnRqV1, this._ud);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception in processAcctInfoTrnRqV1", localException);
    }
    return null;
  }
  
  protected TypeStmtTrnRsV1 processStmtTrnRqV1(TypeStmtTrnRqV1 paramTypeStmtTrnRqV1)
  {
    try
    {
      if (this._OFXTransactionBean != null) {
        return this._OFXTransactionBean.processStmtTrnRqV1(paramTypeStmtTrnRqV1, this._ud);
      }
      return this._OFXTransactionHandler.processStmtTrnRqV1(paramTypeStmtTrnRqV1, this._ud);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception in processStmtTrnRqV1", localException);
    }
    return null;
  }
  
  protected TypeCCStmtTrnRsV1 processCCStmtTrnRqV1(TypeCCStmtTrnRqV1 paramTypeCCStmtTrnRqV1)
  {
    try
    {
      if (this._OFXTransactionBean != null) {
        return this._OFXTransactionBean.processCCStmtTrnRqV1(paramTypeCCStmtTrnRqV1, this._ud);
      }
      return this._OFXTransactionHandler.processCCStmtTrnRqV1(paramTypeCCStmtTrnRqV1, this._ud);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception in processCCStmtTrnRqV1", localException);
    }
    return null;
  }
  
  protected TypeSonRsV1 processSonRqV1(TypeSonRqV1 paramTypeSonRqV1)
  {
    try
    {
      if (this._OFXTransactionBean != null) {
        return this._OFXTransactionBean.processSonRqV1(paramTypeSonRqV1, this._ud);
      }
      return this._OFXTransactionHandler.processSonRqV1(paramTypeSonRqV1, this._ud);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception in processSonRqV1", localException);
    }
    return null;
  }
  
  protected TypePinChTrnRsV1 processPinChTrnRqV1(TypePinChTrnRqV1 paramTypePinChTrnRqV1)
  {
    try
    {
      if (this._OFXTransactionBean != null) {
        return this._OFXTransactionBean.processPinChTrnRqV1(paramTypePinChTrnRqV1, this._ud);
      }
      return this._OFXTransactionHandler.processPinChTrnRqV1(paramTypePinChTrnRqV1, this._ud);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception in processPinChTrnRqV1", localException);
    }
    return null;
  }
  
  protected TypeDisconnectRs processDisconnectRq(TypeDisconnectRq paramTypeDisconnectRq)
  {
    try
    {
      if (this._OFXTransactionBean != null) {
        return this._OFXTransactionBean.processDisconnectRq(paramTypeDisconnectRq, this._ud);
      }
      return this._OFXTransactionHandler.processDisconnectRq(paramTypeDisconnectRq, this._ud);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception in processDisconnectRq", localException);
    }
    return null;
  }
  
  public EnumAccountEnum getAccountType(int paramInt)
  {
    for (int i = 0; i < Defines.acctTypes.length; i++) {
      if (paramInt == Defines.acctMap[i]) {
        return Defines.acctTypes[i];
      }
    }
    if (this.debugService) {
      DebugLog.log(Level.INFO, "NON-Standard getAccountType() = " + paramInt);
    }
    throw new IllegalArgumentException("getAccountType: type = " + paramInt);
  }
  
  public int getAccountType(EnumAccountEnum paramEnumAccountEnum)
  {
    for (int i = 0; i < Defines.acctTypes.length; i++) {
      if (Defines.acctTypes[i].value() == paramEnumAccountEnum.value()) {
        return Defines.acctMap[i];
      }
    }
    if (this.debugService) {
      DebugLog.log(Level.INFO, "NON-Standard getAccountType() EnumAccountEnum = " + paramEnumAccountEnum.value());
    }
    throw new IllegalArgumentException("getAccountType: EnumAccountEnum=" + paramEnumAccountEnum.value());
  }
  
  public int getFrequency(EnumFreqEnum paramEnumFreqEnum)
  {
    for (int i = 0; i < Defines.FreqEnums.length; i++) {
      if (paramEnumFreqEnum.value() == Defines.FreqEnums[i].value()) {
        return Defines.FreqMap[i];
      }
    }
    if (this.debugService) {
      DebugLog.log(Level.INFO, "NON-Standard getFrequency() EnumFreqEnum = " + paramEnumFreqEnum.value());
    }
    return -1;
  }
  
  public EnumFreqEnum getFrequency(int paramInt)
  {
    for (int i = 0; i < Defines.FreqMap.length; i++) {
      if (paramInt == Defines.FreqMap[i]) {
        return Defines.FreqEnums[i];
      }
    }
    if (this.debugService) {
      DebugLog.log(Level.INFO, "NON-Standard getFrequency() freq = " + paramInt);
    }
    return null;
  }
  
  public class a
    extends HandlerBase
  {
    public a() {}
    
    public void attribute(String paramString1, String paramString2, boolean paramBoolean)
    {
      if (paramString1.equals("ConnectionURL"))
      {
        Base.this.url = paramString2;
      }
      else if (paramString1.equals("CertificatesDirectory"))
      {
        Base.this.certsDirectory = paramString2;
      }
      else if (paramString1.equals("BankID"))
      {
        Base.this.bankID = paramString2;
      }
      else if (paramString1.equals("BankName"))
      {
        Base.this.bankName = paramString2;
      }
      else if (paramString1.equals("ApplicationID"))
      {
        Base.this.applicationID = paramString2;
      }
      else if (paramString1.equals("ApplicationVersion"))
      {
        Base.this.applicationVersion = Integer.parseInt(paramString2);
      }
      else
      {
        Object localObject;
        String str;
        if (paramString1.equals("CallBackBeanURL"))
        {
          localObject = new StringTokenizer(paramString2);
          while (((StringTokenizer)localObject).hasMoreTokens())
          {
            str = ((StringTokenizer)localObject).nextToken(",");
            Base.this.provider_url_list.add(str);
          }
          Base.this.provider_url = paramString2;
        }
        else if (paramString1.equals("ContextFactory"))
        {
          Base.this.context_factory = paramString2;
        }
        else if (paramString1.equals("CallBackJNDIName"))
        {
          Base.this.callback_JNDI_name = paramString2;
        }
        else if (paramString1.equals("ContextUserName"))
        {
          Base.this.context_username = paramString2;
        }
        else if (paramString1.equals("ContextPassword"))
        {
          Base.this.context_password = paramString2;
        }
        else if (paramString1.equals("PrincipalClass"))
        {
          Base.this.principalClass = paramString2;
        }
        else if (paramString1.equals("UseEJB"))
        {
          Base.this.useNoEJB = (!Boolean.valueOf(paramString2).booleanValue());
        }
        else if (paramString1.equals("DebugService"))
        {
          Base.this.debugService = Boolean.valueOf(paramString2).booleanValue();
        }
        else if (paramString1.equals("ContextProperty"))
        {
          if (Base.this.contextProperty == null) {
            Base.this.contextProperty = new HashMap();
          }
          localObject = null;
          str = null;
          int i = paramString2.indexOf("=");
          if (i > 0)
          {
            localObject = paramString2.substring(0, i);
            str = paramString2.substring(i + 1);
            Base.this.contextProperty.put(localObject, str);
          }
        }
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.FFServer.OFX200.Base
 * JD-Core Version:    0.7.0.1
 */