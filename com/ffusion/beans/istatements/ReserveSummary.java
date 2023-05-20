package com.ffusion.beans.istatements;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.XMLStrings;
import com.ffusion.util.XMLHandler;
import java.util.Locale;

public class ReserveSummary
  extends ExtendABean
  implements XMLStrings
{
  protected DateTime previousReserveDate;
  protected Currency previousReserveAmount;
  protected DateTime newReserveDate;
  protected Currency newReserveAmount;
  protected Currency pmtsOnReserveAmount;
  protected Currency reserveTransAmount;
  protected Currency financeChargeAmount;
  protected Currency serviceChargeAmount;
  protected double periodIntRate;
  protected double annualIntRate;
  protected Currency approvedReserveAmount;
  protected Currency availableReserveAmount;
  protected Currency resInUseFinanceChargeAmount;
  
  public ReserveSummary() {}
  
  public ReserveSummary(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.previousReserveAmount != null) {
      this.previousReserveAmount.setLocale(paramLocale);
    }
    if (this.previousReserveDate != null) {
      this.previousReserveDate.setLocale(paramLocale);
    }
    if (this.newReserveAmount != null) {
      this.newReserveAmount.setLocale(paramLocale);
    }
    if (this.newReserveDate != null) {
      this.newReserveDate.setLocale(paramLocale);
    }
    if (this.pmtsOnReserveAmount != null) {
      this.pmtsOnReserveAmount.setLocale(paramLocale);
    }
    if (this.reserveTransAmount != null) {
      this.reserveTransAmount.setLocale(paramLocale);
    }
    if (this.financeChargeAmount != null) {
      this.financeChargeAmount.setLocale(paramLocale);
    }
    if (this.serviceChargeAmount != null) {
      this.serviceChargeAmount.setLocale(paramLocale);
    }
    if (this.approvedReserveAmount != null) {
      this.approvedReserveAmount.setLocale(paramLocale);
    }
    if (this.availableReserveAmount != null) {
      this.availableReserveAmount.setLocale(paramLocale);
    }
    if (this.resInUseFinanceChargeAmount != null) {
      this.resInUseFinanceChargeAmount.setLocale(paramLocale);
    }
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.previousReserveDate != null) {
      this.previousReserveDate.setFormat(paramString);
    }
    if (this.newReserveDate != null) {
      this.newReserveDate.setFormat(paramString);
    }
  }
  
  public DateTime getPreviousReserveDateValue()
  {
    return this.previousReserveDate;
  }
  
  public String getPreviousReserveDate()
  {
    String str = "";
    if (this.previousReserveDate != null) {
      str = this.previousReserveDate.toString();
    }
    return str;
  }
  
  public void setPreviousReserveDate(DateTime paramDateTime)
  {
    this.previousReserveDate = paramDateTime;
  }
  
  public void setPreviousReserveDate(String paramString)
  {
    try
    {
      if (this.previousReserveDate == null) {
        this.previousReserveDate = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.previousReserveDate.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public Currency getPreviousReserveAmountValue()
  {
    return this.previousReserveAmount;
  }
  
  public void setPreviousReserveAmount(Currency paramCurrency)
  {
    this.previousReserveAmount = paramCurrency;
  }
  
  public void setPreviousReserveAmount(String paramString)
  {
    if (this.previousReserveAmount == null) {
      this.previousReserveAmount = new Currency(paramString, this.locale);
    } else {
      this.previousReserveAmount.fromString(paramString);
    }
  }
  
  public String getPreviousReserveAmount()
  {
    if (this.previousReserveAmount != null) {
      return this.previousReserveAmount.toString();
    }
    return null;
  }
  
  public DateTime getNewReserveDateValue()
  {
    return this.newReserveDate;
  }
  
  public String getNewReserveDate()
  {
    String str = "";
    if (this.newReserveDate != null) {
      str = this.newReserveDate.toString();
    }
    return str;
  }
  
  public void setNewReserveDate(DateTime paramDateTime)
  {
    this.newReserveDate = paramDateTime;
  }
  
  public void setNewReserveDate(String paramString)
  {
    try
    {
      if (this.newReserveDate == null) {
        this.newReserveDate = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.newReserveDate.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public Currency getNewReserveAmountValue()
  {
    return this.newReserveAmount;
  }
  
  public void setNewReserveAmount(Currency paramCurrency)
  {
    this.newReserveAmount = paramCurrency;
  }
  
  public void setNewReserveAmount(String paramString)
  {
    if (this.newReserveAmount == null) {
      this.newReserveAmount = new Currency(paramString, this.locale);
    } else {
      this.newReserveAmount.fromString(paramString);
    }
  }
  
  public String getNewReserveAmount()
  {
    if (this.newReserveAmount != null) {
      return this.newReserveAmount.toString();
    }
    return null;
  }
  
  public Currency getPmtsOnReserveAmountValue()
  {
    return this.pmtsOnReserveAmount;
  }
  
  public void setPmtsOnReserveAmount(Currency paramCurrency)
  {
    this.pmtsOnReserveAmount = paramCurrency;
  }
  
  public void setPmtsOnReserveAmount(String paramString)
  {
    if (this.pmtsOnReserveAmount == null) {
      this.pmtsOnReserveAmount = new Currency(paramString, this.locale);
    } else {
      this.pmtsOnReserveAmount.fromString(paramString);
    }
  }
  
  public String getPmtsOnReserveAmount()
  {
    if (this.pmtsOnReserveAmount != null) {
      return this.pmtsOnReserveAmount.toString();
    }
    return null;
  }
  
  public Currency getReserveTransAmountValue()
  {
    return this.reserveTransAmount;
  }
  
  public void setReserveTransAmount(Currency paramCurrency)
  {
    this.reserveTransAmount = paramCurrency;
  }
  
  public void setReserveTransAmount(String paramString)
  {
    if (this.reserveTransAmount == null) {
      this.reserveTransAmount = new Currency(paramString, this.locale);
    } else {
      this.reserveTransAmount.fromString(paramString);
    }
  }
  
  public String getReserveTransAmount()
  {
    if (this.reserveTransAmount != null) {
      return this.reserveTransAmount.toString();
    }
    return null;
  }
  
  public Currency getFinanceChargeAmountValue()
  {
    return this.financeChargeAmount;
  }
  
  public void setFinanceChargeAmount(Currency paramCurrency)
  {
    this.financeChargeAmount = paramCurrency;
  }
  
  public void setFinanceChargeAmount(String paramString)
  {
    if (this.financeChargeAmount == null) {
      this.financeChargeAmount = new Currency(paramString, this.locale);
    } else {
      this.financeChargeAmount.fromString(paramString);
    }
  }
  
  public String getFinanceChargeAmount()
  {
    if (this.financeChargeAmount != null) {
      return this.financeChargeAmount.toString();
    }
    return null;
  }
  
  public Currency getServiceChargeAmountValue()
  {
    return this.serviceChargeAmount;
  }
  
  public void setServiceChargeAmount(Currency paramCurrency)
  {
    this.serviceChargeAmount = paramCurrency;
  }
  
  public void setServiceChargeAmount(String paramString)
  {
    if (this.serviceChargeAmount == null) {
      this.serviceChargeAmount = new Currency(paramString, this.locale);
    } else {
      this.serviceChargeAmount.fromString(paramString);
    }
  }
  
  public String getServiceChargeAmount()
  {
    if (this.serviceChargeAmount != null) {
      return this.serviceChargeAmount.toString();
    }
    return null;
  }
  
  public double getPeriodIntRateValue()
  {
    return this.periodIntRate;
  }
  
  public String getPeriodIntRate()
  {
    return String.valueOf(this.periodIntRate);
  }
  
  public void setPeriodIntRate(double paramDouble)
  {
    this.periodIntRate = paramDouble;
  }
  
  public double getAnnualIntRateValue()
  {
    return this.annualIntRate;
  }
  
  public String getAnnualIntRate()
  {
    return String.valueOf(this.annualIntRate);
  }
  
  public void setAnnualIntRate(double paramDouble)
  {
    this.annualIntRate = paramDouble;
  }
  
  public Currency getApprovedReserveAmountValue()
  {
    return this.approvedReserveAmount;
  }
  
  public void setApprovedReserveAmount(Currency paramCurrency)
  {
    this.approvedReserveAmount = paramCurrency;
  }
  
  public void setApprovedReserveAmount(String paramString)
  {
    if (this.approvedReserveAmount == null) {
      this.approvedReserveAmount = new Currency(paramString, this.locale);
    } else {
      this.approvedReserveAmount.fromString(paramString);
    }
  }
  
  public String getApprovedReserveAmount()
  {
    if (this.approvedReserveAmount != null) {
      return this.approvedReserveAmount.toString();
    }
    return null;
  }
  
  public Currency getAvailableReserveAmountValue()
  {
    return this.availableReserveAmount;
  }
  
  public void setAvailableReserveAmount(Currency paramCurrency)
  {
    this.availableReserveAmount = paramCurrency;
  }
  
  public void setAvailableReserveAmount(String paramString)
  {
    if (this.availableReserveAmount == null) {
      this.availableReserveAmount = new Currency(paramString, this.locale);
    } else {
      this.availableReserveAmount.fromString(paramString);
    }
  }
  
  public String getAvailableReserveAmount()
  {
    if (this.availableReserveAmount != null) {
      return this.availableReserveAmount.toString();
    }
    return null;
  }
  
  public Currency getResInUseFinanceChargeAmountValue()
  {
    return this.resInUseFinanceChargeAmount;
  }
  
  public void setResInUseFinanceChargeAmount(Currency paramCurrency)
  {
    this.resInUseFinanceChargeAmount = paramCurrency;
  }
  
  public void setResInUseFinanceChargeAmount(String paramString)
  {
    if (this.resInUseFinanceChargeAmount == null) {
      this.resInUseFinanceChargeAmount = new Currency(paramString, this.locale);
    } else {
      this.resInUseFinanceChargeAmount.fromString(paramString);
    }
  }
  
  public String getResInUseFinanceChargeAmount()
  {
    if (this.resInUseFinanceChargeAmount != null) {
      return this.resInUseFinanceChargeAmount.toString();
    }
    return null;
  }
  
  public void set(ReserveSummary paramReserveSummary)
  {
    if (paramReserveSummary != null)
    {
      if (paramReserveSummary.getPreviousReserveDateValue() != null) {
        setPreviousReserveDate((DateTime)paramReserveSummary.getPreviousReserveDateValue().clone());
      }
      if (paramReserveSummary.getPreviousReserveAmountValue() != null) {
        setPreviousReserveAmount((Currency)paramReserveSummary.getPreviousReserveAmountValue().clone());
      }
      if (paramReserveSummary.getNewReserveDateValue() != null) {
        setNewReserveDate((DateTime)paramReserveSummary.getNewReserveDateValue().clone());
      }
      if (paramReserveSummary.getNewReserveAmountValue() != null) {
        setNewReserveAmount((Currency)paramReserveSummary.getNewReserveAmountValue().clone());
      }
      if (paramReserveSummary.getPmtsOnReserveAmountValue() != null) {
        setPmtsOnReserveAmount((Currency)paramReserveSummary.getPmtsOnReserveAmountValue().clone());
      }
      if (paramReserveSummary.getReserveTransAmountValue() != null) {
        setReserveTransAmount((Currency)paramReserveSummary.getReserveTransAmountValue().clone());
      }
      if (paramReserveSummary.getFinanceChargeAmountValue() != null) {
        setFinanceChargeAmount((Currency)paramReserveSummary.getFinanceChargeAmountValue().clone());
      }
      if (paramReserveSummary.getServiceChargeAmountValue() != null) {
        setServiceChargeAmount((Currency)paramReserveSummary.getServiceChargeAmountValue().clone());
      }
      setPeriodIntRate(paramReserveSummary.getPeriodIntRateValue());
      setAnnualIntRate(paramReserveSummary.getAnnualIntRateValue());
      if (paramReserveSummary.getApprovedReserveAmountValue() != null) {
        setApprovedReserveAmount((Currency)paramReserveSummary.getApprovedReserveAmountValue().clone());
      }
      if (paramReserveSummary.getAvailableReserveAmountValue() != null) {
        setAvailableReserveAmount((Currency)paramReserveSummary.getAvailableReserveAmountValue().clone());
      }
      if (paramReserveSummary.getResInUseFinanceChargeAmountValue() != null) {
        setResInUseFinanceChargeAmount((Currency)paramReserveSummary.getResInUseFinanceChargeAmountValue().clone());
      }
      super.set(paramReserveSummary);
    }
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("PREV_RES_IN_USE")) {
      setPreviousReserveAmount(paramString2);
    } else if (paramString1.equals("PREV_RES_IN_USE_DATE")) {
      setPreviousReserveDate(paramString2);
    } else if (paramString1.equals("NEW_RES_IN_USE")) {
      setNewReserveAmount(paramString2);
    } else if (paramString1.equals("NEW_RES_IN_USE_DATE")) {
      setNewReserveDate(paramString2);
    } else if (paramString1.equals("PAYMENTS_ON_RESERVE")) {
      setPmtsOnReserveAmount(paramString2);
    } else if (paramString1.equals("RES_TRANSACTIONS")) {
      setReserveTransAmount(paramString2);
    } else if (paramString1.equals("FINANCE_CHARGE")) {
      setFinanceChargeAmount(paramString2);
    } else if (paramString1.equals("SVC_CHARGE")) {
      setServiceChargeAmount(paramString2);
    } else if (paramString1.equals("PERIODIC_INT_RATE")) {
      this.periodIntRate = Double.valueOf(paramString2).doubleValue();
    } else if (paramString1.equals("ANNUAL_INT_RATE")) {
      this.annualIntRate = Double.valueOf(paramString2).doubleValue();
    } else if (paramString1.equals("APPROVED_RESERVE")) {
      setApprovedReserveAmount(paramString2);
    } else if (paramString1.equals("AVAILABLE_RESERVE")) {
      setAvailableReserveAmount(paramString2);
    } else if (paramString1.equals("AVG_DLY_RES_INUSE_FIN_CHG")) {
      setResInUseFinanceChargeAmount(paramString2);
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "RESERVE_SUMMARY");
    if (this.previousReserveDate != null) {
      XMLHandler.appendTag(localStringBuffer, "PREV_RES_IN_USE_DATE", this.previousReserveDate.toXMLFormat());
    }
    if (this.previousReserveAmount != null) {
      XMLHandler.appendTag(localStringBuffer, "PREV_RES_IN_USE", this.previousReserveAmount.doubleValue());
    }
    if (this.pmtsOnReserveAmount != null) {
      XMLHandler.appendTag(localStringBuffer, "PAYMENTS_ON_RESERVE", this.pmtsOnReserveAmount.doubleValue());
    }
    if (this.reserveTransAmount != null) {
      XMLHandler.appendTag(localStringBuffer, "RES_TRANSACTIONS", this.reserveTransAmount.doubleValue());
    }
    if (this.reserveTransAmount != null) {
      XMLHandler.appendTag(localStringBuffer, "FINANCE_CHARGE", this.financeChargeAmount.doubleValue());
    }
    if (this.reserveTransAmount != null) {
      XMLHandler.appendTag(localStringBuffer, "SVC_CHARGE", this.serviceChargeAmount.doubleValue());
    }
    if (this.newReserveDate != null) {
      XMLHandler.appendTag(localStringBuffer, "NEW_RES_IN_USE_DATE", this.newReserveDate.toXMLFormat());
    }
    if (this.newReserveAmount != null) {
      XMLHandler.appendTag(localStringBuffer, "NEW_RES_IN_USE", this.newReserveAmount.doubleValue());
    }
    XMLHandler.appendTag(localStringBuffer, "PERIODIC_INT_RATE", this.periodIntRate);
    XMLHandler.appendTag(localStringBuffer, "ANNUAL_INT_RATE", this.annualIntRate);
    if (this.approvedReserveAmount != null) {
      XMLHandler.appendTag(localStringBuffer, "APPROVED_RESERVE", this.approvedReserveAmount.doubleValue());
    }
    if (this.availableReserveAmount != null) {
      XMLHandler.appendTag(localStringBuffer, "AVAILABLE_RESERVE", this.availableReserveAmount.doubleValue());
    }
    if (this.resInUseFinanceChargeAmount != null) {
      XMLHandler.appendTag(localStringBuffer, "AVG_DLY_RES_INUSE_FIN_CHG", this.resInUseFinanceChargeAmount.doubleValue());
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "RESERVE_SUMMARY");
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.istatements.ReserveSummary
 * JD-Core Version:    0.7.0.1
 */