package com.ffusion.ffs.bpw.interfaces;

import java.io.Serializable;

public class CustPayeeRslt
  extends BPWInfoBase
  implements Serializable
{
  public String customerID;
  public int payeeListID;
  public String action;
  public int status;
  public String message;
  public String payeeID;
  public String payeeStatus;
  public int routeID;
  public String batchKey;
  
  public CustPayeeRslt() {}
  
  public CustPayeeRslt(String paramString1, int paramInt1, String paramString2, int paramInt2, String paramString3, String paramString4, String paramString5, int paramInt3)
  {
    this.customerID = paramString1;
    this.payeeListID = paramInt1;
    this.action = paramString2;
    this.status = paramInt2;
    this.message = paramString3;
    this.payeeID = paramString4;
    this.payeeStatus = paramString5;
    this.routeID = paramInt3;
  }
  
  public CustPayeeRslt(String paramString1, int paramInt1, String paramString2, int paramInt2, String paramString3, String paramString4, String paramString5, int paramInt3, String paramString6)
  {
    this.customerID = paramString1;
    this.payeeListID = paramInt1;
    this.action = paramString2;
    this.status = paramInt2;
    this.message = paramString3;
    this.payeeID = paramString4;
    this.payeeStatus = paramString5;
    this.routeID = paramInt3;
    this.batchKey = paramString6;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.CustPayeeRslt
 * JD-Core Version:    0.7.0.1
 */