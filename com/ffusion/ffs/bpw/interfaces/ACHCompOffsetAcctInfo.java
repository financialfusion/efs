package com.ffusion.ffs.bpw.interfaces;

public class ACHCompOffsetAcctInfo
  extends BPWBankAcctInfo
{
  String p;
  
  public String getCompId()
  {
    return this.p;
  }
  
  public void setCompId(String paramString)
  {
    this.p = paramString;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.ACHCompOffsetAcctInfo
 * JD-Core Version:    0.7.0.1
 */