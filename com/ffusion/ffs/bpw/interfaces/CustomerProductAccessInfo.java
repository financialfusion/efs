package com.ffusion.ffs.bpw.interfaces;

import java.io.Serializable;

public class CustomerProductAccessInfo
  implements Serializable
{
  public String consumerID;
  public String productType;
  public String accessType;
  public String statusType;
  public String submitDate;
  
  public CustomerProductAccessInfo() {}
  
  public CustomerProductAccessInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    this.consumerID = paramString1;
    this.productType = paramString2;
    this.accessType = paramString3;
    this.statusType = paramString4;
    this.submitDate = paramString5;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.CustomerProductAccessInfo
 * JD-Core Version:    0.7.0.1
 */