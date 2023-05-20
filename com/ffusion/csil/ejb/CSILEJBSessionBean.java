package com.ffusion.csil.ejb;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.AccountHistories;
import com.ffusion.beans.accounts.AccountSummaries;
import com.ffusion.beans.alerts.UserAlert;
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
import com.ffusion.csil.beans.entitlements.MultiEntitlement;
import com.ffusion.csil.core.Alerts;
import com.ffusion.csil.core.Approvals;
import com.ffusion.csil.core.BankReport;
import com.ffusion.csil.core.Banking;
import com.ffusion.csil.core.DataConsolidator;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.csil.core.FX;
import com.ffusion.csil.core.FileImporter;
import com.ffusion.csil.core.FileMonitor;
import com.ffusion.csil.core.Initialize;
import com.ffusion.csil.core.Messages;
import com.ffusion.csil.core.PaymentsAdmin;
import com.ffusion.csil.core.Portal;
import com.ffusion.csil.core.PositivePay;
import com.ffusion.csil.core.SmartCalendar;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.efs.adapters.profile.SignonSettings;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.beans.BankIdentifier;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class CSILEJBSessionBean
  implements SessionBean
{
  public void ejbCreate()
    throws CreateException, EJBException
  {
    Class localClass = Initialize.class;
  }
  
  public void ejbActivate()
    throws EJBException, RemoteException
  {}
  
  public void ejbPassivate()
    throws EJBException, RemoteException
  {}
  
  public void ejbRemove()
    throws EJBException, RemoteException
  {}
  
  public void setSessionContext(SessionContext paramSessionContext)
    throws EJBException, RemoteException
  {}
  
  public void processFIFiles(String paramString, HashMap paramHashMap)
    throws EJBException, CSILException, RemoteException
  {
    FileImporter.processFiles(paramString, paramHashMap);
  }
  
  public void cleanupFIFiles(String paramString1, String paramString2, int paramInt, HashMap paramHashMap)
    throws EJBException, CSILException, RemoteException
  {
    FileImporter.cleanup(paramString1, paramString2, paramInt, paramHashMap);
  }
  
  public void cleanupPPay(int paramInt, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException
  {
    PositivePay.cleanup(paramInt, paramHashMap);
  }
  
  public void cleanupDC(String paramString, int paramInt, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException
  {
    DataConsolidator.cleanup(paramString, paramInt, paramHashMap);
  }
  
  public void cleanupEntitlements(int paramInt1, int paramInt2, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException
  {
    Entitlements.cleanup(paramInt1, paramInt2, paramHashMap);
  }
  
  public int cleanupCacheChanges(long paramLong)
    throws EJBException, RemoteException, CSILException
  {
    return Entitlements.cleanupCacheChanges(paramLong);
  }
  
  public int getNumIssues(SecureUser paramSecureUser, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException
  {
    return PositivePay.getNumIssues(paramSecureUser, paramHashMap);
  }
  
  public int getNumberOfPendingApprovals(SecureUser paramSecureUser, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException
  {
    return Approvals.getNumberOfPendingApprovals(paramSecureUser, paramHashMap);
  }
  
  public ApprovalsItemCounts getNumberOfPendingApprovalsDetail(SecureUser paramSecureUser, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException
  {
    return Approvals.getNumberOfPendingApprovalsDetail(paramSecureUser, paramHashMap);
  }
  
  public void cleanupApprovals(int paramInt, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException
  {
    Approvals.cleanup(paramInt, paramHashMap);
  }
  
  public void cleanupForeignExchange(int paramInt, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException
  {
    FX.cleanup(paramInt, paramHashMap);
  }
  
  public void cleanupBankReport(int paramInt, String paramString, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException
  {
    BankReport.cleanupOldReports(paramInt, paramString, paramHashMap);
  }
  
  public String getAdditionalData(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException
  {
    return UserAdmin.getAdditionalData(paramSecureUser, paramString, paramHashMap);
  }
  
  public AccountHistories getAccountHistory(SecureUser paramSecureUser, Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException
  {
    return DataConsolidator.getHistory(paramSecureUser, paramAccount, paramCalendar1, paramCalendar2, paramHashMap);
  }
  
  public AccountSummaries getAccountSummary(SecureUser paramSecureUser, Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException
  {
    return Banking.getSummary(paramSecureUser, paramAccount, paramCalendar1, paramCalendar2, paramHashMap);
  }
  
  public IReportResult getAccountReportData(ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException
  {
    return DataConsolidator.getAccountReportData(paramReportCriteria, paramHashMap);
  }
  
  public com.ffusion.beans.accounts.Accounts getEntitledAccountsByBusinessEmployee(SecureUser paramSecureUser, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException
  {
    return com.ffusion.csil.core.Accounts.getEntitledAccountsByBusinessEmployee(paramSecureUser, paramHashMap);
  }
  
  public Account isAccountEntitledNoCache(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException
  {
    return com.ffusion.csil.core.Accounts.isAccountEntitledNoCache(paramSecureUser, paramAccount, paramHashMap);
  }
  
  public com.ffusion.beans.accounts.Accounts getAccountsByBusinessEmployee(SecureUser paramSecureUser, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException
  {
    return com.ffusion.csil.core.Accounts.getAccountsByBusinessEmployee(paramSecureUser, paramHashMap);
  }
  
  public Message sendMessage(SecureUser paramSecureUser, Message paramMessage, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException
  {
    return Messages.sendMessage(paramSecureUser, paramMessage, paramHashMap);
  }
  
  public Stock getStock(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException
  {
    return Portal.getStock(paramSecureUser, paramString, paramHashMap);
  }
  
  public Stock getStock(SecureUser paramSecureUser, String paramString, boolean paramBoolean, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException
  {
    return Portal.getStock(paramSecureUser, paramString, paramBoolean, paramHashMap);
  }
  
  public Stocks getStocks(SecureUser paramSecureUser, Stocks paramStocks, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException
  {
    return Portal.getStocks(paramSecureUser, paramStocks, paramHashMap);
  }
  
  public StockIndexes getStockIndexes(SecureUser paramSecureUser, StockIndexes paramStockIndexes, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException
  {
    return Portal.getStockIndexes(paramSecureUser, paramStockIndexes, paramHashMap);
  }
  
  public StockSymbols getStockSymbols(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException
  {
    return Portal.getStockSymbols(paramSecureUser, paramString, paramHashMap);
  }
  
  public int getNumberOfUnreadMessages(SecureUser paramSecureUser, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException
  {
    return Messages.getNumberOfUnreadMessages(paramSecureUser, paramHashMap);
  }
  
  public int getNumberOfUnreadMessagesExcludingAlerts(SecureUser paramSecureUser, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException
  {
    return Messages.getNumberOfUnreadMessagesExcludingAlerts(paramSecureUser, paramHashMap);
  }
  
  public void cleanupFM(String paramString, int paramInt, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException
  {
    String str = paramString;
    if (paramString.equalsIgnoreCase("ALL")) {
      str = null;
    }
    FileMonitor.cleanup(str, paramInt, paramHashMap);
  }
  
  public boolean isBankingDay(SecureUser paramSecureUser, BankIdentifier paramBankIdentifier, Date paramDate, HashMap paramHashMap)
    throws CSILException
  {
    return SmartCalendar.isBankingDay(paramSecureUser, paramBankIdentifier, paramDate, paramHashMap);
  }
  
  public void processGlobalMessages()
    throws EJBException, RemoteException, CSILException
  {}
  
  public boolean isAccountEntitled(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException
  {
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    MultiEntitlement localMultiEntitlement = new MultiEntitlement();
    localMultiEntitlement.setObjects(new String[] { "Account" }, new String[] { EntitlementsUtil.getEntitlementObjectId(paramAccount) });
    int i = -1;
    if (paramSecureUser.getUserType() == 1) {
      i = paramSecureUser.getBusinessID();
    }
    return EntitlementsUtil.checkAccountEntitlement(localEntitlementGroupMember, localMultiEntitlement, i) == null;
  }
  
  public boolean allowDuplicateUserNames()
    throws EJBException, RemoteException, CSILException
  {
    return SignonSettings.allowDuplicateUserNames();
  }
  
  public void submitDefaultDecisions(int paramInt, HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException
  {
    PositivePay.submitDefaultDecisions(paramInt, paramHashMap);
  }
  
  public void cleanupPayments(HashMap paramHashMap)
    throws EJBException, RemoteException, CSILException
  {
    PaymentsAdmin.cleanup(paramHashMap);
  }
  
  public BAITypeCodeInfo getBAITypeCodeInfo(int paramInt)
    throws RemoteException, EJBException, CSILException
  {
    return DataConsolidator.getBAITypeCodeInfo(paramInt);
  }
  
  public boolean checkEntitlement(EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement)
    throws RemoteException, EJBException, CSILException
  {
    return Entitlements.checkEntitlement(paramEntitlementGroupMember, paramEntitlement);
  }
  
  public boolean checkEntitlement(int paramInt, Entitlement paramEntitlement)
    throws RemoteException, EJBException, CSILException
  {
    return Entitlements.checkEntitlement(paramInt, paramEntitlement);
  }
  
  public UserAlerts getPagedUserAlerts(int paramInt1, int paramInt2, int paramInt3, HashMap paramHashMap)
    throws RemoteException, EJBException, CSILException
  {
    return Alerts.getPagedUserAlerts(paramInt1, paramInt2, paramInt3, paramHashMap);
  }
  
  public ArrayList getAlertsForAccounts(com.ffusion.beans.accounts.Accounts paramAccounts, HashMap paramHashMap)
    throws RemoteException, EJBException, CSILException
  {
    return Alerts.getAlertsForAccounts(paramAccounts, paramHashMap);
  }
  
  public void modifyUserAlerts(UserAlerts paramUserAlerts, HashMap paramHashMap)
    throws RemoteException, EJBException, CSILException
  {
    if (paramUserAlerts == null) {
      return;
    }
    Iterator localIterator = paramUserAlerts.iterator();
    while (localIterator.hasNext())
    {
      UserAlert localUserAlert = (UserAlert)localIterator.next();
      String str = localUserAlert.getAdditionalProperty("SECUREUSER");
      if (str != null)
      {
        SecureUser localSecureUser = new SecureUser(localUserAlert.getLocale());
        localSecureUser.setXML(str);
        Alerts.modifyUserAlert(localSecureUser, localUserAlert, paramHashMap);
      }
    }
  }
  
  public com.ffusion.beans.accounts.Accounts fillAccountCollection(com.ffusion.beans.accounts.Accounts paramAccounts, HashMap paramHashMap)
    throws RemoteException, EJBException, CSILException
  {
    com.ffusion.csil.core.Accounts.fillAccountCollection(paramAccounts, paramHashMap);
    return paramAccounts;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.ejb.CSILEJBSessionBean
 * JD-Core Version:    0.7.0.1
 */