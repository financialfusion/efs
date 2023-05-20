package com.ffusion.efs.adapters.profile;

import java.io.PrintStream;
import java.math.BigDecimal;

public class SummaryItem
{
  public BigDecimal totalDebitsAmount = new BigDecimal("0");
  public long totalNumDebits = 0L;
  public BigDecimal totalCreditsAmount = new BigDecimal("0");
  public long totalNumCredits = 0L;
  public long totalNumBatches = 0L;
  public long totalNumTransactions = 0L;
  public String currencyCode = "USD";
  public SummaryItem toAmountItem = null;
  
  public SummaryItem() {}
  
  public SummaryItem(BigDecimal paramBigDecimal, long paramLong)
  {
    if (paramBigDecimal != null) {
      this.totalDebitsAmount = this.totalDebitsAmount.add(paramBigDecimal);
    }
    this.totalNumDebits = paramLong;
  }
  
  public SummaryItem(BigDecimal paramBigDecimal1, long paramLong1, BigDecimal paramBigDecimal2, long paramLong2)
  {
    if (paramBigDecimal1 != null) {
      this.totalDebitsAmount = this.totalDebitsAmount.add(paramBigDecimal1);
    }
    this.totalNumDebits = paramLong1;
    if (paramBigDecimal2 != null) {
      this.totalCreditsAmount = this.totalCreditsAmount.add(paramBigDecimal2);
    }
    this.totalNumCredits = paramLong2;
  }
  
  public SummaryItem(BigDecimal paramBigDecimal1, long paramLong1, BigDecimal paramBigDecimal2, long paramLong2, long paramLong3)
  {
    if (paramBigDecimal1 != null) {
      this.totalDebitsAmount = this.totalDebitsAmount.add(paramBigDecimal1);
    }
    this.totalNumDebits = paramLong1;
    if (paramBigDecimal2 != null) {
      this.totalCreditsAmount = this.totalCreditsAmount.add(paramBigDecimal2);
    }
    this.totalNumCredits = paramLong2;
    this.totalNumBatches = paramLong3;
  }
  
  public void reset()
  {
    this.totalDebitsAmount = new BigDecimal("0");
    this.totalNumDebits = 0L;
    this.totalCreditsAmount = new BigDecimal("0");
    this.totalNumCredits = 0L;
    this.totalNumBatches = 0L;
    this.totalNumTransactions = 0L;
    this.currencyCode = "USD";
  }
  
  public void add(SummaryItem paramSummaryItem)
  {
    if (paramSummaryItem == null) {
      return;
    }
    if ((this.currencyCode != null) && (!this.currencyCode.equals(paramSummaryItem.getCurrencyCode())))
    {
      System.out.println("Currency Codes Don't Match!!!");
      return;
    }
    if ((paramSummaryItem.totalNumCredits > 0L) && (paramSummaryItem.totalCreditsAmount != null))
    {
      this.totalCreditsAmount = this.totalCreditsAmount.add(paramSummaryItem.totalCreditsAmount);
      this.totalNumCredits += paramSummaryItem.totalNumCredits;
    }
    if ((paramSummaryItem.totalNumDebits > 0L) && (paramSummaryItem.totalDebitsAmount != null))
    {
      this.totalDebitsAmount = this.totalDebitsAmount.add(paramSummaryItem.totalDebitsAmount);
      this.totalNumDebits += paramSummaryItem.totalNumDebits;
    }
    this.totalNumBatches += paramSummaryItem.totalNumBatches;
    this.totalNumTransactions += paramSummaryItem.totalNumTransactions;
    if (paramSummaryItem.toAmountItem != null)
    {
      if (this.toAmountItem == null)
      {
        this.toAmountItem = new SummaryItem();
        this.toAmountItem.setCurrencyCode(paramSummaryItem.getCurrencyCode());
      }
      paramSummaryItem.toAmountItem.toAmountItem = null;
      this.toAmountItem.add(paramSummaryItem.toAmountItem);
    }
  }
  
  public void setCurrencyCode(String paramString)
  {
    this.currencyCode = paramString;
  }
  
  public SummaryItem getToAmountItem()
  {
    return this.toAmountItem;
  }
  
  public void setToAmountItem(SummaryItem paramSummaryItem)
  {
    this.toAmountItem = paramSummaryItem;
  }
  
  public String getCurrencyCode()
  {
    return this.currencyCode;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.adapters.profile.SummaryItem
 * JD-Core Version:    0.7.0.1
 */