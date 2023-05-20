package com.ffusion.beans.bcreport;

import com.ffusion.beans.ExtendABean;
import java.util.HashMap;

public class ConsumerServiceChargeList
  extends ExtendABean
  implements Comparable
{
  private String a4E;
  private String a4D;
  private HashMap a4F;
  private HashMap a4G;
  
  public ConsumerServiceChargeList() {}
  
  public ConsumerServiceChargeList(String paramString1, String paramString2)
  {
    this.a4E = paramString1;
    this.a4D = paramString2;
    this.a4F = new HashMap();
    this.a4G = new HashMap();
  }
  
  public void setMarketSegment(String paramString)
  {
    this.a4E = paramString;
  }
  
  public void setServicePackage(String paramString)
  {
    this.a4D = paramString;
  }
  
  public void setConsumers(HashMap paramHashMap)
  {
    this.a4F = paramHashMap;
  }
  
  public String getMarketSegment()
  {
    return this.a4E;
  }
  
  public String getServicePackage()
  {
    return this.a4D;
  }
  
  public HashMap getConsumers()
  {
    return this.a4F;
  }
  
  public HashMap getChargeableOperations()
  {
    return this.a4G;
  }
  
  public void addCustomer(String paramString, String[] paramArrayOfString)
  {
    this.a4F.put(paramString, paramArrayOfString);
  }
  
  public String getCustomer(int paramInt)
  {
    return (String)this.a4F.get(new Integer(paramInt));
  }
  
  public void addChargeableOperation(String paramString1, String paramString2)
  {
    this.a4G.put(paramString1, paramString2);
  }
  
  public String getServiceCodeByOperationName(String paramString)
  {
    return (String)this.a4G.get(paramString);
  }
  
  public int compareTo(Object paramObject)
  {
    ConsumerServiceChargeList localConsumerServiceChargeList = (ConsumerServiceChargeList)paramObject;
    int i = this.a4E.compareTo(localConsumerServiceChargeList.getMarketSegment());
    if (i < 0) {
      return -1;
    }
    if (i > 0) {
      return 1;
    }
    i = this.a4D.compareTo(localConsumerServiceChargeList.getServicePackage());
    if (i < 0) {
      return -1;
    }
    return 1;
  }
  
  public int compare(Object paramObject, String paramString)
  {
    ConsumerServiceChargeList localConsumerServiceChargeList = (ConsumerServiceChargeList)paramObject;
    int i;
    if (paramString.equals("MARKET_SEGMENT"))
    {
      i = this.a4E.compareTo(localConsumerServiceChargeList.getMarketSegment());
      if (i < 0) {
        return -1;
      }
      if (i > 0) {
        return 1;
      }
      return 0;
    }
    if (paramString.equals("SERVICE_PACKAGE"))
    {
      i = this.a4D.compareTo(localConsumerServiceChargeList.getServicePackage());
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
 * Qualified Name:     com.ffusion.beans.bcreport.ConsumerServiceChargeList
 * JD-Core Version:    0.7.0.1
 */