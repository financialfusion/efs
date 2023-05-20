package com.ffusion.ffs.bpw.interfaces;

public class CutOffInfoList
  extends BPWInfoBase
{
  protected String lk;
  protected String lh;
  protected String[] lj;
  protected String[] li;
  protected CutOffInfo[] ll;
  
  public CutOffInfo[] getCutOffInfoList()
  {
    return this.ll;
  }
  
  public void setCutOffInfoList(CutOffInfo[] paramArrayOfCutOffInfo)
  {
    this.ll = paramArrayOfCutOffInfo;
  }
  
  public String getFIId()
  {
    return this.lk;
  }
  
  public void setFIId(String paramString)
  {
    this.lk = paramString;
  }
  
  public String getInstructionType()
  {
    return this.lh;
  }
  
  public void setInstructionType(String paramString)
  {
    this.lh = paramString;
  }
  
  public String[] getStatusList()
  {
    return this.lj;
  }
  
  public void setStatusList(String[] paramArrayOfString)
  {
    this.lj = paramArrayOfString;
  }
  
  public String[] getCutOffIdList()
  {
    return this.li;
  }
  
  public void setCutOffIdList(String[] paramArrayOfString)
  {
    this.li = paramArrayOfString;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.CutOffInfoList
 * JD-Core Version:    0.7.0.1
 */