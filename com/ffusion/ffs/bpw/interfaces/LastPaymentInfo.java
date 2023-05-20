package com.ffusion.ffs.bpw.interfaces;

public class LastPaymentInfo
  extends BPWInfoBase
{
  private static final long lu = -70701813058212416L;
  protected String ls = null;
  protected String lr = null;
  protected CustomerPayeeInfo[] lq;
  protected PmtInfo[] lt = null;
  protected int statusCode = -1;
  protected String statusMsg = null;
  
  public String getCustomerId()
  {
    return this.ls;
  }
  
  public void setCustomerId(String paramString)
  {
    this.ls = paramString;
  }
  
  public CustomerPayeeInfo[] getCustPayeeList()
  {
    return this.lq;
  }
  
  public void setCustPayeeList(CustomerPayeeInfo[] paramArrayOfCustomerPayeeInfo)
  {
    this.lq = paramArrayOfCustomerPayeeInfo;
  }
  
  public String getFiId()
  {
    return this.lr;
  }
  
  public void setFiId(String paramString)
  {
    this.lr = paramString;
  }
  
  public PmtInfo[] getLastPayments()
  {
    return this.lt;
  }
  
  public void setLastPayments(PmtInfo[] paramArrayOfPmtInfo)
  {
    this.lt = paramArrayOfPmtInfo;
  }
  
  public int getStatusCode()
  {
    return this.statusCode;
  }
  
  public void setStatusCode(int paramInt)
  {
    this.statusCode = paramInt;
  }
  
  public String getStatusMsg()
  {
    return this.statusMsg;
  }
  
  public void setStatusMsg(String paramString)
  {
    this.statusMsg = paramString;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.LastPaymentInfo
 * JD-Core Version:    0.7.0.1
 */