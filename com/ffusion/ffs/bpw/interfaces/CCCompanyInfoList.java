package com.ffusion.ffs.bpw.interfaces;

public class CCCompanyInfoList
  extends BPWInfoBase
{
  protected String tj;
  protected String tl;
  protected String tk;
  protected String[] to;
  protected CCCompanyInfo[] tn;
  protected boolean tm = false;
  
  public String getCompId()
  {
    return this.tj;
  }
  
  public void setCompId(String paramString)
  {
    this.tj = paramString;
  }
  
  public String getFIId()
  {
    return this.tl;
  }
  
  public void setFIId(String paramString)
  {
    this.tl = paramString;
  }
  
  public String getCustomerId()
  {
    return this.tk;
  }
  
  public void setCustomerId(String paramString)
  {
    this.tk = paramString;
  }
  
  public String[] getCompIdList()
  {
    return this.to;
  }
  
  public String getCompIdList(int paramInt)
  {
    return null;
  }
  
  public void setCompIdList(String[] paramArrayOfString)
  {
    this.to = paramArrayOfString;
  }
  
  public void setCompIdList(int paramInt, String paramString) {}
  
  public CCCompanyInfo[] getCCCompanyInfoList()
  {
    return this.tn;
  }
  
  public CCCompanyInfo getCCCompanyInfoList(int paramInt)
  {
    return null;
  }
  
  public void setCCCompanyInfoList(CCCompanyInfo[] paramArrayOfCCCompanyInfo)
  {
    this.tn = paramArrayOfCCCompanyInfo;
  }
  
  public void setCCCompanyInfoList(int paramInt, CCCompanyInfo paramCCCompanyInfo) {}
  
  public void setIncludeDeletedEntries(boolean paramBoolean)
  {
    this.tm = paramBoolean;
  }
  
  public boolean getIncludeDeletedEntries()
  {
    return this.tm;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.CCCompanyInfoList
 * JD-Core Version:    0.7.0.1
 */