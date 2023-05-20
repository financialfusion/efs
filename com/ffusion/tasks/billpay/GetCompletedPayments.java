package com.ffusion.tasks.billpay;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.beans.billpay.RecPayments;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Billpay;
import com.ffusion.services.BillPay;
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

public class GetCompletedPayments
  extends BaseTask
  implements Task
{
  protected boolean dateChanged = false;
  protected DateTime startDate = null;
  protected DateTime endDate = null;
  private String G3 = null;
  private Locale Ha;
  private boolean G7 = true;
  private boolean G8 = false;
  private boolean G4 = false;
  private boolean G6 = true;
  private boolean G5 = false;
  private String G9;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Payments localPayments = null;
    this.Ha = ((Locale)localHttpSession.getAttribute("java.util.Locale"));
    this.error = 0;
    localPayments = (Payments)localHttpSession.getAttribute("CompletedPayments");
    if (localPayments == null) {
      this.G7 = true;
    }
    if (this.dateChanged)
    {
      this.G7 = true;
      this.G8 = false;
      this.G4 = false;
    }
    if ((this.G7 == true) || (this.G8 == true) || (this.G4 == true))
    {
      HashMap localHashMap = new HashMap();
      BillPay localBillPay = (BillPay)localHttpSession.getAttribute("com.ffusion.services.BillPay");
      if (localBillPay != null) {
        localHashMap.put("SERVICE", localBillPay);
      }
      if ((this.G9 != null) && (this.G9.length() > 0)) {
        localHashMap.put("PAGESIZE", this.G9);
      }
      RecPayments localRecPayments = (RecPayments)localHttpSession.getAttribute("RecPayments");
      if (localRecPayments == null) {
        localRecPayments = new RecPayments();
      }
      localHashMap.put("RecPayments", localRecPayments);
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      Accounts localAccounts = (Accounts)localHttpSession.getAttribute("BillPayAccounts");
      Payees localPayees = (Payees)localHttpSession.getAttribute("Payees");
      try
      {
        if ((localPayments == null) || (this.G7 == true))
        {
          PagingContext localPagingContext = new PagingContext(this.startDate, this.endDate);
          localPayments = Billpay.getPagedCompletedPayments(localSecureUser, localAccounts, localPayees, localPagingContext, localHashMap);
        }
        else if (this.G4 == true)
        {
          localPayments = Billpay.getPreviousCompletedPayments(localSecureUser, localAccounts, localPayees, localPayments.getPagingContext(), localHashMap);
        }
        else if (this.G8 == true)
        {
          localPayments = Billpay.getNextCompletedPayments(localSecureUser, localAccounts, localPayees, localPayments.getPagingContext(), localHashMap);
        }
        if (this.G3 != null) {
          localPayments.setDateFormat(this.G3);
        }
        if (localPayments != null) {
          localPayments.setSortedBy("PAYDATE");
        }
        jdMethod_int(localPayments);
        localRecPayments = (RecPayments)localHashMap.get("RecPayments");
        if (localRecPayments != null) {
          localHttpSession.setAttribute("RecPayments", localRecPayments);
        }
        localPayments.setFilter("All");
        localHttpSession.setAttribute("CompletedPayments", localPayments);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
    }
    this.G7 = false;
    this.G8 = false;
    this.G4 = false;
    this.dateChanged = false;
    return str;
  }
  
  public void setFirstPage(String paramString)
  {
    this.G7 = true;
  }
  
  public void setNextPage(String paramString)
  {
    this.G8 = true;
  }
  
  public void setPreviousPage(String paramString)
  {
    this.G4 = true;
  }
  
  public String getIsFirstPage()
  {
    if (this.G6) {
      return "true";
    }
    return "false";
  }
  
  public String getIsLastPage()
  {
    if (this.G5) {
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
        localDateTime = new DateTime(paramString, this.Ha, this.G3);
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
      System.out.println("GetCompletedPayments.setStartDate:Exception = " + localThrowable.getMessage() + "," + localThrowable.toString());
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
        localDateTime = new DateTime(paramString, this.Ha, this.G3);
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
    this.G3 = paramString;
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
    this.G9 = paramString;
    this.G7 = true;
  }
  
  public String getPageSize()
  {
    return this.G9;
  }
  
  private void jdMethod_int(Payments paramPayments)
  {
    PagingContext localPagingContext = paramPayments.getPagingContext();
    if (localPagingContext != null)
    {
      this.G6 = localPagingContext.isFirstPage();
      this.G5 = localPagingContext.isLastPage();
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpay.GetCompletedPayments
 * JD-Core Version:    0.7.0.1
 */