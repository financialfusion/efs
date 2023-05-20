package com.ffusion.tasks.util;

import com.ffusion.beans.util.AccountUtil;
import com.ffusion.beans.util.UtilException;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetAccountDisplayText
  extends BaseTask
{
  protected String accountDisplayText = null;
  protected String accountID = null;
  protected String localeSessionName = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Locale localLocale;
    if (this.localeSessionName == null) {
      localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
    } else {
      localLocale = (Locale)localHttpSession.getAttribute(this.localeSessionName);
    }
    if (localLocale == null)
    {
      this.error = 41;
      str = this.taskErrorURL;
    }
    else if (this.accountID == null)
    {
      this.error = 50;
      str = this.taskErrorURL;
    }
    else
    {
      try
      {
        this.accountDisplayText = AccountUtil.buildAccountDisplayText(this.accountID, localLocale);
        str = this.successURL;
      }
      catch (UtilException localUtilException)
      {
        this.error = 123;
        str = this.taskErrorURL;
      }
    }
    return str;
  }
  
  public void setAccountID(String paramString)
  {
    this.accountID = paramString;
  }
  
  public void setLocaleSessionName(String paramString)
  {
    this.localeSessionName = paramString;
  }
  
  public String getAccountDisplayText()
  {
    return this.accountDisplayText;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.util.GetAccountDisplayText
 * JD-Core Version:    0.7.0.1
 */