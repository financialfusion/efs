package com.ffusion.tasks.banking;

import com.ffusion.beans.FundsTransaction;
import com.ffusion.beans.FundsTransactions;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.banking.Transfer;
import com.ffusion.beans.banking.TransferBatch;
import com.ffusion.beans.banking.Transfers;
import com.ffusion.beans.reporting.ReportSortCriterion;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.util.GetPagedData;
import com.ffusion.util.FilteredList;
import com.ffusion.util.beans.PagingContext;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetAllTransferTemplates
  extends GetPagedData
  implements Task
{
  protected String accountsCollection = "BankingAccounts";
  protected String bankingServiceName = "com.ffusion.services.Banking";
  
  public GetAllTransferTemplates()
  {
    setDataSetName("TransferTemplates");
    this._pagingContext = new PagingContext(new GregorianCalendar(1970, 0, 1), new GregorianCalendar(2100, 0, 1));
    this._pagingContext.setDirection("FIRST");
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    try
    {
      this.error = 0;
      HashMap localHashMap1 = new HashMap();
      localHashMap1.put("PAGESIZE", "0");
      SecureUser localSecureUser = jdMethod_int(localHttpSession, localHashMap1);
      if (localSecureUser == null) {
        return null;
      }
      if (localHashMap1 == null) {
        localHashMap1 = new HashMap();
      }
      HashMap localHashMap2 = new HashMap();
      localHashMap2.put("ReportCriteria", this._criteria);
      this._pagingContext.setMap(localHashMap2);
      FundsTransactions localFundsTransactions = com.ffusion.csil.core.Banking.getPagedFundsTransactions(localSecureUser, this._pagingContext, localHashMap1);
      jdMethod_for(localHashMap1, localHttpSession, localFundsTransactions);
      localHttpSession.setAttribute(getDataSetName(), localFundsTransactions);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    catch (Exception localException)
    {
      this.error = -1009;
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  private SecureUser jdMethod_int(HttpSession paramHttpSession, HashMap paramHashMap)
    throws Exception
  {
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    Accounts localAccounts = (Accounts)paramHttpSession.getAttribute(this.accountsCollection);
    com.ffusion.services.Banking localBanking = (com.ffusion.services.Banking)paramHttpSession.getAttribute(this.bankingServiceName);
    if ((localSecureUser == null) || (localAccounts == null) || (localBanking == null)) {
      return null;
    }
    paramHashMap.put("SERVICE", localBanking);
    paramHashMap.put("TRANSFER_ACCOUNTS", localAccounts);
    paramHashMap.put("TRANSFER_STATUS", "TRANSFER_STATUS_TEMPLATE");
    paramHashMap.put("TransType", "TEMPLATE,RECTEMPLATE,BATCHTEMPLATE");
    paramHashMap.put("Dests", "BOTH");
    return localSecureUser;
  }
  
  private void jdMethod_for(HashMap paramHashMap, HttpSession paramHttpSession, FilteredList paramFilteredList)
  {
    Accounts localAccounts = (Accounts)paramHttpSession.getAttribute(this.accountsCollection);
    if (localAccounts == null) {
      return;
    }
    Iterator localIterator = paramFilteredList.iterator();
    while (localIterator.hasNext())
    {
      Object localObject1 = localIterator.next();
      Object localObject2;
      Object localObject3;
      if ((localObject1 instanceof Transfer))
      {
        localObject2 = (Transfer)localObject1;
        localObject3 = localAccounts.getByID(((Transfer)localObject2).getFromAccountID());
        if (localObject3 != null) {
          ((Transfer)localObject2).setFromAccount((Account)localObject3);
        }
        localObject3 = localAccounts.getByID(((Transfer)localObject2).getToAccountID());
        if (localObject3 != null) {
          ((Transfer)localObject2).setToAccount((Account)localObject3);
        }
      }
      else if ((localObject1 instanceof TransferBatch))
      {
        localObject2 = (TransferBatch)localObject1;
        if (((TransferBatch)localObject2).getTransfers() != null)
        {
          localObject3 = ((TransferBatch)localObject2).getTransfers().iterator();
          while (((Iterator)localObject3).hasNext())
          {
            Transfer localTransfer = (Transfer)((Iterator)localObject3).next();
            Account localAccount = localAccounts.getByID(localTransfer.getFromAccountID());
            if (localAccount != null) {
              localTransfer.setFromAccount(localAccount);
            }
            localAccount = localAccounts.getByID(localTransfer.getToAccountID());
            if (localAccount != null) {
              localTransfer.setToAccount(localAccount);
            }
          }
        }
      }
    }
  }
  
  protected FilteredList getFirstPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    return null;
  }
  
  protected FilteredList getLastPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    return null;
  }
  
  protected FilteredList getNextPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    return null;
  }
  
  protected FilteredList getPreviousPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    return null;
  }
  
  protected Object getValueForSortCriterion(ReportSortCriterion paramReportSortCriterion, Object paramObject)
  {
    FundsTransaction localFundsTransaction = (FundsTransaction)paramObject;
    if (paramReportSortCriterion.getName().equals("TemplateName")) {
      return localFundsTransaction.getTemplateName();
    }
    return null;
  }
  
  public void setAccountsCollection(String paramString)
  {
    this.accountsCollection = paramString;
  }
  
  public String getAccountsCollection()
  {
    return this.accountsCollection;
  }
  
  public void setBankingServiceName(String paramString)
  {
    this.bankingServiceName = paramString;
  }
  
  public String getBankingServiceName()
  {
    return this.bankingServiceName;
  }
  
  public void setCollectionSessionName(String paramString)
  {
    setDataSetName(paramString);
  }
  
  public String getCollectionSessionName()
  {
    return getDataSetName();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.GetAllTransferTemplates
 * JD-Core Version:    0.7.0.1
 */