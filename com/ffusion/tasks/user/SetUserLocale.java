package com.ffusion.tasks.user;

import com.ffusion.beans.user.UserLocale;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.UserLocaleCore;
import com.ffusion.csil.core.Util;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.UserLocaleConsts;
import java.io.IOException;
import java.util.Locale;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetUserLocale
  extends BaseTask
  implements UserLocaleConsts
{
  private String aKU;
  private String aKW = null;
  private String aKT = null;
  private String aKV = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    UserLocale localUserLocale = (UserLocale)localHttpSession.getAttribute("UserLocale");
    if (this.aKW == null) {
      if (this.aKT == null)
      {
        this.aKW = ((String)localHttpSession.getAttribute("default_locale"));
      }
      else
      {
        this.aKW = this.aKT;
        if (this.aKV != null) {
          this.aKW = (this.aKW + "_" + this.aKV);
        }
      }
    }
    if (this.aKW == null) {
      try
      {
        this.aKW = Util.getDefaultLanguage(null).toString();
      }
      catch (CSILException localCSILException1)
      {
        this.aKW = Locale.getDefault().toString();
      }
    }
    StringTokenizer localStringTokenizer = new StringTokenizer(this.aKW, "_");
    String str1 = null;
    String str2 = null;
    if (localStringTokenizer.hasMoreTokens()) {
      str1 = localStringTokenizer.nextToken();
    }
    if (localStringTokenizer.hasMoreTokens()) {
      str2 = localStringTokenizer.nextToken();
    }
    if ((str1 == null) || (str1.length() == 0))
    {
      str1 = Locale.getDefault().getLanguage();
      str2 = Locale.getDefault().getCountry();
    }
    if ((localUserLocale == null) || (!localUserLocale.getLanguage().equalsIgnoreCase(str1)) || (!localUserLocale.getCountry().equalsIgnoreCase(str2)))
    {
      try
      {
        if (str2 == null) {
          str2 = "";
        }
        localUserLocale = UserLocaleCore.getUserLocale(str1, str2);
      }
      catch (CSILException localCSILException2)
      {
        this.error = MapError.mapError(localCSILException2);
        this.aKU = this.serviceErrorURL;
      }
      if (localUserLocale == null)
      {
        this.error = 70000;
        this.aKU = this.serviceErrorURL;
      }
      else
      {
        localHttpSession.setAttribute("UserLocale", localUserLocale);
        Locale localLocale = new Locale(str1, str2);
        localHttpSession.setAttribute("java.util.Locale", localLocale);
        this.aKU = this.successURL;
      }
    }
    else
    {
      this.aKU = this.successURL;
    }
    return this.aKU;
  }
  
  public void setLocaleName(String paramString)
  {
    this.aKW = paramString;
  }
  
  public void setLanguage(String paramString)
  {
    this.aKT = paramString;
  }
  
  public void setCountry(String paramString)
  {
    this.aKV = paramString;
  }
  
  public String getLanguage()
  {
    return this.aKT;
  }
  
  public String getCountry()
  {
    return this.aKV;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.SetUserLocale
 * JD-Core Version:    0.7.0.1
 */