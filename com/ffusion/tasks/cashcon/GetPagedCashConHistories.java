package com.ffusion.tasks.cashcon;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.cashcon.CashCons;
import com.ffusion.beans.reporting.ReportSortCriterion;
import com.ffusion.tasks.util.GetPagedData;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.FilteredList;
import com.ffusion.util.beans.PagingContext;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class GetPagedCashConHistories
  extends GetPagedData
{
  protected boolean viewAll = false;
  protected String status;
  
  public GetPagedCashConHistories()
  {
    setDataSetName("PendingCashCons");
  }
  
  public void setFirstPage(String paramString)
  {
    setPage("first");
  }
  
  public void setNextPage(String paramString)
  {
    setPage("next");
  }
  
  public void setPreviousPage(String paramString)
  {
    setPage("previous");
  }
  
  public void setCollectionSessionName(String paramString)
  {
    setDataSetName(paramString);
  }
  
  public String getCollectionSessionName()
  {
    return getDataSetName();
  }
  
  public void setViewAll(String paramString)
  {
    this.viewAll = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getViewAll()
  {
    if (this.viewAll == true) {
      return "true";
    }
    return "false";
  }
  
  public void setStatus(String paramString)
  {
    this.status = paramString;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  protected FilteredList getFirstPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null) {
      return null;
    }
    this._pagingContext.setDirection("FIRST");
    if (this.viewAll == true) {
      paramHashMap.put("ACH_VIEW", "ALL");
    }
    paramHashMap.put("CASHCON_STATUS", this.status);
    CashCons localCashCons = com.ffusion.csil.core.CashCon.getPagedCashConHistories(localSecureUser, paramPagingContext, paramHashMap);
    if (getDateFormat() != null) {
      localCashCons.setDateFormat(getDateFormat());
    }
    return localCashCons;
  }
  
  protected FilteredList getNextPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null) {
      return null;
    }
    this._pagingContext.setDirection("NEXT");
    if (this.viewAll == true) {
      paramHashMap.put("ACH_VIEW", "ALL");
    }
    paramHashMap.put("CASHCON_STATUS", this.status);
    CashCons localCashCons = com.ffusion.csil.core.CashCon.getPagedCashConHistories(localSecureUser, paramPagingContext, paramHashMap);
    if (getDateFormat() != null) {
      localCashCons.setDateFormat(getDateFormat());
    }
    return localCashCons;
  }
  
  protected FilteredList getPreviousPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null) {
      return null;
    }
    this._pagingContext.setDirection("PREVIOUS");
    if (this.viewAll == true) {
      paramHashMap.put("ACH_VIEW", "ALL");
    }
    paramHashMap.put("CASHCON_STATUS", this.status);
    CashCons localCashCons = com.ffusion.csil.core.CashCon.getPagedCashConHistories(localSecureUser, paramPagingContext, paramHashMap);
    if (getDateFormat() != null) {
      localCashCons.setDateFormat(getDateFormat());
    }
    return localCashCons;
  }
  
  protected FilteredList getLastPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    this.error = 20112;
    return null;
  }
  
  protected Object getValueForSortCriterion(ReportSortCriterion paramReportSortCriterion, Object paramObject)
  {
    com.ffusion.beans.cashcon.CashCon localCashCon = (com.ffusion.beans.cashcon.CashCon)paramObject;
    if (paramReportSortCriterion.getName().equals("DueDate"))
    {
      String str = "";
      DateFormat localDateFormat = DateFormatUtil.getFormatter("yyyyMMdd");
      if (localCashCon.getSubmitDate() != null)
      {
        str = localDateFormat.format(localCashCon.getSubmitDateValue().getTime());
        if (str != null) {
          str = str + "00";
        }
      }
      return str;
    }
    if (paramReportSortCriterion.getName().equals("Division")) {
      return localCashCon.getDivisionName();
    }
    if (paramReportSortCriterion.getName().equals("Location")) {
      return localCashCon.getLocationName();
    }
    if (paramReportSortCriterion.getName().equals("Type"))
    {
      if (localCashCon.getType() == 15) {
        return "CONCENTRATION";
      }
      if (localCashCon.getType() == 16) {
        return "DISBURSEMENT";
      }
    }
    else
    {
      if (paramReportSortCriterion.getName().equals("Status")) {
        return com.ffusion.beans.cashcon.CashCon.mapStatusToBPWStatus(localCashCon.getStatus());
      }
      if (paramReportSortCriterion.getName().equals("Amount"))
      {
        long l = localCashCon.getAmountValue().getAmountValue().multiply(new BigDecimal(100.0D)).longValue();
        return "" + l + ".0000";
      }
      if (paramReportSortCriterion.getName().equals("TransactionIndex")) {
        return new Long(localCashCon.getTransactionIndex());
      }
    }
    return null;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.cashcon.GetPagedCashConHistories
 * JD-Core Version:    0.7.0.1
 */