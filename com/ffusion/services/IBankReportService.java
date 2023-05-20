package com.ffusion.services;

import com.ffusion.bankreport.BRException;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.bankreport.BankReport;
import com.ffusion.beans.bankreport.BankReportDefinitions;
import com.ffusion.beans.bankreport.BankReports;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.reporting.IReportResult;
import java.io.InputStream;
import java.util.HashMap;

public abstract interface IBankReportService
{
  public static final String SERVICE_INIT_XML = "bankreports.xml";
  
  public abstract void initialize(HashMap paramHashMap)
    throws BRException;
  
  public abstract void processBankReportFile(InputStream paramInputStream, HashMap paramHashMap)
    throws BRException;
  
  public abstract BankReportDefinitions getReportDefinitions(SecureUser paramSecureUser, HashMap paramHashMap)
    throws BRException;
  
  public abstract BankReports getReports(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws BRException;
  
  public abstract Accounts getAccountsForReport(SecureUser paramSecureUser, BankReport paramBankReport, HashMap paramHashMap)
    throws BRException;
  
  public abstract IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws BRException;
  
  public abstract void cleanupOldReports(int paramInt, String paramString, HashMap paramHashMap)
    throws BRException;
  
  public abstract void removeReport(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws BRException;
  
  public abstract void removeAccountData(int paramInt, Account paramAccount, HashMap paramHashMap)
    throws BRException;
  
  public abstract void removeBusinessData(int paramInt, HashMap paramHashMap)
    throws BRException;
  
  public abstract void removeUserData(int paramInt, HashMap paramHashMap)
    throws BRException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.IBankReportService
 * JD-Core Version:    0.7.0.1
 */