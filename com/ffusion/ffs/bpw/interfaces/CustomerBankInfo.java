package com.ffusion.ffs.bpw.interfaces;

import java.io.Serializable;

public class CustomerBankInfo
  implements Serializable
{
  public String consumerID;
  public String routingAndTransitNumber;
  public String acctNumber;
  public String acctType;
  public String settlementRefNumber;
  public String primaryAcctFlag;
  public String status;
  public String submitDate;
  
  public CustomerBankInfo() {}
  
  public CustomerBankInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8)
  {
    this.consumerID = paramString1;
    this.routingAndTransitNumber = paramString2;
    this.acctNumber = paramString3;
    this.acctType = paramString4;
    this.settlementRefNumber = paramString5;
    this.primaryAcctFlag = paramString6;
    this.status = paramString7;
    this.submitDate = paramString8;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.CustomerBankInfo
 * JD-Core Version:    0.7.0.1
 */