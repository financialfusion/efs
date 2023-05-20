package com.ffusion.beans.register;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.banking.Transaction;
import com.ffusion.util.LocaleUtil;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean;
import java.math.BigDecimal;
import java.text.Collator;
import java.util.Iterator;
import java.util.Locale;

public class RegisterTransaction
  extends Transaction
{
  public static final String REGISTER_TRANSACTION = "REGISTER_TRANSACTION";
  public static final String REG_CAT_UNASSIGNED_TYPE = "0";
  public static final int REG_TYPE_UNKNOWN = -1;
  public static final int REG_TYPE_ATM = 0;
  public static final int REG_TYPE_CHECK = 1;
  public static final int REG_TYPE_DEBIT = 2;
  public static final int REG_TYPE_DEPOSIT = 3;
  public static final int REG_TYPE_OTHER = 30;
  public static final int REG_TYPE_PAYMENT = 5;
  public static final int REG_TYPE_TRANSFER = 6;
  public static final int MANUAL = 0;
  public static final int BANK = 1;
  public static final int RECONCILED = 2;
  public static final int MATCH_RECONCILED = 3;
  public static final int MATCH_DISCREPANCY = 4;
  public static final int ADD = 0;
  public static final String REGISTER_ID = "REGISTER_ID";
  public static final String STATUS = "STATUS";
  public static final String DATE_ISSUED = "DATE_ISSUED";
  public static final String DATE_ISSUED_MINUS_TIME = "DATE_ISSUED_MINUS_TIME";
  public static final String PAYEE_NAME = "PAYEE_NAME";
  public static final String CATEGORIES = "CATEGORIES";
  public static final String RECONCILE_MATCH = "RECONCILE_MATCH";
  public static final String DISCREPANCY = "DISCREPANCY";
  public static final String TOTAL_AMOUNT = "TOTAL_AMOUNT";
  public static final String REGISTER_TYPE = "REGISTER_TYPE";
  public static final String REGISTER_TYPE_ABBR = "REGISTER_TYPE_ABBR";
  public static final String REFERENCE_NUMBER = "REFERENCE_NUMBER";
  public static final String CHECK_NUMBER = "CHECK_NUMBER";
  public static final String TRANSFER_MATCH = "TRANSFER_MATCH";
  public static final String SERVER_TID = "SERVER_TID";
  public static final String RECSRVRTID = "RECSRVRTID";
  public static final String TRANSACTION_TYPE = "TransactionType";
  public static final String TRANSACTION_TYPE_ABBR = "TransactionTypeAbbr";
  public static final String TRANSFER_TEXT = "TransferText";
  public static final String TRANSFER_TO_TEXT = "TransferToText";
  public static final String TRANSFER_FROM_TEXT = "TransferFromText";
  public static final String DISCREPANCY_TEXT = "DiscrepancyText";
  public static final String DATE_RANGE = "DATE_RANGE";
  public static final String DATE_RANGE_IN = "in";
  public static final String DATE_RANGE_BEFORE = "before";
  public static final String DATE_RANGE_AFTER = "after";
  public static final String DATE_RANGE_UNKNOWN = "unknown";
  public static final String RESOURCE_BUNDLE = "com.ffusion.beans.register.resources";
  public static final String TRANSACTION_TYPE_RESOURCE_FILE = "TransactionTypeResourceFile";
  protected int registerId = 0;
  protected String payeeName = null;
  protected int status = 0;
  protected DateTime dateIssued = null;
  protected boolean discrepancy = false;
  protected String reconcileMatch = null;
  protected TransactionCategories transactionCategories = new TransactionCategories();
  public static final String KEY_MESSAGE_ROUTING_NUM = "TransactionProperty0";
  public static final String KEY_MESSAGE_ACCOUNT = "TransactionProperty1";
  public static final String KEY_MESSAGE_DATE = "TransactionProperty2";
  public static final String KEY_MESSAGE_AMOUNT = "TransactionProperty3";
  public static final String KEY_MESSAGE_TRANSACTION_TYPE = "TransactionProperty4";
  public static final String KEY_MESSAGE_REFERENCE_NUM = "TransactionProperty5";
  public static final String KEY_MESSAGE_MEMO = "TransactionProperty6";
  public static final String KEY_MESSAGE_PAYEE_PAYOR = "TransactionProperty7";
  public static final String KEY_MESSAGE_RECONCILIATION_STATUS = "TransactionProperty8";
  public static final String KEY_RECONCILIATION_STATUS_INPROCESS = "ReconciliationStatus0";
  public static final String KEY_RECONCILIATION_STATUS_RECONCILED = "ReconciliationStatus1";
  public static final String KEY_RECONCILIATION_STATUS_MATCHED_RECONCILED = "ReconciliationStatus2";
  public static final String KEY_RECONCILIATION_STATUS_MATCHED_RECONCILED_WITH_DISCREPANCY = "ReconciliationStatus3";
  
  public RegisterTransaction() {}
  
  public RegisterTransaction(DateTime paramDateTime, int paramInt)
  {
    this.dateIssued = paramDateTime;
    this.status = paramInt;
  }
  
  public RegisterTransaction(DateTime paramDateTime)
  {
    this.dateIssued = paramDateTime;
  }
  
  public RegisterTransaction(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public RegisterTransaction(Transaction paramTransaction)
  {
    super(paramTransaction.getLocale());
    set(paramTransaction);
  }
  
  public void setRegisterId(String paramString)
  {
    this.registerId = Integer.parseInt(paramString);
  }
  
  public void setRegisterId(int paramInt)
  {
    this.registerId = paramInt;
  }
  
  public String getRegisterId()
  {
    return String.valueOf(this.registerId);
  }
  
  public int getRegisterIdValue()
  {
    return this.registerId;
  }
  
  public void setPayeeName(String paramString)
  {
    this.payeeName = paramString;
  }
  
  public String getPayeeName()
  {
    return this.payeeName;
  }
  
  public void setStatus(String paramString)
  {
    this.status = Integer.parseInt(paramString);
  }
  
  public void setStatus(int paramInt)
  {
    this.status = paramInt;
  }
  
  public void setType(String paramString)
  {
    setType(Integer.parseInt(paramString));
  }
  
  public String getStatus()
  {
    return String.valueOf(this.status);
  }
  
  public int getStatusValue()
  {
    return this.status;
  }
  
  public String getDate()
  {
    if (getDateValue() == null) {
      return "";
    }
    return super.getDate();
  }
  
  public void setDate(DateTime paramDateTime)
  {
    if (paramDateTime != null) {
      super.setDate(paramDateTime);
    }
  }
  
  public void setDateIssued(String paramString)
  {
    try
    {
      if ((paramString == null) || (paramString.equals("")))
      {
        this.dateIssued = null;
      }
      else
      {
        if (this.dateIssued == null) {
          this.dateIssued = new DateTime(this.locale);
        }
        this.dateIssued.setFormat(this.datetype);
        this.dateIssued.fromString(paramString);
        this.dateIssued.set(11, 23);
        this.dateIssued.set(12, 59);
        this.dateIssued.set(13, 59);
        this.dateIssued.set(14, 999);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      this.dateIssued.fromXMLFormat(paramString);
    }
  }
  
  public void setDateIssued(DateTime paramDateTime)
  {
    this.dateIssued = paramDateTime;
  }
  
  public String getDateIssued()
  {
    if (this.dateIssued == null) {
      return null;
    }
    this.dateIssued.setFormat(this.datetype);
    return this.dateIssued.toString();
  }
  
  public DateTime getDateIssuedValue()
  {
    return this.dateIssued;
  }
  
  public void setCurrent(String paramString)
  {
    if (this.transactionCategories != null) {
      this.transactionCategories.setCurrent(paramString);
    }
  }
  
  public void setRemoveCategory(String paramString)
  {
    if (this.transactionCategories != null) {
      this.transactionCategories.setRemoveCategory(paramString);
    }
  }
  
  public void setAmount(String paramString)
  {
    if (this.transactionCategories != null) {
      this.transactionCategories.setAmount(paramString);
    }
  }
  
  public void setAmount(Currency paramCurrency)
  {
    if (this.transactionCategories != null) {
      this.transactionCategories.setAmount(paramCurrency);
    }
  }
  
  public String getAmount()
  {
    if (this.transactionCategories == null) {
      return null;
    }
    return this.transactionCategories.getAmount();
  }
  
  public String getAmountWithoutDollarSign()
  {
    Currency localCurrency = getAmountValue();
    return localCurrency.getCurrencyStringNoSymbol_1();
  }
  
  public Currency getAmountValue()
  {
    if (this.transactionCategories == null) {
      return null;
    }
    return this.transactionCategories.getAmountValue();
  }
  
  public String getDecimalAmount()
  {
    if (this.transactionCategories != null) {
      return this.transactionCategories.getDecimalAmount();
    }
    return null;
  }
  
  public String getDecimalAbsoluteAmount()
  {
    if (this.transactionCategories != null) {
      return this.transactionCategories.getDecimalAbsoluteAmount();
    }
    return null;
  }
  
  public void clearCategories()
  {
    this.transactionCategories = new TransactionCategories();
  }
  
  public void setClearCategories(String paramString)
  {
    clearCategories();
  }
  
  public void setRegisterCategoryId(int paramInt)
  {
    if (this.transactionCategories != null) {
      this.transactionCategories.setRegisterCategoryId(paramInt);
    }
  }
  
  public void setRegisterCategoryId(String paramString)
  {
    setRegisterCategoryId(Integer.parseInt(paramString));
  }
  
  public String getRegisterCategoryId()
  {
    if (this.transactionCategories == null) {
      return null;
    }
    return this.transactionCategories.getRegisterCategoryId();
  }
  
  public void setDiscrepancy(String paramString)
  {
    this.discrepancy = paramString.equalsIgnoreCase("true");
  }
  
  public void setDiscrepancy(boolean paramBoolean)
  {
    this.discrepancy = paramBoolean;
  }
  
  public String getDiscrepancy()
  {
    return String.valueOf(this.discrepancy);
  }
  
  public boolean getDiscrepancyValue()
  {
    return this.discrepancy;
  }
  
  public boolean getMultipleCategories()
  {
    return (this.transactionCategories != null) && (this.transactionCategories.size() > 1);
  }
  
  public String getNumberOfCategories()
  {
    if (this.transactionCategories == null) {
      return "0";
    }
    return this.transactionCategories.size() + "";
  }
  
  public void setReconcileMatch(String paramString)
  {
    try
    {
      this.reconcileMatch = paramString;
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public String getReconcileMatch()
  {
    return this.reconcileMatch;
  }
  
  public void setReferenceNumber(String paramString)
  {
    if ((paramString == null) || (paramString.length() <= 0)) {
      super.setReferenceNumber(null);
    } else {
      super.setReferenceNumber(paramString);
    }
  }
  
  public void setTransactionCategories(TransactionCategories paramTransactionCategories)
  {
    this.transactionCategories = paramTransactionCategories;
  }
  
  public TransactionCategories getTransactionCategories()
  {
    return this.transactionCategories;
  }
  
  public void setRegisterType(int paramInt)
  {
    setType(jdMethod_new(paramInt));
  }
  
  public void setRegisterType(String paramString)
  {
    try
    {
      setRegisterType(Integer.parseInt(paramString));
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public String getRegisterType()
  {
    try
    {
      return ResourceUtil.getString("TransactionType" + getTypeValue(), "com.ffusion.beans.register.resources", this.locale);
    }
    catch (Exception localException) {}
    return String.valueOf(jdMethod_try(getTypeValue()));
  }
  
  public int getRegisterTypeValue()
  {
    return Integer.parseInt(getRegisterType());
  }
  
  public String getRegisterTypeName()
  {
    try
    {
      String str = ResourceUtil.getString("TransactionTypeResourceFile", "com.ffusion.beans.register.resources", this.locale);
      return ResourceUtil.getString("TransactionType" + getRegisterType(), str, this.locale);
    }
    catch (Exception localException) {}
    return ResourceUtil.getString("TranType." + String.valueOf(jdMethod_try(getTypeValue())), "com.ffusion.beans.register.resources", this.locale);
  }
  
  public String getRegisterTypeAbbr()
  {
    try
    {
      String str = ResourceUtil.getString("TransactionTypeResourceFile", "com.ffusion.beans.register.resources", this.locale);
      return ResourceUtil.getString("TransactionTypeAbbr" + getRegisterType(), str, this.locale);
    }
    catch (Exception localException) {}
    return ResourceUtil.getString("TranTypeAbbr." + String.valueOf(jdMethod_try(getTypeValue())), "com.ffusion.beans.register.resources", this.locale);
  }
  
  public static boolean usingNewTranTypes()
  {
    try
    {
      ResourceUtil.getString("TransactionType1", "com.ffusion.beans.register.resources", LocaleUtil.getDefaultLocale());
    }
    catch (Exception localException)
    {
      return false;
    }
    return true;
  }
  
  private int jdMethod_try(int paramInt)
  {
    int i = 30;
    switch (paramInt)
    {
    case 6: 
    case 7: 
    case 28: 
      i = 0;
      break;
    case 13: 
    case 25: 
      i = 5;
      break;
    case 3: 
      i = 1;
      break;
    case 5: 
    case 12: 
      i = 2;
      break;
    case 1: 
    case 29: 
      i = 3;
      break;
    case 16: 
      i = 6;
      break;
    case 0: 
    case 1000: 
      i = -1;
    }
    return i;
  }
  
  private int jdMethod_new(int paramInt)
  {
    int i = 30;
    switch (paramInt)
    {
    case 0: 
      i = 7;
      break;
    case 5: 
      i = 25;
      break;
    case 1: 
      i = 3;
      break;
    case 2: 
      i = 5;
      break;
    case 3: 
      i = 1;
      break;
    case 6: 
      i = 16;
      break;
    case -1: 
      i = 0;
    }
    return i;
  }
  
  public boolean hasNegativeAmount()
  {
    if (this.transactionCategories == null) {
      return false;
    }
    return this.transactionCategories.hasNegativeAmount();
  }
  
  public void setAmountsNegative()
  {
    if (this.transactionCategories == null) {
      return;
    }
    this.transactionCategories.setAmountsNegative();
  }
  
  public void setAmountsBasedonCategory(RegisterCategories paramRegisterCategories)
  {
    if (this.transactionCategories == null) {
      return;
    }
    Iterator localIterator = this.transactionCategories.iterator();
    while (localIterator.hasNext())
    {
      TransactionCategory localTransactionCategory = (TransactionCategory)localIterator.next();
      RegisterCategory localRegisterCategory = paramRegisterCategories.getById(localTransactionCategory.getRegisterCategoryId());
      if (localRegisterCategory != null)
      {
        if ((localRegisterCategory.getTypeValue() == 2) || (localRegisterCategory.getTypeValue() == 0))
        {
          if ((localTransactionCategory.getAmountValue() != null) && (localTransactionCategory.getAmountValue().getAmountValue().doubleValue() < 0.0D)) {
            localTransactionCategory.negateAmount();
          }
        }
        else if ((localTransactionCategory.getAmountValue() != null) && (localTransactionCategory.getAmountValue().getAmountValue().doubleValue() > 0.0D)) {
          localTransactionCategory.negateAmount();
        }
      }
      else if ((localTransactionCategory.getRegisterCategoryId().equals("0")) && (localTransactionCategory.getAmountValue() != null) && (localTransactionCategory.getAmountValue().getAmountValue().doubleValue() > 0.0D)) {
        localTransactionCategory.negateAmount();
      }
    }
  }
  
  public void setAmountsPositive()
  {
    if (this.transactionCategories == null) {
      return;
    }
    this.transactionCategories.setAmountsPositive();
  }
  
  public void setCurrency(String paramString)
  {
    if (this.transactionCategories == null) {
      return;
    }
    this.transactionCategories.setCurrency(paramString);
  }
  
  public void set(RegisterTransaction paramRegisterTransaction)
  {
    setRegisterId(paramRegisterTransaction.getRegisterId());
    setPayeeName(paramRegisterTransaction.getPayeeName());
    setStatus(paramRegisterTransaction.getStatusValue());
    setType(paramRegisterTransaction.getTypeValue());
    if (paramRegisterTransaction.getDateIssuedValue() != null) {
      setDateIssued((DateTime)paramRegisterTransaction.getDateIssuedValue().clone());
    } else {
      setDateIssued((DateTime)null);
    }
    setDiscrepancy(paramRegisterTransaction.getDiscrepancyValue());
    setReconcileMatch(paramRegisterTransaction.getReconcileMatch());
    TransactionCategories localTransactionCategories1 = paramRegisterTransaction.getTransactionCategories();
    TransactionCategories localTransactionCategories2 = new TransactionCategories();
    Iterator localIterator = localTransactionCategories1.iterator();
    while (localIterator.hasNext())
    {
      TransactionCategory localTransactionCategory1 = (TransactionCategory)localIterator.next();
      TransactionCategory localTransactionCategory2 = new TransactionCategory();
      localTransactionCategory2.set(localTransactionCategory1);
      localTransactionCategories2.add(localTransactionCategory2);
    }
    setTransactionCategories(localTransactionCategories2);
    super.setID(paramRegisterTransaction.getID());
    super.setType(paramRegisterTransaction.getTypeValue());
    super.setDescription(paramRegisterTransaction.getDescription());
    super.setReferenceNumber(paramRegisterTransaction.getReferenceNumber());
    super.setMemo(paramRegisterTransaction.getMemo());
    if (paramRegisterTransaction.getDateValue() != null) {
      super.setDate((DateTime)paramRegisterTransaction.getDateValue().clone());
    }
    super.set(paramRegisterTransaction);
  }
  
  public int compare(Object paramObject, String paramString)
  {
    RegisterTransaction localRegisterTransaction = (RegisterTransaction)paramObject;
    int i = 1;
    Collator localCollator = doGetCollator();
    if (paramString.equalsIgnoreCase("REGISTER_ID"))
    {
      if (this.registerId > localRegisterTransaction.getRegisterIdValue()) {
        i = 1;
      } else if (this.registerId < localRegisterTransaction.getRegisterIdValue()) {
        i = -1;
      } else {
        i = 0;
      }
    }
    else if (paramString.equalsIgnoreCase("PAYEE_NAME"))
    {
      i = ExtendABean.compareStrings(this.payeeName, localRegisterTransaction.getPayeeName(), localCollator);
    }
    else if (paramString.equalsIgnoreCase("REFERENCE_NUMBER"))
    {
      i = super.compare(paramObject, "REFERENCENUMBER");
    }
    else if (paramString.equalsIgnoreCase("CHECK_NUMBER"))
    {
      if (((getTypeValue() == 3) && (localRegisterTransaction.getTypeValue() == 3)) || ((getTypeValue() != 3) && (localRegisterTransaction.getTypeValue() != 3))) {
        i = super.compare(paramObject, "REFERENCENUMBER");
      } else if (getTypeValue() == 3) {
        i = 1;
      } else {
        i = -1;
      }
    }
    else if (paramString.equalsIgnoreCase("REGISTER_TYPE"))
    {
      i = ExtendABean.compareStrings(getRegisterTypeName(), localRegisterTransaction.getRegisterTypeName(), localCollator);
    }
    else if (paramString.equalsIgnoreCase("REGISTER_TYPE_ABBR"))
    {
      i = ExtendABean.compareStrings(getRegisterTypeAbbr(), localRegisterTransaction.getRegisterTypeAbbr(), localCollator);
    }
    else if (paramString.equalsIgnoreCase("STATUS"))
    {
      if (this.status > localRegisterTransaction.getStatusValue()) {
        i = 1;
      } else if (this.status < localRegisterTransaction.getStatusValue()) {
        i = -1;
      } else {
        i = 0;
      }
    }
    else if (paramString.equalsIgnoreCase("DATE_ISSUED"))
    {
      if ((this.dateIssued != null) && (localRegisterTransaction.getDateIssuedValue() != null))
      {
        i = this.dateIssued.compare(localRegisterTransaction.getDateIssuedValue());
        if (i == 0) {
          if (this.registerId > localRegisterTransaction.getRegisterIdValue()) {
            i = 1;
          } else if (this.registerId < localRegisterTransaction.getRegisterIdValue()) {
            i = -1;
          }
        }
      }
      else if ((this.dateIssued == null) && (localRegisterTransaction.getDateIssuedValue() == null))
      {
        i = 0;
      }
      else if (this.dateIssued == null)
      {
        i = -1;
      }
      else
      {
        i = 1;
      }
    }
    else if ((paramString.equalsIgnoreCase("DATE_ISSUED_MINUS_TIME")) && (this.dateIssued != null) && (localRegisterTransaction.getDateIssuedValue() != null))
    {
      int j = this.dateIssued.get(1);
      int k = localRegisterTransaction.getDateIssuedValue().get(1);
      if (j > k)
      {
        i = 1;
      }
      else if (j < k)
      {
        i = -1;
      }
      else
      {
        int m = this.dateIssued.get(6);
        int n = localRegisterTransaction.getDateIssuedValue().get(6);
        if (m > n) {
          i = 1;
        } else if (m < n) {
          i = -1;
        } else {
          i = 0;
        }
      }
    }
    else
    {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    String str1 = paramString1.toUpperCase();
    if (str1.equals("REGISTER_ID")) {
      return isFilterable(String.valueOf(this.registerId), paramString2, paramString3);
    }
    if (str1.equals("STATUS")) {
      return isFilterable(String.valueOf(this.status), paramString2, paramString3);
    }
    if ((str1.equals("PAYEE_NAME")) && (this.payeeName != null)) {
      return isFilterable(this.payeeName, paramString2, paramString3);
    }
    if (str1.equals("RECONCILE_MATCH")) {
      return isFilterable(String.valueOf(this.reconcileMatch), paramString2, paramString3);
    }
    if (str1.equals("DATE_ISSUED"))
    {
      if (this.dateIssued == null) {
        return isFilterable(null, paramString2, paramString3);
      }
      return isFilterable(this.dateIssued.toString(), paramString2, paramString3);
    }
    if ((str1.equals("CATEGORIES")) && (this.transactionCategories != null))
    {
      boolean bool = false;
      Iterator localIterator = this.transactionCategories.iterator();
      while ((localIterator.hasNext()) && (!bool))
      {
        String str2 = ((TransactionCategory)localIterator.next()).getRegisterCategoryId();
        bool = isFilterable(str2, paramString2, paramString3);
      }
      return bool;
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    if (paramString1.equalsIgnoreCase("DATE_ISSUED")) {
      setDateIssued(paramString2);
    } else if (paramString1.equalsIgnoreCase("PAYEE_NAME")) {
      setPayeeName(paramString2);
    } else if (paramString1.equalsIgnoreCase("REGISTER_ID")) {
      setRegisterId(paramString2);
    } else if (paramString1.equalsIgnoreCase("STATUS")) {
      setStatus(paramString2);
    } else if (paramString1.equalsIgnoreCase("RECONCILE_MATCH")) {
      setReconcileMatch(paramString2);
    } else if (paramString1.equalsIgnoreCase("DISCREPANCY")) {
      setDiscrepancy(paramString2);
    } else if (paramString1.equalsIgnoreCase("REFERENCE_NUMBER")) {
      setReferenceNumber(paramString2);
    } else {
      return super.set(paramString1, paramString2);
    }
    return true;
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "REGISTER_TRANSACTION");
    XMLHandler.appendTag(localStringBuffer, "REGISTER_ID", this.registerId);
    XMLHandler.appendTag(localStringBuffer, "STATUS", this.status);
    XMLHandler.appendTag(localStringBuffer, "DATE_ISSUED", this.dateIssued);
    XMLHandler.appendTag(localStringBuffer, "PAYEE_NAME", this.payeeName);
    XMLHandler.appendTag(localStringBuffer, "RECONCILE_MATCH", this.reconcileMatch);
    XMLHandler.appendTag(localStringBuffer, "DISCREPANCY", this.discrepancy ? "T" : "F");
    XMLHandler.appendTag(localStringBuffer, "TOTAL_AMOUNT", getDecimalAmount());
    localStringBuffer.append(super.getXML());
    localStringBuffer.append(this.transactionCategories.toXML());
    XMLHandler.appendEndTag(localStringBuffer, "REGISTER_TRANSACTION");
    return localStringBuffer.toString();
  }
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(null), paramString);
    }
    catch (Exception localException) {}
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a(null));
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.transactionCategories != null) {
      this.transactionCategories.setLocale(paramLocale);
    }
  }
  
  private class a
    extends XMLHandler
  {
    String jdField_int = "";
    
    private a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equalsIgnoreCase("TRANSACTION_CATEGORIES")) {
        RegisterTransaction.this.transactionCategories.continueXMLParsing(getHandler());
      }
    }
    
    public void endElement(String paramString)
    {
      if (paramString.equalsIgnoreCase("TRANSACTION_CATEGORIES"))
      {
        RegisterTransaction.this.transactionCategories = new TransactionCategories();
        RegisterTransaction.this.transactionCategories.continueXMLParsing(getHandler());
      }
      else
      {
        RegisterTransaction.this.set(paramString, this.jdField_int);
      }
    }
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      this.jdField_int = new String(paramArrayOfChar, paramInt1, paramInt2);
    }
    
    a(RegisterTransaction.1 param1)
    {
      this();
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.register.RegisterTransaction
 * JD-Core Version:    0.7.0.1
 */