package com.ffusion.ffs.bpw.interfaces;

public class CCCompanyAcctInfoList
  extends BPWInfoBase
{
  protected String wP;
  protected String wR;
  protected String wS;
  protected CCCompanyAcctInfo[] wQ;
  
  public String getCompId()
  {
    return this.wP;
  }
  
  public void setCompId(String paramString)
  {
    this.wP = paramString;
  }
  
  public String getCustomerId()
  {
    return this.wR;
  }
  
  public void setCustomerId(String paramString)
  {
    this.wR = paramString;
  }
  
  public String getTransactionType()
  {
    return this.wS;
  }
  
  public void setTransactionType(String paramString)
  {
    this.wS = paramString;
  }
  
  public CCCompanyAcctInfo[] getCCCompanyAcctInfoList()
  {
    return this.wQ;
  }
  
  public CCCompanyAcctInfo getCCCompanyAcctInfoList(int paramInt)
  {
    return null;
  }
  
  public void setCCCompanyAcctInfoList(CCCompanyAcctInfo[] paramArrayOfCCCompanyAcctInfo)
  {
    this.wQ = paramArrayOfCCCompanyAcctInfo;
  }
  
  public void setCCCompanyAcctInfoList(int paramInt, CCCompanyAcctInfo paramCCCompanyAcctInfo) {}
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.CCCompanyAcctInfoList
 * JD-Core Version:    0.7.0.1
 */