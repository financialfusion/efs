package com.ffusion.webservices.beans;

import java.util.Date;

public class Transfer
  extends Bean
{
  protected String id;
  protected String recid;
  protected Currency amount;
  protected Date date;
  protected String fromAccountID;
  protected String toAccountID;
  protected String referenceNumber;
  protected String memo;
  protected int status;
  protected int error;
  protected String transactionID;
  protected String trackingID;
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String paramString)
  {
    this.id = paramString;
  }
  
  public String getRecid()
  {
    return this.recid;
  }
  
  public void setRecid(String paramString)
  {
    this.recid = paramString;
  }
  
  public Currency getAmount()
  {
    return this.amount;
  }
  
  public void setAmount(Currency paramCurrency)
  {
    this.amount = paramCurrency;
  }
  
  public Date getDate()
  {
    return this.date;
  }
  
  public void setDate(Date paramDate)
  {
    this.date = paramDate;
  }
  
  public String getFromAccountID()
  {
    return this.fromAccountID;
  }
  
  public void setFromAccountID(String paramString)
  {
    this.fromAccountID = paramString;
  }
  
  public String getToAccountID()
  {
    return this.toAccountID;
  }
  
  public void setToAccountID(String paramString)
  {
    this.toAccountID = paramString;
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
  
  public int getStatus()
  {
    return this.status;
  }
  
  public void setStatus(int paramInt)
  {
    this.status = paramInt;
  }
  
  public int getError()
  {
    return this.error;
  }
  
  public void setError(int paramInt)
  {
    this.error = paramInt;
  }
  
  public String getTransactionID()
  {
    return this.transactionID;
  }
  
  public void setTransactionID(String paramString)
  {
    this.transactionID = paramString;
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
 * Qualified Name:     com.ffusion.webservices.beans.Transfer
 * JD-Core Version:    0.7.0.1
 */