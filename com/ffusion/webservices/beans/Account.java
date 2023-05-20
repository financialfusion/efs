package com.ffusion.webservices.beans;

public class Account
  extends Bean
{
  protected String id;
  protected String number;
  protected String bankID;
  protected String nickName;
  protected int type;
  protected int status;
  protected Balance currentBalance;
  protected Balance availableBalance;
  protected String bankName;
  protected Balance closingBalance;
  protected String coreAccount;
  protected String currencyCode;
  protected String routingNum;
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String paramString)
  {
    this.id = paramString;
  }
  
  public String getNumber()
  {
    return this.number;
  }
  
  public void setNumber(String paramString)
  {
    this.number = paramString;
  }
  
  public String getBankID()
  {
    return this.bankID;
  }
  
  public void setBankID(String paramString)
  {
    this.bankID = paramString;
  }
  
  public String getNickName()
  {
    return this.nickName;
  }
  
  public void setNickName(String paramString)
  {
    this.nickName = paramString;
  }
  
  public int getType()
  {
    return this.type;
  }
  
  public void setType(int paramInt)
  {
    this.type = paramInt;
  }
  
  public int getStatus()
  {
    return this.status;
  }
  
  public void setStatus(int paramInt)
  {
    this.status = paramInt;
  }
  
  public Balance getCurrentBalance()
  {
    return this.currentBalance;
  }
  
  public void setCurrentBalance(Balance paramBalance)
  {
    this.currentBalance = paramBalance;
  }
  
  public Balance getAvailableBalance()
  {
    return this.availableBalance;
  }
  
  public void setAvailableBalance(Balance paramBalance)
  {
    this.availableBalance = paramBalance;
  }
  
  public String getBankName()
  {
    return this.bankName;
  }
  
  public void setBankName(String paramString)
  {
    this.bankName = paramString;
  }
  
  public Balance getClosingBalance()
  {
    return this.closingBalance;
  }
  
  public void setClosingBalance(Balance paramBalance)
  {
    this.closingBalance = paramBalance;
  }
  
  public String getCoreAccount()
  {
    return this.coreAccount;
  }
  
  public void setCoreAccount(String paramString)
  {
    this.coreAccount = paramString;
  }
  
  public String getCurrencyCode()
  {
    return this.currencyCode;
  }
  
  public void setCurrencyCode(String paramString)
  {
    this.currencyCode = paramString;
  }
  
  public String getRoutingNumber()
  {
    return this.routingNum;
  }
  
  public void setRoutingNumber(String paramString)
  {
    this.routingNum = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.webservices.beans.Account
 * JD-Core Version:    0.7.0.1
 */