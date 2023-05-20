package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.disbursement.DisbursementAccount;
import com.ffusion.beans.disbursement.DisbursementAccounts;
import com.ffusion.beans.disbursement.DisbursementSummaries;
import com.ffusion.beans.disbursement.DisbursementTransactions;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.reporting.IReportResult;
import com.ffusion.services.disbursements.DisbException;
import java.util.Calendar;
import java.util.HashMap;

public abstract interface Disbursements
{
  public static final String SERVICE_INIT_XML = "disbursements.xml";
  
  public abstract void initialize()
    throws DisbException;
  
  public abstract DisbursementAccounts getAccounts(SecureUser paramSecureUser, HashMap paramHashMap)
    throws DisbException;
  
  public abstract DisbursementSummaries getSummaries(DisbursementAccounts paramDisbursementAccounts, HashMap paramHashMap)
    throws DisbException;
  
  public abstract DisbursementTransactions getTransactions(DisbursementAccount paramDisbursementAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DisbException;
  
  public abstract DisbursementTransactions getPagedTransactions(DisbursementAccount paramDisbursementAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DisbException;
  
  public abstract DisbursementTransactions getRecentTransactions(DisbursementAccount paramDisbursementAccount, HashMap paramHashMap)
    throws DisbException;
  
  public abstract DisbursementTransactions getNextTransactions(DisbursementAccount paramDisbursementAccount, long paramLong, HashMap paramHashMap)
    throws DisbException;
  
  public abstract DisbursementTransactions getPreviousTransactions(DisbursementAccount paramDisbursementAccount, long paramLong, HashMap paramHashMap)
    throws DisbException;
  
  public abstract IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws DisbException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.Disbursements
 * JD-Core Version:    0.7.0.1
 */