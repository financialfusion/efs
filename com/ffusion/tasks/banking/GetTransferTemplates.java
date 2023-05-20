package com.ffusion.tasks.banking;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.banking.Transfer;
import com.ffusion.beans.banking.Transfers;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.beans.PagingContext;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetTransferTemplates
  extends ExtendedBaseTask
  implements Task
{
  protected boolean dateChanged = false;
  protected Calendar startDate = null;
  protected Calendar endDate = null;
  protected String datetype = null;
  protected Locale locale;
  protected Transfer template = null;
  protected String pageSize;
  protected boolean firstPage = true;
  protected boolean nextPage = false;
  protected boolean previousPage = false;
  protected boolean bIsFirstPage = true;
  protected boolean bIsLastPage = false;
  protected String bankingServiceName = null;
  
  public GetTransferTemplates()
  {
    this.collectionSessionName = "TransferTemplates";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.locale = ((Locale)localHttpSession.getAttribute("java.util.Locale"));
    String str = this.successURL;
    this.error = 0;
    Transfers localTransfers = null;
    localTransfers = (Transfers)localHttpSession.getAttribute(this.collectionSessionName);
    if (localTransfers == null) {
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
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Accounts localAccounts = (Accounts)localHttpSession.getAttribute("TransferAccounts");
    localHashMap.put("ACCOUNTS", localAccounts);
    setBusinessID(String.valueOf(localSecureUser.getBusinessID()));
    Object localObject;
    if ((this.bankingServiceName != null) && (this.bankingServiceName.length() > 0))
    {
      localObject = (com.ffusion.services.Banking)localHttpSession.getAttribute(this.bankingServiceName);
      localHashMap.put("SERVICE", localObject);
    }
    try
    {
      if ((localTransfers == null) || (this.firstPage == true))
      {
        localObject = new PagingContext(this.startDate, this.endDate);
        ((PagingContext)localObject).setDirection("FIRST");
        localTransfers = com.ffusion.csil.core.Banking.getTransferTemplates(localSecureUser, this.template, (PagingContext)localObject, localHashMap);
      }
      else if (this.previousPage == true)
      {
        localObject = localTransfers.getPagingContext();
        ((PagingContext)localObject).setDirection("PREVIOUS");
        localTransfers = com.ffusion.csil.core.Banking.getTransferTemplates(localSecureUser, this.template, (PagingContext)localObject, localHashMap);
      }
      else if (this.nextPage == true)
      {
        localObject = localTransfers.getPagingContext();
        ((PagingContext)localObject).setDirection("NEXT");
        localTransfers = com.ffusion.csil.core.Banking.getTransferTemplates(localSecureUser, this.template, (PagingContext)localObject, localHashMap);
      }
      if (this.datetype != null) {
        localTransfers.setDateFormat(this.datetype);
      }
      updateFromPagingContext(localTransfers);
      localHttpSession.setAttribute(this.collectionSessionName, localTransfers);
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
    GregorianCalendar localGregorianCalendar = null;
    DateFormat localDateFormat = DateFormatUtil.getFormatter("M/d/yyyy");
    try
    {
      if ((paramString != null) && (paramString.length() > 0))
      {
        localGregorianCalendar = new GregorianCalendar();
        localGregorianCalendar.setTime(localDateFormat.parse(paramString));
        localGregorianCalendar.set(11, 0);
        localGregorianCalendar.set(12, 0);
        localGregorianCalendar.set(13, 0);
        if ((this.startDate == null) || (!localDateFormat.format(localGregorianCalendar.getTime()).equalsIgnoreCase(localDateFormat.format(this.startDate.getTime())))) {
          this.dateChanged = true;
        }
        this.startDate = localGregorianCalendar;
      }
      else
      {
        this.startDate = null;
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("GetTransferTemplates.setStartDate:Exception = " + localThrowable.getMessage() + "," + localThrowable.toString());
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
    GregorianCalendar localGregorianCalendar = null;
    try
    {
      if ((paramString != null) && (paramString.length() > 0))
      {
        localGregorianCalendar = new GregorianCalendar();
        localGregorianCalendar.setTime(localDateFormat.parse(paramString));
        localGregorianCalendar.set(11, 23);
        localGregorianCalendar.set(12, 59);
        localGregorianCalendar.set(13, 59);
        if ((this.endDate == null) || (!localDateFormat.format(localGregorianCalendar.getTime()).equalsIgnoreCase(localDateFormat.format(this.endDate.getTime())))) {
          this.dateChanged = true;
        }
        this.endDate = localGregorianCalendar;
      }
      else
      {
        this.endDate = null;
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("GetTransferTemplates.setStartDate:Exception = " + localThrowable.getMessage() + "," + localThrowable.toString());
    }
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
  
  public void setBusinessID(String paramString)
  {
    this.template.setCustomerID(paramString);
  }
  
  public String getBusinessID()
  {
    return this.template.getCustomerID();
  }
  
  public void setStatus(String paramString)
  {
    this.template.setStatus(paramString);
  }
  
  public void setType(String paramString)
  {
    this.template.setTransferDestination(paramString);
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
  
  protected void updateFromPagingContext(Transfers paramTransfers)
  {
    PagingContext localPagingContext = paramTransfers.getPagingContext();
    if (localPagingContext != null)
    {
      this.bIsFirstPage = localPagingContext.isFirstPage();
      this.bIsLastPage = localPagingContext.isLastPage();
    }
  }
  
  public void setBankingServiceName(String paramString)
  {
    this.bankingServiceName = paramString;
  }
  
  public String getBankingServiceName()
  {
    return this.bankingServiceName;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.GetTransferTemplates
 * JD-Core Version:    0.7.0.1
 */