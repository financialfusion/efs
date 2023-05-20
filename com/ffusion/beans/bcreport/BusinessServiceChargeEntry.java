package com.ffusion.beans.bcreport;

import com.ffusion.beans.ExtendABean;

public class BusinessServiceChargeEntry
  extends ExtendABean
{
  private String a4I;
  private String a4H;
  private String a4K;
  private ServiceChargeEntries a4J;
  
  public BusinessServiceChargeEntry(String paramString1, String paramString2, String paramString3, ServiceChargeEntries paramServiceChargeEntries)
  {
    this.a4I = paramString1;
    this.a4H = paramString2;
    this.a4K = paramString3;
    this.a4J = paramServiceChargeEntries;
  }
  
  public void setMarketSegment(String paramString)
  {
    this.a4I = paramString;
  }
  
  public String getMarketSegment()
  {
    return this.a4I;
  }
  
  public void setServicePackage(String paramString)
  {
    this.a4H = paramString;
  }
  
  public String getServicePackage()
  {
    return this.a4H;
  }
  
  public void setBusiness(String paramString)
  {
    this.a4K = paramString;
  }
  
  public String getBusiness()
  {
    return this.a4K;
  }
  
  public void setServiceCharges(ServiceChargeEntries paramServiceChargeEntries)
  {
    this.a4J = paramServiceChargeEntries;
  }
  
  public ServiceChargeEntries getServiceCharges()
  {
    return this.a4J;
  }
  
  public int compareTo(Object paramObject)
  {
    BusinessServiceChargeEntry localBusinessServiceChargeEntry = (BusinessServiceChargeEntry)paramObject;
    int i = this.a4I.compareTo(localBusinessServiceChargeEntry.getMarketSegment());
    if (i < 0) {
      return -1;
    }
    if (i > 0) {
      return 1;
    }
    i = this.a4H.compareTo(localBusinessServiceChargeEntry.getServicePackage());
    if (i < 0) {
      return -1;
    }
    if (i > 0) {
      return 1;
    }
    i = this.a4K.compareTo(localBusinessServiceChargeEntry.getBusiness());
    if (i < 0) {
      return -1;
    }
    if (i > 0) {
      return 1;
    }
    int j = this.a4J.size();
    int k = localBusinessServiceChargeEntry.getServiceCharges().size();
    if (j < k) {
      return -1;
    }
    if (j > k) {
      return 1;
    }
    return 1;
  }
  
  public int compare(Object paramObject, String paramString)
  {
    BusinessServiceChargeEntry localBusinessServiceChargeEntry = (BusinessServiceChargeEntry)paramObject;
    int i;
    if (paramString.equals("MARKETSEGMENT"))
    {
      i = this.a4I.compareTo(localBusinessServiceChargeEntry.getMarketSegment());
      if (i < 0) {
        return -1;
      }
      if (i > 0) {
        return 1;
      }
      return 0;
    }
    if (paramString.equals("SERVICEPACKAGE"))
    {
      i = this.a4H.compareTo(localBusinessServiceChargeEntry.getServicePackage());
      if (i < 0) {
        return -1;
      }
      if (i > 0) {
        return 1;
      }
      return 0;
    }
    if (paramString.equals("BUSINESS"))
    {
      i = this.a4K.compareTo(localBusinessServiceChargeEntry.getBusiness());
      if (i < 0) {
        return -1;
      }
      if (i > 0) {
        return 1;
      }
      return 0;
    }
    if (paramString.equals("SERVICECHARGES"))
    {
      i = this.a4J.size();
      int j = localBusinessServiceChargeEntry.getServiceCharges().size();
      if (i > j) {
        return 1;
      }
      if (i < j) {
        return -1;
      }
      return 1;
    }
    return super.compare(paramObject, paramString);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.bcreport.BusinessServiceChargeEntry
 * JD-Core Version:    0.7.0.1
 */