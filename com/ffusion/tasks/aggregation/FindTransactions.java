package com.ffusion.tasks.aggregation;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.aggregation.Account;
import com.ffusion.beans.aggregation.Accounts;
import com.ffusion.beans.aggregation.Transaction;
import com.ffusion.beans.aggregation.Transactions;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
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
  private String nW = "AccountAggregationCollection";
  private String nV = "AggregatedAccount";
  private String nU = "AggTransactions";
  protected static String ACCOUNTID = "ACCOUNTID";
  protected static String STARTDATE = "STARTDATE";
  protected static String ENDDATE = "ENDDATE";
  protected String validate;
  protected boolean processFlag = false;
  protected String accountID;
  protected String keyword;
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
      if ((this.validate.indexOf(ACCOUNTID) != -1) && (this.accountID == null)) {
        this.error = 11008;
      } else if ((this.validate.indexOf(STARTDATE) != -1) && (!this.allDates) && (this.startDate == null)) {
        this.error = 11006;
      } else if ((this.validate.indexOf(ENDDATE) != -1) && (!this.allDates) && (this.endDate == null)) {
        this.error = 11007;
      }
      this.validate = null;
    }
    return this.error == 0;
  }
  
  protected boolean findTransactions(HttpSession paramHttpSession)
  {
    this.error = 0;
    Account localAccount = (Account)paramHttpSession.getAttribute(this.nV);
    if (localAccount == null)
    {
      this.error = 11009;
    }
    else if (!localAccount.getDownloadedItems())
    {
      this.error = 11010;
    }
    else
    {
      Locale localLocale = (Locale)paramHttpSession.getAttribute("java.util.Locale");
      Transactions localTransactions = new Transactions(localLocale);
      searchTransactions(localTransactions, localAccount.getTransactions());
      paramHttpSession.setAttribute(this.nU, localTransactions);
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
      if (this.startDateStr != null) {
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
      } else {
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
        }
        catch (InvalidDateTimeException localInvalidDateTimeException2)
        {
          this.endDate = null;
        }
        this.startDateStr = null;
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
          localObject = localTransaction.getDescription();
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
              this.startDate = ((DateTime)localObject);
            }
            if ((this.endDate == null) || (((DateTime)localObject).after(this.endDate))) {
              this.endDate = ((DateTime)localObject);
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
      Accounts localAccounts = (Accounts)paramHttpSession.getAttribute(this.nW);
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
    return this.number = this.number;
  }
  
  public void setStartDate(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.startDateStr = null;
    } else {
      this.startDateStr = paramString;
    }
  }
  
  public String getStartDate()
  {
    if (this.startDateStr != null) {
      return this.startDateStr;
    }
    if (this.startDate != null) {
      return this.startDate.toString();
    }
    return "";
  }
  
  public void setEndDate(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.endDateStr = null;
    } else {
      this.endDateStr = paramString;
    }
  }
  
  public String getEndDate()
  {
    if (this.endDateStr != null) {
      return this.endDateStr;
    }
    if (this.endDate != null) {
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
  
  public void setTransactionsInSessionName(String paramString)
  {
    this.nU = paramString;
  }
  
  public String getTransactionsInSessionName()
  {
    return this.nU;
  }
  
  public void setAccountsInSessionName(String paramString)
  {
    this.nW = paramString;
  }
  
  public String getAccountsInSessionName()
  {
    return this.nW;
  }
  
  public void setAccountInSessionName(String paramString)
  {
    this.nV = paramString;
  }
  
  public String getAccountInSessionName()
  {
    return this.nV;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.aggregation.FindTransactions
 * JD-Core Version:    0.7.0.1
 */