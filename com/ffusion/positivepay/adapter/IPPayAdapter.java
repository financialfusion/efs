package com.ffusion.positivepay.adapter;

import com.ffusion.beans.positivepay.PPayAccount;
import com.ffusion.beans.positivepay.PPayAccounts;
import com.ffusion.beans.positivepay.PPayDecisions;
import com.ffusion.beans.positivepay.PPayIssue;
import com.ffusion.beans.positivepay.PPayIssues;
import com.ffusion.beans.positivepay.PPaySummaries;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.positivepay.PPayException;
import com.ffusion.reporting.IReportResult;
import java.util.HashMap;

public abstract interface IPPayAdapter
{
  public abstract void initialize(HashMap paramHashMap)
    throws PPayException;
  
  public abstract PPaySummaries getSummaries(PPayAccounts paramPPayAccounts, HashMap paramHashMap)
    throws PPayException;
  
  public abstract int getNumIssues(PPayAccounts paramPPayAccounts, HashMap paramHashMap)
    throws PPayException;
  
  public abstract PPayIssues getIssuesForAccount(PPayAccount paramPPayAccount, HashMap paramHashMap)
    throws PPayException;
  
  public abstract void insertIssues(PPayIssues paramPPayIssues, HashMap paramHashMap)
    throws PPayException;
  
  public abstract void insertIssue(PPayIssue paramPPayIssue, HashMap paramHashMap)
    throws PPayException;
  
  public abstract IReportResult getReportData(ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws PPayException;
  
  public abstract IReportResult getBCReportData(ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws PPayException;
  
  public abstract void submitDecisions(PPayDecisions paramPPayDecisions, HashMap paramHashMap)
    throws PPayException;
  
  public abstract void deleteAccount(PPayAccount paramPPayAccount, HashMap paramHashMap)
    throws PPayException;
  
  public abstract void cleanup(int paramInt, HashMap paramHashMap)
    throws PPayException;
  
  public abstract void submitDefaultDecisions(PPayAccounts paramPPayAccounts, String paramString, HashMap paramHashMap)
    throws PPayException;
  
  public abstract void cleanup(PPayAccounts paramPPayAccounts, int paramInt, HashMap paramHashMap)
    throws PPayException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.positivepay.adapter.IPPayAdapter
 * JD-Core Version:    0.7.0.1
 */