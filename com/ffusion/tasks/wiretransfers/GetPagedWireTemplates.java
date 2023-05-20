package com.ffusion.tasks.wiretransfers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.reporting.ReportSortCriterion;
import com.ffusion.beans.wiretransfers.WireStatusMap;
import com.ffusion.beans.wiretransfers.WireTransfer;
import com.ffusion.beans.wiretransfers.WireTransferPayee;
import com.ffusion.beans.wiretransfers.WireTransfers;
import com.ffusion.csil.core.Wire;
import com.ffusion.tasks.util.GetPagedData;
import com.ffusion.util.FilteredList;
import com.ffusion.util.beans.PagingContext;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class GetPagedWireTemplates
  extends GetPagedData
  implements WireTaskDefines
{
  protected WireTransfer searchWire = null;
  protected boolean bPendingApproval = false;
  
  public GetPagedWireTemplates()
  {
    setDataSetName("WiresTemplates");
    this.searchWire = new WireTransfer();
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
  
  public void setWireScope(String paramString)
  {
    this.searchWire.setWireScope(paramString);
  }
  
  public String getWireScope()
  {
    return this.searchWire.getWireScope();
  }
  
  public void setBusinessID(String paramString)
  {
    this.searchWire.setCustomerID(paramString);
  }
  
  public String getBusinessID()
  {
    return this.searchWire.getCustomerID();
  }
  
  public void setStatus(String paramString)
  {
    this.searchWire.setStatus(paramString);
  }
  
  public void setStatus(int paramInt)
  {
    this.searchWire.setStatus(paramInt);
  }
  
  public void setType(String paramString)
  {
    this.searchWire.setWireDestination(paramString);
  }
  
  public void setPendingApproval(String paramString)
  {
    if (paramString.equalsIgnoreCase("true")) {
      this.bPendingApproval = true;
    }
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
    paramHashMap.put("PENDING_APPROVAL", String.valueOf(this.bPendingApproval));
    if (getWireScope() == null) {
      setWireScope("USER");
    }
    if (localSecureUser.getUserType() == 2)
    {
      if ((getBusinessID() == null) || (getBusinessID().length() == 0))
      {
        this.error = 12034;
        this._nextURL = this.taskErrorURL;
        return null;
      }
      setWireScope("BANK");
    }
    else
    {
      setBusinessID(String.valueOf(localSecureUser.getBusinessID()));
    }
    WireTransfers localWireTransfers = Wire.getPagedWireTemplates(localSecureUser, paramPagingContext, this.searchWire, paramHashMap);
    if (getDateFormat() != null) {
      localWireTransfers.setDateFormat(getDateFormat());
    }
    return localWireTransfers;
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
    paramHashMap.put("PENDING_APPROVAL", String.valueOf(this.bPendingApproval));
    WireTransfers localWireTransfers = Wire.getPagedWireTemplates(localSecureUser, paramPagingContext, this.searchWire, paramHashMap);
    if (getDateFormat() != null) {
      localWireTransfers.setDateFormat(getDateFormat());
    }
    return localWireTransfers;
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
    paramHashMap.put("PENDING_APPROVAL", String.valueOf(this.bPendingApproval));
    WireTransfers localWireTransfers = Wire.getPagedWireTemplates(localSecureUser, paramPagingContext, this.searchWire, paramHashMap);
    if (getDateFormat() != null) {
      localWireTransfers.setDateFormat(getDateFormat());
    }
    return localWireTransfers;
  }
  
  protected FilteredList getLastPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    this.error = 20112;
    return null;
  }
  
  protected Object getValueForSortCriterion(ReportSortCriterion paramReportSortCriterion, Object paramObject)
  {
    WireTransfer localWireTransfer = (WireTransfer)paramObject;
    if (paramReportSortCriterion.getName().equals("AccountNum")) {
      return localWireTransfer.getFromAccountNum();
    }
    if (paramReportSortCriterion.getName().equals("Amount")) {
      return localWireTransfer.getAmountForBPW();
    }
    if (paramReportSortCriterion.getName().equals("BeneficiaryName"))
    {
      WireTransferPayee localWireTransferPayee = localWireTransfer.getWirePayee();
      if (localWireTransferPayee != null) {
        return localWireTransferPayee.getPayeeName();
      }
      return localWireTransfer.getWirePayeeID();
    }
    if (paramReportSortCriterion.getName().equals("DateToPost")) {
      return localWireTransfer.getDateToPost();
    }
    if (paramReportSortCriterion.getName().equals("Destination")) {
      return localWireTransfer.getWireDestination();
    }
    if (paramReportSortCriterion.getName().equals("Status"))
    {
      int i = localWireTransfer.getStatus();
      return WireStatusMap.mapToStatusToStr(i);
    }
    if (paramReportSortCriterion.getName().equals("TransactionType")) {
      return localWireTransfer.getWireType();
    }
    if (paramReportSortCriterion.getName().equals("WireName")) {
      return localWireTransfer.getWireName();
    }
    if (paramReportSortCriterion.getName().equals("TransactionIndex")) {
      return new Long(localWireTransfer.getTransactionIndex());
    }
    return null;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.GetPagedWireTemplates
 * JD-Core Version:    0.7.0.1
 */