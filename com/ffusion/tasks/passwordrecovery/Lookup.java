package com.ffusion.tasks.passwordrecovery;

import com.ffusion.beans.user.User;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.PasswordRecovery;
import com.ffusion.efs.adapters.profile.SignonSettings;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Lookup
  extends BaseTask
{
  private String Wv;
  private String Ww;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    Enumeration localEnumeration = paramHttpServletRequest.getParameterNames();
    while (localEnumeration.hasMoreElements())
    {
      str1 = (String)localEnumeration.nextElement();
      str2 = paramHttpServletRequest.getParameter(str1);
      if (("PREmailAddress".equals(str1)) && (str2.length() > 40))
      {
        this.error = 146;
        return this.taskErrorURL;
      }
      if (("PRUserName".equals(str1)) && (str2.length() > 20))
      {
        this.error = 128;
        return this.taskErrorURL;
      }
      localHashMap1.put(str1, str2);
    }
    String str1 = paramHttpServletRequest.getParameter("CustomerID");
    String str2 = paramHttpServletRequest.getParameter("PRUserName");
    String str3 = paramHttpServletRequest.getParameter("PREmailAddress");
    String str4 = paramHttpServletRequest.getParameter("CustomerType");
    if (((str4 == null) || (!str4.equals(String.valueOf(2)))) && (SignonSettings.allowDuplicateUserNames()) && ((str1 == null) || (str1.trim().length() == 0)))
    {
      localHttpSession.setAttribute("CustomerIDEmpty", "true");
      return this.Wv;
    }
    if ((str2 == null) || (str2.trim().length() == 0))
    {
      localHttpSession.setAttribute("UserNameEmpty", "true");
      return this.Wv;
    }
    if ((str3 != null) && (str3.trim().length() == 0))
    {
      localHttpSession.setAttribute("EmailAddressEmpty", "true");
      return this.Wv;
    }
    this.error = 0;
    String str5;
    try
    {
      User localUser = PasswordRecovery.lookup(localHashMap1, localHashMap2);
      if (localUser != null)
      {
        String str6 = localUser.getPasswordClue();
        String str7 = localUser.getPasswordReminder();
        String str8 = localUser.getPasswordClue2();
        String str9 = localUser.getPasswordReminder2();
        if ((str6 == null) || (str6.trim().length() == 0) || (str7 == null) || (str7.trim().length() == 0) || (str8 == null) || (str8.trim().length() == 0) || (str9 == null) || (str9.trim().length() == 0))
        {
          str5 = this.Ww;
        }
        else
        {
          localHttpSession.setAttribute("User", localUser);
          str5 = super.getSuccessURL();
        }
      }
      else
      {
        localHttpSession.setAttribute("UserLookupFailure", "true");
        str5 = this.Wv;
      }
    }
    catch (CSILException localCSILException)
    {
      if (localCSILException.getCode() == 33004)
      {
        localHttpSession.setAttribute("UserLockedOut", "true");
        return this.Wv;
      }
      if (localCSILException.getCode() == 33008)
      {
        localHttpSession.setAttribute("UserExpired", "true");
        return this.Wv;
      }
      this.error = MapError.mapError(localCSILException);
      str5 = super.getServiceErrorURL();
    }
    return str5;
  }
  
  public void setLookupFailureURL(String paramString)
  {
    this.Wv = paramString;
  }
  
  public void setResetNotConfiguredURL(String paramString)
  {
    this.Ww = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.passwordrecovery.Lookup
 * JD-Core Version:    0.7.0.1
 */