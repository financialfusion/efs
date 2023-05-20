package com.ffusion.beans.bcreport;

import com.ffusion.beans.Currency;
import com.ffusion.beans.ExtendABean;
import java.math.BigDecimal;

public class ServiceChargeEntry
  extends ExtendABean
{
  private String a4S;
  private int a4R;
  private Currency a4Q;
  private String a4P;
  
  public ServiceChargeEntry(String paramString, int paramInt, Currency paramCurrency)
  {
    this.a4S = paramString;
    this.a4R = paramInt;
    this.a4Q = paramCurrency;
  }
  
  public ServiceChargeEntry(String paramString, int paramInt)
  {
    this.a4S = paramString;
    this.a4R = paramInt;
  }
  
  public void setServiceCode(String paramString)
  {
    this.a4S = paramString;
  }
  
  public String getServiceCode()
  {
    return this.a4S;
  }
  
  public void setNumTransactions(int paramInt)
  {
    this.a4R = paramInt;
  }
  
  public int getNumTransactions()
  {
    return this.a4R;
  }
  
  public void setTotalTransactionValue(Currency paramCurrency)
  {
    this.a4Q = paramCurrency;
  }
  
  public Currency getTotalTransactionValue()
  {
    return this.a4Q;
  }
  
  public void setCurrencyCode(String paramString)
  {
    this.a4P = paramString;
  }
  
  public String getCurrencyCode()
  {
    return this.a4P;
  }
  
  public int compareTo(Object paramObject)
  {
    ServiceChargeEntry localServiceChargeEntry = (ServiceChargeEntry)paramObject;
    int i = this.a4S.compareTo(localServiceChargeEntry.getServiceCode());
    if (i < 0) {
      return -1;
    }
    if (i > 0) {
      return 1;
    }
    if (this.a4R < localServiceChargeEntry.getNumTransactions()) {
      return -1;
    }
    if (this.a4R > localServiceChargeEntry.getNumTransactions()) {
      return 1;
    }
    return this.a4Q.compareTo(localServiceChargeEntry.getTotalTransactionValue());
  }
  
  public void addTotalTransactionValue(BigDecimal paramBigDecimal)
  {
    this.a4Q.addAmount(paramBigDecimal);
  }
  
  public void addNumTransactions(int paramInt)
  {
    this.a4R += paramInt;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.bcreport.ServiceChargeEntry
 * JD-Core Version:    0.7.0.1
 */