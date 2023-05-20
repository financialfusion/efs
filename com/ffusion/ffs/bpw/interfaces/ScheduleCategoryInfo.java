package com.ffusion.ffs.bpw.interfaces;

public class ScheduleCategoryInfo
  extends BPWInfoBase
{
  protected String lo;
  protected String ln;
  protected CutOffInfo[] lp;
  protected InstructionType[] lm;
  
  public String getFIId()
  {
    return this.lo;
  }
  
  public void setFIId(String paramString)
  {
    this.lo = paramString;
  }
  
  public String getCategory()
  {
    return this.ln;
  }
  
  public void setCategory(String paramString)
  {
    this.ln = paramString;
  }
  
  public CutOffInfo[] getCutOffInfoList()
  {
    return this.lp;
  }
  
  public void setCutOffInfoList(CutOffInfo[] paramArrayOfCutOffInfo)
  {
    this.lp = paramArrayOfCutOffInfo;
  }
  
  public InstructionType[] getInstructionTypeList()
  {
    return this.lm;
  }
  
  public void setInstructionTypeList(InstructionType[] paramArrayOfInstructionType)
  {
    this.lm = paramArrayOfInstructionType;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.ScheduleCategoryInfo
 * JD-Core Version:    0.7.0.1
 */