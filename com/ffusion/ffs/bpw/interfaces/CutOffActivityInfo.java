package com.ffusion.ffs.bpw.interfaces;

public class CutOffActivityInfo
  extends BPWInfoBase
{
  protected String ot = null;
  protected String os = null;
  protected String or = null;
  protected String op = null;
  protected String oo = null;
  protected String on = null;
  protected String oq = null;
  protected String om = null;
  
  public String getProcessId()
  {
    return this.ot;
  }
  
  public void setProcessId(String paramString)
  {
    this.ot = paramString;
  }
  
  public String getCutOffId()
  {
    return this.os;
  }
  
  public void setCutOffId(String paramString)
  {
    this.os = paramString;
  }
  
  public String getStartTime()
  {
    return this.or;
  }
  
  public void setStartTime(String paramString)
  {
    this.or = paramString;
  }
  
  public String getEndTime()
  {
    return this.op;
  }
  
  public void setEndTime(String paramString)
  {
    this.op = paramString;
  }
  
  public String getCutOffDay()
  {
    return this.oo;
  }
  
  public void setCutOffDay(String paramString)
  {
    this.oo = paramString;
  }
  
  public String getCutOffProcessTime()
  {
    return this.on;
  }
  
  public void setCutOffProcessTime(String paramString)
  {
    this.on = paramString;
  }
  
  public String getCutOffExtension()
  {
    return this.oq;
  }
  
  public void setCutOffExtension(String paramString)
  {
    this.oq = paramString;
  }
  
  public String getStatus()
  {
    return this.om;
  }
  
  public void setStatus(String paramString)
  {
    this.om = paramString;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.CutOffActivityInfo
 * JD-Core Version:    0.7.0.1
 */