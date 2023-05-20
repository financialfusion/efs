package com.ffusion.beans.ach;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.user.User;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.efs.adapters.profile.CustomerAdapter;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.RoutingNumberUtil;
import com.ffusion.util.Strings;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.LocalizableDate;
import com.ffusion.util.beans.LocalizableString;
import java.io.Serializable;
import java.text.MessageFormat;

public class ACHPayee
  extends ExtendABean
  implements ACHAccountTypes, ACHClassCode, Comparable, Serializable
{
  private static final String BEAN_NAME = ACHPayee.class.getName();
  public static final String RESOURCE_BUNDLE = "com.ffusion.beans.ach.resources";
  public static final String PROPERTY_ID = "ID";
  public static final String PROPERTY_NICK_NAME = "NickName";
  public static final String PROPERTY_NAME = "Name";
  public static final String PROPERTY_ACCOUNT_NUMBER = "AccountNumber";
  public static final String PROPERTY_ACCOUNT_TYPE = "AccountType";
  public static final String PROPERTY_BANK_NAME = "BankName";
  public static final String PROPERTY_ROUTING_NUMBER = "RoutingNumber";
  public static final String PROPERTY_DESCRIPTION = "Description";
  public static final String PROPERTY_USER_ACCOUNT_NUMBER = "UserAccountNumber";
  public static final String PRENOTE_DEMAND_CREDIT = "Credit";
  public static final String PRENOTE_DEMAND_DEBIT = "Debit";
  public static final String PRENOTE_DEMAND_BOTH = "Both";
  public static final String KEY_ACH_ACCOUNT_TYPE_PREFIX = "ACHAccountType";
  public static final String KEY_ACH_PAYEE_GROUP_PREFIX = "ACHPayeeGroup";
  public static final String KEY_ACH_PRENOTE_STATUS_PREFIX = "ACHPrenoteStatus";
  public static final String KEY_ACH_SCOPE_PREFIX = "ACHScope";
  protected String id;
  protected String nickName;
  protected String name;
  protected String accountNumber;
  protected int accountType = 1;
  protected String bankName;
  protected String routingNumber;
  protected String description;
  protected String userAccountNumber;
  protected String prenoteStatus = "PRENOTE_NOT_REQUIRED";
  protected DateTime prenoteSubmitDate;
  protected DateTime prenoteMatureDate;
  protected String companyID;
  protected String trackingID;
  protected String submittedBy;
  protected String scope = "ACHCompany";
  protected int payeeGroup = 2;
  protected boolean securePayee = false;
  protected boolean displayAsSecurePayee = false;
  protected boolean addToList = false;
  protected String prenoteDemand;
  public static final int ACH_PAYEE_GROUP_USER = 1;
  public static final int ACH_PAYEE_GROUP_COMPANY = 2;
  public static final int ACH_PAYEE_GROUP_BUSINESS = 3;
  
  public String getID()
  {
    return this.id;
  }
  
  public void setID(String paramString)
  {
    this.id = paramString;
  }
  
  public String getTrackingID()
  {
    return this.trackingID;
  }
  
  public void setTrackingID(String paramString)
  {
    this.trackingID = paramString;
  }
  
  public String getNickName()
  {
    return this.nickName;
  }
  
  public void setNickName(String paramString)
  {
    this.nickName = paramString;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public void setCompanyID(String paramString)
  {
    this.companyID = paramString;
  }
  
  public String getCompanyID()
  {
    return this.companyID;
  }
  
  public String getDescription()
  {
    return this.description;
  }
  
  public void setDescription(String paramString)
  {
    this.description = paramString;
  }
  
  public String getAccountType()
  {
    return ResourceUtil.getString("ACHAccountType" + this.accountType, "com.ffusion.beans.ach.resources", this.locale);
  }
  
  public int getAccountTypeValue()
  {
    return this.accountType;
  }
  
  public void setAccountType(String paramString)
  {
    if (paramString == null) {
      return;
    }
    for (int i = 1; i <= 4; i++)
    {
      String str = ResourceUtil.getString("ACHAccountType" + i, "com.ffusion.beans.ach.resources", this.locale);
      if ((str.equalsIgnoreCase(paramString)) || (paramString.equals("" + i)))
      {
        setAccountType(i);
        break;
      }
    }
  }
  
  public void setAccountType(int paramInt)
  {
    this.accountType = paramInt;
  }
  
  public String getAccountNumber()
  {
    return this.accountNumber;
  }
  
  public String getAccountNumberNoSpaces()
  {
    String str = this.accountNumber;
    if (str != null)
    {
      str = str.trim();
      str = Strings.removeChars(str, ' ');
    }
    return str;
  }
  
  public void setAccountNumber(String paramString)
  {
    this.accountNumber = paramString;
  }
  
  public String getBankName()
  {
    return this.bankName;
  }
  
  public void setBankName(String paramString)
  {
    this.bankName = paramString;
  }
  
  public String getRoutingNumber()
  {
    return this.routingNumber;
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.prenoteMatureDate != null) {
      this.prenoteMatureDate.setFormat(this.datetype);
    }
    if (this.prenoteSubmitDate != null) {
      this.prenoteSubmitDate.setFormat(this.datetype);
    }
  }
  
  public String getScope()
  {
    return this.scope;
  }
  
  public void setScope(String paramString)
  {
    if (paramString.equalsIgnoreCase("ACHCompany")) {
      this.scope = "ACHCompany";
    } else if (paramString.equalsIgnoreCase("ACHBatch")) {
      this.scope = "ACHBatch";
    } else if (paramString.equalsIgnoreCase("ACHTemplate")) {
      this.scope = "ACHTemplate";
    }
  }
  
  public String getPayeeGroup()
  {
    return ResourceUtil.getString("ACHPayeeGroup" + this.payeeGroup, "com.ffusion.beans.ach.resources", this.locale);
  }
  
  public int getPayeeGroupValue()
  {
    return this.payeeGroup;
  }
  
  public void setPayeeGroup(int paramInt)
  {
    this.payeeGroup = paramInt;
  }
  
  public void setPayeeGroup(String paramString)
  {
    for (int i = 1; i <= 3; i++)
    {
      String str = ResourceUtil.getString("ACHPayeeGroup" + i, "com.ffusion.beans.ach.resources", this.locale);
      if ((str.equalsIgnoreCase(paramString)) || (paramString.equals(String.valueOf(i))))
      {
        setPayeeGroup(i);
        break;
      }
    }
  }
  
  public void setSecurePayee(boolean paramBoolean)
  {
    this.securePayee = paramBoolean;
  }
  
  public void setSecurePayee(String paramString)
  {
    this.securePayee = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getSecurePayee()
  {
    return "" + this.securePayee;
  }
  
  public String getSecurePayeeMask()
  {
    return ResourceUtil.getString("ACHSecurePayeeMask", "com.ffusion.beans.ach.resources", this.locale);
  }
  
  public boolean getSecurePayeeValue()
  {
    return this.securePayee;
  }
  
  public void setDisplayAsSecurePayee(boolean paramBoolean)
  {
    this.displayAsSecurePayee = paramBoolean;
  }
  
  public void setDisplayAsSecurePayee(String paramString)
  {
    this.displayAsSecurePayee = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getDisplayAsSecurePayee()
  {
    return "" + this.displayAsSecurePayee;
  }
  
  public boolean getDisplayAsSecurePayeeValue()
  {
    return this.displayAsSecurePayee;
  }
  
  public void setAddToList(boolean paramBoolean)
  {
    this.addToList = paramBoolean;
  }
  
  public void setAddToList(String paramString)
  {
    this.addToList = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getAddToList()
  {
    return "" + this.addToList;
  }
  
  public boolean getAddToListValue()
  {
    return this.addToList;
  }
  
  public String getPrenoteStatusDisplay()
  {
    return ResourceUtil.getString(this.prenoteStatus, "com.ffusion.beans.ach.resources", this.locale);
  }
  
  public String getPrenoteStatus()
  {
    return this.prenoteStatus;
  }
  
  public void setPrenoteStatus(String paramString)
  {
    this.prenoteStatus = paramString;
  }
  
  public DateTime getPrenoteMatureDateValue()
  {
    return this.prenoteMatureDate;
  }
  
  public String getPrenoteMatureDate()
  {
    if (this.prenoteMatureDate != null) {
      return this.prenoteMatureDate.toString();
    }
    return null;
  }
  
  public void setPrenoteMatureDate(String paramString)
  {
    try
    {
      if (this.prenoteMatureDate == null) {
        this.prenoteMatureDate = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.prenoteMatureDate.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      this.prenoteMatureDate = null;
    }
  }
  
  public void setPrenoteMatureDate(DateTime paramDateTime)
  {
    this.prenoteMatureDate = paramDateTime;
  }
  
  public DateTime getPrenoteSubmitDateValue()
  {
    return this.prenoteSubmitDate;
  }
  
  public String getPrenoteSubmitDate()
  {
    if (this.prenoteSubmitDate != null) {
      return this.prenoteSubmitDate.toString();
    }
    return null;
  }
  
  public void setPrenoteSubmitDate(String paramString)
  {
    try
    {
      if (this.prenoteSubmitDate == null) {
        this.prenoteSubmitDate = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.prenoteSubmitDate.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      this.prenoteSubmitDate = null;
    }
  }
  
  public void setPrenoteSubmitDate(DateTime paramDateTime)
  {
    this.prenoteSubmitDate = paramDateTime;
  }
  
  public String getPrenoteDemand()
  {
    return this.prenoteDemand;
  }
  
  public void setPrenoteDemand(String paramString)
  {
    if (paramString != null) {
      if (paramString.equalsIgnoreCase("Credit")) {
        this.prenoteDemand = "Credit";
      } else if (paramString.equalsIgnoreCase("Debit")) {
        this.prenoteDemand = "Debit";
      } else if (paramString.equalsIgnoreCase("Both")) {
        this.prenoteDemand = "Both";
      }
    }
  }
  
  public void setRoutingNumber(String paramString)
  {
    this.routingNumber = paramString;
  }
  
  public String getUserAccountNumber()
  {
    return this.userAccountNumber;
  }
  
  public void setUserAccountNumber(String paramString)
  {
    this.userAccountNumber = paramString;
  }
  
  public String getSubmittedBy()
  {
    return this.submittedBy;
  }
  
  public void setSubmittedBy(String paramString)
  {
    this.submittedBy = paramString;
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("ID"))
    {
      setID(paramString2);
    }
    else if (paramString1.equals("TRACKINGID"))
    {
      setTrackingID(paramString2);
    }
    else if (paramString1.equals("NICKNAME"))
    {
      setNickName(paramString2);
    }
    else if (paramString1.equals("NAME"))
    {
      setName(paramString2);
    }
    else if (paramString1.equals("DESCRIPTION"))
    {
      setDescription(paramString2);
    }
    else if (paramString1.equals("ACCOUNTNUMBER"))
    {
      setAccountNumber(paramString2);
    }
    else if (paramString1.equals("ACCOUNTTYPE"))
    {
      setAccountType(paramString2);
    }
    else if (paramString1.equals("BANKNAME"))
    {
      setBankName(paramString2);
    }
    else if (paramString1.equals("ROUTINGNUM"))
    {
      setRoutingNumber(paramString2);
    }
    else if (paramString1.equals("SECUREPAYEE"))
    {
      setSecurePayee(paramString2);
    }
    else if (paramString1.equals("USERACCOUNTNUMBER"))
    {
      setUserAccountNumber(paramString2);
    }
    else if (paramString1.equals("PRENOTESTATUS"))
    {
      setPrenoteStatus(paramString2);
    }
    else if (paramString1.equalsIgnoreCase("PRENOTESUBMITDATE"))
    {
      if (this.prenoteSubmitDate == null)
      {
        this.prenoteSubmitDate = new DateTime(this.locale);
        this.prenoteSubmitDate.setFormat(this.datetype);
      }
      this.prenoteSubmitDate.fromXMLFormat(paramString2);
    }
    else if (paramString1.equalsIgnoreCase("PRENOTEMATUREDATE"))
    {
      if (this.prenoteMatureDate == null)
      {
        this.prenoteMatureDate = new DateTime(this.locale);
        this.prenoteMatureDate.setFormat(this.datetype);
      }
      this.prenoteMatureDate.fromXMLFormat(paramString2);
    }
    else if (paramString1.equals("SCOPE"))
    {
      setScope(paramString2);
    }
    else if (paramString1.equals("PAYEEGROUP"))
    {
      setPayeeGroup(paramString2);
    }
    else if (paramString1.equals("COMPANYID"))
    {
      setCompanyID(paramString2);
    }
    else if (paramString1.equals("SUBMITTED_BY"))
    {
      setSubmittedBy(paramString2);
    }
    else if (paramString1.equals("PRENOTEDEMAND"))
    {
      setPrenoteDemand(paramString2);
    }
    else
    {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  public void set(ACHPayee paramACHPayee)
  {
    if ((this == paramACHPayee) || (paramACHPayee == null)) {
      return;
    }
    setID(paramACHPayee.getID());
    setTrackingID(paramACHPayee.getTrackingID());
    setNickName(paramACHPayee.getNickName());
    setName(paramACHPayee.getName());
    setDescription(paramACHPayee.getDescription());
    setAccountNumber(paramACHPayee.getAccountNumber());
    setAccountType(paramACHPayee.getAccountTypeValue());
    setBankName(paramACHPayee.getBankName());
    setCompanyID(paramACHPayee.getCompanyID());
    setRoutingNumber(paramACHPayee.getRoutingNumber());
    setUserAccountNumber(paramACHPayee.getUserAccountNumber());
    setPrenoteStatus(paramACHPayee.getPrenoteStatus());
    setPrenoteDemand(paramACHPayee.getPrenoteDemand());
    if (paramACHPayee.getPrenoteSubmitDateValue() != null) {
      this.prenoteSubmitDate = ((DateTime)paramACHPayee.getPrenoteSubmitDateValue().clone());
    } else {
      this.prenoteSubmitDate = null;
    }
    if (paramACHPayee.getPrenoteMatureDateValue() != null) {
      this.prenoteMatureDate = ((DateTime)paramACHPayee.getPrenoteMatureDateValue().clone());
    } else {
      this.prenoteMatureDate = null;
    }
    setScope(paramACHPayee.getScope());
    setPayeeGroup(paramACHPayee.getPayeeGroup());
    setSecurePayee(paramACHPayee.getSecurePayee());
    setAddToList(paramACHPayee.getAddToList());
    setSubmittedBy(paramACHPayee.getSubmittedBy());
    super.set(paramACHPayee);
  }
  
  public void merge(ACHPayee paramACHPayee)
  {
    String str = paramACHPayee.getID();
    if (str != null) {
      setID(str);
    }
    str = paramACHPayee.getNickName();
    if (str != null) {
      setNickName(str);
    }
    str = paramACHPayee.getName();
    if (str != null) {
      setName(str);
    }
    str = paramACHPayee.getAccountNumber();
    if (str != null) {
      setAccountNumber(str);
    }
    int i = paramACHPayee.getAccountTypeValue();
    if (i != 0) {
      setAccountType(i);
    }
    str = paramACHPayee.getBankName();
    if (str != null) {
      setBankName(str);
    }
    str = paramACHPayee.getRoutingNumber();
    if (str != null) {
      setRoutingNumber(str);
    }
    str = paramACHPayee.getDescription();
    if (str != null) {
      setDescription(str);
    }
    str = paramACHPayee.getUserAccountNumber();
    if (str != null) {
      setUserAccountNumber(str);
    }
    str = paramACHPayee.getPrenoteStatus();
    if (str != null) {
      setPrenoteStatus(str);
    }
    str = paramACHPayee.getPrenoteSubmitDate();
    if (str != null) {
      setPrenoteSubmitDate(str);
    }
    str = paramACHPayee.getPrenoteMatureDate();
    if (str != null) {
      setPrenoteMatureDate(str);
    }
    str = paramACHPayee.getPrenoteDemand();
    if (str != null) {
      setPrenoteDemand(str);
    }
    str = paramACHPayee.getScope();
    if (str != null) {
      setScope(str);
    }
    str = paramACHPayee.getPayeeGroup();
    if (str != null) {
      setPayeeGroup(str);
    }
    str = paramACHPayee.getSecurePayee();
    if (str != null) {
      setSecurePayee(str);
    }
    str = paramACHPayee.getAddToList();
    if (str != null) {
      setAddToList(str);
    }
    str = paramACHPayee.getCompanyID();
    if (str != null) {
      setCompanyID(str);
    }
    str = paramACHPayee.getSubmittedBy();
    if (str != null) {
      setSubmittedBy(str);
    }
  }
  
  public String getImportError(String paramString)
  {
    return ResourceUtil.getString("ACHPayeeError_" + paramString, "com.ffusion.beans.ach.resources", this.locale);
  }
  
  public String getImportError(String paramString, Object[] paramArrayOfObject)
  {
    if (paramArrayOfObject == null) {
      return ResourceUtil.getString("ACHPayeeError_" + paramString, "com.ffusion.beans.ach.resources", DEFAULT_LOCALE);
    }
    return MessageFormat.format(ResourceUtil.getString("ACHPayeeError_" + paramString, "com.ffusion.beans.ach.resources", DEFAULT_LOCALE), paramArrayOfObject);
  }
  
  public void validate(ACHEntry paramACHEntry, int paramInt, boolean paramBoolean)
  {
    int i;
    switch (paramInt)
    {
    case 1: 
    case 5: 
    case 6: 
    case 9: 
    case 10: 
    case 12: 
    case 17: 
      i = 22;
      break;
    case 2: 
      i = 15;
      break;
    case 13: 
      i = 16;
      break;
    case 18: 
      i = 0;
      break;
    case 3: 
    case 4: 
    case 7: 
    case 8: 
    case 11: 
    case 14: 
    case 15: 
    case 16: 
    default: 
      i = 0;
    }
    if (this.name == null)
    {
      if ((paramBoolean) && (i > 0)) {
        paramACHEntry.addValidationError(getImportError("NameNotInitialized"), getImportError("ValueNotSet"), "NAME");
      }
    }
    else if ((this.name.length() > i) && (i > 0))
    {
      Object[] arrayOfObject1 = { this.name, this.name.substring(0, i) };
      Object[] arrayOfObject2 = { String.valueOf(this.name.length()) };
      paramACHEntry.addValidationError(getImportError("InvalidName", arrayOfObject1), getImportError("FieldLimited" + i + "Chars", arrayOfObject2), "NAME");
      this.name = this.name.substring(0, i);
    }
    else if (this.name.trim().length() == 0)
    {
      paramACHEntry.addValidationError(getImportError("FieldIsRequired"), getImportError("ParticipantNameRequired"), "NAME");
    }
    if ((paramBoolean) && (this.accountType == 0)) {
      paramACHEntry.addValidationError(getImportError("AccountTypeNotInitialized"), getImportError("ValueNotSet"), "ACCOUNTTYPE");
    }
    Object localObject;
    if ((this.routingNumber == null) || (this.routingNumber.length() == 0))
    {
      if (paramBoolean) {
        paramACHEntry.addValidationError(getImportError("RoutingNumberNotInitialized"), getImportError("ValueNotSet"), "ROUTINGNUMBER");
      }
    }
    else
    {
      int j = this.routingNumber.length();
      for (int m = 0; m < j; m++) {
        if (!Character.isDigit(this.routingNumber.charAt(m)))
        {
          localObject = new Object[] { this.routingNumber };
          paramACHEntry.addValidationError(getImportError("InvalidRoutingNumber", (Object[])localObject), getImportError("NumericCharacters"), "ROUTINGNUMBER");
          break;
        }
      }
      if (m == j) {
        if ((j != 8) && (j != 9))
        {
          localObject = new Object[] { this.routingNumber };
          paramACHEntry.addValidationError(getImportError("InvalidRoutingNumber", (Object[])localObject), getImportError("EightOrNine"), "ROUTINGNUMBER");
        }
        else
        {
          localObject = this.routingNumber;
          if (j == 8) {
            localObject = (String)localObject + RoutingNumberUtil.getRoutingNumber_CheckDigit((String)localObject);
          }
          if (!RoutingNumberUtil.isValidRoutingNumber((String)localObject, true))
          {
            Object[] arrayOfObject4 = { this.routingNumber };
            Object[] arrayOfObject5 = { String.valueOf(((String)localObject).charAt(8)), RoutingNumberUtil.getRoutingNumber_CheckDigit(((String)localObject).substring(0, 8)) };
            paramACHEntry.addValidationError(getImportError("InvalidRoutingNumber", arrayOfObject4), getImportError("CheckDigit", arrayOfObject5), "ROUTINGNUMBER");
          }
        }
      }
    }
    Object[] arrayOfObject3;
    if (this.accountNumber == null)
    {
      if (paramBoolean) {
        paramACHEntry.addValidationError(getImportError("AccountNumberNotInitialized"), getImportError("ValueNotSet"), "ACCOUNTNUMBER");
      }
    }
    else if (getAccountNumberNoSpaces().length() > 17)
    {
      String str = getAccountNumberNoSpaces();
      this.accountNumber = str.substring(0, 17);
      arrayOfObject3 = new Object[] { str, this.accountNumber };
      paramACHEntry.addValidationError(getImportError("InvalidAccountNumber", arrayOfObject3), getImportError("InvalidAccountNumberLimit"), "ACCOUNTNUMBER");
    }
    else if (getAccountNumberNoSpaces().length() == 0)
    {
      paramACHEntry.addValidationError(getImportError("FieldIsRequired"), getImportError("AccountNumberRequired"), "ACCOUNTNUMBER");
    }
    if ((this.accountNumber != null) && (!Strings.isValidAccountNumber(getAccountNumberNoSpaces(), this.locale))) {
      paramACHEntry.addValidationError(getImportError("InvalidAccountNumberChars"), getImportError("AccountNumberRequired"), "ACCOUNTNUMBER");
    }
    int k = 0;
    switch (paramInt)
    {
    case 2: 
      i = 22;
      k = 1;
      break;
    case 6: 
    case 9: 
    case 10: 
      i = 15;
      break;
    case 13: 
      i = 15;
      break;
    case 1: 
    case 5: 
    case 12: 
    case 17: 
    case 18: 
      i = 0;
      break;
    case 3: 
    case 4: 
    case 7: 
    case 8: 
    case 11: 
    case 14: 
    case 15: 
    case 16: 
    default: 
      i = 0;
    }
    if ((this.userAccountNumber == null) || (this.userAccountNumber.length() == 0))
    {
      if ((paramBoolean) && (k != 0)) {
        paramACHEntry.addValidationError(getImportError("UserAccountNumberNotInitialized"), getImportError("ValueNotSet"), "USERACCOUNTNUMBER");
      }
    }
    else if ((i != 0) && ("ACHBatch".equalsIgnoreCase(getScope()))) {
      if (this.userAccountNumber.length() > i)
      {
        arrayOfObject3 = new Object[] { this.userAccountNumber };
        localObject = new Object[] { String.valueOf(this.userAccountNumber.length()) };
        paramACHEntry.addValidationError(getImportError("InvalidUserAccountNumber", arrayOfObject3), getImportError("FieldLimited" + i + "Chars", (Object[])localObject), "USERACCOUNTNUMBER");
      }
      else if ((this.userAccountNumber != null) && (!Strings.isValidACHAlphamericString(this.userAccountNumber)))
      {
        arrayOfObject3 = new Object[] { this.userAccountNumber };
        paramACHEntry.addValidationError(getImportError("InvalidUserAccountNumber", arrayOfObject3), getImportError("InvalidUserAccountNumberChars"), "USERACCOUNTNUMBER");
      }
    }
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    ACHPayee localACHPayee = (ACHPayee)paramObject;
    int i;
    if (this == localACHPayee)
    {
      i = 0;
    }
    else
    {
      i = this.name.compareTo(localACHPayee.getName());
      if (i == 0)
      {
        if ((this.nickName != null) && (localACHPayee.getNickName() != null)) {
          i = this.nickName.compareTo(localACHPayee.getNickName());
        }
        if ((i == 0) && (this.userAccountNumber != null) && (localACHPayee.getUserAccountNumber() != null)) {
          i = this.userAccountNumber.compareTo(localACHPayee.getUserAccountNumber());
        }
        if (i == 0) {
          i = this.id.compareTo(localACHPayee.getID());
        }
      }
    }
    return i;
  }
  
  public int compare(Object paramObject, String paramString)
  {
    ACHPayee localACHPayee = (ACHPayee)paramObject;
    int i = 1;
    if ((paramString.equals("ID")) && (getID() != null) && (localACHPayee.getID() != null)) {
      i = numStringCompare(getID(), localACHPayee.getID());
    } else if ((paramString.equals("NICKNAME")) && (getNickName() != null) && (localACHPayee.getNickName() != null)) {
      i = getNickName().toUpperCase().compareTo(localACHPayee.getNickName().toUpperCase());
    } else if ((paramString.equals("NAME")) && (getName() != null) && (localACHPayee.getName() != null)) {
      i = getSecureDisplayValue("NAME").toUpperCase().compareTo(localACHPayee.getSecureDisplayValue("NAME").toUpperCase());
    } else if ((paramString.equals("DESCRIPTION")) && (getDescription() != null) && (localACHPayee.getDescription() != null)) {
      i = getDescription().compareTo(localACHPayee.getDescription());
    } else if ((paramString.equals("ACCOUNTNUMBER")) && (getAccountNumber() != null) && (localACHPayee.getAccountNumber() != null)) {
      i = numStringCompare(getSecureDisplayValue("ACCOUNTNUMBER"), localACHPayee.getSecureDisplayValue("ACCOUNTNUMBER"));
    } else if ((paramString.equals("USERACCOUNTNUMBER")) && (getUserAccountNumber() != null) && (localACHPayee.getUserAccountNumber() != null)) {
      i = numStringCompare(getUserAccountNumber(), localACHPayee.getUserAccountNumber());
    } else if (paramString.equals("ACCOUNTTYPE")) {
      i = getAccountTypeValue() - localACHPayee.getAccountTypeValue();
    } else if ((paramString.equals("COMPANYID")) && (getCompanyID() != null) && (localACHPayee.getCompanyID() != null)) {
      i = numStringCompare(getCompanyID(), localACHPayee.getCompanyID());
    } else if (paramString.equals("ACCOUNTTYPESTRING")) {
      i = getSecureDisplayValue("ACCOUNTTYPE").compareTo(localACHPayee.getSecureDisplayValue("ACCOUNTTYPE"));
    } else if ((paramString.equals("PRENOTESUBMITDATE")) && (this.prenoteSubmitDate != null) && (localACHPayee.getPrenoteSubmitDateValue() != null)) {
      i = getPrenoteSubmitDateValue().compare(localACHPayee.getPrenoteSubmitDateValue());
    } else if ((paramString.equals("PRENOTEMATUREDATE")) && (this.prenoteMatureDate != null) && (localACHPayee.getPrenoteMatureDateValue() != null)) {
      i = getPrenoteMatureDateValue().compare(localACHPayee.getPrenoteMatureDateValue());
    } else if ((paramString.equals("PRENOTEDEMAND")) && (getPrenoteDemand() != null) && (localACHPayee.getPrenoteDemand() != null)) {
      i = getPrenoteDemand().compareTo(localACHPayee.getPrenoteDemand());
    } else if ((paramString.equals("BANKNAME")) && (getBankName() != null) && (localACHPayee.getBankName() != null)) {
      i = getSecureDisplayValue("BANKNAME").toUpperCase().compareTo(localACHPayee.getSecureDisplayValue("BANKNAME").toUpperCase());
    } else if ((paramString.equals("SCOPE")) && (getScope() != null) && (localACHPayee.getScope() != null)) {
      i = getScope().compareTo(localACHPayee.getScope());
    } else if ((paramString.equals("PAYEEGROUP")) && (getPayeeGroup() != null) && (localACHPayee.getPayeeGroup() != null)) {
      i = getPayeeGroup().compareTo(localACHPayee.getPayeeGroup());
    } else if ((paramString.equals("ROUTINGNUM")) && (getRoutingNumber() != null) && (localACHPayee.getRoutingNumber() != null)) {
      i = numStringCompare(getSecureDisplayValue("ROUTINGNUM"), localACHPayee.getSecureDisplayValue("ROUTINGNUM"));
    } else if ((paramString.equals("SUBMITTED_BY")) && (getSubmittedBy() != null) && (localACHPayee.getSubmittedBy() != null)) {
      i = getSubmittedBy().compareTo(localACHPayee.getSubmittedBy());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public String getSecureDisplayValue(String paramString)
  {
    if ((this.displayAsSecurePayee) && ((paramString.equals("NAME")) || (paramString.equals("ACCOUNTNUMBER")) || (paramString.startsWith("ACCOUNTTYPE")) || (paramString.equals("BANKNAME")) || (paramString.equals("ROUTINGNUM")))) {
      return getSecurePayeeMask();
    }
    if (paramString.equals("NAME")) {
      return getName();
    }
    if (paramString.equals("ACCOUNTNUMBER")) {
      return getAccountNumber();
    }
    if (paramString.startsWith("ACCOUNTTYPE")) {
      return getAccountType();
    }
    if (paramString.equals("BANKNAME")) {
      return getBankName();
    }
    if (paramString.equals("ROUTINGNUM")) {
      return getRoutingNumber();
    }
    return "";
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1.equals("ID")) && (getID() != null)) {
      return isFilterable(getID(), paramString2, paramString3);
    }
    if ((paramString1.equals("NICKNAME")) && (getNickName() != null)) {
      return isFilterable(getNickName(), paramString2, paramString3);
    }
    if ((paramString1.equals("NAME")) && (getName() != null)) {
      return isFilterable(getSecureDisplayValue("NAME"), paramString2, paramString3);
    }
    if ((paramString1.equals("DESCRIPTION")) && (getDescription() != null)) {
      return isFilterable(getDescription(), paramString2, paramString3);
    }
    if ((paramString1.equals("ACCOUNTNUMBER")) && (getAccountNumber() != null)) {
      return isFilterable(getSecureDisplayValue("ACCOUNTNUMBER"), paramString2, paramString3);
    }
    if ((paramString1.equals("USERACCOUNTNUMBER")) && (getUserAccountNumber() != null)) {
      return isFilterable(getUserAccountNumber(), paramString2, paramString3);
    }
    if ((paramString1.equals("COMPANYID")) && (getCompanyID() != null)) {
      return isFilterable(getCompanyID(), paramString2, paramString3);
    }
    if ((paramString1.equals("ACCOUNTTYPE")) && (getAccountType() != null)) {
      return isFilterable(getSecureDisplayValue("ACCOUNTTYPE"), paramString2, paramString3);
    }
    if ((paramString1.equals("BANKNAME")) && (getBankName() != null)) {
      return isFilterable(getSecureDisplayValue("BANKNAME"), paramString2, paramString3);
    }
    if ((paramString1.equals("ROUTINGNUM")) && (getRoutingNumber() != null)) {
      return isFilterable(getSecureDisplayValue("ROUTINGNUM"), paramString2, paramString3);
    }
    if ((paramString1.equals("SUBMITTED_BY")) && (getSubmittedBy() != null)) {
      return isFilterable(getSubmittedBy(), paramString2, paramString3);
    }
    if ((paramString1.equals("SCOPE")) && (getScope() != null)) {
      return isFilterable(getScope(), paramString2, paramString3);
    }
    if ((paramString1.equals("PRENOTEDEMAND")) && (getPrenoteDemand() != null)) {
      return isFilterable(getPrenoteDemand(), paramString2, paramString3);
    }
    if ((paramString1.equals("PAYEEGROUP")) && (getPayeeGroup() != null)) {
      return isFilterable(getPayeeGroup(), paramString2, paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (!(paramObject instanceof ACHPayee)) {
      return false;
    }
    ACHPayee localACHPayee = (ACHPayee)paramObject;
    if ((getID() != null) && (!getID().equals(localACHPayee.getID()))) {
      return false;
    }
    if ((getName() != null) && (!getName().equals(localACHPayee.getName()))) {
      return false;
    }
    if ((getNickName() != null) && (!getNickName().equals(localACHPayee.getNickName()))) {
      return false;
    }
    if ((getAccountNumber() != null) && (!getAccountNumber().equals(localACHPayee.getAccountNumber()))) {
      return false;
    }
    if (localACHPayee.getAccountTypeValue() != getAccountTypeValue()) {
      return false;
    }
    if ((getBankName() != null) && (!getBankName().equals(localACHPayee.getBankName()))) {
      return false;
    }
    if ((getRoutingNumber() != null) && (!getRoutingNumber().equals(localACHPayee.getRoutingNumber()))) {
      return false;
    }
    if ((getDescription() != null) && (!getDescription().equals(localACHPayee.getDescription()))) {
      return false;
    }
    if ((getPrenoteStatus() != null) && (!getPrenoteStatus().equals(localACHPayee.getPrenoteStatus()))) {
      return false;
    }
    if ((getCompanyID() != null) && (!getCompanyID().equals(localACHPayee.getCompanyID()))) {
      return false;
    }
    if ((getScope() != null) && (!getScope().equals(localACHPayee.getScope()))) {
      return false;
    }
    if ((getPrenoteDemand() != null) && (!getPrenoteDemand().equals(localACHPayee.getPrenoteDemand()))) {
      return false;
    }
    if ((getSubmittedBy() != null) && (!getSubmittedBy().equals(localACHPayee.getSubmittedBy()))) {
      return false;
    }
    if (localACHPayee.getSecurePayeeValue() != getSecurePayeeValue()) {
      return false;
    }
    if (localACHPayee.getPayeeGroupValue() != getPayeeGroupValue()) {
      return false;
    }
    if ((getUserAccountNumber() != null) && (!getUserAccountNumber().equals(localACHPayee.getUserAccountNumber()))) {
      return false;
    }
    return super.equals(paramObject);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "ACHPAYEE");
    XMLHandler.appendTag(localStringBuffer, "ID", getID());
    XMLHandler.appendTag(localStringBuffer, "TRACKINGID", getTrackingID());
    XMLHandler.appendTag(localStringBuffer, "NICKNAME", getNickName());
    XMLHandler.appendTag(localStringBuffer, "NAME", getName());
    XMLHandler.appendTag(localStringBuffer, "DESCRIPTION", getDescription());
    XMLHandler.appendTag(localStringBuffer, "ACCOUNTNUMBER", getAccountNumber());
    XMLHandler.appendTag(localStringBuffer, "USERACCOUNTNUMBER", getUserAccountNumber());
    XMLHandler.appendTag(localStringBuffer, "ACCOUNTTYPE", getAccountType());
    XMLHandler.appendTag(localStringBuffer, "BANKNAME", getBankName());
    XMLHandler.appendTag(localStringBuffer, "ROUTINGNUM", getRoutingNumber());
    XMLHandler.appendTag(localStringBuffer, "PRENOTESTATUS", getPrenoteStatus());
    XMLHandler.appendTag(localStringBuffer, "SUBMITTED_BY", getSubmittedBy());
    if (this.prenoteSubmitDate != null) {
      XMLHandler.appendTag(localStringBuffer, "PRENOTESUBMITDATE", this.prenoteSubmitDate.toXMLFormat());
    }
    if (this.prenoteMatureDate != null) {
      XMLHandler.appendTag(localStringBuffer, "PRENOTEMATUREDATE", this.prenoteMatureDate.toXMLFormat());
    }
    XMLHandler.appendTag(localStringBuffer, "COMPANYID", getCompanyID());
    XMLHandler.appendTag(localStringBuffer, "SCOPE", getScope());
    XMLHandler.appendTag(localStringBuffer, "PRENOTEDEMAND", getPrenoteDemand());
    XMLHandler.appendTag(localStringBuffer, "PAYEEGROUP", getPayeeGroup());
    XMLHandler.appendTag(localStringBuffer, "SECUREPAYEE", getSecurePayee());
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "ACHPAYEE");
    return localStringBuffer.toString();
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, ACHPayee paramACHPayee, String paramString)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "ID", paramACHPayee.getID(), getID(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "NICKNAME", paramACHPayee.getNickName(), getNickName(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "NAME", paramACHPayee.getName(), getName(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "DESCRIPTION", paramACHPayee.getDescription(), getDescription(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "ACCOUNTNUMBER", paramACHPayee.getAccountNumber(), getAccountNumber(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "USERACCOUNTNUMBER", paramACHPayee.getUserAccountNumber(), getUserAccountNumber(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "ACCOUNTTYPE", paramACHPayee.getAccountType(), getAccountType(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "BANKNAME", paramACHPayee.getBankName(), getBankName(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "ROUTINGNUM", paramACHPayee.getRoutingNumber(), getRoutingNumber(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "PRENOTESTATUS", paramACHPayee.getPrenoteStatus(), getPrenoteStatus(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "PRENOTESUBMITDATE", paramACHPayee.getPrenoteSubmitDate(), getPrenoteSubmitDate(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "PRENOTEMATUREDATE", paramACHPayee.getPrenoteMatureDate(), getPrenoteMatureDate(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "COMPANYID", paramACHPayee.getCompanyID(), getCompanyID(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "SCOPE", paramACHPayee.getScope(), getScope(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "PAYEEGROUP", paramACHPayee.getPayeeGroup(), getPayeeGroup(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "SECUREPAYEE", paramACHPayee.getSecurePayee(), getSecurePayee(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "TRACKINGID", paramACHPayee.getTrackingID(), getTrackingID(), paramString);
    String str1 = paramACHPayee.getSubmittedBy();
    String str2 = getSubmittedBy();
    if ((str2 != null) && (!str2.equals(str1)))
    {
      if ((str1 != null) && (str1.length() > 0)) {
        try
        {
          User localUser1 = CustomerAdapter.getUserById(Integer.parseInt(str1));
          if (localUser1 != null) {
            str1 = localUser1.getName();
          }
        }
        catch (Exception localException1) {}
      } else {
        str1 = "";
      }
      if (str2.length() > 0) {
        try
        {
          User localUser2 = CustomerAdapter.getUserById(Integer.parseInt(str2));
          if (localUser2 != null) {
            str2 = localUser2.getName();
          }
        }
        catch (Exception localException2) {}
      }
      paramHistoryTracker.detectChange(BEAN_NAME, "SUBMITTED_BY", str1, str2, paramString);
    }
    super.logChanges(paramHistoryTracker, paramACHPayee, paramString);
  }
  
  public void logCreation(HistoryTracker paramHistoryTracker, String paramString)
  {
    paramHistoryTracker.logCreate(BEAN_NAME, "TRACKINGID", getTrackingID(), paramString);
  }
  
  public void logDeletion(HistoryTracker paramHistoryTracker, String paramString)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "TRACKINGID", getTrackingID(), null, paramString);
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, ACHPayee paramACHPayee, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "ID", paramACHPayee.getID(), getID(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "NICKNAME", paramACHPayee.getNickName(), getNickName(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "NAME", paramACHPayee.getName(), getName(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "DESCRIPTION", paramACHPayee.getDescription(), getDescription(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "ACCOUNTNUMBER", paramACHPayee.getAccountNumber(), getAccountNumber(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "USERACCOUNTNUMBER", paramACHPayee.getUserAccountNumber(), getUserAccountNumber(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "ACCOUNTTYPE", new LocalizableString("com.ffusion.beans.ach.resources", "ACHAccountType" + paramACHPayee.getAccountTypeValue(), null), new LocalizableString("com.ffusion.beans.ach.resources", "ACHAccountType" + getAccountTypeValue(), null), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "BANKNAME", paramACHPayee.getBankName(), getBankName(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "ROUTINGNUM", paramACHPayee.getRoutingNumber(), getRoutingNumber(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "PRENOTESTATUS", paramACHPayee.getPrenoteStatus() == null ? null : new LocalizableString("com.ffusion.beans.ach.resources", "ACHPrenoteStatus" + paramACHPayee.getPrenoteStatus(), null), getPrenoteStatus() == null ? null : new LocalizableString("com.ffusion.beans.ach.resources", "ACHPrenoteStatus" + getPrenoteStatus(), null), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "PRENOTESUBMITDATE", paramACHPayee.getPrenoteSubmitDateValue() == null ? null : new LocalizableDate(paramACHPayee.getPrenoteSubmitDateValue(), false), getPrenoteSubmitDateValue() == null ? null : new LocalizableDate(getPrenoteSubmitDateValue(), false), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "PRENOTEMATUREDATE", paramACHPayee.getPrenoteMatureDateValue() == null ? null : new LocalizableDate(paramACHPayee.getPrenoteMatureDateValue(), false), getPrenoteMatureDateValue() == null ? null : new LocalizableDate(getPrenoteMatureDateValue(), false), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "COMPANYID", paramACHPayee.getCompanyID(), getCompanyID(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "SCOPE", paramACHPayee.getScope() == null ? null : new LocalizableString("com.ffusion.beans.ach.resources", "ACHScope" + paramACHPayee.getScope(), null), getScope() == null ? null : new LocalizableString("com.ffusion.beans.ach.resources", "ACHScope" + getScope(), null), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "PAYEEGROUP", new LocalizableString("com.ffusion.beans.ach.resources", "ACHPayeeGroup" + paramACHPayee.getPayeeGroupValue(), null), new LocalizableString("com.ffusion.beans.ach.resources", "ACHPayeeGroup" + getPayeeGroupValue(), null), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "SECUREPAYEE", paramACHPayee.getSecurePayeeValue(), getSecurePayeeValue(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "TRACKINGID", paramACHPayee.getTrackingID(), getTrackingID(), paramILocalizable);
    String str1 = paramACHPayee.getSubmittedBy();
    String str2 = getSubmittedBy();
    if ((str2 != null) && (!str2.equals(str1)))
    {
      if ((str1 != null) && (str1.length() > 0)) {
        try
        {
          User localUser1 = CustomerAdapter.getUserById(Integer.parseInt(str1));
          if (localUser1 != null) {
            str1 = localUser1.getName();
          }
        }
        catch (Exception localException1) {}
      } else {
        str1 = "";
      }
      if (str2.length() > 0) {
        try
        {
          User localUser2 = CustomerAdapter.getUserById(Integer.parseInt(str2));
          if (localUser2 != null) {
            str2 = localUser2.getName();
          }
        }
        catch (Exception localException2) {}
      }
      paramHistoryTracker.detectChange(BEAN_NAME, "SUBMITTED_BY", str1, str2, paramILocalizable);
    }
    super.logChanges(paramHistoryTracker, paramACHPayee, paramILocalizable);
  }
  
  public void logCreation(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.logCreate(BEAN_NAME, "TRACKINGID", getTrackingID(), paramILocalizable);
  }
  
  public void logDeletion(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "TRACKINGID", getTrackingID(), null, paramILocalizable);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.ach.ACHPayee
 * JD-Core Version:    0.7.0.1
 */