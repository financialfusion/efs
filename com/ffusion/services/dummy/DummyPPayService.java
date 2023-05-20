package com.ffusion.services.dummy;

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
import com.ffusion.services.PositivePay2;
import java.util.HashMap;

public class DummyPPayService
  implements PositivePay2
{
  public void initialize()
    throws PPayException
  {}
  
  public PPayAccounts getAccounts(SecureUser paramSecureUser, HashMap paramHashMap)
    throws PPayException
  {
    return null;
  }
  
  public synchronized PPaySummaries getSummaries(PPayAccounts paramPPayAccounts, HashMap paramHashMap)
    throws PPayException
  {
    return null;
  }
  
  public synchronized int getNumIssues(PPayAccounts paramPPayAccounts, HashMap paramHashMap)
    throws PPayException
  {
    return 0;
  }
  
  public IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws PPayException
  {
    return null;
  }
  
  public PPayIssues getIssuesForAccount(PPayAccount paramPPayAccount, HashMap paramHashMap)
    throws PPayException
  {
    return null;
  }
  
  public void submitDecisions(SecureUser paramSecureUser, PPayDecisions paramPPayDecisions, HashMap paramHashMap)
    throws PPayException
  {}
  
  public void submitCheckRecords(SecureUser paramSecureUser, PPayCheckRecords paramPPayCheckRecords, HashMap paramHashMap)
    throws PPayException
  {}
  
  public void deleteAccount(PPayAccount paramPPayAccount, HashMap paramHashMap)
    throws PPayException
  {}
  
  public void cleanup(int paramInt, HashMap paramHashMap)
    throws PPayException
  {}
  
  private boolean jdMethod_if(String paramString1, String paramString2, String paramString3)
  {
    return false;
  }
  
  public void submitDefaultDecisions(int paramInt, HashMap paramHashMap)
    throws PPayException
  {}
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.dummy.DummyPPayService
 * JD-Core Version:    0.7.0.1
 */