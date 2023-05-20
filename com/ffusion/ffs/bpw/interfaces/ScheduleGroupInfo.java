package com.ffusion.ffs.bpw.interfaces;

public class ScheduleGroupInfo
  extends BPWInfoBase
{
  protected String p6;
  protected String p5;
  protected CutOffInfo[] p7;
  protected InstructionType[] p4;
  
  public String getFIId()
  {
    return this.p6;
  }
  
  public void setFIId(String paramString)
  {
    this.p6 = paramString;
  }
  
  public String getGroup()
  {
    return this.p5;
  }
  
  public void setGroup(String paramString)
  {
    this.p5 = paramString;
  }
  
  public CutOffInfo[] getCutOffInfoList()
  {
    return this.p7;
  }
  
  public void setCutOffInfoList(CutOffInfo[] paramArrayOfCutOffInfo)
  {
    this.p7 = paramArrayOfCutOffInfo;
  }
  
  public InstructionType[] getInstructionTypeList()
  {
    return this.p4;
  }
  
  public void setInstructionTypeList(InstructionType[] paramArrayOfInstructionType)
  {
    this.p4 = paramArrayOfInstructionType;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.ScheduleGroupInfo
 * JD-Core Version:    0.7.0.1
 */