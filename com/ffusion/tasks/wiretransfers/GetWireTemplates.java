package com.ffusion.tasks.wiretransfers;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.wiretransfers.WireTransfer;
import com.ffusion.beans.wiretransfers.WireTransfers;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Wire;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.beans.InvalidDateTimeException;
import com.ffusion.util.beans.PagingContext;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetWireTemplates
  extends ExtendedBaseTask
  implements WireTaskDefines
{
  protected boolean dateChanged = false;
  protected Calendar startDate = null;
  protected Calendar endDate = null;
  protected String datetype = null;
  protected Locale locale;
  protected WireTransfer wireTemplate = null;
  protected String pageSize;
  protected boolean firstPage = true;
  protected boolean nextPage = false;
  protected boolean previousPage = false;
  protected boolean bIsFirstPage = true;
  protected boolean bIsLastPage = false;
  protected boolean bPendingApproval = false;
  
  public GetWireTemplates()
  {
    this.collectionSessionName = "WiresTemplates";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.locale = ((Locale)localHttpSession.getAttribute("java.util.Locale"));
    String str = this.successURL;
    this.error = 0;
    WireTransfers localWireTransfers = null;
    localWireTransfers = (WireTransfers)localHttpSession.getAttribute(this.collectionSessionName);
    if (localWireTransfers == null) {
      this.firstPage = true;
    }
    if (this.dateChanged)
    {
      this.firstPage = true;
      this.nextPage = false;
      this.previousPage = false;
    }
    HashMap localHashMap = new HashMap();
    if ((this.pageSize != null) && (this.pageSize.length() > 0)) {
      localHashMap.put("PAGESIZE", this.pageSize);
    }
    localHashMap.put("PENDING_APPROVAL", String.valueOf(this.bPendingApproval));
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (getWireScope() == null) {
      setWireScope("USER");
    }
    Object localObject;
    if (localSecureUser.getUserType() == 2)
    {
      if ((getBusinessID() == null) || (getBusinessID().length() == 0))
      {
        this.error = 12034;
        return this.taskErrorURL;
      }
      setWireScope("BANK");
    }
    else
    {
      localObject = (Accounts)localHttpSession.getAttribute("WiresAccounts");
      localHashMap.put("ACCOUNTS", localObject);
      setBusinessID(String.valueOf(localSecureUser.getBusinessID()));
    }
    try
    {
      if ((localWireTransfers == null) || (this.firstPage == true))
      {
        localObject = new PagingContext(this.startDate, this.endDate);
        ((PagingContext)localObject).setDirection("FIRST");
        localWireTransfers = Wire.getWireTemplates(localSecureUser, (PagingContext)localObject, this.wireTemplate, localHashMap);
      }
      else if (this.previousPage == true)
      {
        localObject = localWireTransfers.getPagingContext();
        ((PagingContext)localObject).setDirection("PREVIOUS");
        localWireTransfers = Wire.getWireTemplates(localSecureUser, (PagingContext)localObject, this.wireTemplate, localHashMap);
      }
      else if (this.nextPage == true)
      {
        localObject = localWireTransfers.getPagingContext();
        ((PagingContext)localObject).setDirection("NEXT");
        localWireTransfers = Wire.getWireTemplates(localSecureUser, (PagingContext)localObject, this.wireTemplate, localHashMap);
      }
      if (this.datetype != null) {
        localWireTransfers.setDateFormat(this.datetype);
      }
      updateFromPagingContext(localWireTransfers);
      localHttpSession.setAttribute(this.collectionSessionName, localWireTransfers);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    this.firstPage = false;
    this.nextPage = false;
    this.previousPage = false;
    this.dateChanged = false;
    return str;
  }
  
  public void setFirstPage(String paramString)
  {
    this.firstPage = true;
  }
  
  public void setNextPage(String paramString)
  {
    this.nextPage = true;
  }
  
  public void setPreviousPage(String paramString)
  {
    this.previousPage = true;
  }
  
  public String getIsFirstPage()
  {
    if (this.bIsFirstPage) {
      return "true";
    }
    return "false";
  }
  
  public String getIsLastPage()
  {
    if (this.bIsLastPage) {
      return "true";
    }
    return "false";
  }
  
  public void setStartDate(String paramString)
  {
    DateTime localDateTime = null;
    DateFormat localDateFormat = DateFormatUtil.getFormatter("M/d/yyyy");
    try
    {
      if ((paramString != null) && (paramString.length() > 0))
      {
        localDateTime = new DateTime(paramString, this.locale, this.datetype);
        if (localDateTime != null)
        {
          localDateTime.set(11, 0);
          localDateTime.set(12, 0);
          localDateTime.set(13, 0);
          if ((this.startDate == null) || (!localDateFormat.format(localDateTime.getTime()).equalsIgnoreCase(localDateFormat.format(this.startDate.getTime())))) {
            this.dateChanged = true;
          }
          this.startDate = localDateTime;
        }
      }
      else
      {
        this.startDate = null;
      }
    }
    catch (Throwable localThrowable)
    {
      System.out.println("GetWireTemplates.setStartDate:Exception = " + localThrowable.getMessage() + "," + localThrowable.toString());
    }
  }
  
  public String getStartDate()
  {
    if (this.startDate != null) {
      return this.startDate.toString();
    }
    return null;
  }
  
  public void setEndDate(String paramString)
  {
    DateFormat localDateFormat = DateFormatUtil.getFormatter("M/d/yyyy");
    DateTime localDateTime = null;
    try
    {
      if ((paramString != null) && (paramString.length() > 0))
      {
        localDateTime = new DateTime(paramString, this.locale, this.datetype);
        if (localDateTime != null)
        {
          localDateTime.set(11, 23);
          localDateTime.set(12, 59);
          localDateTime.set(13, 59);
          if ((this.endDate == null) || (!localDateFormat.format(localDateTime.getTime()).equalsIgnoreCase(localDateFormat.format(this.endDate.getTime())))) {
            this.dateChanged = true;
          }
          this.endDate = localDateTime;
        }
      }
      else
      {
        this.endDate = null;
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public void setDateFormat(String paramString)
  {
    this.datetype = paramString;
  }
  
  public String getEndDate()
  {
    if (this.endDate != null) {
      return this.endDate.toString();
    }
    return null;
  }
  
  public void setWireScope(String paramString)
  {
    this.wireTemplate.setWireScope(paramString);
  }
  
  public String getWireScope()
  {
    return this.wireTemplate.getWireScope();
  }
  
  public void setBusinessID(String paramString)
  {
    this.wireTemplate.setCustomerID(paramString);
  }
  
  public String getBusinessID()
  {
    return this.wireTemplate.getCustomerID();
  }
  
  public void setStatus(String paramString)
  {
    this.wireTemplate.setStatus(paramString);
  }
  
  public void setStatus(int paramInt)
  {
    this.wireTemplate.setStatus(paramInt);
  }
  
  public void setType(String paramString)
  {
    this.wireTemplate.setWireDestination(paramString);
  }
  
  public void setPageSize(String paramString)
  {
    this.pageSize = paramString;
    this.firstPage = true;
  }
  
  public String getPageSize()
  {
    return this.pageSize;
  }
  
  public void setPendingApproval(String paramString)
  {
    if (paramString.equalsIgnoreCase("true")) {
      this.bPendingApproval = true;
    }
  }
  
  protected void updateFromPagingContext(WireTransfers paramWireTransfers)
  {
    PagingContext localPagingContext = paramWireTransfers.getPagingContext();
    if (localPagingContext != null)
    {
      this.bIsFirstPage = localPagingContext.isFirstPage();
      this.bIsLastPage = localPagingContext.isLastPage();
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.GetWireTemplates
 * JD-Core Version:    0.7.0.1
 */