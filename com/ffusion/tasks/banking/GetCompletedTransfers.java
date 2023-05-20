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

public class GetCompletedTransfers
  extends BaseTask
  implements Task
{
  protected boolean dateChanged = false;
  protected DateTime startDate;
  protected DateTime endDate;
  private String zk;
  private Locale zs;
  private String zj = "BankingAccounts";
  private String zq;
  private boolean zo = true;
  private boolean zp = false;
  private boolean zl = false;
  private boolean zn = true;
  private boolean zm = false;
  private String zr;
  protected String bankingServiceName = "com.ffusion.services.Banking";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    this.zs = ((Locale)localHttpSession.getAttribute("java.util.Locale"));
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Transfers localTransfers = (Transfers)localHttpSession.getAttribute("CompletedTransfers");
    Accounts localAccounts = (Accounts)localHttpSession.getAttribute(this.zj);
    HashMap localHashMap = new HashMap();
    if ((this.zq != null) && (this.zq.length() > 0)) {
      localHashMap.put("DESTINATION", this.zq);
    }
    String str2 = "DATE";
    if (localTransfers != null) {
      str2 = localTransfers.getSortedBy();
    } else {
      this.zo = true;
    }
    if (this.dateChanged)
    {
      this.zo = true;
      this.zp = false;
      this.zl = false;
    }
    if ((this.zo == true) || (this.zp == true) || (this.zl == true)) {
      try
      {
        com.ffusion.services.Banking localBanking = (com.ffusion.services.Banking)localHttpSession.getAttribute(this.bankingServiceName);
        localHashMap.put("SERVICE", localBanking);
        RecTransfers localRecTransfers = (RecTransfers)localHttpSession.getAttribute("RecTransfers");
        if (localRecTransfers == null) {
          localRecTransfers = new RecTransfers();
        }
        localHashMap.put("RecTransfers", localRecTransfers);
        if ((this.zr != null) && (this.zr.length() > 0)) {
          localHashMap.put("PAGESIZE", this.zr);
        }
        if ((localTransfers == null) || (this.zo))
        {
          PagingContext localPagingContext = new PagingContext(this.startDate, this.endDate);
          localTransfers = com.ffusion.csil.core.Banking.getPagedCompletedTransfers(localSecureUser, localAccounts, localPagingContext, localHashMap);
        }
        else if (this.zl)
        {
          localTransfers = com.ffusion.csil.core.Banking.getPreviousCompletedTransfers(localSecureUser, localAccounts, localTransfers.getPagingContext(), localHashMap);
        }
        else if (this.zp)
        {
          localTransfers = com.ffusion.csil.core.Banking.getNextCompletedTransfers(localSecureUser, localAccounts, localTransfers.getPagingContext(), localHashMap);
        }
        if ((this.zk != null) && (localTransfers != null)) {
          localTransfers.setDateFormat(this.zk);
        }
        localRecTransfers = (RecTransfers)localHashMap.get("RecTransfers");
        if (localRecTransfers != null) {
          localHttpSession.setAttribute("RecTransfers", localRecTransfers);
        }
        localTransfers.setSortedBy("DATE");
        jdMethod_int(localTransfers);
        localHttpSession.setAttribute("CompletedTransfers", localTransfers);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str1 = this.serviceErrorURL;
      }
    }
    this.zo = false;
    this.zp = false;
    this.zl = false;
    this.dateChanged = false;
    return str1;
  }
  
  public void setFirstPage(String paramString)
  {
    this.zo = true;
  }
  
  public void setNextPage(String paramString)
  {
    this.zp = true;
  }
  
  public void setPreviousPage(String paramString)
  {
    this.zl = true;
  }
  
  public String getIsFirstPage()
  {
    if (this.zn) {
      return "true";
    }
    return "false";
  }
  
  public String getIsLastPage()
  {
    if (this.zm) {
      return "true";
    }
    return "false";
  }
  
  public void setStartDate(String paramString)
  {
    try
    {
      DateTime localDateTime = null;
      DateFormat localDateFormat = DateFormatUtil.getFormatter("M/d/yyyy");
      if ((paramString != null) && (paramString.length() > 0))
      {
        localDateTime = new DateTime(paramString, this.zs, this.zk);
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
      System.out.println("GetCompletedTransfers.setStartDate:Exception = " + localThrowable.getMessage() + "," + localThrowable.toString());
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
        localDateTime = new DateTime(paramString, this.zs, this.zk);
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
    this.zk = paramString;
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
    this.zj = paramString;
  }
  
  public String getAccountsCollection()
  {
    return this.zj;
  }
  
  public void setPageSize(String paramString)
  {
    this.zr = paramString;
    this.zo = true;
  }
  
  public String getPageSize()
  {
    return this.zr;
  }
  
  private void jdMethod_int(Transfers paramTransfers)
  {
    PagingContext localPagingContext = paramTransfers.getPagingContext();
    if (localPagingContext != null)
    {
      this.zn = localPagingContext.isFirstPage();
      this.zm = localPagingContext.isLastPage();
    }
  }
  
  public void setBankingServiceName(String paramString)
  {
    this.bankingServiceName = paramString;
  }
  
  public void setDestination(String paramString)
  {
    this.zq = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.GetCompletedTransfers
 * JD-Core Version:    0.7.0.1
 */