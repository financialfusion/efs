package com.ffusion.tasks.stoppayments;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.stoppayments.StopCheck;
import com.ffusion.beans.stoppayments.StopChecks;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FindStopChecks
  extends StopChecks
  implements Task
{
  protected int error = 0;
  protected String taskErrorURL = "TE";
  protected String successURL;
  protected String startDateStr;
  protected String endDateStr;
  protected DateTime startDate;
  protected DateTime endDate;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.taskErrorURL;
    StopChecks localStopChecks = (StopChecks)localHttpSession.getAttribute("StopChecks");
    this.locale = ((Locale)localHttpSession.getAttribute("java.util.Locale"));
    if (this.locale == null) {
      this.error = 41;
    } else if (localStopChecks == null) {
      this.error = 13020;
    } else if (!setStartDate()) {
      this.error = 13017;
    } else if (!setEndDate()) {
      this.error = 13018;
    } else if ((this.startDate != null) && (this.endDate != null) && (this.startDate.after(this.endDate))) {
      this.error = 13023;
    } else if (processFindStopChecks(localStopChecks)) {
      str = this.successURL;
    }
    return str;
  }
  
  protected boolean processFindStopChecks(StopChecks paramStopChecks)
  {
    boolean bool = true;
    clear();
    Iterator localIterator = paramStopChecks.iterator();
    while (localIterator.hasNext())
    {
      StopCheck localStopCheck = (StopCheck)localIterator.next();
      DateTime localDateTime = localStopCheck.getCheckDateValue();
      if ((localDateTime != null) && ((this.startDate == null) || (!this.startDate.after(localDateTime))) && ((this.endDate == null) || (!this.endDate.before(localDateTime)))) {
        add(localStopCheck);
      }
    }
    return bool;
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
      this.endDate = null;
      this.endDateStr = null;
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
 * Qualified Name:     com.ffusion.tasks.stoppayments.FindStopChecks
 * JD-Core Version:    0.7.0.1
 */