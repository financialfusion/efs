package com.ffusion.tasks.portal;

import com.ffusion.csil.core.Portal;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetCityDisplayName
  extends BaseTask
  implements Task
{
  private String ms;
  private String mr;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
    if (localLocale == null)
    {
      this.error = 9015;
      return this.taskErrorURL;
    }
    this.mr = Portal.getFullCityName(this.ms, localLocale);
    return str;
  }
  
  public void setCityCode(String paramString)
  {
    this.ms = paramString;
  }
  
  public String getCityDisplayName()
  {
    return this.mr;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.portal.GetCityDisplayName
 * JD-Core Version:    0.7.0.1
 */