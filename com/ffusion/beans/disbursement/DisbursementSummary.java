package com.ffusion.beans.disbursement;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.util.XMLHandler;
import java.text.Collator;
import java.util.Locale;

public class DisbursementSummary
  extends ExtendABean
  implements Comparable
{
  public static final String DISBURSEMENTSUMMARY = "DISBURSEMENTSUMMARY";
  public static final String ACCOUNT = "ACCOUNT";
  public static final String ACCOUNTNUMBER = "ACCOUNTNUMBER";
  public static final String SUMMARYDATE = "SUMMARYDATE";
  public static final String VALUEDATETIME = "VALUEDATETIME";
  public static final String CURRENTBALANCE = "CURRENTBALANCE";
  public static final String NUMITEMSPENDING = "NUMITEMSPENDING";
  public static final String TOTALDEBITS = "TOTALDEBITS";
  public static final String NUMCREDITS = "NUMCREDITS";
  public static final String TOTALCREDITS = "TOTALCREDITS";
  public static final String NUMDTCCREDITS = "NUMDTCCREDITS";
  public static final String TOTALDTCCREDITS = "TOTALDTCCREDITS";
  public static final String NUMIMMEDIATEFUNDSNEEDED = "NUMIMMEDIATEFUNDSNEEDED";
  public static final String IMMEDIATEFUNDSNEEDED = "IMMEDIATEFUNDSNEEDED";
  public static final String ONEDAYFUNDSNEEDED = "ONEDAYFUNDSNEEDED";
  public static final String TWODAYFUNDSNEEDED = "TWODAYFUNDSNEEDED";
  public static final String NUMCHECKSPAIDEARLY = "NUMCHECKSPAIDEARLY";
  public static final String TOTALCHECKSPAIDEARLYAMOUNT = "TOTALCHECKSPAIDEARLYAMOUNT";
  public static final String NUMCHECKSPAIDLATE = "NUMCHECKSPAIDLATE";
  public static final String TOTALCHECKSPAIDLATEAMOUNT = "TOTALCHECKSPAIDLATEAMOUNT";
  public static final String NUMCHECKSPAIDLAST = "NUMCHECKSPAIDLAST";
  public static final String TOTALCHECKSPAIDLASTAMOUNT = "TOTALCHECKSPAIDLASTAMOUNT";
  public static final String NUMFEDPRESENTMENTESTIMATE = "NUMFEDPRESENTMENTESTIMATE";
  public static final String FEDPRESENTMENTESTIMATE = "FEDPRESENTMENTESTIMATE";
  public static final String NUMLATEDEBITS = "NUMLATEDEBITS";
  public static final String LATEDEBITS = "LATEDEBITS";
  public static final String PRESENTMENT = "PRESENTMENT";
  public static final String DEBITSCURRENCY = "DEBITSCURRENCY";
  private DisbursementAccount aYV;
  private DateTime aYW;
  private DateTime aZb;
  private Currency aY7;
  private int aY2 = -1;
  private Currency aYZ;
  private Currency aYP;
  private int aYY = -1;
  private Currency aZc;
  private int aYS = -1;
  private Currency aY6;
  private int aY5 = -1;
  private Currency aYR;
  private Currency aY8;
  private Currency aY4;
  private int aY0 = -1;
  private Currency aYT;
  private int aZa = -1;
  private Currency aYQ;
  private int aZd = -1;
  private Currency aYU;
  private int aYX = -1;
  private Currency aY1;
  private int aY3 = -1;
  private String aY9;
  
  public DisbursementSummary() {}
  
  public DisbursementSummary(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.aYV != null) {
      this.aYV.setLocale(paramLocale);
    }
    if (this.aYW != null) {
      this.aYW.setLocale(paramLocale);
    }
    if (this.aZb != null) {
      this.aZb.setLocale(paramLocale);
    }
    if (this.aY7 != null) {
      this.aY7.setLocale(paramLocale);
    }
    if (this.aYZ != null) {
      this.aYZ.setLocale(paramLocale);
    }
    if (this.aYP != null) {
      this.aYP.setLocale(paramLocale);
    }
    if (this.aZc != null) {
      this.aZc.setLocale(paramLocale);
    }
    if (this.aY6 != null) {
      this.aY6.setLocale(paramLocale);
    }
    if (this.aYR != null) {
      this.aYR.setLocale(paramLocale);
    }
    if (this.aY8 != null) {
      this.aY8.setLocale(paramLocale);
    }
    if (this.aY4 != null) {
      this.aY4.setLocale(paramLocale);
    }
    if (this.aYT != null) {
      this.aYT.setLocale(paramLocale);
    }
    if (this.aYQ != null) {
      this.aYQ.setLocale(paramLocale);
    }
    if (this.aYU != null) {
      this.aYU.setLocale(paramLocale);
    }
    if (this.aY1 != null) {
      this.aY1.setLocale(paramLocale);
    }
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.aYW != null) {
      this.aYW.setFormat(paramString);
    }
    if (this.aZb != null) {
      this.aZb.setFormat(paramString);
    }
  }
  
  public DisbursementAccount getAccount()
  {
    return this.aYV;
  }
  
  public int getNumItemsPending()
  {
    return this.aY2;
  }
  
  public DateTime getSummaryDate()
  {
    return this.aYW;
  }
  
  public Currency getTotalDebits()
  {
    return this.aYZ;
  }
  
  public int getNumCredits()
  {
    return this.aYY;
  }
  
  public Currency getTotalCredits()
  {
    return this.aYP;
  }
  
  public Currency getImmediateFundsNeeded()
  {
    return this.aY6;
  }
  
  public int getNumImmediateFundsNeeded()
  {
    return this.aY5;
  }
  
  public Currency getOneDayFundsNeeded()
  {
    return this.aYR;
  }
  
  public Currency getTwoDayFundsNeeded()
  {
    return this.aY8;
  }
  
  public int getNumChecksPaidEarly()
  {
    return this.aY0;
  }
  
  public Currency getTotalChecksPaidEarlyAmount()
  {
    return this.aY4;
  }
  
  public int getNumChecksPaidLate()
  {
    return this.aZa;
  }
  
  public Currency getTotalChecksPaidLateAmount()
  {
    return this.aYT;
  }
  
  public Currency getTotalChecksPaidLastAmount()
  {
    return this.aYQ;
  }
  
  public int getNumChecksPaidLast()
  {
    return this.aZd;
  }
  
  public int getNumFedPresentmentEstimate()
  {
    return this.aYX;
  }
  
  public Currency getFedPresentmentEstimate()
  {
    return this.aYU;
  }
  
  public int getNumLateDebits()
  {
    return this.aY3;
  }
  
  public Currency getLateDebits()
  {
    return this.aY1;
  }
  
  public int getNumDTCCredits()
  {
    return this.aYS;
  }
  
  public Currency getTotalDTCCredits()
  {
    return this.aZc;
  }
  
  public Currency getCurrentBalance()
  {
    return this.aY7;
  }
  
  public DateTime getValueDateTime()
  {
    return this.aZb;
  }
  
  public String getPresentment()
  {
    return this.aY9;
  }
  
  public void setValueDateTime(DateTime paramDateTime)
  {
    this.aZb = paramDateTime;
  }
  
  public void setNumDTCCredits(int paramInt)
  {
    this.aYS = paramInt;
  }
  
  public void setTotalDTCCredits(Currency paramCurrency)
  {
    this.aZc = paramCurrency;
  }
  
  public void setCurrentBalance(Currency paramCurrency)
  {
    this.aY7 = paramCurrency;
  }
  
  public void setAccount(DisbursementAccount paramDisbursementAccount)
  {
    this.aYV = paramDisbursementAccount;
  }
  
  public void setSummaryDate(DateTime paramDateTime)
  {
    this.aYW = paramDateTime;
  }
  
  public void setNumItemsPending(int paramInt)
  {
    this.aY2 = paramInt;
  }
  
  public void setTotalDebits(Currency paramCurrency)
  {
    this.aYZ = paramCurrency;
  }
  
  public void setNumCredits(int paramInt)
  {
    this.aYY = paramInt;
  }
  
  public void setTotalCredits(Currency paramCurrency)
  {
    this.aYP = paramCurrency;
  }
  
  public void setNumImmediateFundsNeeded(int paramInt)
  {
    this.aY5 = paramInt;
  }
  
  public void setImmediateFundsNeeded(Currency paramCurrency)
  {
    this.aY6 = paramCurrency;
  }
  
  public void setOneDayFundsNeeded(Currency paramCurrency)
  {
    this.aYR = paramCurrency;
  }
  
  public void setTwoDayFundsNeeded(Currency paramCurrency)
  {
    this.aY8 = paramCurrency;
  }
  
  public void setNumChecksPaidEarly(int paramInt)
  {
    this.aY0 = paramInt;
  }
  
  public void setTotalChecksPaidEarlyAmount(Currency paramCurrency)
  {
    this.aY4 = paramCurrency;
  }
  
  public void setNumChecksPaidLate(int paramInt)
  {
    this.aZa = paramInt;
  }
  
  public void setTotalChecksPaidLateAmount(Currency paramCurrency)
  {
    this.aYT = paramCurrency;
  }
  
  public void setNumChecksPaidLast(int paramInt)
  {
    this.aZd = paramInt;
  }
  
  public void setTotalChecksPaidLastAmount(Currency paramCurrency)
  {
    this.aYQ = paramCurrency;
  }
  
  public void setNumFedPresentmentEstimate(int paramInt)
  {
    this.aYX = paramInt;
  }
  
  public void setFedPresentmentEstimate(Currency paramCurrency)
  {
    this.aYU = paramCurrency;
  }
  
  public void setNumLateDebits(int paramInt)
  {
    this.aY3 = paramInt;
  }
  
  public void setLateDebits(Currency paramCurrency)
  {
    this.aY1 = paramCurrency;
  }
  
  public void setPresentment(String paramString)
  {
    this.aY9 = paramString;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "SUMMARYDATE");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    DisbursementSummary localDisbursementSummary = (DisbursementSummary)paramObject;
    int i = 1;
    Collator localCollator = doGetCollator();
    if ((paramString.equals("SUMMARYDATE")) && (getSummaryDate() != null) && (localDisbursementSummary.getSummaryDate() != null)) {
      i = getSummaryDate().equals(localDisbursementSummary.getSummaryDate()) ? 0 : getSummaryDate().before(localDisbursementSummary.getSummaryDate()) ? -1 : 1;
    } else if ((paramString.equals("VALUEDATETIME")) && (getValueDateTime() != null) && (localDisbursementSummary.getValueDateTime() != null)) {
      i = getValueDateTime().equals(localDisbursementSummary.getValueDateTime()) ? 0 : getValueDateTime().before(localDisbursementSummary.getValueDateTime()) ? -1 : 1;
    } else if ((paramString.equals("CURRENTBALANCE")) && (getCurrentBalance() != null) && (localDisbursementSummary.getCurrentBalance() != null)) {
      i = getCurrentBalance().compareTo(localDisbursementSummary.getCurrentBalance());
    } else if (paramString.equals("ACCOUNTNUMBER")) {
      i = this.aYV.compare(localDisbursementSummary.getAccount(), "ACCOUNTNUMBER");
    } else if (paramString.equals("ACCOUNTID")) {
      i = this.aYV.compare(localDisbursementSummary.getAccount(), "ACCOUNTID");
    } else if (paramString.equals("NUMITEMSPENDING")) {
      i = this.aY2 - localDisbursementSummary.getNumItemsPending();
    } else if ((paramString.equals("TOTALDEBITS")) && (getTotalDebits() != null) && (localDisbursementSummary.getTotalDebits() != null)) {
      i = getTotalDebits().compareTo(localDisbursementSummary.getTotalDebits());
    } else if ((paramString.equals("TOTALCREDITS")) && (getTotalCredits() != null) && (localDisbursementSummary.getTotalCredits() != null)) {
      i = getTotalCredits().compareTo(localDisbursementSummary.getTotalCredits());
    } else if (paramString.equals("NUMCREDITS")) {
      i = getNumCredits() - localDisbursementSummary.getNumCredits();
    } else if ((paramString.equals("TOTALDTCCREDITS")) && (getTotalDTCCredits() != null) && (localDisbursementSummary.getTotalDTCCredits() != null)) {
      i = getTotalDTCCredits().compareTo(localDisbursementSummary.getTotalDTCCredits());
    } else if (paramString.equals("NUMDTCCREDITS")) {
      i = getNumDTCCredits() - localDisbursementSummary.getNumDTCCredits();
    } else if ((paramString.equals("IMMEDIATEFUNDSNEEDED")) && (getImmediateFundsNeeded() != null) && (localDisbursementSummary.getImmediateFundsNeeded() != null)) {
      i = getImmediateFundsNeeded().compareTo(localDisbursementSummary.getImmediateFundsNeeded());
    } else if (paramString.equals("NUMIMMEDIATEFUNDSNEEDED")) {
      i = getNumImmediateFundsNeeded() - localDisbursementSummary.getNumImmediateFundsNeeded();
    } else if ((paramString.equals("ONEDAYFUNDSNEEDED")) && (getOneDayFundsNeeded() != null) && (localDisbursementSummary.getOneDayFundsNeeded() != null)) {
      i = getOneDayFundsNeeded().compareTo(localDisbursementSummary.getOneDayFundsNeeded());
    } else if ((paramString.equals("TWODAYFUNDSNEEDED")) && (getTwoDayFundsNeeded() != null) && (localDisbursementSummary.getTwoDayFundsNeeded() != null)) {
      i = getTwoDayFundsNeeded().compareTo(localDisbursementSummary.getTwoDayFundsNeeded());
    } else if ((paramString.equals("TOTALCHECKSPAIDEARLYAMOUNT")) && (getTotalChecksPaidEarlyAmount() != null) && (localDisbursementSummary.getTotalChecksPaidEarlyAmount() != null)) {
      i = getTotalChecksPaidEarlyAmount().compareTo(localDisbursementSummary.getTotalChecksPaidEarlyAmount());
    } else if (paramString.equals("NUMCHECKSPAIDEARLY")) {
      i = getNumChecksPaidEarly() - localDisbursementSummary.getNumChecksPaidEarly();
    } else if ((paramString.equals("TOTALCHECKSPAIDLATEAMOUNT")) && (getTotalChecksPaidLateAmount() != null) && (localDisbursementSummary.getTotalChecksPaidLateAmount() != null)) {
      i = getTotalChecksPaidLateAmount().compareTo(localDisbursementSummary.getTotalChecksPaidLateAmount());
    } else if (paramString.equals("NUMCHECKSPAIDLATE")) {
      i = getNumChecksPaidLate() - localDisbursementSummary.getNumChecksPaidLate();
    } else if ((paramString.equals("TOTALCHECKSPAIDLASTAMOUNT")) && (getTotalChecksPaidLastAmount() != null) && (localDisbursementSummary.getTotalChecksPaidLastAmount() != null)) {
      i = getTotalChecksPaidLastAmount().compareTo(localDisbursementSummary.getTotalChecksPaidLastAmount());
    } else if (paramString.equals("NUMCHECKSPAIDLAST")) {
      i = getNumChecksPaidLast() - localDisbursementSummary.getNumChecksPaidLast();
    } else if ((paramString.equals("FEDPRESENTMENTESTIMATE")) && (getFedPresentmentEstimate() != null) && (localDisbursementSummary.getFedPresentmentEstimate() != null)) {
      i = getFedPresentmentEstimate().compareTo(localDisbursementSummary.getFedPresentmentEstimate());
    } else if (paramString.equals("NUMFEDPRESENTMENTESTIMATE")) {
      i = getNumFedPresentmentEstimate() - localDisbursementSummary.getNumFedPresentmentEstimate();
    } else if ((paramString.equals("LATEDEBITS")) && (getLateDebits() != null) && (localDisbursementSummary.getLateDebits() != null)) {
      i = getLateDebits().compareTo(localDisbursementSummary.getLateDebits());
    } else if (paramString.equals("NUMLATEDEBITS")) {
      i = getNumLateDebits() - localDisbursementSummary.getNumLateDebits();
    } else if ((paramString.equals("PRESENTMENT")) && (getPresentment() != null) && (localDisbursementSummary.getPresentment() != null)) {
      i = localCollator.compare(getPresentment(), localDisbursementSummary.getPresentment());
    } else if (paramString.equals("DEBITSCURRENCY")) {
      i = localCollator.compare(getTotalDebits().getCurrencyCode(), localDisbursementSummary.getTotalDebits().getCurrencyCode());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1.equals("SUMMARYDATE")) && (getSummaryDate() != null)) {
      return getSummaryDate().isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((paramString1.equals("VALUEDATETIME")) && (getValueDateTime() != null)) {
      return getValueDateTime().isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((paramString1.equals("ACCOUNTNUMBER")) && (this.aYV.getAccountNumber() != null)) {
      return isFilterable(this.aYV.getAccountNumber(), paramString2, paramString3);
    }
    if ((paramString1.equals("CURRENTBALANCE")) && (getCurrentBalance() != null)) {
      return getCurrentBalance().isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((paramString1.equals("TOTALDEBITS")) && (getTotalDebits() != null)) {
      return getTotalDebits().isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((paramString1.equals("TOTALCREDITS")) && (getTotalCredits() != null)) {
      return getTotalCredits().isFilterable("VALUE" + paramString2 + paramString3);
    }
    if (paramString1.equals("NUMCREDITS")) {
      return isFilterable(Integer.toString(getNumCredits()), paramString2, paramString3);
    }
    if ((paramString1.equals("TOTALDTCCREDITS")) && (getTotalDTCCredits() != null)) {
      return getTotalDTCCredits().isFilterable("VALUE" + paramString2 + paramString3);
    }
    if (paramString1.equals("NUMDTCCREDITS")) {
      return isFilterable(Integer.toString(getNumDTCCredits()), paramString2, paramString3);
    }
    if ((paramString1.equals("IMMEDIATEFUNDSNEEDED")) && (getImmediateFundsNeeded() != null)) {
      return getImmediateFundsNeeded().isFilterable("VALUE" + paramString2 + paramString3);
    }
    if (paramString1.equals("NUMIMMEDIATEFUNDSNEEDED")) {
      return isFilterable(Integer.toString(getNumImmediateFundsNeeded()), paramString2, paramString3);
    }
    if ((paramString1.equals("ONEDAYFUNDSNEEDED")) && (getOneDayFundsNeeded() != null)) {
      return getOneDayFundsNeeded().isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((paramString1.equals("TWODAYFUNDSNEEDED")) && (getTwoDayFundsNeeded() != null)) {
      return getTwoDayFundsNeeded().isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((paramString1.equals("TOTALCHECKSPAIDEARLYAMOUNT")) && (getTotalChecksPaidEarlyAmount() != null)) {
      return getTotalChecksPaidEarlyAmount().isFilterable("VALUE" + paramString2 + paramString3);
    }
    if (paramString1.equals("NUMCHECKSPAIDEARLY")) {
      return isFilterable(Integer.toString(getNumChecksPaidEarly()), paramString2, paramString3);
    }
    if ((paramString1.equals("TOTALCHECKSPAIDLATEAMOUNT")) && (getTotalChecksPaidLateAmount() != null)) {
      return getTotalChecksPaidLateAmount().isFilterable("VALUE" + paramString2 + paramString3);
    }
    if (paramString1.equals("NUMCHECKSPAIDLATE")) {
      return isFilterable(Integer.toString(getNumChecksPaidLate()), paramString2, paramString3);
    }
    if ((paramString1.equals("TOTALCHECKSPAIDLASTAMOUNT")) && (getTotalChecksPaidLastAmount() != null)) {
      return getTotalChecksPaidLastAmount().isFilterable("VALUE" + paramString2 + paramString3);
    }
    if (paramString1.equals("NUMCHECKSPAIDLAST")) {
      return isFilterable(Integer.toString(getNumChecksPaidLast()), paramString2, paramString3);
    }
    if ((paramString1.equals("FEDPRESENTMENTESTIMATE")) && (getFedPresentmentEstimate() != null)) {
      return getFedPresentmentEstimate().isFilterable("VALUE" + paramString2 + paramString3);
    }
    if (paramString1.equals("NUMFEDPRESENTMENTESTIMATE")) {
      return isFilterable(Integer.toString(getNumFedPresentmentEstimate()), paramString2, paramString3);
    }
    if ((paramString1.equals("LATEDEBITS")) && (getLateDebits() != null)) {
      return getLateDebits().isFilterable("VALUE" + paramString2 + paramString3);
    }
    if (paramString1.equals("NUMLATEDEBITS")) {
      return isFilterable(Integer.toString(getNumLateDebits()), paramString2, paramString3);
    }
    if ((paramString1.equals("PRESENTMENT")) && (getPresentment() != null)) {
      return isFilterable(getPresentment(), paramString2, paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public void set(DisbursementSummary paramDisbursementSummary)
  {
    if ((this == paramDisbursementSummary) || (paramDisbursementSummary == null)) {
      return;
    }
    if (paramDisbursementSummary.getAccount() != null) {
      setAccount((DisbursementAccount)paramDisbursementSummary.getAccount().clone());
    }
    if (paramDisbursementSummary.getSummaryDate() != null) {
      setSummaryDate((DateTime)paramDisbursementSummary.getSummaryDate().clone());
    } else {
      setSummaryDate(null);
    }
    if (paramDisbursementSummary.getValueDateTime() != null) {
      setValueDateTime((DateTime)paramDisbursementSummary.getValueDateTime().clone());
    } else {
      setValueDateTime(null);
    }
    setNumItemsPending(paramDisbursementSummary.getNumItemsPending());
    if (paramDisbursementSummary.getCurrentBalance() != null) {
      setCurrentBalance((Currency)paramDisbursementSummary.getCurrentBalance().clone());
    } else {
      setCurrentBalance(null);
    }
    if (paramDisbursementSummary.getTotalDebits() != null) {
      setTotalDebits((Currency)paramDisbursementSummary.getTotalDebits().clone());
    } else {
      setTotalDebits(null);
    }
    if (paramDisbursementSummary.getTotalCredits() != null) {
      setTotalCredits((Currency)paramDisbursementSummary.getTotalCredits().clone());
    } else {
      setTotalCredits(null);
    }
    setNumCredits(paramDisbursementSummary.getNumCredits());
    if (paramDisbursementSummary.getTotalDTCCredits() != null) {
      setTotalDTCCredits((Currency)paramDisbursementSummary.getTotalDTCCredits().clone());
    } else {
      setTotalDTCCredits(null);
    }
    setNumDTCCredits(paramDisbursementSummary.getNumDTCCredits());
    if (paramDisbursementSummary.getImmediateFundsNeeded() != null) {
      setImmediateFundsNeeded((Currency)paramDisbursementSummary.getImmediateFundsNeeded().clone());
    } else {
      setImmediateFundsNeeded(null);
    }
    setNumImmediateFundsNeeded(paramDisbursementSummary.getNumImmediateFundsNeeded());
    if (paramDisbursementSummary.getOneDayFundsNeeded() != null) {
      setOneDayFundsNeeded((Currency)paramDisbursementSummary.getOneDayFundsNeeded().clone());
    } else {
      setOneDayFundsNeeded(null);
    }
    if (paramDisbursementSummary.getTwoDayFundsNeeded() != null) {
      setTwoDayFundsNeeded((Currency)paramDisbursementSummary.getTwoDayFundsNeeded().clone());
    } else {
      setTwoDayFundsNeeded(null);
    }
    if (paramDisbursementSummary.getTotalChecksPaidEarlyAmount() != null) {
      setTotalChecksPaidEarlyAmount((Currency)paramDisbursementSummary.getTotalChecksPaidEarlyAmount().clone());
    } else {
      setTotalChecksPaidEarlyAmount(null);
    }
    setNumChecksPaidEarly(paramDisbursementSummary.getNumChecksPaidEarly());
    if (paramDisbursementSummary.getTotalChecksPaidLateAmount() != null) {
      setTotalChecksPaidLateAmount((Currency)paramDisbursementSummary.getTotalChecksPaidLateAmount().clone());
    } else {
      setTotalChecksPaidLateAmount(null);
    }
    setNumChecksPaidLate(paramDisbursementSummary.getNumChecksPaidLate());
    if (paramDisbursementSummary.getTotalChecksPaidLastAmount() != null) {
      setTotalChecksPaidLastAmount((Currency)paramDisbursementSummary.getTotalChecksPaidLastAmount().clone());
    } else {
      setTotalChecksPaidLastAmount(null);
    }
    setNumChecksPaidLast(paramDisbursementSummary.getNumChecksPaidLast());
    if (paramDisbursementSummary.getFedPresentmentEstimate() != null) {
      setFedPresentmentEstimate((Currency)paramDisbursementSummary.getFedPresentmentEstimate().clone());
    } else {
      setFedPresentmentEstimate(null);
    }
    setNumFedPresentmentEstimate(paramDisbursementSummary.getNumFedPresentmentEstimate());
    if (paramDisbursementSummary.getLateDebits() != null) {
      setLateDebits((Currency)paramDisbursementSummary.getLateDebits().clone());
    } else {
      setLateDebits(null);
    }
    setNumLateDebits(paramDisbursementSummary.getNumLateDebits());
    setPresentment(paramDisbursementSummary.getPresentment());
    super.set(paramDisbursementSummary);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if ((paramString1.equals("ACCOUNTNAME")) || (paramString1.equals("ACCOUNTNUMBER")) || (paramString1.equals("BANKID")) || (paramString1.equals("BANKNAME")) || (paramString1.equals("CURRENCYTYPE")) || (paramString1.equals("ROUTINGNUMBER")))
    {
      if (this.aYV == null) {
        this.aYV = new DisbursementAccount();
      }
      this.aYV.set(paramString1, paramString2);
    }
    else if (paramString1.equals("SUMMARYDATE"))
    {
      if (this.aYW == null)
      {
        setSummaryDate(new DateTime(this.locale));
        this.aYW.setFormat(this.datetype);
      }
      this.aYW.fromXMLFormat(paramString2);
    }
    else if (paramString1.equals("VALUEDATETIME"))
    {
      if (this.aZb == null)
      {
        setValueDateTime(new DateTime(this.locale));
        this.aZb.setFormat(this.datetype);
      }
      this.aZb.fromXMLFormat(paramString2);
    }
    else if (paramString1.equals("CURRENTBALANCE"))
    {
      if (this.aY7 == null) {
        this.aY7 = new Currency(paramString2, this.locale);
      } else {
        this.aY7.fromString(paramString2);
      }
    }
    else if (paramString1.equals("NUMITEMSPENDING"))
    {
      try
      {
        this.aY2 = Integer.parseInt(paramString2);
      }
      catch (Exception localException)
      {
        this.aY2 = -1;
      }
    }
    else if (paramString1.equals("TOTALDEBITS"))
    {
      if (this.aYZ == null) {
        this.aYZ = new Currency(paramString2, this.locale);
      } else {
        this.aYZ.fromString(paramString2);
      }
    }
    else if (paramString1.equals("NUMCREDITS"))
    {
      try
      {
        this.aYY = Integer.parseInt(paramString2);
      }
      catch (NumberFormatException localNumberFormatException1)
      {
        this.aYY = -1;
      }
    }
    else if (paramString1.equals("TOTALCREDITS"))
    {
      if (this.aYP == null) {
        this.aYP = new Currency(paramString2, this.locale);
      } else {
        this.aYP.fromString(paramString2);
      }
    }
    else if (paramString1.equals("NUMDTCCREDITS"))
    {
      try
      {
        this.aYS = Integer.parseInt(paramString2);
      }
      catch (NumberFormatException localNumberFormatException2)
      {
        this.aYS = -1;
      }
    }
    else if (paramString1.equals("TOTALDTCCREDITS"))
    {
      if (this.aZc == null) {
        this.aZc = new Currency(paramString2, this.locale);
      } else {
        this.aZc.fromString(paramString2);
      }
    }
    else if (paramString1.equals("NUMIMMEDIATEFUNDSNEEDED"))
    {
      try
      {
        this.aY5 = Integer.parseInt(paramString2);
      }
      catch (NumberFormatException localNumberFormatException3)
      {
        this.aY5 = -1;
      }
    }
    else if (paramString1.equals("IMMEDIATEFUNDSNEEDED"))
    {
      if (this.aY6 == null) {
        this.aY6 = new Currency(paramString2, this.locale);
      } else {
        this.aY6.fromString(paramString2);
      }
    }
    else if (paramString1.equals("ONEDAYFUNDSNEEDED"))
    {
      if (this.aYR == null) {
        this.aYR = new Currency(paramString2, this.locale);
      } else {
        this.aYR.fromString(paramString2);
      }
    }
    else if (paramString1.equals("TWODAYFUNDSNEEDED"))
    {
      if (this.aY8 == null) {
        this.aY8 = new Currency(paramString2, this.locale);
      } else {
        this.aY8.fromString(paramString2);
      }
    }
    else if (paramString1.equals("NUMCHECKSPAIDEARLY"))
    {
      try
      {
        this.aY0 = Integer.parseInt(paramString2);
      }
      catch (NumberFormatException localNumberFormatException4)
      {
        this.aY0 = -1;
      }
    }
    else if (paramString1.equals("TOTALCHECKSPAIDEARLYAMOUNT"))
    {
      if (this.aY4 == null) {
        this.aY4 = new Currency(paramString2, this.locale);
      } else {
        this.aY4.fromString(paramString2);
      }
    }
    else if (paramString1.equals("NUMCHECKSPAIDLATE"))
    {
      try
      {
        this.aZa = Integer.parseInt(paramString2);
      }
      catch (NumberFormatException localNumberFormatException5)
      {
        this.aZa = -1;
      }
    }
    else if (paramString1.equals("TOTALCHECKSPAIDLATEAMOUNT"))
    {
      if (this.aYT == null) {
        this.aYT = new Currency(paramString2, this.locale);
      } else {
        this.aYT.fromString(paramString2);
      }
    }
    else if (paramString1.equals("NUMCHECKSPAIDLAST"))
    {
      try
      {
        this.aZd = Integer.parseInt(paramString2);
      }
      catch (NumberFormatException localNumberFormatException6)
      {
        this.aZd = -1;
      }
    }
    else if (paramString1.equals("TOTALCHECKSPAIDLASTAMOUNT"))
    {
      if (this.aYQ == null) {
        this.aYQ = new Currency(paramString2, this.locale);
      } else {
        this.aYQ.fromString(paramString2);
      }
    }
    else if (paramString1.equals("NUMFEDPRESENTMENTESTIMATE"))
    {
      try
      {
        this.aYX = Integer.parseInt(paramString2);
      }
      catch (NumberFormatException localNumberFormatException7)
      {
        this.aYX = -1;
      }
    }
    else if (paramString1.equals("FEDPRESENTMENTESTIMATE"))
    {
      if (this.aYU == null) {
        this.aYU = new Currency(paramString2, this.locale);
      } else {
        this.aYU.fromString(paramString2);
      }
    }
    else if (paramString1.equals("NUMLATEDEBITS"))
    {
      try
      {
        this.aY3 = Integer.parseInt(paramString2);
      }
      catch (NumberFormatException localNumberFormatException8)
      {
        this.aY3 = -1;
      }
    }
    else if (paramString1.equals("LATEDEBITS"))
    {
      if (this.aY1 == null) {
        this.aY1 = new Currency(paramString2, this.locale);
      } else {
        this.aY1.fromString(paramString2);
      }
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
    XMLHandler.appendBeginTag(localStringBuffer, "DISBURSEMENTSUMMARY");
    if (this.aYV != null) {
      localStringBuffer.append(this.aYV.getXML());
    }
    XMLHandler.appendTag(localStringBuffer, "SUMMARYDATE", getSummaryDate() == null ? "" : getSummaryDate().toXMLFormat());
    XMLHandler.appendTag(localStringBuffer, "VALUEDATETIME", getValueDateTime() == null ? "" : getValueDateTime().toXMLFormat());
    XMLHandler.appendTag(localStringBuffer, "CURRENTBALANCE", getCurrentBalance() == null ? 0.0D : getCurrentBalance().doubleValue());
    XMLHandler.appendTag(localStringBuffer, "NUMITEMSPENDING", getNumItemsPending());
    XMLHandler.appendTag(localStringBuffer, "TOTALDEBITS", getTotalDebits() == null ? 0.0D : getTotalDebits().doubleValue());
    XMLHandler.appendTag(localStringBuffer, "TOTALCREDITS", getTotalCredits() == null ? 0.0D : getTotalCredits().doubleValue());
    XMLHandler.appendTag(localStringBuffer, "NUMCREDITS", getNumCredits());
    XMLHandler.appendTag(localStringBuffer, "TOTALDTCCREDITS", getTotalDTCCredits() == null ? 0.0D : getTotalDTCCredits().doubleValue());
    XMLHandler.appendTag(localStringBuffer, "NUMDTCCREDITS", getNumDTCCredits());
    XMLHandler.appendTag(localStringBuffer, "IMMEDIATEFUNDSNEEDED", getImmediateFundsNeeded() == null ? 0.0D : getImmediateFundsNeeded().doubleValue());
    XMLHandler.appendTag(localStringBuffer, "NUMIMMEDIATEFUNDSNEEDED", getNumImmediateFundsNeeded());
    XMLHandler.appendTag(localStringBuffer, "ONEDAYFUNDSNEEDED", getOneDayFundsNeeded() == null ? 0.0D : getOneDayFundsNeeded().doubleValue());
    XMLHandler.appendTag(localStringBuffer, "TWODAYFUNDSNEEDED", getTwoDayFundsNeeded() == null ? 0.0D : getTwoDayFundsNeeded().doubleValue());
    XMLHandler.appendTag(localStringBuffer, "TOTALCHECKSPAIDEARLYAMOUNT", getTotalChecksPaidEarlyAmount() == null ? 0.0D : getTotalChecksPaidEarlyAmount().doubleValue());
    XMLHandler.appendTag(localStringBuffer, "NUMCHECKSPAIDEARLY", getNumChecksPaidEarly());
    XMLHandler.appendTag(localStringBuffer, "TOTALCHECKSPAIDLATEAMOUNT", getTotalChecksPaidLateAmount() == null ? 0.0D : getTotalChecksPaidLateAmount().doubleValue());
    XMLHandler.appendTag(localStringBuffer, "NUMCHECKSPAIDLATE", getNumChecksPaidLate());
    XMLHandler.appendTag(localStringBuffer, "TOTALCHECKSPAIDLASTAMOUNT", getTotalChecksPaidLastAmount() == null ? 0.0D : getTotalChecksPaidLastAmount().doubleValue());
    XMLHandler.appendTag(localStringBuffer, "NUMCHECKSPAIDLAST", getNumChecksPaidLast());
    XMLHandler.appendTag(localStringBuffer, "FEDPRESENTMENTESTIMATE", getFedPresentmentEstimate() == null ? 0.0D : getFedPresentmentEstimate().doubleValue());
    XMLHandler.appendTag(localStringBuffer, "NUMFEDPRESENTMENTESTIMATE", getNumFedPresentmentEstimate());
    XMLHandler.appendTag(localStringBuffer, "LATEDEBITS", getLateDebits() == null ? 0.0D : getLateDebits().doubleValue());
    XMLHandler.appendTag(localStringBuffer, "NUMLATEDEBITS", getNumLateDebits());
    XMLHandler.appendTag(localStringBuffer, "PRESENTMENT", this.aY9);
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "DISBURSEMENTSUMMARY");
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.disbursement.DisbursementSummary
 * JD-Core Version:    0.7.0.1
 */