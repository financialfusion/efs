package com.ffusion.tasks.wiretransfers;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.reporting.ReportSortCriterion;
import com.ffusion.beans.wiretransfers.WireHistories;
import com.ffusion.beans.wiretransfers.WireHistory;
import com.ffusion.beans.wiretransfers.WireStatusMap;
import com.ffusion.beans.wiretransfers.WireTransferPayee;
import com.ffusion.csil.core.Wire;
import com.ffusion.tasks.util.GetPagedData;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.FilteredList;
import com.ffusion.util.beans.PagingContext;
import java.text.DateFormat;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class GetPagedWireHistories
  extends GetPagedData
  implements WireTaskDefines
{
  protected boolean viewAll = false;
  protected String destination;
  protected String status = "PENDING";
  protected static final String DATEFORMAT = "yyyyMMdd";
  
  public GetPagedWireHistories()
  {
    setDataSetName("PendingWireHistories");
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
  
  public void setType(String paramString)
  {
    this.destination = paramString;
  }
  
  public String getType()
  {
    return this.destination;
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
    if ((this.destination != null) && (this.destination.length() > 0)) {
      paramHashMap.put("DESTINATION", this.destination);
    }
    if (this.viewAll == true) {
      paramHashMap.put("VIEW", "ALL");
    }
    paramHashMap.put("STATUS", this.status);
    WireHistories localWireHistories = Wire.getPagedWireHistories(localSecureUser, paramPagingContext, paramHashMap);
    if (getDateFormat() != null) {
      localWireHistories.setDateFormat(getDateFormat());
    }
    return localWireHistories;
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
      paramHashMap.put("VIEW", "ALL");
    }
    WireHistories localWireHistories = Wire.getPagedWireHistories(localSecureUser, paramPagingContext, paramHashMap);
    if (getDateFormat() != null) {
      localWireHistories.setDateFormat(getDateFormat());
    }
    return localWireHistories;
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
      paramHashMap.put("VIEW", "ALL");
    }
    WireHistories localWireHistories = Wire.getPagedWireHistories(localSecureUser, paramPagingContext, paramHashMap);
    if (getDateFormat() != null) {
      localWireHistories.setDateFormat(getDateFormat());
    }
    return localWireHistories;
  }
  
  protected FilteredList getLastPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    this.error = 20112;
    return null;
  }
  
  protected Object getValueForSortCriterion(ReportSortCriterion paramReportSortCriterion, Object paramObject)
  {
    WireHistory localWireHistory = (WireHistory)paramObject;
    if (paramReportSortCriterion.getName().equals("AccountNum")) {
      return localWireHistory.getFromAccountNum();
    }
    if (paramReportSortCriterion.getName().equals("Amount")) {
      return localWireHistory.getAmountForBPW();
    }
    Object localObject;
    if (paramReportSortCriterion.getName().equals("BeneficiaryName"))
    {
      localObject = localWireHistory.getPayee();
      if (localObject != null) {
        return ((WireTransferPayee)localObject).getPayeeName();
      }
      return localWireHistory.getPayeeID();
    }
    if (paramReportSortCriterion.getName().equals("DateToPost"))
    {
      localObject = "";
      DateFormat localDateFormat = DateFormatUtil.getFormatter("yyyyMMdd");
      if (localWireHistory.getDate() != null)
      {
        localObject = localDateFormat.format(localWireHistory.getDateValue().getTime());
        if (localObject != null) {
          localObject = (String)localObject + "00";
        }
      }
      return localObject;
    }
    if (paramReportSortCriterion.getName().equals("Destination")) {
      return localWireHistory.getDestination();
    }
    if (paramReportSortCriterion.getName().equals("Status"))
    {
      int i = localWireHistory.getStatus();
      return WireStatusMap.mapToStatusToStr(i);
    }
    if (paramReportSortCriterion.getName().equals("TransactionType")) {
      return localWireHistory.getTransType();
    }
    if (paramReportSortCriterion.getName().equals("TransactionIndex")) {
      return new Long(localWireHistory.getTransactionIndex());
    }
    return null;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.GetPagedWireHistories
 * JD-Core Version:    0.7.0.1
 */