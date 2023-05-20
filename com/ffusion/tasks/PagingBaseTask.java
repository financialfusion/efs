package com.ffusion.tasks;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.util.FilteredList;
import com.ffusion.util.beans.PagingContext;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public abstract class PagingBaseTask
  extends BaseTask
  implements Task
{
  public static final String VIEW = "VIEW";
  protected String datetype;
  protected boolean dateChanged = false;
  protected boolean viewChanged = false;
  protected String newStartDate = null;
  protected String newEndDate = null;
  protected DateTime startDate;
  protected DateTime endDate;
  protected Locale locale;
  protected String collectionName;
  protected String sortedBy;
  protected boolean firstPage = true;
  protected boolean nextPage = false;
  protected boolean previousPage = false;
  protected boolean bIsFirstPage = true;
  protected boolean bIsLastPage = false;
  protected boolean viewAll = false;
  protected String _pageSize;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    this.locale = ((Locale)localHttpSession.getAttribute("java.util.Locale"));
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    FilteredList localFilteredList = (FilteredList)localHttpSession.getAttribute(this.collectionName);
    Object localObject;
    if (this.dateChanged)
    {
      localObject = stringToDate(this.newStartDate);
      if (localObject == null)
      {
        this.error = 16127;
        return this.taskErrorURL;
      }
      DateTime localDateTime = stringToDate(this.newEndDate);
      if (localDateTime == null)
      {
        this.error = 16128;
        return this.taskErrorURL;
      }
      if ((this.startDate != null) && (this.startDate.getDiff((Calendar)localObject, 3) == 0.0F) && (this.endDate != null) && (this.endDate.getDiff(localDateTime, 3) == 0.0F))
      {
        this.dateChanged = false;
      }
      else
      {
        this.endDate = localDateTime;
        this.startDate = ((DateTime)localObject);
      }
    }
    if (localFilteredList != null) {
      this.sortedBy = localFilteredList.getSortedBy();
    } else {
      this.firstPage = true;
    }
    if ((this.dateChanged) || (this.viewChanged))
    {
      this.firstPage = true;
      this.nextPage = false;
      this.previousPage = false;
    }
    if ((this.firstPage == true) || (this.nextPage == true) || (this.previousPage == true)) {
      try
      {
        if ((this._pageSize != null) && (this._pageSize.length() > 0)) {
          localHashMap.put("PAGESIZE", this._pageSize);
        }
        if ((localFilteredList == null) || (this.firstPage))
        {
          localObject = new PagingContext(this.startDate, this.endDate);
          ((PagingContext)localObject).setDirection("FIRST");
          if (this.viewAll == true) {
            localHashMap.put("VIEW", "ALL");
          }
          localFilteredList = getPaged(localSecureUser, (PagingContext)localObject, localHashMap);
        }
        else if (this.nextPage)
        {
          localFilteredList = getNext(localSecureUser, localFilteredList.getPagingContext(), localHashMap);
        }
        else if (this.previousPage)
        {
          localFilteredList = getPrevious(localSecureUser, localFilteredList.getPagingContext(), localHashMap);
        }
        updateFromPagingContext(localFilteredList);
        postProcessList(localFilteredList, localHttpSession);
        localFilteredList.setSortedBy(this.sortedBy);
        localHttpSession.setAttribute(this.collectionName, localFilteredList);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
    }
    this.firstPage = false;
    this.nextPage = false;
    this.previousPage = false;
    this.dateChanged = false;
    this.viewChanged = false;
    return str;
  }
  
  protected DateTime stringToDate(String paramString)
  {
    DateTime localDateTime = null;
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
        }
      }
    }
    catch (Throwable localThrowable) {}
    return localDateTime;
  }
  
  public abstract FilteredList getPaged(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException;
  
  public abstract FilteredList getNext(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException;
  
  public abstract FilteredList getPrevious(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException;
  
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
    this.newStartDate = paramString;
    this.dateChanged = true;
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
    this.newEndDate = paramString;
    this.dateChanged = true;
  }
  
  public String getEndDate()
  {
    if (this.endDate != null) {
      return this.endDate.toString();
    }
    return null;
  }
  
  public void setDateFormat(String paramString)
  {
    this.datetype = paramString;
  }
  
  public void setPageSize(String paramString)
  {
    this._pageSize = paramString;
    this.firstPage = true;
  }
  
  public String getPageSize()
  {
    return this._pageSize;
  }
  
  protected void updateFromPagingContext(FilteredList paramFilteredList)
  {
    PagingContext localPagingContext = paramFilteredList.getPagingContext();
    if (localPagingContext != null)
    {
      this.bIsFirstPage = localPagingContext.isFirstPage();
      this.bIsLastPage = localPagingContext.isLastPage();
    }
  }
  
  protected void postProcessList(FilteredList paramFilteredList, HttpSession paramHttpSession) {}
  
  public final void setCollectionName(String paramString)
  {
    this.collectionName = paramString;
  }
  
  public void setViewAll(String paramString)
  {
    boolean bool = this.viewAll;
    this.viewAll = Boolean.valueOf(paramString).booleanValue();
    if (bool != this.viewAll) {
      this.viewChanged = true;
    }
  }
  
  public String getViewAll()
  {
    return "" + this.viewAll;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.PagingBaseTask
 * JD-Core Version:    0.7.0.1
 */