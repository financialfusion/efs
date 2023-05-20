package com.ffusion.ffs.bpw.interfaces;

import java.io.Serializable;

public class IntraTrnRslt
  extends BPWInfoBase
  implements Serializable
{
  public String srvrTid;
  public String confirmationNum;
  public int status;
  public int eventSequence;
  public String batchKey;
  protected String qf;
  protected String qe;
  public static final int BACKEND_NOT_COMPLETED = -1;
  
  public IntraTrnRslt()
  {
    this.status = -1;
  }
  
  public IntraTrnRslt(String paramString, int paramInt1, int paramInt2)
  {
    this.srvrTid = paramString;
    this.status = paramInt1;
    this.eventSequence = paramInt2;
    this.confirmationNum = null;
  }
  
  public IntraTrnRslt(String paramString1, String paramString2, int paramInt1, int paramInt2)
  {
    this.srvrTid = paramString1;
    this.confirmationNum = paramString2;
    this.status = paramInt1;
    this.eventSequence = paramInt2;
  }
  
  public IntraTrnRslt(String paramString1, int paramInt1, int paramInt2, String paramString2)
  {
    this.srvrTid = paramString1;
    this.status = paramInt1;
    this.eventSequence = paramInt2;
    this.confirmationNum = null;
    this.batchKey = paramString2;
  }
  
  public String getFromAmount()
  {
    return this.qf;
  }
  
  public void setFromAmount(String paramString)
  {
    this.qf = paramString;
  }
  
  public String getToAmount()
  {
    return this.qe;
  }
  
  public void setToAmount(String paramString)
  {
    this.qe = paramString;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.IntraTrnRslt
 * JD-Core Version:    0.7.0.1
 */