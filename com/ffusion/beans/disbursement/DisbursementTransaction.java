package com.ffusion.beans.disbursement;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.util.XMLHandler;
import java.text.Collator;
import java.util.Locale;

public class DisbursementTransaction
  extends ExtendABean
  implements Comparable
{
  public static final String DISBURSEMENTTRANSACTION = "DISBURSEMENTTRANSACTION";
  public static final String ACCOUNTID = "ACCOUNTID";
  public static final String ACCOUNTNUMBER = "ACCOUNTNUMBER";
  public static final String BANKID = "BANKID";
  public static final String ROUTINGNUMBER = "ROUTINGNUMBER";
  public static final String TRANSACTIONINDEX = "TRANSACTIONINDEX";
  public static final String TRANSID = "TRANSID";
  public static final String PROCESSINGDATE = "PROCESSINGDATE";
  public static final String CHECKDATE = "CHECKDATE";
  public static final String PAYEE = "PAYEE";
  public static final String CHECKAMOUNT = "CHECKAMOUNT";
  public static final String CHECKNUMBER = "CHECKNUMBER";
  public static final String CHECKREFERENCENUMBER = "CHECKREFERENCENUMBER";
  public static final String MEMO = "MEMO";
  public static final String ISSUEDBY = "ISSUEDBY";
  public static final String APPROVEDBY = "APPROVEDBY";
  public static final String IMMEDIATEFUNDSNEEDED = "IMMEDIATEFUNDSNEEDED";
  public static final String ONEDAYFUNDSNEEDED = "ONEDAYFUNDSNEEDED";
  public static final String TWODAYFUNDSNEEDED = "TWODAYFUNDSNEEDED";
  public static final String VALUEDATETIME = "VALUEDATETIME";
  public static final String BANKREFERENCENUMBER = "BANKREFERENCENUMBER";
  public static final String CUSTOMERREFERENCENUMBER = "CUSTOMERREFERENCENUMBER";
  public static final String PRESENTMENT = "PRESENTMENT";
  private String aYF;
  private String aYH;
  private String aYx;
  private String aYN;
  private long aYO;
  private int aYI;
  private DateTime aYw;
  private DateTime aYv;
  private String aYu;
  private Currency aYB;
  private String aYz;
  private String aYK;
  private String aYy;
  private String aYJ;
  private String aYM;
  private Currency aYD;
  private Currency aYt;
  private Currency aYE;
  private DateTime aYL;
  private String aYA;
  private String aYC;
  private String aYG;
  
  public DisbursementTransaction() {}
  
  public DisbursementTransaction(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.aYw != null) {
      this.aYw.setLocale(paramLocale);
    }
    if (this.aYv != null) {
      this.aYv.setLocale(paramLocale);
    }
    if (this.aYB != null) {
      this.aYB.setLocale(paramLocale);
    }
    if (this.aYD != null) {
      this.aYD.setLocale(paramLocale);
    }
    if (this.aYt != null) {
      this.aYt.setLocale(paramLocale);
    }
    if (this.aYE != null) {
      this.aYE.setLocale(paramLocale);
    }
    if (this.aYL != null) {
      this.aYL.setLocale(paramLocale);
    }
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.aYw != null) {
      this.aYw.setFormat(paramString);
    }
    if (this.aYv != null) {
      this.aYv.setFormat(paramString);
    }
    if (this.aYL != null) {
      this.aYL.setFormat(paramString);
    }
  }
  
  public String getAccountID()
  {
    return this.aYF;
  }
  
  public String getAccountNumber()
  {
    return this.aYH;
  }
  
  public String getBankID()
  {
    return this.aYx;
  }
  
  public String getRoutingNumber()
  {
    return this.aYN;
  }
  
  public long getTransactionIndex()
  {
    return this.aYO;
  }
  
  public DateTime getProcessingDate()
  {
    return this.aYw;
  }
  
  public int getTransID()
  {
    return this.aYI;
  }
  
  public DateTime getCheckDate()
  {
    return this.aYv;
  }
  
  public String getPayee()
  {
    return this.aYu;
  }
  
  public Currency getCheckAmount()
  {
    return this.aYB;
  }
  
  public String getCheckNumber()
  {
    return this.aYz;
  }
  
  public String getCheckReferenceNumber()
  {
    return this.aYK;
  }
  
  public String getMemo()
  {
    return this.aYy;
  }
  
  public String getIssuedBy()
  {
    return this.aYJ;
  }
  
  public String getApprovedBy()
  {
    return this.aYM;
  }
  
  public Currency getImmediateFundsNeeded()
  {
    return this.aYD;
  }
  
  public Currency getOneDayFundsNeeded()
  {
    return this.aYt;
  }
  
  public Currency getTwoDayFundsNeeded()
  {
    return this.aYE;
  }
  
  public DateTime getValueDateTime()
  {
    return this.aYL;
  }
  
  public String getBankReferenceNumber()
  {
    return this.aYA;
  }
  
  public String getCustomerReferenceNumber()
  {
    return this.aYC;
  }
  
  public String getPresentment()
  {
    return this.aYG;
  }
  
  public void setAccountID(String paramString)
  {
    this.aYF = "ID";
  }
  
  public void setAccountNumber(String paramString)
  {
    this.aYH = paramString;
  }
  
  public void setBankID(String paramString)
  {
    this.aYx = paramString;
  }
  
  public void setRoutingNumber(String paramString)
  {
    this.aYN = paramString;
  }
  
  public void setTransactionIndex(long paramLong)
  {
    this.aYO = paramLong;
  }
  
  public void setTransID(int paramInt)
  {
    this.aYI = paramInt;
  }
  
  public void setCheckDate(DateTime paramDateTime)
  {
    this.aYv = paramDateTime;
  }
  
  public void setPayee(String paramString)
  {
    this.aYu = paramString;
  }
  
  public void setCheckAmount(Currency paramCurrency)
  {
    this.aYB = paramCurrency;
  }
  
  public void setCheckNumber(String paramString)
  {
    this.aYz = paramString;
  }
  
  public void setCheckReferenceNumber(String paramString)
  {
    this.aYK = paramString;
  }
  
  public void setMemo(String paramString)
  {
    this.aYy = paramString;
  }
  
  public void setIssuedBy(String paramString)
  {
    this.aYJ = paramString;
  }
  
  public void setApprovedBy(String paramString)
  {
    this.aYM = paramString;
  }
  
  public void setImmediateFundsNeeded(Currency paramCurrency)
  {
    this.aYD = paramCurrency;
  }
  
  public void setOneDayFundsNeeded(Currency paramCurrency)
  {
    this.aYt = paramCurrency;
  }
  
  public void setTwoDayFundsNeeded(Currency paramCurrency)
  {
    this.aYE = paramCurrency;
  }
  
  public void setValueDateTime(DateTime paramDateTime)
  {
    this.aYL = paramDateTime;
  }
  
  public void setBankReferenceNumber(String paramString)
  {
    this.aYA = paramString;
  }
  
  public void setCustomerReferenceNumber(String paramString)
  {
    this.aYC = paramString;
  }
  
  public void setProcessingDate(DateTime paramDateTime)
  {
    this.aYw = paramDateTime;
  }
  
  public void setPresentment(String paramString)
  {
    this.aYG = paramString;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "TRANSACTIONINDEX");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    DisbursementTransaction localDisbursementTransaction = (DisbursementTransaction)paramObject;
    int i = 1;
    Collator localCollator = doGetCollator();
    if ((paramString.equals("ACCOUNTID")) && (this.aYF != null) && (localDisbursementTransaction.getAccountID() != null)) {
      return localCollator.compare(getAccountID(), localDisbursementTransaction.getAccountID());
    }
    if ((paramString.equals("ACCOUNTNUMBER")) && (this.aYH != null) && (localDisbursementTransaction.getAccountNumber() != null)) {
      return localCollator.compare(getAccountNumber(), localDisbursementTransaction.getAccountNumber());
    }
    if ((paramString.equals("BANKID")) && (this.aYx != null) && (localDisbursementTransaction.getBankID() != null)) {
      return localCollator.compare(getBankID(), localDisbursementTransaction.getBankID());
    }
    if ((paramString.equals("ROUTINGNUMBER")) && (this.aYN != null) && (localDisbursementTransaction.getRoutingNumber() != null)) {
      return localCollator.compare(getRoutingNumber(), localDisbursementTransaction.getRoutingNumber());
    }
    if (paramString.equals("TRANSACTIONINDEX"))
    {
      i = (int)(this.aYO - localDisbursementTransaction.getTransactionIndex());
    }
    else if (paramString.equals("TRANSID"))
    {
      i = this.aYI - localDisbursementTransaction.getTransID();
    }
    else if ((paramString.equals("PROCESSINGDATE")) && (getProcessingDate() != null) && (localDisbursementTransaction.getProcessingDate() != null))
    {
      i = getProcessingDate().equals(localDisbursementTransaction.getProcessingDate()) ? 0 : getProcessingDate().before(localDisbursementTransaction.getProcessingDate()) ? -1 : 1;
    }
    else if ((paramString.equals("CHECKDATE")) && (getCheckDate() != null) && (localDisbursementTransaction.getCheckDate() != null))
    {
      i = getCheckDate().equals(localDisbursementTransaction.getCheckDate()) ? 0 : getCheckDate().before(localDisbursementTransaction.getCheckDate()) ? -1 : 1;
    }
    else
    {
      if ((paramString.equals("PAYEE")) && (getPayee() != null) && (localDisbursementTransaction.getPayee() != null)) {
        return localCollator.compare(getPayee(), localDisbursementTransaction.getPayee());
      }
      if ((paramString.equals("CHECKAMOUNT")) && (getCheckAmount() != null) && (localDisbursementTransaction.getCheckAmount() != null))
      {
        i = getCheckAmount().compareTo(localDisbursementTransaction.getCheckAmount());
      }
      else
      {
        if ((paramString.equals("CHECKNUMBER")) && (getCheckNumber() != null) && (localDisbursementTransaction.getCheckNumber() != null)) {
          return localCollator.compare(getCheckNumber(), localDisbursementTransaction.getCheckNumber());
        }
        if ((paramString.equals("CHECKREFERENCENUMBER")) && (getCheckReferenceNumber() != null) && (localDisbursementTransaction.getCheckReferenceNumber() != null)) {
          return localCollator.compare(getCheckReferenceNumber(), localDisbursementTransaction.getCheckReferenceNumber());
        }
        if (paramString.equals("MEMO")) {
          return localCollator.compare(getMemo(), localDisbursementTransaction.getMemo());
        }
        if ((paramString.equals("ISSUEDBY")) && (getIssuedBy() != null) && (localDisbursementTransaction.getIssuedBy() != null)) {
          return localCollator.compare(getIssuedBy(), localDisbursementTransaction.getIssuedBy());
        }
        if ((paramString.equals("APPROVEDBY")) && (getApprovedBy() != null) && (localDisbursementTransaction.getApprovedBy() != null)) {
          return localCollator.compare(getApprovedBy(), localDisbursementTransaction.getApprovedBy());
        }
        if ((paramString.equals("IMMEDIATEFUNDSNEEDED")) && (getImmediateFundsNeeded() != null) && (localDisbursementTransaction.getImmediateFundsNeeded() != null))
        {
          i = getImmediateFundsNeeded().compareTo(localDisbursementTransaction.getImmediateFundsNeeded());
        }
        else if ((paramString.equals("ONEDAYFUNDSNEEDED")) && (getOneDayFundsNeeded() != null) && (localDisbursementTransaction.getOneDayFundsNeeded() != null))
        {
          i = getOneDayFundsNeeded().compareTo(localDisbursementTransaction.getOneDayFundsNeeded());
        }
        else if ((paramString.equals("TWODAYFUNDSNEEDED")) && (getTwoDayFundsNeeded() != null) && (localDisbursementTransaction.getTwoDayFundsNeeded() != null))
        {
          i = getTwoDayFundsNeeded().compareTo(localDisbursementTransaction.getTwoDayFundsNeeded());
        }
        else if ((paramString.equals("VALUEDATETIME")) && (getValueDateTime() != null) && (localDisbursementTransaction.getValueDateTime() != null))
        {
          i = getValueDateTime().equals(localDisbursementTransaction.getValueDateTime()) ? 0 : getValueDateTime().before(localDisbursementTransaction.getValueDateTime()) ? -1 : 1;
        }
        else
        {
          if ((paramString.equals("BANKREFERENCENUMBER")) && (getBankReferenceNumber() != null) && (localDisbursementTransaction.getBankReferenceNumber() != null)) {
            return localCollator.compare(getBankReferenceNumber(), localDisbursementTransaction.getBankReferenceNumber());
          }
          if ((paramString.equals("CUSTOMERREFERENCENUMBER")) && (getCustomerReferenceNumber() != null) && (localDisbursementTransaction.getCustomerReferenceNumber() != null)) {
            return localCollator.compare(getCustomerReferenceNumber(), localDisbursementTransaction.getCustomerReferenceNumber());
          }
          if ((paramString.equals("PRESENTMENT")) && (getPresentment() != null) && (localDisbursementTransaction.getPresentment() != null)) {
            return localCollator.compare(getPresentment(), localDisbursementTransaction.getPresentment());
          }
          i = super.compare(paramObject, paramString);
        }
      }
    }
    return i;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1.equals("ACCOUNTID")) && (getAccountID() != null)) {
      return isFilterable(getAccountID(), paramString2, paramString3);
    }
    if ((paramString1.equals("ACCOUNTNUMBER")) && (getAccountNumber() != null)) {
      return isFilterable(getAccountNumber(), paramString2, paramString3);
    }
    if ((paramString1.equals("BANKID")) && (getBankID() != null)) {
      return isFilterable(getBankID(), paramString2, paramString3);
    }
    if ((paramString1.equals("ROUTINGNUMBER")) && (getRoutingNumber() != null)) {
      return isFilterable(getRoutingNumber(), paramString2, paramString3);
    }
    if (paramString1.equals("TRANSACTIONINDEX")) {
      return isFilterable(String.valueOf(getTransactionIndex()), paramString2, paramString3);
    }
    if (paramString1.equals("TRANSID")) {
      return isFilterable(String.valueOf(getTransID()), paramString2, paramString3);
    }
    if ((paramString1.equals("PROCESSINGDATE")) && (getProcessingDate() != null)) {
      return getProcessingDate().isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((paramString1.equals("CHECKDATE")) && (getCheckDate() != null)) {
      return getCheckDate().isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((paramString1.equals("CHECKAMOUNT")) && (getCheckAmount() != null)) {
      return getCheckAmount().isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((paramString1.equals("PAYEE")) && (getPayee() != null)) {
      return isFilterable(getPayee(), paramString2, paramString3);
    }
    if ((paramString1.equals("CHECKNUMBER")) && (getCheckNumber() != null)) {
      return isFilterable(getCheckNumber(), paramString2, paramString3);
    }
    if ((paramString1.equals("CHECKREFERENCENUMBER")) && (getCheckReferenceNumber() != null)) {
      return isFilterable(getCheckReferenceNumber(), paramString2, paramString3);
    }
    if ((paramString1.equals("MEMO")) && (getMemo() != null)) {
      return isFilterable(getMemo(), paramString2, paramString3);
    }
    if ((paramString1.equals("ISSUEDBY")) && (getIssuedBy() != null)) {
      return isFilterable(getIssuedBy(), paramString2, paramString3);
    }
    if ((paramString1.equals("APPROVEDBY")) && (getApprovedBy() != null)) {
      return isFilterable(getApprovedBy(), paramString2, paramString3);
    }
    if ((paramString1.equals("IMMEDIATEFUNDSNEEDED")) && (getImmediateFundsNeeded() != null)) {
      return getImmediateFundsNeeded().isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((paramString1.equals("ONEDAYFUNDSNEEDED")) && (getOneDayFundsNeeded() != null)) {
      return getOneDayFundsNeeded().isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((paramString1.equals("TWODAYFUNDSNEEDED")) && (getTwoDayFundsNeeded() != null)) {
      return getTwoDayFundsNeeded().isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((paramString1.equals("VALUEDATETIME")) && (getValueDateTime() != null)) {
      return getValueDateTime().isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((paramString1.equals("BANKREFERENCENUMBER")) && (getBankReferenceNumber() != null)) {
      return isFilterable(getBankReferenceNumber(), paramString2, paramString3);
    }
    if ((paramString1.equals("CUSTOMERREFERENCENUMBER")) && (getCustomerReferenceNumber() != null)) {
      return isFilterable(getCustomerReferenceNumber(), paramString2, paramString3);
    }
    if ((paramString1.equals("PRESENTMENT")) && (getPresentment() != null)) {
      return isFilterable(getPresentment(), paramString2, paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public void set(DisbursementTransaction paramDisbursementTransaction)
  {
    if ((this == paramDisbursementTransaction) || (paramDisbursementTransaction == null)) {
      return;
    }
    setAccountID(paramDisbursementTransaction.getAccountID());
    setAccountNumber(paramDisbursementTransaction.getAccountNumber());
    setBankID(paramDisbursementTransaction.getBankID());
    setRoutingNumber(paramDisbursementTransaction.getRoutingNumber());
    setTransactionIndex(paramDisbursementTransaction.getTransactionIndex());
    setTransID(paramDisbursementTransaction.getTransID());
    if (paramDisbursementTransaction.getProcessingDate() != null) {
      setProcessingDate((DateTime)paramDisbursementTransaction.getProcessingDate().clone());
    } else {
      setProcessingDate(null);
    }
    if (paramDisbursementTransaction.getCheckDate() != null) {
      setCheckDate((DateTime)paramDisbursementTransaction.getCheckDate().clone());
    } else {
      setCheckDate(null);
    }
    setPayee(paramDisbursementTransaction.getPayee());
    if (paramDisbursementTransaction.getCheckAmount() != null) {
      setCheckAmount((Currency)paramDisbursementTransaction.getCheckAmount().clone());
    }
    setCheckNumber(paramDisbursementTransaction.getCheckNumber());
    setCheckReferenceNumber(paramDisbursementTransaction.getCheckReferenceNumber());
    setMemo(paramDisbursementTransaction.getMemo());
    setIssuedBy(paramDisbursementTransaction.getIssuedBy());
    setApprovedBy(paramDisbursementTransaction.getApprovedBy());
    if (paramDisbursementTransaction.getImmediateFundsNeeded() != null) {
      setImmediateFundsNeeded((Currency)paramDisbursementTransaction.getImmediateFundsNeeded().clone());
    }
    if (paramDisbursementTransaction.getOneDayFundsNeeded() != null) {
      setOneDayFundsNeeded((Currency)paramDisbursementTransaction.getOneDayFundsNeeded().clone());
    }
    if (paramDisbursementTransaction.getTwoDayFundsNeeded() != null) {
      setTwoDayFundsNeeded((Currency)paramDisbursementTransaction.getTwoDayFundsNeeded().clone());
    }
    if (paramDisbursementTransaction.getValueDateTime() != null) {
      setValueDateTime((DateTime)paramDisbursementTransaction.getValueDateTime().clone());
    } else {
      setValueDateTime(null);
    }
    setBankReferenceNumber(paramDisbursementTransaction.getBankReferenceNumber());
    setCustomerReferenceNumber(paramDisbursementTransaction.getCustomerReferenceNumber());
    setPresentment(paramDisbursementTransaction.getPresentment());
    super.set(paramDisbursementTransaction);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("ACCOUNTID")) {
      setAccountID(paramString2);
    }
    if (paramString1.equals("ACCOUNTNUMBER"))
    {
      setAccountNumber(paramString2);
    }
    else if (paramString1.equals("BANKID"))
    {
      setBankID(paramString2);
    }
    else if (paramString1.equals("ROUTINGNUMBER"))
    {
      setRoutingNumber(paramString2);
    }
    else if (paramString1.equals("TRANSACTIONINDEX"))
    {
      try
      {
        this.aYO = Long.parseLong(paramString2);
      }
      catch (Exception localException1) {}
    }
    else if (paramString1.equals("TRANSID"))
    {
      try
      {
        this.aYI = Integer.parseInt(paramString2);
      }
      catch (Exception localException2) {}
    }
    else if (paramString1.equals("PROCESSINGDATE"))
    {
      if (this.aYw == null)
      {
        setProcessingDate(new DateTime(this.locale));
        this.aYw.setFormat(this.datetype);
      }
      this.aYw.fromXMLFormat(paramString2);
    }
    else if (paramString1.equals("CHECKDATE"))
    {
      if (this.aYv == null)
      {
        setCheckDate(new DateTime(this.locale));
        this.aYv.setFormat(this.datetype);
      }
      this.aYv.fromXMLFormat(paramString2);
    }
    else if (paramString1.equals("PAYEE"))
    {
      setPayee(paramString2);
    }
    else if (paramString1.equals("CHECKAMOUNT"))
    {
      if (this.aYB == null) {
        this.aYB = new Currency(paramString2, this.locale);
      } else {
        this.aYB.fromString(paramString2);
      }
    }
    else if (paramString1.equals("CHECKNUMBER"))
    {
      setCheckNumber(paramString2);
    }
    else if (paramString1.equals("CHECKREFERENCENUMBER"))
    {
      setCheckReferenceNumber(paramString2);
    }
    else if (paramString1.equals("MEMO"))
    {
      setMemo(paramString2);
    }
    else if (paramString1.equals("ISSUEDBY"))
    {
      setIssuedBy(paramString2);
    }
    else if (paramString1.equals("APPROVEDBY"))
    {
      setApprovedBy(paramString2);
    }
    else if (paramString1.equals("IMMEDIATEFUNDSNEEDED"))
    {
      if (this.aYD == null) {
        this.aYD = new Currency(paramString2, this.locale);
      } else {
        this.aYD.fromString(paramString2);
      }
    }
    else if (paramString1.equals("ONEDAYFUNDSNEEDED"))
    {
      if (this.aYt == null) {
        this.aYt = new Currency(paramString2, this.locale);
      } else {
        this.aYt.fromString(paramString2);
      }
    }
    else if (paramString1.equals("TWODAYFUNDSNEEDED"))
    {
      if (this.aYE == null) {
        this.aYE = new Currency(paramString2, this.locale);
      } else {
        this.aYE.fromString(paramString2);
      }
    }
    else if (paramString1.equals("VALUEDATETIME"))
    {
      if (this.aYL == null)
      {
        this.aYL = new DateTime(this.locale);
        this.aYL.setFormat(this.datetype);
      }
      this.aYL.fromXMLFormat(paramString2);
    }
    else if (paramString1.equals("BANKREFERENCENUMBER"))
    {
      setBankReferenceNumber(paramString2);
    }
    else if (paramString1.equals("CUSTOMERREFERENCENUMBER"))
    {
      setCustomerReferenceNumber(paramString2);
    }
    else if (paramString1.equals("PRESENTMENT"))
    {
      setPresentment(paramString2);
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
    XMLHandler.appendBeginTag(localStringBuffer, "DISBURSEMENTTRANSACTION");
    XMLHandler.appendTag(localStringBuffer, "ACCOUNTID", getAccountID());
    XMLHandler.appendTag(localStringBuffer, "ACCOUNTNUMBER", getAccountNumber());
    XMLHandler.appendTag(localStringBuffer, "BANKID", getBankID());
    XMLHandler.appendTag(localStringBuffer, "ROUTINGNUMBER", getRoutingNumber());
    XMLHandler.appendTag(localStringBuffer, "TRANSACTIONINDEX", String.valueOf(getTransactionIndex()));
    XMLHandler.appendTag(localStringBuffer, "TRANSID", getTransID());
    if (getProcessingDate() != null) {
      XMLHandler.appendTag(localStringBuffer, "PROCESSINGDATE", getProcessingDate().toXMLFormat());
    }
    if (getCheckDate() != null) {
      XMLHandler.appendTag(localStringBuffer, "CHECKDATE", getCheckDate().toXMLFormat());
    }
    XMLHandler.appendTag(localStringBuffer, "PAYEE", getPayee());
    if (this.aYB != null) {
      XMLHandler.appendTag(localStringBuffer, "CHECKAMOUNT", this.aYB.doubleValue());
    }
    XMLHandler.appendTag(localStringBuffer, "CHECKNUMBER", this.aYz);
    XMLHandler.appendTag(localStringBuffer, "CHECKREFERENCENUMBER", this.aYK);
    XMLHandler.appendTag(localStringBuffer, "MEMO", this.aYy);
    XMLHandler.appendTag(localStringBuffer, "ISSUEDBY", this.aYJ);
    XMLHandler.appendTag(localStringBuffer, "APPROVEDBY", this.aYM);
    XMLHandler.appendTag(localStringBuffer, "PRESENTMENT", this.aYG);
    if (this.aYD != null) {
      XMLHandler.appendTag(localStringBuffer, "IMMEDIATEFUNDSNEEDED", this.aYD.doubleValue());
    }
    if (this.aYt != null) {
      XMLHandler.appendTag(localStringBuffer, "ONEDAYFUNDSNEEDED", this.aYt.doubleValue());
    }
    if (this.aYE != null) {
      XMLHandler.appendTag(localStringBuffer, "TWODAYFUNDSNEEDED", this.aYE.doubleValue());
    }
    if (getValueDateTime() != null) {
      XMLHandler.appendTag(localStringBuffer, "VALUEDATETIME", getValueDateTime().toXMLFormat());
    }
    XMLHandler.appendTag(localStringBuffer, "BANKREFERENCENUMBER", this.aYA);
    XMLHandler.appendTag(localStringBuffer, "CUSTOMERREFERENCENUMBER", this.aYC);
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "DISBURSEMENTTRANSACTION");
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.disbursement.DisbursementTransaction
 * JD-Core Version:    0.7.0.1
 */