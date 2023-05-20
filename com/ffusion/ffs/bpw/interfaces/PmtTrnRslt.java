package com.ffusion.ffs.bpw.interfaces;

import java.io.Serializable;

public class PmtTrnRslt
  extends BPWInfoBase
  implements Serializable
{
  public String customerID;
  public String srvrTid;
  public int status;
  public String message;
  public String extdPmtInfo;
  public String logID;
  public String batchKey;
  
  public PmtTrnRslt() {}
  
  public PmtTrnRslt(String paramString1, String paramString2, int paramInt, String paramString3, String paramString4)
  {
    this.customerID = paramString1;
    this.srvrTid = paramString2;
    this.status = paramInt;
    this.message = paramString3;
    this.extdPmtInfo = paramString4;
  }
  
  public PmtTrnRslt(String paramString1, String paramString2, int paramInt, String paramString3, String paramString4, String paramString5)
  {
    this.customerID = paramString1;
    this.srvrTid = paramString2;
    this.status = paramInt;
    this.message = paramString3;
    this.extdPmtInfo = paramString4;
    this.batchKey = paramString5;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.PmtTrnRslt
 * JD-Core Version:    0.7.0.1
 */