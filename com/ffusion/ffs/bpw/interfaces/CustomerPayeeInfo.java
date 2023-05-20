package com.ffusion.ffs.bpw.interfaces;

public class CustomerPayeeInfo
  extends BPWInfoBase
{
  public String CustomerID;
  public String PayeeID;
  public int PayeeListID;
  public String PayAcct;
  public String NameOnAcct;
  public String Status;
  public int ErrCode;
  public String ErrMsg;
  public String ExtdInfo;
  public int LinkID;
  public int LinkGoDate;
  public String SubmitDate;
  public PayeeInfo payeeInfo = null;
  public CustomerInfo customerInfo = null;
  
  public CustomerPayeeInfo() {}
  
  public CustomerPayeeInfo(String paramString1, String paramString2, int paramInt1, String paramString3, String paramString4, int paramInt2, String paramString5, String paramString6, int paramInt3, int paramInt4)
  {
    this.CustomerID = paramString1;
    this.PayeeID = paramString2;
    this.PayeeListID = paramInt1;
    this.PayAcct = paramString3;
    this.Status = paramString4;
    this.ErrCode = paramInt2;
    this.ErrMsg = paramString5;
    this.ExtdInfo = paramString6;
    this.LinkID = paramInt3;
    this.LinkGoDate = paramInt4;
  }
  
  public CustomerPayeeInfo(String paramString1, String paramString2, int paramInt1, String paramString3, String paramString4, String paramString5, int paramInt2, String paramString6, String paramString7, int paramInt3, int paramInt4)
  {
    this.CustomerID = paramString1;
    this.PayeeID = paramString2;
    this.PayeeListID = paramInt1;
    this.PayAcct = paramString3;
    this.NameOnAcct = paramString4;
    this.Status = paramString5;
    this.ErrCode = paramInt2;
    this.ErrMsg = paramString6;
    this.ExtdInfo = paramString7;
    this.LinkID = paramInt3;
    this.LinkGoDate = paramInt4;
  }
  
  public CustomerInfo getCustomerInfo()
  {
    return this.customerInfo;
  }
  
  public void setCustomerInfo(CustomerInfo paramCustomerInfo)
  {
    this.customerInfo = paramCustomerInfo;
  }
  
  public PayeeInfo getPayeeInfo()
  {
    return this.payeeInfo;
  }
  
  public void setPayeeInfo(PayeeInfo paramPayeeInfo)
  {
    this.payeeInfo = paramPayeeInfo;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.CustomerPayeeInfo
 * JD-Core Version:    0.7.0.1
 */