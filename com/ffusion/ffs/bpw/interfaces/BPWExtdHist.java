package com.ffusion.ffs.bpw.interfaces;

import java.util.HashMap;

public class BPWExtdHist
  extends BPWHist
{
  public String acctType = null;
  public String bankId = null;
  public String recSrvrTid = null;
  public String payeeListId = null;
  public String billingAcct = null;
  public String payeeId = null;
  public HashMap extraInfo = new HashMap();
  public String fromBankId = null;
  public String acctCreditId = null;
  public String acctCreditType = null;
  public String acctOperator = "AND";
  
  public String getAcctType()
  {
    return this.acctType;
  }
  
  public void setAcctType(String paramString)
  {
    this.acctType = paramString;
  }
  
  public String getBankId()
  {
    return this.bankId;
  }
  
  public void setBankId(String paramString)
  {
    this.bankId = paramString;
  }
  
  public String getRecSrvrTid()
  {
    return this.recSrvrTid;
  }
  
  public void setRecSrvrTid(String paramString)
  {
    this.recSrvrTid = paramString;
  }
  
  public String getPayeeListId()
  {
    return this.payeeListId;
  }
  
  public void setPayeeListId(String paramString)
  {
    this.payeeListId = paramString;
  }
  
  public String getBillingAcct()
  {
    return this.billingAcct;
  }
  
  public void setBillingAcct(String paramString)
  {
    this.billingAcct = paramString;
  }
  
  public String getPayeeId()
  {
    return this.payeeId;
  }
  
  public void setPayeeId(String paramString)
  {
    this.payeeId = paramString;
  }
  
  public HashMap getExtraInfo()
  {
    return this.extraInfo;
  }
  
  public void setExtraInfo(HashMap paramHashMap)
  {
    this.extraInfo = paramHashMap;
  }
  
  public String getAcctCreditId()
  {
    return this.acctCreditId;
  }
  
  public void setAcctCreditId(String paramString)
  {
    this.acctCreditId = paramString;
  }
  
  public String getAcctCreditType()
  {
    return this.acctCreditType;
  }
  
  public void setAcctCreditType(String paramString)
  {
    this.acctCreditType = paramString;
  }
  
  public String getAcctOperator()
  {
    return this.acctOperator;
  }
  
  public void setAcctOperator(String paramString)
  {
    this.acctOperator = paramString;
  }
  
  public String getFromBankId()
  {
    return this.fromBankId;
  }
  
  public void setFromBankId(String paramString)
  {
    this.fromBankId = paramString;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.BPWExtdHist
 * JD-Core Version:    0.7.0.1
 */