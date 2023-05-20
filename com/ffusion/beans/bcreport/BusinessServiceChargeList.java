package com.ffusion.beans.bcreport;

import com.ffusion.beans.ExtendABean;
import java.util.HashMap;

public class BusinessServiceChargeList
  extends ExtendABean
  implements Comparable
{
  private String a4y;
  private String a4x;
  private HashMap a4z;
  private HashMap a4A;
  
  public BusinessServiceChargeList() {}
  
  public BusinessServiceChargeList(String paramString1, String paramString2)
  {
    this.a4y = paramString1;
    this.a4x = paramString2;
    this.a4z = new HashMap();
    this.a4A = new HashMap();
  }
  
  public void setMarketSegment(String paramString)
  {
    this.a4y = paramString;
  }
  
  public void setServicePackage(String paramString)
  {
    this.a4x = paramString;
  }
  
  public void setBusinesses(HashMap paramHashMap)
  {
    this.a4z = paramHashMap;
  }
  
  public String getMarketSegment()
  {
    return this.a4y;
  }
  
  public String getServicePackage()
  {
    return this.a4x;
  }
  
  public HashMap getBusinesses()
  {
    return this.a4z;
  }
  
  public HashMap getChargeableOperations()
  {
    return this.a4A;
  }
  
  public void addBusiness(int paramInt, String paramString)
  {
    this.a4z.put(new Integer(paramInt), paramString);
  }
  
  public String getBusiness(int paramInt)
  {
    return (String)this.a4z.get(new Integer(paramInt));
  }
  
  public void addChargeableOperation(String paramString1, String paramString2)
  {
    this.a4A.put(paramString1, paramString2);
  }
  
  public String getServiceCodeByOperationName(String paramString)
  {
    return (String)this.a4A.get(paramString);
  }
  
  public int compareTo(Object paramObject)
  {
    BusinessServiceChargeList localBusinessServiceChargeList = (BusinessServiceChargeList)paramObject;
    int i = this.a4y.compareTo(localBusinessServiceChargeList.getMarketSegment());
    if (i < 0) {
      return -1;
    }
    if (i > 0) {
      return 1;
    }
    i = this.a4x.compareTo(localBusinessServiceChargeList.getServicePackage());
    if (i < 0) {
      return -1;
    }
    return 1;
  }
  
  public int compare(Object paramObject, String paramString)
  {
    BusinessServiceChargeList localBusinessServiceChargeList = (BusinessServiceChargeList)paramObject;
    int i;
    if (paramString.equals("MARKETSEGMENT"))
    {
      i = this.a4y.compareTo(localBusinessServiceChargeList.getMarketSegment());
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
      i = this.a4x.compareTo(localBusinessServiceChargeList.getServicePackage());
      if (i < 0) {
        return -1;
      }
      if (i > 0) {
        return 1;
      }
      return 0;
    }
    return super.compare(paramObject, paramString);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.bcreport.BusinessServiceChargeList
 * JD-Core Version:    0.7.0.1
 */