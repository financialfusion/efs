package com.ffusion.dataconsolidator.adapter;

import com.ffusion.beans.Currency;

public class SummaryValue
{
  public Currency _amount;
  public int _itemCount;
  
  private SummaryValue(Currency paramCurrency)
  {
    this._amount = paramCurrency;
    this._itemCount = -1;
  }
  
  private SummaryValue(Currency paramCurrency, int paramInt)
  {
    this._amount = paramCurrency;
    this._itemCount = paramInt;
  }
  
  public static SummaryValue getInstance(Currency paramCurrency)
  {
    if (paramCurrency == null) {
      return null;
    }
    return new SummaryValue(paramCurrency);
  }
  
  public static SummaryValue getInstance(Currency paramCurrency, int paramInt)
  {
    if (paramCurrency == null) {
      return null;
    }
    return new SummaryValue(paramCurrency, paramInt);
  }
  
  public void setItemCount(int paramInt)
  {
    this._itemCount = paramInt;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.dataconsolidator.adapter.SummaryValue
 * JD-Core Version:    0.7.0.1
 */