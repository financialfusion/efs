package com.ffusion.ffs.bpw.interfaces;

public class RecACHBatchInfo
  extends ACHBatchInfo
{
  protected String s7 = null;
  protected String s9 = null;
  protected String s8 = null;
  protected int tb = 1;
  protected String[] ta;
  
  public String[] getSingleIds()
  {
    return this.ta;
  }
  
  public void setSingleIds(String[] paramArrayOfString)
  {
    this.ta = paramArrayOfString;
  }
  
  public String getStartDate()
  {
    return this.s7;
  }
  
  public void setStartDate(String paramString)
  {
    this.s7 = paramString;
  }
  
  public String getEndDate()
  {
    return this.s9;
  }
  
  public void setEndDate(String paramString)
  {
    this.s9 = paramString;
  }
  
  public String getFrequency()
  {
    return this.s8;
  }
  
  public void setFrequency(String paramString)
  {
    this.s8 = paramString;
  }
  
  public int getPmtsCount()
  {
    return this.tb;
  }
  
  public void setPmtsCount(int paramInt)
  {
    this.tb = paramInt;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.RecACHBatchInfo
 * JD-Core Version:    0.7.0.1
 */