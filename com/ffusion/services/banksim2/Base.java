package com.ffusion.services.banksim2;

import com.ffusion.banksim.db.DBConnectionDefines;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.csil.CSILException;
import com.ffusion.ffs.bpw.adminEJB.BPWAdminHome;
import com.ffusion.ffs.bpw.adminEJB.IBPWAdmin;
import com.ffusion.ffs.bpw.interfaces.BPWInfoBase;
import com.ffusion.ffs.bpw.util.AccountTypesMap;
import com.ffusion.ffs.ofx.interfaces.TypeUserData;
import com.ffusion.msgbroker.generated.MessageBroker.api.OFX200.BPWServices.IOFX200BPWServices;
import com.ffusion.msgbroker.generated.MessageBroker.api.OFX200.BPWServices.OFX200BPWServicesHome;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumAccountEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumFreqEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumServiceStatusEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBCCAcctFromUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBCCAcctToUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankAcctFromAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankAcctToAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeCCAcctFromAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeCCAcctToAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeFIAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeOFXRequest;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeOFXRqMsgSetsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecurrInstAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeSignOnMsgsRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeSignUpMsgsRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeSonRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStatusAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeUserCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeUserInfoUn;
import com.ffusion.services.AccountService;
import com.ffusion.services.InitFileHandler;
import com.ffusion.services.SignOn2;
import com.ffusion.util.ContextPool;
import com.ffusion.util.ContextPoolList;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.FilteredList;
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
import java.util.Locale;
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
  implements SignOn2, Defines, DBConnectionDefines, AccountService
{
  private static final String jdField_else = "BankID";
  private static final String c = "BankName";
  private static final String jdField_new = "ApplicationID";
  private static final String jdField_try = "ApplicationVersion";
  private static final String jdField_char = "CallBackBeanURL";
  private static final String jdField_int = "ContextFactory";
  private static final String jdField_case = "CallBackJNDIName";
  private static final String jdField_null = "ContextUserName";
  private static final String jdField_long = "ContextPassword";
  private static final String jdField_if = "PrincipalClass";
  private static final String d = "ContextProperty";
  private static final String jdField_goto = "DebugService";
  private static final String jdField_for = "MaxContexts";
  private static final String b = "RestrictAccounts";
  private String jdField_byte = "bpw.BPWAdminHome";
  protected final String SERVICE_DOWN = "ServiceDown";
  protected final int SERVICE_DOWN_ERROR = 1020;
  protected String applicationID;
  protected int applicationVersion;
  protected String bankID;
  protected String bankName;
  protected boolean restrictAccounts = true;
  protected Vector provider_url_list = new Vector();
  protected String provider_url;
  protected String context_factory;
  protected String callback_JNDI_name = "OFX200.OFX200CallbackBean";
  protected String context_username = "system";
  protected String context_password = "weblogic";
  protected String principalClass = "com.ffusion.services.FFServer.iPlanet";
  protected HashMap contextProperty = null;
  protected int maxContexts = 50;
  protected Account account;
  protected Accounts accounts;
  protected boolean signonProcessed = false;
  public static final String DATEFORMAT = "yyyyMMdd";
  protected static DateFormat formatDate = DateFormatUtil.getFormatter("yyyyMMdd");
  public static final String DATETIMEFORMAT = "yyyyMMddHHmmss";
  public static final String DATEEXTTIMEFORMAT = "yyyyMMddHHmmss.SSS";
  protected TypeUserData _ud;
  private static ContextPoolList jdField_void = null;
  public TypeOFXRequest rqmes = null;
  private boolean jdField_do = false;
  protected int _limitID = -1;
  protected String _multipleLimits = null;
  protected static AccountTypesMap typesMap = null;
  protected SecureUser secureUser = new SecureUser();
  protected String userID;
  protected String password;
  protected HashMap savedValues = new HashMap();
  protected HashMap objStatus = new HashMap();
  protected int status;
  protected int lastError;
  private String a = "";
  protected static boolean debugService = false;
  public static int[] acctMap = { 1, 2, 7, 12, 4, 5, 6, 8, 9, 10, 11, 13, 3, 14, 15, 3 };
  public static EnumAccountEnum[] acctTypes = { EnumAccountEnum.CHECKING, EnumAccountEnum.SAVINGS, EnumAccountEnum.CREDITLINE, EnumAccountEnum.MONEYMRKT, EnumAccountEnum.LOAN, EnumAccountEnum.MORTGAGE, EnumAccountEnum.HOMEEQUITY, EnumAccountEnum.CD, EnumAccountEnum.IRA, EnumAccountEnum.STOCKBOND, EnumAccountEnum.BROKERAGE, EnumAccountEnum.BUSINESSLOAN, EnumAccountEnum.CREDIT, EnumAccountEnum.FIXEDDEPOSIT, EnumAccountEnum.OTHER, EnumAccountEnum.CREDIT };
  public static String[] acctTypesStr = { "CHECKING", "SAVINGS", "CREDITLINE", "MONEYMRKT", "LOAN", "MORTGAGE", "HOMEEQUITY", "CD", "IRA", "STOCKBOND", "BROKERAGE", "BUSINESSLOAN", "CREDIT", "FIXEDDEPOSIT", "OTHER", "CREDITCARD" };
  
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
      this._ud = new TypeUserData();
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing("Unable to initialize Bank Simulator service", localThrowable);
      i = 8;
    }
    return i;
  }
  
  protected ContextPool getContextPool()
  {
    ContextPool localContextPool = null;
    try
    {
      if (jdField_void == null) {
        jdField_void = new ContextPoolList();
      }
      localContextPool = jdField_void.getContextPool(this.provider_url);
      if (debugService) {
        DebugLog.log(Level.INFO, "provider_url = " + this.provider_url);
      }
      if (localContextPool == null) {
        try
        {
          localContextPool = new ContextPool(this.provider_url, this.context_username, this.context_password, this.context_factory);
          localContextPool.setInitialContexts(5);
          localContextPool.setIncrementalContexts(1);
          localContextPool.setMaxContexts(this.maxContexts);
          if (this.contextProperty != null)
          {
            Iterator localIterator = this.contextProperty.entrySet().iterator();
            Properties localProperties = localContextPool.getProperties();
            while (localIterator.hasNext())
            {
              Map.Entry localEntry = (Map.Entry)localIterator.next();
              localProperties.put(localEntry.getKey(), localEntry.getValue().toString());
            }
          }
          localContextPool.createPool();
          jdField_void.add(localContextPool);
        }
        catch (Throwable localThrowable1)
        {
          DebugLog.throwing("Error: " + localThrowable1.toString(), localThrowable1);
          throw localThrowable1;
        }
      }
    }
    catch (Throwable localThrowable2)
    {
      DebugLog.throwing("Couldn't get handler in BankSim service", localThrowable2);
    }
    return localContextPool;
  }
  
  protected ContextPool getContextPool(String paramString)
  {
    ContextPool localContextPool = null;
    try
    {
      if (jdField_void == null) {
        jdField_void = new ContextPoolList();
      }
      localContextPool = jdField_void.getContextPool(paramString);
      if (debugService) {
        DebugLog.log(Level.INFO, "provider_url = " + paramString);
      }
      if (localContextPool == null) {
        try
        {
          localContextPool = new ContextPool(paramString, this.context_username, this.context_password, this.context_factory);
          localContextPool.setInitialContexts(5);
          localContextPool.setIncrementalContexts(1);
          localContextPool.setMaxContexts(this.maxContexts);
          if (this.contextProperty != null)
          {
            Iterator localIterator = this.contextProperty.entrySet().iterator();
            Properties localProperties = localContextPool.getProperties();
            while (localIterator.hasNext())
            {
              Map.Entry localEntry = (Map.Entry)localIterator.next();
              localProperties.put(localEntry.getKey(), localEntry.getValue().toString());
            }
          }
          localContextPool.createPool();
          jdField_void.add(localContextPool);
        }
        catch (Throwable localThrowable1)
        {
          DebugLog.throwing("Error: " + localThrowable1.toString(), localThrowable1);
          throw localThrowable1;
        }
      }
    }
    catch (Throwable localThrowable2)
    {
      DebugLog.throwing("Couldn't get handler in BankSim service", localThrowable2);
    }
    return localContextPool;
  }
  
  protected IOFX200BPWServices getOFXHandler()
    throws Exception
  {
    IOFX200BPWServices localIOFX200BPWServices = null;
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
        if (debugService)
        {
          DebugLog.log(Level.INFO, "##################### Getting OFX Handler ################");
          DebugLog.log(Level.INFO, "provider_url = " + str);
        }
        localContextPool = getContextPool(str);
        localContext = localContextPool.getContext();
        Object localObject1 = localContext.lookup(this.callback_JNDI_name);
        localObject2 = (OFX200BPWServicesHome)PortableRemoteObject.narrow(localObject1, OFX200BPWServicesHome.class);
        localIOFX200BPWServices = ((OFX200BPWServicesHome)localObject2).create();
        localContextPool.returnContext(localContext);
        return localIOFX200BPWServices;
      }
      catch (Throwable localThrowable)
      {
        Object localObject2;
        DebugLog.throwing("Couldn't get handler in BankSim service", localThrowable);
        if ((localContext != null) && (localContextPool != null)) {
          try
          {
            localContext = localContextPool.refreshContext(localContext);
            localObject2 = localContext.lookup(this.callback_JNDI_name);
            OFX200BPWServicesHome localOFX200BPWServicesHome = (OFX200BPWServicesHome)PortableRemoteObject.narrow(localObject2, OFX200BPWServicesHome.class);
            localIOFX200BPWServices = localOFX200BPWServicesHome.create();
            localContextPool.returnContext(localContext);
            return localIOFX200BPWServices;
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
    throw new CSILException(25018);
  }
  
  protected static void removeOFXHandler(IOFX200BPWServices paramIOFX200BPWServices)
  {
    if (paramIOFX200BPWServices != null) {
      try
      {
        if (debugService) {
          DebugLog.log(Level.INFO, "##################### Removing OFX Handler ################");
        }
        paramIOFX200BPWServices.remove();
      }
      catch (Throwable localThrowable)
      {
        DebugLog.throwing("Error removing ofx handler", localThrowable);
      }
    }
  }
  
  private void a(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    paramObjectInputStream.defaultReadObject();
  }
  
  protected String getSettings()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "BASESERVICE");
    XMLHandler.appendTag(localStringBuffer, "BankID", this.bankID);
    XMLHandler.appendTag(localStringBuffer, "BankName", this.bankName);
    XMLHandler.appendTag(localStringBuffer, "ApplicationID", this.applicationID);
    XMLHandler.appendTag(localStringBuffer, "ApplicationVersion", this.applicationVersion);
    XMLHandler.appendTag(localStringBuffer, "getLastError", getLastError());
    XMLHandler.appendTag(localStringBuffer, "getLastErrorMessage", getLastErrorMessage());
    XMLHandler.appendTag(localStringBuffer, "DebugService", new Boolean(debugService).toString());
    XMLHandler.appendEndTag(localStringBuffer, "BASESERVICE");
    return localStringBuffer.toString();
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
  
  public int changePIN(String paramString1, String paramString2)
  {
    return 0;
  }
  
  public int getAccounts(Accounts paramAccounts)
  {
    return 0;
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
  
  protected int mapError(int paramInt)
  {
    if (this.jdField_do) {
      return paramInt;
    }
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
      if (debugService) {
        DebugLog.log(Level.INFO, "mapError: unknown result code=" + paramInt + " returning ERROR_UNKNOWN");
      }
      i = 1;
    }
    return i;
  }
  
  protected void formatSIGNONMSGSRQV1(TypeOFXRqMsgSetsAggregate paramTypeOFXRqMsgSetsAggregate, String paramString)
  {
    formatSIGNONMSGSRQV1(paramTypeOFXRqMsgSetsAggregate);
    this._ud._tran_id = paramString;
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
      localTypeSignOnMsgsRqV1Aggregate.SonRq.UserInfoUn.UserCm.UserId = getProfileID();
      localTypeSignOnMsgsRqV1Aggregate.SonRq.UserInfoUn.UserCm.UserPass = this.password;
      localTypeSignOnMsgsRqV1Aggregate.SonRq.AppID = this.applicationID;
      localTypeSignOnMsgsRqV1Aggregate.SonRq.AppVer = this.applicationVersion;
      localTypeSignOnMsgsRqV1Aggregate.SonRq.DtClient = str1;
      localTypeSignOnMsgsRqV1Aggregate.SonRq.Language = "ENG";
      localTypeSignOnMsgsRqV1Aggregate.SonRq.FIExists = true;
      localTypeSignOnMsgsRqV1Aggregate.SonRq.FI = new TypeFIAggregate();
      localTypeSignOnMsgsRqV1Aggregate.SonRq.FI.FIDExists = true;
      localTypeSignOnMsgsRqV1Aggregate.SonRq.FI.FID = getAffiliateID();
      localTypeSignOnMsgsRqV1Aggregate.SonRq.FI.Org = this.bankName;
      this._ud._uid = getProfileID();
      this._ud._submittedBy = getSubmitterID();
      this._ud._org = this.bankName;
      this._ud._fid = getFIID();
      String str2 = getProfileID() + "_" + System.currentTimeMillis();
      this._ud._sessionID = str2;
      this._ud._userDefined = str2;
    }
  }
  
  protected static void formatFromAccount(TypeBCCAcctFromUn paramTypeBCCAcctFromUn, Account paramAccount)
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
  
  protected static void formatToAccount(TypeBCCAcctToUn paramTypeBCCAcctToUn, Account paramAccount)
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
  
  protected static void formatBANKACCTFROM(TypeBankAcctFromAggregate paramTypeBankAcctFromAggregate, Account paramAccount)
  {
    paramTypeBankAcctFromAggregate.BankID = paramAccount.getRoutingNum();
    if ((paramTypeBankAcctFromAggregate.BankID == null) || (paramTypeBankAcctFromAggregate.BankID.trim().equals(""))) {
      paramTypeBankAcctFromAggregate.BankID = paramAccount.getBankID();
    }
    paramTypeBankAcctFromAggregate.AcctID = paramAccount.getNumber();
    paramTypeBankAcctFromAggregate.AcctType = getAccountType(paramAccount.getTypeValue());
  }
  
  protected static void formatBANKACCTTO(TypeBankAcctToAggregate paramTypeBankAcctToAggregate, Account paramAccount)
  {
    paramTypeBankAcctToAggregate.BankID = paramAccount.getRoutingNum();
    if ((paramTypeBankAcctToAggregate.BankID == null) || (paramTypeBankAcctToAggregate.BankID.trim().equals(""))) {
      paramTypeBankAcctToAggregate.BankID = paramAccount.getBankID();
    }
    paramTypeBankAcctToAggregate.AcctID = paramAccount.getNumber();
    paramTypeBankAcctToAggregate.AcctType = getAccountType(paramAccount.getTypeValue());
  }
  
  protected static void formatCCACCTFROM(TypeCCAcctFromAggregate paramTypeCCAcctFromAggregate, Account paramAccount)
  {
    paramTypeCCAcctFromAggregate.AcctID = paramAccount.getNumber();
  }
  
  protected static void formatCCACCTTO(TypeCCAcctToAggregate paramTypeCCAcctToAggregate, Account paramAccount)
  {
    paramTypeCCAcctToAggregate.AcctID = paramAccount.getNumber();
  }
  
  protected static TypeRecurrInstAggregate formatRECURRINST(int paramInt1, int paramInt2)
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
  
  protected void processTransactionsInSignOnMsgsRqV1(TypeSignOnMsgsRqV1Aggregate paramTypeSignOnMsgsRqV1Aggregate, IOFX200BPWServices paramIOFX200BPWServices) {}
  
  protected void processSTATUS(TypeStatusAggregate paramTypeStatusAggregate)
  {
    this.status = paramTypeStatusAggregate.Code;
    if ((paramTypeStatusAggregate.Code != 0) && (paramTypeStatusAggregate.Message != null))
    {
      int i = paramTypeStatusAggregate.Message.indexOf("Limit ID: ");
      if (i != -1)
      {
        String str = paramTypeStatusAggregate.Message.substring(i + "Limit ID: ".length(), paramTypeStatusAggregate.Message.length());
        try
        {
          this._limitID = Integer.parseInt(str);
        }
        catch (Throwable localThrowable)
        {
          this._limitID = -1;
        }
        if ((paramTypeStatusAggregate.Message.indexOf('{') > 0) && (paramTypeStatusAggregate.Message.indexOf('}') > 0)) {
          this._multipleLimits = paramTypeStatusAggregate.Message.substring(paramTypeStatusAggregate.Message.indexOf('{') + 1, paramTypeStatusAggregate.Message.indexOf('}'));
        } else {
          this._multipleLimits = null;
        }
      }
      if (paramTypeStatusAggregate.Message.indexOf("Approval is not allowed") != -1)
      {
        this.status = 20003;
        this.jdField_do = true;
      }
      else if (paramTypeStatusAggregate.Message.indexOf("Approval failed because no flow defined") != -1)
      {
        this.status = 20011;
        this.jdField_do = true;
      }
      else if (paramTypeStatusAggregate.Message.indexOf("Limit Check Failed") != -1)
      {
        this.status = 1054;
        this.jdField_do = true;
      }
      else if (paramTypeStatusAggregate.Message.indexOf("Limit Revert Failed") != -1)
      {
        this.status = 20004;
        this.jdField_do = true;
      }
      else if (paramTypeStatusAggregate.Message.indexOf("Approval failed because create item restricted") != -1)
      {
        this.status = 30216;
        this.jdField_do = true;
      }
      else if (paramTypeStatusAggregate.Message.indexOf("Approval Failed") != -1)
      {
        this.status = 30200;
        this.jdField_do = true;
      }
      else
      {
        this.jdField_do = false;
      }
    }
    else
    {
      this.jdField_do = false;
    }
    if (paramTypeStatusAggregate.MessageExists)
    {
      this.a = paramTypeStatusAggregate.Message;
      if ((debugService) && (!"Success".equalsIgnoreCase(paramTypeStatusAggregate.Message))) {
        DebugLog.log(Level.INFO, "ProcessSTATUS message = " + paramTypeStatusAggregate.Message);
      }
    }
  }
  
  protected void processTransactionsInSignUpMsgsRqV1(TypeSignUpMsgsRqV1Aggregate paramTypeSignUpMsgsRqV1Aggregate, IOFX200BPWServices paramIOFX200BPWServices) {}
  
  protected static void processServiceStatusEnum(EnumServiceStatusEnum paramEnumServiceStatusEnum, Account paramAccount)
  {
    if (paramEnumServiceStatusEnum.value() == 1) {
      paramAccount.setStatus(0);
    } else if (paramEnumServiceStatusEnum.value() == 2) {
      paramAccount.setStatus(1);
    } else if (paramEnumServiceStatusEnum.value() == 0) {
      paramAccount.setStatus(2);
    }
  }
  
  protected Account processBCCAcctFromUn(TypeBCCAcctFromUn paramTypeBCCAcctFromUn, Accounts paramAccounts)
  {
    String str = null;
    int i = 0;
    Account localAccount = null;
    if ("BankAcctFrom".equals(paramTypeBCCAcctFromUn.__memberName))
    {
      str = paramTypeBCCAcctFromUn.BankAcctFrom.AcctID;
      EnumAccountEnum localEnumAccountEnum = paramTypeBCCAcctFromUn.BankAcctFrom.AcctType;
      i = getAccountType(localEnumAccountEnum);
      localAccount = paramAccounts.getByAccountNumberAndType(str, i);
    }
    else if ("CCAcctFrom".equals(paramTypeBCCAcctFromUn.__memberName))
    {
      str = paramTypeBCCAcctFromUn.CCAcctFrom.AcctID;
      i = 3;
      localAccount = paramAccounts.getByAccountNumberAndType(str, i);
    }
    if (localAccount == null) {
      localAccount = paramAccounts.createNoAdd(str, i);
    }
    return localAccount;
  }
  
  protected Account processBCCAcctToUn(TypeBCCAcctToUn paramTypeBCCAcctToUn, Accounts paramAccounts)
  {
    String str = null;
    int i = 0;
    Account localAccount = null;
    if ("BankAcctTo".equals(paramTypeBCCAcctToUn.__memberName))
    {
      str = paramTypeBCCAcctToUn.BankAcctTo.AcctID;
      EnumAccountEnum localEnumAccountEnum = paramTypeBCCAcctToUn.BankAcctTo.AcctType;
      i = getAccountType(localEnumAccountEnum);
      localAccount = paramAccounts.getByAccountNumberAndType(str, i);
    }
    else if ("CCAcctTo".equals(paramTypeBCCAcctToUn.__memberName))
    {
      str = paramTypeBCCAcctToUn.CCAcctTo.AcctID;
      i = 3;
      localAccount = paramAccounts.getByAccountNumberAndType(str, i);
    }
    if (localAccount == null) {
      localAccount = paramAccounts.createNoAdd(str, i);
    }
    return localAccount;
  }
  
  public static EnumAccountEnum getAccountType(int paramInt)
  {
    for (int i = 0; i < acctTypes.length; i++) {
      if (paramInt == acctMap[i]) {
        return acctTypes[i];
      }
    }
    DebugLog.log(Level.INFO, "NON-Standard getAccountType() = " + paramInt);
    return acctTypes[0];
  }
  
  public int getAccountType(EnumAccountEnum paramEnumAccountEnum)
  {
    try
    {
      if (typesMap == null)
      {
        IOFX200BPWServices localIOFX200BPWServices = null;
        try
        {
          localIOFX200BPWServices = getOFXHandler();
          typesMap = localIOFX200BPWServices.getAccountTypesMap();
        }
        catch (Exception localException2)
        {
          DebugLog.throwing("getAccountType ", localException2);
        }
        finally
        {
          if (localIOFX200BPWServices != null) {
            removeOFXHandler(localIOFX200BPWServices);
          }
        }
      }
      if (typesMap != null) {
        return typesMap.getAccountTypeInt(paramEnumAccountEnum);
      }
    }
    catch (Exception localException1)
    {
      DebugLog.log(Level.INFO, "NON-Standard getAccountType() EnumAccountEnum = " + paramEnumAccountEnum.value());
    }
    return acctMap[0];
  }
  
  public static int getBPWAccountType(String paramString)
  {
    for (int i = 0; i < acctTypesStr.length; i++) {
      if (acctTypesStr[i].equals(paramString)) {
        return acctMap[i];
      }
    }
    return 0;
  }
  
  public static int getFrequency(EnumFreqEnum paramEnumFreqEnum)
  {
    for (int i = 0; i < Defines.FreqEnums.length; i++) {
      if (paramEnumFreqEnum.value() == Defines.FreqEnums[i].value()) {
        return Defines.FreqMap[i];
      }
    }
    if ((paramEnumFreqEnum.value() == 0) || (paramEnumFreqEnum.value() == 1)) {
      return 9;
    }
    DebugLog.log(Level.INFO, "NON-Standard getFrequency() EnumFreqEnum = " + paramEnumFreqEnum.value());
    return -1;
  }
  
  public static EnumFreqEnum getFrequency(int paramInt)
  {
    for (int i = 0; i < Defines.FreqMap.length; i++) {
      if (paramInt == Defines.FreqMap[i]) {
        return Defines.FreqEnums[i];
      }
    }
    DebugLog.log(Level.INFO, "NON-Standard getFrequency() freq = " + paramInt);
    return null;
  }
  
  public static String getFrequencyString(int paramInt)
  {
    if (paramInt == 9) {
      return "ANUALLY";
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
  
  public static int getFrequency(String paramString)
  {
    if (("ANNUALLY".equalsIgnoreCase(paramString)) || ("ANUALLY".equalsIgnoreCase(paramString))) {
      return 9;
    }
    if ("BIMONTHLY".equalsIgnoreCase(paramString)) {
      return 6;
    }
    if ("BIWEEKLY".equalsIgnoreCase(paramString)) {
      return 2;
    }
    if ("FOURWEEKS".equalsIgnoreCase(paramString)) {
      return 5;
    }
    if ("MONTHLY".equalsIgnoreCase(paramString)) {
      return 4;
    }
    if ("QUARTERLY".equalsIgnoreCase(paramString)) {
      return 7;
    }
    if ("SEMIANNUALLY".equalsIgnoreCase(paramString)) {
      return 8;
    }
    if (("TWICEMONTHLY".equalsIgnoreCase(paramString)) || ("SEMIMONTHLY".equalsIgnoreCase(paramString))) {
      return 3;
    }
    if ("WEEKLY".equalsIgnoreCase(paramString)) {
      return 1;
    }
    return 1;
  }
  
  protected static String getUniqueSeqNum(String paramString)
  {
    GregorianCalendar localGregorianCalendar1 = new GregorianCalendar();
    Date localDate = new Date();
    Random localRandom = new Random(localDate.getTime());
    String str3 = paramString;
    str3 = str3.toUpperCase();
    str3 = str3.trim();
    int k = str3.lastIndexOf(' ');
    if (k != -1) {
      str3 = str3.substring(0, 2) + str3.substring(k + 1);
    }
    k = 0;
    while (k < str3.length()) {
      if (((str3.charAt(k) < 'A') || (str3.charAt(k) > 'Z')) && ((str3.charAt(k) < '0') || (str3.charAt(k) > '9'))) {
        str3 = str3.substring(0, k) + str3.substring(k + 1);
      } else {
        k++;
      }
    }
    if (str3.length() < 6) {
      str3 = str3 + "HFNHFN";
    }
    str3 = str3.substring(0, 6);
    GregorianCalendar localGregorianCalendar2 = new GregorianCalendar();
    int m = 65;
    k = localGregorianCalendar2.get(2) + m;
    str3 = str3 + k;
    k = localGregorianCalendar2.get(5) / 10 + m;
    str3 = str3 + k;
    k = localGregorianCalendar2.get(5) % 10 + m;
    str3 = str3 + k;
    k = localGregorianCalendar2.get(1) % 10 + m;
    str3 = str3 + k;
    String str1 = str3;
    int j = Math.abs(localRandom.nextInt()) + Math.abs(localRandom.nextInt()) + Math.abs(localRandom.nextInt());
    j <<= 15;
    int i = localGregorianCalendar1.get(14) % 100;
    for (k = 0; k < i; k += 10) {
      Math.abs(localRandom.nextInt());
    }
    j = j + Math.abs(localRandom.nextInt()) + Math.abs(localRandom.nextInt()) + Math.abs(localRandom.nextInt());
    Integer localInteger = new Integer(localGregorianCalendar1.get(11));
    String str2 = localInteger.toString();
    if (str2.length() < 2) {
      str2 = "0" + str2;
    }
    str1 = str1 + str2;
    localInteger = new Integer(localGregorianCalendar1.get(12));
    str2 = localInteger.toString();
    if (str2.length() < 2) {
      str2 = "0" + str2;
    }
    str1 = str1 + str2;
    localInteger = new Integer(localGregorianCalendar1.get(13));
    str2 = localInteger.toString();
    if (str2.length() < 2) {
      str2 = "0" + str2;
    }
    str1 = str1 + str2;
    localInteger = new Integer(localGregorianCalendar1.get(14) / 10);
    str2 = localInteger.toString();
    if (str2.length() < 2) {
      str2 = "0" + str2;
    }
    if (str2.length() < 3) {
      str2 = "0" + str2;
    }
    str1 = str1 + str2;
    localInteger = new Integer(Math.abs(j));
    for (str2 = localInteger.toString(); str2.length() < 10; str2 = "0" + str2) {}
    str1 = str1 + str2;
    return str1;
  }
  
  public static int getAccountType(String paramString)
  {
    if (paramString.equalsIgnoreCase("CHECKING")) {
      return 1;
    }
    if (paramString.equalsIgnoreCase("SAVINGS")) {
      return 2;
    }
    if (paramString.equalsIgnoreCase("CREDITLINE")) {
      return 7;
    }
    if (paramString.equalsIgnoreCase("MONEYMRKT")) {
      return 12;
    }
    if (paramString.equalsIgnoreCase("CREDITCARD")) {
      return 3;
    }
    if (paramString.equalsIgnoreCase("CREDIT")) {
      return 3;
    }
    return 1;
  }
  
  public static String getDBAccountType(int paramInt)
  {
    if (paramInt == 1) {
      return "CHECKING";
    }
    if (paramInt == 2) {
      return "SAVINGS";
    }
    if (paramInt == 7) {
      return "CREDITLINE";
    }
    if (paramInt == 12) {
      return "MONEYMRKT";
    }
    return "CHECKING";
  }
  
  protected static boolean addFilter(Properties paramProperties, StringBuffer paramStringBuffer, String paramString1, String paramString2, String paramString3, boolean paramBoolean)
  {
    String str = paramProperties.getProperty(paramString1);
    if (str != null)
    {
      str = str.trim();
      if (str.length() > 0)
      {
        if (paramBoolean) {
          paramStringBuffer.append(",and,");
        }
        paramBoolean = true;
        paramStringBuffer.append(paramString2 + paramString3 + str);
      }
    }
    return paramBoolean;
  }
  
  protected static void setFiltersOnList(FilteredList paramFilteredList, Properties paramProperties, String paramString1, String paramString2, String paramString3)
  {
    setFiltersOnList(paramFilteredList, paramProperties, paramString1, paramString2, paramString3, false);
  }
  
  protected static void setFiltersOnList(FilteredList paramFilteredList, Properties paramProperties, String paramString1, String paramString2, String paramString3, boolean paramBoolean)
  {
    String str1 = paramProperties.getProperty(paramString1);
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    if (str1 != null)
    {
      str1 = str1.trim();
      if (str1.length() > 0)
      {
        StringTokenizer localStringTokenizer = new StringTokenizer(str1, ",", false);
        while (localStringTokenizer.hasMoreTokens())
        {
          String str2 = localStringTokenizer.nextToken();
          if (str2.trim().length() > 0) {
            if (i != 0)
            {
              localStringBuffer.append("," + paramString2 + paramString3 + str2);
            }
            else
            {
              localStringBuffer.append(paramString2 + paramString3 + str2);
              i = 1;
            }
          }
        }
      }
    }
    if (localStringBuffer.length() > 0) {
      if (paramBoolean) {
        paramFilteredList.setFilterOnFilter(localStringBuffer.toString());
      } else {
        paramFilteredList.setFilter(localStringBuffer.toString());
      }
    }
  }
  
  protected String getProfileID()
  {
    int i = this.secureUser.getBusinessID();
    if (i == 0) {
      i = this.secureUser.getPrimaryUserID();
    }
    if (i == 0) {
      i = this.secureUser.getProfileID();
    }
    return i != 0 ? String.valueOf(i) : this.userID;
  }
  
  protected String getSubmitterID()
  {
    int i = this.secureUser.getProfileID();
    return i != 0 ? String.valueOf(i) : this.userID;
  }
  
  protected String getAffiliateID()
  {
    String str = this.secureUser.getAffiliateID();
    if ((str == null) || (str.length() == 0)) {
      str = this.bankID;
    }
    return str;
  }
  
  protected String getFIID()
  {
    String str = this.secureUser.getBPWFIID();
    if ((str == null) || (str.length() == 0)) {
      str = this.bankID;
    }
    return str;
  }
  
  protected IBPWAdmin getBPWAdminHandler(String paramString)
  {
    IBPWAdmin localIBPWAdmin = null;
    ContextPool localContextPool = null;
    Context localContext = null;
    try
    {
      if (debugService)
      {
        DebugLog.log(Level.INFO, "##################### Getting BPW Handler ################");
        DebugLog.log(Level.INFO, "provider_url = " + paramString);
      }
      localContextPool = getContextPool(paramString);
      localContext = localContextPool.getContext();
      Object localObject1 = localContext.lookup(this.jdField_byte);
      localObject2 = (BPWAdminHome)PortableRemoteObject.narrow(localObject1, BPWAdminHome.class);
      localIBPWAdmin = ((BPWAdminHome)localObject2).create();
      localContextPool.returnContext(localContext);
      return localIBPWAdmin;
    }
    catch (Throwable localThrowable)
    {
      Object localObject2;
      DebugLog.throwing("Couldn't get handler in BankSim service", localThrowable);
      if ((localContext != null) && (localContextPool != null)) {
        try
        {
          localContext = localContextPool.refreshContext(localContext);
          localObject2 = localContext.lookup(this.jdField_byte);
          BPWAdminHome localBPWAdminHome = (BPWAdminHome)PortableRemoteObject.narrow(localObject2, BPWAdminHome.class);
          localIBPWAdmin = localBPWAdminHome.create();
          localContextPool.returnContext(localContext);
          return localIBPWAdmin;
        }
        catch (Exception localException)
        {
          DebugLog.throwing("Couldn't refresh the contexts for " + paramString, localException);
          if ((localContextPool != null) && (localContext != null)) {
            localContextPool.returnContext(localContext);
          }
        }
      }
    }
    return null;
  }
  
  protected void removeBPWAdminHandler(IBPWAdmin paramIBPWAdmin)
  {
    if (paramIBPWAdmin != null) {
      try
      {
        if (debugService) {
          DebugLog.log(Level.INFO, "##################### Removing BPW Handler ################");
        }
        paramIBPWAdmin.remove();
      }
      catch (Throwable localThrowable)
      {
        DebugLog.throwing("Error removing bpw handler", localThrowable);
      }
    }
  }
  
  protected boolean ping(String paramString)
    throws CSILException
  {
    IBPWAdmin localIBPWAdmin = getBPWAdminHandler(paramString);
    if (localIBPWAdmin == null) {
      throw new CSILException(31059);
    }
    try
    {
      boolean bool = localIBPWAdmin.ping();
      return bool;
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Couldn't ping the BPW server for " + paramString, localException);
      throw new CSILException(31060);
    }
    finally
    {
      removeBPWAdminHandler(localIBPWAdmin);
    }
  }
  
  public static void populateOBOAgentInfo(SecureUser paramSecureUser, BPWInfoBase paramBPWInfoBase)
  {
    String str1 = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    SecureUser localSecureUser = paramSecureUser.getAgent();
    if (paramSecureUser.getUserType() == 2)
    {
      str4 = "";
      str1 = String.valueOf(paramSecureUser.getProfileID());
      str2 = paramSecureUser.getUserName();
      str3 = String.valueOf(paramSecureUser.getUserType());
    }
    else
    {
      str4 = String.valueOf(paramSecureUser.getProfileID());
      if (localSecureUser != null) {
        if (localSecureUser.getProfileID() > 0)
        {
          str1 = String.valueOf(localSecureUser.getProfileID());
          str2 = localSecureUser.getUserName();
          str3 = String.valueOf(localSecureUser.getUserType());
        }
        else
        {
          str1 = localSecureUser.getUserName();
        }
      }
    }
    paramBPWInfoBase.setAgentId(str1);
    paramBPWInfoBase.setAgentName(str2);
    paramBPWInfoBase.setAgentType(str3);
    paramBPWInfoBase.setSubmittedBy(str4);
  }
  
  public static void populateOBOAgentInfo(SecureUser paramSecureUser, TypeUserData paramTypeUserData)
  {
    String str1 = null;
    String str2 = null;
    String str3 = null;
    SecureUser localSecureUser = paramSecureUser.getAgent();
    if (paramSecureUser.getUserType() == 2)
    {
      str1 = String.valueOf(paramSecureUser.getProfileID());
      str2 = paramSecureUser.getUserName();
      str3 = String.valueOf(paramSecureUser.getUserType());
    }
    else if (localSecureUser != null)
    {
      if (localSecureUser.getProfileID() > 0)
      {
        str1 = String.valueOf(localSecureUser.getProfileID());
        str2 = localSecureUser.getUserName();
        str3 = String.valueOf(localSecureUser.getUserType());
      }
      else
      {
        str1 = localSecureUser.getUserName();
      }
    }
    paramTypeUserData._agentID = str1;
    paramTypeUserData._agentType = str3;
  }
  
  public static boolean getDebugService()
  {
    return debugService;
  }
  
  protected static DateTime getDate(String paramString)
  {
    int i = Integer.parseInt(paramString.substring(0, 4));
    int j = Integer.parseInt(paramString.substring(4, 6));
    int k = Integer.parseInt(paramString.substring(6, 8));
    int m = 12;
    int n = 0;
    int i1 = 0;
    int i2 = 0;
    if (paramString.length() > 8)
    {
      m = Integer.parseInt(paramString.substring(8, 10));
      n = Integer.parseInt(paramString.substring(10, 12));
      i1 = Integer.parseInt(paramString.substring(12, 14));
      if (paramString.indexOf(":") != -1) {
        i2 = -Integer.parseInt(paramString.substring(paramString.indexOf("[") + 1, paramString.indexOf(":")));
      }
    }
    DateTime localDateTime = new DateTime(Locale.getDefault());
    localDateTime.set(i, j - 1, k, m, n, i1);
    localDateTime.add(10, i2);
    if ((paramString.length() > 8) && (i2 != 0))
    {
      TimeZone localTimeZone = TimeZone.getDefault();
      localDateTime.add(14, localTimeZone.getRawOffset());
      if (localTimeZone.inDaylightTime(localDateTime.getTime())) {
        localDateTime.add(10, 1);
      }
    }
    return localDateTime;
  }
  
  public static String setDate(DateTime paramDateTime, String paramString)
  {
    return DateFormatUtil.getFormatter(paramString).format(paramDateTime.getTime());
  }
  
  public int getLastError()
  {
    return this.lastError;
  }
  
  public String getLastErrorMessage()
  {
    return this.a;
  }
  
  public void logError(int paramInt)
  {
    if (debugService) {
      DebugLog.log(Level.WARNING, "logError = " + paramInt);
    }
    this.lastError = paramInt;
  }
  
  protected int returnStatus()
  {
    Iterator localIterator = this.objStatus.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str1 = (String)localIterator.next();
      String str2 = (String)this.objStatus.get(str1);
      if (!"0".equals(str2)) {
        return Integer.parseInt(str2);
      }
    }
    return this.status;
  }
  
  public void setUserID(String paramString)
  {
    if (paramString == null) {
      return;
    }
    this.userID = paramString;
  }
  
  public void setSettings(String paramString) {}
  
  public class a
    extends HandlerBase
  {
    public a() {}
    
    public void attribute(String paramString1, String paramString2, boolean paramBoolean)
    {
      if (paramString1.equals("BankID"))
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
        else if (paramString1.equals("RestrictAccounts"))
        {
          Base.this.restrictAccounts = Boolean.valueOf(paramString2).booleanValue();
        }
        else if (paramString1.equals("DebugService"))
        {
          Base.debugService = Boolean.valueOf(paramString2).booleanValue();
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
        else if (paramString1.equals("MaxContexts"))
        {
          Base.this.maxContexts = Integer.parseInt(paramString2);
        }
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.banksim2.Base
 * JD-Core Version:    0.7.0.1
 */