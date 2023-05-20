package com.ffusion.webservices.beans;

import java.util.Date;

public class Transaction
  extends Bean
{
  protected String id;
  protected int type;
  protected int category;
  protected String description;
  protected String referenceNumber;
  protected String memo;
  protected Date date;
  protected Currency amount;
  protected Currency runningBalance;
  protected String trackingID;
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String paramString)
  {
    this.id = paramString;
  }
  
  public int getType()
  {
    return this.type;
  }
  
  public void setType(int paramInt)
  {
    this.type = paramInt;
  }
  
  public int getCategory()
  {
    return this.category;
  }
  
  public void setCategory(int paramInt)
  {
    this.category = paramInt;
  }
  
  public String getDescription()
  {
    return this.description;
  }
  
  public void setDescription(String paramString)
  {
    this.description = paramString;
  }
  
  public String getReferenceNumber()
  {
    return this.referenceNumber;
  }
  
  public void setReferenceNumber(String paramString)
  {
    this.referenceNumber = paramString;
  }
  
  public String getMemo()
  {
    return this.memo;
  }
  
  public void setMemo(String paramString)
  {
    this.memo = paramString;
  }
  
  public Date getDate()
  {
    return this.date;
  }
  
  public void setDate(Date paramDate)
  {
    this.date = paramDate;
  }
  
  public Currency getAmount()
  {
    return this.amount;
  }
  
  public void setAmount(Currency paramCurrency)
  {
    this.amount = paramCurrency;
  }
  
  public Currency getRunningBalance()
  {
    return this.runningBalance;
  }
  
  public void setRunningBalance(Currency paramCurrency)
  {
    this.runningBalance = paramCurrency;
  }
  
  public String getTrackingID()
  {
    return this.trackingID;
  }
  
  public void setTrackingID(String paramString)
  {
    this.trackingID = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.webservices.beans.Transaction
 * JD-Core Version:    0.7.0.1
 */