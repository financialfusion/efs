package com.ffusion.tasks.billpay;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FindPayments
  extends BaseTask
  implements Task
{
  protected static String STARTDATE = "STARTDATE";
  protected static String ENDDATE = "ENDDATE";
  protected String accountID;
  protected String payeeID;
  protected String payeeName;
  protected String allAccounts;
  protected String allPayees;
  protected String startDate;
  protected String endDate;
  protected String status;
  protected String allDates;
  protected String sortBy;
  protected int payHistoryPayeesPaymentStatus = 5;
  protected String validate;
  protected boolean processFlag = false;
  protected String datetype = "SHORT";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = processThis(localHttpSession, "Payments");
    return str;
  }
  
  protected String processThis(HttpSession paramHttpSession, String paramString)
  {
    Payments localPayments = (Payments)paramHttpSession.getAttribute(paramString);
    String str = this.successURL;
    if (validatePayments(localPayments, paramHttpSession) == 0)
    {
      setPaymentsSettings(localPayments);
      str = this.successURL;
      paramHttpSession.setAttribute("PayHistoryPayees", localPayments.getPayees(this.payHistoryPayeesPaymentStatus));
    }
    else
    {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  protected int validatePayments(Payments paramPayments, HttpSession paramHttpSession)
  {
    this.error = 0;
    if (this.validate != null)
    {
      if ((this.validate.indexOf(STARTDATE) != -1) && (!validateDate(this.startDate, paramHttpSession)))
      {
        this.error = 2024;
      }
      else if ((this.validate.indexOf(ENDDATE) != -1) && (!validateDate(this.endDate, paramHttpSession)))
      {
        this.error = 2025;
      }
      else if ((this.validate.indexOf(STARTDATE) != -1) && (this.validate.indexOf(ENDDATE) != -1) && (this.startDate != null) && (this.startDate.trim().length() > 0) && (this.endDate != null) && (this.endDate.trim().length() > 0))
      {
        Locale localLocale = (Locale)paramHttpSession.getAttribute("java.util.Locale");
        try
        {
          DateTime localDateTime1 = new DateTime(this.startDate, localLocale, this.datetype);
          DateTime localDateTime2 = new DateTime(this.endDate, localLocale, this.datetype);
          if (localDateTime1.after(localDateTime2)) {
            this.error = 2024;
          }
        }
        catch (InvalidDateTimeException localInvalidDateTimeException)
        {
          this.error = 2024;
        }
      }
      this.validate = null;
    }
    return this.error;
  }
  
  protected boolean validateDate(String paramString, HttpSession paramHttpSession)
  {
    boolean bool = true;
    if ((paramString != null) && (paramString.trim().length() > 0))
    {
      Locale localLocale = (Locale)paramHttpSession.getAttribute("java.util.Locale");
      try
      {
        DateTime localDateTime = new DateTime(paramString, localLocale, this.datetype);
      }
      catch (InvalidDateTimeException localInvalidDateTimeException)
      {
        bool = false;
        this.error = 2021;
      }
    }
    return bool;
  }
  
  protected void setPaymentsSettings(Payments paramPayments)
  {
    if (this.accountID != null) {
      paramPayments.setAccountID(this.accountID);
    }
    if (this.payeeID != null) {
      paramPayments.setPayeeID(this.payeeID);
    }
    if (this.payeeName != null) {
      paramPayments.setPayeeName(this.payeeName);
    }
    if (this.allAccounts != null) {
      paramPayments.setAllAccounts(this.allAccounts);
    }
    if (this.allPayees != null) {
      paramPayments.setAllPayees(this.allPayees);
    }
    if (this.startDate != null) {
      paramPayments.setStartDate(this.startDate);
    }
    if (this.endDate != null) {
      paramPayments.setEndDate(this.endDate);
    }
    if (this.status != null) {
      paramPayments.setStatus(this.status);
    }
    if (this.allDates != null) {
      paramPayments.setAllDates(this.allDates);
    }
    if (this.sortBy != null) {
      paramPayments.setSortedBy(this.sortBy);
    }
    this.accountID = null;
    this.payeeID = null;
    this.payeeName = null;
    this.allAccounts = null;
    this.allPayees = null;
    this.status = null;
    this.allDates = null;
    this.sortBy = null;
  }
  
  public void setAccountID(String paramString)
  {
    this.accountID = paramString;
  }
  
  public String getAccountID()
  {
    return this.accountID;
  }
  
  public void setPayeeID(String paramString)
  {
    this.payeeID = paramString;
  }
  
  public void setPayeeName(String paramString)
  {
    this.payeeName = paramString;
  }
  
  public String getPayeeName()
  {
    return this.payeeName;
  }
  
  public void setAllAccounts(String paramString)
  {
    this.allAccounts = paramString;
  }
  
  public void setAllPayees(String paramString)
  {
    this.allPayees = paramString;
  }
  
  public void setStartDate(String paramString)
  {
    this.startDate = paramString;
    this.allDates = "FALSE";
  }
  
  public String getStartDate()
  {
    if (this.startDate != null) {
      return this.startDate;
    }
    return "";
  }
  
  public void setEndDate(String paramString)
  {
    this.endDate = paramString;
    this.allDates = "FALSE";
  }
  
  public String getEndDate()
  {
    if (this.endDate != null) {
      return this.endDate;
    }
    return "";
  }
  
  public void setStatus(String paramString)
  {
    this.status = paramString;
  }
  
  public void setAllDates(String paramString)
  {
    this.allDates = paramString;
  }
  
  public void setSortedBy(String paramString)
  {
    this.sortBy = paramString;
  }
  
  public void setProcess(String paramString)
  {
    this.processFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setValidate(String paramString)
  {
    this.validate = null;
    if (!paramString.equals("")) {
      this.validate = paramString.toUpperCase();
    }
  }
  
  public void setDateFormat(String paramString)
  {
    this.datetype = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpay.FindPayments
 * JD-Core Version:    0.7.0.1
 */