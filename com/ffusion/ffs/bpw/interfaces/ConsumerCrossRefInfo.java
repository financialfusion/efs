package com.ffusion.ffs.bpw.interfaces;

import java.io.Serializable;

public class ConsumerCrossRefInfo
  implements Serializable
{
  public String consumerID;
  public String federalTaxID;
  public String consumerSSN;
  public String sponsorID;
  public String customerID;
  public String consumerType;
  public String submitDate;
  
  public ConsumerCrossRefInfo() {}
  
  public ConsumerCrossRefInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7)
  {
    this.consumerID = paramString1;
    this.federalTaxID = paramString2;
    this.consumerSSN = paramString3;
    this.sponsorID = paramString4;
    this.customerID = paramString5;
    this.consumerType = paramString6;
    this.submitDate = paramString7;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.ConsumerCrossRefInfo
 * JD-Core Version:    0.7.0.1
 */