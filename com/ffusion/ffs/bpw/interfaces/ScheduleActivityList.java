package com.ffusion.ffs.bpw.interfaces;

public class ScheduleActivityList
  extends BPWInfoBase
{
  protected String sr = null;
  protected String sq = null;
  protected String sp = null;
  protected String[] ss = null;
  protected ScheduleActivityInfo[] st = null;
  
  public String getFIID()
  {
    return this.sp;
  }
  
  public void setFIID(String paramString)
  {
    this.sp = paramString;
  }
  
  public String getFromDate()
  {
    return this.sr;
  }
  
  public void setFromDate(String paramString)
  {
    this.sr = paramString;
  }
  
  public String getToDate()
  {
    return this.sq;
  }
  
  public void setToDate(String paramString)
  {
    this.sq = paramString;
  }
  
  public String[] getInstructionTypeList()
  {
    return this.ss;
  }
  
  public void setInstructionTypeList(String[] paramArrayOfString)
  {
    this.ss = paramArrayOfString;
  }
  
  public ScheduleActivityInfo[] getScheduleActivity()
  {
    return this.st;
  }
  
  public void setScheduleActivity(ScheduleActivityInfo[] paramArrayOfScheduleActivityInfo)
  {
    this.st = paramArrayOfScheduleActivityInfo;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.ScheduleActivityList
 * JD-Core Version:    0.7.0.1
 */