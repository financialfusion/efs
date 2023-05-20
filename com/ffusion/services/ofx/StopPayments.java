package com.ffusion.services.ofx;

import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.stoppayments.StopCheck;
import com.ffusion.beans.stoppayments.StopChecks;
import com.ffusion.services.Stops3;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;

public class StopPayments
  extends Base
  implements Stops3, Serializable
{
  protected StopChecks stopPayments;
  protected StopCheck stopPayment;
  private static final int aQ = 5;
  protected String m_StopPaymentToken;
  
  public void setUserName(String paramString)
  {
    setUserID(paramString);
  }
  
  public void setPassword(String paramString)
  {
    this.password = paramString;
  }
  
  public int initialize(String paramString)
  {
    return initialize(paramString, new a());
  }
  
  public String getSettings()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "StopPaymentService");
    localStringBuffer.append(super.getSettings());
    XMLHandler.appendEndTag(localStringBuffer, "StopPaymentService");
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
  
  private boolean jdMethod_byte()
  {
    this.accounts = null;
    this.account = null;
    this.stopPayment = null;
    this.stopPayments = null;
    return checkStatus();
  }
  
  public int addStopPayment(StopCheck paramStopCheck)
  {
    boolean bool = false;
    this.stopPayment = paramStopCheck;
    logStart(getClass().getName() + ".addStopPayment:");
    char[] arrayOfChar = sendRequest(formatAddStopPaymentRequest());
    bool = handleResponse(arrayOfChar);
    this.status = returnStatus();
    jdMethod_byte();
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
  
  public int deleteStopPayment(StopCheck paramStopCheck)
  {
    return 2;
  }
  
  public int getStopPayments(StopChecks paramStopChecks)
  {
    return 2;
  }
  
  public int getStopPayments(StopChecks paramStopChecks, Accounts paramAccounts)
  {
    boolean bool = false;
    this.stopPayments = paramStopChecks;
    this.accounts = paramAccounts;
    logStart(getClass().getName() + ".getStopPayments:");
    char[] arrayOfChar = sendRequest(formatGetStopPaymentsRequest());
    bool = handleResponse(arrayOfChar);
    jdMethod_byte();
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
  
  public int getStopPayments(StopChecks paramStopChecks, Accounts paramAccounts, Calendar paramCalendar1, Calendar paramCalendar2)
  {
    return 2;
  }
  
  protected char[] formatGetStopPaymentsRequest()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatGetStopPaymentsRequest:");
    }
    appendBeginTag(localStringBuffer, "OFX");
    formatSIGNONMSGSRQV1(localStringBuffer);
    int i = 0;
    for (int j = 0; j < this.accounts.size(); j++)
    {
      this.account = ((Account)this.accounts.get(j));
      if (this.account.getTypeValue() != 3) {
        i++;
      }
    }
    if (i > 0)
    {
      appendBeginTag(localStringBuffer, "BANKMSGSRQV1");
      i = 0;
      for (j = 0; j < this.accounts.size(); j++)
      {
        this.account = ((Account)this.accounts.get(j));
        if (this.account.getTypeValue() != 3)
        {
          appendBeginTag(localStringBuffer, "STPCHKSYNCRQ");
          if ((this.m_StopPaymentToken != null) && (this.m_StopPaymentToken.length() > 0)) {
            appendTag(localStringBuffer, "TOKEN", this.m_StopPaymentToken);
          } else {
            appendTag(localStringBuffer, "REFRESH", "Y");
          }
          appendTag(localStringBuffer, "REJECTIFMISSING", "N");
          formatBANKACCTFROM(localStringBuffer, this.account);
          appendEndTag(localStringBuffer, "STPCHKSYNCRQ");
        }
      }
      appendEndTag(localStringBuffer, "BANKMSGSRQV1");
    }
    appendEndTag(localStringBuffer, "OFX");
    return localStringBuffer.toString().toCharArray();
  }
  
  protected char[] formatAddStopPaymentRequest()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatAddStopPaymentRequest:");
    }
    appendBeginTag(localStringBuffer, "OFX");
    formatSIGNONMSGSRQV1(localStringBuffer);
    String str1 = "";
    if (this.stopPayment != null)
    {
      str1 = this.stopPayment.getCheckNumbers();
      if (str1 == null) {
        str1 = "";
      }
      if ((str1.indexOf("-") != -1) || (str1.indexOf(",") != -1))
      {
        String str2 = "";
        StringTokenizer localStringTokenizer = new StringTokenizer(str1, ",", false);
        while (localStringTokenizer.countTokens() > 0)
        {
          str2 = localStringTokenizer.nextToken();
          appendBeginTag(localStringBuffer, "BANKMSGSRQV1");
          this.stopPayment.set("TRNUID", getUniqueSeqNum());
          appendBeginTag(localStringBuffer, "STPCHKTRNRQ");
          appendTag(localStringBuffer, "TRNUID", (String)this.stopPayment.get("TRNUID"));
          appendBeginTag(localStringBuffer, "STPCHKRQ");
          if (this.stopPayment.getAccount() != null) {
            formatBANKACCTFROM(localStringBuffer, this.stopPayment.getAccount());
          }
          appendBeginTag(localStringBuffer, "CHKRANGE");
          int i = str2.indexOf("-");
          if (i == -1)
          {
            appendTag(localStringBuffer, "CHKNUMSTART", str2);
          }
          else
          {
            String str3 = str2.substring(0, i);
            String str4 = str2.substring(i + 1);
            appendTag(localStringBuffer, "CHKNUMSTART", str3);
            if (str4.length() > 0) {
              appendTag(localStringBuffer, "CHKNUMEND", str4);
            }
          }
          appendEndTag(localStringBuffer, "CHKRANGE");
          appendEndTag(localStringBuffer, "STPCHKRQ");
          appendEndTag(localStringBuffer, "STPCHKTRNRQ");
          appendEndTag(localStringBuffer, "BANKMSGSRQV1");
        }
      }
      else
      {
        appendBeginTag(localStringBuffer, "BANKMSGSRQV1");
        this.stopPayment.set("TRNUID", getUniqueSeqNum());
        appendBeginTag(localStringBuffer, "STPCHKTRNRQ");
        appendTag(localStringBuffer, "TRNUID", (String)this.stopPayment.get("TRNUID"));
        appendBeginTag(localStringBuffer, "STPCHKRQ");
        if (this.stopPayment.getAccount() != null) {
          formatBANKACCTFROM(localStringBuffer, this.stopPayment.getAccount());
        }
        appendBeginTag(localStringBuffer, "CHKDESC");
        appendTag(localStringBuffer, "NAME", this.stopPayment.getPayeeName());
        if ((this.stopPayment.getAmount() != null) && (this.stopPayment.getAmount().length() > 0)) {
          appendTag(localStringBuffer, "TRNAMT", this.stopPayment.getAmount());
        }
        if (this.stopPayment.getCheckNumbers() != null) {
          appendTag(localStringBuffer, "CHECKNUM", this.stopPayment.getCheckNumbers());
        }
        if (this.stopPayment.getCheckDateValue() != null) {
          appendTag(localStringBuffer, "DTUSER", this.stopPayment.getCheckDateValue(), "yyyyMMdd");
        }
        appendEndTag(localStringBuffer, "CHKDESC");
        appendEndTag(localStringBuffer, "STPCHKRQ");
        appendEndTag(localStringBuffer, "STPCHKTRNRQ");
        appendEndTag(localStringBuffer, "BANKMSGSRQV1");
      }
    }
    appendEndTag(localStringBuffer, "OFX");
    return localStringBuffer.toString().toCharArray();
  }
  
  public void setStopPayment()
  {
    if ((this.savedValues.get("TRNUID") != null) && (this.stopPayments != null))
    {
      String str1 = (String)this.savedValues.get("TRNUID");
      if ((str1 != null) && (!str1.equals("0"))) {
        this.stopPayment = ((StopCheck)this.stopPayments.getFirstByFilter("TRNUID=" + str1));
      }
      if (this.stopPayment == null) {
        this.stopPayment = ((StopCheck)this.stopPayments.create());
      }
      if (this.stopPayment != null)
      {
        Iterator localIterator = this.savedValues.keySet().iterator();
        while (localIterator.hasNext())
        {
          String str2 = (String)localIterator.next();
          setStopPayment(str2, (String)this.savedValues.get(str2));
        }
      }
    }
    this.savedValues.clear();
  }
  
  public void setStopPayment(String paramString1, String paramString2)
  {
    if (this.stopPayment != null)
    {
      if (paramString1.equals("FEE"))
      {
        this.stopPayment.setFee(paramString2);
      }
      else if (paramString1.equals("FEEMSG"))
      {
        this.stopPayment.setFeeMessage(paramString2);
      }
      else
      {
        String str;
        if (paramString1.equals("FROMACCTTYPE"))
        {
          str = (String)this.savedValues.get("FROMACCTID");
          if ((str != null) && (this.accounts != null))
          {
            Account localAccount = this.accounts.getByAccountNumberAndType(str, getAccountType(paramString2));
            if (localAccount != null) {
              this.stopPayment.setAccountID(localAccount.getID());
            } else {
              this.stopPayment.setAccountID(str);
            }
            localAccount = null;
          }
        }
        else if (paramString1.equals("CHECKNUM"))
        {
          this.stopPayment.setCheckNumbers(paramString2);
        }
        else if (paramString1.equals("NAME"))
        {
          this.stopPayment.setPayeeName(paramString2);
        }
        else if (paramString1.equals("DTUSER"))
        {
          this.stopPayment.setCheckDate(getDate(paramString2));
        }
        else if (paramString1.equals("TRNAMT"))
        {
          this.stopPayment.setAmount(paramString2);
        }
        else if (paramString1.equals("STATUS"))
        {
          this.objStatus.put(this.savedValues.get("TRNUID"), paramString2);
        }
        else if (paramString1.equals("CHKSTATUS"))
        {
          str = Integer.toString(0);
          if (paramString2.equals("1")) {
            str = Integer.toString(13001);
          } else if (paramString2.equals("100")) {
            str = Integer.toString(13003);
          } else if (paramString2.equals("101")) {
            str = Integer.toString(13010);
          }
          this.stopPayment.setStatus(str);
        }
        else if (paramString1.equals("CHKERROR"))
        {
          this.stopPayment.setErrorMessage(paramString2);
        }
        else if (this.extendedInfo)
        {
          saveTagInExtendABean(paramString1, paramString2, this.stopPayment);
        }
      }
    }
    else {
      this.savedValues.put(paramString1, paramString2);
    }
  }
  
  protected void parseSTPCHKTRNRS()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("STPCHKTRNRS"))) {
      if (str.equals("STATUS")) {
        parseSTATUS(5);
      } else if (str.equals("STPCHKRS")) {
        parseSTPCHKRS();
      } else if ((str.equals("TRNUID")) || (str.equals("CLTCOOKIE"))) {
        setStopPayment(str, getField());
      } else {
        throw new IllegalArgumentException("parseSTPCHKTRNRS failed on tag=" + str);
      }
    }
  }
  
  public void parseSTATUS(int paramInt)
  {
    super.parseSTATUS();
    switch (paramInt)
    {
    case 5: 
      setStopPayment("STATUS", String.valueOf(this.status));
    }
  }
  
  protected void parseSTPCHKSYNCRS()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("STPCHKSYNCRS"))) {
      if (str.equals("BANKACCTFROM"))
      {
        this.account = null;
        parseBANKACCTFROM();
      }
      else if (str.equals("STPCHKTRNRS"))
      {
        parseSTPCHKTRNRS();
      }
      else if ((str.equals("TOKEN")) || (str.equals("LOSTSYNC")))
      {
        skipField();
      }
      else
      {
        throw new IllegalArgumentException("parseSTPCHKSYNCRS failed on tag=" + str);
      }
    }
  }
  
  public void parseSTPCHKRS()
  {
    this.stopPayment = null;
    String str;
    while (((str = getToken()) != null) && (!str.equals("STPCHKRS"))) {
      if (str.equals("STPCHKNUM"))
      {
        parseSTPCHKNUM();
      }
      else if (str.equals("BANKACCTFROM"))
      {
        this.account = null;
        parseBANKACCTFROM();
        this.savedValues.put("FROMACCTID", this.savedValues.get("ACCTID"));
        this.savedValues.put("FROMACCTTYPE", this.savedValues.get("ACCTTYPE"));
        this.savedValues.remove("ACCTID");
        this.savedValues.remove("ACCTTYPE");
      }
      else if ((str.equals("CURDEF")) || (str.equals("FEE")) || (str.equals("FEEMSG")))
      {
        setStopPayment(str, getField());
      }
      else if (this.extendedInfo)
      {
        this.savedValues.put(str, getField());
      }
      else
      {
        throw new IllegalArgumentException("parseSTPCHKRS failed on tag=" + str);
      }
    }
    setStopPayment();
  }
  
  public void parseSTPCHKNUM()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("STPCHKNUM"))) {
      if ((str.equals("CHECKNUM")) || (str.equals("NAME")) || (str.equals("DTUSER")) || (str.equals("TRNAMT")) || (str.equals("CHKSTATUS")) || (str.equals("CHKERROR")) || (str.equals("CURRENCY")) || (str.equals("ORIGCURRENCY"))) {
        setStopPayment(str, getField());
      } else if (this.extendedInfo) {
        this.savedValues.put(str, getField());
      } else {
        throw new IllegalArgumentException("parseSTPCHKNUM failed on tag=" + str);
      }
    }
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
    case 2019: 
    case 10000: 
      i = 1012;
      break;
    case 10500: 
      i = 13102;
      break;
    default: 
      i = super.mapError(paramInt);
    }
    return i;
  }
  
  public class a
    extends Base.ServiceXMLHandler
  {
    public a()
    {
      super();
    }
    
    public void attribute(String paramString1, String paramString2, boolean paramBoolean)
    {
      super.attribute(paramString1, paramString2, paramBoolean);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.ofx.StopPayments
 * JD-Core Version:    0.7.0.1
 */