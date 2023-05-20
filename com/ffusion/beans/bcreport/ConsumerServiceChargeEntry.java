package com.ffusion.beans.bcreport;

import com.ffusion.beans.ExtendABean;

public class ConsumerServiceChargeEntry
  extends ExtendABean
  implements Comparable
{
  private String a4v;
  private String a4t;
  private String a4s;
  private String a4w;
  private String a4r;
  private ServiceChargeEntries a4u;
  
  public ConsumerServiceChargeEntry()
  {
    this.a4v = "";
    this.a4t = "";
    this.a4s = "";
    this.a4w = "";
    this.a4r = "";
    this.a4u = null;
  }
  
  public ConsumerServiceChargeEntry(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, ServiceChargeEntries paramServiceChargeEntries)
  {
    this.a4v = paramString1;
    this.a4t = paramString2;
    this.a4s = paramString3;
    this.a4w = paramString4;
    this.a4r = paramString5;
    this.a4u = paramServiceChargeEntries;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if ((paramObject instanceof ConsumerServiceChargeEntry))
    {
      ConsumerServiceChargeEntry localConsumerServiceChargeEntry = (ConsumerServiceChargeEntry)paramObject;
      bool = (bool) && (this.a4v.equals(localConsumerServiceChargeEntry.getMarketSegment()));
      bool = (bool) && (this.a4t.equals(localConsumerServiceChargeEntry.getServicePackage()));
      bool = (bool) && (this.a4s.equals(localConsumerServiceChargeEntry.getCustomerName()));
      bool = (bool) && (this.a4w.equals(localConsumerServiceChargeEntry.getUserName()));
      bool = (bool) && (this.a4r.equals(localConsumerServiceChargeEntry.getCustomerNumber()));
      if (this.a4u == null) {
        bool = (bool) && (localConsumerServiceChargeEntry.getServiceCharges() == null);
      } else {
        bool = (bool) && (this.a4u.equals(localConsumerServiceChargeEntry.getServiceCharges()));
      }
    }
    else
    {
      bool = false;
    }
    return bool;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    int i = 0;
    if (!(paramObject instanceof ConsumerServiceChargeEntry)) {
      throw new ClassCastException("Object is not an instance of ConsumerServiceChargeEntry.");
    }
    ConsumerServiceChargeEntry localConsumerServiceChargeEntry = (ConsumerServiceChargeEntry)paramObject;
    i = this.a4v.compareTo(localConsumerServiceChargeEntry.a4v);
    if (i == 0)
    {
      i = this.a4t.compareTo(localConsumerServiceChargeEntry.a4t);
      if (i == 0) {
        i = this.a4s.compareTo(localConsumerServiceChargeEntry.a4s);
      }
    }
    return i;
  }
  
  public int compare(Object paramObject, String paramString)
    throws ClassCastException
  {
    int i = 0;
    if (!(paramObject instanceof ConsumerServiceChargeEntry)) {
      throw new ClassCastException("Object is not an instance of ConsumerServiceChargeEntry.");
    }
    ConsumerServiceChargeEntry localConsumerServiceChargeEntry = (ConsumerServiceChargeEntry)paramObject;
    if (paramString.equals("MARKET_SEGMENT")) {
      i = this.a4v.compareTo(localConsumerServiceChargeEntry.a4v);
    } else if (paramString.equals("SERVICE_PACKAGE")) {
      i = this.a4t.compareTo(localConsumerServiceChargeEntry.a4t);
    } else if (paramString.equals("CUSTOMER")) {
      i = this.a4s.compareTo(localConsumerServiceChargeEntry.a4s);
    } else if (paramString.equals("SERVICE_CHARGES")) {
      i = 0;
    }
    return super.compare(paramObject, paramString);
  }
  
  public String getMarketSegment()
  {
    return this.a4v;
  }
  
  public void setMarketSegment(String paramString)
  {
    if (paramString != null) {
      this.a4v = paramString;
    }
  }
  
  public String getServicePackage()
  {
    return this.a4t;
  }
  
  public void setServicePackage(String paramString)
  {
    if (paramString != null) {
      this.a4t = paramString;
    }
  }
  
  public String getCustomerName()
  {
    return this.a4s;
  }
  
  public void setCustomerName(String paramString)
  {
    if (paramString != null) {
      this.a4s = paramString;
    }
  }
  
  public String getUserName()
  {
    return this.a4w;
  }
  
  public void setUserName(String paramString)
  {
    if (paramString != null) {
      this.a4w = paramString;
    }
  }
  
  public String getCustomerNumber()
  {
    return this.a4r;
  }
  
  public void setCustomerNumber(String paramString)
  {
    if (paramString != null) {
      this.a4r = paramString;
    }
  }
  
  public ServiceChargeEntries getServiceCharges()
  {
    return this.a4u;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.bcreport.ConsumerServiceChargeEntry
 * JD-Core Version:    0.7.0.1
 */