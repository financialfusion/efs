package com.ffusion.services;

import com.ffusion.beans.disbursement.DisbursementAccount;
import com.ffusion.beans.disbursement.DisbursementAccounts;
import com.ffusion.beans.disbursement.DisbursementPresentmentSummaries;
import com.ffusion.beans.disbursement.DisbursementSummaries;
import com.ffusion.beans.disbursement.DisbursementTransactions;
import com.ffusion.services.disbursements.DisbException;
import java.util.Calendar;
import java.util.HashMap;

public abstract interface Disbursements2
  extends Disbursements
{
  public abstract DisbursementTransactions getTransactions(DisbursementAccount paramDisbursementAccount, Calendar paramCalendar1, Calendar paramCalendar2, String paramString, HashMap paramHashMap)
    throws DisbException;
  
  public abstract DisbursementTransactions getPagedTransactions(DisbursementAccount paramDisbursementAccount, Calendar paramCalendar1, Calendar paramCalendar2, String paramString, HashMap paramHashMap)
    throws DisbException;
  
  public abstract DisbursementPresentmentSummaries getPresentmentSummaries(DisbursementAccounts paramDisbursementAccounts, HashMap paramHashMap)
    throws DisbException;
  
  public abstract DisbursementSummaries getSummariesForPresentment(DisbursementAccounts paramDisbursementAccounts, String paramString, HashMap paramHashMap)
    throws DisbException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.Disbursements2
 * JD-Core Version:    0.7.0.1
 */