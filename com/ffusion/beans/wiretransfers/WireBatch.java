package com.ffusion.beans.wiretransfers;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.Frequencies;
import com.ffusion.beans.FundsTransaction;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.history.Histories;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.ffs.bpw.interfaces.WireBatchInfo;
import com.ffusion.ffs.bpw.interfaces.WireInfo;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.AuditLogRecord;
import com.ffusion.util.logging.AuditLogUtil;
import com.ffusion.util.logging.DebugLog;
import java.math.BigDecimal;
import java.util.Locale;

public class WireBatch
  extends FundsTransaction
  implements WireDefines, Frequencies, Comparable
{
  public static final String RESOURCE_BUNDLE = "com.ffusion.beans.wiretransfers.resources";
  private String Bl;
  private String Bv;
  private String Bn;
  private String Bp;
  private String BG;
  private String Bm;
  private String submittedBy;
  private String BI;
  private String BJ;
  private Currency BF;
  private Currency Bx;
  private int Bu;
  protected WireTransfers wires;
  private String BK;
  private DateTime BL;
  private DateTime BA;
  private DateTime Bo;
  private String By;
  private String BN = "USD";
  private String BM = "USD";
  private String BD;
  private float BH = 1.0F;
  private String Br;
  private int BC = 0;
  private String Bk;
  private String Bt;
  private String Bs;
  private boolean BB = false;
  private boolean BE = false;
  private String Bw;
  private String Bq;
  private String Bz;
  
  public WireBatch()
  {
    setType(14);
    setBatchDestination("DOMESTIC");
    setAmtCurrencyType("USD");
  }
  
  public WireBatch(String paramString)
  {
    super(paramString);
    setType(14);
    setBatchDestination("DOMESTIC");
    setAmtCurrencyType("USD");
  }
  
  public WireBatch(Locale paramLocale)
  {
    if (paramLocale == null) {
      throw new IllegalArgumentException("Locale cannot be null");
    }
    this.locale = paramLocale;
    this.datetype = "SHORT";
    setType(14);
    setBatchDestination("DOMESTIC");
    setAmtCurrencyType("USD");
  }
  
  public String getBatchID()
  {
    return this.id;
  }
  
  public void setBatchID(String paramString)
  {
    this.id = paramString;
  }
  
  public String getBatchName()
  {
    return this.Bl;
  }
  
  public void setBatchName(String paramString)
  {
    this.Bl = paramString;
  }
  
  public String getBatchType()
  {
    return this.Bv;
  }
  
  public void setBatchType(String paramString)
  {
    this.Bv = paramString;
  }
  
  public String getBatchCategory()
  {
    return this.Bn;
  }
  
  public void setBatchCategory(String paramString)
  {
    this.Bn = paramString;
  }
  
  public String getBatchDestination()
  {
    return this.Bp;
  }
  
  public void setBatchDestination(String paramString)
  {
    this.Bp = paramString;
  }
  
  public String getBankID()
  {
    return this.BG;
  }
  
  public void setBankID(String paramString)
  {
    this.BG = paramString;
  }
  
  public String getCustomerID()
  {
    return this.Bm;
  }
  
  public void setCustomerID(String paramString)
  {
    this.Bm = paramString;
  }
  
  public void setCustomerID(int paramInt)
  {
    this.Bm = Integer.toString(paramInt);
  }
  
  public String getSubmittedBy()
  {
    return this.submittedBy;
  }
  
  public void setSubmittedBy(String paramString)
  {
    this.submittedBy = paramString;
  }
  
  public void setSubmittedBy(int paramInt)
  {
    this.submittedBy = String.valueOf(paramInt);
  }
  
  public String getUserID()
  {
    return this.BI;
  }
  
  public void setUserID(String paramString)
  {
    this.BI = paramString;
  }
  
  public void setUserID(int paramInt)
  {
    this.BI = String.valueOf(paramInt);
  }
  
  public void setApprovingUser(String paramString)
  {
    this.BJ = paramString;
  }
  
  public String getApprovingUser()
  {
    return this.BJ;
  }
  
  public String getTotalDebit()
  {
    if (this.BF != null) {
      return this.BF.toString();
    }
    return "0.00";
  }
  
  public String getTotalDebitForBPW()
  {
    if (this.BF != null)
    {
      BigDecimal localBigDecimal = this.BF.getAmountValue().setScale(2, 5);
      return localBigDecimal.toString();
    }
    return "0.00";
  }
  
  public Currency getTotalDebitValue()
  {
    return this.BF;
  }
  
  public void setTotalDebit(Currency paramCurrency)
  {
    this.BF = paramCurrency;
    if (!getBatchDestination().equals("DRAWDOWN")) {
      setAmount(paramCurrency);
    }
  }
  
  public void setTotalDebit(String paramString)
  {
    if (this.BF == null)
    {
      if (paramString == null) {
        this.BF = new Currency("", this.locale);
      } else {
        this.BF = new Currency(paramString, this.locale);
      }
    }
    else if (paramString == null) {
      this.BF.setAmount("");
    } else {
      this.BF.setAmount(paramString);
    }
    if (!getBatchDestination().equals("DRAWDOWN")) {
      setAmount(this.BF);
    }
  }
  
  public void setTotalDebit(BigDecimal paramBigDecimal)
  {
    if (this.BF == null)
    {
      if (paramBigDecimal == null) {
        this.BF = new Currency("", this.locale);
      } else {
        this.BF = new Currency(paramBigDecimal, this.locale);
      }
    }
    else {
      this.BF.setAmount(paramBigDecimal);
    }
    if (!getBatchDestination().equals("DRAWDOWN")) {
      setAmount(this.BF);
    }
  }
  
  public Currency calculateTotalAmount()
  {
    if (this.wires != null)
    {
      BigDecimal localBigDecimal1 = new BigDecimal(0.0D);
      for (int i = 0; i < this.wires.size(); i++)
      {
        WireTransfer localWireTransfer = (WireTransfer)this.wires.get(i);
        if ((localWireTransfer.getAction() == null) || (!localWireTransfer.getAction().equals("del")))
        {
          BigDecimal localBigDecimal2 = localWireTransfer.getAmountValue().getAmountValue();
          localBigDecimal1 = localBigDecimal1.add(localBigDecimal2);
        }
      }
      return new Currency(localBigDecimal1, this.locale);
    }
    return new Currency("", this.locale);
  }
  
  public String getTotalCredit()
  {
    if (this.Bx != null) {
      return this.Bx.toString();
    }
    return "";
  }
  
  public String getTotalCreditForBPW()
  {
    if (this.Bx == null) {
      return "0.00";
    }
    BigDecimal localBigDecimal = getTotalCreditValue().getAmountValue().setScale(2, 5);
    return localBigDecimal.toString();
  }
  
  public String getTotalAmountForBPW()
  {
    if (this.amount == null) {
      return "0.00";
    }
    BigDecimal localBigDecimal = getAmountValue().getAmountValue().setScale(2, 5);
    return localBigDecimal.toString();
  }
  
  public Currency getTotalCreditValue()
  {
    return this.Bx;
  }
  
  public void setTotalCredit(Currency paramCurrency)
  {
    this.Bx = paramCurrency;
    if (getBatchDestination().equals("DRAWDOWN")) {
      setAmount(paramCurrency);
    }
  }
  
  public void setTotalCredit(String paramString)
  {
    if (this.Bx == null)
    {
      if (paramString == null) {
        this.Bx = new Currency("", this.locale);
      } else {
        this.Bx = new Currency(paramString, this.locale);
      }
    }
    else if (paramString == null) {
      this.Bx.setAmount("");
    } else {
      this.Bx.setAmount(paramString);
    }
    if (getBatchDestination().equals("DRAWDOWN")) {
      setAmount(this.Bx);
    }
  }
  
  public void setTotalCredit(BigDecimal paramBigDecimal)
  {
    if (this.Bx == null)
    {
      if (paramBigDecimal == null) {
        this.Bx = new Currency("", this.locale);
      } else {
        this.Bx = new Currency(paramBigDecimal, this.locale);
      }
    }
    else {
      this.Bx.setAmount(paramBigDecimal);
    }
    if (getBatchDestination().equals("DRAWDOWN")) {
      setAmount(this.Bx);
    }
  }
  
  public String getWireCount()
  {
    return Integer.toString(this.Bu);
  }
  
  public int getWireCountValue()
  {
    return this.Bu;
  }
  
  public int calculateWireCount()
  {
    int i = 0;
    if (this.wires != null) {
      for (int j = 0; j < this.wires.size(); j++)
      {
        WireTransfer localWireTransfer = (WireTransfer)this.wires.get(j);
        if ((localWireTransfer.getAction() == null) || (!localWireTransfer.getAction().equals("del"))) {
          i++;
        }
      }
    }
    return i;
  }
  
  public void setWireCount(int paramInt)
  {
    this.Bu = paramInt;
  }
  
  public void setWireCount(String paramString)
  {
    try
    {
      this.Bu = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
  
  public WireTransfers getWires()
  {
    return this.wires;
  }
  
  public void setWires(WireTransfers paramWireTransfers)
  {
    this.wires = paramWireTransfers;
  }
  
  public String getMemo()
  {
    return this.BK;
  }
  
  public void setMemo(String paramString)
  {
    this.BK = paramString;
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.BA != null) {
      this.BA.setFormat(paramString);
    }
    if (this.Bo != null) {
      this.Bo.setFormat(paramString);
    }
    if (this.BL != null) {
      this.BL.setFormat(paramString);
    }
  }
  
  public void setDueDate(DateTime paramDateTime)
  {
    this.BL = paramDateTime;
  }
  
  public void setDueDate(String paramString)
  {
    try
    {
      if (this.BL == null) {
        this.BL = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.BL.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public DateTime getDueDateValue()
  {
    return this.BL;
  }
  
  public String getDueDate()
  {
    if (this.BL != null) {
      return this.BL.toString();
    }
    return "";
  }
  
  public void setDateToPost(DateTime paramDateTime)
  {
    this.BA = paramDateTime;
  }
  
  public void setDateToPost(String paramString)
  {
    try
    {
      if (this.BA == null) {
        this.BA = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.BA.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public DateTime getDateToPostValue()
  {
    return this.BA;
  }
  
  public String getDateToPost()
  {
    if (this.BA != null) {
      return this.BA.toString();
    }
    return "";
  }
  
  public void setSettlementDate(DateTime paramDateTime)
  {
    this.Bo = paramDateTime;
  }
  
  public void setSettlementDate(String paramString)
  {
    try
    {
      if (this.Bo == null) {
        this.Bo = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.Bo.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public DateTime getSettlementDateValue()
  {
    return this.Bo;
  }
  
  public String getSettlementDate()
  {
    if (this.Bo != null) {
      return this.Bo.toString();
    }
    return "";
  }
  
  public void setProcessTime(String paramString)
  {
    this.By = paramString;
  }
  
  public String getProcessTime()
  {
    if (this.By == null) {
      this.By = "";
    }
    return this.By;
  }
  
  public String getAmtCurrencyType()
  {
    return this.BN;
  }
  
  public void setAmtCurrencyType(String paramString)
  {
    this.BN = paramString;
  }
  
  public String getPayeeCurrencyType()
  {
    return this.BM;
  }
  
  public void setPayeeCurrencyType(String paramString)
  {
    this.BM = paramString;
  }
  
  public void setContractNumber(String paramString)
  {
    this.BD = paramString;
  }
  
  public String getContractNumber()
  {
    return this.BD;
  }
  
  public float getExchangeRateValue()
  {
    return this.BH;
  }
  
  public String getExchangeRate()
  {
    return Float.toString(this.BH);
  }
  
  public void setExchangeRate(float paramFloat)
  {
    this.BH = paramFloat;
  }
  
  public void setExchangeRate(String paramString)
  {
    if (paramString != null) {
      try
      {
        this.BH = Float.parseFloat(paramString);
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }
  
  public String getMathRule()
  {
    return this.Br;
  }
  
  public void setMathRule(String paramString)
  {
    this.Br = paramString;
  }
  
  public String getStatusName()
  {
    return ResourceUtil.getString("WireStatus" + this.BC, "com.ffusion.beans.wiretransfers.resources", this.locale);
  }
  
  public int getStatus()
  {
    return this.BC;
  }
  
  public void setStatus(int paramInt)
  {
    this.BC = paramInt;
  }
  
  public void setStatus(String paramString)
  {
    try
    {
      this.BC = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
  
  public String getConfirmationMsg()
  {
    return this.Bk;
  }
  
  public void setConfirmationMsg(String paramString)
  {
    this.Bk = paramString;
  }
  
  public void setConfirmationNum(String paramString)
  {
    this.Bt = paramString;
  }
  
  public String getConfirmationNum()
  {
    return this.Bt;
  }
  
  public void setFedConfirmationNum(String paramString)
  {
    this.Bs = paramString;
  }
  
  public String getFedConfirmationNum()
  {
    return this.Bs;
  }
  
  public void setCanEdit(String paramString)
  {
    this.BB = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setCanEdit(boolean paramBoolean)
  {
    this.BB = paramBoolean;
  }
  
  public String getCanEdit()
  {
    return String.valueOf(this.BB);
  }
  
  public boolean getCanEditValue()
  {
    return this.BB;
  }
  
  public void setCanDelete(String paramString)
  {
    this.BE = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setCanDelete(boolean paramBoolean)
  {
    this.BE = paramBoolean;
  }
  
  public String getCanDelete()
  {
    return String.valueOf(this.BE);
  }
  
  public boolean getCanDeleteValue()
  {
    return this.BE;
  }
  
  public String getOrigAmount()
  {
    return this.Bw;
  }
  
  public void setOrigAmount(String paramString)
  {
    if (paramString == null)
    {
      this.Bw = null;
    }
    else
    {
      Currency localCurrency = new Currency(paramString, this.locale);
      this.Bw = localCurrency.getAmountValue().toString();
    }
  }
  
  public String getTotalOrigAmount()
  {
    return calculateTotalOrigAmount();
  }
  
  public String calculateTotalOrigAmount()
  {
    if (this.wires != null)
    {
      BigDecimal localBigDecimal1 = new BigDecimal(0.0D);
      for (int i = 0; i < this.wires.size(); i++)
      {
        WireTransfer localWireTransfer = (WireTransfer)this.wires.get(i);
        if ((localWireTransfer.getAction() != null) && (!localWireTransfer.getAction().equals("del")))
        {
          BigDecimal localBigDecimal2 = null;
          if (localWireTransfer.getAction().equals("add") == true) {
            localBigDecimal2 = localWireTransfer.getAmountValue().getAmountValue();
          } else {
            localBigDecimal2 = new BigDecimal(localWireTransfer.getOrigAmount());
          }
          localBigDecimal1 = localBigDecimal1.add(localBigDecimal2);
        }
      }
      localBigDecimal1.setScale(2, 5);
      return localBigDecimal1.toString();
    }
    return "0.0";
  }
  
  public String getOrigCurrency()
  {
    return this.Bq;
  }
  
  public void setOrigCurrency(String paramString)
  {
    this.Bq = paramString;
  }
  
  public String getPaymentAmount()
  {
    return this.Bz;
  }
  
  public void setPaymentAmount(String paramString)
  {
    this.Bz = paramString;
  }
  
  public String getTotalPaymentAmount()
  {
    if ((this.wires != null) && (this.wires.size() > 0))
    {
      BigDecimal localBigDecimal1 = new BigDecimal(0.0D);
      for (int i = 0; i < this.wires.size(); i++)
      {
        WireTransfer localWireTransfer = (WireTransfer)this.wires.get(i);
        if ((localWireTransfer.getAction() == null) || (!localWireTransfer.getAction().equals("del")))
        {
          BigDecimal localBigDecimal2 = new BigDecimal(localWireTransfer.getPaymentAmount());
          localBigDecimal1 = localBigDecimal1.add(localBigDecimal2);
        }
      }
      return localBigDecimal1.toString();
    }
    return "0.0";
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if (paramString1.equals("BATCH_ID")) {
      return isFilterable(getBatchID(), paramString2, paramString3);
    }
    if (paramString1.equals("BATCH_TYPE")) {
      return isFilterable(getBatchType(), paramString2, paramString3);
    }
    if (paramString1.equals("TOTAL_DEBIT")) {
      return this.BF.isFilterable("VALUE" + paramString2 + paramString3);
    }
    if (paramString1.equals("WIRE_COUNT")) {
      return isFilterable(getWireCount(), paramString2, paramString3);
    }
    if (paramString1.equals("STATUS")) {
      return isFilterable(String.valueOf(getStatus()), paramString2, paramString3);
    }
    if ((paramString1.equals("DUE_DATE")) && (this.BL != null)) {
      return this.BL.isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((paramString1.equals("DATE_TO_POST")) && (this.BA != null)) {
      return this.BA.isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((paramString1.equals("BATCH_DESTINATION")) && (getBatchDestination() != null)) {
      return isFilterable(getBatchDestination(), paramString2, paramString3);
    }
    if ((paramString1.equals("CONFIRMATIONNUMBER")) && (getConfirmationNum() != null)) {
      return isFilterable(getConfirmationNum(), paramString2, paramString3);
    }
    if ((paramString1.equals("FED_CONFIRMATION_NUM")) && (getFedConfirmationNum() != null)) {
      return isFilterable(getFedConfirmationNum(), paramString2, paramString3);
    }
    if (paramString1.equals("CONTRACT_NUMBER")) {
      return isFilterable(getContractNumber(), paramString2, paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public Object clone()
  {
    try
    {
      WireBatch localWireBatch = (WireBatch)super.clone();
      if (this.BF != null) {
        localWireBatch.setTotalDebit((Currency)this.BF.clone());
      }
      if (this.Bx != null) {
        localWireBatch.setTotalCredit((Currency)this.Bx.clone());
      }
      if ((this.wires != null) && (this.wires.size() > 0))
      {
        WireTransfers localWireTransfers = new WireTransfers();
        for (int i = 0; i < this.wires.size(); i++)
        {
          WireTransfer localWireTransfer = (WireTransfer)this.wires.get(i);
          localWireTransfers.add((WireTransfer)localWireTransfer.clone());
        }
        localWireBatch.setWires(localWireTransfers);
      }
      if (this.BL != null) {
        localWireBatch.setDueDate(this.BL);
      }
      if (this.BA != null) {
        localWireBatch.setDateToPost(this.BA);
      }
      if (this.Bo != null) {
        localWireBatch.setSettlementDate(this.Bo);
      }
      return localWireBatch;
    }
    catch (Exception localException) {}
    return super.clone();
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "DATE_TO_POST");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    WireBatch localWireBatch = (WireBatch)paramObject;
    int i = 1;
    if ((paramString.equals("ID")) && (getID() != null) && (localWireBatch.getID() != null)) {
      i = numStringCompare(getID(), localWireBatch.getID());
    } else if ((paramString.equals("BATCH_ID")) && (getBatchID() != null) && (localWireBatch.getBatchID() != null)) {
      i = getBatchID().compareTo(localWireBatch.getBatchID());
    } else if ((paramString.equals("BATCH_NAME")) && (getBatchName() != null) && (localWireBatch.getBatchName() != null)) {
      i = getBatchName().compareTo(localWireBatch.getBatchName());
    } else if ((paramString.equals("BATCH_TYPE")) && (getBatchType() != null) && (localWireBatch.getBatchType() != null)) {
      i = getBatchType().compareTo(localWireBatch.getBatchType());
    } else if ((paramString.equals("CUSTOMER_ID")) && (getCustomerID() != null) && (localWireBatch.getCustomerID() != null)) {
      i = numStringCompare(getCustomerID(), localWireBatch.getCustomerID());
    } else if ((paramString.equals("SUBMITTED_BY")) && (getSubmittedBy() != null) && (localWireBatch.getSubmittedBy() != null)) {
      i = getSubmittedBy().compareTo(localWireBatch.getSubmittedBy());
    } else if ((paramString.equals("USERID")) && (getUserID() != null) && (localWireBatch.getUserID() != null)) {
      i = getUserID().compareTo(localWireBatch.getUserID());
    } else if ((paramString.equals("DUE_DATE")) && (getDueDateValue() != null) && (localWireBatch.getDueDateValue() != null)) {
      i = this.BL.equals(localWireBatch.getDueDateValue()) ? 0 : this.BL.before(localWireBatch.getDueDateValue()) ? -1 : 1;
    } else if ((paramString.equals("DATE_TO_POST")) && (getDateToPostValue() != null) && (localWireBatch.getDateToPostValue() != null)) {
      i = this.BA.equals(localWireBatch.getDateToPostValue()) ? 0 : this.BA.before(localWireBatch.getDateToPostValue()) ? -1 : 1;
    } else if ((paramString.equals("TOTAL_DEBIT")) && (this.BF != null) && (localWireBatch.getTotalDebitValue() != null)) {
      i = this.BF.compareTo(localWireBatch.getTotalDebitValue());
    } else if ((paramString.equals("TOTAL_CREDIT")) && (this.Bx != null) && (localWireBatch.getTotalCreditValue() != null)) {
      i = this.Bx.compareTo(localWireBatch.getTotalCreditValue());
    } else if (paramString.equals("STATUS")) {
      i = getStatus() - localWireBatch.getStatus();
    } else if ((paramString.equals("BATCH_DESTINATION")) && (getBatchDestination() != null) && (localWireBatch.getBatchDestination() != null)) {
      i = getBatchDestination().compareTo(localWireBatch.getBatchDestination());
    } else if ((paramString.equals("CONFIRMATIONNUMBER")) && (getConfirmationNum() != null) && (localWireBatch.getConfirmationNum() != null)) {
      i = numStringCompare(getConfirmationNum(), localWireBatch.getConfirmationNum());
    } else if ((paramString.equals("FED_CONFIRMATION_NUM")) && (getFedConfirmationNum() != null) && (localWireBatch.getFedConfirmationNum() != null)) {
      i = numStringCompare(getFedConfirmationNum(), localWireBatch.getFedConfirmationNum());
    } else if ((paramString.equals("CONTRACT_NUMBER")) && (getContractNumber() != null) && (localWireBatch.getContractNumber() != null)) {
      i = getContractNumber().compareTo(localWireBatch.getContractNumber());
    } else if ((paramString.equals("ORIG_AMOUNT")) && (getOrigAmount() != null) && (localWireBatch.getOrigAmount() != null)) {
      i = getOrigAmount().compareTo(localWireBatch.getOrigAmount());
    } else if ((paramString.equals("ORIG_CURRENCY")) && (getOrigCurrency() != null) && (localWireBatch.getOrigCurrency() != null)) {
      i = getOrigCurrency().compareTo(localWireBatch.getOrigCurrency());
    } else if ((paramString.equals("PAYMENT_AMOUNT")) && (getPaymentAmount() != null) && (localWireBatch.getPaymentAmount() != null)) {
      i = getPaymentAmount().compareTo(localWireBatch.getPaymentAmount());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "WIRE_BATCH");
    XMLHandler.appendTag(localStringBuffer, "BATCH_ID", getBatchID());
    XMLHandler.appendTag(localStringBuffer, "BATCH_NAME", getBatchName());
    XMLHandler.appendTag(localStringBuffer, "BATCH_TYPE", getBatchType());
    XMLHandler.appendTag(localStringBuffer, "BATCH_CATEGORY", getBatchCategory());
    XMLHandler.appendTag(localStringBuffer, "BATCH_DESTINATION", getBatchDestination());
    XMLHandler.appendTag(localStringBuffer, "BANK_ID", getBankID());
    XMLHandler.appendTag(localStringBuffer, "CUSTOMER_ID", getCustomerID());
    XMLHandler.appendTag(localStringBuffer, "SUBMITTED_BY", getSubmittedBy());
    XMLHandler.appendTag(localStringBuffer, "USERID", getUserID());
    XMLHandler.appendTag(localStringBuffer, "APPROVING_USER", getApprovingUser());
    XMLHandler.appendTag(localStringBuffer, "TOTAL_DEBIT", getTotalDebit());
    XMLHandler.appendTag(localStringBuffer, "TOTAL_CREDIT", getTotalCredit());
    XMLHandler.appendTag(localStringBuffer, "WIRE_COUNT", getWireCount());
    if (this.wires != null) {
      localStringBuffer.append(this.wires.getXML());
    }
    XMLHandler.appendTag(localStringBuffer, "MEMO", getMemo());
    XMLHandler.appendTag(localStringBuffer, "DUE_DATE", getDueDate());
    XMLHandler.appendTag(localStringBuffer, "DATE_TO_POST", getDateToPost());
    XMLHandler.appendTag(localStringBuffer, "SETTLEMENT_DATE", getSettlementDate());
    XMLHandler.appendTag(localStringBuffer, "PROCESS_TIME", getProcessTime());
    XMLHandler.appendTag(localStringBuffer, "CURRENCY_TYPE", getAmtCurrencyType());
    XMLHandler.appendTag(localStringBuffer, "PAYEE_CURRENCY_TYPE", getPayeeCurrencyType());
    XMLHandler.appendTag(localStringBuffer, "CONTRACT_NUMBER", getContractNumber());
    XMLHandler.appendTag(localStringBuffer, "EXCHANGE_RATE", getExchangeRate());
    XMLHandler.appendTag(localStringBuffer, "MATHRULE", getMathRule());
    XMLHandler.appendTag(localStringBuffer, "STATUS", getStatus());
    XMLHandler.appendTag(localStringBuffer, "CONFIRMATION_MSG", getConfirmationMsg());
    XMLHandler.appendTag(localStringBuffer, "CONFIRMATIONNUMBER", getConfirmationNum());
    XMLHandler.appendTag(localStringBuffer, "FED_CONFIRMATION_NUM", getFedConfirmationNum());
    XMLHandler.appendTag(localStringBuffer, "ORIG_AMOUNT", getOrigAmount());
    XMLHandler.appendTag(localStringBuffer, "ORIG_CURRENCY", getOrigCurrency());
    XMLHandler.appendTag(localStringBuffer, "PAYMENT_AMOUNT", getPaymentAmount());
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "WIRE_BATCH");
    return localStringBuffer.toString();
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    if (paramString1.equalsIgnoreCase("BATCH_ID")) {
      setBatchID(paramString2);
    } else if (paramString1.equalsIgnoreCase("BATCH_NAME")) {
      setBatchName(paramString2);
    } else if (paramString1.equalsIgnoreCase("BATCH_TYPE")) {
      setBatchType(paramString2);
    } else if (paramString1.equalsIgnoreCase("BATCH_CATEGORY")) {
      setBatchCategory(paramString2);
    } else if (paramString1.equalsIgnoreCase("BATCH_DESTINATION")) {
      setBatchDestination(paramString2);
    } else if (paramString1.equalsIgnoreCase("BANK_ID")) {
      setBankID(paramString2);
    } else if (paramString1.equalsIgnoreCase("CUSTOMER_ID")) {
      setCustomerID(paramString2);
    } else if (paramString1.equalsIgnoreCase("SUBMITTED_BY")) {
      setSubmittedBy(paramString2);
    } else if (paramString1.equals("USERID")) {
      setUserID(paramString2);
    } else if (paramString1.equalsIgnoreCase("APPROVING_USER")) {
      setApprovingUser(paramString2);
    } else if (paramString1.equalsIgnoreCase("TOTAL_DEBIT")) {
      setTotalDebit(paramString2);
    } else if (paramString1.equalsIgnoreCase("TOTAL_CREDIT")) {
      setTotalCredit(paramString2);
    } else if (paramString1.equalsIgnoreCase("WIRE_COUNT")) {
      setWireCount(paramString2);
    } else if (paramString1.equalsIgnoreCase("MEMO")) {
      setMemo(paramString2);
    } else if (paramString1.equalsIgnoreCase("DUE_DATE")) {
      setDueDate(paramString2);
    } else if (paramString1.equalsIgnoreCase("DATE_TO_POST")) {
      setDateToPost(paramString2);
    } else if (paramString1.equalsIgnoreCase("SETTLEMENT_DATE")) {
      setSettlementDate(paramString2);
    } else if (paramString1.equalsIgnoreCase("PROCESS_TIME")) {
      setProcessTime(paramString2);
    } else if (paramString1.equalsIgnoreCase("CURRENCY_TYPE")) {
      setAmtCurrencyType(paramString2);
    } else if (paramString1.equalsIgnoreCase("PAYEE_CURRENCY_TYPE")) {
      setPayeeCurrencyType(paramString2);
    } else if (paramString1.equalsIgnoreCase("CONTRACT_NUMBER")) {
      setContractNumber(paramString2);
    } else if (paramString1.equalsIgnoreCase("EXCHANGE_RATE")) {
      setExchangeRate(paramString2);
    } else if (paramString1.equalsIgnoreCase("MATHRULE")) {
      setMathRule(paramString2);
    } else if (paramString1.equalsIgnoreCase("STATUS")) {
      setStatus(paramString2);
    } else if (paramString1.equalsIgnoreCase("CONFIRMATION_MSG")) {
      setConfirmationMsg(paramString2);
    } else if (paramString1.equalsIgnoreCase("CONFIRMATIONNUMBER")) {
      setConfirmationNum(paramString2);
    } else if (paramString1.equalsIgnoreCase("FED_CONFIRMATION_NUM")) {
      setFedConfirmationNum(paramString2);
    } else if (paramString1.equalsIgnoreCase("ORIG_AMOUNT")) {
      setOrigAmount(paramString2);
    } else if (paramString1.equalsIgnoreCase("ORIG_CURRENCY")) {
      setOrigCurrency(paramString2);
    } else if (paramString1.equalsIgnoreCase("PAYMENT_AMOUNT")) {
      setPaymentAmount(paramString2);
    } else {
      return super.set(paramString1, paramString2);
    }
    return true;
  }
  
  public void set(WireBatch paramWireBatch)
  {
    if (paramWireBatch == null) {
      return;
    }
    super.set(paramWireBatch);
    setBatchID(paramWireBatch.getBatchID());
    setBatchName(paramWireBatch.getBatchName());
    setBatchType(paramWireBatch.getBatchType());
    setBatchCategory(paramWireBatch.getBatchCategory());
    setBatchDestination(paramWireBatch.getBatchDestination());
    setBankID(paramWireBatch.getBankID());
    setCustomerID(paramWireBatch.getCustomerID());
    setSubmittedBy(paramWireBatch.getSubmittedBy());
    setUserID(paramWireBatch.getUserID());
    setApprovingUser(paramWireBatch.getApprovingUser());
    if (paramWireBatch.getTotalDebitValue() != null) {
      setTotalDebit((Currency)paramWireBatch.getTotalDebitValue().clone());
    } else {
      setTotalDebit((Currency)null);
    }
    if (paramWireBatch.getTotalCreditValue() != null) {
      setTotalCredit((Currency)paramWireBatch.getTotalCreditValue().clone());
    } else {
      setTotalCredit((Currency)null);
    }
    setWireCount(paramWireBatch.getWireCount());
    WireTransfers localWireTransfers1 = paramWireBatch.getWires();
    if (localWireTransfers1 != null)
    {
      WireTransfers localWireTransfers2 = new WireTransfers();
      for (int i = 0; i < localWireTransfers1.size(); i++)
      {
        WireTransfer localWireTransfer = (WireTransfer)localWireTransfers1.get(i);
        localWireTransfers2.add(localWireTransfer.clone());
      }
      setWires(localWireTransfers2);
    }
    setMemo(paramWireBatch.getMemo());
    if (paramWireBatch.getDueDateValue() != null) {
      setDueDate((DateTime)paramWireBatch.getDueDateValue().clone());
    } else {
      setDueDate((DateTime)null);
    }
    if (paramWireBatch.getDateToPostValue() != null) {
      setDateToPost((DateTime)paramWireBatch.getDateToPostValue().clone());
    } else {
      setDateToPost((DateTime)null);
    }
    if (paramWireBatch.getSettlementDateValue() != null) {
      setSettlementDate((DateTime)paramWireBatch.getSettlementDateValue().clone());
    } else {
      setSettlementDate((DateTime)null);
    }
    setProcessTime(paramWireBatch.getProcessTime());
    setAmtCurrencyType(paramWireBatch.getAmtCurrencyType());
    setPayeeCurrencyType(paramWireBatch.getPayeeCurrencyType());
    setContractNumber(paramWireBatch.getContractNumber());
    setExchangeRate(paramWireBatch.getExchangeRateValue());
    setMathRule(paramWireBatch.getMathRule());
    setStatus(paramWireBatch.getStatus());
    setConfirmationMsg(paramWireBatch.getConfirmationMsg());
    setConfirmationNum(paramWireBatch.getConfirmationNum());
    setFedConfirmationNum(paramWireBatch.getFedConfirmationNum());
    setOrigAmount(paramWireBatch.getOrigAmount());
    setOrigCurrency(paramWireBatch.getOrigCurrency());
    setPaymentAmount(paramWireBatch.getPaymentAmount());
    setVersion(paramWireBatch.getVersion());
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
  
  public WireBatchInfo getWireBatchInfo()
  {
    WireBatchInfo localWireBatchInfo = new WireBatchInfo();
    localWireBatchInfo.setBatchId(getBatchID());
    localWireBatchInfo.setBatchName(getBatchName());
    localWireBatchInfo.setBatchType(getBatchType());
    localWireBatchInfo.setBatchCategory(getBatchCategory());
    localWireBatchInfo.setBatchDest(getBatchDestination());
    localWireBatchInfo.setFIId(getBankID());
    localWireBatchInfo.setCustomerId(getCustomerID());
    localWireBatchInfo.setSubmittedBy(getSubmittedBy());
    localWireBatchInfo.setUserId(getUserID());
    localWireBatchInfo.setExtId(getTrackingID());
    localWireBatchInfo.setTotalAmount(getTotalAmountForBPW());
    localWireBatchInfo.setTotalDebitAmount(getTotalDebitForBPW());
    localWireBatchInfo.setTotalCreditAmount(getTotalCreditForBPW());
    localWireBatchInfo.setWireCount(getWireCount());
    localWireBatchInfo.setOrigAmount(getOrigAmount());
    localWireBatchInfo.setOrigCurrency(getOrigCurrency());
    if ((this.wires != null) && (this.wires.size() > 0))
    {
      WireInfo[] arrayOfWireInfo = new WireInfo[this.wires.size()];
      for (int i = 0; i < arrayOfWireInfo.length; i++)
      {
        WireTransfer localWireTransfer = (WireTransfer)this.wires.get(i);
        if (localWireTransfer != null)
        {
          localWireTransfer.setCustomerID(getCustomerID());
          localWireTransfer.setSubmittedBy(getSubmittedBy());
          localWireTransfer.setUserID(getUserID());
          localWireTransfer.setBankID(getBankID());
          localWireTransfer.setWireType("SINGLE");
          localWireTransfer.setWireSource(getBatchType());
          localWireTransfer.setWireDestination(getBatchDestination());
          localWireTransfer.setAmtCurrencyType(getAmtCurrencyType());
          localWireTransfer.setPayeeCurrencyType(getPayeeCurrencyType());
          localWireTransfer.setExchangeRate(getExchangeRateValue());
          localWireTransfer.setMathRule(getMathRule());
          localWireTransfer.setContractNumber(getContractNumber());
          localWireTransfer.setOrigCurrency(getOrigCurrency());
          localWireTransfer.setDueDate((DateTime)getDueDateValue().clone());
          localWireTransfer.setSettlementDate((DateTime)getSettlementDateValue().clone());
          WireInfo localWireInfo = localWireTransfer.getWireInfo();
          if (this.Bp.equals("DRAWDOWN")) {
            localWireInfo.setAmountType("CREDIT");
          } else {
            localWireInfo.setAmountType("DEBIT");
          }
          arrayOfWireInfo[i] = localWireInfo;
        }
      }
      localWireBatchInfo.setWires(arrayOfWireInfo);
    }
    localWireBatchInfo.setMemo(getMemo());
    localWireBatchInfo.setAmtCurrency(getAmtCurrencyType());
    localWireBatchInfo.setDestCurrency(getPayeeCurrencyType());
    localWireBatchInfo.setContractNumber(getContractNumber());
    localWireBatchInfo.setExchangeRate(getExchangeRate());
    localWireBatchInfo.setMathRule(getMathRule());
    localWireBatchInfo.setVersion(getVersion());
    return localWireBatchInfo;
  }
  
  public void setBatchInfo(WireBatchInfo paramWireBatchInfo)
  {
    if (paramWireBatchInfo == null) {
      return;
    }
    try
    {
      setBatchID(paramWireBatchInfo.getBatchId());
      setBatchName(paramWireBatchInfo.getBatchName());
      setBatchType(paramWireBatchInfo.getBatchType());
      setBatchCategory(paramWireBatchInfo.getBatchCategory());
      setBatchDestination(paramWireBatchInfo.getBatchDest());
      setBankID(paramWireBatchInfo.getFIId());
      setCustomerID(paramWireBatchInfo.getCustomerId());
      setSubmittedBy(paramWireBatchInfo.getSubmittedBy());
      setUserID(paramWireBatchInfo.getUserId());
      setTrackingID(paramWireBatchInfo.getExtId());
      setBankID(paramWireBatchInfo.getFIId());
      setTotalDebit(new BigDecimal(paramWireBatchInfo.getTotalDebitAmount()));
      setTotalCredit(new BigDecimal(paramWireBatchInfo.getTotalCreditAmount()));
      setWireCount(paramWireBatchInfo.getWireCount());
      WireInfo[] arrayOfWireInfo = paramWireBatchInfo.getWires();
      if ((arrayOfWireInfo != null) && (arrayOfWireInfo.length > 0))
      {
        WireTransfers localWireTransfers = new WireTransfers();
        for (int i = 0; i < arrayOfWireInfo.length; i++)
        {
          WireTransfer localWireTransfer = (WireTransfer)localWireTransfers.create();
          localWireTransfer.setWireInfo(arrayOfWireInfo[i]);
          localWireTransfer.setBatchID(paramWireBatchInfo.getBatchId());
        }
        setWires(localWireTransfers);
      }
      setMemo(paramWireBatchInfo.getMemo());
      setAmtCurrencyType(paramWireBatchInfo.getAmtCurrency());
      setPayeeCurrencyType(paramWireBatchInfo.getDestCurrency());
      setContractNumber(paramWireBatchInfo.getContractNumber());
      setExchangeRate(paramWireBatchInfo.getExchangeRate());
      setMathRule(paramWireBatchInfo.getMathRule());
      setOrigAmount(paramWireBatchInfo.getOrigAmount());
      setOrigCurrency(paramWireBatchInfo.getOrigCurrency());
      setConfirmationMsg(paramWireBatchInfo.getConfirmMsg());
      setConfirmationNum(paramWireBatchInfo.getConfirmNum());
      setFedConfirmationNum(paramWireBatchInfo.getConfirmNum2());
      setVersion(paramWireBatchInfo.getVersion());
      setBatchDates(paramWireBatchInfo);
      setStatus(WireStatusMap.mapStatusToInt(paramWireBatchInfo.getPrcStatus()));
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public Histories logChanges(SecureUser paramSecureUser, WireBatch paramWireBatch, String paramString)
  {
    Histories localHistories = new Histories(this.locale);
    WireTransfers localWireTransfers1 = getWires();
    WireTransfers localWireTransfers2 = paramWireBatch.getWires();
    if ((localWireTransfers1 == null) || (localWireTransfers2 == null))
    {
      DebugLog.log("WARNING: Unable to record history for batch wires modification due to null old or original value.");
      return localHistories;
    }
    for (int i = 0; i < localWireTransfers1.size(); i++)
    {
      WireTransfer localWireTransfer1 = (WireTransfer)localWireTransfers1.get(i);
      String str = localWireTransfer1.getAction();
      if (str != null)
      {
        HistoryTracker localHistoryTracker;
        if (str.equals("add"))
        {
          localHistoryTracker = new HistoryTracker(paramSecureUser, 12, localWireTransfer1.getTrackingID());
          if (paramString == null) {
            paramString = localHistoryTracker.lookupComment(1);
          }
          localWireTransfer1.logCreate(paramSecureUser, localHistoryTracker, paramString);
          localHistories.addAll(localHistoryTracker.getHistories());
        }
        else if (str.equals("del"))
        {
          localHistoryTracker = new HistoryTracker(paramSecureUser, 12, localWireTransfer1.getTrackingID());
          if (paramString == null) {
            paramString = localHistoryTracker.lookupComment(2);
          }
          localWireTransfer1.logDelete(localHistoryTracker, paramString);
          localHistories.addAll(localHistoryTracker.getHistories());
        }
        else
        {
          localHistoryTracker = new HistoryTracker(paramSecureUser, 12, localWireTransfer1.getTrackingID());
          WireTransfer localWireTransfer2 = (WireTransfer)localWireTransfers2.getByID(localWireTransfer1.getID());
          if (localWireTransfer2 != null)
          {
            if (paramString == null) {
              paramString = localHistoryTracker.lookupComment(17);
            }
            localWireTransfer1.logChanges(paramSecureUser, localHistoryTracker, localWireTransfer2, paramString);
          }
          else
          {
            if (paramString == null) {
              paramString = localHistoryTracker.lookupComment(1);
            }
            localWireTransfer1.logCreate(paramSecureUser, localHistoryTracker, paramString);
          }
          localHistories.addAll(localHistoryTracker.getHistories());
        }
      }
    }
    return localHistories;
  }
  
  public Histories logCreate(SecureUser paramSecureUser, String paramString)
  {
    Histories localHistories = new Histories(this.locale);
    for (int i = 0; i < this.wires.size(); i++)
    {
      WireTransfer localWireTransfer = (WireTransfer)this.wires.get(i);
      HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 12, localWireTransfer.getTrackingID());
      if (paramString == null) {
        paramString = localHistoryTracker.lookupComment(1);
      }
      localWireTransfer.logCreate(paramSecureUser, localHistoryTracker, paramString);
      localHistories.addAll(localHistoryTracker.getHistories());
    }
    return localHistories;
  }
  
  public Histories logDelete(SecureUser paramSecureUser, String paramString)
  {
    Histories localHistories = new Histories(this.locale);
    for (int i = 0; i < this.wires.size(); i++)
    {
      WireTransfer localWireTransfer = (WireTransfer)this.wires.get(i);
      HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 12, localWireTransfer.getTrackingID());
      if (paramString == null) {
        paramString = localHistoryTracker.lookupComment(2);
      }
      localWireTransfer.logDelete(localHistoryTracker, paramString);
      localHistories.addAll(localHistoryTracker.getHistories());
    }
    return localHistories;
  }
  
  public Histories logChanges(SecureUser paramSecureUser, WireBatch paramWireBatch, ILocalizable paramILocalizable)
  {
    Histories localHistories = new Histories(this.locale);
    WireTransfers localWireTransfers1 = getWires();
    WireTransfers localWireTransfers2 = paramWireBatch.getWires();
    if ((localWireTransfers1 == null) || (localWireTransfers2 == null))
    {
      DebugLog.log("WARNING: Unable to record history for batch wires modification due to null old or original value.");
      return localHistories;
    }
    for (int i = 0; i < localWireTransfers1.size(); i++)
    {
      WireTransfer localWireTransfer1 = (WireTransfer)localWireTransfers1.get(i);
      String str = localWireTransfer1.getAction();
      if (str != null)
      {
        HistoryTracker localHistoryTracker;
        if (str.equals("add"))
        {
          localHistoryTracker = new HistoryTracker(paramSecureUser, 12, localWireTransfer1.getTrackingID());
          localWireTransfer1.logCreate(paramSecureUser, localHistoryTracker, localHistoryTracker.buildLocalizableComment(1));
          localHistories.addAll(localHistoryTracker.getHistories());
        }
        else if (str.equals("del"))
        {
          localHistoryTracker = new HistoryTracker(paramSecureUser, 12, localWireTransfer1.getTrackingID());
          localWireTransfer1.logDelete(localHistoryTracker, localHistoryTracker.buildLocalizableComment(2));
          localHistories.addAll(localHistoryTracker.getHistories());
        }
        else
        {
          localHistoryTracker = new HistoryTracker(paramSecureUser, 12, localWireTransfer1.getTrackingID());
          WireTransfer localWireTransfer2 = (WireTransfer)localWireTransfers2.getByID(localWireTransfer1.getID());
          if (localWireTransfer2 != null) {
            localWireTransfer1.logChanges(paramSecureUser, localHistoryTracker, localWireTransfer2, localHistoryTracker.buildLocalizableComment(17));
          } else {
            localWireTransfer1.logCreate(paramSecureUser, localHistoryTracker, localHistoryTracker.buildLocalizableComment(1));
          }
          localHistories.addAll(localHistoryTracker.getHistories());
        }
      }
    }
    return localHistories;
  }
  
  public Histories logCreate(SecureUser paramSecureUser, ILocalizable paramILocalizable)
  {
    Histories localHistories = new Histories(this.locale);
    for (int i = 0; i < this.wires.size(); i++)
    {
      WireTransfer localWireTransfer = (WireTransfer)this.wires.get(i);
      HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 12, localWireTransfer.getTrackingID());
      localWireTransfer.logCreate(paramSecureUser, localHistoryTracker, localHistoryTracker.buildLocalizableComment(1));
      localHistories.addAll(localHistoryTracker.getHistories());
    }
    return localHistories;
  }
  
  public Histories logDelete(SecureUser paramSecureUser, ILocalizable paramILocalizable)
  {
    Histories localHistories = new Histories(this.locale);
    for (int i = 0; i < this.wires.size(); i++)
    {
      WireTransfer localWireTransfer = (WireTransfer)this.wires.get(i);
      HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 12, localWireTransfer.getTrackingID());
      localWireTransfer.logDelete(localHistoryTracker, localHistoryTracker.buildLocalizableComment(2));
      localHistories.addAll(localHistoryTracker.getHistories());
    }
    return localHistories;
  }
  
  public AuditLogRecord getAuditRecord(SecureUser paramSecureUser, String paramString1, int paramInt, String paramString2)
  {
    return getAuditRecord(paramSecureUser, new LocalizableString("dummy", paramString1, null), paramInt, paramString2);
  }
  
  public AuditLogRecord getAuditRecord(SecureUser paramSecureUser, ILocalizable paramILocalizable, int paramInt, String paramString)
  {
    String str1 = null;
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
    AuditLogRecord localAuditLogRecord = new AuditLogRecord(str1, i, str2, str3, paramILocalizable, getTrackingID(), paramInt, paramSecureUser.getBusinessID(), getTotalDebitValue() != null ? getTotalDebitValue().getAmountValue() : null, getAmtCurrencyType(), getID(), paramString, null, null, null, null, AuditLogUtil.getModuleFromTranType(paramInt));
    return localAuditLogRecord;
  }
  
  public void setBatchDates(WireBatchInfo paramWireBatchInfo)
  {
    setDateFormat("yyyyMMdd");
    String str = paramWireBatchInfo.getDateDue();
    if ((str != null) && (str.length() > 8)) {
      str = str.substring(0, 8);
    }
    setDueDate(str);
    str = paramWireBatchInfo.getDateToPost();
    if ((str != null) && (str.length() > 8)) {
      str = str.substring(0, 8);
    }
    setDateToPost(str);
    str = paramWireBatchInfo.getSettlementDate();
    if ((str != null) && (str.length() > 8)) {
      str = str.substring(0, 8);
    }
    setSettlementDate(str);
    setDateFormat("SHORT");
  }
  
  public int getApprovalType()
  {
    return super.getApprovalType();
  }
  
  public int getApprovalSubType()
  {
    return 10;
  }
  
  public String getApprovalSubTypeName()
  {
    return "Wire Batch";
  }
  
  public Currency getApprovalAmount()
  {
    return getAmountValue();
  }
  
  public DateTime getApprovalDueDate()
  {
    return getDateToPostValue();
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
      if (paramString.equalsIgnoreCase("WIRE_TRANSFERS") == true)
      {
        WireTransfers localWireTransfers = new WireTransfers();
        WireBatch.this.setWires(localWireTransfers);
        localWireTransfers.continueXMLParsing(getHandler());
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
      WireBatch.this.set(getElement(), str);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.wiretransfers.WireBatch
 * JD-Core Version:    0.7.0.1
 */