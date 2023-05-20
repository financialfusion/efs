package com.ffusion.beans.stoppayments;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.Sortable;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.AuditLogRecord;
import com.ffusion.util.logging.AuditLogUtil;
import com.ffusion.util.logging.DebugLog;
import java.io.PrintStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.Collator;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class StopCheck
  extends ExtendABean
  implements StopCheckStatus, Sortable, Comparable, Serializable
{
  public static final String STOPCHECK_ACCOUNTID = "AccountId";
  public static final String STOPCHECK_PAYEE_NAME = "PayeeName";
  public static final String STOPCHECK_AMOUNT = "Amount";
  public static final String STOPCHECK_START_DATE = "StartDate";
  public static final String STOPCHECK_END_DATE = "EndDate";
  public static final String STOPCHECK_DELIMITER = "\n";
  public static final String STOPCHECK_STATUS = "StopPaymentStatus";
  public static final String RESOURCE_BUNDLE = "com.ffusion.beans.stoppayments.resources";
  private static final String kT = "StopPaymentAccountID";
  private static final String kO = "StopPaymentAccountDisplayText";
  private static final String kV = "StopPaymentCheckNumbers";
  private static final String kR = "StopPaymentCheckDate";
  private static final String kQ = "StopPaymentAmount";
  private static final String kW = "StopPaymentPayeeName";
  private static final String kS = "StopPaymentReason";
  protected Account account;
  protected String accountID;
  protected String payeeName;
  protected String currencyCodeString;
  protected Currency amount;
  protected DateTime checkDate;
  protected DateTime createDate;
  protected String checkNumbers;
  protected int status = 0;
  protected String errorMessage;
  protected Currency fee;
  protected String feeMessage;
  protected int ID = 0;
  protected String IDStr = "";
  protected String reason = "";
  protected String trackingID;
  protected boolean isEditable = true;
  protected String accountDisplayText;
  private static final String kX = "com.ffusion.beans.user.resources";
  private static final String kU = "DateFormat";
  private static final String kP = "MM/dd/yyyy";
  
  public StopCheck() {}
  
  public StopCheck(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
  }
  
  public String getTrackingID()
  {
    return this.trackingID;
  }
  
  public void setTrackingID(String paramString)
  {
    this.trackingID = paramString;
  }
  
  public void set(StopCheck paramStopCheck)
  {
    super.set(paramStopCheck);
    if ((paramStopCheck.getID() != null) && (paramStopCheck.getID().trim().length() > 0)) {
      try
      {
        setID(Integer.parseInt(paramStopCheck.getID()));
      }
      catch (NumberFormatException localNumberFormatException) {}
    }
    this.trackingID = paramStopCheck.getTrackingID();
    this.account = paramStopCheck.getAccount();
    this.accountDisplayText = paramStopCheck.getAccountDisplayText();
    this.accountID = paramStopCheck.getAccountID();
    if (this.account != null)
    {
      this.accountID = this.account.getID();
      this.accountDisplayText = this.account.getDisplayTextRoutingNum();
    }
    this.payeeName = paramStopCheck.getPayeeName();
    this.reason = paramStopCheck.getReason();
    if (paramStopCheck.getAmountValue() != null) {
      this.amount = ((Currency)paramStopCheck.getAmountValue().clone());
    } else {
      this.amount = null;
    }
    if (paramStopCheck.getCheckDateValue() != null) {
      this.checkDate = ((DateTime)paramStopCheck.getCheckDateValue().clone());
    } else {
      this.checkDate = null;
    }
    if (paramStopCheck.getCreateDateValue() != null) {
      this.createDate = ((DateTime)paramStopCheck.getCreateDateValue().clone());
    } else {
      this.createDate = null;
    }
    setCheckNumbers(paramStopCheck.getCheckNumbers());
    this.status = paramStopCheck.getStatusValue();
    this.errorMessage = paramStopCheck.getErrorMessage();
    if (paramStopCheck.getFeeValue() != null) {
      this.fee = ((Currency)paramStopCheck.getFeeValue().clone());
    } else {
      this.fee = null;
    }
    this.feeMessage = paramStopCheck.getFeeMessage();
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.fee != null) {
      this.fee.setLocale(paramLocale);
    }
    if (this.amount != null) {
      this.amount.setLocale(paramLocale);
    }
    if (this.checkDate != null) {
      this.checkDate.setLocale(paramLocale);
    }
    if (this.createDate != null) {
      this.createDate.setLocale(paramLocale);
    }
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.checkDate != null) {
      this.checkDate.setFormat(paramString);
    }
    if (this.createDate != null) {
      this.createDate.setFormat(paramString);
    }
    if (this.account != null) {
      this.account.setDateFormat(paramString);
    }
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "CHECKDATE");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    StopCheck localStopCheck = (StopCheck)paramObject;
    if (localStopCheck == null) {
      return 1;
    }
    int i = 0;
    Collator localCollator = doGetCollator();
    if ((paramString.equals("ACCOUNTID")) && (getAccountID() != null) && (localStopCheck.getAccountID() != null))
    {
      i = localCollator.compare(getAccountID(), localStopCheck.getAccountID());
    }
    else if ((paramString.equals("PAYEENAME")) && (getPayeeName() != null) && (localStopCheck.getPayeeName() != null))
    {
      i = localCollator.compare(getPayeeName().toLowerCase(), localStopCheck.getPayeeName().toLowerCase());
    }
    else if (paramString.equals("AMOUNT"))
    {
      double d1 = 0.0D;
      double d2 = 0.0D;
      if (this.amount != null) {
        d1 = this.amount.doubleValue();
      }
      if (localStopCheck.getAmountValue() != null) {
        d2 = localStopCheck.getAmountValue().doubleValue();
      }
      if (d1 == d2) {
        i = 0;
      } else {
        i = d1 < d2 ? -1 : 1;
      }
    }
    else if (paramString.equals("CHECKDATE"))
    {
      if ((this.checkDate != null) && (localStopCheck.getCheckDateValue() != null)) {
        i = this.checkDate.equals(localStopCheck.getCheckDateValue()) ? 0 : this.checkDate.before(localStopCheck.getCheckDateValue()) ? -1 : 1;
      } else if (this.checkDate == localStopCheck.getCheckDateValue()) {
        i = 0;
      } else {
        i = this.checkDate == null ? -1 : 1;
      }
    }
    else if (paramString.equals("CREATE_DATE"))
    {
      if ((this.createDate != null) && (localStopCheck.getCreateDateValue() != null)) {
        i = this.createDate.equals(localStopCheck.getCreateDateValue()) ? 0 : this.createDate.before(localStopCheck.getCreateDateValue()) ? -1 : 1;
      } else if (this.createDate == localStopCheck.getCreateDateValue()) {
        i = 0;
      } else {
        i = this.createDate == null ? -1 : 1;
      }
    }
    else if (paramString.equals("CHECKNUMBERS"))
    {
      if ((getCheckNumbers() != null) && (localStopCheck.getCheckNumbers() != null)) {
        i = numStringCompare(getCheckNumbers(), localStopCheck.getCheckNumbers());
      } else if (getCheckNumbers() == localStopCheck.getCheckNumbers()) {
        i = 0;
      } else {
        i = getCheckNumbers() == null ? -1 : 1;
      }
    }
    else if (paramString.equals("STATUS"))
    {
      i = getStatusValue() - localStopCheck.getStatusValue();
    }
    else if (paramString.equals("REASON"))
    {
      if ((getReason() != null) && (localStopCheck.getReason() != null)) {
        i = getReason().compareTo(localStopCheck.getReason());
      } else if (getReason() == localStopCheck.getReason()) {
        i = 0;
      } else {
        i = getReason() == null ? -1 : 1;
      }
    }
    else if ((paramString.equals("ERRORMESSAGE")) && (getErrorMessage() != null) && (localStopCheck.getErrorMessage() != null))
    {
      i = localCollator.compare(getErrorMessage(), localStopCheck.getErrorMessage());
    }
    else if ((paramString.equals("FEE")) && (getFeeValue() != null) && (localStopCheck.getFeeValue() != null))
    {
      i = getFeeValue().compareTo(localStopCheck.getFeeValue());
    }
    else if ((paramString.equals("FEEMESSAGE")) && (getFeeMessage() != null) && (localStopCheck.getFeeMessage() != null))
    {
      i = localCollator.compare(getFeeMessage(), localStopCheck.getFeeMessage());
    }
    else
    {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof StopCheck)) {
      return false;
    }
    StopCheck localStopCheck = (StopCheck)paramObject;
    int i = 1;
    if ((getAccountID() != null) && (localStopCheck.getAccountID() != null))
    {
      i = getAccountID().compareTo(localStopCheck.getAccountID());
      if (i != 0) {
        return false;
      }
    }
    else
    {
      if ((getAccountID() != null) && (localStopCheck.getAccountID() == null)) {
        return false;
      }
      if ((getAccountID() == null) && (localStopCheck.getAccountID() != null)) {
        return false;
      }
    }
    if ((getPayeeName() != null) && (localStopCheck.getPayeeName() != null))
    {
      i = getPayeeName().compareTo(localStopCheck.getPayeeName());
      if (i != 0) {
        return false;
      }
    }
    else
    {
      if ((getPayeeName() != null) && (localStopCheck.getPayeeName() == null)) {
        return false;
      }
      if ((getPayeeName() == null) && (localStopCheck.getPayeeName() != null)) {
        return false;
      }
    }
    if ((getReason() != null) && (localStopCheck.getReason() != null))
    {
      i = getReason().compareTo(localStopCheck.getReason());
      if (i != 0) {
        return false;
      }
    }
    else
    {
      if ((getReason() != null) && (localStopCheck.getReason() == null)) {
        return false;
      }
      if ((getReason() == null) && (localStopCheck.getReason() != null)) {
        return false;
      }
    }
    if ((this.amount != null) && (localStopCheck.getAmountValue() != null))
    {
      i = this.amount.compareTo(localStopCheck.getAmountValue());
      if (i != 0) {
        return false;
      }
    }
    else
    {
      if ((getAmountValue() != null) && (localStopCheck.getAmountValue() == null)) {
        return false;
      }
      if ((getAmountValue() == null) && (localStopCheck.getAmountValue() != null)) {
        return false;
      }
    }
    if ((this.checkDate != null) && (localStopCheck.getCheckDateValue() != null))
    {
      i = this.checkDate.equals(localStopCheck.getCheckDateValue()) ? 0 : this.checkDate.before(localStopCheck.getCheckDateValue()) ? -1 : 1;
      if (i != 0) {
        return false;
      }
    }
    else
    {
      if ((getCheckDateValue() != null) && (localStopCheck.getCheckDateValue() == null)) {
        return false;
      }
      if ((getCheckDateValue() == null) && (localStopCheck.getCheckDateValue() != null)) {
        return false;
      }
    }
    if ((this.createDate != null) && (localStopCheck.getCreateDateValue() != null))
    {
      i = this.createDate.equals(localStopCheck.getCreateDateValue()) ? 0 : this.createDate.before(localStopCheck.getCreateDateValue()) ? -1 : 1;
      if (i != 0) {
        return false;
      }
    }
    else
    {
      if ((getCreateDateValue() != null) && (localStopCheck.getCreateDateValue() == null)) {
        return false;
      }
      if ((getCreateDateValue() == null) && (localStopCheck.getCreateDateValue() != null)) {
        return false;
      }
    }
    if ((getCheckNumbers() != null) && (localStopCheck.getCheckNumbers() != null))
    {
      i = getCheckNumbers().compareTo(localStopCheck.getCheckNumbers());
      if (i != 0) {
        return false;
      }
    }
    else
    {
      if ((getCheckNumbers() != null) && (localStopCheck.getCheckNumbers() == null)) {
        return false;
      }
      if ((getCheckNumbers() == null) && (localStopCheck.getCheckNumbers() != null)) {
        return false;
      }
    }
    i = getStatusValue() - localStopCheck.getStatusValue();
    if (i != 0) {
      return false;
    }
    if ((getErrorMessage() != null) && (localStopCheck.getErrorMessage() != null))
    {
      i = getErrorMessage().compareTo(localStopCheck.getErrorMessage());
      if (i != 0) {
        return false;
      }
    }
    else
    {
      if ((getErrorMessage() != null) && (localStopCheck.getErrorMessage() == null)) {
        return false;
      }
      if ((getErrorMessage() == null) && (localStopCheck.getErrorMessage() != null)) {
        return false;
      }
    }
    if ((getFeeValue() != null) && (localStopCheck.getFeeValue() != null))
    {
      i = getFeeValue().compareTo(localStopCheck.getFeeValue());
      if (i != 0) {
        return false;
      }
    }
    else
    {
      if ((getFeeValue() != null) && (localStopCheck.getFeeValue() == null)) {
        return false;
      }
      if ((getFeeValue() == null) && (localStopCheck.getFeeValue() != null)) {
        return false;
      }
    }
    if ((getFeeMessage() != null) && (localStopCheck.getFeeMessage() != null))
    {
      i = getFeeMessage().compareTo(localStopCheck.getFeeMessage());
      if (i != 0) {
        return false;
      }
    }
    else
    {
      if ((getFeeMessage() != null) && (localStopCheck.getFeeMessage() == null)) {
        return false;
      }
      if ((getFeeMessage() == null) && (localStopCheck.getFeeMessage() != null)) {
        return false;
      }
    }
    return true;
  }
  
  public void setAccount(Account paramAccount)
  {
    this.account = paramAccount;
    if (this.account != null) {
      this.accountID = null;
    }
  }
  
  public Account getAccount()
  {
    return this.account;
  }
  
  public void setCurrencyCode(String paramString)
  {
    this.currencyCodeString = paramString;
    if (this.amount != null) {
      this.amount = new Currency(this.amount.getAmountValue(), this.currencyCodeString, this.locale);
    }
  }
  
  public String getCurrencyCode()
  {
    return this.currencyCodeString;
  }
  
  public void setAccountID(String paramString)
  {
    this.accountID = paramString;
  }
  
  public String getAccountID()
  {
    if (this.account != null) {
      return this.account.getID();
    }
    return this.accountID;
  }
  
  public void setPayeeName(String paramString)
  {
    this.payeeName = paramString;
  }
  
  public String getPayeeName()
  {
    return this.payeeName;
  }
  
  public void setAmount(BigDecimal paramBigDecimal)
  {
    if (this.amount == null) {
      this.amount = new Currency(paramBigDecimal, this.locale);
    } else {
      this.amount.setAmount(paramBigDecimal);
    }
  }
  
  public void setAmount(Currency paramCurrency)
  {
    this.amount = paramCurrency;
  }
  
  public void setAmount(String paramString)
  {
    if (this.amount == null) {
      this.amount = new Currency(paramString, this.currencyCodeString, this.locale);
    } else {
      this.amount.fromString(paramString);
    }
  }
  
  public String getAmount()
  {
    if (this.amount == null) {
      return "";
    }
    return this.amount.toString();
  }
  
  public Currency getAmountValue()
  {
    return this.amount;
  }
  
  public void setCheckDate(String paramString)
  {
    try
    {
      if (this.checkDate == null) {
        this.checkDate = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.checkDate.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      this.checkDate = null;
      try
      {
        this.checkDate = new DateTime(paramString, this.locale, ResourceUtil.getString("DateFormat", "com.ffusion.beans.user.resources", this.locale));
      }
      catch (Exception localException) {}
    }
  }
  
  public void setCreateDate(String paramString)
  {
    try
    {
      if (this.createDate == null) {
        this.createDate = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.createDate.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      this.createDate = null;
      try
      {
        this.createDate = new DateTime(paramString, this.locale, ResourceUtil.getString("DateFormat", "com.ffusion.beans.user.resources", this.locale));
      }
      catch (Exception localException) {}
    }
  }
  
  public void setCheckDate(DateTime paramDateTime)
  {
    this.checkDate = paramDateTime;
  }
  
  public void setCreateDate(DateTime paramDateTime)
  {
    this.createDate = paramDateTime;
  }
  
  public DateTime getCheckDateValue()
  {
    return this.checkDate;
  }
  
  public DateTime getCreateDateValue()
  {
    return this.createDate;
  }
  
  public String getCheckDate()
  {
    if (this.checkDate != null) {
      return this.checkDate.toString();
    }
    return "";
  }
  
  public String getCheckDate(Locale paramLocale)
  {
    if (this.checkDate != null)
    {
      String str1 = this.checkDate.toString();
      String str2 = ResourceUtil.getString("DateFormat", "com.ffusion.beans.user.resources", paramLocale);
      DateFormat localDateFormat = DateFormatUtil.getFormatter("MM/dd/yyyy", paramLocale);
      try
      {
        str1 = localDateFormat.format(new DateTime(str1, paramLocale, str2).getTime());
      }
      catch (Exception localException)
      {
        return this.checkDate.toString();
      }
      return str1;
    }
    return "";
  }
  
  public String getCreateDate()
  {
    if (this.createDate != null) {
      return this.createDate.toString();
    }
    return "";
  }
  
  public void setCheckNumbers(String paramString)
  {
    this.checkNumbers = paramString;
  }
  
  public String getCheckNumbers()
  {
    return this.checkNumbers;
  }
  
  public void setStatus(String paramString)
  {
    try
    {
      this.status = Integer.parseInt(paramString);
    }
    catch (Exception localException)
    {
      this.status = 0;
    }
    if (this.status == 1) {
      this.isEditable = true;
    } else {
      this.isEditable = false;
    }
  }
  
  public int getStatusValue()
  {
    return this.status;
  }
  
  public String getStatusMessage()
  {
    String str = "";
    if (this.status != 5) {
      str = ResourceUtil.getString("StopPaymentStatus0", "com.ffusion.beans.stoppayments.resources", this.locale);
    } else if (this.status == 5) {
      str = ResourceUtil.getString("StopPaymentStatus1", "com.ffusion.beans.stoppayments.resources", this.locale);
    } else if (this.status == 13000) {
      str = "Success";
    } else if (this.status == 13001) {
      str = "General error";
    } else if (this.status == 13002) {
      str = "General account error";
    } else if (this.status == 13003) {
      str = "Account not found";
    } else if (this.status == 13004) {
      str = "Acount closed";
    } else if (this.status == 13005) {
      str = "Account not authorized";
    } else if (this.status == 13006) {
      str = "Duplicate request";
    } else if (this.status == 13007) {
      str = "Out-of-date token";
    } else if (this.status == 13008) {
      str = " Stop check in process";
    } else if (this.status == 13009) {
      str = " Too many checks to process";
    } else if (this.status == 13010) {
      str = "A request to cancel the stop-check request has been made";
    }
    return str;
  }
  
  public String getStatus()
  {
    return String.valueOf(this.status);
  }
  
  public void setErrorMessage(String paramString)
  {
    this.errorMessage = paramString;
  }
  
  public String getErrorMessage()
  {
    return this.errorMessage;
  }
  
  public void setFee(String paramString)
  {
    if (this.fee == null) {
      this.fee = new Currency(paramString, this.locale);
    } else {
      this.fee.fromString(paramString);
    }
  }
  
  public String getFee()
  {
    if (this.fee == null) {
      return "";
    }
    return this.fee.toString();
  }
  
  public Currency getFeeValue()
  {
    return this.fee;
  }
  
  public void setFeeMessage(String paramString)
  {
    this.feeMessage = paramString;
  }
  
  public String getFeeMessage()
  {
    return this.feeMessage;
  }
  
  protected int getUniqueID()
  {
    while (this.ID == 0)
    {
      Random localRandom = new Random(new Date().getTime());
      Integer localInteger = new Integer(Math.abs(localRandom.nextInt()) * 10000 % 10000);
      this.ID = localInteger.intValue();
    }
    return this.ID;
  }
  
  public String getID()
  {
    if ((this.IDStr != null) && (this.IDStr.length() > 0)) {
      return this.IDStr;
    }
    this.IDStr = "";
    if ((this.payeeName != null) && (this.payeeName.length() > 0)) {
      this.IDStr = (this.IDStr + this.payeeName + "_");
    }
    if ((this.reason != null) && (this.reason.length() > 0)) {
      this.IDStr = (this.IDStr + this.reason + "_");
    }
    if ((this.checkNumbers != null) && (this.checkNumbers.length() > 0)) {
      this.IDStr = (this.IDStr + this.checkNumbers + "_");
    }
    String str1 = getAmount();
    if ((str1 != null) && (str1.length() > 0)) {
      this.IDStr = (this.IDStr + str1 + "_");
    }
    String str2 = getCheckDate();
    if ((str2 != null) && (str2.length() > 0)) {
      this.IDStr = (this.IDStr + str2 + "_");
    }
    this.IDStr += getUniqueID();
    this.IDStr = this.IDStr.replace(' ', '_');
    this.IDStr = this.IDStr.replace('\t', '_');
    this.IDStr = this.IDStr.replace('[', '_');
    this.IDStr = this.IDStr.replace(']', '_');
    this.IDStr = this.IDStr.replace('(', '_');
    this.IDStr = this.IDStr.replace(')', '_');
    this.IDStr = this.IDStr.replace('=', '_');
    this.IDStr = this.IDStr.replace(',', '_');
    this.IDStr = this.IDStr.replace('"', '_');
    this.IDStr = this.IDStr.replace('\\', '_');
    this.IDStr = this.IDStr.replace('/', '_');
    this.IDStr = this.IDStr.replace('?', '_');
    this.IDStr = this.IDStr.replace('@', '_');
    this.IDStr = this.IDStr.replace(':', '_');
    this.IDStr = this.IDStr.replace(';', '_');
    return this.IDStr;
  }
  
  public void setReason(String paramString)
  {
    this.reason = paramString;
  }
  
  public String getReason()
  {
    return this.reason;
  }
  
  public String getInquireComment(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    getInquireComment(paramString, localStringBuffer);
    return localStringBuffer.toString();
  }
  
  protected void getInquireComment(String paramString, StringBuffer paramStringBuffer)
  {
    if (paramString != null) {
      paramStringBuffer.append(paramString);
    }
    paramStringBuffer.append('\n');
    paramStringBuffer.append(ResourceUtil.getString("StopPaymentAccountID", "com.ffusion.beans.stoppayments.resources", this.locale) + "=" + getAccountID());
    paramStringBuffer.append('\n');
    paramStringBuffer.append(ResourceUtil.getString("StopPaymentAccountDisplayText", "com.ffusion.beans.stoppayments.resources", this.locale) + "=" + getAccountDisplayText());
    paramStringBuffer.append('\n');
    paramStringBuffer.append(ResourceUtil.getString("StopPaymentCheckNumbers", "com.ffusion.beans.stoppayments.resources", this.locale) + "=" + getCheckNumbers());
    paramStringBuffer.append('\n');
    paramStringBuffer.append(ResourceUtil.getString("StopPaymentCheckDate", "com.ffusion.beans.stoppayments.resources", this.locale) + "=" + getCheckDate(this.locale));
    paramStringBuffer.append('\n');
    paramStringBuffer.append(ResourceUtil.getString("StopPaymentAmount", "com.ffusion.beans.stoppayments.resources", this.locale) + "=" + getAmount());
    paramStringBuffer.append('\n');
    paramStringBuffer.append(ResourceUtil.getString("StopPaymentPayeeName", "com.ffusion.beans.stoppayments.resources", this.locale) + "=" + getPayeeName());
    paramStringBuffer.append('\n');
    paramStringBuffer.append(ResourceUtil.getString("StopPaymentReason", "com.ffusion.beans.stoppayments.resources", this.locale) + "=" + getReason());
  }
  
  public boolean set(String paramString1, String paramString2, Accounts paramAccounts)
  {
    boolean bool = true;
    try
    {
      if (paramString1.equals("ACCOUNTID"))
      {
        if (paramAccounts == null)
        {
          this.accountID = paramString2;
        }
        else
        {
          this.account = paramAccounts.getByID(paramString2);
          if (this.account == null) {
            this.accountID = paramString2;
          }
        }
      }
      else if (paramString1.equals("PAYEENAME"))
      {
        this.payeeName = paramString2;
      }
      else if (paramString1.equals("AMOUNT"))
      {
        if (this.amount == null) {
          this.amount = new Currency(paramString2, this.locale);
        } else {
          this.amount.fromString(paramString2);
        }
      }
      else if (paramString1.equals("CHECKDATE"))
      {
        if (this.checkDate == null)
        {
          this.checkDate = new DateTime(this.locale);
          this.checkDate.setFormat(this.datetype);
        }
        System.out.println("CHECKDATE: " + paramString2);
        this.checkDate.fromXMLFormat(paramString2);
      }
      else if (paramString1.equals("CREATE_DATE"))
      {
        if (this.createDate == null)
        {
          this.createDate = new DateTime(this.locale);
          this.createDate.setFormat(this.datetype);
        }
        this.createDate.fromXMLFormat(paramString2);
      }
      else if (paramString1.equals("CHECKNUMBERS"))
      {
        this.checkNumbers = paramString2;
      }
      else if (paramString1.equals("STATUS"))
      {
        this.status = Integer.valueOf(paramString2).intValue();
        if ((this.status < 13000) || (this.status > 13010)) {
          switch (this.status)
          {
          case 0: 
            this.status = 13000;
            break;
          case 2000: 
            this.status = 13001;
            break;
          case 2002: 
            this.status = 13002;
            break;
          case 2003: 
            this.status = 13003;
            break;
          case 2004: 
            this.status = 13004;
            break;
          case 2005: 
            this.status = 13005;
            break;
          case 2019: 
            this.status = 13006;
            break;
          case 6502: 
            this.status = 13007;
            break;
          case 10000: 
            this.status = 13008;
            break;
          case 10500: 
            this.status = 13009;
            break;
          default: 
            this.status = 13000;
            break;
          }
        }
      }
      else if (paramString1.equals("ERRORMESSAGE"))
      {
        this.errorMessage = paramString2;
      }
      else if (paramString1.equals("FEE"))
      {
        if (this.fee == null) {
          this.fee = new Currency(paramString2, this.locale);
        } else {
          this.fee.fromString(paramString2);
        }
      }
      else if (paramString1.equals("FEEMESSAGE"))
      {
        this.feeMessage = paramString2;
      }
      else if (paramString1.equals("REASON"))
      {
        this.reason = paramString2;
      }
      else
      {
        bool = super.set(paramString1, paramString2);
      }
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception:", localException);
    }
    return bool;
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public void fromXML(String paramString, Accounts paramAccounts)
  {
    setXML(paramString, paramAccounts);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "STOPCHECK");
    XMLHandler.appendTag(localStringBuffer, "ACCOUNTID", getAccountID());
    if (this.amount != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "AMOUNT");
      localStringBuffer.append(this.amount.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "AMOUNT");
    }
    XMLHandler.appendTag(localStringBuffer, "PAYEENAME", this.payeeName);
    if (this.checkDate != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "CHECKDATE");
      localStringBuffer.append(this.checkDate.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "CHECKDATE");
    }
    if (this.createDate != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "CREATE_DATE");
      localStringBuffer.append(this.createDate.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "CREATE_DATE");
    }
    XMLHandler.appendTag(localStringBuffer, "CHECKNUMBERS", this.checkNumbers);
    XMLHandler.appendTag(localStringBuffer, "REASON", this.reason);
    XMLHandler.appendTag(localStringBuffer, "STATUS", this.status);
    XMLHandler.appendTag(localStringBuffer, "ERRORMESSAGE", this.errorMessage);
    if (this.fee != null) {
      XMLHandler.appendTag(localStringBuffer, "FEE", this.fee.doubleValue());
    }
    XMLHandler.appendTag(localStringBuffer, "FEEMESSAGE", this.feeMessage);
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "STOPCHECK");
    return localStringBuffer.toString();
  }
  
  public AuditLogRecord getAuditRecord(SecureUser paramSecureUser, String paramString1, int paramInt, String paramString2)
  {
    return getAuditRecord(paramSecureUser, new LocalizableString("dummy", paramString1, null), paramInt, paramString2);
  }
  
  public AuditLogRecord getAuditRecord(SecureUser paramSecureUser, ILocalizable paramILocalizable, int paramInt, String paramString)
  {
    String str1 = "";
    String str2 = null;
    String str3 = null;
    int i = 0;
    str1 = String.valueOf(paramSecureUser.getProfileID());
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
    String str4 = "";
    if (this.account != null) {
      str4 = this.account.getRoutingNum();
    }
    AuditLogRecord localAuditLogRecord = new AuditLogRecord(str1, i, str2, str3, paramILocalizable, getTrackingID(), paramInt, paramSecureUser.getBusinessID(), getAmountValue().getAmountValue(), getAmountValue().getCurrencyCode(), getID(), paramString, null, null, getAccountID(), str4, AuditLogUtil.getModuleFromTranType(paramInt));
    return localAuditLogRecord;
  }
  
  public void setXML(String paramString, Accounts paramAccounts)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(paramAccounts), paramString);
    }
    catch (Throwable localThrowable) {}
  }
  
  public void setXML(String paramString)
  {
    setXML(paramString, null);
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a(null));
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler, Accounts paramAccounts)
  {
    paramXMLHandler.continueWith(new a(paramAccounts));
  }
  
  public String getIsEditable()
  {
    return String.valueOf(this.isEditable).toUpperCase();
  }
  
  public void setIDStr(String paramString)
  {
    this.IDStr = paramString;
    this.ID = Integer.parseInt(paramString);
  }
  
  public void setID(int paramInt)
  {
    this.ID = paramInt;
    this.IDStr = String.valueOf(paramInt);
  }
  
  public void setAccountDisplayText(String paramString)
  {
    this.accountDisplayText = paramString;
  }
  
  public String getAccountDisplayText()
  {
    return this.accountDisplayText;
  }
  
  class a
    extends XMLHandler
  {
    Accounts jdField_int;
    
    public a(Accounts paramAccounts)
    {
      this.jdField_int = paramAccounts;
    }
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      StopCheck.this.set(getElement(), str, this.jdField_int);
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("AMOUNT"))
      {
        StopCheck.this.amount = new Currency();
        StopCheck.this.amount.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("CHECKDATE"))
      {
        StopCheck.this.checkDate = new DateTime(StopCheck.this.locale);
        StopCheck.this.checkDate.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("CREATE_DATE"))
      {
        StopCheck.this.createDate = new DateTime(StopCheck.this.locale);
        StopCheck.this.createDate.continueXMLParsing(getHandler());
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.stoppayments.StopCheck
 * JD-Core Version:    0.7.0.1
 */