package com.ffusion.ffs.bpw.interfaces;

public class ACHHistInfo
  extends BPWInfoBase
{
  protected String lG = null;
  protected int lH = -1;
  protected Object lF;
  
  public String getCursorId()
  {
    return this.lG;
  }
  
  public void setCursorId(String paramString)
  {
    this.lG = paramString;
  }
  
  public int getObjType()
  {
    return this.lH;
  }
  
  public void setObjType(int paramInt)
  {
    this.lH = paramInt;
  }
  
  public Object getObjInfo()
  {
    return this.lF;
  }
  
  public void setObjInfo(Object paramObject)
  {
    this.lF = paramObject;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.ACHHistInfo
 * JD-Core Version:    0.7.0.1
 */