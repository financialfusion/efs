package com.ffusion.ffs.bpw.interfaces;

public class CCEntrySummaryInfoList
  extends BPWInfoBase
{
  protected String oV;
  protected String oU;
  protected String oP;
  protected String[] oS;
  protected String[] oO;
  protected String[] oT;
  protected String[] oR;
  protected CCEntrySummaryInfo[] oQ;
  
  public String getFIId()
  {
    return this.oV;
  }
  
  public void setFIId(String paramString)
  {
    this.oV = paramString;
  }
  
  public String getCustomerId()
  {
    return this.oU;
  }
  
  public void setCustomerId(String paramString)
  {
    this.oU = paramString;
  }
  
  public String getCompId()
  {
    return this.oP;
  }
  
  public void setCompId(String paramString)
  {
    this.oP = paramString;
  }
  
  public String[] getLocationIdList()
  {
    return this.oS;
  }
  
  public void setLocationIdList(String[] paramArrayOfString)
  {
    this.oS = paramArrayOfString;
  }
  
  public String[] getTransactionTypeList()
  {
    return this.oO;
  }
  
  public String getTransactionTypeList(int paramInt)
  {
    return null;
  }
  
  public void setTransactionTypeList(String[] paramArrayOfString)
  {
    this.oO = paramArrayOfString;
  }
  
  public void setTransactionTypeList(int paramInt, String paramString) {}
  
  public String[] getCategoryTypeList()
  {
    return this.oT;
  }
  
  public String getCategoryTypeList(int paramInt)
  {
    return null;
  }
  
  public void setCategoryTypeList(String[] paramArrayOfString)
  {
    this.oT = paramArrayOfString;
  }
  
  public void setCategoryTypeList(int paramInt, String paramString) {}
  
  public String[] getStatusList()
  {
    return this.oR;
  }
  
  public String getStatusList(int paramInt)
  {
    return null;
  }
  
  public void setStatusList(String[] paramArrayOfString)
  {
    this.oR = paramArrayOfString;
  }
  
  public void setStatusList(int paramInt, String paramString) {}
  
  public CCEntrySummaryInfo[] getCCEntrySummaryInfoList()
  {
    return this.oQ;
  }
  
  public CCEntrySummaryInfo getCCEntrySummaryInfoList(int paramInt)
  {
    return null;
  }
  
  public void setCCEntrySummaryInfoList(CCEntrySummaryInfo[] paramArrayOfCCEntrySummaryInfo)
  {
    this.oQ = paramArrayOfCCEntrySummaryInfo;
  }
  
  public void setCCEntrySummaryInfoList(int paramInt, CCEntrySummaryInfo paramCCEntrySummaryInfo) {}
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.CCEntrySummaryInfoList
 * JD-Core Version:    0.7.0.1
 */