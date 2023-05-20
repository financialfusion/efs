package com.ffusion.beans.billpay;

import com.ffusion.util.beans.ExtendABean;

public class PayeeRoute
  extends ExtendABean
{
  protected static final String BEAN_NAME = PayeeRoute.class.getName();
  protected String routeID;
  protected String bankIdentifier;
  protected String accountID;
  protected String acctType;
  protected String currencyCode;
  protected boolean custAcctRequired;
  
  public String getRouteID()
  {
    return this.routeID;
  }
  
  public void setRouteID(String paramString)
  {
    this.routeID = paramString;
  }
  
  public String getAccountID()
  {
    return this.accountID;
  }
  
  public void setAccountID(String paramString)
  {
    this.accountID = paramString;
  }
  
  public String getAcctType()
  {
    return this.acctType;
  }
  
  public void setAcctType(String paramString)
  {
    this.acctType = paramString;
  }
  
  public String getCurrencyCode()
  {
    return this.currencyCode;
  }
  
  public void setCurrencyCode(String paramString)
  {
    this.currencyCode = paramString;
  }
  
  public void setCustAcctRequired(boolean paramBoolean)
  {
    this.custAcctRequired = paramBoolean;
  }
  
  public boolean isCustAcctRequired()
  {
    return this.custAcctRequired;
  }
  
  public String getCustAcctRequired()
  {
    String str = null;
    if (this.custAcctRequired == true) {
      str = "true";
    } else {
      str = "false";
    }
    return str;
  }
  
  public String getBankIdentifier()
  {
    return this.bankIdentifier;
  }
  
  public void setBankIdentifier(String paramString)
  {
    this.bankIdentifier = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.billpay.PayeeRoute
 * JD-Core Version:    0.7.0.1
 */