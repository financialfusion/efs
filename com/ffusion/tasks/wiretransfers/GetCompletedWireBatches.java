package com.ffusion.tasks.wiretransfers;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.wiretransfers.WireBatches;
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
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetCompletedWireBatches
  extends ExtendedBaseTask
  implements WireTaskDefines
{
  protected boolean dateChanged = false;
  protected DateTime startDate = null;
  protected DateTime endDate = null;
  protected String datetype = null;
  protected Locale locale;
  protected String pageSize;
  protected boolean viewAll = false;
  protected String batchDestination;
  protected boolean firstPage = true;
  protected boolean nextPage = false;
  protected boolean previousPage = false;
  protected boolean bIsFirstPage = true;
  protected boolean bIsLastPage = false;
  
  public GetCompletedWireBatches()
  {
    this.collectionSessionName = "CompletedWireBatches";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.locale = ((Locale)localHttpSession.getAttribute("java.util.Locale"));
    String str = this.successURL;
    this.error = 0;
    WireBatches localWireBatches = null;
    localWireBatches = (WireBatches)localHttpSession.getAttribute(this.collectionSessionName);
    if (localWireBatches == null) {
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
    if ((this.batchDestination != null) && (this.batchDestination.length() > 0)) {
      localHashMap.put("DESTINATION", this.batchDestination);
    }
    try
    {
      PagingContext localPagingContext;
      if ((localWireBatches == null) || (this.firstPage == true))
      {
        localPagingContext = new PagingContext(this.startDate, this.endDate);
        localPagingContext.setDirection("FIRST");
        if (this.viewAll == true) {
          localHashMap.put("VIEW", "ALL");
        }
        localWireBatches = Wire.getPagedCompletedWireBatches(localSecureUser, localPagingContext, localHashMap);
      }
      else if (this.previousPage == true)
      {
        localPagingContext = localWireBatches.getPagingContext();
        localPagingContext.setDirection("PREVIOUS");
        localWireBatches = Wire.getPagedCompletedWireBatches(localSecureUser, localPagingContext, localHashMap);
      }
      else if (this.nextPage == true)
      {
        localPagingContext = localWireBatches.getPagingContext();
        localPagingContext.setDirection("NEXT");
        localWireBatches = Wire.getPagedCompletedWireBatches(localSecureUser, localPagingContext, localHashMap);
      }
      if (this.datetype != null) {
        localWireBatches.setDateFormat(this.datetype);
      }
      updateFromPagingContext(localWireBatches);
      localHttpSession.setAttribute(this.collectionSessionName, localWireBatches);
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
      System.out.println("GetCompletedWireBatches.setStartDate:Exception = " + localThrowable.getMessage() + "," + localThrowable.toString());
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
  
  public void setPageSize(String paramString)
  {
    this.pageSize = paramString;
    this.firstPage = true;
  }
  
  public String getPageSize()
  {
    return this.pageSize;
  }
  
  protected void updateFromPagingContext(WireBatches paramWireBatches)
  {
    PagingContext localPagingContext = paramWireBatches.getPagingContext();
    if (localPagingContext != null)
    {
      this.bIsFirstPage = localPagingContext.isFirstPage();
      this.bIsLastPage = localPagingContext.isLastPage();
    }
  }
  
  public void setViewAll(String paramString)
  {
    this.viewAll = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setType(String paramString)
  {
    this.batchDestination = paramString;
  }
  
  public String getType()
  {
    return this.batchDestination;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.GetCompletedWireBatches
 * JD-Core Version:    0.7.0.1
 */