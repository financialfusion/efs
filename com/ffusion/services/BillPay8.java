package com.ffusion.services;

import com.ffusion.beans.FundsTransactions;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.billpay.PaymentBatch;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.util.beans.PagingContext;
import java.util.HashMap;

public abstract interface BillPay8
  extends BillPay7
{
  public abstract Payments getLastPayments(SecureUser paramSecureUser, Payees paramPayees, HashMap paramHashMap)
    throws Exception;
  
  public abstract PaymentBatch getPaymentBatchByID(SecureUser paramSecureUser, PaymentBatch paramPaymentBatch, HashMap paramHashMap)
    throws Exception;
  
  public abstract FundsTransactions getAllPaymentTemplates(SecureUser paramSecureUser, Payment paramPayment, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception;
  
  public abstract Payment addPaymentTemplate(SecureUser paramSecureUser, Payment paramPayment, HashMap paramHashMap)
    throws Exception;
  
  public abstract Payment modifyPaymentTemplate(SecureUser paramSecureUser, Payment paramPayment, HashMap paramHashMap)
    throws Exception;
  
  public abstract void deletePaymentTemplate(SecureUser paramSecureUser, Payment paramPayment, HashMap paramHashMap)
    throws Exception;
  
  public abstract void addPaymentBatch(SecureUser paramSecureUser, PaymentBatch paramPaymentBatch, HashMap paramHashMap)
    throws Exception;
  
  public abstract void cancelPaymentBatch(SecureUser paramSecureUser, PaymentBatch paramPaymentBatch, HashMap paramHashMap)
    throws Exception;
  
  public abstract PaymentBatch modifyPaymentBatch(SecureUser paramSecureUser, PaymentBatch paramPaymentBatch, HashMap paramHashMap)
    throws Exception;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.BillPay8
 * JD-Core Version:    0.7.0.1
 */