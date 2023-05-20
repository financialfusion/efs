package com.ffusion.tasks.lockbox;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.lockbox.LockboxAccount;
import com.ffusion.beans.lockbox.LockboxCreditItem;
import com.ffusion.beans.lockbox.LockboxSummaries;
import com.ffusion.beans.lockbox.LockboxSummary;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportSortCriterion;
import com.ffusion.csil.core.Lockbox;
import com.ffusion.tasks.util.GetPagedData;
import com.ffusion.util.FilteredList;
import com.ffusion.util.beans.PagingContext;
import java.util.HashMap;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class GetPagedCreditItems
  extends GetPagedData
  implements Task
{
  static final String Ek = "Account";
  String En = "Account";
  String Ej;
  String Em;
  String El;
  
  public GetPagedCreditItems()
  {
    setDataSetName("LOCKBOX_CREDIT_ITEMS");
    this.Ej = "P";
  }
  
  public void setAccountID(String paramString)
  {
    this.Em = paramString;
  }
  
  public String getAccountID()
  {
    return this.Em;
  }
  
  public void setLockboxNumber(String paramString)
  {
    this.El = paramString;
  }
  
  public String getLockboxNumber()
  {
    return this.El;
  }
  
  public String getDataClassification()
  {
    return this.Ej;
  }
  
  public void setDataClassification(String paramString)
  {
    this.Ej = paramString;
  }
  
  protected FilteredList getFirstPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (this.Em == null)
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
        if (this.Em.equals(((LockboxAccount)localObject2).getAccountID()))
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
    paramHashMap.put("DATA_CLASSIFICATION", this.Ej);
    ReportCriteria localReportCriteria = (ReportCriteria)paramPagingContext.getMap().get("ReportCriteria");
    localReportCriteria.getSearchCriteria().setProperty("LockboxNumber", this.El);
    return Lockbox.getPagedCreditItems((SecureUser)localObject2, localObject1, paramPagingContext, paramHashMap);
  }
  
  protected FilteredList getNextPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (this.Em == null)
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
        if (this.Em.equals(((LockboxAccount)localObject2).getAccountID()))
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
    paramHashMap.put("DATA_CLASSIFICATION", this.Ej);
    ReportCriteria localReportCriteria = (ReportCriteria)paramPagingContext.getMap().get("ReportCriteria");
    localReportCriteria.getSearchCriteria().setProperty("LockboxNumber", this.El);
    return Lockbox.getNextCreditItems((SecureUser)localObject2, localObject1, paramPagingContext, paramHashMap);
  }
  
  protected FilteredList getPreviousPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (this.Em == null)
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
        if (this.Em.equals(((LockboxAccount)localObject2).getAccountID()))
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
    paramHashMap.put("DATA_CLASSIFICATION", this.Ej);
    ReportCriteria localReportCriteria = (ReportCriteria)paramPagingContext.getMap().get("ReportCriteria");
    localReportCriteria.getSearchCriteria().setProperty("LockboxNumber", this.El);
    return Lockbox.getPreviousCreditItems((SecureUser)localObject2, localObject1, paramPagingContext, paramHashMap);
  }
  
  protected FilteredList getLastPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    this.error = 20112;
    return null;
  }
  
  protected Object getValueForSortCriterion(ReportSortCriterion paramReportSortCriterion, Object paramObject)
  {
    LockboxCreditItem localLockboxCreditItem = (LockboxCreditItem)paramObject;
    if (paramReportSortCriterion.getName().equals("Payer")) {
      return localLockboxCreditItem.getPayer();
    }
    if (paramReportSortCriterion.getName().equals("Amount")) {
      return localLockboxCreditItem.getCheckAmount();
    }
    if (paramReportSortCriterion.getName().equals("CheckNumber")) {
      return localLockboxCreditItem.getCheckNumber();
    }
    if (paramReportSortCriterion.getName().equals("CouponAccountNumber")) {
      return localLockboxCreditItem.getCouponAccountNumber();
    }
    if (paramReportSortCriterion.getName().equals("CouponAmount1")) {
      return localLockboxCreditItem.getCouponAmount1();
    }
    if (paramReportSortCriterion.getName().equals("CouponAmount2")) {
      return localLockboxCreditItem.getCouponAmount2();
    }
    if (paramReportSortCriterion.getName().equals("CouponDate1")) {
      return localLockboxCreditItem.getCouponDate1();
    }
    if (paramReportSortCriterion.getName().equals("CouponDate2")) {
      return localLockboxCreditItem.getCouponDate2();
    }
    if (paramReportSortCriterion.getName().equals("CouponRefNum")) {
      return localLockboxCreditItem.getCouponReferenceNumber();
    }
    if (paramReportSortCriterion.getName().equals("TransactionIndex")) {
      return new Long(localLockboxCreditItem.getItemIndex());
    }
    return null;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.lockbox.GetPagedCreditItems
 * JD-Core Version:    0.7.0.1
 */