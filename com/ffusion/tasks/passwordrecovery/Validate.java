package com.ffusion.tasks.passwordrecovery;

import com.ffusion.beans.user.User;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.PasswordRecovery;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Validate
  extends BaseTask
{
  private String Wx;
  
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
      if ((("PRPasswordAnswer".equals(str1)) && (str2.length() > 50)) || (("PRPasswordAnswer2".equals(str1)) && (str2.length() > 50)))
      {
        this.error = 145;
        return this.taskErrorURL;
      }
      localHashMap1.put(str1, str2);
    }
    String str1 = paramHttpServletRequest.getParameter("PRPasswordAnswer");
    String str2 = paramHttpServletRequest.getParameter("PRPasswordAnswer2");
    if ((str1 != null) && (str1.trim().length() == 0))
    {
      localHttpSession.setAttribute("PasswordAnswerEmpty", "true");
      return this.Wx;
    }
    if ((str2 != null) && (str2.trim().length() == 0))
    {
      localHttpSession.setAttribute("PasswordAnswerEmpty", "true");
      return this.Wx;
    }
    this.error = 0;
    User localUser = (User)localHttpSession.getAttribute("User");
    String str3;
    try
    {
      if (PasswordRecovery.validate(localUser, localHashMap1, localHashMap2))
      {
        str3 = super.getSuccessURL();
      }
      else
      {
        localHttpSession.setAttribute("UserValidateFailure", "true");
        str3 = this.Wx;
      }
    }
    catch (CSILException localCSILException)
    {
      if (localCSILException.getCode() == 33004)
      {
        localHttpSession.setAttribute("UserLockedOut", "true");
        return this.Wx;
      }
      this.error = MapError.mapError(localCSILException);
      str3 = super.getServiceErrorURL();
    }
    return str3;
  }
  
  public void setValidateFailureURL(String paramString)
  {
    this.Wx = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.passwordrecovery.Validate
 * JD-Core Version:    0.7.0.1
 */