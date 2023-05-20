package com.ffusion.beans.istatements;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.XMLStrings;
import com.ffusion.util.Sortable;
import com.ffusion.util.XMLHandler;
import java.util.Locale;

public class DailyBalanceSummary
  extends ExtendABean
  implements XMLStrings, Sortable, Comparable
{
  protected DateTime balanceDate;
  protected Currency amount;
  protected Currency reserveInUseAmount;
  
  public DailyBalanceSummary() {}
  
  public DailyBalanceSummary(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.balanceDate != null) {
      this.balanceDate.setLocale(paramLocale);
    }
    if (this.amount != null) {
      this.amount.setLocale(paramLocale);
    }
    if (this.reserveInUseAmount != null) {
      this.reserveInUseAmount.setLocale(paramLocale);
    }
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.balanceDate != null) {
      this.balanceDate.setFormat(paramString);
    }
  }
  
  public DateTime getBalanceDateValue()
  {
    return this.balanceDate;
  }
  
  public String getBalanceDate()
  {
    String str = "";
    if (this.balanceDate != null) {
      str = this.balanceDate.toString();
    }
    return str;
  }
  
  public void setBalanceDate(DateTime paramDateTime)
  {
    this.balanceDate = paramDateTime;
  }
  
  public void setBalanceDate(String paramString)
  {
    try
    {
      if (this.balanceDate == null) {
        this.balanceDate = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.balanceDate.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public Currency getAmountValue()
  {
    return this.amount;
  }
  
  public void setAmount(Currency paramCurrency)
  {
    this.amount = paramCurrency;
  }
  
  public void setAmount(String paramString)
  {
    if (this.amount == null) {
      this.amount = new Currency(paramString, this.locale);
    } else {
      this.amount.fromString(paramString);
    }
  }
  
  public String getAmount()
  {
    if (this.amount != null) {
      return this.amount.toString();
    }
    return null;
  }
  
  public Currency getReserveInUseAmountValue()
  {
    return this.reserveInUseAmount;
  }
  
  public void setReserveInUseAmount(Currency paramCurrency)
  {
    this.reserveInUseAmount = paramCurrency;
  }
  
  public void setReserveInUseAmount(String paramString)
  {
    if (this.reserveInUseAmount == null) {
      this.reserveInUseAmount = new Currency(paramString, this.locale);
    } else {
      this.reserveInUseAmount.fromString(paramString);
    }
  }
  
  public String getReserveInUseAmount()
  {
    if (this.reserveInUseAmount != null) {
      return this.reserveInUseAmount.toString();
    }
    return null;
  }
  
  public void set(DailyBalanceSummary paramDailyBalanceSummary)
  {
    if (paramDailyBalanceSummary != null)
    {
      if (paramDailyBalanceSummary.getBalanceDateValue() != null) {
        setBalanceDate((DateTime)paramDailyBalanceSummary.getBalanceDateValue().clone());
      }
      if (paramDailyBalanceSummary.getAmountValue() != null) {
        setAmount((Currency)paramDailyBalanceSummary.getAmountValue().clone());
      }
      if (paramDailyBalanceSummary.getReserveInUseAmountValue() != null) {
        setReserveInUseAmount((Currency)paramDailyBalanceSummary.getReserveInUseAmountValue().clone());
      }
      super.set(paramDailyBalanceSummary);
    }
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("DBS_DATE")) {
      setBalanceDate(paramString2);
    } else if (paramString1.equals("BALANCE")) {
      setAmount(paramString2);
    } else if (paramString1.equals("RES_IN_USE")) {
      setReserveInUseAmount(paramString2);
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "DBS_DATE");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    DailyBalanceSummary localDailyBalanceSummary = (DailyBalanceSummary)paramObject;
    int i = 1;
    if ((paramString.equals("DBS_DATE")) && (getBalanceDate() != null) && (localDailyBalanceSummary.getBalanceDate() != null)) {
      i = getBalanceDateValue().equals(localDailyBalanceSummary.getBalanceDateValue()) ? 0 : getBalanceDateValue().before(localDailyBalanceSummary.getBalanceDateValue()) ? -1 : 1;
    } else if ((paramString.equals("BALANCE")) && (getAmountValue() != null) && (localDailyBalanceSummary.getAmountValue() != null)) {
      i = getAmountValue().compareTo(localDailyBalanceSummary.getAmountValue());
    } else if ((paramString.equals("RES_IN_USE")) && (getReserveInUseAmountValue() != null) && (localDailyBalanceSummary.getReserveInUseAmountValue() != null)) {
      i = getReserveInUseAmountValue().compareTo(localDailyBalanceSummary.getReserveInUseAmountValue());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "DAILY_BALANCE_SUMMARY");
    if (this.balanceDate != null) {
      XMLHandler.appendTag(localStringBuffer, "DBS_DATE", this.balanceDate.toXMLFormat());
    }
    if (this.amount != null) {
      XMLHandler.appendTag(localStringBuffer, "BALANCE", this.amount.doubleValue());
    }
    if (this.reserveInUseAmount != null) {
      XMLHandler.appendTag(localStringBuffer, "RES_IN_USE", this.reserveInUseAmount.doubleValue());
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "DAILY_BALANCE_SUMMARY");
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.istatements.DailyBalanceSummary
 * JD-Core Version:    0.7.0.1
 */