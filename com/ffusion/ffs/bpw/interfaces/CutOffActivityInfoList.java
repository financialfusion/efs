package com.ffusion.ffs.bpw.interfaces;

public class CutOffActivityInfoList
  extends BPWInfoBase
{
  protected String[] oL = null;
  protected String oK = null;
  protected String oN = null;
  protected CutOffActivityInfo[] oM = null;
  
  public String[] getCutOffIdList()
  {
    return this.oL;
  }
  
  public void setCutOffIdList(String[] paramArrayOfString)
  {
    this.oL = paramArrayOfString;
  }
  
  public String getStartDate()
  {
    return this.oK;
  }
  
  public void setStartDate(String paramString)
  {
    this.oK = paramString;
  }
  
  public String getEndDate()
  {
    return this.oN;
  }
  
  public void setEndDate(String paramString)
  {
    this.oN = paramString;
  }
  
  public CutOffActivityInfo[] getCutOffActivity()
  {
    return this.oM;
  }
  
  public void setCutOffActivity(CutOffActivityInfo[] paramArrayOfCutOffActivityInfo)
  {
    this.oM = paramArrayOfCutOffActivityInfo;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.CutOffActivityInfoList
 * JD-Core Version:    0.7.0.1
 */