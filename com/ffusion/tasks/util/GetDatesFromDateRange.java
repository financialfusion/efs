package com.ffusion.tasks.util;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.user.UserLocale;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.Task;
import com.ffusion.util.DateFormatUtil;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetDatesFromDateRange
  extends BaseTask
  implements Task
{
  public static final String THIS_MONTH_TO_DATE = "This Month-to-date";
  public static final String YESTERDAY = "Yesterday";
  public static final String LAST_WEEK = "Last Week";
  public static final String LAST_WEEK_TO_DATE = "Last Week-to-date";
  public static final String LAST_4_WEEKS = "Last 4 Weeks";
  public static final String LAST_MONTH = "Last Month";
  public static final String LAST_MONTH_TO_DATE = "Last Month-to-date";
  public static final String NEXT_WEEK = "Next Week";
  public static final String NEXT_4_WEEKS = "Next 4 Weeks";
  public static final String CURRENT_WEEK = "Current Week";
  public static final String CURRENT_4_WEEKS = "Current 4 Weeks";
  public static final String CURRENT_MONTH = "Current Month";
  public static final String NEXT_MONTH = "Next Month";
  public static final String TODAY = "Today";
  public static final String FOUR_WEEKS_ENDING = "4 Weeks Ending";
  public static final String CURRENT_7_DAYS = "Current 7 Days";
  public static final String LAST_7_DAYS = "Last 7 Days";
  public static final String LAST_30_DAYS = "Last 30 Days";
  public static final String LAST_60_DAYS = "Last 60 Days";
  public static final String LAST_90_DAYS = "Last 90 Days";
  public static final String LAST_X_NEXT_X = "Last X Days and Next X Days";
  public static final String CURRENT_30_DAYS = "Current 30 Days";
  public static final String CURRENT_60_DAYS = "Current 60 Days";
  public static final String CURRENT_90_DAYS = "Current 90 Days";
  public static final String CURRENT_NEXT_30_DAYS = "Current and Next 30 Days";
  public static final String NEXT_30_DAYS = "Next 30 Days";
  public static final String CURRENT_SESSION = "Current Session";
  public static final String VAL_RANGE_UNKNOWN = "-1";
  public static final String VAL_THIS_MONTH_TO_DATE = "0";
  public static final String VAL_YESTERDAY = "1";
  public static final String VAL_LAST_WEEK = "2";
  public static final String VAL_LAST_WEEK_TO_DATE = "3";
  public static final String VAL_LAST_4_WEEKS = "4";
  public static final String VAL_LAST_MONTH = "5";
  public static final String VAL_LAST_MONTH_TO_DATE = "6";
  public static final String VAL_NEXT_WEEK = "7";
  public static final String VAL_NEXT_4_WEEKS = "8";
  public static final String VAL_CURRENT_WEEK = "9";
  public static final String VAL_CURRENT_4_WEEKS = "10";
  public static final String VAL_NEXT_MONTH = "11";
  public static final String VAL_TODAY = "12";
  public static final String VAL_FOUR_WEEKS_ENDING = "13";
  public static final String VAL_CURRENT_7_DAYS = "14";
  public static final String VAL_LAST_7_DAYS = "15";
  public static final String VAL_LAST_30_DAYS = "16";
  public static final String VAL_LAST_X_NEXT_X = "17";
  public static final String VAL_CURRENT_30_DAYS = "18";
  public static final String VAL_CURRENT_60_DAYS = "19";
  public static final String VAL_CURRENT_90_DAYS = "20";
  public static final String VAL_CURRENT_NEXT_30_DAYS = "21";
  public static final String VAL_NEXT_30_DAYS = "22";
  public static final String VAL_CURRENT_SESSION = "23";
  public static final String VAL_CURRENT_MONTH = "24";
  private static final String[] Ri = { "This Month-to-date", "Yesterday", "Last Week", "Last Week-to-date", "Last 4 Weeks", "Last Month", "Last Month-to-date", "Next Week", "Next 4 Weeks", "Current Week", "Current 4 Weeks", "Next Month", "Today", "4 Weeks Ending", "Current 7 Days", "Last 7 Days", "Last 30 Days", "Last X Days and Next X Days", "Current 30 Days", "Current 60 Days", "Current 90 Days", "Current and Next 30 Days", "Next 30 Days", "Current Session", "Current Month" };
  private String Rf;
  private String Rl;
  private String Rj;
  private String Rk;
  private String Rh = null;
  private String Rm;
  private String Re;
  private int Rg = 0;
  private int Rd = 0;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    DateTime localDateTime = null;
    String str1 = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    UserLocale localUserLocale = (UserLocale)localHttpSession.getAttribute("UserLocale");
    String str2 = null;
    if ((this.Rh == null) || (this.Rh.length() <= 0))
    {
      if (this.Rf != null) {
        str2 = (String)localHttpSession.getAttribute(this.Rf);
      }
      if (str2 == null)
      {
        this.error = 73;
        return this.taskErrorURL;
      }
    }
    else
    {
      str2 = this.Rh;
    }
    try
    {
      int i = Integer.parseInt(str2);
      if ((i >= 0) && (i < Ri.length)) {
        str2 = Ri[i];
      }
    }
    catch (NumberFormatException localNumberFormatException) {}
    String str3 = null;
    String str4 = null;
    DateFormat localDateFormat;
    if (localUserLocale != null) {
      localDateFormat = DateFormatUtil.getFormatter(localUserLocale.getDateFormat());
    } else {
      localDateFormat = DateFormatUtil.getFormatter("MM/dd/yyyy");
    }
    Calendar localCalendar1;
    Object localObject;
    if (str2.equalsIgnoreCase("This Month-to-date"))
    {
      localCalendar1 = Calendar.getInstance();
      localCalendar1.setTime(new Date());
      localCalendar1.set(5, 1);
      str3 = localDateFormat.format(localCalendar1.getTime());
      localObject = new Date();
      str4 = localDateFormat.format((Date)localObject);
    }
    else if (str2.equalsIgnoreCase("Yesterday"))
    {
      localCalendar1 = Calendar.getInstance();
      localCalendar1.setTime(new Date());
      localCalendar1.add(5, -1);
      str3 = localDateFormat.format(localCalendar1.getTime());
      str4 = str3;
    }
    else if (str2.equalsIgnoreCase("Today"))
    {
      localCalendar1 = Calendar.getInstance();
      localCalendar1.setTime(new Date());
      str3 = localDateFormat.format(localCalendar1.getTime());
      str4 = str3;
    }
    else if (str2.equalsIgnoreCase("Current 7 Days"))
    {
      localCalendar1 = Calendar.getInstance();
      localCalendar1.setTime(new Date());
      localObject = Calendar.getInstance();
      localCalendar1.add(5, -7);
      str3 = localDateFormat.format(localCalendar1.getTime());
      str4 = localDateFormat.format(((Calendar)localObject).getTime());
    }
    else if (str2.equalsIgnoreCase("Last 7 Days"))
    {
      localCalendar1 = Calendar.getInstance();
      localCalendar1.setTime(new Date());
      localObject = Calendar.getInstance();
      localCalendar1.add(5, -7);
      ((Calendar)localObject).add(5, -1);
      str3 = localDateFormat.format(localCalendar1.getTime());
      str4 = localDateFormat.format(((Calendar)localObject).getTime());
    }
    else if (str2.equalsIgnoreCase("Last 30 Days"))
    {
      localCalendar1 = Calendar.getInstance();
      localCalendar1.setTime(new Date());
      localObject = Calendar.getInstance();
      localCalendar1.add(5, -30);
      ((Calendar)localObject).add(5, -1);
      str3 = localDateFormat.format(localCalendar1.getTime());
      str4 = localDateFormat.format(((Calendar)localObject).getTime());
    }
    else if (str2.equalsIgnoreCase("Last 60 Days"))
    {
      localCalendar1 = Calendar.getInstance();
      localCalendar1.setTime(new Date());
      localObject = Calendar.getInstance();
      localCalendar1.add(5, -60);
      ((Calendar)localObject).add(5, -1);
      str3 = localDateFormat.format(localCalendar1.getTime());
      str4 = localDateFormat.format(((Calendar)localObject).getTime());
    }
    else if (str2.equalsIgnoreCase("Last 90 Days"))
    {
      localCalendar1 = Calendar.getInstance();
      localCalendar1.setTime(new Date());
      localObject = Calendar.getInstance();
      localCalendar1.add(5, -90);
      ((Calendar)localObject).add(5, -1);
      str3 = localDateFormat.format(localCalendar1.getTime());
      str4 = localDateFormat.format(((Calendar)localObject).getTime());
    }
    else if (str2.equalsIgnoreCase("Last X Days and Next X Days"))
    {
      localCalendar1 = Calendar.getInstance();
      localCalendar1.setTime(new Date());
      localObject = Calendar.getInstance();
      ((Calendar)localObject).add(5, this.Rd);
      localCalendar1.add(5, -this.Rg);
      str3 = localDateFormat.format(localCalendar1.getTime());
      str4 = localDateFormat.format(((Calendar)localObject).getTime());
    }
    else if (str2.equalsIgnoreCase("Last Week"))
    {
      localCalendar1 = Calendar.getInstance();
      localCalendar1.setTime(new Date());
      localCalendar1.add(5, -7);
      localObject = localCalendar1.getTime();
      switch (localCalendar1.get(7))
      {
      case 7: 
        localCalendar1.add(5, -1);
      case 6: 
        localCalendar1.add(5, -1);
      case 5: 
        localCalendar1.add(5, -1);
      case 4: 
        localCalendar1.add(5, -1);
      case 3: 
        localCalendar1.add(5, -1);
      case 2: 
        localCalendar1.add(5, -1);
      case 1: 
        str3 = localDateFormat.format(localCalendar1.getTime());
      }
      localCalendar1.setTime((Date)localObject);
      switch (localCalendar1.get(7))
      {
      case 1: 
        localCalendar1.add(5, 1);
      case 2: 
        localCalendar1.add(5, 1);
      case 3: 
        localCalendar1.add(5, 1);
      case 4: 
        localCalendar1.add(5, 1);
      case 5: 
        localCalendar1.add(5, 1);
      case 6: 
        localCalendar1.add(5, 1);
      case 7: 
        str4 = localDateFormat.format(localCalendar1.getTime());
      }
    }
    else if (str2.equalsIgnoreCase("Last Week-to-date"))
    {
      localCalendar1 = Calendar.getInstance();
      localCalendar1.setTime(new Date());
      localCalendar1.add(5, -7);
      localObject = localCalendar1.getTime();
      switch (localCalendar1.get(7))
      {
      case 7: 
        localCalendar1.add(5, -1);
      case 6: 
        localCalendar1.add(5, -1);
      case 5: 
        localCalendar1.add(5, -1);
      case 4: 
        localCalendar1.add(5, -1);
      case 3: 
        localCalendar1.add(5, -1);
      case 2: 
        localCalendar1.add(5, -1);
      case 1: 
        str3 = localDateFormat.format(localCalendar1.getTime());
      }
      str4 = localDateFormat.format(new Date());
    }
    else if (str2.equalsIgnoreCase("Last 4 Weeks"))
    {
      localCalendar1 = Calendar.getInstance();
      localCalendar1.setTime(new Date());
      localCalendar1.add(5, -28);
      switch (localCalendar1.get(7))
      {
      case 7: 
        localCalendar1.add(5, -1);
      case 6: 
        localCalendar1.add(5, -1);
      case 5: 
        localCalendar1.add(5, -1);
      case 4: 
        localCalendar1.add(5, -1);
      case 3: 
        localCalendar1.add(5, -1);
      case 2: 
        localCalendar1.add(5, -1);
      case 1: 
        str3 = localDateFormat.format(localCalendar1.getTime());
      }
      localCalendar1 = Calendar.getInstance();
      localCalendar1.setTime(new Date());
      localCalendar1.add(5, -7);
      switch (localCalendar1.get(7))
      {
      case 1: 
        localCalendar1.add(5, 1);
      case 2: 
        localCalendar1.add(5, 1);
      case 3: 
        localCalendar1.add(5, 1);
      case 4: 
        localCalendar1.add(5, 1);
      case 5: 
        localCalendar1.add(5, 1);
      case 6: 
        localCalendar1.add(5, 1);
      case 7: 
        str4 = localDateFormat.format(localCalendar1.getTime());
      }
    }
    else if (str2.equalsIgnoreCase("Last Month"))
    {
      localCalendar1 = Calendar.getInstance();
      localCalendar1.setTime(new Date());
      localCalendar1.add(2, -1);
      localCalendar1.set(5, 1);
      str3 = localDateFormat.format(localCalendar1.getTime());
      localCalendar1.set(5, localCalendar1.getActualMaximum(5));
      str4 = localDateFormat.format(localCalendar1.getTime());
    }
    else if (str2.equalsIgnoreCase("Last Month-to-date"))
    {
      localCalendar1 = Calendar.getInstance();
      localCalendar1.setTime(new Date());
      localCalendar1.add(2, -1);
      localCalendar1.set(5, 1);
      str3 = localDateFormat.format(localCalendar1.getTime());
      str4 = localDateFormat.format(new Date());
    }
    else if (str2.equalsIgnoreCase("Next Week"))
    {
      localCalendar1 = Calendar.getInstance();
      localCalendar1.setTime(new Date());
      localCalendar1.add(5, 7);
      localObject = localCalendar1.getTime();
      switch (localCalendar1.get(7))
      {
      case 7: 
        localCalendar1.add(5, -1);
      case 6: 
        localCalendar1.add(5, -1);
      case 5: 
        localCalendar1.add(5, -1);
      case 4: 
        localCalendar1.add(5, -1);
      case 3: 
        localCalendar1.add(5, -1);
      case 2: 
        localCalendar1.add(5, -1);
      case 1: 
        str3 = localDateFormat.format(localCalendar1.getTime());
      }
      localCalendar1.setTime((Date)localObject);
      switch (localCalendar1.get(7))
      {
      case 1: 
        localCalendar1.add(5, 1);
      case 2: 
        localCalendar1.add(5, 1);
      case 3: 
        localCalendar1.add(5, 1);
      case 4: 
        localCalendar1.add(5, 1);
      case 5: 
        localCalendar1.add(5, 1);
      case 6: 
        localCalendar1.add(5, 1);
      case 7: 
        str4 = localDateFormat.format(localCalendar1.getTime());
      }
    }
    else if (str2.equalsIgnoreCase("Next 4 Weeks"))
    {
      localCalendar1 = Calendar.getInstance();
      localCalendar1.setTime(new Date());
      str3 = localDateFormat.format(localCalendar1.getTime());
      localCalendar1.add(5, 28);
      str4 = localDateFormat.format(localCalendar1.getTime());
    }
    else if (str2.equalsIgnoreCase("Current Week"))
    {
      localCalendar1 = Calendar.getInstance();
      localCalendar1.setTime(new Date());
      switch (localCalendar1.get(7))
      {
      case 7: 
        localCalendar1.add(5, -1);
      case 6: 
        localCalendar1.add(5, -1);
      case 5: 
        localCalendar1.add(5, -1);
      case 4: 
        localCalendar1.add(5, -1);
      case 3: 
        localCalendar1.add(5, -1);
      case 2: 
        localCalendar1.add(5, -1);
      case 1: 
        str3 = localDateFormat.format(localCalendar1.getTime());
      }
      localCalendar1 = Calendar.getInstance();
      localCalendar1.setTime(new Date());
      switch (localCalendar1.get(7))
      {
      case 1: 
        localCalendar1.add(5, 1);
      case 2: 
        localCalendar1.add(5, 1);
      case 3: 
        localCalendar1.add(5, 1);
      case 4: 
        localCalendar1.add(5, 1);
      case 5: 
        localCalendar1.add(5, 1);
      case 6: 
        localCalendar1.add(5, 1);
      case 7: 
        str4 = localDateFormat.format(localCalendar1.getTime());
      }
    }
    else if (str2.equalsIgnoreCase("Current 4 Weeks"))
    {
      localCalendar1 = Calendar.getInstance();
      localCalendar1.setTime(new Date());
      localCalendar1.add(5, -21);
      switch (localCalendar1.get(7))
      {
      case 7: 
        localCalendar1.add(5, -1);
      case 6: 
        localCalendar1.add(5, -1);
      case 5: 
        localCalendar1.add(5, -1);
      case 4: 
        localCalendar1.add(5, -1);
      case 3: 
        localCalendar1.add(5, -1);
      case 2: 
        localCalendar1.add(5, -1);
      case 1: 
        str3 = localDateFormat.format(localCalendar1.getTime());
      }
      localCalendar1 = Calendar.getInstance();
      localCalendar1.setTime(new Date());
      switch (localCalendar1.get(7))
      {
      case 1: 
        localCalendar1.add(5, 1);
      case 2: 
        localCalendar1.add(5, 1);
      case 3: 
        localCalendar1.add(5, 1);
      case 4: 
        localCalendar1.add(5, 1);
      case 5: 
        localCalendar1.add(5, 1);
      case 6: 
        localCalendar1.add(5, 1);
      case 7: 
        str4 = localDateFormat.format(localCalendar1.getTime());
      }
    }
    else if (str2.equalsIgnoreCase("Next Month"))
    {
      localCalendar1 = Calendar.getInstance();
      localCalendar1.setTime(new Date());
      localCalendar1.add(2, 1);
      localCalendar1.set(5, 1);
      str3 = localDateFormat.format(localCalendar1.getTime());
      localCalendar1.set(5, localCalendar1.getActualMaximum(5));
      str4 = localDateFormat.format(localCalendar1.getTime());
    }
    else if (str2.equalsIgnoreCase("Current Month"))
    {
      localCalendar1 = Calendar.getInstance();
      localCalendar1.setTime(new Date());
      localCalendar1.set(5, 1);
      str3 = localDateFormat.format(localCalendar1.getTime());
      localCalendar1.set(5, localCalendar1.getActualMaximum(5));
      str4 = localDateFormat.format(localCalendar1.getTime());
    }
    else if ((str2.equalsIgnoreCase("4 Weeks Ending")) && (this.Rk != null))
    {
      localCalendar1 = Calendar.getInstance();
      try
      {
        localObject = localDateFormat.parse(this.Rk);
        localCalendar1.setTime((Date)localObject);
      }
      catch (ParseException localParseException)
      {
        return this.taskErrorURL;
      }
      switch (localCalendar1.get(7))
      {
      case 1: 
        localCalendar1.add(5, -21);
        break;
      case 2: 
        localCalendar1.add(5, -22);
        break;
      case 3: 
        localCalendar1.add(5, -23);
        break;
      case 4: 
        localCalendar1.add(5, -24);
        break;
      case 5: 
        localCalendar1.add(5, -25);
        break;
      case 6: 
        localCalendar1.add(5, -26);
        break;
      case 7: 
        localCalendar1.add(5, -27);
      }
      str3 = localDateFormat.format(localCalendar1.getTime());
    }
    else
    {
      Calendar localCalendar2;
      if (str2.equalsIgnoreCase("Current 30 Days"))
      {
        localCalendar1 = Calendar.getInstance();
        localCalendar1.setTime(new Date());
        localCalendar2 = Calendar.getInstance();
        localCalendar1.add(5, -29);
        str3 = localDateFormat.format(localCalendar1.getTime());
        str4 = localDateFormat.format(localCalendar2.getTime());
      }
      else if (str2.equalsIgnoreCase("Current 60 Days"))
      {
        localCalendar1 = Calendar.getInstance();
        localCalendar1.setTime(new Date());
        localCalendar2 = Calendar.getInstance();
        localCalendar1.add(5, -59);
        str3 = localDateFormat.format(localCalendar1.getTime());
        str4 = localDateFormat.format(localCalendar2.getTime());
      }
      else if (str2.equalsIgnoreCase("Current 90 Days"))
      {
        localCalendar1 = Calendar.getInstance();
        localCalendar1.setTime(new Date());
        localCalendar2 = Calendar.getInstance();
        localCalendar1.add(5, -89);
        str3 = localDateFormat.format(localCalendar1.getTime());
        str4 = localDateFormat.format(localCalendar2.getTime());
      }
      else if (str2.equalsIgnoreCase("Current and Next 30 Days"))
      {
        localCalendar1 = Calendar.getInstance();
        localCalendar1.setTime(new Date());
        localCalendar2 = Calendar.getInstance();
        localCalendar1.add(5, -29);
        localCalendar2.add(5, 30);
        str3 = localDateFormat.format(localCalendar1.getTime());
        str4 = localDateFormat.format(localCalendar2.getTime());
      }
      else if (str2.equalsIgnoreCase("Next 30 Days"))
      {
        localCalendar1 = Calendar.getInstance();
        localCalendar1.setTime(new Date());
        localCalendar2 = Calendar.getInstance();
        localCalendar2.add(5, 30);
        str3 = localDateFormat.format(localCalendar1.getTime());
        str4 = localDateFormat.format(localCalendar2.getTime());
      }
      else if ("Current Session".equalsIgnoreCase(str2))
      {
        localDateTime = (DateTime)localHttpSession.getAttribute("SessionLoginTime");
        if (localDateTime == null)
        {
          this.error = 100200;
          return this.taskErrorURL;
        }
        localCalendar1 = Calendar.getInstance();
        localCalendar1.setTimeInMillis(localDateTime.getTimeInMillis());
        localCalendar2 = Calendar.getInstance();
        localCalendar2.setTime(new Date());
        str3 = localDateFormat.format(localCalendar1.getTime());
        str4 = localDateFormat.format(localCalendar2.getTime());
      }
      else
      {
        return this.taskErrorURL;
      }
    }
    this.Rm = str3;
    this.Re = str4;
    if (this.Rl != null) {
      localHttpSession.setAttribute(this.Rl, str3);
    }
    if (this.Rj != null) {
      localHttpSession.setAttribute(this.Rj, str4);
    }
    return str1;
  }
  
  public void setDateRange(String paramString)
  {
    this.Rf = paramString;
  }
  
  public void setStartDate(String paramString)
  {
    this.Rl = paramString;
  }
  
  public void setEndDate(String paramString)
  {
    this.Rj = paramString;
  }
  
  public String getStartDate()
  {
    return this.Rm;
  }
  
  public String getEndDate()
  {
    return this.Re;
  }
  
  public void setEndOfFourWeeks(String paramString)
  {
    this.Rk = paramString;
  }
  
  public void setDateRangeValue(String paramString)
  {
    this.Rh = paramString;
  }
  
  public String getDateRangeValue()
  {
    return this.Rh;
  }
  
  public void setPreviousDays(String paramString)
  {
    try
    {
      setPreviousDays(Integer.valueOf(paramString).intValue());
    }
    catch (NumberFormatException localNumberFormatException)
    {
      setPreviousDays(0);
    }
  }
  
  public void setPreviousDays(int paramInt)
  {
    if (paramInt < 0) {
      paramInt = 0;
    }
    this.Rg = paramInt;
  }
  
  public void setFutureDays(String paramString)
  {
    try
    {
      setFutureDays(Integer.valueOf(paramString).intValue());
    }
    catch (NumberFormatException localNumberFormatException)
    {
      setFutureDays(0);
    }
  }
  
  public void setFutureDays(int paramInt)
  {
    if (paramInt < 0) {
      paramInt = 0;
    }
    this.Rd = paramInt;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.util.GetDatesFromDateRange
 * JD-Core Version:    0.7.0.1
 */