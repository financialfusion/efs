package com.ffusion.tasks.banking;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.banking.Transaction;
import com.ffusion.beans.banking.Transactions;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FindTransactions
  extends BaseTask
  implements Task
{
  protected static String ACCOUNTID = "ACCOUNTID";
  protected static String STARTDATE = "STARTDATE";
  protected static String ENDDATE = "ENDDATE";
  protected String validate;
  protected boolean processFlag = false;
  protected String accountID;
  protected String keyword;
  protected String searchField = "DESCRIPTION";
  protected String number;
  protected DateTime startDate;
  protected DateTime endDate;
  protected String startDateStr;
  protected String endDateStr;
  protected String sortBy;
  protected boolean allDates;
  protected boolean currentMonth;
  protected Locale locale;
  protected String datetype = "SHORT";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    this.locale = ((Locale)localHttpSession.getAttribute("java.util.Locale"));
    if (validateInput(localHttpSession))
    {
      if (this.processFlag)
      {
        this.processFlag = false;
        if (findTransactions(localHttpSession)) {
          str = this.successURL;
        } else {
          str = this.taskErrorURL;
        }
      }
      else
      {
        str = this.successURL;
      }
    }
    else {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
  {
    this.error = 0;
    if (this.validate != null)
    {
      DateTime localDateTime1 = new DateTime(Calendar.getInstance(), Locale.US);
      if ((this.validate.indexOf(ACCOUNTID) != -1) && (this.accountID == null)) {
        this.error = 1029;
      }
      DateTime localDateTime2;
      if ((this.startDateStr != null) && (this.startDateStr.length() > 0) && (this.error == 0) && (this.validate.indexOf(STARTDATE) != -1))
      {
        localDateTime2 = null;
        try
        {
          localDateTime2 = new DateTime(this.startDateStr, this.locale, this.datetype);
          if ((localDateTime2 == null) || (!localDateTime2.isValid()) || (localDateTime2.after(localDateTime1))) {
            this.error = 1021;
          }
        }
        catch (InvalidDateTimeException localInvalidDateTimeException1)
        {
          this.error = 1021;
        }
      }
      if ((this.endDateStr != null) && (this.endDateStr.length() > 0) && (this.error == 0) && (this.validate.indexOf(ENDDATE) != -1))
      {
        localDateTime2 = null;
        try
        {
          localDateTime2 = new DateTime(this.endDateStr, this.locale, this.datetype);
          if ((localDateTime2 == null) || (!localDateTime2.isValid()) || (localDateTime2.after(localDateTime1))) {
            this.error = 1022;
          }
        }
        catch (InvalidDateTimeException localInvalidDateTimeException2)
        {
          this.error = 1022;
        }
      }
      setStartDate();
      setEndDate();
      if ((this.error == 0) && ((this.validate.indexOf(STARTDATE) != -1) || (this.validate.indexOf(ENDDATE) != -1)) && (this.startDate != null) && (this.endDate != null) && (this.startDate.after(this.endDate))) {
        this.error = 1023;
      }
      this.validate = null;
    }
    return this.error == 0;
  }
  
  protected boolean findTransactions(HttpSession paramHttpSession)
  {
    this.error = 0;
    Account localAccount = (Account)paramHttpSession.getAttribute("Account");
    if (localAccount == null)
    {
      this.error = 1002;
    }
    else if (!localAccount.getDownloadedItems())
    {
      this.error = 1024;
    }
    else
    {
      Locale localLocale = null;
      if (localAccount.getTransactions() != null) {
        localLocale = localAccount.getTransactions().getLocale();
      } else {
        localLocale = localAccount.getLocale();
      }
      Transactions localTransactions = new Transactions(localLocale);
      searchTransactions(localTransactions, localAccount.getTransactions());
      paramHttpSession.setAttribute("Transactions", localTransactions);
    }
    return this.error == 0;
  }
  
  protected void searchTransactions(Transactions paramTransactions1, Transactions paramTransactions2)
  {
    if (this.allDates)
    {
      this.startDate = null;
      this.endDate = null;
    }
    else
    {
      if (this.startDateStr != null)
      {
        try
        {
          if (this.startDate == null) {
            this.startDate = new DateTime(this.startDateStr, this.locale, this.datetype);
          }
          this.startDate.fromString(this.startDateStr);
          this.startDate.set(11, 0);
        }
        catch (InvalidDateTimeException localInvalidDateTimeException1)
        {
          this.startDate = null;
        }
        this.startDateStr = null;
      }
      else
      {
        this.startDate = null;
      }
      if (this.endDateStr != null)
      {
        try
        {
          if (this.endDate == null) {
            this.endDate = new DateTime(this.endDateStr, this.locale, this.datetype);
          }
          this.endDate.fromString(this.endDateStr);
          this.endDate.set(11, 23);
          this.endDate.set(12, 59);
          this.endDate.set(13, 59);
        }
        catch (InvalidDateTimeException localInvalidDateTimeException2)
        {
          this.endDate = null;
        }
        this.endDateStr = null;
      }
      else
      {
        this.endDate = null;
      }
    }
    if (paramTransactions2 != null)
    {
      Iterator localIterator = paramTransactions2.iterator();
      while (localIterator.hasNext())
      {
        Transaction localTransaction = (Transaction)localIterator.next();
        Object localObject;
        if (this.keyword != null)
        {
          localObject = null;
          if (this.searchField.equalsIgnoreCase("MEMO")) {
            localObject = localTransaction.getMemo();
          } else {
            localObject = localTransaction.getDescription();
          }
          if (localObject != null)
          {
            localObject = ((String)localObject).toLowerCase();
            if (((String)localObject).indexOf(this.keyword) == -1) {}
          }
        }
        else if ((this.number == null) || (this.number.equals(localTransaction.getReferenceNumber())))
        {
          if (this.allDates)
          {
            localObject = localTransaction.getDateValue();
            if ((this.startDate == null) || (((DateTime)localObject).before(this.startDate))) {
              this.startDate = ((DateTime)((DateTime)localObject).clone());
            }
            if ((this.endDate == null) || (((DateTime)localObject).after(this.endDate))) {
              this.endDate = ((DateTime)((DateTime)localObject).clone());
            }
          }
          else if (this.currentMonth)
          {
            localObject = localTransaction.getDateValue();
            if ((((DateTime)localObject).before(this.startDate)) || (((DateTime)localObject).after(this.endDate))) {
              continue;
            }
          }
          else if ((this.startDate != null) && (this.endDate != null))
          {
            localObject = localTransaction.getDateValue();
            if ((((DateTime)localObject).before(this.startDate)) || (((DateTime)localObject).after(this.endDate))) {
              continue;
            }
          }
          else
          {
            if (this.startDate != null ? localTransaction.getDateValue().before(this.startDate) : (this.endDate != null) && (localTransaction.getDateValue().after(this.endDate))) {
              continue;
            }
          }
          paramTransactions1.add(localTransaction);
        }
      }
      if ((this.sortBy != null) && (this.sortBy.length() > 0)) {
        paramTransactions1.setSortedBy(this.sortBy);
      }
      this.sortBy = null;
    }
  }
  
  protected Account getAccount(HttpSession paramHttpSession)
  {
    Account localAccount = null;
    if (this.accountID != null)
    {
      Accounts localAccounts = (Accounts)paramHttpSession.getAttribute("BankingAccounts");
      localAccount = localAccounts.getByID(this.accountID);
    }
    return localAccount;
  }
  
  public void setAccountID(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0)) {
      this.accountID = paramString;
    }
  }
  
  public String getAccountID()
  {
    return this.accountID;
  }
  
  public void setKeyword(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0)) {
      this.keyword = paramString.toLowerCase();
    } else {
      this.keyword = null;
    }
  }
  
  public String getKeyword()
  {
    return this.keyword;
  }
  
  public void setNumber(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0)) {
      this.number = paramString;
    } else {
      this.number = null;
    }
  }
  
  public String getNumber()
  {
    return this.number;
  }
  
  public void setStartDate(String paramString)
  {
    this.startDateStr = paramString;
  }
  
  public boolean setStartDate()
  {
    boolean bool = true;
    try
    {
      if (this.startDateStr == null)
      {
        this.startDate = new DateTime(this.startDateStr, this.locale, this.datetype);
      }
      else
      {
        if (this.startDate == null) {
          this.startDate = new DateTime(this.startDateStr, this.locale, this.datetype);
        }
        this.startDate.fromString(this.startDateStr);
      }
      this.startDate.set(11, 0);
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      this.startDate = null;
      this.startDateStr = null;
      bool = false;
    }
    return bool;
  }
  
  public String getStartDate()
  {
    if (this.startDateStr != null) {
      return this.startDateStr;
    }
    if (this.startDate != null)
    {
      this.startDate.setFormat(this.datetype);
      return this.startDate.toString();
    }
    return "";
  }
  
  public void setEndDate(String paramString)
  {
    this.endDateStr = paramString;
  }
  
  public boolean setEndDate()
  {
    boolean bool = true;
    try
    {
      if (this.endDateStr == null)
      {
        this.endDate = new DateTime(this.endDateStr, this.locale, this.datetype);
      }
      else
      {
        if (this.endDate == null) {
          this.endDate = new DateTime(this.endDateStr, this.locale, this.datetype);
        }
        this.endDate.fromString(this.endDateStr);
      }
      this.endDate.set(11, 0);
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      this.endDate = null;
      this.endDateStr = null;
      bool = false;
    }
    return bool;
  }
  
  public String getEndDate()
  {
    if (this.endDateStr != null) {
      return this.endDateStr;
    }
    if (this.endDate != null)
    {
      this.endDate.setFormat(this.datetype);
      return this.endDate.toString();
    }
    return "";
  }
  
  public void setAllDates(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0)) {
      this.allDates = Boolean.valueOf(paramString).booleanValue();
    } else {
      this.allDates = false;
    }
  }
  
  public String getAllDates()
  {
    return String.valueOf(this.allDates);
  }
  
  public void setCurrentMonth(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0)) {
      this.currentMonth = Boolean.valueOf(paramString).booleanValue();
    } else {
      this.currentMonth = false;
    }
    if (this.currentMonth)
    {
      this.endDate = new DateTime(this.locale);
      this.endDate.setFormat(this.datetype);
      this.endDateStr = this.endDate.toString();
      this.startDate = new DateTime(this.locale);
      this.startDate.setFormat(this.datetype);
      this.startDate.add(5, -30);
      this.startDateStr = this.startDate.toString();
    }
  }
  
  public String getCurrentMonth()
  {
    return String.valueOf(this.currentMonth);
  }
  
  public void setDateFormat(String paramString)
  {
    this.datetype = paramString;
  }
  
  public void setSortedBy(String paramString)
  {
    this.sortBy = paramString;
  }
  
  public String getSortedBy()
  {
    return this.sortBy;
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
  
  public Locale getLocale()
  {
    return this.locale;
  }
  
  public String getDateFormat()
  {
    return this.datetype;
  }
  
  public void setSearchField(String paramString)
  {
    this.searchField = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.FindTransactions
 * JD-Core Version:    0.7.0.1
 */