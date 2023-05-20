package com.ffusion.services;

import com.ffusion.beans.disbursement.DisbursementAccounts;
import com.ffusion.beans.disbursement.DisbursementPresentmentSummaries;
import com.ffusion.beans.disbursement.DisbursementSummaries;
import com.ffusion.services.disbursements.DisbException;
import java.util.Calendar;
import java.util.HashMap;

public abstract interface Disbursements3
  extends Disbursements2
{
  public abstract DisbursementSummaries getSummaries(DisbursementAccounts paramDisbursementAccounts, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DisbException;
  
  public abstract DisbursementPresentmentSummaries getPresentmentSummaries(DisbursementAccounts paramDisbursementAccounts, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DisbException;
  
  public abstract DisbursementSummaries getSummariesForPresentment(DisbursementAccounts paramDisbursementAccounts, String paramString, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DisbException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.Disbursements3
 * JD-Core Version:    0.7.0.1
 */