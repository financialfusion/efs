package com.ffusion.entitlements;

import java.util.HashMap;

public class PeriodLimit
  implements Limit, EntitlementCodes
{
  public static final String DAILY = "DAILY";
  public static final String WEEKLY = "WEEKLY";
  public static final String MONTHLY = "MONTHLY";
  public static final String YEARLY = "YEARLY";
  private String NV;
  private String NU;
  private String NZ = "DAILY";
  private int NY = 1;
  private String NX;
  private String N0;
  private double NT;
  private double NW;
  
  public void setPeriodType(String paramString)
  {
    this.NZ = paramString;
  }
  
  public String getPeriodType()
  {
    return this.NZ;
  }
  
  public void setPeriod(String paramString)
  {
    try
    {
      this.NY = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.NY = 0;
    }
  }
  
  public String getPeriod()
  {
    return Integer.toString(this.NY);
  }
  
  public void setAccountID(String paramString)
  {
    this.NX = paramString;
  }
  
  public String getAccountID()
  {
    return this.NX;
  }
  
  public void setPayeeID(String paramString)
  {
    this.N0 = paramString;
  }
  
  public String getPayeeID()
  {
    return this.N0;
  }
  
  public void setLimitValue(String paramString)
  {
    try
    {
      this.NW = Double.parseDouble(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public Limit copy()
  {
    PeriodLimit localPeriodLimit = new PeriodLimit();
    localPeriodLimit.setId(getId());
    localPeriodLimit.setAccountID(getAccountID());
    localPeriodLimit.setPayeeID(getPayeeID());
    localPeriodLimit.setLimitValue(getLimitValue());
    localPeriodLimit.setName(getName());
    localPeriodLimit.setPeriod(getPeriod());
    localPeriodLimit.setPeriodType(getPeriodType());
    return localPeriodLimit;
  }
  
  public void set(Limit paramLimit)
  {
    if ((paramLimit != null) && ((paramLimit instanceof PeriodLimit)))
    {
      PeriodLimit localPeriodLimit = (PeriodLimit)paramLimit;
      setAccountID(localPeriodLimit.getAccountID());
      setId(localPeriodLimit.getId());
      setLimitValue(localPeriodLimit.getLimitValue());
      setName(localPeriodLimit.getName());
      setPayeeID(localPeriodLimit.getPayeeID());
      setPeriod(localPeriodLimit.getPeriod());
      setPeriodType(localPeriodLimit.getPeriodType());
      setRunningTotalValue(localPeriodLimit.getRunningTotalValue());
    }
  }
  
  public void setId(String paramString)
  {
    this.NV = paramString;
  }
  
  public String getId()
  {
    return this.NV;
  }
  
  public void setName(String paramString)
  {
    this.NU = paramString;
  }
  
  public String getName()
  {
    return this.NU;
  }
  
  public String getLimitValue()
  {
    return String.valueOf(this.NW);
  }
  
  public void setRunningTotal(String paramString)
  {
    this.NT = Double.parseDouble(paramString);
  }
  
  public String getRunningTotal()
  {
    return String.valueOf(this.NT);
  }
  
  public void setRunningTotalValue(double paramDouble)
  {
    this.NT = paramDouble;
  }
  
  public double getRunningTotalValue()
  {
    return this.NT;
  }
  
  public boolean isLimitExceeded(Limit paramLimit)
  {
    if ((paramLimit instanceof PeriodLimit))
    {
      PeriodLimit localPeriodLimit = (PeriodLimit)paramLimit;
      if (!localPeriodLimit.getPeriodType().equals(getPeriodType())) {
        return false;
      }
      try
      {
        if (Double.valueOf(localPeriodLimit.getLimitValue()).doubleValue() > this.NW) {
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
    if ((paramLimitable instanceof TransactionLimitable))
    {
      TransactionLimitable localTransactionLimitable = (TransactionLimitable)paramLimitable;
      if ((this.NX != null) && (localTransactionLimitable.getLimitableFromID() == null)) {
        return false;
      }
      if ((this.N0 != null) && (localTransactionLimitable.getLimitableToID() == null)) {
        return false;
      }
      if (localTransactionLimitable.getLimitableValue() == null) {
        return false;
      }
      if ((this.NX != null) && (!localTransactionLimitable.getLimitableFromID().equals(this.NX))) {
        i = 0;
      }
      if ((i != 0) && (this.N0 != null) && (!localTransactionLimitable.getLimitableToID().equals(this.N0))) {
        i = 0;
      }
      if ((i != 0) && (this.NW < localTransactionLimitable.getLimitableValue().doubleValue() + this.NT)) {
        bool = true;
      }
    }
    return bool;
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
    if (getPeriodType() != null) {
      localHashMap.put("PeriodType", getPeriodType());
    }
    if ((getId() == null) || (getId().length() == 0))
    {
      localHashMap.put("RunningTotal", getRunningTotal());
      localHashMap.put("Period", getPeriod());
    }
    return localHashMap;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.entitlements.PeriodLimit
 * JD-Core Version:    0.7.0.1
 */