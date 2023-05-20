package com.ffusion.ffs.bpw.interfaces;

public class ScheduleActivityInfo
  extends BPWInfoBase
{
  protected String rJ = null;
  protected String rO = null;
  protected String rP = null;
  protected String rN = null;
  protected String rM = null;
  protected String rL = null;
  protected String rK = null;
  
  public String getInstructionType()
  {
    return this.rJ;
  }
  
  public void setInstructionType(String paramString)
  {
    this.rJ = paramString;
  }
  
  public String getProcessId()
  {
    return this.rO;
  }
  
  public void setProcessId(String paramString)
  {
    this.rO = paramString;
  }
  
  public String getCutOffId()
  {
    return this.rN;
  }
  
  public void setCutOffId(String paramString)
  {
    this.rN = paramString;
  }
  
  public String getStartTime()
  {
    return this.rM;
  }
  
  public void setStartTime(String paramString)
  {
    this.rM = paramString;
  }
  
  public String getEndTime()
  {
    return this.rL;
  }
  
  public void setEndTime(String paramString)
  {
    this.rL = paramString;
  }
  
  public String getFileName()
  {
    return this.rP;
  }
  
  public void setFileName(String paramString)
  {
    this.rP = paramString;
  }
  
  public String getStatus()
  {
    return this.rK;
  }
  
  public void setStatus(String paramString)
  {
    this.rK = paramString;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.ScheduleActivityInfo
 * JD-Core Version:    0.7.0.1
 */