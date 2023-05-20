package com.ffusion.beans.istatements;

import com.ffusion.beans.Currency;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.XMLStrings;
import com.ffusion.util.XMLHandler;
import java.util.Locale;

public class InterestBalanceSummary
  extends ExtendABean
  implements XMLStrings
{
  protected Currency reserveReqAmount;
  protected Currency avgLedgerBalanceAmount;
  protected Currency avgFloatAmount;
  protected Currency avgColAmount;
  protected Currency avgReserveReqAmount;
  protected Currency avgBalQualIntAmount;
  protected double avgInterestRate;
  protected Currency avgDailyAccrAmount;
  protected int numDaysQual;
  protected Currency totalIntCreditAmount;
  protected int numDaysOver;
  protected Currency avgOverAmount;
  protected double avgOverRate;
  protected Currency avgDailyOverAmount;
  protected int numDaysCharged;
  protected Currency totalOverChargedAmount;
  
  public InterestBalanceSummary() {}
  
  public InterestBalanceSummary(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.reserveReqAmount != null) {
      this.reserveReqAmount.setLocale(paramLocale);
    }
    if (this.avgLedgerBalanceAmount != null) {
      this.avgLedgerBalanceAmount.setLocale(paramLocale);
    }
    if (this.avgFloatAmount != null) {
      this.avgFloatAmount.setLocale(paramLocale);
    }
    if (this.avgColAmount != null) {
      this.avgColAmount.setLocale(paramLocale);
    }
    if (this.avgReserveReqAmount != null) {
      this.avgReserveReqAmount.setLocale(paramLocale);
    }
    if (this.avgBalQualIntAmount != null) {
      this.avgBalQualIntAmount.setLocale(paramLocale);
    }
    if (this.avgDailyAccrAmount != null) {
      this.avgDailyAccrAmount.setLocale(paramLocale);
    }
    if (this.totalIntCreditAmount != null) {
      this.totalIntCreditAmount.setLocale(paramLocale);
    }
    if (this.avgOverAmount != null) {
      this.avgOverAmount.setLocale(paramLocale);
    }
    if (this.avgDailyOverAmount != null) {
      this.avgDailyOverAmount.setLocale(paramLocale);
    }
    if (this.totalOverChargedAmount != null) {
      this.totalOverChargedAmount.setLocale(paramLocale);
    }
  }
  
  public Currency getReserveReqAmountValue()
  {
    return this.reserveReqAmount;
  }
  
  public String getReserveReqAmount()
  {
    if (this.reserveReqAmount != null) {
      return this.reserveReqAmount.toString();
    }
    return null;
  }
  
  public void setReserveReqAmount(Currency paramCurrency)
  {
    this.reserveReqAmount = paramCurrency;
  }
  
  public void setReserveReqAmount(String paramString)
  {
    if (this.reserveReqAmount == null) {
      this.reserveReqAmount = new Currency(paramString, this.locale);
    } else {
      this.reserveReqAmount.fromString(paramString);
    }
  }
  
  public Currency getAvgLedgerBalanceAmountValue()
  {
    return this.avgLedgerBalanceAmount;
  }
  
  public String getAvgLedgerBalanceAmount()
  {
    if (this.avgLedgerBalanceAmount != null) {
      return this.avgLedgerBalanceAmount.toString();
    }
    return null;
  }
  
  public void setAvgLedgerBalanceAmount(Currency paramCurrency)
  {
    this.avgLedgerBalanceAmount = paramCurrency;
  }
  
  public void setAvgLedgerBalanceAmount(String paramString)
  {
    if (this.avgLedgerBalanceAmount == null) {
      this.avgLedgerBalanceAmount = new Currency(paramString, this.locale);
    } else {
      this.avgLedgerBalanceAmount.fromString(paramString);
    }
  }
  
  public Currency getAvgFloatAmountValue()
  {
    return this.avgFloatAmount;
  }
  
  public String getAvgFloatAmount()
  {
    if (this.avgFloatAmount != null) {
      return this.avgFloatAmount.toString();
    }
    return null;
  }
  
  public void setAvgFloatAmount(Currency paramCurrency)
  {
    this.avgFloatAmount = paramCurrency;
  }
  
  public void setAvgFloatAmount(String paramString)
  {
    if (this.avgFloatAmount == null) {
      this.avgFloatAmount = new Currency(paramString, this.locale);
    } else {
      this.avgFloatAmount.fromString(paramString);
    }
  }
  
  public Currency getAvgColAmountValue()
  {
    return this.avgColAmount;
  }
  
  public String getAvgColAmount()
  {
    if (this.avgColAmount != null) {
      return this.avgColAmount.toString();
    }
    return null;
  }
  
  public void setAvgColAmount(Currency paramCurrency)
  {
    this.avgColAmount = paramCurrency;
  }
  
  public void setAvgColAmount(String paramString)
  {
    if (this.avgColAmount == null) {
      this.avgColAmount = new Currency(paramString, this.locale);
    } else {
      this.avgColAmount.fromString(paramString);
    }
  }
  
  public Currency getAvgReserveReqAmountValue()
  {
    return this.avgReserveReqAmount;
  }
  
  public String getAvgReserveReqAmount()
  {
    if (this.avgReserveReqAmount != null) {
      return this.avgReserveReqAmount.toString();
    }
    return null;
  }
  
  public void setAvgReserveReqAmount(Currency paramCurrency)
  {
    this.avgReserveReqAmount = paramCurrency;
  }
  
  public void setAvgReserveReqAmount(String paramString)
  {
    if (this.avgReserveReqAmount == null) {
      this.avgReserveReqAmount = new Currency(paramString, this.locale);
    } else {
      this.avgReserveReqAmount.fromString(paramString);
    }
  }
  
  public Currency getAvgBalQualIntAmountValue()
  {
    return this.avgBalQualIntAmount;
  }
  
  public String getAvgBalQualIntAmount()
  {
    if (this.avgBalQualIntAmount != null) {
      return this.avgBalQualIntAmount.toString();
    }
    return null;
  }
  
  public void setAvgBalQualIntAmount(Currency paramCurrency)
  {
    this.avgBalQualIntAmount = paramCurrency;
  }
  
  public void setAvgBalQualIntAmount(String paramString)
  {
    if (this.avgBalQualIntAmount == null) {
      this.avgBalQualIntAmount = new Currency(paramString, this.locale);
    } else {
      this.avgBalQualIntAmount.fromString(paramString);
    }
  }
  
  public double getAvgIntRateValue()
  {
    return this.avgInterestRate;
  }
  
  public String getAvgIntRate()
  {
    return String.valueOf(this.avgInterestRate);
  }
  
  public void setAvgIntRate(double paramDouble)
  {
    this.avgInterestRate = paramDouble;
  }
  
  public Currency getAvgDailyAccrAmountValue()
  {
    return this.avgDailyAccrAmount;
  }
  
  public String getAvgDailyAccrAmount()
  {
    if (this.avgDailyAccrAmount != null) {
      return this.avgDailyAccrAmount.toString();
    }
    return null;
  }
  
  public void setAvgDailyAccrAmount(Currency paramCurrency)
  {
    this.avgDailyAccrAmount = paramCurrency;
  }
  
  public void setAvgDailyAccrAmount(String paramString)
  {
    if (this.avgDailyAccrAmount == null) {
      this.avgDailyAccrAmount = new Currency(paramString, this.locale);
    } else {
      this.avgDailyAccrAmount.fromString(paramString);
    }
  }
  
  public int getNumDaysQualValue()
  {
    return this.numDaysQual;
  }
  
  public String getNumDaysQual()
  {
    return String.valueOf(this.numDaysQual);
  }
  
  public void setNumDaysQual(int paramInt)
  {
    this.numDaysQual = paramInt;
  }
  
  public Currency getTotalIntCreditAmountValue()
  {
    return this.totalIntCreditAmount;
  }
  
  public String getTotalIntCreditAmount()
  {
    if (this.totalIntCreditAmount != null) {
      return this.totalIntCreditAmount.toString();
    }
    return null;
  }
  
  public void setTotalIntCreditAmount(Currency paramCurrency)
  {
    this.totalIntCreditAmount = paramCurrency;
  }
  
  public void setTotalIntCreditAmount(String paramString)
  {
    if (this.totalIntCreditAmount == null) {
      this.totalIntCreditAmount = new Currency(paramString, this.locale);
    } else {
      this.totalIntCreditAmount.fromString(paramString);
    }
  }
  
  public int getNumDaysOverValue()
  {
    return this.numDaysOver;
  }
  
  public String getNumDaysOver()
  {
    return String.valueOf(this.numDaysOver);
  }
  
  public void setNumDaysOver(int paramInt)
  {
    this.numDaysOver = paramInt;
  }
  
  public Currency getAvgOverAmountValue()
  {
    return this.avgOverAmount;
  }
  
  public String getAvgOverAmount()
  {
    if (this.avgOverAmount != null) {
      return this.avgOverAmount.toString();
    }
    return null;
  }
  
  public void setAvgOverAmount(Currency paramCurrency)
  {
    this.avgOverAmount = paramCurrency;
  }
  
  public void setAvgOverAmount(String paramString)
  {
    if (this.avgOverAmount == null) {
      this.avgOverAmount = new Currency(paramString, this.locale);
    } else {
      this.avgOverAmount.fromString(paramString);
    }
  }
  
  public double getAvgOverRateValue()
  {
    return this.avgOverRate;
  }
  
  public String getAvgOverRate()
  {
    return String.valueOf(this.avgOverRate);
  }
  
  public void setAvgOverRate(double paramDouble)
  {
    this.avgOverRate = paramDouble;
  }
  
  public Currency getAvgDailyOverAmountValue()
  {
    return this.avgDailyOverAmount;
  }
  
  public String getAvgDailyOverAmount()
  {
    if (this.avgDailyOverAmount != null) {
      return this.avgDailyOverAmount.toString();
    }
    return null;
  }
  
  public void setAvgDailyOverAmount(Currency paramCurrency)
  {
    this.avgDailyOverAmount = paramCurrency;
  }
  
  public void setAvgDailyOverAmount(String paramString)
  {
    if (this.avgDailyOverAmount == null) {
      this.avgDailyOverAmount = new Currency(paramString, this.locale);
    } else {
      this.avgDailyOverAmount.fromString(paramString);
    }
  }
  
  public int getNumDaysChargedValue()
  {
    return this.numDaysCharged;
  }
  
  public String getNumDaysCharged()
  {
    return String.valueOf(this.numDaysCharged);
  }
  
  public void setNumDaysCharged(int paramInt)
  {
    this.numDaysCharged = paramInt;
  }
  
  public Currency getTotalOverChargedAmountValue()
  {
    return this.totalOverChargedAmount;
  }
  
  public String getTotalOverChargedAmount()
  {
    if (this.totalOverChargedAmount != null) {
      return this.totalOverChargedAmount.toString();
    }
    return null;
  }
  
  public void setTotalOverChargedAmount(Currency paramCurrency)
  {
    this.totalOverChargedAmount = paramCurrency;
  }
  
  public void setTotalOverChargedAmount(String paramString)
  {
    if (this.totalOverChargedAmount == null) {
      this.totalOverChargedAmount = new Currency(paramString, this.locale);
    } else {
      this.totalOverChargedAmount.fromString(paramString);
    }
  }
  
  public void set(InterestBalanceSummary paramInterestBalanceSummary)
  {
    if (paramInterestBalanceSummary != null)
    {
      if (paramInterestBalanceSummary.getReserveReqAmountValue() != null) {
        setReserveReqAmount((Currency)paramInterestBalanceSummary.getReserveReqAmountValue().clone());
      }
      if (paramInterestBalanceSummary.getAvgLedgerBalanceAmountValue() != null) {
        setAvgLedgerBalanceAmount((Currency)paramInterestBalanceSummary.getAvgLedgerBalanceAmountValue().clone());
      }
      if (paramInterestBalanceSummary.getAvgFloatAmountValue() != null) {
        setAvgFloatAmount((Currency)paramInterestBalanceSummary.getAvgFloatAmountValue().clone());
      }
      if (paramInterestBalanceSummary.getAvgColAmountValue() != null) {
        setAvgColAmount((Currency)paramInterestBalanceSummary.getAvgColAmountValue().clone());
      }
      if (paramInterestBalanceSummary.getAvgReserveReqAmountValue() != null) {
        setAvgReserveReqAmount((Currency)paramInterestBalanceSummary.getAvgReserveReqAmountValue().clone());
      }
      if (paramInterestBalanceSummary.getAvgBalQualIntAmountValue() != null) {
        setAvgBalQualIntAmount((Currency)paramInterestBalanceSummary.getAvgBalQualIntAmountValue().clone());
      }
      setAvgIntRate(paramInterestBalanceSummary.getAvgIntRateValue());
      if (paramInterestBalanceSummary.getAvgDailyAccrAmountValue() != null) {
        setAvgDailyAccrAmount((Currency)paramInterestBalanceSummary.getAvgDailyAccrAmountValue().clone());
      }
      setNumDaysQual(paramInterestBalanceSummary.getNumDaysQualValue());
      if (paramInterestBalanceSummary.getTotalIntCreditAmountValue() != null) {
        setTotalIntCreditAmount((Currency)paramInterestBalanceSummary.getTotalIntCreditAmountValue().clone());
      }
      setNumDaysOver(paramInterestBalanceSummary.getNumDaysOverValue());
      if (paramInterestBalanceSummary.getAvgOverAmountValue() != null) {
        setAvgOverAmount((Currency)paramInterestBalanceSummary.getAvgOverAmountValue().clone());
      }
      setAvgOverRate(paramInterestBalanceSummary.getAvgOverRateValue());
      if (paramInterestBalanceSummary.getAvgDailyOverAmountValue() != null) {
        setAvgDailyOverAmount((Currency)paramInterestBalanceSummary.getAvgDailyOverAmountValue().clone());
      }
      setNumDaysCharged(paramInterestBalanceSummary.getNumDaysChargedValue());
      if (paramInterestBalanceSummary.getTotalOverChargedAmountValue() != null) {
        setTotalOverChargedAmount((Currency)paramInterestBalanceSummary.getTotalOverChargedAmountValue().clone());
      }
      super.set(paramInterestBalanceSummary);
    }
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("FED_RES_REQMT")) {
      setReserveReqAmount(paramString2);
    } else if (paramString1.equals("AVG_LEDGER_BAL")) {
      setAvgLedgerBalanceAmount(paramString2);
    } else if (paramString1.equals("AVG_FLOAT")) {
      setAvgFloatAmount(paramString2);
    } else if (paramString1.equals("AVG_COLL_BALANCE")) {
      setAvgColAmount(paramString2);
    } else if (paramString1.equals("AVG_RESERVE_REQMT")) {
      setAvgReserveReqAmount(paramString2);
    } else if (paramString1.equals("AVG_BAL_QLFYING_FOR_INTEREST")) {
      setAvgBalQualIntAmount(paramString2);
    } else if (paramString1.equals("AVG_INT_RATE")) {
      this.avgInterestRate = Double.valueOf(paramString2).doubleValue();
    } else if (paramString1.equals("AVG_DAILY_ACCR")) {
      setAvgDailyAccrAmount(paramString2);
    } else if (paramString1.equals("NO_OF_DAYS_QLFYING")) {
      this.numDaysQual = Integer.valueOf(paramString2).intValue();
    } else if (paramString1.equals("TOTAL_INT_TO_B_CRDTD_NXT_STMT")) {
      setTotalIntCreditAmount(paramString2);
    } else if (paramString1.equals("NO_OF_DAYS_OVERDRAWN")) {
      this.numDaysOver = Integer.valueOf(paramString2).intValue();
    } else if (paramString1.equals("AVG_OVERDRAFT")) {
      setAvgOverAmount(paramString2);
    } else if (paramString1.equals("AVG_OVERDRAFT_RATE")) {
      this.avgOverRate = Double.valueOf(paramString2).doubleValue();
    } else if (paramString1.equals("AVG_DLY_OVERDRAFT_CHG")) {
      setAvgDailyOverAmount(paramString2);
    } else if (paramString1.equals("NO_OF_DAYS_CHGD")) {
      this.numDaysCharged = Integer.valueOf(paramString2).intValue();
    } else if (paramString1.equals("TOTAL_OD_TOB_CHGD_NXT_STMT")) {
      setTotalOverChargedAmount(paramString2);
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "INTEREST_BALANCE_SUMMARY");
    if (this.reserveReqAmount != null) {
      XMLHandler.appendTag(localStringBuffer, "FED_RES_REQMT", this.reserveReqAmount.doubleValue());
    }
    if (this.avgLedgerBalanceAmount != null) {
      XMLHandler.appendTag(localStringBuffer, "AVG_LEDGER_BAL", this.avgLedgerBalanceAmount.doubleValue());
    }
    if (this.avgFloatAmount != null) {
      XMLHandler.appendTag(localStringBuffer, "AVG_FLOAT", this.avgFloatAmount.doubleValue());
    }
    if (this.avgColAmount != null) {
      XMLHandler.appendTag(localStringBuffer, "AVG_COLL_BALANCE", this.avgColAmount.doubleValue());
    }
    if (this.avgReserveReqAmount != null) {
      XMLHandler.appendTag(localStringBuffer, "AVG_RESERVE_REQMT", this.avgReserveReqAmount.doubleValue());
    }
    if (this.avgBalQualIntAmount != null) {
      XMLHandler.appendTag(localStringBuffer, "AVG_BAL_QLFYING_FOR_INTEREST", this.avgBalQualIntAmount.doubleValue());
    }
    XMLHandler.appendTag(localStringBuffer, "AVG_INT_RATE", this.avgInterestRate);
    if (this.avgDailyAccrAmount != null) {
      XMLHandler.appendTag(localStringBuffer, "AVG_DAILY_ACCR", this.avgDailyAccrAmount.doubleValue());
    }
    XMLHandler.appendTag(localStringBuffer, "NO_OF_DAYS_QLFYING", this.numDaysQual);
    if (this.totalIntCreditAmount != null) {
      XMLHandler.appendTag(localStringBuffer, "TOTAL_INT_TO_B_CRDTD_NXT_STMT", this.totalIntCreditAmount.doubleValue());
    }
    XMLHandler.appendTag(localStringBuffer, "NO_OF_DAYS_OVERDRAWN", this.numDaysOver);
    if (this.avgOverAmount != null) {
      XMLHandler.appendTag(localStringBuffer, "AVG_OVERDRAFT", this.avgOverAmount.doubleValue());
    }
    XMLHandler.appendTag(localStringBuffer, "AVG_OVERDRAFT_RATE", this.avgOverRate);
    if (this.avgDailyOverAmount != null) {
      XMLHandler.appendTag(localStringBuffer, "AVG_DLY_OVERDRAFT_CHG", this.avgDailyOverAmount.doubleValue());
    }
    XMLHandler.appendTag(localStringBuffer, "NO_OF_DAYS_CHGD", this.numDaysCharged);
    if (this.totalOverChargedAmount != null) {
      XMLHandler.appendTag(localStringBuffer, "TOTAL_OD_TOB_CHGD_NXT_STMT", this.totalOverChargedAmount.doubleValue());
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "INTEREST_BALANCE_SUMMARY");
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.istatements.InterestBalanceSummary
 * JD-Core Version:    0.7.0.1
 */