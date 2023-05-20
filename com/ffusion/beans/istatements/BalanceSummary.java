package com.ffusion.beans.istatements;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.XMLStrings;
import com.ffusion.util.XMLHandler;
import java.util.Locale;

public class BalanceSummary
  extends ExtendABean
  implements XMLStrings
{
  protected DateTime previousBalanceDate;
  protected Currency previousAmount;
  protected DateTime newBalanceDate;
  protected Currency newAmount;
  protected int numDeposits;
  protected Currency totalDeposits;
  protected int numWithdrawals;
  protected Currency totalWithdrawals;
  
  public BalanceSummary() {}
  
  public BalanceSummary(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.previousAmount != null) {
      this.previousAmount.setLocale(paramLocale);
    }
    if (this.previousBalanceDate != null) {
      this.previousBalanceDate.setLocale(paramLocale);
    }
    if (this.newAmount != null) {
      this.newAmount.setLocale(paramLocale);
    }
    if (this.newBalanceDate != null) {
      this.newBalanceDate.setLocale(paramLocale);
    }
    if (this.totalDeposits != null) {
      this.totalDeposits.setLocale(paramLocale);
    }
    if (this.totalWithdrawals != null) {
      this.totalWithdrawals.setLocale(paramLocale);
    }
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.previousBalanceDate != null) {
      this.previousBalanceDate.setFormat(paramString);
    }
    if (this.newBalanceDate != null) {
      this.newBalanceDate.setFormat(paramString);
    }
  }
  
  public DateTime getPreviousBalanceDateValue()
  {
    return this.previousBalanceDate;
  }
  
  public String getPreviousBalanceDate()
  {
    String str = "";
    if (this.previousBalanceDate != null) {
      str = this.previousBalanceDate.toString();
    }
    return str;
  }
  
  public void setPreviousBalanceDate(DateTime paramDateTime)
  {
    this.previousBalanceDate = paramDateTime;
  }
  
  public void setPreviousBalanceDate(String paramString)
  {
    try
    {
      if (this.previousBalanceDate == null) {
        this.previousBalanceDate = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.previousBalanceDate.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public Currency getPreviousAmountValue()
  {
    return this.previousAmount;
  }
  
  public void setPreviousAmount(Currency paramCurrency)
  {
    this.previousAmount = paramCurrency;
  }
  
  public void setPreviousAmount(String paramString)
  {
    if (this.previousAmount == null) {
      this.previousAmount = new Currency(paramString, this.locale);
    } else {
      this.previousAmount.fromString(paramString);
    }
  }
  
  public String getPreviousAmount()
  {
    if (this.previousAmount != null) {
      return this.previousAmount.toString();
    }
    return null;
  }
  
  public DateTime getNewBalanceDateValue()
  {
    return this.newBalanceDate;
  }
  
  public String getNewBalanceDate()
  {
    String str = "";
    if (this.newBalanceDate != null) {
      str = this.newBalanceDate.toString();
    }
    return str;
  }
  
  public void setNewBalanceDate(DateTime paramDateTime)
  {
    this.newBalanceDate = paramDateTime;
  }
  
  public void setNewBalanceDate(String paramString)
  {
    try
    {
      if (this.newBalanceDate == null) {
        this.newBalanceDate = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.newBalanceDate.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public Currency getNewAmountValue()
  {
    return this.newAmount;
  }
  
  public String getNewAmount()
  {
    if (this.newAmount != null) {
      return this.newAmount.toString();
    }
    return null;
  }
  
  public void setNewAmount(Currency paramCurrency)
  {
    this.newAmount = paramCurrency;
  }
  
  public void setNewAmount(String paramString)
  {
    if (this.newAmount == null) {
      this.newAmount = new Currency(paramString, this.locale);
    } else {
      this.newAmount.fromString(paramString);
    }
  }
  
  public int getNumDepositsValue()
  {
    return this.numDeposits;
  }
  
  public String getNumDeposits()
  {
    return String.valueOf(this.numDeposits);
  }
  
  public void setNumDeposits(int paramInt)
  {
    this.numDeposits = paramInt;
  }
  
  public Currency getTotalDepositsValue()
  {
    return this.totalDeposits;
  }
  
  public String getTotalDeposits()
  {
    if (this.totalDeposits != null) {
      return this.totalDeposits.toString();
    }
    return null;
  }
  
  public void setTotalDeposits(Currency paramCurrency)
  {
    this.totalDeposits = paramCurrency;
  }
  
  public void setTotalDeposits(String paramString)
  {
    if (this.totalDeposits == null) {
      this.totalDeposits = new Currency(paramString, this.locale);
    } else {
      this.totalDeposits.fromString(paramString);
    }
  }
  
  public int getNumWithdrawalsValue()
  {
    return this.numWithdrawals;
  }
  
  public String getNumWithdrawals()
  {
    return String.valueOf(this.numWithdrawals);
  }
  
  public void setNumWithdrawals(int paramInt)
  {
    this.numWithdrawals = paramInt;
  }
  
  public Currency getTotalWithdrawalsValue()
  {
    return this.totalWithdrawals;
  }
  
  public String getTotalWithdrawals()
  {
    if (this.totalWithdrawals != null) {
      return this.totalWithdrawals.toString();
    }
    return null;
  }
  
  public void setTotalWithdrawals(Currency paramCurrency)
  {
    this.totalWithdrawals = paramCurrency;
  }
  
  public void setTotalWithdrawals(String paramString)
  {
    if (this.totalWithdrawals == null) {
      this.totalWithdrawals = new Currency(paramString, this.locale);
    } else {
      this.totalWithdrawals.fromString(paramString);
    }
  }
  
  public void set(BalanceSummary paramBalanceSummary)
  {
    if (paramBalanceSummary != null)
    {
      if (paramBalanceSummary.getPreviousBalanceDateValue() != null) {
        setPreviousBalanceDate((DateTime)paramBalanceSummary.getPreviousBalanceDateValue().clone());
      }
      if (paramBalanceSummary.getPreviousAmountValue() != null) {
        setPreviousAmount((Currency)paramBalanceSummary.getPreviousAmountValue().clone());
      }
      if (paramBalanceSummary.getNewBalanceDateValue() != null) {
        setNewBalanceDate((DateTime)paramBalanceSummary.getNewBalanceDateValue().clone());
      }
      if (paramBalanceSummary.getNewAmountValue() != null) {
        setNewAmount((Currency)paramBalanceSummary.getNewAmountValue().clone());
      }
      setNumDeposits(paramBalanceSummary.getNumDepositsValue());
      if (paramBalanceSummary.getTotalDepositsValue() != null) {
        setTotalDeposits((Currency)paramBalanceSummary.getTotalDepositsValue().clone());
      }
      setNumWithdrawals(paramBalanceSummary.getNumWithdrawalsValue());
      if (paramBalanceSummary.getTotalWithdrawalsValue() != null) {
        setTotalWithdrawals((Currency)paramBalanceSummary.getTotalWithdrawalsValue().clone());
      }
      super.set(paramBalanceSummary);
    }
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("PREV_BAL_DATE")) {
      setPreviousBalanceDate(paramString2);
    } else if (paramString1.equals("PREV_BAL_AMT")) {
      setPreviousAmount(paramString2);
    } else if (paramString1.equals("NEW_BAL_DATE")) {
      setNewBalanceDate(paramString2);
    } else if (paramString1.equals("NEW_BAL_AMT")) {
      setNewAmount(paramString2);
    } else if (paramString1.equals("NO_OF_DEPOSITS")) {
      this.numDeposits = Integer.valueOf(paramString2).intValue();
    } else if (paramString1.equals("DEPOSITS_TOTAL")) {
      setTotalDeposits(paramString2);
    } else if (paramString1.equals("NO_OF_WITHDRAWALS")) {
      this.numWithdrawals = Integer.valueOf(paramString2).intValue();
    } else if (paramString1.equals("WITHDRAWALS_TOTAL")) {
      setTotalWithdrawals(paramString2);
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "BALANCE_SUMMARY");
    if (this.previousBalanceDate != null) {
      XMLHandler.appendTag(localStringBuffer, "PREV_BAL_DATE", this.previousBalanceDate.toXMLFormat());
    }
    if (this.previousAmount != null) {
      XMLHandler.appendTag(localStringBuffer, "PREV_BAL_AMT", this.previousAmount.doubleValue());
    }
    if (this.newBalanceDate != null) {
      XMLHandler.appendTag(localStringBuffer, "NEW_BAL_DATE", this.newBalanceDate.toXMLFormat());
    }
    if (this.newAmount != null) {
      XMLHandler.appendTag(localStringBuffer, "NEW_BAL_AMT", this.newAmount.doubleValue());
    }
    XMLHandler.appendTag(localStringBuffer, "NO_OF_DEPOSITS", this.numDeposits);
    if (this.totalDeposits != null) {
      XMLHandler.appendTag(localStringBuffer, "DEPOSITS_TOTAL", this.totalDeposits.doubleValue());
    }
    XMLHandler.appendTag(localStringBuffer, "NO_OF_WITHDRAWALS", this.numWithdrawals);
    if (this.totalWithdrawals != null) {
      XMLHandler.appendTag(localStringBuffer, "WITHDRAWALS_TOTAL", this.totalWithdrawals.doubleValue());
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "BALANCE_SUMMARY");
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.istatements.BalanceSummary
 * JD-Core Version:    0.7.0.1
 */