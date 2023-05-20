package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.beans.PagingContext;
import java.util.HashMap;

public abstract interface BillPay3
  extends BillPay2
{
  public abstract Payments getPagedPendingPayments(SecureUser paramSecureUser, Accounts paramAccounts, Payees paramPayees, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception;
  
  public abstract Payments getNextPendingPayments(SecureUser paramSecureUser, Accounts paramAccounts, Payees paramPayees, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception;
  
  public abstract Payments getPreviousPendingPayments(SecureUser paramSecureUser, Accounts paramAccounts, Payees paramPayees, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception;
  
  public abstract Payments getPagedCompletedPayments(SecureUser paramSecureUser, Accounts paramAccounts, Payees paramPayees, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception;
  
  public abstract Payments getNextCompletedPayments(SecureUser paramSecureUser, Accounts paramAccounts, Payees paramPayees, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception;
  
  public abstract Payments getPreviousCompletedPayments(SecureUser paramSecureUser, Accounts paramAccounts, Payees paramPayees, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception;
  
  public abstract Payees getGlobalPayees(SecureUser paramSecureUser, HashMap paramHashMap)
    throws Exception;
  
  public abstract IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws Exception;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.BillPay3
 * JD-Core Version:    0.7.0.1
 */