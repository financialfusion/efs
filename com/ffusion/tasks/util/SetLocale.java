package com.ffusion.tasks.util;

import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetLocale
  extends BaseTask
{
  protected String language = "en";
  protected String country = "US";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Locale localLocale = new Locale(this.language, this.country);
    localHttpSession.setAttribute("java.util.Locale", localLocale);
    return this.successURL;
  }
  
  public void setLanguage(String paramString)
  {
    this.language = paramString;
  }
  
  public void setCountry(String paramString)
  {
    this.country = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.util.SetLocale
 * JD-Core Version:    0.7.0.1
 */