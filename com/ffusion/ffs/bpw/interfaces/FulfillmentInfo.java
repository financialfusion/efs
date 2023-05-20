package com.ffusion.ffs.bpw.interfaces;

public class FulfillmentInfo
  extends BPWInfoBase
{
  public String FulfillmentSystemName;
  public int RouteID;
  public String HandlerName;
  public double PaymentCost;
  protected String rR = null;
  protected String rQ = null;
  protected String rS = null;
  
  public FulfillmentInfo() {}
  
  public FulfillmentInfo(String paramString1, int paramInt, String paramString2, double paramDouble)
  {
    this.FulfillmentSystemName = paramString1;
    this.RouteID = paramInt;
    this.HandlerName = paramString2;
    this.PaymentCost = paramDouble;
  }
  
  public FulfillmentInfo(String paramString1, int paramInt, String paramString2, double paramDouble, String paramString3, String paramString4, String paramString5)
  {
    this.FulfillmentSystemName = paramString1;
    this.RouteID = paramInt;
    this.HandlerName = paramString2;
    this.PaymentCost = paramDouble;
    this.rR = paramString3;
    this.rQ = paramString4;
    this.rS = paramString5;
  }
  
  public String getImmediateFundAllocation()
  {
    return this.rR;
  }
  
  public void setImmediateFundAllocation(String paramString)
  {
    this.rR = paramString;
  }
  
  public String getImmediateHandlerName()
  {
    return this.rS;
  }
  
  public void setImmediateHandlerName(String paramString)
  {
    this.rS = paramString;
  }
  
  public String getImmediateProcessing()
  {
    return this.rQ;
  }
  
  public void setImmediateProcessing(String paramString)
  {
    this.rQ = paramString;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.FulfillmentInfo
 * JD-Core Version:    0.7.0.1
 */