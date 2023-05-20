package com.ffusion.ffs.bpw.interfaces;

public class CCCompanyCutOffInfo
  extends BPWInfoBase
{
  protected String pU;
  protected String pT;
  protected String pV;
  protected String pW;
  
  public String getCompCutOffId()
  {
    return this.pU;
  }
  
  public void setCompCutOffId(String paramString)
  {
    this.pU = paramString;
  }
  
  public String getCompId()
  {
    return this.pT;
  }
  
  public void setCompId(String paramString)
  {
    this.pT = paramString;
  }
  
  public String getCutOffId()
  {
    return this.pV;
  }
  
  public void setCutOffId(String paramString)
  {
    this.pV = paramString;
  }
  
  public String getTransactionType()
  {
    return this.pW;
  }
  
  public void setTransactionType(String paramString)
  {
    this.pW = paramString;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.CCCompanyCutOffInfo
 * JD-Core Version:    0.7.0.1
 */