package com.ffusion.beans.billpay;

import com.ffusion.beans.Currency;
import com.ffusion.beans.FundsTransaction;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.Strings;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.InvalidDateTimeException;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.AuditLogRecord;
import com.ffusion.util.logging.AuditLogUtil;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Locale;

public class PaymentBatch
  extends FundsTransaction
{
  protected String fiID;
  protected int customerID;
  protected String batchType;
  protected int paymentCount;
  protected com.ffusion.util.beans.DateTime submitDate;
  protected String status;
  protected String logID;
  protected Payments payments;
  protected int error;
  protected static final String BEAN_NAME = PaymentBatch.class.getName();
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.payments != null)
    {
      Iterator localIterator = this.payments.iterator();
      while (localIterator.hasNext())
      {
        Payment localPayment = (Payment)localIterator.next();
        localPayment.setDateFormat(paramString);
      }
    }
  }
  
  public int getErrorValue()
  {
    return this.error;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
  
  public void setError(int paramInt)
  {
    this.error = paramInt;
  }
  
  public PaymentBatch() {}
  
  public PaymentBatch(String paramString)
  {
    super(paramString);
  }
  
  public PaymentBatch(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public String getFIID()
  {
    return this.fiID;
  }
  
  public void setFIID(String paramString)
  {
    this.fiID = paramString;
  }
  
  public String getCustomerID()
  {
    return String.valueOf(this.customerID);
  }
  
  public void setCustomerID(String paramString)
  {
    try
    {
      this.customerID = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public void setCustomerID(int paramInt)
  {
    this.customerID = paramInt;
  }
  
  public String getBatchType()
  {
    return this.batchType;
  }
  
  public void setBatchType(String paramString)
  {
    this.batchType = paramString;
  }
  
  public int getPaymentCountValue()
  {
    return this.paymentCount;
  }
  
  public String getPaymentCount()
  {
    return String.valueOf(this.paymentCount);
  }
  
  public void setPaymentCount(int paramInt)
  {
    this.paymentCount = paramInt;
  }
  
  public void setPaymentCount(String paramString)
  {
    try
    {
      this.paymentCount = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public String getSubmitDate()
  {
    if (this.submitDate != null) {
      return this.submitDate.toString();
    }
    return "";
  }
  
  public com.ffusion.util.beans.DateTime getSubmitDateValue()
  {
    return this.submitDate;
  }
  
  public void setSubmitDate(com.ffusion.util.beans.DateTime paramDateTime)
  {
    this.submitDate = paramDateTime;
  }
  
  public void setSubmitDate(String paramString)
  {
    try
    {
      if (this.submitDate == null) {
        this.submitDate = new com.ffusion.beans.DateTime(paramString, this.locale, this.datetype);
      } else {
        this.submitDate.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String paramString)
  {
    this.status = paramString;
  }
  
  public String getLogID()
  {
    return this.logID;
  }
  
  public void setLogID(String paramString)
  {
    this.logID = paramString;
  }
  
  public Payments getPayments()
  {
    return this.payments;
  }
  
  public void setPayments(Payments paramPayments)
  {
    this.payments = paramPayments;
  }
  
  public AuditLogRecord getAuditRecord(SecureUser paramSecureUser, String paramString1, int paramInt, String paramString2)
  {
    return getAuditRecord(paramSecureUser, new LocalizableString("dummy", paramString1, null), paramInt, paramString2);
  }
  
  public AuditLogRecord getAuditRecord(SecureUser paramSecureUser, ILocalizable paramILocalizable, int paramInt, String paramString)
  {
    String str1 = "";
    String str2 = null;
    String str3 = null;
    int i = 0;
    if (paramSecureUser.getUserType() == 2)
    {
      str1 = "";
      str2 = String.valueOf(paramSecureUser.getProfileID());
      str3 = String.valueOf(paramSecureUser.getUserType());
    }
    else
    {
      str1 = String.valueOf(paramSecureUser.getProfileID());
      if (paramSecureUser.getAgent() != null) {
        if (paramSecureUser.getAgent().getProfileID() > 0)
        {
          str2 = String.valueOf(paramSecureUser.getAgent().getProfileID());
          str3 = String.valueOf(paramSecureUser.getAgent().getUserType());
        }
        else
        {
          str2 = paramSecureUser.getAgent().getUserName();
        }
      }
      i = paramSecureUser.getPrimaryUserID();
    }
    String str4 = "";
    String str5 = "";
    if ((getPayments() != null) && (getPayments().size() > 0))
    {
      localObject = ((Payment)getPayments().get(0)).getAccount();
      if (localObject != null)
      {
        str4 = ((Account)localObject).getRoutingNum();
        str5 = ((Account)localObject).getID();
      }
    }
    Object localObject = null;
    String str6 = null;
    if (getAmountValue() != null)
    {
      localObject = getAmountValue().getAmountValue();
      str6 = getAmountValue().getCurrencyCode();
    }
    AuditLogRecord localAuditLogRecord = new AuditLogRecord(str1, i, str2, str3, paramILocalizable, getTrackingID(), paramInt, paramSecureUser.getBusinessID(), (BigDecimal)localObject, str6, getID(), paramString, null, null, str5, str4, AuditLogUtil.getModuleFromTranType(paramInt));
    return localAuditLogRecord;
  }
  
  public void set(PaymentBatch paramPaymentBatch)
  {
    super.set(paramPaymentBatch);
    this.fiID = paramPaymentBatch.getFIID();
    setCustomerID(paramPaymentBatch.getCustomerID());
    this.batchType = paramPaymentBatch.getBatchType();
    this.paymentCount = paramPaymentBatch.getPaymentCountValue();
    this.submitDate = paramPaymentBatch.getSubmitDateValue();
    this.status = paramPaymentBatch.getStatus();
    this.logID = paramPaymentBatch.getLogID();
    this.payments = new Payments(paramPaymentBatch.getPayments().getLocale());
    for (int i = 0; i < paramPaymentBatch.getPayments().size(); i++)
    {
      Payment localPayment1 = (Payment)paramPaymentBatch.getPayments().get(i);
      Payment localPayment2 = (Payment)this.payments.create();
      localPayment2.set(localPayment1);
    }
  }
  
  public int compare(Object paramObject, String paramString)
  {
    PaymentBatch localPaymentBatch = (PaymentBatch)paramObject;
    int i = 1;
    if (paramString.equals("TYPE")) {
      i = Strings.compareTo(localPaymentBatch.getBatchType(), this.batchType);
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if (paramString1.equals("TYPE")) {
      return isFilterable(this.batchType, paramString2, paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendTag(localStringBuffer, "BANK_ID", this.fiID);
    XMLHandler.appendTag(localStringBuffer, "CUSTOMER_ID", this.customerID);
    XMLHandler.appendTag(localStringBuffer, "BATCH_TYPE", this.batchType);
    XMLHandler.appendTag(localStringBuffer, "PAYMENTCOUNT", this.paymentCount);
    if (this.submitDate != null) {
      XMLHandler.appendTag(localStringBuffer, "SUBMITDATE", this.submitDate.toXMLFormat());
    }
    XMLHandler.appendTag(localStringBuffer, "STATUS", this.status);
    localStringBuffer.append(super.getXML());
    XMLHandler.appendBeginTag(localStringBuffer, "PAYMENTS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((Payment)this.payments.get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "PAYMENTS");
    return localStringBuffer.toString();
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, String paramString, PaymentBatch paramPaymentBatch, ILocalizable paramILocalizable)
  {
    if (!BEAN_NAME.equals(paramString)) {
      paramString = paramString + "," + BEAN_NAME;
    }
    if ((getPayments() != null) && (paramPaymentBatch.getPayments() != null))
    {
      Payment localPayment1;
      Payment localPayment2;
      for (int i = 0; i < paramPaymentBatch.getPayments().size(); i++)
      {
        localPayment1 = (Payment)paramPaymentBatch.getPayments().get(i);
        localPayment2 = this.payments.getByID(localPayment1.getID());
        if (localPayment2 == null) {
          localPayment1.logDeletion(paramHistoryTracker, paramILocalizable);
        }
      }
      for (i = 0; i < getPayments().size(); i++)
      {
        localPayment1 = (Payment)getPayments().get(i);
        localPayment2 = paramPaymentBatch.getPayments().getByID(localPayment1.getID());
        if (localPayment2 == null) {
          localPayment1.logCreation(paramHistoryTracker, paramILocalizable);
        } else {
          localPayment1.logChanges(paramHistoryTracker, paramString, localPayment2, paramILocalizable);
        }
      }
    }
    super.logChanges(paramHistoryTracker, paramString, paramPaymentBatch, paramILocalizable);
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, PaymentBatch paramPaymentBatch, ILocalizable paramILocalizable)
  {
    logChanges(paramHistoryTracker, BEAN_NAME, paramPaymentBatch, paramILocalizable);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.billpay.PaymentBatch
 * JD-Core Version:    0.7.0.1
 */