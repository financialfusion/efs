package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.lockbox.LockboxAccount;
import com.ffusion.beans.lockbox.LockboxAccounts;
import com.ffusion.beans.lockbox.LockboxCreditItems;
import com.ffusion.beans.lockbox.LockboxSummaries;
import com.ffusion.beans.lockbox.LockboxTransactions;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.reporting.IReportResult;
import com.ffusion.services.lockbox.LBoxException;
import java.util.Calendar;
import java.util.HashMap;

public abstract interface Lockbox
{
  public static final String SERVICE_INIT_XML = "lockbox.xml";
  
  public abstract void initialize()
    throws LBoxException;
  
  public abstract LockboxAccounts getLockboxAccounts(SecureUser paramSecureUser, HashMap paramHashMap)
    throws LBoxException;
  
  public abstract LockboxSummaries getSummaries(LockboxAccounts paramLockboxAccounts, HashMap paramHashMap)
    throws LBoxException;
  
  public abstract LockboxTransactions getTransactions(LockboxAccount paramLockboxAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws LBoxException;
  
  public abstract LockboxTransactions getPagedTransactions(LockboxAccount paramLockboxAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws LBoxException;
  
  public abstract LockboxTransactions getRecentTransactions(LockboxAccount paramLockboxAccount, HashMap paramHashMap)
    throws LBoxException;
  
  public abstract LockboxTransactions getNextTransactions(LockboxAccount paramLockboxAccount, long paramLong, HashMap paramHashMap)
    throws LBoxException;
  
  public abstract LockboxTransactions getPreviousTransactions(LockboxAccount paramLockboxAccount, long paramLong, HashMap paramHashMap)
    throws LBoxException;
  
  public abstract LockboxCreditItems getCreditItems(LockboxAccount paramLockboxAccount, String paramString, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws LBoxException;
  
  public abstract LockboxCreditItems getPagedCreditItems(LockboxAccount paramLockboxAccount, String paramString, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws LBoxException;
  
  public abstract LockboxCreditItems getRecentCreditItems(LockboxAccount paramLockboxAccount, String paramString, HashMap paramHashMap)
    throws LBoxException;
  
  public abstract LockboxCreditItems getNextCreditItems(LockboxAccount paramLockboxAccount, String paramString, long paramLong, HashMap paramHashMap)
    throws LBoxException;
  
  public abstract LockboxCreditItems getPreviousCreditItems(LockboxAccount paramLockboxAccount, String paramString, long paramLong, HashMap paramHashMap)
    throws LBoxException;
  
  public abstract IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws LBoxException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.Lockbox
 * JD-Core Version:    0.7.0.1
 */