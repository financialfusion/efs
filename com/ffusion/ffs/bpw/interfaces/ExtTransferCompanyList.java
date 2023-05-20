package com.ffusion.ffs.bpw.interfaces;

public class ExtTransferCompanyList
  extends BPWInfoBase
{
  protected String qY;
  protected String[] qX;
  protected String[] qW;
  ExtTransferCompanyInfo[] qV;
  
  public String getFIId()
  {
    return this.qY;
  }
  
  public void setFIId(String paramString)
  {
    this.qY = paramString;
  }
  
  public String[] getCustomerId()
  {
    return this.qX;
  }
  
  public void setCustomerId(String[] paramArrayOfString)
  {
    this.qX = paramArrayOfString;
  }
  
  public String[] getCompId()
  {
    return this.qW;
  }
  
  public void setCompId(String[] paramArrayOfString)
  {
    this.qW = paramArrayOfString;
  }
  
  public ExtTransferCompanyInfo[] getExtTransferCompanys()
  {
    return this.qV;
  }
  
  public void setExtTransferCompanys(ExtTransferCompanyInfo[] paramArrayOfExtTransferCompanyInfo)
  {
    this.qV = paramArrayOfExtTransferCompanyInfo;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.ExtTransferCompanyList
 * JD-Core Version:    0.7.0.1
 */