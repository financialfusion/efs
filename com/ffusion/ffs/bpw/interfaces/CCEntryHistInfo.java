package com.ffusion.ffs.bpw.interfaces;

public class CCEntryHistInfo
  extends BPWInfoBase
{
  protected String oB;
  protected String oH;
  protected String oJ;
  protected String[] oE;
  protected String[] ow;
  protected String[] oy;
  protected String[] oI;
  protected CCEntryInfo[] oC;
  protected String ov = null;
  protected String oA = null;
  protected boolean oD = false;
  protected String ou = null;
  public long totalTrans = 0L;
  protected long oF = 0L;
  protected String ox = null;
  protected String oG = null;
  protected boolean oz = true;
  
  public String getFIId()
  {
    return this.oB;
  }
  
  public void setFIId(String paramString)
  {
    this.oB = paramString;
  }
  
  public String getCustomerId()
  {
    return this.oH;
  }
  
  public void setCustomerId(String paramString)
  {
    this.oH = paramString;
  }
  
  public String getCompId()
  {
    return this.oJ;
  }
  
  public void setCompId(String paramString)
  {
    this.oJ = paramString;
  }
  
  public String[] getLocationIdList()
  {
    return this.oE;
  }
  
  public void setLocationIdList(String[] paramArrayOfString)
  {
    this.oE = paramArrayOfString;
  }
  
  public String[] getTransactionTypeList()
  {
    return this.ow;
  }
  
  public void setTransactionTypeList(String[] paramArrayOfString)
  {
    this.ow = paramArrayOfString;
  }
  
  public String[] getCategoryTypeList()
  {
    return this.oy;
  }
  
  public void setCategoryTypeList(String[] paramArrayOfString)
  {
    this.oy = paramArrayOfString;
  }
  
  public String[] getStatusList()
  {
    return this.oI;
  }
  
  public void setStatusList(String[] paramArrayOfString)
  {
    this.oI = paramArrayOfString;
  }
  
  public CCEntryInfo[] getCCEntryInfoList()
  {
    return this.oC;
  }
  
  public void setCCEntryInfoList(CCEntryInfo[] paramArrayOfCCEntryInfo)
  {
    this.oC = paramArrayOfCCEntryInfo;
  }
  
  public String getStartDate()
  {
    return this.ov;
  }
  
  public void setStartDate(String paramString)
  {
    this.ov = paramString;
  }
  
  public String getEndDate()
  {
    return this.oA;
  }
  
  public void setEndDate(String paramString)
  {
    this.oA = paramString;
  }
  
  public long getTotalTrans()
  {
    return this.totalTrans;
  }
  
  public void setTotalTrans(long paramLong)
  {
    this.totalTrans = paramLong;
  }
  
  public long getPageSize()
  {
    return this.oF;
  }
  
  public void setPageSize(long paramLong)
  {
    this.oF = paramLong;
  }
  
  public String getHistId()
  {
    return this.ox;
  }
  
  public void setHistId(String paramString)
  {
    this.ox = paramString;
  }
  
  public String getCursorId()
  {
    return this.oG;
  }
  
  public void setCursorId(String paramString)
  {
    this.oG = paramString;
  }
  
  public boolean getViewEntitlementCheck()
  {
    return this.oD;
  }
  
  public void setViewEntitlementCheck(boolean paramBoolean)
  {
    this.oD = paramBoolean;
  }
  
  public String getViewerId()
  {
    return this.ou;
  }
  
  public void setViewerId(String paramString)
  {
    this.ou = paramString;
  }
  
  public boolean getAscending()
  {
    return this.oz;
  }
  
  public void setAscending(boolean paramBoolean)
  {
    this.oz = paramBoolean;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.CCEntryHistInfo
 * JD-Core Version:    0.7.0.1
 */