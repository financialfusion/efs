package com.ffusion.beans.ach;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.beans.util.KeyValue;
import com.ffusion.beans.util.KeyValueList;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.RoutingNumberUtil;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.LocalizableString;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class TaxForm
  extends ExtendABean
  implements TaxFormTypes, Serializable
{
  private static final String BEAN_NAME = TaxForm.class.getName();
  public static final String RESOURCE_BUNDLE = "com.ffusion.beans.ach.resources";
  public static final String KEY_TAX_FORM_TYPE_PREFIX = "TaxFormType";
  public static final String TAXFORM = "TAXFORM";
  public static final String CODE = "CODE";
  public static final String NAME = "NAME";
  public static final String ABBR = "ABBR";
  public static final String BANKNAME = "BANKNAME";
  public static final String ROUTINGNO = "ROUTINGNO";
  public static final String ACCOUNTNO = "ACCOUNTNO";
  public static final String DEPOSITACCOUNT = "DEPOSITACCOUNT";
  public static final String FEDERAL = "FEDERAL";
  public static final String STATE = "STATE";
  public static final String CHILDSUPPORT = "CHILDSUPPORT";
  public static final String OTHER = "OTHER";
  public static final String UNKNOWN = "UNKNOWN";
  public static final String INDIVIDUALNAME = "INDIVIDUALNAME";
  public static final String COMPANYDISCRETIONARYDATA = "COMPANYDISCRETIONARYDATA";
  public static final String COMPANYENTRYDISCRIPTION = "COMPANYENTRYDISCRIPTION";
  public static final String COMPANYENTRYDESCRIPTION = "COMPANYENTRYDESCRIPTION";
  public static final String NODEFAULTFIELDS = "NODEFAULTFIELDS";
  public static final String SUPPORTSSUFFIXES = "SUPPORTSSUFFIXES";
  public static final String ID = "ID";
  public static final String TXP = "TXP";
  public static final String DED = "DED";
  protected int type;
  protected String state;
  protected String stateName;
  protected String name;
  protected String IRSTaxFormNumber;
  protected String taxDescription;
  protected TaxFormBank bank = new TaxFormBank();
  protected String individualName;
  protected String identificationNumber;
  protected String compDiscData;
  protected String compEntryDisc;
  protected String id;
  protected String comment = "";
  protected boolean prenote = false;
  protected ArrayList depositAccounts = null;
  protected String currentFieldName;
  protected ArrayList fields = null;
  protected boolean supportsSuffixes = false;
  protected boolean noDefaultFields = false;
  
  public String getIndividualName()
  {
    return this.individualName;
  }
  
  public void setIndividualName(String paramString)
  {
    this.individualName = paramString;
  }
  
  public String getIdentificationNumber()
  {
    return this.identificationNumber;
  }
  
  public void setIdentificationNumber(String paramString)
  {
    this.identificationNumber = paramString;
  }
  
  public String getCompDiscData()
  {
    return this.compDiscData;
  }
  
  public void setCompDiscData(String paramString)
  {
    this.compDiscData = paramString;
  }
  
  public String getCompEntryDisc()
  {
    return this.compEntryDisc;
  }
  
  public void setCompEntryDisc(String paramString)
  {
    this.compEntryDisc = paramString;
  }
  
  public String getIRSTaxFormNumber()
  {
    return this.IRSTaxFormNumber;
  }
  
  public void setIRSTaxFormNumber(String paramString)
  {
    this.IRSTaxFormNumber = paramString;
  }
  
  public String getTaxFormDescription()
  {
    return this.taxDescription;
  }
  
  public void setTaxFormDescription(String paramString)
  {
    this.taxDescription = paramString;
  }
  
  public String getComment()
  {
    return this.comment;
  }
  
  public void setComment(String paramString)
  {
    this.comment = paramString;
  }
  
  public ArrayList getFields()
  {
    return this.fields;
  }
  
  public int getTypeValue()
  {
    return this.type;
  }
  
  public void setTypeValue(int paramInt)
  {
    this.type = paramInt;
  }
  
  public String getType()
  {
    if (this.type == 1) {
      return "FEDERAL";
    }
    if (this.type == 2) {
      return "STATE";
    }
    if (this.type == 4) {
      return "CHILDSUPPORT";
    }
    if (this.type == 3) {
      return "OTHER";
    }
    return "UNKNOWN";
  }
  
  public String getTypeDisplayString()
  {
    int i;
    if ((this.type == 1) || (this.type == 2) || (this.type == 4) || (this.type == 3)) {
      i = this.type;
    } else {
      i = 0;
    }
    String str = ResourceUtil.getString("TaxFormType" + i, "com.ffusion.beans.ach.resources", this.locale);
    if ((this.type == 2) || (this.type == 4)) {
      str = str + ": " + this.state;
    }
    return str;
  }
  
  public void setType(String paramString)
  {
    if (paramString.equalsIgnoreCase("FEDERAL")) {
      this.type = 1;
    } else if (paramString.equalsIgnoreCase("STATE")) {
      this.type = 2;
    } else if (paramString.equalsIgnoreCase("CHILDSUPPORT")) {
      this.type = 4;
    } else if (paramString.equalsIgnoreCase("OTHER")) {
      this.type = 3;
    }
  }
  
  public String getState()
  {
    return this.state;
  }
  
  public String getStateName()
  {
    return this.stateName;
  }
  
  public ArrayList getDepositAccounts()
  {
    return this.depositAccounts;
  }
  
  public String getMultipleDepositAccounts()
  {
    return "" + (this.depositAccounts.size() > 1);
  }
  
  public String getDepositAccountID()
  {
    if (this.bank != null) {
      return this.bank.getID();
    }
    return "";
  }
  
  public void setDepositAccountID(String paramString)
  {
    Iterator localIterator = this.depositAccounts.iterator();
    while (localIterator.hasNext())
    {
      TaxFormBank localTaxFormBank = (TaxFormBank)localIterator.next();
      if ((localTaxFormBank.getID() != null) && (localTaxFormBank.getID().equalsIgnoreCase(paramString)))
      {
        if (this.bank == null) {
          this.bank = new TaxFormBank();
        }
        this.bank.set(localTaxFormBank);
        break;
      }
    }
  }
  
  public void setState(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 2))
    {
      this.state = paramString.substring(0, 2);
      this.stateName = paramString.substring(4, paramString.length() - 1);
    }
    else
    {
      this.state = paramString;
    }
  }
  
  public void setStateName(String paramString)
  {
    this.stateName = paramString;
  }
  
  public String getID()
  {
    return this.id;
  }
  
  public void setID(String paramString)
  {
    this.id = paramString;
  }
  
  public String getSupportsSuffix()
  {
    return "" + this.supportsSuffixes;
  }
  
  public void setSupportsSuffix(String paramString)
  {
    this.supportsSuffixes = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getNoDefaultFields()
  {
    return "" + this.noDefaultFields;
  }
  
  public boolean getNoDefaultFieldsValue()
  {
    return this.noDefaultFields;
  }
  
  public void setNoDefaultFields(String paramString)
  {
    this.noDefaultFields = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
    int i = paramString.lastIndexOf(')');
    int j = i;
    int k = 1;
    while (i > 0)
    {
      i--;
      if (paramString.charAt(i) == '(')
      {
        k--;
        if (k == 0)
        {
          setIRSTaxFormNumber(paramString.substring(i + 1, j));
          setTaxFormDescription(paramString.substring(0, i));
        }
      }
      else if (paramString.charAt(i) == ')')
      {
        k++;
      }
    }
    setTaxFormDescription(paramString);
  }
  
  public boolean addOrMergeField(TaxField paramTaxField)
  {
    TaxField localTaxField = getFieldByName(paramTaxField.getFieldname());
    if (localTaxField != null) {
      return localTaxField.merge(paramTaxField);
    }
    addField(paramTaxField);
    return true;
  }
  
  public void _addNewField(TaxField paramTaxField)
  {
    this.fields.add(paramTaxField);
  }
  
  public void addField(TaxField paramTaxField)
  {
    this.fields.add(paramTaxField.copy());
  }
  
  public TaxFormBank getDepositAccount()
  {
    return this.bank;
  }
  
  public String getBankName()
  {
    return this.bank.bankName;
  }
  
  public void setBankName(String paramString)
  {
    this.bank.bankName = paramString;
  }
  
  public String getBankRoutingNumber()
  {
    return this.bank.bankRoutingNumber;
  }
  
  public void setBankRoutingNumber(String paramString)
  {
    this.bank.bankRoutingNumber = paramString;
  }
  
  public String getBankAccountType()
  {
    return this.bank.bankAccountType;
  }
  
  public void setBankAccountType(String paramString)
  {
    this.bank.bankAccountType = paramString;
  }
  
  public String getBankAccountNumber()
  {
    return this.bank.bankAccountNumber;
  }
  
  public void setBankAccountNumber(String paramString)
  {
    this.bank.bankAccountNumber = paramString;
  }
  
  public void set(TaxForm paramTaxForm)
  {
    if (paramTaxForm == null) {
      return;
    }
    shallowSet(paramTaxForm);
    ArrayList localArrayList = paramTaxForm.getFields();
    this.fields.clear();
    int i = localArrayList.size();
    for (int j = 0; j < i; j++) {
      addField((TaxField)localArrayList.get(j));
    }
    this.depositAccounts.clear();
    Iterator localIterator = paramTaxForm.depositAccounts.iterator();
    while (localIterator.hasNext())
    {
      TaxFormBank localTaxFormBank1 = new TaxFormBank();
      TaxFormBank localTaxFormBank2 = (TaxFormBank)localIterator.next();
      localTaxFormBank1.set(localTaxFormBank2);
      this.depositAccounts.add(localTaxFormBank2);
    }
    this.bank.set(paramTaxForm.bank);
  }
  
  public TaxForm shallowCopy()
  {
    TaxForm localTaxForm = new TaxForm();
    localTaxForm.shallowSet(this);
    return localTaxForm;
  }
  
  protected void shallowSet(TaxForm paramTaxForm)
  {
    setName(paramTaxForm.getName());
    setState(paramTaxForm.getState());
    setTypeValue(paramTaxForm.getTypeValue());
    setIndividualName(paramTaxForm.getIndividualName());
    setIdentificationNumber(paramTaxForm.getIdentificationNumber());
    setCompDiscData(paramTaxForm.getCompDiscData());
    setCompEntryDisc(paramTaxForm.getCompEntryDisc());
    setID(paramTaxForm.getID());
    setPrenote(paramTaxForm.getPrenote());
    setComment(paramTaxForm.getComment());
    setSupportsSuffix(paramTaxForm.getSupportsSuffix());
    setNoDefaultFields(paramTaxForm.getNoDefaultFields());
  }
  
  public String validate()
  {
    String str1 = this.comment;
    int i = 0;
    String[] arrayOfString1 = { "AN", "ID", "DT", "ID", "N2", "ID", "N2", "ID", "N2", "AN" };
    String[] arrayOfString2 = { "ID", "AN", "DT", "N2", "AN", "ID", "AN", "AN", "ID" };
    StringBuffer localStringBuffer = new StringBuffer();
    if ((getBankRoutingNumber() != null) && (getBankRoutingNumber().length() > 0) && (!RoutingNumberUtil.isValidRoutingNumber(getBankRoutingNumber()))) {
      jdMethod_for(localStringBuffer, "Invalid Routing Number found in taxforms.xml! '" + getBankRoutingNumber() + "' for " + getBankName() + " should be " + RoutingNumberUtil.getRoutingNumber_EightDigits(getBankRoutingNumber()) + RoutingNumberUtil.getRoutingNumber_CheckDigit(RoutingNumberUtil.getRoutingNumber_EightDigits(getBankRoutingNumber())), str1);
    }
    if ((this.individualName != null) && (this.individualName.length() > 22)) {
      jdMethod_for(localStringBuffer, "Individual Name field will be truncated when used to '" + this.individualName.substring(0, 22) + "' from '" + this.individualName + "'", str1);
    }
    if ((this.individualName == null) && (getBankName() != null) && (getBankName().length() > 22)) {
      jdMethod_for(localStringBuffer, "Bank Name field will be truncated when used to '" + getBankName().substring(0, 22) + "' from '" + getBankName() + "' (No IndividualName tag)", str1);
    }
    if ((getBankAccountNumber() != null) && (getBankAccountNumber().length() > 17)) {
      jdMethod_for(localStringBuffer, "Bank Account Number too long '" + getBankAccountNumber() + "'", str1);
    }
    if ((getCompEntryDisc() != null) && (getCompEntryDisc().length() > 10)) {
      jdMethod_for(localStringBuffer, "Company Entry Description too long '" + getCompEntryDisc() + "'", str1);
    }
    if (getDepositAccount() != null)
    {
      if ((getDepositAccount().getComment() != null) && (getDepositAccount().getComment().length() > 0)) {
        str1 = ((this.comment != null) && (this.comment.length() > 0) ? this.comment + ":" : "") + getDepositAccount().getComment();
      }
      if (getDepositAccount().isEmpty()) {
        jdMethod_for(localStringBuffer, "Deposit Account is empty/null", str1);
      } else if (!getDepositAccount().isValid()) {
        if (((getDepositAccount().getBankAccountNumber() == null) || (getDepositAccount().getBankAccountNumber().length() == 0)) && ((getDepositAccount().getBankRoutingNumber() == null) || (getDepositAccount().getBankRoutingNumber().length() == 0))) {
          jdMethod_for(localStringBuffer, "Deposit Account is not valid (routing number AND account number missing)", str1);
        } else if ((getDepositAccount().getBankAccountNumber() == null) || (getDepositAccount().getBankAccountNumber().length() == 0)) {
          jdMethod_for(localStringBuffer, "Deposit Account is not valid (account number missing)", str1);
        } else {
          jdMethod_for(localStringBuffer, "Deposit Account is not valid (routing number missing)", str1);
        }
      }
    }
    String str2;
    TaxField localTaxField;
    if (getAddendaFormat().equals("TXP")) {
      while (i < 10)
      {
        i++;
        str2 = "TXP" + (i < 10 ? "0" : "") + i;
        localTaxField = getFieldByName(str2);
        if ((localTaxField == null) && (i < 6)) {
          jdMethod_for(localStringBuffer, str2 + " is null", str1);
        }
        if (localTaxField != null)
        {
          String str3 = localTaxField.getValue();
          if (("TXP02".equals(localTaxField.getFieldname())) && (getName().indexOf("(" + str3 + ")") == -1) && (!localTaxField.getEditableValue())) {
            jdMethod_for(localStringBuffer, "Invalid Form Name in taxforms.xml! '" + getName() + "' Should include " + "(" + str3 + ")", str1);
          }
          if (!localTaxField.getDataType().equals(arrayOfString1[(i - 1)])) {
            jdMethod_for(localStringBuffer, localTaxField.getFieldname() + " is not type '" + arrayOfString1[(i - 1)] + "', found type " + "'" + localTaxField.getDataType() + "'", str1);
          }
          if (("DT".equals(localTaxField.getDataType())) && (localTaxField.getMaxLengthValue() != 6) && (localTaxField.getMaxLengthValue() != 8)) {
            jdMethod_for(localStringBuffer, localTaxField.getFieldname() + " is of type 'DT' but length is not 6 - it is length of " + localTaxField.getMaxLengthValue(), str1);
          }
          if ((!localTaxField.getEditableValue()) && ((str3 == null) || (str3.length() == 0)) && (!localTaxField.anyLeadingOrTrailing()) && ((i != 4) || (localTaxField.getMaxLengthValue() != 5))) {
            jdMethod_for(localStringBuffer, localTaxField.getFieldname() + " is not editable and has a value of " + "'" + str3 + "'", str1);
          }
          if (("ID".equals(localTaxField.getDataType())) && (localTaxField.getOptions() != null))
          {
            ArrayList localArrayList1 = new ArrayList();
            ArrayList localArrayList2 = new ArrayList();
            Iterator localIterator = localTaxField.getOptions().iterator();
            while (localIterator.hasNext())
            {
              KeyValue localKeyValue = (KeyValue)localIterator.next();
              str3 = localKeyValue.getValue();
              if (localArrayList1.contains(localKeyValue.getKey())) {
                jdMethod_for(localStringBuffer, localKeyValue.getKey() + " is already used as a key.  It is repeated - possibly by mistake", str1);
              }
              if (localArrayList2.contains(str3)) {
                jdMethod_for(localStringBuffer, str3 + " is already used as a value.  It is repeated - possibly by mistake", str1);
              }
              localArrayList2.add(str3);
              if ((str3 != null) && (str3.length() > localTaxField.getMaxLengthValue()) && (localTaxField.getMaxLengthValue() > 0)) {
                jdMethod_for(localStringBuffer, localTaxField.getFieldname() + "." + localKeyValue.getKey() + " is too long " + "'" + str3 + "' is longer than " + localTaxField.getMaxLengthValue(), str1);
              }
              if ((str3 != null) && (str3.length() < localTaxField.getMinLengthValue()) && (localTaxField.getMinLengthValue() > 0) && ((localTaxField.getRequiredValue()) || (str3.length() > 0)) && (!localTaxField.anyLeadingOrTrailing())) {
                jdMethod_for(localStringBuffer, localTaxField.getFieldname() + "." + localKeyValue.getKey() + " is too short " + "'" + str3 + "' is shorter than " + localTaxField.getMinLengthValue(), str1);
              }
              localArrayList1.add(localKeyValue.getKey());
            }
          }
          else
          {
            if ((str3 != null) && (str3.length() > localTaxField.getMaxLengthValue()) && (localTaxField.getMaxLengthValue() > 0)) {
              jdMethod_for(localStringBuffer, localTaxField.getFieldname() + " is too long " + "'" + str3 + "' is longer than " + localTaxField.getMaxLengthValue(), str1);
            }
            if ((str3 != null) && (str3.length() < localTaxField.getMinLengthValue()) && (localTaxField.getMinLengthValue() > 0) && (!localTaxField.anyLeadingOrTrailing())) {
              jdMethod_for(localStringBuffer, localTaxField.getFieldname() + " is too short " + "'" + str3 + "' is shorter than " + localTaxField.getMinLengthValue(), str1);
            }
          }
        }
      }
    }
    if (getAddendaFormat().equals("DED")) {
      while (i < 10)
      {
        i++;
        str2 = "DED" + (i < 10 ? "0" : "") + i;
        localTaxField = getFieldByName(str2);
        if ((localTaxField == null) && (i < 7)) {
          jdMethod_for(localStringBuffer, str2 + " is null", str1);
        }
        if (localTaxField != null)
        {
          if (!localTaxField.getDataType().equals(arrayOfString2[(i - 1)])) {
            jdMethod_for(localStringBuffer, localTaxField.getFieldname() + " is not type '" + arrayOfString2[(i - 1)] + "', found type " + "'" + localTaxField.getDataType() + "'", str1);
          }
          if ((!localTaxField.getEditableValue()) && ((localTaxField.getValue() == null) || (localTaxField.getValue().length() == 0))) {
            jdMethod_for(localStringBuffer, localTaxField.getFieldname() + " is not editable and has a value of " + "'" + localTaxField.getValue() + "'", str1);
          }
          if (("DT".equals(localTaxField.getDataType())) && (localTaxField.getMaxLengthValue() != 6) && (localTaxField.getMaxLengthValue() != 8)) {
            jdMethod_for(localStringBuffer, localTaxField.getFieldname() + " is of type 'DT' but length is not 6 - it is length of " + localTaxField.getMaxLengthValue(), str1);
          }
          if ((localTaxField.getValue() != null) && (localTaxField.getValue().length() > localTaxField.getMaxLengthValue()) && (localTaxField.getMaxLengthValue() > 0)) {
            jdMethod_for(localStringBuffer, localTaxField.getFieldname() + " is too long " + "'" + localTaxField.getValue() + "' is longer than " + localTaxField.getMaxLengthValue(), str1);
          }
        }
      }
    }
    return localStringBuffer.toString();
  }
  
  private void jdMethod_for(StringBuffer paramStringBuffer, String paramString1, String paramString2)
  {
    paramStringBuffer.append("\n***ERROR** ").append(paramString1).append((paramString2 != null) && (paramString2.length() > 0) ? " ( " + paramString2 + " )" : "");
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
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "TAXFORM");
    XMLHandler.appendTag(localStringBuffer, "NAME", getName());
    XMLHandler.appendTag(localStringBuffer, "ABBR", getState());
    XMLHandler.appendTag(localStringBuffer, "TYPE", getType());
    XMLHandler.appendTag(localStringBuffer, "BANKNAME", getBankName());
    XMLHandler.appendTag(localStringBuffer, "ROUTINGNO", getBankRoutingNumber());
    XMLHandler.appendTag(localStringBuffer, "ACCOUNTNO", getBankAccountNumber());
    XMLHandler.appendTag(localStringBuffer, "ACCOUNTTYPE", getBankAccountType());
    XMLHandler.appendTag(localStringBuffer, "INDIVIDUALNAME", getIndividualName());
    XMLHandler.appendTag(localStringBuffer, "IDENTIFICATIONNUMBER", getIdentificationNumber());
    XMLHandler.appendTag(localStringBuffer, "COMPANYDISCRETIONARYDATA", getCompDiscData());
    XMLHandler.appendTag(localStringBuffer, "COMPANYENTRYDISCRIPTION", getCompEntryDisc());
    XMLHandler.appendTag(localStringBuffer, "COMMENT", getComment());
    XMLHandler.appendTag(localStringBuffer, "PRENOTE", getPrenote());
    if (this.bank != null) {
      localStringBuffer.append(this.bank.getXML());
    }
    XMLHandler.appendTag(localStringBuffer, "ID", getID());
    if (this.fields != null)
    {
      Iterator localIterator = this.fields.iterator();
      while (localIterator.hasNext())
      {
        TaxField localTaxField = (TaxField)localIterator.next();
        localStringBuffer.append(localTaxField.getXML());
      }
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "TAXFORM");
    return localStringBuffer.toString();
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, TaxForm paramTaxForm, String paramString)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "ID", paramTaxForm.getID(), getID(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "NAME", paramTaxForm.getName(), getName(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "ABBR", paramTaxForm.getState(), getState(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "TYPE", paramTaxForm.getType(), getType(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "BANKNAME", paramTaxForm.getBankName(), getBankName(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "ROUTINGNO", paramTaxForm.getBankRoutingNumber(), getBankRoutingNumber(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "ACCOUNTNO", paramTaxForm.getBankAccountNumber(), getBankAccountNumber(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "ACCOUNTTYPE", paramTaxForm.getBankAccountType(), getBankAccountType(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "INDIVIDUALNAME", paramTaxForm.getIndividualName(), getIndividualName(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "IDENTIFICATIONNUMBER", paramTaxForm.getIdentificationNumber(), getIdentificationNumber(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "COMPANYDISCRETIONARYDATA", paramTaxForm.getCompDiscData(), getCompDiscData(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "COMPANYENTRYDISCRIPTION", paramTaxForm.getCompEntryDisc(), getCompEntryDisc(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "PRENOTE", paramTaxForm.getPrenote(), getPrenote(), paramString);
    super.logChanges(paramHistoryTracker, paramTaxForm, paramString);
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, TaxForm paramTaxForm, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "ID", paramTaxForm.getID(), getID(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "NAME", paramTaxForm.getName(), getName(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "ABBR", paramTaxForm.getState(), getState(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "TYPE", new LocalizableString("com.ffusion.beans.ach.resources", "TaxFormType" + paramTaxForm.getTypeValue(), null), new LocalizableString("com.ffusion.beans.ach.resources", "TaxFormType" + getTypeValue(), null), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "BANKNAME", paramTaxForm.getBankName(), getBankName(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "ROUTINGNO", paramTaxForm.getBankRoutingNumber(), getBankRoutingNumber(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "ACCOUNTNO", paramTaxForm.getBankAccountNumber(), getBankAccountNumber(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "ACCOUNTTYPE", paramTaxForm.getBankAccountType(), getBankAccountType(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "INDIVIDUALNAME", paramTaxForm.getIndividualName(), getIndividualName(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "IDENTIFICATIONNUMBER", paramTaxForm.getIdentificationNumber(), getIdentificationNumber(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "COMPANYDISCRETIONARYDATA", paramTaxForm.getCompDiscData(), getCompDiscData(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "COMPANYENTRYDISCRIPTION", paramTaxForm.getCompEntryDisc(), getCompEntryDisc(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "PRENOTE", paramTaxForm.getPrenoteValue(), getPrenoteValue(), paramILocalizable);
    super.logChanges(paramHistoryTracker, paramTaxForm, paramILocalizable);
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "ID");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    TaxForm localTaxForm = (TaxForm)paramObject;
    int i = 1;
    if ((paramString.equals("ID")) && (getID() != null) && (localTaxForm.getID() != null)) {
      i = getID().compareTo(localTaxForm.getID());
    } else if ((paramString.equals("NAME")) && (getName() != null) && (localTaxForm.getName() != null)) {
      i = getName().compareTo(localTaxForm.getName());
    } else if ((paramString.equals("STATE")) && (getState() != null) && (localTaxForm.getState() != null)) {
      i = getState().compareTo(localTaxForm.getState());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public Object get(Object paramObject)
  {
    String str1 = (String)paramObject;
    String str2 = null;
    if (str1.equalsIgnoreCase("ID")) {
      str2 = getID();
    } else if (str1.equalsIgnoreCase("NAME")) {
      str2 = getName();
    } else if (str1.equalsIgnoreCase("STATE")) {
      str2 = getState();
    } else if (containsKey(str1)) {
      str2 = (String)super.get(paramObject);
    }
    return str2;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if (paramString1.equals("TYPE")) {
      return isFilterable(getType(), paramString2, paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  public String getAddendaFormat()
  {
    int i = this.fields.size();
    for (int j = 0; j < i; j++)
    {
      String str = ((TaxField)this.fields.get(j)).getFieldname();
      if ((str != null) && (str.length() >= 3)) {
        return str.substring(0, 3);
      }
    }
    return "";
  }
  
  public String getFieldValue(String paramString)
  {
    String str = null;
    TaxField localTaxField = getFieldByName(paramString);
    if (localTaxField != null) {
      str = localTaxField.getValue();
    }
    return str;
  }
  
  public TaxField getFieldByName(String paramString)
  {
    int i = this.fields.size();
    for (int j = 0; j < i; j++)
    {
      TaxField localTaxField = (TaxField)this.fields.get(j);
      if (localTaxField.getFieldname().equalsIgnoreCase(paramString)) {
        return localTaxField;
      }
    }
    return null;
  }
  
  public void setCurrentFieldName(String paramString)
  {
    this.currentFieldName = paramString;
  }
  
  public String getCurrentFieldValue()
  {
    return getFieldValue(this.currentFieldName);
  }
  
  public String getCurrentFieldFormattedValue()
  {
    TaxField localTaxField = getFieldByName(this.currentFieldName);
    String str = localTaxField.getValue();
    if (localTaxField.getDataType().equalsIgnoreCase("DT"))
    {
      try
      {
        DateTime localDateTime = new DateTime(str, this.locale, localTaxField.getMaxLengthValue() == 8 ? "yyyyMMdd" : "yyMMdd");
        str = new DateTime(localDateTime.getTime(), this.locale, this.datetype).toString();
      }
      catch (Throwable localThrowable) {}
    }
    else if ((localTaxField.getDataType().equalsIgnoreCase("N2")) && (str != null))
    {
      int i = str.length();
      if (i >= 2) {
        str = str.substring(0, i - 2) + "." + str.substring(i - 2);
      }
    }
    return str;
  }
  
  public Object clone()
  {
    try
    {
      TaxForm localTaxForm = (TaxForm)super.clone();
      ArrayList localArrayList = new ArrayList();
      Iterator localIterator = this.fields.iterator();
      Object localObject2;
      while (localIterator.hasNext())
      {
        localObject1 = (TaxField)localIterator.next();
        localObject2 = new TaxField();
        ((TaxField)localObject2).set((TaxField)localObject1);
        localArrayList.add(localObject2);
      }
      localTaxForm.fields = localArrayList;
      Object localObject1 = new ArrayList();
      localIterator = this.depositAccounts.iterator();
      while (localIterator.hasNext())
      {
        localObject2 = new TaxFormBank();
        TaxFormBank localTaxFormBank = (TaxFormBank)localIterator.next();
        ((TaxFormBank)localObject2).set(localTaxFormBank);
        ((ArrayList)localObject1).add(localObject2);
      }
      localTaxForm.depositAccounts = ((ArrayList)localObject1);
      localTaxForm.bank.set(this.bank);
      return localTaxForm;
    }
    catch (Exception localException) {}
    return null;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(getID()).append(',');
    if (getState() != null) {
      localStringBuffer.append(getState()).append('_').append(getType()).append(',');
    } else {
      localStringBuffer.append(getType()).append(',');
    }
    localStringBuffer.append(getName());
    if (getIndividualName() != null) {
      localStringBuffer.append(",IN=").append(getIndividualName());
    }
    if (getIdentificationNumber() != null) {
      localStringBuffer.append(",ID=").append(getIdentificationNumber());
    }
    if (getCompEntryDisc() != null) {
      localStringBuffer.append(",ED=").append(getCompEntryDisc());
    }
    Iterator localIterator1 = this.depositAccounts.iterator();
    Object localObject;
    while (localIterator1.hasNext())
    {
      localObject = (TaxFormBank)localIterator1.next();
      if (((TaxFormBank)localObject).getID() != null)
      {
        localStringBuffer.append("\n     ").append(((TaxFormBank)localObject).getBankRoutingNumber()).append(':');
        localStringBuffer.append(((TaxFormBank)localObject).getBankAccountNumber());
        if (((TaxFormBank)localObject).getBankAccountType() != null) {
          localStringBuffer.append('(').append(((TaxFormBank)localObject).getBankAccountType()).append("):");
        } else {
          localStringBuffer.append("(checking):");
        }
        localStringBuffer.append(((TaxFormBank)localObject).getBankName());
      }
    }
    if (this.fields != null)
    {
      Iterator localIterator2 = this.fields.iterator();
      while (localIterator2.hasNext())
      {
        localObject = (TaxField)localIterator2.next();
        localStringBuffer.append("\n     " + ((TaxField)localObject).toString());
      }
    }
    localStringBuffer.append('\n');
    return localStringBuffer.toString();
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
  
  class a
    extends XMLHandler
  {
    public a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equalsIgnoreCase("DEPOSITACCOUNT"))
      {
        TaxForm.this.bank = new TaxFormBank();
        TaxForm.this.depositAccounts.add(TaxForm.this.bank);
        TaxForm.this.bank.continueXMLParsing(getHandler());
      }
      else if (paramString.equalsIgnoreCase("FIELD"))
      {
        TaxField localTaxField = new TaxField();
        TaxForm.this.fields.add(localTaxField);
        localTaxField.continueXMLParsing(getHandler());
      }
    }
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str1 = new String(paramArrayOfChar, paramInt1, paramInt2);
      str1 = str1.trim();
      String str2 = getElement();
      if (str2.equalsIgnoreCase("NAME")) {
        TaxForm.this.setName(str1);
      } else if (str2.equalsIgnoreCase("ABBR")) {
        TaxForm.this.setState(str1);
      } else if (str2.equalsIgnoreCase("BANKNAME")) {
        TaxForm.this.setBankName(str1);
      } else if (str2.equalsIgnoreCase("ROUTINGNO")) {
        TaxForm.this.setBankRoutingNumber(str1);
      } else if (str2.equalsIgnoreCase("ACCOUNTNO")) {
        TaxForm.this.setBankAccountNumber(str1);
      } else if (str2.equalsIgnoreCase("ACCOUNTTYPE")) {
        TaxForm.this.setBankAccountType(str1);
      } else if (str2.equalsIgnoreCase("INDIVIDUALNAME")) {
        TaxForm.this.setIndividualName(str1);
      } else if (str2.equalsIgnoreCase("IDENTIFICATIONNUMBER")) {
        TaxForm.this.setIdentificationNumber(str1);
      } else if (str2.equalsIgnoreCase("COMPANYDISCRETIONARYDATA")) {
        TaxForm.this.setCompDiscData(str1);
      } else if ((str2.equalsIgnoreCase("COMPANYENTRYDISCRIPTION")) || (str2.equalsIgnoreCase("COMPANYENTRYDESCRIPTION"))) {
        TaxForm.this.setCompEntryDisc(str1);
      } else if (str2.equalsIgnoreCase("PRENOTE")) {
        TaxForm.this.setPrenote(str1);
      } else if (str2.equalsIgnoreCase("ID")) {
        TaxForm.this.setID(str1);
      } else if (str2.equalsIgnoreCase("TYPE")) {
        TaxForm.this.setType(str1);
      } else if (str2.equalsIgnoreCase("COMMENT")) {
        TaxForm.this.setComment(str1);
      } else if (str2.equalsIgnoreCase("SUPPORTSSUFFIXES")) {
        TaxForm.this.setSupportsSuffix(str1);
      } else if (str2.equalsIgnoreCase("NODEFAULTFIELDS")) {
        TaxForm.this.setNoDefaultFields(str1);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.ach.TaxForm
 * JD-Core Version:    0.7.0.1
 */