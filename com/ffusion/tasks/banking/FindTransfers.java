package com.ffusion.tasks.banking;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.banking.Transfer;
import com.ffusion.beans.banking.Transfers;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FindTransfers
  extends Transfers
  implements Task
{
  protected static String STARTDATE = "STARTDATE";
  protected static String ENDDATE = "ENDDATE";
  protected String startDateStr;
  protected String endDateStr;
  protected DateTime startDate;
  protected DateTime endDate;
  protected DateTime firstDate;
  protected DateTime lastDate;
  protected String successURL = null;
  protected String taskErrorURL = "TE";
  protected int error = 0;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.locale = ((Locale)localHttpSession.getAttribute("java.util.Locale"));
    Transfers localTransfers = getTransfers(localHttpSession);
    String str = this.taskErrorURL;
    if (this.locale == null)
    {
      this.error = 41;
    }
    else if (localTransfers == null)
    {
      this.error = 1003;
    }
    else if (!setStartDate())
    {
      this.error = 1021;
    }
    else if (!setEndDate())
    {
      this.error = 1022;
    }
    else if ((this.startDate != null) && (this.endDate != null) && (this.startDate.after(this.endDate)))
    {
      this.error = 1021;
    }
    else
    {
      findTransfers(localTransfers);
      str = this.successURL;
    }
    return str;
  }
  
  protected Transfers getTransfers(HttpSession paramHttpSession)
  {
    return (Transfers)paramHttpSession.getAttribute("Transfers");
  }
  
  protected void findTransfers(Transfers paramTransfers)
  {
    clear();
    this.firstDate = null;
    this.lastDate = null;
    Iterator localIterator = paramTransfers.iterator();
    while (localIterator.hasNext())
    {
      Transfer localTransfer = (Transfer)localIterator.next();
      DateTime localDateTime = localTransfer.getDateValue();
      if (((this.startDate == null) || (!this.startDate.after(localDateTime))) && ((this.endDate == null) || (!this.endDate.before(localDateTime))))
      {
        add(localTransfer);
        if ((this.firstDate == null) || (this.firstDate.after(localDateTime))) {
          this.firstDate = localDateTime;
        }
        if ((this.lastDate == null) || (this.lastDate.before(localDateTime))) {
          this.lastDate = localDateTime;
        }
      }
    }
  }
  
  protected boolean setStartDate()
  {
    boolean bool = true;
    try
    {
      if (this.startDateStr == null)
      {
        this.startDate = null;
      }
      else
      {
        if (this.startDate == null) {
          this.startDate = new DateTime(this.startDateStr, this.locale, this.dateformat);
        }
        this.startDate.fromString(this.startDateStr);
        this.startDate.set(11, 0);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      this.startDate = null;
      this.startDateStr = null;
      bool = false;
    }
    return bool;
  }
  
  protected boolean setEndDate()
  {
    boolean bool = true;
    try
    {
      if (this.endDateStr == null)
      {
        this.endDate = null;
      }
      else
      {
        if (this.endDate == null) {
          this.endDate = new DateTime(this.endDateStr, this.locale, this.dateformat);
        }
        this.endDate.fromString(this.endDateStr);
        this.endDate.set(11, 23);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      this.startDate = null;
      this.startDateStr = null;
      bool = false;
    }
    return bool;
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
    return this.startDateStr;
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
    return this.endDateStr;
  }
  
  public String getFirstDate()
  {
    if (this.firstDate != null) {
      return this.firstDate.toString();
    }
    return null;
  }
  
  public String getLastDate()
  {
    if (this.lastDate != null) {
      return this.lastDate.toString();
    }
    return null;
  }
  
  public void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.FindTransfers
 * JD-Core Version:    0.7.0.1
 */