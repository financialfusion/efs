package com.ffusion.beans.istatements;

import com.ffusion.beans.Currency;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.XMLStrings;
import com.ffusion.util.XMLHandler;
import java.util.Locale;

public class MonthlyAccountSummary
  extends ExtendABean
  implements XMLStrings
{
  protected Currency avgColBalAmount;
  protected int numDeposits;
  protected int numItemsDeposited;
  protected int numWithdrawals;
  protected int numCashDeposited;
  protected int numTransTowardLimit;
  protected int numElectronicTrans;
  protected int numExcessTrans;
  protected Currency excessFeeAmount;
  protected int excessLimit;
  protected Currency excessTransTotalFeeAmount;
  
  public MonthlyAccountSummary() {}
  
  public MonthlyAccountSummary(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.avgColBalAmount != null) {
      this.avgColBalAmount.setLocale(paramLocale);
    }
    if (this.excessFeeAmount != null) {
      this.excessFeeAmount.setLocale(paramLocale);
    }
  }
  
  public Currency getAvgColBalValue()
  {
    return this.avgColBalAmount;
  }
  
  public String getAvgColBal()
  {
    String str = "";
    if (this.avgColBalAmount != null) {
      str = this.avgColBalAmount.toString();
    }
    return str;
  }
  
  public void setAvgColBal(Currency paramCurrency)
  {
    this.avgColBalAmount = paramCurrency;
  }
  
  public void setAvgColBal(String paramString)
  {
    if (this.avgColBalAmount == null) {
      this.avgColBalAmount = new Currency(paramString, this.locale);
    } else {
      this.avgColBalAmount.fromString(paramString);
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
  
  public int getItemsDepositedValue()
  {
    return this.numItemsDeposited;
  }
  
  public String getItemsDeposited()
  {
    return String.valueOf(this.numItemsDeposited);
  }
  
  public void setItemsDeposited(int paramInt)
  {
    this.numItemsDeposited = paramInt;
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
  
  public int getCashItemsDepositedValue()
  {
    return this.numCashDeposited;
  }
  
  public String getCashItemsDeposited()
  {
    return String.valueOf(this.numCashDeposited);
  }
  
  public void setCashItemsDeposited(int paramInt)
  {
    this.numCashDeposited = paramInt;
  }
  
  public int getTransTowardLimitValue()
  {
    return this.numTransTowardLimit;
  }
  
  public String getTransTowardLimit()
  {
    return String.valueOf(this.numTransTowardLimit);
  }
  
  public void setTransTowardLimit(int paramInt)
  {
    this.numTransTowardLimit = paramInt;
  }
  
  public int getNumElectronicTransValue()
  {
    return this.numElectronicTrans;
  }
  
  public String getNumElectronicTrans()
  {
    return String.valueOf(this.numElectronicTrans);
  }
  
  public void setNumElectronicTrans(int paramInt)
  {
    this.numElectronicTrans = paramInt;
  }
  
  public int getNumExcessTransValue()
  {
    return this.numExcessTrans;
  }
  
  public String getNumExcessTrans()
  {
    return String.valueOf(this.numExcessTrans);
  }
  
  public void setNumExcessTrans(int paramInt)
  {
    this.numExcessTrans = paramInt;
  }
  
  public Currency getExcessFeeAmountValue()
  {
    return this.excessFeeAmount;
  }
  
  public String getExcessFeeAmount()
  {
    String str = "";
    if (this.excessFeeAmount != null) {
      str = this.excessFeeAmount.toString();
    }
    return str;
  }
  
  public void setExcessFeeAmount(Currency paramCurrency)
  {
    this.excessFeeAmount = paramCurrency;
  }
  
  public void setExcessFeeAmount(String paramString)
  {
    if (this.excessFeeAmount == null) {
      this.excessFeeAmount = new Currency(paramString, this.locale);
    } else {
      this.excessFeeAmount.fromString(paramString);
    }
  }
  
  public Currency getExcessTransTotalFeeAmountValue()
  {
    return this.excessTransTotalFeeAmount;
  }
  
  public String getExcessTransTotalFeeAmount()
  {
    String str = "";
    if (this.excessTransTotalFeeAmount != null) {
      str = this.excessTransTotalFeeAmount.toString();
    }
    return str;
  }
  
  public void setExcessTransTotalFeeAmount(Currency paramCurrency)
  {
    this.excessTransTotalFeeAmount = paramCurrency;
  }
  
  public void setExcessTransTotalFeeAmount(String paramString)
  {
    if (this.excessTransTotalFeeAmount == null) {
      this.excessTransTotalFeeAmount = new Currency(paramString, this.locale);
    } else {
      this.excessTransTotalFeeAmount.fromString(paramString);
    }
  }
  
  public int getExcessLimitValue()
  {
    return this.excessLimit;
  }
  
  public String getExcessLimit()
  {
    return String.valueOf(this.excessLimit);
  }
  
  public void setExcessLimit(int paramInt)
  {
    this.excessLimit = paramInt;
  }
  
  public void set(MonthlyAccountSummary paramMonthlyAccountSummary)
  {
    if (paramMonthlyAccountSummary != null)
    {
      if (paramMonthlyAccountSummary.getAvgColBalValue() != null) {
        setAvgColBal((Currency)paramMonthlyAccountSummary.getAvgColBalValue().clone());
      }
      setNumDeposits(paramMonthlyAccountSummary.getNumDepositsValue());
      setItemsDeposited(paramMonthlyAccountSummary.getItemsDepositedValue());
      setNumWithdrawals(paramMonthlyAccountSummary.getNumWithdrawalsValue());
      setCashItemsDeposited(paramMonthlyAccountSummary.getCashItemsDepositedValue());
      setTransTowardLimit(paramMonthlyAccountSummary.getTransTowardLimitValue());
      setNumElectronicTrans(paramMonthlyAccountSummary.getNumElectronicTransValue());
      setNumExcessTrans(paramMonthlyAccountSummary.getNumExcessTransValue());
      if (paramMonthlyAccountSummary.getExcessFeeAmountValue() != null) {
        setExcessFeeAmount((Currency)paramMonthlyAccountSummary.getExcessFeeAmountValue().clone());
      }
      setExcessLimit(paramMonthlyAccountSummary.getExcessLimitValue());
      if (paramMonthlyAccountSummary.getExcessTransTotalFeeAmount() != null) {
        setExcessTransTotalFeeAmount((Currency)paramMonthlyAccountSummary.getExcessTransTotalFeeAmountValue().clone());
      }
      super.set(paramMonthlyAccountSummary);
    }
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("AVG_COLL_BALANCE")) {
      setAvgColBal(paramString2);
    } else if (paramString1.equals("DEPOSITS")) {
      this.numDeposits = Integer.valueOf(paramString2).intValue();
    } else if (paramString1.equals("ITEMS_DEPOSITED")) {
      this.numItemsDeposited = Integer.valueOf(paramString2).intValue();
    } else if (paramString1.equals("WITHDRAWALS")) {
      this.numWithdrawals = Integer.valueOf(paramString2).intValue();
    } else if (paramString1.equals("CASH_DEPOSITED")) {
      this.numCashDeposited = Integer.valueOf(paramString2).intValue();
    } else if (paramString1.equals("TRANS_TO_LIMIT")) {
      this.numTransTowardLimit = Integer.valueOf(paramString2).intValue();
    } else if (paramString1.equals("ELECT_TRANS")) {
      this.numElectronicTrans = Integer.valueOf(paramString2).intValue();
    } else if (paramString1.equals("NUM_XS_TRANSACTIONS")) {
      this.numExcessTrans = Integer.valueOf(paramString2).intValue();
    } else if (paramString1.equals("TRANS_FEE")) {
      setExcessFeeAmount(paramString2);
    } else if (paramString1.equals("XS_LIMIT")) {
      this.excessLimit = Integer.valueOf(paramString2).intValue();
    } else if (paramString1.equals("XS_TRANS_TOTAL_FEE")) {
      setExcessTransTotalFeeAmount(paramString2);
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "MONTHLY_ACCOUNT_SUMMARY");
    if (this.avgColBalAmount != null) {
      XMLHandler.appendTag(localStringBuffer, "AVG_COLL_BALANCE", this.avgColBalAmount.doubleValue());
    }
    XMLHandler.appendTag(localStringBuffer, "NO_OF_DEPOSITS", this.numDeposits);
    XMLHandler.appendTag(localStringBuffer, "ITEMS_DEPOSITED", this.numItemsDeposited);
    XMLHandler.appendTag(localStringBuffer, "NO_OF_WITHDRAWALS", this.numWithdrawals);
    XMLHandler.appendTag(localStringBuffer, "CASH_DEPOSITED", this.numCashDeposited);
    XMLHandler.appendTag(localStringBuffer, "TRANS_TO_LIMIT", this.numTransTowardLimit);
    XMLHandler.appendTag(localStringBuffer, "ELECT_TRANS", this.numElectronicTrans);
    XMLHandler.appendTag(localStringBuffer, "NUM_XS_TRANSACTIONS", this.numExcessTrans);
    if (this.excessFeeAmount != null) {
      XMLHandler.appendTag(localStringBuffer, "TRANS_FEE", this.excessFeeAmount.doubleValue());
    }
    XMLHandler.appendTag(localStringBuffer, "XS_LIMIT", this.excessLimit);
    if (this.excessTransTotalFeeAmount != null) {
      XMLHandler.appendTag(localStringBuffer, "XS_TRANS_TOTAL_FEE", this.excessTransTotalFeeAmount.doubleValue());
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "MONTHLY_ACCOUNT_SUMMARY");
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.istatements.MonthlyAccountSummary
 * JD-Core Version:    0.7.0.1
 */