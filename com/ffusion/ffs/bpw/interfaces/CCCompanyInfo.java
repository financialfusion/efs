package com.ffusion.ffs.bpw.interfaces;

public class CCCompanyInfo
  extends BPWInfoBase
{
  protected String q1;
  protected String q4;
  protected String q6;
  protected String q3;
  protected String q5;
  protected String q0;
  protected String q2;
  protected String qZ;
  
  public String getCompId()
  {
    return this.q1;
  }
  
  public void setCompId(String paramString)
  {
    this.q1 = paramString;
  }
  
  public String getCustomerId()
  {
    return this.q4;
  }
  
  public void setCustomerId(String paramString)
  {
    this.q4 = paramString;
  }
  
  public String getCCCompId()
  {
    return this.q6;
  }
  
  public void setCCCompId(String paramString)
  {
    this.q6 = paramString;
  }
  
  public String getCompName()
  {
    return this.q3;
  }
  
  public void setCompName(String paramString)
  {
    this.q3 = paramString;
  }
  
  public String getConcentrateAcctId()
  {
    return this.q5;
  }
  
  public void setConcentrateAcctId(String paramString)
  {
    this.q5 = paramString;
  }
  
  public String getDisburseAcctId()
  {
    return this.q0;
  }
  
  public void setDisburseAcctId(String paramString)
  {
    this.q0 = paramString;
  }
  
  public String getBatchType()
  {
    return this.q2;
  }
  
  public void setBatchType(String paramString)
  {
    this.q2 = paramString;
  }
  
  public String getStatus()
  {
    return this.qZ;
  }
  
  public void setStatus(String paramString)
  {
    this.qZ = paramString;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.CCCompanyInfo
 * JD-Core Version:    0.7.0.1
 */