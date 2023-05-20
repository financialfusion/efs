package com.ffusion.beans.banking;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.FundsTransaction;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.exttransfers.ExtTransferAccount;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.beans.wiretransfers.WireAccountMap;
import com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo;
import com.ffusion.ffs.bpw.interfaces.TransferInfo;
import com.ffusion.ffs.bpw.interfaces.ValueInfo;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import com.ffusion.util.beans.LocalizableCurrency;
import com.ffusion.util.beans.LocalizableDate;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.enums.UserAssignedAmount;
import com.ffusion.util.logging.AuditLogRecord;
import com.ffusion.util.logging.AuditLogUtil;
import com.ffusion.util.settings.AccountSettings;
import com.ffusion.util.settings.SystemException;
import java.math.BigDecimal;
import java.text.Collator;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.StringTokenizer;

public class Transfer
  extends FundsTransaction
  implements TransferStatus, TransferDefines, Comparable
{
  protected static final String BEAN_NAME = Transfer.class.getName();
  public static final String RESOURCE_BUNDLE = "com.ffusion.beans.banking.resources";
  public static final String KEY_TRANSFER_STATUS_PREFIX = "TransferStatus";
  public static final String KEY_TRANSFER_CATEGORY_PREFIX = "TransferCategory";
  public static final String KEY_TRANSFER_SCOPE_PREFIX = "TransferScope";
  public static final String KEY_INQUIRY_ID = "InquiryID";
  public static final String KEY_INQUIRY_AMOUNT = "InquiryAmount";
  public static final String KEY_INQUIRY_REFERENCE_NUM = "InquiryReferenceNum";
  public static final String KEY_INQUIRY_DUE_DATE = "InquiryDueDate";
  public static final String KEY_INQUIRY_PROCESS_DATE = "InquiryProcessDate";
  public static final String KEY_INQUIRY_FROM_ACCOUNT = "InquiryFromAccount";
  public static final String KEY_INQUIRY_FROM_ACCOUNT_WITH_NICKNAME = "InquiryFromAccountNickname";
  public static final String KEY_INQUIRY_TO_ACCOUNT = "InquiryToAccount";
  public static final String KEY_INQUIRY_TO_ACCOUNT_WITH_NICKNAME = "InquiryToAccountNickname";
  public static final String KEY_INQUIRY_FREQUENCY_LABEL = "InquiryFrequency";
  public static final String KEY_INQUIRY_STATUS = "InquiryStatus";
  private static final String eU = "RecTransferFrequencies";
  private static final String eW = "InquiryNoDate";
  private static final String eS = "InquiryNoAccount";
  private static final String e1 = "InquiryNoAmount";
  private static final String eX = "InquiryNoID";
  private static final String eV = "InquiryNoReference";
  private static final String e0 = "InquiryInvalidStatus";
  private static final String eZ = "InquiryNone";
  protected String recid;
  protected DateTime date;
  protected Account fromAccount;
  protected String fromAccountID;
  protected Account toAccount;
  protected String toAccountID;
  protected String memo;
  protected int status = 1;
  protected int error = 0;
  protected String transactionID;
  protected boolean canEdit = true;
  protected boolean canDelete = true;
  protected boolean accountEntitled = true;
  protected boolean displayRestrictedAccount = true;
  protected String bankID;
  protected String customerID;
  protected String processedBy;
  protected DateTime dateToPost;
  protected DateTime datePosted;
  protected DateTime settlementDate;
  protected String createDate;
  protected String confirmationMsg;
  protected String transferType = "Current";
  protected String transferSource;
  protected String transferCategory = "USER_ENTRY";
  protected String transferScope;
  protected String transferDestination = "INTERNAL";
  protected String amtCurrency;
  protected Currency toAmount;
  protected UserAssignedAmount userAssignedAmount = UserAssignedAmount.SINGLE;
  protected boolean estimatedAmount = false;
  protected String origAmount;
  protected String origCurrency;
  protected String confirmationNum;
  protected String fedConfirmationNum;
  private String eY;
  private int eQ;
  private String eT;
  private int eR;
  
  public Transfer()
  {
    this.funds_type = 1;
  }
  
  public Transfer(String paramString)
  {
    super(paramString);
    this.funds_type = 1;
  }
  
  public Transfer(Locale paramLocale)
  {
    super(paramLocale);
    this.funds_type = 1;
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.date != null) {
      this.date.setFormat(paramString);
    }
    if (this.dateToPost != null) {
      this.dateToPost.setFormat(paramString);
    }
    if (this.datePosted != null) {
      this.datePosted.setFormat(paramString);
    }
    if (this.settlementDate != null) {
      this.settlementDate.setFormat(paramString);
    }
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.date != null) {
      this.date.setLocale(paramLocale);
    }
  }
  
  public void setRecTransferID(String paramString)
  {
    this.recid = paramString;
  }
  
  public String getRecTransferID()
  {
    return this.recid;
  }
  
  public void setFromAccountID(String paramString)
  {
    this.fromAccountID = paramString;
  }
  
  public String getFromAccountDisplayText()
  {
    String str = null;
    if (this.fromAccount != null) {
      str = this.fromAccount.getDisplayText();
    } else {
      try
      {
        str = AccountSettings.buildAccountDisplayText(getFromAccountNum(), "com.ffusion.beans.accounts.resources", "AccountDisplayText", "com.ffusion.beans.accounts.resources", "AccountType" + getFromAccountType(), this.locale);
      }
      catch (SystemException localSystemException)
      {
        str = getFromAccountNum();
      }
    }
    return str;
  }
  
  public String getConsumerFromAccountDisplayText()
  {
    String str1 = null;
    if (this.fromAccount != null) {
      str1 = this.fromAccount.getConsumerDisplayText();
    } else {
      try
      {
        String str2 = AccountSettings.getMaskedAccountNumber(getFromAccountNum(), 4, 'x');
        str1 = AccountSettings.buildAccountDisplayText(str2, "com.ffusion.beans.accounts.resources", "AccountDisplayText", "com.ffusion.beans.accounts.resources", "AccountType" + getFromAccountType(), this.locale);
      }
      catch (SystemException localSystemException)
      {
        str1 = getFromAccountNum();
      }
    }
    return str1;
  }
  
  public String getToAccountDisplayText()
  {
    String str = null;
    if (this.toAccount != null) {
      str = this.toAccount.getDisplayText();
    } else {
      try
      {
        str = AccountSettings.buildAccountDisplayText(getToAccountNum(), "com.ffusion.beans.accounts.resources", "AccountDisplayText", "com.ffusion.beans.accounts.resources", "AccountType" + getToAccountType(), this.locale);
      }
      catch (SystemException localSystemException)
      {
        str = getToAccountNum();
      }
    }
    return str;
  }
  
  public String getConsumerToAccountDisplayText()
  {
    String str1 = null;
    if (this.toAccount != null) {
      str1 = this.toAccount.getConsumerDisplayText();
    } else {
      try
      {
        String str2 = AccountSettings.getMaskedAccountNumber(getToAccountNum(), 4, 'x');
        str1 = AccountSettings.buildAccountDisplayText(str2, "com.ffusion.beans.accounts.resources", "AccountDisplayText", "com.ffusion.beans.accounts.resources", "AccountType" + getToAccountType(), this.locale);
      }
      catch (SystemException localSystemException)
      {
        str1 = getFromAccountNum();
      }
    }
    return str1;
  }
  
  public String getFromAccountID()
  {
    if (this.fromAccount != null) {
      return this.fromAccount.getID();
    }
    return this.fromAccountID;
  }
  
  public void setFromAccount(Account paramAccount)
  {
    this.fromAccount = paramAccount;
    if (this.fromAccount != null) {
      this.fromAccountID = null;
    }
  }
  
  public void setClearFromAccount(String paramString)
  {
    this.fromAccount = null;
  }
  
  public void setClearToAccount(String paramString)
  {
    this.toAccount = null;
  }
  
  public void setClearAccounts(String paramString)
  {
    this.fromAccount = null;
    this.toAccount = null;
  }
  
  public Account getFromAccount()
  {
    return this.fromAccount;
  }
  
  public String getFromAccountNum()
  {
    if (this.eY != null) {
      return this.eY;
    }
    if (this.fromAccount != null) {
      return this.fromAccount.getNumber();
    }
    return null;
  }
  
  public int getFromAccountType()
  {
    if (this.eQ != 0) {
      return this.eQ;
    }
    if (this.fromAccount != null) {
      return this.fromAccount.getTypeValue();
    }
    return 0;
  }
  
  public void setFromAccountNumber(String paramString)
  {
    this.eY = paramString;
  }
  
  public void setFromAccountNumber(int paramInt)
  {
    this.eQ = paramInt;
  }
  
  public void setToAccountID(String paramString)
  {
    this.toAccountID = paramString;
  }
  
  public String getToAccountID()
  {
    if (this.toAccount != null) {
      return this.toAccount.getID();
    }
    return this.toAccountID;
  }
  
  public String getToAccountNum()
  {
    if (this.eT != null) {
      return this.eT;
    }
    if (this.toAccount != null) {
      return this.toAccount.getNumber();
    }
    return null;
  }
  
  public int getToAccountType()
  {
    if (this.eR != 0) {
      return this.eR;
    }
    if (this.toAccount != null) {
      return this.toAccount.getTypeValue();
    }
    return 0;
  }
  
  public void setToAccountNumber(String paramString)
  {
    this.eT = paramString;
  }
  
  public void setToAccountNumber(int paramInt)
  {
    this.eR = paramInt;
  }
  
  public void setToAccount(Account paramAccount)
  {
    this.toAccount = paramAccount;
    if (this.toAccount != null) {
      this.toAccountID = null;
    }
  }
  
  public Account getToAccount()
  {
    return this.toAccount;
  }
  
  public String getExtAcctId()
  {
    return (String)get("EXTACCTID");
  }
  
  public void setExtAcctId(String paramString)
  {
    if (paramString != null) {
      if (paramString.equalsIgnoreCase("null")) {
        remove("EXTACCTID");
      } else {
        put("EXTACCTID", paramString);
      }
    }
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
  
  public boolean getAccountEntitledValue()
  {
    return this.accountEntitled;
  }
  
  public void setAccountEntitled(String paramString)
  {
    this.accountEntitled = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setAccountEntitled(boolean paramBoolean)
  {
    this.accountEntitled = paramBoolean;
  }
  
  public void setDisplayRestrictedAccount(String paramString)
  {
    this.displayRestrictedAccount = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setDisplayRestrictedAccount(boolean paramBoolean)
  {
    this.displayRestrictedAccount = paramBoolean;
  }
  
  public void setDate(String paramString)
  {
    try
    {
      if (this.date == null) {
        this.date = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.date.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      this.date = null;
    }
  }
  
  public void setDate(DateTime paramDateTime)
  {
    if (this.date == null)
    {
      this.date = new DateTime(this.locale);
      this.date.setFormat(this.datetype);
    }
    this.date.setTime(paramDateTime.getTime());
  }
  
  public DateTime getDateValue()
  {
    return this.date;
  }
  
  public String getDate()
  {
    if (this.date != null) {
      return this.date.toString();
    }
    return null;
  }
  
  public String getMemo()
  {
    return this.memo;
  }
  
  public void setMemo(String paramString)
  {
    this.memo = paramString;
  }
  
  public void setStatus(int paramInt)
  {
    this.status = paramInt;
  }
  
  public void setStatus(String paramString)
  {
    try
    {
      this.status = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
  
  public int getStatus()
  {
    return this.status;
  }
  
  public String getStatusName()
  {
    return ResourceUtil.getString("TransferStatus" + this.status, "com.ffusion.beans.banking.resources", this.locale);
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
  
  public void setBankID(String paramString)
  {
    this.bankID = paramString;
  }
  
  public String getBankID()
  {
    return this.bankID;
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
  
  public void setProcessedBy(String paramString)
  {
    this.processedBy = paramString;
  }
  
  public String getProcessedBy()
  {
    return this.processedBy;
  }
  
  public void setDateToPost(DateTime paramDateTime)
  {
    this.dateToPost = paramDateTime;
  }
  
  public void setDateToPost(String paramString)
  {
    try
    {
      if (this.dateToPost == null) {
        this.dateToPost = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.dateToPost.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      if (paramString == "")
      {
        this.dateToPost = null;
      }
      else
      {
        if (this.dateToPost == null)
        {
          this.dateToPost = new DateTime(this.locale);
          this.dateToPost.setFormat(this.datetype);
        }
        this.dateToPost.fromXMLFormat(paramString);
      }
    }
  }
  
  public String getDateToPost()
  {
    if (this.dateToPost != null) {
      return this.dateToPost.toString();
    }
    return "";
  }
  
  public DateTime getDateToPostValue()
  {
    return this.dateToPost;
  }
  
  public void setDatePosted(DateTime paramDateTime)
  {
    this.datePosted = paramDateTime;
  }
  
  public void setDatePosted(String paramString)
  {
    try
    {
      if (this.datePosted == null) {
        this.datePosted = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.datePosted.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      if (paramString == "")
      {
        this.datePosted = null;
      }
      else
      {
        if (this.datePosted == null)
        {
          this.datePosted = new DateTime(this.locale);
          this.datePosted.setFormat(this.datetype);
        }
        this.datePosted.fromXMLFormat(paramString);
      }
    }
  }
  
  public String getDatePosted()
  {
    if (this.datePosted != null) {
      return this.datePosted.toString();
    }
    return "";
  }
  
  public DateTime getDatePostedValue()
  {
    return this.datePosted;
  }
  
  public void setSettlementDate(DateTime paramDateTime)
  {
    this.settlementDate = paramDateTime;
  }
  
  public void setSettlementDate(String paramString)
  {
    try
    {
      if (this.settlementDate == null) {
        this.settlementDate = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.settlementDate.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public DateTime getSettlementDateValue()
  {
    return this.settlementDate;
  }
  
  public String getSettlementDate()
  {
    if (this.settlementDate != null) {
      return this.settlementDate.toString();
    }
    return "";
  }
  
  public void setCreateDate(String paramString)
  {
    this.createDate = paramString;
  }
  
  public String getCreateDate()
  {
    if (this.createDate != null) {
      return this.createDate;
    }
    return "";
  }
  
  public void setConfirmationMsg(String paramString)
  {
    this.confirmationMsg = paramString;
  }
  
  public String getConfirmationMsg()
  {
    return this.confirmationMsg;
  }
  
  public void setTransferType(String paramString)
  {
    this.transferType = paramString;
  }
  
  public String getTransferType()
  {
    return this.transferType;
  }
  
  public void setTransferSource(String paramString)
  {
    this.transferSource = paramString;
  }
  
  public String getTransferSource()
  {
    return this.transferSource;
  }
  
  public void setTransferScope(String paramString)
  {
    this.transferScope = paramString;
  }
  
  public String getTransferScope()
  {
    return this.transferScope;
  }
  
  public void setTransferCategory(String paramString)
  {
    this.transferCategory = paramString;
  }
  
  public String getTransferCategory()
  {
    return this.transferCategory;
  }
  
  public void setTransferDestination(String paramString)
  {
    this.transferDestination = paramString;
  }
  
  public String getTransferDestination()
  {
    return this.transferDestination;
  }
  
  public void setAmtCurrency(String paramString)
  {
    if (this.amount != null) {
      this.amount.setCurrencyCode(paramString);
    }
  }
  
  public String getAmtCurrency()
  {
    if (this.amount == null) {
      return "";
    }
    return this.amount.getCurrencyCode();
  }
  
  public void setToAmtCurrency(String paramString)
  {
    if (this.toAmount != null) {
      this.toAmount.setCurrencyCode(paramString);
    }
  }
  
  public String getToAmtCurrency()
  {
    if (this.toAmount == null) {
      return "";
    }
    return this.toAmount.getCurrencyCode();
  }
  
  public void setToAmount(BigDecimal paramBigDecimal)
  {
    if (this.toAmount == null) {
      this.toAmount = new Currency(paramBigDecimal, this.locale);
    } else {
      this.toAmount.setAmount(paramBigDecimal);
    }
  }
  
  public void setToAmount(Currency paramCurrency)
  {
    this.toAmount = paramCurrency;
  }
  
  public void setToAmount(String paramString)
  {
    if (this.toAmount == null) {
      this.toAmount = new Currency(paramString, this.locale);
    } else {
      this.toAmount.fromString(paramString);
    }
  }
  
  public String getToAmount()
  {
    if (this.toAmount != null) {
      return this.toAmount.toString();
    }
    return "";
  }
  
  public String getToAmountForBPW()
  {
    if (this.toAmount == null) {
      return "0.00";
    }
    BigDecimal localBigDecimal = this.toAmount.getAmountValue().setScale(2, 4);
    return localBigDecimal.toString();
  }
  
  public Currency getToAmountValue()
  {
    return this.toAmount;
  }
  
  public String getToAmountRounded()
  {
    BigDecimal localBigDecimal = getToAmountRoundedValue();
    if (localBigDecimal == null) {
      return "0.00";
    }
    return localBigDecimal.toString();
  }
  
  public BigDecimal getToAmountRoundedValue()
  {
    if (this.toAmount == null) {
      return null;
    }
    return this.toAmount.getAmountValue().setScale(2, 4);
  }
  
  public void setUserAssignedAmountFlagName(String paramString)
  {
    UserAssignedAmount localUserAssignedAmount = UserAssignedAmount.getEnum(paramString);
    if (localUserAssignedAmount == null) {
      throw new IllegalArgumentException("Invalid UserAssignedAmount name: " + paramString);
    }
    setUserAssignedAmountFlag(localUserAssignedAmount);
  }
  
  public String getUserAssignedAmountFlagName()
  {
    if (this.userAssignedAmount == null) {
      return "";
    }
    return this.userAssignedAmount.getName();
  }
  
  public void setUserAssignedAmountFlag(UserAssignedAmount paramUserAssignedAmount)
  {
    this.userAssignedAmount = paramUserAssignedAmount;
  }
  
  public UserAssignedAmount getUserAssignedAmountFlag()
  {
    return this.userAssignedAmount;
  }
  
  public void setUserAssignedAmount(String paramString)
  {
    if ((this.userAssignedAmount != null) && (this.userAssignedAmount == UserAssignedAmount.TO)) {
      setToAmount(paramString);
    } else {
      setAmount(paramString);
    }
  }
  
  public String getUserAssignedAmount()
  {
    if ((this.userAssignedAmount != null) && (this.userAssignedAmount == UserAssignedAmount.TO))
    {
      if (this.toAmount != null) {
        return this.toAmount.getCurrencyStringNoSymbol();
      }
      return "0.00";
    }
    if (this.amount != null) {
      return this.amount.getCurrencyStringNoSymbol();
    }
    return "0.00";
  }
  
  public Currency getUserAssignedAmountValue()
  {
    if ((this.userAssignedAmount != null) && (this.userAssignedAmount == UserAssignedAmount.TO)) {
      return getToAmountValue();
    }
    return getAmountValue();
  }
  
  public void setEstimatedAmount(String paramString)
  {
    if (this.estimatedAmount) {
      if ((this.userAssignedAmount != null) && (this.userAssignedAmount == UserAssignedAmount.TO)) {
        setAmount(paramString);
      } else {
        setToAmount(paramString);
      }
    }
  }
  
  public String getEstimatedAmount()
  {
    if ((this.userAssignedAmount != null) && (this.userAssignedAmount == UserAssignedAmount.TO))
    {
      if (this.amount != null) {
        return this.amount.getCurrencyStringNoSymbol();
      }
      return "0.00";
    }
    if (this.toAmount != null) {
      return this.toAmount.getCurrencyStringNoSymbol();
    }
    return "0.00";
  }
  
  public Currency getEstimatedAmountValue()
  {
    if ((this.userAssignedAmount != null) && (this.userAssignedAmount == UserAssignedAmount.TO)) {
      return getAmountValue();
    }
    return getToAmountValue();
  }
  
  public boolean getIsAmountEstimated()
  {
    return (this.estimatedAmount) && (this.userAssignedAmount == UserAssignedAmount.TO);
  }
  
  public boolean getIsToAmountEstimated()
  {
    return (this.estimatedAmount) && (this.userAssignedAmount == UserAssignedAmount.FROM);
  }
  
  public boolean getEstimatedAmountFlag()
  {
    return this.estimatedAmount;
  }
  
  public void setEstimatedAmountFlag(String paramString)
  {
    this.estimatedAmount = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setEstimatedAmountFlag(boolean paramBoolean)
  {
    this.estimatedAmount = paramBoolean;
  }
  
  public int getApprovalType()
  {
    return super.getApprovalType();
  }
  
  public int getApprovalSubType()
  {
    return 1;
  }
  
  public String getApprovalSubTypeName()
  {
    return "Account Transfer";
  }
  
  public Currency getApprovalAmount()
  {
    if ((this.userAssignedAmount != null) && (this.userAssignedAmount == UserAssignedAmount.TO)) {
      return getToAmountValue();
    }
    return getAmountValue();
  }
  
  public DateTime getApprovalDueDate()
  {
    return getDateValue();
  }
  
  public String getAmountForBPW()
  {
    if (getAmountValue() == null) {
      return "0.00";
    }
    BigDecimal localBigDecimal = getAmountValue().getAmountValue().setScale(2, 4);
    return localBigDecimal.toString();
  }
  
  public String getAmountRounded()
  {
    BigDecimal localBigDecimal = getAmountRoundedValue();
    if (localBigDecimal == null) {
      return "0.00";
    }
    return localBigDecimal.toString();
  }
  
  public BigDecimal getAmountRoundedValue()
  {
    if (getAmountValue() == null) {
      return null;
    }
    return getAmountValue().getAmountValue().setScale(2, 4);
  }
  
  public void setOrigAmount(String paramString)
  {
    this.origAmount = paramString;
  }
  
  public String getOrigAmount()
  {
    return this.origAmount;
  }
  
  public void setOrigCurrency(String paramString)
  {
    this.origCurrency = paramString;
  }
  
  public String getOrigCurrency()
  {
    return this.origCurrency;
  }
  
  public void setConfirmationNum(String paramString)
  {
    this.confirmationNum = paramString;
  }
  
  public String getConfirmationNum()
  {
    return this.confirmationNum;
  }
  
  public void setFedConfirmationNum(String paramString)
  {
    this.fedConfirmationNum = paramString;
  }
  
  public String getFedConfirmationNum()
  {
    return this.fedConfirmationNum;
  }
  
  public void setTranState(String paramString)
  {
    super.set("STATUS", paramString);
  }
  
  public String getTranState()
  {
    return (String)super.get("STATUS");
  }
  
  public String getResourceBundleName()
  {
    return "com.ffusion.beans.banking.resources";
  }
  
  public String getErrorPrefix()
  {
    return "ImportError_";
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    return set(paramString1, paramString2, null);
  }
  
  public void set(Transfer paramTransfer)
  {
    if (paramTransfer == null) {
      return;
    }
    super.set(paramTransfer);
    if (paramTransfer.getDateValue() != null) {
      this.date = ((DateTime)paramTransfer.getDateValue().clone());
    } else {
      this.date = null;
    }
    this.fromAccount = paramTransfer.getFromAccount();
    if (this.fromAccount == null) {
      this.fromAccountID = paramTransfer.getFromAccountID();
    }
    this.toAccount = paramTransfer.getToAccount();
    if (this.toAccount == null) {
      this.toAccountID = paramTransfer.getToAccountID();
    }
    this.recid = paramTransfer.getRecTransferID();
    this.memo = paramTransfer.getMemo();
    this.status = paramTransfer.getStatus();
    this.error = paramTransfer.getErrorValue();
    this.transactionID = paramTransfer.getTransactionID();
    this.bankID = paramTransfer.getBankID();
    this.customerID = paramTransfer.getCustomerID();
    this.processedBy = paramTransfer.getProcessedBy();
    if (paramTransfer.getDateToPostValue() != null) {
      this.dateToPost = ((DateTime)paramTransfer.getDateToPostValue().clone());
    } else {
      this.dateToPost = null;
    }
    if (paramTransfer.getDatePostedValue() != null) {
      this.datePosted = ((DateTime)paramTransfer.getDatePostedValue().clone());
    } else {
      this.datePosted = null;
    }
    if (paramTransfer.getSettlementDateValue() != null) {
      this.settlementDate = ((DateTime)paramTransfer.getSettlementDateValue().clone());
    } else {
      this.settlementDate = null;
    }
    this.confirmationMsg = paramTransfer.getConfirmationMsg();
    this.transferType = paramTransfer.getTransferType();
    this.transferSource = paramTransfer.getTransferSource();
    this.transferCategory = paramTransfer.getTransferCategory();
    this.transferScope = paramTransfer.getTransferScope();
    this.transferDestination = paramTransfer.getTransferDestination();
    this.templateID = paramTransfer.getTemplateID();
    this.templateName = paramTransfer.getTemplateName();
    this.origAmount = paramTransfer.getOrigAmount();
    this.origCurrency = paramTransfer.getOrigCurrency();
    this.confirmationNum = paramTransfer.getConfirmationNum();
    this.fedConfirmationNum = paramTransfer.getFedConfirmationNum();
    if (paramTransfer.getToAmountValue() != null) {
      this.toAmount = ((Currency)paramTransfer.getToAmountValue().clone());
    } else {
      this.toAmount = null;
    }
    this.userAssignedAmount = paramTransfer.getUserAssignedAmountFlag();
  }
  
  public void merge(Transfer paramTransfer)
  {
    super.merge(paramTransfer);
    if (paramTransfer.getDateValue() != null) {
      this.date = ((DateTime)paramTransfer.getDateValue().clone());
    }
    if (paramTransfer.getFromAccount() != null) {
      this.fromAccount = paramTransfer.getFromAccount();
    }
    String str = paramTransfer.getFromAccountID();
    if (str != null) {
      setFromAccountID(str);
    }
    if (paramTransfer.getToAccount() != null) {
      this.toAccount = paramTransfer.getToAccount();
    }
    str = paramTransfer.getToAccountID();
    if (str != null) {
      setToAccountID(str);
    }
    str = paramTransfer.getMemo();
    if (str != null) {
      setMemo(str);
    }
    if (paramTransfer.getDateToPostValue() != null) {
      this.dateToPost = ((DateTime)paramTransfer.getDateToPostValue().clone());
    }
    if (paramTransfer.getDatePostedValue() != null) {
      this.datePosted = ((DateTime)paramTransfer.getDatePostedValue().clone());
    }
    if (paramTransfer.getSettlementDateValue() != null) {
      this.settlementDate = ((DateTime)paramTransfer.getSettlementDateValue().clone());
    }
    if (paramTransfer.getToAmountValue() != null) {
      this.toAmount = ((Currency)paramTransfer.getToAmountValue().clone());
    }
    setUserAssignedAmountFlag(paramTransfer.getUserAssignedAmountFlag());
  }
  
  public boolean set(String paramString1, String paramString2, Accounts paramAccounts)
  {
    boolean bool = true;
    if (paramString1.equalsIgnoreCase("RECTRANSFERID"))
    {
      this.recid = paramString2;
    }
    else if (paramString1.equalsIgnoreCase("FROMACCOUNTID"))
    {
      if (paramAccounts == null)
      {
        this.fromAccountID = paramString2;
      }
      else
      {
        paramAccounts.setFilter("All");
        this.fromAccount = paramAccounts.getByID(paramString2);
        if (this.fromAccount == null) {
          this.fromAccountID = paramString2;
        }
      }
    }
    else if (paramString1.equalsIgnoreCase("TOACCOUNTID"))
    {
      if (paramAccounts == null)
      {
        this.toAccountID = paramString2;
      }
      else
      {
        paramAccounts.setFilter("All");
        this.toAccount = paramAccounts.getByID(paramString2);
        if (this.toAccount == null) {
          this.toAccountID = paramString2;
        }
      }
    }
    else if (paramString1.equalsIgnoreCase("STATUS"))
    {
      this.status = Integer.parseInt(paramString2);
    }
    else if (paramString1.equalsIgnoreCase("ERROR"))
    {
      this.error = Integer.parseInt(paramString2);
    }
    else if (paramString1.equalsIgnoreCase("DATE"))
    {
      if (this.date == null)
      {
        this.date = new DateTime(this.locale);
        this.date.setFormat(this.datetype);
      }
      this.date.fromXMLFormat(paramString2);
    }
    else if (paramString1.equalsIgnoreCase("TRANSACTIONID"))
    {
      this.transactionID = paramString2;
    }
    else if (paramString1.equalsIgnoreCase("MEMO"))
    {
      this.memo = paramString2;
    }
    else if (paramString1.equalsIgnoreCase("BANK_ID"))
    {
      this.bankID = paramString2;
    }
    else if (paramString1.equalsIgnoreCase("CUSTOMER_ID"))
    {
      this.customerID = paramString2;
    }
    else if (paramString1.equalsIgnoreCase("PROCESSED_BY"))
    {
      this.processedBy = paramString2;
    }
    else if (paramString1.equalsIgnoreCase("DATE_TO_POST"))
    {
      setDateToPost(paramString2);
    }
    else if (paramString1.equalsIgnoreCase("DATE_POSTED"))
    {
      setDatePosted(paramString2);
    }
    else if (paramString1.equalsIgnoreCase("SETTLEMENT_DATE"))
    {
      setSettlementDate(paramString2);
    }
    else if (paramString1.equalsIgnoreCase("CONFIRMATION_MSG"))
    {
      this.confirmationMsg = paramString2;
    }
    else if (paramString1.equalsIgnoreCase("TRANSFER_TYPE"))
    {
      this.transferType = paramString2;
    }
    else if (paramString1.equalsIgnoreCase("TRANSFER_SOURCE"))
    {
      this.transferSource = paramString2;
    }
    else if (paramString1.equalsIgnoreCase("TRANSFER_CATEGORY"))
    {
      this.transferCategory = paramString2;
    }
    else if (paramString1.equalsIgnoreCase("TRANSFER_SCOPE"))
    {
      this.transferScope = paramString2;
    }
    else if (paramString1.equalsIgnoreCase("TRANSFER_DESTINATION"))
    {
      this.transferDestination = paramString2;
    }
    else if (paramString1.equalsIgnoreCase("TEMPLATE_ID"))
    {
      this.templateID = paramString2;
    }
    else if (paramString1.equalsIgnoreCase("TEMPLATE_NAME"))
    {
      this.templateName = paramString2;
    }
    else if (paramString1.equalsIgnoreCase("CURRENCY_TYPE"))
    {
      this.amount = new Currency("", paramString2, this.locale);
    }
    else if (paramString1.equalsIgnoreCase("ORIG_AMOUNT"))
    {
      this.origAmount = paramString2;
    }
    else if (paramString1.equalsIgnoreCase("ORIG_CURRENCY"))
    {
      this.origCurrency = paramString2;
    }
    else if (paramString1.equalsIgnoreCase("CONFIRMATIONNUMBER"))
    {
      this.confirmationNum = paramString2;
    }
    else if (paramString1.equalsIgnoreCase("FED_CONFIRMATION_NUM"))
    {
      this.fedConfirmationNum = paramString2;
    }
    else if (paramString1.equalsIgnoreCase("TOAMOUNT"))
    {
      setToAmount(paramString2);
    }
    else if (paramString1.equalsIgnoreCase("TO_CURRENCY_TYPE"))
    {
      setToAmtCurrency(paramString2);
    }
    else if (paramString1.equalsIgnoreCase("USERASSIGNED_AMOUNT"))
    {
      setUserAssignedAmountFlagName(paramString2);
    }
    else
    {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  public void setTransferInfo(TransferInfo paramTransferInfo, Accounts paramAccounts)
  {
    if (paramTransferInfo == null) {
      return;
    }
    setID(paramTransferInfo.getSrvrTId());
    setExternalID(paramTransferInfo.getSrvrTId());
    setTrackingID(paramTransferInfo.getLogId());
    setRecTransferID(paramTransferInfo.getSourceRecSrvrTId());
    setReferenceNumber(paramTransferInfo.getSrvrTId());
    setBankID(paramTransferInfo.getFIId());
    if (paramTransferInfo.getAmount() != null) {
      setAmount(new Currency(paramTransferInfo.getAmount(), paramTransferInfo.getAmountCurrency(), this.locale));
    }
    if (paramTransferInfo.getToAmount() != null) {
      setToAmount(new Currency(paramTransferInfo.getToAmount(), paramTransferInfo.getToAmountCurrency(), this.locale));
    }
    setUserAssignedAmountFlag(paramTransferInfo.getUserAssignedAmount());
    setEstimatedAmountFlag(paramTransferInfo.getEstimatedAmount());
    mapXferStatusToInt(paramTransferInfo.getPrcStatus());
    setError(paramTransferInfo.getStatusCode());
    setCustomerID(paramTransferInfo.getCustomerId());
    setSubmittedBy(paramTransferInfo.getSubmittedBy());
    setTransferType(paramTransferInfo.getTransferType());
    setTransferDestination(paramTransferInfo.getTransferDest());
    setTemplateID(paramTransferInfo.getSourceTemplateId());
    setTemplateName(paramTransferInfo.getTemplateNickName());
    setTransferCategory(paramTransferInfo.getTransferCategory());
    String str1 = getDateFormat();
    setDateFormat("yyyyMMdd");
    String str2 = paramTransferInfo.getDateDue();
    if ((str2 != null) && (str2.length() > 8)) {
      str2 = str2.substring(0, 8);
    }
    setDate(str2);
    str2 = paramTransferInfo.getDateToPost();
    if ((str2 != null) && (str2.length() > 8)) {
      str2 = str2.substring(0, 8);
    }
    setDateToPost(str2);
    str2 = paramTransferInfo.getDateCreate();
    if ((str2 != null) && (str2.length() > 10)) {
      str2 = str2.substring(0, 10);
    }
    setCreateDate(str2);
    if (getTransferDestination().equals("INTERNAL"))
    {
      if (this.dateToPost != null) {
        setDatePosted((DateTime)this.dateToPost.clone());
      }
    }
    else
    {
      str2 = paramTransferInfo.getDatePosted();
      if ((str2 != null) && (str2.length() > 8))
      {
        str2 = str2.substring(0, 8);
        setDatePosted(str2);
      }
    }
    setDateFormat(str1);
    String str3 = paramTransferInfo.getAccountFromNum();
    int i = 0;
    if ((str3 == null) && (paramTransferInfo.getAccountFromInfo() != null))
    {
      str3 = paramTransferInfo.getAccountFromInfo().getAcctNum();
      i = WireAccountMap.mapAccountTypeToInt(paramTransferInfo.getAccountFromInfo().getAcctType());
    }
    else
    {
      i = WireAccountMap.mapAccountTypeToInt(paramTransferInfo.getAccountFromType());
    }
    if (paramAccounts != null)
    {
      localObject1 = paramAccounts.getByAccountNumberAndType(str3, i);
      if ((localObject1 == null) || ((!((Account)localObject1).isFilterable("TransferFrom")) && (!((Account)localObject1).isFilterable("ExternalTransferFrom"))))
      {
        if (this.displayRestrictedAccount)
        {
          localObject1 = paramAccounts.createNoAdd("Restricted", 0);
          ((Account)localObject1).setNickName("Account");
        }
        else
        {
          localObject1 = paramAccounts.createNoAdd(str3, i);
        }
        setAccountEntitled(false);
        setCanEdit(false);
        setCanDelete(false);
      }
      setFromAccount((Account)localObject1);
    }
    else
    {
      localObject1 = new Account();
      ((Account)localObject1).setID(str3, Integer.toString(i));
      ((Account)localObject1).setType(i);
      setFromAccountID(((Account)localObject1).getID());
      setFromAccount((Account)localObject1);
    }
    Object localObject1 = paramTransferInfo.getAccountToNum();
    String str4 = null;
    if (paramTransferInfo.getAccountToType() != null) {
      str4 = paramTransferInfo.getAccountToType().toUpperCase();
    }
    int j = WireAccountMap.mapAccountTypeToInt(str4);
    Object localObject2;
    if (paramAccounts != null)
    {
      localObject2 = paramAccounts.getByAccountNumberAndType((String)localObject1, j);
      if ((localObject2 == null) || ((!((Account)localObject2).isFilterable("TransferTo")) && (!((Account)localObject2).isFilterable("ExternalTransferTo"))))
      {
        if (this.displayRestrictedAccount)
        {
          localObject2 = paramAccounts.createNoAdd("Restricted", 0);
          ((Account)localObject2).setNickName("Account");
        }
        else
        {
          localObject2 = paramAccounts.createNoAdd((String)localObject1, j);
        }
        setAccountEntitled(false);
        setCanEdit(false);
        setCanDelete(false);
      }
      setToAccount((Account)localObject2);
    }
    else
    {
      localObject2 = new Account();
      ((Account)localObject2).setID((String)localObject1, Integer.toString(j));
      ((Account)localObject2).setType(j);
      setToAccountID(((Account)localObject2).getID());
      setToAccount((Account)localObject2);
    }
    Object localObject3;
    Object localObject4;
    if (getTransferDestination().equals("INTERNAL"))
    {
      localObject2 = paramTransferInfo.getMemo();
      if ((localObject2 != null) && (((String)localObject2).trim().length() != 0))
      {
        localObject3 = new StringTokenizer((String)localObject2, ",");
        localObject4 = null;
        String str5 = null;
        while (((StringTokenizer)localObject3).hasMoreTokens())
        {
          String str6 = ((StringTokenizer)localObject3).nextToken();
          int k = str6.indexOf("=");
          if ((k == -1) || (k == str6.length() - 1))
          {
            if (localObject4 != null)
            {
              str5 = str5 + ',' + str6;
              if ("Memo".equalsIgnoreCase((String)localObject4)) {
                setMemo(str5);
              } else {
                set((String)localObject4, str5);
              }
            }
          }
          else
          {
            localObject4 = str6.substring(0, k);
            str5 = str6.substring(k + 1);
            if ("Memo".equalsIgnoreCase((String)localObject4)) {
              setMemo(str5);
            } else {
              set((String)localObject4, str5);
            }
          }
        }
      }
    }
    else
    {
      setMemo(paramTransferInfo.getMemo());
      localObject2 = paramTransferInfo.getExtInfo();
      if (localObject2 != null)
      {
        localObject3 = ((Hashtable)localObject2).entrySet().iterator();
        while (((Iterator)localObject3).hasNext())
        {
          localObject4 = (Map.Entry)((Iterator)localObject3).next();
          if ((((Map.Entry)localObject4).getKey() instanceof String)) {
            set((String)((Map.Entry)localObject4).getKey(), ((Map.Entry)localObject4).getValue().toString());
          }
        }
      }
    }
  }
  
  public TransferInfo getTransferInfo()
  {
    return getTransferInfo(null);
  }
  
  public TransferInfo getTransferInfo(String paramString)
  {
    TransferInfo localTransferInfo = new TransferInfo();
    copyToTransferInfo(localTransferInfo);
    if ("INTERNAL".equals(this.transferDestination)) {
      populateInternalExtraInfo(localTransferInfo);
    } else if (paramString != null) {
      populateExtraInfo(localTransferInfo, paramString);
    }
    return localTransferInfo;
  }
  
  protected void populateExtraInfo(TransferInfo paramTransferInfo, String paramString)
  {
    if ((this.map != null) && (this.map.size() > 0))
    {
      Hashtable localHashtable = paramTransferInfo.getExtInfo();
      if (localHashtable == null)
      {
        localHashtable = new Hashtable();
        paramTransferInfo.setExtInfo(localHashtable);
      }
      Iterator localIterator = this.map.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        if (((localEntry.getKey() instanceof String)) && ((localEntry.getValue() instanceof String))) {
          if ((!localEntry.getKey().equals("FrequencyValue")) && (!localEntry.getKey().equals("EXTACCTID")))
          {
            ValueInfo localValueInfo = new ValueInfo();
            localValueInfo.setValue(localEntry.getValue());
            localValueInfo.setAction(paramString);
            localHashtable.put(localEntry.getKey(), localValueInfo);
          }
        }
      }
    }
  }
  
  protected void populateInternalExtraInfo(TransferInfo paramTransferInfo)
  {
    if ((this.map != null) && (this.map.size() > 0))
    {
      Hashtable localHashtable = paramTransferInfo.getExtInfo();
      if (localHashtable == null)
      {
        localHashtable = new Hashtable();
        paramTransferInfo.setExtInfo(localHashtable);
      }
      Iterator localIterator = this.map.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        if (((localEntry.getKey() instanceof String)) && ((localEntry.getValue() instanceof String)))
        {
          String str1 = (String)localEntry.getKey();
          String str2 = (String)localEntry.getValue();
          if ((str1 != null) && (!"".equals(str1.trim())) && (str2 != null) && (!"".equals(str2.trim()))) {
            localHashtable.put(str1, str2);
          }
        }
      }
    }
  }
  
  public void copyToTransferInfo(TransferInfo paramTransferInfo)
  {
    paramTransferInfo.setSrvrTId(getID());
    paramTransferInfo.setCustomerId(getCustomerID());
    paramTransferInfo.setLogId(getTrackingID());
    paramTransferInfo.setTransferType(getTransferType());
    paramTransferInfo.setFIId(getBankID());
    paramTransferInfo.setSubmittedBy(getSubmittedBy());
    paramTransferInfo.setTransferCategory(getTransferCategory());
    if (getFromAccount() != null)
    {
      paramTransferInfo.setAccountFromNum(getFromAccount().getNumber());
      paramTransferInfo.setAccountFromType(WireAccountMap.mapAccountTypeToStr(getFromAccount().getTypeValue()));
      paramTransferInfo.setBankFromRtn(getFromAccount().getRoutingNum());
    }
    if (getToAccount() != null)
    {
      paramTransferInfo.setAccountToNum(getToAccount().getNumber());
      paramTransferInfo.setAccountToType(WireAccountMap.mapAccountTypeToStr(getToAccount().getTypeValue()));
      paramTransferInfo.setBankToRtn(getToAccount().getRoutingNum());
    }
    setTransferDestinationByAccounts();
    paramTransferInfo.setTransferDest(getTransferDestination());
    if (this.transferDestination.equals("ITOE"))
    {
      if (getExtAcctId() != null)
      {
        paramTransferInfo.setAccountToId(getExtAcctId());
      }
      else
      {
        localObject = (ExtTransferAccount)getToAccount().get("ExternalTransferACCOUNT");
        if (localObject != null) {
          paramTransferInfo.setAccountToId(((ExtTransferAccount)localObject).getBpwID());
        }
      }
    }
    else if (this.transferDestination.equals("ETOI")) {
      if (getExtAcctId() != null)
      {
        paramTransferInfo.setAccountFromId(getExtAcctId());
      }
      else
      {
        localObject = (ExtTransferAccount)getFromAccount().get("ExternalTransferACCOUNT");
        if (localObject != null) {
          paramTransferInfo.setAccountFromId(((ExtTransferAccount)localObject).getBpwID());
        }
      }
    }
    paramTransferInfo.setAmount(getAmountForBPW());
    paramTransferInfo.setAmountCurrency(getAmtCurrency());
    paramTransferInfo.setToAmount(getToAmountForBPW());
    paramTransferInfo.setToAmountCurrency(getToAmtCurrency());
    paramTransferInfo.setUserAssignedAmount(getUserAssignedAmountFlag());
    paramTransferInfo.setEstimatedAmount(getEstimatedAmountFlag());
    paramTransferInfo.setPrcStatus(mapXferStatusToStr(getStatus()));
    Object localObject = DateFormatUtil.getFormatter("yyyyMMdd");
    String str = "";
    if (getDateValue() != null) {
      str = ((DateFormat)localObject).format(getDateValue().getTime());
    }
    paramTransferInfo.setDateDue(str);
    str = "";
    if (getDateToPostValue() != null) {
      str = ((DateFormat)localObject).format(getDateToPostValue().getTime());
    }
    paramTransferInfo.setDateToPost(str);
    str = "";
    if (getDatePostedValue() != null) {
      str = ((DateFormat)localObject).format(getDatePostedValue().getTime());
    }
    paramTransferInfo.setDatePosted(str);
    paramTransferInfo.setMemo(getMemo());
    paramTransferInfo.setSourceTemplateId(getTemplateID());
    paramTransferInfo.setTemplateNickName(getTemplateName());
    paramTransferInfo.setSourceRecSrvrTId(getRecTransferID());
  }
  
  public void setTransferDestinationByAccounts()
  {
    if ((getFromAccount() != null) && (getFromAccount().getCoreAccount() != null) && (!getFromAccount().getCoreAccount().equals("1"))) {
      setTransferDestination("ETOI");
    } else if ((getToAccount() != null) && (getToAccount().getCoreAccount() != null) && (!getToAccount().getCoreAccount().equals("1"))) {
      setTransferDestination("ITOE");
    } else if ((getTransferType().equals("TEMPLATE")) || (getTransferType().equals("RECTEMPLATE"))) {
      setTransferDestination("ITOI");
    } else {
      setTransferDestination("INTERNAL");
    }
  }
  
  public void mapXferStatusToInt(String paramString)
  {
    if (paramString.equals("WILLPROCESSON"))
    {
      setStatus(2);
    }
    else if (paramString.equals("POSTEDON"))
    {
      setStatus(5);
      setCanDelete(false);
      setCanEdit(false);
    }
    else if (paramString.equals("PROCESSEDON"))
    {
      setStatus(18);
      setCanDelete(false);
      setCanEdit(false);
    }
    else if ((paramString.equals("NOFUNDSON")) || (paramString.equals("NOFUNDSON_NOTIF")))
    {
      setStatus(10);
    }
    else if ((paramString.equals("FAILEDON")) || (paramString.equals("FAILEDON_NOTIF")) || (paramString.equals("BACKENDFAILED")))
    {
      setStatus(6);
    }
    else if (paramString.equals("CANCELEDON"))
    {
      setStatus(3);
    }
    else if ((paramString.equals("LIMIT_CHECK_FAILED")) || (paramString.equals("LIMIT_CHECK_FAILED_NOTIF")))
    {
      setStatus(13);
    }
    else if (paramString.equals("LIMIT_REVERT_FAILED"))
    {
      setStatus(14);
    }
    else if (paramString.equals("APPROVAL_FAILED"))
    {
      setStatus(15);
    }
    else if (paramString.equals("TEMPLATE"))
    {
      setStatus(16);
    }
    else if (paramString.equals("RECTEMPLATE"))
    {
      setStatus(17);
    }
    else if (paramString.equals("APPROVAL_PENDING"))
    {
      setStatus(8);
    }
    else if (paramString.equals("APPROVAL_REJECTED"))
    {
      setStatus(12);
    }
    else if (paramString.equals("BATCH_INPROCESS"))
    {
      setStatus(11);
      setCanDelete(false);
      setCanEdit(false);
    }
    else if (paramString.equals("IMMED_INPROCESS"))
    {
      setStatus(19);
      setCanDelete(false);
      setCanEdit(false);
    }
    else if (paramString.equals("INPROCESS"))
    {
      setStatus(20);
      setCanDelete(false);
      setCanEdit(false);
    }
    else if (paramString.equals("FUNDSPROCESSED"))
    {
      setStatus(21);
      setCanDelete(false);
      setCanEdit(false);
    }
    else
    {
      setStatus(0);
    }
  }
  
  public static String mapXferStatusToStr(int paramInt)
  {
    String str = "UNKNOWN";
    switch (paramInt)
    {
    case 2: 
      str = "WILLPROCESSON";
      break;
    case 5: 
      str = "POSTEDON";
      break;
    case 18: 
      str = "PROCESSEDON";
      break;
    case 10: 
      str = "NOFUNDSON";
      break;
    case 6: 
      str = "FAILEDON";
      break;
    case 3: 
      str = "CANCELEDON";
      break;
    case 13: 
      str = "LIMIT_CHECK_FAILED";
      break;
    case 14: 
      str = "LIMIT_REVERT_FAILED";
      break;
    case 8: 
      str = "APPROVAL_PENDING";
      break;
    case 12: 
      str = "APPROVAL_REJECTED";
      break;
    case 15: 
      str = "APPROVAL_FAILED";
      break;
    case 11: 
      str = "BATCH_INPROCESS";
      break;
    case 16: 
      str = "TEMPLATE";
      break;
    case 17: 
      str = "RECTEMPLATE";
      break;
    case 19: 
      str = "IMMED_INPROCESS";
      break;
    case 4: 
    case 7: 
    case 9: 
    default: 
      str = "UNKNOWN";
    }
    return str;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "DATE");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    Transfer localTransfer = (Transfer)paramObject;
    int i = 1;
    Collator localCollator = doGetCollator();
    if ((paramString.equals("RECTRANSFERID")) && (this.recid != null) && (localTransfer.getRecTransferID() != null)) {
      i = localCollator.compare(getRecTransferID(), localTransfer.getRecTransferID());
    } else if (paramString.equals("STATUS")) {
      i = this.status - localTransfer.getStatus();
    } else if ((paramString.equals("DATE")) && (this.date != null) && (localTransfer.getDateValue() != null)) {
      i = this.date.compare(localTransfer.getDateValue());
    } else if ((paramString.equals("FROMACCOUNTID")) && (getFromAccountID() != null) && (localTransfer.getFromAccountID() != null)) {
      i = localCollator.compare(getFromAccountID(), localTransfer.getFromAccountID());
    } else if ((paramString.equals("FROMACCOUNTID")) && (getFromAccount() != null) && (getFromAccount().getID() != null) && (localTransfer.getFromAccount() != null) && (localTransfer.getFromAccount().getID() != null)) {
      i = localCollator.compare(getFromAccount().getID(), localTransfer.getFromAccount().getID());
    } else if ((paramString.equals("TOACCOUNTID")) && (getToAccountID() != null) && (localTransfer.getToAccountID() != null)) {
      i = localCollator.compare(getToAccountID(), localTransfer.getToAccountID());
    } else if ((paramString.equals("TOACCOUNTID")) && (getToAccount() != null) && (getToAccount().getID() != null) && (localTransfer.getToAccount() != null) && (localTransfer.getToAccount().getID() != null)) {
      i = localCollator.compare(getToAccount().getID(), localTransfer.getToAccount().getID());
    } else if ((paramString.equals("TRANSACTIONID")) && (getTransactionID() != null) && (localTransfer.getTransactionID() != null)) {
      i = localCollator.compare(getTransactionID(), localTransfer.getTransactionID());
    } else if ((paramString.equals("MEMO")) && (getMemo() != null) && (localTransfer.getMemo() != null)) {
      i = localCollator.compare(getMemo(), localTransfer.getMemo());
    } else if ((paramString.equals("BANK_ID")) && (getBankID() != null) && (localTransfer.getBankID() != null)) {
      i = localCollator.compare(getBankID(), localTransfer.getBankID());
    } else if ((paramString.equals("TRANSFER_TYPE")) && (getTransferType() != null) && (localTransfer.getTransferType() != null)) {
      i = localCollator.compare(getTransferType().toLowerCase(), localTransfer.getTransferType().toLowerCase());
    } else if ((paramString.equals("TRANSFER_SOURCE")) && (getTransferSource() != null) && (localTransfer.getTransferSource() != null)) {
      i = localCollator.compare(getTransferSource().toLowerCase(), localTransfer.getTransferSource().toLowerCase());
    } else if ((paramString.equals("TRANSFER_DESTINATION")) && (getTransferDestination() != null) && (localTransfer.getTransferDestination() != null)) {
      i = localCollator.compare(getTransferDestination().toLowerCase(), localTransfer.getTransferDestination().toLowerCase());
    } else if ((paramString.equals("TEMPLATE_ID")) && (getTemplateID() != null) && (localTransfer.getTemplateID() != null)) {
      i = localCollator.compare(getTemplateID(), localTransfer.getTemplateID());
    } else if ((paramString.equals("TEMPLATE_NAME")) && (getTemplateName() != null) && (localTransfer.getTemplateName() != null)) {
      i = localCollator.compare(getTemplateName(), localTransfer.getTemplateName());
    } else if ((paramString.equals("DATE_TO_POST")) && (getDateToPostValue() != null) && (localTransfer.getDateToPostValue() != null)) {
      i = this.dateToPost.equals(localTransfer.getDateToPostValue()) ? 0 : this.dateToPost.before(localTransfer.getDateToPostValue()) ? -1 : 1;
    } else if ((paramString.equals("DATE_POSTED")) && (getDatePostedValue() != null) && (localTransfer.getDatePostedValue() != null)) {
      i = this.datePosted.equals(localTransfer.getDatePostedValue()) ? 0 : this.datePosted.before(localTransfer.getDatePostedValue()) ? -1 : 1;
    } else if ((paramString.equals("CUSTOMER_ID")) && (getCustomerID() != null) && (localTransfer.getCustomerID() != null)) {
      i = localCollator.compare(getCustomerID(), localTransfer.getCustomerID());
    } else if ((paramString.equals("CONFIRMATIONNUMBER")) && (getConfirmationNum() != null) && (localTransfer.getConfirmationNum() != null)) {
      i = localCollator.compare(getConfirmationNum(), localTransfer.getConfirmationNum());
    } else if ((paramString.equals("FED_CONFIRMATION_NUM")) && (getFedConfirmationNum() != null) && (localTransfer.getFedConfirmationNum() != null)) {
      i = localCollator.compare(getFedConfirmationNum(), localTransfer.getFedConfirmationNum());
    } else if ((paramString.equals("AMOUNT")) && (getAmount() != null) && (localTransfer.getAmount() != null)) {
      i = localCollator.compare(getAmount(), localTransfer.getAmount());
    } else if ((paramString.equals("TOAMOUNT")) && (getToAmount() != null) && (localTransfer.getToAmount() != null)) {
      i = localCollator.compare(getToAmount(), localTransfer.getToAmount());
    } else if ((paramString.equals("TRANSFER_CATEGORY")) && (getTransferCategory() != null) && (localTransfer.getTransferCategory() != null)) {
      i = localCollator.compare(getTransferCategory(), localTransfer.getTransferCategory());
    } else if ((paramString.equals("TRANSFER_SCOPE")) && (getTransferScope() != null) && (localTransfer.getTransferScope() != null)) {
      i = localCollator.compare(getTransferScope(), localTransfer.getTransferScope());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1.equals("RECTRANSFERID")) && (getRecTransferID() != null)) {
      return isFilterable(getRecTransferID(), paramString2, paramString3);
    }
    if (paramString1.equals("STATUS")) {
      return isFilterable(String.valueOf(getStatus()), paramString2, paramString3);
    }
    if ((paramString1.equals("DATE")) && (this.date != null)) {
      return this.date.isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((paramString1.equals("FROMACCOUNTID")) && (getFromAccountID() != null)) {
      return isFilterable(getFromAccountID(), paramString2, paramString3);
    }
    if ((paramString1.equals("TOACCOUNTID")) && (getToAccountID() != null)) {
      return isFilterable(getToAccountID(), paramString2, paramString3);
    }
    if ((paramString1.equals("TRANSACTIONID")) && (getTransactionID() != null)) {
      return isFilterable(getTransactionID(), paramString2, paramString3);
    }
    if ((paramString1.equals("MEMO")) && (getMemo() != null)) {
      return isFilterable(getMemo(), paramString2, paramString3);
    }
    if ((paramString1.equals("CUSTOMER_ID")) && (getCustomerID() != null)) {
      return isFilterable(getCustomerID(), paramString2, paramString3);
    }
    if ((paramString1.equals("DATE_TO_POST")) && (this.dateToPost != null)) {
      return this.dateToPost.isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((paramString1.equals("DATE_POSTED")) && (this.datePosted != null)) {
      return this.datePosted.isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((paramString1.equals("TRANSFER_TYPE")) && (getTransferType() != null)) {
      return isFilterable(getTransferType(), paramString2, paramString3);
    }
    if ((paramString1.equals("TRANSFER_DESTINATION")) && (getTransferDestination() != null)) {
      return isFilterable(getTransferDestination(), paramString2, paramString3);
    }
    if ((paramString1.equals("TEMPLATE_ID")) && (getTemplateID() != null)) {
      return isFilterable(getTemplateID(), paramString2, paramString3);
    }
    if ((paramString1.equals("TEMPLATE_NAME")) && (getTemplateName() != null)) {
      return isFilterable(getTemplateName(), paramString2, paramString3);
    }
    if ((paramString1.equals("TOAMOUNT")) && (this.toAmount != null)) {
      return this.toAmount.isFilterable("VALUE" + paramString2 + paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public Object clone()
  {
    try
    {
      Transfer localTransfer = (Transfer)super.clone();
      if (this.date != null) {
        localTransfer.setDate((DateTime)this.date.clone());
      }
      if (this.dateToPost != null) {
        localTransfer.setDateToPost((DateTime)this.dateToPost.clone());
      }
      if (this.datePosted != null) {
        localTransfer.setDatePosted((DateTime)this.datePosted.clone());
      }
      if (this.fromAccount != null) {
        localTransfer.setFromAccount((Account)this.fromAccount.clone());
      }
      if (this.toAccount != null) {
        localTransfer.setToAccount((Account)this.toAccount.clone());
      }
      return localTransfer;
    }
    catch (Exception localException) {}
    return super.clone();
  }
  
  public void fromXML(String paramString, Accounts paramAccounts)
  {
    setXML(paramString, paramAccounts);
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(null), paramString);
    }
    catch (Throwable localThrowable) {}
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "TRANSFER");
    XMLHandler.appendTag(localStringBuffer, "RECTRANSFERID", this.recid);
    XMLHandler.appendTag(localStringBuffer, "FROMACCOUNTID", getFromAccountID());
    if (this.fromAccount != null) {
      localStringBuffer.append(this.fromAccount.getXML("FROMACCOUNT", false));
    }
    XMLHandler.appendTag(localStringBuffer, "TOACCOUNTID", getToAccountID());
    if (this.toAccount != null) {
      localStringBuffer.append(this.toAccount.getXML("TOACCOUNT", false));
    }
    if (this.date != null) {
      XMLHandler.appendTag(localStringBuffer, "DATE", this.date.toXMLFormat());
    }
    XMLHandler.appendTag(localStringBuffer, "STATUS", this.status);
    XMLHandler.appendTag(localStringBuffer, "ERROR", this.error);
    XMLHandler.appendTag(localStringBuffer, "TRANSACTIONID", this.transactionID);
    XMLHandler.appendTag(localStringBuffer, "MEMO", this.memo);
    XMLHandler.appendTag(localStringBuffer, "BANK_ID", this.bankID);
    XMLHandler.appendTag(localStringBuffer, "CUSTOMER_ID", this.customerID);
    XMLHandler.appendTag(localStringBuffer, "PROCESSED_BY", this.processedBy);
    if (this.dateToPost != null) {
      XMLHandler.appendTag(localStringBuffer, "DATE_TO_POST", this.dateToPost.toXMLFormat());
    }
    if (this.datePosted != null) {
      XMLHandler.appendTag(localStringBuffer, "DATE_POSTED", this.datePosted.toXMLFormat());
    }
    if (this.settlementDate != null) {
      XMLHandler.appendTag(localStringBuffer, "SETTLEMENT_DATE", this.settlementDate.toXMLFormat());
    }
    XMLHandler.appendTag(localStringBuffer, "CONFIRMATION_MSG", this.confirmationMsg);
    XMLHandler.appendTag(localStringBuffer, "TRANSFER_TYPE", this.transferType);
    XMLHandler.appendTag(localStringBuffer, "TRANSFER_SOURCE", this.transferSource);
    XMLHandler.appendTag(localStringBuffer, "TRANSFER_CATEGORY", this.transferCategory);
    XMLHandler.appendTag(localStringBuffer, "TRANSFER_SCOPE", this.transferScope);
    XMLHandler.appendTag(localStringBuffer, "TRANSFER_DESTINATION", this.transferDestination);
    XMLHandler.appendTag(localStringBuffer, "TEMPLATE_ID", this.templateID);
    XMLHandler.appendTag(localStringBuffer, "TEMPLATE_NAME", this.templateName);
    if (this.amount != null) {
      XMLHandler.appendTag(localStringBuffer, "CURRENCY_TYPE", this.amount.getCurrencyCode());
    }
    if (this.toAmount != null)
    {
      XMLHandler.appendTag(localStringBuffer, "TOAMOUNT", this.toAmount.getCurrencyStringNoSymbolNoComma());
      XMLHandler.appendTag(localStringBuffer, "TO_CURRENCY_TYPE", this.toAmount.getCurrencyCode());
    }
    XMLHandler.appendTag(localStringBuffer, "USERASSIGNED_AMOUNT", getUserAssignedAmountFlagName());
    XMLHandler.appendTag(localStringBuffer, "ORIG_AMOUNT", this.origAmount);
    XMLHandler.appendTag(localStringBuffer, "ORIG_CURRENCY", this.origCurrency);
    XMLHandler.appendTag(localStringBuffer, "CONFIRMATIONNUMBER", this.confirmationNum);
    XMLHandler.appendTag(localStringBuffer, "FED_CONFIRMATION_NUM", this.fedConfirmationNum);
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "TRANSFER");
    return localStringBuffer.toString();
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
  
  protected void getInquireComment(StringBuffer paramStringBuffer)
  {
    Object[] arrayOfObject1 = new Object[1];
    LocalizableString localLocalizableString = null;
    arrayOfObject1[0] = super.getID();
    if ((arrayOfObject1[0] == null) || (((String)arrayOfObject1[0]).length() == 0)) {
      arrayOfObject1[0] = new LocalizableString("com.ffusion.beans.banking.resources", "InquiryNoID", null);
    }
    localLocalizableString = new LocalizableString("com.ffusion.beans.banking.resources", "InquiryID", arrayOfObject1);
    paramStringBuffer.append((String)localLocalizableString.localize(this.locale)).append("\n");
    localLocalizableString = null;
    arrayOfObject1[0] = null;
    if (super.getAmountValue() == null) {
      arrayOfObject1[0] = new LocalizableString("com.ffusion.beans.banking.resources", "InquiryNoAmount", null);
    } else {
      arrayOfObject1[0] = new LocalizableCurrency(super.getAmountValue());
    }
    localLocalizableString = new LocalizableString("com.ffusion.beans.banking.resources", "InquiryAmount", arrayOfObject1);
    paramStringBuffer.append((String)localLocalizableString.localize(this.locale)).append("\n");
    localLocalizableString = null;
    arrayOfObject1[0] = null;
    arrayOfObject1[0] = super.getReferenceNumber();
    if ((arrayOfObject1[0] == null) || (((String)arrayOfObject1[0]).length() == 0)) {
      arrayOfObject1[0] = new LocalizableString("com.ffusion.beans.banking.resources", "InquiryNoReference", null);
    }
    localLocalizableString = new LocalizableString("com.ffusion.beans.banking.resources", "InquiryReferenceNum", arrayOfObject1);
    paramStringBuffer.append((String)localLocalizableString.localize(this.locale)).append("\n");
    localLocalizableString = null;
    arrayOfObject1[0] = null;
    DateTime localDateTime = getDateToPostValue();
    if (localDateTime != null) {
      arrayOfObject1[0] = new LocalizableDate(localDateTime, false);
    } else {
      arrayOfObject1[0] = new LocalizableString("com.ffusion.beans.banking.resources", "InquiryNoDate", null);
    }
    localLocalizableString = new LocalizableString("com.ffusion.beans.banking.resources", "InquiryDueDate", arrayOfObject1);
    paramStringBuffer.append((String)localLocalizableString.localize(this.locale)).append("\n");
    localLocalizableString = null;
    arrayOfObject1[0] = null;
    localDateTime = null;
    localDateTime = getDatePostedValue();
    if (localDateTime != null) {
      arrayOfObject1[0] = new LocalizableDate(localDateTime, false);
    } else {
      arrayOfObject1[0] = new LocalizableString("com.ffusion.beans.banking.resources", "InquiryNoDate", null);
    }
    localLocalizableString = new LocalizableString("com.ffusion.beans.banking.resources", "InquiryProcessDate", arrayOfObject1);
    paramStringBuffer.append((String)localLocalizableString.localize(this.locale)).append("\n");
    localLocalizableString = null;
    arrayOfObject1[0] = null;
    localDateTime = null;
    Object[] arrayOfObject2;
    if ((getFromAccount() != null) && (getFromAccount().getID() != null))
    {
      str1 = getFromAccount().getNickName();
      if ((str1 != null) && (str1.length() != 0))
      {
        arrayOfObject2 = new Object[2];
        arrayOfObject2[0] = getFromAccount().getID();
        arrayOfObject2[1] = getFromAccount().getNickName();
        localLocalizableString = new LocalizableString("com.ffusion.beans.banking.resources", "InquiryFromAccountNickname", arrayOfObject2);
      }
      else
      {
        arrayOfObject1[0] = getFromAccount().getID();
        localLocalizableString = new LocalizableString("com.ffusion.beans.banking.resources", "InquiryFromAccount", arrayOfObject1);
      }
    }
    else
    {
      arrayOfObject1[0] = new LocalizableString("com.ffusion.beans.banking.resources", "InquiryNoAccount", null);
      localLocalizableString = new LocalizableString("com.ffusion.beans.banking.resources", "InquiryFromAccount", arrayOfObject1);
    }
    paramStringBuffer.append((String)localLocalizableString.localize(this.locale)).append("\n");
    localLocalizableString = null;
    arrayOfObject1[0] = null;
    if ((getToAccount() != null) && (getToAccount().getID() != null))
    {
      str1 = getToAccount().getNickName();
      if ((str1 != null) && (str1.length() != 0))
      {
        arrayOfObject2 = new Object[2];
        arrayOfObject2[0] = getToAccount().getID();
        arrayOfObject2[1] = getToAccount().getNickName();
        localLocalizableString = new LocalizableString("com.ffusion.beans.banking.resources", "InquiryToAccountNickname", arrayOfObject2);
      }
      else
      {
        arrayOfObject1[0] = getToAccount().getID();
        localLocalizableString = new LocalizableString("com.ffusion.beans.banking.resources", "InquiryToAccount", arrayOfObject1);
      }
    }
    else
    {
      arrayOfObject1[0] = new LocalizableString("com.ffusion.beans.banking.resources", "InquiryNoAccount", null);
      localLocalizableString = new LocalizableString("com.ffusion.beans.banking.resources", "InquiryToAccount", arrayOfObject1);
    }
    paramStringBuffer.append((String)localLocalizableString.localize(this.locale)).append("\n");
    localLocalizableString = null;
    arrayOfObject1[0] = null;
    String str1 = (String)get("Frequency");
    if ((str1 == null) || (str1.toString().length() == 0)) {
      arrayOfObject1[0] = new LocalizableString("com.ffusion.beans.banking.resources", "InquiryNone", null);
    } else {
      for (int i = 0; i < 10; i++)
      {
        String str2 = ResourceUtil.getString("RecTransferFrequencies" + i, "com.ffusion.beans.banking.resources", this.locale);
        if (str2.equalsIgnoreCase(str1))
        {
          arrayOfObject1[0] = str2;
          break;
        }
      }
    }
    localLocalizableString = new LocalizableString("com.ffusion.beans.banking.resources", "InquiryFrequency", arrayOfObject1);
    paramStringBuffer.append((String)localLocalizableString.localize(this.locale)).append("\n");
    try
    {
      arrayOfObject1[0] = getStatusName();
      if ((arrayOfObject1[0] == null) || (arrayOfObject1[0] == "")) {
        arrayOfObject1[0] = new LocalizableString("com.ffusion.beans.banking.resources", "InquiryNone", null);
      }
    }
    catch (MissingResourceException localMissingResourceException)
    {
      arrayOfObject1[0] = new LocalizableString("com.ffusion.beans.banking.resources", "InquiryInvalidStatus", null);
    }
    localLocalizableString = new LocalizableString("com.ffusion.beans.banking.resources", "InquiryStatus", arrayOfObject1);
    paramStringBuffer.append((String)localLocalizableString.localize(this.locale)).append("\n");
    localLocalizableString = null;
    arrayOfObject1[0] = null;
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, Transfer paramTransfer, String paramString)
  {
    logChanges(paramHistoryTracker, BEAN_NAME, paramTransfer, paramString);
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, String paramString1, Transfer paramTransfer, String paramString2)
  {
    if (!BEAN_NAME.equals(paramString1)) {
      paramString1 = paramString1 + "," + BEAN_NAME;
    }
    paramHistoryTracker.detectChange(paramString1, "ID", paramTransfer.getID(), getID(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "TRACKINGID", paramTransfer.getTrackingID(), getTrackingID(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "RECTRANSFERID", paramTransfer.getRecTransferID(), getRecTransferID(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "FROMACCOUNTID", paramTransfer.getFromAccountDisplayText(), getFromAccountDisplayText(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "TOACCOUNTID", paramTransfer.getToAccountDisplayText(), getToAccountDisplayText(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "DATE", paramTransfer.getDate(), getDate(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "STATUS", paramTransfer.getStatusName(), getStatusName(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "ERROR", paramTransfer.getError(), getError(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "TRANSACTIONID", paramTransfer.getTransactionID(), getTransactionID(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "MEMO", paramTransfer.getMemo(), getMemo(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "BANK_ID", paramTransfer.getBankID(), getBankID(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "CUSTOMER_ID", paramTransfer.getCustomerID(), getCustomerID(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "DATE_TO_POST", paramTransfer.getDateToPost(), getDateToPost(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "DATE_POSTED", paramTransfer.getDatePosted(), getDatePosted(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "SETTLEMENT_DATE", paramTransfer.getSettlementDate(), getSettlementDate(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "TRANSFER_TYPE", paramTransfer.getTransferType(), getTransferType(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "TRANSFER_SOURCE", paramTransfer.getTransferSource(), getTransferSource(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "TRANSFER_CATEGORY", paramTransfer.getTransferCategory(), getTransferCategory(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "TRANSFER_SCOPE", paramTransfer.getTransferScope(), getTransferScope(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "TRANSFER_DESTINATION", paramTransfer.getTransferDestination(), getTransferDestination(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "ORIG_AMOUNT", paramTransfer.getOrigAmount(), getOrigAmount(), paramString2);
    paramHistoryTracker.detectChange(paramString1, "ORIG_CURRENCY", paramTransfer.getOrigCurrency(), getOrigCurrency(), paramString2);
    super.logChanges(paramHistoryTracker, paramString1, paramTransfer, paramString2);
  }
  
  public void logCreation(HistoryTracker paramHistoryTracker, String paramString)
  {
    paramHistoryTracker.logCreate(BEAN_NAME, "TRACKINGID", getTrackingID(), paramString);
  }
  
  public void logDeletion(HistoryTracker paramHistoryTracker, String paramString)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "TRACKINGID", getTrackingID(), null, paramString);
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, Transfer paramTransfer, ILocalizable paramILocalizable)
  {
    logChanges(paramHistoryTracker, BEAN_NAME, paramTransfer, paramILocalizable);
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, String paramString, Transfer paramTransfer, ILocalizable paramILocalizable)
  {
    if (!BEAN_NAME.equals(paramString)) {
      paramString = paramString + "," + BEAN_NAME;
    }
    paramHistoryTracker.detectChange(paramString, "ID", paramTransfer.getID(), getID(), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "TRACKINGID", paramTransfer.getTrackingID(), getTrackingID(), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "RECTRANSFERID", paramTransfer.getRecTransferID(), getRecTransferID(), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "FROMACCOUNTID", paramTransfer.getFromAccount() == null ? null : paramTransfer.getFromAccount().buildLocalizableAccountID(), getFromAccount() == null ? null : getFromAccount().buildLocalizableAccountID(), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "TOACCOUNTID", paramTransfer.getToAccount() == null ? null : paramTransfer.getToAccount().buildLocalizableAccountID(), getToAccount() == null ? null : getToAccount().buildLocalizableAccountID(), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "DATE", paramTransfer.getDateValue() == null ? null : new LocalizableDate(paramTransfer.getDateValue(), false), getDateValue() == null ? null : new LocalizableDate(getDateValue(), false), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "STATUS", new LocalizableString("com.ffusion.beans.banking.resources", "TransferStatus" + paramTransfer.getStatus(), null), new LocalizableString("com.ffusion.beans.banking.resources", "TransferStatus" + getStatus(), null), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "ERROR", paramTransfer.getError(), getError(), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "TRANSACTIONID", paramTransfer.getTransactionID(), getTransactionID(), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "MEMO", paramTransfer.getMemo(), getMemo(), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "BANK_ID", paramTransfer.getBankID(), getBankID(), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "CUSTOMER_ID", paramTransfer.getCustomerID(), getCustomerID(), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "DATE_TO_POST", paramTransfer.getDateToPostValue() == null ? null : new LocalizableDate(paramTransfer.getDateToPostValue(), false), getDateToPostValue() == null ? null : new LocalizableDate(getDateToPostValue(), false), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "DATE_POSTED", paramTransfer.getDatePostedValue() == null ? null : new LocalizableDate(paramTransfer.getDatePostedValue(), false), getDatePostedValue() == null ? null : new LocalizableDate(getDatePostedValue(), false), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "SETTLEMENT_DATE", paramTransfer.getSettlementDateValue() == null ? null : new LocalizableDate(paramTransfer.getSettlementDateValue(), false), getSettlementDateValue() == null ? null : new LocalizableDate(getSettlementDateValue(), false), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "TRANSFER_CATEGORY", paramTransfer.getTransferCategory() == null ? null : new LocalizableString("com.ffusion.beans.banking.resources", "TransferCategory" + paramTransfer.getTransferCategory(), null), getTransferCategory() == null ? null : new LocalizableString("com.ffusion.beans.banking.resources", "TransferCategory" + getTransferCategory(), null), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "TRANSFER_SCOPE", paramTransfer.getTransferScope() == null ? null : new LocalizableString("com.ffusion.beans.banking.resources", "TransferScope" + paramTransfer.getTransferScope(), null), getTransferScope() == null ? null : new LocalizableString("com.ffusion.beans.banking.resources", "TransferScope" + getTransferScope(), null), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "ORIG_CURRENCY", paramTransfer.getOrigCurrency(), getOrigCurrency(), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "CURRENCY_TYPE", paramTransfer.getAmtCurrency(), getAmtCurrency(), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "TOAMOUNT", paramTransfer.getToAmountValue(), getToAmountValue(), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "TO_CURRENCY_TYPE", paramTransfer.getToAmtCurrency(), getToAmtCurrency(), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "USERASSIGNED_AMOUNT", paramTransfer.getUserAssignedAmountFlagName(), getUserAssignedAmountFlagName(), paramILocalizable);
    super.logChanges(paramHistoryTracker, paramString, paramTransfer, paramILocalizable);
  }
  
  public void logCreation(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.logCreate(BEAN_NAME, "TRACKINGID", getTrackingID(), paramILocalizable);
  }
  
  public void logDeletion(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "TRACKINGID", getTrackingID(), null, paramILocalizable);
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
    String str5 = "";
    if (this.fromAccount != null) {
      str4 = this.fromAccount.getRoutingNum();
    }
    if (this.toAccount != null) {
      str5 = this.toAccount.getRoutingNum();
    }
    BigDecimal localBigDecimal1 = null;
    BigDecimal localBigDecimal2 = null;
    if (getAmountValue() != null) {
      localBigDecimal1 = getAmountValue().getAmountValue();
    }
    if (getToAmountValue() != null) {
      localBigDecimal2 = getToAmountValue().getAmountValue();
    }
    AuditLogRecord localAuditLogRecord = new AuditLogRecord(str1, i, str2, str3, paramILocalizable, getTrackingID(), paramInt, paramSecureUser.getBusinessID(), localBigDecimal1, getAmtCurrency(), localBigDecimal2, getToAmtCurrency(), getUserAssignedAmountFlag(), getID(), paramString, getToAccountID(), str5, getFromAccountID(), str4, AuditLogUtil.getModuleFromTranType(paramInt));
    return localAuditLogRecord;
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler, Accounts paramAccounts)
  {
    paramXMLHandler.continueWith(new a(paramAccounts));
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a(null));
  }
  
  class a
    extends ExtendABean.InternalXMLHandler
  {
    Accounts jdField_int;
    
    public a(Accounts paramAccounts)
    {
      super();
      this.jdField_int = paramAccounts;
      if (paramAccounts == null) {
        this.jdField_int = new Accounts();
      }
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      Account localAccount;
      if (paramString.equals("FROMACCOUNT"))
      {
        localAccount = this.jdField_int.create(null);
        Transfer.this.fromAccount = localAccount;
        localAccount.continueXMLParsing(getHandler());
        this.jdField_int.remove(localAccount);
      }
      else if (paramString.equals("TOACCOUNT"))
      {
        localAccount = this.jdField_int.create(null);
        Transfer.this.toAccount = localAccount;
        localAccount.continueXMLParsing(getHandler());
        this.jdField_int.remove(localAccount);
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
      Transfer.this.set(getElement(), str, this.jdField_int);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.banking.Transfer
 * JD-Core Version:    0.7.0.1
 */