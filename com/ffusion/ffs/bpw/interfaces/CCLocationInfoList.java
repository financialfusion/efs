package com.ffusion.ffs.bpw.interfaces;

public class CCLocationInfoList
  extends BPWInfoBase
{
  protected String tg;
  protected String tf;
  protected String td;
  protected String[] te;
  protected String th;
  protected String tc;
  protected CCLocationInfo[] ti;
  
  public String getFIId()
  {
    return this.tg;
  }
  
  public void setFIId(String paramString)
  {
    this.tg = paramString;
  }
  
  public String getCustomerId()
  {
    return this.tf;
  }
  
  public void setCustomerId(String paramString)
  {
    this.tf = paramString;
  }
  
  public String getCompId()
  {
    return this.td;
  }
  
  public void setCompId(String paramString)
  {
    this.td = paramString;
  }
  
  public String[] getLocationIdList()
  {
    return this.te;
  }
  
  public void setLocationIdList(String[] paramArrayOfString)
  {
    this.te = paramArrayOfString;
  }
  
  public CCLocationInfo[] getCCLocationInfoList()
  {
    return this.ti;
  }
  
  public void setCCLocationInfoList(CCLocationInfo[] paramArrayOfCCLocationInfo)
  {
    this.ti = paramArrayOfCCLocationInfo;
  }
  
  public String getConcentrateAcctId()
  {
    return this.th;
  }
  
  public void setConcentrateAcctId(String paramString)
  {
    this.th = paramString;
  }
  
  public String getDisburseAcctId()
  {
    return this.tc;
  }
  
  public void setDisburseAcctId(String paramString)
  {
    this.tc = paramString;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.CCLocationInfoList
 * JD-Core Version:    0.7.0.1
 */