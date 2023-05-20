package com.ffusion.beans.wiretransfers;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.Frequencies;
import com.ffusion.beans.FundsTransaction;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.history.Histories;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.ffs.bpw.interfaces.RecWireInfo;
import com.ffusion.ffs.bpw.interfaces.ValueInfo;
import com.ffusion.ffs.bpw.interfaces.WireInfo;
import com.ffusion.ffs.bpw.interfaces.WirePayeeInfo;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import com.ffusion.util.beans.LocalizableDate;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.AuditLogRecord;
import com.ffusion.util.logging.AuditLogUtil;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.settings.AccountSettings;
import com.ffusion.util.settings.SystemException;
import java.math.BigDecimal;
import java.text.Collator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;

public class WireTransfer
  extends FundsTransaction
  implements WireDefines, Frequencies, Comparable
{
  public static final String RESOURCE_BUNDLE = "com.ffusion.beans.wiretransfers.resources";
  public static final String BEAN_NAME = WireTransfer.class.getName();
  public static final String KEY_WIRE_FREQUENCIES_PREFIX = "WireFrequencies";
  public static final String KEY_WIRE_MATH_RULES_PREFIX = "WireMathRule";
  public static final String KEY_WIRE_CATEGORIES_PREFIX = "WireCategory";
  public static final String KEY_WIRE_SCOPES_PREFIX = "WireScope";
  public static final String KEY_WIRE_DESTINATIONS_PREFIX = "WireDestination";
  public static final String LINE_SEPARATOR = System.getProperty("line.separator");
  private String Cz;
  private String CS;
  private String CV;
  private String Cq;
  private Currency CR;
  private String Da;
  private String Ct;
  private String templateID;
  private String C9;
  private String CI;
  private String CP;
  private String Cx;
  private String CX;
  private String submittedBy;
  private String CG;
  private String Cm;
  private String Ce;
  private WireTransferPayee C7;
  private String CC;
  private String Cn;
  private String Co;
  private int Cp;
  private String CB;
  private String Db = "USD";
  private String CZ = "USD";
  private String CU;
  private float Cc = 1.0F;
  private String CF;
  private DateTime CY;
  private DateTime Dd;
  private DateTime CH;
  private DateTime Df;
  private String Cb;
  private String De;
  private String CL;
  private String CM;
  private String CK;
  private String CJ;
  private String Cv;
  private String Cy;
  private String Cs;
  private String CA;
  private String Cj;
  private String Ci;
  private String Cg;
  private String Cf;
  private String C5;
  private String C4;
  private String C3;
  private String C2;
  private String C1;
  private String C0;
  private int CE = 0;
  private String C6;
  private String Cd;
  private String CQ;
  private String CD;
  private int Cu = 0;
  private int Ch = 0;
  private DateTime CT;
  private Currency Ck;
  private Currency Cr;
  private boolean CW = false;
  private boolean Dc = false;
  private boolean C8 = false;
  private String action;
  private String CN;
  private String Cl;
  private String CO;
  private String Cw;
  protected WireTransferPayee wireCreditInfo;
  
  public WireTransfer()
  {
    setType(5);
    setWireDestination("DOMESTIC");
    setWireSource("FREEFORM");
    setAmtCurrencyType("USD");
  }
  
  public WireTransfer(String paramString)
  {
    super(paramString);
    setType(5);
    setWireDestination("DOMESTIC");
    setWireSource("FREEFORM");
    setAmtCurrencyType("USD");
  }
  
  public WireTransfer(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException("Locale cannot be null");
    }
    this.datetype = "SHORT";
    setType(5);
    setWireDestination("DOMESTIC");
    setWireSource("FREEFORM");
    setAmtCurrencyType("USD");
  }
  
  public void setWireName(String paramString)
  {
    this.Cz = paramString;
  }
  
  public String getWireName()
  {
    return this.Cz;
  }
  
  public void setNickName(String paramString)
  {
    this.CS = paramString;
  }
  
  public String getNickName()
  {
    return this.CS;
  }
  
  public void setWireCategory(String paramString)
  {
    this.CV = paramString;
  }
  
  public String getWireCategory()
  {
    return this.CV;
  }
  
  public void setWireSource(String paramString)
  {
    this.Cq = paramString;
  }
  
  public String getWireSource()
  {
    return this.Cq;
  }
  
  public String getWireLimit()
  {
    if (this.CR != null) {
      return this.CR.toString();
    }
    return "";
  }
  
  public String getDisplayWireLimit()
  {
    if (this.CR != null)
    {
      String str = this.CR.toString();
      if (str.equals("$0.00") == true) {
        return "No Limit";
      }
      return str;
    }
    return "No Limit";
  }
  
  public Currency getWireLimitValue()
  {
    return this.CR;
  }
  
  public void setWireLimit(Currency paramCurrency)
  {
    this.CR = paramCurrency;
  }
  
  public void setWireLimit(String paramString)
  {
    if (this.CR == null)
    {
      if (paramString == null) {
        this.CR = new Currency("", this.locale);
      } else {
        this.CR = new Currency(paramString, this.locale);
      }
    }
    else {
      this.CR.setAmount(paramString);
    }
  }
  
  public void setWireLimit(BigDecimal paramBigDecimal)
  {
    if (this.CR == null)
    {
      if (paramBigDecimal == null) {
        this.CR = new Currency("", this.locale);
      } else {
        this.CR = new Currency(paramBigDecimal, this.locale);
      }
    }
    else {
      this.CR.setAmount(paramBigDecimal);
    }
  }
  
  public String getWireScope()
  {
    return this.Da;
  }
  
  public void setWireScope(String paramString)
  {
    this.Da = paramString;
  }
  
  public String getWireType()
  {
    return this.Ct;
  }
  
  public void setWireType(String paramString)
  {
    this.Ct = paramString;
  }
  
  public String getTemplateID()
  {
    return this.templateID;
  }
  
  public void setTemplateID(String paramString)
  {
    this.templateID = paramString;
  }
  
  public String getBatchID()
  {
    return this.C9;
  }
  
  public void setBatchID(String paramString)
  {
    this.C9 = paramString;
  }
  
  public String getHostID()
  {
    return this.CI;
  }
  
  public void setHostID(String paramString)
  {
    this.CI = paramString;
  }
  
  public String getRecurringID()
  {
    return this.CP;
  }
  
  public void setRecurringID(String paramString)
  {
    this.CP = paramString;
  }
  
  public String getBankID()
  {
    return this.Cx;
  }
  
  public void setBankID(String paramString)
  {
    this.Cx = paramString;
  }
  
  public String getCustomerID()
  {
    return this.CX;
  }
  
  public void setCustomerID(String paramString)
  {
    this.CX = paramString;
  }
  
  public void setCustomerID(int paramInt)
  {
    this.CX = Integer.toString(paramInt);
  }
  
  public String getSubmittedBy()
  {
    return this.submittedBy;
  }
  
  public void setSubmittedBy(String paramString)
  {
    this.submittedBy = paramString;
  }
  
  public void setSubmittedBy(int paramInt)
  {
    this.submittedBy = String.valueOf(paramInt);
  }
  
  public String getUserID()
  {
    return this.CG;
  }
  
  public void setUserID(String paramString)
  {
    this.CG = paramString;
  }
  
  public void setUserID(int paramInt)
  {
    this.CG = String.valueOf(paramInt);
  }
  
  public void setApprovingUser(String paramString)
  {
    this.Cm = paramString;
  }
  
  public String getApprovingUser()
  {
    return this.Cm;
  }
  
  public String getProcessedBy()
  {
    return this.Ce;
  }
  
  public void setProcessedBy(String paramString)
  {
    this.Ce = paramString;
  }
  
  public String getWirePayeeID()
  {
    return this.CC;
  }
  
  public void setWirePayeeID(String paramString)
  {
    this.CC = paramString;
  }
  
  public WireTransferPayee getWirePayee()
  {
    return this.C7;
  }
  
  public void setWirePayee(WireTransferPayee paramWireTransferPayee)
  {
    this.C7 = paramWireTransferPayee;
  }
  
  public String getFromAccountID()
  {
    return this.Cn;
  }
  
  public void setFromAccountID(String paramString)
  {
    this.Cn = paramString;
  }
  
  public String getFromAccountDisplayText()
  {
    String str = null;
    try
    {
      str = AccountSettings.buildAccountDisplayText(getFromAccountNum(), "com.ffusion.beans.accounts.resources", "AccountDisplayText", "com.ffusion.beans.accounts.resources", "AccountType" + getFromAccountType(), this.locale);
    }
    catch (SystemException localSystemException)
    {
      str = getFromAccountNum();
    }
    return str;
  }
  
  public String getFromAccountNum()
  {
    return this.Co;
  }
  
  public void setFromAccountNum(String paramString)
  {
    this.Co = paramString;
  }
  
  public int getFromAccountType()
  {
    return this.Cp;
  }
  
  public void setFromAccountType(int paramInt)
  {
    this.Cp = paramInt;
  }
  
  public void setFromAccountType(String paramString)
  {
    try
    {
      int i = Integer.parseInt(paramString);
      this.Cp = i;
    }
    catch (Exception localException) {}
  }
  
  public String getFromAccountRoutingNum()
  {
    return this.CB;
  }
  
  public void setFromAccountRoutingNum(String paramString)
  {
    this.CB = paramString;
  }
  
  public String getAmtCurrencyType()
  {
    return this.Db;
  }
  
  public void setAmtCurrencyType(String paramString)
  {
    this.Db = paramString;
  }
  
  public String getPayeeCurrencyType()
  {
    return this.CZ;
  }
  
  public void setPayeeCurrencyType(String paramString)
  {
    this.CZ = paramString;
  }
  
  public void setContractNumber(String paramString)
  {
    this.CU = paramString;
  }
  
  public String getContractNumber()
  {
    return this.CU;
  }
  
  public float getExchangeRateValue()
  {
    return this.Cc;
  }
  
  public String getExchangeRate()
  {
    return Float.toString(this.Cc);
  }
  
  public void setExchangeRate(float paramFloat)
  {
    this.Cc = paramFloat;
  }
  
  public void setExchangeRate(String paramString)
  {
    if (paramString != null) {
      try
      {
        this.Cc = Float.parseFloat(paramString);
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }
  
  public String getMathRule()
  {
    return this.CF;
  }
  
  public void setMathRule(String paramString)
  {
    this.CF = paramString;
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.Dd != null) {
      this.Dd.setFormat(paramString);
    }
    if (this.Df != null) {
      this.Df.setFormat(paramString);
    }
    if (this.CY != null) {
      this.CY.setFormat(paramString);
    }
    if (this.CT != null) {
      this.CT.setFormat(paramString);
    }
    if (this.CH != null) {
      this.CH.setFormat(paramString);
    }
  }
  
  public void setDueDate(DateTime paramDateTime)
  {
    this.CY = paramDateTime;
  }
  
  public void setDueDate(String paramString)
  {
    try
    {
      if (this.CY == null) {
        this.CY = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.CY.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public DateTime getDueDateValue()
  {
    return this.CY;
  }
  
  public String getDueDate()
  {
    if (this.CY != null) {
      return this.CY.toString();
    }
    return "";
  }
  
  public void setDateToPost(DateTime paramDateTime)
  {
    this.Dd = paramDateTime;
  }
  
  public void setDateToPost(String paramString)
  {
    try
    {
      if (this.Dd == null) {
        this.Dd = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.Dd.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public DateTime getDateToPostValue()
  {
    return this.Dd;
  }
  
  public String getDateToPost()
  {
    if (this.Dd != null) {
      return this.Dd.toString();
    }
    return "";
  }
  
  public void setSettlementDate(DateTime paramDateTime)
  {
    this.Df = paramDateTime;
  }
  
  public void setSettlementDate(String paramString)
  {
    try
    {
      if (this.Df == null) {
        this.Df = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.Df.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public DateTime getSettlementDateValue()
  {
    return this.Df;
  }
  
  public String getSettlementDate()
  {
    if (this.Df != null) {
      return this.Df.toString();
    }
    return "";
  }
  
  public void setCreateDate(String paramString)
  {
    this.Cb = paramString;
  }
  
  public String getCreateDate()
  {
    if (this.Cb != null) {
      return this.Cb.toString();
    }
    return "";
  }
  
  public DateTime getCreateDateValue()
  {
    DateTime localDateTime = new DateTime();
    localDateTime.setFormat(getDateFormat());
    localDateTime.setDate(this.Cb);
    return localDateTime;
  }
  
  public void setLogDate(String paramString)
  {
    this.De = paramString;
  }
  
  public String getLogDate()
  {
    if (this.De == null) {
      this.De = "";
    }
    return this.De;
  }
  
  public void setDatePosted(DateTime paramDateTime)
  {
    this.CH = paramDateTime;
  }
  
  public void setDatePosted(String paramString)
  {
    try
    {
      if (this.CH == null) {
        this.CH = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.CH.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public DateTime getDatePostedValue()
  {
    return this.CH;
  }
  
  public String getDatePosted()
  {
    if (this.CH != null) {
      return this.CH.toString();
    }
    return "";
  }
  
  public String getComment()
  {
    return this.Cy;
  }
  
  public void setComment(String paramString)
  {
    this.Cy = paramString;
  }
  
  public String getInfoToBeneficiary()
  {
    return this.Cs;
  }
  
  public String getOrigToBeneficiaryInfo1()
  {
    if (this.Cj != null) {
      return this.Cj;
    }
    return "";
  }
  
  public String getOrigToBeneficiaryInfo2()
  {
    if (this.Ci != null) {
      return this.Ci;
    }
    return "";
  }
  
  public String getOrigToBeneficiaryInfo3()
  {
    if (this.Cg != null) {
      return this.Cg;
    }
    return "";
  }
  
  public String getOrigToBeneficiaryInfo4()
  {
    if (this.Cf != null) {
      return this.Cf;
    }
    return "";
  }
  
  public String getInfoToBank()
  {
    return this.CA;
  }
  
  public String getBankToBankInfo1()
  {
    if (this.C5 != null) {
      return this.C5;
    }
    return "";
  }
  
  public String getBankToBankInfo2()
  {
    if (this.C4 != null) {
      return this.C4;
    }
    return "";
  }
  
  public String getBankToBankInfo3()
  {
    if (this.C3 != null) {
      return this.C3;
    }
    return "";
  }
  
  public String getBankToBankInfo4()
  {
    if (this.C2 != null) {
      return this.C2;
    }
    return "";
  }
  
  public String getBankToBankInfo5()
  {
    if (this.C1 != null) {
      return this.C1;
    }
    return "";
  }
  
  public String getBankToBankInfo6()
  {
    if (this.C0 != null) {
      return this.C0;
    }
    return "";
  }
  
  public void setInfoToBeneficiary(String paramString)
  {
    if (paramString != null) {
      this.Cs = paramString.trim();
    }
  }
  
  public void setOrigToBeneficiaryInfo1(String paramString)
  {
    if (paramString != null) {
      this.Cj = paramString.trim();
    }
  }
  
  public void setOrigToBeneficiaryInfo2(String paramString)
  {
    if (paramString != null) {
      this.Ci = paramString.trim();
    }
  }
  
  public void setOrigToBeneficiaryInfo3(String paramString)
  {
    if (paramString != null) {
      this.Cg = paramString.trim();
    }
  }
  
  public void setOrigToBeneficiaryInfo4(String paramString)
  {
    if (paramString != null) {
      this.Cf = paramString.trim();
    }
  }
  
  public void setInfoToBank(String paramString)
  {
    if (paramString != null) {
      this.CA = paramString.trim();
    }
  }
  
  public void setBankToBankInfo1(String paramString)
  {
    if (paramString != null) {
      this.C5 = paramString.trim();
    }
  }
  
  public void setBankToBankInfo2(String paramString)
  {
    if (paramString != null) {
      this.C4 = paramString.trim();
    }
  }
  
  public void setBankToBankInfo3(String paramString)
  {
    if (paramString != null) {
      this.C3 = paramString.trim();
    }
  }
  
  public void setBankToBankInfo4(String paramString)
  {
    if (paramString != null) {
      this.C2 = paramString.trim();
    }
  }
  
  public void setBankToBankInfo5(String paramString)
  {
    if (paramString != null) {
      this.C1 = paramString.trim();
    }
  }
  
  public void setBankToBankInfo6(String paramString)
  {
    if (paramString != null) {
      this.C0 = paramString.trim();
    }
  }
  
  public String getStatusName()
  {
    return ResourceUtil.getString("WireStatus" + this.CE, "com.ffusion.beans.wiretransfers.resources", this.locale);
  }
  
  public int getStatus()
  {
    return this.CE;
  }
  
  public void setStatus(int paramInt)
  {
    this.CE = paramInt;
  }
  
  public void setStatus(String paramString)
  {
    try
    {
      this.CE = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
  
  public String getConfirmationMsg()
  {
    return this.C6;
  }
  
  public void setConfirmationMsg(String paramString)
  {
    this.C6 = paramString;
  }
  
  public boolean isInternational()
  {
    return this.Cd.equals("INTERNATIONAL") == true;
  }
  
  public void setWireDestination(String paramString)
  {
    this.Cd = paramString;
  }
  
  public String getWireDestination()
  {
    return this.Cd;
  }
  
  public void setConfirmationNum(String paramString)
  {
    this.CQ = paramString;
  }
  
  public String getConfirmationNum()
  {
    return this.CQ;
  }
  
  public void setFedConfirmationNum(String paramString)
  {
    this.CD = paramString;
  }
  
  public String getFedConfirmationNum()
  {
    return this.CD;
  }
  
  public void setEndDate(DateTime paramDateTime)
  {
    this.CT = paramDateTime;
  }
  
  public void setEndDate(String paramString)
  {
    try
    {
      if (this.CT == null) {
        this.CT = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.CT.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public DateTime getEndDateValue()
  {
    return this.CT;
  }
  
  public String getEndDate()
  {
    if (this.CT != null) {
      return this.CT.toString();
    }
    return "";
  }
  
  public void setNumberTransfers(int paramInt)
  {
    this.Cu = paramInt;
    if (paramInt > 1) {
      setType(6);
    }
  }
  
  public void setNumberTransfers(String paramString)
  {
    try
    {
      this.Cu = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
    if (this.Cu > 0) {
      setType(6);
    }
  }
  
  public String getNumberTransfers()
  {
    return String.valueOf(this.Cu);
  }
  
  public int getNumberTransfersValue()
  {
    return this.Cu;
  }
  
  public void setFrequency(int paramInt)
  {
    this.Ch = paramInt;
  }
  
  public void setFrequency(String paramString)
  {
    try
    {
      this.Ch = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
  
  public String getFrequency()
  {
    return ResourceUtil.getString("WireFrequencies" + this.Ch, "com.ffusion.beans.wiretransfers.resources", this.locale);
  }
  
  public int getFrequencyValue()
  {
    return this.Ch;
  }
  
  public void setStartAmount(Currency paramCurrency)
  {
    this.Ck = paramCurrency;
  }
  
  public void setStartAmount(String paramString)
  {
    if (this.Ck == null)
    {
      if (paramString == null) {
        this.Ck = new Currency("", this.locale);
      } else {
        this.Ck = new Currency(paramString, this.locale);
      }
    }
    else {
      this.Ck.setAmount(paramString);
    }
  }
  
  public Currency getStartAmountValue()
  {
    return this.Ck;
  }
  
  public String getStartAmount()
  {
    if (this.Ck != null) {
      return this.Ck.toString();
    }
    return "";
  }
  
  public void setEndAmount(Currency paramCurrency)
  {
    this.Cr = paramCurrency;
  }
  
  public void setEndAmount(String paramString)
  {
    if (this.Cr == null)
    {
      if (paramString == null) {
        this.Cr = new Currency("", this.locale);
      } else {
        this.Cr = new Currency(paramString, this.locale);
      }
    }
    else {
      this.Cr.setAmount(paramString);
    }
  }
  
  public Currency getEndAmountValue()
  {
    return this.Cr;
  }
  
  public String getEndAmount()
  {
    if (this.Cr != null) {
      return this.Cr.toString();
    }
    return "";
  }
  
  public void setCanEdit(String paramString)
  {
    this.CW = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setCanEdit(boolean paramBoolean)
  {
    this.CW = paramBoolean;
  }
  
  public String getCanEdit()
  {
    return String.valueOf(this.CW);
  }
  
  public boolean getCanEditValue()
  {
    return this.CW;
  }
  
  public void setCanDelete(String paramString)
  {
    this.Dc = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setCanDelete(boolean paramBoolean)
  {
    this.Dc = paramBoolean;
  }
  
  public String getCanDelete()
  {
    return String.valueOf(this.Dc);
  }
  
  public boolean getCanDeleteValue()
  {
    return this.Dc;
  }
  
  public void setCanLoad(String paramString)
  {
    this.C8 = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setCanLoad(boolean paramBoolean)
  {
    this.C8 = paramBoolean;
  }
  
  public String getCanLoad()
  {
    return String.valueOf(this.C8);
  }
  
  public boolean getCanLoadValue()
  {
    return this.C8;
  }
  
  public void setAction(String paramString)
  {
    if ((paramString != null) && (this.action != null) && (paramString.equals("mod")) && (this.action.equals("add"))) {
      return;
    }
    super.setAction(paramString);
  }
  
  public String getOrigAmount()
  {
    return this.Cl;
  }
  
  public String getDisplayOrigAmount()
  {
    String str = this.Cl;
    if (str == null) {
      return "No Limit";
    }
    if ((str.equals("0.00")) || (str.equals("0.0")) || (str.equals("0"))) {
      return "No Limit";
    }
    return str;
  }
  
  public void setOrigAmount(String paramString)
  {
    if (paramString == null)
    {
      this.Cl = "0.00";
    }
    else
    {
      Currency localCurrency = new Currency(paramString, this.locale);
      this.Cl = localCurrency.getAmountValue().toString();
    }
  }
  
  public String getOrigCurrency()
  {
    return this.CO;
  }
  
  public void setOrigCurrency(String paramString)
  {
    this.CO = paramString;
  }
  
  public String getPaymentAmount()
  {
    return this.Cw;
  }
  
  public void setPaymentAmount(String paramString)
  {
    if (paramString == null)
    {
      this.Cw = "0.00";
    }
    else
    {
      Currency localCurrency = new Currency(paramString, this.locale);
      this.Cw = localCurrency.getAmountValue().toString();
    }
  }
  
  public String getErrorCode()
  {
    return this.CN;
  }
  
  public void setErrorCode(String paramString)
  {
    this.CN = paramString;
  }
  
  public String getAmountForBPW()
  {
    if (getAmountValue() == null) {
      return "0.00";
    }
    BigDecimal localBigDecimal = getAmountValue().getAmountValue().setScale(2, 5);
    return localBigDecimal.toString();
  }
  
  public String getDisplayAmount()
  {
    if (getAmountValue() == null) {
      return "No Limit";
    }
    String str = this.amount.toString();
    if (str.equals("$0.00") == true) {
      return "No Limit";
    }
    return str;
  }
  
  public String getDisplayAmountForBPW()
  {
    if (getAmountValue() == null) {
      return "No Limit";
    }
    String str = getAmountValue().getAmountValue().toString();
    if (getAmountValue().doubleValue() == 0.0D) {
      return "No Limit";
    }
    return str;
  }
  
  public void setTranState(String paramString)
  {
    super.set("STATUS", paramString);
  }
  
  public String getTranState()
  {
    return (String)super.get("STATUS");
  }
  
  public void setWireCreditInfo(WireTransferPayee paramWireTransferPayee)
  {
    this.wireCreditInfo = paramWireTransferPayee;
  }
  
  public WireTransferPayee getWireCreditInfo()
  {
    return this.wireCreditInfo;
  }
  
  public void setReleaseRejectReason(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0))
    {
      ValueInfo localValueInfo = new ValueInfo();
      localValueInfo.setValue(paramString);
      localValueInfo.setAction("add");
      put("RELEASE_REJECT_REASON", localValueInfo);
    }
  }
  
  public String getReleaseRejectReason()
  {
    try
    {
      if (this.map != null)
      {
        ValueInfo localValueInfo = (ValueInfo)this.map.get("RELEASE_REJECT_REASON");
        if (localValueInfo != null) {
          return (String)localValueInfo.getValue();
        }
      }
    }
    catch (Exception localException) {}
    return "";
  }
  
  public String getByOrderOfName()
  {
    if (this.CL != null) {
      return this.CL;
    }
    return "";
  }
  
  public void setByOrderOfName(String paramString)
  {
    this.CL = paramString;
  }
  
  public String getByOrderOfAddress1()
  {
    if (this.CM != null) {
      return this.CM;
    }
    return "";
  }
  
  public void setByOrderOfAddress1(String paramString)
  {
    this.CM = paramString;
  }
  
  public String getByOrderOfAddress2()
  {
    if (this.CK != null) {
      return this.CK;
    }
    return "";
  }
  
  public void setByOrderOfAddress2(String paramString)
  {
    this.CK = paramString;
  }
  
  public String getByOrderOfAddress3()
  {
    if (this.CJ != null) {
      return this.CJ;
    }
    return "";
  }
  
  public void setByOrderOfAddress3(String paramString)
  {
    this.CJ = paramString;
  }
  
  public String getByOrderOfAccount()
  {
    if (this.Cv != null) {
      return this.Cv;
    }
    return "";
  }
  
  public void setByOrderOfAccount(String paramString)
  {
    this.Cv = paramString;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if (paramString1.equals("WIRE_NAME")) {
      return isFilterable(getWireName(), paramString2, paramString3);
    }
    if (paramString1.equals("WIRE_DESTINATION")) {
      return isFilterable(getWireDestination(), paramString2, paramString3);
    }
    if (paramString1.equals("WIRE_CATEGORY")) {
      return isFilterable(getWireCategory(), paramString2, paramString3);
    }
    if (paramString1.equals("WIRE_PAYEE")) {
      return isFilterable(getWirePayee().getPayeeName(), paramString2, paramString3);
    }
    if ((paramString1.equals("WIRE_PAYEE_ID")) && (getWirePayee() != null)) {
      return isFilterable(getWirePayee().getID(), paramString2, paramString3);
    }
    if (paramString1.equals("STATUS")) {
      return isFilterable(String.valueOf(getStatus()), paramString2, paramString3);
    }
    if (paramString1.equals("ACTION")) {
      return isFilterable(getAction(), paramString2, paramString3);
    }
    if ((paramString1.equals("TEMPLATE_ID")) && (getTemplateID() != null)) {
      return isFilterable(getTemplateID(), paramString2, paramString3);
    }
    if ((paramString1.equals("WIRE_SOURCE")) && (getWireSource() != null)) {
      return isFilterable(getWireSource(), paramString2, paramString3);
    }
    if ((paramString1.equals("WIRE_SCOPE")) && (getWireScope() != null)) {
      return isFilterable(getWireScope(), paramString2, paramString3);
    }
    if (paramString1.equals("BATCH_ID")) {
      return isFilterable(getBatchID(), paramString2, paramString3);
    }
    if (paramString1.equals("HOST_ID")) {
      return isFilterable(getHostID(), paramString2, paramString3);
    }
    if ((paramString1.equals("RECURRING_ID")) && (getRecurringID() != null)) {
      return isFilterable(getRecurringID(), paramString2, paramString3);
    }
    if ((paramString1.equals("DUE_DATE")) && (this.CY != null)) {
      return this.CY.isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((paramString1.equals("DATE_TO_POST")) && (this.Dd != null)) {
      return this.Dd.isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((paramString1.equals("DATE_POSTED")) && (this.CH != null)) {
      return this.CH.isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((paramString1.equals("FROM_ACCOUNT_ID")) && (getFromAccountID() != null)) {
      return isFilterable(getFromAccountID(), paramString2, paramString3);
    }
    if ((paramString1.equals("FROM_ACCOUNT_ROUTING_NUM")) && (getFromAccountRoutingNum() != null)) {
      return isFilterable(getFromAccountRoutingNum(), paramString2, paramString3);
    }
    if ((paramString1.equals("WIRE_DESTINATION")) && (getWireDestination() != null)) {
      return isFilterable(getWireDestination(), paramString2, paramString3);
    }
    if ((paramString1.equals("CONFIRMATIONNUMBER")) && (getConfirmationNum() != null)) {
      return isFilterable(getConfirmationNum(), paramString2, paramString3);
    }
    if ((paramString1.equals("FED_CONFIRMATION_NUM")) && (getFedConfirmationNum() != null)) {
      return isFilterable(getFedConfirmationNum(), paramString2, paramString3);
    }
    if (paramString1.equals("NUMBER_TRANSFERS")) {
      return isFilterable(String.valueOf(getNumberTransfers()), paramString2, paramString3);
    }
    if (paramString1.equals("FREQUENCY")) {
      return isFilterable(String.valueOf(getFrequency()), paramString2, paramString3);
    }
    if (paramString1.equals("CONTRACT_NUMBER")) {
      return isFilterable(getContractNumber(), paramString2, paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public Object clone()
  {
    try
    {
      WireTransfer localWireTransfer = (WireTransfer)super.clone();
      if (this.CR != null) {
        localWireTransfer.setWireLimit((Currency)this.CR.clone());
      }
      if (this.C7 != null) {
        localWireTransfer.setWirePayee((WireTransferPayee)this.C7.clone());
      }
      if (this.CY != null) {
        localWireTransfer.setDueDate((DateTime)this.CY.clone());
      }
      if (this.Dd != null) {
        localWireTransfer.setDateToPost((DateTime)this.Dd.clone());
      }
      if (this.Df != null) {
        localWireTransfer.setSettlementDate((DateTime)this.Df.clone());
      }
      if (this.CH != null) {
        localWireTransfer.setDatePosted((DateTime)this.CH.clone());
      }
      if (this.CT != null) {
        localWireTransfer.setEndDate((DateTime)this.CT.clone());
      }
      if (this.Ck != null) {
        localWireTransfer.setStartAmount((Currency)this.Ck.clone());
      }
      if (this.Cr != null) {
        localWireTransfer.setEndAmount((Currency)this.Cr.clone());
      }
      if (this.wireCreditInfo != null) {
        localWireTransfer.setWireCreditInfo((WireTransferPayee)this.wireCreditInfo.clone());
      }
      return localWireTransfer;
    }
    catch (Exception localException) {}
    return super.clone();
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "DATE_TO_POST");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    WireTransfer localWireTransfer = (WireTransfer)paramObject;
    int i = 1;
    Collator localCollator = doGetCollator();
    if (paramString.equals("TRANSACTION_INDEX"))
    {
      if (getTransactionIndex() < localWireTransfer.getTransactionIndex()) {
        return -1;
      }
      if (getTransactionIndex() == localWireTransfer.getTransactionIndex()) {
        return 0;
      }
      return 1;
    }
    if ((paramString.equalsIgnoreCase("WIRE_NAME")) && (getWireName() != null) && (localWireTransfer.getWireName() != null)) {
      i = localCollator.compare(getWireName(), localWireTransfer.getWireName());
    } else if ((paramString.equals("ID")) && (getID() != null) && (localWireTransfer.getID() != null)) {
      i = numStringCompare(getID(), localWireTransfer.getID());
    } else if ((paramString.equals("WIRE_PAYEE")) && (getWirePayee() != null) && (localWireTransfer.getWirePayee() != null)) {
      i = getWirePayee().getPayeeName().compareToIgnoreCase(localWireTransfer.getWirePayee().getPayeeName());
    } else if ((paramString.equals("WIRE_PAYEE_ID")) && (getWirePayeeID() != null) && (localWireTransfer.getWirePayeeID() != null)) {
      i = numStringCompare(getWirePayeeID(), localWireTransfer.getWirePayeeID());
    } else if ((paramString.equals("RECURRING_ID")) && (getRecurringID() != null) && (localWireTransfer.getRecurringID() != null)) {
      i = numStringCompare(getRecurringID(), localWireTransfer.getRecurringID());
    } else if ((paramString.equals("CUSTOMER_ID")) && (getCustomerID() != null) && (localWireTransfer.getCustomerID() != null)) {
      i = numStringCompare(getCustomerID(), localWireTransfer.getCustomerID());
    } else if ((paramString.equals("SUBMITTED_BY")) && (getSubmittedBy() != null) && (localWireTransfer.getSubmittedBy() != null)) {
      i = getSubmittedBy().compareTo(localWireTransfer.getSubmittedBy());
    } else if ((paramString.equals("USERID")) && (getUserID() != null) && (localWireTransfer.getUserID() != null)) {
      i = getUserID().compareTo(localWireTransfer.getUserID());
    } else if ((paramString.equals("PROCESSED_BY")) && (getProcessedBy() != null) && (localWireTransfer.getProcessedBy() != null)) {
      i = getProcessedBy().compareTo(localWireTransfer.getProcessedBy());
    } else if ((paramString.equals("BATCH_ID")) && (getBatchID() != null) && (localWireTransfer.getBatchID() != null)) {
      i = getBatchID().compareTo(localWireTransfer.getBatchID());
    } else if ((paramString.equals("HOST_ID")) && (getHostID() != null) && (localWireTransfer.getHostID() != null)) {
      i = getHostID().compareTo(localWireTransfer.getHostID());
    } else if ((paramString.equals("TEMPLATE_ID")) && (getTemplateID() != null) && (localWireTransfer.getTemplateID() != null)) {
      i = getTemplateID().compareTo(localWireTransfer.getTemplateID());
    } else if ((paramString.equals("DUE_DATE")) && (getDueDateValue() != null) && (localWireTransfer.getDueDateValue() != null)) {
      i = this.CY.equals(localWireTransfer.getDueDateValue()) ? 0 : this.CY.before(localWireTransfer.getDueDateValue()) ? -1 : 1;
    } else if ((paramString.equals("DATE_TO_POST")) && (getDateToPostValue() != null) && (localWireTransfer.getDateToPostValue() != null)) {
      i = this.Dd.equals(localWireTransfer.getDateToPostValue()) ? 0 : this.Dd.before(localWireTransfer.getDateToPostValue()) ? -1 : 1;
    } else if ((paramString.equals("DATE_POSTED")) && (getDatePostedValue() != null) && (localWireTransfer.getDatePostedValue() != null)) {
      i = this.CH.equals(localWireTransfer.getDatePostedValue()) ? 0 : this.CH.before(localWireTransfer.getDatePostedValue()) ? -1 : 1;
    } else if ((paramString.equals("FROM_ACCOUNT_ID")) && (getFromAccountID() != null) && (localWireTransfer.getFromAccountID() != null)) {
      i = getFromAccountID().compareTo(localWireTransfer.getFromAccountID());
    } else if ((paramString.equals("FROM_ACCOUNT_ROUTING_NUM")) && (getFromAccountRoutingNum() != null) && (localWireTransfer.getFromAccountRoutingNum() != null)) {
      i = getFromAccountRoutingNum().compareTo(localWireTransfer.getFromAccountRoutingNum());
    } else if ((paramString.equals("AMOUNT")) && (this.amount != null) && (localWireTransfer.getAmountValue() != null)) {
      i = this.amount.compareTo(localWireTransfer.getAmountValue());
    } else if (paramString.equals("STATUS")) {
      i = getStatus() - localWireTransfer.getStatus();
    } else if ((paramString.equals("WIRE_DESTINATION")) && (getWireDestination() != null) && (localWireTransfer.getWireDestination() != null)) {
      i = getWireDestination().compareTo(localWireTransfer.getWireDestination());
    } else if ((paramString.equals("CONFIRMATIONNUMBER")) && (getConfirmationNum() != null) && (localWireTransfer.getConfirmationNum() != null)) {
      i = numStringCompare(getConfirmationNum(), localWireTransfer.getConfirmationNum());
    } else if ((paramString.equals("FED_CONFIRMATION_NUM")) && (getFedConfirmationNum() != null) && (localWireTransfer.getFedConfirmationNum() != null)) {
      i = numStringCompare(getFedConfirmationNum(), localWireTransfer.getFedConfirmationNum());
    } else if (paramString.equals("NUMBER_TRANSFERS")) {
      i = getNumberTransfersValue() - localWireTransfer.getNumberTransfersValue();
    } else if (paramString.equals("FREQUENCY")) {
      i = getFrequencyValue() - localWireTransfer.getFrequencyValue();
    } else if ((paramString.equals("CONTRACT_NUMBER")) && (getContractNumber() != null) && (localWireTransfer.getContractNumber() != null)) {
      i = getContractNumber().compareTo(localWireTransfer.getContractNumber());
    } else if ((paramString.equals("ORIG_AMOUNT")) && (getOrigAmount() != null) && (localWireTransfer.getOrigAmount() != null)) {
      i = getOrigAmount().compareTo(localWireTransfer.getOrigAmount());
    } else if ((paramString.equals("ORIG_CURRENCY")) && (getOrigCurrency() != null) && (localWireTransfer.getOrigCurrency() != null)) {
      i = getOrigCurrency().compareTo(localWireTransfer.getOrigCurrency());
    } else if ((paramString.equals("PAYMENT_AMOUNT")) && (getPaymentAmount() != null) && (localWireTransfer.getPaymentAmount() != null)) {
      i = getPaymentAmount().compareTo(localWireTransfer.getPaymentAmount());
    } else if ((paramString.equals("BY_ORDER_OF_NAME")) && (getByOrderOfName() != null) && (localWireTransfer.getByOrderOfName() != null)) {
      i = getByOrderOfName().compareTo(localWireTransfer.getByOrderOfName());
    } else if ((paramString.equals("BY_ORDER_OF_ADDRESS1")) && (getByOrderOfAddress1() != null) && (localWireTransfer.getByOrderOfAddress1() != null)) {
      i = getByOrderOfAddress1().compareTo(localWireTransfer.getByOrderOfAddress1());
    } else if ((paramString.equals("BY_ORDER_OF_ADDRESS2")) && (getByOrderOfAddress2() != null) && (localWireTransfer.getByOrderOfAddress2() != null)) {
      i = getByOrderOfAddress2().compareTo(localWireTransfer.getByOrderOfAddress2());
    } else if ((paramString.equals("BY_ORDER_OF_ADDRESS3")) && (getByOrderOfAddress3() != null) && (localWireTransfer.getByOrderOfAddress3() != null)) {
      i = getByOrderOfAddress3().compareTo(localWireTransfer.getByOrderOfAddress3());
    } else if ((paramString.equals("BY_ORDER_OF_ACCOUNT")) && (getByOrderOfAccount() != null) && (localWireTransfer.getByOrderOfAccount() != null)) {
      i = getByOrderOfAccount().compareTo(localWireTransfer.getByOrderOfAccount());
    } else if ((paramString.equals("WIRE_CREDIT_INFO")) && (getWireCreditInfo() != null) && (localWireTransfer.getWireCreditInfo() != null)) {
      i = getWireCreditInfo().getPayeeName().compareToIgnoreCase(localWireTransfer.getWireCreditInfo().getPayeeName());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "WIRE_TRANSFER");
    XMLHandler.appendTag(localStringBuffer, "WIRE_PAYEE_ID", getWirePayeeID());
    if (getWirePayee() != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "WIRE_PAYEE");
      localStringBuffer.append(getWirePayee().getXML());
      XMLHandler.appendEndTag(localStringBuffer, "WIRE_PAYEE");
    }
    XMLHandler.appendTag(localStringBuffer, "CUSTOMER_ID", getCustomerID());
    XMLHandler.appendTag(localStringBuffer, "SUBMITTED_BY", getSubmittedBy());
    XMLHandler.appendTag(localStringBuffer, "USERID", getUserID());
    XMLHandler.appendTag(localStringBuffer, "PROCESSED_BY", getProcessedBy());
    XMLHandler.appendTag(localStringBuffer, "APPROVING_USER", getApprovingUser());
    XMLHandler.appendTag(localStringBuffer, "TEMPLATE_ID", getTemplateID());
    XMLHandler.appendTag(localStringBuffer, "BATCH_ID", getBatchID());
    XMLHandler.appendTag(localStringBuffer, "HOST_ID", getHostID());
    XMLHandler.appendTag(localStringBuffer, "BANK_ID", getBankID());
    XMLHandler.appendTag(localStringBuffer, "FROM_ACCOUNT_ID", getFromAccountID());
    XMLHandler.appendTag(localStringBuffer, "FROM_ACCOUNT_ROUTING_NUM", getFromAccountRoutingNum());
    XMLHandler.appendTag(localStringBuffer, "CURRENCY_TYPE", getAmtCurrencyType());
    XMLHandler.appendTag(localStringBuffer, "PAYEE_CURRENCY_TYPE", getPayeeCurrencyType());
    XMLHandler.appendTag(localStringBuffer, "CONTRACT_NUMBER", getContractNumber());
    XMLHandler.appendTag(localStringBuffer, "EXCHANGE_RATE", getExchangeRate());
    XMLHandler.appendTag(localStringBuffer, "MATHRULE", getMathRule());
    XMLHandler.appendTag(localStringBuffer, "DUE_DATE", getDueDate());
    XMLHandler.appendTag(localStringBuffer, "DATE_TO_POST", getDateToPost());
    XMLHandler.appendTag(localStringBuffer, "LOG_DATE", getLogDate());
    XMLHandler.appendTag(localStringBuffer, "SETTLEMENT_DATE", getSettlementDate());
    XMLHandler.appendTag(localStringBuffer, "WIRE_CREATE_DATE", getCreateDate());
    XMLHandler.appendTag(localStringBuffer, "DATE_POSTED", getDatePosted());
    XMLHandler.appendTag(localStringBuffer, "COMMENT", getComment());
    XMLHandler.appendTag(localStringBuffer, "INFO_TO_BENEFICIARY", l());
    XMLHandler.appendTag(localStringBuffer, "INFO_TO_BANK", m());
    XMLHandler.appendTag(localStringBuffer, "STATUS", getStatus());
    XMLHandler.appendTag(localStringBuffer, "WIRE_NAME", getWireName());
    XMLHandler.appendTag(localStringBuffer, "WIRE_NICKNAME", getNickName());
    XMLHandler.appendTag(localStringBuffer, "WIRE_CATEGORY", getWireCategory());
    XMLHandler.appendTag(localStringBuffer, "WIRE_SOURCE", getWireSource());
    XMLHandler.appendTag(localStringBuffer, "WIRE_LIMIT", getWireLimit());
    XMLHandler.appendTag(localStringBuffer, "WIRE_SCOPE", getWireScope());
    XMLHandler.appendTag(localStringBuffer, "WIRE_TYPE", getWireType());
    XMLHandler.appendTag(localStringBuffer, "CONFIRMATION_MSG", getConfirmationMsg());
    XMLHandler.appendTag(localStringBuffer, "WIRE_DESTINATION", getWireDestination());
    XMLHandler.appendTag(localStringBuffer, "CONFIRMATIONNUMBER", getConfirmationNum());
    XMLHandler.appendTag(localStringBuffer, "FED_CONFIRMATION_NUM", getFedConfirmationNum());
    XMLHandler.appendTag(localStringBuffer, "RECURRING_ID", getRecurringID());
    XMLHandler.appendTag(localStringBuffer, "NUMBER_TRANSFERS", getNumberTransfers());
    XMLHandler.appendTag(localStringBuffer, "FREQUENCY", getFrequencyValue());
    XMLHandler.appendTag(localStringBuffer, "START_AMOUNT", getStartAmount());
    XMLHandler.appendTag(localStringBuffer, "END_AMOUNT", getEndAmount());
    XMLHandler.appendTag(localStringBuffer, "ACTION", getAction());
    XMLHandler.appendTag(localStringBuffer, "ORIG_AMOUNT", getOrigAmount());
    XMLHandler.appendTag(localStringBuffer, "ORIG_CURRENCY", getOrigCurrency());
    XMLHandler.appendTag(localStringBuffer, "PAYMENT_AMOUNT", getPaymentAmount());
    XMLHandler.appendTag(localStringBuffer, "BY_ORDER_OF_NAME", getByOrderOfName());
    XMLHandler.appendTag(localStringBuffer, "BY_ORDER_OF_ADDRESS1", getByOrderOfAddress1());
    XMLHandler.appendTag(localStringBuffer, "BY_ORDER_OF_ADDRESS2", getByOrderOfAddress2());
    XMLHandler.appendTag(localStringBuffer, "BY_ORDER_OF_ADDRESS3", getByOrderOfAddress3());
    XMLHandler.appendTag(localStringBuffer, "BY_ORDER_OF_ACCOUNT", getByOrderOfAccount());
    if (getWireCreditInfo() != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "WIRE_CREDIT_INFO");
      localStringBuffer.append(getWireCreditInfo().getXML());
      XMLHandler.appendEndTag(localStringBuffer, "WIRE_CREDIT_INFO");
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "WIRE_TRANSFER");
    return localStringBuffer.toString();
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    if (paramString1.equalsIgnoreCase("WIRE_NAME")) {
      setWireName(paramString2);
    } else if (paramString1.equalsIgnoreCase("WIRE_NICKNAME")) {
      setNickName(paramString2);
    } else if (paramString1.equalsIgnoreCase("WIRE_CATEGORY")) {
      setWireCategory(paramString2);
    } else if (paramString1.equalsIgnoreCase("WIRE_SOURCE")) {
      setWireSource(paramString2);
    } else if (paramString1.equalsIgnoreCase("WIRE_LIMIT")) {
      setWireLimit(paramString2);
    } else if (paramString1.equalsIgnoreCase("WIRE_SCOPE")) {
      setWireScope(paramString2);
    } else if (paramString1.equalsIgnoreCase("WIRE_TYPE")) {
      setWireType(paramString2);
    } else if (paramString1.equalsIgnoreCase("RECURRING_ID")) {
      setRecurringID(paramString2);
    } else if (paramString1.equalsIgnoreCase("WIRE_PAYEE_ID")) {
      setWirePayeeID(paramString2);
    } else if (paramString1.equalsIgnoreCase("WIRE_PAYEE")) {
      this.C7.setPayeeName(paramString2);
    } else if (paramString1.equals("CUSTOMER_ID")) {
      setCustomerID(paramString2);
    } else if (paramString1.equals("SUBMITTED_BY")) {
      setSubmittedBy(paramString2);
    } else if (paramString1.equals("USERID")) {
      setUserID(paramString2);
    } else if (paramString1.equals("PROCESSED_BY")) {
      setProcessedBy(paramString2);
    } else if (paramString1.equals("APPROVING_USER")) {
      setApprovingUser(paramString2);
    } else if (paramString1.equalsIgnoreCase("TEMPLATE_ID")) {
      setTemplateID(paramString2);
    } else if (paramString1.equalsIgnoreCase("BATCH_ID")) {
      setBatchID(paramString2);
    } else if (paramString1.equalsIgnoreCase("HOST_ID")) {
      setHostID(paramString2);
    } else if (paramString1.equals("BANK_ID")) {
      setBankID(paramString2);
    } else if (paramString1.equalsIgnoreCase("FROM_ACCOUNT_ID")) {
      setFromAccountID(paramString2);
    } else if (paramString1.equalsIgnoreCase("FROM_ACCOUNT_TYPE")) {
      try
      {
        setFromAccountType(Integer.parseInt(paramString2));
      }
      catch (Exception localException)
      {
        setFromAccountType(0);
      }
    } else if (paramString1.equalsIgnoreCase("FROM_ACCOUNT_NUMBER")) {
      setFromAccountNum(paramString2);
    } else if (paramString1.equalsIgnoreCase("FROM_ACCOUNT_ROUTING_NUM")) {
      setFromAccountRoutingNum(paramString2);
    } else if (paramString1.equalsIgnoreCase("CURRENCY_TYPE")) {
      setAmtCurrencyType(paramString2);
    } else if (paramString1.equalsIgnoreCase("PAYEE_CURRENCY_TYPE")) {
      setPayeeCurrencyType(paramString2);
    } else if (paramString1.equalsIgnoreCase("EXCHANGE_RATE")) {
      setExchangeRate(paramString2);
    } else if (paramString1.equalsIgnoreCase("MATHRULE")) {
      setMathRule(paramString2);
    } else if (paramString1.equalsIgnoreCase("DUE_DATE")) {
      setDueDate(paramString2);
    } else if (paramString1.equalsIgnoreCase("DATE_TO_POST")) {
      setDateToPost(paramString2);
    } else if (paramString1.equalsIgnoreCase("SETTLEMENT_DATE")) {
      setSettlementDate(paramString2);
    } else if (paramString1.equalsIgnoreCase("WIRE_CREATE_DATE")) {
      setCreateDate(paramString2);
    } else if (paramString1.equalsIgnoreCase("DATE_POSTED")) {
      setDatePosted(paramString2);
    } else if (paramString1.equalsIgnoreCase("LOG_DATE")) {
      setLogDate(paramString2);
    } else if (paramString1.equalsIgnoreCase("COMMENT")) {
      setComment(paramString2);
    } else if (paramString1.equalsIgnoreCase("INFO_TO_BENEFICIARY")) {
      l(paramString2);
    } else if (paramString1.equalsIgnoreCase("INFO_TO_BANK")) {
      k(paramString2);
    } else if (paramString1.equalsIgnoreCase("STATUS")) {
      setStatus(paramString2);
    } else if (paramString1.equalsIgnoreCase("CONFIRMATION_MSG")) {
      setConfirmationMsg(paramString2);
    } else if (paramString1.equalsIgnoreCase("WIRE_DESTINATION")) {
      setWireDestination(paramString2);
    } else if (paramString1.equalsIgnoreCase("CONTRACT_NUMBER")) {
      setContractNumber(paramString2);
    } else if (paramString1.equalsIgnoreCase("CONFIRMATIONNUMBER")) {
      setConfirmationNum(paramString2);
    } else if (paramString1.equalsIgnoreCase("FED_CONFIRMATION_NUM")) {
      setFedConfirmationNum(paramString2);
    } else if (paramString1.equalsIgnoreCase("NUMBER_TRANSFERS")) {
      setNumberTransfers(paramString2);
    } else if (paramString1.equalsIgnoreCase("FREQUENCY")) {
      setFrequency(paramString2);
    } else if (paramString1.equalsIgnoreCase("START_AMOUNT")) {
      setStartAmount(paramString2);
    } else if (paramString1.equalsIgnoreCase("END_AMOUNT")) {
      setEndAmount(paramString2);
    } else if (paramString1.equalsIgnoreCase("ACTION")) {
      setAction(paramString2);
    } else if (paramString1.equalsIgnoreCase("ORIG_AMOUNT")) {
      setOrigAmount(paramString2);
    } else if (paramString1.equalsIgnoreCase("ORIG_CURRENCY")) {
      setOrigCurrency(paramString2);
    } else if (paramString1.equalsIgnoreCase("PAYMENT_AMOUNT")) {
      setPaymentAmount(paramString2);
    } else if (paramString1.equalsIgnoreCase("BY_ORDER_OF_NAME")) {
      setByOrderOfName(paramString2);
    } else if (paramString1.equalsIgnoreCase("BY_ORDER_OF_ADDRESS1")) {
      setByOrderOfAddress1(paramString2);
    } else if (paramString1.equalsIgnoreCase("BY_ORDER_OF_ADDRESS2")) {
      setByOrderOfAddress2(paramString2);
    } else if (paramString1.equalsIgnoreCase("BY_ORDER_OF_ADDRESS3")) {
      setByOrderOfAddress3(paramString2);
    } else if (paramString1.equalsIgnoreCase("BY_ORDER_OF_ACCOUNT")) {
      setByOrderOfAccount(paramString2);
    } else {
      return super.set(paramString1, paramString2);
    }
    return true;
  }
  
  public void set(WireTransfer paramWireTransfer)
  {
    if (paramWireTransfer == null) {
      return;
    }
    super.set(paramWireTransfer);
    if (paramWireTransfer.getDueDateValue() != null) {
      setDueDate((DateTime)paramWireTransfer.getDueDateValue().clone());
    } else {
      setDueDate((DateTime)null);
    }
    if (paramWireTransfer.getDateToPostValue() != null) {
      setDateToPost((DateTime)paramWireTransfer.getDateToPostValue().clone());
    } else {
      setDateToPost((DateTime)null);
    }
    if (paramWireTransfer.getSettlementDateValue() != null) {
      setSettlementDate((DateTime)paramWireTransfer.getSettlementDateValue().clone());
    } else {
      setSettlementDate((DateTime)null);
    }
    setCreateDate(paramWireTransfer.getCreateDate());
    if (paramWireTransfer.getDatePostedValue() != null) {
      setDatePosted((DateTime)paramWireTransfer.getDatePostedValue().clone());
    } else {
      setDatePosted((DateTime)null);
    }
    setLogDate(paramWireTransfer.getLogDate());
    setID(paramWireTransfer.getID());
    setWireName(paramWireTransfer.getWireName());
    setNickName(paramWireTransfer.getNickName());
    setWireCategory(paramWireTransfer.getWireCategory());
    setWireSource(paramWireTransfer.getWireSource());
    if (paramWireTransfer.getWireLimitValue() != null) {
      setWireLimit((Currency)paramWireTransfer.getWireLimitValue().clone());
    } else {
      setWireLimit((Currency)null);
    }
    setWireScope(paramWireTransfer.getWireScope());
    setWireType(paramWireTransfer.getWireType());
    setTemplateID(paramWireTransfer.getTemplateID());
    setBatchID(paramWireTransfer.getBatchID());
    setHostID(paramWireTransfer.getHostID());
    setRecurringID(paramWireTransfer.getRecurringID());
    setBankID(paramWireTransfer.getBankID());
    setCustomerID(paramWireTransfer.getCustomerID());
    setSubmittedBy(paramWireTransfer.getSubmittedBy());
    setUserID(paramWireTransfer.getUserID());
    setProcessedBy(paramWireTransfer.getProcessedBy());
    setApprovingUser(paramWireTransfer.getApprovingUser());
    setWirePayeeID(paramWireTransfer.getWirePayeeID());
    if (paramWireTransfer.getWirePayee() != null) {
      setWirePayee((WireTransferPayee)paramWireTransfer.getWirePayee().clone());
    }
    setFromAccountID(paramWireTransfer.getFromAccountID());
    setFromAccountNum(paramWireTransfer.getFromAccountNum());
    setFromAccountType(paramWireTransfer.getFromAccountType());
    setFromAccountRoutingNum(paramWireTransfer.getFromAccountRoutingNum());
    setAmtCurrencyType(paramWireTransfer.getAmtCurrencyType());
    setPayeeCurrencyType(paramWireTransfer.getPayeeCurrencyType());
    setExchangeRate(paramWireTransfer.getExchangeRateValue());
    setMathRule(paramWireTransfer.getMathRule());
    setComment(paramWireTransfer.getComment());
    setOrigToBeneficiaryInfo1(paramWireTransfer.getOrigToBeneficiaryInfo1());
    setOrigToBeneficiaryInfo2(paramWireTransfer.getOrigToBeneficiaryInfo2());
    setOrigToBeneficiaryInfo3(paramWireTransfer.getOrigToBeneficiaryInfo3());
    setOrigToBeneficiaryInfo4(paramWireTransfer.getOrigToBeneficiaryInfo4());
    setBankToBankInfo1(paramWireTransfer.getBankToBankInfo1());
    setBankToBankInfo2(paramWireTransfer.getBankToBankInfo2());
    setBankToBankInfo3(paramWireTransfer.getBankToBankInfo3());
    setBankToBankInfo4(paramWireTransfer.getBankToBankInfo4());
    setBankToBankInfo5(paramWireTransfer.getBankToBankInfo5());
    setBankToBankInfo6(paramWireTransfer.getBankToBankInfo6());
    setStatus(paramWireTransfer.getStatus());
    setReferenceNumber(paramWireTransfer.getReferenceNumber());
    setConfirmationMsg(paramWireTransfer.getConfirmationMsg());
    setWireDestination(paramWireTransfer.getWireDestination());
    setContractNumber(paramWireTransfer.getContractNumber());
    setConfirmationNum(paramWireTransfer.getConfirmationNum());
    setFedConfirmationNum(paramWireTransfer.getFedConfirmationNum());
    setNumberTransfers(paramWireTransfer.getNumberTransfersValue());
    setFrequency(paramWireTransfer.getFrequencyValue());
    if (paramWireTransfer.getStartAmountValue() != null) {
      setStartAmount((Currency)paramWireTransfer.getStartAmountValue().clone());
    } else {
      setStartAmount((Currency)null);
    }
    if (paramWireTransfer.getEndAmountValue() != null) {
      setEndAmount((Currency)paramWireTransfer.getEndAmountValue().clone());
    } else {
      setEndAmount((Currency)null);
    }
    setCanEdit(paramWireTransfer.getCanEditValue());
    setCanDelete(paramWireTransfer.getCanDeleteValue());
    setCanLoad(paramWireTransfer.getCanLoadValue());
    setAction(paramWireTransfer.getAction());
    setOrigAmount(paramWireTransfer.getOrigAmount());
    setOrigCurrency(paramWireTransfer.getOrigCurrency());
    setPaymentAmount(paramWireTransfer.getPaymentAmount());
    setByOrderOfName(paramWireTransfer.getByOrderOfName());
    setByOrderOfAddress1(paramWireTransfer.getByOrderOfAddress1());
    setByOrderOfAddress2(paramWireTransfer.getByOrderOfAddress2());
    setByOrderOfAddress3(paramWireTransfer.getByOrderOfAddress3());
    setByOrderOfAccount(paramWireTransfer.getByOrderOfAccount());
    if (paramWireTransfer.getWireCreditInfo() != null) {
      setWireCreditInfo((WireTransferPayee)paramWireTransfer.getWireCreditInfo().clone());
    }
    setVersion(paramWireTransfer.getVersion());
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(), paramString);
    }
    catch (Throwable localThrowable) {}
  }
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  public WireInfo getWireInfo()
  {
    WireInfo localWireInfo = new WireInfo();
    jdMethod_for(localWireInfo);
    return localWireInfo;
  }
  
  public RecWireInfo getRecWireInfo()
  {
    RecWireInfo localRecWireInfo = new RecWireInfo();
    jdMethod_for(localRecWireInfo);
    localRecWireInfo.setFrequency(WireFrequencyMap.mapFreqToStr(getFrequencyValue()));
    localRecWireInfo.setPmtsCount(getNumberTransfersValue());
    localRecWireInfo.setStartAmount(getStartAmount());
    localRecWireInfo.setEndAmount(getEndAmount());
    return localRecWireInfo;
  }
  
  private void jdMethod_for(WireInfo paramWireInfo)
  {
    paramWireInfo.setSrvrTid(getID());
    paramWireInfo.setWireName(getWireName());
    paramWireInfo.setNickName(getNickName());
    paramWireInfo.setWireCategory(getWireCategory());
    paramWireInfo.setWireSource(getWireSource());
    paramWireInfo.setWireLimit(getWireLimit());
    paramWireInfo.setWireScope(getWireScope());
    paramWireInfo.setWireType(getWireType());
    paramWireInfo.setTemplateId(getTemplateID());
    paramWireInfo.setBatchId(getBatchID());
    paramWireInfo.setHostId(getHostID());
    paramWireInfo.setRecSrvrTid(getRecurringID());
    paramWireInfo.setTrnId(getTrackingID());
    paramWireInfo.setExtId(getTrackingID());
    paramWireInfo.setFiID(getBankID());
    paramWireInfo.setAction(getAction());
    paramWireInfo.setOrigAmount(getOrigAmount());
    paramWireInfo.setOrigCurrency(getOrigCurrency());
    paramWireInfo.setPrcStatus(WireStatusMap.mapToStatusToStr(getStatus()));
    paramWireInfo.setOrigToBeneficiaryMemo(getComment());
    paramWireInfo.setOrigToBeneficiaryInfo(l());
    paramWireInfo.setBanktoBankInfo(m());
    paramWireInfo.setByOrderOfName(getByOrderOfName());
    paramWireInfo.setByOrderOfAddr1(getByOrderOfAddress1());
    paramWireInfo.setByOrderOfAddr2(getByOrderOfAddress2());
    paramWireInfo.setByOrderOfAddr3(getByOrderOfAddress3());
    paramWireInfo.setByOrderOfAcctNum(getByOrderOfAccount());
    paramWireInfo.setAmtCurrency(getAmtCurrencyType());
    paramWireInfo.setDestCurrency(getPayeeCurrencyType());
    paramWireInfo.setMathRule(getMathRule());
    paramWireInfo.setAmount(getAmountForBPW());
    paramWireInfo.setExchangeRate(getExchangeRate());
    paramWireInfo.setWireDest(getWireDestination());
    paramWireInfo.setContractNumber(getContractNumber());
    if (this.Cd.equals("DRAWDOWN")) {
      paramWireInfo.setAmountType("CREDIT");
    } else {
      paramWireInfo.setAmountType("DEBIT");
    }
    paramWireInfo.setFromBankId(getFromAccountRoutingNum());
    paramWireInfo.setFromAcctId(getFromAccountNum());
    paramWireInfo.setFromAcctType(WireAccountMap.mapAccountTypeToStr(getFromAccountType()));
    paramWireInfo.setCustomerID(getCustomerID());
    paramWireInfo.setSubmitedBy(getSubmittedBy());
    paramWireInfo.setUserId(getUserID());
    if ((getWirePayeeID() != null) && (getWirePayeeID().length() != 0)) {
      paramWireInfo.setWirePayeeId(getWirePayeeID());
    }
    WireTransferPayee localWireTransferPayee = getWirePayee();
    if (localWireTransferPayee != null)
    {
      localWireTransferPayee.setCustomerID(getCustomerID());
      localWireTransferPayee.setSubmittedBy(getSubmittedBy());
      if (!localWireTransferPayee.getPayeeScope().equals("UNMANAGED")) {
        localWireTransferPayee.setAction("add");
      }
      paramWireInfo.setWirePayeeInfo(localWireTransferPayee.getWirePayeeInfo());
    }
    else
    {
      paramWireInfo.setWirePayeeInfo(null);
    }
    Object localObject1;
    if (getWireCreditInfo() != null)
    {
      localObject1 = getWireCreditInfo();
      ((WireTransferPayee)localObject1).setTrackingID(getTrackingID());
      ((WireTransferPayee)localObject1).setCustomerID(getCustomerID());
      ((WireTransferPayee)localObject1).setSubmittedBy(getSubmittedBy());
      if (isNullOrEmpty(getWireCreditInfo().getAction())) {
        ((WireTransferPayee)localObject1).setAction("add");
      }
      ((WireTransferPayee)localObject1).setPayeeDestination("CREDIT");
      ((WireTransferPayee)localObject1).setPayeeScope("SECURE");
      ((WireTransferPayee)localObject1).setAccountNum(getFromAccountNum());
      ((WireTransferPayee)localObject1).setAccountType(WireAccountMap.mapAccountTypeToStr(getFromAccountType()));
      paramWireInfo.setWireCreditInfo(((WireTransferPayee)localObject1).getWirePayeeInfo());
      if ((((WireTransferPayee)localObject1).getID() != null) && (((WireTransferPayee)localObject1).getID().length() != 0)) {
        paramWireInfo.setWireCreditId(((WireTransferPayee)localObject1).getID());
      }
    }
    if (this.map != null)
    {
      localObject1 = new Hashtable();
      Iterator localIterator = this.map.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        Object localObject2 = this.map.get(str);
        if ((localObject2 != null) && ((localObject2 instanceof ValueInfo))) {
          ((Hashtable)localObject1).put(str, localObject2);
        }
      }
      paramWireInfo.setExtInfo((Hashtable)localObject1);
    }
    paramWireInfo.setVersion(getVersion());
  }
  
  public void setWireInfo(WireInfo paramWireInfo)
  {
    if (paramWireInfo == null) {
      return;
    }
    try
    {
      setID(paramWireInfo.getSrvrTid());
      setExternalID(paramWireInfo.getSrvrTid());
      setWireName(paramWireInfo.getWireName());
      setNickName(paramWireInfo.getNickName());
      setWireCategory(paramWireInfo.getWireCategory());
      setWireSource(paramWireInfo.getWireSource());
      setWireLimit(paramWireInfo.getWireLimit());
      setWireScope(paramWireInfo.getWireScope());
      setWireType(paramWireInfo.getWireType());
      setTemplateID(paramWireInfo.getTemplateId());
      setBatchID(paramWireInfo.getBatchId());
      setHostID(paramWireInfo.getHostId());
      setRecurringID(paramWireInfo.getRecSrvrTid());
      setTrackingID(paramWireInfo.getExtId());
      setReferenceNumber(paramWireInfo.getSrvrTid());
      setStatus(WireStatusMap.mapStatusToInt(paramWireInfo.getPrcStatus()));
      setComment(paramWireInfo.getOrigToBeneficiaryMemo());
      l(paramWireInfo.getOrigToBeneficiaryInfo());
      k(paramWireInfo.getBanktoBankInfo());
      setByOrderOfName(paramWireInfo.getByOrderOfName());
      setByOrderOfAddress1(paramWireInfo.getByOrderOfAddr1());
      setByOrderOfAddress2(paramWireInfo.getByOrderOfAddr2());
      setByOrderOfAddress3(paramWireInfo.getByOrderOfAddr3());
      setByOrderOfAccount(paramWireInfo.getByOrderOfAcctNum());
      setAmtCurrencyType(paramWireInfo.getAmtCurrency());
      setPayeeCurrencyType(paramWireInfo.getDestCurrency());
      setMathRule(paramWireInfo.getMathRule());
      setAmount(paramWireInfo.getAmount());
      setWireDestination(paramWireInfo.getWireDest());
      setContractNumber(paramWireInfo.getContractNumber());
      setBankID(paramWireInfo.getFiID());
      setFromAccountRoutingNum(paramWireInfo.getFromBankId());
      int i = WireAccountMap.mapAccountTypeToInt(paramWireInfo.getFromAcctType());
      String str1 = paramWireInfo.getFromAcctId();
      Account localAccount = new Account();
      localAccount.setID(str1, Integer.toString(i));
      setFromAccountID(localAccount.getID());
      setFromAccountNum(localAccount.getNumber());
      setFromAccountType(localAccount.getTypeValue());
      setWireDates(paramWireInfo);
      setCustomerID(paramWireInfo.getCustomerID());
      setSubmittedBy(paramWireInfo.getSubmitedBy());
      setUserID(paramWireInfo.getUserId());
      setProcessedBy(paramWireInfo.getProcessedBy());
      WirePayeeInfo localWirePayeeInfo = paramWireInfo.getWirePayeeInfo();
      if (localWirePayeeInfo != null)
      {
        localObject1 = new WireTransferPayee();
        ((WireTransferPayee)localObject1).setWirePayeeInfo(localWirePayeeInfo);
        setWirePayee((WireTransferPayee)localObject1);
      }
      setWirePayeeID(paramWireInfo.getWirePayeeId());
      setConfirmationMsg(paramWireInfo.getConfirmMsg());
      setConfirmationNum(paramWireInfo.getConfirmNum());
      setFedConfirmationNum(paramWireInfo.getConfirmNum2());
      setExchangeRate(paramWireInfo.getExchangeRate());
      setFrequency(0);
      if (paramWireInfo.getAction() == null) {
        setAction("");
      } else {
        setAction(paramWireInfo.getAction());
      }
      setOrigAmount(paramWireInfo.getOrigAmount());
      setOrigCurrency(paramWireInfo.getOrigCurrency());
      Object localObject2;
      if (paramWireInfo.getWireCreditInfo() != null)
      {
        localObject1 = paramWireInfo.getWireCreditInfo();
        localObject2 = new WireTransferPayee();
        ((WireTransferPayee)localObject2).setWirePayeeInfo((WirePayeeInfo)localObject1);
        setWireCreditInfo((WireTransferPayee)localObject2);
      }
      Object localObject1 = paramWireInfo.getExtInfo();
      if (localObject1 != null)
      {
        if (this.map == null) {
          this.map = new HashMap();
        }
        localObject2 = ((Hashtable)localObject1).keys();
        while (((Enumeration)localObject2).hasMoreElements())
        {
          String str2 = (String)((Enumeration)localObject2).nextElement();
          ValueInfo localValueInfo = (ValueInfo)((Hashtable)localObject1).get(str2);
          if (localValueInfo != null)
          {
            localValueInfo.setAction("");
            this.map.put(str2, localValueInfo);
          }
        }
      }
      setVersion(paramWireInfo.getVersion());
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public void setRecWireInfo(RecWireInfo paramRecWireInfo)
  {
    if (paramRecWireInfo == null) {
      return;
    }
    setWireInfo(paramRecWireInfo);
    try
    {
      setFrequency(WireFrequencyMap.mapFreqToInt(paramRecWireInfo.getFrequency()));
      setType(6);
      setNumberTransfers(paramRecWireInfo.getPmtsCount());
      setStartAmount(paramRecWireInfo.getStartAmount());
      setEndAmount(paramRecWireInfo.getEndAmount());
      setDateFormat("yyyyMMdd");
      String str = paramRecWireInfo.getStartDate();
      if ((str != null) && (str.length() > 8)) {
        str = str.substring(0, 8);
      }
      setDueDate(str);
      setDateFormat("SHORT");
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public Currency getCurrencyValue(double paramDouble)
  {
    return new Currency(new BigDecimal(paramDouble), this.locale);
  }
  
  protected void getInquireComment(StringBuffer paramStringBuffer)
  {
    super.getInquireComment(paramStringBuffer);
    String str1 = getDateToPost();
    if ((str1 != null) && (str1.length() != 0)) {
      paramStringBuffer.append("Date is ").append(str1).append(".\n");
    }
    String str2 = getWireSource();
    if ("HOST".equals(str2))
    {
      paramStringBuffer.append("Beneficiary is HOST.\n");
    }
    else
    {
      localObject = getWirePayee();
      if ((localObject != null) && (((WireTransferPayee)localObject).getPayeeName() != null)) {
        paramStringBuffer.append("Beneficiary is ").append(((WireTransferPayee)localObject).getPayeeName()).append(".\n");
      } else {
        paramStringBuffer.append("No beneficiary.\n");
      }
    }
    Object localObject = getFrequency();
    if ((localObject == null) || (((String)localObject).toString().length() == 0)) {
      paramStringBuffer.append("No frequency.\n");
    } else {
      paramStringBuffer.append("Frequency is ").append((String)localObject).append(".\n");
    }
    String str3 = getStatusName();
    if ((str3 == null) || (str3.toString().length() == 0)) {
      paramStringBuffer.append("No status.\n");
    } else {
      paramStringBuffer.append("Status is ").append(str3).append(".\n");
    }
  }
  
  public void logChanges(SecureUser paramSecureUser, HistoryTracker paramHistoryTracker, WireTransfer paramWireTransfer, String paramString)
  {
    logChanges(paramSecureUser, paramHistoryTracker, BEAN_NAME, paramWireTransfer, paramString);
  }
  
  public void logChanges(SecureUser paramSecureUser, HistoryTracker paramHistoryTracker, String paramString1, WireTransfer paramWireTransfer, String paramString2)
  {
    if (paramWireTransfer == null)
    {
      DebugLog.log("WARNING: Unable to record history for wire modification due to null old wire.");
      return;
    }
    paramHistoryTracker.detectChange(paramString1, "FROM_ACCOUNT_ID", paramWireTransfer.getFromAccountDisplayText(), getFromAccountDisplayText(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "FROM_ACCOUNT_ROUTING_NUM", paramWireTransfer.getFromAccountRoutingNum(), getFromAccountRoutingNum(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "BANK_ID", paramWireTransfer.getBankID(), getBankID(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "CURRENCY_TYPE", paramWireTransfer.getAmtCurrencyType(), getAmtCurrencyType(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "PAYEE_CURRENCY_TYPE", paramWireTransfer.getPayeeCurrencyType(), getPayeeCurrencyType(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "CONTRACT_NUMBER", paramWireTransfer.getContractNumber(), getContractNumber(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "EXCHANGE_RATE", paramWireTransfer.getExchangeRateValue(), getExchangeRateValue(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "MATHRULE", paramWireTransfer.getMathRule(), getMathRule(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "DUE_DATE", paramWireTransfer.getDueDate(), getDueDate(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "DATE_TO_POST", paramWireTransfer.getDateToPost(), getDateToPost(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "SETTLEMENT_DATE", paramWireTransfer.getSettlementDate(), getSettlementDate(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "WIRE_CREATE_DATE", paramWireTransfer.getCreateDate(), getCreateDate(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "COMMENT", paramWireTransfer.getComment(), getComment(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "INFO_TO_BENEFICIARY", paramWireTransfer.l(), l(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "INFO_TO_BANK", paramWireTransfer.m(), m(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "WIRE_NAME", paramWireTransfer.getWireName(), getWireName(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "WIRE_NICKNAME", paramWireTransfer.getNickName(), getNickName(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "WIRE_CATEGORY", paramWireTransfer.getWireCategory(), getWireCategory(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "WIRE_SCOPE", paramWireTransfer.getWireScope(), getWireScope(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "WIRE_DESTINATION", paramWireTransfer.getWireDestination(), getWireDestination(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "NUMBER_TRANSFERS", paramWireTransfer.getNumberTransfers(), getNumberTransfers(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "FREQUENCY", paramWireTransfer.getFrequency(), getFrequency(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "ORIG_AMOUNT", paramWireTransfer.getOrigAmount(), getOrigAmount(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "ORIG_CURRENCY", paramWireTransfer.getOrigCurrency(), getOrigCurrency(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "BY_ORDER_OF_NAME", paramWireTransfer.getByOrderOfName(), getByOrderOfName(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "BY_ORDER_OF_ADDRESS1", paramWireTransfer.getByOrderOfAddress1(), getByOrderOfAddress1(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "BY_ORDER_OF_ADDRESS2", paramWireTransfer.getByOrderOfAddress2(), getByOrderOfAddress2(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "BY_ORDER_OF_ADDRESS3", paramWireTransfer.getByOrderOfAddress3(), getByOrderOfAddress3(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "BY_ORDER_OF_ACCOUNT", paramWireTransfer.getByOrderOfAccount(), getByOrderOfAccount(), paramString2);
    if ((this.CC != null) && (this.CC.length() > 0))
    {
      paramHistoryTracker.detectChange(paramString1, "WIRE_PAYEE_ID", paramWireTransfer.getWirePayeeID(), getWirePayeeID(), paramString2);
    }
    else if (this.C7 != null)
    {
      jdMethod_for(paramSecureUser, paramHistoryTracker, paramString2);
      paramHistoryTracker.detectChange(paramString1, "WIRE_PAYEE_ID", paramWireTransfer.getWirePayeeID(), this.C7.getID(), paramString2);
    }
    if (this.wireCreditInfo != null) {
      this.wireCreditInfo.logChanges(paramHistoryTracker, paramString1 + "." + "WIRE_CREDIT_INFO", paramWireTransfer.getWireCreditInfo(), paramString2);
    }
    super.logChanges(paramHistoryTracker, paramString1, paramWireTransfer, paramString2);
  }
  
  public void logCreate(SecureUser paramSecureUser, HistoryTracker paramHistoryTracker, String paramString)
  {
    logCreate(paramSecureUser, paramHistoryTracker, BEAN_NAME, paramString);
  }
  
  public void logCreate(SecureUser paramSecureUser, HistoryTracker paramHistoryTracker, String paramString1, String paramString2)
  {
    paramHistoryTracker.logCreate(paramString1, "TRACKINGID", getTrackingID(), paramString2);
    if ((this.CC == null) || (this.CC.length() == 0)) {
      try
      {
        if (this.C7 != null) {
          jdMethod_for(paramSecureUser, paramHistoryTracker, paramString2);
        }
      }
      catch (Exception localException)
      {
        DebugLog.log("WARNING: Failed to create entry for new WireBeneficiary in " + paramString2);
        localException.printStackTrace();
      }
    }
  }
  
  public void logDelete(HistoryTracker paramHistoryTracker, String paramString)
  {
    logDelete(paramHistoryTracker, BEAN_NAME, paramString);
  }
  
  public void logDelete(HistoryTracker paramHistoryTracker, String paramString1, String paramString2)
  {
    paramHistoryTracker.logDelete(paramString1, "TRACKINGID", getTrackingID(), paramString2);
  }
  
  private void jdMethod_for(SecureUser paramSecureUser, HistoryTracker paramHistoryTracker, String paramString)
  {
    HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 13, this.C7.getID(), this.C7.getTrackingID());
    this.C7.logCreate(localHistoryTracker, paramString);
    paramHistoryTracker.getHistories().addAll(localHistoryTracker.getHistories());
  }
  
  public void logChanges(SecureUser paramSecureUser, HistoryTracker paramHistoryTracker, WireTransfer paramWireTransfer, ILocalizable paramILocalizable)
  {
    logChanges(paramSecureUser, paramHistoryTracker, BEAN_NAME, paramWireTransfer, paramILocalizable);
  }
  
  public void logChanges(SecureUser paramSecureUser, HistoryTracker paramHistoryTracker, String paramString, WireTransfer paramWireTransfer, ILocalizable paramILocalizable)
  {
    if (paramWireTransfer == null)
    {
      DebugLog.log("WARNING: Unable to record history for wire modification due to null old wire.");
      return;
    }
    Account localAccount1 = null;
    Account localAccount2 = null;
    if (getFromAccountNum() != null)
    {
      localAccount1 = new Account();
      localAccount1.setID(getFromAccountNum(), Integer.toString(getFromAccountType()));
    }
    if (paramWireTransfer.getFromAccountNum() != null)
    {
      localAccount2 = new Account();
      localAccount2.setID(paramWireTransfer.getFromAccountNum(), Integer.toString(paramWireTransfer.getFromAccountType()));
    }
    paramHistoryTracker.detectChange(paramString, "FROM_ACCOUNT_ID", localAccount2 == null ? null : localAccount2.buildLocalizableAccountID(), localAccount1 == null ? null : localAccount1.buildLocalizableAccountID(), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "FROM_ACCOUNT_ROUTING_NUM", paramWireTransfer.getFromAccountRoutingNum(), getFromAccountRoutingNum(), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "BANK_ID", paramWireTransfer.getBankID(), getBankID(), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "CURRENCY_TYPE", paramWireTransfer.getAmtCurrencyType(), getAmtCurrencyType(), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "PAYEE_CURRENCY_TYPE", paramWireTransfer.getPayeeCurrencyType(), getPayeeCurrencyType(), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "CONTRACT_NUMBER", paramWireTransfer.getContractNumber(), getContractNumber(), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "EXCHANGE_RATE", paramWireTransfer.getExchangeRateValue(), getExchangeRateValue(), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "MATHRULE", paramWireTransfer.getMathRule() == null ? null : new LocalizableString("com.ffusion.beans.wiretransfers.resources", "WireMathRule" + paramWireTransfer.getMathRule(), null), getMathRule() == null ? null : new LocalizableString("com.ffusion.beans.wiretransfers.resources", "WireMathRule" + getMathRule(), null), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "DUE_DATE", paramWireTransfer.getDueDateValue() == null ? null : new LocalizableDate(paramWireTransfer.getDueDateValue(), false), getDueDateValue() == null ? null : new LocalizableDate(getDueDateValue(), false), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "DATE_TO_POST", paramWireTransfer.getDateToPostValue() == null ? null : new LocalizableDate(paramWireTransfer.getDateToPostValue(), false), getDateToPostValue() == null ? null : new LocalizableDate(getDateToPostValue(), false), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "SETTLEMENT_DATE", paramWireTransfer.getSettlementDateValue() == null ? null : new LocalizableDate(paramWireTransfer.getSettlementDateValue(), false), getSettlementDateValue() == null ? null : new LocalizableDate(getSettlementDateValue(), false), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "COMMENT", paramWireTransfer.getComment(), getComment(), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "INFO_TO_BENEFICIARY", paramWireTransfer.l(), l(), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "INFO_TO_BANK", paramWireTransfer.m(), m(), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "WIRE_NAME", paramWireTransfer.getWireName(), getWireName(), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "WIRE_NICKNAME", paramWireTransfer.getNickName(), getNickName(), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "WIRE_CATEGORY", paramWireTransfer.getWireCategory() == null ? null : new LocalizableString("com.ffusion.beans.wiretransfers.resources", "WireCategory" + paramWireTransfer.getWireCategory(), null), getWireCategory() == null ? null : new LocalizableString("com.ffusion.beans.wiretransfers.resources", "WireCategory" + getWireCategory(), null), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "WIRE_SCOPE", paramWireTransfer.getWireScope() == null ? null : new LocalizableString("com.ffusion.beans.wiretransfers.resources", "WireScope" + paramWireTransfer.getWireScope(), null), getWireScope() == null ? null : new LocalizableString("com.ffusion.beans.wiretransfers.resources", "WireScope" + getWireScope(), null), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "WIRE_DESTINATION", paramWireTransfer.getWireDestination() == null ? null : new LocalizableString("com.ffusion.beans.wiretransfers.resources", "WireDestination" + paramWireTransfer.getWireDestination(), null), getWireDestination() == null ? null : new LocalizableString("com.ffusion.beans.wiretransfers.resources", "WireDestination" + getWireDestination(), null), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "NUMBER_TRANSFERS", paramWireTransfer.getNumberTransfers(), getNumberTransfers(), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "FREQUENCY", new LocalizableString("com.ffusion.beans.wiretransfers.resources", "WireFrequencies" + paramWireTransfer.getFrequencyValue(), null), new LocalizableString("com.ffusion.beans.wiretransfers.resources", "WireFrequencies" + getFrequencyValue(), null), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "ORIG_AMOUNT", paramWireTransfer.getOrigAmount(), getOrigAmount(), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "ORIG_CURRENCY", paramWireTransfer.getOrigCurrency(), getOrigCurrency(), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "BY_ORDER_OF_NAME", paramWireTransfer.getByOrderOfName(), getByOrderOfName(), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "BY_ORDER_OF_ADDRESS1", paramWireTransfer.getByOrderOfAddress1(), getByOrderOfAddress1(), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "BY_ORDER_OF_ADDRESS2", paramWireTransfer.getByOrderOfAddress2(), getByOrderOfAddress2(), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "BY_ORDER_OF_ADDRESS3", paramWireTransfer.getByOrderOfAddress3(), getByOrderOfAddress3(), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "BY_ORDER_OF_ACCOUNT", paramWireTransfer.getByOrderOfAccount(), getByOrderOfAccount(), paramILocalizable);
    if ((this.CC != null) && (this.CC.length() > 0))
    {
      paramHistoryTracker.detectChange(paramString, "WIRE_PAYEE_ID", paramWireTransfer.getWirePayeeID(), getWirePayeeID(), paramILocalizable);
    }
    else if (this.C7 != null)
    {
      jdMethod_for(paramSecureUser, paramHistoryTracker, paramILocalizable);
      paramHistoryTracker.detectChange(paramString, "WIRE_PAYEE_ID", paramWireTransfer.getWirePayeeID(), this.C7.getID(), paramILocalizable);
    }
    if (this.wireCreditInfo != null) {
      this.wireCreditInfo.logChanges(paramHistoryTracker, paramString + "." + "WIRE_CREDIT_INFO", paramWireTransfer.getWireCreditInfo(), paramILocalizable);
    }
    super.logChanges(paramHistoryTracker, paramString, paramWireTransfer, paramILocalizable);
  }
  
  public void logCreate(SecureUser paramSecureUser, HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    logCreate(paramSecureUser, paramHistoryTracker, BEAN_NAME, paramILocalizable);
  }
  
  public void logCreate(SecureUser paramSecureUser, HistoryTracker paramHistoryTracker, String paramString, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.logCreate(paramString, "TRACKINGID", getTrackingID(), paramILocalizable);
    if ((this.CC == null) || (this.CC.length() == 0)) {
      try
      {
        if (this.C7 != null) {
          jdMethod_for(paramSecureUser, paramHistoryTracker, paramILocalizable);
        }
      }
      catch (Exception localException)
      {
        DebugLog.log("WARNING: Failed to create entry for new WireBeneficiary in " + paramILocalizable);
        localException.printStackTrace();
      }
    }
  }
  
  public void logDelete(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    logDelete(paramHistoryTracker, BEAN_NAME, paramILocalizable);
  }
  
  public void logDelete(HistoryTracker paramHistoryTracker, String paramString, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.logDelete(paramString, "TRACKINGID", getTrackingID(), paramILocalizable);
  }
  
  private void jdMethod_for(SecureUser paramSecureUser, HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 13, this.C7.getID(), this.C7.getTrackingID());
    this.C7.logCreate(localHistoryTracker, paramILocalizable);
    paramHistoryTracker.getHistories().addAll(localHistoryTracker.getHistories());
  }
  
  public AuditLogRecord getAuditRecord(SecureUser paramSecureUser, String paramString1, int paramInt, String paramString2)
  {
    return getAuditRecord(paramSecureUser, new LocalizableString("dummy", paramString1, null), paramInt, paramString2);
  }
  
  public AuditLogRecord getAuditRecord(SecureUser paramSecureUser, ILocalizable paramILocalizable, int paramInt, String paramString)
  {
    String str1 = null;
    String str2 = null;
    String str3 = null;
    int i = 0;
    if (paramSecureUser.getUserType() == 2)
    {
      str2 = Integer.toString(paramSecureUser.getProfileID());
      str3 = Integer.toString(paramSecureUser.getUserType());
    }
    else
    {
      str1 = Integer.toString(paramSecureUser.getProfileID());
      if (paramSecureUser.getAgent() != null) {
        if (paramSecureUser.getAgent().getProfileID() > 0)
        {
          str2 = String.valueOf(paramSecureUser.getAgent().getProfileID());
          str3 = String.valueOf(paramSecureUser.getAgent().getUserType());
        }
        else
        {
          str2 = paramSecureUser.getAgent().getUserName();
        }
      }
      i = paramSecureUser.getPrimaryUserID();
    }
    int j = paramSecureUser.getBusinessID();
    try
    {
      j = Integer.parseInt(getCustomerID());
    }
    catch (Exception localException) {}
    Object localObject1 = null;
    Object localObject2 = null;
    String str4 = null;
    String str5 = null;
    if (this.C7 != null)
    {
      str4 = this.C7.getAccountNum();
      if (this.C7.getDestinationBank() != null) {
        str5 = this.C7.getDestinationBank().getRoutingNumberValue();
      }
    }
    if (this.Cd.equals("HOST"))
    {
      localObject1 = "HOST";
      str4 = "HOST";
    }
    else if (this.Cd.equals("DRAWDOWN"))
    {
      localObject1 = str4;
      localObject2 = str5;
      str4 = this.Cn;
      str5 = this.CB;
    }
    else
    {
      localObject1 = this.Cn;
      localObject2 = this.CB;
    }
    AuditLogRecord localAuditLogRecord = new AuditLogRecord(str1, i, str2, str3, paramILocalizable, getTrackingID(), paramInt, j, getAmountValue().getAmountValue(), getAmtCurrencyType(), getID(), paramString, str4, str5, (String)localObject1, (String)localObject2, AuditLogUtil.getModuleFromTranType(paramInt));
    return localAuditLogRecord;
  }
  
  private String l()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (!isNullOrEmpty(this.Cj))
    {
      localStringBuffer.append(this.Cj);
      localStringBuffer.append(LINE_SEPARATOR);
    }
    if (!isNullOrEmpty(this.Ci))
    {
      localStringBuffer.append(this.Ci);
      localStringBuffer.append(LINE_SEPARATOR);
    }
    if (!isNullOrEmpty(this.Cg))
    {
      localStringBuffer.append(this.Cg);
      localStringBuffer.append(LINE_SEPARATOR);
    }
    if (!isNullOrEmpty(this.Cf)) {
      localStringBuffer.append(this.Cf);
    }
    return localStringBuffer.toString();
  }
  
  private void l(String paramString)
  {
    if (isNullOrEmpty(paramString) == true) {
      return;
    }
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, LINE_SEPARATOR);
    if (localStringTokenizer.hasMoreTokens()) {
      setOrigToBeneficiaryInfo1(localStringTokenizer.nextToken());
    }
    if (localStringTokenizer.hasMoreTokens()) {
      setOrigToBeneficiaryInfo2(localStringTokenizer.nextToken());
    }
    if (localStringTokenizer.hasMoreTokens()) {
      setOrigToBeneficiaryInfo3(localStringTokenizer.nextToken());
    }
    if (localStringTokenizer.hasMoreTokens()) {
      setOrigToBeneficiaryInfo4(localStringTokenizer.nextToken());
    }
  }
  
  private String m()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (!isNullOrEmpty(this.C5))
    {
      localStringBuffer.append(this.C5);
      localStringBuffer.append(LINE_SEPARATOR);
    }
    if (!isNullOrEmpty(this.C4))
    {
      localStringBuffer.append(this.C4);
      localStringBuffer.append(LINE_SEPARATOR);
    }
    if (!isNullOrEmpty(this.C3))
    {
      localStringBuffer.append(this.C3);
      localStringBuffer.append(LINE_SEPARATOR);
    }
    if (!isNullOrEmpty(this.C2))
    {
      localStringBuffer.append(this.C2);
      localStringBuffer.append(LINE_SEPARATOR);
    }
    if (!isNullOrEmpty(this.C1))
    {
      localStringBuffer.append(this.C1);
      localStringBuffer.append(LINE_SEPARATOR);
    }
    if (!isNullOrEmpty(this.C0)) {
      localStringBuffer.append(this.C0);
    }
    return localStringBuffer.toString();
  }
  
  private void k(String paramString)
  {
    if (isNullOrEmpty(paramString) == true) {
      return;
    }
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, LINE_SEPARATOR);
    if (localStringTokenizer.hasMoreTokens()) {
      setBankToBankInfo1(localStringTokenizer.nextToken());
    }
    if (localStringTokenizer.hasMoreTokens()) {
      setBankToBankInfo2(localStringTokenizer.nextToken());
    }
    if (localStringTokenizer.hasMoreTokens()) {
      setBankToBankInfo3(localStringTokenizer.nextToken());
    }
    if (localStringTokenizer.hasMoreTokens()) {
      setBankToBankInfo4(localStringTokenizer.nextToken());
    }
    if (localStringTokenizer.hasMoreTokens()) {
      setBankToBankInfo5(localStringTokenizer.nextToken());
    }
    if (localStringTokenizer.hasMoreTokens()) {
      setBankToBankInfo6(localStringTokenizer.nextToken());
    }
  }
  
  public void setWireDates(WireInfo paramWireInfo)
  {
    setDateFormat("yyyyMMdd");
    String str = paramWireInfo.getDateDue();
    if ((str != null) && (str.length() > 8)) {
      str = str.substring(0, 8);
    }
    setDueDate(str);
    str = paramWireInfo.getDateToPost();
    if ((str != null) && (str.length() > 8)) {
      str = str.substring(0, 8);
    }
    setDateToPost(str);
    if (paramWireInfo.getLogDate() != null) {
      setLogDate(paramWireInfo.getLogDate().toString().substring(0, 19));
    } else {
      setLogDate("0000/00/00 00:00:00");
    }
    str = paramWireInfo.getSettlementDate();
    if ((str != null) && (str.length() > 8)) {
      str = str.substring(0, 8);
    }
    setSettlementDate(str);
    str = paramWireInfo.getCreateDate();
    if ((str != null) && (str.length() > 10)) {
      str = str.substring(0, 10);
    }
    setCreateDate(jdMethod_for(str, "yyyy/MM/dd", "MM/dd/yyyy"));
    str = paramWireInfo.getDatePosted();
    if ((str != null) && (str.length() > 8)) {
      str = str.substring(0, 8);
    }
    setDatePosted(str);
    setDateFormat("SHORT");
  }
  
  private String jdMethod_for(String paramString1, String paramString2, String paramString3)
  {
    try
    {
      DateTime localDateTime = new DateTime(paramString1, this.locale, paramString2);
      localDateTime.setFormat(paramString3);
      return localDateTime.toString();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return paramString1;
  }
  
  public static boolean isNullOrEmpty(String paramString)
  {
    return (paramString == null) || (paramString.length() == 0);
  }
  
  public int getApprovalType()
  {
    return super.getApprovalType();
  }
  
  public int getApprovalSubType()
  {
    return 5;
  }
  
  public String getApprovalSubTypeName()
  {
    String str1 = null;
    String str2 = getWireType();
    if ((str2 != null) && ((str2.equalsIgnoreCase("TEMPLATE")) || (str2.equalsIgnoreCase("RECTEMPLATE")))) {
      str1 = "Wire Template";
    } else {
      str1 = "Wire Transfer";
    }
    return str1;
  }
  
  public Currency getApprovalAmount()
  {
    Currency localCurrency = getAmountValue();
    String str = getWireType();
    if ((str.equals("TEMPLATE")) || (str.equals("RECTEMPLATE")))
    {
      BigDecimal localBigDecimal = new BigDecimal(0.0D);
      if (localCurrency.equals(localBigDecimal)) {
        localCurrency = new Currency("1000000000000", this.locale);
      }
    }
    return localCurrency;
  }
  
  public DateTime getApprovalDueDate()
  {
    DateTime localDateTime = null;
    if ((this.Ct.equals("TEMPLATE")) || (this.Ct.equals("RECTEMPLATE"))) {
      localDateTime = getDueDateValue();
    } else {
      localDateTime = getDateToPostValue();
    }
    return localDateTime;
  }
  
  class a
    extends ExtendABean.InternalXMLHandler
  {
    a()
    {
      super();
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      WireTransferPayee localWireTransferPayee;
      if (paramString.equalsIgnoreCase("WIRE_PAYEE") == true)
      {
        localWireTransferPayee = new WireTransferPayee();
        WireTransfer.this.setWirePayee(localWireTransferPayee);
        localWireTransferPayee.continueXMLParsing(getHandler());
      }
      else if (paramString.equalsIgnoreCase("WIRE_CREDIT_INFO") == true)
      {
        localWireTransferPayee = new WireTransferPayee();
        WireTransfer.this.setWireCreditInfo(localWireTransferPayee);
        localWireTransferPayee.continueXMLParsing(getHandler());
      }
      else
      {
        super.startElement(paramString);
      }
    }
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      WireTransfer.this.set(getElement(), str);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.wiretransfers.WireTransfer
 * JD-Core Version:    0.7.0.1
 */