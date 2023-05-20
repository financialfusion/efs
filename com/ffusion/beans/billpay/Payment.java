package com.ffusion.beans.billpay;

import com.ffusion.beans.Currency;
import com.ffusion.beans.FundsTransaction;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Billpay;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import com.ffusion.util.beans.LocalizableCurrency;
import com.ffusion.util.beans.LocalizableDate;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.AuditLogRecord;
import com.ffusion.util.logging.AuditLogUtil;
import com.ffusion.util.settings.AccountSettings;
import com.ffusion.util.settings.SystemException;
import java.math.BigDecimal;
import java.text.Collator;
import java.util.Calendar;
import java.util.Locale;
import java.util.MissingResourceException;

public class Payment
  extends FundsTransaction
  implements PaymentStatus, PaymentFulfillmentType, Comparable
{
  protected static final String BEAN_NAME = Payment.class.getName();
  public static final String RESOURCE_BUNDLE = "com.ffusion.beans.billpay.resources";
  public static final String KEY_STATUS_PREFIX = "PaymentStatus";
  public static final String LOG_DATE_KEY = "LogDate";
  public static final String USER_ID_KEY = "UserId";
  public static final String KEY_INQUIRY_ID = "InquiryID";
  public static final String KEY_INQUIRY_AMOUNT = "InquiryAmount";
  public static final String KEY_INQUIRY_AMOUNT_CURRENCY = "InquiryAmountCurrency";
  public static final String KEY_INQUIRY_REFERENCE_NUM = "InquiryReferenceNum";
  public static final String KEY_INQUIRY_DATE = "InquiryDate";
  public static final String KEY_INQUIRY_PAYEE = "InquiryPayee";
  public static final String KEY_INQUIRY_FROM_ACCOUNT = "InquiryFromAccount";
  public static final String KEY_INQUIRY_FROM_ACCOUNT_WITH_NICKNAME = "InquiryFromAccountNickname";
  public static final String KEY_INQUIRY_FREQUENCY_LABEL = "InquiryFrequency";
  public static final String KEY_INQUIRY_STATUS = "InquiryStatus";
  private static final String dU = "RecPaymentFrequencies";
  private static final String dX = "InquiryNoDate";
  private static final String dT = "InquiryNoAccount";
  private static final String d1 = "InquiryNoAmount";
  private static final String dY = "InquiryNoID";
  private static final String dV = "InquiryNoReference";
  private static final String d0 = "InquiryInvalidStatus";
  private static final String dW = "InquiryNoPayee";
  private static final String dZ = "InquiryNone";
  protected String recid;
  protected Payee payee;
  protected String payeeName;
  protected com.ffusion.beans.DateTime payDate;
  protected com.ffusion.util.beans.DateTime deliverByDate;
  protected Account account;
  protected String accountID;
  protected String confirmationNum;
  protected String memo;
  protected int status = 1;
  protected int error = 0;
  protected String transactionID;
  protected int fulfillmentType;
  protected boolean canEdit = true;
  protected boolean canDelete = true;
  protected String amtCurrency;
  protected String paymentType = "PAYMENT";
  protected String customerID;
  protected String FIID;
  protected boolean accountEntitled = true;
  
  protected Payment()
  {
    this.funds_type = 3;
  }
  
  protected Payment(String paramString)
  {
    super(paramString);
    this.funds_type = 3;
  }
  
  protected Payment(Locale paramLocale)
  {
    super(paramLocale);
    this.funds_type = 3;
  }
  
  public void set(Payment paramPayment)
  {
    super.set(paramPayment);
    if (paramPayment.getPayDateValue() != null) {
      this.payDate = ((com.ffusion.beans.DateTime)paramPayment.getPayDateValue().clone());
    } else {
      this.payDate = null;
    }
    if (paramPayment.getDeliverByDateValue() != null) {
      this.deliverByDate = ((com.ffusion.util.beans.DateTime)paramPayment.getDeliverByDateValue().clone());
    } else {
      this.deliverByDate = null;
    }
    this.account = paramPayment.getAccount();
    if (this.account == null) {
      this.accountID = paramPayment.getAccountID();
    }
    this.payee = paramPayment.getPayee();
    if (this.payee == null) {
      this.payeeName = paramPayment.getPayeeName();
    }
    this.confirmationNum = paramPayment.getConfirmationNum();
    this.recid = paramPayment.getRecPaymentID();
    this.memo = paramPayment.getMemo();
    this.amtCurrency = paramPayment.getAmtCurrency();
    this.status = paramPayment.getStatus();
    this.error = paramPayment.getErrorValue();
    this.transactionID = paramPayment.getTransactionID();
    this.fulfillmentType = paramPayment.getFulfillmentType();
    this.trackingID = paramPayment.getTrackingID();
    this.paymentType = paramPayment.getPaymentType();
    this.customerID = paramPayment.getCustomerID();
    this.FIID = paramPayment.getFIID();
  }
  
  public void merge(Payment paramPayment)
  {
    super.merge(paramPayment);
    if (paramPayment.getPayDateValue() != null) {
      this.payDate = ((com.ffusion.beans.DateTime)paramPayment.getPayDateValue().clone());
    }
    if (paramPayment.getDeliverByDateValue() != null) {
      this.deliverByDate = ((com.ffusion.util.beans.DateTime)paramPayment.getDeliverByDateValue().clone());
    }
    if (paramPayment.getAccount() != null) {
      this.account = paramPayment.getAccount();
    }
    if (this.account == null) {
      this.accountID = paramPayment.getAccountID();
    }
    if (paramPayment.getPayee() != null) {
      this.payee = paramPayment.getPayee();
    }
    if (this.payee == null) {
      this.payeeName = paramPayment.getPayeeName();
    }
    if (paramPayment.getMemo() != null) {
      this.memo = paramPayment.getMemo();
    }
    if (paramPayment.getAmtCurrency() != null) {
      this.amtCurrency = paramPayment.getAmtCurrency();
    }
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "PAYDATE");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    Payment localPayment = (Payment)paramObject;
    int i = 1;
    Collator localCollator = doGetCollator();
    if ((paramString.equals("RECPAYMENTID")) && (this.recid != null) && (localPayment.getRecPaymentID() != null)) {
      i = localCollator.compare(getRecPaymentID(), localPayment.getRecPaymentID());
    } else if (paramString.equals("STATUS")) {
      i = this.status - localPayment.getStatus();
    } else if ((paramString.equals("PAYDATE")) && (this.payDate != null) && (localPayment.getPayDateValue() != null)) {
      i = this.payDate.compare(localPayment.getPayDateValue());
    } else if ((paramString.equals("DELIVERBYDATE")) && (this.deliverByDate != null) && (localPayment.getDeliverByDateValue() != null)) {
      i = this.deliverByDate.compare(localPayment.getDeliverByDateValue());
    } else if ((paramString.equals("ACCOUNTID")) && (getAccountID() != null) && (localPayment.getAccountID() != null)) {
      i = localCollator.compare(getAccountID(), localPayment.getAccountID());
    } else if ((paramString.equals("PAYEENAME")) && (getPayeeName() != null) && (localPayment.getPayeeName() != null)) {
      i = localCollator.compare(getPayeeName(), localPayment.getPayeeName());
    } else if ((paramString.equals("PAYEENICKNAME")) && (getPayeeNickName() != null) && (localPayment.getPayeeNickName() != null)) {
      i = localCollator.compare(getPayeeNickName(), localPayment.getPayeeNickName());
    } else if ((paramString.equals("PAYEEID")) && (getPayee() != null) && (localPayment.getPayee() != null)) {
      i = numStringCompare(getPayee().getID(), localPayment.getPayee().getID());
    } else if ((paramString.equals("TRANSACTIONID")) && (getTransactionID() != null) && (localPayment.getTransactionID() != null)) {
      i = localCollator.compare(getTransactionID(), localPayment.getTransactionID());
    } else if ((paramString.equals("CONFIRMATIONNUMBER")) && (getConfirmationNum() != null) && (localPayment.getConfirmationNum() != null)) {
      i = localCollator.compare(getConfirmationNum(), localPayment.getConfirmationNum());
    } else if ((paramString.equals("MEMO")) && (getMemo() != null) && (localPayment.getMemo() != null)) {
      i = localCollator.compare(getMemo(), localPayment.getMemo());
    } else if ((paramString.equals("NICKNAME")) && (getAccount() != null) && (localPayment.getAccount() != null)) {
      i = getAccount().getNickName().compareToIgnoreCase(localPayment.getAccount().getNickName());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1.equals("RECPAYMENTID")) && (this.recid != null)) {
      return isFilterable(getRecPaymentID(), paramString2, paramString3);
    }
    if (paramString1.equals("STATUS")) {
      return isFilterable(String.valueOf(getStatus()), paramString2, paramString3);
    }
    if ((paramString1.equals("PAYDATE")) && (this.payDate != null)) {
      return this.payDate.isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((paramString1.equals("DELIVERBYDATE")) && (this.deliverByDate != null)) {
      return this.deliverByDate.isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((paramString1.equals("AMOUNT")) && (this.amount != null)) {
      return this.amount.isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((paramString1.equals("ACCOUNTID")) && (getAccountID() != null)) {
      return isFilterable(getAccountID(), paramString2, paramString3);
    }
    if ((paramString1.equals("PAYEENAME")) && (getPayeeName() != null)) {
      return isFilterable(getPayeeName(), paramString2, paramString3);
    }
    if ((paramString1.equals("PAYEENICKNAME")) && (getPayeeNickName() != null)) {
      return isFilterable(getPayeeNickName(), paramString2, paramString3);
    }
    if ((paramString1.equals("PAYEEID")) && (getPayee() != null)) {
      return isFilterable(getPayee().getID(), paramString2, paramString3);
    }
    if ((paramString1.equals("TRANSACTIONID")) && (getTransactionID() != null)) {
      return isFilterable(getTransactionID(), paramString2, paramString3);
    }
    if ((paramString1.equals("CONFIRMATIONNUMBER")) && (getConfirmationNum() != null)) {
      return isFilterable(getConfirmationNum(), paramString2, paramString3);
    }
    if ((paramString1.equals("MEMO")) && (getMemo() != null)) {
      return isFilterable(getMemo(), paramString2, paramString3);
    }
    if ((paramString1.equals("NICKNAME")) && (getAccount() != null)) {
      return isFilterable(getAccount().getNickName(), paramString2, paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public void setRecPaymentID(String paramString)
  {
    this.recid = paramString;
  }
  
  public String getRecPaymentID()
  {
    return this.recid;
  }
  
  public void setPayeeName(String paramString)
  {
    if ((this.payee == null) || ((paramString != null) && (!paramString.equals(this.payee.getName())))) {
      this.payeeName = paramString;
    }
  }
  
  public String getPayeeName()
  {
    if (this.payee != null) {
      return this.payee.getName();
    }
    return this.payeeName;
  }
  
  public String getPayeeNickName()
  {
    if (this.payee != null) {
      return this.payee.getNickName();
    }
    return this.payeeName;
  }
  
  public void setPayee(Payee paramPayee)
  {
    this.payee = paramPayee;
    if (this.payee != null) {
      this.payeeName = null;
    }
  }
  
  public Payee getPayee()
  {
    return this.payee;
  }
  
  public void setConfirmationNum(String paramString)
  {
    this.confirmationNum = paramString;
  }
  
  public String getConfirmationNum()
  {
    return this.confirmationNum;
  }
  
  public void setStatus(int paramInt)
  {
    this.status = paramInt;
  }
  
  public int getStatus()
  {
    return this.status;
  }
  
  public String getStatusName()
  {
    return ResourceUtil.getString("PaymentStatus" + this.status, "com.ffusion.beans.billpay.resources", this.locale);
  }
  
  public void setError(int paramInt)
  {
    this.error = paramInt;
  }
  
  public int getErrorValue()
  {
    return this.error;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
  
  public void setTransactionID(String paramString)
  {
    this.transactionID = paramString;
  }
  
  public String getTransactionID()
  {
    return this.transactionID;
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
  
  public void setAccount(Account paramAccount)
  {
    this.account = paramAccount;
    if (this.account != null) {
      this.accountID = paramAccount.getID();
    }
  }
  
  public Account getAccount()
  {
    return this.account;
  }
  
  public String getAccountDisplayText()
  {
    Object localObject = null;
    try
    {
      if (this.account != null)
      {
        localObject = this.account.getDisplayText();
      }
      else
      {
        if ((this.accountID == null) || (this.accountID.endsWith("-"))) {
          return null;
        }
        String str1 = "0";
        str2 = "";
        i = getAccountID().indexOf("-");
        if (i > 0)
        {
          str2 = getAccountID().substring(0, i);
          str1 = getAccountID().substring(i + 1);
        }
        else
        {
          str2 = getAccountID();
        }
        localObject = AccountSettings.buildAccountDisplayText(str2, "com.ffusion.beans.accounts.resources", "AccountDisplayText", "com.ffusion.beans.accounts.resources", "AccountType" + str1, this.locale);
      }
    }
    catch (SystemException localSystemException)
    {
      String str2;
      int i;
      if (this.account != null)
      {
        localObject = this.account.getNumber();
      }
      else
      {
        if ((this.accountID == null) || (this.accountID.endsWith("-"))) {
          return null;
        }
        str2 = "";
        i = getAccountID().indexOf("-");
        if (i > 0) {
          str2 = getAccountID().substring(0, i);
        } else {
          str2 = getAccountID();
        }
        localObject = str2;
      }
    }
    return localObject;
  }
  
  public void setPayDate(String paramString)
  {
    try
    {
      if (this.payDate == null) {
        this.payDate = new com.ffusion.beans.DateTime(paramString, this.locale, this.datetype);
      } else {
        this.payDate.fromString(paramString);
      }
    }
    catch (com.ffusion.beans.InvalidDateTimeException localInvalidDateTimeException)
    {
      this.payDate = null;
    }
  }
  
  public void setPayDate(Calendar paramCalendar)
  {
    if (this.payDate == null)
    {
      this.payDate = new com.ffusion.beans.DateTime(this.locale);
      this.payDate.setFormat(this.datetype);
    }
    this.payDate.setTime(paramCalendar.getTime());
  }
  
  public com.ffusion.beans.DateTime getPayDateValue()
  {
    return this.payDate;
  }
  
  public String getPayDate()
  {
    if (this.payDate != null) {
      return this.payDate.toString();
    }
    return "";
  }
  
  public void setDeliverByDateDate(String paramString)
  {
    try
    {
      if (this.deliverByDate == null) {
        this.deliverByDate = new com.ffusion.util.beans.DateTime(paramString, this.locale, this.datetype);
      } else {
        this.deliverByDate.fromString(paramString);
      }
    }
    catch (com.ffusion.util.beans.InvalidDateTimeException localInvalidDateTimeException)
    {
      this.deliverByDate = null;
    }
  }
  
  public void setDeliverByDate(Calendar paramCalendar)
  {
    if (this.deliverByDate == null)
    {
      this.deliverByDate = new com.ffusion.util.beans.DateTime(this.locale);
      this.deliverByDate.setFormat(this.datetype);
    }
    this.deliverByDate.setTime(paramCalendar.getTime());
  }
  
  public com.ffusion.util.beans.DateTime getDeliverByDateValue()
  {
    return this.deliverByDate;
  }
  
  public String getDeliverByDate()
  {
    if (this.deliverByDate != null) {
      return this.deliverByDate.toString();
    }
    return "";
  }
  
  public String getMemo()
  {
    return this.memo;
  }
  
  public void setMemo(String paramString)
  {
    this.memo = paramString;
  }
  
  public String getResourceBundleName()
  {
    return "com.ffusion.beans.billpay.resources";
  }
  
  public String getErrorPrefix()
  {
    return "ImportError_";
  }
  
  public int getFulfillmentType()
  {
    return this.fulfillmentType;
  }
  
  public void setFulfillmentType(int paramInt)
  {
    this.fulfillmentType = paramInt;
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.payDate != null) {
      this.payDate.setLocale(paramLocale);
    }
    if (this.payee != null) {
      this.payee.setLocale(paramLocale);
    }
    if (this.deliverByDate != null) {
      this.deliverByDate.setLocale(paramLocale);
    }
    if (this.account != null) {
      this.account.setLocale(paramLocale);
    }
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.payDate != null) {
      this.payDate.setFormat(paramString);
    }
    if (this.deliverByDate != null) {
      this.deliverByDate.setFormat(paramString);
    }
  }
  
  public void setAmtCurrency(String paramString)
  {
    this.amtCurrency = paramString;
  }
  
  public String getAmtCurrency()
  {
    return this.amtCurrency;
  }
  
  public int getApprovalType()
  {
    return super.getApprovalType();
  }
  
  public int getApprovalSubType()
  {
    return 3;
  }
  
  public String getApprovalSubTypeName()
  {
    return "Payment";
  }
  
  public Currency getApprovalAmount()
  {
    return super.getApprovalAmount();
  }
  
  public com.ffusion.beans.DateTime getApprovalDueDate()
  {
    return getPayDateValue();
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
    if (paramSecureUser.getUserType() == 2)
    {
      str1 = "";
      str2 = String.valueOf(paramSecureUser.getProfileID());
      str3 = String.valueOf(paramSecureUser.getUserType());
    }
    else
    {
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
    }
    String str4 = "";
    if (this.account != null) {
      str4 = this.account.getRoutingNum();
    }
    AuditLogRecord localAuditLogRecord = new AuditLogRecord(str1, i, str2, str3, paramILocalizable, getTrackingID(), paramInt, paramSecureUser.getBusinessID(), getAmountValue().getAmountValue(), getAmountValue().getCurrencyCode(), getID(), paramString, null, null, getAccountID(), str4, AuditLogUtil.getModuleFromTranType(paramInt));
    return localAuditLogRecord;
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    set(paramString1, paramString2, null, null);
    return true;
  }
  
  public String getAmountForBPW()
  {
    if (getAmountValue() == null) {
      return "0.00";
    }
    BigDecimal localBigDecimal = getAmountValue().getAmountValue().setScale(2, 5);
    return localBigDecimal.toString();
  }
  
  public void set(String paramString1, String paramString2, Accounts paramAccounts, Payees paramPayees)
  {
    if (paramString1.equals("RECPAYMENTID"))
    {
      this.recid = paramString2;
    }
    else if (paramString1.equals("ACCOUNTID"))
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
    else if (paramString1.equals("PAYEEID"))
    {
      if (paramPayees == null)
      {
        if (this.payeeName == null) {
          this.payeeName = paramString2;
        }
        put(paramString1, paramString2);
      }
      else
      {
        this.payee = paramPayees.getByID(paramString2);
      }
    }
    else if (paramString1.equals("PAYDATE"))
    {
      if (this.payDate == null)
      {
        this.payDate = new com.ffusion.beans.DateTime(this.locale);
        this.payDate.setFormat(this.datetype);
      }
      this.payDate.fromXMLFormat(paramString2);
    }
    else if (paramString1.equals("STATUS"))
    {
      this.status = Integer.parseInt(paramString2);
    }
    else if (paramString1.equalsIgnoreCase("CURRENCY_TYPE"))
    {
      this.amount = new Currency("", paramString2, this.locale);
      this.amtCurrency = paramString2;
    }
    else if (paramString1.equals("ERROR"))
    {
      this.error = Integer.parseInt(paramString2);
    }
    else if (paramString1.equals("TRANSACTIONID"))
    {
      this.transactionID = paramString2;
    }
    else if (paramString1.equals("CONFIRMATIONNUMBER"))
    {
      this.confirmationNum = paramString2;
    }
    else if (paramString1.equals("MEMO"))
    {
      this.memo = paramString2;
    }
    else
    {
      super.set(paramString1, paramString2);
    }
  }
  
  public void fromXML(String paramString, Accounts paramAccounts, Payees paramPayees)
  {
    setXML(paramString, paramAccounts, paramPayees);
  }
  
  public void fromXML(String paramString)
  {
    setXML(paramString, null, null);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "PAYMENT");
    XMLHandler.appendTag(localStringBuffer, "RECPAYMENTID", this.recid);
    XMLHandler.appendTag(localStringBuffer, "ACCOUNTID", getAccountID());
    if (this.account != null) {
      localStringBuffer.append(this.account.getXML("ACCOUNT", false));
    }
    XMLHandler.appendTag(localStringBuffer, "PAYEENAME", getPayeeName());
    if (this.payee != null)
    {
      XMLHandler.appendTag(localStringBuffer, "PAYEEID", this.payee.getID());
      localStringBuffer.append(this.payee.getXML());
    }
    if (this.payDate != null) {
      XMLHandler.appendTag(localStringBuffer, "PAYDATE", this.payDate.toXMLFormat());
    }
    XMLHandler.appendTag(localStringBuffer, "STATUS", this.status);
    XMLHandler.appendTag(localStringBuffer, "ERROR", this.error);
    XMLHandler.appendTag(localStringBuffer, "CURRENCY_TYPE", this.amtCurrency);
    XMLHandler.appendTag(localStringBuffer, "TRANSACTIONID", this.transactionID);
    XMLHandler.appendTag(localStringBuffer, "CONFIRMATIONNUMBER", this.confirmationNum);
    XMLHandler.appendTag(localStringBuffer, "MEMO", this.memo);
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "PAYMENT");
    return localStringBuffer.toString();
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, Payment paramPayment, String paramString)
  {
    logChanges(paramHistoryTracker, BEAN_NAME, paramPayment, paramString);
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, String paramString1, Payment paramPayment, String paramString2)
  {
    if (!BEAN_NAME.equals(paramString1)) {
      paramString1 = paramString1 + "," + BEAN_NAME;
    }
    paramHistoryTracker.detectChange(paramString1, "ID", paramPayment.getID(), getID(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "TRACKINGID", paramPayment.getTrackingID(), getTrackingID(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "RECPAYMENTID", paramPayment.getRecPaymentID(), getRecPaymentID(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "ACCOUNTID", paramPayment.getAccountDisplayText(), getAccountDisplayText(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "PAYEENAME", paramPayment.getPayeeName(), getPayeeName(), paramString2);
    if ((this.payee != null) && (paramPayment.payee != null)) {
      paramHistoryTracker.detectChange(paramString1, "PAYEEID", paramPayment.payee.getID(), this.payee.getID(), paramString2);
    }
    paramHistoryTracker.detectChange(paramString1, "PAYDATE", paramPayment.getPayDate(), getPayDate(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "STATUS", paramPayment.getStatusName(), getStatusName(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "ERROR", paramPayment.getError(), getError(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "TRANSACTIONID", paramPayment.getTransactionID(), getTransactionID(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "CONFIRMATIONNUMBER", paramPayment.getConfirmationNum(), getConfirmationNum(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "MEMO", paramPayment.getMemo(), getMemo(), paramString2);
    super.logChanges(paramHistoryTracker, paramString1, paramPayment, paramString2);
  }
  
  public void logCreation(HistoryTracker paramHistoryTracker, String paramString)
  {
    paramHistoryTracker.logCreate(BEAN_NAME, "TRACKINGID", getTrackingID(), paramString);
  }
  
  public void logDeletion(HistoryTracker paramHistoryTracker, String paramString)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "TRACKINGID", getTrackingID(), null, paramString);
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, Payment paramPayment, ILocalizable paramILocalizable)
  {
    logChanges(paramHistoryTracker, BEAN_NAME, paramPayment, paramILocalizable);
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, String paramString, Payment paramPayment, ILocalizable paramILocalizable)
  {
    if (!BEAN_NAME.equals(paramString)) {
      paramString = paramString + "," + BEAN_NAME;
    }
    paramHistoryTracker.detectChange(paramString, "ID", paramPayment.getID(), getID(), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "TRACKINGID", paramPayment.getTrackingID(), getTrackingID(), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "RECPAYMENTID", paramPayment.getRecPaymentID(), getRecPaymentID(), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "ACCOUNTID", paramPayment.getAccount() == null ? null : paramPayment.getAccount().buildLocalizableAccountID(), getAccount() == null ? null : getAccount().buildLocalizableAccountID(), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "PAYEENAME", paramPayment.getPayeeName(), getPayeeName(), paramILocalizable);
    if ((this.payee != null) && (paramPayment.payee != null)) {
      paramHistoryTracker.detectChange(paramString, "PAYEEID", paramPayment.payee.getID(), this.payee.getID(), paramILocalizable);
    }
    paramHistoryTracker.detectChange(paramString, "PAYDATE", paramPayment.getPayDateValue() == null ? null : new LocalizableDate(paramPayment.getPayDateValue(), false), getPayDateValue() == null ? null : new LocalizableDate(getPayDateValue(), false), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "STATUS", new LocalizableString("com.ffusion.beans.billpay.resources", "PaymentStatus" + paramPayment.getStatus(), null), new LocalizableString("com.ffusion.beans.billpay.resources", "PaymentStatus" + getStatus(), null), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "ERROR", paramPayment.getError(), getError(), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "TRANSACTIONID", paramPayment.getTransactionID(), getTransactionID(), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "CONFIRMATIONNUMBER", paramPayment.getConfirmationNum(), getConfirmationNum(), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "MEMO", paramPayment.getMemo(), getMemo(), paramILocalizable);
    super.logChanges(paramHistoryTracker, paramString, paramPayment, paramILocalizable);
  }
  
  public void logCreation(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.logCreate(BEAN_NAME, "TRACKINGID", getTrackingID(), paramILocalizable);
  }
  
  public void logDeletion(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "TRACKINGID", getTrackingID(), null, paramILocalizable);
  }
  
  public String getCanEdit()
  {
    return "" + this.canEdit;
  }
  
  public void setCanEdit(String paramString)
  {
    this.canEdit = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setCanEdit(boolean paramBoolean)
  {
    this.canEdit = paramBoolean;
  }
  
  public String getCanDelete()
  {
    return "" + this.canDelete;
  }
  
  public void setCanDelete(String paramString)
  {
    this.canDelete = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setCanDelete(boolean paramBoolean)
  {
    this.canDelete = paramBoolean;
  }
  
  public String getAccountEntitled()
  {
    return "" + this.accountEntitled;
  }
  
  public void setAccountEntitled(String paramString)
  {
    this.accountEntitled = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setAccountEntitled(boolean paramBoolean)
  {
    this.accountEntitled = paramBoolean;
  }
  
  public void setPaymentType(String paramString)
  {
    this.paymentType = paramString;
  }
  
  public String getPaymentType()
  {
    return this.paymentType;
  }
  
  public void setCustomerID(String paramString)
  {
    this.customerID = paramString;
  }
  
  public void setCustomerID(int paramInt)
  {
    this.customerID = String.valueOf(paramInt);
  }
  
  public String getCustomerID()
  {
    return this.customerID;
  }
  
  public void setFIID(String paramString)
  {
    this.FIID = paramString;
  }
  
  public String getFIID()
  {
    return this.FIID;
  }
  
  protected void getInquireComment(StringBuffer paramStringBuffer)
  {
    Object[] arrayOfObject = new Object[1];
    LocalizableString localLocalizableString = null;
    com.ffusion.beans.DateTime localDateTime = getPayDateValue();
    arrayOfObject[0] = super.getID();
    if ((arrayOfObject[0] == null) || (((String)arrayOfObject[0]).length() == 0)) {
      arrayOfObject[0] = new LocalizableString("com.ffusion.beans.billpay.resources", "InquiryNoID", null);
    }
    localLocalizableString = new LocalizableString("com.ffusion.beans.billpay.resources", "InquiryID", arrayOfObject);
    paramStringBuffer.append((String)localLocalizableString.localize(this.locale)).append("\n");
    localLocalizableString = null;
    arrayOfObject[0] = null;
    Object localObject2;
    String str;
    if (super.getAmountValue() == null)
    {
      arrayOfObject[0] = new LocalizableString("com.ffusion.beans.billpay.resources", "InquiryNoAmount", null);
      localLocalizableString = new LocalizableString("com.ffusion.beans.billpay.resources", "InquiryAmount", arrayOfObject);
    }
    else
    {
      localObject1 = new Object[2];
      localObject2 = new LocalizableCurrency(super.getAmountValue());
      ((LocalizableCurrency)localObject2).setShowCurrencySymbol(false);
      str = null;
      Currency localCurrency = null;
      if ((this.payee != null) && (this.payee.getPayeeRoute() != null)) {
        str = this.payee.getPayeeRoute().getCurrencyCode();
      }
      if ((str == null) || (str.trim().length() == 0))
      {
        try
        {
          localCurrency = Billpay.getDefaultCurrency();
        }
        catch (CSILException localCSILException) {}
        str = localCurrency == null ? "" : localCurrency.getCurrencyCode();
      }
      localObject1[0] = localObject2;
      localObject1[1] = str;
      localLocalizableString = new LocalizableString("com.ffusion.beans.billpay.resources", "InquiryAmountCurrency", (Object[])localObject1);
    }
    paramStringBuffer.append((String)localLocalizableString.localize(this.locale)).append("\n");
    localLocalizableString = null;
    arrayOfObject[0] = null;
    arrayOfObject[0] = super.getReferenceNumber();
    if ((arrayOfObject[0] == null) || (((String)arrayOfObject[0]).length() == 0)) {
      arrayOfObject[0] = new LocalizableString("com.ffusion.beans.billpay.resources", "InquiryNoReference", null);
    }
    localLocalizableString = new LocalizableString("com.ffusion.beans.billpay.resources", "InquiryReferenceNum", arrayOfObject);
    paramStringBuffer.append((String)localLocalizableString.localize(this.locale)).append("\n");
    localLocalizableString = null;
    arrayOfObject[0] = null;
    if (localDateTime != null) {
      arrayOfObject[0] = new LocalizableDate(localDateTime, false);
    } else {
      arrayOfObject[0] = new LocalizableString("com.ffusion.beans.billpay.resources", "InquiryNoDate", null);
    }
    localLocalizableString = new LocalizableString("com.ffusion.beans.billpay.resources", "InquiryDate", arrayOfObject);
    paramStringBuffer.append((String)localLocalizableString.localize(this.locale)).append("\n");
    localLocalizableString = null;
    arrayOfObject[0] = null;
    localDateTime = null;
    arrayOfObject[0] = getPayeeName();
    if (arrayOfObject[0] == null) {
      arrayOfObject[0] = new LocalizableString("com.ffusion.beans.billpay.resources", "InquiryNoPayee", null);
    } else if (getPayeeNickName() != null) {
      arrayOfObject[0] = (arrayOfObject[0] + " (" + getPayeeNickName() + ")");
    }
    localLocalizableString = new LocalizableString("com.ffusion.beans.billpay.resources", "InquiryPayee", arrayOfObject);
    paramStringBuffer.append((String)localLocalizableString.localize(this.locale)).append("\n");
    localLocalizableString = null;
    arrayOfObject[0] = null;
    if ((getAccount() != null) && (getAccount().getID() != null))
    {
      localObject1 = getAccount().getNickName();
      if ((localObject1 != null) && (((String)localObject1).length() != 0))
      {
        localObject2 = new Object[2];
        localObject2[0] = getAccount().getID();
        localObject2[1] = getAccount().getNickName();
        localLocalizableString = new LocalizableString("com.ffusion.beans.billpay.resources", "InquiryFromAccountNickname", (Object[])localObject2);
      }
      else
      {
        arrayOfObject[0] = getAccount().getID();
        localLocalizableString = new LocalizableString("com.ffusion.beans.billpay.resources", "InquiryFromAccount", arrayOfObject);
      }
    }
    else
    {
      arrayOfObject[0] = new LocalizableString("com.ffusion.beans.billpay.resources", "InquiryNoAccount", null);
      localLocalizableString = new LocalizableString("com.ffusion.beans.billpay.resources", "InquiryFromAccount", arrayOfObject);
    }
    paramStringBuffer.append((String)localLocalizableString.localize(this.locale)).append("\n");
    localLocalizableString = null;
    arrayOfObject[0] = null;
    Object localObject1 = (String)get("Frequency");
    if ((localObject1 == null) || (((String)localObject1).toString().length() == 0)) {
      arrayOfObject[0] = new LocalizableString("com.ffusion.beans.billpay.resources", "InquiryNone", null);
    } else {
      for (int i = 0; i < 10; i++)
      {
        str = ResourceUtil.getString("RecPaymentFrequencies" + i, "com.ffusion.beans.billpay.resources", this.locale);
        if (str.equalsIgnoreCase((String)localObject1))
        {
          arrayOfObject[0] = str;
          break;
        }
      }
    }
    localLocalizableString = new LocalizableString("com.ffusion.beans.billpay.resources", "InquiryFrequency", arrayOfObject);
    paramStringBuffer.append((String)localLocalizableString.localize(this.locale)).append("\n");
    try
    {
      arrayOfObject[0] = getStatusName();
      if ((arrayOfObject[0] == null) || (arrayOfObject[0] == "")) {
        arrayOfObject[0] = new LocalizableString("com.ffusion.beans.billpay.resources", "InquiryNone", null);
      }
    }
    catch (MissingResourceException localMissingResourceException)
    {
      arrayOfObject[0] = new LocalizableString("com.ffusion.beans.billpay.resources", "InquiryInvalidStatus", null);
    }
    localLocalizableString = new LocalizableString("com.ffusion.beans.billpay.resources", "InquiryStatus", arrayOfObject);
    paramStringBuffer.append((String)localLocalizableString.localize(this.locale)).append("\n");
    localLocalizableString = null;
    arrayOfObject[0] = null;
  }
  
  public void setXML(String paramString, Accounts paramAccounts, Payees paramPayees)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(paramAccounts, paramPayees), paramString);
    }
    catch (Throwable localThrowable) {}
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(null, null), paramString);
    }
    catch (Throwable localThrowable) {}
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler, Accounts paramAccounts, Payees paramPayees)
  {
    paramXMLHandler.continueWith(new a(paramAccounts, paramPayees));
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a(null, null));
  }
  
  public void mapPmtStatusToInt(String paramString)
  {
    if (paramString.equals("WILLPROCESSON"))
    {
      setStatus(2);
    }
    else if (paramString.equals("PROCESSEDON"))
    {
      setStatus(5);
      setCanEdit(false);
      setCanDelete(false);
    }
    else if (paramString.equals("POSTEDON"))
    {
      setStatus(19);
      setCanEdit(false);
      setCanDelete(false);
    }
    else if (paramString.equals("NOFUNDSON"))
    {
      setStatus(11);
    }
    else if (paramString.equals("FUNDSALLOCATED"))
    {
      setStatus(12);
      setCanEdit(false);
    }
    else if (paramString.equals("FUNDSFAILEDON"))
    {
      setStatus(14);
    }
    else if (paramString.equals("BATCH_INPROCESS"))
    {
      setStatus(13);
      setCanEdit(false);
      setCanDelete(false);
    }
    else if (paramString.equals("FAILEDON"))
    {
      setStatus(6);
    }
    else if (paramString.equals("LIMIT_CHECK_FAILED"))
    {
      setStatus(16);
    }
    else if (paramString.equals("LIMIT_REVERT_FAILED"))
    {
      setStatus(17);
    }
    else if (paramString.equals("APPROVAL_FAILED"))
    {
      setStatus(18);
    }
    else if (paramString.equals("APPROVAL_NOT_ALLOWED"))
    {
      setStatus(21);
    }
    else if (paramString.equals("FUNDSREVERTED"))
    {
      setStatus(22);
    }
    else if (paramString.equals("FUNDSREVERTED_NOTIF"))
    {
      setStatus(23);
    }
    else if (paramString.equals("CANCELEDON"))
    {
      setStatus(3);
    }
    else if (paramString.equals("APPROVAL_PENDING"))
    {
      setStatus(9);
    }
    else if (paramString.equals("APPROVAL_REJECTED"))
    {
      setStatus(15);
    }
    else if (paramString.equals("ACTIVE"))
    {
      setStatus(20);
    }
    else
    {
      setStatus(0);
    }
  }
  
  public static String mapPmtStatusToStr(int paramInt)
  {
    String str = "UNKNOWN";
    switch (paramInt)
    {
    case 2: 
      str = "WILLPROCESSON";
      break;
    case 5: 
      str = "PROCESSEDON";
      break;
    case 19: 
      str = "POSTEDON";
      break;
    case 11: 
      str = "NOFUNDSON";
      break;
    case 12: 
      str = "FUNDSALLOCATED";
      break;
    case 14: 
      str = "FUNDSFAILEDON";
      break;
    case 13: 
      str = "BATCH_INPROCESS";
      break;
    case 6: 
      str = "FAILEDON";
      break;
    case 16: 
      str = "LIMIT_CHECK_FAILED";
      break;
    case 17: 
      str = "LIMIT_REVERT_FAILED";
      break;
    case 18: 
      str = "APPROVAL_FAILED";
      break;
    case 21: 
      str = "APPROVAL_NOT_ALLOWED";
      break;
    case 22: 
      str = "FUNDSREVERTED";
      break;
    case 23: 
      str = "FUNDSREVERTED_NOTIF";
      break;
    case 9: 
      str = "APPROVAL_PENDING";
      break;
    case 15: 
      str = "APPROVAL_REJECTED";
      break;
    case 20: 
      str = "ACTIVE";
      break;
    case 3: 
    case 4: 
    case 7: 
    case 8: 
    case 10: 
    default: 
      str = "UNKNOWN";
    }
    return str;
  }
  
  public String getFrequency(int paramInt)
  {
    return ResourceUtil.getString("RecPaymentFrequencies" + paramInt, "com.ffusion.beans.billpay.resources", this.locale);
  }
  
  public String getConsumerAccountDisplayText()
  {
    Object localObject = null;
    try
    {
      if (this.account != null)
      {
        localObject = this.account.getConsumerDisplayText();
      }
      else
      {
        if ((this.accountID == null) || (this.accountID.endsWith("-"))) {
          return null;
        }
        String str1 = "0";
        str2 = "";
        i = getAccountID().indexOf("-");
        if (i > 0)
        {
          str2 = getAccountID().substring(0, i);
          str1 = getAccountID().substring(i + 1);
        }
        else
        {
          str2 = getAccountID();
        }
        str2 = AccountSettings.getMaskedAccountNumber(str2, 4, 'x');
        localObject = AccountSettings.buildAccountDisplayText(str2, "com.ffusion.beans.accounts.resources", "AccountDisplayText", "com.ffusion.beans.accounts.resources", "AccountType" + str1, this.locale);
      }
    }
    catch (SystemException localSystemException)
    {
      String str2;
      int i;
      if (this.account != null)
      {
        localObject = this.account.getNumber();
      }
      else
      {
        if ((this.accountID == null) || (this.accountID.endsWith("-"))) {
          return null;
        }
        str2 = "";
        i = getAccountID().indexOf("-");
        if (i > 0) {
          str2 = getAccountID().substring(0, i);
        } else {
          str2 = getAccountID();
        }
        localObject = str2;
      }
    }
    return localObject;
  }
  
  class a
    extends ExtendABean.InternalXMLHandler
  {
    Accounts jdField_new;
    Payees jdField_int;
    
    public a(Accounts paramAccounts, Payees paramPayees)
    {
      super();
      this.jdField_new = paramAccounts;
      this.jdField_int = paramPayees;
      if (paramAccounts == null) {
        this.jdField_new = new Accounts();
      }
      if (paramPayees == null) {
        this.jdField_int = new Payees();
      }
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("ACCOUNT"))
      {
        Payment.this.account = this.jdField_new.create(null);
        Payment.this.account.continueXMLParsing(getHandler());
        this.jdField_new.remove(Payment.this.account);
      }
      else if (paramString.equals("PAYEE"))
      {
        Payment.this.payee = this.jdField_int.createNoAdd();
        Payment.this.payee.continueXMLParsing(getHandler());
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
      Payment.this.set(getElement(), str, this.jdField_new, this.jdField_int);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.billpay.Payment
 * JD-Core Version:    0.7.0.1
 */