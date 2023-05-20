package com.ffusion.ffs.bpw.interfaces;

import java.io.Serializable;

public class PayeeRslt
  extends BPWInfoBase
  implements Serializable
{
  public String payeeID;
  public String action;
  public int status;
  public String message;
  public String extdPayeeID;
  
  public PayeeRslt() {}
  
  public PayeeRslt(String paramString1, String paramString2, int paramInt, String paramString3, String paramString4)
  {
    this.payeeID = paramString1;
    this.action = paramString2;
    this.status = paramInt;
    this.message = paramString3;
    this.extdPayeeID = paramString4;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.PayeeRslt
 * JD-Core Version:    0.7.0.1
 */