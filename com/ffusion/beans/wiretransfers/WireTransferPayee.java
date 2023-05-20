package com.ffusion.beans.wiretransfers;

import com.ffusion.beans.Contact;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.ffs.bpw.interfaces.BPWBankInfo;
import com.ffusion.ffs.bpw.interfaces.WirePayeeInfo;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.AuditLogRecord;
import com.ffusion.util.logging.AuditLogUtil;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.settings.AccountSettings;
import com.ffusion.util.settings.SystemException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;

public class WireTransferPayee
  extends Contact
  implements WireDefines, Cloneable, Comparable
{
  public static final String KEY_WIRE_PAYEE_ACCOUNT_TYPES_PREFIX = "AccountType_";
  public static final String KEY_WIRE_PAYEE_SCOPE_PREFIX = "WirePayeeScope";
  public static final String KEY_WIRE_PAYEE_DESTINATION_PREFIX = "WirePayeeDestination";
  private String BY;
  private String Ca;
  private String BQ;
  private String BS;
  private String B4;
  private String BR;
  private String B6;
  private String B9;
  private String B8;
  private String BP;
  private String BT;
  private String B5;
  private String B2;
  private static final String BEAN_NAME = WireTransferPayee.class.getName();
  private String BO;
  private int B1;
  private DateTime BW;
  private DateTime BZ;
  private WireTransferBank BV;
  private WireTransferBanks BX;
  private boolean B0 = false;
  private boolean B3 = false;
  private int BU;
  String B7;
  
  public WireTransferPayee()
  {
    this.BV = new WireTransferBank();
    this.BX = new WireTransferBanks();
    this.BP = "USER";
    this.BT = "REGULAR";
  }
  
  public WireTransferPayee(Locale paramLocale)
  {
    if (paramLocale == null) {
      throw new IllegalArgumentException("Locale cannot be null");
    }
    this.locale = paramLocale;
    this.datetype = "SHORT";
    this.BV = new WireTransferBank();
    this.BX = new WireTransferBanks();
    this.BP = "USER";
    this.BT = "REGULAR";
  }
  
  public String getID()
  {
    return this.BY;
  }
  
  public void setID(String paramString)
  {
    this.BY = paramString;
  }
  
  public String getTrackingID()
  {
    return this.Ca;
  }
  
  public void setTrackingID(String paramString)
  {
    this.Ca = paramString;
  }
  
  public String getCustomerID()
  {
    return this.BQ;
  }
  
  public void setCustomerID(String paramString)
  {
    this.BQ = paramString;
  }
  
  public void setCustomerID(int paramInt)
  {
    this.BQ = Integer.toString(paramInt);
  }
  
  public String getSubmittedBy()
  {
    return this.BS;
  }
  
  public void setSubmittedBy(String paramString)
  {
    this.BS = paramString;
  }
  
  public void setSubmittedBy(int paramInt)
  {
    this.BS = String.valueOf(paramInt);
  }
  
  public String getAccountNum()
  {
    return this.B4;
  }
  
  public void setAccountNum(String paramString)
  {
    this.B4 = paramString;
  }
  
  public String getAccountType()
  {
    if (this.BR == null) {
      return "";
    }
    return this.BR;
  }
  
  public void setAccountType(String paramString)
  {
    this.BR = paramString;
  }
  
  public String getAccountDisplayText()
  {
    String str = "";
    if ((this.B4 == null) || (this.B4.trim().length() == 0)) {
      return str;
    }
    if ((this.BR == null) || (this.BR.trim().length() == 0)) {
      return this.B4;
    }
    try
    {
      str = AccountSettings.buildAccountDisplayText(this.B4, "com.ffusion.beans.accounts.resources", "AccountDisplayText", "com.ffusion.beans.accounts.resources", "AccountType" + this.BR, this.locale);
    }
    catch (SystemException localSystemException)
    {
      str = this.B4;
    }
    return str;
  }
  
  public String getPayeeName()
  {
    return this.B6;
  }
  
  public void setPayeeName(String paramString)
  {
    this.B6 = paramString;
  }
  
  public String getNickName()
  {
    return this.B9;
  }
  
  public void setNickName(String paramString)
  {
    this.B9 = paramString;
  }
  
  public String getContactName()
  {
    return this.B8;
  }
  
  public void setContactName(String paramString)
  {
    this.B8 = paramString;
  }
  
  public String getName()
  {
    return this.B6;
  }
  
  public void setName(String paramString)
  {
    this.B6 = paramString;
  }
  
  public String getContact()
  {
    return this.B8;
  }
  
  public void setContact(String paramString)
  {
    this.B8 = paramString;
  }
  
  public String getPayeeScope()
  {
    return this.BP;
  }
  
  public void setPayeeScope(String paramString)
  {
    this.BP = paramString;
  }
  
  public String getPayeeDestination()
  {
    return this.BT;
  }
  
  public void setPayeeDestination(String paramString)
  {
    this.BT = paramString;
  }
  
  public String getMemo()
  {
    return this.B5;
  }
  
  public void setMemo(String paramString)
  {
    if (paramString != null) {
      this.B5 = paramString.trim();
    } else {
      this.B5 = paramString;
    }
  }
  
  public String getAction()
  {
    return this.B2;
  }
  
  public void setAction(String paramString)
  {
    this.B2 = paramString;
  }
  
  public String getRoutes()
  {
    return this.BO;
  }
  
  public void setRoutes(String paramString)
  {
    this.BO = paramString;
  }
  
  public int getStatusValue()
  {
    return this.B1;
  }
  
  public String getStatus()
  {
    return Integer.toString(this.B1);
  }
  
  public void setStatus(int paramInt)
  {
    this.B1 = paramInt;
  }
  
  public void setStatus(String paramString)
  {
    try
    {
      this.B1 = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
  
  public String getActivationDate()
  {
    if (this.BW != null) {
      return this.BW.toString();
    }
    return null;
  }
  
  public DateTime getActivationDateValue()
  {
    return this.BW;
  }
  
  public void setActivationDate(DateTime paramDateTime)
  {
    this.BW = paramDateTime;
  }
  
  public void setActivationDate(String paramString)
  {
    try
    {
      if (this.BW == null) {
        this.BW = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.BW.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public String getLastModDate()
  {
    if (this.BZ != null) {
      return this.BZ.toString();
    }
    return null;
  }
  
  public DateTime getLastModDateValue()
  {
    return this.BZ;
  }
  
  public void setLastModDate(DateTime paramDateTime)
  {
    this.BZ = paramDateTime;
  }
  
  public void setLastModDate(String paramString)
  {
    try
    {
      if (this.BZ == null) {
        this.BZ = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.BZ.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public String getCanEdit()
  {
    if (this.B0) {
      return "true";
    }
    return "false";
  }
  
  public boolean getCanEditValue()
  {
    return this.B0;
  }
  
  public void setCanEdit(boolean paramBoolean)
  {
    this.B0 = paramBoolean;
  }
  
  public void setCanEdit(String paramString)
  {
    this.B0 = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getCanDelete()
  {
    if (this.B3) {
      return "true";
    }
    return "false";
  }
  
  public boolean getCanDeleteValue()
  {
    return this.B3;
  }
  
  public void setCanDelete(boolean paramBoolean)
  {
    this.B3 = paramBoolean;
  }
  
  public void setCanDelete(String paramString)
  {
    this.B3 = Boolean.valueOf(paramString).booleanValue();
  }
  
  public WireTransferBank getDestinationBank()
  {
    return this.BV;
  }
  
  public void setDestinationBank(WireTransferBank paramWireTransferBank)
  {
    this.BV = paramWireTransferBank;
  }
  
  public WireTransferBanks getIntermediaryBanks()
  {
    return this.BX;
  }
  
  public void setIntermediaryBanks(WireTransferBanks paramWireTransferBanks)
  {
    this.BX = paramWireTransferBanks;
  }
  
  public int getErrorCode()
  {
    return this.BU;
  }
  
  public void setErrorCode(int paramInt)
  {
    this.BU = paramInt;
  }
  
  public String getDestBankId()
  {
    return this.B7;
  }
  
  public void setDestBankId(String paramString)
  {
    this.B7 = paramString;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1.equals("ID")) && (getID() != null)) {
      return isFilterable(getID(), paramString2, paramString3);
    }
    if ((paramString1.equals("TRACKINGID")) && (getTrackingID() != null)) {
      return isFilterable(getTrackingID(), paramString2, paramString3);
    }
    if ((paramString1.equals("CUSTOMER_ID")) && (getCustomerID() != null)) {
      return isFilterable(getCustomerID(), paramString2, paramString3);
    }
    if ((paramString1.equals("SUBMITTED_BY")) && (getSubmittedBy() != null)) {
      return isFilterable(getSubmittedBy(), paramString2, paramString3);
    }
    if ((paramString1.equals("PAYEENAME")) && (getPayeeName() != null)) {
      return isFilterable(getPayeeName(), paramString2, paramString3);
    }
    if ((paramString1.equals("PAYEENICKNAME")) && (getNickName() != null)) {
      return isFilterable(getNickName(), paramString2, paramString3);
    }
    if ((paramString1.equals("CONTACT_NAME")) && (getContactName() != null)) {
      return isFilterable(getContactName(), paramString2, paramString3);
    }
    if ((paramString1.equals("PAYEE_SCOPE")) && (getPayeeScope() != null)) {
      return isFilterable(getPayeeScope(), paramString2, paramString3);
    }
    if ((paramString1.equals("PAYEE_DESTINATION")) && (getPayeeDestination() != null)) {
      return isFilterable(getPayeeDestination(), paramString2, paramString3);
    }
    if ((paramString1.equals("CITY")) && (getCity() != null)) {
      return isFilterable(getCity(), paramString2, paramString3);
    }
    if ((paramString1.equals("STATE")) && (getState() != null)) {
      return isFilterable(getState(), paramString2, paramString3);
    }
    if ((paramString1.equals("ZIP_CODE")) && (getZipCode() != null)) {
      return isFilterable(getZipCode(), paramString2, paramString3);
    }
    if ((paramString1.equals("COUNTRY")) && (getCountry() != null)) {
      return isFilterable(getCountry(), paramString2, paramString3);
    }
    if ((paramString1.equals("TOACCOUNTID")) && (getAccountNum() != null)) {
      return isFilterable(getAccountNum(), paramString2, paramString3);
    }
    if ((paramString1.equals("STATUS")) && (getStatus() != null)) {
      return isFilterable(getStatus(), paramString2, paramString3);
    }
    if (paramString1.equals("ACTION")) {
      return isFilterable(getAction(), paramString2, paramString3);
    }
    if ((paramString1.equals("ROUTES")) && (getRoutes() != null)) {
      return getRoutes().indexOf(paramString3) > -1;
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    WireTransferPayee localWireTransferPayee = (WireTransferPayee)paramObject;
    int i;
    if (this == localWireTransferPayee) {
      i = 0;
    } else {
      i = this.B6.compareToIgnoreCase(localWireTransferPayee.getPayeeName());
    }
    return i;
  }
  
  public int compare(Object paramObject, String paramString)
  {
    WireTransferPayee localWireTransferPayee = (WireTransferPayee)paramObject;
    int i = 1;
    if ((paramString.equals("ID")) && (getID() != null) && (localWireTransferPayee.getID() != null)) {
      i = numStringCompare(getID(), localWireTransferPayee.getID());
    }
    if ((paramString.equals("TRACKINGID")) && (getTrackingID() != null) && (localWireTransferPayee.getTrackingID() != null)) {
      i = numStringCompare(getTrackingID(), localWireTransferPayee.getTrackingID());
    } else if ((paramString.equals("PAYEENAME")) && (getPayeeName() != null) && (localWireTransferPayee.getPayeeName() != null)) {
      i = getPayeeName().compareToIgnoreCase(localWireTransferPayee.getPayeeName());
    } else if ((paramString.equals("PAYEENICKNAME")) && (getNickName() != null) && (localWireTransferPayee.getNickName() != null)) {
      i = getNickName().compareToIgnoreCase(localWireTransferPayee.getNickName());
    } else if ((paramString.equals("CITY")) && (getCity() != null) && (localWireTransferPayee.getCity() != null)) {
      i = getCity().compareToIgnoreCase(localWireTransferPayee.getCity());
    } else if ((paramString.equals("STATE")) && (getState() != null) && (localWireTransferPayee.getState() != null)) {
      i = getState().compareToIgnoreCase(localWireTransferPayee.getState());
    } else if ((paramString.equals("ZIP_CODE")) && (getZipCode() != null) && (localWireTransferPayee.getZipCode() != null)) {
      i = numStringCompare(getZipCode(), localWireTransferPayee.getZipCode());
    } else if ((paramString.equals("COUNTRY")) && (getCountry() != null) && (localWireTransferPayee.getCountry() != null)) {
      i = getCountry().compareToIgnoreCase(localWireTransferPayee.getCountry());
    } else if (paramString.equals("STATUS")) {
      i = getStatusValue() - localWireTransferPayee.getStatusValue();
    } else if ((paramString.equals("TOACCOUNTID")) && (getAccountNum() != null) && (localWireTransferPayee.getAccountNum() != null)) {
      i = numStringCompare(getAccountNum(), localWireTransferPayee.getAccountNum());
    } else if ((paramString.equals("ACCOUNT_TYPE")) && (getAccountType() != null) && (localWireTransferPayee.getAccountType() != null)) {
      i = getAccountType().compareToIgnoreCase(localWireTransferPayee.getAccountType());
    } else if ((paramString.equals("ACTIVATION_DATE")) && (getActivationDateValue() != null) && (localWireTransferPayee.getActivationDateValue() != null)) {
      i = this.BW.equals(localWireTransferPayee.getActivationDateValue()) ? 0 : this.BW.before(localWireTransferPayee.getActivationDateValue()) ? -1 : 1;
    } else if ((paramString.equals("LAST_MOD_DATE")) && (getLastModDateValue() != null) && (localWireTransferPayee.getLastModDateValue() != null)) {
      i = this.BZ.equals(localWireTransferPayee.getLastModDateValue()) ? 0 : this.BZ.before(localWireTransferPayee.getLastModDateValue()) ? -1 : 1;
    } else if ((paramString.equals("PAYEE_DESTINATION")) && (getPayeeDestination() != null) && (localWireTransferPayee.getPayeeDestination() != null)) {
      i = getPayeeDestination().compareToIgnoreCase(localWireTransferPayee.getPayeeDestination());
    } else if ((paramString.equals("PAYEE_SCOPE")) && (getPayeeScope() != null) && (localWireTransferPayee.getPayeeScope() != null)) {
      i = getPayeeScope().compareToIgnoreCase(localWireTransferPayee.getPayeeScope());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "WIRE_TRANSFER_PAYEE");
    XMLHandler.appendTag(localStringBuffer, "ID", getID());
    XMLHandler.appendTag(localStringBuffer, "TRACKINGID", getTrackingID());
    XMLHandler.appendTag(localStringBuffer, "CUSTOMER_ID", getCustomerID());
    XMLHandler.appendTag(localStringBuffer, "SUBMITTED_BY", getSubmittedBy());
    XMLHandler.appendTag(localStringBuffer, "TOACCOUNTID", getAccountNum());
    XMLHandler.appendTag(localStringBuffer, "ACCOUNT_TYPE", getAccountType());
    XMLHandler.appendTag(localStringBuffer, "PAYEENAME", getPayeeName());
    XMLHandler.appendTag(localStringBuffer, "PAYEENICKNAME", getNickName());
    XMLHandler.appendTag(localStringBuffer, "CONTACT_NAME", getContactName());
    XMLHandler.appendTag(localStringBuffer, "PAYEE_SCOPE", getPayeeScope());
    XMLHandler.appendTag(localStringBuffer, "PAYEE_DESTINATION", getPayeeDestination());
    XMLHandler.appendTag(localStringBuffer, "MEMO", getMemo());
    XMLHandler.appendTag(localStringBuffer, "ACTION", getAction());
    XMLHandler.appendTag(localStringBuffer, "ROUTES", getRoutes());
    XMLHandler.appendTag(localStringBuffer, "STATUS", getStatus());
    XMLHandler.appendTag(localStringBuffer, "ACTIVATION_DATE", getActivationDate());
    XMLHandler.appendTag(localStringBuffer, "LAST_MOD_DATE", getLastModDate());
    XMLHandler.appendTag(localStringBuffer, "DESTINATION_BANK_ID", getDestBankId());
    if (getDestinationBank() != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "DESTINATION_BANK");
      localStringBuffer.append(getDestinationBank().getXML());
      XMLHandler.appendEndTag(localStringBuffer, "DESTINATION_BANK");
    }
    if (getIntermediaryBanks() != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "INTERMEDIARY_BANKS");
      localStringBuffer.append(getIntermediaryBanks().getXML());
      XMLHandler.appendEndTag(localStringBuffer, "INTERMEDIARY_BANKS");
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "WIRE_TRANSFER_PAYEE");
    return localStringBuffer.toString();
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    if (paramString1.equalsIgnoreCase("ID")) {
      setID(paramString2);
    }
    if (paramString1.equalsIgnoreCase("TRACKINGID")) {
      setTrackingID(paramString2);
    }
    if (paramString1.equalsIgnoreCase("CUSTOMER_ID")) {
      setCustomerID(paramString2);
    }
    if (paramString1.equalsIgnoreCase("SUBMITTED_BY")) {
      setSubmittedBy(paramString2);
    } else if (paramString1.equalsIgnoreCase("PAYEENAME")) {
      setPayeeName(paramString2);
    } else if (paramString1.equalsIgnoreCase("PAYEENICKNAME")) {
      setNickName(paramString2);
    } else if (paramString1.equalsIgnoreCase("CONTACT_NAME")) {
      setContactName(paramString2);
    } else if (paramString1.equalsIgnoreCase("PAYEE_SCOPE")) {
      setPayeeScope(paramString2);
    } else if (paramString1.equalsIgnoreCase("PAYEE_DESTINATION")) {
      setPayeeDestination(paramString2);
    } else if (paramString1.equalsIgnoreCase("TOACCOUNTID")) {
      setAccountNum(paramString2);
    } else if (paramString1.equalsIgnoreCase("ACCOUNT_TYPE")) {
      setAccountType(paramString2);
    } else if (paramString1.equalsIgnoreCase("ROUTES")) {
      setRoutes(paramString2);
    } else if (paramString1.equals("STATUS")) {
      setStatus(paramString2);
    } else if (paramString1.equalsIgnoreCase("MEMO")) {
      setMemo(paramString2);
    } else if (paramString1.equalsIgnoreCase("ACTIVATION_DATE")) {
      setActivationDate(paramString2);
    } else if (paramString1.equalsIgnoreCase("LAST_MOD_DATE")) {
      setLastModDate(paramString2);
    } else if (paramString1.equalsIgnoreCase("ACTION")) {
      setAction(paramString2);
    } else if (paramString1.equalsIgnoreCase("DESTINATION_BANK_ID")) {
      setDestBankId(paramString2);
    } else {
      return super.set(paramString1, paramString2);
    }
    return true;
  }
  
  public void set(WireTransferPayee paramWireTransferPayee)
  {
    if ((this == paramWireTransferPayee) || (paramWireTransferPayee == null)) {
      return;
    }
    super.set(paramWireTransferPayee);
    setID(paramWireTransferPayee.getID());
    setTrackingID(paramWireTransferPayee.getTrackingID());
    setCustomerID(paramWireTransferPayee.getCustomerID());
    setSubmittedBy(paramWireTransferPayee.getSubmittedBy());
    setPayeeName(paramWireTransferPayee.getPayeeName());
    setNickName(paramWireTransferPayee.getNickName());
    setContactName(paramWireTransferPayee.getContactName());
    setPayeeScope(paramWireTransferPayee.getPayeeScope());
    setPayeeDestination(paramWireTransferPayee.getPayeeDestination());
    setAccountNum(paramWireTransferPayee.getAccountNum());
    setAccountType(paramWireTransferPayee.getAccountType());
    setStatus(paramWireTransferPayee.getStatus());
    setRoutes(paramWireTransferPayee.getRoutes());
    setMemo(paramWireTransferPayee.getMemo());
    setAction(paramWireTransferPayee.getAction());
    setDestBankId(paramWireTransferPayee.getDestBankId());
    if (paramWireTransferPayee.getActivationDateValue() != null) {
      setActivationDate((DateTime)paramWireTransferPayee.getActivationDateValue().clone());
    } else {
      setActivationDate((DateTime)null);
    }
    if (paramWireTransferPayee.getLastModDateValue() != null) {
      setLastModDate((DateTime)paramWireTransferPayee.getLastModDateValue().clone());
    } else {
      setLastModDate((DateTime)null);
    }
    if (paramWireTransferPayee.getDestinationBank() != null) {
      setDestinationBank((WireTransferBank)paramWireTransferPayee.getDestinationBank().clone());
    }
    WireTransferBanks localWireTransferBanks1 = paramWireTransferPayee.getIntermediaryBanks();
    if (localWireTransferBanks1 != null)
    {
      WireTransferBanks localWireTransferBanks2 = new WireTransferBanks();
      for (int i = 0; i < localWireTransferBanks1.size(); i++)
      {
        WireTransferBank localWireTransferBank = (WireTransferBank)localWireTransferBanks1.get(i);
        localWireTransferBanks2.add(localWireTransferBank.clone());
      }
      setIntermediaryBanks(localWireTransferBanks2);
    }
  }
  
  public Object clone()
  {
    try
    {
      WireTransferPayee localWireTransferPayee = (WireTransferPayee)super.clone();
      if (this.BV != null) {
        localWireTransferPayee.setDestinationBank((WireTransferBank)this.BV.clone());
      }
      if ((this.BX != null) && (this.BX.size() > 0))
      {
        WireTransferBanks localWireTransferBanks = new WireTransferBanks();
        for (int i = 0; i < this.BX.size(); i++)
        {
          WireTransferBank localWireTransferBank = (WireTransferBank)this.BX.get(i);
          localWireTransferBanks.add(localWireTransferBank.clone());
        }
        localWireTransferPayee.setIntermediaryBanks(localWireTransferBanks);
      }
      if (this.BW != null) {
        localWireTransferPayee.setActivationDate(this.BW);
      }
      if (this.BZ != null) {
        localWireTransferPayee.setLastModDate(this.BZ);
      }
      return localWireTransferPayee;
    }
    catch (Exception localException) {}
    return super.clone();
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
  
  public WirePayeeInfo getWirePayeeInfo()
  {
    WirePayeeInfo localWirePayeeInfo = new WirePayeeInfo();
    localWirePayeeInfo.setPayeeId(getID());
    localWirePayeeInfo.setExtId(getTrackingID());
    localWirePayeeInfo.setSubmittedBy(getSubmittedBy());
    localWirePayeeInfo.setPayeeGroup(getPayeeScope());
    localWirePayeeInfo.setPayeeDest(getPayeeDestination());
    localWirePayeeInfo.setCustomerID(getCustomerID());
    localWirePayeeInfo.setBeneficiaryName(getPayeeName());
    localWirePayeeInfo.setNickName(getNickName());
    localWirePayeeInfo.setContactName(getContactName());
    localWirePayeeInfo.setPayeeAddr1(getStreet());
    localWirePayeeInfo.setPayeeAddr2(getStreet2());
    localWirePayeeInfo.setPayeeAddr3(getStreet3());
    localWirePayeeInfo.setPayeeCity(getCity());
    localWirePayeeInfo.setPayeeState(getState());
    localWirePayeeInfo.setPayeeZipcode(getZipCode());
    localWirePayeeInfo.setPayeeCountry(getCountry());
    localWirePayeeInfo.setPayeePhone(getPhone());
    localWirePayeeInfo.setBeneficiaryBankId(getDestBankId());
    Hashtable localHashtable = new Hashtable();
    if (getMemo() != null) {
      localHashtable.put("MEMO", getMemo());
    } else {
      localHashtable.put("MEMO", "");
    }
    localWirePayeeInfo.setMemo(localHashtable);
    if (getAction() == null) {
      localWirePayeeInfo.setAction("");
    } else {
      localWirePayeeInfo.setAction(getAction());
    }
    WireTransferBank localWireTransferBank1 = getDestinationBank();
    localWirePayeeInfo.setBeneficiaryBankInfo(localWireTransferBank1.getBankInfo());
    localWirePayeeInfo.setRouteId(getRoutes());
    localWirePayeeInfo.setBankAcctId(getAccountNum());
    localWirePayeeInfo.setBankAcctType(getAccountType());
    WireTransferBanks localWireTransferBanks = getIntermediaryBanks();
    if (localWireTransferBanks != null)
    {
      localWireTransferBanks.setFilter("All");
      if (localWireTransferBanks.size() > 0)
      {
        BPWBankInfo[] arrayOfBPWBankInfo = new BPWBankInfo[localWireTransferBanks.size()];
        Iterator localIterator = localWireTransferBanks.iterator();
        int i = 0;
        while (localIterator.hasNext())
        {
          WireTransferBank localWireTransferBank2 = (WireTransferBank)localIterator.next();
          if (localWireTransferBank2 != null) {
            arrayOfBPWBankInfo[(i++)] = localWireTransferBank2.getBankInfo();
          }
        }
        localWirePayeeInfo.setIntermediateBanks(arrayOfBPWBankInfo);
      }
      else
      {
        localWirePayeeInfo.setIntermediateBanks(null);
      }
    }
    else
    {
      localWirePayeeInfo.setIntermediateBanks(null);
    }
    return localWirePayeeInfo;
  }
  
  public void setWirePayeeInfo(WirePayeeInfo paramWirePayeeInfo)
  {
    if (paramWirePayeeInfo == null) {
      return;
    }
    try
    {
      setID(paramWirePayeeInfo.getPayeeId());
      setTrackingID(paramWirePayeeInfo.getExtId());
      setCustomerID(paramWirePayeeInfo.getCustomerID());
      setSubmittedBy(paramWirePayeeInfo.getSubmittedBy());
      setPayeeName(paramWirePayeeInfo.getBeneficiaryName());
      setNickName(paramWirePayeeInfo.getNickName());
      setContactName(paramWirePayeeInfo.getContactName());
      setPayeeScope(paramWirePayeeInfo.getPayeeGroup());
      setPayeeDestination(paramWirePayeeInfo.getPayeeDest());
      setAccountNum(paramWirePayeeInfo.getBankAcctId());
      setAccountType(paramWirePayeeInfo.getBankAcctType());
      setActivationDate(paramWirePayeeInfo.getActivationDate());
      setStreet(paramWirePayeeInfo.getPayeeAddr1());
      setStreet2(paramWirePayeeInfo.getPayeeAddr2());
      setStreet3(paramWirePayeeInfo.getPayeeAddr3());
      setCity(paramWirePayeeInfo.getPayeeCity());
      setState(paramWirePayeeInfo.getPayeeState());
      setZipCode(paramWirePayeeInfo.getPayeeZipcode());
      setCountry(paramWirePayeeInfo.getPayeeCountry());
      setStatus(paramWirePayeeInfo.getStatus());
      setActivationDate(paramWirePayeeInfo.getActivationDate());
      setLastModDate(paramWirePayeeInfo.getLastModDate());
      setDestBankId(paramWirePayeeInfo.getBeneficiaryBankId());
      Hashtable localHashtable = paramWirePayeeInfo.getMemo();
      if (localHashtable != null)
      {
        localObject = (String)localHashtable.get("MEMO");
        if ((localObject != null) && (((String)localObject).length() > 0)) {
          setMemo((String)localObject);
        }
      }
      else
      {
        setMemo("");
      }
      if (paramWirePayeeInfo.getAction() == null) {
        setAction("");
      } else {
        setAction(paramWirePayeeInfo.getAction());
      }
      Object localObject = new WireTransferBank();
      BPWBankInfo localBPWBankInfo1 = paramWirePayeeInfo.getBeneficiaryBankInfo();
      ((WireTransferBank)localObject).setBankInfo(localBPWBankInfo1);
      setDestinationBank((WireTransferBank)localObject);
      setRoutes(paramWirePayeeInfo.getRouteId());
      BPWBankInfo[] arrayOfBPWBankInfo = paramWirePayeeInfo.getIntermediateBanks();
      if ((arrayOfBPWBankInfo != null) && (arrayOfBPWBankInfo.length != 0))
      {
        WireTransferBanks localWireTransferBanks = new WireTransferBanks();
        for (int i = 0; i < arrayOfBPWBankInfo.length; i++)
        {
          BPWBankInfo localBPWBankInfo2 = arrayOfBPWBankInfo[i];
          if (localBPWBankInfo2.getStatusCode() == 0)
          {
            WireTransferBank localWireTransferBank = localWireTransferBanks.create();
            localWireTransferBank.setBankInfo(localBPWBankInfo2);
          }
        }
        setIntermediaryBanks(localWireTransferBanks);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, WireTransferPayee paramWireTransferPayee, String paramString)
  {
    logChanges(paramHistoryTracker, BEAN_NAME, paramWireTransferPayee, paramString);
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, String paramString1, WireTransferPayee paramWireTransferPayee, String paramString2)
  {
    if (paramWireTransferPayee == null)
    {
      DebugLog.log("WARNING: Unable to record history for wire payee modification due to null old payee.");
      return;
    }
    paramHistoryTracker.detectChange(paramString1, "SUBMITTED_BY", paramWireTransferPayee.BS, this.BS, paramString2);
    paramHistoryTracker.detectChange(paramString1, "TOACCOUNTID", paramWireTransferPayee.B4, this.B4, paramString2);
    paramHistoryTracker.detectChange(paramString1, "ACCOUNT_TYPE", paramWireTransferPayee.BR, this.BR, paramString2);
    paramHistoryTracker.detectChange(paramString1, "PAYEENAME", paramWireTransferPayee.B6, this.B6, paramString2);
    paramHistoryTracker.detectChange(paramString1, "PAYEENICKNAME", paramWireTransferPayee.B9, this.B9, paramString2);
    paramHistoryTracker.detectChange(paramString1, "CONTACT_NAME", paramWireTransferPayee.B8, this.B8, paramString2);
    paramHistoryTracker.detectChange(paramString1, "PAYEE_SCOPE", paramWireTransferPayee.BP, this.BP, paramString2);
    paramHistoryTracker.detectChange(paramString1, "PAYEE_DESTINATION", paramWireTransferPayee.BT, this.BT, paramString2);
    paramHistoryTracker.detectChange(paramString1, "MEMO", paramWireTransferPayee.B5, this.B5, paramString2);
    this.BV.logChanges(paramHistoryTracker, paramString1 + "." + "DESTINATION_BANK", paramWireTransferPayee.getDestinationBank(), paramString2);
    if ((this.BX != null) && (this.BX.size() > 0))
    {
      this.BX.setFilter("All");
      for (int i = 0; i < this.BX.size(); i++)
      {
        WireTransferBank localWireTransferBank = (WireTransferBank)this.BX.get(i);
        if ("add".equals(localWireTransferBank.getAction())) {
          paramHistoryTracker.logCreate(paramString1, "INTERMEDIARY_BANKS", localWireTransferBank.getRoutingNumber(), paramString2);
        } else if ("del".equals(localWireTransferBank.getAction())) {
          paramHistoryTracker.detectChange(paramString1, "INTERMEDIARY_BANKS", localWireTransferBank.getRoutingNumber(), null, paramString2);
        }
      }
    }
    super.logChanges(paramHistoryTracker, paramString1, paramWireTransferPayee, paramString2);
  }
  
  public void logCreate(HistoryTracker paramHistoryTracker, String paramString)
  {
    logCreate(paramHistoryTracker, BEAN_NAME, paramString);
  }
  
  public void logCreate(HistoryTracker paramHistoryTracker, String paramString1, String paramString2)
  {
    paramHistoryTracker.logCreate(paramString1, "TRACKINGID", getTrackingID(), paramString2);
  }
  
  public void logDelete(HistoryTracker paramHistoryTracker, String paramString)
  {
    logDelete(paramHistoryTracker, BEAN_NAME, paramString);
  }
  
  public void logDelete(HistoryTracker paramHistoryTracker, String paramString1, String paramString2)
  {
    paramHistoryTracker.logDelete(paramString1, "TRACKINGID", getTrackingID(), paramString2);
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, WireTransferPayee paramWireTransferPayee, ILocalizable paramILocalizable)
  {
    logChanges(paramHistoryTracker, BEAN_NAME, paramWireTransferPayee, paramILocalizable);
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, String paramString, WireTransferPayee paramWireTransferPayee, ILocalizable paramILocalizable)
  {
    if (paramWireTransferPayee == null)
    {
      DebugLog.log("WARNING: Unable to record history for wire payee modification due to null old payee.");
      return;
    }
    paramHistoryTracker.detectChange(paramString, "SUBMITTED_BY", paramWireTransferPayee.BS, this.BS, paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "TOACCOUNTID", paramWireTransferPayee.B4, this.B4, paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "ACCOUNT_TYPE", paramWireTransferPayee.BR == null ? null : new LocalizableString("com.ffusion.beans.wiretransfers.resources", "AccountType_" + paramWireTransferPayee.BR, null), this.BR == null ? null : new LocalizableString("com.ffusion.beans.wiretransfers.resources", "AccountType_" + this.BR, null), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "PAYEENAME", paramWireTransferPayee.B6, this.B6, paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "PAYEENICKNAME", paramWireTransferPayee.B9, this.B9, paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "CONTACT_NAME", paramWireTransferPayee.B8, this.B8, paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "PAYEE_SCOPE", paramWireTransferPayee.BP == null ? null : new LocalizableString("com.ffusion.beans.wiretransfers.resources", "WirePayeeScope" + paramWireTransferPayee.BP, null), this.BP == null ? null : new LocalizableString("com.ffusion.beans.wiretransfers.resources", "WirePayeeScope" + this.BP, null), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "PAYEE_DESTINATION", paramWireTransferPayee.BT == null ? null : new LocalizableString("com.ffusion.beans.wiretransfers.resources", "WirePayeeDestination" + paramWireTransferPayee.BT, null), this.BT == null ? null : new LocalizableString("com.ffusion.beans.wiretransfers.resources", "WirePayeeDestination" + this.BT, null), paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "MEMO", paramWireTransferPayee.B5, this.B5, paramILocalizable);
    this.BV.logChanges(paramHistoryTracker, paramString + "." + "DESTINATION_BANK", paramWireTransferPayee.getDestinationBank(), paramILocalizable);
    if ((this.BX != null) && (this.BX.size() > 0))
    {
      this.BX.setFilter("All");
      for (int i = 0; i < this.BX.size(); i++)
      {
        WireTransferBank localWireTransferBank = (WireTransferBank)this.BX.get(i);
        if ("add".equals(localWireTransferBank.getAction())) {
          paramHistoryTracker.logCreate(paramString, "INTERMEDIARY_BANKS", localWireTransferBank.getRoutingNumber(), paramILocalizable);
        } else if ("del".equals(localWireTransferBank.getAction())) {
          paramHistoryTracker.detectChange(paramString, "INTERMEDIARY_BANKS", localWireTransferBank.getRoutingNumber(), null, paramILocalizable);
        }
      }
    }
    super.logChanges(paramHistoryTracker, paramString, paramWireTransferPayee, paramILocalizable);
  }
  
  public void logCreate(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    logCreate(paramHistoryTracker, BEAN_NAME, paramILocalizable);
  }
  
  public void logCreate(HistoryTracker paramHistoryTracker, String paramString, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.logCreate(paramString, "TRACKINGID", getTrackingID(), paramILocalizable);
  }
  
  public void logDelete(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    logDelete(paramHistoryTracker, BEAN_NAME, paramILocalizable);
  }
  
  public void logDelete(HistoryTracker paramHistoryTracker, String paramString, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.logDelete(paramString, "TRACKINGID", getTrackingID(), paramILocalizable);
  }
  
  public AuditLogRecord getAuditRecord(SecureUser paramSecureUser, String paramString1, int paramInt, String paramString2)
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
    AuditLogRecord localAuditLogRecord = new AuditLogRecord(str1, i, str2, str3, new LocalizableString("dummy", paramString1, null), getTrackingID(), paramInt, paramSecureUser.getBusinessID(), null, null, getID(), paramString2, getAccountNum(), null, null, null, AuditLogUtil.getModuleFromTranType(paramInt));
    return localAuditLogRecord;
  }
  
  protected static boolean isNullOrEmpty(String paramString)
  {
    return (paramString == null) || (paramString.length() == 0);
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
      Object localObject;
      if (paramString.equalsIgnoreCase("DESTINATION_BANK"))
      {
        localObject = new WireTransferBank();
        WireTransferPayee.this.setDestinationBank((WireTransferBank)localObject);
        ((WireTransferBank)localObject).continueXMLParsing(getHandler());
      }
      else if (paramString.equalsIgnoreCase("INTERMEDIARY_BANKS"))
      {
        localObject = new WireTransferBanks();
        WireTransferPayee.this.setIntermediaryBanks((WireTransferBanks)localObject);
        ((WireTransferBanks)localObject).continueXMLParsing(getHandler());
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.wiretransfers.WireTransferPayee
 * JD-Core Version:    0.7.0.1
 */