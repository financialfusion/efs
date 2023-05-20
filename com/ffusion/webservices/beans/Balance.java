package com.ffusion.webservices.beans;

import java.util.Date;

public class Balance
  extends Bean
{
  protected Currency amount;
  protected Date date;
  
  public Currency getAmount()
  {
    return this.amount;
  }
  
  public void setAmount(Currency paramCurrency)
  {
    this.amount = paramCurrency;
  }
  
  public Date getDate()
  {
    return this.date;
  }
  
  public void setDate(Date paramDate)
  {
    this.date = paramDate;
  }
  
  public String toString()
  {
    return this.amount.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.webservices.beans.Balance
 * JD-Core Version:    0.7.0.1
 */