package com.ffusion.csil.ejb;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.AccountHistories;
import com.ffusion.beans.accounts.AccountSummaries;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.alerts.UserAlerts;
import com.ffusion.beans.approvals.ApprovalsItemCounts;
import com.ffusion.beans.dataconsolidator.BAITypeCodeInfo;
import com.ffusion.beans.messages.Message;
import com.ffusion.beans.portal.Stock;
import com.ffusion.beans.portal.StockIndexes;
import com.ffusion.beans.portal.StockSymbols;
import com.ffusion.beans.portal.Stocks;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.beans.BankIdentifier;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import javax.ejb.EJBException;
import javax.ejb.EJBObject;

public abstract interface ICSILEJB
  extends EJBObject
{
  public abstract void processFIFiles(String paramString, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException;
  
  public abstract void cleanupFIFiles(String paramString1, String paramString2, int paramInt, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException;
  
  public abstract void cleanupPPay(int paramInt, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException;
  
  public abstract void cleanupDC(String paramString, int paramInt, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException;
  
  public abstract void cleanupEntitlements(int paramInt1, int paramInt2, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException;
  
  public abstract int cleanupCacheChanges(long paramLong)
    throws EJBException, RemoteException, CSILException;
  
  public abstract int getNumIssues(SecureUser paramSecureUser, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException;
  
  public abstract int getNumberOfPendingApprovals(SecureUser paramSecureUser, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException;
  
  public abstract ApprovalsItemCounts getNumberOfPendingApprovalsDetail(SecureUser paramSecureUser, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException;
  
  public abstract void cleanupApprovals(int paramInt, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException;
  
  public abstract void cleanupForeignExchange(int paramInt, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException;
  
  public abstract void cleanupBankReport(int paramInt, String paramString, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException;
  
  public abstract String getAdditionalData(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException;
  
  public abstract AccountHistories getAccountHistory(SecureUser paramSecureUser, Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException;
  
  public abstract AccountSummaries getAccountSummary(SecureUser paramSecureUser, Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException;
  
  public abstract IReportResult getAccountReportData(ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException;
  
  public abstract Accounts getAccountsByBusinessEmployee(SecureUser paramSecureUser, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException;
  
  public abstract Account isAccountEntitledNoCache(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException;
  
  public abstract Accounts getEntitledAccountsByBusinessEmployee(SecureUser paramSecureUser, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException;
  
  public abstract Message sendMessage(SecureUser paramSecureUser, Message paramMessage, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException;
  
  public abstract Stock getStock(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException;
  
  public abstract Stock getStock(SecureUser paramSecureUser, String paramString, boolean paramBoolean, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException;
  
  public abstract Stocks getStocks(SecureUser paramSecureUser, Stocks paramStocks, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException;
  
  public abstract StockIndexes getStockIndexes(SecureUser paramSecureUser, StockIndexes paramStockIndexes, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException;
  
  public abstract StockSymbols getStockSymbols(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException;
  
  public abstract int getNumberOfUnreadMessages(SecureUser paramSecureUser, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException;
  
  public abstract int getNumberOfUnreadMessagesExcludingAlerts(SecureUser paramSecureUser, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException;
  
  public abstract void cleanupFM(String paramString, int paramInt, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException;
  
  public abstract boolean isBankingDay(SecureUser paramSecureUser, BankIdentifier paramBankIdentifier, Date paramDate, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException;
  
  public abstract void processGlobalMessages()
    throws EJBException, RemoteException, CSILException;
  
  public abstract boolean isAccountEntitled(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException;
  
  public abstract boolean allowDuplicateUserNames()
    throws EJBException, RemoteException, CSILException;
  
  public abstract void submitDefaultDecisions(int paramInt, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException;
  
  public abstract void cleanupPayments(HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException;
  
  public abstract BAITypeCodeInfo getBAITypeCodeInfo(int paramInt)
    throws RemoteException, EJBException, CSILException;
  
  public abstract boolean checkEntitlement(EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement)
    throws RemoteException, EJBException, CSILException;
  
  public abstract boolean checkEntitlement(int paramInt, Entitlement paramEntitlement)
    throws RemoteException, EJBException, CSILException;
  
  public abstract UserAlerts getPagedUserAlerts(int paramInt1, int paramInt2, int paramInt3, HashMap paramHashMap)
    throws RemoteException, EJBException, CSILException;
  
  public abstract ArrayList getAlertsForAccounts(Accounts paramAccounts, HashMap paramHashMap)
    throws RemoteException, EJBException, CSILException;
  
  public abstract void modifyUserAlerts(UserAlerts paramUserAlerts, HashMap paramHashMap)
    throws RemoteException, EJBException, CSILException;
  
  public abstract Accounts fillAccountCollection(Accounts paramAccounts, HashMap paramHashMap)
    throws RemoteException, EJBException, CSILException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.ejb.ICSILEJB
 * JD-Core Version:    0.7.0.1
 */