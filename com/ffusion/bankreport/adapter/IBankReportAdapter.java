package com.ffusion.bankreport.adapter;

import com.ffusion.bankreport.BRException;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.bankreport.BankReport;
import com.ffusion.beans.bankreport.BankReports;
import com.ffusion.beans.bankreport.ReportLineItem;
import com.ffusion.util.beans.DateTime;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Properties;

public abstract interface IBankReportAdapter
{
  public abstract void initialize(Properties paramProperties)
    throws BRException;
  
  public abstract BankReports getReports(int paramInt, String paramString, HashMap paramHashMap)
    throws BRException;
  
  public abstract Accounts getAccountsForReport(int paramInt, HashMap paramHashMap)
    throws BRException;
  
  public abstract void cleanupOldReports(int paramInt, String paramString, HashMap paramHashMap)
    throws BRException;
  
  public abstract void removeReportFile(Connection paramConnection, String paramString, DateTime paramDateTime, HashMap paramHashMap)
    throws BRException;
  
  public abstract void removeReport(int paramInt, HashMap paramHashMap)
    throws BRException;
  
  public abstract void removeAccountData(int paramInt, Account paramAccount, HashMap paramHashMap)
    throws BRException;
  
  public abstract void removeDirectoryData(int paramInt, HashMap paramHashMap)
    throws BRException;
  
  public abstract void removeEmptyReports(Connection paramConnection, int paramInt, HashMap paramHashMap)
    throws BRException;
  
  public abstract Connection getConnection()
    throws BRException;
  
  public abstract void releaseConnection(Connection paramConnection)
    throws BRException;
  
  public abstract void insertBankReport(Connection paramConnection, BankReport paramBankReport, HashMap paramHashMap)
    throws BRException;
  
  public abstract void updateBankReport(Connection paramConnection, BankReport paramBankReport, HashMap paramHashMap)
    throws BRException;
  
  public abstract void insertLineItem(Connection paramConnection, ReportLineItem paramReportLineItem, HashMap paramHashMap)
    throws BRException;
  
  public abstract int getBusinessIdFromAccount(String paramString1, String paramString2, String paramString3)
    throws BRException;
  
  public abstract int[] getBusinessIdsFromAccount(String paramString1, String paramString2, String paramString3)
    throws BRException;
  
  public abstract int getUserIdFromAccount(String paramString1, String paramString2, String paramString3)
    throws BRException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.bankreport.adapter.IBankReportAdapter
 * JD-Core Version:    0.7.0.1
 */