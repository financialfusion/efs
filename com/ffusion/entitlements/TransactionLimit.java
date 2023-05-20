package com.ffusion.entitlements;

import java.util.HashMap;

public class TransactionLimit
  implements Limit, EntitlementCodes
{
  private String NP;
  private String NN;
  protected String accountId;
  protected String payeeId;
  protected double limit;
  
  public void setAccountID(String paramString)
  {
    this.accountId = paramString;
  }
  
  public String getAccountID()
  {
    return this.accountId;
  }
  
  public void setPayeeID(String paramString)
  {
    this.payeeId = paramString;
  }
  
  public String getPayeeID()
  {
    return this.payeeId;
  }
  
  public void setLimitValue(String paramString)
  {
    try
    {
      this.limit = Double.parseDouble(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public String getLimitValue()
  {
    return String.valueOf(this.limit);
  }
  
  public Limit copy()
  {
    TransactionLimit localTransactionLimit = new TransactionLimit();
    localTransactionLimit.setId(getId());
    localTransactionLimit.setAccountID(getAccountID());
    localTransactionLimit.setPayeeID(getPayeeID());
    localTransactionLimit.setLimitValue(getLimitValue());
    localTransactionLimit.setName(getName());
    return localTransactionLimit;
  }
  
  public void set(Limit paramLimit)
  {
    if ((paramLimit != null) && ((paramLimit instanceof TransactionLimit)))
    {
      TransactionLimit localTransactionLimit = (TransactionLimit)paramLimit;
      setId(localTransactionLimit.getId());
      setName(localTransactionLimit.getName());
      setAccountID(localTransactionLimit.getAccountID());
      setPayeeID(localTransactionLimit.getPayeeID());
      setLimitValue(localTransactionLimit.getLimitValue());
    }
  }
  
  public boolean isLimitExceeded(Limit paramLimit)
  {
    if ((paramLimit instanceof TransactionLimit))
    {
      TransactionLimit localTransactionLimit = (TransactionLimit)paramLimit;
      try
      {
        if (Double.valueOf(localTransactionLimit.getLimitValue()).doubleValue() > this.limit) {
          return true;
        }
      }
      catch (Exception localException)
      {
        return false;
      }
    }
    return false;
  }
  
  public boolean isLimitExceeded(Limitable paramLimitable)
  {
    boolean bool = false;
    int i = 1;
    double d = this.limit;
    if ((paramLimitable instanceof TransactionLimitable))
    {
      TransactionLimitable localTransactionLimitable = (TransactionLimitable)paramLimitable;
      if ((this.accountId != null) && (localTransactionLimitable.getLimitableFromID() == null)) {
        return false;
      }
      if ((this.payeeId != null) && (localTransactionLimitable.getLimitableToID() == null)) {
        return false;
      }
      if (localTransactionLimitable.getLimitableValue() == null) {
        return false;
      }
      if ((this.accountId != null) && (!localTransactionLimitable.getLimitableFromID().equals(this.accountId))) {
        i = 0;
      }
      if ((i != 0) && (this.payeeId != null) && (!localTransactionLimitable.getLimitableToID().equals(this.payeeId))) {
        i = 0;
      }
      if ((i != 0) && (this.limit < localTransactionLimitable.getLimitableValue().doubleValue())) {
        bool = true;
      }
    }
    return bool;
  }
  
  public void setId(String paramString)
  {
    this.NP = paramString;
  }
  
  public String getId()
  {
    return this.NP;
  }
  
  public void setName(String paramString)
  {
    this.NN = paramString;
  }
  
  public String getName()
  {
    return this.NN;
  }
  
  public HashMap getNameValuePairs()
  {
    HashMap localHashMap = new HashMap();
    if (getLimitValue() != null) {
      localHashMap.put("LimitValue", getLimitValue());
    }
    if (getAccountID() != null) {
      localHashMap.put("AccountID", getAccountID());
    }
    if (getPayeeID() != null) {
      localHashMap.put("PayeeID", getPayeeID());
    }
    return localHashMap;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.entitlements.TransactionLimit
 * JD-Core Version:    0.7.0.1
 */