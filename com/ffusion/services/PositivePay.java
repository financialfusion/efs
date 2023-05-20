package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.positivepay.PPayAccount;
import com.ffusion.beans.positivepay.PPayAccounts;
import com.ffusion.beans.positivepay.PPayCheckRecords;
import com.ffusion.beans.positivepay.PPayDecisions;
import com.ffusion.beans.positivepay.PPayIssues;
import com.ffusion.beans.positivepay.PPaySummaries;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.positivepay.PPayException;
import com.ffusion.reporting.IReportResult;
import java.util.HashMap;

public abstract interface PositivePay
{
  public static final String SERVICE_INIT_XML = "positivepay.xml";
  public static final String ACCOUNTS = "accounts";
  
  public abstract void initialize()
    throws PPayException;
  
  public abstract PPayAccounts getAccounts(SecureUser paramSecureUser, HashMap paramHashMap)
    throws PPayException;
  
  public abstract PPaySummaries getSummaries(PPayAccounts paramPPayAccounts, HashMap paramHashMap)
    throws PPayException;
  
  public abstract int getNumIssues(PPayAccounts paramPPayAccounts, HashMap paramHashMap)
    throws PPayException;
  
  public abstract PPayIssues getIssuesForAccount(PPayAccount paramPPayAccount, HashMap paramHashMap)
    throws PPayException;
  
  public abstract IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws PPayException;
  
  public abstract void submitDecisions(SecureUser paramSecureUser, PPayDecisions paramPPayDecisions, HashMap paramHashMap)
    throws PPayException;
  
  public abstract void submitCheckRecords(SecureUser paramSecureUser, PPayCheckRecords paramPPayCheckRecords, HashMap paramHashMap)
    throws PPayException;
  
  public abstract void deleteAccount(PPayAccount paramPPayAccount, HashMap paramHashMap)
    throws PPayException;
  
  public abstract void cleanup(int paramInt, HashMap paramHashMap)
    throws PPayException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.PositivePay
 * JD-Core Version:    0.7.0.1
 */