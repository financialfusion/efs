package com.ffusion.tasks.alerts;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.alerts.LogMessage;
import com.ffusion.beans.alerts.LogMessages;
import com.ffusion.beans.user.UserLocale;
import java.io.IOException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FindLogMessages
  extends LogMessages
  implements Task
{
  protected static String ALERTID = "ALERTID";
  protected static String TYPE = "TYPE";
  protected static String STARTDATE = "STARTDATE";
  protected static String ENDDATE = "ENDDATE";
  protected static final String DATE_STRING_FORMAT = "MM/DD/YYYY";
  protected String alertID;
  protected String type;
  protected String startDateStr;
  protected String endDateStr;
  protected DateTime startDate;
  protected DateTime endDate;
  protected DateTime firstDate;
  protected DateTime lastDate;
  protected String deliveryChannelsToExclude;
  protected int error = 0;
  protected String successURL = null;
  protected String taskErrorURL = "TE";
  protected String logMessagesName = "LogMessages";
  private String k3 = "";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.locale = ((Locale)localHttpSession.getAttribute("java.util.Locale"));
    UserLocale localUserLocale = (UserLocale)localHttpSession.getAttribute("UserLocale");
    if (localUserLocale != null) {
      this.k3 = localUserLocale.getDateFormat();
    }
    LogMessages localLogMessages = getLogMessages(localHttpSession);
    String str = this.taskErrorURL;
    if (this.locale == null)
    {
      this.error = 41;
    }
    else if (localLogMessages == null)
    {
      this.error = 19003;
    }
    else if (!setStartDate())
    {
      this.error = 19102;
    }
    else if (!setEndDate())
    {
      this.error = 19103;
    }
    else if ((this.startDate != null) && (this.endDate != null) && (this.startDate.after(this.endDate)))
    {
      this.error = 19102;
    }
    else if (isDateInvalid(this.locale))
    {
      this.error = 19114;
    }
    else
    {
      findLogMessages(localLogMessages);
      str = this.successURL;
    }
    return str;
  }
  
  protected boolean isDateInvalid(Locale paramLocale)
  {
    boolean bool = false;
    DateTime localDateTime = new DateTime(Calendar.getInstance(), paramLocale);
    localDateTime.set(11, 0);
    localDateTime.set(12, 0);
    localDateTime.set(13, 0);
    localDateTime.add(5, 1);
    localDateTime.add(14, -1);
    if ((this.startDate != null) && (this.startDate.after(localDateTime))) {
      bool = true;
    } else if ((this.endDate != null) && (this.endDate.after(localDateTime))) {
      bool = true;
    }
    return bool;
  }
  
  protected LogMessages getLogMessages(HttpSession paramHttpSession)
  {
    return (LogMessages)paramHttpSession.getAttribute("LogMessages");
  }
  
  protected void findLogMessages(LogMessages paramLogMessages)
  {
    clear();
    this.firstDate = null;
    this.lastDate = null;
    Iterator localIterator = paramLogMessages.iterator();
    while (localIterator.hasNext())
    {
      int i = 1;
      LogMessage localLogMessage = (LogMessage)localIterator.next();
      DateTime localDateTime = localLogMessage.getDateValue();
      if ((this.startDate != null) && (this.startDate.after(localDateTime)))
      {
        i = 0;
      }
      else if ((this.endDate != null) && (this.endDate.before(localDateTime)))
      {
        i = 0;
      }
      else if ((this.alertID != null) && (this.alertID.length() != 0) && (this.alertID.compareTo(localLogMessage.getAlertID()) != 0))
      {
        i = 0;
      }
      else if ((this.type != null) && (this.type.length() != 0) && (this.type.compareTo(localLogMessage.getType()) != 0))
      {
        i = 0;
      }
      else
      {
        if ((this.deliveryChannelsToExclude != null) && (this.deliveryChannelsToExclude.trim().length() != 0))
        {
          int j = 0;
          StringTokenizer localStringTokenizer = new StringTokenizer(this.deliveryChannelsToExclude, ",");
          while (localStringTokenizer.hasMoreTokens())
          {
            String str = localStringTokenizer.nextToken();
            if ((str != null) && (localLogMessage.getDeliveryChannel().compareTo(str) == 0)) {
              j = 1;
            } else {
              j = j;
            }
          }
          if (j != 0)
          {
            i = 0;
            continue;
          }
        }
        if (i != 0)
        {
          add(localLogMessage);
          if ((this.firstDate == null) || (this.firstDate.after(localDateTime))) {
            this.firstDate = localDateTime;
          }
          if ((this.lastDate == null) || (this.lastDate.before(localDateTime))) {
            this.lastDate = localDateTime;
          }
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
          this.startDate = new DateTime(this.startDateStr, this.locale, this.datetype);
        }
        this.startDate.fromString(this.startDateStr);
        this.startDate.set(11, 0);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      this.startDate = null;
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
          this.endDate = new DateTime(this.endDateStr, this.locale, this.datetype);
        }
        this.endDate.fromString(this.endDateStr);
        this.endDate.set(11, 23);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      this.endDate = null;
      bool = false;
    }
    return bool;
  }
  
  public void setStartDate(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0) || (paramString.equals(this.k3))) {
      this.startDateStr = null;
    } else {
      this.startDateStr = paramString;
    }
  }
  
  public String getStartDate()
  {
    if (this.startDateStr == null) {
      return this.k3;
    }
    return this.startDateStr;
  }
  
  public void setEndDate(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0) || (paramString.equals(this.k3))) {
      this.endDateStr = null;
    } else {
      this.endDateStr = paramString;
    }
  }
  
  public String getEndDate()
  {
    if (this.endDateStr == null) {
      return this.k3;
    }
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
  
  public String getAlertID()
  {
    return this.alertID;
  }
  
  public void setAlertID(String paramString)
  {
    this.alertID = paramString;
  }
  
  public String getType()
  {
    return this.type;
  }
  
  public void setType(String paramString)
  {
    this.type = paramString;
  }
  
  public String getDeliveryChannelsToExclude()
  {
    return this.deliveryChannelsToExclude;
  }
  
  public void setDeliveryChannelsToExclude(String paramString)
  {
    this.deliveryChannelsToExclude = paramString;
  }
  
  public final void setLogMessagesName(String paramString)
  {
    this.logMessagesName = paramString;
  }
  
  public final String getLogMessagesName()
  {
    return this.logMessagesName;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.alerts.FindLogMessages
 * JD-Core Version:    0.7.0.1
 */