package com.ffusion.tasks.lockbox;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.lockbox.LockboxAccount;
import com.ffusion.beans.lockbox.LockboxSummaries;
import com.ffusion.beans.lockbox.LockboxSummary;
import com.ffusion.beans.lockbox.LockboxTransaction;
import com.ffusion.beans.reporting.ReportSortCriterion;
import com.ffusion.csil.core.Lockbox;
import com.ffusion.tasks.util.GetPagedData;
import com.ffusion.util.FilteredList;
import com.ffusion.util.beans.PagingContext;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class GetPagedTransactions
  extends GetPagedData
  implements Task
{
  static final String Ep = "Account";
  String Er = "Account";
  String Eo;
  String Eq;
  
  public GetPagedTransactions()
  {
    setDataSetName("LOCKBOX_TRANSACTIONS");
    this.Eo = "P";
  }
  
  public void setAccountID(String paramString)
  {
    this.Eq = paramString;
  }
  
  public String getAccountID()
  {
    return this.Eq;
  }
  
  public void setTransactionsName(String paramString)
  {
    setDataSetName(paramString);
  }
  
  public String getTransactionsName()
  {
    return getDataSetName();
  }
  
  public String getDataClassification()
  {
    return this.Eo;
  }
  
  public void setDataClassification(String paramString)
  {
    this.Eo = paramString;
  }
  
  protected FilteredList getFirstPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (this.Eq == null)
    {
      this.error = 22000;
      return null;
    }
    LockboxSummaries localLockboxSummaries = (LockboxSummaries)localHttpSession.getAttribute("LOCKBOX_SUMMARIES");
    if (localLockboxSummaries == null)
    {
      this.error = 22001;
      return null;
    }
    Object localObject1 = null;
    if (localLockboxSummaries != null)
    {
      localObject2 = null;
      for (int i = 0; i < localLockboxSummaries.size(); i++)
      {
        localObject2 = ((LockboxSummary)localLockboxSummaries.get(i)).getLockboxAccount();
        if (this.Eq.equals(((LockboxAccount)localObject2).getAccountID()))
        {
          localObject1 = localObject2;
          break;
        }
      }
    }
    Object localObject2 = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localObject2 == null) {
      return null;
    }
    paramHashMap.put("DATA_CLASSIFICATION", this.Eo);
    return Lockbox.getPagedTransactions((SecureUser)localObject2, localObject1, paramPagingContext, paramHashMap);
  }
  
  protected FilteredList getNextPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (this.Eq == null)
    {
      this.error = 22000;
      return null;
    }
    LockboxSummaries localLockboxSummaries = (LockboxSummaries)localHttpSession.getAttribute("LOCKBOX_SUMMARIES");
    if (localLockboxSummaries == null)
    {
      this.error = 22001;
      return null;
    }
    Object localObject1 = null;
    if (localLockboxSummaries != null)
    {
      localObject2 = null;
      for (int i = 0; i < localLockboxSummaries.size(); i++)
      {
        localObject2 = ((LockboxSummary)localLockboxSummaries.get(i)).getLockboxAccount();
        if (this.Eq.equals(((LockboxAccount)localObject2).getAccountID()))
        {
          localObject1 = localObject2;
          break;
        }
      }
    }
    Object localObject2 = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localObject2 == null) {
      return null;
    }
    paramHashMap.put("DATA_CLASSIFICATION", this.Eo);
    return Lockbox.getNextTransactions((SecureUser)localObject2, localObject1, paramPagingContext, paramHashMap);
  }
  
  protected FilteredList getPreviousPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (this.Eq == null)
    {
      this.error = 22000;
      return null;
    }
    LockboxSummaries localLockboxSummaries = (LockboxSummaries)localHttpSession.getAttribute("LOCKBOX_SUMMARIES");
    if (localLockboxSummaries == null)
    {
      this.error = 22001;
      return null;
    }
    Object localObject1 = null;
    if (localLockboxSummaries != null)
    {
      localObject2 = null;
      for (int i = 0; i < localLockboxSummaries.size(); i++)
      {
        localObject2 = ((LockboxSummary)localLockboxSummaries.get(i)).getLockboxAccount();
        if (this.Eq.equals(((LockboxAccount)localObject2).getAccountID()))
        {
          localObject1 = localObject2;
          break;
        }
      }
    }
    Object localObject2 = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localObject2 == null) {
      return null;
    }
    paramHashMap.put("DATA_CLASSIFICATION", this.Eo);
    return Lockbox.getPreviousTransactions((SecureUser)localObject2, localObject1, paramPagingContext, paramHashMap);
  }
  
  protected FilteredList getLastPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    this.error = 20112;
    return null;
  }
  
  protected Object getValueForSortCriterion(ReportSortCriterion paramReportSortCriterion, Object paramObject)
  {
    LockboxTransaction localLockboxTransaction = (LockboxTransaction)paramObject;
    if (paramReportSortCriterion.getName().equals("LockBoxNumber")) {
      return localLockboxTransaction.getLockboxNumber();
    }
    if (paramReportSortCriterion.getName().equals("Amount")) {
      return localLockboxTransaction.getAmount();
    }
    if (paramReportSortCriterion.getName().equals("ImmediateFloat")) {
      return localLockboxTransaction.getImmediateFloat();
    }
    if (paramReportSortCriterion.getName().equals("OneDayFloat")) {
      return localLockboxTransaction.getOneDayFloat();
    }
    if (paramReportSortCriterion.getName().equals("TwoDayFloat")) {
      return localLockboxTransaction.getTwoDayFloat();
    }
    if (paramReportSortCriterion.getName().equals("NumRejectedChecks")) {
      return new Integer(localLockboxTransaction.getNumRejectedChecks());
    }
    if (paramReportSortCriterion.getName().equals("AmountRejected")) {
      return localLockboxTransaction.getRejectedAmount();
    }
    if (paramReportSortCriterion.getName().equals("TransactionIndex")) {
      return new Long(localLockboxTransaction.getTransactionIndex());
    }
    return null;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.lockbox.GetPagedTransactions
 * JD-Core Version:    0.7.0.1
 */