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

public class GetPendingPayments
  extends BaseTask
  implements Task
{
  protected boolean dateChanged = false;
  protected DateTime startDate = null;
  protected DateTime endDate = null;
  private String GV = null;
  private Locale G2;
  private String G1;
  private boolean GZ = true;
  private boolean G0 = false;
  private boolean GW = false;
  private boolean GY = true;
  private boolean GX = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Payments localPayments = null;
    this.G2 = ((Locale)localHttpSession.getAttribute("java.util.Locale"));
    this.error = 0;
    localPayments = (Payments)localHttpSession.getAttribute("PendingPayments");
    if (localPayments == null) {
      this.GZ = true;
    }
    if (this.dateChanged)
    {
      this.GZ = true;
      this.G0 = false;
      this.GW = false;
    }
    if ((this.GZ == true) || (this.G0 == true) || (this.GW == true))
    {
      HashMap localHashMap = new HashMap();
      BillPay localBillPay = (BillPay)localHttpSession.getAttribute("com.ffusion.services.BillPay");
      if (localBillPay != null) {
        localHashMap.put("SERVICE", localBillPay);
      }
      RecPayments localRecPayments = (RecPayments)localHttpSession.getAttribute("RecPayments");
      if (localRecPayments == null) {
        localRecPayments = new RecPayments();
      }
      localHashMap.put("RecPayments", localRecPayments);
      if ((this.G1 != null) && (this.G1.length() > 0)) {
        localHashMap.put("PAGESIZE", this.G1);
      }
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      Accounts localAccounts = (Accounts)localHttpSession.getAttribute("BillPayAccounts");
      Payees localPayees = (Payees)localHttpSession.getAttribute("Payees");
      try
      {
        if ((localPayments == null) || (this.GZ == true))
        {
          PagingContext localPagingContext = new PagingContext(this.startDate, this.endDate);
          localPayments = Billpay.getPagedPendingPayments(localSecureUser, localAccounts, localPayees, localPagingContext, localHashMap);
        }
        else if (this.GW == true)
        {
          localPayments = Billpay.getPreviousPendingPayments(localSecureUser, localAccounts, localPayees, localPayments.getPagingContext(), localHashMap);
        }
        else if (this.G0 == true)
        {
          localPayments = Billpay.getNextPendingPayments(localSecureUser, localAccounts, localPayees, localPayments.getPagingContext(), localHashMap);
        }
        if (this.GV != null) {
          localPayments.setDateFormat(this.GV);
        }
        if (localPayments != null) {
          localPayments.setSortedBy("PAYDATE");
        }
        jdMethod_for(localPayments);
        localRecPayments = (RecPayments)localHashMap.get("RecPayments");
        if (localRecPayments != null) {
          localHttpSession.setAttribute("RecPayments", localRecPayments);
        }
        localHttpSession.setAttribute("PendingPayments", localPayments);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
    }
    this.GZ = false;
    this.G0 = false;
    this.GW = false;
    this.dateChanged = false;
    return str;
  }
  
  public void setFirstPage(String paramString)
  {
    this.GZ = true;
  }
  
  public void setNextPage(String paramString)
  {
    this.G0 = true;
  }
  
  public void setPreviousPage(String paramString)
  {
    this.GW = true;
  }
  
  public String getIsFirstPage()
  {
    if (this.GY) {
      return "true";
    }
    return "false";
  }
  
  public String getIsLastPage()
  {
    if (this.GX) {
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
        localDateTime = new DateTime(paramString, this.G2, this.GV);
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
        localDateTime = new DateTime(paramString, this.G2, this.GV);
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
    this.GV = paramString;
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
    this.G1 = paramString;
    this.GZ = true;
  }
  
  public String getPageSize()
  {
    return this.G1;
  }
  
  private void jdMethod_for(Payments paramPayments)
  {
    PagingContext localPagingContext = paramPayments.getPagingContext();
    if (localPagingContext != null)
    {
      this.GY = localPagingContext.isFirstPage();
      this.GX = localPagingContext.isLastPage();
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpay.GetPendingPayments
 * JD-Core Version:    0.7.0.1
 */