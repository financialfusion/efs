package com.ffusion.tasks.util;

import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.Task;
import com.ffusion.util.Localeable;
import com.ffusion.util.ResourceUtil;
import java.io.IOException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Resource
  extends BaseTask
  implements Task, Localeable
{
  private Locale Qq;
  private String Qs;
  private String Qr;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (this.Qq == null) {
      this.Qq = ((Locale)localHttpSession.getAttribute("java.util.Locale"));
    }
    if (this.Qq == null)
    {
      this.error = 41;
      str = this.taskErrorURL;
    }
    else if ((this.Qr != null) && (this.Qr.length() > 0))
    {
      ResourceBundle localResourceBundle = ResourceUtil.getBundle(this.Qr, this.Qq);
      if (localResourceBundle == null)
      {
        this.error = 43;
        str = this.taskErrorURL;
      }
      else
      {
        str = this.successURL;
      }
    }
    else
    {
      str = this.successURL;
    }
    return str;
  }
  
  public void setResourceFilename(String paramString)
  {
    this.Qr = paramString;
  }
  
  public String getResourceFilename()
  {
    return this.Qr;
  }
  
  public void setResourceID(String paramString)
  {
    this.Qs = paramString;
  }
  
  public String getResourceID()
  {
    return this.Qs;
  }
  
  public String getResource()
  {
    String str;
    try
    {
      str = ResourceUtil.getString(this.Qs, this.Qr, this.Qq);
    }
    catch (MissingResourceException localMissingResourceException)
    {
      str = "";
    }
    return str;
  }
  
  public void setLocale(Locale paramLocale)
  {
    this.Qq = paramLocale;
  }
  
  public Locale getLocale()
  {
    return this.Qq;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.util.Resource
 * JD-Core Version:    0.7.0.1
 */