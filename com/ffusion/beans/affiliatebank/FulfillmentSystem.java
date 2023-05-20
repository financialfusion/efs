package com.ffusion.beans.affiliatebank;

import com.ffusion.beans.ExtendABean;
import com.ffusion.ffs.bpw.interfaces.FulfillmentInfo;
import java.io.Serializable;

public class FulfillmentSystem
  extends ExtendABean
  implements Comparable, Serializable
{
  private String a5z;
  private int a5C;
  private double a5B;
  private String a5A;
  
  public String getFulfillmentSystemName()
  {
    return this.a5z;
  }
  
  public void setFulfillmentSystemName(String paramString)
  {
    this.a5z = paramString;
  }
  
  public String getRouteId()
  {
    return String.valueOf(this.a5C);
  }
  
  public void setRouteId(String paramString)
  {
    this.a5C = Integer.parseInt(paramString);
  }
  
  public int getRouteIdValue()
  {
    return this.a5C;
  }
  
  public void setRouteIdValue(int paramInt)
  {
    this.a5C = paramInt;
  }
  
  public String getPaymentCost()
  {
    return String.valueOf(this.a5B);
  }
  
  public void setPaymentCost(String paramString)
  {
    this.a5B = Double.parseDouble(paramString);
  }
  
  public double getPaymentCostValue()
  {
    return this.a5B;
  }
  
  public void setPaymentCostValue(double paramDouble)
  {
    this.a5B = paramDouble;
  }
  
  public String getHandlerName()
  {
    return this.a5A;
  }
  
  public void setHandlerName(String paramString)
  {
    this.a5A = paramString;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return 0;
  }
  
  public FulfillmentInfo getFulfillmentInfo()
  {
    FulfillmentInfo localFulfillmentInfo = new FulfillmentInfo();
    localFulfillmentInfo.FulfillmentSystemName = this.a5z;
    localFulfillmentInfo.RouteID = this.a5C;
    localFulfillmentInfo.HandlerName = this.a5A;
    localFulfillmentInfo.PaymentCost = this.a5B;
    return localFulfillmentInfo;
  }
  
  public void setFulfillmentInfo(FulfillmentInfo paramFulfillmentInfo)
  {
    this.a5z = paramFulfillmentInfo.FulfillmentSystemName;
    this.a5C = paramFulfillmentInfo.RouteID;
    this.a5A = paramFulfillmentInfo.HandlerName;
    this.a5B = paramFulfillmentInfo.PaymentCost;
  }
  
  public void set(FulfillmentSystem paramFulfillmentSystem)
  {
    if (paramFulfillmentSystem == null) {
      return;
    }
    this.a5z = paramFulfillmentSystem.a5z;
    this.a5C = paramFulfillmentSystem.a5C;
    this.a5A = paramFulfillmentSystem.a5A;
    this.a5B = paramFulfillmentSystem.a5B;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.affiliatebank.FulfillmentSystem
 * JD-Core Version:    0.7.0.1
 */