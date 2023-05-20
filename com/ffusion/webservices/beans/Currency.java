package com.ffusion.webservices.beans;

import java.math.BigDecimal;

public class Currency
  extends Bean
{
  protected BigDecimal amount = new BigDecimal(0.0D);
  
  public BigDecimal getAmount()
  {
    return this.amount;
  }
  
  public void setAmount(BigDecimal paramBigDecimal)
  {
    this.amount = paramBigDecimal;
  }
  
  public String toString()
  {
    return this.amount.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.webservices.beans.Currency
 * JD-Core Version:    0.7.0.1
 */