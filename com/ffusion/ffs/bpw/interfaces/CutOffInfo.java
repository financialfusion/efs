package com.ffusion.ffs.bpw.interfaces;

public class CutOffInfo
  extends BPWInfoBase
{
  protected String pJ;
  protected String pM;
  protected String pI;
  protected int pP = 9;
  protected int pQ;
  protected String pN;
  protected String pS;
  protected String pO;
  protected String pH;
  protected String pR;
  protected String pL;
  protected String pK;
  
  public String getStatus()
  {
    return this.pL;
  }
  
  public void setStatus(String paramString)
  {
    this.pL = paramString;
  }
  
  public String getCutOffId()
  {
    return this.pJ;
  }
  
  public void setCutOffId(String paramString)
  {
    this.pJ = paramString;
  }
  
  public String getFIId()
  {
    return this.pM;
  }
  
  public void setFIId(String paramString)
  {
    this.pM = paramString;
  }
  
  public String getInstructionType()
  {
    return this.pI;
  }
  
  public void setInstructionType(String paramString)
  {
    this.pI = paramString;
  }
  
  public int getFrequency()
  {
    return this.pP;
  }
  
  public void setFrequency(int paramInt)
  {
    this.pP = paramInt;
  }
  
  public int getDay()
  {
    return this.pQ;
  }
  
  public void setDay(int paramInt)
  {
    this.pQ = paramInt;
  }
  
  public String getProcessTime()
  {
    return this.pN;
  }
  
  public void setProcessTime(String paramString)
  {
    this.pN = paramString;
  }
  
  public String getExtension()
  {
    return this.pS;
  }
  
  public void setExtension(String paramString)
  {
    this.pS = paramString;
  }
  
  public String getNextProcessTime()
  {
    return this.pO;
  }
  
  public void setNextProcessTime(String paramString)
  {
    this.pO = paramString;
  }
  
  public String getMemo()
  {
    return this.pR;
  }
  
  public void setMemo(String paramString)
  {
    this.pR = paramString;
  }
  
  public String getAction()
  {
    return this.pK;
  }
  
  public void setAction(String paramString)
  {
    this.pK = paramString;
  }
  
  public String getLastProcessTime()
  {
    return this.pH;
  }
  
  public void setLastProcessTime(String paramString)
  {
    this.pH = paramString;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.CutOffInfo
 * JD-Core Version:    0.7.0.1
 */