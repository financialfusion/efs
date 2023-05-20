package com.ffusion.beans.banking;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Banking;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import java.math.BigDecimal;
import java.text.Collator;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;

public class Transaction
  extends ExtendABean
  implements TransactionTypes, TransactionCategories, Comparable
{
  public static final String RUNNING_BALANCE = "RUNNING_BALANCE";
  public static final String TRANS_IDX = "TRANS_IDX";
  public static final String POSTING_DATE = "POSTING_DATE";
  public static final String DUE_DATE = "DUE_DATE";
  public static final String FIXED_DEP_RATE = "FIXED_DEP_RATE";
  public static final String INSTRUMENT_NUMBER = "INSTRUMENT_NUMBER";
  public static final String INSTRUMENT_BANK_NAME = "INSTRUMENT_BANK_NAME";
  public static final String PAYEE_PAYOR = "PAYEE_PAYOR";
  public static final String PAYOR_NUM = "PAYOR_NUM";
  public static final String ORIG_USER = "ORIG_USER";
  public static final String PO_NUM = "PO_NUM";
  public static final String IMMED_AVAIL_AMT = "IMMED_AVAIL_AMT";
  public static final String ONE_DAY_AVAIL_AMT = "ONE_DAY_AVAIL_AMT";
  public static final String MORE_THAN_ONE_DAY_AVAIL_AMT = "MORE_THAN_ONE_DAY_AVAIL_AMT";
  public static final String BANK_REF_NUM = "BANK_REF_NUM";
  public static final String CUST_REF_NUM = "CUST_REF_NUM";
  public static final String PROCESSING_DATE = "PROCESSING_DATE";
  public static final String DATASOURCE_LOAD_TIME = "DATASOURCE_LOAD_TIME";
  public static final String DATA_CLASSIFICATION = "DATA_CLASSIFICATION";
  public static final String IS_CREDIT = "IS_CREDIT";
  public static final String RESOURCE_BUNDLE = "com.ffusion.beans.banking.resources";
  public static final int DEFAULT_DESCRIPTION_TRUNCATION_LENGTH = 30;
  public static final String INTRA_DAY_TRANSACTION = "I";
  public static final String PREVIOUS_DAY_TRANSACTION = "P";
  private String Fy;
  private int FI;
  private int FA;
  private int Fh;
  private String Fm;
  private int FC = 30;
  private String Fq;
  private String Fn;
  private DateTime FF;
  private DateTime Fw;
  private DateTime Fx;
  private DateTime FB;
  private DateTime Fi;
  private Currency Fg;
  private Currency Fv;
  protected String trackingID;
  private long Fk;
  private float Fz = -1.0F;
  private String FG;
  private String Fs;
  private String FH;
  private String Ft;
  private String Fo;
  private String Fu;
  private Currency Ff;
  private Currency Fe;
  private Currency Fj;
  private String FD;
  private String FE;
  private DateTime Fp;
  private String Fr;
  private boolean Fl;
  
  protected Transaction() {}
  
  protected Transaction(Locale paramLocale)
  {
    super(paramLocale);
    this.datetype = "SHORT";
  }
  
  public String getTrackingID()
  {
    return this.trackingID;
  }
  
  public void setTrackingID(String paramString)
  {
    this.trackingID = paramString;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "DATE");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    Transaction localTransaction = (Transaction)paramObject;
    int i = 1;
    Collator localCollator = doGetCollator();
    if ((paramString.equals("ID")) && (getID() != null) && (localTransaction.getID() != null))
    {
      i = localCollator.compare(getID(), localTransaction.getID());
    }
    else if ((paramString.equals("DESCRIPTION")) && (getDescription() != null) && (localTransaction.getDescription() != null))
    {
      i = localCollator.compare(getDescription().toLowerCase(), localTransaction.getDescription().toLowerCase());
    }
    else if ((paramString.equals("REFERENCENUMBER")) && (getReferenceNumber() != null) && (localTransaction.getReferenceNumber() != null))
    {
      i = localCollator.compare(getReferenceNumber(), localTransaction.getReferenceNumber());
    }
    else if ((paramString.equals("MEMO")) && (getMemo() != null) && (localTransaction.getMemo() != null))
    {
      i = localCollator.compare(getMemo().toLowerCase(), localTransaction.getMemo().toLowerCase());
    }
    else if ((paramString.equals("AMOUNT")) && (getAmountValue() != null) && (localTransaction.getAmountValue() != null))
    {
      i = getAmountValue().compareTo(localTransaction.getAmountValue());
    }
    else if ((paramString.equals("RUNNING_BALANCE")) && (getRunningBalanceValue() != null) && (localTransaction.getRunningBalanceValue() != null))
    {
      i = getRunningBalanceValue().compareTo(localTransaction.getRunningBalanceValue());
    }
    else if ((paramString.equals("DATE")) && (getDateValue() != null) && (localTransaction.getDateValue() != null))
    {
      i = getDateValue().equals(localTransaction.getDateValue()) ? 0 : getDateValue().before(localTransaction.getDateValue()) ? -1 : 1;
    }
    else if (paramString.equals("CATEGORY"))
    {
      i = getCategory() - localTransaction.getCategory();
    }
    else if (paramString.equals("TYPE"))
    {
      i = getTypeValue() - localTransaction.getTypeValue();
    }
    else if ((paramString.equals("TYPESTRING")) && (getType() != null) && (localTransaction.getType() != null))
    {
      i = localCollator.compare(getType(), localTransaction.getType());
    }
    else if ((paramString.equals("TYPE_ABBR")) && (getTypeAbbr() != null) && (localTransaction.getTypeAbbr() != null))
    {
      i = localCollator.compare(getTypeAbbr(), localTransaction.getTypeAbbr());
    }
    else if (paramString.equals("SUBTYPE"))
    {
      i = getSubTypeValue() - localTransaction.getSubTypeValue();
    }
    else if ((paramString.equals("SUBTYPESTRING")) && (getSubType() != null) && (localTransaction.getSubType() != null))
    {
      i = localCollator.compare(getSubType(), localTransaction.getSubType());
    }
    else if (paramString.equals("TRANS_IDX"))
    {
      i = (int)(getTransactionIndex() - localTransaction.getTransactionIndex());
    }
    else if ((paramString.equals("POSTING_DATE")) && (getPostingDate() != null) && (localTransaction.getPostingDate() != null))
    {
      i = getPostingDate().compare(localTransaction.getPostingDate(), null);
    }
    else if ((paramString.equals("DUE_DATE")) && (getDueDate() != null) && (localTransaction.getDueDate() != null))
    {
      i = getDueDate().compare(localTransaction.getDueDate(), null);
    }
    else if (paramString.equals("FIXED_DEP_RATE"))
    {
      float f = getFixedDepositRate() - localTransaction.getFixedDepositRate();
      i = f < 0.0F ? -1 : f == 0.0F ? 0 : 1;
    }
    else if ((paramString.equals("INSTRUMENT_NUMBER")) && (getInstrumentNumber() != null) && (localTransaction.getInstrumentNumber() != null))
    {
      i = localCollator.compare(getInstrumentNumber(), localTransaction.getInstrumentNumber());
    }
    else if ((paramString.equals("INSTRUMENT_BANK_NAME")) && (getInstrumentBankName() != null) && (localTransaction.getInstrumentBankName() != null))
    {
      i = localCollator.compare(getInstrumentBankName(), localTransaction.getInstrumentBankName());
    }
    else if ((paramString.equals("PAYEE_PAYOR")) && (getPayeePayor() != null) && (localTransaction.getPayeePayor() != null))
    {
      i = localCollator.compare(getPayeePayor(), localTransaction.getPayeePayor());
    }
    else if ((paramString.equals("PAYOR_NUM")) && (getPayorNum() != null) && (localTransaction.getPayorNum() != null))
    {
      i = localCollator.compare(getPayorNum(), localTransaction.getPayorNum());
    }
    else if ((paramString.equals("ORIG_USER")) && (getOrigUser() != null) && (localTransaction.getOrigUser() != null))
    {
      i = localCollator.compare(getOrigUser(), localTransaction.getOrigUser());
    }
    else if ((paramString.equals("PO_NUM")) && (getPONum() != null) && (localTransaction.getPONum() != null))
    {
      i = localCollator.compare(getPONum(), localTransaction.getPONum());
    }
    else if ((paramString.equals("IMMED_AVAIL_AMT")) && (getImmediateAvailAmount() != null) && (localTransaction.getImmediateAvailAmount() != null))
    {
      i = getImmediateAvailAmount().compareTo(localTransaction.getImmediateAvailAmount());
    }
    else if ((paramString.equals("ONE_DAY_AVAIL_AMT")) && (getOneDayAvailAmount() != null) && (localTransaction.getOneDayAvailAmount() != null))
    {
      i = getOneDayAvailAmount().compareTo(localTransaction.getOneDayAvailAmount());
    }
    else if ((paramString.equals("MORE_THAN_ONE_DAY_AVAIL_AMT")) && (getMoreThanOneDayAvailAmount() != null) && (localTransaction.getMoreThanOneDayAvailAmount() != null))
    {
      i = getMoreThanOneDayAvailAmount().compareTo(localTransaction.getMoreThanOneDayAvailAmount());
    }
    else if ((paramString.equals("BANK_REF_NUM")) && (getBankReferenceNumber() != null) && (localTransaction.getBankReferenceNumber() != null))
    {
      i = localCollator.compare(getBankReferenceNumber(), localTransaction.getBankReferenceNumber());
    }
    else if ((paramString.equals("CUST_REF_NUM")) && (getCustomerReferenceNumber() != null) && (localTransaction.getCustomerReferenceNumber() != null))
    {
      i = localCollator.compare(getCustomerReferenceNumber(), localTransaction.getCustomerReferenceNumber());
    }
    else if ((paramString.equals("PROCESSING_DATE")) && (getProcessingDate() != null) && (localTransaction.getProcessingDate() != null))
    {
      i = getProcessingDate().compare(localTransaction.getProcessingDate(), null);
    }
    else if ((paramString.equals("DATA_CLASSIFICATION")) && (getDataClassification() != null) && (localTransaction.getDataClassification() != null))
    {
      i = localCollator.compare(getDataClassification(), localTransaction.getDataClassification());
    }
    else
    {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1.equals("ID")) && (getID() != null)) {
      return isFilterable(getID(), paramString2, paramString3);
    }
    if ((paramString1.equals("DESCRIPTION")) && (getDescription() != null)) {
      return isFilterable(getDescription(), paramString2, paramString3);
    }
    if ((paramString1.equals("REFERENCENUMBER")) && (getReferenceNumber() != null)) {
      return isFilterable(getReferenceNumber(), paramString2, paramString3);
    }
    if ((paramString1.equals("MEMO")) && (getMemo() != null)) {
      return isFilterable(getMemo(), paramString2, paramString3);
    }
    if ((paramString1.equals("AMOUNT")) && (this.Fg != null)) {
      return this.Fg.isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((paramString1.equals("RUNNING_BALANCE")) && (this.Fv != null)) {
      return this.Fv.isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((paramString1.equals("DATE")) && (this.FF != null)) {
      return this.FF.isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((paramString1.equals("VALUEDATE")) && (this.Fw != null)) {
      return this.Fw.isFilterable("VALUE" + paramString2 + paramString3);
    }
    if (paramString1.equals("CATEGORY")) {
      return isFilterable(String.valueOf(getCategory()), paramString2, paramString3);
    }
    if (paramString1.equals("TYPE")) {
      return isFilterable(getType(), paramString2, paramString3);
    }
    if (paramString1.equals("SUBTYPE")) {
      return isFilterable(getSubType(), paramString2, paramString3);
    }
    if (paramString1.equals("TRANS_IDX")) {
      return isFilterable(String.valueOf(this.Fk), paramString2, paramString3);
    }
    if ((paramString1.equals("POSTING_DATE")) && (this.Fx != null)) {
      return this.Fx.isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((paramString1.equals("DUE_DATE")) && (this.FB != null)) {
      return this.FB.isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((paramString1.equals("FIXED_DEP_RATE")) && (this.Fz != -1.0F)) {
      return isFilterable(getFixedDepositRateString(), paramString2, paramString3);
    }
    if ((paramString1.equals("INSTRUMENT_NUMBER")) && (this.FG != null)) {
      return isFilterable(this.FG, paramString2, paramString3);
    }
    if ((paramString1.equals("INSTRUMENT_BANK_NAME")) && (this.Fs != null)) {
      return isFilterable(this.Fs, paramString2, paramString3);
    }
    if ((paramString1.equals("PAYOR_NUM")) && (this.Ft != null)) {
      return isFilterable(this.Ft, paramString2, paramString3);
    }
    if ((paramString1.equals("ORIG_USER")) && (this.Fo != null)) {
      return isFilterable(this.Fo, paramString2, paramString3);
    }
    if ((paramString1.equals("PO_NUM")) && (this.Fu != null)) {
      return isFilterable(this.Fu, paramString2, paramString3);
    }
    if ((paramString1.equals("IMMED_AVAIL_AMT")) && (this.Ff != null)) {
      return this.Ff.isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((paramString1.equals("ONE_DAY_AVAIL_AMT")) && (this.Fe != null)) {
      return this.Fe.isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((paramString1.equals("MORE_THAN_ONE_DAY_AVAIL_AMT")) && (this.Fj != null)) {
      return this.Fj.isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((paramString1.equals("BANK_REF_NUM")) && (this.FD != null)) {
      return isFilterable(this.FD, paramString2, paramString3);
    }
    if ((paramString1.equals("CUST_REF_NUM")) && (this.FE != null)) {
      return isFilterable(this.FE, paramString2, paramString3);
    }
    if ((paramString1.equals("PROCESSING_DATE")) && (this.Fi != null)) {
      return this.Fi.isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((paramString1.equals("DATA_CLASSIFICATION")) && (this.Fr != null)) {
      return isFilterable(this.Fr, paramString2, paramString3);
    }
    if ("IS_CREDIT".equals(paramString1)) {
      return isFilterable(Boolean.toString(this.Fl), paramString2, paramString3.toLowerCase());
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.FF != null) {
      this.FF.setFormat(paramString);
    }
    if (this.Fw != null) {
      this.Fw.setFormat(paramString);
    }
    if (this.Fx != null) {
      this.Fx.setFormat(paramString);
    }
    if (this.FB != null) {
      this.FB.setFormat(paramString);
    }
    if (this.Fi != null) {
      this.Fi.setFormat(paramString);
    }
  }
  
  public void setID(String paramString)
  {
    this.Fy = paramString;
  }
  
  public String getID()
  {
    return this.Fy;
  }
  
  public void setReferenceNumber(String paramString)
  {
    this.Fq = paramString;
  }
  
  public String getReferenceNumber()
  {
    return this.Fq;
  }
  
  public void setType(int paramInt)
  {
    this.FI = paramInt;
  }
  
  public String getType()
  {
    TransactionTypeInfo localTransactionTypeInfo = null;
    try
    {
      localTransactionTypeInfo = Banking.getTransactionTypeInfo(getTypeValue(), new HashMap(0));
    }
    catch (CSILException localCSILException) {}
    return localTransactionTypeInfo == null ? null : localTransactionTypeInfo.getDescription(this.locale);
  }
  
  public String getTypeAbbr()
  {
    TransactionTypeInfo localTransactionTypeInfo = null;
    try
    {
      localTransactionTypeInfo = Banking.getTransactionTypeInfo(getTypeValue(), new HashMap(0));
    }
    catch (CSILException localCSILException) {}
    return localTransactionTypeInfo == null ? null : localTransactionTypeInfo.getAbbr(this.locale);
  }
  
  public int getTypeValue()
  {
    return this.FI;
  }
  
  public void setSubType(int paramInt)
  {
    this.FA = paramInt;
  }
  
  public String getSubType()
  {
    return Integer.toString(this.FA);
  }
  
  public String getSubTypeAbbr()
  {
    return "";
  }
  
  public int getSubTypeValue()
  {
    return this.FA;
  }
  
  public void setCategory(int paramInt)
  {
    this.Fh = paramInt;
  }
  
  public int getCategory()
  {
    return this.Fh;
  }
  
  public void setDate(DateTime paramDateTime)
  {
    this.FF = paramDateTime;
    if (this.FF != null) {
      this.FF.setFormat(this.datetype);
    }
  }
  
  public DateTime getDateValue()
  {
    return this.FF;
  }
  
  public String getDate()
  {
    return this.FF.toString();
  }
  
  public void setValueDate(DateTime paramDateTime)
  {
    this.Fw = paramDateTime;
    if (this.Fw != null) {
      this.Fw.setFormat(this.datetype);
    }
  }
  
  public DateTime getValueDate()
  {
    return this.Fw;
  }
  
  public void setAmount(String paramString)
  {
    if (this.Fg == null) {
      this.Fg = new Currency(paramString, this.locale);
    } else {
      this.Fg.fromString(paramString);
    }
  }
  
  public void setAmount(Currency paramCurrency)
  {
    this.Fg = paramCurrency;
  }
  
  public void setAmount(BigDecimal paramBigDecimal)
  {
    if (this.Fg == null) {
      this.Fg = new Currency(paramBigDecimal, this.locale);
    } else {
      this.Fg.setAmount(paramBigDecimal);
    }
  }
  
  public Currency getAmountValue()
  {
    return this.Fg;
  }
  
  public Currency getDebitValue()
  {
    if (this.Fg == null) {
      return null;
    }
    if (this.Fg.doubleValue() < 0.0D) {
      return new Currency(this.Fg.getAmountValue().negate(), this.Fg.getCurrencyCode(), this.Fg.getLocale());
    }
    return null;
  }
  
  public Currency getCreditValue()
  {
    if (this.Fg == null) {
      return null;
    }
    if (this.Fg.doubleValue() >= 0.0D) {
      return this.Fg;
    }
    return null;
  }
  
  public Currency getDebitValueAbsolute()
  {
    if (this.Fg == null) {
      return null;
    }
    if (this.Fg.doubleValue() < 0.0D) {
      return new Currency(this.Fg.getAmountValue().abs(), this.locale);
    }
    return null;
  }
  
  public String getAmount()
  {
    if (this.Fg == null) {
      return null;
    }
    return this.Fg.toString();
  }
  
  public String getDebit()
  {
    if (this.Fg == null) {
      return null;
    }
    if (this.Fg.doubleValue() < 0.0D) {
      return this.Fg.toStringAbsolute();
    }
    return "";
  }
  
  public String getDebitNoSymbol()
  {
    if (this.Fg == null) {
      return null;
    }
    if (this.Fg.doubleValue() < 0.0D) {
      return this.Fg.getCurrencyStringNoSymbol();
    }
    return "";
  }
  
  public String getCredit()
  {
    if (this.Fg == null) {
      return null;
    }
    if (this.Fg.doubleValue() >= 0.0D) {
      return this.Fg.toString();
    }
    return "";
  }
  
  public String getCreditNoSymbol()
  {
    if (this.Fg == null) {
      return null;
    }
    if (this.Fg.doubleValue() >= 0.0D) {
      return this.Fg.getCurrencyStringNoSymbol();
    }
    return "";
  }
  
  public void setRunningBalance(Currency paramCurrency)
  {
    this.Fv = paramCurrency;
  }
  
  public Currency getRunningBalanceValue()
  {
    return this.Fv;
  }
  
  public String getRunningBalance()
  {
    return this.Fv.toString();
  }
  
  public void setDescription(String paramString)
  {
    this.Fm = paramString;
  }
  
  public String getDescription()
  {
    return this.Fm;
  }
  
  public String getTruncatedDescription()
  {
    if (this.Fm.length() <= this.FC) {
      return this.Fm;
    }
    if (this.FC <= 3) {
      return "...";
    }
    return this.Fm.substring(0, this.FC - 3) + "...";
  }
  
  public void setDescriptionTruncationLength(String paramString)
  {
    try
    {
      this.FC = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public int getDescriptionTruncationLength()
  {
    return this.FC;
  }
  
  public void setMemo(String paramString)
  {
    this.Fn = paramString;
  }
  
  public String getMemo()
  {
    return this.Fn;
  }
  
  public long getTransactionIndex()
  {
    return this.Fk;
  }
  
  public void setTransactionIndex(long paramLong)
  {
    this.Fk = paramLong;
  }
  
  public DateTime getPostingDate()
  {
    return this.Fx;
  }
  
  public void setPostingDate(DateTime paramDateTime)
  {
    this.Fx = paramDateTime;
  }
  
  public DateTime getDueDate()
  {
    return this.FB;
  }
  
  public void setDueDate(DateTime paramDateTime)
  {
    this.FB = paramDateTime;
  }
  
  public float getFixedDepositRate()
  {
    return this.Fz;
  }
  
  public String getFixedDepositRateString()
  {
    if (this.Fz == -1.0F) {
      return null;
    }
    NumberFormat localNumberFormat = NumberFormat.getNumberInstance(this.locale);
    localNumberFormat.setMinimumFractionDigits(5);
    localNumberFormat.setMaximumFractionDigits(5);
    return localNumberFormat.format(this.Fz);
  }
  
  public void setFixedDepositRate(float paramFloat)
  {
    this.Fz = paramFloat;
  }
  
  public String getInstrumentNumber()
  {
    return this.FG;
  }
  
  public void setInstrumentNumber(String paramString)
  {
    this.FG = paramString;
  }
  
  public String getInstrumentBankName()
  {
    return this.Fs;
  }
  
  public void setInstrumentBankName(String paramString)
  {
    this.Fs = paramString;
  }
  
  public String getPayeePayor()
  {
    return this.FH;
  }
  
  public void setPayeePayor(String paramString)
  {
    this.FH = paramString;
  }
  
  public String getPayorNum()
  {
    return this.Ft;
  }
  
  public void setPayorNum(String paramString)
  {
    this.Ft = paramString;
  }
  
  public String getOrigUser()
  {
    return this.Fo;
  }
  
  public void setOrigUser(String paramString)
  {
    this.Fo = paramString;
  }
  
  public String getPONum()
  {
    return this.Fu;
  }
  
  public void setPONum(String paramString)
  {
    this.Fu = paramString;
  }
  
  public Currency getImmediateAvailAmount()
  {
    return this.Ff;
  }
  
  public void setImmediateAvailAmount(Currency paramCurrency)
  {
    this.Ff = paramCurrency;
  }
  
  public Currency getOneDayAvailAmount()
  {
    return this.Fe;
  }
  
  public void setOneDayAvailAmount(Currency paramCurrency)
  {
    this.Fe = paramCurrency;
  }
  
  public Currency getMoreThanOneDayAvailAmount()
  {
    return this.Fj;
  }
  
  public void setMoreThanOneDayAvailAmount(Currency paramCurrency)
  {
    this.Fj = paramCurrency;
  }
  
  public String getBankReferenceNumber()
  {
    return this.FD;
  }
  
  public void setBankReferenceNumber(String paramString)
  {
    this.FD = paramString;
  }
  
  public String getCustomerReferenceNumber()
  {
    return this.FE;
  }
  
  public void setCustomerReferenceNumber(String paramString)
  {
    this.FE = paramString;
  }
  
  public DateTime getProcessingDate()
  {
    return this.Fi;
  }
  
  public void setProcessingDate(DateTime paramDateTime)
  {
    this.Fi = paramDateTime;
  }
  
  public void setDataSourceLoadTime(DateTime paramDateTime)
  {
    this.Fp = paramDateTime;
  }
  
  public DateTime getDataSourceLoadTime()
  {
    return this.Fp;
  }
  
  public void setDataClassification(String paramString)
  {
    this.Fr = paramString;
  }
  
  public String getDataClassification()
  {
    return this.Fr;
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.Fv != null) {
      this.Fv.setLocale(paramLocale);
    }
    if (this.Fg != null) {
      this.Fg.setLocale(paramLocale);
    }
    if (this.FF != null) {
      this.FF.setLocale(paramLocale);
    }
    if (this.Fw != null) {
      this.Fw.setLocale(paramLocale);
    }
    if (this.Fx != null) {
      this.Fx.setLocale(paramLocale);
    }
    if (this.FB != null) {
      this.FB.setLocale(paramLocale);
    }
    if (this.Ff != null) {
      this.Ff.setLocale(paramLocale);
    }
    if (this.Fe != null) {
      this.Fe.setLocale(paramLocale);
    }
    if (this.Fj != null) {
      this.Fj.setLocale(paramLocale);
    }
    if (this.Fi != null) {
      this.Fi.setLocale(paramLocale);
    }
    if (this.Fp != null) {
      this.Fp.setLocale(paramLocale);
    }
  }
  
  public void set(Transaction paramTransaction)
  {
    setID(paramTransaction.getID());
    setType(paramTransaction.getTypeValue());
    setSubType(paramTransaction.getSubTypeValue());
    setDescription(paramTransaction.getDescription());
    setReferenceNumber(paramTransaction.getReferenceNumber());
    setMemo(paramTransaction.getMemo());
    if (paramTransaction.getDateValue() != null) {
      setDate((DateTime)paramTransaction.getDateValue().clone());
    } else {
      setDate(null);
    }
    if (paramTransaction.getValueDate() != null) {
      setValueDate((DateTime)paramTransaction.getValueDate().clone());
    } else {
      setValueDate(null);
    }
    setCategory(paramTransaction.getCategory());
    if (paramTransaction.getAmountValue() != null) {
      setAmount((Currency)paramTransaction.getAmountValue().clone());
    }
    if (paramTransaction.getRunningBalanceValue() != null) {
      setRunningBalance((Currency)paramTransaction.getRunningBalanceValue().clone());
    }
    setTransactionIndex(paramTransaction.getTransactionIndex());
    setPostingDate(paramTransaction.getPostingDate());
    setDueDate(paramTransaction.getDueDate());
    setFixedDepositRate(paramTransaction.getFixedDepositRate());
    setInstrumentNumber(paramTransaction.getInstrumentNumber());
    setInstrumentBankName(paramTransaction.getInstrumentBankName());
    setPayeePayor(paramTransaction.getPayeePayor());
    setPayorNum(paramTransaction.getPayorNum());
    setOrigUser(paramTransaction.getOrigUser());
    setPONum(paramTransaction.getPONum());
    setImmediateAvailAmount(paramTransaction.getImmediateAvailAmount());
    setOneDayAvailAmount(paramTransaction.getOneDayAvailAmount());
    setMoreThanOneDayAvailAmount(paramTransaction.getMoreThanOneDayAvailAmount());
    setBankReferenceNumber(paramTransaction.getBankReferenceNumber());
    setCustomerReferenceNumber(paramTransaction.getCustomerReferenceNumber());
    setProcessingDate(paramTransaction.getProcessingDate());
    setDataSourceLoadTime(paramTransaction.getDataSourceLoadTime());
    setDataClassification(paramTransaction.getDataClassification());
    super.set(paramTransaction);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("ID"))
    {
      this.Fy = paramString2;
    }
    else if (paramString1.equals("TYPE"))
    {
      this.FI = Integer.parseInt(paramString2);
    }
    else if (paramString1.equals("SUBTYPE"))
    {
      this.FA = Integer.parseInt(paramString2);
    }
    else if (paramString1.equals("CATEGORY"))
    {
      this.Fh = Integer.parseInt(paramString2);
    }
    else if (paramString1.equals("DESCRIPTION"))
    {
      this.Fm = paramString2;
    }
    else if (paramString1.equals("REFERENCENUMBER"))
    {
      this.Fq = paramString2;
    }
    else if (paramString1.equals("MEMO"))
    {
      this.Fn = paramString2;
    }
    else if (paramString1.equals("DATE"))
    {
      if (this.FF == null)
      {
        this.FF = new DateTime(this.locale);
        this.FF.setFormat(this.datetype);
      }
      this.FF.fromXMLFormat(paramString2);
    }
    else if (paramString1.equals("VALUEDATE"))
    {
      if (this.Fw == null)
      {
        this.Fw = new DateTime(this.locale);
        this.Fw.setFormat(this.datetype);
      }
      this.Fw.fromXMLFormat(paramString2);
    }
    else if (paramString1.equals("AMOUNT"))
    {
      if (this.Fg == null) {
        this.Fg = new Currency(paramString2, this.locale);
      } else {
        this.Fg.fromString(paramString2);
      }
    }
    else if (paramString1.equals("RUNNING_BALANCE"))
    {
      if (this.Fv == null) {
        this.Fv = new Currency(paramString2, this.locale);
      } else {
        this.Fv.fromString(paramString2);
      }
    }
    else if (paramString1.equals("TRANS_IDX"))
    {
      BigDecimal localBigDecimal = new BigDecimal(paramString2);
      this.Fk = localBigDecimal.longValue();
    }
    else if (paramString1.equals("POSTING_DATE"))
    {
      if (this.Fx == null)
      {
        this.Fx = new DateTime(this.locale);
        this.Fx.setFormat(this.datetype);
      }
      this.Fx.fromXMLFormat(paramString2);
    }
    else if (paramString1.equals("DUE_DATE"))
    {
      if (this.FB == null)
      {
        this.FB = new DateTime(this.locale);
        this.FB.setFormat(this.datetype);
      }
      this.FB.fromXMLFormat(paramString2);
    }
    else if (paramString1.equals("FIXED_DEP_RATE"))
    {
      this.Fz = Float.parseFloat(paramString2);
    }
    else if (paramString1.equals("INSTRUMENT_NUMBER"))
    {
      this.FG = paramString2;
    }
    else if (paramString1.equals("INSTRUMENT_BANK_NAME"))
    {
      this.Fs = paramString2;
    }
    else if (paramString1.equals("PAYEE_PAYOR"))
    {
      this.FH = paramString2;
    }
    else if (paramString1.equals("PAYOR_NUM"))
    {
      this.Ft = paramString2;
    }
    else if (paramString1.equals("ORIG_USER"))
    {
      this.Fo = paramString2;
    }
    else if (paramString1.equals("PO_NUM"))
    {
      this.Fu = paramString2;
    }
    else if (paramString1.equals("IMMED_AVAIL_AMT"))
    {
      if (this.Ff == null) {
        this.Ff = new Currency(paramString2, this.locale);
      } else {
        this.Ff.fromString(paramString2);
      }
    }
    else if (paramString1.equals("ONE_DAY_AVAIL_AMT"))
    {
      if (this.Fe == null) {
        this.Fe = new Currency(paramString2, this.locale);
      } else {
        this.Fe.fromString(paramString2);
      }
    }
    else if (paramString1.equals("MORE_THAN_ONE_DAY_AVAIL_AMT"))
    {
      if (this.Fj == null) {
        this.Fj = new Currency(paramString2, this.locale);
      } else {
        this.Fj.fromString(paramString2);
      }
    }
    else if (paramString1.equals("BANK_REF_NUM"))
    {
      this.FD = paramString2;
    }
    else if (paramString1.equals("CUST_REF_NUM"))
    {
      this.FE = paramString2;
    }
    else if (paramString1.equals("PROCESSING_DATE"))
    {
      if (this.Fi == null)
      {
        this.Fi = new DateTime(this.locale);
        this.Fi.setFormat(this.datetype);
      }
      this.Fi.fromXMLFormat(paramString2);
    }
    else if (paramString1.equals("DATASOURCE_LOAD_TIME"))
    {
      if (this.Fp == null)
      {
        this.Fp = new DateTime(this.locale);
        this.Fp.setFormat(this.datetype);
      }
      this.Fp.fromXMLFormat(paramString2);
    }
    else if (paramString1.equals("DATA_CLASSIFICATION"))
    {
      this.Fr = paramString2;
    }
    else if ("IS_CREDIT".equals(paramString1))
    {
      setIsCredit(paramString2);
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
    XMLHandler.appendBeginTag(localStringBuffer, "TRANSACTION");
    XMLHandler.appendTag(localStringBuffer, "ID", this.Fy);
    if (this.FI != 0) {
      XMLHandler.appendTag(localStringBuffer, "TYPE", this.FI);
    }
    if (this.FA != 0) {
      XMLHandler.appendTag(localStringBuffer, "SUBTYPE", this.FA);
    }
    if (this.Fh != 0) {
      XMLHandler.appendTag(localStringBuffer, "CATEGORY", this.Fh);
    }
    XMLHandler.appendTag(localStringBuffer, "DESCRIPTION", this.Fm);
    XMLHandler.appendTag(localStringBuffer, "REFERENCENUMBER", this.Fq);
    XMLHandler.appendTag(localStringBuffer, "MEMO", this.Fn);
    if (this.FF != null) {
      XMLHandler.appendTag(localStringBuffer, "DATE", this.FF.toXMLFormat());
    }
    if (this.Fw != null) {
      XMLHandler.appendTag(localStringBuffer, "VALUEDATE", this.Fw.toXMLFormat());
    }
    if (this.Fg != null) {
      XMLHandler.appendTag(localStringBuffer, "AMOUNT", this.Fg.doubleValue());
    }
    if (this.Fv != null) {
      XMLHandler.appendTag(localStringBuffer, "RUNNING_BALANCE", this.Fv.doubleValue());
    }
    XMLHandler.appendTag(localStringBuffer, "TRANS_IDX", this.Fk);
    if (this.Fx != null) {
      XMLHandler.appendTag(localStringBuffer, "POSTING_DATE", this.Fx.toXMLFormat());
    }
    if (this.FB != null) {
      XMLHandler.appendTag(localStringBuffer, "DUE_DATE", this.FB.toXMLFormat());
    }
    if (this.Fz != -1.0F) {
      XMLHandler.appendTag(localStringBuffer, "FIXED_DEP_RATE", getFixedDepositRateString());
    }
    if (this.FG != null) {
      XMLHandler.appendTag(localStringBuffer, "INSTRUMENT_NUMBER", this.FG);
    }
    if (this.Fs != null) {
      XMLHandler.appendTag(localStringBuffer, "INSTRUMENT_BANK_NAME", this.Fs);
    }
    if (this.FH != null) {
      XMLHandler.appendTag(localStringBuffer, "PAYEE_PAYOR", this.FH);
    }
    if (this.Ft != null) {
      XMLHandler.appendTag(localStringBuffer, "PAYOR_NUM", this.Ft);
    }
    if (this.Fo != null) {
      XMLHandler.appendTag(localStringBuffer, "ORIG_USER", this.Fo);
    }
    if (this.Fu != null) {
      XMLHandler.appendTag(localStringBuffer, "PO_NUM", this.Fu);
    }
    if (this.Ff != null) {
      XMLHandler.appendTag(localStringBuffer, "IMMED_AVAIL_AMT", this.Ff.doubleValue());
    }
    if (this.Fe != null) {
      XMLHandler.appendTag(localStringBuffer, "ONE_DAY_AVAIL_AMT", this.Fe.doubleValue());
    }
    if (this.Fj != null) {
      XMLHandler.appendTag(localStringBuffer, "MORE_THAN_ONE_DAY_AVAIL_AMT", this.Fj.doubleValue());
    }
    if (this.FD != null) {
      XMLHandler.appendTag(localStringBuffer, "BANK_REF_NUM", this.FD);
    }
    if (this.FE != null) {
      XMLHandler.appendTag(localStringBuffer, "CUST_REF_NUM", this.FE);
    }
    if (this.Fi != null) {
      XMLHandler.appendTag(localStringBuffer, "PROCESSING_DATE", this.Fi.toXMLFormat());
    }
    if (this.Fp != null) {
      XMLHandler.appendTag(localStringBuffer, "DATASOURCE_LOAD_TIME", this.Fp.toXMLFormat());
    }
    if (this.Fr != null) {
      XMLHandler.appendTag(localStringBuffer, "DATA_CLASSIFICATION", this.Fr);
    }
    XMLHandler.appendTag(localStringBuffer, "IS_CREDIT", Boolean.toString(this.Fl));
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "TRANSACTION");
    return localStringBuffer.toString();
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new ExtendABean.InternalXMLHandler(this));
  }
  
  public void setIsCredit(boolean paramBoolean)
  {
    this.Fl = paramBoolean;
  }
  
  public void setIsCredit(String paramString)
  {
    this.Fl = Boolean.valueOf(paramString).booleanValue();
  }
  
  public boolean getIsCredit()
  {
    return this.Fl;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.banking.Transaction
 * JD-Core Version:    0.7.0.1
 */