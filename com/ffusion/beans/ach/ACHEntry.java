package com.ffusion.beans.ach;

import com.ffusion.beans.Currency;
import com.ffusion.beans.FundsTransaction;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.DebugLog;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Locale;

public class ACHEntry
  extends FundsTransaction
  implements ACHAccountTypes, ACHClassCode, ACHStatus, Cloneable, Comparable, Serializable, XMLable
{
  private static final String BEAN_NAME = ACHEntry.class.getName();
  public static final String RESOURCE_BUNDLE = "com.ffusion.beans.ach.resources";
  public static final String ACH_ACTION_MODIFY = "MOD";
  public static final String ACH_ACTION_DELETE = "CAN";
  public static final String ACH_ACTION_ADD = "ADD";
  public static final String ACH_ACTION_PRENOTE = "PRENOTE";
  public static final String ACH_ACTION_REVERSE = "REVERSE";
  public static final String ACH_ACTION_ADDED = "ADDED";
  public static final String ACH_ACTION_MATCHED = "MATCHED";
  public static final String ACH_ACTION_UNMATCHED = "UNMATCHED";
  public static final String ENTRY_ADDED = "ENTRY_ADDED";
  public static final String ENTRY_CANCELED = "ENTRY_CANCELED";
  public static final String KEY_ACH_ACCOUNT_TYPES_PREFIX = "ACHAccountType";
  public static final String KEY_PAYMENT_CODES_PREFIX = "PaymentTypeCodes";
  protected String offsetAccountID;
  protected int offsetAccountType = 1;
  protected String offsetAccountBankID;
  protected String offsetAccountNumber;
  protected String offsetAccountName;
  protected ACHPayee achPayee;
  protected ACHAddendas addendas = new ACHAddendas();
  protected int status;
  protected boolean active = true;
  protected boolean canReverse;
  protected Boolean amountIsDebit = new Boolean(true);
  protected String checkSerialNumber;
  protected String discretionaryData;
  protected String recID;
  protected String terminalState;
  protected String terminalCity;
  protected String processControlField;
  protected String itemResearchNumber;
  protected String paymentTypeCode;
  protected String identificationNumber;
  protected String payeeID;
  protected Object bpwEntryObject;
  protected Object bpwBalancedEntryObject;
  protected boolean prenote = false;
  protected String serviceClassCode;
  protected String taxFormID;
  private short[] d6 = { 23, 28, 33, 38, 43, 48, 53 };
  private short[] d4 = { 20, 21, 22, 23, 24, 30, 31, 32, 33, 34, 41, 42, 43, 44, 51, 52, 53, 54 };
  private short[] d7 = { 20, 21, 22, 23, 24, 25, 26, 27, 28, 29 };
  private short[] d3 = { 30, 31, 32, 33, 34, 35, 36, 37, 38, 39 };
  private short[] d5 = { 41, 42, 43, 44, 46, 47, 48, 49 };
  private short[] d2 = { 51, 52, 53, 54, 55, 56 };
  
  public ACHEntry()
  {
    this.funds_type = 7;
  }
  
  public ACHEntry(String paramString)
  {
    super(paramString);
    this.funds_type = 7;
  }
  
  public ACHEntry(Locale paramLocale)
  {
    super(paramLocale);
    this.funds_type = 7;
  }
  
  public ACHAddendas getAddendas()
  {
    return this.addendas;
  }
  
  public String getAddendaString()
  {
    String str = "";
    if (this.addendas != null) {
      str = this.addendas.getAddendaString();
    }
    return str;
  }
  
  public void setAddendaString(String paramString)
  {
    if (this.addendas == null) {
      this.addendas = new ACHAddendas();
    }
    this.addendas.setAddendaString(paramString);
  }
  
  public int getAddendaCount()
  {
    return this.addendas != null ? this.addendas.size() : 0;
  }
  
  public void setAddendas(ACHAddendas paramACHAddendas)
  {
    this.addendas = paramACHAddendas;
  }
  
  public String getRecID()
  {
    return this.recID;
  }
  
  public void setRecID(String paramString)
  {
    this.recID = paramString;
  }
  
  public String getOffsetAccountType()
  {
    return ResourceUtil.getString("ACHAccountType" + this.offsetAccountType, "com.ffusion.beans.ach.resources", this.locale);
  }
  
  public int getOffsetAccountTypeValue()
  {
    return this.offsetAccountType;
  }
  
  public void setOffsetAccountType(String paramString)
  {
    for (int i = 1; i <= 4; i++)
    {
      String str = ResourceUtil.getString("ACHAccountType" + i, "com.ffusion.beans.ach.resources", this.locale);
      if (str.equalsIgnoreCase(paramString))
      {
        setOffsetAccountType(i);
        break;
      }
    }
  }
  
  public Object clone()
  {
    try
    {
      ACHEntry localACHEntry = (ACHEntry)super.clone();
      if (localACHEntry.getAchPayee() != null) {
        localACHEntry.setAchPayee((ACHPayee)localACHEntry.getAchPayee().clone());
      }
      if (localACHEntry.getAddendas() != null)
      {
        ACHAddendas localACHAddendas = new ACHAddendas();
        Iterator localIterator = localACHEntry.getAddendas().iterator();
        while (localIterator.hasNext())
        {
          ACHAddenda localACHAddenda = (ACHAddenda)((ACHAddenda)localIterator.next()).clone();
          localACHAddendas.add(localACHAddenda);
        }
        localACHEntry.setAddendas(localACHAddendas);
      }
      return localACHEntry;
    }
    catch (Exception localException) {}
    return null;
  }
  
  public void setOffsetAccountType(int paramInt)
  {
    this.offsetAccountType = paramInt;
  }
  
  public String getOffsetAccountID()
  {
    return this.offsetAccountID;
  }
  
  public void setOffsetAccountID(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0)) {
      this.offsetAccountID = paramString;
    }
  }
  
  public String getOffsetAccountBankID()
  {
    return this.offsetAccountBankID;
  }
  
  public void setOffsetAccountBankID(String paramString)
  {
    this.offsetAccountBankID = paramString;
  }
  
  public String getOffsetAccountNumber()
  {
    return this.offsetAccountNumber;
  }
  
  public void setOffsetAccountNumber(String paramString)
  {
    this.offsetAccountNumber = paramString;
  }
  
  public String getOffsetAccountName()
  {
    return this.offsetAccountName;
  }
  
  public void setOffsetAccountName(String paramString)
  {
    this.offsetAccountName = paramString;
  }
  
  public ACHPayee getAchPayee()
  {
    return this.achPayee;
  }
  
  public void setAchPayee(ACHPayee paramACHPayee)
  {
    this.achPayee = paramACHPayee;
    if (this.achPayee != null) {
      this.achPayee.setDateFormat(getDateFormat());
    }
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.achPayee != null) {
      this.achPayee.setDateFormat(paramString);
    }
  }
  
  public String getAchPayeeID()
  {
    if (this.achPayee == null) {
      return this.payeeID;
    }
    return this.achPayee.getID();
  }
  
  public void setAchPayeeID(String paramString)
  {
    this.payeeID = paramString;
  }
  
  public String getAchPayeeScope()
  {
    if (this.achPayee == null) {
      return null;
    }
    return this.achPayee.getScope();
  }
  
  public Object getBpwEntryObject()
  {
    return this.bpwEntryObject;
  }
  
  public void setBpwEntryObject(Object paramObject)
  {
    this.bpwEntryObject = paramObject;
  }
  
  public Object getBpwBalancedEntryObject()
  {
    return this.bpwBalancedEntryObject;
  }
  
  public void setBpwBalancedEntryObject(Object paramObject)
  {
    this.bpwBalancedEntryObject = paramObject;
  }
  
  public short getTransactionCodeValue(int paramInt)
  {
    boolean bool = false;
    double d = 0.0D;
    if (getAmountValue() != null) {
      d = getAmountValue().doubleValue();
    }
    if (((paramInt == 12) || (paramInt == 13)) && (d == 0.0D) && (!this.prenote) && (getAddendaString() != null) && (getAddendaString().length() > 0)) {
      bool = true;
    }
    return getTransactionCodeValue(bool, isAmountIsDebit(), this.achPayee == null ? 1 : this.achPayee.getAccountTypeValue(), this.prenote);
  }
  
  public static short getTransactionCodeValue(boolean paramBoolean1, boolean paramBoolean2, int paramInt, boolean paramBoolean3)
  {
    if (paramInt == 1)
    {
      if (paramBoolean3)
      {
        if (paramBoolean2) {
          return 28;
        }
        return 23;
      }
      if (paramBoolean1)
      {
        if (paramBoolean2) {
          return 29;
        }
        return 24;
      }
      if (paramBoolean2) {
        return 27;
      }
      return 22;
    }
    if (paramInt == 2)
    {
      if (paramBoolean3)
      {
        if (paramBoolean2) {
          return 38;
        }
        return 33;
      }
      if (paramBoolean1)
      {
        if (paramBoolean2) {
          return 39;
        }
        return 34;
      }
      if (paramBoolean2) {
        return 37;
      }
      return 32;
    }
    if (paramInt == 4)
    {
      if (paramBoolean3)
      {
        if (paramBoolean2) {
          return 48;
        }
        return 43;
      }
      if (paramBoolean1)
      {
        if (paramBoolean2) {
          return 49;
        }
        return 44;
      }
      if (paramBoolean2) {
        return 47;
      }
      return 42;
    }
    if (paramInt == 3)
    {
      if (paramBoolean3) {
        return 53;
      }
      if (paramBoolean1) {
        return 54;
      }
      if (paramBoolean2) {
        return 55;
      }
      return 52;
    }
    return 0;
  }
  
  public void setTransactionCode(short paramShort)
  {
    if (jdMethod_for(this.d6, paramShort))
    {
      this.amountIsDebit = new Boolean(false);
      this.prenote = true;
    }
    if (jdMethod_for(this.d4, paramShort)) {
      this.amountIsDebit = new Boolean(false);
    } else {
      this.amountIsDebit = new Boolean(true);
    }
    if (jdMethod_for(this.d7, paramShort)) {
      this.achPayee.setAccountType(1);
    } else if (jdMethod_for(this.d3, paramShort)) {
      this.achPayee.setAccountType(2);
    } else if (jdMethod_for(this.d2, paramShort)) {
      this.achPayee.setAccountType(3);
    } else if (jdMethod_for(this.d5, paramShort)) {
      this.achPayee.setAccountType(4);
    }
  }
  
  public void setTransactionCode(String paramString)
  {
    setTransactionCode(new Short(paramString).shortValue());
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
    return ResourceUtil.getString("ACHStatus" + this.status, "com.ffusion.beans.ach.resources", this.locale);
  }
  
  public String getResourceBundleName()
  {
    return "com.ffusion.beans.ach.resources";
  }
  
  public String getErrorPrefix()
  {
    return "ACHEntryError_";
  }
  
  public void setActive(String paramString)
  {
    this.active = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getActive()
  {
    return "" + this.active;
  }
  
  public boolean getActiveValue()
  {
    return this.active;
  }
  
  public void setCanReverse(String paramString)
  {
    this.canReverse = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getCanReverse()
  {
    return "" + this.canReverse;
  }
  
  public boolean getCanReverseValue()
  {
    return this.canReverse;
  }
  
  public void setCheckSerialNumber(String paramString)
  {
    this.checkSerialNumber = paramString;
  }
  
  public String getCheckSerialNumber()
  {
    return this.checkSerialNumber;
  }
  
  public String getTerminalState()
  {
    return this.terminalState;
  }
  
  public void setTerminalState(String paramString)
  {
    this.terminalState = paramString;
  }
  
  public String getTaxFormID()
  {
    return this.taxFormID;
  }
  
  public void setTaxFormID(String paramString)
  {
    this.taxFormID = paramString;
  }
  
  public String getTerminalCity()
  {
    return this.terminalCity;
  }
  
  public void setTerminalCity(String paramString)
  {
    this.terminalCity = paramString;
  }
  
  public String getProcessControlField()
  {
    return this.processControlField;
  }
  
  public void setProcessControlField(String paramString)
  {
    this.processControlField = paramString;
  }
  
  public String getItemResearchNumber()
  {
    return this.itemResearchNumber;
  }
  
  public void setItemResearchNumber(String paramString)
  {
    this.itemResearchNumber = paramString;
  }
  
  public String getPaymentTypeCode()
  {
    return this.paymentTypeCode;
  }
  
  public void setPaymentTypeCode(String paramString)
  {
    this.paymentTypeCode = paramString;
  }
  
  public String getIdentificationNumber()
  {
    return this.identificationNumber;
  }
  
  public void setIdentificationNumber(String paramString)
  {
    this.identificationNumber = paramString;
  }
  
  public boolean isAmountIsDebit()
  {
    if (this.amountIsDebit != null) {
      return this.amountIsDebit.booleanValue();
    }
    return true;
  }
  
  public Boolean getAmountIsDebitObject()
  {
    return this.amountIsDebit;
  }
  
  public void setAmountIsDebit(boolean paramBoolean)
  {
    this.amountIsDebit = new Boolean(paramBoolean);
  }
  
  public void setAmountIsDebit(Boolean paramBoolean)
  {
    this.amountIsDebit = paramBoolean;
  }
  
  public void setAmountIsDebit(String paramString)
  {
    this.amountIsDebit = new Boolean(paramString);
  }
  
  public String getAmountIsDebit()
  {
    return "" + this.amountIsDebit;
  }
  
  public boolean getAmountIsDebitValue()
  {
    if (this.amountIsDebit != null) {
      return this.amountIsDebit.booleanValue();
    }
    return true;
  }
  
  public String getDiscretionaryData()
  {
    return this.discretionaryData;
  }
  
  public void setDiscretionaryData(String paramString)
  {
    this.discretionaryData = paramString;
  }
  
  public String getServiceClassCode()
  {
    return this.serviceClassCode;
  }
  
  public void setServiceClassCode(String paramString)
  {
    this.serviceClassCode = paramString;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "AMOUNT");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    ACHEntry localACHEntry = (ACHEntry)paramObject;
    int i = 1;
    if ((paramString.equals("RECID")) && (this.recID != null) && (localACHEntry.getRecID() != null))
    {
      i = numStringCompare(getRecID(), localACHEntry.getRecID());
    }
    else if (paramString.equals("STATUS"))
    {
      i = this.status - localACHEntry.getStatus();
    }
    else if ((paramString.equals("STATUSSTRING")) || (paramString.equals("STATUSNAME")))
    {
      i = getStatusName().compareTo(localACHEntry.getStatusName());
    }
    else if (paramString.equals("TYPE"))
    {
      i = getAmountIsDebit().compareTo(localACHEntry.getAmountIsDebit());
    }
    else if ((paramString.equals("USERACCOUNTNUMBER")) && (this.achPayee != null) && (localACHEntry.getAchPayee() != null))
    {
      i = this.achPayee.getUserAccountNumber().compareTo(localACHEntry.getAchPayee().getUserAccountNumber());
    }
    else if ((paramString.equals("PAYEENICKNAME")) && (this.achPayee != null) && (localACHEntry.getAchPayee() != null))
    {
      String str1 = this.achPayee.getNickName();
      if ((str1 == null) || (str1.length() == 0)) {
        str1 = this.achPayee.getName();
      }
      String str2 = localACHEntry.getAchPayee().getNickName();
      if ((str2 == null) || (str2.length() == 0)) {
        str2 = localACHEntry.getAchPayee().getName();
      }
      i = str1.toUpperCase().compareTo(str2.toUpperCase());
    }
    else if ((paramString.equals("PAYEENAME")) && (this.achPayee != null) && (localACHEntry.getAchPayee() != null))
    {
      i = this.achPayee.getName().toUpperCase().compareTo(localACHEntry.getAchPayee().getName().toUpperCase());
    }
    else if ((paramString.equals("DISCRETIONARYDATA")) && (getDiscretionaryData() != null) && (localACHEntry.getDiscretionaryData() != null))
    {
      i = getDiscretionaryData().compareTo(localACHEntry.getDiscretionaryData());
    }
    else if ((paramString.equals("ITEMRESEARCHNUMBER")) && (this.itemResearchNumber != null) && (localACHEntry.getItemResearchNumber() != null))
    {
      i = this.itemResearchNumber.compareTo(localACHEntry.getItemResearchNumber());
    }
    else if (((paramString.equals("ACTIVEENTRY")) || (paramString.equals("ACTIVE"))) && (getActive() != null) && (localACHEntry.getActive() != null))
    {
      i = getActive().compareTo(localACHEntry.getActive());
    }
    else if ((paramString.equals("OFFSETACCOUNTBANKID")) && (this.offsetAccountBankID != null) && (localACHEntry.getOffsetAccountBankID() != null))
    {
      i = numStringCompare(this.offsetAccountBankID, localACHEntry.getOffsetAccountBankID());
    }
    else if ((paramString.equals("OFFSETACCOUNTNAME")) && (this.offsetAccountName != null) && (localACHEntry.getOffsetAccountName() != null))
    {
      i = this.offsetAccountName.toUpperCase().compareTo(localACHEntry.getOffsetAccountName().toUpperCase());
    }
    else if (paramString.equals("OFFSETACCOUNTTYPE"))
    {
      i = getOffsetAccountTypeValue() - localACHEntry.getOffsetAccountTypeValue();
    }
    else if (paramString.equals("OFFSETACCOUNTTYPESTRING"))
    {
      i = getOffsetAccountType().compareTo(localACHEntry.getOffsetAccountType());
    }
    else if ((paramString.equals("OFFSETACCOUNTNUMBER")) && (this.offsetAccountNumber != null) && (localACHEntry.getOffsetAccountNumber() != null))
    {
      i = numStringCompare(this.offsetAccountNumber, localACHEntry.getOffsetAccountNumber());
    }
    else if ((paramString.equals("ROUTINGNUM")) && (this.achPayee != null) && (localACHEntry.getAchPayee() != null))
    {
      i = this.achPayee.getRoutingNumber().compareTo(localACHEntry.getAchPayee().getRoutingNumber());
    }
    else if ((paramString.equals("ACCOUNTNUMBER")) && (this.achPayee != null) && (localACHEntry.getAchPayee() != null))
    {
      i = this.achPayee.getAccountNumber().compareTo(localACHEntry.getAchPayee().getAccountNumber());
    }
    else if ((paramString.equals("ACCOUNTTYPE")) && (this.achPayee != null) && (localACHEntry.getAchPayee() != null))
    {
      i = this.achPayee.getAccountType().compareTo(localACHEntry.getAchPayee().getAccountType());
    }
    else
    {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1.equals("RECID")) && (this.recID != null)) {
      return isFilterable(this.recID, paramString2, paramString3);
    }
    if (paramString1.equals("ACTIVEENTRY")) {
      return isFilterable(getActive(), paramString2, paramString3);
    }
    if ((paramString1.toUpperCase().startsWith("PAYEE")) && (this.achPayee != null) && (this.achPayee.getID() != null)) {
      return isFilterable(this.achPayee.getID(), paramString2, paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public void set(ACHEntry paramACHEntry)
  {
    setOffsetAccountName(paramACHEntry.getOffsetAccountName());
    setOffsetAccountNumber(paramACHEntry.getOffsetAccountNumber());
    setOffsetAccountBankID(paramACHEntry.getOffsetAccountBankID());
    setOffsetAccountType(paramACHEntry.getOffsetAccountTypeValue());
    setOffsetAccountID(paramACHEntry.getOffsetAccountID());
    setAchPayee(paramACHEntry.getAchPayee());
    setAddendas(paramACHEntry.getAddendas());
    setRecID(paramACHEntry.getRecID());
    setStatus(paramACHEntry.getStatus());
    setActive(paramACHEntry.getActive());
    setPrenote(paramACHEntry.getPrenote());
    setTaxFormID(paramACHEntry.getTaxFormID());
    setCheckSerialNumber(paramACHEntry.getCheckSerialNumber());
    setTerminalState(paramACHEntry.getTerminalState());
    setTerminalCity(paramACHEntry.getTerminalCity());
    setProcessControlField(paramACHEntry.getProcessControlField());
    setItemResearchNumber(paramACHEntry.getItemResearchNumber());
    setPaymentTypeCode(paramACHEntry.getPaymentTypeCode());
    setIdentificationNumber(paramACHEntry.getIdentificationNumber());
    setDiscretionaryData(paramACHEntry.getDiscretionaryData());
    setAmountIsDebit(paramACHEntry.getAmountIsDebitObject());
    super.set(paramACHEntry);
  }
  
  public boolean getPrenoteValue()
  {
    return this.prenote;
  }
  
  public String getPrenote()
  {
    return "" + this.prenote;
  }
  
  public void setPrenote(String paramString)
  {
    this.prenote = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void merge(ACHEntry paramACHEntry)
  {
    super.merge(paramACHEntry);
    String str = paramACHEntry.getOffsetAccountBankID();
    if (str != null) {
      setOffsetAccountBankID(str);
    }
    str = paramACHEntry.getOffsetAccountNumber();
    if (str != null) {
      setOffsetAccountNumber(str);
    }
    str = paramACHEntry.getOffsetAccountName();
    if (str != null) {
      setOffsetAccountName(str);
    }
    int i = paramACHEntry.getOffsetAccountTypeValue();
    if (i != 0) {
      setOffsetAccountType(i);
    }
    Boolean localBoolean = paramACHEntry.getAmountIsDebitObject();
    if (localBoolean != null) {
      setAmountIsDebit(localBoolean);
    }
    str = paramACHEntry.getCheckSerialNumber();
    if (str != null) {
      setCheckSerialNumber(str);
    }
    str = paramACHEntry.getDiscretionaryData();
    if (str != null) {
      setDiscretionaryData(str);
    }
    str = paramACHEntry.getRecID();
    if (str != null) {
      setRecID(str);
    }
    str = paramACHEntry.getTerminalState();
    if (str != null) {
      setTerminalState(str);
    }
    str = paramACHEntry.getTerminalCity();
    if (str != null) {
      setTerminalCity(str);
    }
    str = paramACHEntry.getProcessControlField();
    if (str != null) {
      setProcessControlField(str);
    }
    str = paramACHEntry.getItemResearchNumber();
    if (str != null) {
      setItemResearchNumber(str);
    }
    str = paramACHEntry.getPaymentTypeCode();
    if (str != null) {
      setPaymentTypeCode(str);
    }
    str = paramACHEntry.getIdentificationNumber();
    if (str != null) {
      setIdentificationNumber(str);
    }
    str = paramACHEntry.getAchPayeeID();
    if (str != null) {
      setAchPayeeID(str);
    }
    ACHPayee localACHPayee = paramACHEntry.getAchPayee();
    if (localACHPayee != null) {
      if (this.achPayee == null) {
        this.achPayee = ((ACHPayee)localACHPayee.clone());
      } else {
        this.achPayee.merge(localACHPayee);
      }
    }
    ACHAddendas localACHAddendas = paramACHEntry.getAddendas();
    if (localACHAddendas != null) {}
  }
  
  public boolean validate(int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
  {
    super.validate(paramInt, paramBoolean1);
    if (this.achPayee == null)
    {
      if (paramBoolean1) {
        addValidationError(getImportError("PayeeInformationMissing"), getImportError("InternalError"));
      }
    }
    else {
      this.achPayee.validate(this, paramInt, paramBoolean1);
    }
    int i;
    if (paramInt == 5) {
      i = 9;
    } else {
      i = 15;
    }
    if ((paramBoolean2) && (this.offsetAccountID == null)) {
      addValidationError(getImportError("ValueNotSet"), getImportError("OffsetAcctNotInitialized"), "OFFSETACCOUNT");
    }
    if ((this.checkSerialNumber == null) || (this.checkSerialNumber.trim().length() == 0))
    {
      if ((paramBoolean1) && (!paramBoolean3) && ((paramInt == 1) || (paramInt == 5) || (paramInt == 17) || (paramInt == 18))) {
        addValidationError(getImportError("ValueNotSet"), getImportError("CheckSerialNumberNotInitialized"), "CHECKSERIALNUMBER");
      }
    }
    else if (this.checkSerialNumber.length() > i)
    {
      localObject = new Object[] { this.checkSerialNumber };
      Object[] arrayOfObject1 = { String.valueOf(this.checkSerialNumber.length()) };
      addValidationError(getImportError("InvalidCheckSerialNumber", (Object[])localObject), getImportError("FieldLimited" + i + "Chars", arrayOfObject1), "CHECKSERIALNUMBER");
    }
    else if ((paramInt == 1) || (paramInt == 5) || (paramInt == 17) || (paramInt == 18))
    {
      localObject = this.checkSerialNumber.trim();
      int j = 1;
      for (int k = 0; (j != 0) && (k < ((String)localObject).length()); k++) {
        if (((String)localObject).charAt(k) != '0') {
          j = 0;
        }
      }
      if (j != 0)
      {
        Object[] arrayOfObject3 = { this.checkSerialNumber };
        addValidationError(getImportError("InvalidCheckSerialNumber", arrayOfObject3), getImportError("ZeroCheckSerialNumber"), "CHECKSERIALNUMBER");
      }
    }
    Object[] arrayOfObject2;
    if ((this.discretionaryData != null) && (this.discretionaryData.length() > 2))
    {
      localObject = new Object[] { this.discretionaryData };
      arrayOfObject2 = new Object[] { String.valueOf(this.discretionaryData.length()) };
      addValidationError(getImportError("InvalidDiscretionaryData", (Object[])localObject), getImportError("FieldLimited2Chars", arrayOfObject2), "DISCRETIONARYDATA");
    }
    if ((this.terminalState == null) || (this.terminalState.trim().length() == 0))
    {
      if ((paramBoolean1) && (paramInt == 5)) {
        addValidationError(getImportError("ValueNotSet"), getImportError("TerminalStateNotInitialized"), "TERMINALSTATE");
      }
    }
    else if (this.terminalState.length() > 2)
    {
      localObject = new Object[] { this.terminalState };
      arrayOfObject2 = new Object[] { String.valueOf(this.terminalState.length()) };
      addValidationError(getImportError("InvalidTerminalState", (Object[])localObject), getImportError("FieldLimited2Chars", arrayOfObject2), "TERMINALSTATE");
    }
    if ((this.terminalCity == null) || (this.terminalCity.trim().length() == 0))
    {
      if ((paramBoolean1) && (paramInt == 5)) {
        addValidationError(getImportError("ValueNotSet"), getImportError("TerminalCityNotInitialized"), "TERMINALCITY");
      }
    }
    else if (this.terminalCity.length() > 4)
    {
      localObject = new Object[] { this.terminalCity };
      arrayOfObject2 = new Object[] { String.valueOf(this.terminalCity.length()) };
      addValidationError(getImportError("InvalidTerminalCity", (Object[])localObject), getImportError("FieldLimited4Chars", arrayOfObject2), "TERMINALCITY");
    }
    if ((this.processControlField == null) || (this.processControlField.trim().length() == 0))
    {
      if ((paramBoolean1) && (paramInt == 18)) {
        addValidationError(getImportError("ValueNotSet"), getImportError("ProcessControlNotInitialized"), "PROCESSCONTROLFIELD");
      }
    }
    else if (this.processControlField.length() > 6)
    {
      localObject = new Object[] { this.processControlField };
      arrayOfObject2 = new Object[] { String.valueOf(this.processControlField.length()) };
      addValidationError(getImportError("InvalidProcessControl", (Object[])localObject), getImportError("FieldLimited6Chars", arrayOfObject2), "PROCESSCONTROLFIELD");
    }
    if ((this.itemResearchNumber == null) || (this.itemResearchNumber.trim().length() == 0))
    {
      if ((paramBoolean1) && (paramInt == 18)) {
        addValidationError(getImportError("ValueNotSet"), getImportError("ItemResearchNumberNotInitialized"), "ITEMRESEARCHNUMBER");
      }
    }
    else if (this.itemResearchNumber.length() > 16)
    {
      localObject = new Object[] { this.itemResearchNumber };
      arrayOfObject2 = new Object[] { String.valueOf(this.itemResearchNumber.length()) };
      addValidationError(getImportError("InvalidItemResearchNumber", (Object[])localObject), getImportError("FieldLimited16Chars", arrayOfObject2), "ITEMRESEARCHNUMBER");
    }
    Object localObject = { this.amount.toString() };
    if ((this.amount != null) && (this.amount.doubleValue() > 99999999.989999995D)) {
      addValidationError(getImportError("AmountTooLarge", (Object[])localObject), getImportError("AmountInvalid"), "AMOUNT");
    }
    if ((this.amount != null) && (this.amount.doubleValue() < 0.0D)) {
      addValidationError(getImportError("AmountTooSmall", (Object[])localObject), getImportError("AmountMustBePositive"), "AMOUNT");
    }
    if (this.amountIsDebit == null) {
      addValidationError(getImportError("ValueNotSet"), getImportError("DebitOrCredit"), "AMOUNT");
    }
    if ((this.amountIsDebit != null) && (this.amountIsDebit.booleanValue() == true))
    {
      if ((paramInt == 2) && ((this.serviceClassCode == null) || (this.serviceClassCode.equalsIgnoreCase("225")))) {
        addValidationError(getImportError("MustBeCredit", (Object[])localObject), getImportError("CreditOnly"), "AMOUNT");
      }
      if ((this.achPayee != null) && (this.achPayee.getAccountTypeValue() == 3) && (!paramBoolean4)) {
        addValidationError(getImportError("LoanAccountNotValid"), getImportError("CanNotUseParticipant"), "AMOUNT");
      }
      if ((this.achPayee != null) && (this.achPayee.getAccountTypeValue() == 4) && (!paramBoolean4) && ((paramInt == 5) || (paramInt == 10) || (paramInt == 17) || (paramInt == 18) || (paramInt == 1) || (paramInt == 2) || (paramInt == 9))) {
        addValidationError(getImportError("LedgerAccountNotValid"), getImportError("CanNotUseParticipant"), "AMOUNT");
      }
    }
    if ((this.amountIsDebit != null) && (!this.amountIsDebit.booleanValue()) && ((paramInt == 1) || (paramInt == 5) || (paramInt == 9) || (paramInt == 17) || (paramInt == 10) || (paramInt == 18)) && ((this.serviceClassCode == null) || (this.serviceClassCode.equalsIgnoreCase("220")))) {
      addValidationError(getImportError("MustBeDebit", (Object[])localObject), getImportError("DebitOnly"), "AMOUNT");
    }
    double d = 0.0D;
    int m = 0;
    if (getAmountValue() != null) {
      d = getAmountValue().doubleValue();
    }
    if (((paramInt == 12) || (paramInt == 13)) && (!this.prenote) && (getAddendaString() != null) && (getAddendaString().length() > 0)) {
      m = 1;
    }
    if (d == 0.0D)
    {
      if ((!this.prenote) && (m == 0) && (!paramBoolean3) && (this.active)) {
        addValidationError(getImportError("ZeroNotAllowed", (Object[])localObject), getImportError("ZeroDollarRemittance"), "AMOUNT");
      }
    }
    else if (this.prenote) {
      addValidationError(getImportError("ZeroPrenote", (Object[])localObject), getImportError("PrenoteZeroDollar"), "AMOUNT");
    } else if ((d > 2500.0D) && ((paramInt == 17) || (paramInt == 18))) {
      addValidationError(getImportError("AmountTooLarge", (Object[])localObject), getImportError("RCK_XCK_Exceed2500"), "AMOUNT");
    } else if ((d > 25000.0D) && ((paramInt == 1) || (paramInt == 5))) {
      addValidationError(getImportError("AmountTooLarge", (Object[])localObject), getImportError("POP_ARC_Exceed25000"), "AMOUNT");
    } else if ((d > 25000.0D) && (paramInt == 19)) {
      addValidationError(getImportError("AmountTooLarge", (Object[])localObject), getImportError("BOC_Exceed25000"), "AMOUNT");
    }
    if (this.addendas != null)
    {
      String str = getAddendaString();
      if (((paramInt == 12) || (paramInt == 2) || (paramInt == 6) || (paramInt == 10)) && (str != null) && (str.length() > 80)) {
        addValidationError(getImportError("InvalidAddenda"), getImportError("InvalidAddendaTooLong"), "ACHADDENDA");
      }
      Iterator localIterator = this.addendas.iterator();
      while (localIterator.hasNext()) {
        ACHAddenda localACHAddenda = (ACHAddenda)localIterator.next();
      }
    }
    return this.validationErrors == null;
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    try
    {
      if (paramString1.equals("OFFSETACCOUNTBANKID")) {
        this.offsetAccountBankID = paramString2;
      } else if (paramString1.equals("OFFSETACCOUNTID")) {
        this.offsetAccountID = paramString2;
      } else if (paramString1.equals("OFFSETACCOUNTNAME")) {
        this.offsetAccountName = paramString2;
      } else if (paramString1.equals("OFFSETACCOUNTNUMBER")) {
        this.offsetAccountNumber = paramString2;
      } else if (paramString1.equals("OFFSETACCOUNTTYPE")) {
        setOffsetAccountType(paramString2);
      } else if (paramString1.equals("STATUS")) {
        this.status = Integer.parseInt(paramString2);
      } else if (paramString1.equals("TAXFORMID")) {
        setTaxFormID(paramString2);
      } else if (paramString1.equals("ACTIVEENTRY")) {
        setActive(paramString2);
      } else if (paramString1.equals("CHECKSERIALNUMBER")) {
        this.checkSerialNumber = paramString2;
      } else if (paramString1.equals("TERMINALSTATE")) {
        this.terminalState = paramString2;
      } else if (paramString1.equals("TERMINALCITY")) {
        this.terminalCity = paramString2;
      } else if (paramString1.equals("SERVICECLASSCODE")) {
        this.serviceClassCode = paramString2;
      } else if (paramString1.equals("PROCESSCONTROLFIELD")) {
        this.processControlField = paramString2;
      } else if (paramString1.equals("ITEMRESEARCHNUMBER")) {
        this.itemResearchNumber = paramString2;
      } else if (paramString1.equals("PAYMENTTYPECODE")) {
        this.paymentTypeCode = paramString2;
      } else if (paramString1.equals("IDENTIFICATIONNUMBER")) {
        this.identificationNumber = paramString2;
      } else if (paramString1.equals("AMOUNTISDEBIT")) {
        setAmountIsDebit(paramString2);
      } else if (paramString1.equals("PRENOTE")) {
        setPrenote(paramString2);
      } else if (paramString1.equals("DISCRETIONARYDATA")) {
        this.discretionaryData = paramString2;
      } else if (paramString1.equals("ACHPAYEEID")) {
        this.payeeID = paramString2;
      } else {
        bool = super.set(paramString1, paramString2);
      }
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception", localException);
    }
    return bool;
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "ACHENTRY");
    XMLHandler.appendTag(localStringBuffer, "ACTIVEENTRY", getActive());
    XMLHandler.appendTag(localStringBuffer, "OFFSETACCOUNTID", this.offsetAccountID);
    XMLHandler.appendTag(localStringBuffer, "OFFSETACCOUNTBANKID", this.offsetAccountBankID);
    XMLHandler.appendTag(localStringBuffer, "OFFSETACCOUNTNAME", this.offsetAccountName);
    XMLHandler.appendTag(localStringBuffer, "OFFSETACCOUNTNUMBER", this.offsetAccountNumber);
    XMLHandler.appendTag(localStringBuffer, "OFFSETACCOUNTTYPE", getOffsetAccountType());
    XMLHandler.appendTag(localStringBuffer, "CHECKSERIALNUMBER", getCheckSerialNumber());
    XMLHandler.appendTag(localStringBuffer, "TAXFORMID", getTaxFormID());
    XMLHandler.appendTag(localStringBuffer, "SERVICECLASSCODE", getServiceClassCode());
    XMLHandler.appendTag(localStringBuffer, "TERMINALSTATE", getTerminalState());
    XMLHandler.appendTag(localStringBuffer, "TERMINALCITY", getTerminalCity());
    XMLHandler.appendTag(localStringBuffer, "PROCESSCONTROLFIELD", getProcessControlField());
    XMLHandler.appendTag(localStringBuffer, "ITEMRESEARCHNUMBER", getItemResearchNumber());
    XMLHandler.appendTag(localStringBuffer, "PAYMENTTYPECODE", getPaymentTypeCode());
    XMLHandler.appendTag(localStringBuffer, "IDENTIFICATIONNUMBER", getIdentificationNumber());
    XMLHandler.appendTag(localStringBuffer, "AMOUNTISDEBIT", getAmountIsDebit());
    XMLHandler.appendTag(localStringBuffer, "PRENOTE", getPrenote());
    XMLHandler.appendTag(localStringBuffer, "DISCRETIONARYDATA", getDiscretionaryData());
    if (this.achPayee != null) {
      localStringBuffer.append(this.achPayee.getXML());
    } else {
      XMLHandler.appendTag(localStringBuffer, "ACHPAYEEID", getAchPayeeID());
    }
    if (this.addendas != null) {
      localStringBuffer.append(this.addendas.getXML());
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "ACHENTRY");
    return localStringBuffer.toString();
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, ACHEntry paramACHEntry, String paramString)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "ID", paramACHEntry.getID(), getID(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "ACTIVEENTRY", paramACHEntry.getActive(), getActive(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "OFFSETACCOUNTID", paramACHEntry.getOffsetAccountID(), getOffsetAccountID(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "OFFSETACCOUNTBANKID", paramACHEntry.getOffsetAccountBankID(), getOffsetAccountBankID(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "OFFSETACCOUNTNAME", paramACHEntry.getOffsetAccountName(), getOffsetAccountName(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "OFFSETACCOUNTNUMBER", paramACHEntry.getOffsetAccountNumber(), getOffsetAccountNumber(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "OFFSETACCOUNTTYPE", paramACHEntry.getOffsetAccountType(), getOffsetAccountType(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "TAXFORMID", paramACHEntry.getTaxFormID(), getTaxFormID(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "CHECKSERIALNUMBER", paramACHEntry.getCheckSerialNumber(), getCheckSerialNumber(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "TERMINALSTATE", paramACHEntry.getTerminalState(), getTerminalState(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "TERMINALCITY", paramACHEntry.getTerminalCity(), getTerminalCity(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "PROCESSCONTROLFIELD", paramACHEntry.getProcessControlField(), getProcessControlField(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "ITEMRESEARCHNUMBER", paramACHEntry.getItemResearchNumber(), getItemResearchNumber(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "PAYMENTTYPECODE", paramACHEntry.getPaymentTypeCode(), getPaymentTypeCode(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "IDENTIFICATIONNUMBER", paramACHEntry.getIdentificationNumber(), getIdentificationNumber(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "AMOUNTISDEBIT", paramACHEntry.getAmountIsDebit(), getAmountIsDebit(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "PRENOTE", paramACHEntry.getPrenote(), getPrenote(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "DISCRETIONARYDATA", paramACHEntry.getDiscretionaryData(), getDiscretionaryData(), paramString);
    if (this.achPayee != null) {
      this.achPayee.logChanges(paramHistoryTracker, paramACHEntry.getAchPayee(), paramString);
    }
    ACHAddendas localACHAddendas1 = new ACHAddendas();
    ACHAddendas localACHAddendas2 = new ACHAddendas();
    if (this.addendas != null) {
      localACHAddendas1.addAll(this.addendas);
    }
    if (paramACHEntry.getAddendas() != null) {
      localACHAddendas2.addAll(paramACHEntry.getAddendas());
    }
    Iterator localIterator = localACHAddendas2.iterator();
    ACHAddenda localACHAddenda2;
    while (localIterator.hasNext())
    {
      ACHAddenda localACHAddenda1 = (ACHAddenda)localIterator.next();
      localACHAddenda2 = localACHAddendas1.getByID(localACHAddenda1.getID());
      if (localACHAddenda2 == null)
      {
        localACHAddenda1.logDeletion(paramHistoryTracker, paramString + paramHistoryTracker.lookupField(BEAN_NAME, "ACHADDENDA") + localACHAddenda1.getID());
      }
      else
      {
        localACHAddenda2.logChanges(paramHistoryTracker, localACHAddenda1, paramString + paramHistoryTracker.lookupField(BEAN_NAME, "ACHADDENDA") + localACHAddenda1.getID());
        localACHAddendas1.remove(localACHAddenda2);
      }
    }
    localIterator = localACHAddendas1.iterator();
    while (localIterator.hasNext())
    {
      localACHAddenda2 = (ACHAddenda)localIterator.next();
      localACHAddenda2.logCreation(paramHistoryTracker, paramString + paramHistoryTracker.lookupField(BEAN_NAME, "ACHADDENDA") + localACHAddenda2.getID());
    }
    super.logChanges(paramHistoryTracker, BEAN_NAME, paramACHEntry, paramString);
  }
  
  public void logCreation(HistoryTracker paramHistoryTracker, String paramString)
  {
    paramHistoryTracker.logCreate(BEAN_NAME, "ENTRY_ADDED", getID(), paramString);
  }
  
  public void logDeletion(HistoryTracker paramHistoryTracker, String paramString)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "ENTRY_CANCELED", getID(), null, paramString);
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, ACHEntry paramACHEntry, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "ID", paramACHEntry.getID(), getID(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "ACTIVEENTRY", paramACHEntry.getActiveValue(), getActiveValue(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "OFFSETACCOUNTID", paramACHEntry.getOffsetAccountID(), getOffsetAccountID(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "OFFSETACCOUNTBANKID", paramACHEntry.getOffsetAccountBankID(), getOffsetAccountBankID(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "OFFSETACCOUNTNAME", paramACHEntry.getOffsetAccountName(), getOffsetAccountName(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "OFFSETACCOUNTNUMBER", paramACHEntry.getOffsetAccountNumber(), getOffsetAccountNumber(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "OFFSETACCOUNTTYPE", new LocalizableString("com.ffusion.beans.ach.resources", "ACHAccountType" + paramACHEntry.getOffsetAccountTypeValue(), null), new LocalizableString("com.ffusion.beans.ach.resources", "ACHAccountType" + getOffsetAccountTypeValue(), null), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "TAXFORMID", paramACHEntry.getTaxFormID(), getTaxFormID(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "CHECKSERIALNUMBER", paramACHEntry.getCheckSerialNumber(), getCheckSerialNumber(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "TERMINALSTATE", paramACHEntry.getTerminalState(), getTerminalState(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "TERMINALCITY", paramACHEntry.getTerminalCity(), getTerminalCity(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "PROCESSCONTROLFIELD", paramACHEntry.getProcessControlField(), getProcessControlField(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "ITEMRESEARCHNUMBER", paramACHEntry.getItemResearchNumber(), getItemResearchNumber(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "PAYMENTTYPECODE", paramACHEntry.getPaymentTypeCode() == null ? null : new LocalizableString("com.ffusion.beans.ach.resources", "PaymentTypeCodes" + paramACHEntry.getPaymentTypeCode(), null), getPaymentTypeCode() == null ? null : new LocalizableString("com.ffusion.beans.ach.resources", "PaymentTypeCodes" + getPaymentTypeCode(), null), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "IDENTIFICATIONNUMBER", paramACHEntry.getIdentificationNumber(), getIdentificationNumber(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "AMOUNTISDEBIT", paramACHEntry.getAmountIsDebitValue(), getAmountIsDebitValue(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "PRENOTE", paramACHEntry.getPrenoteValue(), getPrenoteValue(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "DISCRETIONARYDATA", paramACHEntry.getDiscretionaryData(), getDiscretionaryData(), paramILocalizable);
    if (this.achPayee != null) {
      this.achPayee.logChanges(paramHistoryTracker, paramACHEntry.getAchPayee(), paramILocalizable);
    }
    ACHAddendas localACHAddendas1 = new ACHAddendas();
    ACHAddendas localACHAddendas2 = new ACHAddendas();
    if (this.addendas != null) {
      localACHAddendas1.addAll(this.addendas);
    }
    if (paramACHEntry.getAddendas() != null) {
      localACHAddendas2.addAll(paramACHEntry.getAddendas());
    }
    Iterator localIterator = localACHAddendas2.iterator();
    ACHAddenda localACHAddenda2;
    Object[] arrayOfObject;
    LocalizableString localLocalizableString;
    while (localIterator.hasNext())
    {
      ACHAddenda localACHAddenda1 = (ACHAddenda)localIterator.next();
      localACHAddenda2 = localACHAddendas1.getByID(localACHAddenda1.getID());
      arrayOfObject = new Object[2];
      arrayOfObject[0] = paramILocalizable;
      arrayOfObject[1] = localACHAddenda1.getID();
      localLocalizableString = new LocalizableString("com.ffusion.beans.history.resources", "ACH_ADDENDA", arrayOfObject);
      if (localACHAddenda2 == null)
      {
        localACHAddenda1.logDeletion(paramHistoryTracker, localLocalizableString);
      }
      else
      {
        localACHAddenda2.logChanges(paramHistoryTracker, localACHAddenda1, localLocalizableString);
        localACHAddendas1.remove(localACHAddenda2);
      }
    }
    localIterator = localACHAddendas1.iterator();
    while (localIterator.hasNext())
    {
      localACHAddenda2 = (ACHAddenda)localIterator.next();
      arrayOfObject = new Object[2];
      arrayOfObject[0] = paramILocalizable;
      arrayOfObject[1] = localACHAddenda2.getID();
      localLocalizableString = new LocalizableString("com.ffusion.beans.history.resources", "ACH_ADDENDA", arrayOfObject);
      localACHAddenda2.logCreation(paramHistoryTracker, localLocalizableString);
    }
    super.logChanges(paramHistoryTracker, BEAN_NAME, paramACHEntry, paramILocalizable);
  }
  
  public void logCreation(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.logCreate(BEAN_NAME, "ENTRY_ADDED", getID(), paramILocalizable);
  }
  
  public void logDeletion(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "ENTRY_CANCELED", getID(), null, paramILocalizable);
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
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  private static boolean jdMethod_for(short[] paramArrayOfShort, short paramShort)
  {
    for (int i = 0; i < paramArrayOfShort.length; i++) {
      if (paramArrayOfShort[i] == paramShort) {
        return true;
      }
    }
    return false;
  }
  
  class a
    extends XMLHandler
  {
    public a() {}
    
    public void startElement(String paramString)
      throws Exception
    {
      Object localObject;
      if (paramString.equals("ACHPAYEE"))
      {
        localObject = new ACHPayee();
        ACHEntry.this.achPayee = ((ACHPayee)localObject);
        ((ACHPayee)localObject).continueXMLParsing(getHandler());
      }
      else if (paramString.equals("ACHADDENDAS"))
      {
        localObject = new ACHAddendas();
        ACHEntry.this.setAddendas((ACHAddendas)localObject);
        ((ACHAddendas)localObject).continueXMLParsing(getHandler());
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
      ACHEntry.this.set(getElement(), str);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.ach.ACHEntry
 * JD-Core Version:    0.7.0.1
 */