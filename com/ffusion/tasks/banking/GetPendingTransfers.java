package com.ffusion.tasks.banking;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.banking.RecTransfers;
import com.ffusion.beans.banking.Transfers;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.DateFormatUtil;
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

public class GetPendingTransfers
  extends BaseTask
  implements Task
{
  protected boolean dateChanged = false;
  protected DateTime startDate;
  protected DateTime endDate;
  private String yu;
  private Locale yC;
  private String yt = "BankingAccounts";
  private String yA;
  private boolean yy = true;
  private boolean yz = false;
  private boolean yv = false;
  private boolean yx = true;
  private boolean yw = false;
  private String yB;
  protected String bankingServiceName = "com.ffusion.services.Banking";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.yC = ((Locale)localHttpSession.getAttribute("java.util.Locale"));
    this.error = 0;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Transfers localTransfers = (Transfers)localHttpSession.getAttribute("PendingTransfers");
    Accounts localAccounts = (Accounts)localHttpSession.getAttribute(this.yt);
    HashMap localHashMap = new HashMap();
    if ((this.yA != null) && (this.yA.length() > 0)) {
      localHashMap.put("DESTINATION", this.yA);
    }
    String str2 = "DATE";
    if (localTransfers != null) {
      str2 = localTransfers.getSortedBy();
    } else {
      this.yy = true;
    }
    if (this.dateChanged)
    {
      this.yy = true;
      this.yz = false;
      this.yv = false;
    }
    if ((this.yy == true) || (this.yz == true) || (this.yv == true)) {
      try
      {
        com.ffusion.services.Banking localBanking = (com.ffusion.services.Banking)localHttpSession.getAttribute(this.bankingServiceName);
        localHashMap.put("SERVICE", localBanking);
        RecTransfers localRecTransfers = (RecTransfers)localHttpSession.getAttribute("RecTransfers");
        if (localRecTransfers == null) {
          localRecTransfers = new RecTransfers();
        }
        localHashMap.put("RecTransfers", localRecTransfers);
        if ((this.yB != null) && (this.yB.length() > 0)) {
          localHashMap.put("PAGESIZE", this.yB);
        }
        if ((localTransfers == null) || (this.yy))
        {
          PagingContext localPagingContext = new PagingContext(this.startDate, this.endDate);
          localTransfers = com.ffusion.csil.core.Banking.getPagedPendingTransfers(localSecureUser, localAccounts, localPagingContext, localHashMap);
        }
        else if (this.yv)
        {
          localTransfers = com.ffusion.csil.core.Banking.getPreviousPendingTransfers(localSecureUser, localAccounts, localTransfers.getPagingContext(), localHashMap);
        }
        else if (this.yz)
        {
          localTransfers = com.ffusion.csil.core.Banking.getNextPendingTransfers(localSecureUser, localAccounts, localTransfers.getPagingContext(), localHashMap);
        }
        if (this.yu != null) {
          localTransfers.setDateFormat(this.yu);
        }
        localTransfers.setSortedBy("DATE");
        jdMethod_for(localTransfers);
        localRecTransfers = (RecTransfers)localHashMap.get("RecTransfers");
        if (localRecTransfers != null) {
          localHttpSession.setAttribute("RecTransfers", localRecTransfers);
        }
        localHttpSession.setAttribute("PendingTransfers", localTransfers);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str1 = this.serviceErrorURL;
      }
    }
    this.yy = false;
    this.yz = false;
    this.yv = false;
    this.dateChanged = false;
    return str1;
  }
  
  public void setFirstPage(String paramString)
  {
    this.yy = true;
  }
  
  public void setNextPage(String paramString)
  {
    this.yz = true;
  }
  
  public void setPreviousPage(String paramString)
  {
    this.yv = true;
  }
  
  public String getIsFirstPage()
  {
    if (this.yx) {
      return "true";
    }
    return "false";
  }
  
  public String getIsLastPage()
  {
    if (this.yw) {
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
        localDateTime = new DateTime(paramString, this.yC, this.yu);
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
      System.out.println("GetPendingTransfers.setStartDate:Exception = " + localThrowable.getMessage() + "," + localThrowable.toString());
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
        localDateTime = new DateTime(paramString, this.yC, this.yu);
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
    this.yu = paramString;
  }
  
  public String getEndDate()
  {
    if (this.endDate != null) {
      return this.endDate.toString();
    }
    return null;
  }
  
  public void setAccountsCollection(String paramString)
  {
    this.yt = paramString;
  }
  
  public String getAccountsCollection()
  {
    return this.yt;
  }
  
  public void setPageSize(String paramString)
  {
    this.yB = paramString;
    this.yy = true;
  }
  
  public String getPageSize()
  {
    return this.yB;
  }
  
  private void jdMethod_for(Transfers paramTransfers)
  {
    PagingContext localPagingContext = paramTransfers.getPagingContext();
    if (localPagingContext != null)
    {
      this.yx = localPagingContext.isFirstPage();
      this.yw = localPagingContext.isLastPage();
    }
  }
  
  public void setBankingServiceName(String paramString)
  {
    this.bankingServiceName = paramString;
  }
  
  public void setDestination(String paramString)
  {
    this.yA = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.GetPendingTransfers
 * JD-Core Version:    0.7.0.1
 */