package com.ffusion.ffs.bpw.interfaces;

public class PaymentBatchInfo
  extends BPWInfoBase
{
  protected String qM;
  protected String qR;
  protected String qU;
  protected String qT;
  protected String qO;
  protected String qN;
  protected String qS;
  protected String qL;
  protected String qQ;
  protected PmtInfo[] qP;
  
  public String getBatchID()
  {
    return this.qM;
  }
  
  public void setBatchID(String paramString)
  {
    this.qM = paramString;
  }
  
  public String getFIID()
  {
    return this.qR;
  }
  
  public void setFIID(String paramString)
  {
    this.qR = paramString;
  }
  
  public String getCustomerId()
  {
    return this.qU;
  }
  
  public void setCustomerId(String paramString)
  {
    this.qU = paramString;
  }
  
  public String getBatchName()
  {
    return this.qT;
  }
  
  public void setBatchName(String paramString)
  {
    this.qT = paramString;
  }
  
  public String getBatchType()
  {
    return this.qO;
  }
  
  public void setBatchType(String paramString)
  {
    this.qO = paramString;
  }
  
  public String getTotalAmount()
  {
    return this.qN;
  }
  
  public void setTotalAmount(String paramString)
  {
    this.qN = paramString;
  }
  
  public String getAmountCurrency()
  {
    return this.qS;
  }
  
  public void setAmountCurrency(String paramString)
  {
    this.qS = paramString;
  }
  
  public String getPaymentCount()
  {
    return this.qL;
  }
  
  public void setPaymentCount(String paramString)
  {
    this.qL = paramString;
  }
  
  public PmtInfo[] getPayments()
  {
    return this.qP;
  }
  
  public void setPayments(PmtInfo[] paramArrayOfPmtInfo)
  {
    this.qP = paramArrayOfPmtInfo;
  }
  
  public String getBatchStatus()
  {
    return this.qQ;
  }
  
  public void setBatchStatus(String paramString)
  {
    this.qQ = paramString;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.PaymentBatchInfo
 * JD-Core Version:    0.7.0.1
 */