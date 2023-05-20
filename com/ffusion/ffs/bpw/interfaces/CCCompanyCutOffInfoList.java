package com.ffusion.ffs.bpw.interfaces;

public class CCCompanyCutOffInfoList
  extends BPWInfoBase
{
  protected String pZ;
  protected String pY;
  protected String p1;
  protected String p0;
  protected String p2;
  protected CCCompanyCutOffInfo[] pX;
  
  public String getCompCutOffId()
  {
    return this.pZ;
  }
  
  public void setCompCutOffId(String paramString)
  {
    this.pZ = paramString;
  }
  
  public String getCompId()
  {
    return this.pY;
  }
  
  public void setCompId(String paramString)
  {
    this.pY = paramString;
  }
  
  public String getCutOffId()
  {
    return this.p1;
  }
  
  public void setCutOffId(String paramString)
  {
    this.p1 = paramString;
  }
  
  public String getCustomerId()
  {
    return this.p0;
  }
  
  public void setCustomerId(String paramString)
  {
    this.p0 = paramString;
  }
  
  public String getTransactionType()
  {
    return this.p2;
  }
  
  public void setTransactionType(String paramString)
  {
    this.p2 = paramString;
  }
  
  public CCCompanyCutOffInfo[] getCCCompanyCutOffInfoList()
  {
    return this.pX;
  }
  
  public CCCompanyCutOffInfo getCCCompanyCutOffInfoList(int paramInt)
  {
    return null;
  }
  
  public void setCCCompanyCutOffInfoList(CCCompanyCutOffInfo[] paramArrayOfCCCompanyCutOffInfo)
  {
    this.pX = paramArrayOfCCCompanyCutOffInfo;
  }
  
  public void setCCCompanyCutOffInfoList(int paramInt, CCCompanyCutOffInfo paramCCCompanyCutOffInfo) {}
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.CCCompanyCutOffInfoList
 * JD-Core Version:    0.7.0.1
 */