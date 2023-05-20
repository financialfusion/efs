package com.ffusion.services.FFServer.OFX151;

import com.ffusion.beans.Balance;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.ffs.ofx.interfaces.TypeUserData;
import com.ffusion.msgbroker.generated.MessageBroker.api.OFX151.Callback.IOFX151Callback;
import com.ffusion.msgbroker.generated.MessageBroker.api.OFX151.Callback.OFX151CallbackBean;
import com.ffusion.msgbroker.generated.MessageBroker.api.OFX151.Callback.OFX151CallbackHome;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumAccountEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumFreqEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumServiceStatusEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeAcctInfoRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeAcctInfoRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeAcctInfoTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeAcctInfoTrnRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeAcctInfoTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeAcctInfoTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeAcctInfoV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeAcctInfoV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeAvailBalAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBCCAcctFromV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBCCAcctToV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBPAcctInfoV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBankAcctFromV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBankAcctInfoV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBankAcctToV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBankMsgsRqUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBankMsgsRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBankMsgsRqV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeCCAcctFromAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeCCAcctInfoV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeCCAcctToAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeCCStmtRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeCCStmtRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeCCStmtTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeCCStmtTrnRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeCCStmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeCCStmtTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeCreditCardMsgsRqUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeCreditCardMsgsRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeCreditCardMsgsRqV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeDisconnectRq;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeDisconnectRs;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeFIAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeLedgerBalAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeOFXRequest;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeOFXRqMsgSetsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePinChRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePinChTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePinChTrnRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePinChTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePinChTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecurrInstAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSignOnMsgsRqUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSignOnMsgsRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSignUpMsgsRqUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSignUpMsgsRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSignUpMsgsRqV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSonRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSonRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSonRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSonRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStatusV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStmtRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStmtRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStmtTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStmtTrnRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStmtTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTrnRqCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTrnRsV1Cm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeUserInfoV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeUserV1Cm;
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
import java.util.TimeZone;
import java.util.logging.Level;
import javax.naming.Context;
import javax.rmi.PortableRemoteObject;

public abstract class Base
  extends Core
  implements SignOn2, Defines, AccountService
{
  private static final String be = "ConnectionURL";
  private static final String a1 = "CertificatesDirectory";
  private static final String a7 = "BankID";
  private static final String bc = "BankName";
  private static final String a2 = "ApplicationID";
  private static final String a4 = "ApplicationVersion";
  private static final String a6 = "CallBackBeanURL";
  private static final String a0 = "ContextFactory";
  private static final String a5 = "CallBackJNDIName";
  private static final String ba = "ContextUserName";
  private static final String a9 = "ContextPassword";
  private static final String bd = "ContextProperty";
  private static final String aZ = "PrincipalClass";
  private static final String a3 = "UseEJB";
  private static final String a8 = "DebugService";
  protected final String SERVICE_DOWN = "ServiceDown";
  protected final int SERVICE_DOWN_ERROR = 4;
  protected String url;
  protected String certsDirectory;
  protected String applicationID;
  protected int applicationVersion;
  protected String bankID;
  protected String bankName;
  protected String provider_url;
  protected String context_factory;
  protected String callback_JNDI_name = "OFX151.OFX151CallbackBean";
  protected String context_username = "system";
  protected String context_password = "weblogic";
  protected String principalClass = "com.ffusion.services.FFServer.iPlanet";
  protected HashMap contextProperty = null;
  protected Account account;
  protected Accounts accounts;
  protected transient IOFX151Callback _OFXTransactionHandler = null;
  protected boolean reloadHome = false;
  protected boolean signonProcessed = false;
  protected OFX151CallbackBean _OFXTransactionBean = null;
  protected boolean useNoEJB = false;
  protected TypeUserData _ud;
  private static ContextPoolList bb = null;
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
        this._OFXTransactionBean = new OFX151CallbackBean();
      } else if (bb == null) {
        bb = new ContextPoolList();
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
    try
    {
      OFX151CallbackHome localOFX151CallbackHome = null;
      localContextPool = bb.getContextPool(this.provider_url);
      if (localContextPool == null) {
        try
        {
          localContextPool = new ContextPool(this.provider_url, this.context_username, this.context_password, this.context_factory);
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
          bb.add(localContextPool);
        }
        catch (Throwable localThrowable2)
        {
          DebugLog.throwing("Exception in getHandler", localThrowable2);
        }
      }
      if (this.debugService) {
        DebugLog.log(Level.INFO, "provider_url = " + this.provider_url);
      }
      localContext = localContextPool.getContext();
      localObject1 = localContext.lookup(this.callback_JNDI_name);
      localOFX151CallbackHome = (OFX151CallbackHome)PortableRemoteObject.narrow(localObject1, OFX151CallbackHome.class);
      this._OFXTransactionHandler = localOFX151CallbackHome.create();
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
          localObject2 = (OFX151CallbackHome)PortableRemoteObject.narrow(localObject1, OFX151CallbackHome.class);
          this._OFXTransactionHandler = ((OFX151CallbackHome)localObject2).create();
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
  
  private void jdMethod_if(ObjectInputStream paramObjectInputStream)
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
    a(paramAccounts);
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
  
  private int a(Accounts paramAccounts)
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
    this.rqmes.OFXRequest.SignUpMsgsRqUn = new TypeSignUpMsgsRqUn();
    this.rqmes.OFXRequest.SignUpMsgsRqUnExists = true;
    this.rqmes.OFXRequest.SignUpMsgsRqUn.__memberName = "SignUpMsgsRqV1";
    this.rqmes.OFXRequest.SignUpMsgsRqUn.SignUpMsgsRqV1 = new TypeSignUpMsgsRqV1Aggregate();
    this.rqmes.OFXRequest.SignUpMsgsRqUn.SignUpMsgsRqV1.SignUpMsgsRqV1Un = new TypeSignUpMsgsRqV1Un[1];
    this.rqmes.OFXRequest.SignUpMsgsRqUn.SignUpMsgsRqV1.SignUpMsgsRqV1Un[0] = new TypeSignUpMsgsRqV1Un();
    this.rqmes.OFXRequest.SignUpMsgsRqUn.SignUpMsgsRqV1.SignUpMsgsRqV1Un[0].__memberName = "AcctInfoTrnRq";
    TypeAcctInfoTrnRqV1Aggregate localTypeAcctInfoTrnRqV1Aggregate = new TypeAcctInfoTrnRqV1Aggregate();
    this.rqmes.OFXRequest.SignUpMsgsRqUn.SignUpMsgsRqV1.SignUpMsgsRqV1Un[0].AcctInfoTrnRq = localTypeAcctInfoTrnRqV1Aggregate;
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
    this.rqmes.OFXRequest.SignOnMsgsRqUn.SignOnMsgsRqV1.PinChTrnRq = new TypePinChTrnRqV1Aggregate();
    this.rqmes.OFXRequest.SignOnMsgsRqUn.SignOnMsgsRqV1.PinChTrnRqExists = true;
    TypePinChTrnRqV1Aggregate localTypePinChTrnRqV1Aggregate = this.rqmes.OFXRequest.SignOnMsgsRqUn.SignOnMsgsRqV1.PinChTrnRq;
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
    paramTypeOFXRqMsgSetsAggregate.SignOnMsgsRqUn = new TypeSignOnMsgsRqUn();
    paramTypeOFXRqMsgSetsAggregate.SignOnMsgsRqUn.__memberName = "SignOnMsgsRqV1";
    paramTypeOFXRqMsgSetsAggregate.SignOnMsgsRqUn.SignOnMsgsRqV1 = new TypeSignOnMsgsRqV1Aggregate();
    TypeSignOnMsgsRqV1Aggregate localTypeSignOnMsgsRqV1Aggregate = paramTypeOFXRqMsgSetsAggregate.SignOnMsgsRqUn.SignOnMsgsRqV1;
    if (localTypeSignOnMsgsRqV1Aggregate != null)
    {
      localTypeSignOnMsgsRqV1Aggregate.SonRq = new TypeSonRqV1Aggregate();
      localTypeSignOnMsgsRqV1Aggregate.SonRq.DtClient = str1;
      localTypeSignOnMsgsRqV1Aggregate.SonRq.UserInfoV1Un = new TypeUserInfoV1Un();
      localTypeSignOnMsgsRqV1Aggregate.SonRq.UserInfoV1Un.__memberName = "UserV1Cm";
      localTypeSignOnMsgsRqV1Aggregate.SonRq.UserInfoV1Un.UserV1Cm = new TypeUserV1Cm();
      localTypeSignOnMsgsRqV1Aggregate.SonRq.UserInfoV1Un.UserV1Cm.UserId = this.userID;
      localTypeSignOnMsgsRqV1Aggregate.SonRq.UserInfoV1Un.UserV1Cm.UserPass = this.password;
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
      paramTypeOFXRqMsgSetsAggregate.CreditCardMsgsRqUn = new TypeCreditCardMsgsRqUn();
      paramTypeOFXRqMsgSetsAggregate.CreditCardMsgsRqUnExists = true;
      paramTypeOFXRqMsgSetsAggregate.CreditCardMsgsRqUn.__memberName = "CreditCardMsgsRqV1";
      paramTypeOFXRqMsgSetsAggregate.CreditCardMsgsRqUn.CreditCardMsgsRqV1 = new TypeCreditCardMsgsRqV1Aggregate();
      paramTypeOFXRqMsgSetsAggregate.CreditCardMsgsRqUn.CreditCardMsgsRqV1.CreditCardMsgsRqV1Un = new TypeCreditCardMsgsRqV1Un[i];
      i = 0;
      for (k = 0; k < j; k++)
      {
        Account localAccount = (Account)paramAccounts.get(k);
        if (localAccount.getTypeValue() == 3)
        {
          paramTypeOFXRqMsgSetsAggregate.CreditCardMsgsRqUn.CreditCardMsgsRqV1.CreditCardMsgsRqV1Un[i] = new TypeCreditCardMsgsRqV1Un();
          paramTypeOFXRqMsgSetsAggregate.CreditCardMsgsRqUn.CreditCardMsgsRqV1.CreditCardMsgsRqV1Un[i].__memberName = "CCStmtTrnRq";
          TypeCCStmtTrnRqV1Aggregate localTypeCCStmtTrnRqV1Aggregate = new TypeCCStmtTrnRqV1Aggregate();
          paramTypeOFXRqMsgSetsAggregate.CreditCardMsgsRqUn.CreditCardMsgsRqV1.CreditCardMsgsRqV1Un[i].CCStmtTrnRq = localTypeCCStmtTrnRqV1Aggregate;
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
      paramTypeOFXRqMsgSetsAggregate.BankMsgsRqUn = new TypeBankMsgsRqUn();
      paramTypeOFXRqMsgSetsAggregate.BankMsgsRqUnExists = true;
      paramTypeOFXRqMsgSetsAggregate.BankMsgsRqUn.__memberName = "BankMsgsRqV1";
      paramTypeOFXRqMsgSetsAggregate.BankMsgsRqUn.BankMsgsRqV1 = new TypeBankMsgsRqV1Aggregate();
      paramTypeOFXRqMsgSetsAggregate.BankMsgsRqUn.BankMsgsRqV1.BankMsgsRqV1Un = new TypeBankMsgsRqV1Un[i];
      i = 0;
      for (k = 0; k < j; k++)
      {
        Account localAccount = (Account)paramAccounts.get(k);
        if (localAccount.getTypeValue() != 3)
        {
          paramTypeOFXRqMsgSetsAggregate.BankMsgsRqUn.BankMsgsRqV1.BankMsgsRqV1Un[i] = new TypeBankMsgsRqV1Un();
          paramTypeOFXRqMsgSetsAggregate.BankMsgsRqUn.BankMsgsRqV1.BankMsgsRqV1Un[i].__memberName = "StmtTrnRq";
          TypeStmtTrnRqV1Aggregate localTypeStmtTrnRqV1Aggregate = new TypeStmtTrnRqV1Aggregate();
          paramTypeOFXRqMsgSetsAggregate.BankMsgsRqUn.BankMsgsRqV1.BankMsgsRqV1Un[i].StmtTrnRq = localTypeStmtTrnRqV1Aggregate;
          localAccount.set("TRNUID", getUniqueSeqNum());
          localTypeStmtTrnRqV1Aggregate.TrnRqCm = new TypeTrnRqCm();
          localTypeStmtTrnRqV1Aggregate.TrnRqCm.TrnUID = ((String)localAccount.get("TRNUID"));
          localTypeStmtTrnRqV1Aggregate.StmtRq = new TypeStmtRqV1Aggregate();
          TypeBankAcctFromV1Aggregate localTypeBankAcctFromV1Aggregate = new TypeBankAcctFromV1Aggregate();
          formatBANKACCTFROM(localTypeBankAcctFromV1Aggregate, localAccount);
          localTypeStmtTrnRqV1Aggregate.StmtRq.BankAcctFrom = localTypeBankAcctFromV1Aggregate;
          i++;
        }
      }
    }
  }
  
  protected void formatFromAccount(TypeBCCAcctFromV1Un paramTypeBCCAcctFromV1Un, Account paramAccount)
  {
    if (paramAccount.getTypeValue() == 3)
    {
      paramTypeBCCAcctFromV1Un.__memberName = "CCAcctFrom";
      paramTypeBCCAcctFromV1Un.CCAcctFrom = new TypeCCAcctFromAggregate();
      formatCCACCTFROM(paramTypeBCCAcctFromV1Un.CCAcctFrom, paramAccount);
    }
    else
    {
      paramTypeBCCAcctFromV1Un.__memberName = "BankAcctFrom";
      paramTypeBCCAcctFromV1Un.BankAcctFrom = new TypeBankAcctFromV1Aggregate();
      formatBANKACCTFROM(paramTypeBCCAcctFromV1Un.BankAcctFrom, paramAccount);
    }
  }
  
  protected void formatToAccount(TypeBCCAcctToV1Un paramTypeBCCAcctToV1Un, Account paramAccount)
  {
    if (paramAccount.getTypeValue() == 3)
    {
      paramTypeBCCAcctToV1Un.__memberName = "CCAcctTo";
      paramTypeBCCAcctToV1Un.CCAcctTo = new TypeCCAcctToAggregate();
      formatCCACCTTO(paramTypeBCCAcctToV1Un.CCAcctTo, paramAccount);
    }
    else
    {
      paramTypeBCCAcctToV1Un.__memberName = "BankAcctTo";
      paramTypeBCCAcctToV1Un.BankAcctTo = new TypeBankAcctToV1Aggregate();
      formatBANKACCTTO(paramTypeBCCAcctToV1Un.BankAcctTo, paramAccount);
    }
  }
  
  protected void formatBANKACCTFROM(TypeBankAcctFromV1Aggregate paramTypeBankAcctFromV1Aggregate, Account paramAccount)
  {
    paramTypeBankAcctFromV1Aggregate.BankID = paramAccount.getBankID();
    paramTypeBankAcctFromV1Aggregate.AcctID = paramAccount.getNumber();
    paramTypeBankAcctFromV1Aggregate.AcctType = getAccountType(paramAccount.getTypeValue());
  }
  
  protected void formatBANKACCTTO(TypeBankAcctToV1Aggregate paramTypeBankAcctToV1Aggregate, Account paramAccount)
  {
    paramTypeBankAcctToV1Aggregate.BankID = paramAccount.getBankID();
    paramTypeBankAcctToV1Aggregate.AcctID = paramAccount.getNumber();
    paramTypeBankAcctToV1Aggregate.AcctType = getAccountType(paramAccount.getTypeValue());
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
  
  protected void processSTATUS(TypeStatusV1Aggregate paramTypeStatusV1Aggregate)
  {
    this.status = paramTypeStatusV1Aggregate.Code;
    if (paramTypeStatusV1Aggregate.MessageExists)
    {
      logError(paramTypeStatusV1Aggregate.Message);
      if (this.debugService) {
        DebugLog.log(Level.INFO, "ProcessSTATUS message = " + paramTypeStatusV1Aggregate.Message);
      }
    }
  }
  
  protected void processPINCHTRNRS(TypePinChTrnRsV1Aggregate paramTypePinChTrnRsV1Aggregate)
  {
    if (paramTypePinChTrnRsV1Aggregate.PinChRsExists) {}
    processSTATUS(paramTypePinChTrnRsV1Aggregate.TrnRsV1Cm.Status);
    this.savedValues.put("TRNUID", paramTypePinChTrnRsV1Aggregate.TrnRsV1Cm.TrnUID);
  }
  
  protected void processTransactionsInSignUpMsgsRqV1(TypeSignUpMsgsRqV1Aggregate paramTypeSignUpMsgsRqV1Aggregate)
  {
    for (int i = 0; i < paramTypeSignUpMsgsRqV1Aggregate.SignUpMsgsRqV1Un.length; i++) {
      if (paramTypeSignUpMsgsRqV1Aggregate.SignUpMsgsRqV1Un[i].__memberName.equals("AcctInfoTrnRq"))
      {
        TypeAcctInfoTrnRqV1 localTypeAcctInfoTrnRqV1 = new TypeAcctInfoTrnRqV1(paramTypeSignUpMsgsRqV1Aggregate.SignUpMsgsRqV1Un[i].AcctInfoTrnRq);
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
    processSTATUS(paramTypeAcctInfoTrnRsV1Aggregate.TrnRsV1Cm.Status);
    if (paramTypeAcctInfoTrnRsV1Aggregate.AcctInfoRsExists)
    {
      String str = null;
      TypeAcctInfoRsV1Aggregate localTypeAcctInfoRsV1Aggregate = paramTypeAcctInfoTrnRsV1Aggregate.AcctInfoRs;
      int i = localTypeAcctInfoRsV1Aggregate.AcctInfo.length;
      TypeAcctInfoV1Aggregate[] arrayOfTypeAcctInfoV1Aggregate = localTypeAcctInfoRsV1Aggregate.AcctInfo;
      for (int j = 0; j < i; j++)
      {
        str = null;
        if (arrayOfTypeAcctInfoV1Aggregate[j].DescExists) {
          str = arrayOfTypeAcctInfoV1Aggregate[j].Desc;
        }
        processAcctInfoV1Un(arrayOfTypeAcctInfoV1Aggregate[j].AcctInfoV1Un);
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
  
  protected void processAcctInfoV1Un(TypeAcctInfoV1Un[] paramArrayOfTypeAcctInfoV1Un)
  {
    for (int i = 0; i < paramArrayOfTypeAcctInfoV1Un.length; i++)
    {
      String str = null;
      int j = 0;
      this.account = null;
      EnumAccountEnum localEnumAccountEnum;
      if (paramArrayOfTypeAcctInfoV1Un[i].__memberName.equals("BankAcctInfo"))
      {
        str = paramArrayOfTypeAcctInfoV1Un[i].BankAcctInfo.BankAcctFrom.AcctID;
        localEnumAccountEnum = paramArrayOfTypeAcctInfoV1Un[i].BankAcctInfo.BankAcctFrom.AcctType;
        j = getAccountType(localEnumAccountEnum);
        this.account = this.accounts.getByAccountNumberAndType(str, j);
        if (this.account == null) {
          this.account = this.accounts.create(str, j);
        }
        this.account.setBankID(paramArrayOfTypeAcctInfoV1Un[i].BankAcctInfo.BankAcctFrom.BankID);
        if (paramArrayOfTypeAcctInfoV1Un[i].BankAcctInfo.SupTXDL) {
          this.account.setFilterable("Transactions");
        }
        if (paramArrayOfTypeAcctInfoV1Un[i].BankAcctInfo.XferDest) {
          this.account.setFilterable("TransferTo");
        }
        if (paramArrayOfTypeAcctInfoV1Un[i].BankAcctInfo.XferSrc) {
          this.account.setFilterable("TransferFrom");
        }
        processServiceStatusEnum(paramArrayOfTypeAcctInfoV1Un[i].BankAcctInfo.SvcStatus);
      }
      else if (paramArrayOfTypeAcctInfoV1Un[i].__memberName.equals("BPAcctInfo"))
      {
        str = paramArrayOfTypeAcctInfoV1Un[i].BPAcctInfo.BankAcctFrom.AcctID;
        localEnumAccountEnum = paramArrayOfTypeAcctInfoV1Un[i].BPAcctInfo.BankAcctFrom.AcctType;
        j = getAccountType(localEnumAccountEnum);
        this.account = this.accounts.getByAccountNumberAndType(str, j);
        if (this.account == null) {
          this.account = this.accounts.create(str, j);
        }
        this.account.setBankID(paramArrayOfTypeAcctInfoV1Un[i].BPAcctInfo.BankAcctFrom.BankID);
        EnumServiceStatusEnum localEnumServiceStatusEnum = paramArrayOfTypeAcctInfoV1Un[i].BPAcctInfo.SvcStatus;
        processServiceStatusEnum(localEnumServiceStatusEnum);
        if (localEnumServiceStatusEnum.value() == 0) {
          this.account.setFilterable("BillPay");
        }
      }
      else if (paramArrayOfTypeAcctInfoV1Un[i].__memberName.equals("CCAcctInfo"))
      {
        str = paramArrayOfTypeAcctInfoV1Un[i].CCAcctInfo.CCAcctFrom.AcctID;
        j = 3;
        this.account = this.accounts.getByAccountNumberAndType(str, j);
        if (this.account == null) {
          this.account = this.accounts.create(str, j);
        }
        processServiceStatusEnum(paramArrayOfTypeAcctInfoV1Un[i].CCAcctInfo.SvcStatus);
        if (paramArrayOfTypeAcctInfoV1Un[i].CCAcctInfo.SupTXDL) {
          this.account.setFilterable("Transactions");
        }
        if (paramArrayOfTypeAcctInfoV1Un[i].CCAcctInfo.XferDest) {
          this.account.setFilterable("TransferTo");
        }
        if (paramArrayOfTypeAcctInfoV1Un[i].CCAcctInfo.XferSrc) {
          this.account.setFilterable("TransferFrom");
        }
      }
      else if ((paramArrayOfTypeAcctInfoV1Un[i].__memberName.equals("InvAcctInfo")) || (!paramArrayOfTypeAcctInfoV1Un[i].__memberName.equals("PresAcctInfo"))) {}
    }
  }
  
  protected void processStmtTrnRs(TypeStmtTrnRsV1Aggregate paramTypeStmtTrnRsV1Aggregate)
  {
    processSTATUS(paramTypeStmtTrnRsV1Aggregate.TrnRsV1Cm.Status);
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
    for (int i = 0; i < paramTypeBankMsgsRqV1Aggregate.BankMsgsRqV1Un.length; i++) {
      if (paramTypeBankMsgsRqV1Aggregate.BankMsgsRqV1Un[i].__memberName.equals("StmtTrnRq"))
      {
        TypeStmtTrnRqV1 localTypeStmtTrnRqV1 = new TypeStmtTrnRqV1(paramTypeBankMsgsRqV1Aggregate.BankMsgsRqV1Un[i].StmtTrnRq);
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
    processSTATUS(paramTypeCCStmtTrnRsV1Aggregate.TrnRsV1Cm.Status);
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
  
  protected Account processBCCAcctFromV1Un(TypeBCCAcctFromV1Un paramTypeBCCAcctFromV1Un, Accounts paramAccounts)
  {
    String str = null;
    Account localAccount = null;
    int i;
    if (paramTypeBCCAcctFromV1Un.__memberName.equals("BankAcctFrom"))
    {
      str = paramTypeBCCAcctFromV1Un.BankAcctFrom.AcctID;
      EnumAccountEnum localEnumAccountEnum = paramTypeBCCAcctFromV1Un.BankAcctFrom.AcctType;
      i = getAccountType(localEnumAccountEnum);
      localAccount = paramAccounts.getByAccountNumberAndType(str, i);
      if (localAccount == null) {
        localAccount = paramAccounts.create(str, i);
      }
    }
    else if (paramTypeBCCAcctFromV1Un.__memberName.equals("CCAcctFrom"))
    {
      str = paramTypeBCCAcctFromV1Un.CCAcctFrom.AcctID;
      i = 3;
      localAccount = paramAccounts.getByAccountNumberAndType(str, i);
      if (localAccount == null) {
        localAccount = paramAccounts.create(str, i);
      }
    }
    return localAccount;
  }
  
  protected Account processBCCAcctToV1Un(TypeBCCAcctToV1Un paramTypeBCCAcctToV1Un, Accounts paramAccounts)
  {
    String str = null;
    Account localAccount = null;
    int i;
    if (paramTypeBCCAcctToV1Un.__memberName.equals("BankAcctTo"))
    {
      str = paramTypeBCCAcctToV1Un.BankAcctTo.AcctID;
      EnumAccountEnum localEnumAccountEnum = paramTypeBCCAcctToV1Un.BankAcctTo.AcctType;
      i = getAccountType(localEnumAccountEnum);
      localAccount = paramAccounts.getByAccountNumberAndType(str, i);
      if (localAccount == null) {
        localAccount = paramAccounts.create(str, i);
      }
    }
    else if (paramTypeBCCAcctToV1Un.__memberName.equals("CCAcctTo"))
    {
      str = paramTypeBCCAcctToV1Un.CCAcctTo.AcctID;
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
  
  protected EnumAccountEnum getAccountType(int paramInt)
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
  
  protected int getAccountType(EnumAccountEnum paramEnumAccountEnum)
  {
    for (int i = 0; i < Defines.acctTypes.length; i++) {
      if (Defines.acctTypes[i].value() == paramEnumAccountEnum.value()) {
        return Defines.acctMap[i];
      }
    }
    if (this.debugService) {
      DebugLog.log(Level.INFO, "NON-Standard getAccountType() EnumAccountEnum = " + paramEnumAccountEnum.value());
    }
    throw new IllegalArgumentException("getAccountType: type = " + paramEnumAccountEnum.value());
  }
  
  protected int getFrequency(EnumFreqEnum paramEnumFreqEnum)
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
  
  protected EnumFreqEnum getFrequency(int paramInt)
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
      else if (paramString1.equals("CallBackBeanURL"))
      {
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
        String str1 = null;
        String str2 = null;
        int i = paramString2.indexOf("=");
        if (i > 0)
        {
          str1 = paramString2.substring(0, i);
          str2 = paramString2.substring(i + 1);
          Base.this.contextProperty.put(str1, str2);
        }
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.FFServer.OFX151.Base
 * JD-Core Version:    0.7.0.1
 */