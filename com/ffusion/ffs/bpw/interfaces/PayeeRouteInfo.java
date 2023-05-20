package com.ffusion.ffs.bpw.interfaces;

import java.io.Serializable;

public class PayeeRouteInfo
  extends BPWInfoBase
  implements Serializable
{
  public String PayeeID;
  public int PayeeType;
  public double PaymentCost;
  public String ExtdPayeeID;
  public int RouteID;
  public String BankID;
  public String AcctID;
  public String AcctType;
  public String ExtdInfo;
  public String CurrencyCode;
  public boolean CustAcctRequired;
  
  public PayeeRouteInfo() {}
  
  public PayeeRouteInfo(String paramString1, int paramInt1, double paramDouble, String paramString2, int paramInt2, String paramString3, String paramString4, String paramString5, String paramString6)
  {
    this.PayeeID = paramString1;
    this.PayeeType = paramInt1;
    this.PaymentCost = paramDouble;
    this.ExtdPayeeID = paramString2;
    this.RouteID = paramInt2;
    this.BankID = paramString3;
    this.AcctID = paramString4;
    this.AcctType = paramString5;
    this.ExtdInfo = paramString6;
  }
  
  public PayeeRouteInfo(String paramString1, int paramInt1, double paramDouble, String paramString2, int paramInt2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, boolean paramBoolean)
  {
    this.PayeeID = paramString1;
    this.PayeeType = paramInt1;
    this.PaymentCost = paramDouble;
    this.ExtdPayeeID = paramString2;
    this.RouteID = paramInt2;
    this.BankID = paramString3;
    this.AcctID = paramString4;
    this.AcctType = paramString5;
    this.ExtdInfo = paramString6;
    this.CurrencyCode = paramString7;
    this.CustAcctRequired = paramBoolean;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.PayeeRouteInfo
 * JD-Core Version:    0.7.0.1
 */