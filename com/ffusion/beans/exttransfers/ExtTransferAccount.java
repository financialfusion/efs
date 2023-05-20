package com.ffusion.beans.exttransfers;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.AccountMaskConsts;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.settings.AccountSettings;
import com.ffusion.util.settings.SystemException;
import java.text.Collator;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ExtTransferAccount
  extends ExtendABean
  implements AccountMaskConsts, Comparable
{
  public static final String RESOURCE_BUNDLE = "com.ffusion.beans.accounts.resources";
  public static final String KEY_RECIPIENT_TYPE_PREFIX = "RecipientType";
  public static final String KEY_ACCOUNT_DISPLAY_TEXT = "AccountDisplayText";
  public static final String KEY_ACCOUNT_TYPE = "AccountType";
  public static final String KEY_STATUS_TYPE = "ETFStatus";
  public static final String KEY_VERIFYSTATUS_TYPE = "ETFVerifyStatus";
  public static final String KEY_CONSUMER_ACCOUNT_DISPLAY_TEXT = "ConsumerAccountDisplayText";
  static final String BEAN_NAME = ExtTransferAccount.class.getName();
  public static final String EXTTRANSFERACCOUNTINFO = "EXTTRANSFERACCOUNTINFO";
  protected String bpwID;
  protected String number;
  protected String currencyCode;
  protected String bankId;
  protected String nickname;
  protected String bankName;
  protected int verifyStatus;
  protected int status;
  protected DateTime depositDate;
  protected int verifyFailedCount;
  protected String primaryAcctHolder;
  protected String secondaryAcctHolder;
  protected String checkNumber;
  protected DateTime createDate;
  protected int type;
  protected String routingNumber;
  protected String acctBankIDType;
  protected String recipientName;
  protected String recipientId;
  protected int recipientType;
  protected boolean prenote;
  protected String acctCategory;
  public static String RECIPIENT_TYPE_BUSINESS = "Business (CCD)";
  public static String RECIPIENT_TYPE_PERSONAL = "Personal (PPD)";
  public static int RECIPIENT_TYPE_VALUE_BUSINESS = 1;
  public static int RECIPIENT_TYPE_VALUE_PERSONAL = 2;
  private ArrayList iC;
  public static final String KEY_CONSUMER_ACCOUNT_EXTERNAL_XFER_MENU_DISPLAY_TEXT = "ConsumerAccountExtXferMenuDisplayText";
  
  public ExtTransferAccount() {}
  
  public ExtTransferAccount(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
  }
  
  public Object clone()
  {
    try
    {
      ExtTransferAccount localExtTransferAccount = (ExtTransferAccount)super.clone();
      if (this.depositDate != null) {
        localExtTransferAccount.setDepositDateValue((DateTime)this.depositDate.clone());
      }
      if (this.createDate != null) {
        localExtTransferAccount.setCreateDateValue((DateTime)this.createDate.clone());
      }
      return localExtTransferAccount;
    }
    catch (Exception localException) {}
    return null;
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.createDate != null) {
      this.createDate.setLocale(paramLocale);
    }
    if (this.depositDate != null) {
      this.depositDate.setLocale(paramLocale);
    }
  }
  
  public void setBpwID(String paramString)
  {
    this.bpwID = paramString;
  }
  
  public String getBpwID()
  {
    return this.bpwID;
  }
  
  public void mapAccount(Account paramAccount)
  {
    setNickname(paramAccount.getNickName());
    setBankName(paramAccount.getBankName());
    setCurrencyCode(paramAccount.getCurrencyCode());
  }
  
  public void setAccount(Account paramAccount)
  {
    setNumber(paramAccount.getNumber());
    setBankId(paramAccount.getBankID());
    setRoutingNumber(paramAccount.getRoutingNum());
    setType(paramAccount.getType());
    setNickname(paramAccount.getNickName());
    setBankName(paramAccount.getBankName());
    setCurrencyCode(paramAccount.getCurrencyCode());
  }
  
  public String getCurrencyCode()
  {
    return this.currencyCode;
  }
  
  public void setCurrencyCode(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 3)) {
      throw new IllegalArgumentException("Currency code '" + paramString + "' has too many characters.");
    }
    this.currencyCode = paramString;
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.createDate != null) {
      this.createDate.setFormat(paramString);
    }
    if (this.depositDate != null) {
      this.depositDate.setFormat(paramString);
    }
  }
  
  public void setBankId(String paramString)
  {
    this.bankId = paramString;
  }
  
  public String getBankId()
  {
    return this.bankId;
  }
  
  public void setNumber(String paramString)
  {
    this.number = paramString;
  }
  
  public String getNumber()
  {
    return this.number;
  }
  
  public String getNumberMasked()
  {
    try
    {
      return AccountSettings.getMaskedAccountNumber(getNumber(), 4, 'x');
    }
    catch (SystemException localSystemException) {}
    return "";
  }
  
  public void setNickname(String paramString)
  {
    this.nickname = paramString;
  }
  
  public String getNickname()
  {
    return this.nickname;
  }
  
  public void setBankName(String paramString)
  {
    this.bankName = paramString;
  }
  
  public String getBankName()
  {
    return this.bankName;
  }
  
  public void setRoutingNumber(String paramString)
  {
    this.routingNumber = paramString;
  }
  
  public String getRoutingNumber()
  {
    return this.routingNumber;
  }
  
  public void setAcctBankIDType(String paramString)
  {
    this.acctBankIDType = paramString;
  }
  
  public String getAcctBankIDType()
  {
    return this.acctBankIDType;
  }
  
  public void setRecipientName(String paramString)
  {
    this.recipientName = paramString;
  }
  
  public String getRecipientName()
  {
    return this.recipientName;
  }
  
  public void setRecipientId(String paramString)
  {
    this.recipientId = paramString;
  }
  
  public String getRecipientId()
  {
    return this.recipientId;
  }
  
  public void setRecipientType(String paramString)
  {
    try
    {
      if (RECIPIENT_TYPE_BUSINESS.equalsIgnoreCase(paramString)) {
        this.recipientType = RECIPIENT_TYPE_VALUE_BUSINESS;
      } else if (RECIPIENT_TYPE_PERSONAL.equalsIgnoreCase(paramString)) {
        this.recipientType = RECIPIENT_TYPE_VALUE_PERSONAL;
      } else {
        this.recipientType = -1;
      }
    }
    catch (Exception localException) {}
  }
  
  public String getRecipientType()
  {
    String str = null;
    if (this.recipientType == RECIPIENT_TYPE_VALUE_BUSINESS) {
      str = RECIPIENT_TYPE_BUSINESS;
    } else if (this.recipientType == RECIPIENT_TYPE_VALUE_PERSONAL) {
      str = RECIPIENT_TYPE_PERSONAL;
    }
    return str;
  }
  
  public int getRecipientTypeValue()
  {
    return this.recipientType;
  }
  
  public void setType(String paramString)
  {
    try
    {
      this.type = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
    setTypeFilter(this.type);
  }
  
  public void setType(int paramInt)
  {
    this.type = paramInt;
    setTypeFilter(paramInt);
  }
  
  public int getTypeValue()
  {
    return this.type;
  }
  
  public String getTypeValueAsString()
  {
    return String.valueOf(this.type);
  }
  
  public String getType()
  {
    return ResourceUtil.getString("AccountType" + this.type, "com.ffusion.beans.accounts.resources", this.locale);
  }
  
  public int getVerifyStatusValue()
  {
    return this.verifyStatus;
  }
  
  public String getVerifyStatusString()
  {
    return String.valueOf(this.verifyStatus);
  }
  
  public void setVerifyStatusValue(int paramInt)
  {
    this.verifyStatus = paramInt;
  }
  
  public void setVerifyStatusValue(String paramString)
  {
    try
    {
      this.verifyStatus = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
  
  public String getVerifyStatus()
  {
    return ResourceUtil.getString("ETFVerifyStatus" + this.verifyStatus, "com.ffusion.beans.accounts.resources", this.locale);
  }
  
  public void setVerifyStatus(String paramString)
  {
    for (int i = 0; i <= 4; i++)
    {
      String str = ResourceUtil.getString("ETFVerifyStatus" + i, "com.ffusion.beans.accounts.resources", this.locale);
      if ((str.equalsIgnoreCase(paramString)) || (("" + i).equals(paramString)))
      {
        setVerifyStatusValue(i);
        break;
      }
    }
  }
  
  public String getStatus()
  {
    return ResourceUtil.getString("ETFStatus" + this.status, "com.ffusion.beans.accounts.resources", this.locale);
  }
  
  public void setStatus(String paramString)
  {
    for (int i = 0; i <= 3; i++)
    {
      String str = ResourceUtil.getString("ETFStatus" + i, "com.ffusion.beans.accounts.resources", this.locale);
      if ((str.equalsIgnoreCase(paramString)) || (("" + i).equals(paramString)))
      {
        setStatusValue(i);
        break;
      }
    }
  }
  
  public int getStatusValue()
  {
    return this.status;
  }
  
  public void setStatusValue(int paramInt)
  {
    this.status = paramInt;
  }
  
  public String getVerifyFailedCount()
  {
    return "" + this.verifyFailedCount;
  }
  
  public int getVerifyFailedCountValue()
  {
    return this.verifyFailedCount;
  }
  
  public void setVerifyFailedCountValue(int paramInt)
  {
    this.verifyFailedCount = paramInt;
  }
  
  public String getDepositDate()
  {
    if (this.depositDate != null) {
      return this.depositDate.toString();
    }
    return "";
  }
  
  public DateTime getDepositDateValue()
  {
    return this.depositDate;
  }
  
  public String getCreateDate()
  {
    if (this.createDate != null) {
      return this.createDate.toString();
    }
    return "";
  }
  
  public DateTime getCreateDateValue()
  {
    return this.createDate;
  }
  
  public void setDepositDateValue(DateTime paramDateTime)
  {
    this.depositDate = paramDateTime;
  }
  
  public void setCreateDateValue(DateTime paramDateTime)
  {
    this.createDate = paramDateTime;
  }
  
  public void setDepositDate(String paramString)
  {
    try
    {
      if (this.depositDate == null) {
        this.depositDate = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.depositDate.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      this.depositDate = null;
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
    }
  }
  
  public String getPrimaryAcctHolder()
  {
    return this.primaryAcctHolder;
  }
  
  public void setPrimaryAcctHolder(String paramString)
  {
    this.primaryAcctHolder = paramString;
  }
  
  public String getSecondaryAcctHolder()
  {
    return this.secondaryAcctHolder;
  }
  
  public void setSecondaryAcctHolder(String paramString)
  {
    this.secondaryAcctHolder = paramString;
  }
  
  public String getCheckNumber()
  {
    return this.checkNumber;
  }
  
  public void setCheckNumber(String paramString)
  {
    this.checkNumber = paramString;
  }
  
  public String getIsActiveDisabled()
  {
    if ((this.verifyStatus == 2) || (this.verifyStatus == 1)) {
      return "true";
    }
    return "false";
  }
  
  public String getIsActive()
  {
    if (this.status == 1) {
      return "true";
    }
    return "false";
  }
  
  public String getIsInactive()
  {
    if (this.status == 2) {
      return "true";
    }
    return "false";
  }
  
  public String getCanDeleteByCSR()
  {
    return "true";
  }
  
  public String getCanDeleteByUser()
  {
    if ((this.verifyStatus == 2) || (this.verifyStatus == 1)) {
      return "false";
    }
    return "true";
  }
  
  public String getCanCSRRedeposit()
  {
    if (this.verifyStatus == 2) {
      return "true";
    }
    return "false";
  }
  
  public String getCanDepositNow()
  {
    if (this.verifyStatus == 1) {
      return "true";
    }
    return "false";
  }
  
  public String getCanVerifyNow()
  {
    if (this.verifyStatus == 2) {
      return "true";
    }
    return "false";
  }
  
  public void setPrenote(String paramString)
  {
    try
    {
      if ((paramString != null) && (paramString.equalsIgnoreCase("true"))) {
        this.prenote = Boolean.valueOf(paramString).booleanValue();
      } else {
        this.prenote = false;
      }
    }
    catch (Exception localException) {}
  }
  
  public void setPrenote(boolean paramBoolean)
  {
    this.prenote = paramBoolean;
  }
  
  public String getPrenote()
  {
    return String.valueOf(this.prenote);
  }
  
  public boolean getPrenoteValue()
  {
    return this.prenote;
  }
  
  public String getAcctCategory()
  {
    return this.acctCategory;
  }
  
  public void setAcctCategory(String paramString)
  {
    this.acctCategory = paramString;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "TYPE");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    ExtTransferAccount localExtTransferAccount = (ExtTransferAccount)paramObject;
    int i = 1;
    Collator localCollator = doGetCollator();
    if ((paramString.equals("BPWID")) && (this.bpwID != null) && (localExtTransferAccount.getBpwID() != null)) {
      i = localCollator.compare(getBpwID(), localExtTransferAccount.getBpwID());
    } else if ((paramString.equals("NUMBER")) && (this.number != null) && (localExtTransferAccount.getNumber() != null)) {
      i = localCollator.compare(getNumber(), localExtTransferAccount.getNumber());
    } else if ((paramString.equals("NICKNAME")) && (this.nickname != null) && (localExtTransferAccount.getNickname() != null)) {
      i = localCollator.compare(getNickname().toLowerCase(), localExtTransferAccount.getNickname().toLowerCase());
    } else if ((paramString.equals("BANKNAME")) && (this.bankName != null) && (localExtTransferAccount.getBankName() != null)) {
      i = localCollator.compare(getBankName().toLowerCase(), localExtTransferAccount.getBankName().toLowerCase());
    } else if ((paramString.equals("ROUTINGNUMBER")) && (this.routingNumber != null) && (localExtTransferAccount.getRoutingNumber() != null)) {
      i = localCollator.compare(getRoutingNumber(), localExtTransferAccount.getRoutingNumber());
    } else if ((paramString.equals("RECIPIENTNAME")) && (this.recipientName != null) && (localExtTransferAccount.getRecipientName() != null)) {
      i = localCollator.compare(getRecipientName().toLowerCase(), localExtTransferAccount.getRecipientName().toLowerCase());
    } else if ((paramString.equals("RECIPIENTID")) && (this.recipientId != null) && (localExtTransferAccount.getRecipientId() != null)) {
      i = localCollator.compare(getRecipientId(), localExtTransferAccount.getRecipientId());
    } else if ((paramString.equals("RECIPIENTTYPE")) && (getRecipientType() != null) && (localExtTransferAccount.getRecipientType() != null)) {
      i = getRecipientTypeValue() - localExtTransferAccount.getRecipientTypeValue();
    } else if ((paramString.equals("TYPE")) && (getType() != null) && (localExtTransferAccount.getType() != null)) {
      i = getTypeValue() - localExtTransferAccount.getTypeValue();
    } else if ((paramString.equals("CURRENCY_CODE")) && (this.currencyCode != null) && (localExtTransferAccount.getCurrencyCode() != null)) {
      i = localCollator.compare(getCurrencyCode().toLowerCase(), localExtTransferAccount.getCurrencyCode().toLowerCase());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1.equals("BPWID")) && (this.bpwID != null)) {
      return isFilterable(this.bpwID, paramString2, paramString3);
    }
    if ((paramString1.equals("NUMBER")) && (this.number != null)) {
      return isFilterable(this.number, paramString2, paramString3);
    }
    if ((paramString1.equals("NICKNAME")) && (this.nickname != null)) {
      return isFilterable(this.nickname, paramString2, paramString3);
    }
    if ((paramString1.equals("BANKNAME")) && (this.bankName != null)) {
      return isFilterable(this.bankName, paramString2, paramString3);
    }
    if ((paramString1.equals("ROUTINGNUMBER")) && (this.routingNumber != null)) {
      return isFilterable(this.routingNumber, paramString2, paramString3);
    }
    if ((paramString1.equals("RECIPIENTNAME")) && (this.recipientName != null)) {
      return isFilterable(this.recipientName, paramString2, paramString3);
    }
    if ((paramString1.equals("RECIPIENTID")) && (this.recipientId != null)) {
      return isFilterable(this.recipientId, paramString2, paramString3);
    }
    if ((paramString1.equals("RECIPIENTID")) && (this.recipientId != null)) {
      return isFilterable(this.recipientId, paramString2, paramString3);
    }
    if ((paramString1.equals("RECIPIENTTYPE")) && (getRecipientType() != null)) {
      return isFilterable(getRecipientType(), paramString2, paramString3);
    }
    if ((paramString1.equals("TYPE")) && (getType() != null)) {
      return isFilterable(getType(), paramString2, paramString3);
    }
    if ((paramString1.equals("PRENOTE")) && (getPrenote() != null)) {
      return isFilterable(getPrenote(), paramString2, paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public void set(ExtTransferAccount paramExtTransferAccount)
  {
    super.set(paramExtTransferAccount);
    this.bpwID = paramExtTransferAccount.getBpwID();
    this.nickname = paramExtTransferAccount.getNickname();
    this.bankName = paramExtTransferAccount.getBankName();
    this.number = paramExtTransferAccount.getNumber();
    this.recipientName = paramExtTransferAccount.getRecipientName();
    this.recipientId = paramExtTransferAccount.getRecipientId();
    this.type = paramExtTransferAccount.getTypeValue();
    this.recipientType = paramExtTransferAccount.getRecipientTypeValue();
    this.prenote = paramExtTransferAccount.getPrenoteValue();
    this.routingNumber = paramExtTransferAccount.getRoutingNumber();
    setAcctBankIDType(paramExtTransferAccount.getAcctBankIDType());
    this.bankId = paramExtTransferAccount.getBankId();
    this.currencyCode = paramExtTransferAccount.getCurrencyCode();
    this.status = paramExtTransferAccount.getStatusValue();
    this.verifyStatus = paramExtTransferAccount.getVerifyStatusValue();
    if (paramExtTransferAccount.getDepositDateValue() != null) {
      this.depositDate = ((DateTime)paramExtTransferAccount.getDepositDateValue().clone());
    } else {
      this.depositDate = null;
    }
    if (paramExtTransferAccount.getCreateDateValue() != null) {
      this.createDate = ((DateTime)paramExtTransferAccount.getCreateDateValue().clone());
    } else {
      this.createDate = null;
    }
    this.verifyFailedCount = paramExtTransferAccount.verifyFailedCount;
    this.primaryAcctHolder = paramExtTransferAccount.primaryAcctHolder;
    this.secondaryAcctHolder = paramExtTransferAccount.secondaryAcctHolder;
    this.checkNumber = paramExtTransferAccount.checkNumber;
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("BPWID"))
    {
      setBpwID(paramString2);
    }
    else if (paramString1.equals("NUMBER"))
    {
      this.number = paramString2;
    }
    else if (paramString1.equals("NICKNAME"))
    {
      this.nickname = paramString2;
    }
    else if (paramString1.equals("BANKNAME"))
    {
      this.bankName = paramString2;
    }
    else if (paramString1.equals("BANKID"))
    {
      this.bankId = paramString2;
    }
    else if (paramString1.equals("CURRENCY_CODE"))
    {
      this.currencyCode = paramString2;
    }
    else if (paramString1.equals("ROUTINGNUMBER"))
    {
      this.routingNumber = paramString2;
    }
    else if (paramString1.equals("RECIPIENTNAME"))
    {
      this.recipientName = paramString2;
    }
    else if (paramString1.equals("RECIPIENTID"))
    {
      this.recipientId = paramString2;
    }
    else if (paramString1.equals("RECIPIENTTYPE"))
    {
      this.recipientType = Integer.parseInt(paramString2);
    }
    else if (paramString1.equals("TYPE"))
    {
      this.type = Integer.parseInt(paramString2);
    }
    else if (paramString1.equals("PRENOTE"))
    {
      this.prenote = Boolean.valueOf(paramString2).booleanValue();
    }
    else if (paramString1.equalsIgnoreCase("DEPOSITDATE"))
    {
      if (this.depositDate == null)
      {
        this.depositDate = new DateTime(this.locale);
        this.depositDate.setFormat(this.datetype);
      }
      this.depositDate.fromXMLFormat(paramString2);
    }
    else if (paramString1.equalsIgnoreCase("CREATEDATE"))
    {
      if (this.createDate == null)
      {
        this.createDate = new DateTime(this.locale);
        this.createDate.setFormat(this.datetype);
      }
      this.createDate.fromXMLFormat(paramString2);
    }
    else if (paramString1.equals("VERIFYSTATUS"))
    {
      this.verifyStatus = Integer.valueOf(paramString2).intValue();
    }
    else if (paramString1.equals("STATUS"))
    {
      this.status = Integer.valueOf(paramString2).intValue();
    }
    else if (paramString1.equals("VERIFYFAILEDCOUNT"))
    {
      this.verifyFailedCount = Integer.valueOf(paramString2).intValue();
    }
    else if (paramString1.equals("PRIMARYACCTHOLDER"))
    {
      this.primaryAcctHolder = paramString2;
    }
    else if (paramString1.equals("SECONDARYACCTHOLDER"))
    {
      this.secondaryAcctHolder = paramString2;
    }
    else if (paramString1.equals("CHECKNUM"))
    {
      this.checkNumber = paramString2;
    }
    else
    {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendTag(localStringBuffer, "TYPE", getTypeValueAsString());
    XMLHandler.appendTag(localStringBuffer, "BPWID", this.bpwID);
    XMLHandler.appendTag(localStringBuffer, "NUMBER", this.number);
    XMLHandler.appendTag(localStringBuffer, "ROUTINGNUMBER", this.routingNumber);
    XMLHandler.appendTag(localStringBuffer, "NICKNAME", this.nickname);
    XMLHandler.appendTag(localStringBuffer, "BANKNAME", this.bankName);
    XMLHandler.appendTag(localStringBuffer, "RECIPIENTNAME", this.recipientName);
    XMLHandler.appendTag(localStringBuffer, "RECIPIENTID", this.recipientId);
    XMLHandler.appendTag(localStringBuffer, "RECIPIENTTYPE", String.valueOf(this.recipientType));
    XMLHandler.appendTag(localStringBuffer, "STATUS", getStatusValue());
    XMLHandler.appendTag(localStringBuffer, "VERIFYSTATUS", getVerifyStatusValue());
    if (getDepositDateValue() != null) {
      XMLHandler.appendTag(localStringBuffer, "DEPOSITDATE", getDepositDateValue().toXMLFormat());
    }
    if (getCreateDateValue() != null) {
      XMLHandler.appendTag(localStringBuffer, "CREATEDATE", getCreateDateValue().toXMLFormat());
    }
    XMLHandler.appendTag(localStringBuffer, "VERIFYFAILEDCOUNT", this.verifyFailedCount);
    XMLHandler.appendTag(localStringBuffer, "PRIMARYACCTHOLDER", this.primaryAcctHolder);
    XMLHandler.appendTag(localStringBuffer, "SECONDARYACCTHOLDER", this.secondaryAcctHolder);
    XMLHandler.appendTag(localStringBuffer, "CHECKNUM", this.checkNumber);
    XMLHandler.appendTag(localStringBuffer, "TYPE", String.valueOf(this.type));
    XMLHandler.appendTag(localStringBuffer, "PRENOTE", String.valueOf(this.prenote));
    localStringBuffer.append(super.getXML());
    return localStringBuffer.toString();
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, ExtTransferAccount paramExtTransferAccount, String paramString)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "BPWID", paramExtTransferAccount.getBpwID(), this.bpwID, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "NUMBER", paramExtTransferAccount.getNumber(), this.number, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "ROUTINGNUMBER", paramExtTransferAccount.getRoutingNumber(), this.routingNumber, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "NICKNAME", paramExtTransferAccount.getNickname(), this.nickname, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "BANKNAME", paramExtTransferAccount.getBankName(), getBankName(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "TYPE", paramExtTransferAccount.getType(), getType(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "RECIPIENTTYPE", paramExtTransferAccount.getRecipientType(), getRecipientType(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "RECIPIENTNAME", paramExtTransferAccount.getRecipientName(), this.recipientName, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "RECIPIENTID", paramExtTransferAccount.getRecipientId(), this.recipientId, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "PRENOTE", paramExtTransferAccount.getPrenote(), getPrenote(), paramString);
    super.logChanges(paramHistoryTracker, paramExtTransferAccount, paramString);
  }
  
  public void logCreation(HistoryTracker paramHistoryTracker, String paramString)
  {
    paramHistoryTracker.logCreate(BEAN_NAME, "BPWID", getBpwID(), paramString);
  }
  
  public void logDeletion(HistoryTracker paramHistoryTracker, String paramString)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "BPWID", getBpwID(), null, paramString);
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, ExtTransferAccount paramExtTransferAccount, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "BPWID", paramExtTransferAccount.getBpwID(), this.bpwID, paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "NUMBER", paramExtTransferAccount.getNumber(), this.number, paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "ROUTINGNUMBER", paramExtTransferAccount.getRoutingNumber(), this.routingNumber, paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "NICKNAME", paramExtTransferAccount.getNickname(), this.nickname, paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "BANKNAME", paramExtTransferAccount.getBankName(), getBankName(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "TYPE", new LocalizableString("com.ffusion.beans.accounts.resources", "AccountType" + paramExtTransferAccount.getTypeValue(), null), new LocalizableString("com.ffusion.beans.accounts.resources", "AccountType" + getTypeValue(), null), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "RECIPIENTTYPE", new LocalizableString("com.ffusion.beans.accounts.resources", "RecipientType" + paramExtTransferAccount.getRecipientTypeValue(), null), new LocalizableString("com.ffusion.beans.accounts.resources", "RecipientType" + getRecipientTypeValue(), null), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "RECIPIENTNAME", paramExtTransferAccount.getRecipientName(), this.recipientName, paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "RECIPIENTID", paramExtTransferAccount.getRecipientId(), this.recipientId, paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "PRENOTE", paramExtTransferAccount.getPrenoteValue(), getPrenoteValue(), paramILocalizable);
    super.logChanges(paramHistoryTracker, paramExtTransferAccount, paramILocalizable);
  }
  
  public void logCreation(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.logCreate(BEAN_NAME, "BPWID", getBpwID(), paramILocalizable);
  }
  
  public void logDeletion(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "BPWID", getBpwID(), null, paramILocalizable);
  }
  
  public String getInquireComment()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    getInquireComment(localStringBuffer);
    return localStringBuffer.toString();
  }
  
  protected void getInquireComment(StringBuffer paramStringBuffer)
  {
    if (getBpwID() == null) {
      paramStringBuffer.append("No BPWID.\n");
    } else {
      paramStringBuffer.append("Bpw ID is ").append(getBpwID()).append(".\n");
    }
    if (getNumber() == null) {
      paramStringBuffer.append("No number.\n");
    } else {
      paramStringBuffer.append("Number is ").append(getNumber()).append(".\n");
    }
    if (getNickname() == null) {
      paramStringBuffer.append("No nickname.\n");
    } else {
      paramStringBuffer.append("Nickname is ").append(getNickname()).append(".\n");
    }
    if (getBankName() == null) {
      paramStringBuffer.append("No bankName.\n");
    } else {
      paramStringBuffer.append("BankName is ").append(getBankName()).append(".\n");
    }
    if (getRecipientName() == null) {
      paramStringBuffer.append("No recipientName.\n");
    } else {
      paramStringBuffer.append("RecipientName is ").append(getRecipientName()).append(".\n");
    }
    if (getRecipientId() == null) {
      paramStringBuffer.append("No recipientId.\n");
    } else {
      paramStringBuffer.append("RecipientId is ").append(getRecipientId()).append(".\n");
    }
    if (getRecipientType() == null) {
      paramStringBuffer.append("No recipientType.\n");
    } else {
      paramStringBuffer.append("RecipientType is ").append(getRecipientType()).append(".\n");
    }
    if (getType() == null) {
      paramStringBuffer.append("No type.\n");
    } else {
      paramStringBuffer.append("Type is ").append(getType()).append(".\n");
    }
    if (getPrenote() == null) {
      paramStringBuffer.append("No prenote.\n");
    } else {
      paramStringBuffer.append("Prenote is ").append(getPrenote()).append(".\n");
    }
  }
  
  public void setTypeFilter(int paramInt)
  {
    if (this.iC != null) {
      for (int i = this.iC.size() - 1; i >= 0; i--)
      {
        String str = (String)this.iC.get(i);
        for (int j = 0; j < Account.acctTypeNames.length; j++) {
          if (str.equals(Account.acctTypeNames[j]))
          {
            this.iC.remove(i);
            break;
          }
        }
      }
    }
    switch (paramInt)
    {
    case 1: 
      setFilterable("Checking");
      break;
    case 2: 
      setFilterable("Savings");
      break;
    case 3: 
      setFilterable("Credit");
      break;
    case 4: 
      setFilterable("Loan");
      break;
    case 5: 
      setFilterable("Mortgage");
      break;
    case 6: 
      setFilterable("HomeEquity");
      break;
    case 7: 
      setFilterable("CreditLine");
      break;
    case 8: 
      setFilterable("CD");
      break;
    case 9: 
      setFilterable("IRA");
      break;
    case 10: 
      setFilterable("Stocks");
      break;
    case 11: 
      setFilterable("Brokerage");
      break;
    case 12: 
      setFilterable("MoneyMarket");
      break;
    case 13: 
      setFilterable("BusinessLoan");
      break;
    case 15: 
      setFilterable("Other");
      break;
    }
  }
  
  public void setFilterable(String paramString)
  {
    if (this.iC == null) {
      this.iC = new ArrayList();
    }
    if (!this.iC.contains(paramString)) {
      this.iC.add(paramString);
    }
  }
  
  public void removeFilter(String paramString)
  {
    int i = this.iC.indexOf(paramString);
    if (i != -1) {
      this.iC.remove(i);
    }
  }
  
  public String getDisplayText()
  {
    String str = null;
    try
    {
      str = AccountSettings.buildAccountDisplayText(getNumber(), "com.ffusion.beans.accounts.resources", "AccountDisplayText", "com.ffusion.beans.accounts.resources", "AccountType" + getTypeValue(), this.locale);
    }
    catch (SystemException localSystemException)
    {
      str = getNumber();
    }
    return str;
  }
  
  public String getConsumerDisplayText()
  {
    return getConsumerDisplayTextGeneric("ConsumerAccountDisplayText");
  }
  
  public String getConsumerDisplayTextGeneric(String paramString)
  {
    String str1 = null;
    String str2 = getNumber();
    String str3 = getType();
    String str4 = "0.0";
    if ((str2 != null) && (str2.length() > 0) && (str3 != null) && (str3.length() > 0) && (str4 != null) && (str4.length() > 0))
    {
      String str5 = null;
      str5 = ResourceUtil.getString(paramString, "com.ffusion.beans.accounts.resources", this.locale);
      if (str5.length() == 0) {
        str1 = "";
      } else {
        try
        {
          str1 = MessageFormat.format(str5, new Object[] { str2, str3, this.nickname, AccountSettings.getMaskedAccountNumber(str2, 4, 'x'), str4 });
        }
        catch (SystemException localSystemException)
        {
          str1 = "";
        }
      }
    }
    else
    {
      str1 = "";
    }
    return str1;
  }
  
  public String getConsumerMenuDisplayText()
  {
    String str1 = null;
    String str2 = getNumber();
    String str3 = getType();
    String str4 = getNickname();
    String str5 = "0.0";
    if ((str2 != null) && (str2.length() > 0) && (str3 != null) && (str3.length() > 0) && (str5 != null) && (str5.length() > 0))
    {
      String str6 = null;
      str6 = ResourceUtil.getString("ConsumerAccountExtXferMenuDisplayText", "com.ffusion.beans.accounts.resources", this.locale);
      if (str6.length() == 0) {
        str1 = "";
      } else {
        try
        {
          str1 = MessageFormat.format(str6, new Object[] { str2, str3, str4, AccountSettings.getMaskedAccountNumber(str2, 4, 'x'), str5, getBankName() });
        }
        catch (SystemException localSystemException)
        {
          str1 = "";
        }
      }
    }
    else
    {
      str1 = "";
    }
    return str1;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.exttransfers.ExtTransferAccount
 * JD-Core Version:    0.7.0.1
 */