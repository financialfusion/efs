package com.ffusion.entitlements;

import java.util.ArrayList;
import java.util.Iterator;

public class Entitlement
  implements EntitlementCodes
{
  public static final String ENTITLEMENT = "ENTITLEMENT";
  public static final String ID = "ID";
  public static final String NAME = "NAME";
  public static final String LIMITS = "LIMITS";
  private String NJ;
  private String NH = String.valueOf(1);
  private String NE;
  private String NF;
  private ArrayList ND = new ArrayList();
  private EntitlementService NI;
  private Limit NG;
  
  public ArrayList getLimits()
  {
    return this.ND;
  }
  
  public void setCurrentLimitById(String paramString)
  {
    this.NG = getLimitById(paramString);
  }
  
  public void setCurrentLimitByName(String paramString)
  {
    this.NG = getLimitByName(paramString);
  }
  
  public String getCurrentLimitValue()
  {
    if (this.NG == null) {
      return "";
    }
    return this.NG.getLimitValue();
  }
  
  protected boolean addLimit(Limit paramLimit)
  {
    if (paramLimit == null) {
      return false;
    }
    this.ND.add(paramLimit);
    return true;
  }
  
  protected boolean deleteLimitById(String paramString)
  {
    Iterator localIterator = this.ND.iterator();
    while (localIterator.hasNext())
    {
      Limit localLimit = (Limit)localIterator.next();
      if (localLimit.getId().equals(paramString))
      {
        this.ND.remove(localLimit);
        return true;
      }
    }
    return false;
  }
  
  protected boolean deleteLimitByName(String paramString)
  {
    Iterator localIterator = this.ND.iterator();
    while (localIterator.hasNext())
    {
      Limit localLimit = (Limit)localIterator.next();
      if (localLimit.getName().equals(paramString))
      {
        this.ND.remove(localLimit);
        return true;
      }
    }
    return false;
  }
  
  protected Limit getLimitById(String paramString)
  {
    Iterator localIterator = this.ND.iterator();
    while (localIterator.hasNext())
    {
      Object localObject = localIterator.next();
      if (((localObject instanceof Limit)) && (((Limit)localObject).getId().equals(paramString))) {
        return (Limit)localObject;
      }
    }
    return null;
  }
  
  protected Limit getLimitByName(String paramString)
  {
    int i = this.ND.size();
    Iterator localIterator = this.ND.iterator();
    while (localIterator.hasNext())
    {
      Object localObject = localIterator.next();
      if (((localObject instanceof Limit)) && (((Limit)localObject).getName().equals(paramString))) {
        return (Limit)localObject;
      }
    }
    return null;
  }
  
  protected void setEntService(EntitlementService paramEntitlementService)
  {
    this.NI = paramEntitlementService;
  }
  
  public void setName(String paramString)
  {
    this.NE = paramString;
  }
  
  public String getName()
  {
    return this.NE;
  }
  
  public void setId(String paramString)
  {
    this.NF = paramString;
  }
  
  public String getId()
  {
    return this.NF;
  }
  
  public void setUserName(String paramString)
  {
    this.NJ = paramString;
  }
  
  public String getUserName()
  {
    return this.NJ;
  }
  
  public void setUserType(String paramString)
  {
    this.NH = paramString;
  }
  
  public String getUserType()
  {
    return this.NH;
  }
  
  protected Entitlement copy()
  {
    Entitlement localEntitlement = new Entitlement();
    localEntitlement.setName(getName());
    Limit localLimit = null;
    Iterator localIterator = this.ND.iterator();
    while (localIterator.hasNext())
    {
      localLimit = (Limit)localIterator.next();
      localEntitlement.addLimit(localLimit.copy());
    }
    return localEntitlement;
  }
  
  public void set(Entitlement paramEntitlement)
  {
    setId(paramEntitlement.getId());
    setUserName(paramEntitlement.getUserName());
    setUserType(paramEntitlement.getUserType());
    setName(paramEntitlement.getName());
    this.ND = paramEntitlement.ND;
  }
  
  public int getExceededLimits(Limitable paramLimitable, ArrayList paramArrayList)
  {
    if (this.NI == null) {
      return 14002;
    }
    int i = 0;
    i = this.NI.getRunningTotals(this.ND);
    Limit localLimit = null;
    Iterator localIterator = this.ND.iterator();
    while (localIterator.hasNext())
    {
      localLimit = (Limit)localIterator.next();
      if (localLimit.isLimitExceeded(paramLimitable))
      {
        paramArrayList.add(localLimit);
        i = 14001;
      }
    }
    return i;
  }
  
  protected int updateRunningTotals(ValueLimitable paramValueLimitable)
  {
    if (this.NI == null) {
      return 14002;
    }
    if (!(paramValueLimitable instanceof ValueLimitable)) {
      return 0;
    }
    return this.NI.updateRunningTotals(paramValueLimitable, this.ND);
  }
  
  protected int rollbackRunningTotals(ValueLimitable paramValueLimitable)
  {
    if (this.NI == null) {
      return 14002;
    }
    int i = 0;
    i = this.NI.getRunningTotals(this.ND);
    if (i == 0) {
      i = this.NI.rollbackRunningTotals(paramValueLimitable, this.ND);
    }
    return i;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.entitlements.Entitlement
 * JD-Core Version:    0.7.0.1
 */