package com.ffusion.ffs.bpw.interfaces;

public class SmartCalendarFile
  extends BPWInfoBase
{
  private String rn;
  private String ro;
  
  public SmartCalendarFile() {}
  
  public SmartCalendarFile(String paramString1, String paramString2)
  {
    this.rn = paramString1;
    this.ro = paramString2;
  }
  
  public String getFiId()
  {
    return this.rn;
  }
  
  public void setFiId(String paramString)
  {
    this.rn = paramString;
  }
  
  public String getSmartCalendarStr()
  {
    return this.ro;
  }
  
  public void setSmartCalendarStr(String paramString)
  {
    this.ro = paramString;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.SmartCalendarFile
 * JD-Core Version:    0.7.0.1
 */