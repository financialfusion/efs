package com.ffusion.services.ofx;

import com.ffusion.beans.Balance;
import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.services.AccountService;
import com.ffusion.services.InitFileHandler;
import com.ffusion.services.SignOn2;
import com.ffusion.util.MessageBroadcaster;
import com.ffusion.util.MessageListener;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import com.microstar.xml.HandlerBase;
import com.microstar.xml.XmlParser;
import java.io.BufferedReader;
import java.io.StringReader;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import java.util.logging.Level;

public class Base
  extends Core
  implements SignOn2, Defines, MessageBroadcaster, AccountService
{
  private static final String G = "ConnectionURL";
  private static final String C = "CertificatesDirectory";
  private static final String F = "BankID";
  private static final String A = "BankName";
  private static final String K = "ApplicationID";
  private static final String J = "ApplicationVersion";
  private String E;
  private String x;
  protected String applicationID;
  protected String applicationVersion;
  protected String bankID;
  protected String bankName;
  protected String userID;
  protected String password;
  private String y;
  private static final String I = "DebugService";
  private static final String z = "OFXVersion";
  private static final String w = "OFXExtendedInfo";
  private static final String D = "OFXErrorRecovery";
  private static final String L = "OFXUniqueDTClient";
  protected boolean OFX_Test = false;
  protected boolean debugService = false;
  protected boolean extendedInfo = false;
  protected boolean errorRecovery = true;
  protected boolean uniqueDTClient = false;
  protected SimpleDateFormat formatDate;
  private Random v;
  protected HashMap savedValues;
  protected Account account;
  protected Accounts accounts;
  protected int status;
  private Connect H;
  private ArrayList B;
  protected HashMap objStatus;
  protected long sendTime = 0L;
  
  public Base()
  {
    Date localDate = new Date();
    this.v = new Random(localDate.getTime());
    this.savedValues = new HashMap();
    this.objStatus = new HashMap();
    this.formatDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
  }
  
  public void addMessageListener(MessageListener paramMessageListener)
  {
    if (this.B == null) {
      this.B = new ArrayList();
    }
    this.B.add(paramMessageListener);
  }
  
  public void removeMessageListener(MessageListener paramMessageListener)
  {
    if (this.B != null) {
      this.B.remove(paramMessageListener);
    }
  }
  
  protected void fireMessage(Object paramObject)
  {
    if (this.debugService) {
      DebugLog.log(Level.INFO, paramObject.toString());
    }
  }
  
  public void logStart(String paramString)
  {
    if (this.debugService) {
      DebugLog.log(Level.INFO, paramString);
    }
  }
  
  public void logEnd() {}
  
  protected boolean checkStatus()
  {
    Iterator localIterator = this.objStatus.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str1 = (String)localIterator.next();
      String str2 = (String)this.objStatus.get(str1);
      if (!str2.equals("0")) {
        return false;
      }
    }
    return true;
  }
  
  protected int returnStatus()
  {
    Iterator localIterator = this.objStatus.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str1 = (String)localIterator.next();
      String str2 = (String)this.objStatus.get(str1);
      if (!str2.equals("0")) {
        return Integer.parseInt(str2);
      }
    }
    return this.status;
  }
  
  public int initialize(String paramString)
  {
    return initialize(paramString, new ServiceXMLHandler());
  }
  
  protected int initialize(String paramString, HandlerBase paramHandlerBase)
  {
    int i = 0;
    try
    {
      InitFileHandler localInitFileHandler = new InitFileHandler();
      i = localInitFileHandler.initialize(paramString, paramHandlerBase);
      if (!this.OFX_Test)
      {
        setConnection();
        if (this.H == null) {
          i = 4;
        }
      }
    }
    catch (Throwable localThrowable)
    {
      i = 8;
    }
    return i;
  }
  
  public void setInitURL(String paramString)
  {
    initialize(paramString);
  }
  
  public void setInitURL(String paramString, HandlerBase paramHandlerBase)
  {
    initialize(paramString, paramHandlerBase);
  }
  
  public void setSettings(String paramString)
  {
    try
    {
      StringReader localStringReader = new StringReader(paramString);
      BufferedReader localBufferedReader = new BufferedReader(localStringReader);
      XmlParser localXmlParser = new XmlParser();
      localXmlParser.setHandler(new a());
      localXmlParser.parse(null, null, localBufferedReader);
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing("Base.setSettings", localThrowable);
    }
  }
  
  protected String getSettings()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "BASESERVICE");
    XMLHandler.appendTag(localStringBuffer, "ConnectionURL", this.E);
    XMLHandler.appendTag(localStringBuffer, "CertificatesDirectory", this.x);
    XMLHandler.appendTag(localStringBuffer, "BankID", this.bankID);
    XMLHandler.appendTag(localStringBuffer, "BankName", this.bankName);
    XMLHandler.appendTag(localStringBuffer, "ApplicationID", this.applicationID);
    XMLHandler.appendTag(localStringBuffer, "ApplicationVersion", this.applicationVersion);
    if (this.debugService)
    {
      XMLHandler.appendTag(localStringBuffer, "USERID", this.userID);
      XMLHandler.appendTag(localStringBuffer, "PASSWORD", this.password);
    }
    XMLHandler.appendTag(localStringBuffer, "getLastError", getLastError());
    XMLHandler.appendTag(localStringBuffer, "getLastErrorMessage", getLastErrorMessage());
    XMLHandler.appendTag(localStringBuffer, "DebugService", new Boolean(this.debugService).toString());
    XMLHandler.appendTag(localStringBuffer, "OFX_VERSION", new Integer(this.OFX_Version).toString());
    XMLHandler.appendEndTag(localStringBuffer, "BASESERVICE");
    return localStringBuffer.toString();
  }
  
  public boolean getDebugService()
  {
    return this.debugService;
  }
  
  public void setConnection()
  {
    try
    {
      this.H = new Connect(this, this.E, this.x);
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing("Base.setConnection", localThrowable);
    }
  }
  
  public void setUserID(String paramString)
  {
    if (paramString == null) {
      return;
    }
    this.userID = paramString;
    this.y = this.userID;
    this.y = this.y.toUpperCase();
    this.y = this.y.trim();
    int i = this.y.lastIndexOf(' ');
    if (i != -1) {
      this.y = (this.y.substring(0, 2) + this.y.substring(i + 1));
    }
    i = 0;
    while (i < this.y.length()) {
      if (((this.y.charAt(i) < 'A') || (this.y.charAt(i) > 'Z')) && ((this.y.charAt(i) < '0') || (this.y.charAt(i) > '9'))) {
        this.y = (this.y.substring(0, i) + this.y.substring(i + 1));
      } else {
        i++;
      }
    }
    if (this.y.length() < 6) {
      this.y += "HFNHFN";
    }
    this.y = this.y.substring(0, 6);
    GregorianCalendar localGregorianCalendar = new GregorianCalendar();
    int j = 65;
    i = localGregorianCalendar.get(2) + j;
    this.y += i;
    i = localGregorianCalendar.get(5) / 10 + j;
    this.y += i;
    i = localGregorianCalendar.get(5) % 10 + j;
    this.y += i;
    i = localGregorianCalendar.get(1) % 10 + j;
    this.y += i;
  }
  
  public String getUniqueSeqNum()
  {
    String str1 = this.y;
    GregorianCalendar localGregorianCalendar = new GregorianCalendar();
    int k = Math.abs(this.v.nextInt()) + Math.abs(this.v.nextInt()) + Math.abs(this.v.nextInt());
    k <<= 15;
    int j = localGregorianCalendar.get(14) % 100;
    for (int i = 0; i < j; i += 10) {
      Math.abs(this.v.nextInt());
    }
    k = k + Math.abs(this.v.nextInt()) + Math.abs(this.v.nextInt()) + Math.abs(this.v.nextInt());
    Integer localInteger = new Integer(localGregorianCalendar.get(11));
    String str2 = localInteger.toString();
    if (str2.length() < 2) {
      str2 = "0" + str2;
    }
    str1 = str1 + str2;
    localInteger = new Integer(localGregorianCalendar.get(12));
    str2 = localInteger.toString();
    if (str2.length() < 2) {
      str2 = "0" + str2;
    }
    str1 = str1 + str2;
    localInteger = new Integer(localGregorianCalendar.get(13));
    str2 = localInteger.toString();
    if (str2.length() < 2) {
      str2 = "0" + str2;
    }
    str1 = str1 + str2;
    localInteger = new Integer(localGregorianCalendar.get(14) / 10);
    str2 = localInteger.toString();
    if (str2.length() < 2) {
      str2 = "0" + str2;
    }
    if (str2.length() < 3) {
      str2 = "0" + str2;
    }
    str1 = str1 + str2;
    localInteger = new Integer(Math.abs(k));
    for (str2 = localInteger.toString(); str2.length() < 10; str2 = "0" + str2) {}
    str1 = str1 + str2;
    return str1;
  }
  
  public char[] sendRequest(char[] paramArrayOfChar)
  {
    char[] arrayOfChar = null;
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".sendRequest:");
    }
    this.status = 0;
    Socket localSocket = null;
    try
    {
      localSocket = this.H.open();
      localSocket.setSoTimeout(120000);
      this.H.sendMessage(paramArrayOfChar, paramArrayOfChar.length, localSocket);
      arrayOfChar = this.H.receiveMessage(localSocket);
    }
    catch (Throwable localThrowable)
    {
      logError(4);
    }
    finally
    {
      if (this.H != null)
      {
        this.H.close(localSocket);
        localSocket = null;
      }
      else
      {
        logError(4);
      }
    }
    return arrayOfChar;
  }
  
  protected boolean startResponse(char[] paramArrayOfChar)
  {
    boolean bool = false;
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".startResponse:");
    }
    try
    {
      start(paramArrayOfChar);
      String str;
      while (((str = getToken()) != null) && (!str.equals("OFX"))) {}
      if (str == null) {
        logError(3);
      } else {
        bool = true;
      }
    }
    catch (Throwable localThrowable)
    {
      logError(3);
      if (this.debugService) {
        DebugLog.log(Level.INFO, "Invalid OFX Response in startResponse");
      }
    }
    return bool;
  }
  
  protected boolean handleResponseNotHandled(String paramString)
  {
    boolean bool = false;
    DebugLog.log(Level.INFO, getClass().getName() + ".handleResponse token not handled: " + paramString);
    return bool;
  }
  
  protected boolean handleEResponseNotHandled(String paramString)
  {
    boolean bool = false;
    DebugLog.log(Level.INFO, getClass().getName() + ".handleEResponse token not handled: " + paramString);
    return bool;
  }
  
  public int signOn(String paramString1, String paramString2)
  {
    int i = 0;
    setUserID(paramString1);
    this.password = paramString2;
    DebugLog.log(Level.INFO, getClass().getName() + ".signOn:");
    char[] arrayOfChar = sendRequest(formatSignOnRequest());
    if ((arrayOfChar != null) && (startResponse(arrayOfChar)))
    {
      String str;
      while (((str = getToken()) != null) && (!str.equals("OFX"))) {
        if (str.equals("SIGNONMSGSRSV1")) {
          parseSIGNONMSGSRSV1();
        } else {
          logError(3);
        }
      }
      if ((str != null) && (str.equals("OFX"))) {
        i = 1;
      }
    }
    if ((i != 0) && (this.status != 0))
    {
      i = 0;
      logError(mapError(this.status));
      if (this.status != 15000)
      {
        setUserID(null);
        this.password = null;
      }
    }
    if (i != 0) {
      return 0;
    }
    return this.lastError;
  }
  
  public int changePIN(String paramString1, String paramString2)
  {
    int i = 0;
    StringBuffer localStringBuffer = new StringBuffer();
    if (paramString1.equals(this.password))
    {
      DebugLog.log(Level.INFO, getClass().getName() + ".changePIN:");
      char[] arrayOfChar = sendRequest(formatChangePinRequest(paramString2, localStringBuffer));
      if ((arrayOfChar != null) && (startResponse(arrayOfChar)))
      {
        while (((str = getToken()) != null) && (!str.equals("OFX"))) {
          if (str.equals("SIGNONMSGSRSV1")) {
            parseSIGNONMSGSRSV1();
          } else {
            logError(3);
          }
        }
        if ((str != null) && (str.equals("OFX"))) {
          i = 1;
        }
      }
      String str = (String)this.savedValues.get("TRNUID");
      if ((str != null) && (this.status == 0) && (str.equals(localStringBuffer.toString()))) {
        this.password = paramString2;
      }
    }
    else
    {
      logError(101);
    }
    if (i != 0) {
      return 0;
    }
    return this.lastError;
  }
  
  public int getAccounts(Accounts paramAccounts)
  {
    int i = 0;
    this.accounts = paramAccounts;
    DebugLog.log(Level.INFO, getClass().getName() + ".getAccounts:");
    char[] arrayOfChar = sendRequest(formatGetAccountsRequest());
    if ((arrayOfChar != null) && (startResponse(arrayOfChar)))
    {
      String str;
      while (((str = getToken()) != null) && (!str.equals("OFX"))) {
        if (str.equals("SIGNONMSGSRSV1")) {
          parseSIGNONMSGSRSV1();
        } else if (str.equals("SIGNUPMSGSRSV1")) {
          parseSIGNUPMSGSRSV1();
        } else {
          logError(3);
        }
      }
      if ((str != null) && (str.equals("OFX"))) {
        i = 1;
      }
    }
    this.accounts = null;
    this.account = null;
    if ((i != 0) && (this.status == 0)) {
      i = getBalances(paramAccounts) == 0 ? 1 : 0;
    }
    if ((i != 0) && (this.status != 0))
    {
      i = 0;
      logError(mapError(this.status));
    }
    if (i != 0) {
      return 0;
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
  
  public int getBalances(Accounts paramAccounts)
  {
    int i = 0;
    this.accounts = paramAccounts;
    DebugLog.log(Level.INFO, getClass().getName() + ".getBalances:");
    char[] arrayOfChar = sendRequest(formatGetBalancesRequest());
    if ((arrayOfChar != null) && (startResponse(arrayOfChar)))
    {
      String str;
      while (((str = getToken()) != null) && (!str.equals("OFX"))) {
        if (str.equals("SIGNONMSGSRSV1")) {
          parseSIGNONMSGSRSV1();
        } else if (str.equals("BANKMSGSRSV1")) {
          parseBANKMSGSRSV1();
        } else if (str.equals("CREDITCARDMSGSRSV1")) {
          parseCREDITCARDMSGSRSV1();
        } else {
          logError(3);
        }
      }
      if ((str != null) && (str.equals("OFX"))) {
        i = 1;
      }
    }
    this.accounts = null;
    this.account = null;
    if ((i != 0) && (this.status != 0))
    {
      i = 0;
      logError(mapError(this.status));
    }
    if (i != 0) {
      return 0;
    }
    return this.lastError;
  }
  
  protected char[] formatSignOnRequest()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatSignOnRequest:");
    }
    appendBeginTag(localStringBuffer, "OFX");
    formatSIGNONMSGSRQV1(localStringBuffer);
    appendEndTag(localStringBuffer, "OFX");
    return localStringBuffer.toString().toCharArray();
  }
  
  protected char[] formatGetAccountsRequest()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    GregorianCalendar localGregorianCalendar = new GregorianCalendar();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatGetAccountsRequest:");
    }
    localGregorianCalendar.set(1989, 1, 1, 0, 0, 0);
    appendBeginTag(localStringBuffer, "OFX");
    formatSIGNONMSGSRQV1(localStringBuffer);
    appendBeginTag(localStringBuffer, "SIGNUPMSGSRQV1");
    appendBeginTag(localStringBuffer, "ACCTINFOTRNRQ");
    appendTag(localStringBuffer, "TRNUID", getUniqueSeqNum());
    appendBeginTag(localStringBuffer, "ACCTINFORQ");
    appendTag(localStringBuffer, "DTACCTUP", localGregorianCalendar, "yyyyMMddHHmmss");
    appendEndTag(localStringBuffer, "ACCTINFORQ");
    appendEndTag(localStringBuffer, "ACCTINFOTRNRQ");
    appendEndTag(localStringBuffer, "SIGNUPMSGSRQV1");
    appendEndTag(localStringBuffer, "OFX");
    return localStringBuffer.toString().toCharArray();
  }
  
  protected char[] formatGetBalancesRequest()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatGetBalancesRequest:");
    }
    appendBeginTag(localStringBuffer, "OFX");
    formatSIGNONMSGSRQV1(localStringBuffer);
    formatSTMTTRNRQ(localStringBuffer);
    formatCCSTMTTRNRQ(localStringBuffer);
    appendEndTag(localStringBuffer, "OFX");
    return localStringBuffer.toString().toCharArray();
  }
  
  public String getAccountType(int paramInt)
  {
    for (int i = 0; i < Defines.acctTypes.length; i++) {
      if (paramInt == Defines.acctMap[i]) {
        return Defines.acctTypes[i];
      }
    }
    return "UNKNOWN";
  }
  
  public int getFrequency(String paramString)
  {
    for (int i = 0; i < Defines.FreqStrs.length; i++) {
      if (paramString.equals(Defines.FreqStrs[i])) {
        return Defines.FreqMap[i];
      }
    }
    return -1;
  }
  
  public String getFrequency(int paramInt)
  {
    for (int i = 0; i < Defines.FreqMap.length; i++) {
      if (paramInt == Defines.FreqMap[i]) {
        return Defines.FreqStrs[i];
      }
    }
    return null;
  }
  
  protected void formatSONRQ(StringBuffer paramStringBuffer)
  {
    TimeZone localTimeZone = TimeZone.getTimeZone("UTC");
    GregorianCalendar localGregorianCalendar = null;
    if (this.uniqueDTClient == true) {
      synchronized (this)
      {
        long l = System.currentTimeMillis();
        try
        {
          if (l - this.sendTime < 1000L) {
            wait(1001L - (l - this.sendTime));
          }
        }
        catch (InterruptedException localInterruptedException)
        {
          localInterruptedException.printStackTrace();
        }
        localGregorianCalendar = new GregorianCalendar(localTimeZone);
        this.sendTime = localGregorianCalendar.getTime().getTime();
      }
    }
    localGregorianCalendar = new GregorianCalendar(localTimeZone);
    appendBeginTag(paramStringBuffer, "SONRQ");
    appendTag(paramStringBuffer, "DTCLIENT", localGregorianCalendar, "yyyyMMddHHmmss");
    appendTag(paramStringBuffer, "USERID", this.userID);
    appendTag(paramStringBuffer, "USERPASS", this.password);
    appendTag(paramStringBuffer, "LANGUAGE", "ENG");
    appendBeginTag(paramStringBuffer, "FI");
    appendTag(paramStringBuffer, "ORG", this.bankName);
    appendTag(paramStringBuffer, "FID", this.bankID);
    appendEndTag(paramStringBuffer, "FI");
    appendTag(paramStringBuffer, "APPID", this.applicationID);
    appendTag(paramStringBuffer, "APPVER", this.applicationVersion);
    appendEndTag(paramStringBuffer, "SONRQ");
  }
  
  protected void formatSIGNONMSGSRQV1(StringBuffer paramStringBuffer)
  {
    appendBeginTag(paramStringBuffer, "SIGNONMSGSRQV1");
    formatSONRQ(paramStringBuffer);
    appendEndTag(paramStringBuffer, "SIGNONMSGSRQV1");
  }
  
  protected void formatCCSTMTTRNRQ(StringBuffer paramStringBuffer)
  {
    int i = 0;
    int j = this.accounts.size();
    for (int k = 0; k < j; k++) {
      if (((Account)this.accounts.get(k)).getTypeValue() == 3) {
        i++;
      }
    }
    if (i != 0)
    {
      appendBeginTag(paramStringBuffer, "CREDITCARDMSGSRQV1");
      for (k = 0; k < j; k++)
      {
        this.account = ((Account)this.accounts.get(k));
        if (this.account.getTypeValue() == 3)
        {
          appendBeginTag(paramStringBuffer, "CCSTMTTRNRQ");
          this.account.set("TRNUID", getUniqueSeqNum());
          appendTag(paramStringBuffer, "TRNUID", (String)this.account.get("TRNUID"));
          appendBeginTag(paramStringBuffer, "CCSTMTRQ");
          formatCCACCTFROM(paramStringBuffer, this.account);
          appendEndTag(paramStringBuffer, "CCSTMTRQ");
          appendEndTag(paramStringBuffer, "CCSTMTTRNRQ");
        }
      }
      appendEndTag(paramStringBuffer, "CREDITCARDMSGSRQV1");
    }
  }
  
  protected void formatSTMTTRNRQ(StringBuffer paramStringBuffer)
  {
    int i = 0;
    int j = this.accounts.size();
    for (int k = 0; k < j; k++) {
      if (((Account)this.accounts.get(k)).getTypeValue() != 3) {
        i++;
      }
    }
    if (i != 0)
    {
      appendBeginTag(paramStringBuffer, "BANKMSGSRQV1");
      for (k = 0; k < j; k++)
      {
        this.account = ((Account)this.accounts.get(k));
        if (this.account.getTypeValue() != 3)
        {
          appendBeginTag(paramStringBuffer, "STMTTRNRQ");
          this.account.set("TRNUID", getUniqueSeqNum());
          appendTag(paramStringBuffer, "TRNUID", (String)this.account.get("TRNUID"));
          appendBeginTag(paramStringBuffer, "STMTRQ");
          formatBANKACCTFROM(paramStringBuffer, this.account);
          appendEndTag(paramStringBuffer, "STMTRQ");
          appendEndTag(paramStringBuffer, "STMTTRNRQ");
        }
      }
      appendEndTag(paramStringBuffer, "BANKMSGSRQV1");
    }
  }
  
  protected void formatFromAccount(StringBuffer paramStringBuffer, Account paramAccount)
  {
    if (paramAccount.getTypeValue() == 3) {
      formatCCACCTFROM(paramStringBuffer, paramAccount);
    } else {
      formatBANKACCTFROM(paramStringBuffer, paramAccount);
    }
  }
  
  protected void formatToAccount(StringBuffer paramStringBuffer, Account paramAccount)
  {
    if (paramAccount.getTypeValue() == 3) {
      formatCCACCTTO(paramStringBuffer, paramAccount);
    } else {
      formatBANKACCTTO(paramStringBuffer, paramAccount);
    }
  }
  
  protected void formatBANKACCTFROM(StringBuffer paramStringBuffer, Account paramAccount)
  {
    appendBeginTag(paramStringBuffer, "BANKACCTFROM");
    appendTag(paramStringBuffer, "BANKID", paramAccount.getBankID());
    appendTag(paramStringBuffer, "ACCTID", paramAccount.getNumber());
    appendTag(paramStringBuffer, "ACCTTYPE", getAccountType(paramAccount.getTypeValue()));
    appendEndTag(paramStringBuffer, "BANKACCTFROM");
  }
  
  protected void formatBANKACCTTO(StringBuffer paramStringBuffer, Account paramAccount)
  {
    appendBeginTag(paramStringBuffer, "BANKACCTTO");
    appendTag(paramStringBuffer, "BANKID", paramAccount.getBankID());
    appendTag(paramStringBuffer, "ACCTID", paramAccount.getNumber());
    appendTag(paramStringBuffer, "ACCTTYPE", getAccountType(paramAccount.getTypeValue()));
    appendEndTag(paramStringBuffer, "BANKACCTTO");
  }
  
  protected void formatCCACCTFROM(StringBuffer paramStringBuffer, Account paramAccount)
  {
    appendBeginTag(paramStringBuffer, "CCACCTFROM");
    appendTag(paramStringBuffer, "ACCTID", paramAccount.getNumber());
    appendEndTag(paramStringBuffer, "CCACCTFROM");
  }
  
  protected void formatCCACCTTO(StringBuffer paramStringBuffer, Account paramAccount)
  {
    appendBeginTag(paramStringBuffer, "CCACCTTO");
    appendTag(paramStringBuffer, "ACCTID", paramAccount.getNumber());
    appendEndTag(paramStringBuffer, "CCACCTTO");
  }
  
  protected void formatRECURRINST(StringBuffer paramStringBuffer, int paramInt, String paramString)
  {
    appendBeginTag(paramStringBuffer, "RECURRINST");
    if (paramInt < 750) {
      appendTag(paramStringBuffer, "NINSTS", paramInt);
    }
    appendTag(paramStringBuffer, "FREQ", paramString);
    appendEndTag(paramStringBuffer, "RECURRINST");
  }
  
  protected char[] formatChangePinRequest(String paramString, StringBuffer paramStringBuffer)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatChangePinRequest:");
    }
    paramStringBuffer.setLength(0);
    paramStringBuffer.append(getUniqueSeqNum());
    appendBeginTag(localStringBuffer, "OFX");
    appendBeginTag(localStringBuffer, "SIGNONMSGSRQV1");
    formatSONRQ(localStringBuffer);
    appendBeginTag(localStringBuffer, "PINCHTRNRQ");
    appendTag(localStringBuffer, "TRNUID", paramStringBuffer.toString());
    appendBeginTag(localStringBuffer, "PINCHRQ");
    appendTag(localStringBuffer, "USERID", this.userID);
    appendTag(localStringBuffer, "NEWUSERPASS", paramString);
    appendEndTag(localStringBuffer, "PINCHRQ");
    appendEndTag(localStringBuffer, "PINCHTRNRQ");
    appendEndTag(localStringBuffer, "SIGNONMSGSRQV1");
    appendEndTag(localStringBuffer, "OFX");
    return localStringBuffer.toString().toCharArray();
  }
  
  public int getAccountType(String paramString)
  {
    for (int i = 0; (i < Defines.acctTypes.length) && (!Defines.acctTypes[i].equals(paramString)); i++) {}
    if (i < Defines.acctMap.length) {
      i = Defines.acctMap[i];
    } else {
      throw new IllegalArgumentException("getAccountType: type=" + paramString);
    }
    return i;
  }
  
  public void setAccount()
  {
    if (this.account != null) {
      return;
    }
    if ((this.savedValues.get("ACCTTYPE") == null) && (this.savedValues.get("ACCTID") == null)) {
      throw new IllegalArgumentException("setAccount: ACCTTYPE=" + this.savedValues.get("ACCTTYPE") + " ACCTID=" + this.savedValues.get("ACCTID"));
    }
    int i = getAccountType((String)this.savedValues.get("ACCTTYPE"));
    String str1 = (String)this.savedValues.get("ACCTID");
    this.account = this.accounts.getByAccountNumberAndType(str1, i);
    if (this.account == null) {
      this.account = this.accounts.create(str1, i);
    }
    Iterator localIterator = this.savedValues.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str2 = (String)localIterator.next();
      setAccount(str2, (String)this.savedValues.get(str2));
    }
    this.savedValues.clear();
  }
  
  public void setAccount(String paramString1, String paramString2)
  {
    if (this.account != null)
    {
      if (paramString1.equals("DESC"))
      {
        this.account.setNickName(paramString2);
      }
      else if (paramString1.equals("BANKID"))
      {
        this.account.setBankID(paramString2);
      }
      else if ((paramString1.equals("XFERSRC")) && (paramString2.equals("Y")))
      {
        this.account.setFilterable("TransferFrom");
      }
      else if ((paramString1.equals("XFERDEST")) && (paramString2.equals("Y")))
      {
        this.account.setFilterable("TransferTo");
      }
      else if ((paramString1.equals("SUPTXDL")) && (paramString2.equals("Y")))
      {
        this.account.setFilterable("Transactions");
      }
      else if (paramString1.equals("BILLPAYSUPPORTED"))
      {
        this.account.setFilterable("BillPay");
      }
      else if (paramString1.equals("SVCSTATUS"))
      {
        if (paramString2.equals("AVAIL")) {
          this.account.setStatus(0);
        } else if (paramString2.equals("PEND")) {
          this.account.setStatus(1);
        } else if (paramString2.equals("ACTIVE")) {
          this.account.setStatus(2);
        }
      }
      else if (paramString1.equals("LEDGERBALBALAMT"))
      {
        if (this.account.getCurrentBalance() == null) {
          this.account.setCurrentBalance(new Balance(this.account.getLocale()));
        }
        this.account.getCurrentBalance().setAmount(paramString2);
      }
      else if (paramString1.equals("LEDGERBALDTASOF"))
      {
        if (this.account.getCurrentBalance() == null) {
          this.account.setCurrentBalance(new Balance(this.account.getLocale()));
        }
        this.account.getCurrentBalance().setDate(getDate(paramString2));
      }
      else if (paramString1.equals("AVAILBALBALAMT"))
      {
        if (this.account.getAvailableBalance() == null) {
          this.account.setAvailableBalance(new Balance(this.account.getLocale()));
        }
        this.account.getAvailableBalance().setAmount(paramString2);
      }
      else if (paramString1.equals("AVAILBALDTASOF"))
      {
        if (this.account.getAvailableBalance() == null) {
          this.account.setAvailableBalance(new Balance(this.account.getLocale()));
        }
        this.account.getAvailableBalance().setDate(getDate(paramString2));
      }
      else
      {
        if ((paramString1.equals("ACCTID")) && (!paramString2.equals(this.account.getNumber()))) {
          throw new IllegalArgumentException("setAccount: Account Numbers do not match:" + this.account.getNumber() + " ->" + paramString2);
        }
        if (this.extendedInfo) {
          saveTagInExtendABean(paramString1, paramString2, this.account);
        }
      }
    }
    else {
      this.savedValues.put(paramString1, paramString2);
    }
  }
  
  protected void saveTagInExtendABean(String paramString1, String paramString2, ExtendABean paramExtendABean)
  {
    Object localObject;
    if ((paramString1.endsWith("_DATE")) || (paramString1.endsWith("_DATETIME")))
    {
      localObject = new DateTime(getDate(paramString2), paramExtendABean.getLocale());
      paramExtendABean.put(paramString1, localObject);
    }
    else if ((paramString1.endsWith("_CUR")) || (paramString1.endsWith("_CURRENCY")))
    {
      localObject = new Currency(paramString2, paramExtendABean.getLocale());
      paramExtendABean.put(paramString1, localObject);
    }
    else
    {
      paramExtendABean.put(paramString1, paramString2);
    }
  }
  
  protected void parseSIGNONMSGSRSV1()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("SIGNONMSGSRSV1"))) {
      if (str.equals("SONRS")) {
        parseSONRS();
      } else if (str.equals("PINCHTRNRS")) {
        parsePINCHTRNRS();
      } else if (str.equals("CHALLENGETRNRS")) {
        parseNotSupported(str);
      } else {
        throw new IllegalArgumentException("parseSIGNONMSGSRSV1 failed on tag=" + str);
      }
    }
  }
  
  protected void parseSONRS()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("SONRS"))) {
      if (str.equals("STATUS")) {
        parseSTATUS();
      } else if (str.equals("FI")) {
        parseFI();
      } else if ((str.equals("DTSERVER")) || (str.equals("USERKEY")) || (str.equals("TSKEYEXPIRE")) || (str.equals("LANGUAGE")) || (str.equals("DTPROFUP")) || (str.equals("DTACCTUP")) || (str.equals("SESSCOOKIE"))) {
        getField();
      } else {
        throw new IllegalArgumentException("parseSONRS failed on tag=" + str);
      }
    }
  }
  
  protected void parseFI()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("FI"))) {
      if ((str.equals("FID")) || (str.equals("ORG"))) {
        getField();
      } else {
        throw new IllegalArgumentException("parseFI failed on tag=" + str);
      }
    }
  }
  
  protected void parseSIGNUPMSGSRSV1()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("SIGNUPMSGSRSV1"))) {
      if (str.equals("ENROLLTRNRS")) {
        parseNotSupported(str);
      } else if (str.equals("ACCTINFOTRNRS")) {
        parseACCTINFOTRNRS();
      } else if (str.equals("CHGUSERINFOTRNRS")) {
        parseNotSupported(str);
      } else if (str.equals("CHGUSERINFOSYNCRS")) {
        parseNotSupported(str);
      } else if (str.equals("ACCTTRNRS")) {
        parseNotSupported(str);
      } else if (str.equals("ACCTSYNCRS")) {
        parseNotSupported(str);
      } else {
        throw new IllegalArgumentException("parseSIGNUPMSGSRSV1 failed on tag=" + str);
      }
    }
  }
  
  protected void parseACCTINFOTRNRS()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("ACCTINFOTRNRS"))) {
      if (str.equals("STATUS")) {
        parseSTATUS();
      } else if (str.equals("ACCTINFORS")) {
        parseACCTINFORS();
      } else if ((str.equals("TRNUID")) || (str.equals("CLTCOOKIE"))) {
        getField();
      } else {
        throw new IllegalArgumentException("parseACCTINFOTRNRS failed on tag=" + str);
      }
    }
  }
  
  protected void parseACCTINFORS()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("ACCTINFORS"))) {
      if (str.equals("ACCTINFO")) {
        parseACCTINFO();
      } else if (str.equals("DTACCTUP")) {
        setAccount(str, getField());
      } else {
        throw new IllegalArgumentException("parseACCTINFORS failed on tag=" + str);
      }
    }
  }
  
  protected void parseACCTINFO()
  {
    this.account = null;
    String str;
    while (((str = getToken()) != null) && (!str.equals("ACCTINFO"))) {
      if (str.equals("BANKACCTINFO")) {
        parseBANKACCTINFO();
      } else if (str.equals("CCACCTINFO")) {
        parseCCACCTINFO();
      } else if (str.equals("BPACCTINFO")) {
        parseBPACCTINFO();
      } else if (str.equals("INVACCTINFO")) {
        parseNotSupported(str);
      } else if ((str.equals("DESC")) || (str.equals("PHONE"))) {
        setAccount(str, getField());
      } else if (this.extendedInfo) {
        this.savedValues.put(str, getField());
      } else {
        throw new IllegalArgumentException("parseACCTINFO failed on tag=" + str);
      }
    }
    setAccount();
  }
  
  protected void parseBANKACCTINFO()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("BANKACCTINFO"))) {
      if (str.equals("BANKACCTFROM")) {
        parseBANKACCTFROM();
      } else if ((str.equals("SUPTXDL")) || (str.equals("XFERSRC")) || (str.equals("XFERDEST")) || (str.equals("SVCSTATUS"))) {
        setAccount(str, getField());
      } else if (this.extendedInfo) {
        this.savedValues.put(str, getField());
      } else {
        throw new IllegalArgumentException("parseBANKACCTINFO failed on tag=" + str);
      }
    }
  }
  
  protected void parseCCACCTINFO()
  {
    setAccount("ACCTTYPE", "CREDITCARD");
    String str;
    while (((str = getToken()) != null) && (!str.equals("CCACCTINFO"))) {
      if (str.equals("CCACCTFROM")) {
        parseCCACCTFROM();
      } else if ((str.equals("SUPTXDL")) || (str.equals("XFERSRC")) || (str.equals("XFERDEST")) || (str.equals("SVCSTATUS"))) {
        setAccount(str, getField());
      } else if (this.extendedInfo) {
        this.savedValues.put(str, getField());
      } else {
        throw new IllegalArgumentException("parseCCACCTINFO failed on tag=" + str);
      }
    }
  }
  
  protected void parseBPACCTINFO()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("BPACCTINFO"))) {
      if (str.equals("BANKACCTFROM"))
      {
        parseBANKACCTFROM();
      }
      else if (str.equals("SVCSTATUS"))
      {
        str = getField();
        setAccount("SVCSTATUS", str);
        if (str.equals("ACTIVE")) {
          setAccount("BILLPAYSUPPORTED", "");
        }
      }
      else if (this.extendedInfo)
      {
        this.savedValues.put(str, getField());
      }
      else
      {
        throw new IllegalArgumentException("parseBPACCTINFO failed on tag=" + str);
      }
    }
  }
  
  protected void parseBANKMSGSRSV1()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("BANKMSGSRSV1"))) {
      if (str.equals("STMTTRNRS")) {
        parseSTMTTRNRS();
      } else if (str.equals("STMTENDTRNRS")) {
        parseNotSupported(str);
      } else if (str.equals("INTRATRNRS")) {
        parseINTRATRNRS();
      } else if (str.equals("RECINTRATRNRS")) {
        parseRECINTRATRNRS();
      } else if (str.equals("STPCHKTRNRS")) {
        parseSTPCHKTRNRS();
      } else if (str.equals("BANKMAILTRNRS")) {
        parseBANKMAILTRNRS();
      } else if (str.equals("BANKMAILSYNCRS")) {
        parseBANKMAILSYNCRS();
      } else if (str.equals("STPCHKSYNCRS")) {
        parseSTPCHKSYNCRS();
      } else if (str.equals("INTRASYNCRS")) {
        parseINTRASYNCRS();
      } else if (str.equals("RECINTRASYNCRS")) {
        parseRECINTRASYNCRS();
      } else {
        throw new IllegalArgumentException("parseBANKMSGSRSV1 failed on tag=" + str);
      }
    }
  }
  
  protected void parseCREDITCARDMSGSRSV1()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("CREDITCARDMSGSRSV1"))) {
      if (str.equals("CCSTMTTRNRS")) {
        parseCCSTMTTRNRS();
      } else if (str.equals("CCSTMTENDTRNRS")) {
        parseNotSupported(str);
      } else {
        throw new IllegalArgumentException("parseCREDITCARDMSGSRSV1 failed on tag=" + str);
      }
    }
  }
  
  protected void parseINTRATRNRS()
  {
    parseNotSupported("INTRATRNRS");
  }
  
  public void parseRECINTRATRNRS()
  {
    parseNotSupported("RECINTRATRNRS");
  }
  
  public void parseINTRASYNCRS()
  {
    parseNotSupported("INTRASYNCRS");
  }
  
  public void parseRECINTRASYNCRS()
  {
    parseNotSupported("RECINTRASYNCRS");
  }
  
  protected void parseBANKMAILTRNRS()
  {
    parseNotSupported("BANKMAILTRNRS");
  }
  
  protected void parseBANKMAILSYNCRS()
  {
    parseNotSupported("BANKMAILSYNCRS");
  }
  
  protected void parseSTPCHKTRNRS()
  {
    parseNotSupported("STPCHKTRNRS");
  }
  
  protected void parseSTPCHKSYNCRS()
  {
    parseNotSupported("STPCHKSYNCRS");
  }
  
  protected void parseSTMTTRNRS()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("STMTTRNRS"))) {
      if (str.equals("STATUS")) {
        parseSTATUS();
      } else if (str.equals("STMTRS")) {
        parseSTMTRS();
      } else if ((str.equals("TRNUID")) || (str.equals("CLTCOOKIE"))) {
        getField();
      } else {
        throw new IllegalArgumentException("parseSTMTTRNRS failed on tag=" + str);
      }
    }
  }
  
  protected void parseSTMTRS()
  {
    if (this.accounts != null) {
      this.account = null;
    }
    String str;
    while (((str = getToken()) != null) && (!str.equals("STMTRS"))) {
      if (str.equals("BANKACCTFROM"))
      {
        parseBANKACCTFROM();
        setAccount();
      }
      else if (str.equals("BANKTRANLIST"))
      {
        parseBANKTRANLIST();
      }
      else if (str.equals("LEDGERBAL"))
      {
        parseLEDGERBAL();
      }
      else if (str.equals("AVAILBAL"))
      {
        parseAVAILBAL();
      }
      else if ((str.equals("CURDEF")) || (str.equals("MKTGINFO")))
      {
        getField();
      }
      else if (this.extendedInfo)
      {
        this.savedValues.put(str, getField());
      }
      else
      {
        throw new IllegalArgumentException("parseSTMTRS failed on tag=" + str);
      }
    }
  }
  
  protected void parseCCSTMTTRNRS()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("CCSTMTTRNRS"))) {
      if (str.equals("STATUS")) {
        parseSTATUS();
      } else if (str.equals("CCSTMTRS")) {
        parseCCSTMTRS();
      } else if ((str.equals("TRNUID")) || (str.equals("CLTCOOKIE"))) {
        getField();
      } else {
        throw new IllegalArgumentException("parseCCSTMTTRNRS failed on tag=" + str);
      }
    }
  }
  
  protected void parseCCSTMTRS()
  {
    if (this.accounts != null) {
      this.account = null;
    }
    String str;
    while (((str = getToken()) != null) && (!str.equals("CCSTMTRS"))) {
      if (str.equals("CCACCTFROM"))
      {
        parseCCACCTFROM();
        setAccount();
      }
      else if (str.equals("BANKTRANLIST"))
      {
        parseBANKTRANLIST();
      }
      else if (str.equals("LEDGERBAL"))
      {
        parseLEDGERBAL();
      }
      else if (str.equals("AVAILBAL"))
      {
        parseAVAILBAL();
      }
      else if ((str.equals("CURDEF")) || (str.equals("MKTGINFO")))
      {
        getField();
      }
      else if (this.extendedInfo)
      {
        this.savedValues.put(str, getField());
      }
      else
      {
        throw new IllegalArgumentException("parseCCSTMTRS failed on tag=" + str);
      }
    }
  }
  
  protected void parseBANKTRANLIST()
  {
    parseNotSupported("BANKTRANLIST");
  }
  
  protected void parseLEDGERBAL()
  {
    parseBALANCE("LEDGERBAL");
  }
  
  protected void parseAVAILBAL()
  {
    parseBALANCE("AVAILBAL");
  }
  
  protected void parseBALANCE(String paramString)
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals(paramString))) {
      if ((str.equals("BALAMT")) || (str.equals("DTASOF"))) {
        setAccount(paramString + str, getField());
      } else {
        throw new IllegalArgumentException("parseAVAILBAL failed on tag=" + str);
      }
    }
  }
  
  protected void parseBANKACCTFROM()
  {
    parseBANKACCT("BANKACCTFROM");
  }
  
  protected void parseBANKACCTTO()
  {
    parseBANKACCT("BANKACCTTO");
  }
  
  protected void parseBANKACCT(String paramString)
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals(paramString))) {
      if ((str.equals("BANKID")) || (str.equals("BRANCHID")) || (str.equals("ACCTID")) || (str.equals("ACCTTYPE")) || (str.equals("ACCTKEY"))) {
        setAccount(str, getField());
      } else {
        throw new IllegalArgumentException("parseBANKACCT failed on tag=" + str);
      }
    }
  }
  
  protected void parseCCACCTFROM()
  {
    parseCCACCT("CCACCTFROM");
  }
  
  protected void parseCCACCTTO()
  {
    parseCCACCT("CCACCTTO");
  }
  
  protected void parseCCACCT(String paramString)
  {
    setAccount("ACCTTYPE", "CREDITCARD");
    String str;
    while (((str = getToken()) != null) && (!str.equals(paramString))) {
      if ((str.equals("ACCTID")) || (str.equals("ACCTKEY"))) {
        setAccount(str, getField());
      } else {
        throw new IllegalArgumentException("parseCCACCT failed on tag=" + str);
      }
    }
  }
  
  public void parseXFERINFO()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("XFERINFO"))) {
      if (str.equals("BANKACCTFROM"))
      {
        this.account = null;
        parseBANKACCTFROM();
        this.savedValues.put("FROMACCTID", this.savedValues.get("ACCTID"));
        this.savedValues.put("FROMACCTTYPE", this.savedValues.get("ACCTTYPE"));
        this.savedValues.remove("ACCTID");
        this.savedValues.remove("ACCTTYPE");
      }
      else if (str.equals("CCACCTFROM"))
      {
        this.account = null;
        parseCCACCTFROM();
        this.savedValues.put("FROMACCTID", this.savedValues.get("ACCTID"));
        this.savedValues.put("FROMACCTTYPE", "CREDITCARD");
        this.savedValues.remove("ACCTID");
      }
      else if (str.equals("BANKACCTTO"))
      {
        this.account = null;
        parseBANKACCTTO();
        this.savedValues.put("TOACCTID", this.savedValues.get("ACCTID"));
        this.savedValues.put("TOACCTTYPE", this.savedValues.get("ACCTTYPE"));
        this.savedValues.put("TOBANKID", this.savedValues.get("BANKID"));
        this.savedValues.remove("ACCTID");
        this.savedValues.remove("ACCTTYPE");
        this.savedValues.remove("BANKID");
      }
      else if (str.equals("CCACCTTO"))
      {
        this.account = null;
        parseCCACCTTO();
        this.savedValues.put("TOACCTID", this.savedValues.get("ACCTID"));
        this.savedValues.put("TOACCTTYPE", "CREDITCARD");
        this.savedValues.remove("ACCTID");
      }
      else if ((str.equals("TRNAMT")) || (str.equals("DTDUE")))
      {
        this.savedValues.put(str, getField());
      }
      else if (this.extendedInfo)
      {
        this.savedValues.put(str, getField());
      }
      else
      {
        throw new IllegalArgumentException("parseXFERINFO failed on tag=" + str);
      }
    }
  }
  
  public void parseRECURRINST()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("RECURRINST"))) {
      if ((str.equals("NINSTS")) || (str.equals("FREQ"))) {
        this.savedValues.put(str, getField());
      } else if (this.extendedInfo) {
        this.savedValues.put(str, getField());
      } else {
        throw new IllegalArgumentException("parseRECURRINST failed on tag=" + str);
      }
    }
  }
  
  public void parseXFERPRCSTS()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("XFERPRCSTS"))) {
      if ((str.equals("XFERPRCCODE")) || (str.equals("DTXFERPRC"))) {
        this.savedValues.put(str, getField());
      } else if (this.extendedInfo) {
        this.savedValues.put(str, getField());
      } else {
        throw new IllegalArgumentException("parseXFERPRCSTS failed on tag=" + str);
      }
    }
  }
  
  protected void parsePINCHTRNRS()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("PINCHTRNRS"))) {
      if (str.equals("STATUS")) {
        parseSTATUS();
      } else if (str.equals("PINCHRS")) {
        parsePINCHRS();
      } else if ((str.equals("TRNUID")) || (str.equals("CLTCOOKIE"))) {
        this.savedValues.put(str, getField());
      } else {
        throw new IllegalArgumentException("parsePINCHTRNRS failed on tag=" + str);
      }
    }
  }
  
  protected void parsePINCHRS()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("PINCHRS"))) {
      if ((str.equals("USERID")) || (str.equals("DTCHANGED"))) {
        this.savedValues.put(str, getField());
      } else {
        throw new IllegalArgumentException("parsePINCHRS failed on tag=" + str);
      }
    }
  }
  
  protected void parseSTATUS()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("STATUS"))) {
      if (str.equals("CODE")) {
        this.status = Integer.parseInt(getField());
      } else if (str.equals("SEVERITY")) {
        skipField();
      } else if (str.equals("MESSAGE")) {
        logError(getField());
      } else {
        throw new IllegalArgumentException("parseSTATUS failed on tag=" + str);
      }
    }
  }
  
  protected void parseNotSupported(String paramString)
  {
    if (this.debugService) {
      DebugLog.log(Level.INFO, "parseNotSupported: " + paramString + " is not currently supported!");
    }
    skipTokens(paramString);
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
  
  public class ServiceXMLHandler
    extends HandlerBase
  {
    public ServiceXMLHandler() {}
    
    public void attribute(String paramString1, String paramString2, boolean paramBoolean)
    {
      if (paramString1.equals("ConnectionURL")) {
        Base.this.E = paramString2;
      } else if (paramString1.equals("CertificatesDirectory")) {
        Base.this.x = paramString2;
      } else if (paramString1.equals("BankID")) {
        Base.this.bankID = paramString2;
      } else if (paramString1.equals("BankName")) {
        Base.this.bankName = paramString2;
      } else if (paramString1.equals("ApplicationID")) {
        Base.this.applicationID = paramString2;
      } else if (paramString1.equals("ApplicationVersion")) {
        Base.this.applicationVersion = paramString2;
      } else if (paramString1.equals("OFXVersion")) {
        Base.this.OFX_Version = Integer.valueOf(paramString2).intValue();
      } else if (paramString1.equals("DebugService")) {
        Base.this.debugService = Boolean.valueOf(paramString2).booleanValue();
      } else if (paramString1.equals("OFXExtendedInfo")) {
        Base.this.extendedInfo = Boolean.valueOf(paramString2).booleanValue();
      } else if (paramString1.equals("OFXErrorRecovery")) {
        Base.this.errorRecovery = Boolean.valueOf(paramString2).booleanValue();
      } else if (paramString1.equals("OFXUniqueDTClient")) {
        Base.this.uniqueDTClient = Boolean.valueOf(paramString2).booleanValue();
      }
    }
  }
  
  public class a
    extends HandlerBase
  {
    public a() {}
    
    public void attribute(String paramString1, String paramString2, boolean paramBoolean)
    {
      if (paramString1.equals("DebugService")) {
        Base.this.debugService = Boolean.valueOf(paramString2).booleanValue();
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.ofx.Base
 * JD-Core Version:    0.7.0.1
 */