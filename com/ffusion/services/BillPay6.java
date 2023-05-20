package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.util.beans.PagingContext;
import java.util.HashMap;

public abstract interface BillPay6
  extends BillPay5
{
  public abstract Payments getPagedPayments(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.BillPay6
 * JD-Core Version:    0.7.0.1
 */