package com.ffusion.services;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.istatements.IStatementException;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.FilteredList;
import java.util.HashMap;

public abstract interface StatementData2
  extends StatementData
{
  public abstract String getStatementID(SecureUser paramSecureUser, String paramString, DateTime paramDateTime, HashMap paramHashMap)
    throws IStatementException;
  
  public abstract FilteredList getStatementDates(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws IStatementException;
  
  public abstract IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws IStatementException;
  
  public abstract void addAccountsForIStatement(SecureUser paramSecureUser, String paramString, Accounts paramAccounts, HashMap paramHashMap)
    throws IStatementException;
  
  public abstract void removeAccountsForIStatement(SecureUser paramSecureUser, String paramString, Accounts paramAccounts, HashMap paramHashMap)
    throws IStatementException;
  
  public abstract Accounts getAccountsForIStatement(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws IStatementException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.StatementData2
 * JD-Core Version:    0.7.0.1
 */