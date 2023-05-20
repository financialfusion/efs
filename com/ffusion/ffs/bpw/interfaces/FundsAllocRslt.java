package com.ffusion.ffs.bpw.interfaces;

import java.io.Serializable;

public class FundsAllocRslt
  extends BPWInfoBase
  implements Serializable
{
  public String customerID;
  public String srvrTid;
  public int status;
  public String batchKey;
  protected PmtInfo nK;
  
  public FundsAllocRslt() {}
  
  public FundsAllocRslt(String paramString1, String paramString2, int paramInt)
  {
    this.customerID = paramString1;
    this.srvrTid = paramString2;
    this.status = paramInt;
  }
  
  public FundsAllocRslt(String paramString1, String paramString2, int paramInt, String paramString3)
  {
    this.customerID = paramString1;
    this.srvrTid = paramString2;
    this.status = paramInt;
    this.batchKey = paramString3;
  }
  
  public FundsAllocRslt(String paramString1, String paramString2, int paramInt, String paramString3, PmtInfo paramPmtInfo)
  {
    this.customerID = paramString1;
    this.srvrTid = paramString2;
    this.status = paramInt;
    this.batchKey = paramString3;
    this.nK = paramPmtInfo;
  }
  
  public PmtInfo getPmtInfo()
  {
    return this.nK;
  }
  
  public void setPmtInfo(PmtInfo paramPmtInfo)
  {
    this.nK = paramPmtInfo;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.FundsAllocRslt
 * JD-Core Version:    0.7.0.1
 */