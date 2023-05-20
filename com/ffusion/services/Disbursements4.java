package com.ffusion.services;

import com.ffusion.beans.disbursement.DisbursementAccount;
import com.ffusion.beans.disbursement.DisbursementTransactions;
import com.ffusion.services.disbursements.DisbException;
import com.ffusion.util.beans.PagingContext;
import java.util.HashMap;

public abstract interface Disbursements4
  extends Disbursements3
{
  public abstract DisbursementTransactions getPagedTransactions(DisbursementAccount paramDisbursementAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws DisbException;
  
  public abstract DisbursementTransactions getNextTransactions(DisbursementAccount paramDisbursementAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws DisbException;
  
  public abstract DisbursementTransactions getPreviousTransactions(DisbursementAccount paramDisbursementAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws DisbException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.Disbursements4
 * JD-Core Version:    0.7.0.1
 */