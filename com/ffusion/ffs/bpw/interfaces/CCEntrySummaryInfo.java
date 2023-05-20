package com.ffusion.ffs.bpw.interfaces;

public class CCEntrySummaryInfo
  extends BPWInfoBase
{
  protected String rl;
  protected String rj;
  protected String ri;
  protected int rk;
  protected int rh;
  protected float rm;
  protected float rg;
  
  public String getCustomerId()
  {
    return this.rl;
  }
  
  public void setCustomerId(String paramString)
  {
    this.rl = paramString;
  }
  
  public String getCompId()
  {
    return this.rj;
  }
  
  public void setCompId(String paramString)
  {
    this.rj = paramString;
  }
  
  public String getLocationId()
  {
    return this.ri;
  }
  
  public void setLocationId(String paramString)
  {
    this.ri = paramString;
  }
  
  public int getNumOfDepositEntries()
  {
    return this.rk;
  }
  
  public void setNumOfDepositEntries(int paramInt)
  {
    this.rk = paramInt;
  }
  
  public int getNumOfDisburseEntries()
  {
    return this.rh;
  }
  
  public void setNumOfDisburseEntries(int paramInt)
  {
    this.rh = paramInt;
  }
  
  public float getTotalDepositAmt()
  {
    return this.rm;
  }
  
  public void setTotalDepositAmt(float paramFloat)
  {
    this.rm = paramFloat;
  }
  
  public float getTotalDisbursmentAmt()
  {
    return this.rg;
  }
  
  public void setTotalDisbursmentAmt(float paramFloat)
  {
    this.rg = paramFloat;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.CCEntrySummaryInfo
 * JD-Core Version:    0.7.0.1
 */