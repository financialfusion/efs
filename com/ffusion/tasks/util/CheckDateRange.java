package com.ffusion.tasks.util;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.user.UserLocale;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CheckDateRange
  extends BaseTask
{
  private String Q7;
  private String Q9;
  private boolean Q8 = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    UserLocale localUserLocale = (UserLocale)localHttpSession.getAttribute("UserLocale");
    String str = null;
    if (localUserLocale != null) {
      str = localUserLocale.getDateFormat();
    } else {
      str = "MM/dd/yyyy";
    }
    if (!jdMethod_for(getStartDate(), str, localHttpSession))
    {
      this.error = 1021;
      return this.taskErrorURL;
    }
    if (!jdMethod_for(getEndDate(), str, localHttpSession))
    {
      this.error = 1022;
      return this.taskErrorURL;
    }
    if ((this.Q8) && (!jdMethod_for(getStartDate(), getEndDate(), str, localHttpSession)))
    {
      this.error = 1023;
      return this.taskErrorURL;
    }
    if ((!this.Q8) && (this.Q7 != null) && (this.Q9 != null) && (this.Q7.trim().length() != 0) && (this.Q9.trim().length() != 0) && (!this.Q7.equals(str)) && (!this.Q9.equals(str)) && (!jdMethod_for(this.Q7, this.Q9, str, localHttpSession)))
    {
      this.error = 1023;
      return this.taskErrorURL;
    }
    return this.successURL;
  }
  
  private boolean jdMethod_for(String paramString1, String paramString2, String paramString3, HttpSession paramHttpSession)
  {
    Locale localLocale = (Locale)paramHttpSession.getAttribute("java.util.Locale");
    try
    {
      DateTime localDateTime1 = new DateTime(paramString1, localLocale, paramString3);
      DateTime localDateTime2 = new DateTime(paramString2, localLocale, paramString3);
      if (localDateTime1.compare(localDateTime2) == 1) {
        return false;
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      return false;
    }
    return true;
  }
  
  private boolean jdMethod_for(String paramString1, String paramString2, HttpSession paramHttpSession)
  {
    Locale localLocale = (Locale)paramHttpSession.getAttribute("java.util.Locale");
    if ((paramString1 == null) || (paramString1.trim().length() == 0) || (paramString1.equals(paramString2))) {
      return !this.Q8;
    }
    try
    {
      DateTime localDateTime = new DateTime(paramString1, localLocale, paramString2);
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      return false;
    }
    return true;
  }
  
  public void setStartDate(String paramString)
  {
    this.Q7 = paramString;
  }
  
  public String getStartDate()
  {
    return this.Q7;
  }
  
  public void setEndDate(String paramString)
  {
    this.Q9 = paramString;
  }
  
  public String getEndDate()
  {
    return this.Q9;
  }
  
  public String getRequireBoth()
  {
    return new Boolean(this.Q8).toString();
  }
  
  public void setRequireBoth(String paramString)
  {
    this.Q8 = Boolean.getBoolean(paramString);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.util.CheckDateRange
 * JD-Core Version:    0.7.0.1
 */