package com.ffusion.webservices.beans;

import java.util.Date;

public class Payment
  extends Bean
{
  protected String id;
  protected String recid;
  protected String payeeID;
  protected String payeeName;
  protected Currency amount;
  protected Date payDate;
  protected Date deliverByDate;
  protected String accountID;
  protected String confirmationNum;
  protected String referenceNumber;
  protected String memo;
  protected int status;
  protected int error;
  protected String transactionID;
  protected String trackingID;
  protected String paymentType = "PAYMENT";
  protected String customerID;
  protected String FIID;
  
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
  
  public String getPayeeID()
  {
    return this.payeeID;
  }
  
  public void setPayeeID(String paramString)
  {
    this.payeeID = paramString;
  }
  
  public String getPayeeName()
  {
    return this.payeeName;
  }
  
  public void setPayeeName(String paramString)
  {
    this.payeeName = paramString;
  }
  
  public Currency getAmount()
  {
    return this.amount;
  }
  
  public void setAmount(Currency paramCurrency)
  {
    this.amount = paramCurrency;
  }
  
  public Date getPayDate()
  {
    return this.payDate;
  }
  
  public void setPayDate(Date paramDate)
  {
    this.payDate = paramDate;
  }
  
  public Date getDeliverByDate()
  {
    return this.deliverByDate;
  }
  
  public void setDeliverByDate(Date paramDate)
  {
    this.deliverByDate = paramDate;
  }
  
  public String getAccountID()
  {
    return this.accountID;
  }
  
  public void setAccountID(String paramString)
  {
    this.accountID = paramString;
  }
  
  public String getConfirmationNum()
  {
    return this.confirmationNum;
  }
  
  public void setConfirmationNum(String paramString)
  {
    this.confirmationNum = paramString;
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
  
  public void setPaymentType(String paramString)
  {
    this.paymentType = paramString;
  }
  
  public String getPaymentType()
  {
    return this.paymentType;
  }
  
  public void setCustomerID(String paramString)
  {
    this.customerID = paramString;
  }
  
  public String getCustomerID()
  {
    return this.customerID;
  }
  
  public void setFIID(String paramString)
  {
    this.FIID = paramString;
  }
  
  public String getFIID()
  {
    return this.FIID;
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
 * Qualified Name:     com.ffusion.webservices.beans.Payment
 * JD-Core Version:    0.7.0.1
 */