package com.ffusion.tasks.util;

import com.ffusion.beans.DateTime;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.Task;
import java.io.IOException;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetCurrentDate
  extends BaseTask
  implements Task
{
  protected String dateFormat;
  protected Locale locale;
  protected int addDays = 0;
  protected DateTime dateTime;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.locale = ((Locale)localHttpSession.getAttribute("java.util.Locale"));
    if (this.locale == null)
    {
      this.error = 41;
      str = this.taskErrorURL;
    }
    else
    {
      this.dateTime = new DateTime(this.locale);
      if (this.dateFormat != null) {
        this.dateTime.setFormat(this.dateFormat);
      }
      str = this.successURL;
    }
    return str;
  }
  
  public void setDateFormat(String paramString)
  {
    this.dateFormat = paramString;
    if (this.dateTime != null) {
      this.dateTime.setFormat(paramString);
    }
  }
  
  public void setAdditionalDays(String paramString)
  {
    try
    {
      this.addDays = Integer.parseInt(paramString);
    }
    catch (Exception localException)
    {
      this.addDays = 0;
    }
  }
  
  public String getDate()
  {
    if (this.dateTime != null) {
      return this.dateTime.toString();
    }
    return null;
  }
  
  public String getDateWithAdditionalDays()
  {
    String str = null;
    if (this.dateTime != null)
    {
      this.dateTime.add(5, this.addDays);
      str = this.dateTime.toString();
      this.dateTime.add(5, this.addDays * -1);
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.util.GetCurrentDate
 * JD-Core Version:    0.7.0.1
 */